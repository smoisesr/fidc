package mvcapital.reporter;

 import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mvcapital.bancopaulista.ExtratorBancoPaulista;
import mvcapital.conta.Conta;
import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.mysql.MySQLAccess;
import mvcapital.portalfidc.OperadorPortalPaulista;
import mvcapital.utils.OperatingSystem;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Reporter 
{
	private String server = "localhost";
	private int port = 3306;		
	private String userName = "root"; 
	private String password = "root";
	private String dbName = "root";
	private MySQLAccess mysql = null;
	private static Connection conn = null;
	private static String separator = "";
	private static String rootLocalDir = "";
	private ArrayList<FundoDeInvestimento> funds = new ArrayList<FundoDeInvestimento>();
	private ArrayList<Conta> fundAccounts = new ArrayList<Conta>();
	private Conta managerAccount = new Conta();
	private OperadorPortalPaulista operadorPortal = null;
	private ArrayList<ExtratorBancoPaulista> extratorBanco = new ArrayList<ExtratorBancoPaulista>();

	public Reporter()
	{
		this.readConf();
		this.mysql = new MySQLAccess(this.server, this.port, this.userName, this.password, this.dbName);
		mysql.connect();	
		conn = (Connection) mysql.getConn();		
		this.readFunds();
		this.readFundAccounts();
		this.readManagerAccount();		
//		this.operadorPortal = new OperadorPortalPaulista(this.managerAccount);
	}
	
	public static void main(String[] args)
	{
		Calendar cal = Calendar.getInstance();
    	cal.getTime();

    	Calendar calLimit=Calendar.getInstance();
        Date limitTime=calLimit.getTime();
        calLimit.set(Calendar.HOUR_OF_DAY, 21);
        calLimit.set(Calendar.MINUTE, 45);
        calLimit.set(Calendar.SECOND, 0);
		limitTime=calLimit.getTime();
		
		Reporter reporter = new Reporter();
		OperadorPortalPaulista operator = new OperadorPortalPaulista(reporter.getManagerAccount());
		while (cal.getTime().before(limitTime))
		{
			operator.checkStatus();
			cal = Calendar.getInstance();
		}
		operator.logout();
		
//		reporter.setOperadorPortal(new OperadorPortalPaulista(reporter.getManagerAccount()));
////		reporter.getOperadorPortal().login();
//		reporter.getOperadorPortal().checkStatus();
////		reporter.getOperadorPortal().logout();
	}
	
	public void readManagerAccount()
	{
		try 
		{
			System.out.println("MVCapital - Usuário de Portal");
			Statement stmt = (Statement) Reporter.conn.createStatement();
			String query = "SELECT * FROM Conta WHERE idEntidadeProprietario=10";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
//				int idConta = rs.getInt("idConta");
				int idEntidadeProprietario = rs.getInt("idEntidadeProprietario");
				int idEntidadeServidor = rs.getInt("idEntidadeServidor");
				String numero=rs.getString("numero");
				String agencia=rs.getString("agencia");
				String usuario= rs.getString("usuario");
				String senha= rs.getString("senha");
				int idTipoDeConta=rs.getInt("idTipoDeConta");
				
				Entidade entidadeProprietario = new Entidade(idEntidadeProprietario,conn);
				Entidade entidadeServidor = new Entidade(idEntidadeServidor,conn);
				Conta account = new	Conta(entidadeProprietario, entidadeServidor, usuario, senha, agencia, numero, idTipoDeConta, conn);
				account.showShort();
				this.managerAccount=account;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void readFundAccounts()
	{
		
		for (FundoDeInvestimento fundo:funds)
		{
			/**
			 * Finding id for flag that indicates a fund as active in table FundoAtivo
			 */
			try 
			{
				Statement stmt = (Statement) Reporter.conn.createStatement();
				System.out.println(fundo.getNomeCurto());
				String query = "SELECT * FROM Conta WHERE idEntidadeProprietario="+fundo.getIdEntidade();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next())
				{
//					int idConta = rs.getInt("idConta");
					int idEntidadeProprietario = rs.getInt("idEntidadeProprietario");
					int idEntidadeServidor = rs.getInt("idEntidadeServidor");
					String numero=rs.getString("numero");
					String agencia=rs.getString("agencia");
					String usuario= rs.getString("usuario");
					String senha= rs.getString("senha");
					int idTipoDeConta=rs.getInt("idTipoDeConta");
					Entidade entidadeProprietario = new Entidade(idEntidadeProprietario,conn);
					Entidade entidadeServidor = new Entidade(idEntidadeServidor,conn);
					
					Conta account = new	Conta(entidadeProprietario, entidadeServidor, usuario, senha, agencia, numero, idTipoDeConta, conn);
					account.showShort();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
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
			Statement stmt = (Statement) Reporter.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idFundoAtivo FROM FundoAtivo WHERE ativo='Sim'");
			while (rs.next())
			{				
				idFundoAtivoSim = rs.getInt("idFundoAtivo");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/**
		 * Finding every idFundo that is active in table Fundo
		 */
		try {
			Statement stmt = (Statement) Reporter.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idFundo FROM Fundo WHERE idFundoAtivo="+idFundoAtivoSim);
			while (rs.next())
			{				
				int idFundo = rs.getInt("idFundo");
				this.funds.add(new FundoDeInvestimento(idFundo, conn));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/**
		 * For every fund perform some activity
		 * at this moment we are showing the funds name
		 */
		System.out.println("-------------------");
		System.out.println("Operational funds");
		for (FundoDeInvestimento fundo:this.funds)
		{
			try {
				Statement sNome = (Statement) Reporter.conn.createStatement();
				ResultSet rsNome = sNome.executeQuery("SELECT nomeCurto FROM Entidade WHERE idEntidade="+fundo.getIdEntidade());
				while (rsNome.next())
				{				
					String nomeCurto = rsNome.getString("nomeCurto");
					System.out.println("- " + nomeCurto);
					//fundo.showMembers();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
    public void readConf()
	{
		BufferedReader reader = null;
		System.out.println("Reading conf/automata.conf file");
		System.out.println("------------------");
		try 
		{
			reader = new BufferedReader(new FileReader("conf/extratobancopaulista.conf"));
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
		Reporter.conn = conn;
	}


	public static String getSeparator() {
		return separator;
	}


	public static void setSeparator(String separator) {
		Reporter.separator = separator;
	}


	public static String getRootLocalDir() {
		return rootLocalDir;
	}


	public static void setRootLocalDir(String rootLocalDir) {
		Reporter.rootLocalDir = rootLocalDir;
	}


	public ArrayList<FundoDeInvestimento> getFunds() {
		return funds;
	}


	public void setFunds(ArrayList<FundoDeInvestimento> funds) {
		this.funds = funds;
	}

	public ArrayList<Conta> getFundAccounts() {
		return fundAccounts;
	}

	public void setFundAccounts(ArrayList<Conta> fundAccounts) {
		this.fundAccounts = fundAccounts;
	}

	public Conta getManagerAccount() {
		return managerAccount;
	}

	public void setManagerAccount(Conta managerAccount) {
		this.managerAccount = managerAccount;
	}

	public OperadorPortalPaulista getOperadorPortal() {
		return operadorPortal;
	}

	public void setOperadorPortal(OperadorPortalPaulista operadorPortal) {
		this.operadorPortal = operadorPortal;
	}

	public ArrayList<ExtratorBancoPaulista> getExtratorBanco() {
		return extratorBanco;
	}

	public void setExtratorBanco(ArrayList<ExtratorBancoPaulista> extratorBanco) {
		this.extratorBanco = extratorBanco;
	}
}
