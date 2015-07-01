package ftpclient;

import fundo.FundoDeInvestimento;
import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;

import utils.OperatingSystem;

public class FilesDownloaderFTP 
{
	private ArrayList<FundoDeInvestimento> fundos = new ArrayList<FundoDeInvestimento>();
	private ArrayList<String> keyRemote = new ArrayList<String>();
	private ArrayList<String> keyBlockRemote = new ArrayList<String>();
	private ArrayList<String> keyRemoteSpecial  = new ArrayList<String>();
	private String separator = OperatingSystem.getSeparator();
	public FilesDownloaderFTP()
	{
		
	}
	
	public FilesDownloaderFTP(ArrayList<FundoDeInvestimento> fundos)
	{
		System.out.println("******************************************");
		System.out.println("Running FilesDownloaderFTP");
		System.out.println("******************************************");
		System.out.println("Quantity of funds " + fundos.size());
		this.fundos = fundos;
		this.setKeyRemote();
		this.setKeyBlockRemote();
		this.setKeyRemoteSpecial();
		for (FundoDeInvestimento fundo:fundos)
		{
			System.out.println("Will retrieve files for " + fundo.getLocalName());
		}
	}
	
	public void setKeyRemote()
	{
		this.keyRemote.add("Carteira");
		this.keyRemote.add("EXISTENCIA");
		this.keyRemote.add("Cotas");
		this.keyRemote.add("Posição");
		this.keyRemote.add("Posicao");
		this.keyRemote.add("Recompra");
	}

	public void setKeyBlockRemote()
	{
		this.keyBlockRemote.add("dm_carteira");
	}

	
	public void setKeyRemoteSpecial()
	{
		this.keyRemoteSpecial.add("titulosvencidos");
		this.keyRemoteSpecial.add("titulospagos");
		this.keyRemoteSpecial.add("titulos");
	}
	
	public void downloadAllFunds()
	{
		for (FundoDeInvestimento fundo:this.fundos)
		{
			System.out.println("-------------------------");
			System.out.println("Downloading files for " + fundo.getLocalName());
			System.out.println("-------------------------");
			ArrayList<File> downloadsList = this.downloadFiles(fundo);		
			fundo.setFilesDownloaded(downloadsList);
		}
	}
	  	
