package mvcapital.limites;

import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.relatorio.cessao.TipoTitulo;

public class LimiteDeTaxa extends Limite
{
	private double valor=0.0;
	
	public LimiteDeTaxa()
	{
		
	}
	
	public LimiteDeTaxa(int idLimiteDeTaxa, int idTipoDeTaxa, FundoDeInvestimento fundo, String tipoDeTaxa, TipoTitulo tipoDeRecebivel) 
	{
//		protected int idLimite=0;
//		protected int idTipoDeLimite=0;
//		protected FundoDeInvestimento fundo = new FundoDeInvestimento();
//		protected String tipoDeLimite="";
//		protected TipoDeRecebivel tipoDeRecebivel= new TipoDeRecebivel();
//		protected boolean ok = false;		
		super(idLimiteDeTaxa, idTipoDeTaxa, fundo, tipoDeTaxa, tipoDeRecebivel);
	}

	public void show()
	{
		System.out.println("----------");
		System.out.println("tipoDeTaxa: " + tipoDeLimite);
		System.out.println("fundo: " + fundo.getNome());
		System.out.println("valor: " + valor);
		System.out.println("tipoDeRecebivel: " + tipoTitulo.getDescricao());
		System.out.println("----------");

	}	
	
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
