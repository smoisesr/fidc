package fundo;

import java.io.File;
import java.util.ArrayList;

import utils.OperatingSystem;
import ftpclient.LoginFTP;
import administrador.*;

/**
 * @author MVCapital - Mois�s
 * Class to define the investment fund
 */
public class FundoDeInvestimento 
{
	private String name = "";
	private String localName = "";
	private Administrador adm = new Administrador();	
	private LoginFTP loginFTP = new LoginFTP();
	private String pathFTP = "";
	private ArrayList<String> localFolders = new ArrayList<String>();
	private ArrayList<String> descritores = new ArrayList<String>();
	private ArrayList<File> filesDownloaded = new ArrayList<File>();
	private String rootLocalPath = "";
	private String unzipPath="";
	public enum Lista {Orion};
	private String zipPassword="";
	private String separator=OperatingSystem.getSeparator();
	
	public FundoDeInvestimento()
	{
	}
	
	public FundoDeInvestimento(String name, Administrador adm)
	{
		this.name = name;
		this.adm = adm;
	}

	public FundoDeInvestimento(String name)
	{
		this.setName(name);
	}

	/**
	 * 
	 * @param nomeFundo
	 * @param administrador
	 * @param login
	 * @param rootLocalDir
	 * @param zipPassword
	 */
	public FundoDeInvestimento(Lista nomeFundo, Administrador administrador, LoginFTP login, String rootLocalDir, String zipPassword)
	{
		this.name = nomeFundo.toString();
		this.adm = administrador;
		this.loginFTP = login;
		this.rootLocalPath = rootLocalDir;
		this.setPathFTP();
		this.setLocalName();
		this.setDescritores();
		this.setLocalFolders(rootLocalDir);
		this.createLocalFolders();
		this.zipPassword = zipPassword;
	}
	
	public void createLocalFolders()
	{
		System.out.println("---------------------------");
		System.out.println("Verifying folders for " + this.localName);
		System.out.println("---------------------------");
		for(String completePathDirectory:this.getLocalFolders())
		{
			File saveDir = new File(completePathDirectory);	
			if(!saveDir.exists())
			{
				//System.out.println("Folder " + completePathDirectory + " dosn't exist ");
				saveDir.mkdirs();
				System.out.println("Folder " + completePathDirectory + "\t created ");
			}
			else
			{
				System.out.println("Folder " + completePathDirectory + "\t already exist ");
			}
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Administrador getAdm()
	{
		return adm;
	}

	public void setAdm(Administrador adm)
	{
		this.adm = adm;
	}

	public LoginFTP getLoginFTP()
	{
		return loginFTP;
	}

	public void setLoginFTP(LoginFTP loginFTP)
	{
		this.loginFTP = loginFTP;
	}

	public String getPathFTP()
	{
		return pathFTP;
	}

	public void setPathFTP(String pathFTP)
	{
		this.pathFTP = pathFTP;
	}
	
	public void setPathFTP()
	{
		String admName = this.adm.getName();
		switch (admName)
		{
			case "Petra":
				this.pathFTP = "/" + this.name.toLowerCase() + "/" + this.adm.getDirFiles();
				break;

			default:
				break;
		}
	}

	public ArrayList<String> getLocalFolders()
	{
		return localFolders;
	}

	public void setLocalFolders(String rootLocalDir)
	{		
		for (String descritor:this.descritores)
		{
			if(descritor.equals("Estoque")||descritor.equals("Cotas")||descritor.equals("Carteira")||descritor.equals("Caixa")||descritor.equals("Baixas"))
			{
				this.localFolders.add(rootLocalDir+ separator + "Fontes" + separator + this.adm.getName()+separator+descritor+separator+this.localName);
				this.localFolders.add(rootLocalDir+ separator + "Fontes" + separator + this.adm.getName()+separator+descritor+separator+this.localName+separator + "A Processar");
				if(descritor.equals("Baixas"))
				{
					this.localFolders.add(rootLocalDir+ separator + "Fontes" + separator + this.adm.getName()+separator+descritor+separator+this.localName+separator + "Titulos Pagos");
					this.localFolders.add(rootLocalDir+ separator + "Fontes" + separator + this.adm.getName()+separator+descritor+separator+this.localName+separator + "Titulos Pagos"+separator +"A Processar");
				}
			}
			else
			{
				this.localFolders.add(rootLocalDir+ separator + "Fontes" + separator + this.adm.getName()+separator+descritor+separator+this.localName);
			}
			if (descritor.equals("Unzip"))
			{
				this.setUnzipPath(rootLocalDir+ separator + "Fontes" + separator + this.adm.getName()+separator+descritor+separator+this.localName);
			}
		}
	}
		
	public String getLocalName()
	{
		return localName;
	}

	public void setLocalName()
	{
		if(this.getName()=="Orion")
		{
			this.localName = "FIDC Orion";
		}
	}
	
	public void setLocalName(String localName)
	{
		this.localName = localName;
	}

	public void setLocalFolders(ArrayList<String> localFolders)
	{
		this.localFolders = localFolders;
	}

	public ArrayList<String> getDescritores()
	{
		return descritores;
	}

	public void setDescritores()
	{
		descritores.add("Baixas");
		descritores.add("Caixa");
		descritores.add("Carteira");
		descritores.add("CNAB");
		descritores.add("Cotas");		
		descritores.add("Estoque");
		descritores.add("Investidor");
		descritores.add("XML");
		descritores.add("FTP");
		descritores.add("Unzip");
	}
	
	public void setDescritores(ArrayList<String> descritores)
	{
		this.descritores = descritores;
	}

	public ArrayList<File> getFilesDownloaded()
	{
		return filesDownloaded;
	}

	public void setFilesDownloaded(ArrayList<File> filesDownloaded)
	{
		this.filesDownloaded = filesDownloaded;
	}

	public String getRootLocalPath()
	{
		return rootLocalPath;
	}

	public void setRootLocalPath(String rootLocalPath)
	{
		this.rootLocalPath = rootLocalPath;
	}

	public String getUnzipPath()
	{
		return unzipPath;
	}

	public void setUnzipPath(String unzipPath)
	{
		this.unzipPath = unzipPath;
	}

	public String getZipPassword()
	{
		return zipPassword;
	}

	public void setZipPassword(String zipPassword)
	{
		this.zipPassword = zipPassword;
	}
}
