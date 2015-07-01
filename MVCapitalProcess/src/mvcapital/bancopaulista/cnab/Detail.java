package mvcapital.bancopaulista.cnab;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import mvcapital.bancopaulista.liquidadosebaixados.TipoOcorrencia;
import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;
import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.relatorio.cessao.TipoTitulo;
import mvcapital.relatorio.cessao.Titulo;

public class Detail extends Register
{
	private Campo debitoAutomaticoContaCorrente=new Campo();
	private Campo coObrigacao=new Campo();
	private Campo caracteristicaEspecial=new Campo();
	private Campo modalidadeOperacao=new Campo();
	private Campo naturezaOperacao=new Campo();
	private Campo origemRecurso=new Campo();
	private Campo classeRiscoOperacao=new Campo();
	private Campo zeros=new Campo();
	private Campo numeroControleParticipante=new Campo();
	private Campo numeroBanco=new Campo();
	private Campo zeros1=new Campo();
	private Campo identificacaoTituloBanco=new Campo();
	private Campo digitoNossoNumero=new Campo();
	private Campo valorPago=new Campo();
	private Campo condicaoParaEmissaoPapeletaCobranca=new Campo();
	private Campo identificacaoSeEmitePapeletaParaDebitoAutomatico=new Campo();
	private Campo dataLiquidacao=new Campo();
	private Campo identificacaoOperacaoBanco=new Campo();
	private Campo indicadorRateioCredito=new Campo();
	private Campo enderacamentoParaAvisoDebitoAutomaticoContaCorrente=new Campo();
	private Campo branco=new Campo();
	private Campo identificacaoOcorrencia=new Campo();
	private Campo numeroDocumento=new Campo();
	private Campo dataVencimentoTitulo=new Campo();
	private Campo valorTitulo=new Campo();
	private Campo bancoEncarregadoCobranca=new Campo();
	private Campo agenciaDepositaria=new Campo();
	private Campo especieTitulo=new Campo();
	private Campo identificacao=new Campo();
	private Campo dataEmissaoTitulo=new Campo();	
	private Campo primeiraInstrucao=new Campo();
	private Campo segundaInstrucao=new Campo();
	private Campo tipoPessoaCedente=new Campo();
	private Campo zeros2=new Campo();
	private Campo numeroTermoCessao=new Campo();
	private Campo valorPresenteParcela=new Campo();
	private Campo valorAbatimento=new Campo();
	private Campo identificacaoTipoInscricaoSacado=new Campo();
	private Campo numeroInscricaoSacado=new Campo();
	private Campo nomeSacado=new Campo();
	private Campo enderecoCompletoSacado=new Campo();
	private Campo numeroNotaFiscalDuplicata=new Campo();
	private Campo numeroSerieNotaFiscalDuplicata=new Campo();
	private Campo cep=new Campo();
	private Campo nomeCedente=new Campo();
	private Campo cadastroCedente=new Campo();
	private Campo chaveDaNota=new Campo();
	public static SimpleDateFormat sdfCNAB = new SimpleDateFormat("ddMMyy"); //$NON-NLS-1$
	
	public Detail() 
	{
		super();
		this.setupDefault();
	}
	
	public Detail(FundoDeInvestimento fundo, Titulo titulo, TipoOcorrencia tipoOcorrencia, double valorPago, double valorAbatimento, int numeroCessao, int sequencialDetail, Connection conn)
	{
		super();
		this.setupDefault();
		String stringModalidadeOperacao=""; //$NON-NLS-1$
		if(titulo.getTipoTitulo().getDescricao().toLowerCase().contains("cheque")) //$NON-NLS-1$
		{
			stringModalidadeOperacao="0302"; //$NON-NLS-1$
		}
		else if(titulo.getTipoTitulo().getDescricao().toLowerCase().contains("duplicata")) //$NON-NLS-1$
		{
			stringModalidadeOperacao="0301"; //$NON-NLS-1$
		}
		else if(titulo.getTipoTitulo().getDescricao().toLowerCase().contains("fatura de cartao de credito")) //$NON-NLS-1$
		{
			stringModalidadeOperacao="0303"; //$NON-NLS-1$
		}
		else
		{
			stringModalidadeOperacao="0399"; //$NON-NLS-1$
		}
		this.getModalidadeOperacao().setConteudo(stringModalidadeOperacao); 
		this.getNumeroControleParticipante().setConteudo(titulo.getSeuNumero());
		this.getNumeroBanco().setConteudo("237"); //$NON-NLS-1$
		this.getValorPago().setConteudo(valorToString(valorPago));
		this.getDataLiquidacao().setConteudo(sdfCNAB.format(Calendar.getInstance().getTime()));
		String stringOcorrencia = codigoPaulistaOcorrencia(tipoOcorrencia, conn);
		this.getIdentificacaoOcorrencia().setConteudo(stringOcorrencia);
		this.getNumeroDocumento().setConteudo(titulo.getNumeroDocumento());
		this.getDataVencimentoTitulo().setConteudo(sdfCNAB.format(titulo.getDataVencimento()));
		this.getValorTitulo().setConteudo(valorToString(titulo.getValorNominal()));
		this.getBancoEncarregadoCobranca().setConteudo("237"); //$NON-NLS-1$
		String stringEspecieTitulo=codigoPaulistaEspecieTitulo(titulo.getTipoTitulo(), conn);		
		this.getEspecieTitulo().setConteudo(stringEspecieTitulo);
		this.getDataEmissaoTitulo().setConteudo(sdfCNAB.format(titulo.getDataAquisicao()));
		String stringTipoPessoaCedente = Integer.toString(tipoPessoa(titulo.getCedente(),conn));
		this.getTipoPessoaCedente().setConteudo(stringTipoPessoaCedente);
		String stringNumeroTermoCessao = Integer.toString(numeroCessao);
//		System.out.println("NroCessao: " +  stringNumeroTermoCessao);
		this.getNumeroTermoCessao().setConteudo(stringNumeroTermoCessao);
		this.getValorPresenteParcela().setConteudo(valorToString(valorPago));
		this.getValorAbatimento().setConteudo(valorToString(valorAbatimento));
		String stringIdentificacaoTipoInscricaoSacado = Integer.toString(tipoPessoa(titulo.getSacado(),conn));
		this.getIdentificacaoTipoInscricaoSacado().setConteudo(stringIdentificacaoTipoInscricaoSacado);
		this.getNumeroInscricaoSacado().setConteudo(titulo.getSacado().getCadastro());
		this.getNomeSacado().setConteudo(titulo.getSacado().getNome());		
		this.getEnderecoCompletoSacado().setConteudo(titulo.getSacado().getEndereco());
		this.getCep().setConteudo(checkCEP(titulo.getSacado().getIdCEP(), conn));
		this.getNomeCedente().setConteudo(titulo.getCedente().getNome());
		this.getCadastroCedente().setConteudo(titulo.getCedente().getCadastro());
		super.getNumeroSequencialRegistro().setConteudo(Integer.toString(sequencialDetail));
	}
	
