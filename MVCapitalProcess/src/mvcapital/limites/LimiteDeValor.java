package mvcapital.limites;

import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.relatorio.cessao.TipoTitulo;

public class LimiteDeValor extends Limite 
{
	private double valor=0.0;

	public LimiteDeValor() 
	{
	
	}

	public LimiteDeValor(int idLimiteDeValor, int idTipoDeValor, FundoDeInvestimento fundo, String tipoDeValor, TipoTitulo tipoDeRecebivel) 
	{
//		protected int idLimite=0;
//		protected int idTipoDeLimite=0;
//		protected FundoDeInvestimento fundo = new FundoDeInvestimento();
//		protected String tipoDeLimite="";
//		protected TipoDeRecebivel tipoDeRecebivel= new TipoDeRecebivel();
//		protected boolean ok = false;		
		super(idLimiteDeValor, idTipoDeValor, fundo, tipoDeValor, tipoDeRecebivel);
	}
	
	public void show()
	{
		System.out.println("----------");
		System.out.println("tipoDeValor: " + tipoDeLimite);
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
