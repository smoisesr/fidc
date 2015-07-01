package treat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import report.Report;
import report.petra.ReportPetra;
import treat.petra.TreatCarteiraPetra;
import treat.socopa.TreatCarteiraSocopa;
import utils.Login;
import utils.OperatingSystem;

import com.mysql.jdbc.Connection;

import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class TreatCarteira extends Treat 
{
	protected String rootCarteira = "";
	protected String rootCarteiraProcessar = "";
	protected String rootCarteiraProcessado = "";
	protected String rootCarteiraProcessadoOriginal = "";

	public TreatCarteira()
	{
		super();
	}
	
	public TreatCarteira(FundoDeInvestimento fundo, int idEntidadeServidor, Login login, Connection conn)
	{
		super(fundo,idEntidadeServidor,login,conn);
		this.setFolders();
	}
	
	public void setFolders()
	{
		super.setFolders();
		this.rootCarteira = super.getRootLocalDir()+Treat.separator+super.getRootFundLocal()+Treat.separator+"Carteira";
		this.rootCarteiraProcessar = rootCarteira+Treat.separator+"Processar";
		this.rootCarteiraProcessado = rootCarteira+Treat.separator+"Processado";
		this.rootCarteiraProcessadoOriginal = rootCarteiraProcessado+Treat.separator+"Original";	
		/*
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println("rootFundoLocal: " + super.getRootFundLocal());
		System.out.println("rootCarteira: " + this.rootCarteira);
		System.out.println("rootCarteiraProcessar: " + this.rootCarteiraProcessar);
		System.out.println("rootCarteiraProcessado: " + this.rootCarteiraProcessado);
		System.out.println("rootCarteiraProcessadoOriginal: " + this.rootCarteiraProcessadoOriginal);
		System.out.println("**********************************");
		System.out.println("**********************************");
		*/
	}
	
	public void treatFolder()
	{
		ArrayList<File> localFiles = OperatingSystem.filesInDirectory(this.rootCarteiraProcessar);
		System.out.println("--------------------------");
		System.out.println("Processing files inside folder Carteira for fund " + this.getFundo().getNomeCurto());
		for(File file:localFiles)
		{
			String extension = file.getName().substring(file.getName().length()-4);
			
			if(file.length()==0)
			{
				System.out.println("\tFile empty ");
				try {
					OperatingSystem.delete(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}
			else
			{
				if(extension.equals(".txt"))
				{				
					System.out.println("\t" + file.getName());
					this.treatFile(file, this.rootCarteiraProcessar);
					
					File source = file;
					File destiny = new File(this.rootCarteiraProcessadoOriginal + Treat.separator + file.getName());
					OperatingSystem.copyRecentFile(source, destiny);
					
					try {
						OperatingSystem.delete(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else if(extension.equals(".xls"))
				{
					System.out.println("\t" + file.getName());
					this.treatFile(file, this.rootCarteiraProcessar);
					
					File source = file;
					File destiny = new File(this.rootCarteiraProcessadoOriginal + Treat.separator + file.getName());
					OperatingSystem.copyRecentFile(source, destiny);
					try 
					{
						OperatingSystem.delete(file);
					} catch (IOException e) {
						e.printStackTrace();
					}						
				}
				else if(extension.equals(".tmp"))
				{
					System.out.println("\t" + file.getName());
					this.treatFile(file, this.rootCarteiraProcessar);
					
					File source = file;
					File destiny = new File(this.rootCarteiraProcessadoOriginal + Treat.separator + file.getName());
					OperatingSystem.copyRecentFile(source, destiny);
					try 
					{
						OperatingSystem.delete(file);
					} catch (IOException e) {
						e.printStackTrace();
					}						
	
				}
				else if(extension.equals(".tmc"))
				{
					System.out.println("\t" + file.getName());
					this.treatFile(file, this.rootCarteiraProcessar);
					
					File source = file;
					File destiny = new File(this.rootCarteiraProcessadoOriginal + Treat.separator + file.getName());
					OperatingSystem.copyRecentFile(source, destiny);
					try 
					{
						OperatingSystem.delete(file);
					} catch (IOException e) {
						e.printStackTrace();
					}						
				}
			}
		}
	}
	
	public void treatFile(File file, String rootCarteiraProcessar2)
	{
		this.chooseTreatByEntidade(this.getEntidadeServidor(), file, rootCarteiraProcessar2);
	}
	
	public void chooseTreatByEntidade(Entidade entidadeServidor, File file, String folder)
	{
		switch (entidadeServidor.getNomeCurto())
		{
			case "Petra":
				TreatCarteiraPetra treatCarteiraPetra = new TreatCarteiraPetra(this.fundo, super.idEntidadeServidor,this.login,this.conn);
				treatCarteiraPetra.treatFile(file, folder);
				String dataDePosicaoPetra=ReportPetra.formatterDateShortNumber.format(treatCarteiraPetra.getReport().getBlockCabecalho().getDataDaPosicao());
//				System.out.println("DataDePosicaoPetra: " + dataDePosicao.replace("/", "-"));
//				String fileNameCSV=file.getName().substring(0, file.getName().length()-4)+"_" + dataDePosicaoPetra + ".csv";
				String pathFile= file.getPath().substring(0, file.getPath().length()-4)+"_" + dataDePosicaoPetra + ".csv";
				System.out.println("Writing file " + pathFile);
				BufferedWriter writer = null;
				try {
					writer = new BufferedWriter( new FileWriter(pathFile));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					writer.write(treatCarteiraPetra.getReport().getReportCSV());
					System.out.println("Writed file " + pathFile);
				} catch (IOException e) {
	
					e.printStackTrace();
				}
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				OperatingSystem.copyRecentFile(file, new File (this.rootCarteiraProcessadoOriginal + Treat.separator + file.getName()));
				break;
			case "Socopa":
				TreatCarteiraSocopa treatCarteiraSocopa = new TreatCarteiraSocopa(this.fundo, super.idEntidadeServidor,this.login,this.conn);				
				treatCarteiraSocopa.treatFile(file, folder);
				String extensionFile = file.getName().substring(file.getName().length()-4,file.getName().length());
				if(extensionFile.equals(".xls"))
				{
				}	
				else if(extensionFile.equals(".tmp")||extensionFile.equals(".tmc")||extensionFile.equals(".txt"))
				{
					if(!(treatCarteiraSocopa.getReport()==null))
					{
						String dataDePosicaoSocopa=Report.formatterDateShortNumber.format(treatCarteiraSocopa.getReport().getBlockCabecalho().getDataDaPosicao());
	//					System.out.println("DataDePosicaoSocopa: " + dataDePosicaoSocopa.replace("/", "-"));
						String fileNameCSV1=file.getName().substring(0, file.getName().length()-4).replace(".tmp", "")+"_" + dataDePosicaoSocopa + ".csv";
						String pathFile1= this.rootCarteiraProcessar + Treat.separator + fileNameCSV1;				
						
						BufferedWriter writer1 = null;
						try {
							writer1 = new BufferedWriter( new FileWriter(pathFile1));
						} catch (IOException e) {
							e.printStackTrace();
						}
						try {
							writer1.write(treatCarteiraSocopa.getReport().getReportCSV());
						} catch (IOException e) {
			
							e.printStackTrace();
						}
						try {
							writer1.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				break;
			default:
				break;
		}

	}
	
	public Entidade getEntidadeServidor()
	{
		Entidade entidade=null;
		entidade = new Entidade(super.login.getIdEntidadeServidor(), super.conn);
		return entidade;
	}
	
	public String getRootCarteira() {
		return rootCarteira;
	}

	public void setRootCarteira(String rootCarteira) {
		this.rootCarteira = rootCarteira;
	}

	public String getRootCarteiraProcessar() {
		return rootCarteiraProcessar;
	}

	public void setRootCarteiraProcessar(String rootCarteiraProcessar) {
		this.rootCarteiraProcessar = rootCarteiraProcessar;
	}

	public String getRootCarteiraProcessado() {
		return rootCarteiraProcessado;
	}

	public void setRootCarteiraProcessado(String rootCarteiraProcessado) {
		this.rootCarteiraProcessado = rootCarteiraProcessado;
	}

	public String getRootCarteiraProcessadoOriginal() {
		return rootCarteiraProcessadoOriginal;
	}

	public void setRootCarteiraProcessadoOriginal(
			String rootCarteiraProcessadoOriginal) {
		this.rootCarteiraProcessadoOriginal = rootCarteiraProcessadoOriginal;
	}
}
