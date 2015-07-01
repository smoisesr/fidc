package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvcapital.mysql.MySQLAccess;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UpdateTaxasTituloTable
{
	private static Connection conn=null;

	public UpdateTaxasTituloTable()
	{

	}
	
	public static void main(String[] args)
	{
			MySQLAccess.readConf();
			MySQLAccess mysqlAccess = new MySQLAccess();
			mysqlAccess.connect();
			UpdateTaxasTituloTable.conn = (Connection) mysqlAccess.getConn();
			UpdateTaxasTituloTable.taxaMediaAoAno();
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
				int idTitulo = rs.getInt("idTitulo");				
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
