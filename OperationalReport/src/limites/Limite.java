package limites;

import relatorio.cessao.TipoDeRecebivel;
import fundo.FundoDeInvestimento;

public class Limite 
{
	protected int idLimite=0;
	protected int idTipoDeLimite=0;
	protected FundoDeInvestimento fundo = new FundoDeInvestimento();
	protected String tipoDeLimite=""; //$NON-NLS-1$
	protected TipoDeRecebivel tipoDeRecebivel= new TipoDeRecebivel();
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

	public Limite(int idLimite, int idTipoDeLimite, FundoDeInvestimento fundo, String tipoDeLimite, TipoDeRecebivel tipoDeRecebivel)
	{
		this.idLimite=idLimite;
		this.idTipoDeLimite=idTipoDeLimite;
		this.fundo=fundo;
		this.tipoDeLimite=tipoDeLimite;
		this.tipoDeRecebivel=tipoDeRecebivel;		
	}
	
	
	public int getIdLimite() {
		return this.idLimite;
	}

	public void setIdLimite(int idLimite) {
		this.idLimite = idLimite;
	}

	public int getIdTipoDeLimite() {
		return this.idTipoDeLimite;
	}

	public void setIdTipoDeLimite(int idTipoDeLimite) {
		this.idTipoDeLimite = idTipoDeLimite;
	}

	public FundoDeInvestimento getFundo() {
		return this.fundo;
	}

	public void setFundo(FundoDeInvestimento fundo) {
		this.fundo = fundo;
	}

	public String getTipoDeLimite() {
		return this.tipoDeLimite;
	}

	public void setTipoDeLimite(String tipoDeLimite) {
		this.tipoDeLimite = tipoDeLimite;
	}

	public TipoDeRecebivel getTipoDeRecebivel() {
		return this.tipoDeRecebivel;
	}

	public void setTipoDeRecebivel(TipoDeRecebivel tipoDeRecebivel) {
		this.tipoDeRecebivel = tipoDeRecebivel;
	}

	public boolean isOk() {
		return this.ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

}
