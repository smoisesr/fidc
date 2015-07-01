package mvcapital.bradesco.cnab.cheque.remessaexclusaoalteracao.webta.corp;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Header extends Register 
{
	private Campo id = new Campo();
	private Campo fixo = new Campo();
	private Campo codigoServico = new Campo();
	private Campo filler = new Campo();
	private Campo bancoApresentante = new Campo();
	private Campo filler2 = new Campo();
	private Campo dataMovimento = new Campo();
	private Campo dataCriacao = new Campo();
	private Campo hora = new Campo();
	private Campo filler3 = new Campo();
	private Campo codigoDeEmpresa = new Campo();
	private Campo agenciaEmpresa = new Campo();
	private Campo contaDaEmpresa = new Campo();
	private Campo filial = new Campo();
	private Campo fixo2 = new Campo();

	private Campo fixo3 = new Campo();
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
		this.id = new Campo(1,1,1,1,true,Register.campoNumerico,0,"");
		this.fixo = new Campo(2,2,31,30,true,Register.campoNumerico,0,"");
		this.codigoServico = new Campo(3,32,37,6,true,Register.campoNumerico,0,"");
		this.filler = new Campo(4,38,40,3,true,Register.campoNumerico,0,"");
		this.bancoApresentante = new Campo(5,41,43,3,true,Register.campoNumerico,0,"");
		this.filler2 = new Campo(6,44,45,2,true,Register.campoAlfaNumerico,0,"");
		this.dataMovimento = new Campo(7,46,53,8,true,Register.campoNumerico,0,"");
		this.dataCriacao = new Campo(8,54,61,8,true,Register.campoNumerico,0,"");
		this.hora = new Campo(9,62,65,4,true,Register.campoNumerico,0,"");
		this.filler3 = new Campo(10,66,69,4,true,Register.campoNumerico,0,"");
		this.codigoDeEmpresa = new Campo(11,70,75,6,true,Register.campoNumerico,0,"");
		this.agenciaEmpresa = new Campo(12,76,80,5,true,Register.campoNumerico,0,"");
		this.contaDaEmpresa = new Campo(13,81,87,7,true,Register.campoNumerico,0,"");
		this.filial = new Campo(14,88,92,5,true,Register.campoNumerico,0,"");
		this.fixo2 = new Campo(15,93,96,4,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(16,97,106,10,true,Register.campoNumerico,0,"");
		this.fixo3 = new Campo(17,107,122,16,true,Register.campoNumerico,0,"");
		this.filler4 = new Campo(18,123,150,28,true,Register.campoAlfaNumerico,0,"");
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


	public Campo getFiller()
	{
		return filler;
	}


	public void setFiller(Campo filler)
	{
		this.filler = filler;
	}


	public Campo getBancoApresentante()
	{
		return bancoApresentante;
	}


	public void setBancoApresentante(Campo bancoApresentante)
	{
		this.bancoApresentante = bancoApresentante;
	}


	public Campo getFiller2()
	{
		return filler2;
	}


	public void setFiller2(Campo filler2)
	{
		this.filler2 = filler2;
	}


	public Campo getDataMovimento()
	{
		return dataMovimento;
	}


	public void setDataMovimento(Campo dataMovimento)
	{
		this.dataMovimento = dataMovimento;
	}


	public Campo getDataCriacao()
	{
		return dataCriacao;
	}


	public void setDataCriacao(Campo dataCriacao)
	{
		this.dataCriacao = dataCriacao;
	}


	public Campo getHora()
	{
		return hora;
	}


	public void setHora(Campo hora)
	{
		this.hora = hora;
	}


	public Campo getFiller3()
	{
		return filler3;
	}


	public void setFiller3(Campo filler3)
	{
		this.filler3 = filler3;
	}


	public Campo getCodigoDeEmpresa()
	{
		return codigoDeEmpresa;
	}


	public void setCodigoDeEmpresa(Campo codigoDeEmpresa)
	{
		this.codigoDeEmpresa = codigoDeEmpresa;
	}


	public Campo getAgenciaEmpresa()
	{
		return agenciaEmpresa;
	}


	public void setAgenciaEmpresa(Campo agenciaEmpresa)
	{
		this.agenciaEmpresa = agenciaEmpresa;
	}


	public Campo getContaDaEmpresa()
	{
		return contaDaEmpresa;
	}


	public void setContaDaEmpresa(Campo contaDaEmpresa)
	{
		this.contaDaEmpresa = contaDaEmpresa;
	}


	public Campo getFilial()
	{
		return filial;
	}


	public void setFilial(Campo filial)
	{
		this.filial = filial;
	}


	public Campo getFixo2()
	{
		return fixo2;
	}


	public void setFixo2(Campo fixo2)
	{
		this.fixo2 = fixo2;
	}


	public Campo getFixo3()
	{
		return fixo3;
	}


	public void setFixo3(Campo fixo3)
	{
		this.fixo3 = fixo3;
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
