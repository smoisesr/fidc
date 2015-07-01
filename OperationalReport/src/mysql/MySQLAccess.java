package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLAccess 
{
	private Connection conn = null;
	String server = "localhost"; //$NON-NLS-1$
	int port=3306;
	String userName="root"; //$NON-NLS-1$
	String password="root"; //$NON-NLS-1$
	String dbName="user"; //$NON-NLS-1$
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
		String driver = "com.mysql.jdbc.Driver"; //$NON-NLS-1$
		String url = "jdbc:mysql://" + this.server +":"+this.port+"/"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
			System.out.println("-------------------"); //$NON-NLS-1$
			System.out.println("Connecting with the following parameters"); //$NON-NLS-1$
			System.out.println("server: " + this.server); //$NON-NLS-1$
			System.out.println("port: " + this.port); //$NON-NLS-1$
			System.out.println("userName: " + this.userName); //$NON-NLS-1$
			System.out.println("password: " + this.password); //$NON-NLS-1$
			System.out.println("dbName: " + this.dbName); //$NON-NLS-1$
			this.conn = (Connection) DriverManager.getConnection(url+this.dbName,this.userName,this.password);
			System.out.println("-------------------"); //$NON-NLS-1$
			System.out.println("Connected to the database " + this.dbName); //$NON-NLS-1$
		} catch (SQLException e) {
			System.err.println("-------------------"); //$NON-NLS-1$
			System.err.println("Please chech the connection parameters"); //$NON-NLS-1$
			System.err.println("edit the conf/automata.conf file"); //$NON-NLS-1$
			System.err.println("-------------------"); //$NON-NLS-1$
			e.printStackTrace();
		}
	  }
	  
	  public void close()
	  {
		  try {
			this.conn.close();
			  System.out.println("Disconnected from database"); //$NON-NLS-1$
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public Connection getConn() {
		return this.conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return this.dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}