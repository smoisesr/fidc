package mvcapital.mysql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLAccess 
{
	private static Connection conn = null;
	private static String server = "localhost"; //$NON-NLS-1$
	private static int port=3306;
	private static String userName="root"; //$NON-NLS-1$
	private static String password="root"; //$NON-NLS-1$
	private static String dbName="user"; //$NON-NLS-1$
	/**
	 * 
	 * @param server
	 * @param port
	 * @param username
	 * @param password
	 * @param dbName
	 */
	public MySQLAccess()
	{
		
	}
	public MySQLAccess(String server, int port, String username, String password, String dbName)
	{
		MySQLAccess.dbName = dbName;
		MySQLAccess.userName = username;
		MySQLAccess.server = server;
		MySQLAccess.port = port;
		MySQLAccess.password = password;		
	}	
	@SuppressWarnings("resource")
	public static void readConf()
	{
		BufferedReader reader = null;
		System.out.println("Reading conf/mysql.conf file"); //$NON-NLS-1$
		System.out.println("------------------"); //$NON-NLS-1$
		try 
		{
			reader = new BufferedReader(new FileReader("conf/mysql.conf")); //$NON-NLS-1$
		} catch (FileNotFoundException e1) 
		{

			e1.printStackTrace();
		}
		String line = null;
		try 
		{
			while ((line = reader.readLine()) != null) 
			{
				if(!line.isEmpty())
				{
					
					String[] fields = line.split(","); //$NON-NLS-1$
					if (fields[0].contains("#")) //$NON-NLS-1$
					{
						System.out.println("Comment Line:\t" + line); //$NON-NLS-1$
					}
					else
					{
						System.out.println("Parameters Line:\t" + line); //$NON-NLS-1$
						for (int i = 0; i<fields.length; i++)
						{
							switch (fields[0]) 
							{
					            case "server":   //$NON-NLS-1$
					            	MySQLAccess.server = fields[1];
					                break;
					            case "port": //$NON-NLS-1$
					            	MySQLAccess.port = Integer.parseInt(fields[1].replace(" ", "")); //$NON-NLS-1$ //$NON-NLS-2$
					                break;				            	
					            case "userName": //$NON-NLS-1$
					            	MySQLAccess.userName = fields[1];
					                break;
					            case "password": //$NON-NLS-1$
					            	MySQLAccess.password = fields[1];
					                break;
					            case "dbName": //$NON-NLS-1$
					            	MySQLAccess.dbName = fields[1];
					                break;
					            default: 
					            break;
							}
						}
					}
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("resource")
	public static void readConf(String pathConf)
	{
		BufferedReader reader = null;
		System.out.println("Reading " + pathConf + "mysql.conf file"); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("------------------"); //$NON-NLS-1$
		try 
		{
			reader = new BufferedReader(new FileReader(pathConf + "mysql.conf")); //$NON-NLS-1$
		} catch (FileNotFoundException e1) 
		{

			e1.printStackTrace();
		}
		String line = null;
		try 
		{
			while ((line = reader.readLine()) != null) 
			{
				if(!line.isEmpty())
				{
					
					String[] fields = line.split(","); //$NON-NLS-1$
					if (fields[0].contains("#")) //$NON-NLS-1$
					{
						System.out.println("Comment Line:\t" + line); //$NON-NLS-1$
					}
					else
					{
						System.out.println("Parameters Line:\t" + line); //$NON-NLS-1$
						for (int i = 0; i<fields.length; i++)
						{
							switch (fields[0]) 
							{
					            case "server":   //$NON-NLS-1$
					            	MySQLAccess.server = fields[1];
					                break;
					            case "port": //$NON-NLS-1$
					            	MySQLAccess.port = Integer.parseInt(fields[1].replace(" ", "")); //$NON-NLS-1$ //$NON-NLS-2$
					                break;				            	
					            case "userName": //$NON-NLS-1$
					            	MySQLAccess.userName = fields[1];
					                break;
					            case "password": //$NON-NLS-1$
					            	MySQLAccess.password = fields[1];
					                break;
					            case "dbName": //$NON-NLS-1$
					            	MySQLAccess.dbName = fields[1];
					                break;
					            default: 
					            break;
							}
						}
					}
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void connect()
	{		
		String driver = "com.mysql.jdbc.Driver"; //$NON-NLS-1$
		String url = "jdbc:mysql://" + server +":"+port+"/"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
			System.out.println("server: " + MySQLAccess.server); //$NON-NLS-1$
			System.out.println("port: " + MySQLAccess.port); //$NON-NLS-1$
			System.out.println("userName: " + MySQLAccess.userName); //$NON-NLS-1$
			System.out.println("password: " + MySQLAccess.password); //$NON-NLS-1$
			System.out.println("dbName: " + MySQLAccess.dbName); //$NON-NLS-1$
			MySQLAccess.conn = (Connection) DriverManager.getConnection(url+MySQLAccess.dbName,MySQLAccess.userName,MySQLAccess.password);
			System.out.println("-------------------"); //$NON-NLS-1$
			System.out.println("Connected to the database " + MySQLAccess.dbName); //$NON-NLS-1$
		} catch (SQLException e) {
			System.err.println("-------------------"); //$NON-NLS-1$
			System.err.println("Please chech the connection parameters"); //$NON-NLS-1$
			System.err.println("edit the conf/automata.conf file"); //$NON-NLS-1$
			System.err.println("-------------------"); //$NON-NLS-1$
			e.printStackTrace();
		}
	  }
	  
	  public static void close()
	  {
		  try {
			MySQLAccess.conn.close();
			  System.out.println("Disconnected from database"); //$NON-NLS-1$
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		MySQLAccess.conn = conn;
	}

	public static String getServer() {
		return server;
	}

	public static void setServer(String server) {
		MySQLAccess.server = server;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		MySQLAccess.port = port;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		MySQLAccess.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		MySQLAccess.password = password;
	}

	public static String getDbName() {
		return dbName;
	}

	public static void setDbName(String dbName) {
		MySQLAccess.dbName = dbName;
	}
}