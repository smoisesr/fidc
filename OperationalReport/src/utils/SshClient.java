package utils;
import com.jcraft.jsch.*;

import java.awt.*;

import javax.swing.*;

import java.io.*;
 
public class SshClient
{
	private JSch jsch=new JSch();
	private String host="localhost"; //$NON-NLS-1$
	private int port=22;
	private String user="user"; //$NON-NLS-1$
	private String password="password"; //$NON-NLS-1$
	private Session session = null;	
	
	
	public SshClient(String host, int port, String user, String password)
	{
		this.host=host;
		this.port=port;
		this.user=user;
		this.password=password;
	}
	
	public static void main(String[] arg)
	{
		System.out.println("Client Creation"); //$NON-NLS-1$
		SshClient sshClient = new SshClient("192.168.2.122", 22, "moises", "preciosa"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		System.out.println("Connection"); //$NON-NLS-1$
		sshClient.connect();
		System.out.println("Command send"); //$NON-NLS-1$
		sshClient.executeCommand("ls -ls"); //$NON-NLS-1$
		System.out.println("Disconnect client"); //$NON-NLS-1$
		sshClient.disconnect();
	}
	
	public void connect()
	{
		try {
			this.session = this.jsch.getSession(this.user, this.host, 22);
		} catch (JSchException e) {
			e.printStackTrace();
		}
		UserInfo ui=new MyUserInfo(this.password);
		this.session.setUserInfo(ui);
		try 
		{
			this.session.connect();
		} catch (JSchException e) 
		{
				e.printStackTrace();
		}	
	}
	
	public void disconnect()
	{
		this.session.disconnect();
	}
	public void executeCommand(String command)
	{
		Channel channel = null;
		try 
		{
			channel = this.session.openChannel("exec"); //$NON-NLS-1$
		} catch (JSchException e) {

			e.printStackTrace();
		}
		((ChannelExec)channel).setCommand(command);
		channel.setInputStream(null);
		((ChannelExec)channel).setErrStream(System.err);
		InputStream in = null;
		try 
		{
			in = channel.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
	    try 
	    {
			channel.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
	    byte[] tmp=new byte[1024];
	    while(true)
	    {
	        try 
	        {
				while(in.available()>0)
				{
				  int i=in.read(tmp, 0, 1024);
				  if(i<0) break;
				  System.out.print(new String(tmp, 0, i));
				}
			} 
	        catch (IOException e) 
	        {

				e.printStackTrace();
			}
	        if(channel.isClosed())
	        {
	          try 
	          {
				if(in.available()>0) continue;
	          } 
	          catch (IOException e) 
	          {
				e.printStackTrace();
	          } 
	          System.out.println("exit-status: "+channel.getExitStatus()); //$NON-NLS-1$
	          break;
	        }
	        	try
				{
					Thread.sleep(1000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
	      }
	      channel.disconnect();
	}

	public String executeCommandOutput(String command)
	{
		Channel channel = null;
		String output=""; //$NON-NLS-1$
		try 
		{
			channel = this.session.openChannel("exec"); //$NON-NLS-1$
		} catch (JSchException e) {

			e.printStackTrace();
		}
		((ChannelExec)channel).setCommand(command);
		channel.setInputStream(null);
		((ChannelExec)channel).setErrStream(System.err);
		InputStream in = null;
		try 
		{
			in = channel.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
	    try 
	    {
			channel.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
	    byte[] tmp=new byte[1024];
	    while(true)
	    {
	        try 
	        {
				while(in.available()>0)
				{
				  int i=in.read(tmp, 0, 1024);
				  if(i<0) break;
				  System.out.print(new String(tmp, 0, i));
				  output=output+new String(tmp, 0, i);
				}
			} 
	        catch (IOException e) 
	        {

				e.printStackTrace();
			}
	        if(channel.isClosed())
	        {
	          try 
	          {
				if(in.available()>0) continue;
	          } 
	          catch (IOException e) 
	          {
				e.printStackTrace();
	          } 
	          System.out.println("exit-status: "+channel.getExitStatus()); //$NON-NLS-1$
	          break;
	        }
	        try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
	      }
	      channel.disconnect();
	      return output;
	}
	
	
	public static class MyUserInfo implements UserInfo, UIKeyboardInteractive
  {
		String passwd=""; //$NON-NLS-1$
		public MyUserInfo(String password)
		{
			this.passwd = password;
		}
	
    public String getPassword(){ return this.passwd; }
    
    public boolean promptYesNo(String str){
    	/*
      Object[] options={ "yes", "no" };
      int foo=JOptionPane.showOptionDialog(null, 
             str,
             "Warning", 
             JOptionPane.DEFAULT_OPTION, 
             JOptionPane.WARNING_MESSAGE,
             null, options, options[0]);
      
       return foo==0;
       */
    	return true;
    }
  

    JTextField passwordField=(JTextField)new JPasswordField(20);
 
    public String getPassphrase(){ return null; }
    public boolean promptPassphrase(String message){ return true; }
  
    public boolean promptPassword(String message){
//      Object[] ob={passwordField}; 
//      int result=
//        JOptionPane.showConfirmDialog(null, ob, message,
//                                      JOptionPane.OK_CANCEL_OPTION);
//      if(result==JOptionPane.OK_OPTION){
//        passwd=passwordField.getText();
//        return true;
//      }
//      else{ 
//        return false; 
//      }
    	return true;
    }
    
    public void showMessage(String message){
      JOptionPane.showMessageDialog(null, message);
    }
    final GridBagConstraints gbc = 
      new GridBagConstraints(0,0,1,1,1,1,
                             GridBagConstraints.NORTHWEST,
                             GridBagConstraints.NONE,
                             new Insets(0,0,0,0),0,0);
    private Container panel;
    public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo){
      this.panel = new JPanel();
      this.panel.setLayout(new GridBagLayout());
 
      this.gbc.weightx = 1.0;
      this.gbc.gridwidth = GridBagConstraints.REMAINDER;
      this.gbc.gridx = 0;
      this.panel.add(new JLabel(instruction), this.gbc);
      this.gbc.gridy++;
 
      this.gbc.gridwidth = GridBagConstraints.RELATIVE;
 
      JTextField[] texts=new JTextField[prompt.length];
      for(int i=0; i<prompt.length; i++){
        this.gbc.fill = GridBagConstraints.NONE;
        this.gbc.gridx = 0;
        this.gbc.weightx = 1;
        this.panel.add(new JLabel(prompt[i]),this.gbc);
 
        this.gbc.gridx = 1;
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.weighty = 1;
        if(echo[i]){
          texts[i]=new JTextField(20);
        }
        else{
          texts[i]=new JPasswordField(20);
        }
        this.panel.add(texts[i], this.gbc);
        this.gbc.gridy++;
      }
 
      if(JOptionPane.showConfirmDialog(null, this.panel, 
                                       destination+": "+name, //$NON-NLS-1$
                                       JOptionPane.OK_CANCEL_OPTION,
                                       JOptionPane.QUESTION_MESSAGE)
         ==JOptionPane.OK_OPTION){
        String[] response=new String[prompt.length];
        for(int i=0; i<prompt.length; i++){
          response[i]=texts[i].getText();
        }
	return response;
      }
      else{
        return null;  // cancel
      }
    }
  }

public JSch getJsch() {
	return this.jsch;
}


public void setJsch(JSch jsch) {
	this.jsch = jsch;
}


public String getHost() {
	return this.host;
}


public void setHost(String host) {
	this.host = host;
}


public int getPort() {
	return this.port;
}


public void setPort(int port) {
	this.port = port;
}


public String getUser() {
	return this.user;
}


public void setUser(String user) {
	this.user = user;
}


public String getPassword() {
	return this.password;
}


public void setPassword(String password) {
	this.password = password;
}

public Session getSession() {
	return this.session;
}

public void setSession(Session session) {
	this.session = session;
}
}