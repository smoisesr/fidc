package pdfcarteiratotext;

import java.io.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.util.*;


/**
 * @author MVCapital
 * Converting the files Carteira*.pdf into Carteira*.txt
 */
public class Textfrompdf 
{
	public static String extractFromPDF(File input)
	{
		 PDDocument pd;
		 String outputText = "";
		 try 
		 {
		//         File input = new File("C:\\Tmp\\TestePDF.pdf");  // The PDF file from where you would like to extract
		 //File output = new File("C:\\Tmp\\SampleText.txt"); // The text file where you are going to store the extracted data
		 pd = PDDocument.load(input);
		//         System.out.println(pd.getNumberOfPages());
		//         System.out.println(pd.isEncrypted());
		 //pd.save("CopyOfInvoice.pdf"); // Creates a copy called "CopyOfInvoice.pdf"
		 PDFTextStripper stripper = new PDFTextStripper();
		 //wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
		     String text = stripper.getText(pd);
		   String space = "\n";
		   String[] lines = text.split(space);
		   //System.out.println(lines[0]);
		   String prefix = ""; 
       
	       for (String line:lines)
	       {    	   
	    	   if (line.contains("FIDC"))
	    	   {
	    		   //System.out.print(line);
	    		   line.replace("\n", "");
	    		   line.replace("\t", "");
	    		   line.replace("  ", " ");
	    		   String[] palavras = line.split(" ");
	    		   //System.out.println(palavras[2]);
	    		   int i = 0;
	    		   prefix="";
	    		   for(String palavra:palavras)
	    		   {
	    			   if (i>=2 && !palavra.isEmpty() && i < palavras.length-1)
	    			   {    				   
	    				   //System.out.println(palavra.isEmpty());
	    				   prefix = prefix + " " + palavra;
	    			   }
	    			   i++;
	    		   }
	    	   }
	    	   else if (line.contains(","))
	    	   {
	    		   String[] colunas = line.split(" ");
	    		   String outputLine;
	    		   //System.out.print(prefix + "\t" + line);
	    		   outputLine = prefix + "\t" + colunas[0] + "\t" + colunas[4] + "\t" + colunas[5] + "\t" + colunas[6];
	    		   //System.out.println(prefix + "\t" + colunas[0] + "\t" + colunas[4] + "\t" + colunas[5] + "\t" + colunas[6]);
	    		   outputText=outputText + outputLine + "\n";
	    	   }
	       }
	       System.out.println(outputText);
	       return outputText;
        } 
	 catch (Exception e)
	 {
         e.printStackTrace();
     }
	 return outputText;
     }
}
