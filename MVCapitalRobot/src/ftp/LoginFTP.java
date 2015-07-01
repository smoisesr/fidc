package ftp;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import com.mysql.jdbc.Connection;

import utils.Login;

public class LoginFTP extends Login {
	private FTPClient client=new FTPClient();


	public LoginFTP(int idLogin, Connection conn) {
		super(idLogin, conn);
	}

	public void connectFTP()
	{
		if(port == 990)
		{
			SSLContext sslContext = null;
			TrustManager[] trustManager = new TrustManager[] 
	    	{ 
	    			new X509TrustManager() 
	    			{
	    				public X509Certificate[] getAcceptedIssuers() 
	    				{
	    					return null;
	    				}
	    				
	    				public void checkClientTrusted(X509Certificate[] certs, String authType) 
	    				{
	    				}
	    				public void checkServerTrusted(X509Certificate[] certs, String authType) 
	    				{
	    				}
	    			} 
	    	};
	
			try 
			{
				sslContext = SSLContext.getInstance("SSL");
				sslContext.init(null, trustManager, new SecureRandom());
			} 
			catch (NoSuchAlgorithmException e) 
			{
				e.printStackTrace();
			} 
			catch (KeyManagementException e) 
			{
				e.printStackTrace();
			}
			    	
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
	    	this.client.setSSLSocketFactory(sslSocketFactory);
	    	this.client.setSecurity(FTPClient.SECURITY_FTPS); // or client.setSecurity(FTPClient.SECURITY_FTPES);
	
	    	try 
	    	{
				this.client.connect(this.server);
				System.out.println("Passed Connection");
				this.client.login(username, password);
				System.out.println("Passed Login");
			} 
	    	catch (IllegalStateException | IOException | FTPIllegalReplyException
					| FTPException e) 
	    	{
				e.printStackTrace();
			}
		}
		else
		{
			try {
				this.client.connect(this.server);
				System.out.println("Passed Connection");
				this.client.login(username, password);
				System.out.println("Passed Login");				
			} catch (IllegalStateException | IOException
					| FTPIllegalReplyException | FTPException e) {
				e.printStackTrace();
			}
			
		}
	}

	public FTPClient getClient()
	{
		return client;
	}

	public void setClient(FTPClient client)
	{
		this.client = client;
	}
}
