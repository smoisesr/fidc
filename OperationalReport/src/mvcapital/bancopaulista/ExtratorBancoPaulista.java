package mvcapital.bancopaulista;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import mysql.MySQLAccess;

import org.openqa.selenium.By;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import utils.OperatingSystem;
import utils.SortEntidades;
import utils.SshClient;
import conta.Conta;
import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class ExtratorBancoPaulista  
{
    private WebDriver driver = null;	
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); //$NON-NLS-1$
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd"); //$NON-NLS-1$
	private static SimpleDateFormat sdfExtrato = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
	private static DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private static String server=""; //$NON-NLS-1$
	private static int port=0;
	private static String userName=""; //$NON-NLS-1$
	private static String dbName=""; //$NON-NLS-1$
	private static String passwordDb=""; //$NON-NLS-1$
	private static Connection conn = null;
	private static MySQLAccess mysql = null;
	private static String sshServer=""; //$NON-NLS-1$
	private static int sshPort=0;
	private static String sshUser=""; //$NON-NLS-1$
	private static String sshPassword=""; //$NON-NLS-1$
	private static SshClient sshClient = null;
	private FundoDeInvestimento fundo = new FundoDeInvestimento();
	private Conta fundAccount=new Conta();
	private Extrato extrato=new Extrato();
	
	
	public ExtratorBancoPaulista()
	{
		
	}
	
	public ExtratorBancoPaulista(Conta fundAccount)
	{
		this.fundAccount = fundAccount;
	}
	
	public ExtratorBancoPaulista(String user, String password)
	{
		/**
		 * Fun
		 */
	}
	
	public static void main(String[] args)	
	{
		ExtratorBancoPaulista.readConf();
		while(true)
		{
	    	
	
	    	Calendar calLimit=Calendar.getInstance();
	        Date limitTime=calLimit.getTime();
	        calLimit.set(Calendar.HOUR_OF_DAY, 17);
	        calLimit.set(Calendar.MINUTE, 30);
	        calLimit.set(Calendar.SECOND, 0);
			limitTime=calLimit.getTime();
			
			Calendar calBegin=Calendar.getInstance();
	        Date beginTime=calBegin.getTime();
	        calBegin.set(Calendar.HOUR_OF_DAY, 8);
	        calBegin.set(Calendar.MINUTE, 25);
	        calBegin.set(Calendar.SECOND, 0);
			beginTime=calBegin.getTime();
			
			if(Calendar.getInstance().getTime().after(limitTime))
			{
				System.out.println("Beginning after limit time"); //$NON-NLS-1$
				calBegin.add(Calendar.DATE, 1);
		        beginTime=calBegin.getTime();
				beginTime=calBegin.getTime();
				
				calLimit.add(Calendar.DATE, 1);
				limitTime=calLimit.getTime();
				
				System.out.println("New values"); //$NON-NLS-1$
				System.out.println("Begin Time: " + beginTime); //$NON-NLS-1$
				System.out.println("Limit Time: " + limitTime); //$NON-NLS-1$
			}
			
			
			while(Calendar.getInstance().getTime().before(beginTime))
			{
				System.out.println("Waiting to begin at " + beginTime + " currentTime: " + Calendar.getInstance().getTime());				 //$NON-NLS-1$ //$NON-NLS-2$
				try
				{
					Thread.sleep(5000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}				
			}
			ExtratorBancoPaulista.mysql = new MySQLAccess(ExtratorBancoPaulista.server, ExtratorBancoPaulista.port, ExtratorBancoPaulista.userName, ExtratorBancoPaulista.passwordDb, ExtratorBancoPaulista.dbName);
			ExtratorBancoPaulista.mysql.connect();	
			ExtratorBancoPaulista.conn = (Connection) ExtratorBancoPaulista.mysql.getConn();
			ArrayList<ExtratorBancoPaulista> extrators = new ArrayList<ExtratorBancoPaulista>();
			
//			ArrayList<FundoDeInvestimento> fundos = ExtratorBancoPaulista.readFundos();
			extrators = ExtratorBancoPaulista.readExtratores();
			System.out.println("Connecting to ssh server"); //$NON-NLS-1$
			ExtratorBancoPaulista.getSshClient().connect();
			System.out.println("Connected to ssh server"); //$NON-NLS-1$			
			
			for(ExtratorBancoPaulista extrator:extrators)
			{
		    	Date hoje=Calendar.getInstance().getTime();				
				extrator.getExtrato().setMovimentacoes(HandlerMovimentacao.read(extrator.getFundo(), hoje, ExtratorBancoPaulista.conn));
				System.out.println("Login to " + extrator.getFundAccount().getEntidadeServidor().getNome() + " for " + extrator.getFundAccount().getEntidadeProprietario().getNome()); //$NON-NLS-1$ //$NON-NLS-2$
				extrator.login();
			}
			while(Calendar.getInstance().getTime().before(limitTime))
			{
				for(ExtratorBancoPaulista extrator:extrators)
				{	
					extrator.updateExtrato();
				}
				System.out.println("RESUMO"); //$NON-NLS-1$
				for(ExtratorBancoPaulista extrator:extrators)
				{
					if( extrator.getExtrato().getMovimentacoes().size()>0)
					{
						int indexLastMove = extrator.getExtrato().getMovimentacoes().size()-1;			
						System.out.println(extrator.getExtrato().getMovimentacoes().get(indexLastMove).getString());
					}
				}
				
				System.out.println();
				
				for(ExtratorBancoPaulista extrator:extrators)
				{
					System.out.println("DETALHE ");				 //$NON-NLS-1$
					extrator.getExtrato().show();
					ExtratorBancoPaulista.storeExtrato(extrator);
					System.out.println();
				}
//				cal = Calendar.getInstance();
		        System.out.println("Last Update " + sdf.format(Calendar.getInstance().getTime()));			 //$NON-NLS-1$
				System.out.println("---------------------------------------------------------------------------------------------------------------------------------"); //$NON-NLS-1$
				ExtratorBancoPaulista.saveToFile(extrators);
			}
			for(ExtratorBancoPaulista extrator:extrators)
			{
				extrator.sair();
				extrator.close();
			}
			ExtratorBancoPaulista.getSshClient().disconnect();
			ExtratorBancoPaulista.mysql.close();
		}
	}

	public static void storeExtrato(ExtratorBancoPaulista extrator)
	{
		for(Movimentacao movimentacao:extrator.getExtrato().getMovimentacoes())
		{
			HandlerMovimentacao.store(movimentacao, conn);
		}
	}
	
	public static ArrayList<ExtratorBancoPaulista> readExtratores()
	{
		ArrayList<ExtratorBancoPaulista> extratores = new ArrayList<ExtratorBancoPaulista>();
		ArrayList<FundoDeInvestimento> fundos = ExtratorBancoPaulista.readFundos();
		System.out.println("Extractors order"); //$NON-NLS-1$
		for(FundoDeInvestimento fundo:fundos)
		{
			ExtratorBancoPaulista extrator = new ExtratorBancoPaulista();
			extrator.setFundo(fundo);
			ExtratorBancoPaulista.readAccount(extrator);
			
			System.out.println("Fundo: " + fundo.getIdEntidade()); //$NON-NLS-1$
			if(extrator.getFundAccount().getUsuario()!="") //$NON-NLS-1$
			{
				extratores.add(extrator);
			}
		}
		return extratores;
	}
	
	public static void readAccount(ExtratorBancoPaulista extrator)
	{
		Statement stmt=null;
		try {
			stmt = (Statement) ExtratorBancoPaulista.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "select * from conta where idTipoDeConta=1 " + " and idEntidadeProprietario="+extrator.getFundo().getIdEntidade(); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idConta = rs.getInt("idConta"); //$NON-NLS-1$
				int idEntidadeProprietario = rs.getInt("idEntidadeProprietario"); //$NON-NLS-1$
				int idEntidadeServidor = rs.getInt("idEntidadeServidor"); //$NON-NLS-1$
				String numero = rs.getString("numero"); //$NON-NLS-1$
				String agencia = rs.getString("agencia"); //$NON-NLS-1$
				String usuario = rs.getString("usuario"); //$NON-NLS-1$
				String senha = rs.getString("senha"); //$NON-NLS-1$
				int idTipoDeConta = rs.getInt("idTipoDeConta"); //$NON-NLS-1$
				
				Conta conta = new Conta();
				conta.setIdConta(idConta);
				conta.setEntidadeProprietario(new Entidade(idEntidadeProprietario,conn));
				conta.setEntidadeServidor(new Entidade(idEntidadeServidor, conn));
				conta.setNumero(numero);
				conta.setAgencia(agencia);
				conta.setUsuario(usuario);
				conta.setSenha(senha);
				conta.setIdTipoDeConta(idTipoDeConta);
				
				extrator.setFundAccount(conta);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<FundoDeInvestimento> readFundos()
	{
		ArrayList<FundoDeInvestimento> fundos = new ArrayList<FundoDeInvestimento>();
		ArrayList<FundoDeInvestimento> sortFundos = new ArrayList<FundoDeInvestimento>();

		ArrayList<Entidade> entidadesFundo = new ArrayList<Entidade>();
		ArrayList<Entidade> entidadesSortFundo = new ArrayList<Entidade>();
		Statement stmt=null;
		try {
			stmt = (Statement) ExtratorBancoPaulista.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT idFundo FROM fundo WHERE idFundoAtivo=1"; //$NON-NLS-1$
		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				FundoDeInvestimento fundo = new FundoDeInvestimento(rs.getInt("idFundo"),conn); //$NON-NLS-1$
				fundos.add(fundo);
				entidadesFundo.add(new Entidade(fundo.getIdEntidade(),conn));								
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		entidadesSortFundo = SortEntidades.sort(entidadesFundo);
		
		for(Entidade entidadeFundo:entidadesSortFundo)
		{
			for(FundoDeInvestimento fundo:fundos)
			{
				if(entidadeFundo.getIdEntidade() == fundo.getIdEntidade())
				{
					sortFundos.add(fundo);
				}
			}
		}
		
		for(FundoDeInvestimento fundo:sortFundos)
		{
			System.out.println("sortedFund: " + fundo.getIdFundo()); //$NON-NLS-1$
		}
		return sortFundos;
	}
	
	
	
	@SuppressWarnings("resource")
	public static void saveToFile(ArrayList<ExtratorBancoPaulista> extrators)
	{
    	File file=null;
    	String fileName = ""; //$NON-NLS-1$
    	if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
    	{
    			fileName = "R:\\reportExtract.txt"; //$NON-NLS-1$
    	}
    	else
    	{
    		fileName = "/home/Reports/reportExtract.txt"; //$NON-NLS-1$
    	}
		file = new File(fileName);
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String content=""; //$NON-NLS-1$
		content=content+"RESUMO\n"; //$NON-NLS-1$
		for(ExtratorBancoPaulista extrator:extrators)
		{
			if(extrator.getExtrato().getMovimentacoes().size()>0)
			{
				for(int i=0;i<extrator.getExtrato().getMovimentacoes().size(); i++)
				{
//					int indexLastMove = extrator.getExtrato().getMovimentacoes().size()-1;
					if(extrator.getExtrato().getMovimentacoes().get(i).getString().toLowerCase().contains("saldo disponivel")) //$NON-NLS-1$
					{
						content=content+extrator.getExtrato().getMovimentacoes().get(i).getString()+"\n"; //$NON-NLS-1$
						break;
					}
				}
			}
		}
		content=content+"\n"; //$NON-NLS-1$
		
		for(ExtratorBancoPaulista extrator:extrators)
		{
			content=content+"DETALHE "+extrator.getFundAccount().getUsuario()+"\n"; //$NON-NLS-1$ //$NON-NLS-2$
			content=content+extrator.getExtrato().getString()+"\n"; //$NON-NLS-1$
			content=content+"\n"; //$NON-NLS-1$
		}
		Calendar cal = Calendar.getInstance();
        content=content+"Last Update " + sdf.format(cal.getTime())+"\n"; //$NON-NLS-1$ //$NON-NLS-2$
        
		
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file, "UTF-8"); //$NON-NLS-1$
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		pw.write(content);
		pw.flush();
		pw.close();
	    String command="";	     //$NON-NLS-1$
    	    
	    command="perl /home/Reports/generateExtractsSummaryWeb.pl"; //$NON-NLS-1$
	    System.out.println("command: " + command); //$NON-NLS-1$
	    sshClient.executeCommandOutput(command);		 

	}
	
    @SuppressWarnings("resource")
	public static void readConf()
	{
		BufferedReader reader = null;
		System.out.println("Reading conf/automata.conf file"); //$NON-NLS-1$
		System.out.println("------------------"); //$NON-NLS-1$
		try 
		{
			reader = new BufferedReader(new FileReader("conf/extratobancopaulista.conf")); //$NON-NLS-1$
		} catch (FileNotFoundException e1) 
		{

			e1.printStackTrace();
		}
		String line = null;
		try 
		{
			while ((line = reader.readLine()) != null) 
			{
				if(!line.isEmpty())
				{
					
					String[] fields = line.split(","); //$NON-NLS-1$
					if (fields[0].contains("#")) //$NON-NLS-1$
					{
						System.out.println("Comment Line:\t" + line); //$NON-NLS-1$
					}
					else
					{
						System.out.println("Parameters Line:\t" + line); //$NON-NLS-1$
						for (int i = 0; i<fields.length; i++)
						{
							switch (fields[0]) 
							{
					            case "server":   //$NON-NLS-1$
					            	server = fields[1];
					                break;
					            case "port": //$NON-NLS-1$
					            	port = Integer.parseInt(fields[1].replace(" ", "")); //$NON-NLS-1$ //$NON-NLS-2$
					                break;				            	
					            case "userName": //$NON-NLS-1$
					            	userName = fields[1];
					                break;
					            case "password": //$NON-NLS-1$
					            	passwordDb = fields[1];
					                break;
					            case "dbName": //$NON-NLS-1$
					            	dbName = fields[1];
					                break;
					            case "rootLocalWindows": //$NON-NLS-1$
					            	OperatingSystem.setRootLocalWindows(fields[1]);
					            	break;
					            case "rootLocalLinux": //$NON-NLS-1$
					            	OperatingSystem.setRootLocalLinux(fields[1]);
					            	break;
					            case "sshserver": //$NON-NLS-1$
					            	sshServer=fields[1];
					            	break;
					            case "sshport": //$NON-NLS-1$
					            	sshPort=Integer.parseInt(fields[1]);
					            	break;
					            case "sshuser": //$NON-NLS-1$
					            	sshUser=fields[1];
					            	break;
					            case "sshpassword": //$NON-NLS-1$
					            	sshPassword=fields[1];
					            	break;
					            default: 
					            break;
							}
						}
					}
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("SshServer:" + sshServer); //$NON-NLS-1$
		System.out.println("SshPort:" + sshPort); //$NON-NLS-1$
		System.out.println("SshUser:" + sshUser); //$NON-NLS-1$
		System.out.println("SshPassword:" + sshPassword); //$NON-NLS-1$
		sshClient = new SshClient(sshServer, sshPort, sshUser, sshPassword);
	}
	
	public void close()
	{
		Calendar cal = Calendar.getInstance();		
		System.out.println( sdf.format(cal.getTime()) );
//		Date currentTime = cal.getTime();
    	System.out.println("--------------------"); //$NON-NLS-1$
        System.out.println("Close the browser"); //$NON-NLS-1$
        this.driver.quit();		
	}
	public void sair()
	{
//		https://ibk.bancopaulista.com.br/ibk-paulista/servlet/login?action=logoff
		WebElement sair = this.driver.findElement(By.linkText("Sair"));		 //$NON-NLS-1$
	    sair.click();
	    System.out.println("Saindo"); //$NON-NLS-1$
	}
	public void login()
	{		
		this.driver = new FirefoxDriver();
        this.driver.navigate().to("https://ibk.bancopaulista.com.br/ibk-paulista/servlet/telaLogin"); //$NON-NLS-1$
        timeAndWait(3);
        WebElement elementUsername = this.driver.findElement(By.id("usuario")); //$NON-NLS-1$
        elementUsername.sendKeys(this.fundAccount.getUsuario());
        timeAndWait(3);
        WebElement elementPassword = this.driver.findElement(By.id("senha")); //$NON-NLS-1$
        elementPassword.click();
        this.fundAccount.setSenha(this.fundAccount.getSenha().toLowerCase());
        timeAndWait(3);
        for(int iChar=0;iChar<this.fundAccount.getSenha().length();iChar++)
        {
        	WebElement charPassword = this.driver.findElement(By.name("" + this.fundAccount.getSenha().charAt(iChar))); //$NON-NLS-1$
        	charPassword.click();
        }
        WebElement buttonContinuar = this.driver.findElement(By.id("lnkContinuar")); //$NON-NLS-1$
        buttonContinuar.submit();
        timeAndWait(5);
        WebElement consultas = this.driver.findElement(By.linkText("Consultas")); //$NON-NLS-1$
        consultas.click();
        timeAndWait(2);
        WebElement contaCorrente = this.driver.findElement(By.linkText("Conta Corrente")); //$NON-NLS-1$
        contaCorrente.click();
        timeAndWait(2);
	}

    public void updateExtrato() 
    {
//    	Calendar cal = Calendar.getInstance();
        WebElement extratoCincoDias = this.driver.findElement(By.partialLinkText("5 dias")); //$NON-NLS-1$
        extratoCincoDias.click();
//        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.linkText("Sair"))));
        timeAndWait(3);                  
        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<String> descriptions = new ArrayList<String>();
        ArrayList<String> docNumbers = new ArrayList<String>();
        ArrayList<Double> values = new ArrayList<Double>();
        ArrayList<String> transactions = new ArrayList<String>();
        
        List<WebElement> colData = this.driver.findElements(By.className("coldata")); //$NON-NLS-1$
        
        for(WebElement rowData:colData)
        {
        	//System.out.println("Text: " + rowData.getText());
        	dates.add(rowData.getText());
        }
        
        List<WebElement> colDescExt = this.driver.findElements(By.className("coldescext")); //$NON-NLS-1$
        for(WebElement rowData:colDescExt)
        {
        	//System.out.println("Text: " + rowData.getText());
        	descriptions.add(rowData.getText());
        }

        List<WebElement> colNumDoc = this.driver.findElements(By.className("coldoc")); //$NON-NLS-1$
        for(WebElement rowData:colNumDoc)
        {
        	//System.out.println("Text: " + rowData.getText());
        	docNumbers.add(rowData.getText());
        }

        List<WebElement> colValues = this.driver.findElements(By.className("colvalor")); //$NON-NLS-1$
        for(WebElement rowData:colValues)
        {
        	//System.out.println("Text: " + rowData.getText());
        	if(rowData.getText().contains(",")) //$NON-NLS-1$
        	{
        		values.add(Double.parseDouble(rowData.getText().replace(".", "").replace(",", "."))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        	}
        	else
        	{
        		values.add(0.0);
        	}
        }
        
        List<WebElement> colTransactions = this.driver.findElements(By.className("coltransact")); //$NON-NLS-1$
        for(WebElement rowData:colTransactions)
        {
        	//System.out.println("Text: " + rowData.getText());
        	transactions.add(rowData.getText());
        }        
        
        int iBeginDay=0;
        for(int iMove=dates.size()-1;iMove>0;iMove--)
        {
        	if(descriptions.get(iMove).toLowerCase().equals("saldo c/c")) //$NON-NLS-1$
        	{
        		iBeginDay=iMove;
        		break;
        	}
        }        
        
        ArrayList<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();

        if(dates.size()>0)
        {
	        for(int iMove=iBeginDay;iMove<dates.size();iMove++)
	        {
				Date data=null;
				Date hora=null;
				if(dates.get(iMove).contains("/")) //$NON-NLS-1$
				{
					try {
						data=sdfExtrato.parse(dates.get(iMove));						
					} catch (ParseException e) {
						e.printStackTrace();
					}
					String descricao=descriptions.get(iMove);
					double valor=values.get(iMove);
					String tipoDeMovimentacao = transactions.get(iMove);
					hora = Calendar.getInstance().getTime();
					
					if(descricao.contains("SALDO C/C")) //$NON-NLS-1$
					{					
						Calendar cal = Calendar.getInstance();
						cal.setTime(data);
				        cal.set(Calendar.HOUR_OF_DAY, 23);
				        cal.set(Calendar.MINUTE, 59);
				        cal.set(Calendar.SECOND, 59);
				        hora = cal.getTime();
					}

					movimentacoes.add(new Movimentacao(this.getFundo(),data, hora, descricao, valor, tipoDeMovimentacao, conn));
				}
	        }
	        for(Movimentacao movimentacao:movimentacoes)
	        {
	        	boolean existMov=false;
	        	for(Movimentacao mov:this.extrato.getMovimentacoes())
	        	{	        		
	        		if(movimentacao.getTipoDeMovimentacao().getTipo().equals("S")) //$NON-NLS-1$
	        		{
	        			if(movimentacao.getDescricao().equals(mov.getDescricao()))
	        			{
        					System.out.println();
	        				if(mov.getValor()!=movimentacao.getValor())
	        				{
		        				mov.setValor(movimentacao.getValor());
		        				mov.setHora(movimentacao.getHora());
	        				}

	        				existMov=true;
	        				break;
	        			}
	        		}
	        		
//	        		System.out.println("Comparing " + movimentacao.getDescricao() + " " + movimentacao.getValor() + " to " + mov.getDescricao() + " " +  mov.getValor());
	        		
	        		if(movimentacao.getDescricao().equals(mov.getDescricao()))
	        		{
	        			System.out.println("\tFound equal description " + mov.getDescricao()); //$NON-NLS-1$
	        			System.out.println("ValueStored: " + mov.getValor()); //$NON-NLS-1$
	        			System.out.println("ValueReadedOnline: " + movimentacao.getValor()); //$NON-NLS-1$
	        			if(Double.compare(movimentacao.getValor(),mov.getValor())==0)
	        			{
	        				System.out.println("\tFound equal value " + mov.getValor()); //$NON-NLS-1$
	        				if(movimentacao.getTipoDeMovimentacao().getTipo().equals(mov.getTipoDeMovimentacao().getTipo()))
	        				{
	        					System.out.println("\tFound equal move type " + mov.getTipoDeMovimentacao().getTipo()); //$NON-NLS-1$
			        			existMov=true;
			        			break;	        					
	        				}	        						
	        			}
	        		}	        		
	        	}
	        	
	        	if(!existMov)
	        	{
	        		this.extrato.getMovimentacoes().add(movimentacao);
	        	}
	        }
	        
	        HandlerExtrato.sort(this.extrato);
        }
        
        
        
    }
    
    public static void timeAndWaitDisplay(int n)
    {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        System.out.println( sdf.format(cal.getTime()) );
    	for(int i=0;i<n;i++)
    	{
	        cal = Calendar.getInstance();
	        cal.getTime();
//	        System.out.println( sdf.format(cal.getTime()) );

    		System.out.println("Waiting " +  (n-i) + " seconds " + sdf.format(cal.getTime())); //$NON-NLS-1$ //$NON-NLS-2$
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }

   public static void timeAndWait(int n)
    {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
//        System.out.println( sdf.format(cal.getTime()) );
//        System.out.println("Waiting " +  (n) + " seconds " + sdf.format(cal.getTime()));
    	for(int i=0;i<n;i++)
    	{
	        cal = Calendar.getInstance();
	        cal.getTime();
//	        System.out.println( sdf.format(cal.getTime()) );

//    		System.out.println("Waiting " +  (n-i) + " seconds " + sdf.format(cal.getTime()));
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
public WebDriver getDriver() {
	return this.driver;
}
public void setDriver(WebDriver driver) {
	this.driver = driver;
}
public static SimpleDateFormat getSdf() {
	return sdf;
}
public static void setSdf(SimpleDateFormat sdf) {
	ExtratorBancoPaulista.sdf = sdf;
}
public static SimpleDateFormat getSdfd() {
	return sdfd;
}
public static void setSdfd(SimpleDateFormat sdfd) {
	ExtratorBancoPaulista.sdfd = sdfd;
}
public static DecimalFormat getDf() {
	return df;
}
public static void setDf(DecimalFormat df) {
	ExtratorBancoPaulista.df = df;
}
public Conta getFundAccount() {
	return this.fundAccount;
}
public void setFundAccount(Conta fundAccount) {
	this.fundAccount = fundAccount;
}
public static SimpleDateFormat getSdfExtrato() {
	return sdfExtrato;
}
public static void setSdfExtrato(SimpleDateFormat sdfExtrato) {
	ExtratorBancoPaulista.sdfExtrato = sdfExtrato;
}
public void setExtrato(Extrato extrato) {
	this.extrato = extrato;
}
public Extrato getExtrato() {
	return this.extrato;
}
public static String getServer() {
	return server;
}
public static void setServer(String server) {
	ExtratorBancoPaulista.server = server;
}
public static int getPort() {
	return port;
}
public static void setPort(int port) {
	ExtratorBancoPaulista.port = port;
}
public static String getUserName() {
	return userName;
}
public static void setUserName(String userName) {
	ExtratorBancoPaulista.userName = userName;
}
public static String getDbName() {
	return dbName;
}
public static void setDbName(String dbName) {
	ExtratorBancoPaulista.dbName = dbName;
}
public static String getPasswordDb() {
	return passwordDb;
}
public static void setPasswordDb(String passwordDb) {
	ExtratorBancoPaulista.passwordDb = passwordDb;
}
public static Connection getConn() {
	return conn;
}
public static void setConn(Connection conn) {
	ExtratorBancoPaulista.conn = conn;
}
public static MySQLAccess getMysql() {
	return mysql;
}
public static void setMysql(MySQLAccess mysql) {
	ExtratorBancoPaulista.mysql = mysql;
}

public FundoDeInvestimento getFundo() {
	return this.fundo;
}

public void setFundo(FundoDeInvestimento fundo) {
	this.fundo = fundo;
}

public static SshClient getSshClient() {
	return sshClient;
}

public static void setSshClient(SshClient sshClient) {
	ExtratorBancoPaulista.sshClient = sshClient;
}

public static String getSshServer()
{
	return sshServer;
}

public static void setSshServer(String sshServer)
{
	ExtratorBancoPaulista.sshServer = sshServer;
}

public static String getSshUser()
{
	return sshUser;
}

public static void setSshUser(String sshUser)
{
	ExtratorBancoPaulista.sshUser = sshUser;
}

public static String getSshPassword()
{
	return sshPassword;
}

public static void setSshPassword(String sshPassword)
{
	ExtratorBancoPaulista.sshPassword = sshPassword;
}
}