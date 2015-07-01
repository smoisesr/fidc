package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class TipoDeLogin 
{
	private int idTipoDeLogin=0;
	private String tipo="";
	Connection conn = null;
	
	
	public TipoDeLogin()
	{
		
	}
	
	public TipoDeLogin(int idTipoDeLogin, Connection conn)
	{
		this.idTipoDeLogin = idTipoDeLogin;
		this.conn = conn;
		this.setTipo();
	}
	public int getIdTipoDeLogin() {
		return idTipoDeLogin;
	}
	public void setIdTipoDeLogin(int idTipoDeLogin) {
		this.idTipoDeLogin = idTipoDeLogin;
	}
	
	public void setTipo() {
		/**
		 * Finding tipo for TipoDeLogin
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT tipo FROM TipoDeLogin WHERE idTipoDeLogin=" + this.getIdTipoDeLogin());
			while (rs.next())
			{				
				this.tipo = rs.getString("tipo");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
