package test;

import java.util.Date;

import com.mysql.jdbc.Connection;

public class UpdaterThread extends Thread
{
	public static Connection conn=null;
	private Date dataEstoque=null;
	public UpdaterThread()
	{

	}
	
	public void run()
	{
		UpdateTaxaSobreCDITable.UpdateDate(this.dataEstoque, UpdaterThread.conn);
	}

	public static Connection getConn()
	{
		return conn;
	}

	public static void setConn(Connection conn)
	{
		UpdaterThread.conn = conn;
	}

	public Date getDataEstoque()
	{
		return dataEstoque;
	}

	public void setDataEstoque(Date dataEstoque)
	{
		this.dataEstoque = dataEstoque;
	}
}
