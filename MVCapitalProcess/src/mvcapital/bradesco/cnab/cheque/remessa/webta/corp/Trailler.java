package mvcapital.bradesco.cnab.cheque.remessa.webta.corp;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Trailler extends Register
{
	private Campo tipoDeRegistro = new Campo();
	private Campo dataDeGeracaoArquivo = new Campo();
	private Campo identificacaoDaRotina = new Campo();
	private Campo quantidadeDeRegistros = new Campo();
	private Campo valorTotalDoArquivo = new Campo();
	private Campo filler = new Campo();
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
		this.tipoDeRegistro = new Campo(1,1,1,1,true,Register.campoNumerico,0,"9");
		this.dataDeGeracaoArquivo = new Campo(2,2,9,8,true,Register.campoNumerico,0,"");
		this.identificacaoDaRotina = new Campo(3,10,17,8,true,Register.campoAlfaNumerico,0,"CUSTODIA");
		this.quantidadeDeRegistros = new Campo(4,18,26,9,true,Register.campoNumerico,0,"");
		this.valorTotalDoArquivo = new Campo(5,27,43,17,true,Register.campoNumerico,0,"");
		this.filler = new Campo(6,44,180,137,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(7,181,190,10,true,Register.campoNumerico,0,"");
		this.filler2 = new Campo(8,191,250,60,true,Register.campoAlfaNumerico,0,"");
	}



	public Campo getTipoDeRegistro()
	{
		return tipoDeRegistro;
	}



	public void setTipoDeRegistro(Campo tipoDeRegistro)
	{
		this.tipoDeRegistro = tipoDeRegistro;
	}



	public Campo getDataDeGeracaoArquivo()
	{
		return dataDeGeracaoArquivo;
	}



	public void setDataDeGeracaoArquivo(Campo dataDeGeracaoArquivo)
	{
		this.dataDeGeracaoArquivo = dataDeGeracaoArquivo;
	}



	public Campo getIdentificacaoDaRotina()
	{
		return identificacaoDaRotina;
	}



	public void setIdentificacaoDaRotina(Campo identificacaoDaRotina)
	{
		this.identificacaoDaRotina = identificacaoDaRotina;
	}



	public Campo getQuantidadeDeRegistros()
	{
		return quantidadeDeRegistros;
	}



	public void setQuantidadeDeRegistros(Campo quantidadeDeRegistros)
	{
		this.quantidadeDeRegistros = quantidadeDeRegistros;
	}



	public Campo getValorTotalDoArquivo()
	{
		return valorTotalDoArquivo;
	}



	public void setValorTotalDoArquivo(Campo valorTotalDoArquivo)
	{
		this.valorTotalDoArquivo = valorTotalDoArquivo;
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
}
