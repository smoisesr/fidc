package mvcapital.bancopaulista.portalfidc;

import com.jaunt.Element;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

public class JauntTestPage
{

	public JauntTestPage()
	{

	}
	
	public static void main(String[] args)
	{
		try{
			  UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
			  userAgent.visit("http://portalfidc.bancopaulista.com.br/portal/login");                        //visit a url   //$NON-NLS-1$
			  System.out.println(userAgent.doc.innerHTML());               //print the document as HTML
//			  userAgent.doc.fillout("j_username:", "moises.ito"); //$NON-NLS-1$ //$NON-NLS-2$
//			  userAgent.doc.fillout("j_password:", "Paulista32"); //$NON-NLS-1$ //$NON-NLS-2$
//			  userAgent.doc.submit();
			  userAgent.doc.apply("moises.ito","Paulista32"); //$NON-NLS-1$ //$NON-NLS-2$
//			  userAgent.doc.submit(" Entrar"); //$NON-NLS-1$
			  Element button = userAgent.doc.findFirst("<button>");
			  userAgent.doc.submit();
			  
			  System.out.println(userAgent.getLocation());
			}
			catch(JauntException e)
		{         //if an HTTP/connection error occurs, handle JauntException.
			  System.err.println(e);
			}		
	}

}
