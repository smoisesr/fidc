package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLAccess 
{
	private Connection conn = null;
	String server = "localhost";
	int port=3306;
	String userName="root";
	String password="root";
	String dbName="user";
	/**
	 * 
	 * @param server
	 * @param port
	 * @param username
	 * @param password
	 * @param dbName
	 */
	public MySQLAccess(String server, int port, String username, String password, String dbName)
	{
		this.dbName = dbName;
		this.userName = username;
		this.server = server;
		this.port = port;
		this.password = password;		
	}	
	
	public void connect()
	{		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://" + server +":"+port+"/";
		try {
			Class.forName(driver).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try 
		{
			System.out.println("-------------------");
			System.out.println("Connecting with the following parameters");
			System.out.println("server: " + this.server);
			System.out.println("port: " + this.port);
			System.out.println("userName: " + this.userName);
			System.out.println("password: " + this.password);
			System.out.println("dbName: " + this.dbName);
			this.conn = (Connection) DriverManager.getConnection(url+this.dbName,this.userName,this.password);
			System.out.println("-------------------");
			System.out.println("Connected to the database " + this.dbName);
		} catch (SQLException e) {
			System.err.println("-------------------");
			System.err.println("Please chech the connection parameters");
			System.err.println("edit the conf/automata.conf file");
			System.err.println("-------------------");
			e.printStackTrace();
		}
	  }
	  
	  public void close()
	  {
		  try {
			this.conn.close();
			  System.out.println("Disconnected from database");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}