	public Detail(String line)
	{
		this();
		this.identificacaoRegistro.setConteudo(line.substring(this.identificacaoRegistro.getPosicaoInicial()-1,this.identificacaoRegistro.getPosicaoFinal()));
		this.debitoAutomaticoContaCorrente.setConteudo(line.substring(this.debitoAutomaticoContaCorrente.getPosicaoInicial()-1,this.debitoAutomaticoContaCorrente.getPosicaoFinal()));
		this.coObrigacao.setConteudo(line.substring(this.coObrigacao.getPosicaoInicial()-1,this.coObrigacao.getPosicaoFinal()));
		this.caracteristicaEspecial.setConteudo(line.substring(this.caracteristicaEspecial.getPosicaoInicial()-1,this.caracteristicaEspecial.getPosicaoFinal()));
		this.modalidadeOperacao.setConteudo(line.substring(this.modalidadeOperacao.getPosicaoInicial()-1,this.modalidadeOperacao.getPosicaoFinal()));
		this.naturezaOperacao.setConteudo(line.substring(this.naturezaOperacao.getPosicaoInicial()-1,this.naturezaOperacao.getPosicaoFinal()));
		this.origemRecurso.setConteudo(line.substring(this.origemRecurso.getPosicaoInicial()-1,this.origemRecurso.getPosicaoFinal()));
		this.classeRiscoOperacao.setConteudo(line.substring(this.classeRiscoOperacao.getPosicaoInicial()-1,this.classeRiscoOperacao.getPosicaoFinal()));
		this.zeros.setConteudo(line.substring(this.zeros.getPosicaoInicial()-1,this.zeros.getPosicaoFinal()));
		this.numeroControleParticipante.setConteudo(line.substring(this.numeroControleParticipante.getPosicaoInicial()-1,this.numeroControleParticipante.getPosicaoFinal()));
		this.numeroBanco.setConteudo(line.substring(this.numeroBanco.getPosicaoInicial()-1,this.numeroBanco.getPosicaoFinal()));
		this.zeros1.setConteudo(line.substring(this.zeros1.getPosicaoInicial()-1,this.zeros1.getPosicaoFinal()));
		this.identificacaoTituloBanco.setConteudo(line.substring(this.identificacaoTituloBanco.getPosicaoInicial()-1,this.identificacaoTituloBanco.getPosicaoFinal()));
		this.digitoNossoNumero.setConteudo(line.substring(this.digitoNossoNumero.getPosicaoInicial()-1,this.digitoNossoNumero.getPosicaoFinal()));
		this.valorPago.setConteudo(line.substring(this.valorPago.getPosicaoInicial()-1,this.valorPago.getPosicaoFinal()));
		this.condicaoParaEmissaoPapeletaCobranca.setConteudo(line.substring(this.condicaoParaEmissaoPapeletaCobranca.getPosicaoInicial()-1,this.condicaoParaEmissaoPapeletaCobranca.getPosicaoFinal()));
		this.identificacaoSeEmitePapeletaParaDebitoAutomatico.setConteudo(line.substring(this.identificacaoSeEmitePapeletaParaDebitoAutomatico.getPosicaoInicial()-1,this.identificacaoSeEmitePapeletaParaDebitoAutomatico.getPosicaoFinal()));
		this.dataLiquidacao.setConteudo(line.substring(this.dataLiquidacao.getPosicaoInicial()-1,this.dataLiquidacao.getPosicaoFinal()));
		this.identificacaoOperacaoBanco.setConteudo(line.substring(this.identificacaoOperacaoBanco.getPosicaoInicial()-1,this.identificacaoOperacaoBanco.getPosicaoFinal()));
		this.indicadorRateioCredito.setConteudo(line.substring(this.indicadorRateioCredito.getPosicaoInicial()-1,this.indicadorRateioCredito.getPosicaoFinal()));
		this.enderacamentoParaAvisoDebitoAutomaticoContaCorrente.setConteudo(line.substring(this.enderacamentoParaAvisoDebitoAutomaticoContaCorrente.getPosicaoInicial()-1,this.enderacamentoParaAvisoDebitoAutomaticoContaCorrente.getPosicaoFinal()));
		this.branco.setConteudo(line.substring(this.branco.getPosicaoInicial()-1,this.branco.getPosicaoFinal()));
		this.identificacaoOcorrencia.setConteudo(line.substring(this.identificacaoOcorrencia.getPosicaoInicial()-1,this.identificacaoOcorrencia.getPosicaoFinal()));
		this.numeroDocumento.setConteudo(line.substring(this.numeroDocumento.getPosicaoInicial()-1,this.numeroDocumento.getPosicaoFinal()));
		this.dataVencimentoTitulo.setConteudo(line.substring(this.dataVencimentoTitulo.getPosicaoInicial()-1,this.dataVencimentoTitulo.getPosicaoFinal()));
		this.valorTitulo.setConteudo(line.substring(this.valorTitulo.getPosicaoInicial()-1,this.valorTitulo.getPosicaoFinal()));
		this.bancoEncarregadoCobranca.setConteudo(line.substring(this.bancoEncarregadoCobranca.getPosicaoInicial()-1,this.bancoEncarregadoCobranca.getPosicaoFinal()));
		this.agenciaDepositaria.setConteudo(line.substring(this.agenciaDepositaria.getPosicaoInicial()-1,this.agenciaDepositaria.getPosicaoFinal()));
		this.especieTitulo.setConteudo(line.substring(this.especieTitulo.getPosicaoInicial()-1,this.especieTitulo.getPosicaoFinal()));
		this.identificacao.setConteudo(line.substring(this.identificacao.getPosicaoInicial()-1,this.identificacao.getPosicaoFinal()));
		this.dataEmissaoTitulo.setConteudo(line.substring(this.dataEmissaoTitulo.getPosicaoInicial()-1,this.dataEmissaoTitulo.getPosicaoFinal()));
		this.primeiraInstrucao.setConteudo(line.substring(this.primeiraInstrucao.getPosicaoInicial()-1,this.primeiraInstrucao.getPosicaoFinal()));
		this.segundaInstrucao.setConteudo(line.substring(this.segundaInstrucao.getPosicaoInicial()-1,this.segundaInstrucao.getPosicaoFinal()));
		this.tipoPessoaCedente.setConteudo(line.substring(this.tipoPessoaCedente.getPosicaoInicial()-1,this.tipoPessoaCedente.getPosicaoFinal()));
		this.zeros2.setConteudo(line.substring(this.zeros2.getPosicaoInicial()-1,this.zeros2.getPosicaoFinal()));
		this.numeroTermoCessao.setConteudo(line.substring(this.numeroTermoCessao.getPosicaoInicial()-1,this.numeroTermoCessao.getPosicaoFinal()));
		this.valorPresenteParcela.setConteudo(line.substring(this.valorPresenteParcela.getPosicaoInicial()-1,this.valorPresenteParcela.getPosicaoFinal()));
		this.valorAbatimento.setConteudo(line.substring(this.valorAbatimento.getPosicaoInicial()-1,this.valorAbatimento.getPosicaoFinal()));
		this.identificacaoTipoInscricaoSacado.setConteudo(line.substring(this.identificacaoTipoInscricaoSacado.getPosicaoInicial()-1,this.identificacaoTipoInscricaoSacado.getPosicaoFinal()));
		this.numeroInscricaoSacado.setConteudo(line.substring(this.numeroInscricaoSacado.getPosicaoInicial()-1,this.numeroInscricaoSacado.getPosicaoFinal()));
		this.nomeSacado.setConteudo(line.substring(this.nomeSacado.getPosicaoInicial()-1,this.nomeSacado.getPosicaoFinal()));
		this.enderecoCompletoSacado.setConteudo(line.substring(this.enderecoCompletoSacado.getPosicaoInicial()-1,this.enderecoCompletoSacado.getPosicaoFinal()));
		this.numeroNotaFiscalDuplicata.setConteudo(line.substring(this.numeroNotaFiscalDuplicata.getPosicaoInicial()-1,this.numeroNotaFiscalDuplicata.getPosicaoFinal()));
		this.numeroSerieNotaFiscalDuplicata.setConteudo(line.substring(this.numeroSerieNotaFiscalDuplicata.getPosicaoInicial()-1,this.numeroSerieNotaFiscalDuplicata.getPosicaoFinal()));
		this.cep.setConteudo(line.substring(this.cep.getPosicaoInicial()-1,this.cep.getPosicaoFinal()));
		this.nomeCedente.setConteudo(line.substring(this.nomeCedente.getPosicaoInicial()-1,this.nomeCedente.getPosicaoFinal()));
		this.cadastroCedente.setConteudo(line.substring(this.cadastroCedente.getPosicaoInicial()-1,this.cadastroCedente.getPosicaoFinal()));
		this.chaveDaNota.setConteudo(line.substring(this.chaveDaNota.getPosicaoInicial()-1,this.chaveDaNota.getPosicaoFinal()));
		this.numeroSequencialRegistro.setConteudo(line.substring(this.numeroSequencialRegistro.getPosicaoInicial()-1,this.numeroSequencialRegistro.getPosicaoFinal()));
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder(); 
		sb.append(this.identificacaoRegistro.getConteudo());
		sb.append(this.debitoAutomaticoContaCorrente.getConteudo());
		sb.append(this.coObrigacao.getConteudo());
		sb.append(this.caracteristicaEspecial.getConteudo());
		sb.append(this.modalidadeOperacao.getConteudo());
		sb.append(this.naturezaOperacao.getConteudo());
		sb.append(this.origemRecurso.getConteudo());
		sb.append(this.classeRiscoOperacao.getConteudo());
		sb.append(this.zeros.getConteudo());
		sb.append(this.numeroControleParticipante.getConteudo());
		sb.append(this.numeroBanco.getConteudo());
		sb.append(this.zeros1.getConteudo());
		sb.append(this.identificacaoTituloBanco.getConteudo());
		sb.append(this.digitoNossoNumero.getConteudo());
		sb.append(this.valorPago.getConteudo());
		sb.append(this.condicaoParaEmissaoPapeletaCobranca.getConteudo());
		sb.append(this.identificacaoSeEmitePapeletaParaDebitoAutomatico.getConteudo());
		sb.append(this.dataLiquidacao.getConteudo());
		sb.append(this.identificacaoOperacaoBanco.getConteudo());
		sb.append(this.indicadorRateioCredito.getConteudo());
		sb.append(this.enderacamentoParaAvisoDebitoAutomaticoContaCorrente.getConteudo());
		sb.append(this.branco.getConteudo());
		sb.append(this.identificacaoOcorrencia.getConteudo());
		sb.append(this.numeroDocumento.getConteudo());
		sb.append(this.dataVencimentoTitulo.getConteudo());
		sb.append(this.valorTitulo.getConteudo());
		sb.append(this.bancoEncarregadoCobranca.getConteudo());
		sb.append(this.agenciaDepositaria.getConteudo());
		sb.append(this.especieTitulo.getConteudo());
		sb.append(this.identificacao.getConteudo());
		sb.append(this.dataEmissaoTitulo.getConteudo());
		sb.append(this.primeiraInstrucao.getConteudo());
		sb.append(this.segundaInstrucao.getConteudo());
		sb.append(this.tipoPessoaCedente.getConteudo());
		sb.append(this.zeros2.getConteudo());
		sb.append(this.numeroTermoCessao.getConteudo());
		sb.append(this.valorPresenteParcela.getConteudo());
		sb.append(this.valorAbatimento.getConteudo());
		sb.append(this.identificacaoTipoInscricaoSacado.getConteudo());
		sb.append(this.numeroInscricaoSacado.getConteudo());
		sb.append(this.nomeSacado.getConteudo());
		sb.append(this.enderecoCompletoSacado.getConteudo());
		sb.append(this.numeroNotaFiscalDuplicata.getConteudo());
		sb.append(this.numeroSerieNotaFiscalDuplicata.getConteudo());
		sb.append(this.cep.getConteudo());
		sb.append(this.nomeCedente.getConteudo());
		sb.append(this.cadastroCedente.getConteudo());
		sb.append(this.chaveDaNota.getConteudo());
		sb.append(this.numeroSequencialRegistro.getConteudo());;		
		return sb.toString();
	}

