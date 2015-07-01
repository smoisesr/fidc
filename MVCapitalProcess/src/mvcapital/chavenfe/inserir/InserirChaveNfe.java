package mvcapital.chavenfe.inserir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import mvcapital.utils.FileToLines;

public class InserirChaveNfe
{
	private static String pathProcessar = "W:\\Fundos\\Operacao\\ChaveNFe\\InserirChaveNFe\\Processar\\"; //$NON-NLS-1$
	private static String pathProcessado = "W:\\Fundos\\Operacao\\ChaveNFe\\InserirChaveNFe\\Processado\\"; //$NON-NLS-1$

	public InserirChaveNfe()
	{

	}
	
	public static void main(String[] args)
	{
		InserirChaveNfe.inserirChaveNFE();
	}

	public static void inserirChaveNFE()
	{
		File[] filesArray = new File(pathProcessar).listFiles();
		ArrayList<File> cnabFiles = new ArrayList<File>();
		ArrayList<SetFiles> setFiles = new ArrayList<SetFiles>();
		for(File file:filesArray)
		{
			if(file.getName().toLowerCase().contains(".rem")) //$NON-NLS-1$
			{
				System.out.println("CNAB: " + file.getName()); //$NON-NLS-1$
				cnabFiles.add(file);
			}
		}
		
		for(File cnabFile:cnabFiles)
		{
			File csvFile = haveCSV(cnabFile);
			
			if(csvFile!=null)
			{
				SetFiles set = new SetFiles(cnabFile, csvFile);
				setFiles.add(set);
				buildPairs(set);
				String cnabMod = buildCnabFile(set);
				
				File novoCnab = new File(pathProcessado+cnabFile.getName());
				if(cnabMod.length()>0)
				{
					try (PrintWriter output = new PrintWriter(novoCnab))
					{
						output.print(cnabMod);
						output.close();						
					} catch (FileNotFoundException e)
					{
						e.printStackTrace();
					} 
				}
			}
		}
	}	
	
	public static String buildCnabFile(SetFiles set)
	{
		ArrayList<String> lines = FileToLines.readLowerCase(set.getCnabFile());
		ArrayList<String> linesMod = new ArrayList<String>();
		
		for(String line:lines)
		{
			if(line.startsWith("1")) //$NON-NLS-1$
			{
				linesMod.add(lineBuild(line, set.getPairs()));
			}
			else
			{
				linesMod.add(line.toUpperCase());
			}
		}
		String cnabMod=""; //$NON-NLS-1$
		for(int i=0;i<linesMod.size();i++)
		{
			if(i<linesMod.size()-1)
			{
				cnabMod+=linesMod.get(i)+"\n"; //$NON-NLS-1$
			}
			else
			{
				cnabMod+=linesMod.get(i);
			}
		}
		return cnabMod;
	}	
	
	public static String lineBuild(String line, ArrayList<Pair> pairs)
	{
		String lineBuild=""; //$NON-NLS-1$
		String nroDoc=line.substring(110, 115);
		for(Pair pair:pairs)
		{
			if(nroDoc.contains(pair.getNroDocumento()))
			{
				lineBuild=line.substring(0,394) + pair.getNroChaveNFE() + line.substring(438,444);
				System.out.println(line);
				System.out.println(lineBuild);
				break;
			}
		}		
		return lineBuild.toUpperCase();
	}
	
	public static void buildPairs(SetFiles set)
	{
		ArrayList<String> lines = FileToLines.readLowerCase(set.getCsvFile());
		for(String line:lines)
		{
			if(!line.toLowerCase().contains("nronota")) //$NON-NLS-1$
			{
				String[] fields = line.split(";"); //$NON-NLS-1$
				String nroNota = fields[0];
				String nroChaveNFE = fields[1];
				Pair pair = new Pair(nroNota,nroChaveNFE);
				set.getPairs().add(pair);
			}
		}
	}
	
	public static File haveCSV(File cnabFile)
	{
		File[] filesArray = new File(pathProcessar).listFiles();
		String nameCSVFile = cnabFile.getAbsolutePath().replace(".REM", ".csv"); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("Searching " + nameCSVFile); //$NON-NLS-1$
		for(File file:filesArray)
		{
			System.out.println(file.getName());
			if(file.getAbsolutePath().contains(nameCSVFile))
			{
				System.out.println("Returned file!"); //$NON-NLS-1$
				return file;
			}
		}
		return null;
	}

	public static String getPathProcessar()
	{
		return pathProcessar;
	}

	public static void setPathProcessar(String pathProcessar)
	{
		InserirChaveNfe.pathProcessar = pathProcessar;
	}

	public static String getPathProcessado()
	{
		return pathProcessado;
	}

	public static void setPathProcessado(String pathProcessado)
	{
		InserirChaveNfe.pathProcessado = pathProcessado;
	}
}
