package ftpclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import mysql.MySQLAccess;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import utils.OperatingSystem;
import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

public class ReaderDI 
{
	private String serverFTP = "ftp.cetip.com.br"; //$NON-NLS-1$
	private int portFTP = 21;
	private LoginFTP login = new LoginFTP(this.serverFTP, this.portFTP);
	private FTPClient client = this.login.getClient();
	private String rootLocalDir = OperatingSystem.getRootLocalDir();
	private String separator = OperatingSystem.getSeparator();
	
	private String tempDirIndiceDI = this.rootLocalDir + this.separator + "Fontes"+this.separator+"MercadoIndicadores"+this.separator+"CETIP"+this.separator+"Indice"+this.separator; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	private String tempDirMediaCDI = this.rootLocalDir + this.separator + "Fontes"+this.separator+"MercadoIndicadores"+this.separator+"CETIP"+this.separator+"Media"+this.separator; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	
	private String remoteDirIndiceDI = "/IndiceDI"; //$NON-NLS-1$
	private String remoteDirMediaCDI = "/MediaCDI"; //$NON-NLS-1$
	private File outputFile = new File(this.rootLocalDir +this.separator+"Fontes"+this.separator+"MercadoIndicadores"+this.separator+"CETIP"+this.separator+"DI.csv"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	
	private ArrayList<Date> dataIndice = new ArrayList<Date>();
	private ArrayList<Double> indice = new ArrayList<Double>();
	private ArrayList<Date> dataMedia = new ArrayList<Date>();
	private ArrayList<Double> media = new ArrayList<Double>();
	private ArrayList<Double> fatorDI = new ArrayList<Double>();
	
	private static SimpleDateFormat sdfFTP = new SimpleDateFormat("yyyyMMdd"); //$NON-NLS-1$
		
	private MySQLAccess mysql = null;
	private String serverDB="192.168.1.48"; //$NON-NLS-1$
	private int portDB=3306;
	private String userName="robot"; //$NON-NLS-1$
	private String password=""; //$NON-NLS-1$
	private String dbName=""; //$NON-NLS-1$
	public static Connection conn = null;
	
	private ArrayList<CDIOver> cdiOver = new ArrayList<CDIOver>();

	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		ReaderDI reader = new ReaderDI("MVCapitalDC", "autoatmvc123");; //$NON-NLS-1$ //$NON-NLS-2$
		@SuppressWarnings("unused")
		ReaderDI reader2 = new ReaderDI("MVCapital", "autoatmvc123"); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("--------------------"); //$NON-NLS-1$
		System.out.println("Program finished!"); //$NON-NLS-1$
		System.out.println("--------------------"); //$NON-NLS-1$
	}

	public ReaderDI(String dbName, String password)
	{
		this.dbName = dbName;
		this.password = password;
		ArrayList<CDIOver> cdiOverFTP = new ArrayList<CDIOver>();
		this.login.connectFTP();
		this.checkFolders();
		this.mysql = new MySQLAccess(this.serverDB, this.portDB, this.userName, this.password, this.dbName);
		this.mysql.connect();			
		ReaderDI.conn=(Connection) this.mysql.getConn();
		this.readMedias();
		this.getIndices(this.remoteDirIndiceDI, this.tempDirIndiceDI);
		cdiOverFTP = this.getMedias(this.remoteDirMediaCDI, this.tempDirMediaCDI);
		this.storeMedias(cdiOverFTP);
		this.consolidar();
		this.getMysql().close();
	}
	
	public ReaderDI()
	{
		ArrayList<CDIOver> cdiOverFTP = new ArrayList<CDIOver>();
		this.login.connectFTP();
		this.checkFolders();
		this.mysql = new MySQLAccess(this.serverDB, this.portDB, this.userName, this.password, this.dbName);
		this.mysql.connect();			
		ReaderDI.conn=(Connection) this.mysql.getConn();
		this.readMedias();
		this.getIndices(this.remoteDirIndiceDI, this.tempDirIndiceDI);
		cdiOverFTP = this.getMedias(this.remoteDirMediaCDI, this.tempDirMediaCDI);
		this.storeMedias(cdiOverFTP);
		this.consolidar();
	}
	
