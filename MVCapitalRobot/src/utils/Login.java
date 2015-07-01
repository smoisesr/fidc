package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Login 
{
	/*
	idTipoDeLogin, 
	idEntidadeCliente, 
	idEntidadeServidor, 
	*/
	private int idLogin;
	private int idTipoDeLogin;
	private int idEntidadeCliente;
	private int idEntidadeServidor;
	protected String username;
	protected String password;
	protected int port;
	protected String server;
	private TipoDeLogin tipoDeLogin = null;
	Connection conn = null;
	
	public Login(int idLogin, Connection conn)
	{
		this.idLogin = idLogin;
		this.conn = conn;
		/**
		 * Finding idTipoDeLogin for Login
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idTipoDeLogin FROM Login WHERE idLogin=" + this.getIdLogin());
			while (rs.next())
			{				
				this.idTipoDeLogin = rs.getInt("idTipoDeLogin");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.tipoDeLogin=new TipoDeLogin(this.idTipoDeLogin, this.conn);
		System.out.println("Login " + this.tipoDeLogin.getTipo());
		this.setMembers();
		//this.showLogin();
	}
	
	public void setMembers(){
		this.setUsername();
		this.setPassword();
		this.setServer();
		this.setPort();
		this.setIdEntidadeCliente();
		this.setIdEntidadeServidor();
	}
	

	public void showLogin() {
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("server: " + server);
		System.out.println("port: " + port);
	}
	
	public int getIdLogin() {
		return idLogin;
	}

	public void setIdLogin(int idLogin) {
		this.idLogin = idLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername() {
		/**
		 * Finding username for Login
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT username FROM Login WHERE idLogin=" + this.getIdLogin());
			while (rs.next())
			{				
				this.username = rs.getString("username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword() {
		/**
		 * Finding password for Login
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT password FROM Login WHERE idLogin=" + this.getIdLogin());
			while (rs.next())
			{				
				this.password = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort() {
		/**
		 * Finding port for Login
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT port FROM Login WHERE idLogin=" + this.getIdLogin());
			while (rs.next())
			{				
				this.port = rs.getInt("port");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	public String getServer() {
		return server;
	}

	public void setServer() {
		/**
		 * Finding server for Login
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT server FROM Login WHERE idLogin=" + this.getIdLogin());
			while (rs.next())
			{				
				this.server = rs.getString("server");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getIdTipoDeLogin() {
		return idTipoDeLogin;
	}

	public void setIdTipoDeLogin(int idTipoDeLogin) {
		this.idTipoDeLogin = idTipoDeLogin;
	}

	public int getIdEntidadeCliente() {
		return idEntidadeCliente;
	}

	public void setIdEntidadeCliente() {
		/**
		 * Finding idEntidadeCliente for Login
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidadeCliente FROM Login WHERE idLogin=" + this.getIdLogin());
			while (rs.next())
			{				
				this.idEntidadeCliente = rs.getInt("idEntidadeCliente");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getIdEntidadeServidor() {
		return idEntidadeServidor;
	}

	public void setIdEntidadeServidor() {
		/**
		 * Finding idEntidadeServidor for Login
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidadeServidor FROM Login WHERE idLogin=" + this.getIdLogin());
			while (rs.next())
			{				
				this.idEntidadeServidor = rs.getInt("idEntidadeServidor");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public TipoDeLogin getTipoDeLogin() {
		return this.tipoDeLogin;
	}

	public void setTipoDeLogin(TipoDeLogin tipoDeLogin) {
		this.tipoDeLogin = tipoDeLogin;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setServer(String server) {
		this.server = server;
	}
}
