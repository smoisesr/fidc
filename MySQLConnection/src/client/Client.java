package client;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.MySQLConnection;

public class Client
{

	public Connection conn=null;
	public String server="";
	public int port=0;
	public String user="";
	public String password="";
	public String dbName="";
	public MySQLAccess mysql=null;
	
	
	public Client()
	{
//		server,192.168.2.160
//		port,3306
//		userName,robot
//		password,autoatmvc123
//		dbName,MVCapital
//		rootLocalLinux,/home/fundos/
//		rootLocalWindows,W:\\		

		this.server = "192.168.2.160";
		this.port = 3306;
		this.user = "robot";
		this.password = "autoatmvc123";
		this.dbName = "MVCapital";
		
		this.mysql = new MySQLAccess(this.server, this.port, this.user, this.password, this.dbName);
		this.mysql.connect();
	}
	
	public static void main(String[] args)
	{
		Client someClient = new Client();
	}

}
