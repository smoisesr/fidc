package organize.petra;
import java.io.File;
import java.io.IOException;

import organize.OrganizeCotas;

import com.mysql.jdbc.Connection;

import fundo.FundoDeInvestimento;
import utils.Login;
import utils.OperatingSystem;

public class OrganizeCotasPetra extends OrganizeCotas
{
	public OrganizeCotasPetra(FundoDeInvestimento fundo, int idEntidadeServidor, Login login, Connection conn)	
	{
		super(fundo, idEntidadeServidor, login, conn);
	}
	public void organizeFile(File file, File folder)
	{
		System.out.println("\t\tOrganizeCotas: " + file.getName() + " from " + super.getEntidadeServidor().getNomeCurto());
		String extension = file.getName().substring(file.getName().length()-4,file.getName().length()).toLowerCase();
		String subFolderName = OperatingSystem.folderNameFromDate(OperatingSystem.extractDateFromDirectory(folder.getName()));
		if (extension.equals(".pdf"))
		{
			String destinationFolder = super.rootCotas + separator + subFolderName;
			OperatingSystem.checkDirectory(destinationFolder);						
			String fileNamePDF = "Cotas_" + fundo.getNomeCurto() + "_" + OperatingSystem.extractDateFromDirectory(folder.getName()) + ".pdf";

    		File destiny = new File(destinationFolder+separator+fileNamePDF);
    		File tempDestiny = new File(super.rootCotasProcessar+separator+fileNamePDF);
    		OperatingSystem.copyRecentFile(file, destiny);
    		OperatingSystem.copyRecentFile(file, tempDestiny);
    		try {
				OperatingSystem.delete(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (extension.equals(".txt"))
		{
			/*
			System.out.println("\t\tDeleting");
    		try {
				OperatingSystem.delete(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
		}
	}
	
	public static void classifyRow(String line)
	{
		
	}
}
