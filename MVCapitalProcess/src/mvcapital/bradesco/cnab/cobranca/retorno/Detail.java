package mvcapital.bradesco.cnab.cobranca.retorno;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Detail extends Register
{
	private Campo tipoInscricaoEmpresa=new Campo();	
	private Campo numeroInscricaoEmpresa=new Campo();
	private Campo zeros=new Campo();
	private Campo identiFicacaoEmpresaBeneficiarioNoBanco=new Campo();
	private Campo numeroControleParticipante=new Campo();
	private Campo zeros1=new Campo();
	private Campo identificacaoTituloBanco=new Campo();
	private Campo usoDoBanco=new Campo();
	private Campo usoDoBanco1=new Campo();
	private Campo indicadorRateioCredito=new Campo();
	private Campo zeros2=new Campo();
	private Campo carteira=new Campo();
	private Campo identificacaoOcorrencia=new Campo();
	private Campo dataOcorrenciaBanco=new Campo();
	private Campo numeroDocumento=new Campo();
	private Campo identificacaoTituloBanco2=new Campo();
	private Campo dataVencimentoTitulo=new Campo();
	private Campo valorTitulo=new Campo();
	private Campo bancoCobrador=new Campo();
	private Campo agenciaCobradora=new Campo();
	private Campo especieTitulo=new Campo();
	private Campo despesasCobranca=new Campo();
	private Campo outrasDespesas=new Campo();
	private Campo jurosOperacaoAtraso=new Campo();
	private Campo iofDevido=new Campo();
	private Campo abatimentoConcedidoTitulo=new Campo();
	private Campo descontoConcedido=new Campo();
	private Campo valorPago=new Campo();
	private Campo jurosMora=new Campo();
	private Campo outrosCreditos=new Campo();
	private Campo brancos=new Campo();
	private Campo motivoCodigoOcorrencia=new Campo();
	private Campo dataCredito=new Campo();
	private Campo origemPagamento=new Campo();
	private Campo brancos1=new Campo();
	private Campo quandoChequeCodigoBanco=new Campo();
	private Campo motivosRejeicoes=new Campo();
	private Campo brancos2=new Campo();
	private Campo numeroCartorio=new Campo();
	private Campo numeroProtocolo=new Campo();
	private Campo brancos3=new Campo();
		
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
		this.identificacaoRegistro.setConteudo("1"); //$NON-NLS-1$
		this.tipoInscricaoEmpresa=new Campo(2,2,3,2,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.numeroInscricaoEmpresa=new Campo(3,4,17,14,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.zeros=new Campo(4,18,20,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.identiFicacaoEmpresaBeneficiarioNoBanco=new Campo(5,21,37,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.numeroControleParticipante=new Campo(6,38,62,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.zeros1=new Campo(7,63,70,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.identificacaoTituloBanco=new Campo(8,71,82,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.usoDoBanco=new Campo(9,83,92,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.usoDoBanco1=new Campo(10,93,104,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.indicadorRateioCredito=new Campo(11,105,105,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.zeros2=new Campo(12,106,107,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.carteira=new Campo(13,108,108,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.identificacaoOcorrencia=new Campo(14,109,110,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.dataOcorrenciaBanco=new Campo(15,111,116,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.numeroDocumento=new Campo(16,117,126,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.identificacaoTituloBanco2=new Campo(17,127,146,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.dataVencimentoTitulo=new Campo(18,147,152,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.valorTitulo=new Campo(19,153,165,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.bancoCobrador=new Campo(20,166,168,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.agenciaCobradora=new Campo(21,169,173,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.especieTitulo=new Campo(22,174,175,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.despesasCobranca=new Campo(23,176,188,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.outrasDespesas=new Campo(24,189,201,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.jurosOperacaoAtraso=new Campo(25,202,214,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.iofDevido=new Campo(26,215,227,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.abatimentoConcedidoTitulo=new Campo(27,228,240,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.descontoConcedido=new Campo(28,241,253,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.valorPago=new Campo(29,254,266,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.jurosMora=new Campo(30,267,279,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.outrosCreditos=new Campo(31,280,292,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.brancos=new Campo(32,293,294,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.motivoCodigoOcorrencia=new Campo(33,295,295,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.dataCredito=new Campo(34,296,301,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.origemPagamento=new Campo(35,302,304,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.brancos1=new Campo(36,305,314,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.quandoChequeCodigoBanco=new Campo(37,315,318,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.motivosRejeicoes=new Campo(38,319,328,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.brancos2=new Campo(39,329,368,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.numeroCartorio=new Campo(40,369,370,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.numeroProtocolo=new Campo(41,371,380,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.brancos3=new Campo(42,381,394,false,Register.campoAlfaNumerico,0,"");		 //$NON-NLS-1$
		this.numeroSequencialRegistro=new Campo(49,395,400,6,true,Register.campoNumerico,0,""); //$NON-NLS-1$
	}
	
	public Detail(String line)
	{
		this();
		this.tipoInscricaoEmpresa.setConteudo(line.substring(this.tipoInscricaoEmpresa.getPosicaoInicial()-1,this.tipoInscricaoEmpresa.getPosicaoFinal()));
		this.numeroInscricaoEmpresa.setConteudo(line.substring(this.numeroInscricaoEmpresa.getPosicaoInicial()-1,this.numeroInscricaoEmpresa.getPosicaoFinal()));
		this.zeros.setConteudo(line.substring(this.zeros.getPosicaoInicial()-1,this.zeros.getPosicaoFinal()));
		this.identiFicacaoEmpresaBeneficiarioNoBanco.setConteudo(line.substring(this.identiFicacaoEmpresaBeneficiarioNoBanco.getPosicaoInicial()-1,this.identiFicacaoEmpresaBeneficiarioNoBanco.getPosicaoFinal()));
		this.numeroControleParticipante.setConteudo(line.substring(this.numeroControleParticipante.getPosicaoInicial()-1,this.numeroControleParticipante.getPosicaoFinal()));
		this.zeros1.setConteudo(line.substring(this.zeros1.getPosicaoInicial()-1,this.zeros1.getPosicaoFinal()));
		this.identificacaoTituloBanco.setConteudo(line.substring(this.identificacaoTituloBanco.getPosicaoInicial()-1,this.identificacaoTituloBanco.getPosicaoFinal()));
		this.usoDoBanco.setConteudo(line.substring(this.usoDoBanco.getPosicaoInicial()-1,this.usoDoBanco.getPosicaoFinal()));
		this.usoDoBanco1.setConteudo(line.substring(this.usoDoBanco1.getPosicaoInicial()-1,this.usoDoBanco1.getPosicaoFinal()));
		this.indicadorRateioCredito.setConteudo(line.substring(this.indicadorRateioCredito.getPosicaoInicial()-1,this.indicadorRateioCredito.getPosicaoFinal()));
		this.zeros2.setConteudo(line.substring(this.zeros2.getPosicaoInicial()-1,this.zeros2.getPosicaoFinal()));
		this.carteira.setConteudo(line.substring(this.carteira.getPosicaoInicial()-1,this.carteira.getPosicaoFinal()));
		this.identificacaoOcorrencia.setConteudo(line.substring(this.identificacaoOcorrencia.getPosicaoInicial()-1,this.identificacaoOcorrencia.getPosicaoFinal()));
		this.dataOcorrenciaBanco.setConteudo(line.substring(this.dataOcorrenciaBanco.getPosicaoInicial()-1,this.dataOcorrenciaBanco.getPosicaoFinal()));
		this.numeroDocumento.setConteudo(line.substring(this.numeroDocumento.getPosicaoInicial()-1,this.numeroDocumento.getPosicaoFinal()));
		this.identificacaoTituloBanco2.setConteudo(line.substring(this.identificacaoTituloBanco2.getPosicaoInicial()-1,this.identificacaoTituloBanco2.getPosicaoFinal()));
		this.dataVencimentoTitulo.setConteudo(line.substring(this.dataVencimentoTitulo.getPosicaoInicial()-1,this.dataVencimentoTitulo.getPosicaoFinal()));
		this.valorTitulo.setConteudo(line.substring(this.valorTitulo.getPosicaoInicial()-1,this.valorTitulo.getPosicaoFinal()));
		this.bancoCobrador.setConteudo(line.substring(this.bancoCobrador.getPosicaoInicial()-1,this.bancoCobrador.getPosicaoFinal()));
		this.agenciaCobradora.setConteudo(line.substring(this.agenciaCobradora.getPosicaoInicial()-1,this.agenciaCobradora.getPosicaoFinal()));
		this.especieTitulo.setConteudo(line.substring(this.especieTitulo.getPosicaoInicial()-1,this.especieTitulo.getPosicaoFinal()));
		this.despesasCobranca.setConteudo(line.substring(this.despesasCobranca.getPosicaoInicial()-1,this.despesasCobranca.getPosicaoFinal()));
		this.outrasDespesas.setConteudo(line.substring(this.outrasDespesas.getPosicaoInicial()-1,this.outrasDespesas.getPosicaoFinal()));
		this.jurosOperacaoAtraso.setConteudo(line.substring(this.jurosOperacaoAtraso.getPosicaoInicial()-1,this.jurosOperacaoAtraso.getPosicaoFinal()));
		this.iofDevido.setConteudo(line.substring(this.iofDevido.getPosicaoInicial()-1,this.iofDevido.getPosicaoFinal()));
		this.abatimentoConcedidoTitulo.setConteudo(line.substring(this.abatimentoConcedidoTitulo.getPosicaoInicial()-1,this.abatimentoConcedidoTitulo.getPosicaoFinal()));
		this.descontoConcedido.setConteudo(line.substring(this.descontoConcedido.getPosicaoInicial()-1,this.descontoConcedido.getPosicaoFinal()));
		this.valorPago.setConteudo(line.substring(this.valorPago.getPosicaoInicial()-1,this.valorPago.getPosicaoFinal()));
		this.jurosMora.setConteudo(line.substring(this.jurosMora.getPosicaoInicial()-1,this.jurosMora.getPosicaoFinal()));
		this.outrosCreditos.setConteudo(line.substring(this.outrosCreditos.getPosicaoInicial()-1,this.outrosCreditos.getPosicaoFinal()));
		this.brancos.setConteudo(line.substring(this.brancos.getPosicaoInicial()-1,this.brancos.getPosicaoFinal()));
		this.motivoCodigoOcorrencia.setConteudo(line.substring(this.motivoCodigoOcorrencia.getPosicaoInicial()-1,this.motivoCodigoOcorrencia.getPosicaoFinal()));
		this.dataCredito.setConteudo(line.substring(this.dataCredito.getPosicaoInicial()-1,this.dataCredito.getPosicaoFinal()));
		this.origemPagamento.setConteudo(line.substring(this.origemPagamento.getPosicaoInicial()-1,this.origemPagamento.getPosicaoFinal()));
		this.brancos1.setConteudo(line.substring(this.brancos1.getPosicaoInicial()-1,this.brancos1.getPosicaoFinal()));
		this.quandoChequeCodigoBanco.setConteudo(line.substring(this.quandoChequeCodigoBanco.getPosicaoInicial()-1,this.quandoChequeCodigoBanco.getPosicaoFinal()));
		this.motivosRejeicoes.setConteudo(line.substring(this.motivosRejeicoes.getPosicaoInicial()-1,this.motivosRejeicoes.getPosicaoFinal()));
		this.brancos2.setConteudo(line.substring(this.brancos2.getPosicaoInicial()-1,this.brancos2.getPosicaoFinal()));
		this.numeroCartorio.setConteudo(line.substring(this.numeroCartorio.getPosicaoInicial()-1,this.numeroCartorio.getPosicaoFinal()));
		this.numeroProtocolo.setConteudo(line.substring(this.numeroProtocolo.getPosicaoInicial()-1,this.numeroProtocolo.getPosicaoFinal()));
		this.brancos3.setConteudo(line.substring(this.brancos3.getPosicaoInicial()-1,this.brancos3.getPosicaoFinal()));
		this.numeroSequencialRegistro.setConteudo(line.substring(this.numeroSequencialRegistro.getPosicaoInicial()-1,this.numeroSequencialRegistro.getPosicaoFinal()));		
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.identificacaoRegistro.getConteudo());
		sb.append(this.tipoInscricaoEmpresa.getConteudo());
		sb.append(this.numeroInscricaoEmpresa.getConteudo());
		sb.append(this.zeros.getConteudo());
		sb.append(this.identiFicacaoEmpresaBeneficiarioNoBanco.getConteudo());
		sb.append(this.numeroControleParticipante.getConteudo());
		sb.append(this.zeros1.getConteudo());
		sb.append(this.identificacaoTituloBanco.getConteudo());
		sb.append(this.usoDoBanco.getConteudo());
		sb.append(this.usoDoBanco1.getConteudo());
		sb.append(this.indicadorRateioCredito.getConteudo());
		sb.append(this.zeros2.getConteudo());
		sb.append(this.carteira.getConteudo());
		sb.append(this.identificacaoOcorrencia.getConteudo());
		sb.append(this.dataOcorrenciaBanco.getConteudo());
		sb.append(this.numeroDocumento.getConteudo());
		sb.append(this.identificacaoTituloBanco2.getConteudo());
		sb.append(this.dataVencimentoTitulo.getConteudo());
		sb.append(this.valorTitulo.getConteudo());
		sb.append(this.bancoCobrador.getConteudo());
		sb.append(this.agenciaCobradora.getConteudo());
		sb.append(this.especieTitulo.getConteudo());
		sb.append(this.despesasCobranca.getConteudo());
		sb.append(this.outrasDespesas.getConteudo());
		sb.append(this.jurosOperacaoAtraso.getConteudo());
		sb.append(this.iofDevido.getConteudo());
		sb.append(this.abatimentoConcedidoTitulo.getConteudo());
		sb.append(this.descontoConcedido.getConteudo());
		sb.append(this.valorPago.getConteudo());
		sb.append(this.jurosMora.getConteudo());
		sb.append(this.outrosCreditos.getConteudo());
		sb.append(this.brancos.getConteudo());
		sb.append(this.motivoCodigoOcorrencia.getConteudo());
		sb.append(this.dataCredito.getConteudo());
		sb.append(this.origemPagamento.getConteudo());
		sb.append(this.brancos1.getConteudo());
		sb.append(this.quandoChequeCodigoBanco.getConteudo());
		sb.append(this.motivosRejeicoes.getConteudo());
		sb.append(this.brancos2.getConteudo());
		sb.append(this.numeroCartorio.getConteudo());
		sb.append(this.numeroProtocolo.getConteudo());
		sb.append(this.brancos3.getConteudo());
		sb.append(this.numeroSequencialRegistro.getConteudo());

		return sb.toString();
	}
	
	public String toCSV()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.identificacaoRegistro.getConteudo());
		sb.append(";"+this.tipoInscricaoEmpresa.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroInscricaoEmpresa.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.zeros.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.identiFicacaoEmpresaBeneficiarioNoBanco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroControleParticipante.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.zeros1.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.identificacaoTituloBanco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.usoDoBanco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.usoDoBanco1.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.indicadorRateioCredito.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.zeros2.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.carteira.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.identificacaoOcorrencia.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.dataOcorrenciaBanco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroDocumento.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.identificacaoTituloBanco2.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.dataVencimentoTitulo.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.valorTitulo.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.bancoCobrador.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.agenciaCobradora.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.especieTitulo.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.despesasCobranca.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.outrasDespesas.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.jurosOperacaoAtraso.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.iofDevido.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.abatimentoConcedidoTitulo.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.descontoConcedido.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.valorPago.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.jurosMora.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.outrosCreditos.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.brancos.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.motivoCodigoOcorrencia.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.dataCredito.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.origemPagamento.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.brancos1.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.quandoChequeCodigoBanco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.motivosRejeicoes.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.brancos2.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroCartorio.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroProtocolo.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.brancos3.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroSequencialRegistro.getConteudo()); //$NON-NLS-1$

		return sb.toString();
	}
	
	public static String csvColumns()
	{
		return "identificacaoRegistro;tipoInscricaoEmpresa;numeroInscricaoEmpresa;zeros;identiFicacaoEmpresaBeneficiarioNoBanco;numeroControleParticipante;zeros1;identificacaoTituloBanco;usoDoBanco;usoDoBanco1;indicadorRateioCredito;zeros2;carteira;identificacaoOcorrencia;dataOcorrenciaBanco;numeroDocumento;identificacaoTituloBanco2;dataVencimentoTitulo;valorTitulo;bancoCobrador;agenciaCobradora;especieTitulo;despesasCobranca;outrasDespesas;jurosOperacaoAtraso;iofDevido;abatimentoConcedidoTitulo;descontoConcedido;valorPago;jurosMora;outrosCreditos;brancos;motivoCodigoOcorrencia;dataCredito;origemPagamento;brancos1;quandoChequeCodigoBanco;motivosRejeicoes;brancos2;numeroCartorio;numeroProtocolo;brancos3;numeroSequencialRegistro"; //$NON-NLS-1$
	}
	
	public Campo getTipoInscricaoEmpresa()
	{
		return this.tipoInscricaoEmpresa;
	}

	public void setTipoInscricaoEmpresa(Campo tipoInscricaoEmpresa)
	{
		this.tipoInscricaoEmpresa = tipoInscricaoEmpresa;
	}

	public Campo getNumeroInscricaoEmpresa()
	{
		return this.numeroInscricaoEmpresa;
	}

	public void setNumeroInscricaoEmpresa(Campo numeroInscricaoEmpresa)
	{
		this.numeroInscricaoEmpresa = numeroInscricaoEmpresa;
	}

	public Campo getZeros()
	{
		return this.zeros;
	}

	public void setZeros(Campo zeros)
	{
		this.zeros = zeros;
	}

	public Campo getIdentiFicacaoEmpresaBeneficiarioNoBanco()
	{
		return this.identiFicacaoEmpresaBeneficiarioNoBanco;
	}

	public void setIdentiFicacaoEmpresaBeneficiarioNoBanco(
			Campo identiFicacaoEmpresaBeneficiarioNoBanco)
	{
		this.identiFicacaoEmpresaBeneficiarioNoBanco = identiFicacaoEmpresaBeneficiarioNoBanco;
	}

	public Campo getNumeroControleParticipante()
	{
		return this.numeroControleParticipante;
	}

	public void setNumeroControleParticipante(Campo numeroControleParticipante)
	{
		this.numeroControleParticipante = numeroControleParticipante;
	}

	public Campo getZeros1()
	{
		return this.zeros1;
	}

	public void setZeros1(Campo zeros1)
	{
		this.zeros1 = zeros1;
	}

	public Campo getIdentificacaoTituloBanco()
	{
		return this.identificacaoTituloBanco;
	}

	public void setIdentificacaoTituloBanco(Campo identificacaoTituloBanco)
	{
		this.identificacaoTituloBanco = identificacaoTituloBanco;
	}

	public Campo getUsoDoBanco()
	{
		return this.usoDoBanco;
	}

	public void setUsoDoBanco(Campo usoDoBanco)
	{
		this.usoDoBanco = usoDoBanco;
	}

	public Campo getUsoDoBanco1()
	{
		return this.usoDoBanco1;
	}

	public void setUsoDoBanco1(Campo usoDoBanco1)
	{
		this.usoDoBanco1 = usoDoBanco1;
	}

	public Campo getIndicadorRateioCredito()
	{
		return this.indicadorRateioCredito;
	}

	public void setIndicadorRateioCredito(Campo indicadorRateioCredito)
	{
		this.indicadorRateioCredito = indicadorRateioCredito;
	}

	public Campo getZeros2()
	{
		return this.zeros2;
	}

	public void setZeros2(Campo zeros2)
	{
		this.zeros2 = zeros2;
	}

	public Campo getCarteira()
	{
		return this.carteira;
	}

	public void setCarteira(Campo carteira)
	{
		this.carteira = carteira;
	}

	public Campo getIdentificacaoOcorrencia()
	{
		return this.identificacaoOcorrencia;
	}

	public void setIdentificacaoOcorrencia(Campo identificacaoOcorrencia)
	{
		this.identificacaoOcorrencia = identificacaoOcorrencia;
	}

	public Campo getDataOcorrenciaBanco()
	{
		return this.dataOcorrenciaBanco;
	}

	public void setDataOcorrenciaBanco(Campo dataOcorrenciaBanco)
	{
		this.dataOcorrenciaBanco = dataOcorrenciaBanco;
	}

	public Campo getNumeroDocumento()
	{
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(Campo numeroDocumento)
	{
		this.numeroDocumento = numeroDocumento;
	}

	public Campo getIdentificacaoTituloBanco2()
	{
		return this.identificacaoTituloBanco2;
	}

	public void setIdentificacaoTituloBanco2(Campo identificacaoTituloBanco2)
	{
		this.identificacaoTituloBanco2 = identificacaoTituloBanco2;
	}

	public Campo getDataVencimentoTitulo()
	{
		return this.dataVencimentoTitulo;
	}

	public void setDataVencimentoTitulo(Campo dataVencimentoTitulo)
	{
		this.dataVencimentoTitulo = dataVencimentoTitulo;
	}

	public Campo getValorTitulo()
	{
		return this.valorTitulo;
	}

	public void setValorTitulo(Campo valorTitulo)
	{
		this.valorTitulo = valorTitulo;
	}

	public Campo getBancoCobrador()
	{
		return this.bancoCobrador;
	}

	public void setBancoCobrador(Campo bancoCobrador)
	{
		this.bancoCobrador = bancoCobrador;
	}

	public Campo getAgenciaCobradora()
	{
		return this.agenciaCobradora;
	}

	public void setAgenciaCobradora(Campo agenciaCobradora)
	{
		this.agenciaCobradora = agenciaCobradora;
	}

	public Campo getEspecieTitulo()
	{
		return this.especieTitulo;
	}

	public void setEspecieTitulo(Campo especieTitulo)
	{
		this.especieTitulo = especieTitulo;
	}

	public Campo getDespesasCobranca()
	{
		return this.despesasCobranca;
	}

	public void setDespesasCobranca(Campo despesasCobranca)
	{
		this.despesasCobranca = despesasCobranca;
	}

	public Campo getOutrasDespesas()
	{
		return this.outrasDespesas;
	}

	public void setOutrasDespesas(Campo outrasDespesas)
	{
		this.outrasDespesas = outrasDespesas;
	}

	public Campo getJurosOperacaoAtraso()
	{
		return this.jurosOperacaoAtraso;
	}

	public void setJurosOperacaoAtraso(Campo jurosOperacaoAtraso)
	{
		this.jurosOperacaoAtraso = jurosOperacaoAtraso;
	}

	public Campo getIofDevido()
	{
		return this.iofDevido;
	}

	public void setIofDevido(Campo iofDevido)
	{
		this.iofDevido = iofDevido;
	}

	public Campo getAbatimentoConcedidoTitulo()
	{
		return this.abatimentoConcedidoTitulo;
	}

	public void setAbatimentoConcedidoTitulo(Campo abatimentoConcedidoTitulo)
	{
		this.abatimentoConcedidoTitulo = abatimentoConcedidoTitulo;
	}

	public Campo getDescontoConcedido()
	{
		return this.descontoConcedido;
	}

	public void setDescontoConcedido(Campo descontoConcedido)
	{
		this.descontoConcedido = descontoConcedido;
	}

	public Campo getValorPago()
	{
		return this.valorPago;
	}

	public void setValorPago(Campo valorPago)
	{
		this.valorPago = valorPago;
	}

	public Campo getJurosMora()
	{
		return this.jurosMora;
	}

	public void setJurosMora(Campo jurosMora)
	{
		this.jurosMora = jurosMora;
	}

	public Campo getOutrosCreditos()
	{
		return this.outrosCreditos;
	}

	public void setOutrosCreditos(Campo outrosCreditos)
	{
		this.outrosCreditos = outrosCreditos;
	}

	public Campo getBrancos()
	{
		return this.brancos;
	}

	public void setBrancos(Campo brancos)
	{
		this.brancos = brancos;
	}

	public Campo getMotivoCodigoOcorrencia()
	{
		return this.motivoCodigoOcorrencia;
	}

	public void setMotivoCodigoOcorrencia(Campo motivoCodigoOcorrencia)
	{
		this.motivoCodigoOcorrencia = motivoCodigoOcorrencia;
	}

	public Campo getDataCredito()
	{
		return this.dataCredito;
	}

	public void setDataCredito(Campo dataCredito)
	{
		this.dataCredito = dataCredito;
	}

	public Campo getOrigemPagamento()
	{
		return this.origemPagamento;
	}

	public void setOrigemPagamento(Campo origemPagamento)
	{
		this.origemPagamento = origemPagamento;
	}

	public Campo getBrancos1()
	{
		return this.brancos1;
	}

	public void setBrancos1(Campo brancos1)
	{
		this.brancos1 = brancos1;
	}

	public Campo getQuandoChequeCodigoBanco()
	{
		return this.quandoChequeCodigoBanco;
	}

	public void setQuandoChequeCodigoBanco(Campo quandoChequeCodigoBanco)
	{
		this.quandoChequeCodigoBanco = quandoChequeCodigoBanco;
	}

	public Campo getMotivosRejeicoes()
	{
		return this.motivosRejeicoes;
	}

	public void setMotivosRejeicoes(Campo motivosRejeicoes)
	{
		this.motivosRejeicoes = motivosRejeicoes;
	}

	public Campo getBrancos2()
	{
		return this.brancos2;
	}

	public void setBrancos2(Campo brancos2)
	{
		this.brancos2 = brancos2;
	}

	public Campo getNumeroCartorio()
	{
		return this.numeroCartorio;
	}

	public void setNumeroCartorio(Campo numeroCartorio)
	{
		this.numeroCartorio = numeroCartorio;
	}

	public Campo getNumeroProtocolo()
	{
		return this.numeroProtocolo;
	}

	public void setNumeroProtocolo(Campo numeroProtocolo)
	{
		this.numeroProtocolo = numeroProtocolo;
	}

	public Campo getBrancos3()
	{
		return this.brancos3;
	}

	public void setBrancos3(Campo brancos3)
	{
		this.brancos3 = brancos3;
	}
}
