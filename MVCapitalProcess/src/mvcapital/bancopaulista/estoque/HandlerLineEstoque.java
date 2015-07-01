package mvcapital.bancopaulista.estoque;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.relatorio.cessao.HandlerTitulo;
import mvcapital.relatorio.cessao.TipoTitulo;
import mvcapital.relatorio.cessao.Titulo;

import com.mysql.jdbc.Connection;

public class HandlerLineEstoque extends Thread
{
	private static Connection conn = null;
	private String line = "";
	private int iLine = 0;
	private FundoDeInvestimento fundo = new FundoDeInvestimento();
	public HandlerLineEstoque()
	{

	}
	
	public void run()
	{
		SimpleDateFormat sdfE = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
		String[] fields = this.line.replace("&AMP;", "").replace("&amp;", "").split(";"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
//		if(this.iLine==0)
//		{
//			System.out.println(fields[0]);
//			String stringFundo=fields[0];
//			
//			if(fields[0].equals("LEGO NP FUNDO DE INVESTIMENTO EM DIREITOS CREDITORIOS")) //$NON-NLS-1$
//			{
//				stringFundo="FUNDO DE INVESTIMENTO EM DIREITOS CREDITORIOS MULTISSETORIAL LEGO - LP"; //$NON-NLS-1$
//			}
//			this.fundo = new FundoDeInvestimento(stringFundo, HandlerEstoque.conn);				
//			try {
//				Date dataFundo = sdfE.parse(fields[2]);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			System.out.println("Fundo: " + this.fundo.getNome() + " " + Calendar.getInstance().getTime()); //$NON-NLS-1$ //$NON-NLS-2$				
//		}
		
		
		String cadastroOriginador = fields[4].replace(".", "").replace("/", "").replace("-", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		Entidade originador = new Entidade(fields[3], cadastroOriginador,this.conn);
		if(originador.getCadastro()==null)
		{
			originador.setCadastro(cadastroOriginador);
			Entidade.updateCadastro(originador.getIdEntidade(), cadastroOriginador, this.conn);
		}
		
		String cadastroCedente = fields[6].replace(".", "").replace("/", "").replace("-", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		Entidade cedente = new Entidade(fields[5], cadastroCedente, this.conn);
		if(cedente.getCadastro()==null)
		{
			cedente.setCadastro(cadastroCedente);
			Entidade.updateCadastro(cedente.getIdEntidade(), cadastroCedente, this.conn);
		}
		
		String cadastroSacado = fields[8].replace(".", "").replace("/", "").replace("-", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		Entidade sacado = new Entidade(fields[7], cadastroSacado, this.conn);
		if(sacado.getCadastro()==null)
		{
			sacado.setCadastro(cadastroSacado);
			Entidade.updateCadastro(sacado.getIdEntidade(), cadastroSacado, this.conn);
		}				
		String seuNumero = fields[9];
		String numeroDocumento = fields[10];
		TipoTitulo tipoTitulo = new TipoTitulo(fields[11],this.conn);
		double valorNominal = Double.parseDouble(fields[12].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		double valorPresente = Double.parseDouble(fields[13].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		double valorAquisicao = Double.parseDouble(fields[14].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		double valorPDD = Double.parseDouble(fields[15].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
		String faixaPDD = fields[16];
		
		Date dataReferencia=null;
		try {
			dataReferencia = sdfE.parse(fields[17]);
			double taxaCDIOver = HandlerEstoque.taxaCDI(dataReferencia, this.conn);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date dataVencimentoOriginal = null;
		try {
			dataVencimentoOriginal = sdfE.parse(fields[18]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		Date dataVencimentoAjustada = null;
//		try {
//			dataVencimentoAjustada = sdfE.parse(fields[19]);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		Date dataEmissao = null;
//		try {
//			dataEmissao = sdfE.parse(fields[20]);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		Date dataAquisicao = null;
		try {
			dataAquisicao = sdfE.parse(fields[21]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		int prazo = Integer.parseInt(fields[22]);
//		int prazoAtual = Integer.parseInt(fields[23]);
//		String situacaoTitulo = fields[24];
//		double taxaCessao = Double.parseDouble(fields[25].replace(".", "").replace(",", "."));
//		double taxaTitulo = Double.parseDouble(fields[26].replace(".", "").replace(",", "."));
		
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
		
		Titulo titulo = new Titulo (
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
				this.conn);			
//		titulo.show();
		
//		if(HandlerTitulo.isStored(titulo, conn))
//		{
////			System.out.println("Existe!");				
//		}
//		else
//		{
//			HandlerTitulo.store(titulo, conn);
//		}	
//		
		{
//			StorerTituloEstoque ste = new StorerTituloEstoque();
//			ste.setConn(conn);
//			ste.setTitulo(titulo);
//			ste.setDataReferencia(dataReferencia);
//			ste.start();
			HandlerEstoque.storeTituloEstoque(titulo, dataReferencia,conn);
		}
		{
//			StorerPDD spdd = new StorerPDD();
//			spdd.setConn(conn);
//			spdd.setTitulo(titulo);
//			spdd.setValorPDD(valorPDD);
//			spdd.setFaixaPDD(faixaPDD);
//			spdd.start();
			HandlerEstoque.storePDD(titulo, dataReferencia, valorPDD, faixaPDD, conn);
		}
		{
//			StorerValorPresente svp = new StorerValorPresente();
//			svp.setConn(conn);;
//			svp.setTitulo(titulo);
//			svp.setDataReferencia(dataReferencia);
//			svp.setValorPresente(valorPresente);
//			svp.start();
			HandlerEstoque.storeValorPresente(titulo, dataReferencia, valorPresente, conn);
		}
		{
//			StorerPrazo sp = new StorerPrazo();
//			sp.setConn(conn);;
//			sp.setTitulo(titulo);
//			sp.setDataReferencia(dataReferencia);
//			sp.start();
			HandlerEstoque.storePrazo(titulo, dataReferencia, conn);
		}		
		{
//			StorerTaxaSobreCDI stscdi = new StorerTaxaSobreCDI();
//			stscdi.setConn(conn);;
//			stscdi.setTitulo(titulo);
//			stscdi.setDataReferencia(dataReferencia);
//			stscdi.start();
			HandlerEstoque.storeTaxaSobreCDI(titulo, dataReferencia, conn);
		}
		
	}

	public static Connection getConn()
	{
		return conn;
	}

	public static void setConn(Connection conn)
	{
		HandlerLineEstoque.conn = conn;
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

	public FundoDeInvestimento getFundo()
	{
		return this.fundo;
	}

	public void setFundo(FundoDeInvestimento fundo)
	{
		this.fundo = fundo;
	}

}
