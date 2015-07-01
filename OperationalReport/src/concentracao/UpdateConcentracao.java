package concentracao;

import java.io.BufferedReader;
import java.io.File;
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

import mysql.MySQLAccess;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entidade.Entidade;
import fundo.FundoDeInvestimento;
import utils.OperatingSystem;

public class UpdateConcentracao 
{
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	private String server = "localhost"; //$NON-NLS-1$
	private int port = 3306;		
	private String userName = "root";  //$NON-NLS-1$
	private String password = "root"; //$NON-NLS-1$
	private String dbName = "root"; //$NON-NLS-1$
	private MySQLAccess mysql = null;
	private static Connection conn = null;
//	private ArrayList<ConcentracaoCedente> concentracaoCedente = new ArrayList<ConcentracaoCedente>();
//	private ArrayList<ConcentracaoSacado> concentracaoSacado = new ArrayList<ConcentracaoSacado>();
	
	public UpdateConcentracao() 
	{
		this.readConf();
		this.mysql = new MySQLAccess(this.server, this.port, this.userName, this.password, this.dbName);
		this.mysql.connect();	
		UpdateConcentracao.conn = (Connection) this.mysql.getConn();
	}
	
	public UpdateConcentracao(Connection conn)
	{
		UpdateConcentracao.conn = conn;
	}
	
	public static void main(String[] args)
	{
		UpdateConcentracao uc = new UpdateConcentracao();
		uc.readConf();
		UpdateConcentracao.update();
	}

