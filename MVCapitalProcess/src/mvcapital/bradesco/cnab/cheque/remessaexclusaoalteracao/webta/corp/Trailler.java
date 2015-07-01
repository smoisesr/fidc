package mvcapital.bradesco.cnab.cheque.remessaexclusaoalteracao.webta.corp;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Trailler extends Register
{
	private Campo id = new Campo();
	private Campo fixo = new Campo();
	private Campo codigoServico = new Campo();
	private Campo fixo2 = new Campo();
	private Campo valorTotalArquivo = new Campo();
	private Campo fixo3 = new Campo();
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
		this.id = new Campo(1,1,1,1,true,Register.campoNumerico,0,"");
		this.fixo = new Campo(2,2,31,30,true,Register.campoNumerico,0,"");
		this.codigoServico = new Campo(3,32,37,6,true,Register.campoNumerico,0,"");
		this.fixo2 = new Campo(4,38,78,41,true,Register.campoAlfaNumerico,0,"");
		this.valorTotalArquivo = new Campo(5,79,96,18,true,Register.campoNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(6,97,106,10,true,Register.campoNumerico,0,"");
		this.fixo3 = new Campo(7,107,122,16,true,Register.campoNumerico,0,"");
		this.filler = new Campo(8,123,150,28,true,Register.campoAlfaNumerico,0,"");
	}

	public Campo getId()
	{
		return id;
	}

	public void setId(Campo id)
	{
		this.id = id;
	}

	public Campo getFixo()
	{
		return fixo;
	}

	public void setFixo(Campo fixo)
	{
		this.fixo = fixo;
	}

	public Campo getCodigoServico()
	{
		return codigoServico;
	}

	public void setCodigoServico(Campo codigoServico)
	{
		this.codigoServico = codigoServico;
	}

	public Campo getFixo2()
	{
		return fixo2;
	}

	public void setFixo2(Campo fixo2)
	{
		this.fixo2 = fixo2;
	}

	public Campo getValorTotalArquivo()
	{
		return valorTotalArquivo;
	}

	public void setValorTotalArquivo(Campo valorTotalArquivo)
	{
		this.valorTotalArquivo = valorTotalArquivo;
	}

	public Campo getFixo3()
	{
		return fixo3;
	}

	public void setFixo3(Campo fixo3)
	{
		this.fixo3 = fixo3;
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