	public String toCSV()
	{
		StringBuilder sb = new StringBuilder(); 
		sb.append(this.identificacaoRegistro.getConteudo());
		sb.append(";"+this.debitoAutomaticoContaCorrente.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.coObrigacao.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.caracteristicaEspecial.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.modalidadeOperacao.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.naturezaOperacao.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.origemRecurso.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.classeRiscoOperacao.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.zeros.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroControleParticipante.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroBanco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.zeros1.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.identificacaoTituloBanco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.digitoNossoNumero.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.valorPago.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.condicaoParaEmissaoPapeletaCobranca.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.identificacaoSeEmitePapeletaParaDebitoAutomatico.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.dataLiquidacao.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.identificacaoOperacaoBanco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.indicadorRateioCredito.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.enderacamentoParaAvisoDebitoAutomaticoContaCorrente.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.branco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.identificacaoOcorrencia.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroDocumento.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.dataVencimentoTitulo.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.valorTitulo.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.bancoEncarregadoCobranca.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.agenciaDepositaria.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.especieTitulo.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.identificacao.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.dataEmissaoTitulo.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.primeiraInstrucao.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.segundaInstrucao.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.tipoPessoaCedente.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.zeros2.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroTermoCessao.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.valorPresenteParcela.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.valorAbatimento.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.identificacaoTipoInscricaoSacado.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroInscricaoSacado.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.nomeSacado.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.enderecoCompletoSacado.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroNotaFiscalDuplicata.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroSerieNotaFiscalDuplicata.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.cep.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.nomeCedente.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.cadastroCedente.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.chaveDaNota.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroSequencialRegistro.getConteudo());;		 //$NON-NLS-1$
		return sb.toString();
	}

