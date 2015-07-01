package operation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import conta.Conta;
import entidade.Entidade;
import fundo.FundoDeInvestimento;
import relatorio.cessao.Relatorio;

public class Operacao 
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private static DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

	private int idOperacao=0;
	private FundoDeInvestimento fundo=new FundoDeInvestimento();
	private boolean aprovado=false;
	
	private String nomeArquivo="";
	private Entidade cedente=new Entidade();
	private double valorDeTransferencia=0.0;
	private double valorTEDCedente=0.0;
	private Date registerTime=Calendar.getInstance().getTime();
	private ArrayList<Status> statuses=new ArrayList<Status>();
	private double taxaDeCessao=0;
	private Date dataDeEntrada=Calendar.getInstance().getTime();
	private Date dataDeImportacao=Calendar.getInstance().getTime();
	private double totalAquisicao=0;
	private double totalNominal=0;	
	private double valorTotalAquisicaoComAdiantamento=0;
	private double valorRecompraComAdiantamento=0;
	private double reembolsoContaEspecial=0;
	private double valorTotalComAdiantamento=0;	
	private String contaCorrenteInternaPaulista="";
	private double valorTotalContaCorrenteInternaPaulista=0;	
	private double valorRemessaSemAdiantamento=0;
	private double valorRecompraSemAdiantamento=0;
	private double reembolsoContaNormal=0;
	private double valorTotalSemAdiantamento=0;	
	private Conta contaInterna = new Conta();
	private Conta contaExterna1 = new Conta();
	private Conta contaExterna2 = new Conta();
	private String contaCedenteSemAdiantamento1="";
	private double valorTotalCedenteSemAdiantamento1=0;
	private String contaCedenteSemAdiantamento2="";
	private double valorTotalCedenteSemAdiantamento2=0;
	private boolean registrado=false;
	private boolean registradoTitulos=false;
	private OperationSummary resumo = new OperationSummary();
	private String certificadora="";
	private Relatorio relatorio=new Relatorio();
	private boolean concentracaoOK=false;
	private boolean prazoOK=false;
	private boolean valorOK=false;
	private boolean taxaOK=false;
	private boolean tedEnviada=false;
	private boolean haveTransferDetailsChecked=false;
	
	public Operacao()
	{
		
	}
	
	public void show()
	{
//		# idOperacao
//		 idFundo
//		 nomeArquivo
//		 idEntidadeCedente
//		 totalAquisicao
//		 totalNominal
//		 dataDeEntrada
//		 remessaComAdiantamento
//		 recompraComAdiantamento
//		 reembolsoContaInterna
//		 remessaSemAdiantamento
//		 recompraSemAdiantamento
//		 reembolsoContaExterna
//		 valorTEDContaInterna
//		 valorTEDContaExterna1
//		 valorTEDContaExterna2
//		 idContaInterna
//		 idContaExterna1
//		 idContaExterna2
		System.out.println("---------------------------------");
		System.out.println("idOperacao: " + this.getIdOperacao());
		System.out.println("Fundo: " + fundo.getNomeCurto());
		System.out.println("Nome do arquivo: " + nomeArquivo);
		System.out.println("Cedente: " + cedente.getNome());
		System.out.println("Hora de Registro: " + sdf.format(registerTime));
		System.out.println("TotalAquisicao: " + this.getTotalAquisicao());
		System.out.println("TotalNominal: " + this.getTotalNominal());
		System.out.println("DataDeEntrada: " + this.getDataDeEntrada());
		System.out.println("Remessa: " + this.getValorRemessaSemAdiantamento());
		System.out.println("Recompra: " + this.getValorRecompraSemAdiantamento());
		System.out.println("Reembolso: " + this.getReembolsoContaNormal());
		System.out.println("ValorTED: " + this.getValorTotalSemAdiantamento());
		if(statuses.size()>0)
		{
			System.out.println("Situação: " + statuses.get(statuses.size()-1).getDescription());
			System.out.println("Última Situação: " + sdf.format(statuses.get(statuses.size()-1).getBeginDate()));
			System.out.println("Última Verificação: " + sdf.format(statuses.get(statuses.size()-1).getEndDate()));
		}
	}
	public FundoDeInvestimento getFundo() {
		return fundo;
	}
	public void setFundo(FundoDeInvestimento fundo) {
		this.fundo = fundo;
	}
	public Entidade getCedente() {
		return cedente;
	}
	public void setCedente(Entidade cedente) {
		this.cedente = cedente;
	}
	public Double getValorTEDCedente() {
		return valorTEDCedente;
	}
	public void setValorTEDCedente(Double valorTEDCedente) {
		this.valorTEDCedente = valorTEDCedente;
	}
	public static SimpleDateFormat getSdf() {
		return sdf;
	}
	public static void setSdf(SimpleDateFormat sdf) {
		Operacao.sdf = sdf;
	}
	public static DecimalFormat getDf() {
		return df;
	}
	public static void setDf(DecimalFormat df) {
		Operacao.df = df;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public ArrayList<Status> getStatuses() {
		return statuses;
	}
	public void setStatuses(ArrayList<Status> statuses) {
		this.statuses = statuses;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}


	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}


	public Relatorio getRelatorio() {
		return relatorio;
	}


	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}


	public double getTaxaDeCessao() {
		return taxaDeCessao;
	}


	public void setTaxaDeCessao(double taxaDeCessao) {
		this.taxaDeCessao = taxaDeCessao;
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


	public void setValorTEDOriginador(double valorTEDOriginador) {
		this.valorDeTransferencia = valorTEDOriginador;
	}


	public void setValorTEDCedente(double valorTEDCedente) {
		this.valorTEDCedente = valorTEDCedente;
	}


	public double getValorTotalAquisicaoComAdiantamento() {
		return valorTotalAquisicaoComAdiantamento;
	}


	public void setValorTotalAquisicaoComAdiantamento(
			double valorTotalAquisicaoComAdiantamento) {
		this.valorTotalAquisicaoComAdiantamento = valorTotalAquisicaoComAdiantamento;
	}


	public double getValorRecompraComAdiantamento() {
		return valorRecompraComAdiantamento;
	}


	public void setValorRecompraComAdiantamento(double valorRecompraComAdiantamento) {
		this.valorRecompraComAdiantamento = valorRecompraComAdiantamento;
	}


	public double getReembolsoContaEspecial() {
		return reembolsoContaEspecial;
	}


	public void setReembolsoContaEspecial(double reembolsoContaEspecial) {
		this.reembolsoContaEspecial = reembolsoContaEspecial;
	}


	public double getValorTotalComAdiantamento() {
		return valorTotalComAdiantamento;
	}


	public void setValorTotalComAdiantamento(double valorTotalComAdiantamento) {
		this.valorTotalComAdiantamento = valorTotalComAdiantamento;
	}


	public String getContaCorrenteInternaPaulista() {
		return contaCorrenteInternaPaulista;
	}


	public void setContaCorrenteInternaPaulista(String contaCorrenteInternaPaulista) {
		this.contaCorrenteInternaPaulista = contaCorrenteInternaPaulista;
	}


	public double getValorTotalContaCorrenteInternaPaulista() {
		return valorTotalContaCorrenteInternaPaulista;
	}


	public void setValorTotalContaCorrenteInternaPaulista(
			double valorTotalContaCorrenteInternaPaulista) {
		this.valorTotalContaCorrenteInternaPaulista = valorTotalContaCorrenteInternaPaulista;
	}


	public double getValorRemessaSemAdiantamento() {
		return valorRemessaSemAdiantamento;
	}


	public void setValorRemessaSemAdiantamento(double valorRemessaSemAdiantamento) {
		this.valorRemessaSemAdiantamento = valorRemessaSemAdiantamento;
	}


	public double getValorRecompraSemAdiantamento() {
		return valorRecompraSemAdiantamento;
	}


	public void setValorRecompraSemAdiantamento(double valorRecompraSemAdiantamento) {
		this.valorRecompraSemAdiantamento = valorRecompraSemAdiantamento;
	}


	public double getReembolsoContaNormal() {
		return reembolsoContaNormal;
	}


	public void setReembolsoContaNormal(double reembolsoContaNormal) {
		this.reembolsoContaNormal = reembolsoContaNormal;
	}


	public double getValorTotalSemAdiantamento() {
		return valorTotalSemAdiantamento;
	}


	public void setValorTotalSemAdiantamento(double valorTotalSemAdiantamento) {
		this.valorTotalSemAdiantamento = valorTotalSemAdiantamento;
	}


	public String getContaCedenteSemAdiantamento1() {
		return contaCedenteSemAdiantamento1;
	}


	public void setContaCedenteSemAdiantamento1(String contaCedenteSemAdiantamento1) {
		this.contaCedenteSemAdiantamento1 = contaCedenteSemAdiantamento1;
	}


	public double getValorTotalCedenteSemAdiantamento1() {
		return valorTotalCedenteSemAdiantamento1;
	}


	public void setValorTotalCedenteSemAdiantamento1(
			double valorTotalCedenteSemAdiantamento1) {
		this.valorTotalCedenteSemAdiantamento1 = valorTotalCedenteSemAdiantamento1;
	}


	public String getContaCedenteSemAdiantamento2() {
		return contaCedenteSemAdiantamento2;
	}


	public void setContaCedenteSemAdiantamento2(String contaCedenteSemAdiantamento2) {
		this.contaCedenteSemAdiantamento2 = contaCedenteSemAdiantamento2;
	}


	public double getValorTotalCedenteSemAdiantamento2() {
		return valorTotalCedenteSemAdiantamento2;
	}


	public void setValorTotalCedenteSemAdiantamento2(
			double valorTotalCedenteSemAdiantamento2) {
		this.valorTotalCedenteSemAdiantamento2 = valorTotalCedenteSemAdiantamento2;
	}

	public String getCertificadora() {
		return certificadora;
	}


	public void setCertificadora(String certificadora) {
		this.certificadora = certificadora;
	}


	public double getValorDeTransferencia() {
		return valorDeTransferencia;
	}


	public void setValorDeTransferencia(double valorDeTransferencia) {
		this.valorDeTransferencia = valorDeTransferencia;
	}


	public Date getDataDeImportacao() {
		return dataDeImportacao;
	}


	public void setDataDeImportacao(Date dataDeImportacao) {
		this.dataDeImportacao = dataDeImportacao;
	}


	public boolean isAprovado() {
		return aprovado;
	}


	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}


	public OperationSummary getResumo() {
		return resumo;
	}


	public void setResumo(OperationSummary resumo) {
		this.resumo = resumo;
	}


	public boolean isRegistrado() {
		return registrado;
	}


	public void setRegistrado(boolean registrado) {
		this.registrado = registrado;
	}


	public boolean isRegistradoTitulos() {
		return registradoTitulos;
	}


	public void setRegistradoTitulos(boolean registradoTitulos) {
		this.registradoTitulos = registradoTitulos;
	}
	public Conta getContaInterna() {
		return contaInterna;
	}
	public void setContaInterna(Conta contaInterna) {
		this.contaInterna = contaInterna;
	}
	public Conta getContaExterna1() {
		return contaExterna1;
	}
	public void setContaExterna1(Conta contaExterna1) {
		this.contaExterna1 = contaExterna1;
	}
	public Conta getContaExterna2() {
		return contaExterna2;
	}
	public void setContaExterna2(Conta contaExterna2) {
		this.contaExterna2 = contaExterna2;
	}
	public int getIdOperacao() {
		return idOperacao;
	}
	public void setIdOperacao(int idOperacao) {
		this.idOperacao = idOperacao;
	}

	public boolean isConcentracaoOK() {
		return concentracaoOK;
	}

	public void setConcentracaoOK(boolean concentracaoOK) {
		this.concentracaoOK = concentracaoOK;
	}

	public boolean isPrazoOK() {
		return prazoOK;
	}

	public void setPrazoOK(boolean prazoOK) {
		this.prazoOK = prazoOK;
	}

	public boolean isValorOK() {
		return valorOK;
	}

	public void setValorOK(boolean valorOK) {
		this.valorOK = valorOK;
	}

	public boolean isTaxaOK() {
		return taxaOK;
	}

	public void setTaxaOK(boolean taxaOK) {
		this.taxaOK = taxaOK;
	}

	public boolean isTedEnviada() {
		return tedEnviada;
	}

	public void setTedEnviada(boolean tedEnviada) {
		this.tedEnviada = tedEnviada;
	}

	public boolean isHaveTransferDetailsChecked() {
		return haveTransferDetailsChecked;
	}

	public void setHaveTransferDetailsChecked(boolean haveTransferDetailsChecked) {
		this.haveTransferDetailsChecked = haveTransferDetailsChecked;
	}

}
