package mvcapital.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import mvcapital.mysql.MySQLAccess;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class WorkingDays 
{
	private static SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public WorkingDays() 
	{
	
	}
		
	public static void main(String[] args)
	{
		String strLine = "MORAES GASPAR &amp; MORAES GASPAR L";
		String line= strLine.toUpperCase().replace(";", "").replace(" &AMP", "");
		
		System.out.println(strLine);
		System.out.println(line);
		System.out.println(line.equals("MORAES GASPAR MORAES GASPAR L"));
		MySQLAccess mysql = new MySQLAccess("192.168.2.160", 3306, "robot", "autoatmvc123", "MVCapital");
		mysql.connect();	
		Connection conn = (Connection) mysql.getConn();
		ArrayList<Integer> prazosUteis = new ArrayList<Integer>();
		
		prazosUteis.add(2);

		
		Date start = Calendar.getInstance().getTime();
		try {
			start = sdfr.parse("06/08/2014");
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		Date end = Calendar.getInstance().getTime();
//		try {
//			end = sdfr.parse("09/09/2014");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		
		System.out.println("Size Prazos: " +  prazosUteis.size());
		System.out.println("DataInicial\tPrazo\tPrazoDiasCorridos\tVenc");
		for(int prazo:prazosUteis)
		{
			int prazoDiasCorridos = (int) allDays(start, afterWorkDays(start,prazo, conn));
			System.out.println(sdfr.format(start)
								+ "\t" + prazo
								+ "\t" + prazoDiasCorridos
								+ "\t" + sdfr.format(afterWorkDays(start,prazo, conn))
								+ "\t" + sdfr.format(beforeWeekDay(start,prazo))
								);
		}
	}
	
	public static Date beforeWeekDay(Date start, int n)
	{
		Calendar c1 = Calendar.getInstance();
	    c1.setTime(start);
	    c1.add(Calendar.DATE, -n);

		Date weekDay  = c1.getTime();
		
//		while(weekDays(start,weekDay)<n)
//		{
//			c1.add(Calendar.DATE, -1);
//			weekDay = c1.getTime();
//		}
		
		return weekDay;	
	}

	public static Date afterWorkDays(Date start, int n, Connection conn)
	{
		Date workingDay = afterWeekDay(start, n);
//		Calendar cal = Calendar.getInstance(); 

		while(countWorkingDays(start, workingDay, conn) < (n-1))
		{
			workingDay = afterWeekDay(workingDay, 1);
		}
//		System.out.println("weekDays: " + weekDays(start, workingDay) + " hollidays: " + countValidHollidays(start, workingDay, conn));
		return workingDay;
	}
	
	public static long countValidHollidays(Date start, Date end, Connection conn)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int countHollidays = 0;
		try 
		{
			Statement stmt = (Statement) conn.createStatement();
			String query = "SELECT count(data) AS COUNT FROM Feriado WHERE data >= '" + sdf.format(start) +"' and data <= '" + sdf.format(end) +"' and fimdesemana=0";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				countHollidays = rs.getInt("COUNT");
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return countHollidays;
	}
	public static long countWorkingDays(Date start, Date end, Connection conn)
	{

		long countHollidays = countValidHollidays(start, end, conn);
		long countWeekDays = weekDays(start,end);
		return countWeekDays - countHollidays;
	}
	
	public static Date afterWeekDay(Date start, int n)
	{
	    Calendar c1 = Calendar.getInstance();
	    c1.setTime(start);
	    c1.add(Calendar.DATE, n);

		Date weekDay  = c1.getTime();
		
		while(weekDays(start,weekDay)<n)
		{
			c1.add(Calendar.DATE, 1);
			weekDay = c1.getTime();
		}
		
		return weekDay;
	}

	public static long allDays(Date start, Date end)
	{
	    //Ignore argument check

	    Calendar c1 = GregorianCalendar.getInstance();
	    c1.setTime(start);
//	    int w1 = c1.get(Calendar.DAY_OF_WEEK);
//	    c1.add(Calendar.DAY_OF_WEEK, -w1 + 1);

	    Calendar c2 = GregorianCalendar.getInstance();
	    c2.setTime(end);
//	    int w2 = c2.get(Calendar.DAY_OF_WEEK);
//	    c2.add(Calendar.DAY_OF_WEEK, -w2 + 1);

	    //end Saturday to start Saturday 
	    long days = (c2.getTimeInMillis()-c1.getTimeInMillis())/(1000*60*60*24);
//	    long daysWithoutSunday = days-(days*2/7);

	    return days;
	}
	
	public static long weekDays(Date start, Date end)
	{
	    //Ignore argument check

	    Calendar c1 = GregorianCalendar.getInstance();
	    c1.setTime(start);
	    int w1 = c1.get(Calendar.DAY_OF_WEEK);
	    c1.add(Calendar.DAY_OF_WEEK, -w1 + 1);

	    Calendar c2 = GregorianCalendar.getInstance();
	    c2.setTime(end);
	    int w2 = c2.get(Calendar.DAY_OF_WEEK);
	    c2.add(Calendar.DAY_OF_WEEK, -w2 + 1);

	    //end Saturday to start Saturday 
	    long days = (c2.getTimeInMillis()-c1.getTimeInMillis())/(1000*60*60*24);
	    long daysWithoutSunday = days-(days*2/7);

	    if (w1 == Calendar.SUNDAY) {
	        w1 = Calendar.MONDAY;
	    }
	    if (w2 == Calendar.SUNDAY) {
	        w2 = Calendar.MONDAY;
	    }
	    return daysWithoutSunday-w1+w2;
	}

	public static SimpleDateFormat getSdfr() {
		return sdfr;
	}

	public static void setSdfr(SimpleDateFormat sdfr) {
		WorkingDays.sdfr = sdfr;
	}

	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	public static void setSdf(SimpleDateFormat sdf) {
		WorkingDays.sdf = sdf;
	}
}
