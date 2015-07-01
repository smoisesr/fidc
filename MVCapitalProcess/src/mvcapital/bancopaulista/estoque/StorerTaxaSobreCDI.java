package mvcapital.bancopaulista.estoque;

import java.util.Calendar;
import java.util.Date;

import mvcapital.relatorio.cessao.Titulo;

import com.mysql.jdbc.Connection;

public class StorerTaxaSobreCDI extends Thread
{
	private Titulo titulo = new Titulo();
	private Date dataReferencia = Calendar.getInstance().getTime();
	private Connection conn = null;

	public StorerTaxaSobreCDI()
	{
	}
	
	public void run()
	{
		HandlerEstoque.storeTaxaSobreCDI(titulo, dataReferencia, conn);
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

}
