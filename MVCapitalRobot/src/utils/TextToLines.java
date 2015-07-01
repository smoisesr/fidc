package utils;

import java.util.ArrayList;

public class TextToLines 
{
	public static ArrayList<String> extractLines(String text)
	{
		ArrayList<String> lines=new ArrayList<String>();
		String[] rawLines = text.toString().split("\n+");
		for(String rawLine:rawLines)
		{
			if (rawLine.length()>2)
			{
				lines.add(rawLine);
			}
		}
		return lines;
	}

	
	public static ArrayList<String> extractLines(StringBuilder text)
	{
		ArrayList<String> lines=new ArrayList<String>();
		String[] rawLines = text.toString().split("\n+");
		for(String rawLine:rawLines)
		{
			if (rawLine.length()>2)
			{
				lines.add(rawLine);
			}
		}
		return lines;
	}
}
