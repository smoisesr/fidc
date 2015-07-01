package mvcapital.portalfidc;
import java.applet.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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

import operation.HandlerOperacoes;
import operation.HandlerStatus;
import operation.Operacao;
import operation.OperationSummary;
import operation.Status;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import relatorio.cessao.BlockIdentificacao;
import relatorio.cessao.BlockDireitosCreditorios;
import relatorio.cessao.HandlerDireitoCreditorio;
import relatorio.cessao.Relatorio;
import relatorio.cessao.DireitoCreditorio;
import relatorio.cessao.TipoDeRecebivel;
import utils.OperatingSystem;
import utils.SshClient;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import concentracao.UpdateConcentracao;
import conta.Conta;
import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class OperadorPortalPaulistaChrome  
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); //$NON-NLS-1$
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd"); //$NON-NLS-1$
	private static SimpleDateFormat sdtfd =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$
	private static SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$

    private static DecimalFormat dfs = new DecimalFormat("#.00"); //$NON-NLS-1$

	private ArrayList<Operacao> operations = new ArrayList<Operacao>();
	private ArrayList<Operacao> blackListOperations = new ArrayList<Operacao>();
	
	private static DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    private WebDriver driver = null;
	private Calendar cal = Calendar.getInstance();
    private Calendar calLimit=Calendar.getInstance();
    private Date limitTime=this.calLimit.getTime();
	private Date currentTime = this.cal.getTime();
	private static Conta managerAccount;
	private MySQLAccess mysql = null;
	public static Connection conn = null;
	private WebDriverWait wait = null;
	private String server = "localhost"; //$NON-NLS-1$
	private int port = 3306;		
	private String userName = "root";  //$NON-NLS-1$
	private String password = "root"; //$NON-NLS-1$
	private String dbName = "root"; //$NON-NLS-1$
	
	private String sshServer = ""; //$NON-NLS-1$
	private int sshPort = 3306;		
	private String sshUser = ""; //$NON-NLS-1$
	private String sshPassword = ""; //$NON-NLS-1$
	
