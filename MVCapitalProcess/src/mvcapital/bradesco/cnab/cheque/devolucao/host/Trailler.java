package mvcapital.bradesco.cnab.cheque.devolucao.host;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Trailler extends Register
{
	private Campo identificador = new Campo();
	private Campo totalDetalhes = new Campo();
	private Campo fico = new Campo();
	private Campo valorTotalDetalhes = new Campo();
	private Campo filler = new Campo();
	
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
		this.identificador = new Campo(1,1,47,47,true,Register.campoNumerico,0,"");
		this.totalDetalhes = new Campo(2,48,57,10,true,Register.campoNumerico,0,"");
		this.fico = new Campo(3,58,58,1,true,Register.campoNumerico,0,"");
		this.valorTotalDetalhes = new Campo(4,59,76,18,true,Register.campoNumerico,0,"");
		this.filler = new Campo(5,77,150,74,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(6,151,160,10,true,Register.campoNumerico,0,"");
	}

	public Campo getIdentificador()
	{
		return identificador;
	}

	public void setIdentificador(Campo identificador)
	{
		this.identificador = identificador;
	}

	public Campo getTotalDetalhes()
	{
		return totalDetalhes;
	}

	public void setTotalDetalhes(Campo totalDetalhes)
	{
		this.totalDetalhes = totalDetalhes;
	}

	public Campo getFico()
	{
		return fico;
	}

	public void setFico(Campo fico)
	{
		this.fico = fico;
	}

	public Campo getValorTotalDetalhes()
	{
		return valorTotalDetalhes;
	}

	public void setValorTotalDetalhes(Campo valorTotalDetalhes)
	{
		this.valorTotalDetalhes = valorTotalDetalhes;
	}

	public Campo getFiller()
	{
		return filler;
	}

	public void setFiller(Campo filler)
	{
		this.filler = filler;
	}
}
