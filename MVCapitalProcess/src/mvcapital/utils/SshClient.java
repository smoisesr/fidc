package mvcapital.utils;
import com.jcraft.jsch.*;

import java.awt.*;

import javax.swing.*;

import java.io.*;
 
public class SshClient
{
	private JSch jsch=new JSch();
	private String host="localhost";
	private int port=22;
	private String user="user";
	private String password="password";
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
		System.out.println("Client Creation");
		SshClient sshClient = new SshClient("192.168.2.122", 22, "moises", "preciosa");
		System.out.println("Connection");
		sshClient.connect();
		System.out.println("Command send");
		sshClient.executeCommand("ls -ls");
		System.out.println("Disconnect client");
		sshClient.disconnect();
	}
	
	public void connect()
	{
		try {
			session = jsch.getSession(user, host, 22);
		} catch (JSchException e) {
			e.printStackTrace();
		}
		UserInfo ui=new MyUserInfo(password);
		session.setUserInfo(ui);
		try 
		{
			session.connect();
		} catch (JSchException e) 
		{
				e.printStackTrace();
		}	
	}
	
	public void disconnect()
	{
		session.disconnect();
	}
	public void executeCommand(String command)
	{
		Channel channel = null;
		try 
		{
			channel = session.openChannel("exec");
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
	          System.out.println("exit-status: "+channel.getExitStatus());
	          break;
	        }
	        try{Thread.sleep(1000);}catch(Exception ee){}
	      }
	      channel.disconnect();
	}

	public String executeCommandOutput(String command)
	{
		Channel channel = null;
		String output="";
		try 
		{
			channel = session.openChannel("exec");
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
	          System.out.println("exit-status: "+channel.getExitStatus());
	          break;
	        }
	        try{Thread.sleep(1000);}catch(Exception ee){}
	      }
	      channel.disconnect();
	      return output;
	}
	
	
	public static class MyUserInfo implements UserInfo, UIKeyboardInteractive
  {
		String passwd="";
		public MyUserInfo(String password)
		{
			passwd = password;
		}
	
    public String getPassword(){ return passwd; }
    
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
      panel = new JPanel();
      panel.setLayout(new GridBagLayout());
 
      gbc.weightx = 1.0;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      gbc.gridx = 0;
      panel.add(new JLabel(instruction), gbc);
      gbc.gridy++;
 
      gbc.gridwidth = GridBagConstraints.RELATIVE;
 
      JTextField[] texts=new JTextField[prompt.length];
      for(int i=0; i<prompt.length; i++){
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.weightx = 1;
        panel.add(new JLabel(prompt[i]),gbc);
 
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;
        if(echo[i]){
          texts[i]=new JTextField(20);
        }
        else{
          texts[i]=new JPasswordField(20);
        }
        panel.add(texts[i], gbc);
        gbc.gridy++;
      }
 
      if(JOptionPane.showConfirmDialog(null, panel, 
                                       destination+": "+name,
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
	return jsch;
}


public void setJsch(JSch jsch) {
	this.jsch = jsch;
}


public String getHost() {
	return host;
}


public void setHost(String host) {
	this.host = host;
}


public int getPort() {
	return port;
}


public void setPort(int port) {
	this.port = port;
}


public String getUser() {
	return user;
}


public void setUser(String user) {
	this.user = user;
}


public String getPassword() {
	return password;
}


public void setPassword(String password) {
	this.password = password;
}

public Session getSession() {
	return session;
}

public void setSession(Session session) {
	this.session = session;
}
}