package keywords;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class KeywordProcess extends Keyword
{
	private int idKeywordProcess=0;
	private int idLogin=0;
	private int idKeywordProcessor=0;
	private KeywordProcessor keywordProcessor = null;
	Connection conn=null;
	public KeywordProcess()
	{
		
	}
	
	public KeywordProcess(String keyword)
	{
		super(keyword);
	}
	
	public KeywordProcess(int idKeywordProcess, Connection conn)
	{
		this.conn=conn;
		this.idKeywordProcess=idKeywordProcess;
		/**
		 * Finding keyword from KeywordProcessor 
		 */
		try 
		{
			Statement stmto = (Statement) this.getConn().createStatement();
			ResultSet rso = stmto.executeQuery("SELECT idLogin,keyword,idKeywordProcessor FROM KeywordProcess WHERE idKeywordProcess=" + idKeywordProcess);
			while (rso.next())
			{				
				super.setKeyword(rso.getString("keyword"));
				this.setIdLogin(rso.getInt("idLogin"));
				this.setIdKeywordProcessor(rso.getInt("idKeywordProcessor"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.setKeywordProcessor(new KeywordProcessor(this.idKeywordProcessor, conn));
	}
	public int getIdKeywordProcessor() {
		return idKeywordProcessor;
	}

	public void setIdKeywordProcessor(int idKeywordProcessor) {
		this.idKeywordProcessor = idKeywordProcessor;
	}

	public int getIdKeywordProcess() {
		return idKeywordProcess;
	}

	public void setIdKeywordProcess(int idKeywordProcess) {
		this.idKeywordProcess = idKeywordProcess;
	}

	public int getIdLogin() {
		return idLogin;
	}

	public void setIdLogin(int idLogin) {
		this.idLogin = idLogin;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public KeywordProcessor getKeywordProcessor() {
		return keywordProcessor;
	}

	public void setKeywordProcessor(KeywordProcessor keywordProcessor) {
		this.keywordProcessor = keywordProcessor;
	}
}
