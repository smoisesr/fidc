package mvcapital.limites;

import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.relatorio.cessao.TipoTitulo;

public class Limite 
{
	protected int idLimite=0;
	protected int idTipoDeLimite=0;
	protected FundoDeInvestimento fundo = new FundoDeInvestimento();
	protected String tipoDeLimite="";
	protected TipoTitulo tipoTitulo= new TipoTitulo();
	protected boolean ok = false;
	
	public Limite() 
	{
	
	}

	public Limite(int idLimite, int idTipoDeLimite, FundoDeInvestimento fundo, String tipoDeLimite)
	{
		this.idLimite=idLimite;
		this.idTipoDeLimite=idTipoDeLimite;
		this.fundo=fundo;
		this.tipoDeLimite=tipoDeLimite;
	}

	public Limite(int idLimite, int idTipoDeLimite, FundoDeInvestimento fundo, String tipoDeLimite, TipoTitulo tipoDeRecebivel)
	{
		this.idLimite=idLimite;
		this.idTipoDeLimite=idTipoDeLimite;
		this.fundo=fundo;
		this.tipoDeLimite=tipoDeLimite;
		this.tipoTitulo=tipoDeRecebivel;		
	}
	
	
	public int getIdLimite() {
		return idLimite;
	}

	public void setIdLimite(int idLimite) {
		this.idLimite = idLimite;
	}

	public int getIdTipoDeLimite() {
		return idTipoDeLimite;
	}

	public void setIdTipoDeLimite(int idTipoDeLimite) {
		this.idTipoDeLimite = idTipoDeLimite;
	}

	public FundoDeInvestimento getFundo() {
		return fundo;
	}

	public void setFundo(FundoDeInvestimento fundo) {
		this.fundo = fundo;
	}

	public String getTipoDeLimite() {
		return tipoDeLimite;
	}

	public void setTipoDeLimite(String tipoDeLimite) {
		this.tipoDeLimite = tipoDeLimite;
	}

	public TipoTitulo getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(TipoTitulo tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

}
