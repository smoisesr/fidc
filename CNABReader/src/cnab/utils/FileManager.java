package cnab.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class FileManager 
{
	public static ArrayList<File> inDirectory(String directory)
	{
	    ArrayList<File> filesList = new ArrayList<File>();
	    File folder = new File(directory);
	    File[] listOfFiles = folder.listFiles();
	    
	    if (folder.listFiles()==null)
	    {
	      return null;
	    }
	    else
	    {
	      for (int i = 0; i < listOfFiles.length; i++) 
	      {
	        filesList.add(listOfFiles[i]);
	      }
	      return filesList;
	    }
	}
	
	public static void copyFilesToProcessed(ArrayList<File> listFiles, String destinyDirectory)
	  {
	    for(File file:listFiles)
	    {
	      copyFile(file, destinyDirectory);	      
	    }
	  }

	public static void deleteFiles(ArrayList<File> listFiles)
	{
		Calendar cal = new GregorianCalendar();
		System.out.println("Before sleep " + cal.getTime().toString());
		
		
		try 
		{
			TimeUnit.NANOSECONDS.sleep(2000000000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Calendar cal2 = new GregorianCalendar();
		System.out.println("After sleep " + cal2.getTime().toString());

	    for(File file:listFiles)
	    {
			file.delete();
	    }
	}
	
	  public static void copyFile(File source, String outputFolder)
	  {
		  File output = new File(outputFolder + source.getName());
		  InputStream inStream = null;
		  OutputStream outStream = null;
		 
		    	try{
		 
		    	    File afile = source;
		    	    File bfile = output;
		 
		    	    inStream = new FileInputStream(afile);
		    	    
		    	    outStream = new FileOutputStream(bfile);
		 
		    	    byte[] buffer = new byte[1024];
		 
		    	    int length;
		    	    //copy the file content in bytes 
		    	    while ((length = inStream.read(buffer)) > 0){
		 
		    	    	outStream.write(buffer, 0, length);
		 
		    	    }
		    	    inStream.close();
		    	    outStream.close();
		 
		    	    //delete the original file
		    	    //afile.deleteOnExit();
		    	    //afile.delete();
		    	    System.out.println("File is copied successful!");
		 
		    	}
		    	catch(IOException e)
		    	{
		    	    e.printStackTrace();
		    	}
		    
	  }
	 
	  public void deleteTempDestination(String tempFolder)
		{
			File directory = new File(tempFolder);
			 
	    	//make sure directory exists
	    	if(!directory.exists())
	    	{
	           System.out.println("Directory does not exist.");
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
	  
	  public static void delete(File file)
		    	throws IOException
		{
		 
		    	if(file.isDirectory()){
		 
		    		//directory is empty, then delete it
		    		if(file.list().length==0){
		 
		    		   file.delete();
		    		   System.out.println("\tDirectory is deleted : " 
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
		        	     System.out.println("\tDirectory is deleted : " + file.getAbsolutePath());
		        	   }
		    		}
		 
		    	}
		    	else
		    	{
		    		//if file, then delete it
		    		System.out.println("File deleting " + file.delete());
		    		System.out.println("\tFile is deleted : " + file.getAbsolutePath());
		    	}
	    }	  
}
