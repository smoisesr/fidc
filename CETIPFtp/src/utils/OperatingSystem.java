package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;


public class OperatingSystem 
{
	private static String rootLocalWindows="W:\\";
	private static String rootLocalLinux="/home/database";
	public static String separator="\\";
	public static String rootLocalDir="";
	
	public static void setSeparator()
	{
		String separatorWindows = "\\";
		String separatorLinux = "/";
	
		System.out.println("OS Name: " + System.getProperty("os.name").toLowerCase());
		if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			System.out.println("Set separator to " + separatorWindows);
			separator=separatorWindows;
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux"))
		{
			System.out.println("Set separator to " + separatorLinux);
			separator=separatorLinux;
		}
//		System.out.println("separator: " + separator);
	}
	public static String getSeparator()
	{
		return separator;
	}
	
	public static void setRootLocalDir()
	{
		System.out.println("OS Name: " + System.getProperty("os.name").toLowerCase());
		if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			System.out.println("Set rootLocalDir to " + rootLocalWindows);
			separator="\\";
			rootLocalDir=rootLocalWindows;
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux"))
		{
			System.out.println("Set rootLocalDir to " + rootLocalLinux);
			separator="/";
			rootLocalDir=rootLocalLinux;
		}
//		System.out.println("RootLocalDir: " + rootLocalDir);
	}

	public static String getRootLocalDir()	
	{
		setRootLocalDir();
		return rootLocalDir;
	}
	public static ArrayList<File> filesInDirectory(String directory)
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
	
	
	public static void delete(File file)
	    	throws IOException
	{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	 
	    		   file.delete();
	 	 
	    		   System.out.println("\t\tDirectory is deleted : " 
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
	        	     System.out.println("\t\tDirectory is deleted : " 
	                                                  + file.getAbsolutePath());
	        	   }
	    		}
	 
	    	}
	    	else
	    	{
	    		//if file, then delete it
	    		try {
	    		    Files.delete(file.toPath());
	    		} catch (NoSuchFileException x) {
	    		    System.err.format("%s: no such" + " file or directory%n", file.toPath());
	    		} catch (DirectoryNotEmptyException x) {
	    		    System.err.format("%s not empty%n", file.toPath());
	    		} catch (IOException x) {
	    		    // File permission problems are caught here.
	    		    System.err.println(x);
	    		}
	    		System.out.println("\t\tFile is deleted : " + file.getAbsolutePath());
	    	}
    }
	
	public static void copyRecentFile(File source, File destiny)
	{		
		Date lastModSource = new Date(source.lastModified());
		Date lastModDestiny = new Date(destiny.lastModified());
		/**
		 * In value > 0, lastModSource is more recent than lastModDestiny
		 */
		if (destiny.exists())
		{
			System.out.println("\t\tFile " + source + " exist");
			System.out.println("\t\tNew file last modification " + lastModSource.toString());
			System.out.println("\t\tOld file last modification " + lastModDestiny.toString());
			if(lastModSource.compareTo(lastModDestiny) > 0)
			{
				try
				{
					destiny.delete();
					Path tempPath = destiny.toPath();
					Files.copy(source.toPath(), tempPath);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			else
			{
				System.out.println("\t\tFile " + destiny + " is updated");
			}
		}
		else 
		{
			String tempPath = destiny.getAbsolutePath();
			
			destiny.delete();
			
			destiny = new File(tempPath);
			
			try
			{
				Files.copy(source.toPath(), destiny.toPath());
				System.out.println("\t\tCopying " + source.toPath() + "\n\t\tto " + destiny.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
    public static String extractDateFromDirectory(String fileName)
    {
    	int endIndex = fileName.length();
    	int beginIndex = endIndex - 8;
    	String yearMonth = fileName.substring(beginIndex, endIndex);
    	return yearMonth;
    }
    public static String folderNameFromDateFull(String yearMonthDay)
    {
    	String year = yearMonthDay.substring(0, 4);
    	String month = yearMonthDay.substring(4, 6);
//    	String day = yearMonthDay.substring(6, 8);
    	String result = year + "-" + month + " " + nameMonth(Integer.parseInt(month));
    	return result;
    }
    public static String folderNameFromDate(String yearMonthDay)
    {
    	String year = yearMonthDay.substring(0, 4);
    	String month = yearMonthDay.substring(4, 6);
//    	String day = yearMonthDay.substring(6, 8);
    	String result = year + "-" + month;
    	return result;
    }

    public static String nameMonth(int month)
    {
        String monthString;
        switch (month) {
            case 1:  monthString = "Jan";
                     break;
            case 2:  monthString = "Fev";
                     break;
            case 3:  monthString = "Mar";
                     break;
            case 4:  monthString = "Abr";
                     break;
            case 5:  monthString = "Mai";
                     break;
            case 6:  monthString = "Jun";
                     break;
            case 7:  monthString = "Jul";
                     break;
            case 8:  monthString = "Ago";
                     break;
            case 9:  monthString = "Set";
                     break;
            case 10: monthString = "Out";
                     break;
            case 11: monthString = "Nov";
                     break;
            case 12: monthString = "Dez";
                     break;
            default: monthString = "Invalid month";
                     break;
        }
        //System.out.println(monthString);
    			
    	return monthString;
    }
    public static void checkDirectory(String directory)
	{
		File dir = new File(directory);
		if(!dir.exists())
		{
			dir.mkdirs();
			System.out.println("\t\tFolder " + dir + " created ");
		}
	}

	public static String getRootLocalWindows() {
		return rootLocalWindows;
	}

	public static void setRootLocalWindows(String rootLocalWindows) {
		OperatingSystem.rootLocalWindows = rootLocalWindows;
	}

	public static String getRootLocalLinux() {
		return rootLocalLinux;
	}

	public static void setRootLocalLinux(String rootLocalLinux) {
		OperatingSystem.rootLocalLinux = rootLocalLinux;
	}
	
	public static double sizeFile(File file)
    {	
		double bytes = 0.0;
		double kilobytes = 0.0;
		double megabytes = 0.0;
		double gigabytes = 0.0;
		double terabytes = 0.0;
		double petabytes = 0.0;
		double exabytes = 0.0;
		double zettabytes = 0.0;
		double yottabytes = 0.0;
		if(file.exists())
		{
			bytes = file.length();
			kilobytes = (bytes / 1024);
			megabytes = (kilobytes / 1024);
			gigabytes = (megabytes / 1024);
			terabytes = (gigabytes / 1024);
			petabytes = (terabytes / 1024);
			exabytes = (petabytes / 1024);
			zettabytes = (exabytes / 1024);
			yottabytes = (zettabytes / 1024);
 
			System.out.println("bytes : " + bytes);
			System.out.println("kilobytes : " + kilobytes);
			System.out.println("megabytes : " + megabytes);
			System.out.println("gigabytes : " + gigabytes);
			System.out.println("terabytes : " + terabytes);
			System.out.println("petabytes : " + petabytes);
			System.out.println("exabytes : " + exabytes);
			System.out.println("zettabytes : " + zettabytes);
			System.out.println("yottabytes : " + yottabytes);
		}
		else
		{
			 System.out.println("File does not exists!");
		}
		return megabytes;
    }    

}
