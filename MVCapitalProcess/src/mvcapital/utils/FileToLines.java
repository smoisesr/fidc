package mvcapital.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileToLines
{

	public FileToLines()
	{

	}

	@SuppressWarnings("resource")
	public static ArrayList<String> readLowerCase(File file)
	{
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader reader = null;
		System.out.println("Reading file " + file.getName()); //$NON-NLS-1$
		System.out.println("------------------"); //$NON-NLS-1$
		try
		{
			reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try
		{
			String line = null;	
			while ((line = reader.readLine()) != null) 
			{
				line = line.toLowerCase();
				if(!line.isEmpty())
				{
					lines.add(line);
				}
			}
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			reader.close();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		return lines;
	}
	
	@SuppressWarnings("resource")
	public static ArrayList<String> readUpperCase(File file)
	{
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader reader = null;
		System.out.println("Reading file " + file.getName()); //$NON-NLS-1$
		System.out.println("------------------"); //$NON-NLS-1$
		try
		{
			reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try
		{
			String line = null;	
			while ((line = reader.readLine()) != null) 
			{
				line = line.toUpperCase();
				if(!line.isEmpty())
				{
					lines.add(line);
				}
			}
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			reader.close();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		return lines;
	}

	@SuppressWarnings("resource")
	public static ArrayList<String> readUpperCaseWithoutSemiColons(File file) throws FileNotFoundException
	{
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader reader = null;
		System.out.println("*************************"); //$NON-NLS-1$
		System.out.println("Reading file " + file.getName()); //$NON-NLS-1$
		System.out.println("------------------"); //$NON-NLS-1$
		reader = new BufferedReader(new FileReader(file.getAbsolutePath()));

		try
		{
			String line = null;	
			while ((line = reader.readLine()) != null) 
			{
				line = line.toUpperCase();
				if(!line.isEmpty())
				{
					lines.add(line.replace(";"," ")); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			reader.close();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		return lines;
	}
	
	public static ArrayList<String> read(File file)
	{
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader reader = null;
		System.out.println("Reading file " + file.getName()); //$NON-NLS-1$
		System.out.println("------------------"); //$NON-NLS-1$
		try
		{
			reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try
		{
			String line = null;	
			while ((line = reader.readLine()) != null) 
			{
				if(!line.isEmpty())
				{
					lines.add(line);
				}
			}
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			reader.close();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		return lines;
	}
	
}
