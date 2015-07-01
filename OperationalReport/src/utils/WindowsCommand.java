package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowsCommand {

	public WindowsCommand() {

	}
	
	public static void execute(String command)
	{
		System.out.println("Trying " + command);
		try 
	    { 
	        Process p=Runtime.getRuntime().exec(command); 
	        p.waitFor(); 
	        BufferedReader reader = new BufferedReader(
	            new InputStreamReader(p.getInputStream())
	        ); 
	        
	        String line; 
	        while((line = reader.readLine()) != null) 
	        { 
	            System.out.println(line);
	        } 
	
	    }
	    catch(IOException e1) 
		{
	    	System.err.println(e1);
		} 
	    catch(InterruptedException e2) 
		{
	    	System.err.println(e2);
		} 
	
	    System.out.println("Done");
	}
}
