package zipextractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

import pdfcarteiratotext.TextFromPDFAuto;
//import xlstocsv.XlsToCSV;
import administrador.Administrador;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import ftpclient.FilesDownloaderFTP;
import ftpclient.LoginFTP;
import fundo.FundoDeInvestimento;
import utils.OperatingSystem;

/**
 * 
 * @author MVCapital - Moisés Ito
 *
 */
public class ZipExtractor
{
	private FilesDownloaderFTP fdFTP = new FilesDownloaderFTP();
	private ArrayList<Administrador> adms = new ArrayList<Administrador>();
	private ArrayList<FundoDeInvestimento> fundos = new ArrayList<FundoDeInvestimento>(); 
	private ArrayList<String> tempFilesNames = new ArrayList<String>();
	private String separator = OperatingSystem.getSeparator();

	/**
	 * 
	 * @param adms List of administrators
	 * @param fundos List of funds
	 */
	public ZipExtractor(ArrayList<Administrador> adms, ArrayList<FundoDeInvestimento> fundos)
	{
		this.adms = adms;
		this.fundos = fundos;
		this.fdFTP = new FilesDownloaderFTP(this.fundos);
	}
	
	/**
	 * This Main method performs all the actions
	 */
	public static void main(String[] args) 
    {
		ArrayList<Administrador> adms = new ArrayList<Administrador>();
		ArrayList<FundoDeInvestimento> fundos = new ArrayList<FundoDeInvestimento>();
		System.out.println("Operating system " + System.getProperty("os.name"));
		String rootLocalDir = OperatingSystem.getRootLocalDir();
		String zipPassword = "actas904";
		
		LoginFTP loginOrion = new LoginFTP("ftp.personaltrader.com.br", 990, "orion", "!F1Dc0R10n#");
		
		adms.add(new Administrador(Administrador.Lista.Petra, "RECEBER"));		
		fundos.add(new FundoDeInvestimento(FundoDeInvestimento.Lista.Orion, adms.get(0), loginOrion, rootLocalDir, zipPassword));
		System.out.println("Fundos Listados " + fundos.size());
		if(fundos.size()!=0)
		{
			for(FundoDeInvestimento fundo:fundos)
			{
				System.out.println(fundo.getLocalName());
			}
		}
		ZipExtractor zipE = new ZipExtractor(adms, fundos);
		zipE.unzipFiles();
    }
	
