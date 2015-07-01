package relatorio.cessao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import operation.Operacao;

public class BlockIdentificacao extends Block 
{
	private static SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
	
	private String fundo="";
	private String cedente="";
	private double taxaDeCessao=0;
	private String nomeArquivo="";
	private Date dataDeEntrada=Calendar.getInstance().getTime();
	private double totalAquisicao=0;
	private double totalNominal=0;
	
	public BlockIdentificacao() 
	{
		super.setNome("Identificacao");
	}

	public BlockIdentificacao(Operacao op) 
	{
		this.fundo = op.getFundo().getNome();
		this.cedente = op.getCedente().getNome();
		this.nomeArquivo = op.getNomeArquivo();
		this.dataDeEntrada = op.getDataDeEntrada();
		this.totalAquisicao = op.getTotalAquisicao();
		this.totalNominal = op.getTotalNominal();
	}
	
	public void show()
	{
		System.out.println("IDENTIFICACAO");
		System.out.println("Fundo: " + fundo);
		System.out.println("Cedente: " + cedente);
		System.out.println("Taxa de cessao: " + taxaDeCessao + "%");
		System.out.println("Arquivo: " + nomeArquivo);
		System.out.println("Data de entrada: " + sdfr.format(dataDeEntrada));
		System.out.println("Total Aquisicao: R$ " + totalAquisicao);
		System.out.println("Total Nominal: R$ " + totalNominal);
	}

	public String getFundo() {
		return fundo;
	}

	public void setFundo(String fundo) {
		this.fundo = fundo;
	}

	public String getCedente() {
		return cedente;
	}

	public void setCedente(String cedente) {
		this.cedente = cedente;
	}

	public double getTaxaDeCessao() {
		return taxaDeCessao;
	}

	public void setTaxaDeCessao(double taxaDeCessao) {
		this.taxaDeCessao = taxaDeCessao;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public Date getDataDeEntrada() {
		return dataDeEntrada;
	}

	public void setDataDeEntrada(Date dataDeEntrada) {
		this.dataDeEntrada = dataDeEntrada;
	}

	public double getTotalAquisicao() {
		return totalAquisicao;
	}

	public void setTotalAquisicao(double totalAquisicao) {
		this.totalAquisicao = totalAquisicao;
	}

	public double getTotalNominal() {
		return totalNominal;
	}

	public void setTotalNominal(double totalNominal) {
		this.totalNominal = totalNominal;
	}

}
