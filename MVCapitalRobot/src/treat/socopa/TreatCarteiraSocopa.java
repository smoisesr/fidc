package treat.socopa;
//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import report.socopa.ReportSocopa;
import treat.TreatCarteira;

import com.mysql.jdbc.Connection;

import fundo.FundoDeInvestimento;
import utils.CleanString;
import utils.Login;
import utils.OperatingSystem;
import utils.XlsToCsv;

public class TreatCarteiraSocopa extends TreatCarteira
{
	private ReportSocopa report = null;
	public TreatCarteiraSocopa(FundoDeInvestimento fundo, int idEntidadeServidor, Login login, Connection conn)	
	{
		super(fundo, idEntidadeServidor, login, conn);
	}
	public void treatFile(File file, String folder)
	{	
		System.out.println("FileCarteira: " + file.getName() + " within " + folder);
		String extension=file.getName().substring(file.getName().length()-4, file.getName().length());
		if(extension.equals(".txt"))
		{
			System.out.println("\tTreat " + file.getName() + " from " + super.getEntidadeServidor().getNomeCurto());
			ArrayList<String> lines = new ArrayList<String>();
			ArrayList<String> textLines = new ArrayList<String>();
			try {
				lines = (ArrayList<String>) Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
				//lines = (ArrayList<String>) Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for (String line:lines)
			{
				System.out.println(line);
				if (line.length()>0)
				{
//					String[] fields = line.split(" ");
					if(line.matches(".*\\d.*")||line.matches(".*\\w.*"))
					{
						textLines.add(line.trim().replaceAll(" +", " ").replace("\t", ";"));
//						System.out.println(line.matches(".*\\d.*"));
//						System.out.println(line.trim().replaceAll(" +", " ").replace("\t", ";"));
						 
					}
				}
			}			
			//ArrayList<String> cleanLines = this.report.checkLines(textLines);
			this.report=new ReportSocopa(super.getFundo(), textLines);
			this.report.setReportCSV();
//			System.out.println(report.getReportCSV());			
		}
		else if(extension.equals(".xls"))
		{
			System.out.println("File: " + file.getName() + " to be converted to csv");
			XlsToCsv.xls(file);
			File destiny = new File(super.rootCarteiraProcessadoOriginal + separator + file.getName());
					
			OperatingSystem.copyRecentFile(file, destiny);
//			try {
//				OperatingSystem.delete(file);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}			
		}
		else if(extension.equals(".tmp"))
		{
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}  
			int b = 0;
			try {
				b = fis.read();
			} catch (IOException e1) {
				e1.printStackTrace();
			}  
			if (b == -1)  
			{  
			  System.out.println("!!File " + file.getName() + " emty!!");  
			} 
			else
			{
				ArrayList<String> lines = new ArrayList<String>();
				ArrayList<String> cleanLines = new ArrayList<String>();
				try 
				{
					lines = (ArrayList<String>) Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
				} catch (IOException e) 
				{
					e.printStackTrace();
				}			
				for(String line:lines)
				{
	//				System.out.println(CleanString.cleanWithSpace(line).replace("\"", ""));
					cleanLines.add(CleanString.cleanWithSpace(line).replace("\"", ""));
				}
				
				this.report=new ReportSocopa(super.getFundo(), cleanLines);
				this.report.setReportCSV();
	//			System.out.println(report.getReportCSV());
			}
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			try {
//				OperatingSystem.delete(file);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}						
		}
		else if(extension.equals(".tmc"))
		{
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}  
			int b = 0;
			try {
				b = fis.read();
			} catch (IOException e1) {
				e1.printStackTrace();
			}  
			if (b == -1)  
			{  
			  System.out.println("!!File " + file.getName() + " emty!!");  			  
			} 
			else
			{
				ArrayList<String> lines = new ArrayList<String>();
				ArrayList<String> cleanLines = new ArrayList<String>();
				try 
				{
					lines = (ArrayList<String>) Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
				} catch (IOException e) 
				{
					e.printStackTrace();
				}			
				for(String line:lines)
				{
					if(!line.replace(";", "").equals(""))
					{
						cleanLines.add(CleanString.cleanWithSpace(line).replace("\"", ""));
//						System.out.println(line);
					}
				}
				
				this.report=new ReportSocopa(super.getFundo(), cleanLines);
				this.report.setReportCSV();
	//			System.out.println(report.getReportCSV());
			}
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			try {
//				OperatingSystem.delete(file);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}			
		}
	}
	
	public static void classifyRow(String line)
	{
		
	}
	public ReportSocopa getReport() {
		return report;
	}
	public void setReport(ReportSocopa report) {
		this.report = report;
	}
}
