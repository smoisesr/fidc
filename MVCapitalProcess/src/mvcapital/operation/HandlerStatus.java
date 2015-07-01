package mvcapital.operation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class HandlerStatus 
{
	private static SimpleDateFormat sdtfd =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	public HandlerStatus() 
	{
	
	}

	public static void readStored(Operacao op, Connection conn)
    {
//		*private ArrayList<Status> status=new ArrayList<Status>();
//		*private Date registerTime=Calendar.getInstance().getTime();
		
		ArrayList<Status> statuses = new ArrayList<Status>();
    	System.out.println("-------------------------");
    	System.out.println("Reading stored Status for " + op.getNomeArquivo());
    	System.out.println("-------------------------");

		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "SELECT * FROM statusoperacao WHERE idOperacao=" + op.getIdOperacao() + "  ORDER BY fim";
		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			int countStatus=0;
			while (rs.next())
			{
				Status newStatus = new Status();
				int idStatusOperacao = rs.getInt("idStatusOperacao");
				Date inicio = null;
				try {
					inicio = sdtfd.parse(rs.getString("inicio"));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				Date fim = null;
				try {
					fim = sdtfd.parse(rs.getString("fim"));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				int idDescricaoStatus = rs.getInt("idDescricaoStatus");
				
				System.out.println("idStatusOperacao: " + idStatusOperacao);
				System.out.println("Inicio: " + inicio);
				System.out.println("Fim: " + fim);
				System.out.println("idDescricaoStatus: " + idDescricaoStatus);

				newStatus.setIdStatusOperacao(idStatusOperacao);
				newStatus.setBeginDate(inicio);
				newStatus.setEndDate(fim);
				newStatus.setIdOperacao(op.getIdOperacao());
				newStatus.setIdDescricaoStatus(idDescricaoStatus);
				Statement stmt2=null;
				try {
					stmt2 = (Statement) conn.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				query = "SELECT descricao FROM descricaostatus WHERE idDescricaoStatus=" + idDescricaoStatus;
				System.out.println(query);
				ResultSet rs2 = null;
				try {
					rs2 = stmt2.executeQuery(query);
					while (rs2.next())
					{
						String descricao = rs2.getString("descricao");
						System.out.println("DescricaoStatus: " + descricao);
						newStatus.setDescription(descricao);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				if(countStatus==0)
				{
					op.setRegisterTime(inicio);
				}
				
				if(!newStatus.getDescription().toLowerCase().contains("cancelado"))
				{
					statuses.add(newStatus);
					countStatus=countStatus+1;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		*int idStatusOperacao=0;
//		*int idOperacao=0;
//		*String description="";
//		*int idDescricaoStatus=0;
//		*Date beginDate=Calendar.getInstance().getTime();
//		*Date endDate = Calendar.getInstance().getTime();
//		*Long lifeTime = (long) 0;
//		*String lifeTimeString = "0";

//		idStatusOperacao, 
//		idOperacao, 
//		inicio, 
//		fim, 
//		idDescricaoStatus
    	op.setStatuses(statuses);		
    }
	
	public static void store(Operacao op, Connection conn)
    {       	
    	System.out.println("-------------------------");
    	System.out.println("Storing Status");
    	System.out.println("-------------------------");
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
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

}
