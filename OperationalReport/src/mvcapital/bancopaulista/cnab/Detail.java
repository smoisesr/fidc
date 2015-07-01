package mvcapital.bancopaulista.cnab;

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
		this.identificacaoDoRegistro.setConteudo("1");
		this.debitoAutomaticoContaCorrente=new Campo(2,2,20,19,false,Register.campoAlfaNumerico,0,"");
		this.coObrigacao=new Campo(3,21,2,2,true,Register.campoNumerico,0,"");
		this.caracteristicaEspecial=new Campo(4,23,24,2,false,Register.campoNumerico,0,"");
		this.modalidadeOperacao=new Campo(5,25,28,4,false,Register.campoNumerico,0,"");
		this.naturezaOperacao=new Campo(6,29,30,2,false,Register.campoNumerico,0,"");
		this.origemRecurso=new Campo(7,31,34,4,false,Register.campoNumerico,0,"");
		this.classeRiscoOperacao=new Campo(8,35,36,2,false,Register.campoNumerico,0,"");
		this.zeros=new Campo(9,37,37,1,false,Register.campoNumerico,0,"");
		this.numeroControleParticipante=new Campo(10,38,62,25,true,Register.campoAlfaNumerico,0,"");
		this.numeroBanco=new Campo(11,63,65,3,true,Register.campoNumerico,0,"");
		this.zeros1=new Campo(12,66,70,5,true,Register.campoNumerico,0,"");
		this.identificacaoTituloBanco=new Campo(13,71,81,11,false,Register.campoNumerico,0,"");
		this.digitoNossoNumero=new Campo(14,82,82,1,false,Register.campoAlfaNumerico,0,"");
		this.valorPago=new Campo(15,83,92,10,false,Register.campoNumerico,2,"");
		this.condicaoParaEmissaoPapeletaCobranca=new Campo(16,93,93,1,false,Register.campoNumerico,0,"");
		this.identificacaoSeEmitePapeletaParaDebitoAutomatico=new Campo(17,94,94,1,false,Register.campoAlfaNumerico,0,"");
		this.dataLiquidacao=new Campo(18,95,100,6,true,Register.campoNumerico,0,"");
		this.identificacaoOperacaoBanco=new Campo(19,101,104,4,false,Register.campoAlfaNumerico,0,"");
		this.indicadorRateioCredito=new Campo(20,105,105,1,false,Register.campoAlfaNumerico,0,"");
		this.enderacamentoParaAvisoDebitoAutomaticoContaCorrente=new Campo(21,106,106,1,false,Register.campoNumerico,0,"");
		this.branco=new Campo(22,107,108,2,false,Register.campoAlfaNumerico,0,"");
		this.identificacaoOcorrencia=new Campo(23,109,110,2,true,Register.campoNumerico,0,"");
		this.numeroDocumento=new Campo(24,111,120,10,true,Register.campoAlfaNumerico,0,"");
		this.dataVencimentoTitulo=new Campo(25,121,126,6,true,Register.campoNumerico,0,"");
		this.valorTitulo=new Campo(26,127,139,13,true,Register.campoNumerico,2,"");
		this.bancoEncarregadoCobranca=new Campo(27,140,142,3,false,Register.campoNumerico,0,"");
		this.agenciaDepositaria=new Campo(28,143,147,2,true,Register.campoNumerico,0,"");
		this.especieTitulo=new Campo(29,148,149,2,true,Register.campoNumerico,0,"");
		this.identificacao=new Campo(30,150,150,1,false,Register.campoAlfaNumerico,0,"");
		this.dataEmissaoTitulo=new Campo(31,151,156,6,true,Register.campoNumerico,0,"");	
		this.primeiraInstrucao=new Campo(32,157,158,2,false,Register.campoNumerico,0,"");		
		this.segundaInstrucao=new Campo(33,159,159,1,false,Register.campoNumerico,0,"");
		this.tipoPessoaCedente=new Campo(34,160,161,2,true,Register.campoAlfaNumerico,0,"");
		this.zeros2=new Campo(35,162,173,11,false,Register.campoAlfaNumerico,0,"");
		this.numeroTermoCessao=new Campo(36,174,192,19,true,Register.campoAlfaNumerico,0,"");
		this.valorPresenteParcela=new Campo(37,193,205,13,true,Register.campoNumerico,2,"");
		this.valorAbatimento=new Campo(38,206,218,13,true,Register.campoNumerico,2,"");
		this.identificacaoTipoInscricaoSacado=new Campo(39,219,220,2,true,Register.campoNumerico,0,"");
		this.numeroInscricaoSacado=new Campo(40,221,234,14,true,Register.campoNumerico,0,"");
		this.nomeSacado=new Campo(41,235,274,40,true,Register.campoAlfaNumerico,0,"");
		this.enderecoCompletoSacado=new Campo(42,275,314,40,true,Register.campoAlfaNumerico,0,"");
		this.numeroNotaFiscalDuplicata=new Campo(43,315,323,9,true,Register.campoAlfaNumerico,0,"");
		this.numeroSerieNotaFiscalDuplicata=new Campo(44,324,326,3,false,Register.campoAlfaNumerico,0,"");
		this.cep=new Campo(45,327,334,8,true,Register.campoNumerico,0,"");
		this.nomeCedente=new Campo(46,335,380,46,true,Register.campoAlfaNumerico,0,"");
		this.cadastroCedente=new Campo(47,381,394,14,true,Register.campoAlfaNumerico,0,"");
		this.chaveDaNota=new Campo(48,395,438,44,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro=new Campo(49,439,444,6,true,Register.campoNumerico,0,"");
	}

	public Campo getDebitoAutomaticoContaCorrente() {
		return debitoAutomaticoContaCorrente;
	}

	public void setDebitoAutomaticoContaCorrente(Campo debitoAutomaticoContaCorrente) {
		this.debitoAutomaticoContaCorrente = debitoAutomaticoContaCorrente;
	}

	public Campo getCoObrigacao() {
		return coObrigacao;
	}

	public void setCoObrigacao(Campo coObrigacao) {
		this.coObrigacao = coObrigacao;
	}

	public Campo getCaracteristicaEspecial() {
		return caracteristicaEspecial;
	}

	public void setCaracteristicaEspecial(Campo caracteristicaEspecial) {
		this.caracteristicaEspecial = caracteristicaEspecial;
	}

	public Campo getModalidadeOperacao() {
		return modalidadeOperacao;
	}

	public void setModalidadeOperacao(Campo modalidadeOperacao) {
		this.modalidadeOperacao = modalidadeOperacao;
	}

	public Campo getNaturezaOperacao() {
		return naturezaOperacao;
	}

	public void setNaturezaOperacao(Campo naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}

	public Campo getOrigemRecurso() {
		return origemRecurso;
	}

	public void setOrigemRecurso(Campo origemRecurso) {
		this.origemRecurso = origemRecurso;
	}

	public Campo getClasseRiscoOperacao() {
		return classeRiscoOperacao;
	}

	public void setClasseRiscoOperacao(Campo classeRiscoOperacao) {
		this.classeRiscoOperacao = classeRiscoOperacao;
	}

	public Campo getZeros() {
		return zeros;
	}

	public void setZeros(Campo zeros) {
		this.zeros = zeros;
	}

	public Campo getNumeroControleParticipante() {
		return numeroControleParticipante;
	}

	public void setNumeroControleParticipante(Campo numeroControleParticipante) {
		this.numeroControleParticipante = numeroControleParticipante;
	}

	public Campo getNumeroBanco() {
		return numeroBanco;
	}

	public void setNumeroBanco(Campo numeroBanco) {
		this.numeroBanco = numeroBanco;
	}

	public Campo getZeros1() {
		return zeros1;
	}

	public void setZeros1(Campo zeros1) {
		this.zeros1 = zeros1;
	}

	public Campo getIdentificacaoTituloBanco() {
		return identificacaoTituloBanco;
	}

	public void setIdentificacaoTituloBanco(Campo identificacaoTituloBanco) {
		this.identificacaoTituloBanco = identificacaoTituloBanco;
	}

	public Campo getDigitoNossoNumero() {
		return digitoNossoNumero;
	}

	public void setDigitoNossoNumero(Campo digitoNossoNumero) {
		this.digitoNossoNumero = digitoNossoNumero;
	}

	public Campo getValorPago() {
		return valorPago;
	}

	public void setValorPago(Campo valorPago) {
		this.valorPago = valorPago;
	}

	public Campo getCondicaoParaEmissaoPapeletaCobranca() {
		return condicaoParaEmissaoPapeletaCobranca;
	}

	public void setCondicaoParaEmissaoPapeletaCobranca(
			Campo condicaoParaEmissaoPapeletaCobranca) {
		this.condicaoParaEmissaoPapeletaCobranca = condicaoParaEmissaoPapeletaCobranca;
	}

	public Campo getIdentificacaoSeEmitePapeletaParaDebitoAutomatico() {
		return identificacaoSeEmitePapeletaParaDebitoAutomatico;
	}

	public void setIdentificacaoSeEmitePapeletaParaDebitoAutomatico(
			Campo identificacaoSeEmitePapeletaParaDebitoAutomatico) {
		this.identificacaoSeEmitePapeletaParaDebitoAutomatico = identificacaoSeEmitePapeletaParaDebitoAutomatico;
	}

	public Campo getDataLiquidacao() {
		return dataLiquidacao;
	}

	public void setDataLiquidacao(Campo dataLiquidacao) {
		this.dataLiquidacao = dataLiquidacao;
	}

	public Campo getIdentificacaoOperacaoBanco() {
		return identificacaoOperacaoBanco;
	}

	public void setIdentificacaoOperacaoBanco(Campo identificacaoOperacaoBanco) {
		this.identificacaoOperacaoBanco = identificacaoOperacaoBanco;
	}

	public Campo getIndicadorRateioCredito() {
		return indicadorRateioCredito;
	}

	public void setIndicadorRateioCredito(Campo indicadorRateioCredito) {
		this.indicadorRateioCredito = indicadorRateioCredito;
	}

	public Campo getEnderacamentoParaAvisoDebitoAutomaticoContaCorrente() {
		return enderacamentoParaAvisoDebitoAutomaticoContaCorrente;
	}

	public void setEnderacamentoParaAvisoDebitoAutomaticoContaCorrente(
			Campo enderacamentoParaAvisoDebitoAutomaticoContaCorrente) {
		this.enderacamentoParaAvisoDebitoAutomaticoContaCorrente = enderacamentoParaAvisoDebitoAutomaticoContaCorrente;
	}

	public Campo getBranco() {
		return branco;
	}

	public void setBranco(Campo branco) {
		this.branco = branco;
	}

	public Campo getIdentificacaoOcorrencia() {
		return identificacaoOcorrencia;
	}

	public void setIdentificacaoOcorrencia(Campo identificacaoOcorrencia) {
		this.identificacaoOcorrencia = identificacaoOcorrencia;
	}

	public Campo getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(Campo numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Campo getDataVencimentoTitulo() {
		return dataVencimentoTitulo;
	}

	public void setDataVencimentoTitulo(Campo dataVencimentoTitulo) {
		this.dataVencimentoTitulo = dataVencimentoTitulo;
	}

	public Campo getValorTitulo() {
		return valorTitulo;
	}

	public void setValorTitulo(Campo valorTitulo) {
		this.valorTitulo = valorTitulo;
	}

	public Campo getBancoEncarregadoCobranca() {
		return bancoEncarregadoCobranca;
	}

	public void setBancoEncarregadoCobranca(Campo bancoEncarregadoCobranca) {
		this.bancoEncarregadoCobranca = bancoEncarregadoCobranca;
	}

	public Campo getAgenciaDepositaria() {
		return agenciaDepositaria;
	}

	public void setAgenciaDepositaria(Campo agenciaDepositaria) {
		this.agenciaDepositaria = agenciaDepositaria;
	}

	public Campo getEspecieTitulo() {
		return especieTitulo;
	}

	public void setEspecieTitulo(Campo especieTitulo) {
		this.especieTitulo = especieTitulo;
	}

	public Campo getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(Campo identificacao) {
		this.identificacao = identificacao;
	}

	public Campo getDataEmissaoTitulo() {
		return dataEmissaoTitulo;
	}

	public void setDataEmissaoTitulo(Campo dataEmissaoTitulo) {
		this.dataEmissaoTitulo = dataEmissaoTitulo;
	}

	public Campo getPrimeiraInstrucao() {
		return primeiraInstrucao;
	}

	public void setPrimeiraInstrucao(Campo primeiraInstrucao) {
		this.primeiraInstrucao = primeiraInstrucao;
	}

	public Campo getSegundaInstrucao() {
		return segundaInstrucao;
	}

	public void setSegundaInstrucao(Campo segundaInstrucao) {
		this.segundaInstrucao = segundaInstrucao;
	}

	public Campo getTipoPessoaCedente() {
		return tipoPessoaCedente;
	}

	public void setTipoPessoaCedente(Campo tipoPessoaCedente) {
		this.tipoPessoaCedente = tipoPessoaCedente;
	}

	public Campo getZeros2() {
		return zeros2;
	}

	public void setZeros2(Campo zeros2) {
		this.zeros2 = zeros2;
	}

	public Campo getNumeroTermoCessao() {
		return numeroTermoCessao;
	}

	public void setNumeroTermoCessao(Campo numeroTermoCessao) {
		this.numeroTermoCessao = numeroTermoCessao;
	}

	public Campo getValorPresenteParcela() {
		return valorPresenteParcela;
	}

	public void setValorPresenteParcela(Campo valorPresenteParcela) {
		this.valorPresenteParcela = valorPresenteParcela;
	}

	public Campo getValorAbatimento() {
		return valorAbatimento;
	}

	public void setValorAbatimento(Campo valorAbatimento) {
		this.valorAbatimento = valorAbatimento;
	}

	public Campo getIdentificacaoTipoInscricaoSacado() {
		return identificacaoTipoInscricaoSacado;
	}

	public void setIdentificacaoTipoInscricaoSacado(
			Campo identificacaoTipoInscricaoSacado) {
		this.identificacaoTipoInscricaoSacado = identificacaoTipoInscricaoSacado;
	}

	public Campo getNumeroInscricaoSacado() {
		return numeroInscricaoSacado;
	}

	public void setNumeroInscricaoSacado(Campo numeroInscricaoSacado) {
		this.numeroInscricaoSacado = numeroInscricaoSacado;
	}

	public Campo getNomeSacado() {
		return nomeSacado;
	}

	public void setNomeSacado(Campo nomeSacado) {
		this.nomeSacado = nomeSacado;
	}

	public Campo getEnderecoCompletoSacado() {
		return enderecoCompletoSacado;
	}

	public void setEnderecoCompletoSacado(Campo enderecoCompletoSacado) {
		this.enderecoCompletoSacado = enderecoCompletoSacado;
	}

	public Campo getNumeroNotaFiscalDuplicata() {
		return numeroNotaFiscalDuplicata;
	}

	public void setNumeroNotaFiscalDuplicata(Campo numeroNotaFiscalDuplicata) {
		this.numeroNotaFiscalDuplicata = numeroNotaFiscalDuplicata;
	}

	public Campo getNumeroSerieNotaFiscalDuplicata() {
		return numeroSerieNotaFiscalDuplicata;
	}

	public void setNumeroSerieNotaFiscalDuplicata(
			Campo numeroSerieNotaFiscalDuplicata) {
		this.numeroSerieNotaFiscalDuplicata = numeroSerieNotaFiscalDuplicata;
	}

	public Campo getCep() {
		return cep;
	}

	public void setCep(Campo cep) {
		this.cep = cep;
	}

	public Campo getNomeCedente() {
		return nomeCedente;
	}

	public void setNomeCedente(Campo nomeCedente) {
		this.nomeCedente = nomeCedente;
	}

	public Campo getCadastroCedente() {
		return cadastroCedente;
	}

	public void setCadastroCedente(Campo cadastroCedente) {
		this.cadastroCedente = cadastroCedente;
	}

	public Campo getChaveDaNota() {
		return chaveDaNota;
	}

	public void setChaveDaNota(Campo chaveDaNota) {
		this.chaveDaNota = chaveDaNota;
	}

}