	public void storeMedias(ArrayList<CDIOver> cdiOverFTP)
	{
		for(CDIOver coFTP:cdiOverFTP)
		{
			boolean exist=false;
			for(CDIOver co:this.cdiOver)
			{				
				if(co.getData().compareTo(coFTP.getData())==0)
				{
					exist=true;
					break;
				}
			}
			if(exist)
			{
				continue;
			}
			else
			{
				ReaderDI.storeMedia(coFTP);
			}
		}
	}
	
	public static void storeMedia(CDIOver co)
	{
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String sql = "INSERT INTO `cdiover` (`data`, `taxa`) " //$NON-NLS-1$
					+ "VALUES (" //$NON-NLS-1$
					+ sdfFTP.format(co.getData())
					+ "," + co.getTaxa() //$NON-NLS-1$
					+ ")"; //$NON-NLS-1$
		System.out.println(sql);
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void readMedias()
	{
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT * from CDIOver ORDER BY data ASC";		 //$NON-NLS-1$
		
		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				Date data = rs.getDate("data"); //$NON-NLS-1$
				double taxa = rs.getDouble("taxa"); //$NON-NLS-1$
				this.cdiOver.add(new CDIOver(data,taxa));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(this.cdiOver.size() + " cdiOver values found"); //$NON-NLS-1$
		for(CDIOver co:this.cdiOver)
		{
			System.out.println(co.getData() + "\t" + co.getTaxa()); //$NON-NLS-1$
		}
	}
	
	public void consolidar()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
		DecimalFormat eightDForm = new DecimalFormat("#.########"); //$NON-NLS-1$
		DecimalFormat sixteenDForm = new DecimalFormat("#.################"); //$NON-NLS-1$
		ArrayList<Date> datasIguais = new ArrayList<Date>();
		ArrayList<Dyad> indicesIguais = new ArrayList<Dyad>();
		int iIndice=0;
		for (Date dataIndice:this.dataIndice)
		{			
			int iMedia=0;
			for(Date dataMedia:this.dataMedia)
			{
				if(dataIndice.equals(dataMedia))
				{
					datasIguais.add(dataIndice);
					indicesIguais.add(new Dyad(iIndice,iMedia));
				}
				iMedia++;
			}
			iIndice++;
		}
		ArrayList<Double> diAcumuladoMes = new ArrayList<Double>();
		ArrayList<Double> diTaxaAcumuladoMes = new ArrayList<Double>();
		
		PrintStream stream = null;
		try {
			stream = new PrintStream(this.outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String lineHeader = "Data;IndiceDI;DI;FatorDI;TaxaDI_AcumMês;TaxaDI_AcumMêsB";  //$NON-NLS-1$
		stream.print(lineHeader + "\n"); //$NON-NLS-1$

        
		for (int i = 0; i<datasIguais.size(); i++)
		{
			String data = formatter.format(datasIguais.get(i));
			Double indiceValue = this.indice.get(indicesIguais.get(i).getI());
			Double mediaValue = this.media.get(indicesIguais.get(i).getJ());
			
			Double number = Math.pow(mediaValue/100.00+1,(1.0/252.0));
			Double	fatorDIValue = Double.parseDouble(eightDForm.format(number).toString().replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$
			
			Double number2 = 0.0;
			
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			if (i!=0)
			{
				cal1.setTime(datasIguais.get(i-1));
			}
	        cal2.setTime(datasIguais.get(i));

			if(i==0)
			{
				number2 = fatorDIValue;
				diAcumuladoMes.add(Double.parseDouble(sixteenDForm.format(number2).toString().replace(",", "."))); //$NON-NLS-1$ //$NON-NLS-2$
				diTaxaAcumuladoMes.add(Double.parseDouble(eightDForm.format(number2).toString().replace(",", "."))); //$NON-NLS-1$ //$NON-NLS-2$
			}
			else if(cal1.get(Calendar.MONTH)!=cal2.get(Calendar.MONTH))
			{
				number2 = fatorDIValue;
				diAcumuladoMes.add(Double.parseDouble(sixteenDForm.format(number2).toString().replace(",", "."))); //$NON-NLS-1$ //$NON-NLS-2$
				diTaxaAcumuladoMes.add(Double.parseDouble(eightDForm.format(number2).toString().replace(",", "."))); //$NON-NLS-1$ //$NON-NLS-2$
			}
			else
			{
				number2 = fatorDIValue*diAcumuladoMes.get(i-1);
				diAcumuladoMes.add(Double.parseDouble(sixteenDForm.format(number2).toString().replace(",", "."))); //$NON-NLS-1$ //$NON-NLS-2$
				diTaxaAcumuladoMes.add(Double.parseDouble(eightDForm.format(number2).toString().replace(",", "."))); //$NON-NLS-1$ //$NON-NLS-2$
			}
			
			System.out.println(data+"\t"+indiceValue+"\t"+mediaValue+"\t"+fatorDIValue+"\t"+diAcumuladoMes.get(i)+"\t"+diTaxaAcumuladoMes.get(i)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			String lineCSV = data+";"+indiceValue+";"+mediaValue+";"+fatorDIValue+";"+diAcumuladoMes.get(i)+";"+diTaxaAcumuladoMes.get(i); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			stream.print(lineCSV.replace(".", ",") + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}
	
	public void checkFolders()
	{
		File saveDirMedia = new File(this.tempDirMediaCDI);	
		if(!saveDirMedia.exists())
		{
			//System.out.println("Folder " + completePathDirectory + " dosn't exist ");
			saveDirMedia.mkdirs();
			System.out.println("Folder " + this.tempDirMediaCDI + "\t created "); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
		{
			System.out.println("Folder " + this.tempDirMediaCDI + "\t already exist "); //$NON-NLS-1$ //$NON-NLS-2$
		}					
		
		File saveDirCota = new File(this.tempDirIndiceDI);	
		if(!saveDirCota.exists())
		{
			//System.out.println("Folder " + completePathDirectory + " dosn't exist ");
			saveDirCota.mkdirs();
			System.out.println("Folder " + this.tempDirIndiceDI + "\t created "); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
		{
			System.out.println("Folder " + this.tempDirIndiceDI + "\t already exist "); //$NON-NLS-1$ //$NON-NLS-2$
		}				
	}
	
	public void getIndices(String remoteDir, String localDir)
	{
		System.out.println("Path FTP IndiceDI");		 //$NON-NLS-1$
		try {
			this.client.changeDirectory(remoteDir);
		} catch (IllegalStateException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			e.printStackTrace();
		} catch (FTPException e) {
			e.printStackTrace();
		}

		System.out.println("Entering to " + remoteDir); //$NON-NLS-1$

		ArrayList<FTPFile> fileList = new ArrayList<FTPFile>();
		try 
		{
			FTPFile[] listVector = null;
			try {
				listVector = this.client.list();
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
		System.out.println("File list"); //$NON-NLS-1$
		ArrayList<File> localFiles = new ArrayList<File>();
		ArrayList<File> oldFiles = new ArrayList<File>();
		ArrayList<FTPFile> selectedFiles = new ArrayList<FTPFile>();
		oldFiles = OperatingSystem.filesInDirectory(localDir);
//		System.out.println("Local files list");
		
//		for(File oldFile:oldFiles)
//		{
//			System.out.println("LF: " + oldFile.getName());
//		}
		for (FTPFile file:fileList)
		{
			boolean exist=false;
			System.out.println("Check " + file.getName()); //$NON-NLS-1$
			for(File oldFile:oldFiles)
			{
				if(file.getName().equals(oldFile.getName()))
				{
					//System.out.println(file.getName() + " exist!");
					localFiles.add(oldFile);
					exist=true;
					break;
				}
			}
			if(!exist)
			{
				selectedFiles.add(file);
			}
		}		
		
		for (FTPFile file:selectedFiles)
		{
			System.out.println("Downloading: " + file.getName()); //$NON-NLS-1$
			
			File localFile=new File(localDir + file.getName());

			try 
			{
				this.login.getClient().download(file.getName(), localFile);
				localFiles.add(localFile);
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
			} catch (FTPDataTransferException e) {
				e.printStackTrace();
			} catch (FTPAbortedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Lista de cotas"); //$NON-NLS-1$
		for (File file:localFiles)
		{
			BufferedReader readerFile = null;
			try {
				readerFile = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
//			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat formatterRemote = new SimpleDateFormat("yyyyMMdd"); //$NON-NLS-1$
			String dateString = file.getName().substring(0, 8);
			Date data=null;
			try 
			{
				System.out.println(file.getName()+ " " +dateString); //$NON-NLS-1$
				data = formatterRemote.parse(dateString);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			String line = null;
			try {
				while ((line = readerFile.readLine()) != null) 
				{
					this.dataIndice.add(data);
					this.indice.add(Double.parseDouble(line)/100);
//					System.out.println(formatter.format(data) + " " +);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
	public ArrayList<CDIOver> getMedias(String remoteDir, String localDir)
	{
		System.out.println("Path FTP MediaCDI");		 //$NON-NLS-1$
		try {
			this.client.changeDirectory(remoteDir);
		} catch (IllegalStateException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			e.printStackTrace();
		} catch (FTPException e) {
			e.printStackTrace();
		}

		System.out.println("Entering to " + remoteDir); //$NON-NLS-1$

		ArrayList<FTPFile> fileList = new ArrayList<FTPFile>();
		
		try 
		{
			FTPFile[] listVector = null;
			try {
				listVector = this.client.list();
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
		System.out.println("File list"); //$NON-NLS-1$
		ArrayList<File> localFiles = new ArrayList<File>();
		ArrayList<File> oldFiles = new ArrayList<File>();
		ArrayList<FTPFile> selectedFiles = new ArrayList<FTPFile>();
		oldFiles = OperatingSystem.filesInDirectory(localDir);
		
		for (FTPFile file:fileList)
		{
			boolean exist=false;
			System.out.println("Check " + file.getName()); //$NON-NLS-1$
			for(File oldFile:oldFiles)
			{
				if(file.getName().equals(oldFile.getName()))
				{
					System.out.println(file.getName() + " exist!"); //$NON-NLS-1$
					localFiles.add(oldFile);
					exist=true;
					break;
				}
			}
			if(!exist)
			{
				selectedFiles.add(file);
			}
		}		
		
		
		for (FTPFile file:selectedFiles)
		{
			System.out.println(file.getName());
			
			File localFile=new File(localDir + file.getName());

			try 
			{
				this.login.getClient().download(file.getName(), localFile);
				localFiles.add(localFile);
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
			} catch (FTPDataTransferException e) {
				e.printStackTrace();
			} catch (FTPAbortedException e) {
				e.printStackTrace();
			}
		}
		
		//System.out.println("Lista de cotas");
		for (File file:localFiles)
		{
			BufferedReader readerFile = null;
			try {
				readerFile = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
//			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String dateString = file.getName().substring(0, 8);
			Date data=null;
			try 
			{
				//System.out.println(file.getName());
				//System.out.println(dateString);
				data = sdfFTP.parse(dateString);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			String line = null;
			try {
				while ((line = readerFile.readLine()) != null) 
				{
					this.dataMedia.add(data);
					this.media.add(Double.parseDouble(line)/100);
//					System.out.println(formatter.format(data) + " " +);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ArrayList<CDIOver> cdiOverFTP = new ArrayList<CDIOver>();
		for(int i=0;i<this.dataMedia.size();i++)
		{
			cdiOverFTP.add(new CDIOver(this.dataMedia.get(i),this.media.get(i)));
		}
		return cdiOverFTP;
	}	
	/**
	 * 
	 * @param tempFolder
	 */
	public static void deleteTempDestination(String tempFolder)
	{
		File directory = new File(tempFolder);
		 
    	//make sure directory exists
    	if(!directory.exists())
    	{
           System.out.println("Directory does not exist."); //$NON-NLS-1$
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
	    		   System.out.println("\tDirectory is deleted : "  //$NON-NLS-1$
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
	        	     System.out.println("\tDirectory is deleted : "  //$NON-NLS-1$
	                                                  + file.getAbsolutePath());
	        	   }
	    		}
	 
	    	}
	    	else
	    	{
	    		//if file, then delete it
	    		file.delete();
	    		System.out.println("\tFile is deleted : " + file.getAbsolutePath()); //$NON-NLS-1$
	    	}
    }

	public String getServerFTP() {
		return this.serverFTP;
	}

	public void setServerFTP(String serverFTP) {
		this.serverFTP = serverFTP;
	}

	public int getPortFTP() {
		return this.portFTP;
	}

	public void setPortFTP(int portFTP) {
		this.portFTP = portFTP;
	}

	public LoginFTP getLogin() {
		return this.login;
	}

	public void setLogin(LoginFTP login) {
		this.login = login;
	}

	public FTPClient getClient() {
		return this.client;
	}

	public void setClient(FTPClient client) {
		this.client = client;
	}

	public String getRootLocalDir() {
		return this.rootLocalDir;
	}

	public void setRootLocalDir(String rootLocalDir) {
		this.rootLocalDir = rootLocalDir;
	}

	public String getSeparator() {
		return this.separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getTempDirIndiceDI() {
		return this.tempDirIndiceDI;
	}

	public void setTempDirIndiceDI(String tempDirIndiceDI) {
		this.tempDirIndiceDI = tempDirIndiceDI;
	}

	public String getRemoteDirIndiceDI() {
		return this.remoteDirIndiceDI;
	}

	public void setRemoteDirIndiceDI(String remoteDirIndiceDI) {
		this.remoteDirIndiceDI = remoteDirIndiceDI;
	}

	public String getTempDirMediaCDI() {
		return this.tempDirMediaCDI;
	}

	public void setTempDirMediaCDI(String tempDirMediaCDI) {
		this.tempDirMediaCDI = tempDirMediaCDI;
	}

	public String getRemoteDirMediaCDI() {
		return this.remoteDirMediaCDI;
	}

	public void setRemoteDirMediaCDI(String remoteDirMediaCDI) {
		this.remoteDirMediaCDI = remoteDirMediaCDI;
	}

	public File getOutputFile() {
		return this.outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	public ArrayList<Date> getDataIndice() {
		return this.dataIndice;
	}

	public void setDataIndice(ArrayList<Date> dataIndice) {
		this.dataIndice = dataIndice;
	}

	public ArrayList<Double> getIndice() {
		return this.indice;
	}

	public void setIndice(ArrayList<Double> indice) {
		this.indice = indice;
	}

	public ArrayList<Date> getDataMedia() {
		return this.dataMedia;
	}

	public void setDataMedia(ArrayList<Date> dataMedia) {
		this.dataMedia = dataMedia;
	}

	public ArrayList<Double> getMedia() {
		return this.media;
	}

	public void setMedia(ArrayList<Double> media) {
		this.media = media;
	}

	public ArrayList<Double> getFatorDI() {
		return this.fatorDI;
	}

	public void setFatorDI(ArrayList<Double> fatorDI) {
		this.fatorDI = fatorDI;
	}

	public MySQLAccess getMysql() {
		return this.mysql;
	}

	public void setMysql(MySQLAccess mysql) {
		this.mysql = mysql;
	}

	public String getServerDB() {
		return this.serverDB;
	}

	public void setServerDB(String serverDB) {
		this.serverDB = serverDB;
	}

	public int getPortDB() {
		return this.portDB;
	}

	public void setPortDB(int portDB) {
		this.portDB = portDB;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return this.dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public ArrayList<CDIOver> getCdiOver() {
		return this.cdiOver;
	}

	public void setCdiOver(ArrayList<CDIOver> cdiOver) {
		this.cdiOver = cdiOver;
	}

	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		ReaderDI.conn = conn;
	}
}
