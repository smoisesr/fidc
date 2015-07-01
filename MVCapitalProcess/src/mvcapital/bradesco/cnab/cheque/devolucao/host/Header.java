package mvcapital.bradesco.cnab.cheque.devolucao.host;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Header extends Register 
{
	private Campo identificador = new Campo();
	private Campo agencia = new Campo();
	private Campo razao = new Campo();
	private Campo conta = new Campo();
	private Campo dataMovimento = new Campo();
	private Campo codigoServico = new Campo();
	private Campo codigoPerfil = new Campo();
	private Campo filler = new Campo();

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
		this.identificador = new Campo(1,1,47,47,true,Register.campoNumerico,0,"");
		this.agencia = new Campo(2,48,51,4,true,Register.campoNumerico,0,"");
		this.razao = new Campo(3,52,55,4,true,Register.campoNumerico,0,"");
		this.conta = new Campo(4,56,65,10,true,Register.campoNumerico,0,"");
		this.dataMovimento = new Campo(5,66,73,8,true,Register.campoNumerico,0,"");
		this.codigoServico = new Campo(6,74,78,5,true,Register.campoAlfaNumerico,0,"");
		this.codigoPerfil = new Campo(7,79,83,5,true,Register.campoAlfaNumerico,0,"");
		this.filler = new Campo(8,84,150,67,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(9,151,160,10,true,Register.campoNumerico,0,"");
	}

	public Campo getIdentificador()
	{
		return identificador;
	}

	public void setIdentificador(Campo identificador)
	{
		this.identificador = identificador;
	}

	public Campo getAgencia()
	{
		return agencia;
	}

	public void setAgencia(Campo agencia)
	{
		this.agencia = agencia;
	}

	public Campo getRazao()
	{
		return razao;
	}

	public void setRazao(Campo razao)
	{
		this.razao = razao;
	}

	public Campo getConta()
	{
		return conta;
	}

	public void setConta(Campo conta)
	{
		this.conta = conta;
	}

	public Campo getDataMovimento()
	{
		return dataMovimento;
	}

	public void setDataMovimento(Campo dataMovimento)
	{
		this.dataMovimento = dataMovimento;
	}

	public Campo getCodigoServico()
	{
		return codigoServico;
	}

	public void setCodigoServico(Campo codigoServico)
	{
		this.codigoServico = codigoServico;
	}

	public Campo getCodigoPerfil()
	{
		return codigoPerfil;
	}

	public void setCodigoPerfil(Campo codigoPerfil)
	{
		this.codigoPerfil = codigoPerfil;
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
