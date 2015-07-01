package mvcapital.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RenomearEstoque
{

	public RenomearEstoque()
	{
	}
	
	public static void main(String[] args)
	{
		String pathProcessado="W:\\Fundos\\Operacao\\Estoque\\MIGRACAO\\estoque_historico\\processado\\"; //$NON-NLS-1$
		String pathProcessar="W:\\Fundos\\Operacao\\Estoque\\MIGRACAO\\estoque_historico\\processar\\"; //$NON-NLS-1$
		SimpleDateFormat sdfO = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
		SimpleDateFormat sdfM = new SimpleDateFormat("yyyyMMdd"); //$NON-NLS-1$
		File[] filesArray = new File(pathProcessado).listFiles();
		for(File file:filesArray)
		{
			System.out.println(file.getName());
			ArrayList<String> lines = new ArrayList<String>();
			BufferedReader reader = null;
			try 
			{
				reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
			} catch (FileNotFoundException e1) 
			{
				e1.printStackTrace();
			}
			
			try 
			{
				String line = null;
				while (((line = reader.readLine()) != null) && lines.size() < 1) 
				{
					line = line.toUpperCase();
					if(!line.isEmpty())
					{			
						String[] fields = line.split(";"); //$NON-NLS-1$
						if(!fields[0].equals("NOME_FUNDO")) //$NON-NLS-1$
						{
							System.out.println(line);
							String nomeFundo=fields[0].replace(" ", ""); //$NON-NLS-1$ //$NON-NLS-2$
							Date dataRef=null;
							try
							{
								dataRef = sdfO.parse(fields[17]);
							} catch (ParseException e)
							{
								e.printStackTrace();
							}
							String fileNewName = sdfM.format(dataRef) + "_" + nomeFundo + ".csv"; //$NON-NLS-1$ //$NON-NLS-2$
							System.out.println(sdfM.format(dataRef) + "_" + nomeFundo + ".csv"); //$NON-NLS-1$ //$NON-NLS-2$
							lines.add(line);
							String pathTarget = pathProcessar + fileNewName;
							File target = new File(pathTarget);
							OperatingSystem.copyFileUsingFileChannels(file, target);							
						}
					}
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}
