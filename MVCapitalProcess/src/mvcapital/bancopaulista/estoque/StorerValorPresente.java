package mvcapital.bancopaulista.estoque;

import java.util.Calendar;
import java.util.Date;

import mvcapital.relatorio.cessao.Titulo;

import com.mysql.jdbc.Connection;

public class StorerValorPresente extends Thread
{
	private Titulo titulo = new Titulo();
	private Date dataReferencia = Calendar.getInstance().getTime();
	private Connection conn = null;
	private double valorPresente = 0.0;

	public StorerValorPresente()
	{
	}
	
	public void run()
	{
		HandlerEstoque.storeValorPresente(titulo, dataReferencia, valorPresente, conn);
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

	public double getValorPresente()
	{
		return valorPresente;
	}

	public void setValorPresente(double valorPresente)
	{
		this.valorPresente = valorPresente;
	}
}