//	private SshClient sshClient = new SshClient("192.168.2.122", 22, "moises", "preciosa"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private SshClient sshClient = null;	
	private String rootDownloadsLinux = ""; //$NON-NLS-1$
	private String rootDownloadsLocal = ""; //$NON-NLS-1$
	private String rootReportsLocal = ""; //$NON-NLS-1$
	private String rootReportsLocalLinux = "/home/Reports"; //$NON-NLS-1$
	private String rootReportsLinux = "/home/Reports"; //$NON-NLS-1$
	private String hostname = ""; //$NON-NLS-1$
	private int clickX=0;
	private int clickY=0;
	private int scrollSize=0;
	private UpdateConcentracao uc = new UpdateConcentracao();
	private boolean justApprouved=false;
	private boolean justReadedTransferDetails=false;
	
	public OperadorPortalPaulistaChrome()
	{
		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    this.hostname = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
		    System.out.println("Hostname can not be resolved"); //$NON-NLS-1$
		}		
		System.out.println("Hostname: " + this.hostname); //$NON-NLS-1$

		System.out.println("RootDownloadsLinux: " + this.rootDownloadsLinux); //$NON-NLS-1$
		this.readConf();
		if(this.hostname.toLowerCase().contains("mvcapital12")) //$NON-NLS-1$
		{
			this.clickX = 810;
			this.clickY = 520;
			this.rootDownloadsLinux = "/home/"+this.sshUser+"/Downloads"; //$NON-NLS-1$ //$NON-NLS-2$
			this.rootDownloadsLocal = "C:\\DownloadsPortal"; //$NON-NLS-1$
			this.rootReportsLocal = "C:\\DownloadsPortal\\Reports"; //$NON-NLS-1$
			this.rootReportsLocalLinux = "/home/DownloadsPortal2/Reports"; //$NON-NLS-1$
			this.scrollSize = 100;
		}
		else if(this.hostname.toLowerCase().contains("mvcapital06")) //$NON-NLS-1$
		{
			this.clickX = 670;
			this.clickY = 440;			
			this.rootDownloadsLinux = "/home/"+this.sshUser+"/Downloads"; //$NON-NLS-1$ //$NON-NLS-2$
			this.rootDownloadsLocal = "C:\\DownloadsPortal"; //$NON-NLS-1$
			this.rootReportsLocal = "C:\\DownloadsPortal\\Reports"; //$NON-NLS-1$
			this.rootReportsLocalLinux = "/home/DownloadsPortal/Reports"; //$NON-NLS-1$
			this.scrollSize = 110;
		}
		else
		{
			this.clickX = 670;
			this.clickY = 440;			
			this.rootDownloadsLinux = "/home/"+this.sshUser+"/Downloads"; //$NON-NLS-1$ //$NON-NLS-2$
			this.rootDownloadsLocal = "C:\\DownloadsPortal"; //$NON-NLS-1$
			this.rootReportsLocal = "C:\\DownloadsPortal\\Reports"; //$NON-NLS-1$
			this.rootReportsLocalLinux = "/home/Reports"; //$NON-NLS-1$
			this.scrollSize = 110;			
		}
		
		this.sshClient = new SshClient(this.sshServer, this.sshPort, this.sshUser, this.sshPassword); 
		this.mysql = new MySQLAccess(this.server, this.port, this.userName, this.password, this.dbName);
		this.mysql.connect();	
		OperadorPortalPaulistaChrome.conn = (Connection) this.mysql.getConn();
		readManagerAccount();
	}
	
	public OperadorPortalPaulistaChrome(Conta managerAccount2) {
		OperadorPortalPaulistaChrome.readManagerAccount();
	}

	public void openBrowser()
	{
		FirefoxProfile fxProfile = new FirefoxProfile();

	    fxProfile.setPreference("browser.download.folderList",2); //$NON-NLS-1$
	    fxProfile.setPreference("browser.download.manager.showWhenStarting",false); //$NON-NLS-1$
	    
	    System.out.println("OS Name: " + System.getProperty("os.name").toLowerCase()); //$NON-NLS-1$ //$NON-NLS-2$
	    
		if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			fxProfile.setPreference("browser.download.dir", this.getRootDownloadsLocal()); //$NON-NLS-1$
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux")) //$NON-NLS-1$ //$NON-NLS-2$
		{			
			fxProfile.setPreference("browser.download.dir", this.getRootDownloadsLinux()); //$NON-NLS-1$
		}
	    //fxProfile.setPreference("browser.download.dir","downloads");
	    fxProfile.setPreference("browser.helperApps.alwaysAsk.force", false); //$NON-NLS-1$
	    fxProfile.setPreference("browser.download.manager.showWhenStarting",false); //$NON-NLS-1$
	    fxProfile.setPreference("browser.pdfjs.disabled",true); //$NON-NLS-1$
	    fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",true); //$NON-NLS-1$
	    this.driver = new FirefoxDriver(fxProfile);
		this.wait = new WebDriverWait(this.driver, 20);		
	}
	
	public void openBrowserChrome()
	{

//		ChromeOptions options = new ChromeOptions();
		if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
		{
//			options.addArguments("user-data-dir=C:\\Users\\Moises\\AppData\\Local\\Google\\Chrome\\User Data");			 //$NON-NLS-1$
//			System.out.println("Chrome options at windows"); //$NON-NLS-1$
			System.setProperty("webdriver.chrome.driver", "C:\\Webdriver\\chromedriver.exe"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
		{	
//			options.addArguments("user-data-dir=/home/robot/.config/google-chrome"); //$NON-NLS-1$
//			System.out.println("Chrome options at linux"); //$NON-NLS-1$
			System.setProperty("webdriver.chrome.driver", "/home/Webdriver/chromedriver"); //$NON-NLS-1$ //$NON-NLS-2$
		}
//		System.out.println("Opening Chrome with profile options"); //$NON-NLS-1$
//		this.driver = new ChromeDriver(options);
		this.driver = new ChromeDriver();
		this.wait = new WebDriverWait(this.driver, 20);		
	}	
	
	public static void main(String[] args)	
	{		
		OperadorPortalPaulistaChrome operador = new OperadorPortalPaulistaChrome();		
		Calendar calNow = Calendar.getInstance();
		if(calNow.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calNow.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
		{
			System.out.println("Waiting to pass the weekend!!"); //$NON-NLS-1$
			operador.writeOperationSummary();
			try
			{
				Thread.sleep(5000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}				
		}
		else
		{
			System.out.println("Just a week day!"); //$NON-NLS-1$
			System.out.println("I'll wake up and start to work, \n" //$NON-NLS-1$
					+ "hopefully the Paulista's portal will not be down"); //$NON-NLS-1$
		}			
		
		Calendar cal = Calendar.getInstance();
		Date currentTime = cal.getTime();		
		operador.getSshClient().connect();
		operador.setUc(new UpdateConcentracao(OperadorPortalPaulistaChrome.conn));

		operador.getUc();
		UpdateConcentracao.update();
//		operador.openBrowser();
		operador.openBrowserChrome();
		operador.login();
		while (currentTime.before(operador.limitTime))
		{	
			
			operador.monitor();
			cal = Calendar.getInstance();
			currentTime = cal.getTime();
			HandlerOperacoes.timeAndWait(3);
		}
		operador.logout();
		operador.getSshClient().disconnect();
		return;
	}

	public void monitor() 
	{
		this.cal=Calendar.getInstance();
    	System.out.println("--------------------"); //$NON-NLS-1$
    	System.out.println("Monitoring at\t" + sdf.format(this.cal.getTime())); //$NON-NLS-1$
    	System.out.println("--------------------"); //$NON-NLS-1$
    	
    	if(this.operations.size()==0)
    	{
    		writeOperationSummary();
    		ArrayList<Operacao> storedOperations = HandlerOperacoes.readStoredOperations(OperadorPortalPaulistaChrome.conn);
    		if(storedOperations.size()!=0)
    		{
	    		for(Operacao op:storedOperations)
	    		{
	    			System.out.println("---------------------------------------"); //$NON-NLS-1$
	    			System.out.println("Adding stored operation " + op.getFundo().getNome() + "\t" +op.getNomeArquivo()); //$NON-NLS-1$ //$NON-NLS-2$
	    			System.out.println("---------------------------------------"); //$NON-NLS-1$
//	    			HandlerOperacoes.assessOperation(op);
	    			this.operations.add(op);
	    			this.setJustReadedTransferDetails(true);
	    			writeOperationSummary();
	    		}
    		}
    	}
    	
    	if(this.operations.size()==0 || this.isJustApprouved() || this.isJustReadedTransferDetails())
    	{
    	    if(this.operations.size()>0)
    	    {
    	    	JavascriptExecutor jse = (JavascriptExecutor) this.driver;
//        		System.out.println("Scroll up before click on pdf link ");
        		int scrollUp = -900-this.scrollSize*this.operations.size();
//        		System.out.println("ScrollUp: " + scrollUp);
        		jse.executeScript("window.scrollBy(0, " + scrollUp +")", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    	    }
	    	WebElement linkLiquidacao = this.driver.findElement(By.linkText("Liquida��o")); //$NON-NLS-1$
	    	linkLiquidacao.click();
	    	HandlerOperacoes.timeAndWait(5);
	        WebElement xField = this.driver.findElement(By.className("search-choice-close")); //$NON-NLS-1$
	    	xField.click();
	    	this.setJustApprouved(false);
    	}	    
	    if(this.operations.size()>0)
	    {
	    	JavascriptExecutor jse = (JavascriptExecutor) this.driver;
//    		System.out.println("Scroll up before click on pdf link ");
    		int scrollUp = -900-this.scrollSize*this.operations.size();
//    		System.out.println("ScrollUp: " + scrollUp);
    		jse.executeScript("window.scrollBy(0, " + scrollUp +")", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	    }
	    HandlerOperacoes.timeAndWait(3);
        WebElement buttonPesquisar = this.driver.findElement(By.id("pesquisa")); //$NON-NLS-1$
    	buttonPesquisar.click();
    	System.out.println("Pesquisar clicked"); //$NON-NLS-1$
    	HandlerOperacoes.timeAndWait(4);
        
    	List<WebElement> listOfRowsLiquidacao = this.driver.findElements(By.className("rowLiquidacao")); //$NON-NLS-1$
    	List<WebElement> listOfPdfCessao = this.driver.findElements(By.name("pdfCessao")); //$NON-NLS-1$
//    	List<WebElement> listOfPdfHistorico = this.driver.findElements(By.name("pdfHistorico")); //$NON-NLS-1$
    	
    	if (listOfRowsLiquidacao.size()==0 && listOfPdfCessao.size() == 0)
    	{
    		System.out.println("Writing an empty summary!"); //$NON-NLS-1$
    		writeOperationSummary();
    		return;
    	}

    	if(listOfRowsLiquidacao.size()%listOfPdfCessao.size()==0)
    	{
        	System.out.println("ListOfRowsLiquidacaoSize: " + listOfRowsLiquidacao.size()); //$NON-NLS-1$
        	System.out.println("ListOfPdfCessao: " + listOfPdfCessao.size()); //$NON-NLS-1$
//        	System.out.println("ListOfPdfHistorico: " + listOfPdfHistorico.size()); //$NON-NLS-1$
        	System.out.println(listOfRowsLiquidacao.size()/listOfPdfCessao.size() + " fields"); //$NON-NLS-1$
        	System.out.println(listOfPdfCessao.size() + " ops"); //$NON-NLS-1$
    	}
    	
    	ArrayList<ArrayList<WebElement>> rowsOperation = new ArrayList<ArrayList<WebElement>>();
    	
    	int countWE=0;
    	int iOp=0;
    	ArrayList<WebElement> rowLiquidacaoOperacao = new ArrayList<WebElement>();
    	for(WebElement we:listOfRowsLiquidacao)
    	{   
    		rowLiquidacaoOperacao.add(we);
    		//System.out.println("countWE: " + countWE);
    		if((countWE+1)%6==0)
    		{
    			//System.out.println("\tiOp: " + iOp);
    			rowLiquidacaoOperacao.add(listOfPdfCessao.get(iOp));
//    			rowLiquidacaoOperacao.add(listOfPdfHistorico.get(iOp));
    			rowsOperation.add(rowLiquidacaoOperacao);
    			iOp++;
    			if(countWE<listOfRowsLiquidacao.size())
    			{
    				rowLiquidacaoOperacao = new ArrayList<WebElement>();
    			}
    		}    		    		
    		countWE=countWE+1;
    	}

    	System.out.println("rowsOperation: " + rowsOperation.size()); //$NON-NLS-1$
    	
    	for(ArrayList<WebElement> row:rowsOperation)
    	{
    		if(row.size()<7)
    		{
    			writeOperationSummary();
    			return;
    		}
    		for(WebElement we:row)
    		{
    			if(we.isDisplayed())
    			{
    				System.out.println("Doing nothing because we is not displayed"); //$NON-NLS-1$
    			}
    			else
    			{
    				System.out.println("Missing displayed elements"); //$NON-NLS-1$
    				writeOperationSummary();
    				return;
    			}
    		}
    	}
    	
    	System.out.println("All WebElements in correct number!"); //$NON-NLS-1$
//    	System.out.println("rowsOperation: " + rowsOperation.size());
    	
    	ArrayList<Operacao> onlineOperations = new ArrayList<Operacao>();
    	
    	for(ArrayList<WebElement> rowOperation:rowsOperation)
    	{
//    		JavascriptExecutor jse = (JavascriptExecutor) this.driver;
//    		System.out.println("Scroll down before click on pdf link "); //$NON-NLS-1$
//    		jse.executeScript("window.scrollBy(0, " + this.scrollSize + ")", "");  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
//    		System.out.println("After Scroll down before click on pdf link "); //$NON-NLS-1$
    		Operacao operacao = readOperationBasic(rowOperation);    		
    		onlineOperations.add(operacao);
    		boolean existOp=false;
    		for(Operacao op:this.operations)
    		{
    			if(HandlerOperacoes.equals(operacao, op))
    			{
    				existOp=true;
    				
    				if(!op.getStatuses().get(op.getStatuses().size()-1).getDescription().equals(operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription())
    						&& !op.isTedEnviada())
    				{
    					this.cal = Calendar.getInstance();
    					Date lastStatusTime = this.cal.getTime();
    					operacao.getStatuses().get(0).setBeginDate(lastStatusTime);
    					System.out.println("-- Changed Status --");    					 //$NON-NLS-1$
    					System.out.println("From " + op.getStatuses().get(op.getStatuses().size()-1).getDescription()); //$NON-NLS-1$
    					System.out.println("To  " + operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription()); //$NON-NLS-1$
    					
    					String beforeStatusDescription = op.getStatuses().get(op.getStatuses().size()-1).getDescription();
    					String currentStatusDescription = operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription();

    					if(!op.isAprovado() 
    							&& beforeStatusDescription.contains("Gestor") //$NON-NLS-1$
    							&& !beforeStatusDescription.contains("TED") //$NON-NLS-1$
    							&& currentStatusDescription.contains("Aguardando Assinatura Digital") //$NON-NLS-1$
    							)
    					{
        					Status statusAprovadoManualmente = new Status("Aprovado manualmente",Calendar.getInstance().getTime()); //$NON-NLS-1$
        					op.getStatuses().add(statusAprovadoManualmente);
    					}
    					
    					boolean haveStatusAlready=false;
    					
    					for(Status s:op.getStatuses())
    					{
    						if(s.getDescription().equals(currentStatusDescription))
    						{
    							haveStatusAlready=true;
    							break;
    						}
    					}
    					
    					if(!haveStatusAlready)
    					{
    						op.getStatuses().add(operacao.getStatuses().get(operacao.getStatuses().size()-1));
    					}
    					
    					int iStatus=0;
    					for(Status status:op.getStatuses())
    					{
    						if(status.getDescription().toLowerCase().contains("cancelado")) //$NON-NLS-1$
    						{
    							System.out.println("Trying to remove: " + op.getStatuses().get(iStatus).getDescription()); //$NON-NLS-1$
    							op.getStatuses().remove(iStatus);
    							iStatus=iStatus-1;
    							System.out.println("After removing cancelado"); //$NON-NLS-1$
    							break;
    						}    						
    						iStatus=iStatus+1;
    					}  
    					
    				}

					if(!op.isTedEnviada())
		        	{
		        		System.out.println("--Check electronic transfer--"); //$NON-NLS-1$
		        		System.out.println("--For " + op.getNomeArquivo() + "--"); //$NON-NLS-1$ //$NON-NLS-2$
		        		
		        		boolean haveResourcesSent=false;
		        		for(Status status:op.getStatuses())
		        		{
		        			if(status.getDescription().toLowerCase().contains("recursos enviada")) //$NON-NLS-1$
		        			{
		        				haveResourcesSent=true;
		        				break;
		        			}
		        		}
		        		
		        		if(haveResourcesSent)
		        		{        			
		        			System.out.println("\tHaveResourcesSent"); //$NON-NLS-1$
		        			if(!op.isHaveTransferDetailsChecked())
		        			{
		        				readOperationTransferDetails(rowOperation, op);
				    			HandlerOperacoes.updateStore(op, conn);
				    			writeOperationSummary();
				    			return;
		        			}
		        			HandlerOperacoes.transferDone(op, conn);
		        		}
		        		else
		        		{
		        			System.out.println("Skip checking transfer controller"); //$NON-NLS-1$
		        		}
		        	}        						


					if(!op.getContaExterna1().getAgencia().equals("") //$NON-NLS-1$
							|| !op.getContaExterna2().getAgencia().equals("") //$NON-NLS-1$
							|| !op.getContaInterna().getAgencia().equals("") //$NON-NLS-1$
							)
					{
						if(!op.isRegistrado())
						{
							System.out.println("Saving Operation details to database"); //$NON-NLS-1$
							HandlerOperacoes.store(op, conn);
							System.out.println("Operation stored database"); //$NON-NLS-1$
							HandlerStatus.store(op, conn);
							System.out.println("Status stored database"); //$NON-NLS-1$
							HandlerDireitoCreditorio.store(op, conn);
							System.out.println("Titles stored database"); //$NON-NLS-1$
							op.setRegistrado(true);
						}
					}
					else
					{
						System.out.println("Operacao sem dados bancarios"); //$NON-NLS-1$
						readOperationTransferDetails(rowOperation, op);
						writeOperationSummary();
						return;
					}
    				
    				if(op.isAprovado())
    				{
    					System.out.println("Aprovado"); //$NON-NLS-1$
    					if(op.getStatuses().get(op.getStatuses().size()-1).getDescription().contains("aprova��o do Gestor")) //$NON-NLS-1$
    					//if(op.getStatus().get(op.getStatus().size()-1).getDescription().contains("Reprovado"))
    		    		{
    						System.out.println("Aguardando Gestor"); //$NON-NLS-1$
    						if(op.getStatuses().get(op.getStatuses().size()-1).getDescription().contains("TED")) //$NON-NLS-1$
    						{
    							System.out.println("Gestor de TED"); //$NON-NLS-1$
    							//this.setJustReadedTransferDetails(true);
    							break;
    						}
    						else
    						{
    							System.out.println("N�o � gestor de TED"); //$NON-NLS-1$
    		        			if(!op.isHaveTransferDetailsChecked())
    		        			{
    		        				readOperationTransferDetails(rowOperation, op);
    				    			HandlerOperacoes.updateStore(op, conn);
    		        			}
    			    			System.out.println("--------------------"); //$NON-NLS-1$
    			    	    	System.out.println("Approuving at\t" + sdf.format(this.cal.getTime())); //$NON-NLS-1$
    			    	    	System.out.println("--------------------");	    	    	 //$NON-NLS-1$
    			    			approuve(op);
    			    			writeOperationSummary();
    			    			return;
    						}
    		    		}
    				}
    				    				
    				break;
    			}   
    			
    		}

    		if(!existOp)
    		{
    			HandlerOperacoes.readOperationRelatorio(rowOperation, operacao,this.getRootDownloadsLinux(),this.sshClient,this.clickX, this.clickY);
    			if(operacao.getRelatorio().getBlockDireitosCreditorios().getDireitosCreditorios().size()==0)
    			{
    				System.out.println("Trouble reading pdf file!"); //$NON-NLS-1$
    				break;
    			}
    			else
    			{
    				System.out.println("PDF File successfully read"); //$NON-NLS-1$
    			}
    			//assessOperation(operacao);
    			OperationSummary resumo = new OperationSummary(operacao);
    			operacao.setResumo(resumo);
    			HandlerOperacoes.assessOperation(operacao, conn);
    			
        		if(operacao.getResumo().getSacadosAttempt().size()==0)
    	    	{
    	    		System.out.println("Trouble reading sacados! - Try again"); //$NON-NLS-1$
    	    		break;
    	    	}
        		
        		readOperationTransferDetails(rowOperation, operacao);
				this.cal = Calendar.getInstance();
				Date lastCheckTime = this.cal.getTime();		
				operacao.getStatuses().get(operacao.getStatuses().size()-1).setBeginDate(lastCheckTime);
				//operacao.getStatus().get(operacao.getStatus().size()-1).setEndDate(lastCheckTime);
				
				if(!operacao.getContaExterna1().getAgencia().equals("") //$NON-NLS-1$
						|| !operacao.getContaExterna2().getAgencia().equals("") //$NON-NLS-1$
						|| !operacao.getContaInterna().getAgencia().equals("")) //$NON-NLS-1$
				{
					System.out.println("Saving New Operation details to database"); //$NON-NLS-1$
					HandlerOperacoes.store(operacao, conn);
					System.out.println("Operation stored database"); //$NON-NLS-1$
					HandlerStatus.store(operacao, conn);
					System.out.println("Status stored database"); //$NON-NLS-1$
					HandlerDireitoCreditorio.store(operacao, conn);
					System.out.println("Titles stored database"); //$NON-NLS-1$
				}
    			this.operations.add(operacao);
    			System.out.println("---New Array Operations Element---"); //$NON-NLS-1$
    			this.operations.get(this.operations.size()-1).show();
    			//operacao.show();
    			if(operacao.isAprovado())
    			{
    				System.out.println("Aprovado");    				 //$NON-NLS-1$
    				if(operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription().contains("aprova��o do Gestor")) //$NON-NLS-1$
    				//if(operacao.getStatus().get(operacao.getStatus().size()-1).getDescription().contains("Reprovado"))
    	    		{
    					System.out.println("Aguardando Gestor"); //$NON-NLS-1$
						if(operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription().contains("TED")) //$NON-NLS-1$
						{
							System.out.println("Gestor de TED"); //$NON-NLS-1$
							this.setJustReadedTransferDetails(true);
							writeOperationSummary();
							return;
						}
						else
    					{
    						System.out.println("N�o � gestor de TED"); //$NON-NLS-1$
//    		    			readOperationTransferDetails(rowOperation, operacao);
    		    			System.out.println("--------------------"); //$NON-NLS-1$
    		    	    	System.out.println("Approuving at\t" + sdf.format(this.cal.getTime())); //$NON-NLS-1$
    		    	    	System.out.println("--------------------");	    	    	 //$NON-NLS-1$
    		    			approuve(operacao);
    		    			writeOperationSummary();
    		    			return;
    					}
    	    		}
        			else
        			{
        				writeOperationSummary();
        				return;
        			}

    			}
    			else
    			{
    				writeOperationSummary();
    				return;
    			}
    		}
//    		onlineOperations.add(operacao);    		
    	}

        for(Operacao op:this.operations)
        {
        	boolean isErased=true;
        	if(onlineOperations.size()==0)
        	{
        		writeOperationSummary();
        		return;
        	}
        	
        	for(Operacao oop:onlineOperations)
        	{
        		if(HandlerOperacoes.equals(oop, op))
        		{
        			isErased=false;        			
        		}
        	}
        	
        	if(!op.isTedEnviada())
        	{
        		System.out.println("--Check electronic transfer--"); //$NON-NLS-1$
        		System.out.println("--For " + op.getNomeArquivo() + "--"); //$NON-NLS-1$ //$NON-NLS-2$
        		
        		boolean haveResourcesSent=false;
        		for(Status status:op.getStatuses())
        		{
        			if(status.getDescription().toLowerCase().contains("recursos enviada")) //$NON-NLS-1$
        			{
        				haveResourcesSent=true;
        				break;
        			}
        		}
        		
        		if(haveResourcesSent)
        		{        			
        			System.out.println("\tHaveResourcesSent"); //$NON-NLS-1$
        			HandlerOperacoes.transferDone(op, conn);
        		}
        		else
        		{
        			System.out.println("Skip checking transfer controller"); //$NON-NLS-1$
        		}
        	}        	

        	if(isErased && !op.getStatuses().get(op.getStatuses().size()-1).getDescription().equals("Cancelado"))        		 //$NON-NLS-1$
        	{
        		Status statusCancelado = new Status(op.getIdOperacao(), "Cancelado",Calendar.getInstance().getTime(),conn); //$NON-NLS-1$
        		op.getStatuses().add(statusCancelado);
        		HandlerOperacoes.updateConcentracaoCancelado(op);
        	}
        	else if(isErased && op.getStatuses().get(op.getStatuses().size()-1).getDescription().equals("Cancelado")) //$NON-NLS-1$
        	{
        		op.getStatuses().get(op.getStatuses().size()-1).setEndDate(Calendar.getInstance().getTime());
        	}
			op.getStatuses().get(op.getStatuses().size()-1).setEndDate(Calendar.getInstance().getTime());
        }

    	System.out.println("--------------------"); //$NON-NLS-1$
    	writeOperationSummary();
//    	String stringReport="";
    	
    }
	 
	 public void writeOperationSummary()
	 {
	    	String operationsSummary = ""; //$NON-NLS-1$
	    	for(Operacao op:this.operations)
	    	{
	    		String stringAprovadoGestor="ReprovadoGestor"; //$NON-NLS-1$
	    		System.out.println("SizeStatus: " + op.getStatuses().size()); //$NON-NLS-1$
	    		if(op.getStatuses().size()==0)
	    		{
	    			continue;
	    		}
	    		op.getStatuses().get(op.getStatuses().size()-1).setEndDate(Calendar.getInstance().getTime());
	    		if(op.isAprovado())
	    		{
	    			stringAprovadoGestor="AprovadoGestor"; //$NON-NLS-1$
	    		}
	    		
	    		String stringTiposDeDireitoCreditorio=""; //$NON-NLS-1$
	    		for(TipoDeRecebivel tipo:op.getRelatorio().getBlockDireitosCreditorios().getTiposDeRecebivel())
	    		{
	    			if(stringTiposDeDireitoCreditorio.equals("")) //$NON-NLS-1$
	    			{
	    				stringTiposDeDireitoCreditorio=stringTiposDeDireitoCreditorio+tipo.getDescricao();
	    			}
	    			else
	    			{
	    				stringTiposDeDireitoCreditorio=stringTiposDeDireitoCreditorio+","+tipo.getDescricao(); //$NON-NLS-1$
	    			}
	    		}    		
	    		
	    		if(!op.isRegistrado())
	    		{
		    		System.out.println("I must save this operation at DataBase, without the titles on it"); //$NON-NLS-1$
		    		System.out.println("Fundo: " + op.getFundo().getNome()); //$NON-NLS-1$
		    		System.out.println("Arquivo: " + op.getNomeArquivo()); //$NON-NLS-1$
		    		System.out.println("Cedente: " + op.getCedente().getNome());		    		 //$NON-NLS-1$
//		    		HandlerOperacoes.store(op, conn);
//		    		HandlerStatus.store(op, conn);
//		    		HandlerDireitoCreditorio.store(op, conn);
		    		op.setRegistrado(true);
	    		}
	    		else
	    		{
		    		System.out.println("Operation already saved at DataBase");		    		 //$NON-NLS-1$
	    		}

	    		if(op.getContaInterna().getIdConta()!=0
	    				||op.getContaExterna1().getIdConta()!=0
	    				||op.getContaExterna2().getIdConta()!=0
	    				)
	    		{
		    		HandlerOperacoes.store(op, conn);
		    		HandlerStatus.store(op, conn);	    			
	    		}
	    		
	    		operationsSummary = operationsSummary 
	    						+ op.getFundo().getNome()
	    						+ ";" + op.getNomeArquivo() //$NON-NLS-1$
	    						+ ";" + stringAprovadoGestor //$NON-NLS-1$
	    						+ ";" + op.getCedente().getNome() //$NON-NLS-1$
	    						+ ";" + stringTiposDeDireitoCreditorio //$NON-NLS-1$
	    						+ ";" + op.getCertificadora() //$NON-NLS-1$
	    						+ ";" + dfs.format(op.getTotalAquisicao()) //$NON-NLS-1$
	    						+ ";" + dfs.format(op.getTotalNominal()) //$NON-NLS-1$
	    						+ ";" + op.getStatuses().get(op.getStatuses().size()-1).getDescription() //$NON-NLS-1$
								+ ";" + sdf.format(op.getRegisterTime()) //$NON-NLS-1$
								+ ";" + sdf.format(op.getStatuses().get(op.getStatuses().size()-1).getBeginDate()) //$NON-NLS-1$
								+ ";" + sdf.format(op.getStatuses().get(op.getStatuses().size()-1).getEndDate())    							 //$NON-NLS-1$
								+ ";" + op.getStatuses().get(op.getStatuses().size()-1).getLifeTimeString() //$NON-NLS-1$
	    						+ ";" + dfs.format(op.getValorRemessaSemAdiantamento()) //$NON-NLS-1$
	    						+ ";" + dfs.format(op.getValorRecompraSemAdiantamento()) //$NON-NLS-1$
	    						+ ";" + dfs.format(op.getReembolsoContaNormal()) //$NON-NLS-1$
	    						+ ";" + dfs.format(op.getValorTotalSemAdiantamento()) //$NON-NLS-1$
	    						+ ";" + op.getContaExterna1().getCodigoServidor() //$NON-NLS-1$
	    						+ ";" + op.getContaExterna1().getAgencia() //$NON-NLS-1$
	    						+ ";" + op.getContaExterna1().getNumero() //$NON-NLS-1$
	    						+ ";" + dfs.format(op.getValorTotalCedenteSemAdiantamento1()) //$NON-NLS-1$
	    						+ ";" + op.getContaExterna2().getCodigoServidor() //$NON-NLS-1$
	    						+ ";" + op.getContaExterna2().getAgencia() //$NON-NLS-1$
	    						+ ";" + op.getContaExterna2().getNumero() //$NON-NLS-1$
	    						+ ";" + dfs.format(op.getValorTotalCedenteSemAdiantamento2())    						    						    						 //$NON-NLS-1$
	    						+ ";" + op.getResumo().getDescription() //$NON-NLS-1$
	    						+ "\n" //$NON-NLS-1$
	    				;
	    	}
	    	
	    	System.out.println(operationsSummary);
	    	System.out.println("--Reprovados - Detail--"); //$NON-NLS-1$
	    	String reprouvedOperationsSummary=""; //$NON-NLS-1$
	    	for(Operacao op:this.operations)
	    	{
	    		String stringAprovado="ReprovadoGestor"; //$NON-NLS-1$
	    		String[] descriptionDetails=op.getResumo().getDescription().split(";"); //$NON-NLS-1$
	    		if(!op.isAprovado())
	    		{   			    		    		
	    			reprouvedOperationsSummary=reprouvedOperationsSummary +
	    										op.getFundo().getNomeCurto()
					    						+ "\t" + op.getNomeArquivo()   						 //$NON-NLS-1$
					    						+ "\t" + op.getStatuses().get(op.getStatuses().size()-1).getDescription()    						 //$NON-NLS-1$
					    						+ "\t" + stringAprovado //$NON-NLS-1$
					    						+ "\n" //$NON-NLS-1$
					    						;
	    			for(String detail:descriptionDetails)
	    			{
	    				System.out.println("\t" + detail); //$NON-NLS-1$
	    			}
	    		}
	    	}
	    	System.out.println(reprouvedOperationsSummary);
	    	this.saveReportFile("operationsSummary.txt", operationsSummary); //$NON-NLS-1$
//	    	this.saveReportFileLocal("operationsSummary.txt", operationsSummary); //$NON-NLS-1$
	 }
	 
	public static void readStoredStatusForOperation(Operacao op)
	{
		ArrayList<Status> status = new ArrayList<Status>();
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulistaChrome.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT * FROM operacao WHERE " //$NON-NLS-1$
				       + " dataDeEntrada = " + sdfd.format(Calendar.getInstance().getTime()) //$NON-NLS-1$
				       ;
		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				op.setIdOperacao(rs.getInt("idOperacao")); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		query = "SELECT idStatusOperacao FROM statusoperacao WHERE idOperacao = " + op.getIdOperacao(); //$NON-NLS-1$
		System.out.println(query);
		rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				int idStatusOperacao = rs.getInt("idStatusOperacao"); //$NON-NLS-1$
				status.add(new Status(idStatusOperacao,conn));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		op.setStatuses(status);
	}
	 

	public static int getIdEntidadeBanco(String numeroBanco)
	{
		int idEntidadeBanco=0;
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulistaChrome.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String query = "SELECT idEntidadeBanco FROM codigobanco WHERE codigo=" + numeroBanco; //$NON-NLS-1$
		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idEntidadeBanco = rs.getInt("idEntidadeBanco");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(idEntidadeBanco==0)
		{
			System.out.println("Error bringing idEntidadeBanco"); //$NON-NLS-1$
		}
		return idEntidadeBanco;
	}	
	

	public void saveReportFile(String fileName, String content)
	{
    	File file=null;
    	System.out.println("Trying to create file " + fileName); //$NON-NLS-1$
    	
    	if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
    	{
    		file = new File(this.getRootReportsLocal() + "\\" + fileName); //$NON-NLS-1$
    	}
    	else
    	{
    		file = new File(this.getRootReportsLocalLinux() + "/" + fileName); //$NON-NLS-1$
    	}
		
		String fileNewName = this.getRootReportsLinux() + "/" + fileName; //$NON-NLS-1$
		
		System.out.println("File name " + file.getAbsolutePath()); //$NON-NLS-1$
		System.out.println("New file name " + fileNewName); //$NON-NLS-1$
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		PrintWriter pw = null;
		try 
		{
			pw = new PrintWriter(file, "ISO_8859_1"); //$NON-NLS-1$
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		pw.write(content);
		pw.flush();
		pw.close();
	    String command="";	     //$NON-NLS-1$		
		if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
		{
		    command="cp " + this.getRootReportsLocalLinux() + "/" + fileName + " " + fileNewName; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		    System.out.println("command: " + command); //$NON-NLS-1$
		    this.sshClient.executeCommandOutput(command);
		}	    
	    command="perl " + this.getRootReportsLinux() + "/generateOperationsSummaryWeb.pl"; //$NON-NLS-1$ //$NON-NLS-2$
	    System.out.println("command: " + command); //$NON-NLS-1$
	    this.sshClient.executeCommandOutput(command);		 
	    
	}

	public void saveReportFileLocal(String fileName, String content)
	{
    	File file=null;
    	System.out.println("Trying to create file " + fileName); //$NON-NLS-1$
    	
    	if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
    	{
    		file = new File(this.getRootReportsLocal() + "\\" + fileName); //$NON-NLS-1$
    	}
    	else
    	{
    		file = new File(this.getRootReportsLinux() + "/dev/" + fileName); //$NON-NLS-1$
    	}
		
		System.out.println("File name " + file.getAbsolutePath()); //$NON-NLS-1$

		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		PrintWriter pw = null;
		try 
		{
			pw = new PrintWriter(file, "ISO_8859_1"); //$NON-NLS-1$
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		pw.write(content);
		pw.flush();
		pw.close();
		System.out.println("File " + file.getName() + " saved"); //$NON-NLS-1$ //$NON-NLS-2$
		
//	    String command="perl C:\\DownloadsPortal\\Reports\\generateOperationsSummaryWeb.pl";
//	    WindowsCommand.execute(command);
//	    System.out.println("Perl command executed!");
	    
	    String command="perl " + this.getRootReportsLinux() + "/generateOperationsSummaryWeb.pl"; //$NON-NLS-1$ //$NON-NLS-2$
	    System.out.println("command: " + command); //$NON-NLS-1$
	    this.sshClient.executeCommandOutput(command);		 
	    
	}
	
	public void approuve(Operacao operacao)
	{    	
    	if(operacao.isAprovado())
    	{
    		System.out.println("\nOpera��o " + operacao.getFundo().getNome() + " " + operacao.getCedente().getNome() +  " "+ operacao.getNomeArquivo() + " aprovada!"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    	}	    	

    	if (operacao.isAprovado())
    	{
    		//Click to approuve
    		
    		if (operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription().toLowerCase().contains("assinatura digital")) //$NON-NLS-1$
    		{
    			HandlerOperacoes.updateConcentracao(operacao);
    		}
    		else
    		{
    			if(!this.driver.findElements(By.id("aprovar")).isEmpty()) //$NON-NLS-1$
    			{
		    		WebElement buttonAprovar = this.driver.findElement(By.id("aprovar")); //$NON-NLS-1$
		    		if(buttonAprovar.isDisplayed())
		    		{
			    		buttonAprovar.click();
		    			System.out.println("Aprovar clicked!"); //$NON-NLS-1$
		    		}
		    		else
		    		{
		    			System.out.println("Already Aprovar clicked!"); //$NON-NLS-1$
		    		}
    			}
    			else
    			{
    				System.out.println("Aprovar webElement is not present"); //$NON-NLS-1$
    			}
	    		HandlerOperacoes.updateConcentracao(operacao);
    		}
    	}
    	else
    	{
    		if (operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription().toLowerCase().contains("assinatura digital")) //$NON-NLS-1$
    		{
    			HandlerOperacoes.updateConcentracao(operacao);
    		}    		
    		this.blackListOperations.add(operacao);
    	}

    	System.out.println("Just approuved!"); //$NON-NLS-1$
        this.setJustApprouved(true);
	}
	
	public void readOperationTransferDetails(List<WebElement> rowOperation, Operacao operacao)
	{
		System.out.println("--Reading trasfer details--"); //$NON-NLS-1$
		String nomeFundo=operacao.getFundo().getNome();
		String nomeArquivo=operacao.getNomeArquivo();
		Date dataDeImportacao=Calendar.getInstance().getTime();

		double valorTotalAquisicaoComAdiantamento=0;
		double valorRecompraComAdiantamento=0;
		double reembolsoContaEspecial=0;
		double valorTotalComAdiantamento=0;

		String contaCorrenteInternaPaulista=""; //$NON-NLS-1$
		double valorTotalContaCorrenteInternaPaulista=0;
		String numeroBancoContaCorrenteInternaPaulista=""; //$NON-NLS-1$
		String numeroAgenciaContaCorrenteInternaPaulista=""; //$NON-NLS-1$
		String numeroContaCorrenteInternaPaulista=""; //$NON-NLS-1$

		double valorRemessaSemAdiantamento=0;
		double valorRecompraSemAdiantamento=0;
		double reembolsoContaNormal=0;
		double valorTotalSemAdiantamento=0;

		String contaCedenteSemAdiantamento1=""; //$NON-NLS-1$
		double valorTotalCedenteSemAdiantamento1=0;
		String numeroBancoContaCedenteSemAdiantamento1=""; //$NON-NLS-1$
		String numeroAgenciaContaCedenteSemAdiantamento1=""; //$NON-NLS-1$
		String numeroContaCorrenteContaCedenteSemAdiantamento1=""; //$NON-NLS-1$

		String contaCedenteSemAdiantamento2=""; //$NON-NLS-1$
		double valorTotalCedenteSemAdiantamento2=0;
		String numeroBancoContaCedenteSemAdiantamento2=""; //$NON-NLS-1$
		String numeroAgenciaContaCedenteSemAdiantamento2=""; //$NON-NLS-1$
		String numeroContaCorrenteContaCedenteSemAdiantamento2=""; //$NON-NLS-1$
		
		String certificadora=""; //$NON-NLS-1$
				
        rowOperation.get(5).click();
        System.out.println("--Click--"); //$NON-NLS-1$
        HandlerOperacoes.timeAndWait(5);
		System.out.println("--After waiting--"); //$NON-NLS-1$
    	
//    	System.out.println("\nMore Data");
		if(this.driver.findElement(By.id("nomeFundo")).isDisplayed()) //$NON-NLS-1$
		{	
	        System.out.println("--NomeFundoIsDisplayed--"); //$NON-NLS-1$
	    	WebElement weNomeFundo = this.driver.findElement(By.id("nomeFundo"));	    	 //$NON-NLS-1$
	    	if(!nomeFundo.equals(weNomeFundo.getAttribute("value"))) //$NON-NLS-1$
	    	{
	    		System.out.println("Nome do fundo n�o coincide!"); //$NON-NLS-1$
	    		System.out.println("Tentando novamente!"); //$NON-NLS-1$
	    		//this.approuve();	//Recursive attempt
	    	}
	    	else
	    	{
	    		System.out.println("nomeFundo: " + nomeFundo); //$NON-NLS-1$
	    	}
	    	WebElement weDataDeImportacao = this.driver.findElement(By.id("dataImportacao")); //$NON-NLS-1$
	    	try {
				dataDeImportacao = sdfr.parse(weDataDeImportacao.getAttribute("value")); //$NON-NLS-1$
				System.out.println("dataDeImportacao: " + sdfr.format(dataDeImportacao)); //$NON-NLS-1$
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    	WebElement nomeArquivo2 = this.driver.findElement(By.id("nomeArquivo")); //$NON-NLS-1$
	    	if(!nomeArquivo.equals(nomeArquivo2.getAttribute("value"))) //$NON-NLS-1$
	    	{
	    		System.out.println("Nome do Arquivo n�o coincide!"); //$NON-NLS-1$
	    		System.out.println("Tentando novamente!"); //$NON-NLS-1$
	    		//this.approuve();	//Recursive attempt    		
	    	}
	    	else
	    	{
	//    		System.out.println("nomeArquivo: " + nomeArquivo);
	    	}

	    	if(this.driver.findElement(By.id("valorTotalAquisicaoComAdiantamento")).isDisplayed()) //$NON-NLS-1$
	    	{
	    		System.out.println("--Remessa com adiantamento is displayed--"); //$NON-NLS-1$
	    	}
	    	
	    	WebElement weValorTotalAquisicaoComAdiantamento = this.driver.findElement(By.id("valorTotalAquisicaoComAdiantamento")); //$NON-NLS-1$
	    	String stringValorTotalAquisicaoComAdiantamento = weValorTotalAquisicaoComAdiantamento.getText();
	    	System.out.println("Remesa: " + stringValorTotalAquisicaoComAdiantamento); //$NON-NLS-1$
	    	if(stringValorTotalAquisicaoComAdiantamento.length()>0)
	    	{
	    		valorTotalAquisicaoComAdiantamento=Double.parseDouble(stringValorTotalAquisicaoComAdiantamento
	    																.replace("-R$ ", "-") //$NON-NLS-1$ //$NON-NLS-2$
													    				.replace("R$", "") //$NON-NLS-1$ //$NON-NLS-2$
													    				.replace(".","") //$NON-NLS-1$ //$NON-NLS-2$
													    				.replace(",", ".").trim() //$NON-NLS-1$ //$NON-NLS-2$
													    				);	    		
	    	}

	    	if(this.driver.findElement(By.id("valorRecompraComAdiantamento")).isDisplayed()) //$NON-NLS-1$
	    	{
	    		System.out.println("--Recompra com adiantamento is displayed--"); //$NON-NLS-1$
	    	}
	    	
	    	WebElement weValorRecompraComAdiantamento = this.driver.findElement(By.id("valorRecompraComAdiantamento")); //$NON-NLS-1$
	    	String stringValorRecompraComAdiantamento = weValorRecompraComAdiantamento.getText();
	    	if(stringValorRecompraComAdiantamento.length()>0)
	    	{
	    		valorRecompraComAdiantamento = Double.parseDouble(weValorRecompraComAdiantamento.getText().replace("R$", "").replace(".","").replace(",", ".").trim()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	    	}
	    	System.out.println("stringValorRecompraComAdiantamento: " + stringValorRecompraComAdiantamento); //$NON-NLS-1$
	    	
	    	if(this.driver.findElement(By.id("reembolsoContaEspecial")).isDisplayed()) //$NON-NLS-1$
	    	{
	    		System.out.println("--Reembolso com adiantamento is displayed--"); //$NON-NLS-1$
	    	}
	    	 	
	    	WebElement weReembolsoContaEspecial = this.driver.findElement(By.id("reembolsoContaEspecial")); //$NON-NLS-1$
	    	String stringReembolsoContaEspecial = weReembolsoContaEspecial.getText();
	    	if(stringReembolsoContaEspecial.length()>0)
	    	{
	    		reembolsoContaEspecial = Double.parseDouble(stringReembolsoContaEspecial.replace("R$", "").replace(".","").replace(",", ".").trim()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		    	System.out.println("stringReembolsoContaEspecial: "+ stringReembolsoContaEspecial); //$NON-NLS-1$
	    	}
	    	
	    	
	    	if(this.driver.findElement(By.id("valorTotalComAdiantamento")).isDisplayed()) //$NON-NLS-1$
	    	{
	    		System.out.println("--Valor total com adiantamento is displayed--"); //$NON-NLS-1$
	    	}
	    	
	    	WebElement weValorTotalComAdiantamento = this.driver.findElement(By.id("valorTotalComAdiantamento")); //$NON-NLS-1$
	    	String stringValorTotalComAdiantamento = weValorTotalComAdiantamento.getText();
	    	if(stringValorTotalComAdiantamento.length()>0)
	    	{
	    		valorTotalComAdiantamento = Double.parseDouble(stringValorTotalComAdiantamento.replace("R$", "").replace(".","").replace(",", ".").trim()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		    	System.out.println("Valor total com Adiantamento: " + stringValorTotalComAdiantamento);	    		 //$NON-NLS-1$
	    	}

	    	
	    	WebElement comboContaCorrenteInternaPaulista = this.driver.findElement(By.id("comboContaCorrenteInternaPaulista")); //$NON-NLS-1$
	    	contaCorrenteInternaPaulista = comboContaCorrenteInternaPaulista.getAttribute("value"); //$NON-NLS-1$
	    		    	
	    	WebElement weValorTotalContaCorrenteInternaPaulista = this.driver.findElement(By.id("valorTotalContaCorrenteInternaPaulista")); //$NON-NLS-1$
	    	String stringValorTotalContaCorrenteInternaPaulista=weValorTotalContaCorrenteInternaPaulista.getText();
	    	if(stringValorTotalContaCorrenteInternaPaulista.length()>0)
	    	{
	    		valorTotalContaCorrenteInternaPaulista = Double.parseDouble(stringValorTotalContaCorrenteInternaPaulista.replace("R$", "").replace(".","").replace(",", ".").trim()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	    	}
	    	
	    	WebElement weNumeroBancoContaCorrenteInternaPaulista = this.driver.findElement(By.id("numeroBancoContaCorrenteInternaPaulista")); //$NON-NLS-1$
	    	numeroBancoContaCorrenteInternaPaulista=weNumeroBancoContaCorrenteInternaPaulista.getAttribute("value"); //$NON-NLS-1$
	    	
	    	WebElement weNumeroAgenciaContaCorrenteInternaPaulista = this.driver.findElement(By.id("numeroAgenciaContaCorrenteInternaPaulista")); //$NON-NLS-1$
	    	numeroAgenciaContaCorrenteInternaPaulista=weNumeroAgenciaContaCorrenteInternaPaulista.getAttribute("value");	    	 //$NON-NLS-1$
	    	
	    	WebElement weNumeroContaCorrenteInternaPaulista = this.driver.findElement(By.id("numeroContaCorrenteInternaPaulista")); //$NON-NLS-1$
	    	numeroContaCorrenteInternaPaulista = weNumeroContaCorrenteInternaPaulista.getAttribute("value"); //$NON-NLS-1$
	    	if(valorTotalAquisicaoComAdiantamento!=0)
	    	{
		    	System.out.println("\nOpera��o Conta Interna Paulista");	    	 //$NON-NLS-1$
		    	System.out.println("Remessa: " + valorTotalAquisicaoComAdiantamento); //$NON-NLS-1$
		    	System.out.println("Recompra: " + valorRecompraComAdiantamento); //$NON-NLS-1$
		    	System.out.println("Reembolso de Despesas: " + reembolsoContaEspecial); //$NON-NLS-1$
		    	System.out.println("Valor Total: " + valorTotalComAdiantamento); //$NON-NLS-1$
		    	System.out.println("\nConta Paulista: " + contaCorrenteInternaPaulista); //$NON-NLS-1$
		    	System.out.println("Valor: " + valorTotalContaCorrenteInternaPaulista); //$NON-NLS-1$
		    	System.out.println("Banco: " + numeroBancoContaCorrenteInternaPaulista); //$NON-NLS-1$
		    	System.out.println("Agencia Sem Digito: " + numeroAgenciaContaCorrenteInternaPaulista); //$NON-NLS-1$
		    	System.out.println("Conta Corrente: " + numeroContaCorrenteInternaPaulista); //$NON-NLS-1$
	    	}
	    	
	    	if(this.driver.findElement(By.id("valorRemessaSemAdiantamento")).isDisplayed()) //$NON-NLS-1$
	    	{
	    		System.out.println("--Remessa sem adiantamento is displayed--"); //$NON-NLS-1$
	    	}
	    	
	    	WebElement weValorRemessaSemAdiantamento = this.driver.findElement(By.id("valorRemessaSemAdiantamento")); //$NON-NLS-1$
	    	String stringValorRemessaSemAdiantamento = weValorRemessaSemAdiantamento.getAttribute("value"); //$NON-NLS-1$

	    	if(stringValorRemessaSemAdiantamento.length()>0)
	    	{
		    	System.out.println("Remessa: " + stringValorRemessaSemAdiantamento); //$NON-NLS-1$
	    		valorRemessaSemAdiantamento=Double.parseDouble(stringValorRemessaSemAdiantamento    																
	    															.replace("-R$ ", "-") //$NON-NLS-1$ //$NON-NLS-2$
	    															.replace("R$", "") //$NON-NLS-1$ //$NON-NLS-2$
	    															.replace(".","") //$NON-NLS-1$ //$NON-NLS-2$
	    															.replace(",", ".").trim() //$NON-NLS-1$ //$NON-NLS-2$
	    														);
	    	}
	    	
	    	if(this.driver.findElement(By.id("valorRecompraSemAdiantamento")).isDisplayed()) //$NON-NLS-1$
	    	{
	    		System.out.println("--Recompra sem adiantamento is displayed--"); //$NON-NLS-1$
	    	}
	    	
	    	WebElement weValorRecompraSemAdiantamento = this.driver.findElement(By.id("valorRecompraSemAdiantamento")); //$NON-NLS-1$
	    	String stringValorRecompraSemAdiantamento = weValorRecompraSemAdiantamento.getAttribute("value"); //$NON-NLS-1$

	    	if(stringValorRecompraSemAdiantamento.length()>0)
	    	{
		    	System.out.println("Recompra: " + stringValorRecompraSemAdiantamento); //$NON-NLS-1$
	    		valorRecompraSemAdiantamento = Double.parseDouble(stringValorRecompraSemAdiantamento.replace("R$", "").replace(".","").replace(",", ".").trim()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	    	}

	    	if(this.driver.findElement(By.id("reembolsoContaNormal")).isDisplayed()) //$NON-NLS-1$
	    	{
	    		System.out.println("--Reembolso sem adiantamento is displayed--"); //$NON-NLS-1$
	    	}
	    	
	    	WebElement weReembolsoContaNormal = this.driver.findElement(By.id("reembolsoContaNormal")); //$NON-NLS-1$
	    	String stringReembolsoContaNormal = weReembolsoContaNormal.getAttribute("value"); //$NON-NLS-1$
	    	if(stringReembolsoContaNormal.length()>0)
	    	{
	    		System.out.println("Reembolso: " + stringReembolsoContaNormal); //$NON-NLS-1$
	    		reembolsoContaNormal = Double.parseDouble(stringReembolsoContaNormal.replace("R$", "").replace(".","").replace(",", ".").trim()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	    	}

	    	if(this.driver.findElement(By.id("valorTotalSemAdiantamento")).isDisplayed()) //$NON-NLS-1$
	    	{
	    		System.out.println("--Valor total sem adiantamento is displayed--"); //$NON-NLS-1$
	    	}
	    		    	
	    	WebElement weValorTotalSemAdiantamento = this.driver.findElement(By.id("valorTotalSemAdiantamento")); //$NON-NLS-1$
	    	String stringValorTotalSemAdiantamento = weValorTotalSemAdiantamento.getAttribute("value"); //$NON-NLS-1$
	    	if(stringValorTotalSemAdiantamento.length()!=0)
	    	{
	    		System.out.println("Valor total sem adiantamento: " + stringValorTotalSemAdiantamento); //$NON-NLS-1$
	    		valorTotalSemAdiantamento=Double.parseDouble(stringValorTotalSemAdiantamento.replace("-R$ ", "-").replace("R$", "").replace(".","").replace(",", ".").trim()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	    	}
	    	
	    	
	    	System.out.println("\nOpera��o Conta Externa Paulista"); //$NON-NLS-1$
	    	System.out.println("Remessa: " + valorRemessaSemAdiantamento); //$NON-NLS-1$
	    	System.out.println("Recompra: " + valorRecompraSemAdiantamento); //$NON-NLS-1$
	    	System.out.println("Reembolso de Despesas: " + reembolsoContaNormal); //$NON-NLS-1$
	    	System.out.println("Valor Total: " + valorTotalSemAdiantamento); //$NON-NLS-1$
    	
	    	if(this.driver.findElement(By.id("comboCedenteSemAdiantamento1")).isDisplayed()) //$NON-NLS-1$
	    	{
	    		System.out.println("--Conta sem Adiantamento 1 is displayed--"); //$NON-NLS-1$
	    	}
	    	
	    	WebElement comboContaCedenteSemAdiantamento1 = this.driver.findElement(By.id("comboCedenteSemAdiantamento1")); //$NON-NLS-1$
	    	contaCedenteSemAdiantamento1 = comboContaCedenteSemAdiantamento1.getAttribute("value"); //$NON-NLS-1$
	    	System.out.println("Conta desc: " + contaCedenteSemAdiantamento1); //$NON-NLS-1$
	    	
	    	WebElement weValorTotalCedenteSemAdiantamento1 = this.driver.findElement(By.id("valorTotalCedenteSemAdiantamento1")); //$NON-NLS-1$
	    	String stringValorTotalCedenteSemAdiantamento1 = weValorTotalCedenteSemAdiantamento1.getAttribute("value"); //$NON-NLS-1$
	    	if(stringValorTotalCedenteSemAdiantamento1.length()!=0)
	    	{
	    		valorTotalCedenteSemAdiantamento1=Double.parseDouble(stringValorTotalCedenteSemAdiantamento1.replace("-R$ ", "-").replace("R$", "").replace(".","").replace(",", ".").trim()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	    	}
	    			    	
	    	WebElement weNumeroBancoContaCedenteSemAdiantamento1 = this.driver.findElement(By.id("numeroBancoContaCedenteSemAdiantamento1")); //$NON-NLS-1$
	    	numeroBancoContaCedenteSemAdiantamento1=weNumeroBancoContaCedenteSemAdiantamento1.getAttribute("value"); //$NON-NLS-1$
	    	WebElement weNumeroAgenciaContaCedenteSemAdiantamento1 = this.driver.findElement(By.id("numeroAgenciaContaCedenteSemAdiantamento1")); //$NON-NLS-1$
	    	numeroAgenciaContaCedenteSemAdiantamento1=weNumeroAgenciaContaCedenteSemAdiantamento1.getAttribute("value"); //$NON-NLS-1$

	    	WebElement weNumeroContaCorrenteCedenteSemAdiantamento1 = this.driver.findElement(By.id("numeroContaCorrenteContaCedenteSemAdiantamento1")); //$NON-NLS-1$
	    	numeroContaCorrenteContaCedenteSemAdiantamento1=weNumeroContaCorrenteCedenteSemAdiantamento1.getAttribute("value"); //$NON-NLS-1$
	    	
	    	if(valorTotalCedenteSemAdiantamento1!=0)
	    	{
		    	System.out.println("\nConta 1 Cedente: " + contaCedenteSemAdiantamento1); //$NON-NLS-1$
		    	System.out.println("Valor: " + valorTotalCedenteSemAdiantamento1); //$NON-NLS-1$
		    	System.out.println("Banco: " + numeroBancoContaCedenteSemAdiantamento1); //$NON-NLS-1$
		    	System.out.println("Agencia sem Digito: " + numeroAgenciaContaCedenteSemAdiantamento1); //$NON-NLS-1$
		    	System.out.println("Conta Corrente: " + numeroContaCorrenteContaCedenteSemAdiantamento1); //$NON-NLS-1$
	    	}
	    	
	    	WebElement comboContaCedenteSemAdiantamento2 = this.driver.findElement(By.id("comboCedenteSemAdiantamento2")); //$NON-NLS-1$
	    	contaCedenteSemAdiantamento2 = comboContaCedenteSemAdiantamento2.getAttribute("value"); //$NON-NLS-1$
	    	
	    	WebElement weValorTotalCedenteSemAdiantamento2 = this.driver.findElement(By.id("valorTotalCedenteSemAdiantamento2")); //$NON-NLS-1$
	    	String stringValorTotalCedenteSemAdiantamento2 = weValorTotalCedenteSemAdiantamento2.getAttribute("value"); //$NON-NLS-1$
	    	if(stringValorTotalCedenteSemAdiantamento2.length()>0)
	    	{
	    		valorTotalCedenteSemAdiantamento2=Double.parseDouble(stringValorTotalCedenteSemAdiantamento2.replace("-R$ ", "-").replace("R$", "").replace(".","").replace(",", ".").trim()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	    	}
	    			    	
	    	WebElement weNumeroBancoContaCedenteSemAdiantamento2 = this.driver.findElement(By.id("numeroBancoContaCedenteSemAdiantamento2")); //$NON-NLS-1$
	    	numeroBancoContaCedenteSemAdiantamento2=weNumeroBancoContaCedenteSemAdiantamento2.getAttribute("value"); //$NON-NLS-1$

	    	WebElement weNumeroAgenciaContaCedenteSemAdiantamento2 = this.driver.findElement(By.id("numeroAgenciaContaCedenteSemAdiantamento2")); //$NON-NLS-1$
	    	numeroAgenciaContaCedenteSemAdiantamento2=weNumeroAgenciaContaCedenteSemAdiantamento2.getAttribute("value"); //$NON-NLS-1$

	    	WebElement weNumeroContaCorrenteCedenteSemAdiantamento2 = this.driver.findElement(By.id("numeroContaCorrenteContaCedenteSemAdiantamento2")); //$NON-NLS-1$
	    	numeroContaCorrenteContaCedenteSemAdiantamento2=weNumeroContaCorrenteCedenteSemAdiantamento2.getAttribute("value"); //$NON-NLS-1$
	    	
	    	if(valorTotalCedenteSemAdiantamento2!=0)
	    	{
		    	System.out.println("\nConta 2 Cedente: " + contaCedenteSemAdiantamento2); //$NON-NLS-1$
		    	System.out.println("Valor: " + valorTotalCedenteSemAdiantamento2); //$NON-NLS-1$
		    	System.out.println("Banco: " + numeroBancoContaCedenteSemAdiantamento2); //$NON-NLS-1$
		    	System.out.println("Agencia sem Digito: " + numeroAgenciaContaCedenteSemAdiantamento2); //$NON-NLS-1$
		    	System.out.println("Conta Corrente: " + numeroContaCorrenteContaCedenteSemAdiantamento2); //$NON-NLS-1$
	    	}		    	
    	
	    	WebElement weCertificadora = this.driver.findElement(By.id("comboCertificadoras")); //$NON-NLS-1$
	    	certificadora = weCertificadora.getText().trim().replaceAll(" +", " "); //$NON-NLS-1$ //$NON-NLS-2$
	    	System.out.println("\nCertificadora: " + certificadora); //$NON-NLS-1$
	    	
	    	operacao.setDataDeImportacao(dataDeImportacao);
	    	operacao.setValorTotalAquisicaoComAdiantamento(valorTotalAquisicaoComAdiantamento);
	    	operacao.setValorRecompraComAdiantamento(valorRecompraComAdiantamento);
	    	operacao.setReembolsoContaEspecial(reembolsoContaEspecial);
	    	operacao.setValorTotalComAdiantamento(valorTotalComAdiantamento);
	    	operacao.setContaCorrenteInternaPaulista(contaCorrenteInternaPaulista);
	    	operacao.setValorTotalContaCorrenteInternaPaulista(valorTotalContaCorrenteInternaPaulista);
	    	
	    	Conta contaInterna = new Conta();
	    	if(!numeroBancoContaCorrenteInternaPaulista.trim().equals("")) //$NON-NLS-1$
	    	{
	    		System.out.println("Setting internal account"); //$NON-NLS-1$
	    		System.out.println("Banco: " + numeroBancoContaCorrenteInternaPaulista); //$NON-NLS-1$
	    		System.out.println("Agencia: " + numeroAgenciaContaCorrenteInternaPaulista); //$NON-NLS-1$
	    		System.out.println("Conta: " + numeroContaCorrenteInternaPaulista); //$NON-NLS-1$
	    		contaInterna = new Conta(operacao.getCedente(), numeroBancoContaCorrenteInternaPaulista, numeroAgenciaContaCorrenteInternaPaulista, numeroContaCorrenteInternaPaulista, conn);    		
	    		operacao.setContaInterna(contaInterna);
	    	}
	    	
	    	operacao.setValorRemessaSemAdiantamento(valorRemessaSemAdiantamento);
	    	operacao.setValorRecompraSemAdiantamento(valorRecompraSemAdiantamento);
	    	operacao.setReembolsoContaNormal(reembolsoContaNormal);
	    	operacao.setValorTotalSemAdiantamento(valorTotalSemAdiantamento);
	    	operacao.setContaCedenteSemAdiantamento1(contaCedenteSemAdiantamento1);
	    	operacao.setValorTotalCedenteSemAdiantamento1(valorTotalCedenteSemAdiantamento1);
	
	    	Conta contaExterna1 = new Conta();
	    	if(!numeroBancoContaCedenteSemAdiantamento1.trim().equals("")) //$NON-NLS-1$
	    	{
	    		System.out.println("Setting account 1"); //$NON-NLS-1$
	    		System.out.println("Banco: " + numeroBancoContaCedenteSemAdiantamento1); //$NON-NLS-1$
	    		System.out.println("Agencia: " + numeroAgenciaContaCedenteSemAdiantamento1); //$NON-NLS-1$
	    		System.out.println("Conta: " + numeroContaCorrenteContaCedenteSemAdiantamento1); //$NON-NLS-1$
	    		contaExterna1 = new Conta(operacao.getCedente(), numeroBancoContaCedenteSemAdiantamento1, numeroAgenciaContaCedenteSemAdiantamento1, numeroContaCorrenteContaCedenteSemAdiantamento1, conn);    		
	    		operacao.setContaExterna1(contaExterna1);
	    	}
	
	    	operacao.setContaCedenteSemAdiantamento2(contaCedenteSemAdiantamento2);
	    	operacao.setValorTotalCedenteSemAdiantamento2(valorTotalCedenteSemAdiantamento2);
	    	
	    	Conta contaExterna2 = new Conta();
	    	if(!numeroBancoContaCedenteSemAdiantamento2.trim().equals("")) //$NON-NLS-1$
	    	{   
	    		System.out.println("Setting account 2"); //$NON-NLS-1$
	    		System.out.println("Banco: " + numeroBancoContaCedenteSemAdiantamento2); //$NON-NLS-1$
	    		System.out.println("Agencia: " + numeroAgenciaContaCedenteSemAdiantamento2); //$NON-NLS-1$
	    		System.out.println("Conta: " + numeroContaCorrenteContaCedenteSemAdiantamento2); //$NON-NLS-1$
	    		contaExterna2 = new Conta(operacao.getCedente(), numeroBancoContaCedenteSemAdiantamento2, numeroAgenciaContaCedenteSemAdiantamento2, numeroContaCorrenteContaCedenteSemAdiantamento2, conn);    		    		
	    		operacao.setContaExterna2(contaExterna2);
	    	}
	    	
	    	operacao.setCertificadora(certificadora);
		}
		else
		{
			System.out.println("--N�o foi possivel ler o nome do fundo nem os outros detalhes--"); //$NON-NLS-1$
		}
    	this.setJustReadedTransferDetails(true);
	}

	public static Operacao readOperationBasic(List<WebElement> rowOperation)
	{
		String nomeFundo=""; //$NON-NLS-1$
		String nomeArquivo=""; //$NON-NLS-1$
		String nomeCedente=""; //$NON-NLS-1$
		double valorDeTransferencia=0.0;
		double valorTEDCedente=0.0;
		Date registerTime=Calendar.getInstance().getTime();
		ArrayList<Status> statuses=new ArrayList<Status>();
		double taxaDeCessao=0;
		Date dataDeEntrada=Calendar.getInstance().getTime();
		
		System.out.println("Read elements"); //$NON-NLS-1$
        WebElement weFundo = rowOperation.get(0); 
        nomeFundo = weFundo.getAttribute("title"); //$NON-NLS-1$
        System.out.println("Read nomeFundo " + nomeFundo); //$NON-NLS-1$
        WebElement weNomeArquivo = rowOperation.get(1);
        nomeArquivo = weNomeArquivo.getText();
        System.out.println("Read nomeArquivo " + nomeArquivo); //$NON-NLS-1$
        WebElement weCedente = rowOperation.get(2);
        nomeCedente = weCedente.getText().replace("'", ""); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("Read nomeCedente " + nomeCedente); //$NON-NLS-1$
        WebElement weValorDeTransferencia = rowOperation.get(3);
//        System.out.println("ValorDeTransferencia: " + weValorDeTransferencia.getText());
        valorDeTransferencia = Double.parseDouble(weValorDeTransferencia.getText().replace("R$", "").replace(".","").replace(",", ".").trim()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
        System.out.println("Read valorDeTransferencia " + valorDeTransferencia); //$NON-NLS-1$

        WebElement weValorTEDCedente = rowOperation.get(4);
        valorTEDCedente = Double.parseDouble(weValorTEDCedente.getText().replace("R$", "").replace(".","").replace(",", ".").trim()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
        System.out.println("Read valorTEDCedente " + valorTEDCedente); //$NON-NLS-1$

        WebElement weSituacao = rowOperation.get(5);
        System.out.println("Readed situacao " + weSituacao.getText()); //$NON-NLS-1$
        
        String cadastroFundo = nomeArquivo.split("_")[0]; //$NON-NLS-1$
        
        Operacao currentOperation = new Operacao();        
    	currentOperation.setFundo(new FundoDeInvestimento(nomeFundo, cadastroFundo, OperadorPortalPaulistaChrome.conn)); //Missing Fundo Constructor
    	System.out.println("Set fundo"); //$NON-NLS-1$
    	currentOperation.setNomeArquivo(nomeArquivo);
    	System.out.println("Set nomeArquivo"); //$NON-NLS-1$
    	//System.out.println("BeforeCedente");
    	currentOperation.setCedente(new Entidade(nomeCedente, OperadorPortalPaulistaChrome.conn));
    	System.out.println("Set cedente"); //$NON-NLS-1$
    	//System.out.println("AfterCedente");
    	currentOperation.setValorDeTransferencia(valorDeTransferencia);
    	System.out.println("Set valorDeTransferencia"); //$NON-NLS-1$
    	currentOperation.setValorTEDCedente(valorTEDCedente);
    	System.out.println("Set valorTED"); //$NON-NLS-1$
    	currentOperation.setRegisterTime(registerTime);
    	System.out.println("Set registerTime"); //$NON-NLS-1$
    	currentOperation.setStatuses(statuses);
    	System.out.println("Set statuses"); //$NON-NLS-1$
    	currentOperation.setTaxaDeCessao(taxaDeCessao);
    	System.out.println("Set taxaDeCessao"); //$NON-NLS-1$
    	currentOperation.setDataDeEntrada(dataDeEntrada);
    	System.out.println("Set dataDeEntrada"); //$NON-NLS-1$
    	
    	System.out.println("\nBASIC READING"); //$NON-NLS-1$
    	System.out.println("Fundo: " + currentOperation.getFundo().getNomeCurto() + " idFundo: " + currentOperation.getFundo().getIdFundo()); //$NON-NLS-1$ //$NON-NLS-2$
    	System.out.println("Cedente: " + currentOperation.getCedente().getNome()); //$NON-NLS-1$
    	System.out.println("Arquivo: " + currentOperation.getNomeArquivo()); //$NON-NLS-1$
    	
        statuses.add(new Status(weSituacao.getText(),Calendar.getInstance().getTime()));
    	return currentOperation; 
	}	
	

	public void checkStatus() 
    {
    	Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	
    	System.out.println("--------------------"); //$NON-NLS-1$
    	System.out.println("Check status at\t" + sdf.format(cal.getTime()) ); //$NON-NLS-1$
    	System.out.println("--------------------"); //$NON-NLS-1$
    	
    	

        this.driver.navigate().to("http://portalfidc.bancopaulista.com.br/portal/financeiro/liquidacao"); //$NON-NLS-1$
        
        HandlerOperacoes.timeAndWait(5);
        WebElement xField = this.driver.findElement(By.className("search-choice-close")); //$NON-NLS-1$
    	xField.click();

    	HandlerOperacoes.timeAndWait(3);
        WebElement buttonPesquisar = this.driver.findElement(By.id("pesquisa")); //$NON-NLS-1$
    	buttonPesquisar.click();
    	
    	File file=null;
    	String fileName = 	"reportOperations.txt"; //$NON-NLS-1$
		file = new File("conf/"+fileName); //$NON-NLS-1$
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(this.operations.size()==0)
		{
			ArrayList<String> lines = new ArrayList<String>();
			try {
				lines = (ArrayList<String>) Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			for(String line:lines)
			{
				System.out.println(line);
			}
			for(String line:lines)
			{
				//System.out.println(line);
				String[] field = line.split("\t"); //$NON-NLS-1$
//				int iOp=Integer.parseInt(field[0]);
				String fundName = field[1];
				String fileOpName = field[2];
				String nomeCedente = field[3];
				Double tedCedente = Double.parseDouble(field[4].replaceAll(" ", "").replace("R$", "").replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
				String situacao = field[5];
				cal.getTime();
				cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(field[6].substring(0,2)));
				cal.set(Calendar.MINUTE, Integer.parseInt(field[6].substring(3,5)));
				cal.set(Calendar.SECOND, Integer.parseInt(field[6].substring(6,8)));
				Date registerDate =  cal.getTime();
				cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(field[7].substring(0,2)));
				cal.set(Calendar.MINUTE, Integer.parseInt(field[7].substring(3,5)));
				cal.set(Calendar.SECOND, Integer.parseInt(field[7].substring(6,8)));
				Date statusDate = cal.getTime();
				cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(field[8].substring(0,2)));
				cal.set(Calendar.MINUTE, Integer.parseInt(field[8].substring(3,5)));
				cal.set(Calendar.SECOND, Integer.parseInt(field[8].substring(6,8)));
				Date checkDate = cal.getTime();
				System.out.println(field[9]);
				Long lifeTime = (long) 0;
				if(!field[9].equals("0")) //$NON-NLS-1$
				{
					lifeTime = (long) ( 
										Integer.parseInt(field[9].substring(0,2))*60*60+
										Integer.parseInt(field[9].substring(3,5))*60+
										Integer.parseInt(field[9].substring(6,8))
										)*1000;
				}
				Operacao op = new Operacao();
				op.setFundo(new FundoDeInvestimento(fundName, OperadorPortalPaulistaChrome.conn));
				op.setNomeArquivo(fileOpName);
				op.setCedente(new Entidade(nomeCedente,conn));				
				op.setValorTEDCedente(tedCedente);
				op.setRegisterTime(registerDate);
				op.getStatuses().add(new Status());
				op.getStatuses().get(0).setDescription(situacao);
				op.getStatuses().get(0).setBeginDate(statusDate);
				op.getStatuses().get(0).setEndDate(checkDate);
				op.getStatuses().get(0).setLifeTime(lifeTime);
				
				System.out.println("Adding:\n" + line); //$NON-NLS-1$
				this.operations.add(op);
			}
		}
		HandlerOperacoes.timeAndWait(5);
        List<WebElement> listRows = this.driver.findElements(By.className("rowLiquidacao")); //$NON-NLS-1$
        ArrayList<Operacao> onlineOperations = new ArrayList<Operacao>();

        if(listRows.size()>0)
        {
	        int fieldCounter=0;
	        Operacao tempOp=new Operacao();
	    	for(WebElement row:listRows)
	    	{   
	    		if(fieldCounter==0)
	    		{
	    			tempOp=new Operacao();
//		    			System.out.println("--------------------");
//		    			System.out.println("Opera��o " + iOp);	    			
	    			String nomeFundo = row.getAttribute("title") //$NON-NLS-1$
	    								.replace("...", "") //$NON-NLS-1$ //$NON-NLS-2$
	    								.replace(" - ", ""); //$NON-NLS-1$ //$NON-NLS-2$
//		    			System.out.println("Fundo: " + fundo);
	    			tempOp.setFundo(new FundoDeInvestimento(nomeFundo,OperadorPortalPaulistaChrome.conn));
	    			fieldCounter++;
	    		}
	    		else if(fieldCounter==1)
	    		{
	    			String fileOperationName = row.getText();
//		    			System.out.println("Nome do arquivo: " + fileName);
	    			tempOp.setNomeArquivo(fileOperationName);
	    			fieldCounter++;
	    		}
	    		else if(fieldCounter==2)
	    		{
	    			String nomeCedente = row.getText();
//		    			System.out.println("Cedente: " + cedente);
	    			tempOp.setCedente(new Entidade(nomeCedente,conn));
	    			fieldCounter++;
	    		}
	    		else if(fieldCounter==3)
	    		{
	    			String valorTEDOriginador = row.getText()
	    										.replace("R$ ", "") //$NON-NLS-1$ //$NON-NLS-2$
	    										.replace(".", "") //$NON-NLS-1$ //$NON-NLS-2$
	    										.replace(",", "."); //$NON-NLS-1$ //$NON-NLS-2$
//		    			System.out.println("Valor TED Originador: R$ " + df.format(Double.parseDouble(valorTEDOriginador)));
	    			tempOp.setValorTEDOriginador(Double.parseDouble(valorTEDOriginador));
	    			fieldCounter++;
	    		}
	    		else if(fieldCounter==4)
	    		{
	    			String valorTEDCedente = row.getText()
	    										.replace("R$ ", "") //$NON-NLS-1$ //$NON-NLS-2$
	    										.replace(".", "") //$NON-NLS-1$ //$NON-NLS-2$
	    										.replace(",", "."); //$NON-NLS-1$ //$NON-NLS-2$
//		    			System.out.println("Valor TED Cedente: R$ " + df.format(Double.parseDouble(valorTEDCedente)));
	    			tempOp.setValorTEDCedente(Double.parseDouble(valorTEDCedente));
	    			fieldCounter++;
	    		}
	    		else if(fieldCounter==5)
	    		{
	    			String situacaoDescription = row.getText();
//		    			System.out.println("Situa��o: " + situacao);
	    			tempOp.getStatuses().add(new Status());
	    			tempOp.getStatuses().get(0).setDescription(situacaoDescription);
	    			    			
	    			fieldCounter=0;
	    			
		    		boolean isRegistered=false;
		    		
		    		if(this.operations.size()>0)
		    		{
			    		for(Operacao op:this.operations)
			    		{
//				    			System.out.println("Fundo: " + op.getFundo() + " FundoTemp: " + tempOp.getFundo());
			    			if(op.getFundo().equals(tempOp.getFundo()) && op.getNomeArquivo().equals(tempOp.getNomeArquivo()))
			    			{
//			    				System.out.println("Already registered!");
			    				isRegistered=true;
			    				cal = Calendar.getInstance();
			    				Date lastCheckTime = cal.getTime();
			    				
		    					op.getStatuses().get(op.getStatuses().size()-1).setEndDate(lastCheckTime);
			    				if(!op.getStatuses().get(op.getStatuses().size()-1).getDescription().equals(tempOp.getStatuses().get(0).getDescription()))
	    						{
			    					cal = Calendar.getInstance();
			    					Date lastStatusTime = cal.getTime();
			    					tempOp.getStatuses().get(0).setBeginDate(lastStatusTime);				    									    					
			    					Status statusNew = tempOp.getStatuses().get(0);
			    					op.getStatuses().add(statusNew);
	    						}
			    				break;
			    			}
			    		}
		    		}
		    		
		    		if(!isRegistered)
	    			{
		    			cal = Calendar.getInstance();
		    			Date registerTime = cal.getTime();
		    			tempOp.setRegisterTime(registerTime);
		    			tempOp.getStatuses().get(0).setBeginDate(registerTime);
		    			tempOp.show();
		    			this.operations.add(tempOp);
	    			}
		    		onlineOperations.add(tempOp);
	    		}	
	    		else
	    		{
//		    			System.out.println("rowText " + row.getText());
	    		}	    		
	    	}
	        cal = Calendar.getInstance();
	        this.currentTime = cal.getTime();
        }
		
        for(Operacao op:this.operations)
        {
        	boolean isErased=true;
        	for(Operacao oop:onlineOperations)
        	{
        		if(op.getNomeArquivo().equals(oop.getNomeArquivo()))
        		{
        			isErased=false;
        		}
        	}
        	if(isErased && !op.getStatuses().get(op.getStatuses().size()-1).getDescription().equals("Cancelado"))        		 //$NON-NLS-1$
        	{
        		Status statusCancelado = new Status("Cancelado",Calendar.getInstance().getTime(), conn); //$NON-NLS-1$
        		op.getStatuses().add(statusCancelado);
        	}
        	else if(isErased && op.getStatuses().get(op.getStatuses().size()-1).getDescription().equals("Cancelado")) //$NON-NLS-1$
        	{
        		op.getStatuses().get(op.getStatuses().size()-1).setEndDate(Calendar.getInstance().getTime());
        	}
        }

    	System.out.println("--------------------"); //$NON-NLS-1$
		System.out.println("Hora Limite: " + sdf.format(this.limitTime) + " " + this.limitTime); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("Hora Atual: " + sdf.format(this.currentTime) + " " + this.currentTime); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("--------------------"); //$NON-NLS-1$
    	String content=""; //$NON-NLS-1$
        if(this.operations.size()>0)	        	
        {     	
        	System.out.println("Registered " + this.operations.size() + " operations"); //$NON-NLS-1$ //$NON-NLS-2$
        	int iOp=0;
        	System.out.println("Nro"+ " "  //$NON-NLS-1$ //$NON-NLS-2$
					+ "\tFundo       "  //$NON-NLS-1$
					+ "\t NomeDeArquivo      "  //$NON-NLS-1$
					+ "\t Cedente     " //$NON-NLS-1$
//						+ "\tR$ " + df.format(op.getValorTEDOriginador())
					+ "\tValorTEDCed" //$NON-NLS-1$
					+ "\tSituacao                            " //$NON-NLS-1$
					+ "\tRegistro    " //$NON-NLS-1$
					+ "\tStatus      " //$NON-NLS-1$
					+ "\tVerifica��o " //$NON-NLS-1$
					);
        	double totalValorCedente=0;
        	for(Operacao op:this.operations)
        	{
        		iOp++;
        		String stringValorTEDCedente = ""; //$NON-NLS-1$
        		if(op.getValorTEDCedente()==0.0)
        		{
        			stringValorTEDCedente = df.format(op.getValorTEDCedente()) + "    "; //$NON-NLS-1$
        		}
        		else
        		{
        			stringValorTEDCedente = df.format(op.getValorTEDCedente());
        		}
        		String stringSituacao = ""; //$NON-NLS-1$
        		Status situacao = op.getStatuses().get(op.getStatuses().size()-1);
        		
        		if(situacao.getDescription().toLowerCase().contains("reprovado") //$NON-NLS-1$
        				|| situacao.getDescription().toLowerCase().contains("digital") //$NON-NLS-1$
        				)
        		{
        			stringSituacao = situacao.getDescription() + "          "; //$NON-NLS-1$
        		}
        		else
        		{
        			stringSituacao = situacao.getDescription();
        		}
        		
        		System.out.println(iOp
        						+ "\t" + op.getFundo().getNome().substring(0,12)  //$NON-NLS-1$
        						+ "\t" + op.getNomeArquivo() //$NON-NLS-1$
        						+ "\t" + op.getCedente().getNome().substring(0,15) //$NON-NLS-1$
//	        						+ "\tR$ " + df.format(op.getValorTEDOriginador())
        						+ "\tR$ " + stringValorTEDCedente //$NON-NLS-1$
        						+ "\t" + stringSituacao //$NON-NLS-1$
        						+ "\t" + sdf.format(op.getRegisterTime()) //$NON-NLS-1$
        						+ "\t" + sdf.format(situacao.getBeginDate()) //$NON-NLS-1$
        						+ "\t" + sdf.format(situacao.getEndDate()) //$NON-NLS-1$
        						+ "\t" + op.getStatuses().get(op.getStatuses().size()-1).getLifeTimeString() //$NON-NLS-1$
        						);
        		content = content + iOp
						+ "\t" + op.getFundo().getNome()  //$NON-NLS-1$
						+ "\t" + op.getNomeArquivo() //$NON-NLS-1$
						+ "\t" + op.getCedente() //$NON-NLS-1$
//    						+ "\tR$ " + df.format(op.getValorTEDOriginador())
						+ "\tR$ " + stringValorTEDCedente //$NON-NLS-1$
						+ "\t" + stringSituacao //$NON-NLS-1$
						+ "\t" + sdf.format(op.getRegisterTime()) //$NON-NLS-1$
						+ "\t" + sdf.format(situacao.getBeginDate()) //$NON-NLS-1$
						+ "\t" + sdf.format(situacao.getEndDate()) //$NON-NLS-1$
						+ "\t" + op.getStatuses().get(op.getStatuses().size()-1).getLifeTimeString() //$NON-NLS-1$
						+ "\n"; //$NON-NLS-1$
        		
        		totalValorCedente=totalValorCedente+op.getValorTEDCedente();
        	}
        	System.out.println("   "+ " "  //$NON-NLS-1$ //$NON-NLS-2$
					+ "\t            "  //$NON-NLS-1$
					+ "\t                    "  //$NON-NLS-1$
					+ "\tTOTAL       " //$NON-NLS-1$
//						+ "\tR$ " + df.format(op.getValorTEDOriginador())
					+ "\tR$ " + df.format(totalValorCedente) //$NON-NLS-1$
					);        	
        }
        else
        {
        	System.out.println("No Registered operations"); //$NON-NLS-1$
        }
    	
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file, "ISO_8859_1"); //$NON-NLS-1$
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		pw.write(content);
		pw.flush();
		pw.close();        
    }
    
    public static void saveStatus(Operacao op)
    {       	
    	System.out.println("-------------------------"); //$NON-NLS-1$
    	System.out.println("Saving Status"); //$NON-NLS-1$
    	System.out.println("-------------------------"); //$NON-NLS-1$
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulistaChrome.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for(Status status:op.getStatuses())
		{
			int idDescricaoStatus = 0;
			String query = "SELECT idDescricaoStatus FROM descricaostatus WHERE descricao=\"" + status.getDescription() + "\""; //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println(query);
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				while (rs.next())
				{
					idDescricaoStatus = rs.getInt("idDescricaoStatus");				 //$NON-NLS-1$
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(idDescricaoStatus==0)
			{
				String sql = "INSERT INTO `mvcapital`.`descricaostatus` (`descricao`) VALUES(" //$NON-NLS-1$
						+ "\"" + status.getDescription() + "\""  //$NON-NLS-1$ //$NON-NLS-2$
						+ ")"; //$NON-NLS-1$
		//		System.out.println(sql);
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		
				query = "SELECT idDescricaoStatus FROM descricaostatus WHERE descricao= \"" + status.getDescription() + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		//		System.out.println(query);
				rs=null;
				try {
					rs = stmt.executeQuery(query);
					while (rs.next())
					{				
						idDescricaoStatus = rs.getInt("idDescricaoStatus"); //$NON-NLS-1$
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}						
			}

			query = "SELECT idStatusOperacao FROM statusoperacao WHERE idOperacao= " + op.getIdOperacao() //$NON-NLS-1$
					+ " AND idDescricaoStatus = " + idDescricaoStatus;			 //$NON-NLS-1$
			System.out.println(query);
			int idStatusOperacao=0;
			rs=null;
			try {
				rs = stmt.executeQuery(query);
				while (rs.next())
				{				
					idStatusOperacao = rs.getInt("idStatusOperacao"); //$NON-NLS-1$
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}						
			
			if(idStatusOperacao!=0)
			{
				System.out.println("Status already registered!"); //$NON-NLS-1$
				String sql = "UPDATE `mvcapital`.`statusoperacao` SET `fim` = " + "'" + sdtfd.format(status.getEndDate())  + "'" + " WHERE `idStatusOperacao` = " + idStatusOperacao; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				System.out.println(sql);
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("New status for this operation!"); //$NON-NLS-1$
				String sql = "INSERT INTO `mvcapital`.`statusoperacao` (`idOperacao`,`inicio`,`fim`,`idDescricaoStatus`) VALUES (" //$NON-NLS-1$
		    				+ op.getIdOperacao()
		    				+ ",'" + sdtfd.format(status.getBeginDate()) + "'" //$NON-NLS-1$ //$NON-NLS-2$
		    				+ ",'" + sdtfd.format(status.getEndDate()) + "'" //$NON-NLS-1$ //$NON-NLS-2$
		    				+ "," + idDescricaoStatus //$NON-NLS-1$
		    				+ ")"; //$NON-NLS-1$
				System.out.println(sql);
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
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
    
    public static Relatorio buildBlocks(ArrayList<String> lines)
    {
    	BlockIdentificacao blockIdentificacao = new BlockIdentificacao();
    	BlockDireitosCreditorios blockTitulos = new BlockDireitosCreditorios();
    	System.out.println("------------------------------------"); //$NON-NLS-1$
    	System.out.println("Building blocks"); //$NON-NLS-1$
    	System.out.println("------------------------------------"); //$NON-NLS-1$
    	for(String line:lines)
    	{
    		System.out.println(line);
    	}
    	for(String line:lines)
    	{
    		//System.out.println(line);
    		String[] fields = line.split("\t"); //$NON-NLS-1$
    		if(line.startsWith("fundo")) //$NON-NLS-1$
    		{
    			blockIdentificacao.setFundo(fields[1]);
    		}
    		else if(line.startsWith("cedente")) //$NON-NLS-1$
    		{
    			blockIdentificacao.setCedente(fields[1]);
    		}
    		else if(line.startsWith("taxaDeCessao")) //$NON-NLS-1$
    		{
    			String stringTaxaDeCessao = fields[1].replace("%", "").replace(".", "").replace(",", ".").trim(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    			blockIdentificacao.setTaxaDeCessao(Double.parseDouble(stringTaxaDeCessao));
    		}
    		else if(line.startsWith("arquivo")) //$NON-NLS-1$
    		{
    			blockIdentificacao.setNomeArquivo(fields[1]);
    		}
    		else if(line.startsWith("dataDeEntrada")) //$NON-NLS-1$
    		{
    			try {
					blockIdentificacao.setDataDeEntrada(sdfr.parse(fields[1]));
				} catch (ParseException e) {
					e.printStackTrace();
				}
    		}
    		else if(line.startsWith("totalAquisicao")) //$NON-NLS-1$
    		{
    			String stringTotalAquisicao = fields[1].replace("R$", "").replace("%", "").replace(".", "").replace(",", ".").trim(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
    			blockIdentificacao.setTotalAquisicao(Double.parseDouble(stringTotalAquisicao));
    		}
    		else if(line.startsWith("totalNominal")) //$NON-NLS-1$
    		{
    			String stringTotalNominal = fields[1].replace("R$", "").replace("%", "").replace(".", "").replace(",", ".").trim(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
    			blockIdentificacao.setTotalNominal(Double.parseDouble(stringTotalNominal));
    		}
    		else if(line.startsWith("titulo")) //$NON-NLS-1$
    		{
    			String[] fieldsTitulo = line.split("\t"); //$NON-NLS-1$
    			String tipoDeRecebivel = fieldsTitulo[1];    			
    			String seuNumero = fieldsTitulo[2];
    			String numeroDoDocumento = fieldsTitulo[3];
    			double valorDeAquisicao = Double.parseDouble(fieldsTitulo[4].replace(".","").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    			double valorNominal = Double.parseDouble(fieldsTitulo[5].replace(".","").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    			int prazo = Integer.parseInt(fieldsTitulo[6]);
//    			double taxaDeJuros = Double.parseDouble(fieldsTitulo[7].replace(".", "").replace(",","."));    			    		
    			String nomeSacado = fieldsTitulo[8];
    			String nomeCedente = blockIdentificacao.getCedente();
    			String nomeFundo = blockIdentificacao.getFundo();
    			
    			DireitoCreditorio titulo = new DireitoCreditorio(conn,
																tipoDeRecebivel,
																nomeSacado, 
																nomeCedente, 
																nomeFundo, 
																seuNumero,
																numeroDoDocumento, 
																blockIdentificacao.getDataDeEntrada(), 
																prazo, 
																valorDeAquisicao,
																valorNominal);
    			System.out.println("Title created"); //$NON-NLS-1$
//    			titulo.show();
    			
    			blockTitulos.getDireitosCreditorios().add(titulo);
    			boolean existTipoDeDireitoCreditorio=false;
    			if(blockTitulos.getTiposDeRecebivel().size()>0)
    			{
	    			for(TipoDeRecebivel tipoDeDireitoCreditorio:blockTitulos.getTiposDeRecebivel())
	    			{
	    				if(tipoDeDireitoCreditorio.getDescricao().equals(tipoDeRecebivel))
	    				{
	    					existTipoDeDireitoCreditorio=true;
	    					break;
	    				}
	    			}
    			}
    			if(!existTipoDeDireitoCreditorio)
    			{
    				blockTitulos.getTiposDeRecebivel().add(new TipoDeRecebivel(tipoDeRecebivel, conn));
    			}
    		}
    	}
    	blockIdentificacao.show();
    	blockTitulos.show();
    	System.out.println("------------------------------------"); //$NON-NLS-1$
    	Relatorio report = new Relatorio(blockIdentificacao,blockTitulos);
    	return report;
    }
    
    public static ArrayList<String> readTxtFile(String fileTxtName)
	{
		ArrayList<String> lines=new ArrayList<String>();
		ArrayList<String> linesFile = new ArrayList<String>();
//		System.out.println("------------------");
//		System.out.println("Reading  file " + fileTxtName);
//		System.out.println("------------------");
		File file = new File(fileTxtName);
		if(file.exists())
		{
			try {
				linesFile = (ArrayList<String>) Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
				System.out.println("Reading file " + file.toPath()); //$NON-NLS-1$
				
				for(String line:linesFile)
				{
					System.out.println(line);
				}
				
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		else
		{
			System.out.println(file.getName() + " does not exist!"); //$NON-NLS-1$
			return null;
		}
		
		boolean existBeginBlock=false;
		String tipoDeRecebivel=""; //$NON-NLS-1$

		String lineBefore = ""; //$NON-NLS-1$
		String lineAfter = ""; //$NON-NLS-1$
		int iLines=0;
		for(iLines=0;iLines<linesFile.size();iLines++) 
		{	
			String lineFile = linesFile.get(iLines).replace(";", "").replace(" &amp", "");  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			System.out.println(lineFile);
			
			if(iLines>0)
			{
				lineBefore = linesFile.get(iLines-1).replace(";", "").replace(" &amp", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
			if(iLines<linesFile.size()-2)
			{
				lineAfter = linesFile.get(iLines+1).replace(";", "").replace(" &amp", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}			
			
			if(lineFile.length()>3)
			{				
//				lines.add(lineFile);
				
				String line = lineFile;
				
				if(!existBeginBlock)
				{
//					System.out.println(line);
					if(line.contains("Fundo:")) //$NON-NLS-1$
					{
						String[] fields = line.split(":"); //$NON-NLS-1$
						String fundo = fields[1].trim();
//						System.out.println("Fundo: " + fundo);
						lines.add("fundo\t" + fundo); //$NON-NLS-1$
					}
					else if(line.contains("Cedente:")) //$NON-NLS-1$
					{
						String[] fields = line.split(":"); //$NON-NLS-1$
						String cedente = fields[1].trim().split("Taxa")[0].trim(); //$NON-NLS-1$
						String taxaDeCessao = fields[2].trim();
//						System.out.println("Cedente: " + cedente);			
//						System.out.println("TaxaDeCessao: " + taxaDeCessao);
						lines.add("cedente\t" + cedente); //$NON-NLS-1$
						lines.add("taxaDeCessao\t" + taxaDeCessao); //$NON-NLS-1$
					}
					else if(line.contains("Arquivo:")) //$NON-NLS-1$
					{
						String[] fields = line.split(":"); //$NON-NLS-1$
						String arquivo = fields[1].trim().split("Data")[0].trim(); //$NON-NLS-1$
						String dataDeEntrada = fields[2].trim();
//						System.out.println("Arquivo: " + arquivo);
//						System.out.println("DataDeEntrada: " + dataDeEntrada);
						lines.add("arquivo\t" + arquivo); //$NON-NLS-1$
						lines.add("dataDeEntrada\t" + dataDeEntrada); //$NON-NLS-1$
					}
					else if(line.contains("Total Nominal:")) //$NON-NLS-1$
					{
						String[] fields = line.split(":"); //$NON-NLS-1$
						String totalAquisicao = fields[1].trim().split("Total")[0].trim(); //$NON-NLS-1$
						String totalNominal = fields[2].trim();
//						System.out.println("TotalAquisi��o: " + totalAquisicao);
//						System.out.println("TotalNominal: " + totalNominal);
						lines.add("totalAquisicao\t" + totalAquisicao); //$NON-NLS-1$
						lines.add("totalNominal\t" + totalNominal); //$NON-NLS-1$
					}
					if(line.contains("Tipo de ")) //$NON-NLS-1$
					{
						String[] fields = line.split(":"); //$NON-NLS-1$
						tipoDeRecebivel = fields[1].trim();
						System.out.println(tipoDeRecebivel);
						existBeginBlock=true;
						lines.add("tipoDeRecebivel\t" +tipoDeRecebivel); //$NON-NLS-1$
					}
				}
				else if(existBeginBlock)
				{
					
					System.out.println(line);				
					if(line.contains("Tipo de ")) //$NON-NLS-1$
					{
						String[] fields = line.split(":"); //$NON-NLS-1$
						if(!tipoDeRecebivel.equals(fields[1].trim()))
						{
							tipoDeRecebivel = fields[1].trim();
							System.out.println(tipoDeRecebivel);
							existBeginBlock=true;
							lines.add("tipoDeRecebivel\t" + tipoDeRecebivel); //$NON-NLS-1$
						}					
					}
					else
					{
						if(!line.contains("Prazo")&&!line.contains("Cedente")&&!line.contains("Fundo:")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						{
							String lineComplete=line;							
							if(!line.contains("%"))								 //$NON-NLS-1$
							{
								System.out.println("Inside - No have %"); //$NON-NLS-1$
								String afterPrazo = ""; //$NON-NLS-1$
								if(		   lineBefore.trim().length()>0 
										&& lineBefore.trim().length() < 104 
										&& lineAfter.trim().length() >0 
										&& lineAfter.trim().length() < 140 
										&& lineAfter.trim().contains("%") //$NON-NLS-1$
									)
								{									
									System.out.println("\tInside - All true"); //$NON-NLS-1$
									String[] fieldsBefore=lineBefore.trim().split(" "); //$NON-NLS-1$
									String[] fieldsAfter=lineAfter.trim().split("%"); //$NON-NLS-1$
									
									if(fieldsBefore[0].contains(".") && fieldsBefore[0].matches(".*\\d.*")) //$NON-NLS-1$ //$NON-NLS-2$
									{
										System.out.println("Contains number"); //$NON-NLS-1$
										String stringJuros=""; //$NON-NLS-1$
										System.out.println("field0Before: " + fieldsBefore[0]); //$NON-NLS-1$
										if(fieldsAfter.length>0)
										{
											System.out.println("field0After: " + fieldsAfter[0]); //$NON-NLS-1$
											if(fieldsAfter[0].length()!=0)
											{																				
												stringJuros = (fieldsBefore[0]+fieldsAfter[0]).trim()+" %"; //$NON-NLS-1$
											}							
											else
											{
												stringJuros = fieldsBefore[0]+" %"; //$NON-NLS-1$
											}
										}
										else
										{
											System.out.println("Only one line for juros"); //$NON-NLS-1$
											stringJuros = fieldsBefore[0].trim()+" %"; //$NON-NLS-1$
										}
										
										System.out.println("Passed juros: " + stringJuros); //$NON-NLS-1$

										String stringSacado=""; //$NON-NLS-1$
										
										if(fieldsAfter.length>1)
										{
											System.out.println("LineAfter: " + lineAfter); //$NON-NLS-1$
											if(fieldsAfter[1].length()>0)
											{
												stringSacado = lineBefore.trim().substring(fieldsBefore[0].length()).trim()
																  + lineAfter.trim().substring(fieldsAfter[0].length()).trim().replace("%", ""); //$NON-NLS-1$ //$NON-NLS-2$
											}											
										}
										else
										{											
											stringSacado = lineBefore.trim().substring(fieldsBefore[0].length()).trim();
										}
										System.out.println("Passed sacado: " + stringSacado); //$NON-NLS-1$
										afterPrazo = stringJuros + " " + stringSacado; //$NON-NLS-1$
										lineComplete = line + " " + afterPrazo; //$NON-NLS-1$
										System.out.println("AT: " + lineComplete);										 //$NON-NLS-1$
									}
									else
									{
										System.out.println("\t\tHave no number"); //$NON-NLS-1$
										continue;
										
									}
								}
								else
								{
									System.out.println("\tInside - Else - skip"); //$NON-NLS-1$
									continue;
								}
							}
							else if(line.contains("%") && line.trim().length() > 100)  //$NON-NLS-1$
							{
								System.out.println("Inside - Have % and lenght > 100"); //$NON-NLS-1$
								if(line.charAt(line.length()-1)=='%')
								{
									lineComplete = line + "     " + lineBefore.trim() + " " + lineAfter.trim(); //$NON-NLS-1$ //$NON-NLS-2$
								}
								System.out.println("LC%: "+ lineComplete); //$NON-NLS-1$
							}
							else 
							{
								System.out.println("Inside - Have % and lenght <= 100  - skip"); //$NON-NLS-1$
								continue;
							}
							
							
							
							if(lineComplete.trim().length() > 100)
							{
								System.out.println("LC: " + lineComplete);	 //$NON-NLS-1$
								String seuNumeroAndNumeroDoDocumento = lineComplete.split("R\\$ ")[0].trim(); //$NON-NLS-1$
	//								System.out.println("Mixed:" + seuNumeroAndNumeroDoDocumento);
								String seuNumero = ""; //$NON-NLS-1$
								String numeroDoDocumento = ""; //$NON-NLS-1$
								String[] fieldsNumero = seuNumeroAndNumeroDoDocumento.split(" "); //$NON-NLS-1$
								for(int i=0; i<fieldsNumero.length;i++)
								{
									if(i==fieldsNumero.length-1)
									{
										if(numeroDoDocumento.length()!=0)
										{
											numeroDoDocumento=numeroDoDocumento+" " + fieldsNumero[i]; //$NON-NLS-1$
										}
										else
										{
											numeroDoDocumento=fieldsNumero[i];
										}
									}
									else
									{
										if(i==fieldsNumero.length-2)
										{
											if((seuNumero+fieldsNumero[i]).length()>25)
											{
												numeroDoDocumento=fieldsNumero[i];
											}
											else
											{
												seuNumero=seuNumero+fieldsNumero[i];
											}
										}
										else
										{
											seuNumero=seuNumero+fieldsNumero[i] + " "; //$NON-NLS-1$
										}
									}
								}
								seuNumero=seuNumero.trim();
//									System.out.println("SeuNumero: " + seuNumero);
//									System.out.println("NumeroDocumento: " + numeroDoDocumento);
								String valorAquisicao = lineComplete.split("R\\$ ")[1].trim(); //$NON-NLS-1$
								String valorNominal = lineComplete.split("R\\$ ")[2].trim().split(" ")[0]; //$NON-NLS-1$ //$NON-NLS-2$
								String prazo = lineComplete.split("R\\$ ")[2].trim().replaceAll(" +", " ").split(" ")[1]; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
								String taxaDeJuros = lineComplete.split("R\\$ ")[2].trim().replaceAll(" +", " ").split(" ")[2]; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
								String sacado = lineComplete.split("%")[1].trim(); //$NON-NLS-1$
								String chaveNFE = lineComplete.substring(lineComplete.length()-1-44, lineComplete.length());
	//								System.out.println(seuNumero +"\t"+numeroDoDocumento+"\t"+valorAquisicao+"\t"+valorNominal+"\t"+prazo+"\t"+taxaDeJuros+"\t"+sacado);
								
								String lineClean = "titulo" //$NON-NLS-1$
													+ "\t" + tipoDeRecebivel //$NON-NLS-1$
													+ "\t" + (seuNumero  //$NON-NLS-1$
													+ "\t" + numeroDoDocumento //$NON-NLS-1$
													+ "\t" + valorAquisicao  //$NON-NLS-1$
													+ "\t" + valorNominal //$NON-NLS-1$
													+ "\t" + prazo //$NON-NLS-1$
													+ "\t" + taxaDeJuros //$NON-NLS-1$
													+ "\t" + sacado //$NON-NLS-1$
													+ "\t" + chaveNFE //$NON-NLS-1$
															).toUpperCase(); 
//								System.out.println(lineClean);
								lines.add(lineClean);
							}
						}
					}
				}
			}
		}
		return lines;
	}

    public void readConf()
	{
		BufferedReader reader = null;

		System.out.println("------------------"); //$NON-NLS-1$
		try 
		{
			String relativePath="/home/Reports/conf/extratobancopaulista.conf"; //$NON-NLS-1$
			System.out.println("Reading " + relativePath + "file"); //$NON-NLS-1$ //$NON-NLS-2$
			reader = new BufferedReader(new FileReader(relativePath)); 
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
					            	this.server = fields[1];
					                break;
					            case "port": //$NON-NLS-1$
					            	this.port = Integer.parseInt(fields[1].replace(" ", "")); //$NON-NLS-1$ //$NON-NLS-2$
					                break;				            	
					            case "userName": //$NON-NLS-1$
					            	this.userName = fields[1];
					                break;
					            case "password": //$NON-NLS-1$
					            	this.password = fields[1];
					                break;
					            case "dbName": //$NON-NLS-1$
					            	this.dbName = fields[1];
					                break;
					            case "rootLocalWindows": //$NON-NLS-1$
					            	OperatingSystem.setRootLocalWindows(fields[1]);
					            	break;
					            case "sshserver": //$NON-NLS-1$
					            	this.sshServer=fields[1];
					            	break;					   
					            case "sshport": //$NON-NLS-1$
					            	this.sshPort = Integer.parseInt(fields[1].replace(" ", "")); //$NON-NLS-1$ //$NON-NLS-2$
					                break;
					            case "sshuser": //$NON-NLS-1$
					            	this.sshUser=fields[1];
					            	break;
					            case "sshpassword": //$NON-NLS-1$
					            	this.sshPassword=fields[1];
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

	}
	public static void readManagerAccount()
	{
		try 
		{
			System.out.println("MVCapital - Usu�rio de Portal"); //$NON-NLS-1$
			Statement stmt = (Statement) OperadorPortalPaulistaChrome.conn.createStatement();
			String query = "SELECT * FROM Conta WHERE idEntidadeProprietario=10"; //$NON-NLS-1$
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idConta = rs.getInt("idConta"); //$NON-NLS-1$
				int idEntidadeProprietario = rs.getInt("idEntidadeProprietario"); //$NON-NLS-1$
				int idEntidadeServidor = rs.getInt("idEntidadeServidor"); //$NON-NLS-1$
				
				Entidade entidadeProprietario = new Entidade(idEntidadeProprietario, conn);
				Entidade entidadeServidor = new Entidade(idEntidadeServidor, conn);
				
				String numero=rs.getString("numero"); //$NON-NLS-1$
				String agencia=rs.getString("agencia"); //$NON-NLS-1$
				String usuario= rs.getString("usuario"); //$NON-NLS-1$
				String senha= rs.getString("senha"); //$NON-NLS-1$
				int idTipoDeConta=rs.getInt("idTipoDeConta"); //$NON-NLS-1$
				Conta account = new	Conta(				 
										entidadeProprietario, 
										entidadeServidor,
										usuario,
										senha,
										agencia, 
										numero,
										idTipoDeConta,
										conn
										);
				account.setIdConta(idConta);
				account.showShort();
				managerAccount=account;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void login()
	{
		
        // And now use this to enter the site
        //driver.get("http://portalfidc.bancopaulista.com.br/portal/login");
        // Alternatively the same thing can be done like this

//	    driver = new FirefoxDriver(fxProfile);
//	    driver = new FirefoxDriver();
	    WebDriverWait wait = new WebDriverWait(this.driver, 20);
//		driver = new ChromeDriver();
	    
        this.driver.navigate().to("http://portalfidc.bancopaulista.com.br/portal/login"); //$NON-NLS-1$
        wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.name("j_username")))); //$NON-NLS-1$

        // Find the text input element by its name
        System.out.println("--------------------"); //$NON-NLS-1$
    	System.out.println("User Login\t"); //$NON-NLS-1$
    	System.out.println("--------------------"); //$NON-NLS-1$
        WebElement elementUsername = this.driver.findElement(By.name("j_username")); //$NON-NLS-1$
        elementUsername.sendKeys(managerAccount.getUsuario());

        WebElement elementPassword = this.driver.findElement(By.name("j_password")); //$NON-NLS-1$
        elementPassword.sendKeys(managerAccount.getSenha());
        elementPassword.submit();

        this.limitTime=this.calLimit.getTime();
        this.calLimit.set(Calendar.HOUR_OF_DAY, 17);
        this.calLimit.set(Calendar.MINUTE, 29);
        this.calLimit.set(Calendar.SECOND, 00);
		this.limitTime=this.calLimit.getTime();				
		this.currentTime = this.cal.getTime();
	}		
	public void logout()
	{
		System.out.println("--------------------"); //$NON-NLS-1$
        System.out.println("Close the browser"); //$NON-NLS-1$
        this.driver.quit();
	}
    public static void playSound(String sound)
    {
    	String urlPathWindows="C:/Binarios/conf/"; //$NON-NLS-1$
    	String urlPathLinux="/home/moises/bin/conf/"; //$NON-NLS-1$
    	
    	String urlPath=""; //$NON-NLS-1$
    	if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			urlPath = urlPathWindows;
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			urlPath = urlPathLinux;
		}
		try 
		{
			System.out.println("file://" + urlPath + sound); //$NON-NLS-1$
			AudioClip clip = Applet.newAudioClip(new URL("file://" + urlPath + sound + ".wav")); //$NON-NLS-1$ //$NON-NLS-2$
			clip.play();
		} 
		catch (MalformedURLException murle) 
		{
			System.out.println(murle);
		}
    }
    
    

	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	public static void setSdf(SimpleDateFormat sdf) {
		OperadorPortalPaulistaChrome.sdf = sdf;
	}

	public static SimpleDateFormat getSdfd() {
		return sdfd;
	}

	public static void setSdfd(SimpleDateFormat sdfd) {
		OperadorPortalPaulistaChrome.sdfd = sdfd;
	}

	public ArrayList<Operacao> getOperations() {
		return this.operations;
	}

	public void setOperations(ArrayList<Operacao> operations) {
		this.operations = operations;
	}

	public static DecimalFormat getDf() {
		return df;
	}

	public static void setDf(DecimalFormat df) {
		OperadorPortalPaulistaChrome.df = df;
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public Calendar getCal() {
		return this.cal;
	}

	public void setCal(Calendar cal) {
		this.cal = cal;
	}

	public Calendar getCalLimit() {
		return this.calLimit;
	}

	public void setCalLimit(Calendar calLimit) {
		this.calLimit = calLimit;
	}

	public Date getLimitTime() {
		return this.limitTime;
	}

	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}

	public Date getCurrentTime() {
		return this.currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public static Conta getManagerAccount() {
		return managerAccount;
	}

	public static void setManagerAccount(Conta managerAccount) {
		OperadorPortalPaulistaChrome.managerAccount = managerAccount;
	}

	public MySQLAccess getMysql() {
		return this.mysql;
	}

	public void setMysql(MySQLAccess mysql) {
		this.mysql = mysql;
	}

	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		OperadorPortalPaulistaChrome.conn = conn;
	}

	public WebDriverWait getWait() {
		return this.wait;
	}

	public void setWait(WebDriverWait wait) {
		this.wait = wait;
	}

	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return this.dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public SshClient getSshClient() {
		return this.sshClient;
	}

	public void setSshClient(SshClient sshClient) {
		this.sshClient = sshClient;
	}

	public static SimpleDateFormat getSdfr() {
		return sdfr;
	}

	public static void setSdfr(SimpleDateFormat sdfr) {
		OperadorPortalPaulistaChrome.sdfr = sdfr;
	}

	public UpdateConcentracao getUc() {
		return this.uc;
	}

	public void setUc(UpdateConcentracao uc) {
		this.uc = uc;
	}

	public ArrayList<Operacao> getBlackListOperations() {
		return this.blackListOperations;
	}

	public void setBlackListOperations(ArrayList<Operacao> blackListOperations) {
		this.blackListOperations = blackListOperations;
	}

	public String getRootDownloadsLinux() {
		return this.rootDownloadsLinux;
	}

	public void setRootDownloadsLinux(String rootDownloadsLinux) {
		this.rootDownloadsLinux = rootDownloadsLinux;
	}

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getClickX() {
		return this.clickX;
	}

	public void setClickX(int clickX) {
		this.clickX = clickX;
	}

	public int getClickY() {
		return this.clickY;
	}

	public void setClickY(int clickY) {
		this.clickY = clickY;
	}

	public int getScrollSize() {
		return this.scrollSize;
	}

	public void setScrollSize(int scrollSize) {
		this.scrollSize = scrollSize;
	}

	public boolean isJustApprouved() {
		return this.justApprouved;
	}

	public void setJustApprouved(boolean justApprouved) {
		this.justApprouved = justApprouved;
	}

	public boolean isJustReadedTransferDetails() {
		return this.justReadedTransferDetails;
	}

	public void setJustReadedTransferDetails(boolean justReadedTransferDetails) {
		this.justReadedTransferDetails = justReadedTransferDetails;
	}

	public String getRootDownloadsLocal() {
		return this.rootDownloadsLocal;
	}

	public void setRootDownloadsLocal(String rootDownloadsLocal) {
		this.rootDownloadsLocal = rootDownloadsLocal;
	}

	public String getRootReportsLocal() {
		return this.rootReportsLocal;
	}

	public void setRootReportsLocal(String rootReportsLocal) {
		this.rootReportsLocal = rootReportsLocal;
	}

	public String getRootReportsLocalLinux() {
		return this.rootReportsLocalLinux;
	}

	public void setRootReportsLocalLinux(String rootReportsLocalLinux) {
		this.rootReportsLocalLinux = rootReportsLocalLinux;
	}

	public String getRootReportsLinux() {
		return this.rootReportsLinux;
	}

	public void setRootReportsLinux(String rootReportsLinux) {
		this.rootReportsLinux = rootReportsLinux;
	}

	public static SimpleDateFormat getSdtfd() {
		return sdtfd;
	}

	public static void setSdtfd(SimpleDateFormat sdtfd) {
		OperadorPortalPaulistaChrome.sdtfd = sdtfd;
	}

	public static DecimalFormat getDfs() {
		return dfs;
	}

	public static void setDfs(DecimalFormat dfs) {
		OperadorPortalPaulistaChrome.dfs = dfs;
	}

	public String getSshServer()
	{
		return this.sshServer;
	}

	public void setSshServer(String sshServer)
	{
		this.sshServer = sshServer;
	}

	public String getSshUser()
	{
		return this.sshUser;
	}

	public void setSshUser(String sshUser)
	{
		this.sshUser = sshUser;
	}

	public String getSshPassword()
	{
		return this.sshPassword;
	}

	public void setSshPassword(String sshPassword)
	{
		this.sshPassword = sshPassword;
	}

	public int getSshPort()
	{
		return this.sshPort;
	}

	public void setSshPort(int sshPort)
	{
		this.sshPort = sshPort;
	}
}