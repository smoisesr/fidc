package mvcapital.ratings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

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

public class CheckRatings
{

	public CheckRatings()
	{

	}
	
	public static void main(String[] args)
	{		
		String subject="Liberum Ratings - " + Calendar.getInstance().getTime(); //$NON-NLS-1$
		sendEmail(linesToMessage(readLiberum()), subject);
		sendEmail(readAustin().get(0),"Austin Ratings - " + Calendar.getInstance().getTime()); //$NON-NLS-1$
	}
	
	public static String linesToMessage(ArrayList<String> lines)
	{
		StringBuilder sb=new StringBuilder();
		sb.append("<table>\n"); //$NON-NLS-1$
		for(String line:lines)
		{
			sb.append("<tr>" + line + "</tr>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		sb.append("</table>"); //$NON-NLS-1$
		return sb.toString();
	}

	public static ArrayList<String> readLiberum( )
	{		
		ArrayList<String> lines = new ArrayList<String>();
		String url = "http://www.liberumratings.com.br/lista_news_release.php"; //$NON-NLS-1$
				
        Document doc;
        try {
        	
            doc = Jsoup.connect(url)
            		  .data("query", "Java") //$NON-NLS-1$ //$NON-NLS-2$
            		  .userAgent("Mozilla") //$NON-NLS-1$
            		  .cookie("auth", "token") //$NON-NLS-1$ //$NON-NLS-2$
            		  .timeout(3000)
            		  .post();
            Element newsTable = doc.select("table").get(0); //$NON-NLS-1$
            Elements noticias = newsTable.select("tr"); //$NON-NLS-1$
            int iNoticia=0;
            for(Element noticia:noticias)
            {
            	String line = noticia.text();
            	if(line.contains("/")) //$NON-NLS-1$
            	{
            		if(iNoticia==0 || iNoticia==1)
            		{
            			/**
            			 * 
            			 */
            		}
            		else
            		{
            			lines.add(line);
            		}
            		iNoticia++;
//            		System.out.println(line);
            	}
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lines;
	}
	
	public static ArrayList<String> readAustin( )
	{		
		ArrayList<String> lines = new ArrayList<String>();
		String url = "http://www.austin.com.br/"; //$NON-NLS-1$
				
        Document doc;
        try {
        	
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
            
            for (Element table : doc.select("#inform"))  //$NON-NLS-1$
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
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse("fundos@mvcapital.com.br,moises.ito@mvcapital.com.br")); //$NON-NLS-1$
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
