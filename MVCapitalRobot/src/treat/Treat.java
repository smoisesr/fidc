package treat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.Login;
import utils.OperatingSystem;
import keywords.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import fundo.FundoDeInvestimento;
public class Treat {
	/**
	 * This is the zip file extraction password
	 */
	private String password="";
	protected FundoDeInvestimento fundo=null;
	protected int idEntidadeServidor = 0;
	protected Login login;
	protected Connection conn=null;
	protected static String separator = OperatingSystem.getSeparator();
	private String rootLocalDir = OperatingSystem.getRootLocalDir();
	private String rootFundLocal = "";	
	private ArrayList<KeywordProcess> keywordProcess=new ArrayList<KeywordProcess>();

	public Treat()
	{
		
	}
	
	public Treat(Login login, Connection conn)
	{
		this.login=login;
		this.conn=conn;
	}
	
	public Treat(FundoDeInvestimento fundo, int idEntidadeServidor, Login login, Connection conn)
	{
		this.fundo = fundo;
		this.idEntidadeServidor=idEntidadeServidor;
		this.login=login;
		this.conn = conn;
		this.setPassword();
		this.setFolders();
		this.setKeywordProcess();
	}
	
	public void setFolders()
	{
		this.rootLocalDir = OperatingSystem.getRootLocalDir();
		this.rootFundLocal = "Fundos" + separator + fundo.getCategoriaGeralAnbima() + separator + fundo.getNomeCurto().toUpperCase()+"_"+fundo.getCodigoCVM();
	}
	
	public void setIdEntidadeServidor()
	{
		/**
		 * Finding idEntidadeServidor for Login
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidadeServidor FROM Login WHERE idLogin=" + this.login.getIdLogin());
			while (rs.next())
			{				
				this.idEntidadeServidor = rs.getInt("idEntidadeServidor");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void treatAll()
	{
		System.out.println("Treat for files from fund " + this.fundo.getNomeCurto());
		this.treatAllOrganizedFolders();
	}
	public ArrayList<KeywordProcess> getKeywordProcess()
	{
		return this.keywordProcess;
	}		
	public void setKeywordProcess()
	{
		/**
		 * Finding keyword for Login from KeywordProcess
		 */
//		System.out.println("----------------------------");
//		System.out.println("List of keywordProcess");
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idKeywordProcess FROM KeywordProcess WHERE idLogin=" + this.login.getIdLogin());
			while (rs.next())
			{				
				KeywordProcess kProcess = new KeywordProcess(rs.getInt("idKeywordProcess"), this.getConn());
				this.keywordProcess.add(kProcess);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void treatAllOrganizedFolders()
	{
		TreatCarteira treatCarteira = new TreatCarteira(this.fundo, this.idEntidadeServidor,this.login,this.conn);
		treatCarteira.treatFolder();
		
	}
		public String getPassword() {
		return password;
	}

	public void setPassword() {
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT password FROM SenhaZip WHERE idFundo=" + this.getFundo().getIdFundo() + " AND idEntidadeServidor=" + this.idEntidadeServidor);
			while (rs.next())
			{				
				this.password = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println("Zip files password: " + this.getPassword());
	}

	public FundoDeInvestimento getFundo() {
		return fundo;
	}

	public void setFundo(FundoDeInvestimento fundo) {
		this.fundo = fundo;
	}

	public int getIdEntidadeServidor() {
		return idEntidadeServidor;
	}

	public void setIdEntidadeServidor(int idEntidadeServidor) {
		this.idEntidadeServidor = idEntidadeServidor;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		Treat.separator = separator;
	}

	public String getRootLocalDir() {
		return rootLocalDir;
	}

	public void setRootLocalDir(String rootLocalDir) {
		this.rootLocalDir = rootLocalDir;
	}

	public String getRootFundLocal() {
		return rootFundLocal;
	}

	public void setRootFundLocal(String rootFundLocal) {
		this.rootFundLocal = rootFundLocal;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