    public ArrayList<File> downloadFiles(FundoDeInvestimento fundo) 
    {
    	LoginFTP login = fundo.getLoginFTP();
    	//login.getClient().setType(FTPClient.TYPE_BINARY);
    	//login.getClient().setType(FTPClient.TYPE_TEXTUAL);
    	//login.getClient().setCharset("UTF-8");
    	//login.getClient().setType(FTPClient.TYPE_AUTO);
    	ArrayList<File> filesDownloaded = new ArrayList<File>(); 
    	/**
    	 * Connection to the FTP server
    	 */    	
    	System.out.println("Login to " + login.getServer() + ":" + login.getPort());
    	System.out.println("Username " + login.getUsername());
    	System.out.println("Password " + login.getPassword());
    	login.connectFTP();
    	FTPClient client = new FTPClient();
    	client = login.getClient();
    	/**
    	 * Entering to the right directory
    	 */    	
    	System.out.println("Path FTP " + fundo.getPathFTP());
		String remotePath = fundo.getPathFTP();

		try 
		{
			client.changeDirectory(remotePath);
		} catch (IllegalStateException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			e.printStackTrace();
		} catch (FTPException e) {
			e.printStackTrace();
		}

		System.out.println("Entering to " + remotePath);

		ArrayList<FTPFile> fileList = new ArrayList<FTPFile>();
		ArrayList<Date> modDateList =  new ArrayList<Date>();
		boolean readingFine = false;
		while(!readingFine)
		{
			try 
			{
				FTPFile[] listVector = null;
				try 
				{
					listVector = client.list();
					readingFine = true;
				} catch (IllegalStateException e) {
					e.printStackTrace();
					readingFine = false;
					System.out.println("Trying again the connection");
				} catch (IOException e) {
					e.printStackTrace();
					readingFine = false;
					System.out.println("Trying again the connection");
				} catch (FTPIllegalReplyException e) {
					e.printStackTrace();
					readingFine = false;
					System.out.println("Trying again the connection");
				} catch (FTPException e) {
					e.printStackTrace();
					readingFine = false;
					System.out.println("Trying again the connection");
				}
				
				for(int i = 0; i < listVector.length; i++)
				{
					fileList.add(listVector[i]);
				}
			} 
			catch (FTPDataTransferException e) 
			{
				e.printStackTrace();
			} 
			catch (FTPAbortedException e) 
			{
				e.printStackTrace();
			} 
			catch (FTPListParseException e) 
			{
				e.printStackTrace();
			}
		}
		//System.out.println("Checking creation time for files");
		
		Calendar calCurrent = Calendar.getInstance();
		Calendar calYesterday = Calendar.getInstance();
		
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");

		Date dateRef = calCurrent.getTime();
		Date dateLastRef = calYesterday.getTime();
		
		while (month.format(dateRef).equals(month.format(dateLastRef)))
		{
			calYesterday.add(Calendar.DATE, -1);
			dateLastRef = calYesterday.getTime();
			//System.out.println(dateLastRef.toString());
		}
		
		String currentMonthString = year.format(dateRef) + month.format(dateRef);
		String lastMonthString = year.format(dateLastRef) + month.format(dateLastRef);
		

		ArrayList<FTPFile> selectedFiles = new ArrayList<FTPFile>();
		
		int iFile=0;				
		for (FTPFile file:fileList)
		{
			String fileName = remotePath + separator + file.getName();
			//Skiping the . and .. directories
			boolean haveKeyRemoteSpecial = false;
			for(String keySpecial:keyRemoteSpecial)
			{			
				if (fileName.contains(keySpecial))
				{
//					System.out.println("----------------------------------");
//					System.out.println("Special file " + fileName);
//					System.out.println("----------------------------------");
					haveKeyRemoteSpecial = true;
					break;
				}
			}
			if (file.getName().equals(".") || file.getName().equals(".."))
			{
				continue;
			}
			else if (fileName.contains(currentMonthString) || fileName.contains(lastMonthString) || haveKeyRemoteSpecial)
			{
				boolean containsKeyWord=false;
				if(!haveKeyRemoteSpecial)
				{
					for (String keyWord:this.keyRemote)
					{
						if(fileName.toLowerCase().contains(keyWord.toLowerCase()))
						{
							boolean containsKeyBlock=false;
							for(String keyBlock:this.keyBlockRemote)
							{
								//System.out.println("Checking " + fileName.toLowerCase() + " with " + keyBlock);
								if (fileName.toLowerCase().contains(keyBlock.toLowerCase()))
								{
									containsKeyBlock=true;
									break;
								}
							}
							if (!containsKeyBlock)
							{								
								containsKeyWord=true;
							}
							break;
						}
					}
				}
				
				try 
				{
					modDateList.add(client.modifiedDate(fileName));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (FTPIllegalReplyException e) {
					e.printStackTrace();
				} catch (FTPException e) {
					e.printStackTrace();
				}
				
				if(containsKeyWord || haveKeyRemoteSpecial)
				{
					selectedFiles.add(file);
					System.out.println(iFile + "\tSelected " + fileName);
				}
				iFile++;
				
			}
		}
		System.out.println("Total files selected " + selectedFiles.size());
		
		String completeLocalFTPPathDirectory="";
		
		for(String destination:fundo.getLocalFolders())
		{
			if (destination.contains("FTP"))
			{
				completeLocalFTPPathDirectory = destination;
				break;
			}						
		}
		
		//System.out.println("Descriptor " + descriptor.get(iDes));
		System.out.println("Destination " + completeLocalFTPPathDirectory);
		int iFilesSaved = 0;
		String dateForRecompra="";
		for (int iSelectedFile = 0; iSelectedFile < selectedFiles.size(); iSelectedFile++)
		{
			//System.out.println(modDateList.get(i).toString() + " " + selectedFiles.get(i).getName());
			String fileName = selectedFiles.get(iSelectedFile).getName();
			
			if (fileName.contains("Recompra"))
			{
				//RecompraPagamentoORION20140131.csv
				int endIndex = fileName.length()-4;
				int beginIndex = endIndex - 8; 
				dateForRecompra = fileName.substring(beginIndex, endIndex);
			}

			String fileExtension = fileName.substring(fileName.length()-4, fileName.length()).toLowerCase();
			String fileNameWithoutExtension = fileName.substring(0,fileName.length()-4);
			
//			Date lastModSource = selectedFiles.get(iSelectedFile).getModifiedDate();
		
//			String dateString = lastModSource.toString();
//    		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
//    		String dateFormatted = formatDate.format(lastModSource);
    		
    		//System.out.println(dateFormatted);			
			

			System.out.println((iSelectedFile+1)+"\tProcessing " + fileNameWithoutExtension + fileExtension);
			File saveDir = new File(completeLocalFTPPathDirectory);

			if(!saveDir.exists())
			{
				saveDir.mkdirs();
				System.out.println("\tFolder " + completeLocalFTPPathDirectory + " created ");
			}
			
			ArrayList<File> localFolderFiles = filesInDirectory(completeLocalFTPPathDirectory);					
			ArrayList<String> localFilesNames = new ArrayList<String>();
			
			boolean exist = false;

			if(localFolderFiles!=null)
			{
				for (File f:localFolderFiles)
				{
					//System.out.println(f.getName());
					localFilesNames.add(f.getName());
				}

				int iLocalFile=0;
				for (String destinyName:localFilesNames)
				{
					if (destinyName.toLowerCase().equals(fileName.toLowerCase()))
					{												
						//System.out.println("Equal name");
						Date lastModLocal = new Date(localFolderFiles.get(iLocalFile).lastModified());
						if(lastModLocal.compareTo(modDateList.get(iSelectedFile))<0)
						{
							//System.out.println(fileName + " already exist!");								
							exist = true;	
						}
						exist = true;	
					}
					iLocalFile++;
				}
				if (!exist)
				{
					File output=new File(completeLocalFTPPathDirectory + separator + fileName);
					if(fileExtension.equals(".zip"))
					{
						output = new File(completeLocalFTPPathDirectory + separator + fileName);
					}
					else 
					{
						output = new File(completeLocalFTPPathDirectory + separator + fileNameWithoutExtension + "_" + dateForRecompra+fileExtension);
					}
					String completeRemoteFileName = remotePath + separator + fileName;
					//System.out.println((iFilesSaved+1)+"\tProcessing " + fileNameWithoutExtension + fileExtension);
					System.out.println("\tLocal file will be " + output.getAbsolutePath());
					System.out.println("\tRemote file " + completeRemoteFileName);
					
					iFilesSaved++;
					boolean transferWell=false;
					while(!transferWell)
					{
						boolean savedWell=false;
						try 
						{							
							while (!savedWell)
							{
								try 
								{
									client.download(completeRemoteFileName, output);
									filesDownloaded.add(output);
									System.out.println("\tSaved to " + output);
									savedWell = true;
								} 
								catch (IllegalStateException e) 
								{
									e.printStackTrace();
									try 
									{
									    Thread.sleep(1000);
									} catch(InterruptedException ex) {
									    Thread.currentThread().interrupt();
									}
									System.out.println("Saving " + output.getName() + " get wrong trying again");
									savedWell = false;
								} 
								catch (FileNotFoundException e) 
								{
									e.printStackTrace();
									try {
									    Thread.sleep(1000);
									} catch(InterruptedException ex) {
									    Thread.currentThread().interrupt();
									}
									System.out.println("Saving " + output.getName() + " get wrong trying again");
									savedWell = false;
								} 
								catch (IOException e) 
								{
									e.printStackTrace();
									try {
									    Thread.sleep(1000);
									} catch(InterruptedException ex) {
									    Thread.currentThread().interrupt();
									}
									System.out.println("Saving " + output.getName() + " get wrong trying again");
									savedWell = false;
								} 
								catch (FTPIllegalReplyException e) 
								{
									e.printStackTrace();
									try {
									    Thread.sleep(1000);
									} catch(InterruptedException ex) {
									    Thread.currentThread().interrupt();
									}
									System.out.println("Saving " + output.getName() + " get wrong trying again");
									savedWell = false;
								} 
								catch (FTPException e) 
								{
									e.printStackTrace();
									try {
									    Thread.sleep(1000);
									} catch(InterruptedException ex) {
									    Thread.currentThread().interrupt();
									}
									System.out.println("Saving " + output.getName() + " get wrong trying again");
									savedWell = false;
								}
							}
							
							transferWell=true;
							
						} 
						catch (FTPDataTransferException e) 
						{
							e.printStackTrace();
							System.out.println("Transfering " + output.getName() + " get wrong trying again");
							transferWell=false;
						} catch (FTPAbortedException e) {
							e.printStackTrace();
							System.out.println("Transfering " + output.getName() + " get wrong trying again");
							transferWell=false;
						}
					}
				}
				else
				{
					System.out.println("\tNothing to do");
				}
			}
		}
		System.out.println("Total files downloaded " + iFilesSaved);
		System.out.println("******************************************");	
		System.out.println("All files processed for " + fundo.getLocalName());
		System.out.println("******************************************");
		return filesDownloaded;
	}
       
    public static String extractDateFromFileName(String fileName)
    {
    	int endIndex = fileName.length()-4;
    	int beginIndex = endIndex - 8;
    	String yearMonth = fileName.substring(beginIndex, endIndex);
    	return yearMonth;
    }

    public static String extractDateFromDirectory(String fileName)
    {
    	int endIndex = fileName.length();
    	int beginIndex = endIndex - 8;
    	String yearMonth = fileName.substring(beginIndex, endIndex);
    	return yearMonth;
    }

    public static String folderNameFromDate(String yearMonthDay)
    {
    	String year = yearMonthDay.substring(0, 4);
    	String month = yearMonthDay.substring(4, 6);
//    	String day = yearMonthDay.substring(6, 8);
    	String result = year + "-" + month + " " + nameMonth(Integer.parseInt(month));
    	return result;
    }
    
    public static String nameMonth(int month)
    {
        String monthString;
        switch (month) {
            case 1:  monthString = "Jan";
                     break;
            case 2:  monthString = "Fev";
                     break;
            case 3:  monthString = "Mar";
                     break;
            case 4:  monthString = "Abr";
                     break;
            case 5:  monthString = "Mai";
                     break;
            case 6:  monthString = "Jun";
                     break;
            case 7:  monthString = "Jul";
                     break;
            case 8:  monthString = "Ago";
                     break;
            case 9:  monthString = "Set";
                     break;
            case 10: monthString = "Out";
                     break;
            case 11: monthString = "Nov";
                     break;
            case 12: monthString = "Dez";
                     break;
            default: monthString = "Invalid month";
                     break;
        }
        //System.out.println(monthString);
    			
    	return monthString;
    }
    
    public static ArrayList<File> filesInDirectory(String directory)
    {
    	ArrayList<File> filesList = new ArrayList<File>();
    	File folder = new File(directory);
    	File[] listOfFiles = folder.listFiles();
    	
    	if (folder.listFiles()==null)
    	{
    		return null;
    	}
    	else
    	{
	    	for (int i = 0; i < listOfFiles.length; i++) 
	    	{
	    		filesList.add(listOfFiles[i]);
	    	}
	    	return filesList;
    	}
    }

	public ArrayList<FundoDeInvestimento> getFundos()
	{
		return fundos;
	}

	public void setFundos(ArrayList<FundoDeInvestimento> fundos)
	{
		this.fundos = fundos;
	}

	public ArrayList<String> getKeyRemote()
	{
		return keyRemote;
	}

	public void setKeyRemote(ArrayList<String> keyRemote)
	{
		this.keyRemote = keyRemote;
	}

	public ArrayList<String> getKeyRemoteSpecial()
	{
		return keyRemoteSpecial;
	}

	public void setKeyRemoteSpecial(ArrayList<String> keyRemoteSpecial)
	{
		this.keyRemoteSpecial = keyRemoteSpecial;
	}
}