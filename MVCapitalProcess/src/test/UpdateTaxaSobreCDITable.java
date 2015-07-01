package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mvcapital.bancopaulista.estoque.HandlerEstoque;
import mvcapital.indicadores.HandlerIndicadores;
import mvcapital.mysql.MySQLAccess;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UpdateTaxaSobreCDITable
{
	private static Connection conn=null;
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	public UpdateTaxaSobreCDITable()
	{

	}

	public static void main(String[] args)
	{
			MySQLAccess.readConf();
			MySQLAccess mysqlAccess = new MySQLAccess();
			mysqlAccess.connect();
			UpdateTaxaSobreCDITable.conn = (Connection) mysqlAccess.getConn();
			UpdateTaxaSobreCDITable.UpdateAllDates();
	}
	
	public static void UpdateDate(Date dataEstoque, Connection conn)
	{
		double taxaCDIOver = HandlerIndicadores.taxaCDI(dataEstoque, conn);
	
		String query = "select idTitulo from Estoque where" //$NON-NLS-1$
				+ " data = " + "'" + sdfd.format(dataEstoque) + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Statement stmt = null;
		ArrayList<Integer> idTitulos = new ArrayList<Integer>();
		ArrayList<Double> valores = new ArrayList<Double>();
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
		double valor= 0.0;
		try
		{
			while (rs.next())
			{
				int idTitulo = rs.getInt("idTitulo"); //$NON-NLS-1$
				valor = calculateValue(idTitulo, dataEstoque, taxaCDIOver);
				if(idTitulo!=0)
				{
					idTitulos.add(idTitulo);
					valores.add(valor);
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		int sizeTitulos=idTitulos.size();
		for(int i=0;i<idTitulos.size();i++)
		{
			HandlerEstoque.storeTaxaSobreCDI(idTitulos.get(i), dataEstoque, valores.get(i), conn);
			System.out.println(i + " de " + sizeTitulos); //$NON-NLS-1$
		}
		idTitulos=null;
	}
	
	public static double calculateValue(int idTitulo, Date dataEstoque, double taxaCDIOver)
	{
		String query = "select taxaDiaUtil from Titulo where" //$NON-NLS-1$
				+ " idTitulo = " + idTitulo; //$NON-NLS-1$
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
		double taxaDiaUtil = 0.0;
		try
		{
			while (rs.next())
			{
				taxaDiaUtil =rs.getDouble("taxaDiaUtil"); //$NON-NLS-1$
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		double taxaSobreCDI = 0.0;
		if(taxaCDIOver!=0)
		{
			taxaSobreCDI = taxaDiaUtil/taxaCDIOver;
		}
		return taxaSobreCDI;		
	}
	
	public static void UpdateAllDates()
	{
		ArrayList<Date> datasEstoque = new ArrayList<Date>();
//		String query = "select DISTINCT data from estoque where" //$NON-NLS-1$
//				+ " data<'2015-01-26'" //$NON-NLS-1$
//				+ " and  data > '2015-01-01'"; //$NON-NLS-1$
		
		String query = "select DISTINCT data from estoque"; //$NON-NLS-1$
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
				Date dataEstoque = rs.getDate("data"); //$NON-NLS-1$
				System.out.println(dataEstoque);
				datasEstoque.add(dataEstoque);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		UpdaterThread.conn = conn;
		int iData=0;		
		for(iData=0;iData<datasEstoque.size();iData++)
		{
			{
				System.out.println("Processing: " + sdfd.format(datasEstoque.get(iData))); //$NON-NLS-1$
				UpdaterThread ut = new UpdaterThread();
				ut.setDataEstoque(datasEstoque.get(iData));
				ut.start();
//				UpdateDate(datasEstoque.get(iData), conn);
			}
//			if(iData%2 == 0)
//			{
//				try
//				{					
//					System.out.println("Sleeping at " + Calendar.getInstance().getTime()); //$NON-NLS-1$
//					Thread.sleep(3000);
//					System.out.println("After Sleep" + Calendar.getInstance().getTime()); //$NON-NLS-1$
//					System.gc();
//				} catch (InterruptedException e)
//				{
//					e.printStackTrace();
//				}
//			}
		}
	}
	
	public static void taxaMediaAoAno()
	{
		String query = "select idTitulo,valorNominal,valorAquisicao,prazoUtil from Titulo"; //$NON-NLS-1$
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
				double valorNominal = rs.getDouble("valorNominal"); //$NON-NLS-1$
				double valorAquisicao = rs.getDouble("valorAquisicao"); //$NON-NLS-1$
				int prazoUtil = rs.getInt("prazoUtil"); //$NON-NLS-1$
				int idTitulo = rs.getInt("idTitulo");				 //$NON-NLS-1$
//				System.out.println(idTitulo);
				double taxaOperacao = (valorNominal/valorAquisicao - 1);
				double taxaDiaUtil = taxaOperacao / prazoUtil;
				double taxaAoAno = taxaDiaUtil * 252;
				updateTaxas(idTitulo, taxaAoAno, taxaDiaUtil);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void updateTaxas(int idTitulo, double taxaAoAno, double taxaDiaUtil)
	{
		String sql =  "UPDATE `titulo`" //$NON-NLS-1$
				+ " SET " //$NON-NLS-1$
				+ "`taxaAoAno` = " + taxaAoAno //$NON-NLS-1$
				+ ", `taxaDiaUtil` = " + taxaDiaUtil //$NON-NLS-1$
				+ " WHERE `idTitulo` = " + idTitulo;		 //$NON-NLS-1$
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
}
