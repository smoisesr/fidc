package mvcapital.bradesco.cnab.cobranca.retorno;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Trailler extends Register
{
	private Campo identificacaoRetorno=new Campo();
	private Campo identificacaoTipoRegistro=new Campo();
	private Campo codigoBanco=new Campo();
	private Campo branco=new Campo();
	private Campo quantidadeTitulosCobranca=new Campo();
	private Campo valorTotalCobranca=new Campo();
	private Campo numeroAvisoBancario=new Campo();
	private Campo branco1=new Campo();
	private Campo quantidadeRegistrosOcorrencia02=new Campo();
	private Campo valorRegistrosOcorrencia02=new Campo();
	private Campo valorRegistrosOcorrencia06Liquidacao=new Campo();
	private Campo quantidadeRegistrosOcorrencia06Liquidacao=new Campo();
	private Campo quantidadeRegistrosOcorrencia06=new Campo();
	private Campo quantidadeRegistrosOcorrencia09e10=new Campo();
	private Campo valorRegistrosOcorrencia09e10=new Campo();
	private Campo quantidadeRegistrosOcorrencia13=new Campo();
	private Campo valorRegistrosOcorrencia13=new Campo();
	private Campo quantidadeRegistrosOcorrencia14=new Campo();
	private Campo valorRegistrosOcorrencia14=new Campo();
	private Campo quantidadeRegistrosOcorrencia12=new Campo();
	private Campo valorRegistrosOcorrencia12=new Campo();
	private Campo quantidadeRegistrosOcorrencia19=new Campo();
	private Campo valorRegistrosOcorrencia19=new Campo();
	private Campo branco2=new Campo();
	private Campo valorTotalRateiosEfefuados=new Campo();
	private Campo quantidadeTotalRateiosEfefuados=new Campo();
	private Campo branco3=new Campo();
	
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
		this.identificacaoRegistro.setConteudo("9"); //$NON-NLS-1$
		this.identificacaoRetorno=new Campo(2,2,2,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.identificacaoTipoRegistro=new Campo(3,3,4,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.codigoBanco=new Campo(4,5,7,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.branco=new Campo(5,8,17,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.quantidadeTitulosCobranca=new Campo(6,18,25,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.valorTotalCobranca=new Campo(7,26,39,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.numeroAvisoBancario=new Campo(8,40,47,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.branco1=new Campo(9,48,57,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.quantidadeRegistrosOcorrencia02=new Campo(10,58,62,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.valorRegistrosOcorrencia02=new Campo(11,63,74,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.valorRegistrosOcorrencia06Liquidacao=new Campo(12,75,86,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.quantidadeRegistrosOcorrencia06Liquidacao=new Campo(13,87,91,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.quantidadeRegistrosOcorrencia06=new Campo(14,92,103,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.quantidadeRegistrosOcorrencia09e10=new Campo(15,104,108,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.valorRegistrosOcorrencia09e10=new Campo(16,109,120,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.quantidadeRegistrosOcorrencia13=new Campo(17,121,125,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.valorRegistrosOcorrencia13=new Campo(18,126,137,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.quantidadeRegistrosOcorrencia14=new Campo(19,138,142,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.valorRegistrosOcorrencia14=new Campo(20,143,154,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.quantidadeRegistrosOcorrencia12=new Campo(21,155,159,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.valorRegistrosOcorrencia12=new Campo(22,160,171,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.quantidadeRegistrosOcorrencia19=new Campo(23,172,176,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.valorRegistrosOcorrencia19=new Campo(24,177,188,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.branco2=new Campo(25,189,362,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.valorTotalRateiosEfefuados=new Campo(26,363,377,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.quantidadeTotalRateiosEfefuados=new Campo(27,378,385,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.branco3=new Campo(28,386,394,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.numeroSequencialRegistro=new Campo(29,395,400,true,Register.campoNumerico,0,"");		 //$NON-NLS-1$
	}

	public Campo getIdentificacaoRetorno()
	{
		return this.identificacaoRetorno;
	}

	public void setIdentificacaoRetorno(Campo identificacaoRetorno)
	{
		this.identificacaoRetorno = identificacaoRetorno;
	}

	public Campo getIdentificacaoTipoRegistro()
	{
		return this.identificacaoTipoRegistro;
	}

	public void setIdentificacaoTipoRegistro(Campo identificacaoTipoRegistro)
	{
		this.identificacaoTipoRegistro = identificacaoTipoRegistro;
	}

	public Campo getCodigoBanco()
	{
		return this.codigoBanco;
	}

	public void setCodigoBanco(Campo codigoBanco)
	{
		this.codigoBanco = codigoBanco;
	}

	public Campo getBranco()
	{
		return this.branco;
	}

	public void setBranco(Campo branco)
	{
		this.branco = branco;
	}

	public Campo getQuantidadeTitulosCobranca()
	{
		return this.quantidadeTitulosCobranca;
	}

	public void setQuantidadeTitulosCobranca(Campo quantidadeTitulosCobranca)
	{
		this.quantidadeTitulosCobranca = quantidadeTitulosCobranca;
	}

	public Campo getValorTotalCobranca()
	{
		return this.valorTotalCobranca;
	}

	public void setValorTotalCobranca(Campo valorTotalCobranca)
	{
		this.valorTotalCobranca = valorTotalCobranca;
	}

	public Campo getNumeroAvisoBancario()
	{
		return this.numeroAvisoBancario;
	}

	public void setNumeroAvisoBancario(Campo numeroAvisoBancario)
	{
		this.numeroAvisoBancario = numeroAvisoBancario;
	}

	public Campo getBranco1()
	{
		return this.branco1;
	}

	public void setBranco1(Campo branco1)
	{
		this.branco1 = branco1;
	}

	public Campo getQuantidadeRegistrosOcorrencia02()
	{
		return this.quantidadeRegistrosOcorrencia02;
	}

	public void setQuantidadeRegistrosOcorrencia02(
			Campo quantidadeRegistrosOcorrencia02)
	{
		this.quantidadeRegistrosOcorrencia02 = quantidadeRegistrosOcorrencia02;
	}

	public Campo getValorRegistrosOcorrencia02()
	{
		return this.valorRegistrosOcorrencia02;
	}

	public void setValorRegistrosOcorrencia02(Campo valorRegistrosOcorrencia02)
	{
		this.valorRegistrosOcorrencia02 = valorRegistrosOcorrencia02;
	}

	public Campo getValorRegistrosOcorrencia06Liquidacao()
	{
		return this.valorRegistrosOcorrencia06Liquidacao;
	}

	public void setValorRegistrosOcorrencia06Liquidacao(
			Campo valorRegistrosOcorrencia06Liquidacao)
	{
		this.valorRegistrosOcorrencia06Liquidacao = valorRegistrosOcorrencia06Liquidacao;
	}

	public Campo getQuantidadeRegistrosOcorrencia06Liquidacao()
	{
		return this.quantidadeRegistrosOcorrencia06Liquidacao;
	}

	public void setQuantidadeRegistrosOcorrencia06Liquidacao(
			Campo quantidadeRegistrosOcorrencia06Liquidacao)
	{
		this.quantidadeRegistrosOcorrencia06Liquidacao = quantidadeRegistrosOcorrencia06Liquidacao;
	}

	public Campo getQuantidadeRegistrosOcorrencia06()
	{
		return this.quantidadeRegistrosOcorrencia06;
	}

	public void setQuantidadeRegistrosOcorrencia06(
			Campo quantidadeRegistrosOcorrencia06)
	{
		this.quantidadeRegistrosOcorrencia06 = quantidadeRegistrosOcorrencia06;
	}

	public Campo getQuantidadeRegistrosOcorrencia09e10()
	{
		return this.quantidadeRegistrosOcorrencia09e10;
	}

	public void setQuantidadeRegistrosOcorrencia09e10(
			Campo quantidadeRegistrosOcorrencia09e10)
	{
		this.quantidadeRegistrosOcorrencia09e10 = quantidadeRegistrosOcorrencia09e10;
	}

	public Campo getValorRegistrosOcorrencia09e10()
	{
		return this.valorRegistrosOcorrencia09e10;
	}

	public void setValorRegistrosOcorrencia09e10(Campo valorRegistrosOcorrencia09e10)
	{
		this.valorRegistrosOcorrencia09e10 = valorRegistrosOcorrencia09e10;
	}

	public Campo getQuantidadeRegistrosOcorrencia13()
	{
		return this.quantidadeRegistrosOcorrencia13;
	}

	public void setQuantidadeRegistrosOcorrencia13(
			Campo quantidadeRegistrosOcorrencia13)
	{
		this.quantidadeRegistrosOcorrencia13 = quantidadeRegistrosOcorrencia13;
	}

	public Campo getValorRegistrosOcorrencia13()
	{
		return this.valorRegistrosOcorrencia13;
	}

	public void setValorRegistrosOcorrencia13(Campo valorRegistrosOcorrencia13)
	{
		this.valorRegistrosOcorrencia13 = valorRegistrosOcorrencia13;
	}

	public Campo getQuantidadeRegistrosOcorrencia14()
	{
		return this.quantidadeRegistrosOcorrencia14;
	}

	public void setQuantidadeRegistrosOcorrencia14(
			Campo quantidadeRegistrosOcorrencia14)
	{
		this.quantidadeRegistrosOcorrencia14 = quantidadeRegistrosOcorrencia14;
	}

	public Campo getValorRegistrosOcorrencia14()
	{
		return this.valorRegistrosOcorrencia14;
	}

	public void setValorRegistrosOcorrencia14(Campo valorRegistrosOcorrencia14)
	{
		this.valorRegistrosOcorrencia14 = valorRegistrosOcorrencia14;
	}

	public Campo getQuantidadeRegistrosOcorrencia12()
	{
		return this.quantidadeRegistrosOcorrencia12;
	}

	public void setQuantidadeRegistrosOcorrencia12(
			Campo quantidadeRegistrosOcorrencia12)
	{
		this.quantidadeRegistrosOcorrencia12 = quantidadeRegistrosOcorrencia12;
	}

	public Campo getValorRegistrosOcorrencia12()
	{
		return this.valorRegistrosOcorrencia12;
	}

	public void setValorRegistrosOcorrencia12(Campo valorRegistrosOcorrencia12)
	{
		this.valorRegistrosOcorrencia12 = valorRegistrosOcorrencia12;
	}

	public Campo getQuantidadeRegistrosOcorrencia19()
	{
		return this.quantidadeRegistrosOcorrencia19;
	}

	public void setQuantidadeRegistrosOcorrencia19(
			Campo quantidadeRegistrosOcorrencia19)
	{
		this.quantidadeRegistrosOcorrencia19 = quantidadeRegistrosOcorrencia19;
	}

	public Campo getValorRegistrosOcorrencia19()
	{
		return this.valorRegistrosOcorrencia19;
	}

	public void setValorRegistrosOcorrencia19(Campo valorRegistrosOcorrencia19)
	{
		this.valorRegistrosOcorrencia19 = valorRegistrosOcorrencia19;
	}

	public Campo getBranco2()
	{
		return this.branco2;
	}

	public void setBranco2(Campo branco2)
	{
		this.branco2 = branco2;
	}

	public Campo getValorTotalRateiosEfefuados()
	{
		return this.valorTotalRateiosEfefuados;
	}

	public void setValorTotalRateiosEfefuados(Campo valorTotalRateiosEfefuados)
	{
		this.valorTotalRateiosEfefuados = valorTotalRateiosEfefuados;
	}

	public Campo getQuantidadeTotalRateiosEfefuados()
	{
		return this.quantidadeTotalRateiosEfefuados;
	}

	public void setQuantidadeTotalRateiosEfefuados(
			Campo quantidadeTotalRateiosEfefuados)
	{
		this.quantidadeTotalRateiosEfefuados = quantidadeTotalRateiosEfefuados;
	}

	public Campo getBranco3()
	{
		return this.branco3;
	}

	public void setBranco3(Campo branco3)
	{
		this.branco3 = branco3;
	}

}
