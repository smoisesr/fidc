package mvcapital.bradesco.cnab.cheque.devolucao.host;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Detail extends Register
{
	private Campo codigoCompensacao = new Campo();
	private Campo banco = new Campo();
	private Campo agencia = new Campo();
	private Campo ctrDv2 = new Campo();
	private Campo conta = new Campo();
	private Campo ctrDv1 = new Campo();
	private Campo numeroDoCheque = new Campo();
	private Campo ctrDv3 = new Campo();
	private Campo uf = new Campo();
	private Campo valor = new Campo();
	private Campo tipificacao = new Campo();
	private Campo td = new Campo();
	private Campo filler = new Campo();
	private Campo bancoRemetente = new Campo();
	private Campo agenciaRemetente = new Campo();
	private Campo agenciaDepositante = new Campo();
	private Campo contaDepositante = new Campo();
	private Campo compensacaoAcolhida = new Campo();
	private Campo dataMovimento = new Campo();
	private Campo numeroDoLote = new Campo();
	private Campo sequenciaDoLote = new Campo();
	private Campo cpfCnpj = new Campo();
	private Campo filial = new Campo();
	private Campo digitoCpfCnpj = new Campo();
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
		this.codigoCompensacao = new Campo(1,1,3,3,true,Register.campoNumerico,0,"");
		this.banco = new Campo(2,4,6,3,true,Register.campoNumerico,0,"");
		this.agencia = new Campo(3,7,10,4,true,Register.campoNumerico,0,"");
		this.ctrDv2 = new Campo(4,11,11,1,true,Register.campoNumerico,0,"");
		this.conta = new Campo(5,12,23,12,true,Register.campoNumerico,0,"");
		this.ctrDv1 = new Campo(6,24,24,1,true,Register.campoNumerico,0,"");
		this.numeroDoCheque = new Campo(7,25,30,6,true,Register.campoNumerico,0,"");
		this.ctrDv3 = new Campo(8,31,31,1,true,Register.campoNumerico,0,"");
		this.uf = new Campo(9,32,33,2,true,Register.campoAlfaNumerico,0,"");
		this.valor = new Campo(10,34,50,17,true,Register.campoNumerico,0,"");
		this.tipificacao = new Campo(11,51,51,1,true,Register.campoNumerico,0,"");
		this.td = new Campo(12,52,53,2,true,Register.campoNumerico,0,"");
		this.filler = new Campo(13,54,55,2,true,Register.campoAlfaNumerico,0,"");
		this.bancoRemetente = new Campo(14,56,58,3,true,Register.campoNumerico,0,"");
		this.agenciaRemetente = new Campo(15,59,62,4,true,Register.campoNumerico,0,"");
		this.agenciaDepositante = new Campo(16,63,66,4,true,Register.campoNumerico,0,"");
		this.contaDepositante = new Campo(17,67,78,12,true,Register.campoNumerico,0,"");
		this.compensacaoAcolhida = new Campo(18,79,81,3,true,Register.campoNumerico,0,"");
		this.dataMovimento = new Campo(19,82,89,8,true,Register.campoNumerico,0,"");
		this.numeroDoLote = new Campo(20,90,96,7,true,Register.campoNumerico,0,"");
		this.sequenciaDoLote = new Campo(21,97,99,3,true,Register.campoNumerico,0,"");
		this.cpfCnpj = new Campo(22,100,108,9,true,Register.campoNumerico,0,"");
		this.filial = new Campo(23,109,112,4,true,Register.campoNumerico,0,"");
		this.digitoCpfCnpj = new Campo(24,113,114,2,true,Register.campoNumerico,0,"");
		this.filler2 = new Campo(25,115,150,36,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(26,151,160,10,true,Register.campoNumerico,0,"");
	}
	public Campo getCodigoCompensacao()
	{
		return codigoCompensacao;
	}
	public void setCodigoCompensacao(Campo codigoCompensacao)
	{
		this.codigoCompensacao = codigoCompensacao;
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
	public Campo getCtrDv2()
	{
		return ctrDv2;
	}
	public void setCtrDv2(Campo ctrDv2)
	{
		this.ctrDv2 = ctrDv2;
	}
	public Campo getConta()
	{
		return conta;
	}
	public void setConta(Campo conta)
	{
		this.conta = conta;
	}
	public Campo getCtrDv1()
	{
		return ctrDv1;
	}
	public void setCtrDv1(Campo ctrDv1)
	{
		this.ctrDv1 = ctrDv1;
	}
	public Campo getNumeroDoCheque()
	{
		return numeroDoCheque;
	}
	public void setNumeroDoCheque(Campo numeroDoCheque)
	{
		this.numeroDoCheque = numeroDoCheque;
	}
	public Campo getCtrDv3()
	{
		return ctrDv3;
	}
	public void setCtrDv3(Campo ctrDv3)
	{
		this.ctrDv3 = ctrDv3;
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
	public Campo getTipificacao()
	{
		return tipificacao;
	}
	public void setTipificacao(Campo tipificacao)
	{
		this.tipificacao = tipificacao;
	}
	public Campo getTd()
	{
		return td;
	}
	public void setTd(Campo td)
	{
		this.td = td;
	}
	public Campo getFiller()
	{
		return filler;
	}
	public void setFiller(Campo filler)
	{
		this.filler = filler;
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
	public Campo getDataMovimento()
	{
		return dataMovimento;
	}
	public void setDataMovimento(Campo dataMovimento)
	{
		this.dataMovimento = dataMovimento;
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
	public Campo getCpfCnpj()
	{
		return cpfCnpj;
	}
	public void setCpfCnpj(Campo cpfCnpj)
	{
		this.cpfCnpj = cpfCnpj;
	}
	public Campo getFilial()
	{
		return filial;
	}
	public void setFilial(Campo filial)
	{
		this.filial = filial;
	}
	public Campo getDigitoCpfCnpj()
	{
		return digitoCpfCnpj;
	}
	public void setDigitoCpfCnpj(Campo digitoCpfCnpj)
	{
		this.digitoCpfCnpj = digitoCpfCnpj;
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
