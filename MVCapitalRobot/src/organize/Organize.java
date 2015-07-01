package organize;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.CleanString;
import utils.Login;
import utils.OperatingSystem;
import keywords.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import fundo.FundoDeInvestimento;

public class Organize {
	/**
	 * This is the zip file extraction password
	 */
	private String password="";
	protected FundoDeInvestimento fundo=null;
	protected int idEntidadeServidor = 0;
	protected Login login;
	protected Connection conn=null;
	protected static String separator = OperatingSystem.getSeparator();
	private String rootLocalDir = OperatingSystem.getRootLocalDir();
	private String rootFundLocal = "";
	private String rootRepositorioLocal ="";
	private String rootRepositorioLocalNew ="";
	private String localRepositoryPathNew = "";
	private String localRepositoryPath = "";
	private ArrayList<KeywordProcess> keywordProcess=new ArrayList<KeywordProcess>();

	public Organize()
	{
		
	}
	
	public Organize(Login login, Connection conn)
	{
		this.login=login;
		this.conn=conn;
	}
	
	public Organize(FundoDeInvestimento fundo, int idEntidadeServidor, Login login, Connection conn)
	{
		this.fundo = fundo;
		this.idEntidadeServidor=idEntidadeServidor;
		this.login=login;
		this.conn = conn;
		this.setPassword();
		this.setFolders();
		this.setKeywordProcess();
	}
	
