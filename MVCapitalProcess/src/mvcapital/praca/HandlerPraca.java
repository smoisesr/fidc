package mvcapital.praca;

import java.sql.ResultSet;
import java.sql.SQLException;

import mvcapital.entidade.Entidade;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class HandlerPraca
{

	public HandlerPraca()
	{

	}
	
	public static Entidade entidadeBanco(String codigoBanco, Connection conn)
	{
		Entidade entidadeBanco = new Entidade();
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(codigoBanco.length()<3)
		{
			String s = "0"; //$NON-NLS-1$
			int n = 3-codigoBanco.length();
			String prefix = String.format("%0" + n + "d", 0).replace("0",s); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			codigoBanco=prefix + codigoBanco;
		}
		
		String query = "SELECT idEntidadeBanco FROM codigobanco WHERE codigo= " + "'" + codigoBanco + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
//		System.out.println(query);
		int idEntidadeBanco = 0;
		ResultSet rs=null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				idEntidadeBanco = rs.getInt("idEntidadeBanco"); //$NON-NLS-1$
				if(idEntidadeBanco!=0)
				{
					entidadeBanco = new Entidade(idEntidadeBanco, conn);
				}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return entidadeBanco;
	}
	
	public static void setupIdPraca(Praca praca, Connection conn)
	{
		Entidade entidadeBanco = new Entidade();
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT idPraca FROM praca " //$NON-NLS-1$
				+ " WHERE " //$NON-NLS-1$
				+ " idEntidadeBanco= " + praca.getEntidadeBanco().getIdEntidade() //$NON-NLS-1$
				+ " AND agencia=" + "'" + praca.getAgencia() + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		int idPraca = 0;
		ResultSet rs=null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				idPraca = rs.getInt("idPraca"); //$NON-NLS-1$
				if(idPraca!=0)
				{
					praca.setIdPraca(idPraca);
				}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
	}	
	public static void store(Praca praca, Connection conn)
	{
//		idPraca, 
//		idEntidadeBanco, 
//		Agencia, 
//		idCEP, 
//		DataAtualizacao
		
		Statement stmt=null;
		int idPraca=0;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		String query = "SELECT idPraca from Praca " //$NON-NLS-1$
	 				+ " WHERE " //$NON-NLS-1$
	 				+ " idEntidadeBanco=" + praca.getEntidadeBanco().getIdEntidade() //$NON-NLS-1$
	 				+ " AND " + " agencia=" + "'" + praca.getAgencia() + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
//		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idPraca = rs.getInt("idPraca"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		if (idPraca!=0)
		{
			System.out.println("Praca already exist!"); //$NON-NLS-1$
		}
		else
		{
			String sql="INSERT INTO `praca` (`idEntidadeBanco`" //$NON-NLS-1$
													+ ", `agencia`" //$NON-NLS-1$
													+ ") VALUES (" //$NON-NLS-1$
													+ praca.getEntidadeBanco().getIdEntidade()
													+ ", " + "'" + praca.getAgencia() + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
													+ ")"; //$NON-NLS-1$
			
			System.out.println(sql);
			try
			{
				stmt.executeUpdate(sql);
				System.out.println("Praca stored at database!"); //$NON-NLS-1$
			} catch (SQLException e)
			{				
				e.printStackTrace();
				System.out.println("Houston, we have a problem!"); //$NON-NLS-1$
			}
		}
	}

}
