package mvcapital.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CheckResumoHtml
{

	public CheckResumoHtml()
	{

	}
	
	public static void main(String[] args)
	{		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
		sendEmail(linesToMessage(readResumoOperacoes()),"Resumo Operacional - " + sdf.format(Calendar.getInstance().getTime())); //$NON-NLS-1$
	}
	
	public static String linesToMessage(ArrayList<String> lines)
	{
		StringBuilder sb=new StringBuilder();
		int i=0;
		sb.append("<html>"); //$NON-NLS-1$
		sb.append("<body>"); //$NON-NLS-1$
		sb.append("<div style=\"width: 1600px; margin:0 auto;\"> <div style=\"float: left;\"> <img src=\"http://www.mvcapital.com.br/img/logo-mvcapital.png\" alt=\"MV Capital\" title=\"MV Capital\" id=\"mvcapital\"></div><div style=\"float: right;\">"); //$NON-NLS-1$
		sb.append("<table>"); //$NON-NLS-1$
		for(String line:lines)
		{
			if(i==0)
			{
				sb.append("<tr style=\"background: #ff3333; color: #ffffff; font-size:12px;\">" + line + "</tr>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			else if(i%2==0)
			{
				sb.append("<tr style=\"background: #dddddd; font-size:12px;\">" + line + "</tr>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			else
			{
				sb.append("<tr style=\"background: #ffffff; font-size:12px;\">" + line + "</tr>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$				
			}
			i++;
		}
		sb.append("</table>"); //$NON-NLS-1$
		sb.append("</body>"); //$NON-NLS-1$
		sb.append("</html>"); //$NON-NLS-1$
		return sb.toString();
	}

	
	public static ArrayList<String> readResumoOperacoes( )
	{		
		ArrayList<String> lines = new ArrayList<String>();
		String url = "http://192.168.1.152/resumoOperacoes3.html"; //$NON-NLS-1$
				
        Document doc;
        try {
        	Connection connectionTest = Jsoup.connect(url)
        			.cookie("cookiereference", "cookievalue") //$NON-NLS-1$ //$NON-NLS-2$
        			.method(Method.POST);
        	Document response = Jsoup.parse(new String(
        			connectionTest.execute().bodyAsBytes(),"ISO-8859-15")); //$NON-NLS-1$
        	
            doc = Jsoup.connect(url)
            		  .data("query", "Java") //$NON-NLS-1$ //$NON-NLS-2$
            		  .userAgent("Mozilla") //$NON-NLS-1$
            		  .cookie("auth", "token") //$NON-NLS-1$ //$NON-NLS-2$
            		  .timeout(3000)
            		  .get();
//            Element newsTable = doc.select("#_rdsmratingsactionportlet_WAR_rdsmratingsactionportlet_Ratings_Action > tbody"); //$NON-NLS-1$
//            Element newsBody = newsTable.select("tbody").first(); //$NON-NLS-1$
             //doc.select("div[class=css-events-list] > table > tbody"))
//            Elements newsRows = doc.select("div[class=table_wrapper] > table > tbody");
            
            for (Element table : response.select("tr"))  //$NON-NLS-1$
            {
                System.out.println(table);
                lines.add(table.html());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lines;
	}
	
	public static void sendEmail(String messageText, String subjet)
	{
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //$NON-NLS-1$ //$NON-NLS-2$
		props.put("mail.smtp.socketFactory.port", "465"); //$NON-NLS-1$ //$NON-NLS-2$
		props.put("mail.smtp.socketFactory.class", //$NON-NLS-1$
				"javax.net.ssl.SSLSocketFactory"); //$NON-NLS-1$
		props.put("mail.smtp.auth", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		props.put("mail.smtp.port", "465"); //$NON-NLS-1$ //$NON-NLS-2$
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("moises.ito@mvcapital.com.br","$onit@82"); //$NON-NLS-1$ //$NON-NLS-2$
				}
			});
 
		try {
 
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("moises.ito@mvcapital.com.br")); //$NON-NLS-1$
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse("marco.valim@mvcapital.com.br,fundos@mvcapital.com.br,moises.ito@mvcapital.com.br")); //$NON-NLS-1$
//			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse("moises.ito@mvcapital.com.br")); //$NON-NLS-1$
			message.setSubject(subjet); 
			message.setText(messageText,"utf-8","html"); //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println(messageText);
			Transport.send(message);
 
			System.out.println("Done"); //$NON-NLS-1$
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		System.out.println("E-mail sent!"); //$NON-NLS-1$
	}
}
