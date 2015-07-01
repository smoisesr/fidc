package mvcapital.cnab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import mvcapital.bancopaulista.cnab.CnabPaulista;
import mvcapital.bradesco.cnab.cobranca.retorno.CnabBradesco;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.mysql.MySQLAccess;
import mvcapital.utils.FileManager;
import mvcapital.utils.FileToLines;
import mvcapital.utils.OperatingSystem;


public class CnabFileReader
{
	private static String pathProcessarPaulista = "W:\\Fundos\\Repositorio\\Cnab\\Paulista\\Processar\\"; //$NON-NLS-1$
	private static String pathProcessadoPaulista = "W:\\Fundos\\Repositorio\\Cnab\\Paulista\\Processado\\"; //$NON-NLS-1$
	private static String pathProcessarBradesco = "W:\\Fundos\\Repositorio\\Cnab\\Bradesco\\Processar\\"; //$NON-NLS-1$
	private static String pathProcessadoBradesco = "W:\\Fundos\\Repositorio\\Cnab\\Bradesco\\Processado\\"; //$NON-NLS-1$
	private static String pathConf="conf\\"; //$NON-NLS-1$
	private static Connection conn=null;
	private static int countCNabPaulista=0;
	private static int countCNabPaulistaTrouble=0;
	private static SimpleDateFormat sdfc = new SimpleDateFormat("yyyyMMdd"); //$NON-NLS-1$
	
	public CnabFileReader()
	{

	}
	
