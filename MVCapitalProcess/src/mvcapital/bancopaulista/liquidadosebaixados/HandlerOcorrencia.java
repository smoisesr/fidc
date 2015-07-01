package mvcapital.bancopaulista.liquidadosebaixados;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class HandlerOcorrencia
{
	private static Connection conn=null;
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	
	public HandlerOcorrencia()
	{
		
	}
	
	public static void store(Ocorrencia ocorrencia, Connection conn)
	{
//		private int idOcorrencia = 0;
//		private Titulo titulo = new Titulo();	
//		private Date data = Calendar.getInstance().getTime();
//		private Entidade cedente = new Entidade();
//		private double valor = 0.0;
//		private int flagCNAB = 0;
//		private int idPraca=0;

		Statement stmt=null;
		int idOcorrencia=0;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// idOcorrencia, 
		// idTitulo, 
		// idTipoOcorrencia, 
		// Data, 
		// Valor, 
		// FlagCNAB, 
		// idPraca, 
		// DataAtualizacao
		
		String query = "SELECT idOcorrencia from ocorrencia " //$NON-NLS-1$
	 				+ " WHERE " //$NON-NLS-1$
	 				+ " idTitulo=" + ocorrencia.getTitulo().getIdTitulo() //$NON-NLS-1$
	 				+ " AND " + " idTipoOcorrencia=" + ocorrencia.getTipoOcorrencia().getIdTipoOcorrencia() //$NON-NLS-1$ //$NON-NLS-2$
//	 				+ " AND " + " valor=" +  ocorrencia.getValor()
	 				;
		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idOcorrencia = rs.getInt("idOcorrencia"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		if (idOcorrencia!=0)
		{
			System.out.println("Ocurrence already exist!"); //$NON-NLS-1$
		}
		else
		{
			String sql=""; //$NON-NLS-1$
			if(ocorrencia.getPraca().getIdPraca()==0)
			{
				sql="INSERT INTO `ocorrencia` (`idTitulo`" //$NON-NLS-1$
													+ ", `idTipoOcorrencia`" //$NON-NLS-1$
													+ ", `Data`" //$NON-NLS-1$
													+ ", `Valor`" //$NON-NLS-1$
													+ ") VALUES (" //$NON-NLS-1$
													+ ocorrencia.getTitulo().getIdTitulo()
													+ "," + ocorrencia.getTipoOcorrencia().getIdTipoOcorrencia() //$NON-NLS-1$
													+ "," + "'" + sdfd.format(ocorrencia.getData()) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
													+ "," + ocorrencia.getValor() //$NON-NLS-1$
													+ ")"; //$NON-NLS-1$
			}
			else
			{
				sql="INSERT INTO `ocorrencia` (`idTitulo`" //$NON-NLS-1$
						+ ", `idTipoOcorrencia`" //$NON-NLS-1$
						+ ", `Data`" //$NON-NLS-1$
						+ ", `Valor`" //$NON-NLS-1$
						+ ", `idPraca`" //$NON-NLS-1$
						+ ") VALUES (" //$NON-NLS-1$
						+ ocorrencia.getTitulo().getIdTitulo()
						+ "," + ocorrencia.getTipoOcorrencia().getIdTipoOcorrencia() //$NON-NLS-1$
						+ "," + "'" + sdfd.format(ocorrencia.getData()) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ "," + ocorrencia.getValor() //$NON-NLS-1$
						+ "," + ocorrencia.getPraca().getIdPraca() //$NON-NLS-1$
						+ ")"; //$NON-NLS-1$
			}
			
			System.out.println(sql);
			try
			{
				stmt.executeUpdate(sql);
				System.out.println("Ocurrence stored at database!"); //$NON-NLS-1$
			} catch (SQLException e)
			{				
				e.printStackTrace();
				System.out.println("Houston, we have a problem!"); //$NON-NLS-1$
			}
		}

	}
	
	public static Connection getConn()
	{
		return conn;
	}
	
	public static void setConn(Connection conn)
	{
		HandlerOcorrencia.conn = conn;
	}

}
