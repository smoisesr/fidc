package mvcapital.repositorio;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.io.comparator.SizeFileComparator;

import com.mysql.jdbc.Connection;

import mvcapital.bancopaulista.estoque.HandlerEstoque;
import mvcapital.bancopaulista.liquidadosebaixados.HandlerLiquidadosEBaixados;
import mvcapital.bradesco.cheque.ReadFileBradesco;
import mvcapital.cheque.HandlerBaixas;
import mvcapital.mysql.MySQLAccess;

public class HandlerDownloadedFiles
{
//	private static String pathProcessar = "W:\\Fundos\\Repositorio\\Processar\\"; //$NON-NLS-1$
	private static String pathProcessar = "W:\\Fundos\\Repositorio\\Estoque\\Preprocessado\\"; //$NON-NLS-1$
	@SuppressWarnings("unused")
	private static String pathProcessado = "W:\\Fundos\\Repositorio\\Processado\\"; //$NON-NLS-1$
	private static Connection conn = null;
	private static int cores = Runtime.getRuntime().availableProcessors();

	public HandlerDownloadedFiles()
	{

	}

	public static void main(String[] args)
	{ 
		setupPaths();
		connect();
		System.out.println("Begin time: " + Calendar.getInstance().getTime()); //$NON-NLS-1$
		HandlerEstoque.conn = HandlerDownloadedFiles.conn;
		HandlerLiquidadosEBaixados.conn = HandlerDownloadedFiles.conn;		
		startProcessEstoques();
		startProcessLiquidadosBaixados();
		startProcessBradesco();
		System.out.println("Ending time: " + Calendar.getInstance().getTime()); //$NON-NLS-1$
	}
	
