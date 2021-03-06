package limites;

import relatorio.cessao.TipoDeRecebivel;
import fundo.FundoDeInvestimento;

public class LimiteDePrazo extends Limite 
{
//	idLimiteDePrazo, 
//	idTipoDePrazo, 
//	idFundo, 
//	idTipoDeRecebivel, 
//	valor

	private int valor=0;

	public LimiteDePrazo() 
	{

	}
	
	public LimiteDePrazo(int idLimiteDePrazo, int idTipoDePrazo, FundoDeInvestimento fundo, String tipoDePrazo, TipoDeRecebivel tipoDeRecebivel) 
	{
//		protected int idLimite=0;
//		protected int idTipoDeLimite=0;
//		protected FundoDeInvestimento fundo = new FundoDeInvestimento();
//		protected String tipoDeLimite="";
//		protected TipoDeRecebivel tipoDeRecebivel= new TipoDeRecebivel();
//		protected boolean ok = false;		
		super(idLimiteDePrazo, idTipoDePrazo, fundo, tipoDePrazo, tipoDeRecebivel);
	}
	
	public void show()
	{
		System.out.println("----------");
		System.out.println("tipoDePrazo: " + tipoDeLimite);
		System.out.println("fundo: " + fundo.getNome());
		System.out.println("valor: " + valor);
		System.out.println("tipoDeRecebivel: " + tipoDeRecebivel.getDescricao());
		System.out.println("----------");

	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

}
