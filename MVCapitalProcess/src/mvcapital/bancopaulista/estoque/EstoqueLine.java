package mvcapital.bancopaulista.estoque;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mysql.jdbc.Connection;

import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.relatorio.cessao.HandlerTitulo;
import mvcapital.relatorio.cessao.TipoTitulo;
import mvcapital.relatorio.cessao.Titulo;

public class EstoqueLine extends Thread
{
	private Titulo titulo = new Titulo();
	private boolean isStored = false;
	private double valorPresente = 0.0;
	private double valorPDD = 0.0;
	private String faixaPDD = ""; //$NON-NLS-1$
	private double taxaCDIOver = 0.0;
	private FundoDeInvestimento fundo = new FundoDeInvestimento();
	public static Connection conn = null;
	private String line = ""; //$NON-NLS-1$
	private Date dataReferencia=Calendar.getInstance().getTime();
	private int iLine=0;
	private int prazoCorrido=0;
	private int prazoUtil=0;
	private double taxaSobreCDI=0.0;
	
	public EstoqueLine()
	{

	}

	public EstoqueLine(FundoDeInvestimento fundo, String line, int iLine)
	{
		this.fundo = fundo;
		this.line = line;
		this.iLine = iLine;
	}

	
	public void run()
	{
		this.setupValues();
	}

