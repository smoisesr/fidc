package mvcapital.bradesco.cnab.cheque.remessa.webta.corp;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Header extends Register 
{
	private Campo tipoDeRegistro = new Campo();
	private Campo dataDeGeracaoDoArquivo = new Campo();
	private Campo identificacaoDaRotina = new Campo();
	private Campo agenciaRecebedora = new Campo();
	private Campo codigoDaEmpresa = new Campo();
	private Campo horaDeCriacao = new Campo();
	private Campo codigoDoBanco = new Campo();
	private Campo agenciaDeCredito = new Campo();
	private Campo contaDeCredito = new Campo();
	private Campo digitoDaContaDeCredito = new Campo();
	private Campo filler = new Campo();
	private Campo filler2 = new Campo();
	private Campo filler3 = new Campo();
	private Campo filler4 = new Campo();

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
		this.tipoDeRegistro = new Campo(1,1,1,1,true,Register.campoNumerico,0,"");
		this.dataDeGeracaoDoArquivo = new Campo(2,2,9,8,true,Register.campoNumerico,0,"");
		this.identificacaoDaRotina = new Campo(3,10,17,8,true,Register.campoAlfaNumerico,0,"CUSTODIA");
		this.agenciaRecebedora = new Campo(4,18,22,5,true,Register.campoNumerico,0,"");
		this.codigoDaEmpresa = new Campo(5,23,28,6,true,Register.campoNumerico,0,"");
		this.horaDeCriacao = new Campo(6,29,34,6,true,Register.campoNumerico,0,"");
		this.codigoDoBanco = new Campo(7,35,37,3,true,Register.campoNumerico,0,"237");
		this.agenciaDeCredito = new Campo(8,38,42,5,true,Register.campoNumerico,0,"");
		this.contaDeCredito = new Campo(9,43,55,13,true,Register.campoNumerico,0,"");
		this.digitoDaContaDeCredito = new Campo(10,56,56,1,true,Register.campoAlfaNumerico,0,"");
		this.filler = new Campo(11,57,57,1,true,Register.campoAlfaNumerico,0,"");
		this.filler2 = new Campo(12,58,62,5,true,Register.campoAlfaNumerico,0,"");
		this.filler3 = new Campo(13,63,180,118,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro=new Campo(14,181,190,10,true,Register.campoNumerico,0,"1");
		this.filler4 = new Campo(15,191,250,60,true,Register.campoAlfaNumerico,0,"");

	}



	public Campo getTipoDeRegistro()
	{
		return tipoDeRegistro;
	}



	public void setTipoDeRegistro(Campo tipoDeRegistro)
	{
		this.tipoDeRegistro = tipoDeRegistro;
	}



	public Campo getDataDeGeracaoDoArquivo()
	{
		return dataDeGeracaoDoArquivo;
	}



	public void setDataDeGeracaoDoArquivo(Campo dataDeGeracaoDoArquivo)
	{
		this.dataDeGeracaoDoArquivo = dataDeGeracaoDoArquivo;
	}



	public Campo getIdentificacaoDaRotina()
	{
		return identificacaoDaRotina;
	}



	public void setIdentificacaoDaRotina(Campo identificacaoDaRotina)
	{
		this.identificacaoDaRotina = identificacaoDaRotina;
	}



	public Campo getAgenciaRecebedora()
	{
		return agenciaRecebedora;
	}



	public void setAgenciaRecebedora(Campo agenciaRecebedora)
	{
		this.agenciaRecebedora = agenciaRecebedora;
	}



	public Campo getCodigoDaEmpresa()
	{
		return codigoDaEmpresa;
	}



	public void setCodigoDaEmpresa(Campo codigoDaEmpresa)
	{
		this.codigoDaEmpresa = codigoDaEmpresa;
	}



	public Campo getHoraDeCriacao()
	{
		return horaDeCriacao;
	}



	public void setHoraDeCriacao(Campo horaDeCriacao)
	{
		this.horaDeCriacao = horaDeCriacao;
	}



	public Campo getCodigoDoBanco()
	{
		return codigoDoBanco;
	}



	public void setCodigoDoBanco(Campo codigoDoBanco)
	{
		this.codigoDoBanco = codigoDoBanco;
	}



	public Campo getAgenciaDeCredito()
	{
		return agenciaDeCredito;
	}



	public void setAgenciaDeCredito(Campo agenciaDeCredito)
	{
		this.agenciaDeCredito = agenciaDeCredito;
	}



	public Campo getContaDeCredito()
	{
		return contaDeCredito;
	}



	public void setContaDeCredito(Campo contaDeCredito)
	{
		this.contaDeCredito = contaDeCredito;
	}



	public Campo getDigitoDaContaDeCredito()
	{
		return digitoDaContaDeCredito;
	}



	public void setDigitoDaContaDeCredito(Campo digitoDaContaDeCredito)
	{
		this.digitoDaContaDeCredito = digitoDaContaDeCredito;
	}



	public Campo getFiller()
	{
		return filler;
	}



	public void setFiller(Campo filler)
	{
		this.filler = filler;
	}



	public Campo getFiller2()
	{
		return filler2;
	}



	public void setFiller2(Campo filler2)
	{
		this.filler2 = filler2;
	}



	public Campo getFiller3()
	{
		return filler3;
	}



	public void setFiller3(Campo filler3)
	{
		this.filler3 = filler3;
	}



	public Campo getFiller4()
	{
		return filler4;
	}



	public void setFiller4(Campo filler4)
	{
		this.filler4 = filler4;
	}
}
