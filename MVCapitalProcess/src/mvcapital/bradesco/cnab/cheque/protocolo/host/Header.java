package mvcapital.bradesco.cnab.cheque.protocolo.host;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Header extends Register 
{
	private Campo fixo = new Campo();
	private Campo codigoDeServico = new Campo();
	private Campo fixo2 = new Campo();
	private Campo bancoApresentante = new Campo();
	private Campo filler = new Campo();
	private Campo dataMovimento = new Campo();
	private Campo dataDeEnvio = new Campo();
	private Campo horaEnvio = new Campo();
	private Campo codigoDoCliente = new Campo();
	private Campo filler2 = new Campo();

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
		this.fixo = new Campo(1,1,31,31,true,Register.campoNumerico,0,"");
		this.codigoDeServico = new Campo(2,32,37,6,true,Register.campoNumerico,0,"");
		this.fixo2 = new Campo(3,38,40,3,true,Register.campoNumerico,0,"");
		this.bancoApresentante = new Campo(4,41,43,3,true,Register.campoNumerico,0,"");
		this.filler = new Campo(5,44,45,2,true,Register.campoAlfaNumerico,0,"");
		this.dataMovimento = new Campo(6,46,53,8,true,Register.campoNumerico,0,"");
		this.dataDeEnvio = new Campo(7,54,61,8,true,Register.campoNumerico,0,"");
		this.horaEnvio = new Campo(8,62,67,6,true,Register.campoNumerico,0,"");
		this.codigoDoCliente = new Campo(9,68,73,6,true,Register.campoNumerico,0,"");
		this.filler2 = new Campo(10,74,130,57,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(11,131,140,10,true,Register.campoNumerico,0,"");
	}

	public Campo getFixo()
	{
		return fixo;
	}

	public void setFixo(Campo fixo)
	{
		this.fixo = fixo;
	}

	public Campo getCodigoDeServico()
	{
		return codigoDeServico;
	}

	public void setCodigoDeServico(Campo codigoDeServico)
	{
		this.codigoDeServico = codigoDeServico;
	}

	public Campo getFixo2()
	{
		return fixo2;
	}

	public void setFixo2(Campo fixo2)
	{
		this.fixo2 = fixo2;
	}

	public Campo getBancoApresentante()
	{
		return bancoApresentante;
	}

	public void setBancoApresentante(Campo bancoApresentante)
	{
		this.bancoApresentante = bancoApresentante;
	}

	public Campo getFiller()
	{
		return filler;
	}

	public void setFiller(Campo filler)
	{
		this.filler = filler;
	}

	public Campo getDataMovimento()
	{
		return dataMovimento;
	}

	public void setDataMovimento(Campo dataMovimento)
	{
		this.dataMovimento = dataMovimento;
	}

	public Campo getDataDeEnvio()
	{
		return dataDeEnvio;
	}

	public void setDataDeEnvio(Campo dataDeEnvio)
	{
		this.dataDeEnvio = dataDeEnvio;
	}

	public Campo getHoraEnvio()
	{
		return horaEnvio;
	}

	public void setHoraEnvio(Campo horaEnvio)
	{
		this.horaEnvio = horaEnvio;
	}

	public Campo getCodigoDoCliente()
	{
		return codigoDoCliente;
	}

	public void setCodigoDoCliente(Campo codigoDoCliente)
	{
		this.codigoDoCliente = codigoDoCliente;
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