	public static void setupPaths()
	{
		if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			pathProcessarPaulista = "W:\\Fundos\\Repositorio\\Cnab\\Paulista\\Processar\\"; //$NON-NLS-1$
			pathProcessadoPaulista = "W:\\Fundos\\Repositorio\\Cnab\\Paulista\\Processado\\"; //$NON-NLS-1$
			pathProcessarBradesco = "W:\\Fundos\\Repositorio\\Cnab\\Bradesco\\Processar\\"; //$NON-NLS-1$
			pathProcessadoBradesco = "W:\\Fundos\\Repositorio\\Cnab\\Bradesco\\Processado\\"; //$NON-NLS-1$
			pathConf="conf\\"; //$NON-NLS-1$
		}
		else
		{
			pathProcessarPaulista = "/home/database/Fundos/Repositorio/Cnab/Paulista/Processar/";  //$NON-NLS-1$
			pathProcessadoPaulista = "/home/database/Fundos/Repositorio/Cnab/Paulista/Processado/"; //$NON-NLS-1$
			pathProcessarBradesco = "/home/database/Fundos/Repositorio/Cnab/Bradesco/Processar/";  //$NON-NLS-1$
			pathProcessadoBradesco = "/home/database/Fundos/Repositorio/Cnab/Bradesco/Processado/";			 //$NON-NLS-1$
			pathConf="/home/Reports/conf/"; //$NON-NLS-1$
		}
		
	}
	
	
	public static void main(String[] args)
	{
		CnabFileReader.setupPaths();
		CnabFileReader.connect();
		ProcessDirectory(pathProcessarPaulista);
		ProcessDirectory(pathProcessarBradesco);
		System.out.println(countCNabPaulista + " Cnabs do Paulista"); //$NON-NLS-1$
		System.out.println(countCNabPaulistaTrouble + " Cnabs do Paulista com Problema!"); //$NON-NLS-1$
	}

	public static void connect()
	{
		MySQLAccess.readConf(pathConf);
		MySQLAccess.connect();
		conn = (Connection) MySQLAccess.getConn();		
	}	

	public static boolean ProcessFile(File file)
	{

		ArrayList<String> lines=new ArrayList<String>();
		try
		{
			lines = FileToLines.readUpperCaseWithoutSemiColons(file);
		} catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		

		if(lines.size()>0)
		{
			String identity=identifyCNAB(lines.get(0));
			if(identity.equals("RETORNO_BRADESCO")) //$NON-NLS-1$
			{
				SimpleDateFormat sdfb = new SimpleDateFormat("ddMMyy"); //$NON-NLS-1$
				CnabBradesco cnab= new CnabBradesco(lines);
				FundoDeInvestimento fundo = identifyFundo(cnab, conn);
				System.out.println("Fundo: " + fundo.getNome()); //$NON-NLS-1$
				Date dataGravacaoArquivo = null;
				try
				{
					dataGravacaoArquivo = sdfb.parse(cnab.getHeader().getDataGravacaoArquivo().getConteudo());
				} catch (ParseException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				File csvFileName = new File(pathProcessarBradesco + sdfc.format(dataGravacaoArquivo) + "_" + fundo.getNomeCurto() + ".csv"); //$NON-NLS-1$ //$NON-NLS-2$
				System.out.println("Creating file " + csvFileName + " from " + file.getName()); //$NON-NLS-1$ //$NON-NLS-2$
				try (PrintWriter output = new PrintWriter(csvFileName))
				{
					output.print(cnab.toCSV());					
					output.close();					
				} catch (FileNotFoundException e)
				{
					System.out.println("Can not write the file"); //$NON-NLS-1$
					e.printStackTrace();
				} 				
				File target = new File(pathProcessadoBradesco + file.getName());
				System.out.println("Copying " + file.getName() + " to " + target.getAbsolutePath()); //$NON-NLS-1$ //$NON-NLS-2$
				OperatingSystem.copyFileUsingFileChannels(file, target);				
				return true;
				
			}			
			else if(identity.equals("REMESSA_PAULISTA444")) //$NON-NLS-1$
			{
				System.out.println("Remessa Paulista 444"); //$NON-NLS-1$
				CnabPaulista cnab = new CnabPaulista(lines);
				FundoDeInvestimento fundo = identifyFundo(cnab, conn);
				System.out.println("Fundo: " + fundo.getNome()); //$NON-NLS-1$
				if(fundo.getNome().length()==0)
				{
					System.out.println("File with trouble " + file.getName()); //$NON-NLS-1$
					setCountCNabPaulistaTrouble(getCountCNabPaulistaTrouble() + 1);
				}
 
				File csvFileName = new File(pathProcessarPaulista + fundo.getNomeCurto() + cnab.getHeader().getDataGravacaoArquivo().getConteudo() + "_" + countCNabPaulista + ".csv"); //$NON-NLS-1$ //$NON-NLS-2$
				System.out.println("Creating file " + csvFileName + " from " + file.getName()); //$NON-NLS-1$ //$NON-NLS-2$				
				PrintWriter output=null;
				try
				{
					output = new PrintWriter(csvFileName);
				} catch (FileNotFoundException e)
				{				
					e.printStackTrace();
				}
				output.print(cnab.toCSVAccess());
				System.out.println("Printing to " + csvFileName); //$NON-NLS-1$
				output.close();						
				File target = new File(pathProcessadoPaulista + file.getName());
				System.out.println("Copying " + file.getName() + " to " + target.getAbsolutePath()); //$NON-NLS-1$ //$NON-NLS-2$
				OperatingSystem.copyFileUsingFileChannels(file, target);
				setCountCNabPaulista(getCountCNabPaulista() + 1);
				return true;
			}
			else if(identity.equals("REMESSA_PAULISTA400")) //$NON-NLS-1$
			{
				System.out.println("Remessa Paulista 400"); //$NON-NLS-1$
				CnabPaulista cnab = new CnabPaulista(lines, 400);
				FundoDeInvestimento fundo = identifyFundo(cnab, conn);
				System.out.println("Fundo: " + fundo.getNome()); //$NON-NLS-1$
				if(fundo.getNome().length()==0)
				{
					System.out.println("File with trouble " + file.getName()); //$NON-NLS-1$
					setCountCNabPaulistaTrouble(getCountCNabPaulistaTrouble() + 1);
				}				
				File csvFileName = new File(pathProcessarPaulista + fundo.getNomeCurto() + cnab.getHeader().getDataGravacaoArquivo().getConteudo() + "_" + countCNabPaulista + ".csv"); //$NON-NLS-1$ //$NON-NLS-2$
				System.out.println("Creating file " + csvFileName + " from " + file.getName()); //$NON-NLS-1$ //$NON-NLS-2$				
				PrintWriter output=null;
				try
				{
					output = new PrintWriter(csvFileName);
				} catch (FileNotFoundException e)
				{				
					e.printStackTrace();
				}
				output.print(cnab.toCSVAccess());
				System.out.println("Printing to " + csvFileName); //$NON-NLS-1$
				output.close();				
				File target = new File(pathProcessadoPaulista + file.getName());
				System.out.println("Copying " + file.getName() + " to " + target.getAbsolutePath()); //$NON-NLS-1$ //$NON-NLS-2$
				OperatingSystem.copyFileUsingFileChannels(file, target);
				setCountCNabPaulista(getCountCNabPaulista() + 1);
				return true;
			}						
		}
		return false;
	}
	
	public static void ProcessDirectory(String rootSourceDirectory)
	{
		ArrayList<File> listTempFiles = FileManager.inDirectory(rootSourceDirectory);
		ArrayList<File> files = new ArrayList<File>();
		if(listTempFiles.size()!=0)
		{
		    for(File file:listTempFiles)
		    {
		      if (file.getName().substring(file.getName().length()-3,file.getName().length()).toLowerCase().equals("txt") //$NON-NLS-1$
		    	  ||file.getName().substring(file.getName().length()-3,file.getName().length()).toLowerCase().equals("rem") //$NON-NLS-1$
		    	  ||file.getName().substring(file.getName().length()-3,file.getName().length()).toLowerCase().equals("ret") //$NON-NLS-1$
		    		  )
		      {
		        files.add(file);
		      }
		    }
		}
		ArrayList<File> processedFiles = new ArrayList<File>();
		for(File file:files)
		{
			if(ProcessFile(file))
			{
				processedFiles.add(file);
			}
		}
		
		try
		{
			Thread.sleep(5000);
		} catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		
		for(File file:processedFiles)
		{
			try
			{
				OperatingSystem.delete(file);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static FundoDeInvestimento identifyFundo(CnabPaulista cnab, Connection conn)
	{
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		String codigoOriginador = cnab.getHeader().getCodigoOriginador().getConteudoLimpo();		
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String query = "SELECT idFundo FROM CodigoDoOriginador WHERE codigo=" + "'" + codigoOriginador + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		System.out.println(query);
		ResultSet rs=null;
		
		int idFundo=0;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		try
		{
			while (rs.next())
			{
				idFundo=rs.getInt("idFundo"); //$NON-NLS-1$
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		if(idFundo!=0)
		{
			fundo=new FundoDeInvestimento(idFundo, conn);
		}
	
		return fundo;
	}
	
	
	public static FundoDeInvestimento identifyFundo(CnabBradesco cnab, Connection conn)
	{
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		String codigoEmpresa = cnab.getHeader().getCodigoEmpresa().getConteudoLimpo();		
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String query = "SELECT idFundo FROM CodigoEmpresaBradesco WHERE codigo=" + "'" + codigoEmpresa + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		System.out.println(query);
		ResultSet rs=null;
		
		int idFundo=0;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		try
		{
			while (rs.next())
			{
				idFundo=rs.getInt("idFundo"); //$NON-NLS-1$
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		if(idFundo!=0)
		{
			fundo=new FundoDeInvestimento(idFundo, conn);
		}
	
		return fundo;
	}

	public static String identifyCNAB(String line)
	{
		System.out.println("Identifying CNAB file"); //$NON-NLS-1$
		if(RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_HEADER_CONCILIACAO_BRADESCO")) //$NON-NLS-1$
		{
			System.out.println("------------------------------------"); //$NON-NLS-1$
			System.out.println("Arquivo de concilia��o do Bradesco"); //$NON-NLS-1$
			System.out.println("------------------------------------"); //$NON-NLS-1$
			return "CONCILIACAO_BRADESCO"; //$NON-NLS-1$
		}
		else if(RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_HEADER_RETORNO_BRADESCO")) //$NON-NLS-1$
		{
			System.out.println("------------------------------------"); //$NON-NLS-1$
			System.out.println("Arquivo de retorno do Bradesco"); //$NON-NLS-1$
			System.out.println("------------------------------------"); //$NON-NLS-1$
			return "RETORNO_BRADESCO"; //$NON-NLS-1$
		}			
		else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_HEADER_REMESSA_PAULISTA")) //$NON-NLS-1$
		{
			if(line.length()==444)
			{
				System.out.println("------------------------------------"); //$NON-NLS-1$
				System.out.println("Arquivo de remessa do Paulista CNAB444"); //$NON-NLS-1$
				System.out.println("------------------------------------"); //$NON-NLS-1$
				return "REMESSA_PAULISTA444"; //$NON-NLS-1$
			}
			else if(line.length()==400)
			{
				System.out.println("------------------------------------"); //$NON-NLS-1$
				System.out.println("Arquivo de remessa do Paulista CNAB400"); //$NON-NLS-1$
				System.out.println("------------------------------------"); //$NON-NLS-1$
				return "REMESSA_PAULISTA400"; //$NON-NLS-1$
			}
			else
			{
				System.out.println("------------------------------------"); //$NON-NLS-1$
				System.out.println("Arquivo de remessa do Paulista Tamanho errado " + line.length()); //$NON-NLS-1$
				System.out.println("------------------------------------"); //$NON-NLS-1$			
				return "DESCONHECIDO"; //$NON-NLS-1$
			}
			
		}
		else
		{
			System.out.println("------------------------------------"); //$NON-NLS-1$
			System.out.println("Arquivo desconhecido"); //$NON-NLS-1$
			System.out.println("------------------------------------"); //$NON-NLS-1$
			
			System.out.println(line);
			return "DESCONHECIDO"; //$NON-NLS-1$				
		}
	}			
	
	public static String getPathProcessarPaulista()
	{
		return pathProcessarPaulista;
	}

	public static void setPathProcessarPaulista(String pathProcessarPaulista)
	{
		CnabFileReader.pathProcessarPaulista = pathProcessarPaulista;
	}

	public static String getPathProcessadoPaulista()
	{
		return pathProcessadoPaulista;
	}

	public static void setPathProcessadoPaulista(String pathProcessadoPaulista)
	{
		CnabFileReader.pathProcessadoPaulista = pathProcessadoPaulista;
	}

	public static String getPathProcessarBradesco()
	{
		return pathProcessarBradesco;
	}

	public static void setPathProcessarBradesco(String pathProcessarBradesco)
	{
		CnabFileReader.pathProcessarBradesco = pathProcessarBradesco;
	}

	public static String getPathProcessadoBradesco()
	{
		return pathProcessadoBradesco;
	}

	public static void setPathProcessadoBradesco(String pathProcessadoBradesco)
	{
		CnabFileReader.pathProcessadoBradesco = pathProcessadoBradesco;
	}


	public static int getCountCNabPaulista()
	{
		return countCNabPaulista;
	}


	public static void setCountCNabPaulista(int countCNabPaulista)
	{
		CnabFileReader.countCNabPaulista = countCNabPaulista;
	}


	public static int getCountCNabPaulistaTrouble()
	{
		return countCNabPaulistaTrouble;
	}


	public static void setCountCNabPaulistaTrouble(int countCNabPaulistaTrouble)
	{
		CnabFileReader.countCNabPaulistaTrouble = countCNabPaulistaTrouble;
	}


	public static SimpleDateFormat getSdfc()
	{
		return sdfc;
	}


	public static void setSdfc(SimpleDateFormat sdfc)
	{
		CnabFileReader.sdfc = sdfc;
	}

}
