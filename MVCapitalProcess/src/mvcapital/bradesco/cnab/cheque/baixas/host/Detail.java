package mvcapital.bradesco.cnab.cheque.baixas.host;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Detail extends Register
{
	private Campo codigoDeCompensacao = new Campo();
	private Campo banco = new Campo();
	private Campo agencia = new Campo();
	private Campo c2 = new Campo();
	private Campo contaCorrente = new Campo();
	private Campo c1 = new Campo();
	private Campo numeroDoCheque = new Campo();
	private Campo c3 = new Campo();
	private Campo uf = new Campo();
	private Campo valor = new Campo();
	private Campo tipif = new Campo();
	private Campo td = new Campo();
	private Campo espacos = new Campo();
	private Campo bancoRemetente = new Campo();
	private Campo agenciaRemetente = new Campo();
	private Campo agenciaDepositante = new Campo();
	private Campo contaDepositante = new Campo();
	private Campo compensacaoAcolhida = new Campo();
	private Campo dataDeMovimento = new Campo();
	private Campo numeroDoLote = new Campo();
	private Campo sequenciaDoLote = new Campo();
	private Campo espacos2 = new Campo();

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
		this.codigoDeCompensacao = new Campo(1,1,3,3,true,Register.campoNumerico,0,"");
		this.banco = new Campo(2,4,6,3,true,Register.campoNumerico,0,"");
		this.agencia = new Campo(3,7,10,4,true,Register.campoNumerico,0,"");
		this.c2 = new Campo(4,11,11,1,true,Register.campoAlfaNumerico,0,"");
		this.contaCorrente = new Campo(5,12,23,12,true,Register.campoAlfaNumerico,0,"");
		this.c1 = new Campo(6,24,24,1,true,Register.campoNumerico,0,"");
		this.numeroDoCheque = new Campo(7,25,30,6,true,Register.campoNumerico,0,"");
		this.c3 = new Campo(8,31,31,1,true,Register.campoAlfaNumerico,0,"");
		this.uf = new Campo(9,32,33,2,true,Register.campoAlfaNumerico,0,"");
		this.valor = new Campo(10,34,50,17,true,Register.campoNumerico,0,"");
		this.tipif = new Campo(11,51,51,1,true,Register.campoNumerico,0,"");
		this.td = new Campo(12,52,53,2,true,Register.campoNumerico,0,"");
		this.espacos = new Campo(13,54,55,2,true,Register.campoAlfaNumerico,0,"");
		this.bancoRemetente = new Campo(14,56,58,3,true,Register.campoNumerico,0,"");
		this.agenciaRemetente = new Campo(15,59,62,4,true,Register.campoNumerico,0,"");
		this.agenciaDepositante = new Campo(16,63,66,4,true,Register.campoNumerico,0,"");
		this.contaDepositante = new Campo(17,67,78,12,true,Register.campoNumerico,0,"");
		this.compensacaoAcolhida = new Campo(18,79,81,3,true,Register.campoNumerico,0,"");
		this.dataDeMovimento = new Campo(19,82,89,8,true,Register.campoAlfaNumerico,0,"");
		this.numeroDoLote = new Campo(20,90,96,7,true,Register.campoNumerico,0,"");
		this.sequenciaDoLote = new Campo(21,97,99,3,true,Register.campoNumerico,0,"");
		this.espacos2 = new Campo(22,100,150,51,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(23,151,160,10,true,Register.campoNumerico,0,"");
	}

	public Campo getCodigoDeCompensacao()
	{
		return codigoDeCompensacao;
	}

	public void setCodigoDeCompensacao(Campo codigoDeCompensacao)
	{
		this.codigoDeCompensacao = codigoDeCompensacao;
	}

	public Campo getBanco()
	{
		return banco;
	}

	public void setBanco(Campo banco)
	{
		this.banco = banco;
	}

	public Campo getAgencia()
	{
		return agencia;
	}

	public void setAgencia(Campo agencia)
	{
		this.agencia = agencia;
	}

	public Campo getC2()
	{
		return c2;
	}

	public void setC2(Campo c2)
	{
		this.c2 = c2;
	}

	public Campo getContaCorrente()
	{
		return contaCorrente;
	}

	public void setContaCorrente(Campo contaCorrente)
	{
		this.contaCorrente = contaCorrente;
	}

	public Campo getC1()
	{
		return c1;
	}

	public void setC1(Campo c1)
	{
		this.c1 = c1;
	}

	public Campo getNumeroDoCheque()
	{
		return numeroDoCheque;
	}

	public void setNumeroDoCheque(Campo numeroDoCheque)
	{
		this.numeroDoCheque = numeroDoCheque;
	}

	public Campo getC3()
	{
		return c3;
	}

	public void setC3(Campo c3)
	{
		this.c3 = c3;
	}

	public Campo getUf()
	{
		return uf;
	}

	public void setUf(Campo uf)
	{
		this.uf = uf;
	}

	public Campo getValor()
	{
		return valor;
	}

	public void setValor(Campo valor)
	{
		this.valor = valor;
	}

	public Campo getTipif()
	{
		return tipif;
	}

	public void setTipif(Campo tipif)
	{
		this.tipif = tipif;
	}

	public Campo getTd()
	{
		return td;
	}

	public void setTd(Campo td)
	{
		this.td = td;
	}

	public Campo getEspacos()
	{
		return espacos;
	}

	public void setEspacos(Campo espacos)
	{
		this.espacos = espacos;
	}

	public Campo getBancoRemetente()
	{
		return bancoRemetente;
	}

	public void setBancoRemetente(Campo bancoRemetente)
	{
		this.bancoRemetente = bancoRemetente;
	}

	public Campo getAgenciaRemetente()
	{
		return agenciaRemetente;
	}

	public void setAgenciaRemetente(Campo agenciaRemetente)
	{
		this.agenciaRemetente = agenciaRemetente;
	}

	public Campo getAgenciaDepositante()
	{
		return agenciaDepositante;
	}

	public void setAgenciaDepositante(Campo agenciaDepositante)
	{
		this.agenciaDepositante = agenciaDepositante;
	}

	public Campo getContaDepositante()
	{
		return contaDepositante;
	}

	public void setContaDepositante(Campo contaDepositante)
	{
		this.contaDepositante = contaDepositante;
	}

	public Campo getCompensacaoAcolhida()
	{
		return compensacaoAcolhida;
	}

	public void setCompensacaoAcolhida(Campo compensacaoAcolhida)
	{
		this.compensacaoAcolhida = compensacaoAcolhida;
	}

	public Campo getDataDeMovimento()
	{
		return dataDeMovimento;
	}

	public void setDataDeMovimento(Campo dataDeMovimento)
	{
		this.dataDeMovimento = dataDeMovimento;
	}

	public Campo getNumeroDoLote()
	{
		return numeroDoLote;
	}

	public void setNumeroDoLote(Campo numeroDoLote)
	{
		this.numeroDoLote = numeroDoLote;
	}

	public Campo getSequenciaDoLote()
	{
		return sequenciaDoLote;
	}

	public void setSequenciaDoLote(Campo sequenciaDoLote)
	{
		this.sequenciaDoLote = sequenciaDoLote;
	}

	public Campo getEspacos2()
	{
		return espacos2;
	}

	public void setEspacos2(Campo espacos2)
	{
		this.espacos2 = espacos2;
	}
}
