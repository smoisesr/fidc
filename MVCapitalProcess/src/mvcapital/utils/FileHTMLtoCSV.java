package mvcapital.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class FileHTMLtoCSV
{

	public FileHTMLtoCSV()
	{

	}
	public static void main(String[] args) 
	{

        File file = new File("W:\\Fundos\\Repositorio\\Cheques\\Processado\\Bradesco_15042015_093717.HTML"); //$NON-NLS-1$
        convertBradescoChequeExtratoHTML(file);
    }
	public static ArrayList<String> convertBradescoChequeExtratoHTML(File file)
	{
		ArrayList<String> lines = new ArrayList<String>();
        Document doc;
        try {
//            doc = Jsoup.connect(url).get();
            doc = Jsoup.parse(file, "UTF-8"); //$NON-NLS-1$
//            Element table = doc
//                    .select("div.mb10");
            Elements rowsFundo = doc.select("table.tabela_comprovante").select("tr"); //$NON-NLS-1$ //$NON-NLS-2$
            Elements list = doc.select("div.mb10").select("li"); //$NON-NLS-1$ //$NON-NLS-2$
            Elements rows = doc.select("div.mb10").select("tr"); //$NON-NLS-1$ //$NON-NLS-2$
            Elements ths = rows.select("th"); //$NON-NLS-1$

            String rowFundo = ""; //$NON-NLS-1$
            for (Element row : rowsFundo) 
            {
                Elements tds = row.select("td"); //$NON-NLS-1$
                for (Element td : tds) 
                {
                	rowFundo += td.text();               		
                }
            }
            rowFundo = rowFundo.replace("|", ""); //$NON-NLS-1$ //$NON-NLS-2$
            String[] fieldsFundo = rowFundo.split("CNPJ:"); //$NON-NLS-1$
            String nomeFundo = (fieldsFundo[0].trim().split("Cheques"))[1].trim(); //$NON-NLS-1$
            String cnpjFundo = (fieldsFundo[1].trim().split("Nome"))[0].trim().replace(".","").replace("/","").replace("-",""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
//            System.out.println(rowFundo);
//            System.out.println("DadosFundo;" + nomeFundo + ";" + cnpjFundo); //$NON-NLS-1$ //$NON-NLS-2$
            lines.add("DadosFundo;" + nomeFundo + ";" + cnpjFundo);
            
            String stringList=""; //$NON-NLS-1$
            for(Element item:list)
            {
                stringList += item.text() + ";"; //$NON-NLS-1$
            }            
//            System.out.println(stringList);
            String[] fieldsConta = stringList.trim().replace("|", "#").split("#"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            String agencia=(fieldsConta[0].split("Ag:"))[1].trim(); //$NON-NLS-1$
            String conta=(fieldsConta[1].split("CC:"))[1].trim(); //$NON-NLS-1$
//            System.out.println("DadosConta;"+agencia+";"+conta); //$NON-NLS-1$ //$NON-NLS-2$
            lines.add("DadosConta;"+agencia+";"+conta);
            
            
            String stringTableHeaders = "";             //$NON-NLS-1$
//            for (Element th : ths) 
//            {
//                stringTableHeaders += th.text() + ";";
//            }
//            System.out.println(stringTableHeaders);

            for (Element row : rows) 
            {
                Elements tds = row.select("td"); //$NON-NLS-1$
                String stringRow = "";
                int iTd=0;                
                for (Element td : tds) 
                {                    	
                	if(iTd!=0)
                	{
                		stringRow += ";"+ td.text(); 
                	}
                	else
                	{
                		stringRow += td.text();
                	}
                	iTd++;
                }
                if(stringRow.length()>0)
                {
                	stringRow=stringRow.replace("Cheque ", "")
                			.replace("Motivo: ", ";")
                			.replace("Visualizar a imagem do cheque", "")
                			.replace(";;", ";")
                			.replace(";;", ";")
                			.replace("Depositado/devolvido;","Devolvido:")
                			;
//                	System.out.println(stringRow); // --> This will print them                                                    // indiviadually //$NON-NLS-1$
                	lines.add(stringRow);
                }
            }
            // System.out.println(table);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lines;
	}
}
