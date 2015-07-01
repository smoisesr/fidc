package cnab.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import cnab.bradesco.conciliacao.RegisterDetailConciliacaoBradesco;
import cnab.bradesco.conciliacao.WriteDetailConciliacaoBradescoToCSV;
import cnab.remessa.paulista.RegisterDetailRemessaPaulista400;
import cnab.remessa.paulista.RegisterDetailRemessaPaulista444;
import cnab.remessa.paulista.RegisterFileHeaderRemessaPaulista400;
import cnab.remessa.paulista.RegisterFileHeaderRemessaPaulista444;
import cnab.remessa.paulista.RegisterFileTrailerRemessaPaulista400;
import cnab.remessa.paulista.RegisterFileTrailerRemessaPaulista444;
import cnab.remessa.paulista.RemessaPaulista400;
import cnab.remessa.paulista.RemessaPaulista444;
import cnab.utils.FileManager;

public class FileCNABReader 
{
	private ArrayList<File> listFiles = new ArrayList<File>();
	
	private static String pathProcessarPaulista = "W:\\Fundos\\Repositorio\\Cnab\\Paulista\\Processar\\"; //$NON-NLS-1$
	private static String pathProcessadoPaulista = "W:\\Fundos\\Repositorio\\Cnab\\Paulista\\Processado\\"; //$NON-NLS-1$
	private static String pathProcessarBradesco = "W:\\Fundos\\Repositorio\\Cnab\\Bradesco\\Processar\\"; //$NON-NLS-1$
	private static String pathProcessadoBradesco = "W:\\Fundos\\Repositorio\\Cnab\\Bradesco\\Processado\\"; //$NON-NLS-1$
	
//	private static String rootSourceDirectory = "/home/Database/Fontes/Bradesco/Caixa/FIDC Orion/A processar/";
//	private static String rootOutputDirectory = "/home/Database/Fontes/Bradesco/Caixa/FIDC Orion/Processado/";

	
	public static void main(String [ ] args)
	{
//		File file = new File(FileCNABReader.rootSourceDirectory + "test.txt");
//		System.out.println(file.getAbsolutePath());
		FileCNABReader readerPaulista = new FileCNABReader(FileCNABReader.pathProcessarPaulista);
		readerPaulista.copyFiles();
	    readerPaulista.readFiles();
		readerPaulista.deleteFiles();	    

		FileCNABReader readerBradesco = new FileCNABReader(FileCNABReader.pathProcessarBradesco);
		readerBradesco.copyFiles();
	    readerBradesco.readFiles();
		readerBradesco.deleteFiles();	    
	}
	
	
	
	public FileCNABReader(String rootSourceDirectory)
	{
		ArrayList<File> listTempFiles = FileManager.inDirectory(rootSourceDirectory);
		if(listTempFiles.size()!=0)
		{
		    for(File file:listTempFiles)
		    {
		      String extensionFile = file.getName().substring(file.getName().length()-3,file.getName().length());
		      System.out.println("File " + file.getName() + " extension " + extensionFile); //$NON-NLS-1$ //$NON-NLS-2$
		      if (file.getName().substring(file.getName().length()-3,file.getName().length()).toLowerCase().equals("txt") //$NON-NLS-1$
		    	  ||file.getName().substring(file.getName().length()-3,file.getName().length()).toLowerCase().equals("rem") //$NON-NLS-1$
		    	  ||file.getName().substring(file.getName().length()-3,file.getName().length()).toLowerCase().equals("ret") //$NON-NLS-1$
		    		  )
		      {
		        this.listFiles.add(file);
		      }
		    }
		}
	}
	public void copyFiles()
	{
		FileManager.copyFilesToProcessed(this.listFiles, pathProcessadoPaulista);
	}

