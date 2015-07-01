package mvcapital.bradesco.cnab.cheque.retornoquinzenal.host;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Detail extends Register
{
	private Campo id = new Campo();
	private Campo dataDeVencimento = new Campo();
	private Campo numeroDoBordero = new Campo();
	private Campo numeroDoLote = new Campo();
	private Campo numeroDoBanco = new Campo();
	private Campo agencia = new Campo();
	private Campo contaCorrente = new Campo();
	private Campo numeroDoCheque = new Campo();
	private Campo valor = new Campo();
	private Campo status = new Campo();
	private Campo espacos = new Campo();
	private Campo sequenciaInterna = new Campo();



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
		this.numeroDoBanco = new Campo(5,24,26,3,true,Register.campoNumerico,0,"");
		this.agencia = new Campo(6,27,30,4,true,Register.campoNumerico,0,"");
		this.contaCorrente = new Campo(7,31,40,10,true,Register.campoNumerico,0,"");
		this.numeroDoCheque = new Campo(8,41,46,6,true,Register.campoAlfaNumerico,0,"");
		this.valor = new Campo(9,47,61,15,true,Register.campoAlfaNumerico,0,"");
		this.status = new Campo(10,62,62,1,true,Register.campoAlfaNumerico,0,"");
		this.espacos = new Campo(11,63,80,18,true,Register.campoAlfaNumerico,0,"");
		this.sequenciaInterna = new Campo(12,81,90,10,true,Register.campoNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(13,91,100,10,true,Register.campoNumerico,0,"");
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



	public Campo getStatus()
	{
		return status;
	}



	public void setStatus(Campo status)
	{
		this.status = status;
	}



	public Campo getEspacos()
	{
		return espacos;
	}



	public void setEspacos(Campo espacos)
	{
		this.espacos = espacos;
	}



	public Campo getSequenciaInterna()
	{
		return sequenciaInterna;
	}



	public void setSequenciaInterna(Campo sequenciaInterna)
	{
		this.sequenciaInterna = sequenciaInterna;
	}
}
