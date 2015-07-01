package utils;

import java.text.Normalizer;

public class CleanString {
	
	public CleanString()
	{
	}
	
	public static String cleanWithoutSpace(String originalString)
	{
		String cleanString=originalString;

		
		return Normalizer.normalize(cleanString, Normalizer.Form.NFD)
		.replaceAll("[\\s]", "_")
		.replaceAll("[^\\p{ASCII}]", "")
		.replace("posioo","posicao")
		.replace("patrimnio","patrimonio");
		/*
		.replaceAll("��","ca")
		.replaceAll("�", "c")
		.replaceAll("�", "c")
		.replaceAll("�","a")
		.replaceAll("�","o")
		.replaceAll("�","a")
		.replaceAll("�","o")
		.replaceAll("�","a")
		.replaceAll("�","o")
		.replaceAll("�","a")
		.replaceAll("�","o");
		*/
	}
	public static String cleanWithSpace(String originalString)
	{
		String cleanString=originalString;
		return Normalizer.normalize(cleanString, Normalizer.Form.NFD)
		.replaceAll("[^\\p{ASCII}]", "");
		/*
		.replace("posioo","posicao")
		.replace("patrimnio","patrimonio")
		.replace("relatrio","relatorio")
		.replace("cdigo","codigo")
		.replace("operaes","operacoes");
		
		.replaceAll("��","ca")
		.replaceAll("�", "c")
		.replaceAll("�", "c")
		.replaceAll("�","a")
		.replaceAll("�","o")
		.replaceAll("�","a")
		.replaceAll("�","o")
		.replaceAll("�","a")
		.replaceAll("�","o")
		.replaceAll("�","a")
		.replaceAll("�","o");
		*/
	}

	public static String removeDoublePoint(String string)
	{
		String cleanString="";
		boolean existFirstPoint=false;
		int iFirstPoint=0;
		for (int i = 0; i < string.length(); i++)
		{
			if(string.charAt(i)=='.')
			{
				if(!existFirstPoint)
				{
					existFirstPoint=true;
					iFirstPoint=i;
				}
				else
				{
					for (int j=0;j<string.length();j++)
					{
						if(iFirstPoint!=j)
						{
							cleanString=cleanString+string.charAt(j);
						}
					}					
					return cleanString;
				}
			}
		}
		return string;
	}
	
	public static boolean checkDoubleComma(String string)
	{
		boolean existFirstComma=false;
		int iFirstPoint=0;
		for (int i = 0; i < string.length(); i++)
		{
			if(string.charAt(i)==',')
			{
				if(!existFirstComma)
				{
					existFirstComma=true;
					iFirstPoint=i;
				}
				else
				{
					for (int j=iFirstPoint+1;j<string.length();j++)
					{
						if(string.charAt(j)==',')
						{
							return true;
						}
					}					
				}
			}
		}
		return false;
	}
}
