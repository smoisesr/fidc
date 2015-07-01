package concentracao;

import java.util.Calendar;
import java.util.Date;

import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class ConcentracaoSacado 
{
	private Entidade sacado = new Entidade();
	private FundoDeInvestimento fundo = new FundoDeInvestimento();
	double valorPresente = 0.0;
	double limite = 0.0;
	double concentracao = 0.0;
	double operar = 0.0;
	Date updateTime = Calendar.getInstance().getTime();

	public ConcentracaoSacado() 
	{
		
	}
	
	public ConcentracaoSacado(Entidade sacado, FundoDeInvestimento fundo, double valorPresente, double limite, double concentracao, double operar, Date updateTime)
	{
		this.sacado=sacado;
		this.fundo=fundo;
		this.valorPresente=valorPresente;
		this.limite=limite;
		this.concentracao=concentracao;
		this.operar=operar;
		this.updateTime=updateTime;
	}

	public Entidade getSacado() {
		return sacado;
	}

	public void setSacado(Entidade sacado) {
		this.sacado = sacado;
	}

	public FundoDeInvestimento getFundo() {
		return fundo;
	}

	public void setFundo(FundoDeInvestimento fundo) {
		this.fundo = fundo;
	}

	public double getValorPresente() {
		return valorPresente;
	}

	public void setValorPresente(double valorPresente) {
		this.valorPresente = valorPresente;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	public double getConcentracao() {
		return concentracao;
	}

	public void setConcentracao(double concentracao) {
		this.concentracao = concentracao;
	}

	public double getOperar() {
		return operar;
	}

	public void setOperar(double operar) {
		this.operar = operar;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
