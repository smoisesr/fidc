package mvcapital.utils;

import java.util.Calendar;

public class RepeatMultiplyingTable
{

	public RepeatMultiplyingTable()
	{

	}

	public static void main(String[] args)
	{
		while(true)
		{
			for(int i=0;i<=12;i++)
			{
				for(int j=0;j<=12;j++)
				{
					System.out.println(i+ "x"+j+" = " + i*j); //$NON-NLS-1$ //$NON-NLS-2$
				}
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
