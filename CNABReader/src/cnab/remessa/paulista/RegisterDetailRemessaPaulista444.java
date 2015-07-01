package cnab.remessa.paulista;

import cnab.base.Register;
import cnab.base.RegisterField;

public class RegisterDetailRemessaPaulista444  extends Register
{
	private RegisterField idDoRegistro = new RegisterField(1,1,"Identificação do registro","idDoRegistro"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField debitoAutomaticoCC = new RegisterField(2,20,"Débito Automático C/C","debitoAutomaticoCC"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField coobrigacao = new RegisterField(21,22,"Coobrigação","coobrigacao"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField caracteristicaEspecial = new RegisterField(23,24,"Característica Especial","caracteristicaEspecial"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField modalidadeDaOperacao = new RegisterField(25,28,"Modalidade da Operação","modalidadeDaOperacao"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField naturezaDaOperacao = new RegisterField(29,30,"Natureza da Operação","naturezaDaOperacao"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField origemDoRecurso = new RegisterField(31,34,"Origem do Recurso","origemDoRecurso"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField classeRiscoDaOperacao = new RegisterField(35,36,"Classe Risco da Operação","classeRiscoDaOperacao"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField zeros = new RegisterField(37,37,"Zeros","zeros"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField numeroDeControleDoParticipante = new RegisterField(38,62,"Número de Controle do Participante","numeroDeControleDoParticipante"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField numeroDoBanco = new RegisterField(63,65,"Número do Banco","numeroDoBanco"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField zeros2 = new RegisterField(66,70,"Zeros","zeros2"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField idDoTituloNoBanco = new RegisterField(71,81,"Identificação do Título no Banco","idDoTituloNoBanco"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField digitoDoNossoNumero = new RegisterField(82,82,"Dígito do Nosso Número","digitoDoNossoNumero"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField valorPago = new RegisterField(83,92,"Valor Pago","valorPago"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField condicaoParaEmissaoDaPapeletaDeCobranca = new RegisterField(93,93,"Condição para Emissão da Papeleta de Cobrança","condicaoParaEmissaoDaPapeletaDeCobranca"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField idSeEmitePapeletaParaDebitoAutomatico = new RegisterField(94,94,"Identifica se emite papeleta para Débito Automático","idSeEmitePapeletaParaDebitoAutomatico"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField dataDaLiquidacao = new RegisterField(95,100,"Data da Liquidação","dataDaLiquidacao"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField idDaOperacaoDoBanco = new RegisterField(101,104,"Identificação da Operação do Banco","idDaOperacaoDoBanco"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField indicadorRateioCredito = new RegisterField(105,105,"Indicador Rateio Crédito","indicadorRateioCredito"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField enderecamentoParaAvisoDoDebitoAutomaticoEmCC = new RegisterField(106,106,"Endereçamento para Aviso do Débito Automático em Conta Corrente","enderecamentoParaAvisoDoDebitoAutomaticoEmCC"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField branco = new RegisterField(107,108,"Branco","branco"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField idOcorrencia = new RegisterField(109,110,"Identificação Ocorrencia","idOcorrencia"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField numeroDoDocumento = new RegisterField(111,120,"Número do Documento","numeroDoDocumento"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField dataDoVencimentoDoTitulo = new RegisterField(121,126,"Data do Vencimento do Título","dataDoVencimentoDoTitulo"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField valorDoTitulo = new RegisterField(127,139,"Valor do Título (Face)","valorDoTitulo"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField bancoEncarregadoDaCobranca = new RegisterField(140,142,"Banco Encarregado da Cobrança","bancoEncarregadoDaCobranca"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField agenciaDepositaria = new RegisterField(143,147,"Agência Depositária","agenciaDepositaria"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField especieDeTitulo = new RegisterField(148,149,"Espécie de Título","especieDeTitulo"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField identificacao = new RegisterField(150,150,"Identificação","identificacao"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField dataDaEmissaoDoTitulo = new RegisterField(151,156,"Data da Emissão do Título","dataDaEmissaoDoTitulo"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField primeiraInstrucao = new RegisterField(157,158,"Primeira Instrução","primeiraInstrucao"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField segundaInstrucao = new RegisterField(159,159,"Segunda Instrução","segundaInstrucao"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField zeros3 = new RegisterField(160,173,"Zeros","zeros3"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField numeroDoTermoDeCessao = new RegisterField(174,192,"Número do Termo de Cessão","numeroDoTermoDeCessao"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField valorPresenteDaParcela = new RegisterField(193,205,"Valor Presente da Parcela (líquido da parcela)","valorPresenteDaParcela"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField valorDoAbatimento = new RegisterField(206,218,"Valor do Abatimento","valorDoAbatimento"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField identificacaoDoTipoDeInscricaoDoSacado = new RegisterField(219,220,"Identificação do tipo de Inscrição do Sacado","identificacaoDoTipoDeInscricaoDoSacado"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField numeroDeInscricaoDoSacado = new RegisterField(221,234,"Número de Inscrição do sacado","numeroDeInscricaoDoSacado"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField nomeDoSacado = new RegisterField(235,274,"Nome do Sacado","nomeDoSacado"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField enderecoCompletoDoSacado = new RegisterField(275,314,"Endereço completo do Sacado","enderecoCompletoDoSacado"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField numeroDaNotaFiscalDaDuplicata = new RegisterField(315,323,"Número da Nota Fiscal da Duplicata","numeroDaNotaFiscalDaDuplicata"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField numeroDaSerieDaNotaFiscalDaDuplicata = new RegisterField(324,326,"Número da Série da Nota Fiscal da Duplicata","numeroDaSerieDaNotaFiscalDaDuplicata"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField cep = new RegisterField(327,334,"CEP do sacado","cep"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField nomeCedente = new RegisterField(335,380,"Nome do Cedente","nomeCedente"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField cadastroCedente = new RegisterField(381,394,"Cadastro do Cedente","cadastroCedente"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField chaveDaNota = new RegisterField(395,438,"Chave da Nota Fiscal Eletronica","chaveDaNota"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField seqDoRegistro = new RegisterField(439,444,"Número sequencial do Registro","seqDoRegistro"); //$NON-NLS-1$ //$NON-NLS-2$

	public RegisterDetailRemessaPaulista444(String line)
	{
		this.idDoRegistro.setValue(RegisterField.extractString(this.idDoRegistro.getInitialPosition(),this.idDoRegistro.getFinalPosition(),line));
		this.debitoAutomaticoCC.setValue(RegisterField.extractString(this.debitoAutomaticoCC.getInitialPosition(),this.debitoAutomaticoCC.getFinalPosition(),line));
		this.coobrigacao.setValue(RegisterField.extractString(this.coobrigacao.getInitialPosition(),this.coobrigacao.getFinalPosition(),line));
		this.caracteristicaEspecial.setValue(RegisterField.extractString(this.caracteristicaEspecial.getInitialPosition(),this.caracteristicaEspecial.getFinalPosition(),line));
		this.modalidadeDaOperacao.setValue(RegisterField.extractString(this.modalidadeDaOperacao.getInitialPosition(),this.modalidadeDaOperacao.getFinalPosition(),line));
		this.naturezaDaOperacao.setValue(RegisterField.extractString(this.naturezaDaOperacao.getInitialPosition(),this.naturezaDaOperacao.getFinalPosition(),line));
		this.origemDoRecurso.setValue(RegisterField.extractString(this.origemDoRecurso.getInitialPosition(),this.origemDoRecurso.getFinalPosition(),line));
		this.classeRiscoDaOperacao.setValue(RegisterField.extractString(this.classeRiscoDaOperacao.getInitialPosition(),this.classeRiscoDaOperacao.getFinalPosition(),line));
		this.zeros.setValue(RegisterField.extractString(this.zeros.getInitialPosition(),this.zeros.getFinalPosition(),line));
		this.numeroDeControleDoParticipante.setValue(RegisterField.extractString(this.numeroDeControleDoParticipante.getInitialPosition(),this.numeroDeControleDoParticipante.getFinalPosition(),line));
		this.numeroDoBanco.setValue(RegisterField.extractString(this.numeroDoBanco.getInitialPosition(),this.numeroDoBanco.getFinalPosition(),line));
		this.zeros2.setValue(RegisterField.extractString(this.zeros2.getInitialPosition(),this.zeros2.getFinalPosition(),line));
		this.idDoTituloNoBanco.setValue(RegisterField.extractString(this.idDoTituloNoBanco.getInitialPosition(),this.idDoTituloNoBanco.getFinalPosition(),line));
		this.digitoDoNossoNumero.setValue(RegisterField.extractString(this.digitoDoNossoNumero.getInitialPosition(),this.digitoDoNossoNumero.getFinalPosition(),line));
		this.valorPago.setValue(RegisterField.extractString(this.valorPago.getInitialPosition(),this.valorPago.getFinalPosition(),line));
		this.condicaoParaEmissaoDaPapeletaDeCobranca.setValue(RegisterField.extractString(this.condicaoParaEmissaoDaPapeletaDeCobranca.getInitialPosition(),this.condicaoParaEmissaoDaPapeletaDeCobranca.getFinalPosition(),line));
		this.idSeEmitePapeletaParaDebitoAutomatico.setValue(RegisterField.extractString(this.idSeEmitePapeletaParaDebitoAutomatico.getInitialPosition(),this.idSeEmitePapeletaParaDebitoAutomatico.getFinalPosition(),line));
		this.dataDaLiquidacao.setValue(RegisterField.extractString(this.dataDaLiquidacao.getInitialPosition(),this.dataDaLiquidacao.getFinalPosition(),line));
		this.idDaOperacaoDoBanco.setValue(RegisterField.extractString(this.idDaOperacaoDoBanco.getInitialPosition(),this.idDaOperacaoDoBanco.getFinalPosition(),line));
		this.indicadorRateioCredito.setValue(RegisterField.extractString(this.indicadorRateioCredito.getInitialPosition(),this.indicadorRateioCredito.getFinalPosition(),line));
		this.enderecamentoParaAvisoDoDebitoAutomaticoEmCC.setValue(RegisterField.extractString(this.enderecamentoParaAvisoDoDebitoAutomaticoEmCC.getInitialPosition(),this.enderecamentoParaAvisoDoDebitoAutomaticoEmCC.getFinalPosition(),line));
		this.branco.setValue(RegisterField.extractString(this.branco.getInitialPosition(),this.branco.getFinalPosition(),line));
		this.idOcorrencia.setValue(RegisterField.extractString(this.idOcorrencia.getInitialPosition(),this.idOcorrencia.getFinalPosition(),line));
		this.numeroDoDocumento.setValue(RegisterField.extractString(this.numeroDoDocumento.getInitialPosition(),this.numeroDoDocumento.getFinalPosition(),line));
		this.dataDoVencimentoDoTitulo.setValue(RegisterField.extractString(this.dataDoVencimentoDoTitulo.getInitialPosition(),this.dataDoVencimentoDoTitulo.getFinalPosition(),line));
		this.valorDoTitulo.setValue(RegisterField.extractString(this.valorDoTitulo.getInitialPosition(),this.valorDoTitulo.getFinalPosition(),line));
		this.bancoEncarregadoDaCobranca.setValue(RegisterField.extractString(this.bancoEncarregadoDaCobranca.getInitialPosition(),this.bancoEncarregadoDaCobranca.getFinalPosition(),line));
		this.agenciaDepositaria.setValue(RegisterField.extractString(this.agenciaDepositaria.getInitialPosition(),this.agenciaDepositaria.getFinalPosition(),line));
		this.especieDeTitulo.setValue(RegisterField.extractString(this.especieDeTitulo.getInitialPosition(),this.especieDeTitulo.getFinalPosition(),line));
		this.identificacao.setValue(RegisterField.extractString(this.identificacao.getInitialPosition(),this.identificacao.getFinalPosition(),line));
		this.dataDaEmissaoDoTitulo.setValue(RegisterField.extractString(this.dataDaEmissaoDoTitulo.getInitialPosition(),this.dataDaEmissaoDoTitulo.getFinalPosition(),line));
		this.primeiraInstrucao.setValue(RegisterField.extractString(this.primeiraInstrucao.getInitialPosition(),this.primeiraInstrucao.getFinalPosition(),line));
		this.segundaInstrucao.setValue(RegisterField.extractString(this.segundaInstrucao.getInitialPosition(),this.segundaInstrucao.getFinalPosition(),line));
		this.zeros3.setValue(RegisterField.extractString(this.zeros3.getInitialPosition(),this.zeros3.getFinalPosition(),line));
		this.numeroDoTermoDeCessao.setValue(RegisterField.extractString(this.numeroDoTermoDeCessao.getInitialPosition(),this.numeroDoTermoDeCessao.getFinalPosition(),line));
		this.valorPresenteDaParcela.setValue(RegisterField.extractString(this.valorPresenteDaParcela.getInitialPosition(),this.valorPresenteDaParcela.getFinalPosition(),line));
		this.valorDoAbatimento.setValue(RegisterField.extractString(this.valorDoAbatimento.getInitialPosition(),this.valorDoAbatimento.getFinalPosition(),line));
		this.identificacaoDoTipoDeInscricaoDoSacado.setValue(RegisterField.extractString(this.identificacaoDoTipoDeInscricaoDoSacado.getInitialPosition(),this.identificacaoDoTipoDeInscricaoDoSacado.getFinalPosition(),line));
		this.numeroDeInscricaoDoSacado.setValue(RegisterField.extractString(this.numeroDeInscricaoDoSacado.getInitialPosition(),this.numeroDeInscricaoDoSacado.getFinalPosition(),line));
		this.nomeDoSacado.setValue(RegisterField.extractString(this.nomeDoSacado.getInitialPosition(),this.nomeDoSacado.getFinalPosition(),line));
		this.enderecoCompletoDoSacado.setValue(RegisterField.extractString(this.enderecoCompletoDoSacado.getInitialPosition(),this.enderecoCompletoDoSacado.getFinalPosition(),line));
		this.numeroDaNotaFiscalDaDuplicata.setValue(RegisterField.extractString(this.numeroDaNotaFiscalDaDuplicata.getInitialPosition(),this.numeroDaNotaFiscalDaDuplicata.getFinalPosition(),line));
		this.numeroDaSerieDaNotaFiscalDaDuplicata.setValue(RegisterField.extractString(this.numeroDaSerieDaNotaFiscalDaDuplicata.getInitialPosition(),this.numeroDaSerieDaNotaFiscalDaDuplicata.getFinalPosition(),line));
		this.cep.setValue(RegisterField.extractString(this.cep.getInitialPosition(),this.cep.getFinalPosition(),line));
		this.nomeCedente.setValue(RegisterField.extractString(this.nomeCedente.getInitialPosition(),this.nomeCedente.getFinalPosition(),line));
		this.cadastroCedente.setValue(RegisterField.extractString(this.cadastroCedente.getInitialPosition(),this.cadastroCedente.getFinalPosition(),line));
		this.chaveDaNota.setValue(RegisterField.extractString(this.chaveDaNota.getInitialPosition(),this.chaveDaNota.getFinalPosition(),line));
		this.seqDoRegistro.setValue(RegisterField.extractString(this.seqDoRegistro.getInitialPosition(),this.seqDoRegistro.getFinalPosition(),line));

	}
	
	public static String labelsCSV()
	{
		String stringLabels="idDoRegistro"				 //$NON-NLS-1$
							+ ";" +	"debitoAutomaticoCC" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"coobrigacao" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"caracteristicaEspecial" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"modalidadeDaOperacao" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"naturezaDaOperacao" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"origemDoRecurso" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"classeRiscoDaOperacao" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"zeros" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"numeroDeControleDoParticipante" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"numeroDoBanco" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"zeros2" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"idDoTituloNoBanco" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"digitoDoNossoNumero" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"valorPago" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"condicaoParaEmissaoDaPapeletaDeCobranca" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"idSeEmitePapeletaParaDebitoAutomatico" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"dataDaLiquidacao" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"idDaOperacaoDoBanco" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"indicadorRateioCredito" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"enderecamentoParaAvisoDoDebitoAutomaticoEmCC" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"branco" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"idOcorrencia" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"numeroDoDocumento" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"dataDoVencimentoDoTitulo" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"valorDoTitulo" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"bancoEncarregadoDaCobranca" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"agenciaDepositaria" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"especieDeTitulo" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"identificacao" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"dataDaEmissaoDoTitulo" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"primeiraInstrucao" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"segundaInstrucao" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"zeros3" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"numeroDoTermoDeCessao" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"valorPresenteDaParcela" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"valorDoAbatimento" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"identificacaoDoTipoDeInscricaoDoSacado" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"numeroDeInscricaoDoSacado" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"nomeDoSacado" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"enderecoCompletoDoSacado" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"numeroDaNotaFiscalDaDuplicata" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"numeroDaSerieDaNotaFiscalDaDuplicata" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"cep" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"nomeCedente" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"cadastroCedente" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" + "chaveDaNota" //$NON-NLS-1$ //$NON-NLS-2$
							+ ";" +	"seqDoRegistro"; //$NON-NLS-1$ //$NON-NLS-2$

		return stringLabels;
	}

	public String toCSV()
	{
		String stringCSV=		
				this.idDoRegistro.getValue().toString()
				+ ";" + this.debitoAutomaticoCC.getValue().toString() //$NON-NLS-1$
				+ ";" + this.coobrigacao.getValue().toString() //$NON-NLS-1$
				+ ";" + this.caracteristicaEspecial.getValue().toString() //$NON-NLS-1$
				+ ";" + this.modalidadeDaOperacao.getValue().toString() //$NON-NLS-1$
				+ ";" + this.naturezaDaOperacao.getValue().toString() //$NON-NLS-1$
				+ ";" + this.origemDoRecurso.getValue().toString() //$NON-NLS-1$
				+ ";" + this.classeRiscoDaOperacao.getValue().toString() //$NON-NLS-1$
				+ ";" + this.zeros.getValue().toString() //$NON-NLS-1$
				+ ";" + this.numeroDeControleDoParticipante.getValue().toString() //$NON-NLS-1$
				+ ";" + this.numeroDoBanco.getValue().toString() //$NON-NLS-1$
				+ ";" + this.zeros2.getValue().toString() //$NON-NLS-1$
				+ ";" + this.idDoTituloNoBanco.getValue().toString() //$NON-NLS-1$
				+ ";" + this.digitoDoNossoNumero.getValue().toString() //$NON-NLS-1$
				+ ";" + this.valorPago.getValue().toString() //$NON-NLS-1$
				+ ";" + this.condicaoParaEmissaoDaPapeletaDeCobranca.getValue().toString() //$NON-NLS-1$
				+ ";" + this.idSeEmitePapeletaParaDebitoAutomatico.getValue().toString() //$NON-NLS-1$
				+ ";" + this.dataDaLiquidacao.getValue().toString() //$NON-NLS-1$
				+ ";" + this.idDaOperacaoDoBanco.getValue().toString() //$NON-NLS-1$
				+ ";" + this.indicadorRateioCredito.getValue().toString() //$NON-NLS-1$
				+ ";" + this.enderecamentoParaAvisoDoDebitoAutomaticoEmCC.getValue().toString() //$NON-NLS-1$
				+ ";" + this.branco.getValue().toString() //$NON-NLS-1$
				+ ";" + this.idOcorrencia.getValue().toString() //$NON-NLS-1$
				+ ";" + this.numeroDoDocumento.getValue().toString() //$NON-NLS-1$
				+ ";" + this.dataDoVencimentoDoTitulo.getValue().toString() //$NON-NLS-1$
				+ ";" + this.valorDoTitulo.getValue().toString() //$NON-NLS-1$
				+ ";" + this.bancoEncarregadoDaCobranca.getValue().toString() //$NON-NLS-1$
				+ ";" + this.agenciaDepositaria.getValue().toString() //$NON-NLS-1$
				+ ";" + this.especieDeTitulo.getValue().toString() //$NON-NLS-1$
				+ ";" + this.identificacao.getValue().toString() //$NON-NLS-1$
				+ ";" + this.dataDaEmissaoDoTitulo.getValue().toString() //$NON-NLS-1$
				+ ";" + this.primeiraInstrucao.getValue().toString() //$NON-NLS-1$
				+ ";" + this.segundaInstrucao.getValue().toString() //$NON-NLS-1$
				+ ";" + this.zeros3.getValue().toString() //$NON-NLS-1$
				+ ";" + this.numeroDoTermoDeCessao.getValue().toString() //$NON-NLS-1$
				+ ";" + this.valorPresenteDaParcela.getValue().toString() //$NON-NLS-1$
				+ ";" + this.valorDoAbatimento.getValue().toString() //$NON-NLS-1$
				+ ";" + this.identificacaoDoTipoDeInscricaoDoSacado.getValue().toString() //$NON-NLS-1$
				+ ";" + this.numeroDeInscricaoDoSacado.getValue().toString() //$NON-NLS-1$
				+ ";" + this.nomeDoSacado.getValue().toString() //$NON-NLS-1$
				+ ";" + this.enderecoCompletoDoSacado.getValue().toString() //$NON-NLS-1$
				+ ";" + this.numeroDaNotaFiscalDaDuplicata.getValue().toString() //$NON-NLS-1$
				+ ";" + this.numeroDaSerieDaNotaFiscalDaDuplicata.getValue().toString() //$NON-NLS-1$
				+ ";" + this.cep.getValue().toString() //$NON-NLS-1$
				+ ";" + this.nomeCedente.getValue().toString() //$NON-NLS-1$
				+ ";" + this.cadastroCedente.getValue().toString() //$NON-NLS-1$
				+ ";" + this.chaveDaNota.getValue().toString() //$NON-NLS-1$
				+ ";" + this.seqDoRegistro.getValue().toString();		 //$NON-NLS-1$
		return stringCSV;
	}
	
	public void showRegister()
	{
		System.out.print(this.idDoRegistro.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.debitoAutomaticoCC.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.coobrigacao.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.caracteristicaEspecial.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.modalidadeDaOperacao.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.naturezaDaOperacao.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.origemDoRecurso.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.classeRiscoDaOperacao.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.zeros.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.numeroDeControleDoParticipante.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.numeroDoBanco.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.zeros2.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.idDoTituloNoBanco.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.digitoDoNossoNumero.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.valorPago.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.condicaoParaEmissaoDaPapeletaDeCobranca.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.idSeEmitePapeletaParaDebitoAutomatico.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.dataDaLiquidacao.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.idDaOperacaoDoBanco.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.indicadorRateioCredito.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.enderecamentoParaAvisoDoDebitoAutomaticoEmCC.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.branco.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.idOcorrencia.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.numeroDoDocumento.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.dataDoVencimentoDoTitulo.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.valorDoTitulo.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.bancoEncarregadoDaCobranca.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.agenciaDepositaria.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.especieDeTitulo.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.identificacao.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.dataDaEmissaoDoTitulo.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.primeiraInstrucao.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.segundaInstrucao.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.zeros3.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.numeroDoTermoDeCessao.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.valorPresenteDaParcela.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.valorDoAbatimento.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.identificacaoDoTipoDeInscricaoDoSacado.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.numeroDeInscricaoDoSacado.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.nomeDoSacado.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.enderecoCompletoDoSacado.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.numeroDaNotaFiscalDaDuplicata.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.numeroDaSerieDaNotaFiscalDaDuplicata.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.cep.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.nomeCedente.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.cadastroCedente.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.seqDoRegistro.getValue().toString()+"\n"); //$NON-NLS-1$

	}
	public RegisterField getIdDoRegistro() {
		return this.idDoRegistro;
	}

	public void setIdDoRegistro(RegisterField idDoRegistro) {
		this.idDoRegistro = idDoRegistro;
	}

	public RegisterField getDebitoAutomaticoCC() {
		return this.debitoAutomaticoCC;
	}

	public void setDebitoAutomaticoCC(RegisterField debitoAutomaticoCC) {
		this.debitoAutomaticoCC = debitoAutomaticoCC;
	}

	public RegisterField getCoobrigacao() {
		return this.coobrigacao;
	}

	public void setCoobrigacao(RegisterField coobrigacao) {
		this.coobrigacao = coobrigacao;
	}

	public RegisterField getCaracteristicaEspecial() {
		return this.caracteristicaEspecial;
	}

	public void setCaracteristicaEspecial(RegisterField caracteristicaEspecial) {
		this.caracteristicaEspecial = caracteristicaEspecial;
	}

	public RegisterField getModalidadeDaOperacao() {
		return this.modalidadeDaOperacao;
	}

	public void setModalidadeDaOperacao(RegisterField modalidadeDaOperacao) {
		this.modalidadeDaOperacao = modalidadeDaOperacao;
	}

	public RegisterField getNaturezaDaOperacao() {
		return this.naturezaDaOperacao;
	}

	public void setNaturezaDaOperacao(RegisterField naturezaDaOperacao) {
		this.naturezaDaOperacao = naturezaDaOperacao;
	}

	public RegisterField getOrigemDoRecurso() {
		return this.origemDoRecurso;
	}

	public void setOrigemDoRecurso(RegisterField origemDoRecurso) {
		this.origemDoRecurso = origemDoRecurso;
	}

	public RegisterField getClasseRiscoDaOperacao() {
		return this.classeRiscoDaOperacao;
	}

	public void setClasseRiscoDaOperacao(RegisterField classeRiscoDaOperacao) {
		this.classeRiscoDaOperacao = classeRiscoDaOperacao;
	}

	public RegisterField getZeros() {
		return this.zeros;
	}

	public void setZeros(RegisterField zeros) {
		this.zeros = zeros;
	}

	public RegisterField getNumeroDeControleDoParticipante() {
		return this.numeroDeControleDoParticipante;
	}

	public void setNumeroDeControleDoParticipante(
			RegisterField numeroDeControleDoParticipante) {
		this.numeroDeControleDoParticipante = numeroDeControleDoParticipante;
	}

	public RegisterField getNumeroDoBanco() {
		return this.numeroDoBanco;
	}

	public void setNumeroDoBanco(RegisterField numeroDoBanco) {
		this.numeroDoBanco = numeroDoBanco;
	}

	public RegisterField getZeros2() {
		return this.zeros2;
	}

	public void setZeros2(RegisterField zeros2) {
		this.zeros2 = zeros2;
	}

	public RegisterField getIdDoTituloNoBanco() {
		return this.idDoTituloNoBanco;
	}

	public void setIdDoTituloNoBanco(RegisterField idDoTituloNoBanco) {
		this.idDoTituloNoBanco = idDoTituloNoBanco;
	}

	public RegisterField getDigitoDoNossoNumero() {
		return this.digitoDoNossoNumero;
	}

	public void setDigitoDoNossoNumero(RegisterField digitoDoNossoNumero) {
		this.digitoDoNossoNumero = digitoDoNossoNumero;
	}

	public RegisterField getValorPago() {
		return this.valorPago;
	}

	public void setValorPago(RegisterField valorPago) {
		this.valorPago = valorPago;
	}

	public RegisterField getCondicaoParaEmissaoDaPapeletaDeCobranca() {
		return this.condicaoParaEmissaoDaPapeletaDeCobranca;
	}

	public void setCondicaoParaEmissaoDaPapeletaDeCobranca(
			RegisterField condicaoParaEmissaoDaPapeletaDeCobranca) {
		this.condicaoParaEmissaoDaPapeletaDeCobranca = condicaoParaEmissaoDaPapeletaDeCobranca;
	}

	public RegisterField getIdSeEmitePapeletaParaDebitoAutomatico() {
		return this.idSeEmitePapeletaParaDebitoAutomatico;
	}

	public void setIdSeEmitePapeletaParaDebitoAutomatico(
			RegisterField idSeEmitePapeletaParaDebitoAutomatico) {
		this.idSeEmitePapeletaParaDebitoAutomatico = idSeEmitePapeletaParaDebitoAutomatico;
	}

	public RegisterField getDataDaLiquidacao() {
		return this.dataDaLiquidacao;
	}

	public void setDataDaLiquidacao(RegisterField dataDaLiquidacao) {
		this.dataDaLiquidacao = dataDaLiquidacao;
	}

	public RegisterField getIdDaOperacaoDoBanco() {
		return this.idDaOperacaoDoBanco;
	}

	public void setIdDaOperacaoDoBanco(RegisterField idDaOperacaoDoBanco) {
		this.idDaOperacaoDoBanco = idDaOperacaoDoBanco;
	}

	public RegisterField getIndicadorRateioCredito() {
		return this.indicadorRateioCredito;
	}

	public void setIndicadorRateioCredito(RegisterField indicadorRateioCredito) {
		this.indicadorRateioCredito = indicadorRateioCredito;
	}

	public RegisterField getEnderecamentoParaAvisoDoDebitoAutomaticoEmCC() {
		return this.enderecamentoParaAvisoDoDebitoAutomaticoEmCC;
	}

	public void setEnderecamentoParaAvisoDoDebitoAutomaticoEmCC(
			RegisterField enderecamentoParaAvisoDoDebitoAutomaticoEmCC) {
		this.enderecamentoParaAvisoDoDebitoAutomaticoEmCC = enderecamentoParaAvisoDoDebitoAutomaticoEmCC;
	}

	public RegisterField getBranco() {
		return this.branco;
	}

	public void setBranco(RegisterField branco) {
		this.branco = branco;
	}

	public RegisterField getIdOcorrencia() {
		return this.idOcorrencia;
	}

	public void setIdOcorrencia(RegisterField idOcorrencia) {
		this.idOcorrencia = idOcorrencia;
	}

	public RegisterField getNumeroDoDocumento() {
		return this.numeroDoDocumento;
	}

	public void setNumeroDoDocumento(RegisterField numeroDoDocumento) {
		this.numeroDoDocumento = numeroDoDocumento;
	}

	public RegisterField getDataDoVencimentoDoTitulo() {
		return this.dataDoVencimentoDoTitulo;
	}

	public void setDataDoVencimentoDoTitulo(RegisterField dataDoVencimentoDoTitulo) {
		this.dataDoVencimentoDoTitulo = dataDoVencimentoDoTitulo;
	}

	public RegisterField getValorDoTitulo() {
		return this.valorDoTitulo;
	}

	public void setValorDoTitulo(RegisterField valorDoTitulo) {
		this.valorDoTitulo = valorDoTitulo;
	}

	public RegisterField getBancoEncarregadoDaCobranca() {
		return this.bancoEncarregadoDaCobranca;
	}

	public void setBancoEncarregadoDaCobranca(
			RegisterField bancoEncarregadoDaCobranca) {
		this.bancoEncarregadoDaCobranca = bancoEncarregadoDaCobranca;
	}

	public RegisterField getAgenciaDepositaria() {
		return this.agenciaDepositaria;
	}

	public void setAgenciaDepositaria(RegisterField agenciaDepositaria) {
		this.agenciaDepositaria = agenciaDepositaria;
	}

	public RegisterField getEspecieDeTitulo() {
		return this.especieDeTitulo;
	}

	public void setEspecieDeTitulo(RegisterField especieDeTitulo) {
		this.especieDeTitulo = especieDeTitulo;
	}

	public RegisterField getIdentificacao() {
		return this.identificacao;
	}

	public void setIdentificacao(RegisterField identificacao) {
		this.identificacao = identificacao;
	}

	public RegisterField getDataDaEmissaoDoTitulo() {
		return this.dataDaEmissaoDoTitulo;
	}

	public void setDataDaEmissaoDoTitulo(RegisterField dataDaEmissaoDoTitulo) {
		this.dataDaEmissaoDoTitulo = dataDaEmissaoDoTitulo;
	}

	public RegisterField getPrimeiraInstrucao() {
		return this.primeiraInstrucao;
	}

	public void setPrimeiraInstrucao(RegisterField primeiraInstrucao) {
		this.primeiraInstrucao = primeiraInstrucao;
	}

	public RegisterField getSegundaInstrucao() {
		return this.segundaInstrucao;
	}

	public void setSegundaInstrucao(RegisterField segundaInstrucao) {
		this.segundaInstrucao = segundaInstrucao;
	}

	public RegisterField getZeros3() {
		return this.zeros3;
	}

	public void setZeros3(RegisterField zeros3) {
		this.zeros3 = zeros3;
	}

	public RegisterField getNumeroDoTermoDeCessao() {
		return this.numeroDoTermoDeCessao;
	}

	public void setNumeroDoTermoDeCessao(RegisterField numeroDoTermoDeCessao) {
		this.numeroDoTermoDeCessao = numeroDoTermoDeCessao;
	}

	public RegisterField getValorPresenteDaParcela() {
		return this.valorPresenteDaParcela;
	}

	public void setValorPresenteDaParcela(RegisterField valorPresenteDaParcela) {
		this.valorPresenteDaParcela = valorPresenteDaParcela;
	}

	public RegisterField getValorDoAbatimento() {
		return this.valorDoAbatimento;
	}

	public void setValorDoAbatimento(RegisterField valorDoAbatimento) {
		this.valorDoAbatimento = valorDoAbatimento;
	}

	public RegisterField getIdentificacaoDoTipoDeInscricaoDoSacado() {
		return this.identificacaoDoTipoDeInscricaoDoSacado;
	}

	public void setIdentificacaoDoTipoDeInscricaoDoSacado(
			RegisterField identificacaoDoTipoDeInscricaoDoSacado) {
		this.identificacaoDoTipoDeInscricaoDoSacado = identificacaoDoTipoDeInscricaoDoSacado;
	}

	public RegisterField getNumeroDeInscricaoDoSacado() {
		return this.numeroDeInscricaoDoSacado;
	}

	public void setNumeroDeInscricaoDoSacado(RegisterField numeroDeInscricaoDoSacado) {
		this.numeroDeInscricaoDoSacado = numeroDeInscricaoDoSacado;
	}

	public RegisterField getNomeDoSacado() {
		return this.nomeDoSacado;
	}

	public void setNomeDoSacado(RegisterField nomeDoSacado) {
		this.nomeDoSacado = nomeDoSacado;
	}

	public RegisterField getEnderecoCompletoDoSacado() {
		return this.enderecoCompletoDoSacado;
	}

	public void setEnderecoCompletoDoSacado(RegisterField enderecoCompletoDoSacado) {
		this.enderecoCompletoDoSacado = enderecoCompletoDoSacado;
	}

	public RegisterField getNumeroDaNotaFiscalDaDuplicata() {
		return this.numeroDaNotaFiscalDaDuplicata;
	}

	public void setNumeroDaNotaFiscalDaDuplicata(
			RegisterField numeroDaNotaFiscalDaDuplicata) {
		this.numeroDaNotaFiscalDaDuplicata = numeroDaNotaFiscalDaDuplicata;
	}

	public RegisterField getNumeroDaSerieDaNotaFiscalDaDuplicata() {
		return this.numeroDaSerieDaNotaFiscalDaDuplicata;
	}

	public void setNumeroDaSerieDaNotaFiscalDaDuplicata(
			RegisterField numeroDaSerieDaNotaFiscalDaDuplicata) {
		this.numeroDaSerieDaNotaFiscalDaDuplicata = numeroDaSerieDaNotaFiscalDaDuplicata;
	}

	public RegisterField getCep() {
		return this.cep;
	}

	public void setCep(RegisterField cep) {
		this.cep = cep;
	}

	public RegisterField getSeqDoRegistro() {
		return this.seqDoRegistro;
	}

	public void setSeqDoRegistro(RegisterField seqDoRegistro) {
		this.seqDoRegistro = seqDoRegistro;
	}

	public RegisterField getChaveDaNota()
	{
		return this.chaveDaNota;
	}

	public void setChaveDaNota(RegisterField chaveDaNota)
	{
		this.chaveDaNota = chaveDaNota;
	}
}
