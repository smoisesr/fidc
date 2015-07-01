package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import mvcapital.entidade.Entidade;
import mvcapital.mysql.MySQLAccess;

public class UpdaterDadosSacado
{

	private ArrayList<Entidade> entidades = new ArrayList<Entidade>();
	private static Connection conn = null;
	public UpdaterDadosSacado()
	{

	}
	public static void main(String[] args)
	{
		UpdaterDadosSacado.connect();
		UpdaterDadosSacado.processFile("W:\\Fundos\\Operacao\\Empresas\\DadosSacados.csv", conn);
	}
	
	public static void connect()
	{
		MySQLAccess.readConf();
		MySQLAccess mysqlAccess = new MySQLAccess(); 
		mysqlAccess.connect();
		conn = (Connection) mysqlAccess.getConn();		
	}
	
	
	public static void processFile(String fileName, Connection conn)
	{
		BufferedReader reader = null;
		
		ArrayList<String> lines = new ArrayList<String>();
		System.out.println("------------------"); //$NON-NLS-1$
		System.out.println("File: " + fileName);		 //$NON-NLS-1$
		try 
		{
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		
		try 
		{
			String line = null;
			while ((line = reader.readLine()) != null) 
			{
				line = line.toUpperCase();
				if(!line.isEmpty())
				{					
					lines.add(line);
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		for(String line:lines)
		{
//			System.out.println(line);
			String[] fields = line.split(";");
			int idTipoCadastro=Integer.parseInt(fields[0]);
			String cadastro=fields[1].trim();
			String nomeSacado=fields[2].trim();
			String enderecoSacado=fields[3].trim();
			String cepSacado=fields[4].trim();
			
//			System.out.println(idTipoCadastro
//								+ "|" + cadastro
//								+ "|" + nomeSacado
//								+ "|" + enderecoSacado
//								+ "|" + cepSacado
//								);
			
			Entidade entidade = new Entidade(nomeSacado, cadastro, conn);			
			Entidade.updateEndereco(entidade.getIdEntidade(), enderecoSacado, cepSacado, conn);
		}
	}
	
	public ArrayList<Entidade> getEntidades()
	{
		return entidades;
	}
	public void setEntidades(ArrayList<Entidade> entidades)
	{
		this.entidades = entidades;
	}
	public static Connection getConn()
	{
		return conn;
	}
	public static void setConn(Connection conn)
	{
		UpdaterDadosSacado.conn = conn;
	}
}
