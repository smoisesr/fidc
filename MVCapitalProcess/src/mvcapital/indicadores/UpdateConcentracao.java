package mvcapital.indicadores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.mysql.MySQLAccess;
import mvcapital.utils.OperatingSystem;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UpdateConcentracao 
{
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
	private String server = "localhost";
	private int port = 3306;		
	private String userName = "root"; 
	private String password = "root";
	private String dbName = "root";
	private MySQLAccess mysql = null;
	private static Connection conn = null;
//	private ArrayList<ConcentracaoCedente> concentracaoCedente = new ArrayList<ConcentracaoCedente>();
//	private ArrayList<ConcentracaoSacado> concentracaoSacado = new ArrayList<ConcentracaoSacado>();
	
	public UpdateConcentracao() 
	{
		this.readConf();
		this.mysql = new MySQLAccess(this.server, this.port, this.userName, this.password, this.dbName);
		mysql.connect();	
		UpdateConcentracao.conn = (Connection) mysql.getConn();
	}
	
	public UpdateConcentracao(Connection conn)
	{
		UpdateConcentracao.conn = conn;
	}
	
//	public static void main(String[] args)
//	{
//		UpdateConcentracao uc = new UpdateConcentracao();
//		uc.readConf();
//		uc.update();
//	}

	public void update()
	{		
		BufferedReader reader = null;
		String pathCedente = "";
		String pathSacado = "";
		String pathPL = "";
		
		Statement stmt = null;

		try {
			stmt = (Statement) UpdateConcentracao.conn.createStatement();
		} catch (SQLException e3) {
			e3.printStackTrace();
		};
		
		ArrayList<String> linesCedente = new ArrayList<String>(); 
		ArrayList<String> linesSacado = new ArrayList<String>();
		ArrayList<String> linesPL = new ArrayList<String>();
		
		if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			pathCedente="W:\\Fundos\\Operacao\\Concentracao\\Cedente.csv";
			pathSacado="W:\\Fundos\\Operacao\\Concentracao\\Sacado.csv";
			pathPL="W:\\Fundos\\Operacao\\Concentracao\\PL.csv";
		}
		
		else if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			pathCedente="/home/Fundos/Operacao/Concentracao/Cedente.csv";
			pathSacado="/home/Fundos/Operacao/Concentracao/Sacado.csv";
			pathPL="/home/Fundos/Operacao/Concentracao/PL.csv";
		}
		
		String query = "Select max(updateTime) as LastUpdate from ConcentracaoCedente";
		
		try 
		{
			ResultSet rs = stmt.executeQuery(query);
			Date updateConcentracaoCedente = null;
			try {
				updateConcentracaoCedente = sdfd.parse("1900-01-01");
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
			while (rs.next())
			{
				 if(rs.getDate("LastUpdate")!=null)
				 {
					 updateConcentracaoCedente=rs.getDate("LastUpdate");
				 }
			}
			String stringUpdateTime = sdfd.format(updateConcentracaoCedente);
			String stringCurrentTime = sdfd.format(Calendar.getInstance().getTime());

			
			if(stringUpdateTime.equals(stringCurrentTime))
			{
				System.out.println("ConcentracaoCedente Updated LastUpdate:" + stringUpdateTime);
				System.out.println("ConcentracaoSacado Updated LastUpdate:" + stringUpdateTime);
			}
			else
			{
				//Read concentracao cedente
				System.out.println("Reading " + pathCedente );
				try 
				{
					reader = new BufferedReader(new FileReader(pathCedente));
				} catch (FileNotFoundException e1) 
				{

					e1.printStackTrace();
				}
				try 
				{
					String line = null;
					while ((line = reader.readLine()) != null) 
					{
						linesCedente.add(line);
					}
				} catch (IOException e) {

					e.printStackTrace();
				}

				//Read concentracao sacado
				System.out.println("Reading " + pathSacado );
				try 
				{
					reader = new BufferedReader(new FileReader(pathSacado));
				} catch (FileNotFoundException e1) 
				{

					e1.printStackTrace();
				}
				try 
				{
					String line = null;
					while ((line = reader.readLine()) != null) 
					{
						linesSacado.add(line);
					}
				} catch (IOException e) {

					e.printStackTrace();
				}					
				
				//Read PL Fundo
				System.out.println("Reading " + pathPL );
				try 
				{
					reader = new BufferedReader(new FileReader(pathPL));
				} catch (FileNotFoundException e1) 
				{

					e1.printStackTrace();
				}
				try 
				{
					String line = null;
					while ((line = reader.readLine()) != null) 
					{
						linesPL.add(line);
					}
				} catch (IOException e) {

					e.printStackTrace();
				}
				
				try 
				{
					stmt = (Statement) UpdateConcentracao.conn.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();		
				}
				// Clean tables ConcentracaoCedente e ConcentracaoSacado
				String sql = "TRUNCATE TABLE ConcentracaoCedente";
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				sql = "TRUNCATE TABLE ConcentracaoSacado";
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				
				this.updateCedente(linesCedente);
				this.updateSacado(linesSacado);
				this.updatePL(linesPL);
			}
		} catch (SQLException e2) 
		{
			e2.printStackTrace();
		}		
	}
	public void updatePL(ArrayList<String> linesPL)
	{
		String nomeFundo = "";
		Date dataPL = Calendar.getInstance().getTime();
		double valor = 0.0;

		Statement stmt = null;

		int idFundo=0;
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		
		for(String line:linesPL)
		{
			//System.out.println(line);
			String[] field = line.split(";");
			
			if(field.length>0)
			{
//				System.out.println(field.length + "\t"+ line);
				if(field.length>2)
				{
					if(field[2].length()>0)
					{
						
						nomeFundo = field[2].replace("FIDC_", "").toLowerCase();
//						System.out.println(nomeFundo);
						if(nomeFundo.length()>0)
						{
							
							try {
								stmt = (Statement) UpdateConcentracao.conn.createStatement();
							} catch (SQLException e) {
								e.printStackTrace();
							}
							String query = "Select idEntidade from Entidade where nomeCurto = '" + nomeFundo + "'";
//							System.out.println(query);
							int idEntidade = 0;
							try {
								ResultSet rs = stmt.executeQuery(query);
								while (rs.next())
								{
									idEntidade = rs.getInt("idEntidade");
		//							System.out.println("Fundo: " + nomeFundo + " idEntidade: " + idEntidade);
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							
							query = "Select idFundo from Fundo where idEntidade = " + idEntidade;
//							System.out.println(query);
							try {
								ResultSet rs = stmt.executeQuery(query);
								while (rs.next())
								{
									idFundo = rs.getInt("idFundo");
//									System.out.println("Fundo: " + nomeFundo + " idFundo: " + idFundo + " idEntidade:" + idEntidade) ;
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							fundo = new FundoDeInvestimento(idFundo, conn);
						}				
					}
				}
				if(fundo.getNomeCurto().length()>0)
				{
					if(field.length>1)
					{
						if(field[0].length()>0 && field[1].length()>0)
						{
							try {
								dataPL = sdfd.parse(field[0]);
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							valor = Double.parseDouble(field[1].replace(".", "").replace(",", "."));
							System.out.println(fundo.getNomeCurto() + " " + sdfd.format(dataPL) + " " + valor);								
							String stringValues = idFundo + ",'" + sdfd.format(dataPL) + "'," + valor;
							String sql = "INSERT INTO `mvcapital`.`PLFundo` (`idFundo`,`data`,`valor`) VALUES (" + stringValues + ")";
//							System.out.println(sql);
							try {
								stmt.executeUpdate(sql);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}	
	
	public void updateCedente(ArrayList<String> linesCedente)
	{
		String nomeFundo = "";
		String nomeCedente = "";
		String docCedente = "";
		double valorPresente = 0.0;
		double limite = 0.0;
		double concentracao = 0.0;
		double operar = 0.0;
		Statement stmt = null;
		ArrayList<ConcentracaoCedenteIndicador> concentracaoCedente = new ArrayList<ConcentracaoCedenteIndicador>();

		int idFundo=0;
		int idEntidadeCedente=0;
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		Entidade cedente = new Entidade();
		
		for(String line:linesCedente)
		{
			//System.out.println(line);
			String[] field = line.split(";");
			
			if(field.length>0)
			{
				
				if(nomeFundo.length()==0 || !field[0].replace("FIDC_", "").toLowerCase().equals(nomeFundo))
				{
					nomeFundo = field[0].replace("FIDC_", "").toLowerCase();
					try {
						stmt = (Statement) UpdateConcentracao.conn.createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String query = "Select idEntidade from Entidade where nomeCurto = '" + nomeFundo + "'";
					int idEntidade = 0;
					try {
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next())
						{
							idEntidade = rs.getInt("idEntidade");
//							System.out.println("Fundo: " + nomeFundo + " idEntidade: " + idEntidade);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					query = "Select idFundo from Fundo where idEntidade = " + idEntidade;
					try {
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next())
						{
							idFundo = rs.getInt("idFundo");
							System.out.println("Fundo: " + nomeFundo + " idFundo: " + idFundo + " idEntidade:" + idEntidade) ;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					fundo = new FundoDeInvestimento(idFundo, conn);
				}				
				
				docCedente = field[3].replace(".", "").replace("/", "").replace("-", "");
				if(nomeCedente.length()==0 || !field[2].equals(nomeCedente))
				{
					nomeCedente = field[2];					
					try {
						stmt = (Statement) UpdateConcentracao.conn.createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String query = "Select idEntidade from Entidade where nome like '" + nomeCedente + "'" + " limit 1";
					idEntidadeCedente = 0;
					try {
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next())
						{
							idEntidadeCedente = rs.getInt("idEntidade");
							//System.out.println("Cedente: " + nomeCedente + " idEntidade: " + idEntidade);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					if(idEntidadeCedente==0)
					{
//						System.out.println("Cedente não cadastrado");
						String stringDataCadastro = sdfd.format(Calendar.getInstance().getTime());
						String nomeCurtoCedente = nomeCedente;
						
						if(nomeCedente.length()>45)
						{
							nomeCurtoCedente = nomeCedente.substring(0, 45);
						}
						try 
						{
							int idTipoDeCadastro = 2;
							if(docCedente.length()<14)
							{
								idTipoDeCadastro = 1;
							}
							stmt = (Statement) conn.createStatement();
							String sql = "INSERT INTO `MVCapital`.`Entidade` (`nome`,`nomeCurto`,`cadastro`,`dataDeInicio`,`idTipoDeCadastro`) VALUES ('"+nomeCedente+"','"+nomeCurtoCedente+"','" + docCedente + "','"+stringDataCadastro+"',"+idTipoDeCadastro+")";
//							System.out.println(sql);
							stmt.executeUpdate(sql);
//							System.out.println("New Cedente: " + nomeCedente);

							stmt = (Statement) conn.createStatement();
							query = "SELECT idEntidade FROM Entidade WHERE nome ='" + nomeCedente + "'";
//							System.out.println(query);
							ResultSet rs = stmt.executeQuery(query);
							while (rs.next())
							{
								idEntidadeCedente = rs.getInt("idEntidade");					
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
					cedente = new Entidade(idEntidadeCedente, conn);					
				}
				valorPresente = Double.parseDouble(field[5].replace(",", "."));
				limite = Double.parseDouble(field[6].replace("%", "").replace(",", "."));
				concentracao = Double.parseDouble(field[7].replace("%", "").replace(",", "."));
				operar = Double.parseDouble(field[8].replace(",", "."));				
				
				if(fundo.getNomeCurto().length()>0)
				{
					System.out.println(fundo.getNomeCurto() + " " + cedente.getNomeCurto() + " " + limite + " " + operar);								
					String stringValues = idEntidadeCedente + "," + idFundo + "," + valorPresente + "," + limite + "," + concentracao + "," + operar;
					String sql = "INSERT INTO `MVCapital`.`ConcentracaoCedente` (`idEntidadeCedente`,`idFundo`,`valorPresente`,`limite`,`concentracao`,`operar`) VALUES (" + stringValues + ")";
	//				System.out.println(sql);
					try {
						stmt.executeUpdate(sql);
					} catch (SQLException e) {
						e.printStackTrace();
					}									
					concentracaoCedente.add(new ConcentracaoCedenteIndicador(cedente,fundo,valorPresente,concentracao,operar));
				}
			}
		}
	}

	public void updateSacado(ArrayList<String> linesSacado)
	{
		String nomeFundo = "";
		String nomeSacado = "";
		String docSacado = "";
		double valorPresente = 0.0;
		double limite = 0.0;
		double concentracao = 0.0;
		double operar = 0.0;
		Statement stmt = null;
		ArrayList<ConcentracaoSacadoIndicador> concentracaoSacado = new ArrayList<ConcentracaoSacadoIndicador>();
		
		int idFundo=0;
		int idEntidadeSacado=0;
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		Entidade sacado = new Entidade();
		
		for(String line:linesSacado)
		{
			//System.out.println(line);
			String[] field = line.split(";");
			
			if(field.length>0)
			{
				
				if(nomeFundo.length()==0 || !field[0].replace("FIDC_", "").toLowerCase().equals(nomeFundo))
				{
					nomeFundo = field[0].replace("FIDC_", "").toLowerCase();
					try {
						stmt = (Statement) UpdateConcentracao.conn.createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String query = "Select idEntidade from Entidade where nomeCurto = '" + nomeFundo + "'";
					int idEntidade = 0;
					try {
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next())
						{
							idEntidade = rs.getInt("idEntidade");
//							System.out.println("Fundo: " + nomeFundo + " idEntidade: " + idEntidade);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					query = "Select idFundo from Fundo where idEntidade = " + idEntidade;
					try {
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next())
						{
							idFundo = rs.getInt("idFundo");
							System.out.println("Fundo: " + nomeFundo + " idFundo: " + idFundo + " idEntidade:" + idEntidade) ;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					fundo = new FundoDeInvestimento(idFundo, conn);
				}				
				
				docSacado = field[3].replace(".", "").replace("/", "").replace("-", "");
				if(nomeSacado.length()==0 || !field[2].equals(nomeSacado))
				{
					nomeSacado = field[2];					
					try {
						stmt = (Statement) UpdateConcentracao.conn.createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String query = "Select idEntidade from Entidade where nome = '" + nomeSacado + "'";
					idEntidadeSacado = 0;
					try {
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next())
						{
							idEntidadeSacado = rs.getInt("idEntidade");
							//System.out.println("Cedente: " + nomeCedente + " idEntidade: " + idEntidade);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					if(idEntidadeSacado==0)
					{
//						System.out.println("Cedente não cadastrado");
						String stringDataCadastro = sdfd.format(Calendar.getInstance().getTime());
						String nomeCurtoSacado = nomeSacado;
						
						if(nomeSacado.length()>45)
						{
							nomeCurtoSacado = nomeSacado.substring(0, 45);
						}
						try 
						{
							int idTipoDeCadastro = 2;
							if(docSacado.length()<14)
							{
								idTipoDeCadastro = 1;
							}
							stmt = (Statement) conn.createStatement();
							String sql = "INSERT INTO `MVCapital`.`Entidade` (`nome`,`nomeCurto`,`cadastro`,`dataDeInicio`,`idTipoDeCadastro`) VALUES ('"+nomeSacado+"','"+nomeCurtoSacado+"','" + docSacado + "','"+stringDataCadastro+"',"+idTipoDeCadastro+")";
//							System.out.println(sql);
							stmt.executeUpdate(sql);
//							System.out.println("New Cedente: " + nomeCedente);

							stmt = (Statement) conn.createStatement();
							query = "SELECT idEntidade FROM Entidade WHERE nome ='" + nomeSacado + "'";
//							System.out.println(query);
							ResultSet rs = stmt.executeQuery(query);
							while (rs.next())
							{
								idEntidadeSacado = rs.getInt("idEntidade");					
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
					sacado = new Entidade(idEntidadeSacado, conn);					
				}
				valorPresente = Double.parseDouble(field[5].replace(",", "."));
				if(!field[6].isEmpty())
				{
					limite = Double.parseDouble(field[6].replace("%", "").replace(",", "."));
				}
				else
				{
					limite = 0.0;
				}
				concentracao = Double.parseDouble(field[7].replace("%", "").replace(",", "."));
				operar = Double.parseDouble(field[8].replace(",", "."));				
				if(fundo.getNomeCurto().length()>0)
				{
					System.out.println(fundo.getNomeCurto() + " " + sacado.getNomeCurto() + " " + limite + " " + operar);
					String stringValues = idEntidadeSacado + "," + idFundo + "," + valorPresente + "," + limite + "," + concentracao + "," + operar;
					String sql = "INSERT INTO `MVCapital`.`ConcentracaoSacado` (`idEntidadeSacado`,`idFundo`,`valorPresente`,`limite`,`concentracao`,`operar`) VALUES (" + stringValues + ")";
	//				System.out.println(sql);
					try {
						stmt.executeUpdate(sql);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					concentracaoSacado.add(new ConcentracaoSacadoIndicador(sacado,fundo,valorPresente,concentracao,operar));
				}
			}
		}
	}
	
	public void readConf()
	{
		BufferedReader reader = null;
		System.out.println("Reading conf/automata.conf file");
		System.out.println("------------------");
		try 
		{
			reader = new BufferedReader(new FileReader("conf/automata.conf"));
		} catch (FileNotFoundException e1) 
		{

			e1.printStackTrace();
		}
		String line = null;
		try 
		{
			while ((line = reader.readLine()) != null) 
			{
				if(!line.isEmpty())
				{
					
					String[] fields = line.split(",");
					if (fields[0].contains("#"))
					{
						System.out.println("Comment Line:\t" + line);
					}
					else
					{
						System.out.println("Parameters Line:\t" + line);
						for (int i = 0; i<fields.length; i++)
						{
							switch (fields[0]) 
							{
					            case "server":  
					            	this.server = fields[1];
					                break;
					            case "port":
					            	this.port = Integer.parseInt(fields[1].replace(" ", ""));
					                break;				            	
					            case "userName":
					            	this.userName = fields[1];
					                break;
					            case "password":
					            	this.password = fields[1];
					                break;
					            case "dbName":
					            	this.dbName = fields[1];
					                break;
					            case "rootLocalWindows":
					            	OperatingSystem.setRootLocalWindows(fields[1]);
					            	break;
					            case "rootLocalLinux":
					            	OperatingSystem.setRootLocalLinux(fields[1]);
					            	break;
					            default: 
					            break;
							}
						}
					}
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public static SimpleDateFormat getSdfd() {
		return sdfd;
	}

	public static void setSdfd(SimpleDateFormat sdfd) {
		UpdateConcentracao.sdfd = sdfd;
	}

	public MySQLAccess getMysql() {
		return mysql;
	}

	public void setMysql(MySQLAccess mysql) {
		this.mysql = mysql;
	}

	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		UpdateConcentracao.conn = conn;
	}

//	public ArrayList<ConcentracaoCedente> getConcentracaoCedente() {
//		return concentracaoCedente;
//	}
//
//	public void setConcentracaoCedente(
//			ArrayList<ConcentracaoCedente> concentracaoCedente) {
//		this.concentracaoCedente = concentracaoCedente;
//	}
//
//	public ArrayList<ConcentracaoSacado> getConcentracaoSacado() {
//		return concentracaoSacado;
//	}
//
//	public void setConcentracaoSacado(
//			ArrayList<ConcentracaoSacado> concentracaoSacado) {
//		this.concentracaoSacado = concentracaoSacado;
//	}
}
