package mvcapital.bradesco.cnab.cheque.retornoinclusao.host;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Trailler extends Register
{
	private Campo noves = new Campo();
	private Campo totalDetalhes = new Campo();
	private Campo fixo = new Campo();
	private Campo totalValorDetalhes = new Campo();
	private Campo espacos = new Campo();
	private Campo sequencia = new Campo();


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
		this.noves = new Campo(1,1,1,1,true,Register.campoAlfaNumerico,0,"");
		this.totalDetalhes = new Campo(2,2,9,8,true,Register.campoNumerico,0,"");
		this.fixo = new Campo(3,10,18,9,true,Register.campoNumerico,0,"");
		this.totalValorDetalhes = new Campo(4,19,28,10,true,Register.campoNumerico,0,"");
		this.espacos = new Campo(5,29,39,11,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(6,40,51,12,true,Register.campoNumerico,0,"");
	}


	public Campo getNoves()
	{
		return noves;
	}


	public void setNoves(Campo noves)
	{
		this.noves = noves;
	}


	public Campo getTotalDetalhes()
	{
		return totalDetalhes;
	}


	public void setTotalDetalhes(Campo totalDetalhes)
	{
		this.totalDetalhes = totalDetalhes;
	}


	public Campo getFixo()
	{
		return fixo;
	}


	public void setFixo(Campo fixo)
	{
		this.fixo = fixo;
	}


	public Campo getTotalValorDetalhes()
	{
		return totalValorDetalhes;
	}


	public void setTotalValorDetalhes(Campo totalValorDetalhes)
	{
		this.totalValorDetalhes = totalValorDetalhes;
	}


	public Campo getEspacos()
	{
		return espacos;
	}


	public void setEspacos(Campo espacos)
	{
		this.espacos = espacos;
	}


	public Campo getSequencia()
	{
		return sequencia;
	}


	public void setSequencia(Campo sequencia)
	{
		this.sequencia = sequencia;
	}
}
