package mvcapital.indicadores;

import java.util.Date;

import com.mysql.jdbc.Connection;

public class HandlerCalculator extends Thread
{
	private static Connection conn=null;
	private Date dataEstoque=null;
	private int idFundo=0;
	private boolean done=false;
	
	public HandlerCalculator()
	{
		// TODO Auto-generated constructor stub
	}

	public void run()
	{
		HandlerIndicadores.calculateFund(this.dataEstoque, this.idFundo, conn);
		this.done=true;
	}

	public static Connection getConn()
	{
		return conn;
	}

	public static void setConn(Connection conn)
	{
		HandlerCalculator.conn = conn;
	}

	public Date getDataEstoque()
	{
		return this.dataEstoque;
	}

	public void setDataEstoque(Date dataEstoque)
	{
		this.dataEstoque = dataEstoque;
	}

	public int getIdFundo()
	{
		return this.idFundo;
	}

	public void setIdFundo(int idFundo)
	{
		this.idFundo = idFundo;
	}

	public boolean isDone()
	{
		return done;
	}

	public void setDone(boolean done)
	{
		this.done = done;
	}
}
