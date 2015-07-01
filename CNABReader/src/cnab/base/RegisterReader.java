package cnab.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cnab.bradesco.conciliacao.RegisterDetailConciliacaoBradesco;
import cnab.bradesco.conciliacao.RegisterFileHeaderConciliacaoBradesco;
import cnab.bradesco.conciliacao.RegisterFileTrailerConciliacaoBradesco;
import cnab.bradesco.conciliacao.RegisterLotHeaderConciliacaoBradesco;
import cnab.bradesco.conciliacao.RegisterLotTrailerConciliacaoBradesco;
import cnab.remessa.paulista.RegisterDetailRemessaPaulista444;
import cnab.remessa.paulista.RegisterFileHeaderRemessaPaulista444;
import cnab.remessa.paulista.RegisterFileTrailerRemessaPaulista444;



public class RegisterReader
{
	public static void main(String [ ] args)
	{
	}
	
	public RegisterReader()
	{
		
	}
	
	public void readRegisters(File file)
	{
		ArrayList<String> lines = new ArrayList<String>();
		FileReader fileReader = null;
		try 
		{
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(fileReader);
		 // if no more lines the readLine() returns null
		 try 
		 {
			String line = null;
			while ((line = br.readLine()) != null) 
			 {
			      // reading lines until the end of the file				
				lines.add(line);
			 }
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		 
		for (String line:lines)
		{			
			//System.out.println(RegisterIdentifyer.getIdentifier(line).getIdentifierDescription() + "  " + line);
			if(RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("DETAIL_CONCILIACAO_BRADESCO"))
			{
				new RegisterDetailConciliacaoBradesco(line).showRegister();
			}
			else if(RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_HEADER_CONCILIACAO_BRADESCO"))
			{
				System.out.println("------------------------------------");
				System.out.println("Começo de Arquivo de conciliação do Bradesco");
				System.out.println("------------------------------------");
				new RegisterFileHeaderConciliacaoBradesco(line).showRegister();
			}
			else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_TRAILER_CONCILIACAO_BRADESCO"))
			{
				new RegisterFileTrailerConciliacaoBradesco(line).showRegister();
				System.out.println("------------------------------------");
				System.out.println("Final de Arquivo de conciliação do Bradesco");
				System.out.println("------------------------------------");
			}
			else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("LOT_HEADER_CONCILIACAO_BRADESCO"))
			{
				new RegisterLotHeaderConciliacaoBradesco(line).showRegister();
			}
			else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("LOT_TRAILER_CONCILIACAO_BRADESCO"))
			{
				new RegisterLotTrailerConciliacaoBradesco(line).showRegister();
			}
			else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_HEADER_REMESSA_PAULISTA"))
			{
				System.out.println("------------------------------------");
				System.out.println("Começo de Arquivo de remessa do Paulista");
				System.out.println("------------------------------------");
				new RegisterFileHeaderRemessaPaulista444(line).showRegister();				
			}
			else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("FILE_TRAILER_REMESSA_PAULISTA"))
			{
				new RegisterFileTrailerRemessaPaulista444(line).showRegister();
				System.out.println("------------------------------------");
				System.out.println("Final de Arquivo de remessa do Paulista");
				System.out.println("------------------------------------");				
			}
			else if (RegisterIdentifyer.getIdentifier(line).getIdentifierDescription().equals("DETAIL_REMESSA_PAULISTA"))
			{				
				new RegisterDetailRemessaPaulista444(line).showRegister();				
			}
		}
	}	
}