	public static String csvColumns()
	{
//		return "identificacaoRegistro;debitoAutomaticoContaCorrente;coObrigacao;caracteristicaEspecial;modalidadeOperacao;naturezaOperacao;origemRecurso;classeRiscoOperacao;zeros;numeroControleParticipante;numeroBanco;zeros1;identificacaoTituloBanco;digitoNossoNumero;valorPago;condicaoParaEmissaoPapeletaCobranca;identificacaoSeEmitePapeletaParaDebitoAutomatico;dataLiquidacao;identificacaoOperacaoBanco;indicadorRateioCredito;enderacamentoParaAvisoDebitoAutomaticoContaCorrente;branco;identificacaoOcorrencia;numeroDocumento;dataVencimentoTitulo;valorTitulo;bancoEncarregadoCobranca;agenciaDepositaria;especieTitulo;identificacao;dataEmissaoTitulo;primeiraInstrucao;segundaInstrucao;tipoPessoaCedente;zeros2;numeroTermoCessao;valorPresenteParcela;valorAbatimento;identificacaoTipoInscricaoSacado;numeroInscricaoSacado;nomeSacado;enderecoCompletoSacado;numeroNotaFiscalDuplicata;numeroSerieNotaFiscalDuplicata;cep;nomeCedente;cadastroCedente;chaveDaNota;numeroSequencialRegistro"; //$NON-NLS-1$
		return "idDoRegistro;debitoAutomaticoCC;coobrigacao;caracteristicaEspecial;modalidadeDaOperacao;naturezaDaOperacao;origemDoRecurso;classeRiscoDaOperacao;zeros;numeroDeControleDoParticipante;numeroDoBanco;zeros2;idDoTituloNoBanco;digitoDoNossoNumero;valorPago;condicaoParaEmissaoDaPapeletaDeCobranca;idSeEmitePapeletaParaDebitoAutomatico;dataDaLiquidacao;idDaOperacaoDoBanco;indicadorRateioCredito;enderecamentoParaAvisoDoDebitoAutomaticoEmCC;branco;idOcorrencia;numeroDoDocumento;dataDoVencimentoDoTitulo;valorDoTitulo;bancoEncarregadoDaCobranca;agenciaDepositaria;especieDeTitulo;identificacao;dataDaEmissaoDoTitulo;primeiraInstrucao;segundaInstrucao;tipoPessoaCedente;zeros3;numeroDoTermoDeCessao;valorPresenteDaParcela;valorDoAbatimento;identificacaoDoTipoDeInscricaoDoSacado;numeroDeInscricaoDoSacado;nomeDoSacado;enderecoCompletoDoSacado;numeroDaNotaFiscalDaDuplicata;numeroDaSerieDaNotaFiscalDaDuplicata;cep;nomeCedente;cadastroCedente;chaveDaNota;seqDoRegistro";
	}
	