	public static void setupPaths()
	{
		if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			pathProcessar = "W:\\Fundos\\Repositorio\\Estoque\\Preprocessado\\"; //$NON-NLS-1$
			pathProcessado = "W:\\Fundos\\Repositorio\\Processado\\"; //$NON-NLS-1$			
		}
		else
		{
			pathProcessar = "/home/database/Fundos/Repositorio/Estoque/Preprocessado/"; //$NON-NLS-1$
			pathProcessado = "/home/database/Fundos/Repositorio/Processado/"; //$NON-NLS-1$			
		}
	}
	
	public static void connect()
	{
		MySQLAccess.readConf();
//		MySQLAccess mysqlAccess = new MySQLAccess(); 
		MySQLAccess.connect();
		conn = (Connection) MySQLAccess.getConn();		
	}
	
	public static void startProcessBradesco()
	{
		ReadFileBradesco.conn = HandlerDownloadedFiles.conn;
		File[] files = new File(ReadFileBradesco.pathProcessar).listFiles(); 
		Arrays.sort(files, SizeFileComparator.SIZE_COMPARATOR);
		for(File file:files)
		{
			if(file.getName().toLowerCase().contains("bradesco")) //$NON-NLS-1$
			{
				boolean wellProcessed = false;			
				wellProcessed = ReadFileBradesco.processFile(file);
				
				if(wellProcessed)
				{
					if(file.renameTo(new File(ReadFileBradesco.pathProcessado + file.getName())))
					{
						System.out.println(file.getName() + " moved to " + ReadFileBradesco.pathProcessado); //$NON-NLS-1$
					}
					else
					{
						System.out.println("Failed to move " + file.getName()); //$NON-NLS-1$
						System.out.println("Trying to delete it "); //$NON-NLS-1$
						file.deleteOnExit();
					}
				}
			}
		}
		
		HandlerBaixas.processBaixas();
	}
	
	public static void startProcessEstoques()
	{
		File[] filesArray = new File(HandlerDownloadedFiles.pathProcessar).listFiles();
		Arrays.sort(filesArray, SizeFileComparator.SIZE_COMPARATOR);
		System.out.println("filesArray: " + filesArray.length); //$NON-NLS-1$
		
		ArrayList<HandlerEstoque> hes = new ArrayList<HandlerEstoque>();
		if(filesArray.length>0 & existEstoque())
		{
			for(File file:filesArray)
			{
				if(file.getName().toLowerCase().contains("estoque")) //$NON-NLS-1$
				{
				    if (file.isFile() && file.exists()) 
				    {
				    	{
				    		HandlerEstoque he = new HandlerEstoque();
				    		he.setFile(file);
				    		he.start();
				    		hes.add(he);				    		
				    	}
				    }
				}
				while(hes.size()>=cores/2)
				{
					try
					{
						for(HandlerEstoque he:hes)
						{
							System.out.println("Waiting for finishing file " + he.getFile().getName()); //$NON-NLS-1$
						}
						Thread.sleep(500);
					} catch (InterruptedException e)
					{
						e.printStackTrace();   
					}										
				}
				hes = new ArrayList<HandlerEstoque>();
			}			
		}
		
		boolean allDone = false;
		
		while(!allDone)
		{
			if(hes.size()==0)
			{
				allDone=true;			
			}
			else
			{
				for(HandlerEstoque he:hes)
				{
					if(!he.isComplete())
					{
						allDone=false;
						System.out.println("Waiting for Estoque files completion"); //$NON-NLS-1$					
						try
						{
							Thread.sleep(500);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}					
						break;
					}
					else
					{
						System.out.println("Thread" + he.getFile().getName() + " returned true!"); //$NON-NLS-1$ //$NON-NLS-2$
						allDone=true;
					}
				}
			}
		}
		
		if(allDone)
		{
			System.out.println("Estoque files complete " + Calendar.getInstance().getTime()); //$NON-NLS-1$
		}
	}

	public static void startProcessLiquidadosBaixados()
	{
		File[] filesArray = new File(HandlerDownloadedFiles.pathProcessar).listFiles();
		Arrays.sort(filesArray, SizeFileComparator.SIZE_COMPARATOR);
		System.out.println("filesArray: " + filesArray.length); //$NON-NLS-1$

				
		filesArray = new File(HandlerDownloadedFiles.pathProcessar).listFiles();

		System.out.println("filesArray: " + filesArray.length); //$NON-NLS-1$
		
		HandlerLiquidadosEBaixados.processLiquidadosEBaixados();
		
//		if(filesArray.length>0 & existLiquidados())
//		{
//			for(File file:filesArray)
//			{
//				if(file.getName().toLowerCase().contains("titulo")) //$NON-NLS-1$
//				{
//					{
////						HandlerLiquidadosEBaixados hlb = new HandlerLiquidadosEBaixados();
////						hlb.setFile(file);
////						hlb.start();
//						HandlerLiquidadosEBaixados.processLiquidadosEBaixados();
//					}					
//				}
//				else
//				{
//					continue;
//				}
//			}
//		}
		while(existLiquidados())
		{
			try
			{
				Thread.sleep(3000);
				System.out.println("Waiting for LiquidadosBaixados completion " + Calendar.getInstance().getTime()); //$NON-NLS-1$
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	public static boolean existLiquidados()
	{
		File[] filesArray = new File(HandlerDownloadedFiles.pathProcessar).listFiles();
		int iLiquidados=0;
		if(filesArray.length>0)
		{
			for(File file:filesArray)
			{
				if(file.getName().toLowerCase().contains("titulo")) //$NON-NLS-1$
				{
					iLiquidados++;
					return true;
				}
			}		
			if(iLiquidados==0)
			{
				return false;
			}
		}
		return false;
	}
	
	public static boolean existEstoque()
	{
		File[] filesArray = new File(HandlerDownloadedFiles.pathProcessar).listFiles();
		if(filesArray.length>0)
		{
			for(File file:filesArray)
			{
				if(file.getName().toLowerCase().contains("estoque")) //$NON-NLS-1$
				{
					return true;
				}
			}		
		}
		try
		{
			Thread.sleep(3000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
