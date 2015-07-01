package mvcapital.bradesco.cnab.cheque.retornoinclusaodmaisum.host;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class HeaderLote extends Register
{
	private Campo zeros = new Campo();
	private Campo agencia = new Campo();
	private Campo razao = new Campo();
	private Campo contaCorrente = new Campo();
	private Campo filial = new Campo();
	private Campo dataDeMovimento = new Campo();
	private Campo codigoDeServico = new Campo();
	private Campo espacos = new Campo();

	public HeaderLote() 
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
		this.zeros = new Campo(1,1,47,47,true,Register.campoNumerico,0,"");
		this.agencia = new Campo(2,48,51,4,true,Register.campoNumerico,0,"");
		this.razao = new Campo(3,52,55,4,true,Register.campoNumerico,0,"");
		this.contaCorrente = new Campo(4,56,65,10,true,Register.campoNumerico,0,"");
		this.filial = new Campo(5,66,69,4,true,Register.campoNumerico,0,"");
		this.dataDeMovimento = new Campo(6,70,77,8,true,Register.campoNumerico,0,"");
		this.codigoDeServico = new Campo(7,78,82,5,true,Register.campoAlfaNumerico,0,"");
		this.espacos = new Campo(8,83,150,68,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(9,151,160,10,true,Register.campoNumerico,0,"");
	}

	public Campo getZeros()
	{
		return zeros;
	}

	public void setZeros(Campo zeros)
	{
		this.zeros = zeros;
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

	public Campo getDataDeMovimento()
	{
		return dataDeMovimento;
	}

	public void setDataDeMovimento(Campo dataDeMovimento)
	{
		this.dataDeMovimento = dataDeMovimento;
	}

	public Campo getCodigoDeServico()
	{
		return codigoDeServico;
	}

	public void setCodigoDeServico(Campo codigoDeServico)
	{
		this.codigoDeServico = codigoDeServico;
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
