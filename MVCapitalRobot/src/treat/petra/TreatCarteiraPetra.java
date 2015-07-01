package treat.petra;
//import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import report.petra.ReportPetra;
import treat.TreatCarteira;

import com.mysql.jdbc.Connection;

import fundo.FundoDeInvestimento;
import utils.Login;

public class TreatCarteiraPetra extends TreatCarteira
{
	private ReportPetra report = null;
	public TreatCarteiraPetra(FundoDeInvestimento fundo, int idEntidadeServidor, Login login, Connection conn)	
	{
		super(fundo, idEntidadeServidor, login, conn);
	}
	public void treatFile(File file, String folder)
	{		
		String extension=file.getName().substring(file.getName().length()-4, file.getName().length());
		if(extension.equals(".txt"))
		{
			System.out.println("\tTreat " + file.getName() + " from " + super.getEntidadeServidor().getNomeCurto());
//			String fileNewName=file.getName();
//			String pathFile = file.getPath();
//			System.out.println("Path: " + pathFile);
//			System.out.println("File: " + fileNewName);
			ArrayList<String> lines = new ArrayList<String>();
			ArrayList<String> textLines = new ArrayList<String>();
			try {
				lines = (ArrayList<String>) Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for (String line:lines)
			{
				if (line.length()>0)
				{
					textLines.add(line.trim());
				}
			}
			//ArrayList<String> cleanLines = this.report.checkLines(textLines);
			this.report=new ReportPetra(super.getFundo(), textLines);
			this.report.setReportCSV();
//			System.out.println(this.report.getReportCSV());			
		}
	}
	
	public static void classifyRow(String line)
	{
		
	}
	public ReportPetra getReport() {
		return report;
	}
	public void setReport(ReportPetra report) {
		this.report = report;
	}
}
