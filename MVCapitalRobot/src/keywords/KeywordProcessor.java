package keywords;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class KeywordProcessor extends Keyword
{
	private int idKeywordProcessor=0;
	Connection conn=null;
	public KeywordProcessor()
	{
		
	}
	
	public KeywordProcessor(String keyword)
	{
		super(keyword);
	}
	
	public KeywordProcessor(int idKeywordProcessor, Connection conn)
	{
		this.conn=conn;
		this.idKeywordProcessor=idKeywordProcessor;
		/**
		 * Finding keyword from KeywordProcessor 
		 */
		try 
		{
			Statement stmto = (Statement) this.conn.createStatement();
			ResultSet rso = stmto.executeQuery("SELECT keyword FROM KeywordProcessor WHERE idKeywordProcessor=" + idKeywordProcessor);
			while (rso.next())
			{				
				super.setKeyword(rso.getString("keyword"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getIdKeywordProcessor() {
		return idKeywordProcessor;
	}

	public void setIdKeywordProcessor(int idKeywordProcessor) {
		this.idKeywordProcessor = idKeywordProcessor;
	}
}
