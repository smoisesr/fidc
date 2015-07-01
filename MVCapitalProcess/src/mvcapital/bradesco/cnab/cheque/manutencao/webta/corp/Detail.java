package mvcapital.bradesco.cnab.cheque.manutencao.webta.corp;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Detail extends Register
{
	private Campo id = new Campo();
	private Campo dataDeVencimento = new Campo();
	private Campo numeroDoBordero = new Campo();
	private Campo numeroDoLote = new Campo();
	private Campo camaraDestino = new Campo();
	private Campo numeroDoBanco = new Campo();
	private Campo agencia = new Campo();
	private Campo contaCorrente = new Campo();
	private Campo numeroDoCheque = new Campo();
	private Campo valor = new Campo();
	private Campo tipoDaSolicitacao = new Campo();
	private Campo cpfCnpj = new Campo();
	private Campo campoExclusivoCliente = new Campo();
	private Campo filler = new Campo();
	private Campo statusDoAtendimento = new Campo();
	
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
		this.dataDeVencimento = new Campo(2,2,9,8,true,Register.campoNumerico,0,"");
		this.numeroDoBordero = new Campo(3,10,16,7,true,Register.campoNumerico,0,"");
		this.numeroDoLote = new Campo(4,17,23,7,true,Register.campoNumerico,0,"");
		this.camaraDestino = new Campo(5,24,26,3,true,Register.campoNumerico,0,"");
		this.numeroDoBanco = new Campo(6,27,29,3,true,Register.campoNumerico,0,"");
		this.agencia = new Campo(7,30,33,4,true,Register.campoNumerico,0,"");
		this.contaCorrente = new Campo(8,34,43,10,true,Register.campoNumerico,0,"");
		this.numeroDoCheque = new Campo(9,44,49,6,true,Register.campoNumerico,0,"");
		this.valor = new Campo(10,50,64,15,true,Register.campoNumerico,0,"");
		this.tipoDaSolicitacao = new Campo(11,65,65,1,true,Register.campoNumerico,0,"");
		this.cpfCnpj = new Campo(12,66,80,15,true,Register.campoNumerico,0,"");
		this.campoExclusivoCliente = new Campo(13,81,105,25,true,Register.campoNumerico,0,"");
		this.filler = new Campo(14,106,127,22,true,Register.campoNumerico,0,"");
		this.statusDoAtendimento = new Campo(15,128,130,3,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(16,131,140,10,true,Register.campoNumerico,0,"");
	}

	public Campo getId()
	{
		return id;
	}

	public void setId(Campo id)
	{
		this.id = id;
	}

	public Campo getDataDeVencimento()
	{
		return dataDeVencimento;
	}

	public void setDataDeVencimento(Campo dataDeVencimento)
	{
		this.dataDeVencimento = dataDeVencimento;
	}

	public Campo getNumeroDoBordero()
	{
		return numeroDoBordero;
	}

	public void setNumeroDoBordero(Campo numeroDoBordero)
	{
		this.numeroDoBordero = numeroDoBordero;
	}

	public Campo getNumeroDoLote()
	{
		return numeroDoLote;
	}

	public void setNumeroDoLote(Campo numeroDoLote)
	{
		this.numeroDoLote = numeroDoLote;
	}

	public Campo getCamaraDestino()
	{
		return camaraDestino;
	}

	public void setCamaraDestino(Campo camaraDestino)
	{
		this.camaraDestino = camaraDestino;
	}

	public Campo getNumeroDoBanco()
	{
		return numeroDoBanco;
	}

	public void setNumeroDoBanco(Campo numeroDoBanco)
	{
		this.numeroDoBanco = numeroDoBanco;
	}

	public Campo getAgencia()
	{
		return agencia;
	}

	public void setAgencia(Campo agencia)
	{
		this.agencia = agencia;
	}

	public Campo getContaCorrente()
	{
		return contaCorrente;
	}

	public void setContaCorrente(Campo contaCorrente)
	{
		this.contaCorrente = contaCorrente;
	}

	public Campo getNumeroDoCheque()
	{
		return numeroDoCheque;
	}

	public void setNumeroDoCheque(Campo numeroDoCheque)
	{
		this.numeroDoCheque = numeroDoCheque;
	}

	public Campo getValor()
	{
		return valor;
	}

	public void setValor(Campo valor)
	{
		this.valor = valor;
	}

	public Campo getTipoDaSolicitacao()
	{
		return tipoDaSolicitacao;
	}

	public void setTipoDaSolicitacao(Campo tipoDaSolicitacao)
	{
		this.tipoDaSolicitacao = tipoDaSolicitacao;
	}

	public Campo getCpfCnpj()
	{
		return cpfCnpj;
	}

	public void setCpfCnpj(Campo cpfCnpj)
	{
		this.cpfCnpj = cpfCnpj;
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

	public Campo getStatusDoAtendimento()
	{
		return statusDoAtendimento;
	}

	public void setStatusDoAtendimento(Campo statusDoAtendimento)
	{
		this.statusDoAtendimento = statusDoAtendimento;
	}
}
