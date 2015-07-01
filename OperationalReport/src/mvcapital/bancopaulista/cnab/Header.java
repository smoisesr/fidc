package mvcapital.bancopaulista.cnab;

public class Header extends Register 
{
	private Campo identificacaoArquivoRemessa=new Campo();
	private Campo literalRemessa=new Campo();
	private Campo codigoServico=new Campo();
	private Campo literalServico=new Campo();
	private Campo codigoOriginador=new Campo();
	private Campo nomeOriginador=new Campo();
	private Campo numeroBancoPaulista=new Campo();
	private Campo nomeBanco=new Campo();
	private Campo dataGravacaoArquivo=new Campo();
	private Campo branco=new Campo();
	private Campo identificacaoSistema=new Campo();
	private Campo numeroSequencialArquivo=new Campo();
	private Campo branco1=new Campo();
	
	
	public Header() 
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
		this.identificacaoDoRegistro.setConteudo("0");
		this.identificacaoArquivoRemessa=new Campo(2,2,2,1,true,Register.campoNumerico,0,"1");
		this.literalRemessa=new Campo(3,3,9,7,true,Register.campoAlfaNumerico,0,"");
		this.codigoServico=new Campo(4,10,11,2,true,Register.campoNumerico,0,"01");
		this.literalServico=new Campo(5,12,26,15,true,Register.campoAlfaNumerico,0,"");
		this.codigoOriginador=new Campo(6,27,46,20,true,Register.campoNumerico,0,"");
		this.nomeOriginador=new Campo(7,47,76,30,true,Register.campoAlfaNumerico,0,"");
		this.numeroBancoPaulista=new Campo(8,77,79,3,true,Register.campoNumerico,0,"611");
		this.nomeBanco=new Campo(9,80,94,15,true,Register.campoAlfaNumerico,0,"PAULISTA S.A.");
		this.dataGravacaoArquivo=new Campo(10,95,100,6,true,Register.campoNumerico,0,"");
		this.branco=new Campo(11,101,108,8,true,Register.campoAlfaNumerico,0,"");
		this.identificacaoSistema=new Campo(12,109,110,2,true,Register.campoAlfaNumerico,0,"MX");
		this.numeroSequencialArquivo=new Campo(13,111,117,7,true,Register.campoNumerico,0,"");
		this.branco1=new Campo(14,118,394,277,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro=new Campo(15,395,400,6,true,Register.campoNumerico,0,"");
	}


	public Campo getIdentificacaoArquivoRemessa() {
		return identificacaoArquivoRemessa;
	}


	public void setIdentificacaoArquivoRemessa(Campo identificacaoArquivoRemessa) {
		this.identificacaoArquivoRemessa = identificacaoArquivoRemessa;
	}


	public Campo getLiteralRemessa() {
		return literalRemessa;
	}


	public void setLiteralRemessa(Campo literalRemessa) {
		this.literalRemessa = literalRemessa;
	}


	public Campo getCodigoServico() {
		return codigoServico;
	}


	public void setCodigoServico(Campo codigoServico) {
		this.codigoServico = codigoServico;
	}


	public Campo getLiteralServico() {
		return literalServico;
	}


	public void setLiteralServico(Campo literalServico) {
		this.literalServico = literalServico;
	}


	public Campo getCodigoOriginador() {
		return codigoOriginador;
	}


	public void setCodigoOriginador(Campo codigoOriginador) {
		this.codigoOriginador = codigoOriginador;
	}


	public Campo getNomeOriginador() {
		return nomeOriginador;
	}


	public void setNomeOriginador(Campo nomeOriginador) {
		this.nomeOriginador = nomeOriginador;
	}


	public Campo getNumeroBancoPaulista() {
		return numeroBancoPaulista;
	}


	public void setNumeroBancoPaulista(Campo numeroBancoPaulista) {
		this.numeroBancoPaulista = numeroBancoPaulista;
	}


	public Campo getNomeBanco() {
		return nomeBanco;
	}


	public void setNomeBanco(Campo nomeBanco) {
		this.nomeBanco = nomeBanco;
	}


	public Campo getDataGravacaoArquivo() {
		return dataGravacaoArquivo;
	}


	public void setDataGravacaoArquivo(Campo dataGravacaoArquivo) {
		this.dataGravacaoArquivo = dataGravacaoArquivo;
	}


	public Campo getBranco() {
		return branco;
	}


	public void setBranco(Campo branco) {
		this.branco = branco;
	}


	public Campo getIdentificacaoSistema() {
		return identificacaoSistema;
	}


	public void setIdentificacaoSistema(Campo identificacaoSistema) {
		this.identificacaoSistema = identificacaoSistema;
	}


	public Campo getNumeroSequencialArquivo() {
		return numeroSequencialArquivo;
	}


	public void setNumeroSequencialArquivo(Campo numeroSequencialArquivo) {
		this.numeroSequencialArquivo = numeroSequencialArquivo;
	}


	public Campo getBranco1() {
		return branco1;
	}


	public void setBranco1(Campo branco1) {
		this.branco1 = branco1;
	}


	public Campo getNumeroSequencialRegistro() {
		return numeroSequencialRegistro;
	}


	public void setNumeroSequencialRegistro(Campo numeroSequencialRegistro) {
		this.numeroSequencialRegistro = numeroSequencialRegistro;
	}

}
