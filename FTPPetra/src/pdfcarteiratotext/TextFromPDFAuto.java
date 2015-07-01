package pdfcarteiratotext;

import java.io.*;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
import org.apache.pdfbox.util.*;


public class TextFromPDFAuto 
{
//	public static void main(String[] args) 
//	{
//		TextFromPDFAuto auto = new TextFromPDFAuto();
//		File input = new File("W:\\Fontes\\Petra\\Cotas\\FIDC Orion\\Cota_Patrimônio FIDC ORION.pdf");
//		File output = new File("W:\\Fontes\\Petra\\Cotas\\FIDC Orion\\Cotas.txt");
//		auto.extractFromPDF(input, output);		
//	}
	public static void extractFromPDF(File input, File output)
	{
		 PDDocument pd;
		 String outputText = "";
		 try 
		 {
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
	       //System.out.println(outputText);
	       //return outputText;
	     } 
		 catch (Exception e)
		 {
	         e.printStackTrace();
	     }
		 //return outputText;
		 try
         {
         FileWriter fw = new FileWriter(output.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(outputText);
			bw.close();
         }
         catch (IOException e1) 
         {
 			e1.printStackTrace();
         }
	}
	
	public static File openPDFDoc(final File pdfFile, String password) throws Exception 
	{
        File originalPDF = pdfFile;
        PDFParser parser = new PDFParser(new BufferedInputStream(new FileInputStream(
                originalPDF)));
        parser.parse();

        PDDocument originalPdfDoc = parser.getPDDocument();

        boolean isOriginalDocEncrypted = originalPdfDoc.isEncrypted();
        if (isOriginalDocEncrypted) 
        {
        	originalPdfDoc.openProtection(new StandardDecryptionMaterial(password));
        	originalPdfDoc.decrypt(password);
        }
        originalPdfDoc.save(originalPDF);
        originalPdfDoc.close();
        return originalPDF;
    }

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
	       //System.out.println(text);
	//       stripper.writeText(pd, wr);
	//       if (pd != null) {
	//           pd.close();
	//       }
	
	        // I use close() to flush the stream.
	//        wr.close();
	        } 
		 catch (Exception e)
		 {
	         e.printStackTrace();
	     }
		 return outputText;
	}
}