	public void setFolders()
	{
		this.rootLocalDir = OperatingSystem.getRootLocalDir();
		this.rootFundLocal = "Fundos" + separator + fundo.getCategoriaGeralAnbima() + separator + fundo.getNomeCurto().toUpperCase()+"_"+fundo.getCodigoCVM();
		this.rootRepositorioLocal = rootFundLocal+separator+"Repositorio"+separator+"Transferido";
		this.rootRepositorioLocalNew = rootFundLocal+separator+"Repositorio"+separator+"Transferir";
		this.localRepositoryPathNew = rootLocalDir + separator + rootRepositorioLocalNew;
		this.localRepositoryPath = rootLocalDir + separator + rootRepositorioLocal;
//		System.out.println("Folder where original files are:\n" + localRepositoryPathNew);
//		System.out.println("After Organize will be moved to:\n" + localRepositoryPath);	
	}	
	public void setIdEntidadeServidor()
	{
		/**
		 * Finding idEntidadeServidor for Login
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidadeServidor FROM Login WHERE idLogin=" + this.login.getIdLogin());
			while (rs.next())
			{				
				this.idEntidadeServidor = rs.getInt("idEntidadeServidor");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void organizeAll()
	{
		System.out.println("Organize for files from fund " + this.fundo.getNomeCurto());
		this.unzipOrMoveAllFiles();
		this.cleanUnzipContentAllFolders();	
		this.organizeUnzipContentAllFolders();
	}
	public ArrayList<KeywordProcess> getKeywordProcess()
	{
		return this.keywordProcess;
	}		
	public void setKeywordProcess()
	{
		/**
		 * Finding keyword for Login from KeywordProcess
		 */
//		System.out.println("----------------------------");
//		System.out.println("List of keywordProcess");
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idKeywordProcess FROM KeywordProcess WHERE idLogin=" + this.login.getIdLogin());
			while (rs.next())
			{				
				KeywordProcess kProcess = new KeywordProcess(rs.getInt("idKeywordProcess"), this.getConn());
				this.keywordProcess.add(kProcess);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void organizeUnzipContentAllFolders()
	{
		String rootUnzip = this.localRepositoryPathNew +separator+"unzip";
		ArrayList<File> localFiles = OperatingSystem.filesInDirectory(rootUnzip);
		ArrayList<File> folders = new ArrayList<File>();
		System.out.println("--------------------------");
		System.out.println("Processing files inside unzip folder");
		System.out.println("Folders List");
		for(File file:localFiles)
		{
			if(file.isDirectory())
			{
				System.out.println("Folder " + file.getName());
				folders.add(file);
			}
		}
		
		for (File folder:folders)
		{
			System.out.println("--------------------------");
			System.out.println("Organizing files inside " + folder.getName()); 			
			ArrayList<File> filesInside = OperatingSystem.filesInDirectory(folder.getPath());
					
			for(File file:filesInside)
			{
				//
				boolean foundKeyword=false;
				for(KeywordProcess keyProcess:keywordProcess)
				{
					//if (file.getName().contains(keyProcess.getKeyword()))
					if (file.getName().matches(keyProcess.getKeyword()))
					{
						System.out.println("\tOrganize " + file.getName() + "\tKeyword " + keyProcess.getKeyword() + " --> " +keyProcess.getKeywordProcessor().getKeyword());
						this.caseOrganize(file, folder, keyProcess);
						foundKeyword=true;
						break;
					}					
				}
				if(!foundKeyword)
				{
					System.out.println("\tSkip " + file.getName());
				}
			}
		}
	}
	
	public void caseOrganize(File file, File folder, KeywordProcess keyProcess)
	{
		switch (keyProcess.getKeywordProcessor().getKeyword())
		{
			case "carteira":
				System.out.println("** File to be organized by Carteira ** : " + file.getName() );
				OrganizeCarteira organizeCarteira = new OrganizeCarteira(this.fundo, this.idEntidadeServidor,this.login,this.conn);
				organizeCarteira.organizeFile(file, folder);
				break;				
			case "cotas":
				OrganizeCotas organizeCotas = new OrganizeCotas(this.fundo, this.idEntidadeServidor,this.login,this.conn);
				organizeCotas.organizeFile(file, folder);
				break;				
			case "cotistas":
				OrganizeCotistas organizeCotistas = new OrganizeCotistas(this.fundo, this.idEntidadeServidor,this.login,this.conn);
				organizeCotistas.organizeFile(file, folder);
				break;
			case "direitoscreditorios":
				OrganizeDireitosCreditorios organizeDireitosCreditorios = new OrganizeDireitosCreditorios(this.fundo, this.idEntidadeServidor,this.login,this.conn);
				organizeDireitosCreditorios.organizeFile(file, folder);
				break;
			case "caixa":
				OrganizeCaixa organizeCaixa = new OrganizeCaixa(this.fundo, this.idEntidadeServidor,this.login,this.conn);
				organizeCaixa.organizeFile(file, folder);
				break;				
				/*
			case "existencia":
				OrganizeExistencia(folderTempZip, fundo);
				break;
			case "others":
				OrganizeOthers(folderTempZip, fundo);
				break;
				*/
			default:
				break;
		}
	}
	
	public void cleanUnzipContentAllFolders()
	{
		String rootUnzip = this.localRepositoryPathNew +separator+"unzip";
		ArrayList<File> localFiles = OperatingSystem.filesInDirectory(rootUnzip);
		ArrayList<File> folders = new ArrayList<File>();
		ArrayList<File> files = new ArrayList<File>();
		System.out.println("--------------------------");
		System.out.println("Cleaning file names inside unzip folder");
		for(File file:localFiles)
		{
			if(file.isDirectory())
			{
				System.out.println("Folder " + file.getName());
				folders.add(file);
			}
			else
			{
				System.out.println("File " + file.getName());
				files.add(file);
			}
		}
		
		for (File folder:folders)
		{
			ArrayList<File> filesInside = OperatingSystem.filesInDirectory(folder.getPath());
			System.out.println("Folder " + folder.getName());	
			if(filesInside.size()==0)
			{
				try {
					OperatingSystem.delete(folder);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else
			{
				for(File file:filesInside)
				{
					String fileNameClean=CleanString.cleanWithoutSpace(file.getName().toLowerCase());
					System.out.println(fileNameClean);
					File destiny = new File(folder.getPath()+Organize.separator+fileNameClean);
	
					if(!file.getName().equals(fileNameClean))
					{
						file.renameTo(destiny);
						System.out.println("\t" + file.getName() + "\t|" + fileNameClean);					
					}
					else
					{
						System.out.println("\t" + file.getName());
					}
				}
			}
		}
	}
	
	
	public void unzipOrMoveAllFiles()
	{
		ArrayList<File> localFiles = OperatingSystem.filesInDirectory(this.localRepositoryPathNew);
		int iFile=0;
		System.out.println("Extracting all .zip files and moving files with other extensions");
		if(localFiles.size()!=0)
		{
			for (File file:localFiles)
			{				
				if(file.isDirectory())
				{
					System.out.println("Folder " + file.getName());
				}
				else
				{
					System.out.println(file.getName());
					String extension = file.getName().substring(file.getName().length()-4, file.getName().length());
					Pattern pattern = Pattern.compile("[0-9]+"); 
					Matcher matcher = pattern.matcher(file.getName());
		
					String match="";
					// Find all matches
					while (matcher.find()) 
					{     						
					   match=match+matcher.group();    							  
					}     												
//					String newName = file.getName().substring(0,file.getName().length()-4)+"_"+match;
					String newName = file.getName();
					
					newName = this.fundo.getNomeCurto()+newName
															.replace(this.fundo.getNomeCurto(), "")
//															.replace(match, "")
															.replace("__", "_")
															.replace("_.", ".");
					while(newName.charAt(newName.length()-1)=='_')
					{
						System.out.println("OldName: " + newName);
						newName=newName.substring(0,newName.length()-1);
						System.out.println("NewName: " + newName);
					}
					
					String folderName = this.localRepositoryPathNew +separator+"unzip"+separator+newName;
					OperatingSystem.checkDirectory(folderName);
					
					if(file.exists())
					{
						if(file.isDirectory())
						{
							if(file.getName().equals("unzip"))
							{
								System.out.println("---------------\n" +(iFile+1) + "/"+ localFiles.size() + " " + file.getName() + ": Folder repository for unzip files");
							}
						}
						else if (extension.equals(".zip"))
						{
							System.out.println("---------------\n" +(iFile+1) + "/"+ localFiles.size() + " " + file.getName());
							this.unzipFile(file, folderName);
						}
						else
						{					
							OperatingSystem.copyRecentFile(file, new File(folderName + separator + file.getName()));
							OperatingSystem.copyRecentFile(file, new File(this.localRepositoryPath + separator + file.getName()));
							try {
								OperatingSystem.delete(file);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					iFile++;
				}
			}
		}
	}
	
	public void unzipFile(File file, String folderName)
	{
		File destiny = new File(this.localRepositoryPath +separator+file.getName());
		OperatingSystem.extractFile(file.getAbsolutePath(), password, folderName);
		OperatingSystem.copyRecentFile(file, destiny);
		try {
			OperatingSystem.delete(file);
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

	public String getPassword() {
		return password;
	}

	public void setPassword() {
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT password FROM SenhaZip WHERE idFundo=" + this.getFundo().getIdFundo() + " AND idEntidadeServidor=" + this.idEntidadeServidor);
			while (rs.next())
			{				
				this.password = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		System.out.println("Zip files password: " + this.getPassword());
	}

	public FundoDeInvestimento getFundo() {
		return fundo;
	}

	public void setFundo(FundoDeInvestimento fundo) {
		this.fundo = fundo;
	}

	public int getIdEntidadeServidor() {
		return idEntidadeServidor;
	}

	public void setIdEntidadeServidor(int idEntidadeServidor) {
		this.idEntidadeServidor = idEntidadeServidor;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		Organize.separator = separator;
	}

	public String getRootLocalDir() {
		return rootLocalDir;
	}

	public void setRootLocalDir(String rootLocalDir) {
		this.rootLocalDir = rootLocalDir;
	}

	public String getRootFundLocal() {
		return rootFundLocal;
	}

	public void setRootFundLocal(String rootFundLocal) {
		this.rootFundLocal = rootFundLocal;
	}

	public String getRootRepositorioLocal() {
		return rootRepositorioLocal;
	}

	public void setRootRepositorioLocal(String rootRepositorioLocal) {
		this.rootRepositorioLocal = rootRepositorioLocal;
	}

	public String getRootRepositorioLocalNew() {
		return rootRepositorioLocalNew;
	}

	public void setRootRepositorioLocalNew(String rootRepositorioLocalNew) {
		this.rootRepositorioLocalNew = rootRepositorioLocalNew;
	}

	public String getLocalRepositoryPathNew() {
		return localRepositoryPathNew;
	}

	public void setLocalRepositoryPathNew(String localRepositoryPathNew) {
		this.localRepositoryPathNew = localRepositoryPathNew;
	}

	public String getLocalRepositoryPath() {
		return localRepositoryPath;
	}

	public void setLocalRepositoryPath(String localRepositoryPath) {
		this.localRepositoryPath = localRepositoryPath;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
