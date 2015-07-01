package mvcapital.chavenfe.inserir;

import java.io.File;
import java.util.ArrayList;

public class SetFiles
{
	private File cnabFile=null;
	private File csvFile=null;
	private ArrayList<Pair> pairs=new ArrayList<Pair>();
	private String stringCnab=""; //$NON-NLS-1$
	
	public SetFiles()
	{

	}
	
	public SetFiles(File cnabFile, File csvFile)
	{
		this.cnabFile=cnabFile;
		this.csvFile=csvFile;
	}

	public File getCnabFile()
	{
		return this.cnabFile;
	}

	public void setCnabFile(File cnabFile)
	{
		this.cnabFile = cnabFile;
	}

	public File getCsvFile()
	{
		return this.csvFile;
	}

	public void setCsvFile(File csvFile)
	{
		this.csvFile = csvFile;
	}

	public ArrayList<Pair> getPairs()
	{
		return this.pairs;
	}

	public void setPairs(ArrayList<Pair> pairs)
	{
		this.pairs = pairs;
	}

	public String getStringCnab()
	{
		return stringCnab;
	}

	public void setStringCnab(String stringCnab)
	{
		this.stringCnab = stringCnab;
	}

}
