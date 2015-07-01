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

import mvcapital.conta.Conta;
import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.indicadores.UpdateConcentracao;
import mvcapital.mysql.MySQLAccess;
import mvcapital.operation.HandlerOperacoes;
import mvcapital.operation.HandlerStatus;
import mvcapital.operation.Operacao;
import mvcapital.operation.OperationSummary;
import mvcapital.operation.Status;
import mvcapital.relatorio.cessao.BlockIdentificacao;
import mvcapital.relatorio.cessao.BlockTitulos;
import mvcapital.relatorio.cessao.HandlerTitulo;
import mvcapital.relatorio.cessao.Relatorio;
import mvcapital.relatorio.cessao.TipoTitulo;
import mvcapital.relatorio.cessao.Titulo;
import mvcapital.utils.OperatingSystem;
import mvcapital.utils.SshClient;

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

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class OperadorPortalPaulista  
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdtfd =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");

    private static DecimalFormat dfs = new DecimalFormat("#.00");

	private ArrayList<Operacao> operations = new ArrayList<Operacao>();
	private ArrayList<Operacao> blackListOperations = new ArrayList<Operacao>();
	
	private static DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
    private WebDriver driver = null;
	private Calendar cal = Calendar.getInstance();
    private Calendar calLimit=Calendar.getInstance();
    private Date limitTime=calLimit.getTime();
	private Date currentTime = cal.getTime();
	private static Conta managerAccount;
	private MySQLAccess mysql = null;
	public static Connection conn = null;
	private WebDriverWait wait = null;
	private String server = "localhost";
	private int port = 3306;		
	private String userName = "root"; 
	private String password = "root";
	private String dbName = "root";
	private SshClient sshClient = new SshClient("192.168.2.122", 22, "moises", "preciosa");
	private String rootDownloadsLinux = "";
	private String rootDownloadsLocal = "";
	private String rootReportsLocal = "";
	private String rootReportsLocalLinux = "";
	private String rootReportsLinux = "/home/Reports";
	private String hostname = "";
	private int clickX=0;
	private int clickY=0;
	private int scrollSize=0;
	private UpdateConcentracao uc = new UpdateConcentracao();
	private boolean justApprouved=false;
	private boolean justReadedTransferDetails=false;
	
	public OperadorPortalPaulista()
	{
		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    this.hostname = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
		    System.out.println("Hostname can not be resolved");
		}		
		System.out.println("Hostname: " + this.hostname);

		if(this.hostname.toLowerCase().contains("mvcapital12"))
		{
			this.clickX = 810;
			this.clickY = 520;
			this.rootDownloadsLinux = "/home/DownloadsPortal2";
			this.rootDownloadsLocal = "C:\\DownloadsPortal";
			this.rootReportsLocal = "C:\\DownloadsPortal\\Reports";
			this.rootReportsLocalLinux = "/home/DownloadsPortal2/Reports";
			this.scrollSize = 100;
		}
		else if(this.hostname.toLowerCase().contains("mvcapital06"))
		{
			this.clickX = 670;
			this.clickY = 440;			
			this.rootDownloadsLinux = "/home/DownloadsPortal";
			this.rootDownloadsLocal = "C:\\DownloadsPortal";
			this.rootReportsLocal = "C:\\DownloadsPortal\\Reports";
			this.rootReportsLocalLinux = "/home/DownloadsPortal/Reports";
			this.scrollSize = 110;
		}
		System.out.println("RootDownloadsLinux: " + this.rootDownloadsLinux);
		this.readConf();
		this.mysql = new MySQLAccess(this.server, this.port, this.userName, this.password, this.dbName);
		mysql.connect();	
		OperadorPortalPaulista.conn = (Connection) mysql.getConn();
		readManagerAccount();
	}
	
	public OperadorPortalPaulista(Conta managerAccount2) {
		OperadorPortalPaulista.readManagerAccount();
	}

	public void openBrowser()
	{
		FirefoxProfile fxProfile = new FirefoxProfile();

	    fxProfile.setPreference("browser.download.folderList",2);
	    fxProfile.setPreference("browser.download.manager.showWhenStarting",false);
	    
	    System.out.println("OS Name: " + System.getProperty("os.name").toLowerCase());
	    
		if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			fxProfile.setPreference("browser.download.dir", this.getRootDownloadsLocal());
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux"))
		{			
			fxProfile.setPreference("browser.download.dir", this.getRootDownloadsLinux());
		}
	    //fxProfile.setPreference("browser.download.dir","downloads");
	    fxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
	    fxProfile.setPreference("browser.download.manager.showWhenStarting",false);
	    fxProfile.setPreference("browser.pdfjs.disabled",true);
	    fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",true);
	    this.driver = new FirefoxDriver(fxProfile);
		this.wait = new WebDriverWait(driver, 20);		
	}
	
	public static void main(String[] args)	
	{
		OperadorPortalPaulista operador = new OperadorPortalPaulista();
		Calendar cal = Calendar.getInstance();
		Date currentTime = cal.getTime();		
		operador.getSshClient().connect();
		operador.setUc(new UpdateConcentracao(OperadorPortalPaulista.conn));

		operador.getUc().update();
		operador.openBrowser();
		operador.login();
		while (currentTime.before(operador.limitTime))
		{	
			operador.monitor();
			cal = Calendar.getInstance();
			currentTime = cal.getTime();
			HandlerOperacoes.timeAndWait(3);
		}
		operador.logout();
		
//		HandlerOperacoes.readStoredOperations(conn);
		
		operador.getSshClient().disconnect();
	}

	public void monitor() 
	{
		cal=Calendar.getInstance();
    	System.out.println("--------------------");
    	System.out.println("Monitoring at\t" + sdf.format(cal.getTime()));
    	System.out.println("--------------------");
    	
    	if(this.operations.size()==0)
    	{
    		ArrayList<Operacao> storedOperations = HandlerOperacoes.readStoredOperations(conn);
    		if(storedOperations.size()!=0)
    		{
	    		for(Operacao op:storedOperations)
	    		{
	    			System.out.println("---------------------------------------");
	    			System.out.println("Adding stored operation " + op.getFundo().getNome() + "\t" +op.getNomeArquivo());
	    			System.out.println("---------------------------------------");
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
        		jse.executeScript("window.scrollBy(0, " + scrollUp +")", "");
    	    }
	    	WebElement linkLiquidacao = driver.findElement(By.linkText("Liquidação"));
	    	linkLiquidacao.click();
	    	HandlerOperacoes.timeAndWait(5);
	        WebElement xField = driver.findElement(By.className("search-choice-close"));
	    	xField.click();
	    	this.setJustApprouved(false);
    	}	    
	    if(this.operations.size()>0)
	    {
	    	JavascriptExecutor jse = (JavascriptExecutor) this.driver;
//    		System.out.println("Scroll up before click on pdf link ");
    		int scrollUp = -900-this.scrollSize*this.operations.size();
//    		System.out.println("ScrollUp: " + scrollUp);
    		jse.executeScript("window.scrollBy(0, " + scrollUp +")", "");
	    }
	    HandlerOperacoes.timeAndWait(3);
        WebElement buttonPesquisar = driver.findElement(By.id("pesquisa"));
    	buttonPesquisar.click();
    	System.out.println("Pesquisar clicked");
    	HandlerOperacoes.timeAndWait(4);
        
    	List<WebElement> listOfRowsLiquidacao = driver.findElements(By.className("rowLiquidacao"));
    	List<WebElement> listOfPdf = driver.findElements(By.name("pdfLiquidacao"));
    	
    	if (listOfRowsLiquidacao.size()==0 && listOfPdf.size() == 0)
    	{
    		return;
    	}

    	if(listOfRowsLiquidacao.size()%listOfPdf.size()==0)
    	{
        	System.out.println("ListOfRowsLiquidacaoSize: " + listOfRowsLiquidacao.size());
        	System.out.println("ListOfPdf: " + listOfPdf.size());
        	System.out.println(listOfRowsLiquidacao.size()/listOfPdf.size() + " fields");
        	System.out.println(listOfPdf.size() + " ops");
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
    			rowLiquidacaoOperacao.add(listOfPdf.get(iOp));
    			rowsOperation.add(rowLiquidacaoOperacao);
    			iOp++;
    			if(countWE<listOfRowsLiquidacao.size())
    			{
    				rowLiquidacaoOperacao = new ArrayList<WebElement>();
    			}
    		}    		    		
    		countWE=countWE+1;
    	}

    	System.out.println("rowsOperation: " + rowsOperation.size());
    	
    	for(ArrayList<WebElement> row:rowsOperation)
    	{
    		if(row.size()<7)
    		{
    			return;
    		}
    		for(WebElement we:row)
    		{
    			if(we.isDisplayed())
    			{
    				
    			}
    			else
    			{
    				System.out.println("Missing displayed elements");
    				return;
    			}
    		}
    	}
    	
    	System.out.println("All WebElements in correct number!");
//    	System.out.println("rowsOperation: " + rowsOperation.size());
    	
    	ArrayList<Operacao> onlineOperations = new ArrayList<Operacao>();
    	
    	for(ArrayList<WebElement> rowOperation:rowsOperation)
    	{
    		JavascriptExecutor jse = (JavascriptExecutor) this.driver;
    		System.out.println("Scroll down before click on pdf link ");
    		jse.executeScript("window.scrollBy(0, " + this.scrollSize + ")", ""); 

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
    					cal = Calendar.getInstance();
    					Date lastStatusTime = cal.getTime();
    					operacao.getStatuses().get(0).setBeginDate(lastStatusTime);
    					System.out.println("-- Changed Status --");    					
    					System.out.println("From " + op.getStatuses().get(op.getStatuses().size()-1).getDescription());
    					System.out.println("To  " + operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription());
    					
    					String beforeStatusDescription = op.getStatuses().get(op.getStatuses().size()-1).getDescription();
    					String currentStatusDescription = operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription();

    					if(!op.isAprovado() 
    							&& beforeStatusDescription.contains("Gestor")
    							&& !beforeStatusDescription.contains("TED")
    							&& currentStatusDescription.contains("Aguardando Assinatura Digital")
    							)
    					{
        					Status statusAprovadoManualmente = new Status("Aprovado manualmente",Calendar.getInstance().getTime());
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
    						if(status.getDescription().toLowerCase().contains("cancelado"))
    						{
    							System.out.println("Trying to remove: " + op.getStatuses().get(iStatus).getDescription());
    							op.getStatuses().remove(iStatus);
    							iStatus=iStatus-1;
    							System.out.println("After removing cancelado");
    							break;
    						}    						
    						iStatus=iStatus+1;
    					}  
    					
    				}

					if(!op.isTedEnviada())
		        	{
		        		System.out.println("--Check electronic transfer--");
		        		System.out.println("--For " + op.getNomeArquivo() + "--");
		        		
		        		boolean haveResourcesSent=false;
		        		for(Status status:op.getStatuses())
		        		{
		        			if(status.getDescription().toLowerCase().contains("recursos enviada"))
		        			{
		        				haveResourcesSent=true;
		        				break;
		        			}
		        		}
		        		
		        		if(haveResourcesSent)
		        		{        			
		        			System.out.println("\tHaveResourcesSent");
		        			if(!op.isHaveTransferDetailsChecked())
		        			{
		        				readOperationTransferDetails(rowOperation, op);
				    			HandlerOperacoes.updateStore(op, conn);
				    			return;
		        			}
		        			HandlerOperacoes.transferDone(op, conn);
		        		}
		        		else
		        		{
		        			System.out.println("Skip checking transfer controller");
		        		}
		        	}        						


					if(!op.getContaExterna1().getAgencia().equals("")
							|| !op.getContaExterna2().getAgencia().equals("")
							|| !op.getContaInterna().getAgencia().equals("")
							)
					{
						if(!op.isRegistrado())
						{
							System.out.println("Saving Operation details to database");
							HandlerOperacoes.store(op, conn);
							System.out.println("Operation stored database");
							HandlerStatus.store(op, conn);
							System.out.println("Status stored database");
							HandlerTitulo.store(op, conn);
							System.out.println("Titles stored database");
							op.setRegistrado(true);
						}
					}
					else
					{
						System.out.println("Operacao sem dados bancarios");
						readOperationTransferDetails(rowOperation, op);
						return;
					}
    				
    				if(op.isAprovado())
    				{
    					System.out.println("Aprovado");
    					if(op.getStatuses().get(op.getStatuses().size()-1).getDescription().contains("aprovação do Gestor"))
    					//if(op.getStatus().get(op.getStatus().size()-1).getDescription().contains("Reprovado"))
    		    		{
    						System.out.println("Aguardando Gestor");
    						if(op.getStatuses().get(op.getStatuses().size()-1).getDescription().contains("TED"))
    						{
    							System.out.println("Gestor de TED");
    							//this.setJustReadedTransferDetails(true);
    							break;
    						}
    						else
    						{
    							System.out.println("Não é gestor de TED");
    		        			if(!op.isHaveTransferDetailsChecked())
    		        			{
    		        				readOperationTransferDetails(rowOperation, op);
    				    			HandlerOperacoes.updateStore(op, conn);
    		        			}
    			    			System.out.println("--------------------");
    			    	    	System.out.println("Approuving at\t" + sdf.format(cal.getTime()));
    			    	    	System.out.println("--------------------");	    	    	
    			    			approuve(op);
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
    			if(operacao.getRelatorio().getBlockTitulos().getTitulos().size()==0)
    			{
    				System.out.println("Trouble reading pdf file!");
    				break;
    			}
    			else
    			{
    				System.out.println("PDF File successfully read");
    			}
    			//assessOperation(operacao);
    			OperationSummary resumo = new OperationSummary(operacao);
    			operacao.setResumo(resumo);
    			HandlerOperacoes.assessOperation(operacao, conn);
    			
        		if(operacao.getResumo().getSacadosAttempt().size()==0)
    	    	{
    	    		System.out.println("Trouble reading sacados! - Try again");
    	    		break;
    	    	}
        		
        		readOperationTransferDetails(rowOperation, operacao);
				cal = Calendar.getInstance();
				Date lastCheckTime = cal.getTime();		
				operacao.getStatuses().get(operacao.getStatuses().size()-1).setBeginDate(lastCheckTime);
				//operacao.getStatus().get(operacao.getStatus().size()-1).setEndDate(lastCheckTime);
				
				if(!operacao.getContaExterna1().getAgencia().equals("")
						|| !operacao.getContaExterna2().getAgencia().equals("")
						|| !operacao.getContaInterna().getAgencia().equals(""))
				{
					System.out.println("Saving New Operation details to database");
					HandlerOperacoes.store(operacao, conn);
					System.out.println("Operation stored database");
					HandlerStatus.store(operacao, conn);
					System.out.println("Status stored database");
					HandlerTitulo.store(operacao, conn);
					System.out.println("Titles stored database");
				}
    			this.operations.add(operacao);
    			System.out.println("---New Array Operations Element---");
    			this.operations.get(this.operations.size()-1).show();
    			//operacao.show();
    			if(operacao.isAprovado())
    			{
    				System.out.println("Aprovado");    				
    				if(operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription().contains("aprovação do Gestor"))
    				//if(operacao.getStatus().get(operacao.getStatus().size()-1).getDescription().contains("Reprovado"))
    	    		{
    					System.out.println("Aguardando Gestor");
						if(operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription().contains("TED"))
						{
							System.out.println("Gestor de TED");
							this.setJustReadedTransferDetails(true);
							return;
						}
						else
    					{
    						System.out.println("Não é gestor de TED");
//    		    			readOperationTransferDetails(rowOperation, operacao);
    		    			System.out.println("--------------------");
    		    	    	System.out.println("Approuving at\t" + sdf.format(cal.getTime()));
    		    	    	System.out.println("--------------------");	    	    	
    		    			approuve(operacao);
    		    			return;
    					}
    	    		}
        			else
        			{
        				return;
        			}

    			}
    			else
    			{
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
        		System.out.println("--Check electronic transfer--");
        		System.out.println("--For " + op.getNomeArquivo() + "--");
        		
        		boolean haveResourcesSent=false;
        		for(Status status:op.getStatuses())
        		{
        			if(status.getDescription().toLowerCase().contains("recursos enviada"))
        			{
        				haveResourcesSent=true;
        				break;
        			}
        		}
        		
        		if(haveResourcesSent)
        		{        			
        			System.out.println("\tHaveResourcesSent");
        			HandlerOperacoes.transferDone(op, conn);
        		}
        		else
        		{
        			System.out.println("Skip checking transfer controller");
        		}
        	}        	

        	if(isErased && !op.getStatuses().get(op.getStatuses().size()-1).getDescription().equals("Cancelado"))        		
        	{
        		Status statusCancelado = new Status(op.getIdOperacao(), "Cancelado",Calendar.getInstance().getTime(),conn);
        		op.getStatuses().add(statusCancelado);
        		HandlerOperacoes.updateConcentracaoCancelado(op);
        	}
        	else if(isErased && op.getStatuses().get(op.getStatuses().size()-1).getDescription().equals("Cancelado"))
        	{
        		op.getStatuses().get(op.getStatuses().size()-1).setEndDate(Calendar.getInstance().getTime());
        	}
			op.getStatuses().get(op.getStatuses().size()-1).setEndDate(Calendar.getInstance().getTime());
        }

    	System.out.println("--------------------");
    	writeOperationSummary();
//    	String stringReport="";
    	
    }
	 
	 public void writeOperationSummary()
	 {
	    	String operationsSummary = "";
	    	for(Operacao op:this.operations)
	    	{
	    		String stringAprovadoGestor="ReprovadoGestor";
	    		System.out.println("SizeStatus: " + op.getStatuses().size());
	    		if(op.getStatuses().size()==0)
	    		{
	    			continue;
	    		}
	    		op.getStatuses().get(op.getStatuses().size()-1).setEndDate(Calendar.getInstance().getTime());
	    		if(op.isAprovado())
	    		{
	    			stringAprovadoGestor="AprovadoGestor";
	    		}
	    		
	    		String stringTiposDeDireitoCreditorio="";
	    		for(TipoTitulo tipo:op.getRelatorio().getBlockTitulos().getTiposTitulo())
	    		{
	    			if(stringTiposDeDireitoCreditorio.equals(""))
	    			{
	    				stringTiposDeDireitoCreditorio=stringTiposDeDireitoCreditorio+tipo.getDescricao();
	    			}
	    			else
	    			{
	    				stringTiposDeDireitoCreditorio=stringTiposDeDireitoCreditorio+","+tipo.getDescricao();
	    			}
	    		}    		
	    		
	    		if(!op.isRegistrado())
	    		{
		    		System.out.println("I must save this operation at DataBase, without the titles on it");
		    		System.out.println("Fundo: " + op.getFundo().getNome());
		    		System.out.println("Arquivo: " + op.getNomeArquivo());
		    		System.out.println("Cedente: " + op.getCedente().getNome());		    		
//		    		HandlerOperacoes.store(op, conn);
//		    		HandlerStatus.store(op, conn);
//		    		HandlerDireitoCreditorio.store(op, conn);
		    		op.setRegistrado(true);
	    		}
	    		else
	    		{
		    		System.out.println("Operation already saved at DataBase");		    		
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
	    						+ ";" + op.getNomeArquivo()
	    						+ ";" + stringAprovadoGestor
	    						+ ";" + op.getCedente().getNome()
	    						+ ";" + stringTiposDeDireitoCreditorio
	    						+ ";" + op.getCertificadora()
	    						+ ";" + dfs.format(op.getTotalAquisicao())
	    						+ ";" + dfs.format(op.getTotalNominal())
	    						+ ";" + op.getStatuses().get(op.getStatuses().size()-1).getDescription()
								+ ";" + sdf.format(op.getRegisterTime())
								+ ";" + sdf.format(op.getStatuses().get(op.getStatuses().size()-1).getBeginDate())
								+ ";" + sdf.format(op.getStatuses().get(op.getStatuses().size()-1).getEndDate())    							
								+ ";" + op.getStatuses().get(op.getStatuses().size()-1).getLifeTimeString()
	    						+ ";" + dfs.format(op.getValorRemessaSemAdiantamento())
	    						+ ";" + dfs.format(op.getValorRecompraSemAdiantamento())
	    						+ ";" + dfs.format(op.getReembolsoContaNormal())
	    						+ ";" + dfs.format(op.getValorTotalSemAdiantamento())
	    						+ ";" + op.getContaExterna1().getCodigoServidor()
	    						+ ";" + op.getContaExterna1().getAgencia()
	    						+ ";" + op.getContaExterna1().getNumero()
	    						+ ";" + dfs.format(op.getValorTotalCedenteSemAdiantamento1())
	    						+ ";" + op.getContaExterna2().getCodigoServidor()
	    						+ ";" + op.getContaExterna2().getAgencia()
	    						+ ";" + op.getContaExterna2().getNumero()
	    						+ ";" + dfs.format(op.getValorTotalCedenteSemAdiantamento2())    						    						    						
	    						+ ";" + op.getResumo().getDescription()
	    						+ "\n"
	    				;
	    	}
	    	
	    	System.out.println(operationsSummary);
	    	System.out.println("--Reprovados - Detail--");
	    	String reprouvedOperationsSummary="";
	    	for(Operacao op:this.operations)
	    	{
	    		String stringAprovado="ReprovadoGestor";
	    		String[] descriptionDetails=op.getResumo().getDescription().split(";");
	    		if(!op.isAprovado())
	    		{   			    		    		
	    			reprouvedOperationsSummary=reprouvedOperationsSummary +
	    										op.getFundo().getNomeCurto()
					    						+ "\t" + op.getNomeArquivo()   						
					    						+ "\t" + op.getStatuses().get(op.getStatuses().size()-1).getDescription()    						
					    						+ "\t" + stringAprovado
					    						+ "\n"
					    						;
	    			for(String detail:descriptionDetails)
	    			{
	    				System.out.println("\t" + detail);
	    			}
	    		}
	    	}
	    	System.out.println(reprouvedOperationsSummary);
	    	this.saveReportFile("operationsSummary.txt", operationsSummary);
//	    	this.saveReportFileLocal("operationsSummary.txt", operationsSummary);
	 }
	 
	public void readStoredStatusForOperation(Operacao op)
	{
		ArrayList<Status> status = new ArrayList<Status>();
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulista.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT * FROM operacao WHERE "
				       + " dataDeEntrada = " + sdfd.format(Calendar.getInstance().getTime())
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
				op.setIdOperacao(rs.getInt("idOperacao"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		query = "SELECT idStatusOperacao FROM statusoperacao WHERE idOperacao = " + op.getIdOperacao();
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
				int idStatusOperacao = rs.getInt("idStatusOperacao");
				status.add(new Status(idStatusOperacao,conn));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		op.setStatuses(status);
	}
	 

	public int getIdEntidadeBanco(String numeroBanco)
	{
		int idEntidadeBanco=0;
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulista.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String query = "SELECT idEntidadeBanco FROM codigobanco WHERE codigo=" + numeroBanco;
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
				idEntidadeBanco = rs.getInt("idEntidadeBanco");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(idEntidadeBanco==0)
		{
			System.out.println("Error bringing idEntidadeBanco");
		}
		return idEntidadeBanco;
	}	
	

	public void saveReportFile(String fileName, String content)
	{
    	File file=null;
    	System.out.println("Trying to create file " + fileName);
		file = new File(this.getRootReportsLocal() + "\\" + fileName);
		
		String fileNewName = this.getRootReportsLinux() + "/" + fileName;
		
		System.out.println("File name " + file.getAbsolutePath());
		System.out.println("New file name " + fileNewName);
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
			pw = new PrintWriter(file, "ISO_8859_1");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		pw.write(content);
		pw.flush();
		pw.close();
		
	    String command="";	    
	    command="cp " + this.getRootReportsLocalLinux() + "/" + fileName + " " + fileNewName;
	    System.out.println("command: " + command);
	    sshClient.executeCommandOutput(command);
	    	    
	    command="perl " + this.getRootReportsLinux() + "/generateOperationsSummaryWeb.pl";
	    System.out.println("command: " + command);
	    sshClient.executeCommandOutput(command);		 
	    
	}

	public void saveReportFileLocal(String fileName, String content)
	{
    	File file=null;
    	System.out.println("Trying to create file " + fileName);
		file = new File(this.getRootReportsLocal() + "\\" + fileName);
		
		System.out.println("File name " + file.getAbsolutePath());

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
			pw = new PrintWriter(file, "ISO_8859_1");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		pw.write(content);
		pw.flush();
		pw.close();
		System.out.println("File " + file.getName() + " saved");
		
//	    String command="perl C:\\DownloadsPortal\\Reports\\generateOperationsSummaryWeb.pl";
//	    WindowsCommand.execute(command);
//	    System.out.println("Perl command executed!");
	    
	    String command="perl " + this.getRootReportsLocalLinux() + "/generateOperationsSummaryWeb.pl";
	    System.out.println("command: " + command);
	    sshClient.executeCommandOutput(command);		 
	    
	}
	
	public void approuve(Operacao operacao)
	{    	
    	if(operacao.isAprovado())
    	{
    		System.out.println("\nOperação " + operacao.getFundo().getNome() + " " + operacao.getCedente().getNome() +  " "+ operacao.getNomeArquivo() + " aprovada!");
    	}	    	

    	if (operacao.isAprovado())
    	{
    		//Click to approuve
    		
    		if (operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription().toLowerCase().contains("assinatura digital"))
    		{
    			HandlerOperacoes.updateConcentracao(operacao);
    		}
    		else
    		{
    			if(!driver.findElements(By.id("aprovar")).isEmpty())
    			{
		    		WebElement buttonAprovar = driver.findElement(By.id("aprovar"));
		    		if(buttonAprovar.isDisplayed())
		    		{
			    		buttonAprovar.click();
		    			System.out.println("Aprovar clicked!");
		    		}
		    		else
		    		{
		    			System.out.println("Already Aprovar clicked!");
		    		}
    			}
    			else
    			{
    				System.out.println("Aprovar webElement is not present");
    			}
	    		HandlerOperacoes.updateConcentracao(operacao);
    		}
    	}
    	else
    	{
    		if (operacao.getStatuses().get(operacao.getStatuses().size()-1).getDescription().toLowerCase().contains("assinatura digital"))
    		{
    			HandlerOperacoes.updateConcentracao(operacao);
    		}    		
    		blackListOperations.add(operacao);
    	}

    	System.out.println("Just approuved!");
        this.setJustApprouved(true);
	}
	
	public void readOperationTransferDetails(List<WebElement> rowOperation, Operacao operacao)
	{
		System.out.println("--Reading trasfer details--");
		String nomeFundo=operacao.getFundo().getNome();
		String nomeArquivo=operacao.getNomeArquivo();
		Date dataDeImportacao=Calendar.getInstance().getTime();

		double valorTotalAquisicaoComAdiantamento=0;
		double valorRecompraComAdiantamento=0;
		double reembolsoContaEspecial=0;
		double valorTotalComAdiantamento=0;

		String contaCorrenteInternaPaulista="";
		double valorTotalContaCorrenteInternaPaulista=0;
		String numeroBancoContaCorrenteInternaPaulista="";
		String numeroAgenciaContaCorrenteInternaPaulista="";
		String numeroContaCorrenteInternaPaulista="";

		double valorRemessaSemAdiantamento=0;
		double valorRecompraSemAdiantamento=0;
		double reembolsoContaNormal=0;
		double valorTotalSemAdiantamento=0;

		String contaCedenteSemAdiantamento1="";
		double valorTotalCedenteSemAdiantamento1=0;
		String numeroBancoContaCedenteSemAdiantamento1="";
		String numeroAgenciaContaCedenteSemAdiantamento1="";
		String numeroContaCorrenteContaCedenteSemAdiantamento1="";

		String contaCedenteSemAdiantamento2="";
		double valorTotalCedenteSemAdiantamento2=0;
		String numeroBancoContaCedenteSemAdiantamento2="";
		String numeroAgenciaContaCedenteSemAdiantamento2="";
		String numeroContaCorrenteContaCedenteSemAdiantamento2="";
		
		String certificadora="";
				
        rowOperation.get(5).click();
        System.out.println("--Click--");
        HandlerOperacoes.timeAndWait(5);
		System.out.println("--After waiting--");
    	
//    	System.out.println("\nMore Data");
		if(driver.findElement(By.id("nomeFundo")).isDisplayed())
		{	
	        System.out.println("--NomeFundoIsDisplayed--");
	    	WebElement weNomeFundo = driver.findElement(By.id("nomeFundo"));	    	
	    	if(!nomeFundo.equals(weNomeFundo.getAttribute("value")))
	    	{
	    		System.out.println("Nome do fundo não coincide!");
	    		System.out.println("Tentando novamente!");
	    		//this.approuve();	//Recursive attempt
	    	}
	    	else
	    	{
	    		System.out.println("nomeFundo: " + nomeFundo);
	    	}
	    	WebElement weDataDeImportacao = driver.findElement(By.id("dataImportacao"));
	    	try {
				dataDeImportacao = sdfr.parse(weDataDeImportacao.getAttribute("value"));
				System.out.println("dataDeImportacao: " + sdfr.format(dataDeImportacao));
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    	WebElement nomeArquivo2 = driver.findElement(By.id("nomeArquivo"));
	    	if(!nomeArquivo.equals(nomeArquivo2.getAttribute("value")))
	    	{
	    		System.out.println("Nome do Arquivo não coincide!");
	    		System.out.println("Tentando novamente!");
	    		//this.approuve();	//Recursive attempt    		
	    	}
	    	else
	    	{
	//    		System.out.println("nomeArquivo: " + nomeArquivo);
	    	}

	    	if(driver.findElement(By.id("valorTotalAquisicaoComAdiantamento")).isDisplayed())
	    	{
	    		System.out.println("--Remessa com adiantamento is displayed--");
	    	}
	    	
	    	WebElement weValorTotalAquisicaoComAdiantamento = driver.findElement(By.id("valorTotalAquisicaoComAdiantamento"));
	    	String stringValorTotalAquisicaoComAdiantamento = weValorTotalAquisicaoComAdiantamento.getText();
	    	System.out.println("Remesa: " + stringValorTotalAquisicaoComAdiantamento);
	    	if(stringValorTotalAquisicaoComAdiantamento.length()>0)
	    	{
	    		valorTotalAquisicaoComAdiantamento=Double.parseDouble(stringValorTotalAquisicaoComAdiantamento
	    																.replace("-R$ ", "-")
													    				.replace("R$", "")
													    				.replace(".","")
													    				.replace(",", ".").trim()
													    				);	    		
	    	}

	    	if(driver.findElement(By.id("valorRecompraComAdiantamento")).isDisplayed())
	    	{
	    		System.out.println("--Recompra com adiantamento is displayed--");
	    	}
	    	
	    	WebElement weValorRecompraComAdiantamento = driver.findElement(By.id("valorRecompraComAdiantamento"));
	    	String stringValorRecompraComAdiantamento = weValorRecompraComAdiantamento.getText();
	    	if(stringValorRecompraComAdiantamento.length()>0)
	    	{
	    		valorRecompraComAdiantamento = Double.parseDouble(weValorRecompraComAdiantamento.getText().replace("R$", "").replace(".","").replace(",", ".").trim());
	    	}
	    	System.out.println("stringValorRecompraComAdiantamento: " + stringValorRecompraComAdiantamento);
	    	
	    	if(driver.findElement(By.id("reembolsoContaEspecial")).isDisplayed())
	    	{
	    		System.out.println("--Reembolso com adiantamento is displayed--");
	    	}
	    	 	
	    	WebElement weReembolsoContaEspecial = driver.findElement(By.id("reembolsoContaEspecial"));
	    	String stringReembolsoContaEspecial = weReembolsoContaEspecial.getText();
	    	if(stringReembolsoContaEspecial.length()>0)
	    	{
	    		reembolsoContaEspecial = Double.parseDouble(stringReembolsoContaEspecial.replace("R$", "").replace(".","").replace(",", ".").trim());
		    	System.out.println("stringReembolsoContaEspecial: "+ stringReembolsoContaEspecial);
	    	}
	    	
	    	
	    	if(driver.findElement(By.id("valorTotalComAdiantamento")).isDisplayed())
	    	{
	    		System.out.println("--Valor total com adiantamento is displayed--");
	    	}
	    	
	    	WebElement weValorTotalComAdiantamento = driver.findElement(By.id("valorTotalComAdiantamento"));
	    	String stringValorTotalComAdiantamento = weValorTotalComAdiantamento.getText();
	    	if(stringValorTotalComAdiantamento.length()>0)
	    	{
	    		valorTotalComAdiantamento = Double.parseDouble(stringValorTotalComAdiantamento.replace("R$", "").replace(".","").replace(",", ".").trim());
		    	System.out.println("Valor total com Adiantamento: " + stringValorTotalComAdiantamento);	    		
	    	}

	    	
	    	WebElement comboContaCorrenteInternaPaulista = driver.findElement(By.id("comboContaCorrenteInternaPaulista"));
	    	contaCorrenteInternaPaulista = comboContaCorrenteInternaPaulista.getAttribute("value");
	    		    	
	    	WebElement weValorTotalContaCorrenteInternaPaulista = driver.findElement(By.id("valorTotalContaCorrenteInternaPaulista"));
	    	String stringValorTotalContaCorrenteInternaPaulista=weValorTotalContaCorrenteInternaPaulista.getText();
	    	if(stringValorTotalContaCorrenteInternaPaulista.length()>0)
	    	{
	    		valorTotalContaCorrenteInternaPaulista = Double.parseDouble(stringValorTotalContaCorrenteInternaPaulista.replace("R$", "").replace(".","").replace(",", ".").trim());
	    	}
	    	
	    	WebElement weNumeroBancoContaCorrenteInternaPaulista = driver.findElement(By.id("numeroBancoContaCorrenteInternaPaulista"));
	    	numeroBancoContaCorrenteInternaPaulista=weNumeroBancoContaCorrenteInternaPaulista.getAttribute("value");
	    	
	    	WebElement weNumeroAgenciaContaCorrenteInternaPaulista = driver.findElement(By.id("numeroAgenciaContaCorrenteInternaPaulista"));
	    	numeroAgenciaContaCorrenteInternaPaulista=weNumeroAgenciaContaCorrenteInternaPaulista.getAttribute("value");	    	
	    	
	    	WebElement weNumeroContaCorrenteInternaPaulista = driver.findElement(By.id("numeroContaCorrenteInternaPaulista"));
	    	numeroContaCorrenteInternaPaulista = weNumeroContaCorrenteInternaPaulista.getAttribute("value");
	    	if(valorTotalAquisicaoComAdiantamento!=0)
	    	{
		    	System.out.println("\nOperação Conta Interna Paulista");	    	
		    	System.out.println("Remessa: " + valorTotalAquisicaoComAdiantamento);
		    	System.out.println("Recompra: " + valorRecompraComAdiantamento);
		    	System.out.println("Reembolso de Despesas: " + reembolsoContaEspecial);
		    	System.out.println("Valor Total: " + valorTotalComAdiantamento);
		    	System.out.println("\nConta Paulista: " + contaCorrenteInternaPaulista);
		    	System.out.println("Valor: " + valorTotalContaCorrenteInternaPaulista);
		    	System.out.println("Banco: " + numeroBancoContaCorrenteInternaPaulista);
		    	System.out.println("Agencia Sem Digito: " + numeroAgenciaContaCorrenteInternaPaulista);
		    	System.out.println("Conta Corrente: " + numeroContaCorrenteInternaPaulista);
	    	}
	    	
	    	if(driver.findElement(By.id("valorRemessaSemAdiantamento")).isDisplayed())
	    	{
	    		System.out.println("--Remessa sem adiantamento is displayed--");
	    	}
	    	
	    	WebElement weValorRemessaSemAdiantamento = driver.findElement(By.id("valorRemessaSemAdiantamento"));
	    	String stringValorRemessaSemAdiantamento = weValorRemessaSemAdiantamento.getAttribute("value");

	    	if(stringValorRemessaSemAdiantamento.length()>0)
	    	{
		    	System.out.println("Remessa: " + stringValorRemessaSemAdiantamento);
	    		valorRemessaSemAdiantamento=Double.parseDouble(stringValorRemessaSemAdiantamento    																
	    															.replace("-R$ ", "-")
	    															.replace("R$", "")
	    															.replace(".","")
	    															.replace(",", ".").trim()
	    														);
	    	}
	    	
	    	if(driver.findElement(By.id("valorRecompraSemAdiantamento")).isDisplayed())
	    	{
	    		System.out.println("--Recompra sem adiantamento is displayed--");
	    	}
	    	
	    	WebElement weValorRecompraSemAdiantamento = driver.findElement(By.id("valorRecompraSemAdiantamento"));
	    	String stringValorRecompraSemAdiantamento = weValorRecompraSemAdiantamento.getAttribute("value");

	    	if(stringValorRecompraSemAdiantamento.length()>0)
	    	{
		    	System.out.println("Recompra: " + stringValorRecompraSemAdiantamento);
	    		valorRecompraSemAdiantamento = Double.parseDouble(stringValorRecompraSemAdiantamento.replace("R$", "").replace(".","").replace(",", ".").trim());
	    	}

	    	if(driver.findElement(By.id("reembolsoContaNormal")).isDisplayed())
	    	{
	    		System.out.println("--Reembolso sem adiantamento is displayed--");
	    	}
	    	
	    	WebElement weReembolsoContaNormal = driver.findElement(By.id("reembolsoContaNormal"));
	    	String stringReembolsoContaNormal = weReembolsoContaNormal.getAttribute("value");
	    	if(stringReembolsoContaNormal.length()>0)
	    	{
	    		System.out.println("Reembolso: " + stringReembolsoContaNormal);
	    		reembolsoContaNormal = Double.parseDouble(stringReembolsoContaNormal.replace("R$", "").replace(".","").replace(",", ".").trim());
	    	}

	    	if(driver.findElement(By.id("valorTotalSemAdiantamento")).isDisplayed())
	    	{
	    		System.out.println("--Valor total sem adiantamento is displayed--");
	    	}
	    		    	
	    	WebElement weValorTotalSemAdiantamento = driver.findElement(By.id("valorTotalSemAdiantamento"));
	    	String stringValorTotalSemAdiantamento = weValorTotalSemAdiantamento.getAttribute("value");
	    	if(stringValorTotalSemAdiantamento.length()!=0)
	    	{
	    		System.out.println("Valor total sem adiantamento: " + stringValorTotalSemAdiantamento);
	    		valorTotalSemAdiantamento=Double.parseDouble(stringValorTotalSemAdiantamento.replace("-R$ ", "-").replace("R$", "").replace(".","").replace(",", ".").trim());
	    	}
	    	
	    	
	    	System.out.println("\nOperação Conta Externa Paulista");
	    	System.out.println("Remessa: " + valorRemessaSemAdiantamento);
	    	System.out.println("Recompra: " + valorRecompraSemAdiantamento);
	    	System.out.println("Reembolso de Despesas: " + reembolsoContaNormal);
	    	System.out.println("Valor Total: " + valorTotalSemAdiantamento);
    	
	    	if(driver.findElement(By.id("comboCedenteSemAdiantamento1")).isDisplayed())
	    	{
	    		System.out.println("--Conta sem Adiantamento 1 is displayed--");
	    	}
	    	
	    	WebElement comboContaCedenteSemAdiantamento1 = driver.findElement(By.id("comboCedenteSemAdiantamento1"));
	    	contaCedenteSemAdiantamento1 = comboContaCedenteSemAdiantamento1.getAttribute("value");
	    	System.out.println("Conta desc: " + contaCedenteSemAdiantamento1);
	    	
	    	WebElement weValorTotalCedenteSemAdiantamento1 = driver.findElement(By.id("valorTotalCedenteSemAdiantamento1"));
	    	String stringValorTotalCedenteSemAdiantamento1 = weValorTotalCedenteSemAdiantamento1.getAttribute("value");
	    	if(stringValorTotalCedenteSemAdiantamento1.length()!=0)
	    	{
	    		valorTotalCedenteSemAdiantamento1=Double.parseDouble(stringValorTotalCedenteSemAdiantamento1.replace("-R$ ", "-").replace("R$", "").replace(".","").replace(",", ".").trim());
	    	}
	    			    	
	    	WebElement weNumeroBancoContaCedenteSemAdiantamento1 = driver.findElement(By.id("numeroBancoContaCedenteSemAdiantamento1"));
	    	numeroBancoContaCedenteSemAdiantamento1=weNumeroBancoContaCedenteSemAdiantamento1.getAttribute("value");
	    	WebElement weNumeroAgenciaContaCedenteSemAdiantamento1 = driver.findElement(By.id("numeroAgenciaContaCedenteSemAdiantamento1"));
	    	numeroAgenciaContaCedenteSemAdiantamento1=weNumeroAgenciaContaCedenteSemAdiantamento1.getAttribute("value");

	    	WebElement weNumeroContaCorrenteCedenteSemAdiantamento1 = driver.findElement(By.id("numeroContaCorrenteContaCedenteSemAdiantamento1"));
	    	numeroContaCorrenteContaCedenteSemAdiantamento1=weNumeroContaCorrenteCedenteSemAdiantamento1.getAttribute("value");
	    	
	    	if(valorTotalCedenteSemAdiantamento1!=0)
	    	{
		    	System.out.println("\nConta 1 Cedente: " + contaCedenteSemAdiantamento1);
		    	System.out.println("Valor: " + valorTotalCedenteSemAdiantamento1);
		    	System.out.println("Banco: " + numeroBancoContaCedenteSemAdiantamento1);
		    	System.out.println("Agencia sem Digito: " + numeroAgenciaContaCedenteSemAdiantamento1);
		    	System.out.println("Conta Corrente: " + numeroContaCorrenteContaCedenteSemAdiantamento1);
	    	}
	    	
	    	WebElement comboContaCedenteSemAdiantamento2 = driver.findElement(By.id("comboCedenteSemAdiantamento2"));
	    	contaCedenteSemAdiantamento2 = comboContaCedenteSemAdiantamento2.getAttribute("value");
	    	
	    	WebElement weValorTotalCedenteSemAdiantamento2 = driver.findElement(By.id("valorTotalCedenteSemAdiantamento2"));
	    	String stringValorTotalCedenteSemAdiantamento2 = weValorTotalCedenteSemAdiantamento2.getAttribute("value");
	    	if(stringValorTotalCedenteSemAdiantamento2.length()>0)
	    	{
	    		valorTotalCedenteSemAdiantamento2=Double.parseDouble(stringValorTotalCedenteSemAdiantamento2.replace("-R$ ", "-").replace("R$", "").replace(".","").replace(",", ".").trim());
	    	}
	    			    	
	    	WebElement weNumeroBancoContaCedenteSemAdiantamento2 = driver.findElement(By.id("numeroBancoContaCedenteSemAdiantamento2"));
	    	numeroBancoContaCedenteSemAdiantamento2=weNumeroBancoContaCedenteSemAdiantamento2.getAttribute("value");

	    	WebElement weNumeroAgenciaContaCedenteSemAdiantamento2 = driver.findElement(By.id("numeroAgenciaContaCedenteSemAdiantamento2"));
	    	numeroAgenciaContaCedenteSemAdiantamento2=weNumeroAgenciaContaCedenteSemAdiantamento2.getAttribute("value");

	    	WebElement weNumeroContaCorrenteCedenteSemAdiantamento2 = driver.findElement(By.id("numeroContaCorrenteContaCedenteSemAdiantamento2"));
	    	numeroContaCorrenteContaCedenteSemAdiantamento2=weNumeroContaCorrenteCedenteSemAdiantamento2.getAttribute("value");
	    	
	    	if(valorTotalCedenteSemAdiantamento2!=0)
	    	{
		    	System.out.println("\nConta 2 Cedente: " + contaCedenteSemAdiantamento2);
		    	System.out.println("Valor: " + valorTotalCedenteSemAdiantamento2);
		    	System.out.println("Banco: " + numeroBancoContaCedenteSemAdiantamento2);
		    	System.out.println("Agencia sem Digito: " + numeroAgenciaContaCedenteSemAdiantamento2);
		    	System.out.println("Conta Corrente: " + numeroContaCorrenteContaCedenteSemAdiantamento2);
	    	}		    	
    	
	    	WebElement weCertificadora = driver.findElement(By.id("comboCertificadoras"));
	    	certificadora = weCertificadora.getText();
	    	System.out.println("\nCertificadora: " + certificadora);
	    	
	    	operacao.setDataDeImportacao(dataDeImportacao);
	    	operacao.setValorTotalAquisicaoComAdiantamento(valorTotalAquisicaoComAdiantamento);
	    	operacao.setValorRecompraComAdiantamento(valorRecompraComAdiantamento);
	    	operacao.setReembolsoContaEspecial(reembolsoContaEspecial);
	    	operacao.setValorTotalComAdiantamento(valorTotalComAdiantamento);
	    	operacao.setContaCorrenteInternaPaulista(contaCorrenteInternaPaulista);
	    	operacao.setValorTotalContaCorrenteInternaPaulista(valorTotalContaCorrenteInternaPaulista);
	    	
	    	Conta contaInterna = new Conta();
	    	if(!numeroBancoContaCorrenteInternaPaulista.trim().equals(""))
	    	{
	    		System.out.println("Setting internal account");
	    		System.out.println("Banco: " + numeroBancoContaCorrenteInternaPaulista);
	    		System.out.println("Agencia: " + numeroAgenciaContaCorrenteInternaPaulista);
	    		System.out.println("Conta: " + numeroContaCorrenteInternaPaulista);
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
	    	if(!numeroBancoContaCedenteSemAdiantamento1.trim().equals(""))
	    	{
	    		System.out.println("Setting account 1");
	    		System.out.println("Banco: " + numeroBancoContaCedenteSemAdiantamento1);
	    		System.out.println("Agencia: " + numeroAgenciaContaCedenteSemAdiantamento1);
	    		System.out.println("Conta: " + numeroContaCorrenteContaCedenteSemAdiantamento1);
	    		contaExterna1 = new Conta(operacao.getCedente(), numeroBancoContaCedenteSemAdiantamento1, numeroAgenciaContaCedenteSemAdiantamento1, numeroContaCorrenteContaCedenteSemAdiantamento1, conn);    		
	    		operacao.setContaExterna1(contaExterna1);
	    	}
	
	    	operacao.setContaCedenteSemAdiantamento2(contaCedenteSemAdiantamento2);
	    	operacao.setValorTotalCedenteSemAdiantamento2(valorTotalCedenteSemAdiantamento2);
	    	
	    	Conta contaExterna2 = new Conta();
	    	if(!numeroBancoContaCedenteSemAdiantamento2.trim().equals(""))
	    	{   
	    		System.out.println("Setting account 2");
	    		System.out.println("Banco: " + numeroBancoContaCedenteSemAdiantamento2);
	    		System.out.println("Agencia: " + numeroAgenciaContaCedenteSemAdiantamento2);
	    		System.out.println("Conta: " + numeroContaCorrenteContaCedenteSemAdiantamento2);
	    		contaExterna2 = new Conta(operacao.getCedente(), numeroBancoContaCedenteSemAdiantamento2, numeroAgenciaContaCedenteSemAdiantamento2, numeroContaCorrenteContaCedenteSemAdiantamento2, conn);    		    		
	    		operacao.setContaExterna2(contaExterna2);
	    	}
	    	
	    	operacao.setCertificadora(certificadora);
		}
		else
		{
			System.out.println("--Não foi possivel ler o nome do fundo nem os outros detalhes--");
		}
    	this.setJustReadedTransferDetails(true);
	}

	public Operacao readOperationBasic(List<WebElement> rowOperation)
	{
		String nomeFundo="";
		String nomeArquivo="";
		String nomeCedente="";
		double valorDeTransferencia=0.0;
		double valorTEDCedente=0.0;
		Date registerTime=Calendar.getInstance().getTime();
		ArrayList<Status> statuses=new ArrayList<Status>();
		double taxaDeCessao=0;
		Date dataDeEntrada=Calendar.getInstance().getTime();
		
        WebElement weFundo = rowOperation.get(0); 
        nomeFundo = weFundo.getAttribute("title");
        WebElement weNomeArquivo = rowOperation.get(1);
        nomeArquivo = weNomeArquivo.getText();
        WebElement weCedente = rowOperation.get(2);
        nomeCedente = weCedente.getText();
        WebElement weValorDeTransferencia = rowOperation.get(3);
//        System.out.println("ValorDeTransferencia: " + weValorDeTransferencia.getText());
        valorDeTransferencia = Double.parseDouble(weValorDeTransferencia.getText().replace("R$", "").replace(".","").replace(",", ".").trim());

        WebElement weValorTEDCedente = rowOperation.get(4);
        valorTEDCedente = Double.parseDouble(weValorTEDCedente.getText().replace("R$", "").replace(".","").replace(",", ".").trim());
        WebElement weSituacao = rowOperation.get(5);
        Operacao currentOperation = new Operacao();
    	currentOperation.setFundo(new FundoDeInvestimento(nomeFundo, OperadorPortalPaulista.conn)); //Missing Fundo Constructor
    	currentOperation.setNomeArquivo(nomeArquivo);
    	//System.out.println("BeforeCedente");
    	currentOperation.setCedente(new Entidade(nomeCedente, OperadorPortalPaulista.conn));
    	//System.out.println("AfterCedente");
    	currentOperation.setValorDeTransferencia(valorDeTransferencia);
    	currentOperation.setValorTEDCedente(valorTEDCedente);
    	currentOperation.setRegisterTime(registerTime);
    	currentOperation.setStatuses(statuses); 	    
    	currentOperation.setTaxaDeCessao(taxaDeCessao);
    	currentOperation.setDataDeEntrada(dataDeEntrada);
    	
    	System.out.println("\nBASIC READING");
    	System.out.println("Fundo: " + currentOperation.getFundo().getNomeCurto());
    	System.out.println("Cedente: " + currentOperation.getCedente().getNome());
    	System.out.println("Arquivo: " + currentOperation.getNomeArquivo());
    	
        statuses.add(new Status(weSituacao.getText(),Calendar.getInstance().getTime()));
    	return currentOperation; 
	}	
	

    public void checkStatus() 
    {
    	Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	
    	System.out.println("--------------------");
    	System.out.println("Check status at\t" + sdf.format(cal.getTime()) );
    	System.out.println("--------------------");
    	
    	

        driver.navigate().to("http://portalfidc.bancopaulista.com.br/portal/financeiro/liquidacao");
        
        HandlerOperacoes.timeAndWait(5);
        WebElement xField = driver.findElement(By.className("search-choice-close"));
    	xField.click();

    	HandlerOperacoes.timeAndWait(3);
        WebElement buttonPesquisar = driver.findElement(By.id("pesquisa"));
    	buttonPesquisar.click();
    	
    	File file=null;
    	String fileName = 	"reportOperations.txt";
		file = new File("conf/"+fileName);
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
				String[] field = line.split("\t");
//				int iOp=Integer.parseInt(field[0]);
				String fundName = field[1];
				String fileOpName = field[2];
				String nomeCedente = field[3];
				Double tedCedente = Double.parseDouble(field[4].replaceAll(" ", "").replace("R$", "").replace(".", "").replace(",", "."));
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
				if(!field[9].equals("0"))
				{
					lifeTime = (long) ( 
										Integer.parseInt(field[9].substring(0,2))*60*60+
										Integer.parseInt(field[9].substring(3,5))*60+
										Integer.parseInt(field[9].substring(6,8))
										)*1000;
				}
				Operacao op = new Operacao();
				op.setFundo(new FundoDeInvestimento(fundName, OperadorPortalPaulista.conn));
				op.setNomeArquivo(fileOpName);
				op.setCedente(new Entidade(nomeCedente,conn));				
				op.setValorTEDCedente(tedCedente);
				op.setRegisterTime(registerDate);
				op.getStatuses().add(new Status());
				op.getStatuses().get(0).setDescription(situacao);
				op.getStatuses().get(0).setBeginDate(statusDate);
				op.getStatuses().get(0).setEndDate(checkDate);
				op.getStatuses().get(0).setLifeTime(lifeTime);
				
				System.out.println("Adding:\n" + line);
				this.operations.add(op);
			}
		}
		HandlerOperacoes.timeAndWait(5);
        List<WebElement> listRows = driver.findElements(By.className("rowLiquidacao"));
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
//		    			System.out.println("Operação " + iOp);	    			
	    			String nomeFundo = row.getAttribute("title")
	    								.replace("...", "")
	    								.replace(" - ", "");
//		    			System.out.println("Fundo: " + fundo);
	    			tempOp.setFundo(new FundoDeInvestimento(nomeFundo,OperadorPortalPaulista.conn));
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
	    										.replace("R$ ", "")
	    										.replace(".", "")
	    										.replace(",", ".");
//		    			System.out.println("Valor TED Originador: R$ " + df.format(Double.parseDouble(valorTEDOriginador)));
	    			tempOp.setValorTEDOriginador(Double.parseDouble(valorTEDOriginador));
	    			fieldCounter++;
	    		}
	    		else if(fieldCounter==4)
	    		{
	    			String valorTEDCedente = row.getText()
	    										.replace("R$ ", "")
	    										.replace(".", "")
	    										.replace(",", ".");
//		    			System.out.println("Valor TED Cedente: R$ " + df.format(Double.parseDouble(valorTEDCedente)));
	    			tempOp.setValorTEDCedente(Double.parseDouble(valorTEDCedente));
	    			fieldCounter++;
	    		}
	    		else if(fieldCounter==5)
	    		{
	    			String situacaoDescription = row.getText();
//		    			System.out.println("Situação: " + situacao);
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
	        currentTime = cal.getTime();
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
        	if(isErased && !op.getStatuses().get(op.getStatuses().size()-1).getDescription().equals("Cancelado"))        		
        	{
        		Status statusCancelado = new Status("Cancelado",Calendar.getInstance().getTime(), conn);
        		op.getStatuses().add(statusCancelado);
        	}
        	else if(isErased && op.getStatuses().get(op.getStatuses().size()-1).getDescription().equals("Cancelado"))
        	{
        		op.getStatuses().get(op.getStatuses().size()-1).setEndDate(Calendar.getInstance().getTime());
        	}
        }

    	System.out.println("--------------------");
		System.out.println("Hora Limite: " + sdf.format(limitTime) + " " + limitTime);
		System.out.println("Hora Atual: " + sdf.format(currentTime) + " " + currentTime);
		System.out.println("--------------------");
    	String content="";
        if(this.operations.size()>0)	        	
        {     	
        	System.out.println("Registered " + this.operations.size() + " operations");
        	int iOp=0;
        	System.out.println("Nro"+ " " 
					+ "\tFundo       " 
					+ "\t NomeDeArquivo      " 
					+ "\t Cedente     "
//						+ "\tR$ " + df.format(op.getValorTEDOriginador())
					+ "\tValorTEDCed"
					+ "\tSituacao                            "
					+ "\tRegistro    "
					+ "\tStatus      "
					+ "\tVerificação "
					);
        	double totalValorCedente=0;
        	for(Operacao op:this.operations)
        	{
        		iOp++;
        		String stringValorTEDCedente = "";
        		if(op.getValorTEDCedente()==0.0)
        		{
        			stringValorTEDCedente = df.format(op.getValorTEDCedente()) + "    ";
        		}
        		else
        		{
        			stringValorTEDCedente = df.format(op.getValorTEDCedente());
        		}
        		String stringSituacao = "";
        		Status situacao = op.getStatuses().get(op.getStatuses().size()-1);
        		
        		if(situacao.getDescription().toLowerCase().contains("reprovado")
        				|| situacao.getDescription().toLowerCase().contains("digital")
        				)
        		{
        			stringSituacao = situacao.getDescription() + "          ";
        		}
        		else
        		{
        			stringSituacao = situacao.getDescription();
        		}
        		
        		System.out.println(iOp
        						+ "\t" + op.getFundo().getNome().substring(0,12) 
        						+ "\t" + op.getNomeArquivo()
        						+ "\t" + op.getCedente().getNome().substring(0,15)
//	        						+ "\tR$ " + df.format(op.getValorTEDOriginador())
        						+ "\tR$ " + stringValorTEDCedente
        						+ "\t" + stringSituacao
        						+ "\t" + sdf.format(op.getRegisterTime())
        						+ "\t" + sdf.format(situacao.getBeginDate())
        						+ "\t" + sdf.format(situacao.getEndDate())
        						+ "\t" + op.getStatuses().get(op.getStatuses().size()-1).getLifeTimeString()
        						);
        		content = content + iOp
						+ "\t" + op.getFundo().getNome() 
						+ "\t" + op.getNomeArquivo()
						+ "\t" + op.getCedente()
//    						+ "\tR$ " + df.format(op.getValorTEDOriginador())
						+ "\tR$ " + stringValorTEDCedente
						+ "\t" + stringSituacao
						+ "\t" + sdf.format(op.getRegisterTime())
						+ "\t" + sdf.format(situacao.getBeginDate())
						+ "\t" + sdf.format(situacao.getEndDate())
						+ "\t" + op.getStatuses().get(op.getStatuses().size()-1).getLifeTimeString()
						+ "\n";
        		
        		totalValorCedente=totalValorCedente+op.getValorTEDCedente();
        	}
        	System.out.println("   "+ " " 
					+ "\t            " 
					+ "\t                    " 
					+ "\tTOTAL       "
//						+ "\tR$ " + df.format(op.getValorTEDOriginador())
					+ "\tR$ " + df.format(totalValorCedente)
					);        	
        }
        else
        {
        	System.out.println("No Registered operations");
        }
    	
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file, "ISO_8859_1");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		pw.write(content);
		pw.flush();
		pw.close();        
    }
    
    public void saveStatus(Operacao op)
    {       	
    	System.out.println("-------------------------");
    	System.out.println("Saving Status");
    	System.out.println("-------------------------");
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulista.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for(Status status:op.getStatuses())
		{
			int idDescricaoStatus = 0;
			String query = "SELECT idDescricaoStatus FROM descricaostatus WHERE descricao=\"" + status.getDescription() + "\"";
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
					idDescricaoStatus = rs.getInt("idDescricaoStatus");				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(idDescricaoStatus==0)
			{
				String sql = "INSERT INTO `mvcapital`.`descricaostatus` (`descricao`) VALUES("
						+ "\"" + status.getDescription() + "\"" 
						+ ")";
		//		System.out.println(sql);
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		
				query = "SELECT idDescricaoStatus FROM descricaostatus WHERE descricao= \"" + status.getDescription() + "\"";
		//		System.out.println(query);
				rs=null;
				try {
					rs = stmt.executeQuery(query);
					while (rs.next())
					{				
						idDescricaoStatus = rs.getInt("idDescricaoStatus");
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}						
			}

			query = "SELECT idStatusOperacao FROM statusoperacao WHERE idOperacao= " + op.getIdOperacao()
					+ " AND idDescricaoStatus = " + idDescricaoStatus;			
			System.out.println(query);
			int idStatusOperacao=0;
			rs=null;
			try {
				rs = stmt.executeQuery(query);
				while (rs.next())
				{				
					idStatusOperacao = rs.getInt("idStatusOperacao");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}						
			
			if(idStatusOperacao!=0)
			{
				System.out.println("Status already registered!");
				String sql = "UPDATE `mvcapital`.`statusoperacao` SET `fim` = " + "'" + sdtfd.format(status.getEndDate())  + "'" + " WHERE `idStatusOperacao` = " + idStatusOperacao;
				System.out.println(sql);
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("New status for this operation!");
				String sql = "INSERT INTO `mvcapital`.`statusoperacao` (`idOperacao`,`inicio`,`fim`,`idDescricaoStatus`) VALUES ("
		    				+ op.getIdOperacao()
		    				+ ",'" + sdtfd.format(status.getBeginDate()) + "'"
		    				+ ",'" + sdtfd.format(status.getEndDate()) + "'"
		    				+ "," + idDescricaoStatus
		    				+ ")";
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

    		System.out.println("Waiting " +  (n-i) + " seconds " + sdf.format(cal.getTime()));
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
    	BlockTitulos blockTitulos = new BlockTitulos();
    	System.out.println("------------------------------------");
    	System.out.println("Building blocks");
    	System.out.println("------------------------------------");
    	for(String line:lines)
    	{
    		System.out.println(line);
    	}
    	for(String line:lines)
    	{
    		//System.out.println(line);
    		String[] fields = line.split("\t");
    		if(line.startsWith("fundo"))
    		{
    			blockIdentificacao.setFundo(fields[1]);
    		}
    		else if(line.startsWith("cedente"))
    		{
    			blockIdentificacao.setCedente(fields[1]);
    		}
    		else if(line.startsWith("taxaDeCessao"))
    		{
    			String stringTaxaDeCessao = fields[1].replace("%", "").replace(".", "").replace(",", ".").trim();
    			blockIdentificacao.setTaxaDeCessao(Double.parseDouble(stringTaxaDeCessao));
    		}
    		else if(line.startsWith("arquivo"))
    		{
    			blockIdentificacao.setNomeArquivo(fields[1]);
    		}
    		else if(line.startsWith("dataDeEntrada"))
    		{
    			try {
					blockIdentificacao.setDataDeEntrada(sdfr.parse(fields[1]));
				} catch (ParseException e) {
					e.printStackTrace();
				}
    		}
    		else if(line.startsWith("totalAquisicao"))
    		{
    			String stringTotalAquisicao = fields[1].replace("R$", "").replace("%", "").replace(".", "").replace(",", ".").trim();
    			blockIdentificacao.setTotalAquisicao(Double.parseDouble(stringTotalAquisicao));
    		}
    		else if(line.startsWith("totalNominal"))
    		{
    			String stringTotalNominal = fields[1].replace("R$", "").replace("%", "").replace(".", "").replace(",", ".").trim();
    			blockIdentificacao.setTotalNominal(Double.parseDouble(stringTotalNominal));
    		}
    		else if(line.startsWith("titulo"))
    		{
    			String[] fieldsTitulo = line.split("\t");
    			String tipoDeRecebivel = fieldsTitulo[1];    			
    			String seuNumero = fieldsTitulo[2];
    			String numeroDoDocumento = fieldsTitulo[3];
    			double valorDeAquisicao = Double.parseDouble(fieldsTitulo[4].replace(".","").replace(",", "."));
    			double valorNominal = Double.parseDouble(fieldsTitulo[5].replace(".","").replace(",", "."));
    			int prazo = Integer.parseInt(fieldsTitulo[6]);
//    			double taxaDeJuros = Double.parseDouble(fieldsTitulo[7].replace(".", "").replace(",","."));    			    		
    			String nomeSacado = fieldsTitulo[8];
    			String nomeCedente = blockIdentificacao.getCedente();
    			String nomeFundo = blockIdentificacao.getFundo();
    			
    			Titulo titulo = new Titulo(conn,
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
    			System.out.println("Title created");
//    			titulo.show();
    			
    			blockTitulos.getTitulos().add(titulo);
    			boolean existTipoDeDireitoCreditorio=false;
    			if(blockTitulos.getTiposTitulo().size()>0)
    			{
	    			for(TipoTitulo tipoDeDireitoCreditorio:blockTitulos.getTiposTitulo())
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
    				blockTitulos.getTiposTitulo().add(new TipoTitulo(tipoDeRecebivel, conn));
    			}
    		}
    	}
    	blockIdentificacao.show();
    	blockTitulos.show();
    	System.out.println("------------------------------------");
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
				System.out.println("Reading file " + file.toPath());
				
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
			System.out.println(file.getName() + " does not exist!");
			return null;
		}
		
		boolean existBeginBlock=false;
		String tipoDeRecebivel="";

		String lineBefore = "";
		String lineAfter = "";
		int iLines=0;
		for(iLines=0;iLines<linesFile.size();iLines++) 
		{	
			String lineFile = linesFile.get(iLines).replace(";", "").replace(" &amp", ""); 
			System.out.println(lineFile);
			
			if(iLines>0)
			{
				lineBefore = linesFile.get(iLines-1).replace(";", "").replace(" &amp", "");
			}
			if(iLines<linesFile.size()-2)
			{
				lineAfter = linesFile.get(iLines+1).replace(";", "").replace(" &amp", "");
			}			
			
			if(lineFile.length()>3)
			{				
//				lines.add(lineFile);
				
				String line = lineFile;
				
				if(!existBeginBlock)
				{
//					System.out.println(line);
					if(line.contains("Fundo:"))
					{
						String[] fields = line.split(":");
						String fundo = fields[1].trim();
//						System.out.println("Fundo: " + fundo);
						lines.add("fundo\t" + fundo);
					}
					else if(line.contains("Cedente:"))
					{
						String[] fields = line.split(":");
						String cedente = fields[1].trim().split("Taxa")[0].trim();
						String taxaDeCessao = fields[2].trim();
//						System.out.println("Cedente: " + cedente);			
//						System.out.println("TaxaDeCessao: " + taxaDeCessao);
						lines.add("cedente\t" + cedente);
						lines.add("taxaDeCessao\t" + taxaDeCessao);
					}
					else if(line.contains("Arquivo:"))
					{
						String[] fields = line.split(":");
						String arquivo = fields[1].trim().split("Data")[0].trim();
						String dataDeEntrada = fields[2].trim();
//						System.out.println("Arquivo: " + arquivo);
//						System.out.println("DataDeEntrada: " + dataDeEntrada);
						lines.add("arquivo\t" + arquivo);
						lines.add("dataDeEntrada\t" + dataDeEntrada);
					}
					else if(line.contains("Total Nominal:"))
					{
						String[] fields = line.split(":");
						String totalAquisicao = fields[1].trim().split("Total")[0].trim();
						String totalNominal = fields[2].trim();
//						System.out.println("TotalAquisição: " + totalAquisicao);
//						System.out.println("TotalNominal: " + totalNominal);
						lines.add("totalAquisicao\t" + totalAquisicao);
						lines.add("totalNominal\t" + totalNominal);
					}
					if(line.contains("Tipo de "))
					{
						String[] fields = line.split(":");
						tipoDeRecebivel = fields[1].trim();
						System.out.println(tipoDeRecebivel);
						existBeginBlock=true;
						lines.add("tipoDeRecebivel\t" +tipoDeRecebivel);
					}
				}
				else if(existBeginBlock)
				{
					
					System.out.println(line);				
					if(line.contains("Tipo de "))
					{
						String[] fields = line.split(":");
						if(!tipoDeRecebivel.equals(fields[1].trim()))
						{
							tipoDeRecebivel = fields[1].trim();
							System.out.println(tipoDeRecebivel);
							existBeginBlock=true;
							lines.add("tipoDeRecebivel\t" + tipoDeRecebivel);
						}					
					}
					else
					{
						if(!line.contains("Prazo")&&!line.contains("Cedente")&&!line.contains("Fundo:"))
						{
							String lineComplete=line;							
							if(!line.contains("%"))								
							{
								System.out.println("Inside - No have %");
								String afterPrazo = "";
								if(		   lineBefore.trim().length()>0 
										&& lineBefore.trim().length() < 104 
										&& lineAfter.trim().length() >0 
										&& lineAfter.trim().length() < 140 
										&& lineAfter.trim().contains("%")
									)
								{									
									System.out.println("\tInside - All true");
									String[] fieldsBefore=lineBefore.trim().split(" ");
									String[] fieldsAfter=lineAfter.trim().split("%");
									
									if(fieldsBefore[0].contains(".") && fieldsBefore[0].matches(".*\\d.*"))
									{
										System.out.println("Contains number");
										String stringJuros="";
										System.out.println("field0Before: " + fieldsBefore[0]);
										if(fieldsAfter.length>0)
										{
											System.out.println("field0After: " + fieldsAfter[0]);
											if(fieldsAfter[0].length()!=0)
											{																				
												stringJuros = (fieldsBefore[0]+fieldsAfter[0]).trim()+" %";
											}							
											else
											{
												stringJuros = fieldsBefore[0]+" %";
											}
										}
										else
										{
											System.out.println("Only one line for juros");
											stringJuros = fieldsBefore[0].trim()+" %";
										}
										
										System.out.println("Passed juros: " + stringJuros);

										String stringSacado="";
										
										if(fieldsAfter.length>1)
										{
											System.out.println("LineAfter: " + lineAfter);
											if(fieldsAfter[1].length()>0)
											{
												stringSacado = lineBefore.trim().substring(fieldsBefore[0].length()).trim()
																  + lineAfter.trim().substring(fieldsAfter[0].length()).trim().replace("%", "");
											}											
										}
										else
										{											
											stringSacado = lineBefore.trim().substring(fieldsBefore[0].length()).trim();
										}
										System.out.println("Passed sacado: " + stringSacado);
										afterPrazo = stringJuros + " " + stringSacado;
										lineComplete = line + " " + afterPrazo;
										System.out.println("AT: " + lineComplete);										
									}
									else
									{
										System.out.println("\t\tHave no number");
										continue;
										
									}
								}
								else
								{
									System.out.println("\tInside - Else - skip");
									continue;
								}
							}
							else if(line.contains("%") && line.trim().length() > 100) 
							{
								System.out.println("Inside - Have % and lenght > 100");
								if(line.charAt(line.length()-1)=='%')
								{
									lineComplete = line + "     " + lineBefore.trim() + " " + lineAfter.trim();
								}
								System.out.println("LC%: "+ lineComplete);
							}
							else 
							{
								System.out.println("Inside - Have % and lenght <= 100  - skip");
								continue;
							}
							
							
							
							if(lineComplete.trim().length() > 100)
							{
								System.out.println("LC: " + lineComplete);	
								String seuNumeroAndNumeroDoDocumento = lineComplete.split("R\\$ ")[0].trim();
	//								System.out.println("Mixed:" + seuNumeroAndNumeroDoDocumento);
								String seuNumero = "";
								String numeroDoDocumento = "";
								String[] fieldsNumero = seuNumeroAndNumeroDoDocumento.split(" ");
								for(int i=0; i<fieldsNumero.length;i++)
								{
									if(i==fieldsNumero.length-1)
									{
										if(numeroDoDocumento.length()!=0)
										{
											numeroDoDocumento=numeroDoDocumento+" " + fieldsNumero[i];
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
											seuNumero=seuNumero+fieldsNumero[i] + " ";
										}
									}
								}
								seuNumero=seuNumero.trim();
//									System.out.println("SeuNumero: " + seuNumero);
//									System.out.println("NumeroDocumento: " + numeroDoDocumento);
								String valorAquisicao = lineComplete.split("R\\$ ")[1].trim();
								String valorNominal = lineComplete.split("R\\$ ")[2].trim().split(" ")[0];
								String prazo = lineComplete.split("R\\$ ")[2].trim().replaceAll(" +", " ").split(" ")[1];
								String taxaDeJuros = lineComplete.split("R\\$ ")[2].trim().replaceAll(" +", " ").split(" ")[2];
								String sacado = lineComplete.split("%")[1].trim();
	//								System.out.println(seuNumero +"\t"+numeroDoDocumento+"\t"+valorAquisicao+"\t"+valorNominal+"\t"+prazo+"\t"+taxaDeJuros+"\t"+sacado);
								
								String lineClean = "titulo"
													+ "\t" + tipoDeRecebivel
													+ "\t" + (seuNumero 
													+ "\t" + numeroDoDocumento
													+ "\t" + valorAquisicao 
													+ "\t" + valorNominal
													+ "\t" + prazo
													+ "\t" + taxaDeJuros
													+ "\t" + sacado).toUpperCase();
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
		System.out.println("Reading conf/automata.conf file");
		System.out.println("------------------");
		try 
		{
			reader = new BufferedReader(new FileReader("conf/operadorPortalFIDC.conf"));
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
					
					String[] fields = line.split(",");
					if (fields[0].contains("#"))
					{
						System.out.println("Comment Line:\t" + line);
					}
					else
					{
						System.out.println("Parameters Line:\t" + line);
						for (int i = 0; i<fields.length; i++)
						{
							switch (fields[0]) 
							{
					            case "server":  
					            	this.server = fields[1];
					                break;
					            case "port":
					            	this.port = Integer.parseInt(fields[1].replace(" ", ""));
					                break;				            	
					            case "userName":
					            	this.userName = fields[1];
					                break;
					            case "password":
					            	this.password = fields[1];
					                break;
					            case "dbName":
					            	this.dbName = fields[1];
					                break;
					            case "rootLocalWindows":
					            	OperatingSystem.setRootLocalWindows(fields[1]);
					            	break;
					            case "rootLocalLinux":
					            	OperatingSystem.setRootLocalLinux(fields[1]);
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
			System.out.println("MVCapital - Usuário de Portal");
			Statement stmt = (Statement) OperadorPortalPaulista.conn.createStatement();
			String query = "SELECT * FROM Conta WHERE idEntidadeProprietario=10";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idConta = rs.getInt("idConta");
				int idEntidadeProprietario = rs.getInt("idEntidadeProprietario");
				int idEntidadeServidor = rs.getInt("idEntidadeServidor");
				
				Entidade entidadeProprietario = new Entidade(idEntidadeProprietario, conn);
				Entidade entidadeServidor = new Entidade(idEntidadeServidor, conn);
				
				String numero=rs.getString("numero");
				String agencia=rs.getString("agencia");
				String usuario= rs.getString("usuario");
				String senha= rs.getString("senha");
				int idTipoDeConta=rs.getInt("idTipoDeConta");
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
	    WebDriverWait wait = new WebDriverWait(driver, 20);
//		driver = new ChromeDriver();
        driver.navigate().to("http://portalfidc.bancopaulista.com.br/portal/login");
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("j_username"))));

        // Find the text input element by its name
        System.out.println("--------------------");
    	System.out.println("User Login\t");
    	System.out.println("--------------------");
        WebElement elementUsername = driver.findElement(By.name("j_username"));
        elementUsername.sendKeys(managerAccount.getUsuario());

        WebElement elementPassword = driver.findElement(By.name("j_password"));
        elementPassword.sendKeys(managerAccount.getSenha());
        elementPassword.submit();

        limitTime=calLimit.getTime();
        calLimit.set(Calendar.HOUR_OF_DAY, 18);
        calLimit.set(Calendar.MINUTE, 50);
        calLimit.set(Calendar.SECOND, 00);
		limitTime=calLimit.getTime();				
		currentTime = cal.getTime();
	}		
	public void logout()
	{
		System.out.println("--------------------");
        System.out.println("Close the browser");
        driver.quit();
	}
    public static void playSound(String sound)
    {
    	String urlPathWindows="C:/Binarios/conf/";
    	String urlPathLinux="/home/moises/bin/conf/";
    	
    	String urlPath="";
    	if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			urlPath = urlPathWindows;
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux"))
		{
			urlPath = urlPathLinux;
		}
		try 
		{
			System.out.println("file://" + urlPath + sound);
			AudioClip clip = Applet.newAudioClip(new URL("file://" + urlPath + sound + ".wav"));
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
		OperadorPortalPaulista.sdf = sdf;
	}

	public static SimpleDateFormat getSdfd() {
		return sdfd;
	}

	public static void setSdfd(SimpleDateFormat sdfd) {
		OperadorPortalPaulista.sdfd = sdfd;
	}

	public ArrayList<Operacao> getOperations() {
		return operations;
	}

	public void setOperations(ArrayList<Operacao> operations) {
		this.operations = operations;
	}

	public static DecimalFormat getDf() {
		return df;
	}

	public static void setDf(DecimalFormat df) {
		OperadorPortalPaulista.df = df;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public Calendar getCal() {
		return cal;
	}

	public void setCal(Calendar cal) {
		this.cal = cal;
	}

	public Calendar getCalLimit() {
		return calLimit;
	}

	public void setCalLimit(Calendar calLimit) {
		this.calLimit = calLimit;
	}

	public Date getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public Conta getManagerAccount() {
		return managerAccount;
	}

	public void setManagerAccount(Conta managerAccount) {
		OperadorPortalPaulista.managerAccount = managerAccount;
	}

	public MySQLAccess getMysql() {
		return mysql;
	}

	public void setMysql(MySQLAccess mysql) {
		this.mysql = mysql;
	}

	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		OperadorPortalPaulista.conn = conn;
	}

	public WebDriverWait getWait() {
		return wait;
	}

	public void setWait(WebDriverWait wait) {
		this.wait = wait;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public SshClient getSshClient() {
		return sshClient;
	}

	public void setSshClient(SshClient sshClient) {
		this.sshClient = sshClient;
	}

	public static SimpleDateFormat getSdfr() {
		return sdfr;
	}

	public static void setSdfr(SimpleDateFormat sdfr) {
		OperadorPortalPaulista.sdfr = sdfr;
	}

	public UpdateConcentracao getUc() {
		return uc;
	}

	public void setUc(UpdateConcentracao uc) {
		this.uc = uc;
	}

	public ArrayList<Operacao> getBlackListOperations() {
		return blackListOperations;
	}

	public void setBlackListOperations(ArrayList<Operacao> blackListOperations) {
		this.blackListOperations = blackListOperations;
	}

	public String getRootDownloadsLinux() {
		return rootDownloadsLinux;
	}

	public void setRootDownloadsLinux(String rootDownloadsLinux) {
		this.rootDownloadsLinux = rootDownloadsLinux;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getClickX() {
		return clickX;
	}

	public void setClickX(int clickX) {
		this.clickX = clickX;
	}

	public int getClickY() {
		return clickY;
	}

	public void setClickY(int clickY) {
		this.clickY = clickY;
	}

	public int getScrollSize() {
		return scrollSize;
	}

	public void setScrollSize(int scrollSize) {
		this.scrollSize = scrollSize;
	}

	public boolean isJustApprouved() {
		return justApprouved;
	}

	public void setJustApprouved(boolean justApprouved) {
		this.justApprouved = justApprouved;
	}

	public boolean isJustReadedTransferDetails() {
		return justReadedTransferDetails;
	}

	public void setJustReadedTransferDetails(boolean justReadedTransferDetails) {
		this.justReadedTransferDetails = justReadedTransferDetails;
	}

	public String getRootDownloadsLocal() {
		return rootDownloadsLocal;
	}

	public void setRootDownloadsLocal(String rootDownloadsLocal) {
		this.rootDownloadsLocal = rootDownloadsLocal;
	}

	public String getRootReportsLocal() {
		return rootReportsLocal;
	}

	public void setRootReportsLocal(String rootReportsLocal) {
		this.rootReportsLocal = rootReportsLocal;
	}

	public String getRootReportsLocalLinux() {
		return rootReportsLocalLinux;
	}

	public void setRootReportsLocalLinux(String rootReportsLocalLinux) {
		this.rootReportsLocalLinux = rootReportsLocalLinux;
	}

	public String getRootReportsLinux() {
		return rootReportsLinux;
	}

	public void setRootReportsLinux(String rootReportsLinux) {
		this.rootReportsLinux = rootReportsLinux;
	}

	public static SimpleDateFormat getSdtfd() {
		return sdtfd;
	}

	public static void setSdtfd(SimpleDateFormat sdtfd) {
		OperadorPortalPaulista.sdtfd = sdtfd;
	}

	public static DecimalFormat getDfs() {
		return dfs;
	}

	public static void setDfs(DecimalFormat dfs) {
		OperadorPortalPaulista.dfs = dfs;
	}
}