package mvcapital.bradesco.cnab.cheque.manutencao.webta.corp;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Header extends Register 
{
	private Campo id = new Campo();
	private Campo dataDeMovimento = new Campo();
	private Campo agencia = new Campo();
	private Campo contaCorrente = new Campo();
	private Campo filial = new Campo();
	private Campo filler = new Campo();
	private Campo codigoDaEmpresa = new Campo();
	private Campo espacos = new Campo();


	public Header() 
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
//		this.identificacaoDoRegistro.setConteudo("0");
		this.id = new Campo(1,1,1,1,true,Register.campoNumerico,0,"");
		this.dataDeMovimento = new Campo(2,2,9,8,true,Register.campoNumerico,0,"");
		this.agencia = new Campo(3,10,13,4,true,Register.campoNumerico,0,"");
		this.contaCorrente = new Campo(4,14,19,6,true,Register.campoNumerico,0,"");
		this.filial = new Campo(5,20,23,4,true,Register.campoNumerico,0,"");
		this.filler = new Campo(6,24,72,49,true,Register.campoNumerico,0,"");
		this.codigoDaEmpresa = new Campo(7,73,80,8,true,Register.campoNumerico,0,"");
		this.espacos = new Campo(8,81,130,50,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(9,131,140,10,true,Register.campoNumerico,0,"");
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


	public Campo getAgencia()
	{
		return agencia;
	}


	public void setAgencia(Campo agencia)
	{
		this.agencia = agencia;
	}


	public Campo getContaCorrente()
	{
		return contaCorrente;
	}


	public void setContaCorrente(Campo contaCorrente)
	{
		this.contaCorrente = contaCorrente;
	}


	public Campo getFilial()
	{
		return filial;
	}


	public void setFilial(Campo filial)
	{
		this.filial = filial;
	}


	public Campo getFiller()
	{
		return filler;
	}


	public void setFiller(Campo filler)
	{
		this.filler = filler;
	}


	public Campo getCodigoDaEmpresa()
	{
		return codigoDaEmpresa;
	}


	public void setCodigoDaEmpresa(Campo codigoDaEmpresa)
	{
		this.codigoDaEmpresa = codigoDaEmpresa;
	}


	public Campo getEspacos()
	{
		return espacos;
	}


	public void setEspacos(Campo espacos)
	{
		this.espacos = espacos;
	}
}
