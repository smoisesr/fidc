package mvcapital.bradesco.cnab.cheque;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Trailler extends Register
{
	private Campo branco=new Campo();
	
	public Trailler() 
	{
		super();
//		private int numero=0;
//		private int posicaoInicial=0;
//		private int posicaoFinal=0;
//		private int tamanho=0;
//		private boolean obrigatorio=true;
//		private TipoCampo tipo=new TipoCampo();
//		private int decimais=0;
//		private String conteudo="";		
		this.identificacaoRegistro.setConteudo("9");
		this.branco=new Campo(2,2,394,393,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro=new Campo(3,395,400,6,true,Register.campoNumerico,0,"");		
	}

	public Campo getBranco() {
		return branco;
	}

	public void setBranco(Campo branco) {
		this.branco = branco;
	}

}
