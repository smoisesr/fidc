package mvcapital.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;


public class OperatingSystem 
{
	private static String rootLocalWindows=""; //$NON-NLS-1$
	private static String rootLocalLinux=""; //$NON-NLS-1$
	public static String separator="\\"; //$NON-NLS-1$
	public static String rootLocalDir=""; //$NON-NLS-1$
	
	public static void setSeparator()
	{
		String separatorWindows = "\\"; //$NON-NLS-1$
		String separatorLinux = "/"; //$NON-NLS-1$
	
		System.out.println("OS Name: " + System.getProperty("os.name").toLowerCase()); //$NON-NLS-1$ //$NON-NLS-2$
		if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			System.out.println("Set separator to " + separatorWindows); //$NON-NLS-1$
			separator=separatorWindows;
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			System.out.println("Set separator to " + separatorLinux); //$NON-NLS-1$
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
		System.out.println("OS Name: " + System.getProperty("os.name").toLowerCase()); //$NON-NLS-1$ //$NON-NLS-2$
		if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			System.out.println("Set rootLocalDir to " + rootLocalWindows); //$NON-NLS-1$
			rootLocalDir=rootLocalWindows;
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			System.out.println("Set rootLocalDir to " + rootLocalLinux); //$NON-NLS-1$
			rootLocalDir=rootLocalLinux;
		}
//		System.out.println("RootLocalDir: " + rootLocalDir);
	}

	public static String getRootLocalDir()
	{
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
	 	 
	    		   System.out.println("\t\tDirectory is deleted : "  //$NON-NLS-1$
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
	        	     System.out.println("\t\tDirectory is deleted : "  //$NON-NLS-1$
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
	    		    System.err.format("%s: no such" + " file or directory%n", file.toPath()); //$NON-NLS-1$ //$NON-NLS-2$
	    		} catch (DirectoryNotEmptyException x) {
	    		    System.err.format("%s not empty%n", file.toPath()); //$NON-NLS-1$
	    		} catch (IOException x) {
	    		    // File permission problems are caught here.
	    		    System.err.println(x);
	    		}
	    		System.out.println("\t\tFile is deleted : " + file.getAbsolutePath()); //$NON-NLS-1$
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
			System.out.println("\t\tFile " + source + " exist"); //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println("\t\tNew file last modification " + lastModSource.toString()); //$NON-NLS-1$
			System.out.println("\t\tOld file last modification " + lastModDestiny.toString()); //$NON-NLS-1$
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
				System.out.println("\t\tFile " + destiny + " is updated"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		else 
		{
			String tempPath = destiny.getAbsolutePath();
			
			if(destiny.delete())
			{
				destiny = new File(tempPath);
			}
			
			try
			{
				Files.copy(source.toPath(), destiny.toPath());
				System.out.println("\t\tCopying " + source.toPath() + "\n\t\tto " + destiny.toPath()); //$NON-NLS-1$ //$NON-NLS-2$
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
    	String result = year + "-" + month + " " + nameMonth(Integer.parseInt(month)); //$NON-NLS-1$ //$NON-NLS-2$
    	return result;
    }
    public static String folderNameFromDate(String yearMonthDay)
    {
    	String year = yearMonthDay.substring(0, 4);
    	String month = yearMonthDay.substring(4, 6);
//    	String day = yearMonthDay.substring(6, 8);
    	String result = year + "-" + month; //$NON-NLS-1$
    	return result;
    }
    
    public static void copyFileUsingFileChannels(File source, File dest)
    {
        @SuppressWarnings("resource")
		FileChannel inputChannel = null;
        @SuppressWarnings("resource")
		FileChannel outputChannel = null;
        try {
            try
			{
				inputChannel = new FileInputStream(source).getChannel();
			} catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			}
            try
			{
				outputChannel = new FileOutputStream(dest).getChannel();
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
            try
			{
				outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
        } finally {
            try
			{
				inputChannel.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
            try
			{
				outputChannel.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
        }
    }

    public static String nameMonth(int month)
    {
        String monthString;
        switch (month) {
            case 1:  monthString = "Jan"; //$NON-NLS-1$
                     break;
            case 2:  monthString = "Fev"; //$NON-NLS-1$
                     break;
            case 3:  monthString = "Mar"; //$NON-NLS-1$
                     break;
            case 4:  monthString = "Abr"; //$NON-NLS-1$
                     break;
            case 5:  monthString = "Mai"; //$NON-NLS-1$
                     break;
            case 6:  monthString = "Jun"; //$NON-NLS-1$
                     break;
            case 7:  monthString = "Jul"; //$NON-NLS-1$
                     break;
            case 8:  monthString = "Ago"; //$NON-NLS-1$
                     break;
            case 9:  monthString = "Set"; //$NON-NLS-1$
                     break;
            case 10: monthString = "Out"; //$NON-NLS-1$
                     break;
            case 11: monthString = "Nov"; //$NON-NLS-1$
                     break;
            case 12: monthString = "Dez"; //$NON-NLS-1$
                     break;
            default: monthString = "Invalid month"; //$NON-NLS-1$
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
			System.out.println("\t\tFolder " + dir + " created "); //$NON-NLS-1$ //$NON-NLS-2$
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
 
			System.out.println("bytes : " + bytes); //$NON-NLS-1$
			System.out.println("kilobytes : " + kilobytes); //$NON-NLS-1$
			System.out.println("megabytes : " + megabytes); //$NON-NLS-1$
			System.out.println("gigabytes : " + gigabytes); //$NON-NLS-1$
			System.out.println("terabytes : " + terabytes); //$NON-NLS-1$
			System.out.println("petabytes : " + petabytes); //$NON-NLS-1$
			System.out.println("exabytes : " + exabytes); //$NON-NLS-1$
			System.out.println("zettabytes : " + zettabytes); //$NON-NLS-1$
			System.out.println("yottabytes : " + yottabytes); //$NON-NLS-1$
		}
		else
		{
			 System.out.println("File does not exists!"); //$NON-NLS-1$
		}
		return megabytes;
    }    

}
