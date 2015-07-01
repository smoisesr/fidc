package organize.petra;
import java.io.File;
import java.io.IOException;

import organize.OrganizeCaixa;

import com.mysql.jdbc.Connection;

import fundo.FundoDeInvestimento;
import utils.Login;
import utils.OperatingSystem;

public class OrganizeCaixaPetra extends OrganizeCaixa
{
	public OrganizeCaixaPetra(FundoDeInvestimento fundo, int idEntidadeServidor, Login login, Connection conn)	
	{
		super(fundo, idEntidadeServidor, login, conn);
	}
	public void organizeFile(File file, File folder)
	{
		System.out.println("\t\tOrganizeCaixa: " + file.getName() + " from " + super.getEntidadeServidor().getNomeCurto());
		String extension = file.getName().substring(file.getName().length()-4,file.getName().length()).toLowerCase();
		String subFolderName = OperatingSystem.folderNameFromDate(OperatingSystem.extractDateFromDirectory(folder.getName()));
		if (extension.equals(".pdf"))
		{
			String destinationFolder = super.rootCaixa + separator + subFolderName;
			OperatingSystem.checkDirectory(destinationFolder);						
			String fileNamePDF = "Caixa_" + fundo.getNomeCurto() + "_" + OperatingSystem.extractDateFromDirectory(folder.getName()) + ".pdf";

    		File destiny = new File(destinationFolder+separator+fileNamePDF);
    		File tempDestiny = new File(super.rootCaixaProcessar+separator+fileNamePDF);
    		OperatingSystem.copyRecentFile(file, destiny);
    		OperatingSystem.copyRecentFile(file, tempDestiny);
    		try {
				OperatingSystem.delete(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (extension.equals(".csv"))
		{
			String destinationFolder = super.rootCaixa + separator + subFolderName;
			OperatingSystem.checkDirectory(destinationFolder);						
			String fileNameCSV = "Caixa_" + fundo.getNomeCurto() + "_" + OperatingSystem.extractDateFromDirectory(folder.getName()) + ".csv";

    		File destiny = new File(destinationFolder+separator+fileNameCSV);
    		File tempDestiny = new File(super.rootCaixaProcessar+separator+fileNameCSV);
    		OperatingSystem.copyRecentFile(file, destiny);
    		OperatingSystem.copyRecentFile(file, tempDestiny);
    		try {
				OperatingSystem.delete(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (extension.equals(".xls"))
		{
			String destinationFolder = super.rootCaixa + separator + subFolderName;
			OperatingSystem.checkDirectory(destinationFolder);						
			String fileNameXLS = "Caixa_" + fundo.getNomeCurto() + "_" + OperatingSystem.extractDateFromDirectory(folder.getName()) + ".xls";

    		File destiny = new File(destinationFolder+separator+fileNameXLS);
    		File tempDestiny = new File(super.rootCaixaProcessar+separator+fileNameXLS);
    		OperatingSystem.copyRecentFile(file, destiny);
    		OperatingSystem.copyRecentFile(file, tempDestiny);    		
    		try {
				OperatingSystem.delete(file);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		
	}
	
	public static void classifyRow(String line)
	{
		
	}
}
