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

public class FilesDownloader 
{
	ArrayList<FundoDeInvestimento> fundos = new ArrayList<FundoDeInvestimento>();

//	public static void main(String[] args) 
//    {
//		ArrayList<Administrador> adms = new ArrayList<Administrador>();
//		ArrayList<FundoDeInvestimento> fundos = new ArrayList<FundoDeInvestimento>();
//		LoginFTP loginOrion = new LoginFTP("ftp.personaltrader.com.br", 990, "orion", "!F1Dc0R10n#");
//		String rootLocalDir = "C:\\Tmp";
//		
//		adms.add(new Administrador(Administrador.Lista.Petra, "RECEBER"));
//		
//		fundos.add(new FundoDeInvestimento(FundoDeInvestimento.Lista.Orion, adms.get(0), loginOrion, rootLocalDir));
//
//		FilesDownloader downloader = new FilesDownloader(fundos);
//		downloader.downloadAllFunds();
//	}
	
	public FilesDownloader(ArrayList<FundoDeInvestimento> fundos)
	{
		this.fundos = fundos;
	}
	
	public void downloadAllFunds()
	{
		for (FundoDeInvestimento fundo:this.fundos)
		{
			this.downloadFiles(fundo);
		}
	}
	  	
    public void downloadFiles(FundoDeInvestimento fundo) 
    {
    	LoginFTP login = fundo.getLoginFTP();
    	/**
    	 * Connection to the FTP server
    	 */    	
    	login.connectFTP();
    	FTPClient client = new FTPClient();
    	client = login.getClient();
    	/**
    	 * Entering to the right directory
    	 */    	
    	System.out.println("Path FTP " + fundo.getPathFTP());
		String dir = fundo.getPathFTP();

		try {
			client.changeDirectory(dir);
		} catch (IllegalStateException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			e.printStackTrace();
		} catch (FTPException e) {
			e.printStackTrace();
		}

		System.out.println("Entering to " + dir);

		ArrayList<FTPFile> fileList = new ArrayList<FTPFile>();
		ArrayList<Date> modDateList =  new ArrayList<Date>();
		try 
		{
			FTPFile[] listVector = null;
			try {
				listVector = client.list();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FTPIllegalReplyException e) {
				e.printStackTrace();
			} catch (FTPException e) {
				e.printStackTrace();
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

		System.out.println("Checking creation time for files");
		
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
		
//		System.out.println("Today's date = "+ calCurrent.getTime());
//		System.out.println("Last day of last month = "+ calYesterday.getTime());
		
		
		String currentMonthString = year.format(dateRef) + month.format(dateRef);
		String lastMonthString = year.format(dateLastRef) + month.format(dateLastRef);
		
		System.out.println("Searching any file containing " + currentMonthString + " or " + lastMonthString);
//		System.out.println(currentMonthString);
//		System.out.println(lastMonthString);

		ArrayList<FTPFile> selectedFiles = new ArrayList<FTPFile>();
		int iFile=0;
		for (FTPFile file:fileList)
		{
			String fileName = dir + "/" + file.getName();
			//Skiping the . and .. directories
			if (file.getName().equals(".") || file.getName().equals(".."))
			{
				continue;
			}
			else if (fileName.contains(currentMonthString) || fileName.contains(lastMonthString))
			{
				try {
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
				selectedFiles.add(file);
				iFile++;
				System.out.println(iFile + "\tSelected " + fileName);
			}
		}
		System.out.println("Total files selected " + selectedFiles.size());
		
		//Source and destination names and codes
		ArrayList<String> descriptor = fundo.getDescritores();
		ArrayList<String> destination = fundo.getLocalFolders();
		
//		ArrayList<String> destinationRoot = fundo.getLocalFolders();
		
//		code.add("Carteira");
		
//		destinationRoot.add("C:\\Tmp\\Fontes\\Petra\\Carteira\\FIDC Orion\\");
//		destination.add("C:\\Tmp\\Fontes\\Petra\\Carteira\\FIDC Orion\\");
		
		for (int iDes = 0; iDes < descriptor.size(); iDes++)
		{
			for (int i = 0; i < selectedFiles.size(); i++)
			{
				
				//System.out.println(modDateList.get(i).toString() + " " + selectedFiles.get(i).getName());
				String fileName = selectedFiles.get(i).getName();
				String folderDataName = folderNameFromDate(extractDateFromFileName(fileName));
				
				//System.out.println("Folder " + folderName);
				
				String completePathDirectory = "";
				if(descriptor.get(iDes)!="FTP")
				{
					completePathDirectory = destination.get(iDes) +"\\" + folderDataName;
				}
				else
				{
					completePathDirectory = destination.get(iDes);
				}
				
				File saveDir = new File(completePathDirectory);

				if(!saveDir.exists())
				{
					saveDir.mkdirs();
					System.out.println("Folder " + completePathDirectory + " created ");
				}
				else
				{
					System.out.println("Folder " + completePathDirectory + " already exist ");					
					ArrayList<File> localFolderFiles = filesInDirectory(completePathDirectory);					
					ArrayList<String> destinyNames = new ArrayList<String>();
					if(localFolderFiles!=null)
					{
						for (File f:localFolderFiles)
						{
							destinyNames.add(f.getName());
						}

						System.out.println("Verificando se " + fileName + " ja existe em " + completePathDirectory);						
						boolean exist = false;	
						
						int iF=0;
						for (String destinyName:destinyNames)
						{
							if (destinyName.equals(fileName))
							{
															
								Date lastModLocal = new Date(localFolderFiles.get(iFile).lastModified());
								if(lastModLocal.compareTo(modDateList.get(i))>0)
								{
									System.out.println(iF + " " + fileName + " exist!");								
									exist = true;	
								}
							}
							iF++;
						}
						if (!exist)
						{
							if (fileName.contains(descriptor.get(iDes)))
							{
								File output = new File(destination.get(iDes)+fileName);
								fileName = completePathDirectory + "\\" + fileName;
								System.out.println(fileName);
								try 
								{
									try {
										client.download(fileName, output);
									} catch (IllegalStateException e) {
										e.printStackTrace();
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									} catch (IOException e) {
										e.printStackTrace();
									} catch (FTPIllegalReplyException e) {
										e.printStackTrace();
									} catch (FTPException e) {
										e.printStackTrace();
									}
								} catch (FTPDataTransferException e) {
									e.printStackTrace();
								} catch (FTPAbortedException e) {
									e.printStackTrace();
								}					
							}
						}
					}
				}
			}
			System.out.println("All files processed for " + descriptor.get(iDes));
		}
    }
    
    public static String extractDateFromFileName(String fileName)
    {
    	int endIndex = fileName.length()-4;
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
}