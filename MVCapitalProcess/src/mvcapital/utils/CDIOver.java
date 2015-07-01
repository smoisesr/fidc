package mvcapital.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class CDIOver
{

	public CDIOver()
	{
		// TODO Auto-generated constructor stub
	}
	
	public static double calculateValue(int idTitulo, Date dataEstoque, double taxaCDIOver, Connection conn)
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
		
		if(taxaDiaUtil==0.0)
		{
			
		}
		double taxaSobreCDI = 0.0;
		if(taxaCDIOver!=0)
		{
			System.out.println("idTitulo:" + idTitulo + " taxaDiaUtil: " + taxaDiaUtil + " taxaCDIOver: " + taxaCDIOver);
			taxaSobreCDI = taxaDiaUtil/taxaCDIOver;
		}
		return taxaSobreCDI;		
	}

}