	/**
	 * Unzip all the files from all the funds
	 */
	public void unzipFiles()
	{
		this.fdFTP.downloadAllFunds();
	    
	    for (FundoDeInvestimento fundo:this.fundos)
	    {
	    	String zipPassword = fundo.getZipPassword();
	    	if (!fundo.getFilesDownloaded().isEmpty())
	    	{
		    	System.out.println("-------------------------");
		    	System.out.println("Files downloaded for " + fundo.getLocalName());
		    	int iFileDownloaded=0;
		    	for(File file:fundo.getFilesDownloaded())
		    	{
		    	    String source = file.getAbsolutePath();
		    	    String fileExtension = file.getName().substring(file.getName().length()-4,file.getName().length()).toLowerCase();
		    	    
		    	    if(fileExtension.equals(".zip"))
		    	    {
			    	    String tempFolderName = file.getName().substring(0, file.getName().length()-4);
			    	    String tempDestination = fundo.getUnzipPath() + separator + tempFolderName;
			    	    System.out.println((iFileDownloaded+1) + "\tTemp destination for unzip " + tempDestination);
			    		this.extractFile(source, zipPassword, tempDestination);
			    		this.tempFilesNames.add(tempDestination);			    		
		    	    }
		    	    
		    	    else
		    	    {
		    	    	String tempDestination = fundo.getUnzipPath() + "\\Others";
		    	    			    	    	
		    			File saveDir = new File(tempDestination);	
		    			
		    			if(!saveDir.exists())
		    			{
		    				//System.out.println("Folder " + completePathDirectory + " dosn't exist ");
		    				saveDir.mkdirs();
			    	    	//tempDestination = tempDestination + separator + file.getName();
		    			}
		    	    	
		    	    	tempDestination = tempDestination + separator + file.getName();
		    	    	File destiny = new File(tempDestination);
		    	    	this.tempFilesNames.add(tempDestination);
		    	    	
		    	    	this.copyRecentFile(file, destiny);
		    	    }
		    	    iFileDownloaded++;
		    	}
		    	
		    	System.out.println("-----------------------------");
		    	System.out.println("After unzip all files ...");
//		    	this.tempFilesNames.add("W:\\Fontes\\Petra\\Unzip\\FIDC Orion\\ORION_Cotas_.pdf");
//		    	TextFromPDFAuto.extractFromPDF(new File("W:\\Fontes\\Petra\\Unzip\\FIDC Orion\\ORION_Cotas_.pdf"), new File("W:\\Fontes\\Petra\\Unzip\\FIDC Orion\\ORION_Cotas_.txt"));
//		    	
//				File decriptedPDF=new File("W:\\Fontes\\Petra\\Unzip\\FIDC Orion\\ORION_Cotas_.pdf");
//				try
//				{
//					decriptedPDF = TextFromPDFAuto.openPDFDoc(new File("W:\\Fontes\\Petra\\Unzip\\FIDC Orion\\ORION_Cotas_.pdf"), fundo.getZipPassword());
//		    		File destiny = new File("W:\\Fontes\\Petra\\Unzip\\FIDC Orion\\ORION_Cotas_"+".txt");
//		    		System.out.println("Destination: " + destiny.getPath());
//		    		//File destiny = new File(destinationFolder+separator+"Cotas.txt");
////		    		destiny.delete();
//		    		TextFromPDFAuto.extractFromPDF(decriptedPDF, destiny);
//
//				} catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//
		    	for(String nameFileTemp:this.getTempFilesNames())
		    	{
		    		String lowerFileName = nameFileTemp.toLowerCase();
		    		if (lowerFileName.contains("carteira")||lowerFileName.contains("cotas")||lowerFileName.contains("existencia")||lowerFileName.contains("posicao")||lowerFileName.contains("posição"))
		    		{
		    			if(lowerFileName.contains("carteira")&&lowerFileName.contains("dm"))
		    			{
		    				
		    			}
		    			else
		    			{
				    		System.out.println("Manipulating " + nameFileTemp);
				    		this.treatmentZipFolder(nameFileTemp, fundo);
		    			}
		    		}
		    		else
		    		{
		    			System.out.println("Skipping " + nameFileTemp);
		    		}
		    	}
		    	
		    	iFileDownloaded=0;
		    	for(File file:fundo.getFilesDownloaded())
		    	{
		    		
		    	    String fileExtension = file.getName().substring(file.getName().length()-4,file.getName().length()).toLowerCase();
		    	    if(fileExtension.equals(".zip"))
		    	    {
		    	    	String tempFolderName = file.getName().substring(0, file.getName().length()-4);
		    	    	String tempDestination = fundo.getUnzipPath() + separator + tempFolderName;
		    	    	this.deleteTempDestination(tempDestination);
		    	    }
		    	    else
		    	    {
		    	    	String tempDestination = fundo.getUnzipPath() + "\\Others";
		    	    	this.deleteTempDestination(tempDestination);
		    	    }
		    	    iFileDownloaded++;
		    	    System.out.println(iFileDownloaded + " deleted");
		    	}		    	
	    	}
	    	System.out.println("--------------------------");
	    	System.out.println("All temp files deleted process finished!");
	    	System.out.println("--------------------------");
	    }
	}
	
	/**
	 * 
	 * @param folderTempZip receives the temporary path for "zip" files
	 * @return
	 */
	public String keyFolder(String folderTempZip)
	{
		String keyFolder="";
		
		if (folderTempZip.toLowerCase().contains("carteira"))
		{
			keyFolder = "carteira";
		}
		else if (folderTempZip.toLowerCase().contains("cotas"))
		{
			keyFolder = "cotas";
		}
		else if (folderTempZip.toLowerCase().contains("posição"))
		{
			keyFolder = "posição";
		}
		else if (folderTempZip.toLowerCase().contains("posicao"))
		{
			keyFolder = "posicao";
		}		
		else if (folderTempZip.toLowerCase().contains("existencia"))
		{
			keyFolder = "existencia";
		}
		else if (folderTempZip.toLowerCase().contains("others"))
		{
			keyFolder = "others";
		}

		return keyFolder;
	}
	
