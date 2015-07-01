package automata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import organize.Organize;
import treat.Treat;
import utils.Login;
import utils.OperatingSystem;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import mysql.MySQLAccess;
import ftp.Downloader;
import ftp.LoginFTP;
import fundo.FundoDeInvestimento;

/**
 * 
 * @author MVCapital - Moisés Ito
 *
 */
public class Automata
{	
	private String server = "localhost"; //$NON-NLS-1$
	private int port = 3306;		
	private String userName = "root";  //$NON-NLS-1$
	private String password = "root"; //$NON-NLS-1$
	private String dbName = "root"; //$NON-NLS-1$
	private MySQLAccess mysql = null;
	private static Connection conn = null;
	private static String separator = ""; //$NON-NLS-1$
	private static String rootLocalDir = ""; //$NON-NLS-1$
	private Calendar cal=Calendar.getInstance();
	
	private ArrayList<FundoDeInvestimento> fundos = new ArrayList<FundoDeInvestimento>(); 

	/**
	 * 
	 * @param adms List of administrators
	 * @param fundos List of funds
	 */
	
	public Automata()
	{
		this.readAutomataConf();
		OperatingSystem.setSeparator();
		OperatingSystem.setRootLocalDir();
		
		separator=OperatingSystem.getSeparator();
		rootLocalDir=OperatingSystem.getRootLocalDir();

		this.mysql = new MySQLAccess(this.server, this.port, this.userName, this.password, this.dbName);
		this.mysql.connect();	
		Automata.conn = (Connection) this.mysql.getConn();
	}
	
	/**
	 * This Main method performs all the actions
	 */
	public static void main(String[] args) 
    {
		Automata zipMVC = new Automata();
		System.out.println("**********************"); //$NON-NLS-1$
		System.out.println("MVCapital - Robot");	 //$NON-NLS-1$
		System.out.println("**********************"); //$NON-NLS-1$
		zipMVC.cal = Calendar.getInstance();
		System.out.println(zipMVC.cal.getTime());

		System.out.println(Automata.separator);
		System.out.println(Automata.rootLocalDir);
		zipMVC.readFunds();
		zipMVC.processFunds();
		zipMVC.closeDatabase();
		zipMVC.cal = Calendar.getInstance();
		System.out.println(zipMVC.cal.getTime());
    }
	
