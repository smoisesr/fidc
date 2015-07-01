package mvcapital.utils;

import java.util.Calendar;

public class TestDates
{

	public TestDates()
	{
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args)
	{
		Calendar calNow = Calendar.getInstance();
		System.out.println(calNow.get(Calendar.DAY_OF_WEEK));
		if(calNow.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calNow.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
		{
			System.out.println("Waiting to pass the weekend!!"); //$NON-NLS-1$
			try
			{
				Thread.sleep(5000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}				
		}
		else
		{
			System.out.println("Just a week day!");
		}
		
	}
}
