package mvcapital.bancopaulista.liquidadosebaixados;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class TipoOcorrencia
{
	private int idTipoOcorrencia=0;
	private String descricao=""; //$NON-NLS-1$
	
	public TipoOcorrencia()
	{
		
	}
	public TipoOcorrencia(int idTipoOcorrencia, Connection conn)
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		if(this.descricao.equals("liquidacao normal") || this.descricao.equals("baixa")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			this.descricao="baixa normal"; //$NON-NLS-1$
		}
		String query = "SELECT idTipoOcorrencia, Descricao FROM tipoOcorrencia WHERE idTipoOcorrencia=" + idTipoOcorrencia; //$NON-NLS-1$
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
				this.idTipoOcorrencia = rs.getInt("idTipoOcorrencia");				 //$NON-NLS-1$
				this.descricao = rs.getString("Descricao");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public TipoOcorrencia(int idOcorrenciaBradesco, String nomeBanco, Connection conn)
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		if(nomeBanco.toLowerCase().contains("bradesco")) //$NON-NLS-1$
		{
			if(idOcorrenciaBradesco==0)
			{
				String queryOcorrencia = "SELECT idTipoOcorrencia, descricao FROM tipoOcorrencia WHERE descricao=" + "'" + "Cheque Depositado" + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				System.out.println(queryOcorrencia);
				ResultSet rs2 = null;
				try {
					rs2 = stmt.executeQuery(queryOcorrencia);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					while (rs2.next())
					{
						this.idTipoOcorrencia = rs2.getInt("idTipoOcorrencia"); //$NON-NLS-1$
						this.descricao = rs2.getString("descricao");	 //$NON-NLS-1$
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			else
			{
				String queryBradesco = "SELECT idTipoOcorrencia FROM codigoBradescoOcorrenciaCheque WHERE codigo=" + idOcorrenciaBradesco; //$NON-NLS-1$
				System.out.println(queryBradesco);
				ResultSet rs = null;
				try {
					rs = stmt.executeQuery(queryBradesco);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					while (rs.next())
					{
						this.idTipoOcorrencia = rs.getInt("idTipoOcorrencia");				 //$NON-NLS-1$
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				Statement stmt2 = null;
				try {
					stmt2 = (Statement) conn.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}		
				
				String queryOcorrencia = "SELECT descricao FROM tipoOcorrencia WHERE idTipoOcorrencia=" + this.idTipoOcorrencia; //$NON-NLS-1$
				System.out.println(queryOcorrencia);
				ResultSet rs2 = null;
				try {
					rs2 = stmt2.executeQuery(queryOcorrencia);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					while (rs2.next())
					{
						this.descricao = rs2.getString("descricao");	 //$NON-NLS-1$
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("TipoOcorrencia: " + this.getIdTipoOcorrencia() + " " + this.getDescricao());
	}
	
	public TipoOcorrencia(String descricao, Connection conn)
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		if(descricao.equals("liquidacao normal") || descricao.equals("baixa")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			descricao="baixa normal"; //$NON-NLS-1$
		}
		String query = "SELECT idTipoOcorrencia FROM tipoOcorrencia WHERE descricao=\"" + descricao + "\""; //$NON-NLS-1$ //$NON-NLS-2$
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
				this.idTipoOcorrencia = rs.getInt("idTipoOcorrencia");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		this.descricao = descricao;
		
		if(this.idTipoOcorrencia==0)
		{
			System.out.println("New TipoOcorrencia " + this.descricao); //$NON-NLS-1$
			String sql = "INSERT INTO `tipoOcorrencia` (`descricao`) " //$NON-NLS-1$
						+ "VALUES (" //$NON-NLS-1$
						+ "'" + this.descricao + "'" //$NON-NLS-1$ //$NON-NLS-2$
						+ ")";  //$NON-NLS-1$
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			rs = null;
			try {
				rs = stmt.executeQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				while (rs.next())
				{
					this.idTipoOcorrencia = rs.getInt("idTipoOcorrencia");				 //$NON-NLS-1$
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}					
		}		
//		System.out.println("tipoOcorrencia: " + this.descricao); //$NON-NLS-1$
	}
	public int getIdTipoOcorrencia()
	{
		return this.idTipoOcorrencia;
	}
	public void setIdTipoOcorrencia(int idTipoOcorrencia)
	{
		this.idTipoOcorrencia = idTipoOcorrencia;
	}
	public String getDescricao()
	{
		return this.descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
}
