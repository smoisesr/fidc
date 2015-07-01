package relatorio.cessao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class TipoDeRecebivel 
{
	private int idTipoDeRecebivel=0;
	private String descricao="";
	
	public TipoDeRecebivel() 
	{
	
	}

	public TipoDeRecebivel(int idTipoDeRecebivel, Connection conn) 
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}		

		String query = "SELECT idTipoDeRecebivel,descricao FROM tipoderecebivel WHERE idTipoDeRecebivel="+idTipoDeRecebivel;
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
				this.idTipoDeRecebivel = rs.getInt("idTipoDeRecebivel");				
				this.descricao=rs.getString("descricao");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
	}	
	
	public TipoDeRecebivel(String tipoDeRecebivel, Connection conn) 
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}		

		String query = "SELECT idTipoDeRecebivel FROM tipoderecebivel WHERE descricao=\"" + tipoDeRecebivel + "\"";
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
				this.idTipoDeRecebivel = rs.getInt("idTipoDeRecebivel");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		this.descricao = tipoDeRecebivel;
		
		if(this.idTipoDeRecebivel==0)
		{
			System.out.println("New TipoDeRecebivel " + this.descricao);
			String sql = "INSERT INTO `mvcapital`.`tipoderecebivel` (`descricao`) "
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
					this.idTipoDeRecebivel = rs.getInt("idTipoDeRecebivel");				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}					
		}
	}

	public int getIdTipoDeRecebivel() {
		return idTipoDeRecebivel;
	}

	public void setIdTipoDeRecebivel(int idTipoDeRecebivel) {
		this.idTipoDeRecebivel = idTipoDeRecebivel;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
