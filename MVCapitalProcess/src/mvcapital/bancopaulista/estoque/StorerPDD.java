package mvcapital.bancopaulista.estoque;

import java.util.Calendar;
import java.util.Date;

import mvcapital.relatorio.cessao.Titulo;

import com.mysql.jdbc.Connection;

public class StorerPDD extends Thread
{
	private Titulo titulo = new Titulo();
	private Date dataReferencia = Calendar.getInstance().getTime();
	private Connection conn = null;
	private double valorPDD = 0.0;
	private String faixaPDD = "";

	public StorerPDD()
	{
	}
	
	public void run()
	{
		HandlerEstoque.storePDD(titulo, dataReferencia, valorPDD, faixaPDD, conn);
	}

	public Titulo getTitulo()
	{
		return this.titulo;
	}

	public void setTitulo(Titulo titulo)
	{
		this.titulo = titulo;
	}

	public Date getDataReferencia()
	{
		return this.dataReferencia;
	}

	public void setDataReferencia(Date dataReferencia)
	{
		this.dataReferencia = dataReferencia;
	}

	public Connection getConn()
	{
		return this.conn;
	}

	public void setConn(Connection conn)
	{
		this.conn = conn;
	}

	public double getValorPDD()
	{
		return valorPDD;
	}

	public void setValorPDD(double valorPDD)
	{
		this.valorPDD = valorPDD;
	}

	public String getFaixaPDD()
	{
		return faixaPDD;
	}

	public void setFaixaPDD(String faixaPDD)
	{
		this.faixaPDD = faixaPDD;
	}

}
