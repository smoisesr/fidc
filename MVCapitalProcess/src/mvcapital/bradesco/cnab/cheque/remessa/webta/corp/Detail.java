package mvcapital.bradesco.cnab.cheque.remessa.webta.corp;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Detail extends Register
{
	private Campo tipoDeRegistro = new Campo();
	private Campo compensacaoDestino = new Campo();
	private Campo bancoDestino = new Campo();
	private Campo agenciaDestino = new Campo();
	private Campo dv1 = new Campo();
	private Campo contaCorrente = new Campo();
	private Campo dv2 = new Campo();
	private Campo numeroDoCheque = new Campo();
	private Campo dv3 = new Campo();
	private Campo valorDoCheque = new Campo();
	private Campo tipificacao = new Campo();
	private Campo dataDoMovimento = new Campo();
	private Campo dataDoVencimento = new Campo();
	private Campo cpfCnpjPrincipal = new Campo();
	private Campo filialCpfCnpj = new Campo();
	private Campo controleCpfCnpj = new Campo();
	private Campo carteira = new Campo();
	private Campo numeroDoContrato = new Campo();
	private Campo numeroDoBordero = new Campo();
	private Campo compensacaoDeAcolhimento = new Campo();
	private Campo tipoDeCaptura = new Campo();
	private Campo campoExclusivoCliente = new Campo();
	private Campo filler = new Campo();
	private Campo filler2 = new Campo();



	
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
		this.tipoDeRegistro = new Campo(1,1,1,1,true,Register.campoNumerico,0,"");
		this.compensacaoDestino = new Campo(2,2,4,3,true,Register.campoNumerico,0,"");
		this.bancoDestino = new Campo(3,5,7,3,true,Register.campoNumerico,0,"");
		this.agenciaDestino = new Campo(4,8,11,4,true,Register.campoNumerico,0,"");
		this.dv1 = new Campo(5,12,12,1,true,Register.campoNumerico,0,"");
		this.contaCorrente = new Campo(6,13,24,12,true,Register.campoNumerico,0,"");
		this.dv2 = new Campo(7,25,25,1,true,Register.campoNumerico,0,"");
		this.numeroDoCheque = new Campo(8,26,31,6,true,Register.campoNumerico,0,"");
		this.dv3 = new Campo(9,32,32,1,true,Register.campoNumerico,0,"");
		this.valorDoCheque = new Campo(10,33,49,17,true,Register.campoNumerico,0,"");
		this.tipificacao = new Campo(11,50,50,1,true,Register.campoNumerico,0,"");
		this.dataDoMovimento = new Campo(12,51,58,8,true,Register.campoNumerico,0,"");
		this.dataDoVencimento = new Campo(13,59,66,8,true,Register.campoNumerico,0,"");
		this.cpfCnpjPrincipal = new Campo(14,67,75,9,true,Register.campoNumerico,0,"");
		this.filialCpfCnpj = new Campo(15,76,80,5,true,Register.campoNumerico,0,"");
		this.controleCpfCnpj = new Campo(16,81,82,2,true,Register.campoNumerico,0,"");
		this.carteira = new Campo(17,83,85,3,true,Register.campoNumerico,0,"");
		this.numeroDoContrato = new Campo(18,86,105,20,true,Register.campoNumerico,0,"");
		this.numeroDoBordero = new Campo(19,106,112,7,true,Register.campoNumerico,0,"");
		this.compensacaoDeAcolhimento = new Campo(20,113,115,3,true,Register.campoNumerico,0,"");
		this.tipoDeCaptura = new Campo(21,116,119,4,true,Register.campoNumerico,0,"");
		this.campoExclusivoCliente = new Campo(22,120,144,25,true,Register.campoAlfaNumerico,0,"");
		this.filler = new Campo(23,145,180,36,true,Register.campoAlfaNumerico,0,"");	
		this.numeroSequencialRegistro = new Campo(24,181,190,10,true,Register.campoNumerico,0,"");
		this.filler2 = new Campo(25,191,250,60,true,Register.campoAlfaNumerico,0,"");

		
	}




	public Campo getTipoDeRegistro()
	{
		return tipoDeRegistro;
	}




	public void setTipoDeRegistro(Campo tipoDeRegistro)
	{
		this.tipoDeRegistro = tipoDeRegistro;
	}




	public Campo getCompensacaoDestino()
	{
		return compensacaoDestino;
	}




	public void setCompensacaoDestino(Campo compensacaoDestino)
	{
		this.compensacaoDestino = compensacaoDestino;
	}




	public Campo getBancoDestino()
	{
		return bancoDestino;
	}




	public void setBancoDestino(Campo bancoDestino)
	{
		this.bancoDestino = bancoDestino;
	}




	public Campo getAgenciaDestino()
	{
		return agenciaDestino;
	}




	public void setAgenciaDestino(Campo agenciaDestino)
	{
		this.agenciaDestino = agenciaDestino;
	}




	public Campo getDv1()
	{
		return dv1;
	}




	public void setDv1(Campo dv1)
	{
		this.dv1 = dv1;
	}




	public Campo getContaCorrente()
	{
		return contaCorrente;
	}




	public void setContaCorrente(Campo contaCorrente)
	{
		this.contaCorrente = contaCorrente;
	}




	public Campo getDv2()
	{
		return dv2;
	}




	public void setDv2(Campo dv2)
	{
		this.dv2 = dv2;
	}




	public Campo getNumeroDoCheque()
	{
		return numeroDoCheque;
	}




	public void setNumeroDoCheque(Campo numeroDoCheque)
	{
		this.numeroDoCheque = numeroDoCheque;
	}




	public Campo getDv3()
	{
		return dv3;
	}




	public void setDv3(Campo dv3)
	{
		this.dv3 = dv3;
	}




	public Campo getValorDoCheque()
	{
		return valorDoCheque;
	}




	public void setValorDoCheque(Campo valorDoCheque)
	{
		this.valorDoCheque = valorDoCheque;
	}




	public Campo getTipificacao()
	{
		return tipificacao;
	}




	public void setTipificacao(Campo tipificacao)
	{
		this.tipificacao = tipificacao;
	}




	public Campo getDataDoMovimento()
	{
		return dataDoMovimento;
	}




	public void setDataDoMovimento(Campo dataDoMovimento)
	{
		this.dataDoMovimento = dataDoMovimento;
	}




	public Campo getDataDoVencimento()
	{
		return dataDoVencimento;
	}




	public void setDataDoVencimento(Campo dataDoVencimento)
	{
		this.dataDoVencimento = dataDoVencimento;
	}




	public Campo getCpfCnpjPrincipal()
	{
		return cpfCnpjPrincipal;
	}




	public void setCpfCnpjPrincipal(Campo cpfCnpjPrincipal)
	{
		this.cpfCnpjPrincipal = cpfCnpjPrincipal;
	}




	public Campo getFilialCpfCnpj()
	{
		return filialCpfCnpj;
	}




	public void setFilialCpfCnpj(Campo filialCpfCnpj)
	{
		this.filialCpfCnpj = filialCpfCnpj;
	}




	public Campo getControleCpfCnpj()
	{
		return controleCpfCnpj;
	}




	public void setControleCpfCnpj(Campo controleCpfCnpj)
	{
		this.controleCpfCnpj = controleCpfCnpj;
	}




	public Campo getCarteira()
	{
		return carteira;
	}




	public void setCarteira(Campo carteira)
	{
		this.carteira = carteira;
	}




	public Campo getNumeroDoContrato()
	{
		return numeroDoContrato;
	}




	public void setNumeroDoContrato(Campo numeroDoContrato)
	{
		this.numeroDoContrato = numeroDoContrato;
	}




	public Campo getNumeroDoBordero()
	{
		return numeroDoBordero;
	}




	public void setNumeroDoBordero(Campo numeroDoBordero)
	{
		this.numeroDoBordero = numeroDoBordero;
	}




	public Campo getCompensacaoDeAcolhimento()
	{
		return compensacaoDeAcolhimento;
	}




	public void setCompensacaoDeAcolhimento(Campo compensacaoDeAcolhimento)
	{
		this.compensacaoDeAcolhimento = compensacaoDeAcolhimento;
	}




	public Campo getTipoDeCaptura()
	{
		return tipoDeCaptura;
	}




	public void setTipoDeCaptura(Campo tipoDeCaptura)
	{
		this.tipoDeCaptura = tipoDeCaptura;
	}




	public Campo getCampoExclusivoCliente()
	{
		return campoExclusivoCliente;
	}




	public void setCampoExclusivoCliente(Campo campoExclusivoCliente)
	{
		this.campoExclusivoCliente = campoExclusivoCliente;
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