	public void setupDefault()
	{
		this.identificacaoRegistro.setConteudo("1"); //$NON-NLS-1$
		this.debitoAutomaticoContaCorrente=new Campo(2,2,20,19,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.coObrigacao=new Campo(3,21,22,2,true,Register.campoNumerico,0,"01"); //$NON-NLS-1$
		this.caracteristicaEspecial=new Campo(4,23,24,2,false,Register.campoNumerico,0,"35"); //$NON-NLS-1$
		this.modalidadeOperacao=new Campo(5,25,28,4,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.naturezaOperacao=new Campo(6,29,30,2,false,Register.campoNumerico,0,"04"); //$NON-NLS-1$
		this.origemRecurso=new Campo(7,31,34,4,false,Register.campoNumerico,0,"0199"); //$NON-NLS-1$
		this.classeRiscoOperacao=new Campo(8,35,36,2,false,Register.campoNumerico,0,"AA"); //$NON-NLS-1$
		this.zeros=new Campo(9,37,37,1,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.numeroControleParticipante=new Campo(10,38,62,25,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.numeroBanco=new Campo(11,63,65,3,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.zeros1=new Campo(12,66,70,5,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.identificacaoTituloBanco=new Campo(13,71,81,11,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.digitoNossoNumero=new Campo(14,82,82,1,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.valorPago=new Campo(15,83,92,10,false,Register.campoNumerico,2,""); //$NON-NLS-1$
		this.condicaoParaEmissaoPapeletaCobranca=new Campo(16,93,93,1,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.identificacaoSeEmitePapeletaParaDebitoAutomatico=new Campo(17,94,94,1,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.dataLiquidacao=new Campo(18,95,100,6,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.identificacaoOperacaoBanco=new Campo(19,101,104,4,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.indicadorRateioCredito=new Campo(20,105,105,1,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.enderacamentoParaAvisoDebitoAutomaticoContaCorrente=new Campo(21,106,106,1,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.branco=new Campo(22,107,108,2,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.identificacaoOcorrencia=new Campo(23,109,110,2,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.numeroDocumento=new Campo(24,111,120,10,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.dataVencimentoTitulo=new Campo(25,121,126,6,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.valorTitulo=new Campo(26,127,139,13,true,Register.campoNumerico,2,""); //$NON-NLS-1$
		this.bancoEncarregadoCobranca=new Campo(27,140,142,3,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.agenciaDepositaria=new Campo(28,143,147,5,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.especieTitulo=new Campo(29,148,149,2,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.identificacao=new Campo(30,150,150,1,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.dataEmissaoTitulo=new Campo(31,151,156,6,true,Register.campoNumerico,0,"");	 //$NON-NLS-1$
		this.primeiraInstrucao=new Campo(32,157,158,2,false,Register.campoNumerico,0,"");		 //$NON-NLS-1$
		this.segundaInstrucao=new Campo(33,159,159,1,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.tipoPessoaCedente=new Campo(34,160,161,2,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.zeros2=new Campo(35,162,173,12,false,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.numeroTermoCessao=new Campo(36,174,192,19,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.valorPresenteParcela=new Campo(37,193,205,13,true,Register.campoNumerico,2,""); //$NON-NLS-1$
		this.valorAbatimento=new Campo(38,206,218,13,true,Register.campoNumerico,2,""); //$NON-NLS-1$
		this.identificacaoTipoInscricaoSacado=new Campo(39,219,220,2,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.numeroInscricaoSacado=new Campo(40,221,234,14,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.nomeSacado=new Campo(41,235,274,40,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.enderecoCompletoSacado=new Campo(42,275,314,40,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.numeroNotaFiscalDuplicata=new Campo(43,315,323,9,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.numeroSerieNotaFiscalDuplicata=new Campo(44,324,326,3,false,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.cep=new Campo(45,327,334,8,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.nomeCedente=new Campo(46,335,380,46,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.cadastroCedente=new Campo(47,381,394,14,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.chaveDaNota=new Campo(48,395,438,44,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.numeroSequencialRegistro=new Campo(49,439,444,6,true,Register.campoNumerico,0,"");	 //$NON-NLS-1$
	}

	public static String valorToString(double valor)
	{
		String stringValor=Integer.toString((int) Math.round(valor*100));
		return stringValor;
	}
	
	public static String checkCEP(int idCEP, Connection conn)
	{
		String stringCEP=""; //$NON-NLS-1$
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
//		select * from titulo where idFundo=5 and valorNominal=1250 and idTipoTitulo=1 and numeroDocumento like "%16%"			
		String query = "SELECT cep FROM CEP " //$NON-NLS-1$
				+ " WHERE" //$NON-NLS-1$
				+ " idCEP = " + idCEP //$NON-NLS-1$
				;
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				stringCEP = rs.getString("CEP"); //$NON-NLS-1$
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return stringCEP;
	}	
	public static int tipoPessoa(Entidade entidade, Connection conn)
	{
		int codigoPaulistaTipoPessoa=0;
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
//		select * from titulo where idFundo=5 and valorNominal=1250 and idTipoTitulo=1 and numeroDocumento like "%16%"			
		String query = "SELECT codigo FROM codigopaulistatipopessoacedente " //$NON-NLS-1$
				+ " WHERE" //$NON-NLS-1$
				+ " idTipoCadastro = " + entidade.getIdTipoCadastro() //$NON-NLS-1$
				;
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				codigoPaulistaTipoPessoa = rs.getInt("Codigo"); //$NON-NLS-1$
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return codigoPaulistaTipoPessoa;
	}

	public static String codigoPaulistaEspecieTitulo(TipoTitulo tipoTitulo, Connection conn)
	{
		String codigoPaulistaEspecieTitulo=""; //$NON-NLS-1$
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
//		select * from titulo where idFundo=5 and valorNominal=1250 and idTipoTitulo=1 and numeroDocumento like "%16%"			
		String query = "SELECT codigo FROM codigoPaulistaEspecieTitulo " //$NON-NLS-1$
				+ " WHERE" //$NON-NLS-1$
				+ " idTipoTitulo = " + tipoTitulo.getIdTipoTitulo() //$NON-NLS-1$
				;
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				codigoPaulistaEspecieTitulo = rs.getString("Codigo"); //$NON-NLS-1$
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return codigoPaulistaEspecieTitulo;
	}

	public static String codigoPaulistaOcorrencia(TipoOcorrencia tipoOcorrencia, Connection conn)
	{
		String codigoPaulistaOcorrencia=""; //$NON-NLS-1$
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
//		select * from titulo where idFundo=5 and valorNominal=1250 and idTipoTitulo=1 and numeroDocumento like "%16%"			
		String query = "SELECT codigo FROM codigoPaulistaOcorrencia " //$NON-NLS-1$
				+ " WHERE" //$NON-NLS-1$
				+ " idTipoOcorrencia = " + tipoOcorrencia.getIdTipoOcorrencia() //$NON-NLS-1$
				;
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				codigoPaulistaOcorrencia = rs.getString("Codigo"); //$NON-NLS-1$
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return codigoPaulistaOcorrencia;
	}	
	public Campo getDebitoAutomaticoContaCorrente() {
		return this.debitoAutomaticoContaCorrente;
	}

	public void setDebitoAutomaticoContaCorrente(Campo debitoAutomaticoContaCorrente) {
		this.debitoAutomaticoContaCorrente = debitoAutomaticoContaCorrente;
	}

	public Campo getCoObrigacao() {
		return this.coObrigacao;
	}

	public void setCoObrigacao(Campo coObrigacao) {
		this.coObrigacao = coObrigacao;
	}

	public Campo getCaracteristicaEspecial() {
		return this.caracteristicaEspecial;
	}

	public void setCaracteristicaEspecial(Campo caracteristicaEspecial) {
		this.caracteristicaEspecial = caracteristicaEspecial;
	}

	public Campo getModalidadeOperacao() {
		return this.modalidadeOperacao;
	}

	public void setModalidadeOperacao(Campo modalidadeOperacao) {
		this.modalidadeOperacao = modalidadeOperacao;
	}

	public Campo getNaturezaOperacao() {
		return this.naturezaOperacao;
	}

	public void setNaturezaOperacao(Campo naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}

	public Campo getOrigemRecurso() {
		return this.origemRecurso;
	}

	public void setOrigemRecurso(Campo origemRecurso) {
		this.origemRecurso = origemRecurso;
	}

	public Campo getClasseRiscoOperacao() {
		return this.classeRiscoOperacao;
	}

	public void setClasseRiscoOperacao(Campo classeRiscoOperacao) {
		this.classeRiscoOperacao = classeRiscoOperacao;
	}

	public Campo getZeros() {
		return this.zeros;
	}

	public void setZeros(Campo zeros) {
		this.zeros = zeros;
	}

	public Campo getNumeroControleParticipante() {
		return this.numeroControleParticipante;
	}

	public void setNumeroControleParticipante(Campo numeroControleParticipante) {
		this.numeroControleParticipante = numeroControleParticipante;
	}

	public Campo getNumeroBanco() {
		return this.numeroBanco;
	}

	public void setNumeroBanco(Campo numeroBanco) {
		this.numeroBanco = numeroBanco;
	}

	public Campo getZeros1() {
		return this.zeros1;
	}

	public void setZeros1(Campo zeros1) {
		this.zeros1 = zeros1;
	}

	public Campo getIdentificacaoTituloBanco() {
		return this.identificacaoTituloBanco;
	}

	public void setIdentificacaoTituloBanco(Campo identificacaoTituloBanco) {
		this.identificacaoTituloBanco = identificacaoTituloBanco;
	}

	public Campo getDigitoNossoNumero() {
		return this.digitoNossoNumero;
	}

	public void setDigitoNossoNumero(Campo digitoNossoNumero) {
		this.digitoNossoNumero = digitoNossoNumero;
	}

	public Campo getValorPago() {
		return this.valorPago;
	}

	public void setValorPago(Campo valorPago) {
		this.valorPago = valorPago;
	}

	public Campo getCondicaoParaEmissaoPapeletaCobranca() {
		return this.condicaoParaEmissaoPapeletaCobranca;
	}

	public void setCondicaoParaEmissaoPapeletaCobranca(
			Campo condicaoParaEmissaoPapeletaCobranca) {
		this.condicaoParaEmissaoPapeletaCobranca = condicaoParaEmissaoPapeletaCobranca;
	}

	public Campo getIdentificacaoSeEmitePapeletaParaDebitoAutomatico() {
		return this.identificacaoSeEmitePapeletaParaDebitoAutomatico;
	}

	public void setIdentificacaoSeEmitePapeletaParaDebitoAutomatico(
			Campo identificacaoSeEmitePapeletaParaDebitoAutomatico) {
		this.identificacaoSeEmitePapeletaParaDebitoAutomatico = identificacaoSeEmitePapeletaParaDebitoAutomatico;
	}

	public Campo getDataLiquidacao() {
		return this.dataLiquidacao;
	}

	public void setDataLiquidacao(Campo dataLiquidacao) {
		this.dataLiquidacao = dataLiquidacao;
	}

	public Campo getIdentificacaoOperacaoBanco() {
		return this.identificacaoOperacaoBanco;
	}

	public void setIdentificacaoOperacaoBanco(Campo identificacaoOperacaoBanco) {
		this.identificacaoOperacaoBanco = identificacaoOperacaoBanco;
	}

	public Campo getIndicadorRateioCredito() {
		return this.indicadorRateioCredito;
	}

	public void setIndicadorRateioCredito(Campo indicadorRateioCredito) {
		this.indicadorRateioCredito = indicadorRateioCredito;
	}

	public Campo getEnderacamentoParaAvisoDebitoAutomaticoContaCorrente() {
		return this.enderacamentoParaAvisoDebitoAutomaticoContaCorrente;
	}

	public void setEnderacamentoParaAvisoDebitoAutomaticoContaCorrente(
			Campo enderacamentoParaAvisoDebitoAutomaticoContaCorrente) {
		this.enderacamentoParaAvisoDebitoAutomaticoContaCorrente = enderacamentoParaAvisoDebitoAutomaticoContaCorrente;
	}

	public Campo getBranco() {
		return this.branco;
	}

	public void setBranco(Campo branco) {
		this.branco = branco;
	}

	public Campo getIdentificacaoOcorrencia() {
		return this.identificacaoOcorrencia;
	}

	public void setIdentificacaoOcorrencia(Campo identificacaoOcorrencia) {
		this.identificacaoOcorrencia = identificacaoOcorrencia;
	}

	public Campo getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(Campo numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Campo getDataVencimentoTitulo() {
		return this.dataVencimentoTitulo;
	}

	public void setDataVencimentoTitulo(Campo dataVencimentoTitulo) {
		this.dataVencimentoTitulo = dataVencimentoTitulo;
	}

	public Campo getValorTitulo() {
		return this.valorTitulo;
	}

	public void setValorTitulo(Campo valorTitulo) {
		this.valorTitulo = valorTitulo;
	}

	public Campo getBancoEncarregadoCobranca() {
		return this.bancoEncarregadoCobranca;
	}

	public void setBancoEncarregadoCobranca(Campo bancoEncarregadoCobranca) {
		this.bancoEncarregadoCobranca = bancoEncarregadoCobranca;
	}

	public Campo getAgenciaDepositaria() {
		return this.agenciaDepositaria;
	}

	public void setAgenciaDepositaria(Campo agenciaDepositaria) {
		this.agenciaDepositaria = agenciaDepositaria;
	}

	public Campo getEspecieTitulo() {
		return this.especieTitulo;
	}

	public void setEspecieTitulo(Campo especieTitulo) {
		this.especieTitulo = especieTitulo;
	}

	public Campo getIdentificacao() {
		return this.identificacao;
	}

	public void setIdentificacao(Campo identificacao) {
		this.identificacao = identificacao;
	}

	public Campo getDataEmissaoTitulo() {
		return this.dataEmissaoTitulo;
	}

	public void setDataEmissaoTitulo(Campo dataEmissaoTitulo) {
		this.dataEmissaoTitulo = dataEmissaoTitulo;
	}

	public Campo getPrimeiraInstrucao() {
		return this.primeiraInstrucao;
	}

	public void setPrimeiraInstrucao(Campo primeiraInstrucao) {
		this.primeiraInstrucao = primeiraInstrucao;
	}

	public Campo getSegundaInstrucao() {
		return this.segundaInstrucao;
	}

	public void setSegundaInstrucao(Campo segundaInstrucao) {
		this.segundaInstrucao = segundaInstrucao;
	}

	public Campo getTipoPessoaCedente() {
		return this.tipoPessoaCedente;
	}

	public void setTipoPessoaCedente(Campo tipoPessoaCedente) {
		this.tipoPessoaCedente = tipoPessoaCedente;
	}

	public Campo getZeros2() {
		return this.zeros2;
	}

	public void setZeros2(Campo zeros2) {
		this.zeros2 = zeros2;
	}

	public Campo getNumeroTermoCessao() {
		return this.numeroTermoCessao;
	}

	public void setNumeroTermoCessao(Campo numeroTermoCessao) {
		this.numeroTermoCessao = numeroTermoCessao;
	}

	public Campo getValorPresenteParcela() {
		return this.valorPresenteParcela;
	}

	public void setValorPresenteParcela(Campo valorPresenteParcela) {
		this.valorPresenteParcela = valorPresenteParcela;
	}

	public Campo getValorAbatimento() {
		return this.valorAbatimento;
	}

	public void setValorAbatimento(Campo valorAbatimento) {
		this.valorAbatimento = valorAbatimento;
	}

	public Campo getIdentificacaoTipoInscricaoSacado() {
		return this.identificacaoTipoInscricaoSacado;
	}

	public void setIdentificacaoTipoInscricaoSacado(
			Campo identificacaoTipoInscricaoSacado) {
		this.identificacaoTipoInscricaoSacado = identificacaoTipoInscricaoSacado;
	}

	public Campo getNumeroInscricaoSacado() {
		return this.numeroInscricaoSacado;
	}

	public void setNumeroInscricaoSacado(Campo numeroInscricaoSacado) {
		this.numeroInscricaoSacado = numeroInscricaoSacado;
	}

	public Campo getNomeSacado() {
		return this.nomeSacado;
	}

	public void setNomeSacado(Campo nomeSacado) {
		this.nomeSacado = nomeSacado;
	}

	public Campo getEnderecoCompletoSacado() {
		return this.enderecoCompletoSacado;
	}

	public void setEnderecoCompletoSacado(Campo enderecoCompletoSacado) {
		this.enderecoCompletoSacado = enderecoCompletoSacado;
	}

	public Campo getNumeroNotaFiscalDuplicata() {
		return this.numeroNotaFiscalDuplicata;
	}

	public void setNumeroNotaFiscalDuplicata(Campo numeroNotaFiscalDuplicata) {
		this.numeroNotaFiscalDuplicata = numeroNotaFiscalDuplicata;
	}

	public Campo getNumeroSerieNotaFiscalDuplicata() {
		return this.numeroSerieNotaFiscalDuplicata;
	}

	public void setNumeroSerieNotaFiscalDuplicata(
			Campo numeroSerieNotaFiscalDuplicata) {
		this.numeroSerieNotaFiscalDuplicata = numeroSerieNotaFiscalDuplicata;
	}

	public Campo getCep() {
		return this.cep;
	}

	public void setCep(Campo cep) {
		this.cep = cep;
	}

	public Campo getNomeCedente() {
		return this.nomeCedente;
	}

	public void setNomeCedente(Campo nomeCedente) {
		this.nomeCedente = nomeCedente;
	}

	public Campo getCadastroCedente() {
		return this.cadastroCedente;
	}

	public void setCadastroCedente(Campo cadastroCedente) {
		this.cadastroCedente = cadastroCedente;
	}

	public Campo getChaveDaNota() {
		return this.chaveDaNota;
	}

	public void setChaveDaNota(Campo chaveDaNota) {
		this.chaveDaNota = chaveDaNota;
	}
}
