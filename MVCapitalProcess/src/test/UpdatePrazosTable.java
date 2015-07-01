package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mvcapital.mysql.MySQLAccess;
import mvcapital.utils.WorkingDays;

import com.jcraft.jsch.SftpATTRS;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UpdatePrazosTable
{
	private static Connection conn=null;
	private static ArrayList<Date> datasEstoque = new ArrayList<Date>();
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	
	public UpdatePrazosTable()
	{

	}
	
	public static void main(String[] args)
	{
			MySQLAccess.readConf();
			MySQLAccess mysqlAccess = new MySQLAccess();
			mysqlAccess.connect();
			UpdatePrazosTable.conn = (Connection) mysqlAccess.getConn();
			UpdatePrazosTable.setupDatasEstoque(conn);
			UpdatePrazosTable.allPrazos();
	}
	
	public static void setupDatasEstoque(Connection conn)
	{
		ArrayList<Date> datasEstoque = new ArrayList<Date>();
		String query = "select distinct Data from estoque;"; //$NON-NLS-1$
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		try
		{
			while (rs.next())
			{
				Date dataEstoque = rs.getDate("Data"); //$NON-NLS-1$
				datasEstoque.add(dataEstoque);
				System.out.println(dataEstoque);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		UpdatePrazosTable.datasEstoque=datasEstoque;
	}
	
	public static void allPrazos()
	{
		for(Date dataEstoque:UpdatePrazosTable.datasEstoque)
		{
			UpdatePrazosTable.prazos(dataEstoque);
		}
	}

	public static void prazos(Date dataEstoque)
	{
		ArrayList<Integer> idTitulos = new ArrayList<Integer>();
		idTitulos = titulosDataEstoque(dataEstoque);
		for(int idTitulo:idTitulos)
		{
			updatePrazos(idTitulo, dataEstoque);
		}
	}
	
	public static ArrayList<Integer> titulosDataEstoque(Date dataEstoque)
	{
		ArrayList<Integer> idTitulos = new ArrayList<Integer>();
		String query = "select idTitulo from Estoque" //$NON-NLS-1$
				+ " where " //$NON-NLS-1$
				+ " Data=" + "'" + sdfd.format(dataEstoque) + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		try
		{
			while (rs.next())
			{
				int idTitulo=rs.getInt("idTitulo"); //$NON-NLS-1$
				if(idTitulo!=0)
				{
					idTitulos.add(idTitulo);
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return idTitulos;
	}
	
	public static void updatePrazos(int idTitulo, Date dataEstoque)
	{
		if(existPrazos(idTitulo, dataEstoque))
		{
//			System.out.println("Prazo exist!"); //$NON-NLS-1$
		}
		else
		{
			
			ArrayList<Integer> valoresPrazos = UpdatePrazosTable.calculatePrazos(idTitulo, dataEstoque);				
			double valorCorrido=valoresPrazos.get(0);
			double valorUtil=valoresPrazos.get(1);
			if(valorCorrido!=0)
			{
				System.out.println();
				String sql =  "INSERT INTO prazo (`idTitulo`,`DataEstoque`,`ValorCorrido`,`ValorUtil`)" //$NON-NLS-1$
						+ " VALUES (" //$NON-NLS-1$
						+ idTitulo
						+ "," + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ "," + valorCorrido //$NON-NLS-1$
						+ "," + valorUtil //$NON-NLS-1$
						+ ")"; //$NON-NLS-1$
				Statement stmt = null;
				try
				{
					stmt = (Statement) conn.createStatement();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				try
				{
					stmt.executeUpdate(sql);
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
//				System.out.println(idTitulo + " Corrido: " + valorCorrido + "\tUtil: " + valorUtil);
			}
		}		
	}
	
	public static ArrayList<Integer> calculatePrazos(int idTitulo, Date dataEstoque)
	{
		int valorCorrido=0;
		int valorUtil=0;
		ArrayList<Integer> valores = new ArrayList<Integer>();
		
		String query = "select DataVencimento from Titulo" //$NON-NLS-1$
				+ " where " //$NON-NLS-1$
				+ " idTitulo=" + idTitulo; //$NON-NLS-1$
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		
		try
		{
			while (rs.next())
			{
				Date dataVencimento=rs.getDate("DataVencimento"); //$NON-NLS-1$
//				if(dataVencimento.compareTo(dataEstoque)>0)
//				{
					valorCorrido = (int) WorkingDays.allDays(dataEstoque,dataVencimento);
					valorUtil = (int) WorkingDays.countWorkingDays(dataEstoque,dataVencimento, conn);
//				}
				System.out.println(idTitulo + " " + sdfd.format(dataVencimento) + " " + sdfd.format(dataEstoque) + " " + valorCorrido);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		valores.add(valorCorrido);
		valores.add(valorUtil);
		return valores;
	}
	public static boolean existPrazos(int idTitulo, Date dataEstoque)
	{
		String query = "select idPrazo from Prazo" //$NON-NLS-1$
				+ " where " //$NON-NLS-1$
				+ " idTitulo=" + idTitulo //$NON-NLS-1$
				+ " AND dataEstoque=" + "'" + sdfd.format(dataEstoque) + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		int idPrazo=0;
		try
		{
			while (rs.next())
			{
				idPrazo=rs.getInt("idPrazo"); //$NON-NLS-1$
				if(idPrazo!=0)
				{
					return true;
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
