package mvcapital.relatorio.cessao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class TipoTitulo 
{
	private int idTipoTitulo=0;
	private String descricao="";
	
	public TipoTitulo() 
	{
	
	}

	public TipoTitulo(int idTipoTitulo, Connection conn) 
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}		

		String query = "SELECT idTipoTitulo,descricao FROM tipoTitulo WHERE idTipoTitulo="+idTipoTitulo;
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
				this.idTipoTitulo = rs.getInt("idTipoTitulo");				
				this.descricao=rs.getString("descricao");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
	}	
	
	public TipoTitulo(String tipoTituloDescricao, Connection conn) 
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}		

		String query = "SELECT idTipoTitulo FROM tipoTitulo WHERE descricao=\"" + tipoTituloDescricao + "\"";
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
				this.idTipoTitulo = rs.getInt("idTipoTitulo");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		this.descricao = tipoTituloDescricao;
		
		if(this.idTipoTitulo==0)
		{
			System.out.println("New TipoTitulo " + this.descricao);
			String sql = "INSERT INTO `tipoTitulo` (`descricao`) "
						+ "VALUES ("
						+ this.descricao
						+ ")"; 
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
					this.idTipoTitulo = rs.getInt("idTipoTitulo");				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}					
		}
	}

	public int getIdTipoTitulo() {
		return idTipoTitulo;
	}

	public void setIdTipoTitulo(int idTipoTitulo) {
		this.idTipoTitulo = idTipoTitulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