	public void deleteFiles()
	{		
		try {
			TimeUnit.NANOSECONDS.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		FileManager.deleteFiles(this.listFiles);
	}

	
	public void readFiles()
	{
		for (File fileSource:this.listFiles)
		{
			System.out.println("File to be processed " + pathProcessarPaulista + fileSource.getName()); //$NON-NLS-1$
			File file = new File(pathProcessarPaulista + fileSource.getName());
			String identity = identifyCNAB(file);
			ArrayList<Register> detailsTemp = getDetails(file);
		    String fileName = file.getName().substring(0,file.getName().length()-4);
			String outputFile = pathProcessarPaulista + identity + fileName + ".csv"; //$NON-NLS-1$
			if(identity.equalsIgnoreCase("CONCILIACAO_BRADESCO")) //$NON-NLS-1$
			{
				ArrayList<RegisterDetailConciliacaoBradesco> details = new ArrayList<RegisterDetailConciliacaoBradesco>();
				for (Register detail:detailsTemp)
				{
					details.add((RegisterDetailConciliacaoBradesco) detail);
				}
				WriteDetailConciliacaoBradescoToCSV.writeCsvFile(outputFile, details);
			}
			
			if(identity.equalsIgnoreCase("REMESSA_PAULISTA444")) //$NON-NLS-1$
			{
				RemessaPaulista444 remessaPaulista = buildRemessaPaulista444(file);
				//remessaPaulista.stringRafaelFormat();
				FileWriter writer=null;
		         try {
					writer = new FileWriter(outputFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
		         try {
					writer.append(remessaPaulista.stringRafaelFormat());
				} catch (IOException e) {
					e.printStackTrace();
				}
		         try {
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		         try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(identity.equalsIgnoreCase("REMESSA_PAULISTA400")) //$NON-NLS-1$
			{
				RemessaPaulista400 remessaPaulista = buildRemessaPaulista400(file);
				//remessaPaulista.stringRafaelFormat();
				FileWriter writer=null;
		         try {
					writer = new FileWriter(outputFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
		         try {
					writer.append(remessaPaulista.stringRafaelFormat());
				} catch (IOException e) {
					e.printStackTrace();
				}
		         try {
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		         try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
			
		}
	}
	
	public static String identifyCNAB(File file)
	{
		if(!file.exists())
		{
			System.out.println("Arquivo não existe"); //$NON-NLS-1$
		}
		ArrayList<String> lines = new ArrayList<String>();
		FileReader fileReader = null;
		try
		{
			try
			{
				fileReader = new FileReader(file);
			} catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			}
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fileReader);
			 // if no more lines the readLine() returns null
			 try 
			 {
				String line = null;
				while ((line = br.readLine()) != null) 
				 {
				      // reading lines until the end of the file				
					lines.add(line);
				 }
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			 
			String line = lines.get(0);
			
			System.out.println("Identifying CNAB file"); //$NON-NLS-1$
			if(RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_HEADER_CONCILIACAO_BRADESCO")) //$NON-NLS-1$
			{
				System.out.println("------------------------------------"); //$NON-NLS-1$
				System.out.println("Arquivo de conciliação do Bradesco"); //$NON-NLS-1$
				System.out.println("------------------------------------"); //$NON-NLS-1$
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return "CONCILIACAO_BRADESCO"; //$NON-NLS-1$
			}
			else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_HEADER_REMESSA_PAULISTA")) //$NON-NLS-1$
			{
				if(line.length()==444)
				{
					System.out.println("------------------------------------"); //$NON-NLS-1$
					System.out.println("Arquivo de remessa do Paulista CNAB444"); //$NON-NLS-1$
					System.out.println("------------------------------------"); //$NON-NLS-1$
					new RegisterFileHeaderRemessaPaulista444(line).showRegister();
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return "REMESSA_PAULISTA444"; //$NON-NLS-1$
				}
				else if(line.length()==400)
				{
					System.out.println("------------------------------------"); //$NON-NLS-1$
					System.out.println("Arquivo de remessa do Paulista CNAB400"); //$NON-NLS-1$
					System.out.println("------------------------------------"); //$NON-NLS-1$
					new RegisterFileHeaderRemessaPaulista400(line).showRegister();
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return "REMESSA_PAULISTA400"; //$NON-NLS-1$
				}
				else
				{
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return "DESCONHECIDO"; //$NON-NLS-1$				
				}
				
			}
			else 
			{
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return "DESCONHECIDO"; //$NON-NLS-1$
			}
		}
		finally
		{
			if(fileReader!=null)
			{
				try
				{
					fileReader.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}		

	public static ArrayList<Register> getDetails(File file)
	{
		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<Register> registers = new ArrayList<Register>();
		FileReader fileReader = null;
		try 
		{
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fileReader);
		 try 
		 {
			String line = null;
			while ((line = br.readLine()) != null) 
			 {
				lines.add(line);
			 }
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		 
		for (String line:lines)
		{	
			line=line.replace("AMP;", "    ").replace("amp;", "    "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			if(RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("DETAIL_CONCILIACAO_BRADESCO")) //$NON-NLS-1$
			{
				registers.add(new RegisterDetailConciliacaoBradesco(line)); 
			}
			else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("DETAIL_REMESSA_PAULISTA")) //$NON-NLS-1$
			{				
				if(line.length()==444)
				{
					registers.add(new RegisterDetailRemessaPaulista444(line));
				}
				if(line.length()==400)
				{
					registers.add(new RegisterDetailRemessaPaulista400(line));
				}
			}
		}
		return registers;
	}

	public static RemessaPaulista444 buildRemessaPaulista444(File file)
	{
		RemessaPaulista444 remessaPaulista = new RemessaPaulista444();
		
		ArrayList<RegisterDetailRemessaPaulista444> registersDetail = new ArrayList<RegisterDetailRemessaPaulista444>();
		RegisterFileHeaderRemessaPaulista444 registerHeader = new RegisterFileHeaderRemessaPaulista444();
		RegisterFileTrailerRemessaPaulista444 registerTrailer = new RegisterFileTrailerRemessaPaulista444();
		
		ArrayList<String> lines = new ArrayList<String>();

		FileReader fileReader = null;
		try 
		{
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fileReader);
		 try 
		 {
			String line = null;
			while ((line = br.readLine()) != null) 
			 {
				lines.add(line);
			 }
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		 
		for (String line:lines)
		{	
//			System.out.println("Org|" + line);
			line=line.replace("amp;", "    ").replace("AMP;", "    "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ 
//			System.out.println("Sub|" + line);
			if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("DETAIL_REMESSA_PAULISTA")) //$NON-NLS-1$
			{				
				registersDetail.add(new RegisterDetailRemessaPaulista444(line));				
			}
			else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_HEADER_REMESSA_PAULISTA")) //$NON-NLS-1$
			{
				registerHeader = new RegisterFileHeaderRemessaPaulista444(line);
			}
			else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_TRAILER_REMESSA_PAULISTA")) //$NON-NLS-1$
			{
				registerTrailer = new RegisterFileTrailerRemessaPaulista444(line);
			}
			
		}
		remessaPaulista.setRegisterHeader(registerHeader);
		remessaPaulista.setRegistersDetail(registersDetail);
		remessaPaulista.setRegisterTrailer(registerTrailer);
		return remessaPaulista;
	}
	public static RemessaPaulista400 buildRemessaPaulista400(File file)
	{
		RemessaPaulista400 remessaPaulista400 = new RemessaPaulista400();
		
		ArrayList<RegisterDetailRemessaPaulista400> registersDetail = new ArrayList<RegisterDetailRemessaPaulista400>();
		RegisterFileHeaderRemessaPaulista400 registerHeader = new RegisterFileHeaderRemessaPaulista400();
		RegisterFileTrailerRemessaPaulista400 registerTrailer = new RegisterFileTrailerRemessaPaulista400();
		
		ArrayList<String> lines = new ArrayList<String>();

		FileReader fileReader = null;
		try 
		{
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fileReader);
		 try 
		 {
			String line = null;
			while ((line = br.readLine()) != null) 
			 {
				lines.add(line);
			 }
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		 
		for (String line:lines)
		{	
//			System.out.println("Org|" + line);
			line=line.replace("amp;", "    ").replace("AMP;", "    "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ 
//			System.out.println("Sub|" + line);
			if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("DETAIL_REMESSA_PAULISTA")) //$NON-NLS-1$
			{				
				registersDetail.add(new RegisterDetailRemessaPaulista400(line));				
			}
			else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_HEADER_REMESSA_PAULISTA")) //$NON-NLS-1$
			{
				registerHeader = new RegisterFileHeaderRemessaPaulista400(line);
			}
			else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_TRAILER_REMESSA_PAULISTA")) //$NON-NLS-1$
			{
				registerTrailer = new RegisterFileTrailerRemessaPaulista400(line);
			}
			
		}
		remessaPaulista400.setRegisterHeader(registerHeader);
		remessaPaulista400.setRegistersDetail(registersDetail);
		remessaPaulista400.setRegisterTrailer(registerTrailer);
		return remessaPaulista400;
	}



	public ArrayList<File> getListFiles()
	{
		return listFiles;
	}



	public void setListFiles(ArrayList<File> listFiles)
	{
		this.listFiles = listFiles;
	}



	public static String getPathProcessarPaulista()
	{
		return pathProcessarPaulista;
	}



	public static void setPathProcessarPaulista(String pathProcessarPaulista)
	{
		FileCNABReader.pathProcessarPaulista = pathProcessarPaulista;
	}



	public static String getPathProcessadoPaulista()
	{
		return pathProcessadoPaulista;
	}



	public static void setPathProcessadoPaulista(String pathProcessadoPaulista)
	{
		FileCNABReader.pathProcessadoPaulista = pathProcessadoPaulista;
	}



	public static String getPathProcessarBradesco()
	{
		return pathProcessarBradesco;
	}



	public static void setPathProcessarBradesco(String pathProcessarBradesco)
	{
		FileCNABReader.pathProcessarBradesco = pathProcessarBradesco;
	}



	public static String getPathProcessadoBradesco()
	{
		return pathProcessadoBradesco;
	}



	public static void setPathProcessadoBradesco(String pathProcessadoBradesco)
	{
		FileCNABReader.pathProcessadoBradesco = pathProcessadoBradesco;
	}

}
