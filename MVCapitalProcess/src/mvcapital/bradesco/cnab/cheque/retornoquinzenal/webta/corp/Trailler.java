package mvcapital.bradesco.cnab.cheque.retornoquinzenal.webta.corp;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Trailler extends Register
{
	private Campo id = new Campo();
	private Campo dataDeMovimento = new Campo();
	private Campo totalDetalhes = new Campo();
	private Campo totalValorDetalhes = new Campo();
	private Campo espacos = new Campo();
	private Campo sequenciaInterna = new Campo();
	
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
		this.id = new Campo(1,1,1,1,true,Register.campoNumerico,0,"");
		this.dataDeMovimento = new Campo(2,2,9,8,true,Register.campoNumerico,0,"");
		this.totalDetalhes = new Campo(3,10,15,6,true,Register.campoNumerico,0,"");
		this.totalValorDetalhes = new Campo(4,16,30,15,true,Register.campoNumerico,0,"");
		this.espacos = new Campo(5,31,80,50,true,Register.campoAlfaNumerico,0,"");
		this.sequenciaInterna = new Campo(6,81,90,10,true,Register.campoNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(7,91,100,10,true,Register.campoNumerico,0,"");
	}

	public Campo getId()
	{
		return id;
	}

	public void setId(Campo id)
	{
		this.id = id;
	}

	public Campo getDataDeMovimento()
	{
		return dataDeMovimento;
	}

	public void setDataDeMovimento(Campo dataDeMovimento)
	{
		this.dataDeMovimento = dataDeMovimento;
	}

	public Campo getTotalDetalhes()
	{
		return totalDetalhes;
	}

	public void setTotalDetalhes(Campo totalDetalhes)
	{
		this.totalDetalhes = totalDetalhes;
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

	public Campo getSequenciaInterna()
	{
		return sequenciaInterna;
	}

	public void setSequenciaInterna(Campo sequenciaInterna)
	{
		this.sequenciaInterna = sequenciaInterna;
	}
}