	public void setupValues()
	{
//		String line = this.line;
//		FundoDeInvestimento fundo = this.fundo;
//		Connection conn = this.conn;
		
//		System.out.println(this.line);
		SimpleDateFormat sdfE = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
		String[] fields = this.line.replace("&AMP;", "").replace("&amp;", "").split(";"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		String cadastroOriginador = fields[4].replace(".", "").replace("/", "").replace("-", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
//		System.out.println(cadastroOriginador);

		Entidade originador = new Entidade(fields[3], cadastroOriginador,conn);
//		System.out.println("Originador: " + originador.getNome()); //$NON-NLS-1$
		if(originador.getCadastro()==null)
		{
			originador.setCadastro(cadastroOriginador);
			Entidade.updateCadastro(originador.getIdEntidade(), cadastroOriginador, conn);
		}
		
		String cadastroCedente = fields[6].replace(".", "").replace("/", "").replace("-", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		Entidade cedente = new Entidade(fields[5], cadastroCedente, conn);
		if(cedente.getCadastro()==null)
		{
			cedente.setCadastro(cadastroCedente);
			Entidade.updateCadastro(cedente.getIdEntidade(), cadastroCedente, conn);
		}
		
		String cadastroSacado = fields[8].replace(".", "").replace("/", "").replace("-", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		Entidade sacado = new Entidade(fields[7], cadastroSacado, conn);
		if(sacado.getCadastro()==null)
		{
			sacado.setCadastro(cadastroSacado);
			Entidade.updateCadastro(sacado.getIdEntidade(), cadastroSacado, conn);
		}				
		String seuNumero = fields[9];
		String numeroDocumento = fields[10];
		TipoTitulo tipoTitulo = new TipoTitulo(fields[11],conn);
		double valorNominal = Double.parseDouble(fields[12].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		this.valorPresente = Double.parseDouble(fields[13].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		double valorAquisicao = Double.parseDouble(fields[14].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		this.valorPDD = Double.parseDouble(fields[15].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$		
		this.faixaPDD = fields[16];
		
//		Date dataReferencia=null;
		try {
			this.dataReferencia = sdfE.parse(fields[17]);
			this.setTaxaCDIOver(HandlerEstoque.taxaCDI(this.dataReferencia, conn));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date dataVencimentoOriginal = null;
		try {
			dataVencimentoOriginal = sdfE.parse(fields[18]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date dataAquisicao = null;
		try {
			dataAquisicao = sdfE.parse(fields[21]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		`titulo`.`idTitulo`,
//	    `titulo`.`idFundo`,
//	    `titulo`.`idEntidadeOriginador`,
//	    `titulo`.`idEntidadeCedente`,
//	    `titulo`.`idEntidadeSacado`,
//	    `titulo`.`idTipoTitulo`,
//	    `titulo`.`SeuNumero`,
//	    `titulo`.`NumeroDocumento`,
//	    `titulo`.`DataAquisicao`,
//	    `titulo`.`DataVencimento`,
//	    `titulo`.`PrazoCorrido`,
//	    `titulo`.`PrazoUtil`,
//	    `titulo`.`ValorAquisicao`,
//	    `titulo`.`ValorNominal`,
//	    `titulo`.`ValorDiarioUtil`,
//	    `titulo`.`NFeChaveAcesso`,
//	    `titulo`.`DataAtualizacao`
		
		this.titulo = new Titulo (
				this.fundo,
				originador,
				cedente,
				sacado,
				tipoTitulo,
				seuNumero,
				numeroDocumento,
				dataAquisicao,
				dataVencimentoOriginal,
				valorAquisicao,
				valorNominal,
				"", //$NON-NLS-1$
				conn);			
//		titulo.show();		
		this.setStored(HandlerTitulo.isStored(this.titulo, conn));
		System.out.println(this.iLine + " " + this.toString() + " " + Calendar.getInstance().getTime()); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public String toString()
	{
//		private Titulo titulo = new Titulo();
//		private boolean isStored = false;
//		private double valorPresente = 0.0;
//		private double valorPDD = 0.0;
//		private String faixaPDD = ""; //$NON-NLS-1$
//		private double taxaCDIOver = 0.0;
		return this.titulo.getIdTitulo()
							+ " " + this.isStored //$NON-NLS-1$
							+ " " + this.valorPresente //$NON-NLS-1$
							+ " " + this.valorPDD //$NON-NLS-1$
							+ " " + this.faixaPDD //$NON-NLS-1$
							+ " " + this.taxaCDIOver //$NON-NLS-1$
							;
	}

	
	public void show()
	{
//		private Titulo titulo = new Titulo();
//		private boolean isStored = false;
//		private double valorPresente = 0.0;
//		private double valorPDD = 0.0;
//		private String faixaPDD = ""; //$NON-NLS-1$
//		private double taxaCDIOver = 0.0;
		System.out.println(this.titulo.getIdTitulo()
							+ " " + this.isStored //$NON-NLS-1$
							+ " " + this.valorPresente //$NON-NLS-1$
							+ " " + this.valorPDD //$NON-NLS-1$
							+ " " + this.faixaPDD //$NON-NLS-1$
							+ " " + this.taxaCDIOver //$NON-NLS-1$
							);
	}

	public Titulo getTitulo()
	{
		return this.titulo;
	}

	public void setTitulo(Titulo titulo)
	{
		this.titulo = titulo;
	}

	public boolean isStored()
	{
		return this.isStored;
	}

	public void setStored(boolean isStored)
	{
		this.isStored = isStored;
	}

	public double getValorPresente()
	{
		return this.valorPresente;
	}

	public void setValorPresente(double valorPresente)
	{
		this.valorPresente = valorPresente;
	}

	public double getValorPDD()
	{
		return this.valorPDD;
	}

	public void setValorPDD(double valorPDD)
	{
		this.valorPDD = valorPDD;
	}

	public String getFaixaPDD()
	{
		return this.faixaPDD;
	}

	public void setFaixaPDD(String faixaPDD)
	{
		this.faixaPDD = faixaPDD;
	}

	public double getTaxaCDIOver()
	{
		return this.taxaCDIOver;
	}

	public void setTaxaCDIOver(double taxaCDIOver)
	{
		this.taxaCDIOver = taxaCDIOver;
	}

	public FundoDeInvestimento getFundo()
	{
		return this.fundo;
	}

	public void setFundo(FundoDeInvestimento fundo)
	{
		this.fundo = fundo;
	}

	public static Connection getConn()
	{
		return conn;
	}

	public static void setConn(Connection conn)
	{
		EstoqueLine.conn = conn;
	}

	public String getLine()
	{
		return this.line;
	}

	public void setLine(String line)
	{
		this.line = line;
	}

	public int getiLine()
	{
		return this.iLine;
	}

	public void setiLine(int iLine)
	{
		this.iLine = iLine;
	}

	public Date getDataReferencia()
	{
		return this.dataReferencia;
	}

	public void setDataReferencia(Date dataReferencia)
	{
		this.dataReferencia = dataReferencia;
	}

	public int getPrazoCorrido()
	{
		return this.prazoCorrido;
	}

	public void setPrazoCorrido(int prazoCorrido)
	{
		this.prazoCorrido = prazoCorrido;
	}

	public int getPrazoUtil()
	{
		return this.prazoUtil;
	}

	public void setPrazoUtil(int prazoUtil)
	{
		this.prazoUtil = prazoUtil;
	}

	public double getTaxaSobreCDI()
	{
		return this.taxaSobreCDI;
	}

	public void setTaxaSobreCDI(double taxaSobreCDI)
	{
		this.taxaSobreCDI = taxaSobreCDI;
	}
}
