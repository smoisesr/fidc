package mvcapital.qcertifica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class ConsultorQcertifica
{
	private static SimpleDateFormat sdfdt = new SimpleDateFormat(
			"yyyyMMdd_hhmmss"); //$NON-NLS-1$
	private static SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss"); //$NON-NLS-1$
	private static WebDriver driver = null;
	private String rootDownloadsLinux = ""; //$NON-NLS-1$
	private String hostname = ""; //$NON-NLS-1$
	private static String pathProcessar = "W:\\Fundos\\Operacao\\ChaveNFe\\Processar\\"; //$NON-NLS-1$
	private static String pathProcessado = "W:\\Fundos\\Operacao\\ChaveNFe\\Processado\\"; //$NON-NLS-1$
	private static String username=""; //$NON-NLS-1$
	private static String password=""; //$NON-NLS-1$

	public ConsultorQcertifica()
	{
		try
		{
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			this.hostname = addr.getHostName();
		} catch (UnknownHostException ex)
		{
			System.out.println("Hostname can not be resolved"); //$NON-NLS-1$
		}
		System.out.println("Hostname: " + this.hostname); //$NON-NLS-1$

		System.out.println("RootDownloadsLinux: " + this.rootDownloadsLinux); //$NON-NLS-1$
		readQCertificaConf();
	}

	public static void main(String[] args)
	{
		ConsultorQcertifica consultor = new ConsultorQcertifica();
		ConsultorQcertifica.openBrowser();
		ConsultorQcertifica.login();
		ConsultorQcertifica.processSeuNumero();
		ConsultorQcertifica.logout();
		return;
	}	
	
	public static void readQCertificaConf()
	{
		BufferedReader reader = null;
		System.out.println("Reading conf/qcertifica.conf file"); //$NON-NLS-1$
		System.out.println("------------------"); //$NON-NLS-1$
		try 
		{
			reader = new BufferedReader(new FileReader("conf/qcertifica.conf")); //$NON-NLS-1$
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
					            case "username": //$NON-NLS-1$
					            	ConsultorQcertifica.username = fields[1];
					                break;
					            case "password": //$NON-NLS-1$
					            	ConsultorQcertifica.password = fields[1];
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
	}	
	
	public static void chooseConsultoria(String codigoConsultoria)
	{
		WebElement inputCodigo = driver.findElement(By.id("ctl00_cphContext_tabManager_PV0_UC0_frvConsultationNFe_txbCdFidc")); //$NON-NLS-1$
		inputCodigo.clear();
		System.out.println("After cleaning Codigo"); //$NON-NLS-1$
		timeAndWait(2);
		inputCodigo = driver.findElement(By.id("ctl00_cphContext_tabManager_PV0_UC0_frvConsultationNFe_txbCdFidc"));		 //$NON-NLS-1$
		inputCodigo.click();
		System.out.println("After clicking Codigo"); //$NON-NLS-1$
		inputCodigo.sendKeys(codigoConsultoria);
		System.out.println("After putting Codigo"); //$NON-NLS-1$
		timeAndWait(2);
		WebElement searchCodigoButton = driver.findElement(By.id("btnLOVctl00_cphContext_tabManager_PV0_UC0_frvConsultationNFe_lovFidc")); //$NON-NLS-1$
		searchCodigoButton.click();
		System.out.println("After searching Codigo"); //$NON-NLS-1$
		timeAndWait(2);
	}
	
	@SuppressWarnings("resource")
	public static void processSeuNumero()
	{
		ArrayList<String> filesList = new ArrayList<String>();
		File[] filesArray = new File(ConsultorQcertifica.pathProcessar).listFiles();
		
		for (File file : filesArray) 
		{
		    if (file.isFile() && file.exists()) 
		    {
		        filesList.add(file.getAbsolutePath());
		        System.out.println(file.getName());
		        System.out.println(file.getAbsolutePath());
		    }
		}
		
		for(File file:filesArray)
		{
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
			String[] fields = file.getName().split("_"); //$NON-NLS-1$
			String nomeCertificadora = fields[0];
//			String nomeConsultoria = fields[1];
			String codigoConsultoria = fields[2];				
//			String newFileName = sdf.format(Calendar.getInstance().getTime()) + "_" + nomeCertificadora + "_" + nomeConsultoria;			
			if(nomeCertificadora.toLowerCase().contains("qcertifica")) //$NON-NLS-1$
			{			
				ConsultorQcertifica.chooseConsultoria(codigoConsultoria);
				ConsultorQcertifica.registerBySeuNumero(file, ConsultorQcertifica.readSeuNumero(file.getAbsolutePath()));

				String fileOldPath = file.getPath();
				
				if(file.renameTo(new File(ConsultorQcertifica.pathProcessado + file.getName() + sdf.format(Calendar.getInstance().getTime()))))
				{
					System.out.println(file.getName() + " moved to " + ConsultorQcertifica.pathProcessado); //$NON-NLS-1$
				}
				else
				{
					System.out.println("Failed to move " + file.getName()); //$NON-NLS-1$
				}			
				
				PrintWriter pw = null;
				try
				{
					pw = new PrintWriter(fileOldPath);
				} catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pw.close();
				
			}
		}
	}
	
	public static void openBrowser()
	{
//		FirefoxProfile fxProfile = new FirefoxProfile();
//
//		fxProfile.setPreference("browser.download.folderList", 2); //$NON-NLS-1$
//		fxProfile.setPreference("browser.download.manager.showWhenStarting", //$NON-NLS-1$
//				false);
//
//		System.out.println("OS Name: " //$NON-NLS-1$
//				+ System.getProperty("os.name").toLowerCase()); //$NON-NLS-1$
//
//		fxProfile.setPreference("browser.helperApps.alwaysAsk.force", false); //$NON-NLS-1$
//		fxProfile.setPreference("browser.download.manager.showWhenStarting", //$NON-NLS-1$
//				false);
//		fxProfile.setPreference("browser.pdfjs.disabled", true); //$NON-NLS-1$
//		fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", true); //$NON-NLS-1$
//		ConsultorQcertifica.driver = new FirefoxDriver(fxProfile);
		ConsultorQcertifica.driver = new FirefoxDriver();
	}

	@SuppressWarnings("resource")
	public static void saveCSVFileLocal(String fileName, String content)
	{
		File file = null;

		file = new File(fileName);
		if (!file.exists())
		{
			try
			{
				System.out.println("Trying to create file " + fileName); //$NON-NLS-1$
				file.createNewFile();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} else
		{
			System.out.println("File name " + file.getAbsolutePath()); //$NON-NLS-1$
		}
		BufferedWriter bw = null;
		try
		{
			bw = new BufferedWriter(new FileWriter(fileName, true));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			bw.write(content);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			bw.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			bw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("File updated at " //$NON-NLS-1$
				+ Calendar.getInstance().getTime());
	}

	public static void login()
	{

		// And now use this to enter the site
		// driver.get("http://portalfidc.bancopaulista.com.br/portal/login");
		// Alternatively the same thing can be done like this

		// driver = new FirefoxDriver(fxProfile);
		// driver = new FirefoxDriver();

		driver.navigate().to(
				"https://portal.qcertifica.com.br/Authentication/Login.aspx"); //$NON-NLS-1$
		timeAndWait(5);
		System.out.println("--------------------"); //$NON-NLS-1$
		System.out.println("User Login\t"); //$NON-NLS-1$
		System.out.println("--------------------"); //$NON-NLS-1$
		WebElement elementUsername = driver.findElement(By
				.name("ctl00$cphContext$txbUser")); //$NON-NLS-1$
		elementUsername.sendKeys(username); 
		//
		WebElement elementPassword = driver.findElement(By.name("ctl00$cphContext$txbPassword")); //$NON-NLS-1$
		elementPassword.sendKeys(password); 

		WebElement elementSubmit = driver.findElement(By.name("ctl00$cphContext$btnLogin")); //$NON-NLS-1$
		elementSubmit.click();
		timeAndWait(2);
		if (driver.findElement(By.className("btnOk")).isDisplayed()) //$NON-NLS-1$
		{
			WebElement buttonOk = driver.findElement(By.className("btnOk")); //$NON-NLS-1$
			buttonOk.click();
		}
		driver.navigate().to("https://portal.qcertifica.com.br/RiskCenterFiles/FCR004.aspx"); //$NON-NLS-1$
		timeAndWait(8);
	}

	public static void register()
	{
		Calendar calBegin = Calendar.getInstance();
		Calendar calFinal = Calendar.getInstance();

		System.out.println("---------"); //$NON-NLS-1$
		System.out.println("calBegin: " + Calendar.getInstance().getTime()); //$NON-NLS-1$
		System.out.println("calFinal: " + Calendar.getInstance().getTime()); //$NON-NLS-1$

		calBegin.set(Calendar.DATE, -20);
		System.out.println(calBegin.getTime() + " to " + calFinal.getTime()); //$NON-NLS-1$

		WebElement dataInicio = driver
				.findElement(By
						.name("ctl00$cphContext$frvConsultationNFe$txbInitialReceiveDate$dateInput")); //$NON-NLS-1$
		dataInicio.click();
		dataInicio.sendKeys(sdfr.format(calBegin.getTime()));
		WebElement dataFinal = driver
				.findElement(By
						.name("ctl00$cphContext$frvConsultationNFe$txbFinalReceiveDate$dateInput")); //$NON-NLS-1$
		dataFinal.clear();
		dataFinal.sendKeys(sdfr.format(calFinal.getTime()));

		WebElement buttonPesquisar = driver.findElement(By
				.name("ctl00$cphContext$frvConsultationNFe$btnFind")); //$NON-NLS-1$
		buttonPesquisar.click();
		timeAndWait(60);
		int currentPage = 1;
		int lastPage = 1;
		String fileCSVName = "W:\\Fundos\\FIDC\\VIAINVEST_104417\\ChaveNFe\\Processar\\NFQCertifica_" //$NON-NLS-1$
				+ sdfdt.format(Calendar.getInstance().getTime()) + ".csv"; //$NON-NLS-1$
		String firstLine = "CodContrat" + ";Contratante" + ";Remessa" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ ";Recebimento" + ";CodCedente" + ";Cedente" + ";CodSacado" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ ";Sacado" + ";ChaveNFe" + ";NumeroNFe" + ";SerieNFe" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ ";SNumero" + ";Valor" + ";Emissao" + ";Vencimento" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ ";NNumero" + ";SeqTitulo" + ";InclusaoNFe" + ";EnvioNFe" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ "\n"; //$NON-NLS-1$
		// Cód. Contrat.
		// Contratante
		// Remessa
		// Recebimento
		// Cód. Cedente
		// Cedente
		// Cód. Sacado
		// Sacado
		// Chave NFe
		// Número NFe
		// Série NFe
		// S. Número
		// Valor
		// Emissão
		// Vencimento
		// N. Número
		// Seq. Título
		// Inclusão NFe
		// Envio NFe

		while (currentPage <= lastPage)
		{
			ArrayList<WebElement> cellTextContainers = new ArrayList<WebElement>();
			cellTextContainers = (ArrayList<WebElement>) driver.findElements(By
					.className("cellTextContainer")); //$NON-NLS-1$

			WebElement numberOfPages = driver.findElement(By
					.className("rgInfoPart")); //$NON-NLS-1$
			String stringNumberOfPages = numberOfPages.getText().trim();
			String[] fieldsNumberOfPages = stringNumberOfPages.split(" "); //$NON-NLS-1$
			currentPage = Integer.parseInt(fieldsNumberOfPages[1]);
			lastPage = Integer
					.parseInt(fieldsNumberOfPages[3].replace(",", "")); //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println("CurrentPage: " + currentPage + " of " //$NON-NLS-1$ //$NON-NLS-2$
					+ lastPage);

			String content = ""; //$NON-NLS-1$
			if (currentPage == 1)
			{
				content = content + firstLine;
			}
			ArrayList<ArrayList<String>> allRows = new ArrayList<ArrayList<String>>();
			ArrayList<String> rowText = new ArrayList<String>();
			for (int i = 0; i < cellTextContainers.size(); i++)
			{
				rowText.add(cellTextContainers.get(i).getText());
				if ((i + 1) % 19 == 0 && i > 0)
				{
					allRows.add(rowText);
					rowText = new ArrayList<String>();
				}
			}

			for (ArrayList<String> rText : allRows)
			{
				for (int i = 0; i < rText.size(); i++)
				{
					String text = rText.get(i);
					if (i != (rText.size() - 1))
					{
						content = content + text.replace(";", "") + ";"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					} else
					{
						content = content + text.replace(";", "") + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}

				}
				// content = content + "\n";
				// System.out.print("\n");
			}

			ConsultorQcertifica.saveCSVFileLocal(fileCSVName, content);

			if (currentPage < lastPage)
			{
				WebElement nextPage = driver.findElement(By
						.className("rgPageNext")); //$NON-NLS-1$
				nextPage.click();
				timeAndWait(3);
			}
			if (currentPage == lastPage && currentPage != 1)
			{
				break;
			}

		}

		// System.out.println("After passing all pages");
		//
		// WebElement nomeUsuario =
		// driver.findElement(By.className("RadMenu_Metro"));
		// nomeUsuario.click();
		// System.out.println("Click on name!");
		//
		// timeAndWait(1);
		// WebElement sair = driver.findElement(By.className("rmLink"));
		// sair.click();
		// System.out.println("Click on Sair!");
		//
		// limitTime=calLimit.getTime();
		// calLimit.set(Calendar.HOUR_OF_DAY, 19);
		// calLimit.set(Calendar.MINUTE, 30);
		// calLimit.set(Calendar.SECOND, 00);
		// limitTime=calLimit.getTime();
		// currentTime = cal.getTime();
	}

	public static void registerBySeuNumero(File file, ArrayList<String> sNumeros)
	{
		if (sNumeros.size() == 0)
		{
			return;
		}
		
		
		String[] fields = file.getName().split("_"); //$NON-NLS-1$
		String nomeCertificadora = fields[0];
		String nomeConsultoria = fields[1];
		String newFileName = sdf.format(Calendar.getInstance().getTime()) + "_" + nomeCertificadora + "_" + nomeConsultoria;			 //$NON-NLS-1$ //$NON-NLS-2$
		String fileCSVName = ConsultorQcertifica.pathProcessar +  newFileName + "NFQCertificaSeuNumero.csv" ; //$NON-NLS-1$
		String firstLine = "CodContrat"  //$NON-NLS-1$
						+ ";Contratante"  //$NON-NLS-1$
						+ ";Remessa" //$NON-NLS-1$
						+ ";Recebimento"  //$NON-NLS-1$
						+ ";CodCedente"  //$NON-NLS-1$
						+ ";Cedente"  //$NON-NLS-1$
						+ ";CodSacado" //$NON-NLS-1$
						+ ";Sacado"  //$NON-NLS-1$
						+ ";ChaveNFe" //$NON-NLS-1$
						+ ";" //$NON-NLS-1$
						+ ";NumeroNFe"  //$NON-NLS-1$
						+ ";SerieNFe" //$NON-NLS-1$
						+ ";SNumero"  //$NON-NLS-1$
						+ ";Valor"  //$NON-NLS-1$
						+ ";Emissao"  //$NON-NLS-1$
						+ ";Vencimento" //$NON-NLS-1$
						+ ";NNumero"  //$NON-NLS-1$
						+ ";SeqTitulo"  //$NON-NLS-1$
						+ ";InclusaoNFe"  //$NON-NLS-1$
						+ ";EnvioNFe" //$NON-NLS-1$
				+ "\n"; //$NON-NLS-1$

		int isNumero = 0;
		int iSeuNumero = 0;
		int totalSeuNumero = sNumeros.size();
		for (String sNumero : sNumeros)
		{
			
			WebElement seuNumero = driver.findElement(By.id("ctl00_cphContext_tabManager_PV0_UC0_frvConsultationNFe_txbTitNr")); //$NON-NLS-1$
			seuNumero.click();
			seuNumero.clear();
			seuNumero.sendKeys(sNumero);

			WebElement buttonPesquisar = driver.findElement(By.id("ctl00_cphContext_tabManager_PV0_UC0_frvConsultationNFe_btnFind")); //$NON-NLS-1$
			buttonPesquisar.click();
			timeAndWait(10);
			int currentPage = 1;
			int lastPage = 1;
			// Cód. Contrat.
			// Contratante
			// Remessa
			// Recebimento
			// Cód. Cedente
			// Cedente
			// Cód. Sacado
			// Sacado
			// Chave NFe
			// Número NFe
			// Série NFe
			// S. Número
			// Valor
			// Emissão
			// Vencimento
			// N. Número
			// Seq. Título
			// Inclusão NFe
			// Envio NFe

			ArrayList<WebElement> cellTextContainers = new ArrayList<WebElement>();
			cellTextContainers = (ArrayList<WebElement>) driver.findElements(By
					.className("cellTextContainer")); //$NON-NLS-1$

			WebElement numberOfPages = driver.findElement(By
					.className("rgInfoPart")); //$NON-NLS-1$
			String stringNumberOfPages = numberOfPages.getText().trim();
			String[] fieldsNumberOfPages = stringNumberOfPages.split(" "); //$NON-NLS-1$
			currentPage = Integer.parseInt(fieldsNumberOfPages[1]);
			lastPage = Integer
					.parseInt(fieldsNumberOfPages[3].replace(",", "")); //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println("CurrentPage: " + currentPage + " of " //$NON-NLS-1$ //$NON-NLS-2$
					+ lastPage);
			System.out.println("Searching " + (iSeuNumero+1) + "/" + totalSeuNumero + ": " + sNumero); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

			String content = ""; //$NON-NLS-1$
			if (currentPage == 1 && isNumero == 0)
			{
				content = content + firstLine;
			}
			isNumero = isNumero + 1;
			ArrayList<ArrayList<String>> allRows = new ArrayList<ArrayList<String>>();
			ArrayList<String> rowText = new ArrayList<String>();
			for (int i = 0; i < cellTextContainers.size(); i++)
			{
				rowText.add(cellTextContainers.get(i).getText());
				if ((i + 1) % 19 == 0 && i > 0)
				{
					allRows.add(rowText);
					rowText = new ArrayList<String>();
				}
			}

			for (ArrayList<String> rText : allRows)
			{
				for (int i = 0; i < rText.size(); i++)
				{
					String text = rText.get(i);
					if (i != (rText.size() - 1))
					{
						content = content + text.replace(";", "") + ";"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					} else
					{
						content = content + text.replace(";", "") + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}

				}
				// content = content + "\n";
				// System.out.print("\n");
			}

			saveCSVFileLocal(fileCSVName, content);
			iSeuNumero=iSeuNumero+1;
		}
		
//		if(driver.findElement(By.name("ctl00$cphContext$frvConsultationNFe$txbTitNr")).isDisplayed())
//		{
//			WebElement seuNumero = driver.findElement(By.name("ctl00$cphContext$frvConsultationNFe$txbTitNr"));
//			seuNumero.click();
//			seuNumero.clear();
//		}		
	}

	public static void logout()
	{
		// Actions actions = new Actions(driver);
		// WebElement menuHoverLink =
		// driver.findElement(By.className("RadMenu_Metro"));
		// actions.moveToElement(menuHoverLink);
		// actions.clickAndHold();
		// actions.perform();
		//
		// // WebElement subLink =
		// driver.findElement(By.cssSelector("#headerMenu .subLink"));
		// // actions.moveToElement(subLink);
		// if(driver.getPageSource().contains("text to search"))
		// {
		// WebElement subLink = driver.findElement(By.className("rmLeftImage"));
		// subLink = driver.findElement(By.className("rmLeftImage"));
		// subLink = driver.findElement(By.className("rmLeftImage"));
		// subLink.click();
		// }

		System.out.println("--------------------"); //$NON-NLS-1$
		System.out.println("Close the browser"); //$NON-NLS-1$
		driver.quit();
	}

	public static void timeAndWait(int n)
	{
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		// System.out.println( sdf.format(cal.getTime()) );
		// System.out.println("Waiting " + (n) + " seconds " +
		// sdf.format(cal.getTime()));
		for (int i = 0; i < n; i++)
		{
			cal = Calendar.getInstance();
			cal.getTime();
			// System.out.println( sdf.format(cal.getTime()) );

			// System.out.println("Waiting " + (n-i) + " seconds " +
			// sdf.format(cal.getTime()));
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("resource")
	public static ArrayList<String> readSeuNumero(String filePath)
	{
		ArrayList<String> seuNumero = new ArrayList<String>();

		BufferedReader reader = null;
		System.out.println("Reading file " + filePath); //$NON-NLS-1$
		System.out.println("------------------"); //$NON-NLS-1$
		String fileSeuNumero = filePath;
		File file = new File(fileSeuNumero);
		if (file.exists())
		{
			try
			{

				reader = new BufferedReader(new FileReader(fileSeuNumero));
			} catch (FileNotFoundException e1)
			{

				e1.printStackTrace();
			}
			String line = null;
			try
			{
				while ((line = reader.readLine()) != null)
				{
					if (!line.isEmpty())
					{

						String txtSeuNumero = line.trim();
						seuNumero.add(txtSeuNumero);
					}
				}
			} catch (IOException e)
			{

				e.printStackTrace();
			}
			try
			{
				reader.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return seuNumero;
	}

	public static SimpleDateFormat getSdfdt()
	{
		return sdfdt;
	}

	public static void setSdfdt(SimpleDateFormat sdfdt)
	{
		ConsultorQcertifica.sdfdt = sdfdt;
	}

	public static SimpleDateFormat getSdfr()
	{
		return sdfr;
	}

	public static void setSdfr(SimpleDateFormat sdfr)
	{
		ConsultorQcertifica.sdfr = sdfr;
	}

	public static WebDriver getDriver()
	{
		return driver;
	}

	public static void setDriver(WebDriver driver)
	{
		ConsultorQcertifica.driver = driver;
	}

	public String getRootDownloadsLinux()
	{
		return this.rootDownloadsLinux;
	}

	public void setRootDownloadsLinux(String rootDownloadsLinux)
	{
		this.rootDownloadsLinux = rootDownloadsLinux;
	}

	public String getHostname()
	{
		return this.hostname;
	}

	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}

	public static String getPathProcessar()
	{
		return pathProcessar;
	}

	public static void setPathProcessar(String pathProcessar)
	{
		ConsultorQcertifica.pathProcessar = pathProcessar;
	}

	public static String getPathProcessado()
	{
		return pathProcessado;
	}

	public static void setPathProcessado(String pathProcessado)
	{
		ConsultorQcertifica.pathProcessado = pathProcessado;
	}

	public static SimpleDateFormat getSdf()
	{
		return sdf;
	}

	public static void setSdf(SimpleDateFormat sdf)
	{
		ConsultorQcertifica.sdf = sdf;
	}

	public static String getUsername()
	{
		return username;
	}

	public static void setUsername(String username)
	{
		ConsultorQcertifica.username = username;
	}

	public static String getPassword()
	{
		return password;
	}

	public static void setPassword(String password)
	{
		ConsultorQcertifica.password = password;
	}
}