package folder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import utils.OperatingSystem;

public class HandlerFolders 
{
	private ArrayList<String> foldersToCreate = new ArrayList<String>();
	private String toCreateList="W:\\Fundos\\Operacao\\Empresas\\GerarPastas.txt";
	private String createdList="W:\\Fundos\\Operacao\\Empresas\\ListaPastas.txt";
	
	public HandlerFolders() 
	{
	
	}
		
	public ArrayList<String> getFoldersToCreate() {
		return foldersToCreate;
	}
	public void setFoldersToCreate(ArrayList<String> foldersToCreate) {
		this.foldersToCreate = foldersToCreate;
	}
	
	public void createFolders()
	{
		this.foldersToCreate = readList();
		String hostFolder="W:\\Fundos\\Operacao\\Empresas\\Documentos\\";
		for(String folder:this.foldersToCreate)
		{
			String fileName=hostFolder + folder;
			File newFolder = new File(fileName);
			if (!newFolder.exists()) 
			{
				if (newFolder.mkdir()) 
				{
					System.out.println("Directory " + fileName +" is created!");
				} else {
					System.out.println("Failed to create directory! " + fileName);
				}
			}		 
		}		
	}

	public void addCreatedFolders()
	{
		for(String folder:foldersToCreate)
		{
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(createdList, true)))) 
			{
			    out.println(folder);
			    out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
	
	public void removeCreateList()
	{
		try {
		    Thread.sleep(3000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}

		File f = new File(toCreateList);
		if (f.exists())
		{
		  //delete if exists
		   f.delete();
		}
		FileWriter out=null;
		try {
			out = new FileWriter(f);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		try {
			out.write("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		

	}
	public ArrayList<String> readList()
	{
		ArrayList<String> foldersName = new ArrayList<String>();
		BufferedReader reader = null;
		System.out.println("Reading " + toCreateList + " file");
		System.out.println("------------------");
		try 
		{
			reader = new BufferedReader(new FileReader(toCreateList));
		} catch (FileNotFoundException e1) 
		{

			e1.printStackTrace();
		}
		
		String line = null;
		try 
		{
			while ((line = reader.readLine()) != null) 
			{
				if(!line.isEmpty())
				{
					System.out.println(line);
					foldersName.add(line);
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return foldersName;
	}
}