	public static ArrayList<String> fileToLines(File file)
	{
		ArrayList<String> lines = new ArrayList<String>();
		String pathFile = file.getAbsolutePath();
		BufferedReader reader = null;
		
		System.out.println("Reading " + pathFile ); //$NON-NLS-1$
		try 
		{
			reader = new BufferedReader(new FileReader(pathFile));
		} catch (FileNotFoundException e1) 
		{

			e1.printStackTrace();
		}
		try 
		{
			String line = null;
			while ((line = reader.readLine()) != null) 
			{
				lines.add(line);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}	
		return lines;
	}
	
	public static void update()
	{		
		String pathCedente = ""; //$NON-NLS-1$
		String pathSacado = ""; //$NON-NLS-1$
		String pathSacadosBloqueados = ""; //$NON-NLS-1$
		String pathPL = ""; //$NON-NLS-1$
		
		Statement stmt = null;

		try {
			stmt = (Statement) UpdateConcentracao.conn.createStatement();
		} catch (SQLException e3) {
			e3.printStackTrace();
		};
		
		ArrayList<String> linesCedente = new ArrayList<String>(); 
		ArrayList<String> linesSacado = new ArrayList<String>();
		ArrayList<String> linesSacadosBloqueados = new ArrayList<String>();
		ArrayList<String> linesPL = new ArrayList<String>();
		
		if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			pathCedente="W:\\Fundos\\Operacao\\Concentracao\\Cedente.csv"; //$NON-NLS-1$
			pathSacado="W:\\Fundos\\Operacao\\Concentracao\\Sacado.csv"; //$NON-NLS-1$
			pathSacadosBloqueados="W:\\Fundos\\Operacao\\Concentracao\\SacadosBloqueados.csv"; //$NON-NLS-1$
			pathPL="W:\\Fundos\\Operacao\\Concentracao\\PL.csv"; //$NON-NLS-1$
		}		
		else 
		{
			pathCedente="/home/Fundos/Operacao/Concentracao/Cedente.csv"; //$NON-NLS-1$
			pathSacado="/home/Fundos/Operacao/Concentracao/Sacado.csv"; //$NON-NLS-1$
			pathSacadosBloqueados="/home/Fundos/Operacao/Concentracao/SacadosBloqueados.csv"; //$NON-NLS-1$
			pathPL="/home/Fundos/Operacao/Concentracao/PL.csv"; //$NON-NLS-1$
		}
		
		System.out.println(pathCedente);
		System.out.println(pathSacado);
		System.out.println(pathSacadosBloqueados);
		System.out.println(pathPL);
		
		String query = "Select max(updateTime) as LastUpdate from ConcentracaoCedente"; //$NON-NLS-1$
		System.out.println(query);

//		linesSacadosBloqueados = fileToLines(new File(pathSacadosBloqueados));
//		UpdateConcentracao.updateSacadosBloqueados(linesSacadosBloqueados);

		
		try 
		{
			ResultSet rs = stmt.executeQuery(query);
			Date updateConcentracaoCedente = null;
			try {
				updateConcentracaoCedente = sdfd.parse("1900-01-01"); //$NON-NLS-1$
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
			while (rs.next())
			{
				 if(rs.getDate("LastUpdate")!=null) //$NON-NLS-1$
				 {
					 updateConcentracaoCedente=rs.getDate("LastUpdate"); //$NON-NLS-1$
				 }
			}
			String stringUpdateTime = sdfd.format(updateConcentracaoCedente);
			String stringCurrentTime = sdfd.format(Calendar.getInstance().getTime());

			
			if(stringUpdateTime.equals(stringCurrentTime))
			{
				System.out.println("ConcentracaoCedente Updated LastUpdate:" + stringUpdateTime); //$NON-NLS-1$
				System.out.println("ConcentracaoSacado Updated LastUpdate:" + stringUpdateTime); //$NON-NLS-1$
			}
			else	
			{
				try 
				{
					stmt = (Statement) UpdateConcentracao.conn.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();		
				}
				// Clean tables ConcentracaoCedente e ConcentracaoSacado
				String sql = "TRUNCATE TABLE ConcentracaoCedente"; //$NON-NLS-1$
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				sql = "TRUNCATE TABLE ConcentracaoSacado"; //$NON-NLS-1$
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// Clean table blackListSacado				
				sql = "TRUNCATE TABLE BlackListSacado"; //$NON-NLS-1$
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				//Read concentracao cedente
				System.out.println("Reading " + pathCedente ); //$NON-NLS-1$				
				linesCedente = fileToLines(new File(pathCedente));
				UpdateConcentracao.updateCedente(linesCedente);
				//Read concentracao sacado
				System.out.println("Reading " + pathSacado ); //$NON-NLS-1$				
				linesSacado = fileToLines(new File(pathSacado));
				UpdateConcentracao.updateSacado(linesSacado);
				//Read PL Fundo
				System.out.println("Reading " + pathPL ); //$NON-NLS-1$				
				linesPL = fileToLines(new File(pathPL));
				UpdateConcentracao.updatePL(linesPL);
				//Read SacadoRisco
				System.out.println("Reading " + pathSacadosBloqueados ); //$NON-NLS-1$
				linesSacadosBloqueados = fileToLines(new File(pathSacadosBloqueados));
				UpdateConcentracao.updatePL(linesSacadosBloqueados);
			}
		} catch (SQLException e2) 
		{
			e2.printStackTrace();
		}		
	}
	public static void updateSacadosBloqueados(ArrayList<String> linesSacadoRisco)
	{
		String nomeFundo = ""; //$NON-NLS-1$
		Statement stmt = null;

		int idFundo=0;
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		
		for(String line:linesSacadoRisco)
		{
			System.out.println(line);
			String[] field = line.split(";"); //$NON-NLS-1$
			
			if(field.length>0)
			{
//				System.out.println(field.length + "\t"+ line);
				if(field.length>2)
				{
					if(field[2].length()>0)
					{
						
						nomeFundo = field[0].replace("FIDC_", "").toLowerCase(); //$NON-NLS-1$ //$NON-NLS-2$
						
						if(nomeFundo.length()>0)
						{
							
							try {
								stmt = (Statement) UpdateConcentracao.conn.createStatement();
							} catch (SQLException e) {
								e.printStackTrace();
							}
							String query = "Select idEntidade from Entidade where nomeCurto = '" + nomeFundo + "'"; //$NON-NLS-1$ //$NON-NLS-2$
//							System.out.println(query);
							int idEntidade = 0;
							try {
								ResultSet rs = stmt.executeQuery(query);
								while (rs.next())
								{
									idEntidade = rs.getInt("idEntidade"); //$NON-NLS-1$
		//							System.out.println("Fundo: " + nomeFundo + " idEntidade: " + idEntidade);
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							
							query = "Select idFundo from Fundo where idEntidade = " + idEntidade; //$NON-NLS-1$
//							System.out.println(query);
							try {
								ResultSet rs = stmt.executeQuery(query);
								while (rs.next())
								{
									idFundo = rs.getInt("idFundo"); //$NON-NLS-1$
//									System.out.println("Fundo: " + nomeFundo + " idFundo: " + idFundo + " idEntidade:" + idEntidade) ;
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							fundo = new FundoDeInvestimento(idFundo, conn);
							String nomeSacado = field[2].trim();
							String cnpjSacado = field[3].replace(".", "").replace("/","").replace("-",""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
							Double cvnp = Double.parseDouble(field[5].replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$
							Double cvnpPerc = Double.parseDouble(field[6].replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$
							Entidade sacado = new Entidade(nomeSacado, cnpjSacado,conn);
							System.out.println(fundo.getNomeCurto() + " -> " + sacado.getNome() + " -> " + cvnp); //$NON-NLS-1$ //$NON-NLS-2$
							
							if(cvnp > 0)
							{
								String stringValues = fundo.getIdFundo()+","+sacado.getIdEntidade()+","+cvnp+","+cvnpPerc; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
								String sql = "INSERT INTO `mvcapital`.`blacklistsacado` ( `idFundo`, `idEntidadeSacado`, `cvnp`,`cvnpPerc`) VALUES ("+ stringValues + ")"; //$NON-NLS-1$ //$NON-NLS-2$
								System.out.println(sql);
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
	}
	
	public static void updatePL(ArrayList<String> linesPL)
	{
		String nomeFundo = ""; //$NON-NLS-1$
		Date dataPL = Calendar.getInstance().getTime();
		double valor = 0.0;

		Statement stmt = null;

		int idFundo=0;
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		
		for(String line:linesPL)
		{
			//System.out.println(line);
			String[] field = line.split(";"); //$NON-NLS-1$
			
			if(field.length>0)
			{
//				System.out.println(field.length + "\t"+ line);
				if(field.length>2)
				{
					if(field[2].length()>0)
					{
						
						nomeFundo = field[2].replace("FIDC_", "").toLowerCase(); //$NON-NLS-1$ //$NON-NLS-2$
//						System.out.println(nomeFundo);
						if(nomeFundo.length()>0)
						{
							
							try {
								stmt = (Statement) UpdateConcentracao.conn.createStatement();
							} catch (SQLException e) {
								e.printStackTrace();
							}
							String query = "Select idEntidade from Entidade where nomeCurto = '" + nomeFundo + "'"; //$NON-NLS-1$ //$NON-NLS-2$
//							System.out.println(query);
							int idEntidade = 0;
							try {
								ResultSet rs = stmt.executeQuery(query);
								while (rs.next())
								{
									idEntidade = rs.getInt("idEntidade"); //$NON-NLS-1$
		//							System.out.println("Fundo: " + nomeFundo + " idEntidade: " + idEntidade);
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							
							query = "Select idFundo from Fundo where idEntidade = " + idEntidade; //$NON-NLS-1$
//							System.out.println(query);
							try {
								ResultSet rs = stmt.executeQuery(query);
								while (rs.next())
								{
									idFundo = rs.getInt("idFundo"); //$NON-NLS-1$
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
							valor = Double.parseDouble(field[1].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
							System.out.println(fundo.getNomeCurto() + " " + sdfd.format(dataPL) + " " + valor);								 //$NON-NLS-1$ //$NON-NLS-2$
							String stringValues = idFundo + ",'" + sdfd.format(dataPL) + "'," + valor; //$NON-NLS-1$ //$NON-NLS-2$
							String sql = "INSERT INTO `mvcapital`.`PLFundo` (`idFundo`,`data`,`valor`) VALUES (" + stringValues + ")"; //$NON-NLS-1$ //$NON-NLS-2$
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
	
	public static void updateCedente(ArrayList<String> linesCedente)
	{
		String nomeFundo = ""; //$NON-NLS-1$
		String nomeCedente = ""; //$NON-NLS-1$
		String docCedente = ""; //$NON-NLS-1$
		double valorPresente = 0.0;
		double limite = 0.0;
		double concentracao = 0.0;
		double operar = 0.0;
		Statement stmt = null;
		ArrayList<ConcentracaoCedente> concentracaoCedente = new ArrayList<ConcentracaoCedente>();

		int idFundo=0;
		int idEntidadeCedente=0;
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		Entidade cedente = new Entidade();
		
		for(String line:linesCedente)
		{
			//System.out.println(line);
			String[] field = line.split(";"); //$NON-NLS-1$
			
			if(field.length>0)
			{
				
				if(nomeFundo.length()==0 || !field[0].replace("FIDC_", "").toLowerCase().equals(nomeFundo)) //$NON-NLS-1$ //$NON-NLS-2$
				{
					nomeFundo = field[0].replace("FIDC_", "").toLowerCase(); //$NON-NLS-1$ //$NON-NLS-2$
					try {
						stmt = (Statement) UpdateConcentracao.conn.createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String query = "Select idEntidade from Entidade where nomeCurto = '" + nomeFundo + "'"; //$NON-NLS-1$ //$NON-NLS-2$
					int idEntidade = 0;
					try {
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next())
						{
							idEntidade = rs.getInt("idEntidade"); //$NON-NLS-1$
//							System.out.println("Fundo: " + nomeFundo + " idEntidade: " + idEntidade);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					query = "Select idFundo from Fundo where idEntidade = " + idEntidade; //$NON-NLS-1$
					try {
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next())
						{
							idFundo = rs.getInt("idFundo"); //$NON-NLS-1$
							System.out.println("Fundo: " + nomeFundo + " idFundo: " + idFundo + " idEntidade:" + idEntidade) ; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					fundo = new FundoDeInvestimento(idFundo, conn);
				}				
				
				docCedente = field[3].replace(".", "").replace("/", "").replace("-", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
				if(nomeCedente.length()==0 || !field[2].equals(nomeCedente))
				{
					nomeCedente = field[2].replace("'", "");					 //$NON-NLS-1$ //$NON-NLS-2$
					try {
						stmt = (Statement) UpdateConcentracao.conn.createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					String query = "Select idEntidade from Entidade where nome like '" + nomeCedente + "'" + " limit 1"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					System.out.println(query);
					idEntidadeCedente = 0;
					try {
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next())
						{
							idEntidadeCedente = rs.getInt("idEntidade"); //$NON-NLS-1$
							//System.out.println("Cedente: " + nomeCedente + " idEntidade: " + idEntidade);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					if(idEntidadeCedente==0)
					{
//						System.out.println("Cedente n�o cadastrado");
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
							String sql = "INSERT INTO `MVCapital`.`Entidade` (`nome`,`nomeCurto`,`cadastro`,`dataDeInicio`,`idTipoDeCadastro`) VALUES ('"+nomeCedente+"','"+nomeCurtoCedente+"','" + docCedente + "','"+stringDataCadastro+"',"+idTipoDeCadastro+")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
//							System.out.println(sql);
							stmt.executeUpdate(sql);
//							System.out.println("New Cedente: " + nomeCedente);

							stmt = (Statement) conn.createStatement();
							query = "SELECT idEntidade FROM Entidade WHERE nome ='" + nomeCedente + "'"; //$NON-NLS-1$ //$NON-NLS-2$
//							System.out.println(query);
							ResultSet rs = stmt.executeQuery(query);
							while (rs.next())
							{
								idEntidadeCedente = rs.getInt("idEntidade");					 //$NON-NLS-1$
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
					cedente = new Entidade(idEntidadeCedente, conn);					
				}
				valorPresente = Double.parseDouble(field[5].replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$
				limite = Double.parseDouble(field[6].replace("%", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				concentracao = Double.parseDouble(field[7].replace("%", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				operar = Double.parseDouble(field[8].replace(",", "."));				 //$NON-NLS-1$ //$NON-NLS-2$
				
				if(fundo.getNomeCurto().length()>0)
				{
					System.out.println(fundo.getNomeCurto() + " " + cedente.getNomeCurto() + " " + limite + " " + operar);								 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					String stringValues = idEntidadeCedente + "," + idFundo + "," + valorPresente + "," + limite + "," + concentracao + "," + operar; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
					String sql = "INSERT INTO `MVCapital`.`ConcentracaoCedente` (`idEntidadeCedente`,`idFundo`,`valorPresente`,`limite`,`concentracao`,`operar`) VALUES (" + stringValues + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	//				System.out.println(sql);
					try {
						stmt.executeUpdate(sql);
					} catch (SQLException e) {
						e.printStackTrace();
					}									
					concentracaoCedente.add(new ConcentracaoCedente(cedente,fundo,valorPresente,limite,concentracao,operar,Calendar.getInstance().getTime()));
				}
			}
		}
	}

	public static void updateSacado(ArrayList<String> linesSacado)
	{
		String nomeFundo = ""; //$NON-NLS-1$
		String nomeSacado = ""; //$NON-NLS-1$
		String docSacado = ""; //$NON-NLS-1$
		double valorPresente = 0.0;
		double limite = 0.0;
		double concentracao = 0.0;
		double operar = 0.0;
		Statement stmt = null;
		ArrayList<ConcentracaoSacado> concentracaoSacado = new ArrayList<ConcentracaoSacado>();
		
		int idFundo=0;
		int idEntidadeSacado=0;
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		Entidade sacado = new Entidade();
		
		for(String line:linesSacado)
		{
			//System.out.println(line);
			String[] field = line.split(";"); //$NON-NLS-1$
			
			if(field.length>0)
			{
				
				if(nomeFundo.length()==0 || !field[0].replace("FIDC_", "").toLowerCase().equals(nomeFundo)) //$NON-NLS-1$ //$NON-NLS-2$
				{
					nomeFundo = field[0].replace("FIDC_", "").toLowerCase(); //$NON-NLS-1$ //$NON-NLS-2$
					try {
						stmt = (Statement) UpdateConcentracao.conn.createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String query = "Select idEntidade from Entidade where nomeCurto = '" + nomeFundo + "'"; //$NON-NLS-1$ //$NON-NLS-2$
					int idEntidade = 0;
					try {
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next())
						{
							idEntidade = rs.getInt("idEntidade"); //$NON-NLS-1$
//							System.out.println("Fundo: " + nomeFundo + " idEntidade: " + idEntidade);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					query = "Select idFundo from Fundo where idEntidade = " + idEntidade; //$NON-NLS-1$
					try {
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next())
						{
							idFundo = rs.getInt("idFundo"); //$NON-NLS-1$
							System.out.println("Fundo: " + nomeFundo + " idFundo: " + idFundo + " idEntidade:" + idEntidade) ; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					fundo = new FundoDeInvestimento(idFundo, conn);
				}				
				
				docSacado = field[3].replace(".", "").replace("/", "").replace("-", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
				if(nomeSacado.length()==0 || !field[2].equals(nomeSacado))
				{
					nomeSacado = field[2];					
					try {
						stmt = (Statement) UpdateConcentracao.conn.createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String query = "Select idEntidade from Entidade where nome = '" + nomeSacado + "'"; //$NON-NLS-1$ //$NON-NLS-2$
					idEntidadeSacado = 0;
					try {
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next())
						{
							idEntidadeSacado = rs.getInt("idEntidade"); //$NON-NLS-1$
							//System.out.println("Cedente: " + nomeCedente + " idEntidade: " + idEntidade);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					if(idEntidadeSacado==0)
					{
//						System.out.println("Cedente n�o cadastrado");
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
							String sql = "INSERT INTO `MVCapital`.`Entidade` (`nome`,`nomeCurto`,`cadastro`,`dataDeInicio`,`idTipoDeCadastro`) VALUES ('"+nomeSacado+"','"+nomeCurtoSacado+"','" + docSacado + "','"+stringDataCadastro+"',"+idTipoDeCadastro+")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
//							System.out.println(sql);
							stmt.executeUpdate(sql);
//							System.out.println("New Cedente: " + nomeCedente);

							stmt = (Statement) conn.createStatement();
							query = "SELECT idEntidade FROM Entidade WHERE nome ='" + nomeSacado + "'"; //$NON-NLS-1$ //$NON-NLS-2$
//							System.out.println(query);
							ResultSet rs = stmt.executeQuery(query);
							while (rs.next())
							{
								idEntidadeSacado = rs.getInt("idEntidade");					 //$NON-NLS-1$
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
					sacado = new Entidade(idEntidadeSacado, conn);					
				}
				valorPresente = Double.parseDouble(field[5].replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$
				if(!field[6].isEmpty())
				{
					limite = Double.parseDouble(field[6].replace("%", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}
				else
				{
					limite = 0.0;
				}
				concentracao = Double.parseDouble(field[7].replace("%", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				operar = Double.parseDouble(field[8].replace(",", "."));				 //$NON-NLS-1$ //$NON-NLS-2$
				if(fundo.getNomeCurto().length()>0)
				{
					System.out.println(fundo.getNomeCurto() + " " + sacado.getNomeCurto() + " " + limite + " " + operar); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					String stringValues = idEntidadeSacado + "," + idFundo + "," + valorPresente + "," + limite + "," + concentracao + "," + operar; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
					String sql = "INSERT INTO `MVCapital`.`ConcentracaoSacado` (`idEntidadeSacado`,`idFundo`,`valorPresente`,`limite`,`concentracao`,`operar`) VALUES (" + stringValues + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	//				System.out.println(sql);
					try {
						stmt.executeUpdate(sql);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					concentracaoSacado.add(new ConcentracaoSacado(sacado,fundo,valorPresente,limite,concentracao,operar,Calendar.getInstance().getTime()));
				}
			}
		}
	}
	
	public void readConf()
	{
		BufferedReader reader = null;
		System.out.println("Reading conf/automata.conf file"); //$NON-NLS-1$
		System.out.println("------------------"); //$NON-NLS-1$
		try 
		{
			reader = new BufferedReader(new FileReader("/home/Reports/conf/extratobancopaulista.conf")); //$NON-NLS-1$
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
					
					String[] fields = line.split(","); //$NON-NLS-1$
					if (fields[0].contains("#")) //$NON-NLS-1$
					{
						System.out.println("Comment Line:\t" + line); //$NON-NLS-1$
					}
					else
					{
						System.out.println("Parameters Line:\t" + line); //$NON-NLS-1$
						for (int i = 0; i<fields.length; i++)
						{
							switch (fields[0]) 
							{
					            case "server":   //$NON-NLS-1$
					            	this.server = fields[1];
					                break;
					            case "port": //$NON-NLS-1$
					            	this.port = Integer.parseInt(fields[1].replace(" ", "")); //$NON-NLS-1$ //$NON-NLS-2$
					                break;				            	
					            case "userName": //$NON-NLS-1$
					            	this.userName = fields[1];
					                break;
					            case "password": //$NON-NLS-1$
					            	this.password = fields[1];
					                break;
					            case "dbName": //$NON-NLS-1$
					            	this.dbName = fields[1];
					                break;
					            case "rootLocalWindows": //$NON-NLS-1$
					            	OperatingSystem.setRootLocalWindows(fields[1]);
					            	break;
					            case "rootLocalLinux": //$NON-NLS-1$
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
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return this.dbName;
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
		return this.mysql;
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