	/**
	 * 
	 * @param folderTempZip This folder contents are the unzipped files
	 * @param fundo This fund have all the settings for paths, both, local and remote
	 */
	public void treatmentZipFolder(String folderTempZip, FundoDeInvestimento fundo)
	{
		String keyFolder=this.keyFolder(folderTempZip);

		System.out.println("KeyFolder: " + keyFolder + " for choose treatment");
		switch (keyFolder)
		{
			case "carteira":
				treatmentCarteira(folderTempZip, fundo);
				break;
			case "cotas":
				treatmentCotas(folderTempZip, fundo);
				break;
			case "posição":
				treatmentPosicao(folderTempZip, fundo);
				break;
			case "posicao":
				treatmentPosicao(folderTempZip, fundo);
				break;				
			case "existencia":
				treatmentExistencia(folderTempZip, fundo);
				break;
			case "others":
				treatmentOthers(folderTempZip, fundo);
				break;

			default:
				break;
		}
	}

	/**
	 * 
	 * @param folderTempCarteira Path to files for Carteira
	 * @param fundo
	 */
	public void treatmentCarteira(String folderTempCarteira, FundoDeInvestimento fundo)
	{
		ArrayList<File> localFolderFiles = FilesDownloaderFTP.filesInDirectory(folderTempCarteira);
		System.out.println("Treatment for zip file Carteira");
		if (localFolderFiles!=null)
		{
			for(File file:localFolderFiles)
			{			
				System.out.println(file.getName());
				String fileExtension = file.getName().substring(file.getName().length()-3,file.getName().length()).toLowerCase();
				//System.out.println("File extension " + fileExtension);
				String destinationFolder="";
	
				String tempNameFolder = folderTempCarteira.replace(separator, ";");
				String[] fileOutputNameSplit =  tempNameFolder.split(";");
				String fileOutputName = fileOutputNameSplit[fileOutputNameSplit.length-1];
				
				String subFolderName = FilesDownloaderFTP.folderNameFromDate(FilesDownloaderFTP.extractDateFromDirectory(fileOutputName));
				
				if(fileExtension.equals("xls")||fileExtension.equals("txt"))
				{
	
					if(fileExtension.equals("xls"))
					{
						String fileNameDestiny = "Caixa_" + fundo.getName() + "_" + FilesDownloaderFTP.extractDateFromDirectory(fileOutputName) + ".xls";
						System.out.println("FileName " + fileNameDestiny);
						for(String pasta:fundo.getLocalFolders())
						{
							System.out.println(pasta);
							if(pasta.contains("Caixa")&&pasta.contains("A Processar"))
							{
								destinationFolder = pasta;
								break;
							}
						}
						
			    		File destiny = new File(destinationFolder+separator+fileNameDestiny);
			    		
			    		Date lastModSource = new Date(file.lastModified());
			    		Date lastModDestiny = new Date(destiny.lastModified());
	
			    		if (destiny.exists())
			    		{
			    			System.out.println("File " + file.getAbsolutePath() + " exist");
			    			System.out.println("New file last modification " + lastModSource.toString());
			    			System.out.println("Old file last modification " + lastModDestiny.toString());
			    			if(lastModSource.compareTo(lastModDestiny) > 0)
			    			{
			    				this.copyRecentFile(file, destiny);			    				
//			    				try
//								{
//									XlsToCSV.convertExcelToCsv(file, destiny);
//									System.out.println("Saved file " + destiny);
//									
//								} catch (IOException e)
//								{
//									e.printStackTrace();
//								}
			    			}
			    			else
			    			{
			    				System.out.println("File " + destiny + " is updated");
			    			}
			    		}		    		
			    		else 
			    		{
			    			String tempPath = destiny.getAbsolutePath();
			    			
			    			destiny.delete();
			    			
			    			destiny = new File(tempPath);
			    			this.copyRecentFile(file, destiny);	
//			    			try
//							{
//			    				this.copyRecentFile(file, destiny);		
//								//XlsToCSV.convertExcelToCsv(file, destiny);
//								System.out.println("Saved file " + destiny);
//								
//							} catch (IOException e)
//							{
//								e.printStackTrace();
//							}
			    		}
			    		
			    		
			    		
			    	//	this.copyRecentFile(file, destiny);				
			    	}
					
					if(fileExtension.equals("txt"))
					{
						String fileNameTXT = "Carteira_" + fundo.getName() + "_" + FilesDownloaderFTP.extractDateFromDirectory(fileOutputName) + ".txt";
						System.out.println("FileNameTXT " + fileNameTXT);
						for(String pasta:fundo.getLocalFolders())
						{
							if(pasta.contains("Carteira")&&pasta.contains("A Processar"))
							{
								destinationFolder = pasta;				
							}
						}
						
			    		File destiny = new File(destinationFolder+separator+fileNameTXT);
			    		this.copyRecentFile(file, destiny);
					}
				}
				else if (fileExtension.equals("pdf"))
				{
					if(fileExtension.equals("pdf") && file.getName().contains("Sub"))
					{
						for(String pasta:fundo.getLocalFolders())
						{
							if(pasta.contains("Carteira"))
							{
								destinationFolder = pasta + separator + subFolderName;
								this.checkDirectory(destinationFolder);
								break;
							}
						}
						
						String fileNamePDF = "Carteira_Sub_" + fundo.getName() + "_" + FilesDownloaderFTP.extractDateFromDirectory(fileOutputName) + ".pdf";
						System.out.println("FileNamePDF Sub " + fileNamePDF);					
						
			    		File destiny = new File(destinationFolder+separator+fileNamePDF);
			    		
			    		this.copyRecentFile(file, destiny);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param directory This will check if the directory exist, and will create it if this not exist
	 */
	public void checkDirectory(String directory)
	{
		File dir = new File(directory);
		if(!dir.exists())
		{
			dir.mkdirs();
			System.out.println("Folder " + dir + " created ");
		}
	}
	
	/**
	 * 
	 * @param source File
	 * @param destiny File
	 */
	public void copyRecentFile(File source, File destiny)
	{		
		Date lastModSource = new Date(source.lastModified());
		Date lastModDestiny = new Date(destiny.lastModified());
		/**
		 * In value > 0, lastModSource is more recent than lastModDestiny
		 */
		if (destiny.exists())
		{
			System.out.println("File " + source + " exist");
			System.out.println("New file last modification " + lastModSource.toString());
			System.out.println("Old file last modification " + lastModDestiny.toString());
			if(lastModSource.compareTo(lastModDestiny) > 0)
			{
				try
				{
					destiny.delete();
					Path tempPath = destiny.toPath();
					Files.copy(source.toPath(), tempPath);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			else
			{
				System.out.println("File " + destiny + " is updated");
			}
		}
		else 
		{
			String tempPath = destiny.getAbsolutePath();
			
			destiny.delete();
			
			destiny = new File(tempPath);
			
			try
			{
				Files.copy(source.toPath(), destiny.toPath());
				System.out.println("Copying " + source.toPath() + " to " + destiny.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param folderTempCotas
	 * @param fundo
	 */
	public void treatmentOthers(String folderTempCotas, FundoDeInvestimento fundo)
	{
		File file = new File(folderTempCotas);
		
		String tempNameFolder = folderTempCotas.replace(separator, ";");
		String[] fileOutputNameSplit =  tempNameFolder.split(";");
		String fileOutputName = fileOutputNameSplit[fileOutputNameSplit.length-1];
		
		String fileExtension = fileOutputName.substring(fileOutputName.length()-4, fileOutputName.length());
		
		System.out.println("*********************************");
		System.out.println("Treatment for other files ");
		System.out.println("*********************************");
		String destinationFolder="";
		
		if(fileOutputName.contains("Recompra"))
		{
			for(String pasta:fundo.getLocalFolders())
			{
				if(pasta.contains("Baixas")&&pasta.contains("A Processar"))
				{
					destinationFolder = pasta;
					break;
				}
			}
			
    	    String dateString = FilesDownloaderFTP.extractDateFromFileName(fileOutputName);
    		File destiny = new File(destinationFolder+"\\Recompra_"+fundo.getName()+"_"+dateString+fileExtension);
    		this.copyRecentFile(file, destiny);
		}
		else if(fileOutputName.contains("titulospagos"))
		{
			for(String pasta:fundo.getLocalFolders())
			{
				if(pasta.contains("Baixas")&&pasta.contains("Titulos Pagos")&&pasta.contains("A Processar"))
				{
					destinationFolder = pasta;
					break;
				}
			}

    	    String dateString = FilesDownloaderFTP.extractDateFromFileName(fileOutputName);
    		File destiny = new File(destinationFolder+"\\TitulosPagos_"+fundo.getName()+"_"+dateString+fileExtension);
    		this.copyRecentFile(file, destiny);
		}
		else if(fileOutputName.contains("titulosvencidos"))
		{
			for(String pasta:fundo.getLocalFolders())
			{
				if(pasta.contains("Baixas")&&pasta.contains("Titulos Pagos")&&pasta.contains("A Processar"))
				{
					destinationFolder = pasta;
					break;
				}
			}

    	    String dateString = FilesDownloaderFTP.extractDateFromFileName(fileOutputName);
    		File destiny = new File(destinationFolder+"\\TitulosVencidosPagos_"+fundo.getName()+"_"+dateString+fileExtension);
    		this.copyRecentFile(file, destiny);
		}
		
	}
	
	/**
	 * 
	 * @param folderTempCotas
	 * @param fundo
	 */
	public void treatmentCotas(String folderTempCotas, FundoDeInvestimento fundo)
	{
		ArrayList<File> localFolderFiles = FilesDownloaderFTP.filesInDirectory(folderTempCotas);
		
		String tempNameFolder = folderTempCotas.replace(separator, ";");
		String[] fileOutputNameSplit =  tempNameFolder.split(";");
		String fileOutputName = fileOutputNameSplit[fileOutputNameSplit.length-1];
		
		System.out.println("Treatment for zip file Cotas");
		String destinationFolder="";
		for(String pasta:fundo.getLocalFolders())
		{
			System.out.println(pasta);
			if(pasta.contains("Cotas") && pasta.contains("A Processar"))
			{
				destinationFolder = pasta;
				break;
			}
		}
		
		for(File file:localFolderFiles)
		{			
			System.out.println(file.getName());
	    	//Files.copy(new File(file.getAbsolutePath()).toPath(), new File(destinationFolder+separator+file.getName()).toPath());
			File decriptedPDF=new File(file.getAbsolutePath());
			try
			{
				decriptedPDF = TextFromPDFAuto.openPDFDoc(file, fundo.getZipPassword());
	    		File destiny = new File(destinationFolder+separator+fileOutputName+".txt");
	    		System.out.println("Destination: " + destiny.getPath());
	    		//File destiny = new File(destinationFolder+separator+"Cotas.txt");
	    		destiny.delete();
	    		TextFromPDFAuto.extractFromPDF(decriptedPDF, destiny);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * 
	 * @param folderTempPosicao
	 * @param fundo
	 */
	public void treatmentPosicao(String folderTempPosicao, FundoDeInvestimento fundo)
	{
		ArrayList<File> localFolderFiles = FilesDownloaderFTP.filesInDirectory(folderTempPosicao);
		System.out.println("Treatment for zip file Posição");
		
		String tempNameFolder = folderTempPosicao.replace((fundo.getLocalName() + separator), ";");
		String[] fileOutputNameSplit =  tempNameFolder.split(";");
		String fileOutputName = fileOutputNameSplit[fileOutputNameSplit.length-1];
		
		String subFolderName = FilesDownloaderFTP.folderNameFromDate(FilesDownloaderFTP.extractDateFromDirectory(fileOutputName));
		
		String destinationFolder="";
		//System.out.println("Lista de pastas");
		for(String pasta:fundo.getLocalFolders())
		{
			//System.out.println(pasta);
			if(pasta.contains("Investidor"))
			{
				destinationFolder = pasta + separator + subFolderName;
				//System.out.println("Escolhido " + destinationFolder);
			}
		}

		File destinyFolder = new File(destinationFolder);
		System.out.println("Destiny folder for Posição " + destinyFolder); 
		if (destinyFolder.exists())
		{
			//System.out.println("Already exist!");
		}
		else
		{
			destinyFolder.mkdirs();
		}

		for(File file:localFolderFiles)
		{	
			File destiny = null;
    		String fileExtension = file.getName().substring(file.getName().length()-3,file.getName().length()).toLowerCase();
    		System.out.println("Processing file " + file.getName() + " at treatmentPosicao");

    		if(fileExtension.equals("pdf"))
    		{
        		destiny = new File(destinyFolder + separator + fileOutputName + ".pdf");
        		this.copyRecentFile(file, destiny);
    		}
    		else if(fileExtension.equals("xls") && file.getName().toLowerCase().contains("caixa"))
			{
				System.out.println("Process Caixa within Posição");
				String fileNameDestiny = "Caixa_" + fundo.getName() + "_" + FilesDownloaderFTP.extractDateFromDirectory(fileOutputName) + ".xls";
				System.out.println("FileName " + fileNameDestiny);
				for(String pasta:fundo.getLocalFolders())
				{
					System.out.println(pasta);
					if(pasta.contains("Caixa")&&pasta.contains("A Processar"))
					{
						destinationFolder = pasta;
						break;
					}
				}
				
	    		destiny = new File(destinationFolder+separator+fileNameDestiny);
	    		
	    		Date lastModSource = new Date(file.lastModified());
	    		Date lastModDestiny = new Date(destiny.lastModified());

	    		if (destiny.exists())
	    		{
	    			System.out.println("File " + file.getAbsolutePath() + " exist");
	    			System.out.println("New file last modification " + lastModSource.toString());
	    			System.out.println("Old file last modification " + lastModDestiny.toString());
	    			if(lastModSource.compareTo(lastModDestiny) > 0)
	    			{
	    				this.copyRecentFile(file, destiny);			    				
//	    				try
//						{
//							XlsToCSV.convertExcelToCsv(file, destiny);
//							System.out.println("Saved file " + destiny);
//							
//						} catch (IOException e)
//						{
//							e.printStackTrace();
//						}
	    			}
	    			else
	    			{
	    				System.out.println("File " + destiny + " is updated");
	    			}
	    		}		    		
	    		else 
	    		{
	    			String tempPath = destiny.getAbsolutePath();
	    			
	    			destiny.delete();
	    			
	    			destiny = new File(tempPath);
	    			this.copyRecentFile(file, destiny);	
//	    			try
//					{
//	    				this.copyRecentFile(file, destiny);		
//						//XlsToCSV.convertExcelToCsv(file, destiny);
//						System.out.println("Saved file " + destiny);
//						
//					} catch (IOException e)
//					{
//						e.printStackTrace();
//					}
	    		}
	    		
	    		
	    		
	    	//	this.copyRecentFile(file, destiny);				
	    	}
	
		}
		
		
		
		
	}
	
	/**
	 * 
	 * @param folderTempExistencia
	 * @param fundo
	 */
	public void treatmentExistencia(String folderTempExistencia, FundoDeInvestimento fundo)
	{
		ArrayList<File> localFolderFiles = FilesDownloaderFTP.filesInDirectory(folderTempExistencia);
		System.out.println("Treatment for zip file Existencia");
		String destinationFolder="";
		for(String pasta:fundo.getLocalFolders())
		{
			if(pasta.contains("Estoque")&&pasta.contains("A Processar"))
			{
				destinationFolder = pasta;
				break;
			}			
		}
		
		for(File file:localFolderFiles)
		{			
			System.out.println(file.getName());
			String newFileName = file.getName().substring(0, file.getName().length()-5) + ".csv";
			File destiny = new File(destinationFolder+separator+newFileName);
    		this.copyRecentFile(file, destiny);
		}
	}
	
	/**
	 * 
	 * @param tempFolder
	 */
	public void deleteTempDestination(String tempFolder)
	{
		File directory = new File(tempFolder);
		 
    	//make sure directory exists
    	if(!directory.exists())
    	{
           System.out.println("Directory does not exist.");
           System.exit(0);
        }
    	else
    	{
           try
           {
               delete(directory);
           }
           catch(IOException e)
           {
               e.printStackTrace();
               System.exit(0);
           }
        }
	}
	
	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void delete(File file)
	    	throws IOException
	{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	 
	    		   file.delete();
	    		   System.out.println("\tDirectory is deleted : " 
	                                                 + file.getAbsolutePath());
	 
	    		}else{
	 
	    		   //list all the directory contents
	        	   String files[] = file.list();
	 
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	 
	        	      //recursive delete
	        	     delete(fileDelete);
	        	   }
	 
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	     System.out.println("\tDirectory is deleted : " 
	                                                  + file.getAbsolutePath());
	        	   }
	    		}
	 
	    	}
	    	else
	    	{
	    		//if file, then delete it
	    		file.delete();
	    		System.out.println("\tFile is deleted : " + file.getAbsolutePath());
	    	}
    }
	
	/**
	 * 
	 * @param source File
	 * @param password
	 * @param destination File
	 */
	public void extractFile(String source, String password, String destination)
	{
		ZipFile zipFile = null;
		try
		{
			zipFile = new ZipFile(source);
		} catch (ZipException e)
		{
			e.printStackTrace();
		}
	    try 
	    {
	        if (zipFile.isEncrypted()) 
	        {
	        	//System.out.println("Encrypted");
	            zipFile.setPassword(password);
	        }
        	zipFile.extractAll(destination);
	        System.out.println("\tExtracted to " + destination);
	    } 
	    catch (ZipException e) 
	    {
	    	System.out.println("\tExtraction Failed" + destination);
	        e.printStackTrace();	        
	    }	
	}

	public ArrayList<Administrador> getAdms()
	{
		return adms;
	}

	public void setAdms(ArrayList<Administrador> adms)
	{
		this.adms = adms;
	}

	public ArrayList<String> getTempFilesNames()
	{
		return tempFilesNames;
	}


	public void setTempFilesNames(ArrayList<String> tempFilesNames)
	{
		this.tempFilesNames = tempFilesNames;
	}
}
