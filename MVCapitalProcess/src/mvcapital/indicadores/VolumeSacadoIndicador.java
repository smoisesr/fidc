package mvcapital.indicadores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class VolumeSacadoIndicador 
{
//	idConcentracaoSacado, 
//	idFundo 
//	idEntidadeSacado 
//	DataEstoque 
//	valorFinanceiro 
//	valorPercentual 
//	valorDisponivelFinanceiro 
//	DataAtualizacao

	private int idConcentracaoSacado=0;
	private FundoDeInvestimento fundo = new FundoDeInvestimento();
	private Entidade sacado = new Entidade();
	private Date dataEstoque = Calendar.getInstance().getTime();			
	private double valorFinanceiro = 0.0;
	private double valorPercentual = 0.0;
	private double valorDisponivelFinanceiro = 0.0;
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$

	public VolumeSacadoIndicador() 
	{
		
	}
	
	public VolumeSacadoIndicador(Entidade sacado, FundoDeInvestimento fundo, double valorFinanceiro, double valorPercentual, double valorDisponivelFinanceiro)
	{
		this.sacado=sacado;
		this.fundo=fundo;
		this.valorFinanceiro=valorFinanceiro;
		this.valorPercentual=valorPercentual;
		this.valorDisponivelFinanceiro=valorDisponivelFinanceiro;
	}
	
	public VolumeSacadoIndicador(Entidade sacado, FundoDeInvestimento fundo, Date dataEstoque, Connection conn)
	{	
		this.sacado = sacado;
		this.fundo = fundo;
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "Select * from ConcentracaoSacado where " //$NON-NLS-1$
				+ " idEntidadeSacado = " + sacado.getIdEntidade()  //$NON-NLS-1$
				+ " and idFundo = " + fundo.getIdFundo() //$NON-NLS-1$
				+ " and DataEstoque = " + "'" + sdfd.format(dataEstoque)  + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				;
		try 
		{
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				this.idConcentracaoSacado = rs.getInt("idConcentracaoSacado"); //$NON-NLS-1$
				this.valorFinanceiro = rs.getDouble("valorPresente"); //$NON-NLS-1$
				this.valorPercentual = rs.getDouble("concentracado"); //$NON-NLS-1$
				this.valorDisponivelFinanceiro = rs.getDouble("operar"); //$NON-NLS-1$
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	public int getIdConcentracaoSacado()
	{
		return this.idConcentracaoSacado;
	}

	public void setIdConcentracaoSacado(int idConcentracaoSacado)
	{
		this.idConcentracaoSacado = idConcentracaoSacado;
	}

	public FundoDeInvestimento getFundo()
	{
		return this.fundo;
	}

	public void setFundo(FundoDeInvestimento fundo)
	{
		this.fundo = fundo;
	}

	public Entidade getSacado()
	{
		return this.sacado;
	}

	public void setSacado(Entidade sacado)
	{
		this.sacado = sacado;
	}

	public Date getDataEstoque()
	{
		return this.dataEstoque;
	}

	public void setDataEstoque(Date dataEstoque)
	{
		this.dataEstoque = dataEstoque;
	}

	public double getValorFinanceiro()
	{
		return this.valorFinanceiro;
	}

	public void setValorFinanceiro(double valorFinanceiro)
	{
		this.valorFinanceiro = valorFinanceiro;
	}

	public double getValorPercentual()
	{
		return this.valorPercentual;
	}

	public void setValorPercentual(double valorPercentual)
	{
		this.valorPercentual = valorPercentual;
	}

	public double getValorDisponivelFinanceiro()
	{
		return this.valorDisponivelFinanceiro;
	}

	public void setValorDisponivelFinanceiro(double valorDisponivelFinanceiro)
	{
		this.valorDisponivelFinanceiro = valorDisponivelFinanceiro;
	}
			
}
