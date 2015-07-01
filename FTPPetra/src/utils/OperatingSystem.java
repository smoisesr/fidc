package utils;

public class OperatingSystem 
{
	public static String getSeparator()
	{
		String separatorWindows = "\\";
		String separatorLinux = "/";
		String separator="";
		
		if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			separator=separatorWindows;
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux"))
		{
			separator=separatorLinux;
		}
		
		return separator;
	}
	
	public static String getRootLocalDir()
	{
		String rootWindows = "W:\\";
		//String rootWindows = "C:\\Tmp";
		String rootLinux = "/home/database";
		String root="";
		
		if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			root=rootWindows;
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux"))
		{
			root=rootLinux;
		}
		
		return root;
	}
}
