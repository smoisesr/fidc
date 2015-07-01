package mvcapital.bradesco.cnab.cheque.protocolo.host;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Trailler extends Register
{
	private Campo fixo = new Campo();
	private Campo quantidadeCheques = new Campo();
	private Campo filler = new Campo();
	private Campo valorArquivo = new Campo();
	private Campo filler2 = new Campo();
	
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
		this.fixo = new Campo(1,1,31,31,true,Register.campoNumerico,0,"");
		this.quantidadeCheques = new Campo(2,32,41,10,true,Register.campoNumerico,0,"");
		this.filler = new Campo(3,42,79,38,true,Register.campoNumerico,0,"");
		this.valorArquivo = new Campo(4,80,96,17,true,Register.campoNumerico,0,"");
		this.filler2 = new Campo(5,97,130,34,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(6,131,140,10,true,Register.campoNumerico,0,"");
	}

	public Campo getFixo()
	{
		return fixo;
	}

	public void setFixo(Campo fixo)
	{
		this.fixo = fixo;
	}

	public Campo getQuantidadeCheques()
	{
		return quantidadeCheques;
	}

	public void setQuantidadeCheques(Campo quantidadeCheques)
	{
		this.quantidadeCheques = quantidadeCheques;
	}

	public Campo getFiller()
	{
		return filler;
	}

	public void setFiller(Campo filler)
	{
		this.filler = filler;
	}

	public Campo getValorArquivo()
	{
		return valorArquivo;
	}

	public void setValorArquivo(Campo valorArquivo)
	{
		this.valorArquivo = valorArquivo;
	}

	public Campo getFiller2()
	{
		return filler2;
	}

	public void setFiller2(Campo filler2)
	{
		this.filler2 = filler2;
	}
}