	/**
	 * Thie method will read the config file for automata
	 */
	public void readAutomataConf()
	{
		BufferedReader reader = null;
		System.out.println("Reading conf/automata.conf file"); //$NON-NLS-1$
		System.out.println("------------------"); //$NON-NLS-1$
		try 
		{
			reader = new BufferedReader(new FileReader("conf/automata.conf")); //$NON-NLS-1$
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
					            case "rootLocalLinux": //$NON-NLS-1$
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
	}
	
	public void readFunds()
	{
		int idFundoAtivoSim=0;

		/**
		 * Finding id for flag that indicates a fund as active in table FundoAtivo
		 */
		try 
		{
			Statement stmt = (Statement) Automata.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idFundoAtivo FROM FundoAtivo WHERE ativo='Sim'"); //$NON-NLS-1$
			while (rs.next())
			{				
				idFundoAtivoSim = rs.getInt("idFundoAtivo");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/**
		 * Finding every idFundo that is active in table Fundo
		 */
		try {
			Statement stmt = (Statement) Automata.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idFundo FROM Fundo WHERE idFundoAtivo="+idFundoAtivoSim); //$NON-NLS-1$
			while (rs.next())
			{				
				int idFundo = rs.getInt("idFundo"); //$NON-NLS-1$
				this.fundos.add(new FundoDeInvestimento(idFundo, conn));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/**
		 * For every fund perform some activity
		 * at this moment we are showing the funds name
		 */
		for (FundoDeInvestimento fundo:this.fundos)
		{
			try {
				Statement sNome = (Statement) Automata.conn.createStatement();
				ResultSet rsNome = sNome.executeQuery("SELECT nomeCurto FROM Entidade WHERE idEntidade="+fundo.getIdEntidade()); //$NON-NLS-1$
				System.out.println("-------------------"); //$NON-NLS-1$
				System.out.println("Operational funds"); //$NON-NLS-1$
				while (rsNome.next())
				{				
					String nomeCurto = rsNome.getString("nomeCurto"); //$NON-NLS-1$
					System.out.println("- " + nomeCurto); //$NON-NLS-1$
					fundo.showMembers();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void processFunds()
	{
		for (final FundoDeInvestimento fundo:this.fundos)
		{
			checkFoldersForFund(fundo);
			downloadFilesForFund(fundo);
//			organizeFilesForFund(fundo);
//			int nPeriods=10;
//			for(int i=0;i<nPeriods;i++)
//			{
//				Date now= new Date();
//				System.out.println("Waiting " + (nPeriods-i) + " seconds " + now); //$NON-NLS-1$ //$NON-NLS-2$
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}				
//			}
//			treatFilesForFund(fundo);
		}
	}
	
	public static void organizeFilesForFund(FundoDeInvestimento fundo)
	{
		ArrayList<Login> loginList = new ArrayList<Login>();
		/**
		 * Finding idLogin for Fund
		 */
		try 
		{
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idLogin FROM Login WHERE idEntidadeCliente=" + fundo.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				
				loginList.add(new Login(rs.getInt("idLogin"), conn)); //$NON-NLS-1$
				System.out.println("Login " + loginList.get(loginList.size()-1).getIdLogin()); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Login login:loginList)
		{
			int idEntidadeServidor = login.getIdEntidadeServidor();
			System.out.println("idEntidadeServidor " + idEntidadeServidor); //$NON-NLS-1$
			Organize organize = new Organize(fundo, idEntidadeServidor, login, conn);
			organize.organizeAll();
		}
	}
	
	public static void treatFilesForFund(FundoDeInvestimento fundo)
	{
		ArrayList<Login> loginList = new ArrayList<Login>();
		/**
		 * Finding idLogin for Fund
		 */
		try 
		{
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idLogin FROM Login WHERE idEntidadeCliente=" + fundo.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				
				loginList.add(new Login(rs.getInt("idLogin"), conn)); //$NON-NLS-1$
				System.out.println("Login " + loginList.get(loginList.size()-1).getIdLogin()); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Login login:loginList)
		{
			int idEntidadeServidor = login.getIdEntidadeServidor();
			System.out.println("idEntidadeServidor " + idEntidadeServidor); //$NON-NLS-1$
			Treat treat = new Treat(fundo, idEntidadeServidor, login, conn);
			treat.treatAll();
		}
	}

	public static void downloadFilesForFund(FundoDeInvestimento fundo)
	{
		ArrayList<Integer> idLoginList=new ArrayList<Integer>();
		Downloader downloader = null;
		/**
		 * Finding idLogin for Fund
		 */
		try 
		{
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idLogin FROM Login WHERE idEntidadeCliente=" + fundo.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				idLoginList.add(rs.getInt("idLogin"));				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(idLoginList.size()>0)
		{
			System.out.println("Fund " + fundo.getNomeCurto() + " have " + idLoginList.size()+ " login accounts"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		for (int idLogin:idLoginList)
		{
			if (idLogin!=0)
			{
				Login login = new Login(idLogin, conn);
				System.out.println("Details "); //$NON-NLS-1$
				login.showLogin();
				if(login.getTipoDeLogin().getTipo().toLowerCase().contains("ftp")) //$NON-NLS-1$
				{
					LoginFTP loginFTP = new LoginFTP(login.getIdLogin(), conn);
					downloader = new Downloader(fundo,loginFTP,conn);
					downloader.downloadFiles(fundo);
				}
			}
		}		
	}
	
	public static void checkFoldersForFund(FundoDeInvestimento fundo)
	{
		System.out.println("---------------"); //$NON-NLS-1$
		System.out.println("Checking folders existence for fund " + fundo.getNomeCurto().toUpperCase() +"_"+fundo.getCodigoCVM());  //$NON-NLS-1$ //$NON-NLS-2$
		
		ArrayList<String> localFolders=new ArrayList<String>();
		localFolders.add("Fundos" + separator + fundo.getCategoriaGeralAnbima() + separator + fundo.getNomeCurto().toUpperCase()+"_"+fundo.getCodigoCVM()); //$NON-NLS-1$ //$NON-NLS-2$
		String rootFundLocal = "Fundos" + separator + fundo.getCategoriaGeralAnbima() + separator + fundo.getNomeCurto().toUpperCase()+"_"+fundo.getCodigoCVM(); //$NON-NLS-1$ //$NON-NLS-2$
		
		localFolders.add(rootFundLocal+separator+"Carteira"); //$NON-NLS-1$
		String rootCarteira = rootFundLocal+separator+"Carteira"; //$NON-NLS-1$
		localFolders.add(rootCarteira+separator+"Processar"); //$NON-NLS-1$
		localFolders.add(rootCarteira+separator+"Processado"); //$NON-NLS-1$
		String rootCarteiraProcessado = rootCarteira+separator+"Processado"; //$NON-NLS-1$
		localFolders.add(rootCarteiraProcessado+separator+"Original"); //$NON-NLS-1$
		
		localFolders.add(rootFundLocal+separator+"ContaCaixa"); //$NON-NLS-1$
		String rootContaCaixa = rootFundLocal+separator+"ContaCaixa"; //$NON-NLS-1$
		localFolders.add(rootContaCaixa+separator+"Processar"); //$NON-NLS-1$
		localFolders.add(rootContaCaixa+separator+"Processado"); //$NON-NLS-1$
		
		localFolders.add(rootFundLocal+separator+"ContaCobranca"); //$NON-NLS-1$
		String rootContaCobranca = rootFundLocal+separator+"ContaCobranca"; //$NON-NLS-1$
		localFolders.add(rootContaCobranca+separator+"Processar"); //$NON-NLS-1$
		localFolders.add(rootContaCobranca+separator+"Processado"); //$NON-NLS-1$

		localFolders.add(rootFundLocal+separator+"ContaCustodia"); //$NON-NLS-1$
		String rootContaCustodia = rootFundLocal+separator+"ContaCustodia"; //$NON-NLS-1$
		localFolders.add(rootContaCustodia+separator+"Processar"); //$NON-NLS-1$
		localFolders.add(rootContaCustodia+separator+"Processado"); //$NON-NLS-1$

		localFolders.add(rootFundLocal+separator+"Cota"); //$NON-NLS-1$
		String rootCotas = rootFundLocal+separator+"Cota"; //$NON-NLS-1$
		localFolders.add(rootCotas+separator+"Processar"); //$NON-NLS-1$
		localFolders.add(rootCotas+separator+"Processado"); //$NON-NLS-1$
		
		localFolders.add(rootFundLocal+separator+"Cotistas"); //$NON-NLS-1$
		String rootCotistas = rootFundLocal+separator+"Cotistas"; //$NON-NLS-1$
		localFolders.add(rootCotistas+separator+"Processar"); //$NON-NLS-1$
		localFolders.add(rootCotistas+separator+"Processado"); //$NON-NLS-1$

		localFolders.add(rootFundLocal+separator+"DireitosCreditorios"); //$NON-NLS-1$
		String rootDireitosCreditorios = rootFundLocal+separator+"DireitosCreditorios"; //$NON-NLS-1$
		localFolders.add(rootDireitosCreditorios+separator+"Processar"); //$NON-NLS-1$
		localFolders.add(rootDireitosCreditorios+separator+"Processado"); //$NON-NLS-1$

		localFolders.add(rootFundLocal+separator+"Repositorio"); //$NON-NLS-1$
		String rootRepositorio = rootFundLocal+separator+"Repositorio"; //$NON-NLS-1$
		localFolders.add(rootRepositorio+separator+"Transferir"); //$NON-NLS-1$
		localFolders.add(rootRepositorio+separator+"Transferido"); //$NON-NLS-1$
		localFolders.add(rootRepositorio+separator+"Transferir"+separator+"unzip"); //$NON-NLS-1$ //$NON-NLS-2$
		
		for(String localFolder:localFolders)
		{
			//System.out.println(rootLocalDir + separator + localFolder);
			String completePathFolder = rootLocalDir + localFolder;
			File saveDir = new File(completePathFolder);	
			if(!saveDir.exists())
			{
				//System.out.println("Folder " + completePathDirectory + " dosn't exist ");
				saveDir.mkdirs();
				System.out.println("Folder " + completePathFolder + "\t created "); //$NON-NLS-1$ //$NON-NLS-2$
			}
			else
			{
				System.out.println("Folder " + completePathFolder + "\t exist! "); //$NON-NLS-1$ //$NON-NLS-2$
			}				
		}
	}
	
	public void closeDatabase()
	{
		this.mysql.close();
	}

	public static String getSeparator() {
		return separator;
	}

	public static void setSeparator(String separator) {
		Automata.separator = separator;
	}

	public static String getRootLocalDir() {
		return rootLocalDir;
	}

	public static void setRootLocalDir(String rootLocalDir) {
		Automata.rootLocalDir = rootLocalDir;
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
		Automata.conn = conn;
	}

	public Calendar getCal() {
		return this.cal;
	}

	public void setCal(Calendar cal) {
		this.cal = cal;
	}

	public ArrayList<FundoDeInvestimento> getFundos() {
		return this.fundos;
	}

	public void setFundos(ArrayList<FundoDeInvestimento> fundos) {
		this.fundos = fundos;
	}	
}
