package mvcapital.operation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import mvcapital.portalfidc.OperadorPortalPaulista;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Status 
{
	int idStatusOperacao=0;
	int idOperacao=0;
	String description="";
	int idDescricaoStatus=0;
	Date beginDate=Calendar.getInstance().getTime();
	Date endDate = Calendar.getInstance().getTime();
	Long lifeTime = (long) 0;
	String lifeTimeString = "0";
	
	public Status()
	{
		
	}

	public Status(int idStatusOperacao, Connection conn)
	{
		this.idStatusOperacao = idStatusOperacao;
		
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT idOperacao,inicio,fim,idDescricaoStatus FROM statusoperacao WHERE idStatusOperacao= " + idStatusOperacao;
//		System.out.println(query);
		ResultSet rs=null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				this.idOperacao = rs.getInt("idOperacao");
				this.beginDate = rs.getDate("inicio");
				this.endDate = rs.getDate("fim");
				this.idDescricaoStatus=rs.getInt("idDescricaoStatus");
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "SELECT descricao FROM descricaostatus WHERE idDescricaoStatus= " + this.idDescricaoStatus;
//		System.out.println(query);
		rs=null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{	
				this.description = rs.getString("descricao");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Status(int idOperacao, int idDescricaoStatus, Connection conn)
	{
		this.idDescricaoStatus=idDescricaoStatus;
		this.idOperacao = idOperacao;
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT idStatusOperacao,inicio,fim FROM statusoperacao WHERE idOperacao= " + idOperacao + " AND idDescricaoStatus = " + idDescricaoStatus;
//		System.out.println(query);
		ResultSet rs=null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{	
				this.beginDate = rs.getDate("inicio");
				this.endDate = rs.getDate("fim");
				this.idStatusOperacao = rs.getInt("idStatusOperacao");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "SELECT descricao FROM descricaostatus WHERE idDescricaoStatus= " + idDescricaoStatus;
//		System.out.println(query);
		rs=null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{	
				this.description = rs.getString("descricao");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	public Status(String description, Date beginDate, Connection conn)
	{
		this.description=description;
		this.setBeginDate(beginDate);
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT idDescricaoStatus FROM descricaostatus WHERE descricao= \"" + this.description + "\"";
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.idDescricaoStatus = rs.getInt("idDescricaoStatus");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (this.idDescricaoStatus==0)
		{
			String sql = "INSERT INTO `mvcapital`.`descricaostatus` (`descricao`) VALUES("
						+ "\"" + this.description + "\"" 
						+ ")";
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			query = "SELECT idDescricaoStatus FROM descricaostatus WHERE descricao= \"" + this.description + "\"";
			System.out.println(query);
			rs=null;
			try {
				rs = stmt.executeQuery(query);
				while (rs.next())
				{				
					this.idDescricaoStatus = rs.getInt("idDescricaoStatus");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}						
		}

		query = "SELECT idStatusOperacao FROM statusoperacao WHERE idOperacao= " + idOperacao + " AND idDescricaoStatus = " + this.idDescricaoStatus;
		System.out.println(query);
		rs=null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{	
				this.idStatusOperacao = rs.getInt("idStatusOperacao");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
	}
	
	public Status(String description, Date beginDate)
	{
		
		this.description=description;
		this.setBeginDate(beginDate);
		Statement stmt = null;
		try {
			stmt = (Statement) OperadorPortalPaulista.getConn().createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT idDescricaoStatus FROM descricaostatus WHERE descricao= \"" + this.description + "\"";
		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.idDescricaoStatus = rs.getInt("idDescricaoStatus");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (this.idDescricaoStatus==0)
		{
			String sql = "INSERT INTO `mvcapital`.`descricaostatus` (`descricao`) VALUES ("
						+ "\"" + this.description + "\"" 
						+ ")";
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			query = "SELECT idDescricaoStatus FROM descricaostatus WHERE descricao= \"" + this.description + "\"";
			System.out.println(query);
			rs=null;
			try {
				rs = stmt.executeQuery(query);
				while (rs.next())
				{				
					this.idDescricaoStatus = rs.getInt("idDescricaoStatus");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}						
		}
	}
	
	public Status(int idOperacao, String description, Date beginDate, Connection conn)
	{
		this.description=description;
		this.idOperacao=idOperacao;
		this.setBeginDate(beginDate);
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT idDescricaoStatus FROM descricaostatus WHERE descricao= \"" + this.description + "\"";
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.idDescricaoStatus = rs.getInt("idDescricaoStatus");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (this.idDescricaoStatus==0)
		{
			String sql = "INSERT INTO `mvcapital`.`descricaostatus` (`descricao`) VALUES("
						+ "\"" + this.description + "\"" 
						+ ")";
//			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			query = "SELECT idDescricaoStatus FROM descricaostatus WHERE descricao= \"" + this.description + "\"";
//			System.out.println(query);
			rs=null;
			try {
				rs = stmt.executeQuery(query);
				while (rs.next())
				{				
					this.idDescricaoStatus = rs.getInt("idDescricaoStatus");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}						
		}

		query = "SELECT idStatusOperacao FROM statusoperacao WHERE idOperacao= " + idOperacao + " AND idDescricaoStatus = " + this.idDescricaoStatus;
//		System.out.println(query);
		rs=null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{	
				this.idStatusOperacao = rs.getInt("idStatusOperacao");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
	}
	
	public void setLifeTime()
	{
		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		calBegin.setTime(this.beginDate);
		calEnd.setTime(this.endDate);
		lifeTime = calEnd.getTimeInMillis() - calBegin.getTimeInMillis();		
	}
	
	public void updateLifeTimeString()
	{
		Long allSeconds = lifeTime/1000;
		
		int seconds = (int) (allSeconds%60); 
		int minutes = (int) (((allSeconds-seconds)/60)%60);		
		int hours = (int) (((allSeconds-seconds-60*minutes)/3600));
		String stringSeconds = String.format("%02d", seconds);
		String stringMinutes = String.format("%02d", minutes);
		String stringHours = String.format("%02d", hours);
		
//		System.out.println("AllSeconds:" + allSeconds);
//		System.out.println("Seconds:" + seconds);
//		System.out.println("Minutes:" + minutes);
//		System.out.println("Hours:" + hours);
		lifeTimeString = stringHours+":"+stringMinutes+":"+stringSeconds;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
		this.endDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		this.setLifeTime();
		this.updateLifeTimeString();
	}

	public Long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(Long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public String getLifeTimeString() {
		return lifeTimeString;
	}

	public void setLifeTimeString(String lifeTimeString) {
		this.lifeTimeString = lifeTimeString;
	}

	public int getIdDescricaoStatus() {
		return idDescricaoStatus;
	}

	public void setIdDescricaoStatus(int idDescricaoStatus) {
		this.idDescricaoStatus = idDescricaoStatus;
	}

	public int getIdStatusOperacao() {
		return idStatusOperacao;
	}

	public void setIdStatusOperacao(int idStatusOperacao) {
		this.idStatusOperacao = idStatusOperacao;
	}

	public int getIdOperacao() {
		return idOperacao;
	}

	public void setIdOperacao(int idOperacao) {
		this.idOperacao = idOperacao;
	}
	
}
