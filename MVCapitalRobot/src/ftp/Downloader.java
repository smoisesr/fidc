package ftp;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import utils.OperatingSystem;

public class Downloader 
{
	private ArrayList<String> keywordRemote = new ArrayList<String>();
	private ArrayList<String> keyBlockRemote = new ArrayList<String>();
	private ArrayList<String> keyRemoteSpecial  = new ArrayList<String>();
//	private String separator = OperatingSystem.getSeparator();
	private static String separator = OperatingSystem.getSeparator();
	private static String rootLocalDir = OperatingSystem.getRootLocalDir();
	
	private FundoDeInvestimento fundo = null;
	private LoginFTP login = null;
	private ArrayList<String> remotePath=new ArrayList<String>();  
	private Connection conn=null;
	private ArrayList<File> downloadedFilesList;
	
	public Downloader()
	{
		
	}	
	public Downloader(FundoDeInvestimento fundo, LoginFTP login, Connection conn)
	{
		this.fundo = fundo;
		this.login = login;
		this.conn = conn;
		this.setRemotePath();
		System.out.println("******************************************");
		System.out.println("Running FilesDownloader");
		System.out.println("******************************************");
		this.setKeywordRemote();
		this.setKeyBlockRemote();
		System.out.println("Will retrieve files for " + fundo.getNomeCurto());
	}	
	public void setKeywordRemote()
	{		
		/**
		 * Finding KeywordRemote for Login
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT keyword FROM KeywordRemote WHERE idLogin=" + this.login.getIdLogin());
			while (rs.next())
			{				
				this.keywordRemote.add(rs.getString("keyword"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void setKeyBlockRemote()
	{
		/**
		 * Finding BlacklistKeyword for Login
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT keyword FROM BlacklistRemote WHERE idLogin=" + this.login.getIdLogin());
			while (rs.next())
			{				
				this.keyBlockRemote.add(rs.getString("keyword"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	public void downloadFiles()
	{
		System.out.println("-------------------------");
		System.out.println("Downloading files for " + fundo.getNomeCurto());
		System.out.println("-------------------------");
		this.setDownloadedFilesList(this.downloadFiles(fundo));		
	}	
	private void setRemotePath()
	{
		/**
		 * Finding remotePath for Login
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT path FROM RemotePath WHERE idLogin=" + this.login.getIdLogin());
			while (rs.next())
			{				
				this.remotePath.add(rs.getString("path"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
    public ArrayList<File> downloadFiles(FundoDeInvestimento fundo) 
    {
    	ArrayList<File> filesDownloaded = new ArrayList<File>(); 
    	/**
    	 * Connection to the FTP server
    	 */    	
    	System.out.println("Login to " + login.getServer() + ":" + login.getPort());
    	System.out.println("Username: " + login.getUsername());
    	System.out.println("Password: " + login.getPassword());
    	login.connectFTP();
    	FTPClient client = new FTPClient();
    	client = login.getClient();
    	/**
    	 * Entering to the right directory
    	 */    	
    	for (String remotePath:this.remotePath)
    	{
    		System.out.println("Path FTP " + remotePath);
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
    		System.out.println("Entering to " + remotePath);

    		Calendar calCurrent = Calendar.getInstance();
    		Calendar calYesterday = Calendar.getInstance();
    		
    		SimpleDateFormat month = new SimpleDateFormat("MM");

    		Date dateRef = calCurrent.getTime();
    		Date dateLastRef = calYesterday.getTime();
    		
    		while (month.format(dateRef).equals(month.format(dateLastRef)))
    		{
    			calYesterday.add(Calendar.DATE, -1);
    			dateLastRef = calYesterday.getTime();
    			//System.out.println(dateLastRef.toString());
    		}
    		
    		ArrayList<FTPFile> selectedFiles = new ArrayList<FTPFile>();
    		
    		int iFile=0;				
    		for (FTPFile file:fileList)
    		{
    			String fileName = (remotePath + separator + file.getName()).toLowerCase();
    			//Skiping the . and .. directories
    			if (file.getName().equals(".") || file.getName().equals(".."))
    			{
    				continue;
    			}
    			else    				
    			{
    				System.out.println("FTP file: " + file.getName());
    				boolean containsKeyWord=false;
					for (String keyWord:this.keywordRemote)
					{
						if(fileName.contains(keyWord))
						{
							boolean containsKeyBlock=false;
							for(String keyBlock:this.keyBlockRemote)
							{
								if (fileName.contains(keyBlock.toLowerCase()))
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
    				
    				if(containsKeyWord)
    				{
    					selectedFiles.add(file);
    					System.out.println(iFile + "\tSelected " + fileName);
    				}
    				iFile++;
    			}
    		}
    		System.out.println("Total files selected " + selectedFiles.size());
//    		String rootLocalDir = OperatingSystem.getRootLocalDir();
    		System.out.println(rootLocalDir);
    		String rootFundLocal = "Fundos" + separator + fundo.getCategoriaGeralAnbima() + separator + fundo.getNomeCurto().toUpperCase()+"_"+fundo.getCodigoCVM();
    		String rootRepositorioTransferido = rootFundLocal+separator+"Repositorio"+separator+"Transferido";
    		String rootRepositorioLocalTransferir = rootFundLocal+separator+"Repositorio"+separator+"Transferir";
    		String localRepositoTransferido = rootLocalDir + rootRepositorioTransferido;
    		String localRepositoroTransferir = rootLocalDir + rootRepositorioLocalTransferir;
    		System.out.println("Destination " + localRepositoTransferido);
    		
    		
			ArrayList<File> localFolderFiles = filesInDirectory(localRepositoTransferido);
			ArrayList<File> localFolderFilesNew = filesInDirectory(localRepositoroTransferir);
			ArrayList<String> localFilesNames = new ArrayList<String>();
			


			if(localFolderFiles!=null)
			{
				for (File f:localFolderFiles)
				{
					localFilesNames.add(f.getName());
				}
			}    			
			if(localFolderFilesNew!=null)
			{
				for (File f:localFolderFilesNew)
				{
					localFilesNames.add(f.getName());
				}
			}

			int iFilesSaved = 0;	
    		for (int iSelectedFile = 0; iSelectedFile < selectedFiles.size(); iSelectedFile++)
    		{
    			boolean exist = false;
    			//System.out.println(modDateList.get(i).toString() + " " + selectedFiles.get(i).getName());
    			String fileName = selectedFiles.get(iSelectedFile).getName();

    			String fileExtension = fileName.substring(fileName.length()-4, fileName.length()).toLowerCase();
    			String fileNameWithoutExtension = fileName.substring(0,fileName.length()-4);

    			System.out.println((iSelectedFile+1)+"/"+selectedFiles.size()+"\tProcessing " + fileNameWithoutExtension + fileExtension);
    			File saveDir = new File(localRepositoTransferido);

    			if(!saveDir.exists())
    			{
    				saveDir.mkdirs();
    				System.out.println("\tFolder " + localRepositoTransferido + " created ");
    			}
    			
    			
				String newName="";
				String fileNameWithoutSpaces=fileName.toLowerCase()
						.replaceAll("\\s+","")
						.replaceAll("ç", "c")
						.replaceAll("ã","a")
						.replaceAll("õ","o")
						.replaceAll("á","a")
						.replaceAll("ó","o")
						.replaceAll("à","a")
						.replaceAll("ò","o")
						.replaceAll("â","a")
						.replaceAll("â","o");
				
				String extension = fileNameWithoutSpaces.substring(fileNameWithoutSpaces.length()-3, fileNameWithoutSpaces.length());
				for (String keyWord:this.keywordRemote)
				{
					if(fileNameWithoutSpaces.contains(keyWord))
					{
						System.out.println("\t\tCheck if  " + fileNameWithoutSpaces + " contains " + keyWord);					
						System.out.println("\t\t\tMatches");
						Pattern pattern = Pattern.compile("[0-9]+"); 
						Matcher matcher = pattern.matcher(fileNameWithoutSpaces);

						String match="";
						// Find all matches
						while (matcher.find()) 
						{     						
						   match=match+matcher.group();    							  
						}     							
						newName = this.fundo.getNomeCurto()+"_"+Character.toUpperCase(keyWord.charAt(0))+keyWord.substring(1)+"_"+match+"."+extension;
						System.out.println("\t\t\tNewName: " + newName);						
						break;
					}
					else
					{
						newName = this.fundo.getNomeCurto()+fileNameWithoutSpaces;
					}
				}
				  			
    			if(localFilesNames.size()>0)
    			{
    				System.out.println("\t\t\tHave local files!");
    				for (String destinyName:localFilesNames)
    				{
    					if (destinyName.toLowerCase().equals(newName.toLowerCase()))
    					{	
    						System.out.println("\t\t\tFile " + destinyName.toLowerCase() + " exist!");
    						exist = true;	
    					}
    				}
    			}
				if (!exist)
				{
					File output=new File(localRepositoroTransferir + separator + newName);
					String completeRemoteFileName = remotePath + separator + fileName;
					System.out.println("\t\tLocal file will be " + output.getAbsolutePath());
					System.out.println("\t\tRemote file " + completeRemoteFileName);
					
					iFilesSaved++;
					boolean transferWell=false;
					while(!transferWell)
					{
						boolean savedWell=false;
						try 
						{	
							int nTry=2;
							int iTry=0;
							while (!savedWell && iTry < nTry)
							{
								try 
								{
									client.setCharset("UTF-8");

//									if(this.fundo.getIdFundo()<=2)//Case Lego and Orion
//									{
//										client.setCharset("UTF-8");
//									}
//									else //if(this.fundo.getIdEntidadeAdministrador()==2)//Case Socopa
//									{
//										client.setCharset("ISO_8859_1");
//									}

									client.download(completeRemoteFileName, output);
									filesDownloaded.add(output);
									System.out.println("\t\tSaved to " + output);
									savedWell = true;
								} 
								catch (IllegalStateException e) 
								{
									
									if(this.fundo.getIdFundo()>2)//Case Lego and Orion
									{
										client.setCharset("UTF-8");
									}
									else //if(this.fundo.getIdEntidadeAdministrador()==2)//Case Socopa
									{
										client.setCharset("ISO_8859_1");
									}

									e.printStackTrace();
									try 
									{
									    Thread.sleep(1000);
										try {
											client.download(completeRemoteFileName, output);
											filesDownloaded.add(output);
											System.out.println("\t\tSaved to " + output);
											savedWell = true;
										} catch (IllegalStateException e1) {
											e1.printStackTrace();
										} catch (FileNotFoundException e1) {
											e1.printStackTrace();
										} catch (IOException e1) {
											e1.printStackTrace();
										} catch (FTPIllegalReplyException e1) {
											e1.printStackTrace();
										} catch (FTPException e1) {
											e1.printStackTrace();
										}
										filesDownloaded.add(output);
										System.out.println("\t\tSaved to " + output);
										savedWell = true;
									    
									} catch(InterruptedException ex) {
									    Thread.currentThread().interrupt();
									}
									System.out.println("Saving " + output.getName() + " get wrong trying again");
									iTry++;
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
									iTry++;
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
									iTry++;
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
									iTry++;
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
									iTry++;
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
    		}
    		System.out.println("Total files downloaded " + iFilesSaved);
    		System.out.println("******************************************");	
    		System.out.println("All files downloaded for " + fundo.getNomeCurto() + " at " + remotePath);
    		System.out.println("******************************************");   		
    	}	
		return filesDownloaded;
	}
       
    public static String extractDateFromFileName(String fileName)
    {
    	int endIndex = fileName.length()-4;
    	int beginIndex = endIndex - 8;
    	String onlyNumbers=fileName.replaceAll("\\D+","");
    	String yearMonth = fileName.substring(beginIndex, endIndex);
    	yearMonth=onlyNumbers;
    	return yearMonth;
    }

    public static String extractDateFromDirectory(String fileName)
    {
    	int endIndex = fileName.length();
    	int beginIndex = endIndex - 8;
    	String onlyNumbers=fileName.replaceAll("\\D+","");
    	String yearMonth = fileName.substring(beginIndex, endIndex);
    	yearMonth=onlyNumbers;
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

	public ArrayList<String> getKeyRemote()
	{
		return keywordRemote;
	}

	public void setKeyRemote(ArrayList<String> keyRemote)
	{
		this.keywordRemote = keyRemote;
	}

	public ArrayList<String> getKeyRemoteSpecial()
	{
		return keyRemoteSpecial;
	}

	public void setKeyRemoteSpecial(ArrayList<String> keyRemoteSpecial)
	{
		this.keyRemoteSpecial = keyRemoteSpecial;
	}

	public ArrayList<File> getDownloadedFilesList() {
		return downloadedFilesList;
	}

	public void setDownloadedFilesList(ArrayList<File> downloadedFilesList) {
		this.downloadedFilesList = downloadedFilesList;
	}
}