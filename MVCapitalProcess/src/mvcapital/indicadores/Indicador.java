package mvcapital.indicadores;

import java.util.Calendar;
import java.util.Date;

import mvcapital.fundo.FundoDeInvestimento;

import com.mysql.jdbc.Connection;

public class Indicador
{
	private int idIndicador=0;
	protected TipoIndicador tipoIndicador=new TipoIndicador();
	private FundoDeInvestimento fundo=new FundoDeInvestimento();
	private Date dataEstoque = Calendar.getInstance().getTime();
	private static Connection conn = null;
	
	public Indicador()
	{

	}

	public Indicador(FundoDeInvestimento fundo, Date dataEstoque)	
	{
		this.fundo=fundo;
		this.dataEstoque=dataEstoque;
	}
	
	public void calculate()
	{
		/**
		 * This block is empty to be a template method or abstract method for all the indicador's child classes.
		 */		
	}

	public void store()
	{
		/**
		 * This block is empty to be a template method or abstract method for all the indicador's child classes.
		 */		
	}	
	public int getIdIndicador()
	{
		return this.idIndicador;
	}

	public void setIdIndicador(int idIndicador)
	{
		this.idIndicador = idIndicador;
	}

	public TipoIndicador getTipoIndicador()
	{
		return this.tipoIndicador;
	}

	public void setTipoIndicador(TipoIndicador tipoIndicador)
	{
		this.tipoIndicador = tipoIndicador;
	}

	public FundoDeInvestimento getFundo()
	{
		return this.fundo;
	}

	public void setFundo(FundoDeInvestimento fundo)
	{
		this.fundo = fundo;
	}

	public Date getDataEstoque()
	{
		return this.dataEstoque;
	}

	public void setDataEstoque(Date dataEstoque)
	{
		this.dataEstoque = dataEstoque;
	}

	public static Connection getConn()
	{
		return conn;
	}

	public static void setConn(Connection conn)
	{
		Indicador.conn = conn;
	}

}
