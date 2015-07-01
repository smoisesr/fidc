package ftpclient;

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

public class LoginFTP
{
	private FTPClient client=new FTPClient();
	private String server="localhost";
	private int port=21;
	private String username="anonymous";
	private String password="anonymous";
	
	/**
	 * @param server
	 * @param port
	 * @param username
	 * @param password
	 */
	public LoginFTP(String server, int port, String username, String password)
	{
		this.server = server;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	public LoginFTP(String server, int port)
	{
		this.server = server;
		this.port = port;
	}
	
	public LoginFTP()
	{
		
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
				//System.out.println("Passed Connection");
				this.client.login(username, password);
				//System.out.println("Passed Login");
			} 
	    	catch (IllegalStateException | IOException | FTPIllegalReplyException
					| FTPException e) 
	    	{
				e.printStackTrace();
			}
		}
		else
		{
			try 
	    	{
				this.client.connect(this.server, this.port);
				this.client.login(username, password);
	    	}
			catch (IllegalStateException | IOException | FTPIllegalReplyException
					| FTPException e) 
	    	{
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

	public String getServer()
	{
		return server;
	}

	public void setServer(String server)
	{
		this.server = server;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
