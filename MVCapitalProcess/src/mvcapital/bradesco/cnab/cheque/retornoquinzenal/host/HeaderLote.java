package mvcapital.bradesco.cnab.cheque.retornoquinzenal.host;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class HeaderLote extends Register
{
	private Campo id = new Campo();
	private Campo agencia = new Campo();
	private Campo contaCorrente = new Campo();
	private Campo filial = new Campo();
	private Campo quantidadeDeCheques = new Campo();
	private Campo valorTotalDeCheques = new Campo();
	private Campo espacos = new Campo();
	private Campo sequenciaInterna = new Campo();

	public HeaderLote() 
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
		this.agencia = new Campo(2,2,5,4,true,Register.campoNumerico,0,"");
		this.contaCorrente = new Campo(3,6,15,10,true,Register.campoNumerico,0,"");
		this.filial = new Campo(4,16,19,4,true,Register.campoNumerico,0,"");
		this.quantidadeDeCheques = new Campo(5,20,25,6,true,Register.campoNumerico,0,"");
		this.valorTotalDeCheques = new Campo(6,26,40,15,true,Register.campoNumerico,0,"");
		this.espacos = new Campo(7,41,80,40,true,Register.campoAlfaNumerico,0,"");
		this.sequenciaInterna = new Campo(8,81,90,10,true,Register.campoNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(9,91,100,10,true,Register.campoNumerico,0,"");
	}

	public Campo getId()
	{
		return id;
	}

	public void setId(Campo id)
	{
		this.id = id;
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

	public Campo getFilial()
	{
		return filial;
	}

	public void setFilial(Campo filial)
	{
		this.filial = filial;
	}

	public Campo getQuantidadeDeCheques()
	{
		return quantidadeDeCheques;
	}

	public void setQuantidadeDeCheques(Campo quantidadeDeCheques)
	{
		this.quantidadeDeCheques = quantidadeDeCheques;
	}

	public Campo getValorTotalDeCheques()
	{
		return valorTotalDeCheques;
	}

	public void setValorTotalDeCheques(Campo valorTotalDeCheques)
	{
		this.valorTotalDeCheques = valorTotalDeCheques;
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
