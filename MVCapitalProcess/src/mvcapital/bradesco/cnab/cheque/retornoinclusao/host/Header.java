package mvcapital.bradesco.cnab.cheque.retornoinclusao.host;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Header extends Register 
{
	private Campo zeros = new Campo();
	private Campo codigoDaEmpresa = new Campo();
	private Campo nomeDaEmpresa = new Campo();
	private Campo dataDeMovimento = new Campo();
	private Campo tipoDeServico = new Campo();
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
		this.zeros = new Campo(1,1,15,15,true,Register.campoNumerico,0,"");
		this.codigoDaEmpresa = new Campo(2,16,25,10,true,Register.campoNumerico,0,"");
		this.nomeDaEmpresa = new Campo(3,26,45,20,true,Register.campoAlfaNumerico,0,"");
		this.dataDeMovimento = new Campo(4,46,53,8,true,Register.campoNumerico,0,"");
		this.tipoDeServico = new Campo(5,54,68,15,true,Register.campoAlfaNumerico,0,"");
		this.espacos = new Campo(6,69,150,82,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(7,151,160,10,true,Register.campoNumerico,0,"");
	}

	public Campo getZeros()
	{
		return zeros;
	}

	public void setZeros(Campo zeros)
	{
		this.zeros = zeros;
	}

	public Campo getCodigoDaEmpresa()
	{
		return codigoDaEmpresa;
	}

	public void setCodigoDaEmpresa(Campo codigoDaEmpresa)
	{
		this.codigoDaEmpresa = codigoDaEmpresa;
	}

	public Campo getNomeDaEmpresa()
	{
		return nomeDaEmpresa;
	}

	public void setNomeDaEmpresa(Campo nomeDaEmpresa)
	{
		this.nomeDaEmpresa = nomeDaEmpresa;
	}

	public Campo getDataDeMovimento()
	{
		return dataDeMovimento;
	}

	public void setDataDeMovimento(Campo dataDeMovimento)
	{
		this.dataDeMovimento = dataDeMovimento;
	}

	public Campo getTipoDeServico()
	{
		return tipoDeServico;
	}

	public void setTipoDeServico(Campo tipoDeServico)
	{
		this.tipoDeServico = tipoDeServico;
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
