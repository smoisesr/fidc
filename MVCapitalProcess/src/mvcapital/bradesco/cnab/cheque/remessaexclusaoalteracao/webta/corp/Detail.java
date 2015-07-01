package mvcapital.bradesco.cnab.cheque.remessaexclusaoalteracao.webta.corp;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Detail extends Register
{
	private Campo id = new Campo();
	private Campo filler = new Campo();
	private Campo bancoSacado = new Campo();
	private Campo agenciaSacado = new Campo();
	private Campo filler2 = new Campo();
	private Campo contaDoSacado = new Campo();
	private Campo filler3 = new Campo();
	private Campo numeroDoDocumento = new Campo();
	private Campo filler4 = new Campo();
	private Campo tipoDeSolicitacao = new Campo();
	private Campo valorDoCheque = new Campo();
	private Campo filler5 = new Campo();
	private Campo tipoDoCheque = new Campo();
	private Campo bancoOrigem = new Campo();
	private Campo filler6 = new Campo();
	private Campo filler7 = new Campo();
	private Campo filler8 = new Campo();
	private Campo dataVencimento = new Campo();
	private Campo filler9 = new Campo();
	private Campo filler10 = new Campo();
	private Campo novaDataDeVencimento = new Campo();
	private Campo filler11 = new Campo();
	private Campo filial = new Campo();
	private Campo dataMovimento = new Campo();
	private Campo filler12 = new Campo();

	public Detail() 
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
		this.filler = new Campo(2,2,3,2,true,Register.campoNumerico,0,"");
		this.bancoSacado = new Campo(3,4,6,3,true,Register.campoNumerico,0,"");
		this.agenciaSacado = new Campo(4,7,10,4,true,Register.campoNumerico,0,"");
		this.filler2 = new Campo(5,11,11,1,true,Register.campoNumerico,0,"");
		this.contaDoSacado = new Campo(6,12,23,12,true,Register.campoNumerico,0,"");
		this.filler3 = new Campo(7,24,24,1,true,Register.campoNumerico,0,"");
		this.numeroDoDocumento = new Campo(8,25,30,6,true,Register.campoNumerico,0,"");
		this.filler4 = new Campo(9,31,31,1,true,Register.campoNumerico,0,"");
		this.tipoDeSolicitacao = new Campo(10,32,33,2,true,Register.campoNumerico,0,"");
		this.valorDoCheque = new Campo(11,34,50,17,true,Register.campoNumerico,0,"");
		this.filler5 = new Campo(12,51,52,2,true,Register.campoAlfaNumerico,0,"");
		this.tipoDoCheque = new Campo(13,53,53,1,true,Register.campoNumerico,0,"");
		this.bancoOrigem = new Campo(14,54,56,3,true,Register.campoNumerico,0,"");
		this.filler6 = new Campo(15,57,60,4,true,Register.campoAlfaNumerico,0,"");
		this.filler7 = new Campo(16,61,67,7,true,Register.campoNumerico,0,"");
		this.filler8 = new Campo(17,68,70,3,true,Register.campoNumerico,0,"");
		this.dataVencimento = new Campo(18,71,78,8,true,Register.campoNumerico,0,"");
		this.filler9 = new Campo(19,79,84,6,true,Register.campoNumerico,0,"");
		this.filler10 = new Campo(20,85,96,12,true,Register.campoNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(21,97,106,10,true,Register.campoNumerico,0,"");
		this.novaDataDeVencimento = new Campo(22,107,122,16,true,Register.campoNumerico,0,"");
		this.filler11 = new Campo(23,123,130,8,true,Register.campoNumerico,0,"");
		this.filial = new Campo(24,131,134,4,true,Register.campoNumerico,0,"");
		this.dataMovimento = new Campo(25,135,142,8,true,Register.campoNumerico,0,"");
		this.filler12 = new Campo(26,143,158,16,true,Register.campoAlfaNumerico,0,"");
	}




	public Campo getId()
	{
		return id;
	}




	public void setId(Campo id)
	{
		this.id = id;
	}




	public Campo getFiller()
	{
		return filler;
	}




	public void setFiller(Campo filler)
	{
		this.filler = filler;
	}




	public Campo getBancoSacado()
	{
		return bancoSacado;
	}




	public void setBancoSacado(Campo bancoSacado)
	{
		this.bancoSacado = bancoSacado;
	}




	public Campo getAgenciaSacado()
	{
		return agenciaSacado;
	}




	public void setAgenciaSacado(Campo agenciaSacado)
	{
		this.agenciaSacado = agenciaSacado;
	}




	public Campo getFiller2()
	{
		return filler2;
	}




	public void setFiller2(Campo filler2)
	{
		this.filler2 = filler2;
	}




	public Campo getContaDoSacado()
	{
		return contaDoSacado;
	}




	public void setContaDoSacado(Campo contaDoSacado)
	{
		this.contaDoSacado = contaDoSacado;
	}




	public Campo getFiller3()
	{
		return filler3;
	}




	public void setFiller3(Campo filler3)
	{
		this.filler3 = filler3;
	}




	public Campo getNumeroDoDocumento()
	{
		return numeroDoDocumento;
	}




	public void setNumeroDoDocumento(Campo numeroDoDocumento)
	{
		this.numeroDoDocumento = numeroDoDocumento;
	}




	public Campo getFiller4()
	{
		return filler4;
	}




	public void setFiller4(Campo filler4)
	{
		this.filler4 = filler4;
	}




	public Campo getTipoDeSolicitacao()
	{
		return tipoDeSolicitacao;
	}




	public void setTipoDeSolicitacao(Campo tipoDeSolicitacao)
	{
		this.tipoDeSolicitacao = tipoDeSolicitacao;
	}




	public Campo getValorDoCheque()
	{
		return valorDoCheque;
	}




	public void setValorDoCheque(Campo valorDoCheque)
	{
		this.valorDoCheque = valorDoCheque;
	}




	public Campo getFiller5()
	{
		return filler5;
	}




	public void setFiller5(Campo filler5)
	{
		this.filler5 = filler5;
	}




	public Campo getTipoDoCheque()
	{
		return tipoDoCheque;
	}




	public void setTipoDoCheque(Campo tipoDoCheque)
	{
		this.tipoDoCheque = tipoDoCheque;
	}




	public Campo getBancoOrigem()
	{
		return bancoOrigem;
	}




	public void setBancoOrigem(Campo bancoOrigem)
	{
		this.bancoOrigem = bancoOrigem;
	}




	public Campo getFiller6()
	{
		return filler6;
	}




	public void setFiller6(Campo filler6)
	{
		this.filler6 = filler6;
	}




	public Campo getFiller7()
	{
		return filler7;
	}




	public void setFiller7(Campo filler7)
	{
		this.filler7 = filler7;
	}




	public Campo getFiller8()
	{
		return filler8;
	}




	public void setFiller8(Campo filler8)
	{
		this.filler8 = filler8;
	}




	public Campo getDataVencimento()
	{
		return dataVencimento;
	}




	public void setDataVencimento(Campo dataVencimento)
	{
		this.dataVencimento = dataVencimento;
	}




	public Campo getFiller9()
	{
		return filler9;
	}




	public void setFiller9(Campo filler9)
	{
		this.filler9 = filler9;
	}




	public Campo getFiller10()
	{
		return filler10;
	}




	public void setFiller10(Campo filler10)
	{
		this.filler10 = filler10;
	}




	public Campo getNovaDataDeVencimento()
	{
		return novaDataDeVencimento;
	}




	public void setNovaDataDeVencimento(Campo novaDataDeVencimento)
	{
		this.novaDataDeVencimento = novaDataDeVencimento;
	}




	public Campo getFiller11()
	{
		return filler11;
	}




	public void setFiller11(Campo filler11)
	{
		this.filler11 = filler11;
	}




	public Campo getFilial()
	{
		return filial;
	}




	public void setFilial(Campo filial)
	{
		this.filial = filial;
	}




	public Campo getDataMovimento()
	{
		return dataMovimento;
	}




	public void setDataMovimento(Campo dataMovimento)
	{
		this.dataMovimento = dataMovimento;
	}




	public Campo getFiller12()
	{
		return filler12;
	}




	public void setFiller12(Campo filler12)
	{
		this.filler12 = filler12;
	}

}
