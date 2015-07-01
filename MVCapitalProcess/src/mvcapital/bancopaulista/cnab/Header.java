package mvcapital.bancopaulista.cnab;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;
import mvcapital.fundo.FundoDeInvestimento;

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
	
	public static SimpleDateFormat sdfCNAB = new SimpleDateFormat("ddMMyy"); //$NON-NLS-1$
	private static int sequenciaArquivo=1;
	
	public Header() 
	{
		super();
		this.setDefaults();
	}
	
	public Header(String codigoDoOriginador, FundoDeInvestimento fundo)
	{
		super();
		this.setDefaults();
		this.codigoOriginador.setConteudo(codigoDoOriginador);
	}
	
	public Header(String line)
	{
		this();
		this.identificacaoRegistro.setConteudo(line.substring(this.identificacaoRegistro.getPosicaoInicial()-1,this.identificacaoRegistro.getPosicaoFinal()));
		this.identificacaoArquivoRemessa.setConteudo(line.substring(this.identificacaoArquivoRemessa.getPosicaoInicial()-1,this.identificacaoArquivoRemessa.getPosicaoFinal()));
		this.literalRemessa.setConteudo(line.substring(this.literalRemessa.getPosicaoInicial()-1,this.literalRemessa.getPosicaoFinal()));
		this.codigoServico.setConteudo(line.substring(this.codigoServico.getPosicaoInicial()-1,this.codigoServico.getPosicaoFinal()));
		this.literalServico.setConteudo(line.substring(this.literalServico.getPosicaoInicial()-1,this.literalServico.getPosicaoFinal()));
		this.codigoOriginador.setConteudo(line.substring(this.codigoOriginador.getPosicaoInicial()-1,this.codigoOriginador.getPosicaoFinal()));
		this.nomeOriginador.setConteudo(line.substring(this.nomeOriginador.getPosicaoInicial()-1,this.nomeOriginador.getPosicaoFinal()));
		this.numeroBancoPaulista.setConteudo(line.substring(this.numeroBancoPaulista.getPosicaoInicial()-1,this.numeroBancoPaulista.getPosicaoFinal()));
		this.nomeBanco.setConteudo(line.substring(this.nomeBanco.getPosicaoInicial()-1,this.nomeBanco.getPosicaoFinal()));
		this.dataGravacaoArquivo.setConteudo(line.substring(this.dataGravacaoArquivo.getPosicaoInicial()-1,this.dataGravacaoArquivo.getPosicaoFinal()));
		this.branco.setConteudo(line.substring(this.branco.getPosicaoInicial()-1,this.branco.getPosicaoFinal()));
		this.identificacaoSistema.setConteudo(line.substring(this.identificacaoSistema.getPosicaoInicial()-1,this.identificacaoSistema.getPosicaoFinal()));
		this.numeroSequencialArquivo.setConteudo(line.substring(this.numeroSequencialArquivo.getPosicaoInicial()-1,this.numeroSequencialArquivo.getPosicaoFinal()));
		this.branco1.setConteudo(line.substring(this.branco1.getPosicaoInicial()-1,this.branco1.getPosicaoFinal()));
		this.numeroSequencialRegistro.setConteudo(line.substring(this.numeroSequencialRegistro.getPosicaoInicial()-1,this.numeroSequencialRegistro.getPosicaoFinal()));
		
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder(); 
		sb.append(this.identificacaoRegistro.getConteudo());
		sb.append(this.identificacaoArquivoRemessa.getConteudo());
		sb.append(this.literalRemessa.getConteudo());
		sb.append(this.codigoServico.getConteudo());
		sb.append(this.literalServico.getConteudo());
		sb.append(this.codigoOriginador.getConteudo());
		sb.append(this.nomeOriginador.getConteudo());
		sb.append(this.numeroBancoPaulista.getConteudo());
		sb.append(this.nomeBanco.getConteudo());
		sb.append(this.dataGravacaoArquivo.getConteudo());
		sb.append(this.branco.getConteudo());
		sb.append(this.identificacaoSistema.getConteudo());
		sb.append(this.numeroSequencialArquivo.getConteudo());
		sb.append(this.branco1.getConteudo());
		sb.append(this.numeroSequencialRegistro.getConteudo());
		return sb.toString();
	}

	public String toCSV()
	{
		StringBuilder sb = new StringBuilder(); 
		sb.append(this.identificacaoRegistro.getConteudo());
		sb.append(";"+this.identificacaoArquivoRemessa.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.literalRemessa.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.codigoServico.getConteudo());  //$NON-NLS-1$
		sb.append(";"+this.literalServico.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.codigoOriginador.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.nomeOriginador.getConteudo());  //$NON-NLS-1$
		sb.append(";"+this.numeroBancoPaulista.getConteudo());  //$NON-NLS-1$
		sb.append(";"+this.nomeBanco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.dataGravacaoArquivo.getConteudo());  //$NON-NLS-1$
		sb.append(";"+this.branco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.identificacaoSistema.getConteudo());  //$NON-NLS-1$
		sb.append(";"+this.numeroSequencialArquivo.getConteudo());  //$NON-NLS-1$
		sb.append(";"+this.branco1.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroSequencialRegistro.getConteudo()); //$NON-NLS-1$
		return sb.toString();
	}	
	
	public static String csvColumns()
	{
		return "identificacaoRegistro;identificacaoArquivoRemessa;literalRemessa;codigoServico;literalServico;codigoOriginador;nomeOriginador;numeroBancoPaulista;nomeBanco;dataGravacaoArquivo;branco;identificacaoSistema;numeroSequencialArquivo;branco1;numeroSequencialRegistro"; //$NON-NLS-1$
	}
	
	public void setDefaults()
	{
//		private int numero=0;
//		private int posicaoInicial=0;
//		private int posicaoFinal=0;
//		private int tamanho=0;
//		private boolean obrigatorio=true;
//		private TipoCampo tipo=new TipoCampo();
//		private int decimais=0;
//		private String conteudo="";		
		this.identificacaoRegistro.setConteudo("0"); //$NON-NLS-1$
		this.identificacaoArquivoRemessa=new Campo(2,2,2,1,true,Register.campoNumerico,0,"1"); //$NON-NLS-1$
		this.literalRemessa=new Campo(3,3,9,7,true,Register.campoAlfaNumerico,0,"REMESSA"); //$NON-NLS-1$
		this.codigoServico=new Campo(4,10,11,2,true,Register.campoNumerico,0,"01"); //$NON-NLS-1$
		this.literalServico=new Campo(5,12,26,15,true,Register.campoAlfaNumerico,0,"COBRANCA"); //$NON-NLS-1$
		this.codigoOriginador=new Campo(6,27,46,20,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.nomeOriginador=new Campo(7,47,76,30,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.numeroBancoPaulista=new Campo(8,77,79,3,true,Register.campoNumerico,0,"611"); //$NON-NLS-1$
		this.nomeBanco=new Campo(9,80,94,15,true,Register.campoAlfaNumerico,0,"PAULISTA S.A."); //$NON-NLS-1$
		this.dataGravacaoArquivo=new Campo(10,95,100,6,true,Register.campoNumerico,0,sdfCNAB.format(Calendar.getInstance().getTime())); 
		this.branco=new Campo(11,101,108,8,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.identificacaoSistema=new Campo(12,109,110,2,true,Register.campoAlfaNumerico,0,"MX"); //$NON-NLS-1$
		this.numeroSequencialArquivo=new Campo(13,111,117,7,true,Register.campoNumerico,0,Integer.toString(sequenciaArquivo)); 
		this.branco1=new Campo(14,118,438,321,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.numeroSequencialRegistro=new Campo(15,439,444,6,true,Register.campoNumerico,0,"1");		 //$NON-NLS-1$
	}
	
	public Campo getIdentificacaoArquivoRemessa() {
		return this.identificacaoArquivoRemessa;
	}


	public void setIdentificacaoArquivoRemessa(Campo identificacaoArquivoRemessa) {
		this.identificacaoArquivoRemessa = identificacaoArquivoRemessa;
	}


	public Campo getLiteralRemessa() {
		return this.literalRemessa;
	}


	public void setLiteralRemessa(Campo literalRemessa) {
		this.literalRemessa = literalRemessa;
	}


	public Campo getCodigoServico() {
		return this.codigoServico;
	}


	public void setCodigoServico(Campo codigoServico) {
		this.codigoServico = codigoServico;
	}


	public Campo getLiteralServico() {
		return this.literalServico;
	}


	public void setLiteralServico(Campo literalServico) {
		this.literalServico = literalServico;
	}


	public Campo getCodigoOriginador() {
		return this.codigoOriginador;
	}


	public void setCodigoOriginador(Campo codigoOriginador) {
		this.codigoOriginador = codigoOriginador;
	}


	public Campo getNomeOriginador() {
		return this.nomeOriginador;
	}


	public void setNomeOriginador(Campo nomeOriginador) {
		this.nomeOriginador = nomeOriginador;
	}


	public Campo getNumeroBancoPaulista() {
		return this.numeroBancoPaulista;
	}


	public void setNumeroBancoPaulista(Campo numeroBancoPaulista) {
		this.numeroBancoPaulista = numeroBancoPaulista;
	}


	public Campo getNomeBanco() {
		return this.nomeBanco;
	}


	public void setNomeBanco(Campo nomeBanco) {
		this.nomeBanco = nomeBanco;
	}


	public Campo getDataGravacaoArquivo() {
		return this.dataGravacaoArquivo;
	}


	public void setDataGravacaoArquivo(Campo dataGravacaoArquivo) {
		this.dataGravacaoArquivo = dataGravacaoArquivo;
	}


	public Campo getBranco() {
		return this.branco;
	}


	public void setBranco(Campo branco) {
		this.branco = branco;
	}


	public Campo getIdentificacaoSistema() {
		return this.identificacaoSistema;
	}


	public void setIdentificacaoSistema(Campo identificacaoSistema) {
		this.identificacaoSistema = identificacaoSistema;
	}


	public Campo getNumeroSequencialArquivo() {
		return this.numeroSequencialArquivo;
	}


	public void setNumeroSequencialArquivo(Campo numeroSequencialArquivo) {
		this.numeroSequencialArquivo = numeroSequencialArquivo;
	}


	public Campo getBranco1() {
		return this.branco1;
	}


	public void setBranco1(Campo branco1) {
		this.branco1 = branco1;
	}


	public Campo getNumeroSequencialRegistro() {
		return this.numeroSequencialRegistro;
	}


	public void setNumeroSequencialRegistro(Campo numeroSequencialRegistro) {
		this.numeroSequencialRegistro = numeroSequencialRegistro;
	}

	public static SimpleDateFormat getSdfCNAB()
	{
		return sdfCNAB;
	}

	public static void setSdfCNAB(SimpleDateFormat sdfCNAB)
	{
		Header.sdfCNAB = sdfCNAB;
	}

	public static int getSequenciaArquivo()
	{
		return sequenciaArquivo;
	}

	public static void setSequenciaArquivo(int sequenciaArquivo)
	{
		Header.sequenciaArquivo = sequenciaArquivo;
	}

}
