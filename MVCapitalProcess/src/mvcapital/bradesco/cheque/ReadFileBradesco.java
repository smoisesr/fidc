package mvcapital.bradesco.cheque;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mvcapital.bancopaulista.liquidadosebaixados.HandlerOcorrencia;
import mvcapital.bancopaulista.liquidadosebaixados.Ocorrencia;
import mvcapital.bancopaulista.liquidadosebaixados.TipoOcorrencia;
import mvcapital.conta.Conta;
import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.mysql.MySQLAccess;
import mvcapital.praca.HandlerPraca;
import mvcapital.praca.Praca;
import mvcapital.relatorio.cessao.Titulo;
import mvcapital.utils.FileHTMLtoCSV;
import mvcapital.utils.FileToLines;
import mvcapital.utils.SshClient;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ReadFileBradesco 
{
	private static SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
	public static Connection conn = null;
//	private Conta conta = new Conta();
//	private FundoDeInvestimento fundo = new FundoDeInvestimento();
	public static String pathProcessar = "W:\\Fundos\\Repositorio\\Cheques\\Processar\\"; //$NON-NLS-1$
	public static String pathProcessarLinux = "/home/database/Fundos/Repositorio/Cheques/Processar/"; //$NON-NLS-1$
	public static String pathProcessado = "W:\\Fundos\\Repositorio\\Cheques\\Processado\\"; //$NON-NLS-1$
	public static String pathProcessadoLinux = "/home/database/Fundos/Repositorio/Cheques/Processado/"; //$NON-NLS-1$
	public static SshClient sshClient = new SshClient("192.168.2.122", 22, "moises", "preciosa"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	public static SimpleDateFormat sdfd= new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	
	public ReadFileBradesco(File file) 
	{
		processFile(file);
	}
	
	public static void main(String[] args)
	{
		MySQLAccess.readConf();
		MySQLAccess mysqla = new MySQLAccess();
		mysqla.connect();
		ReadFileBradesco.conn = (Connection) mysqla.getConn();
		File[] files = new File(ReadFileBradesco.pathProcessar).listFiles(); 
		boolean isConnected=false;
		if(files.length>0)
		{
			for(File file:files)
			{
				if(file.getName().toLowerCase().contains("bradesco") //$NON-NLS-1$
						&& file.getName().toLowerCase().endsWith(".html") //$NON-NLS-1$
						)
				{
					System.out.println("File: " + file.getName()); //$NON-NLS-1$
//					if(file.getName().toLowerCase().endsWith(".html")) //$NON-NLS-1$
//					{
//						if(!isConnected)
//						{
//							ReadFileBradesco.sshClient.connect();
//							isConnected = true;
//						}
//						System.out.println("Need to transform into .csv file"); //$NON-NLS-1$
//						String csvFile = ReadFileBradesco.convertHtmlToCsv(file, sshClient);
//						file = new File(ReadFileBradesco.pathProcessar + csvFile);
//					}
//					else if(file.getName().toLowerCase().endsWith(".csv")) //$NON-NLS-1$
//					{
//						System.out.println("Already a .csv file"); //$NON-NLS-1$
//					}
//					
//					
					boolean wellProcessed = false;			
					wellProcessed = ReadFileBradesco.processFileCSVFromHTML(file);
//					
//					if(wellProcessed)
//					{
//						if(file.renameTo(new File(ReadFileBradesco.pathProcessado + file.getName())))
//						{
//							System.out.println(file.getName() + " moved to " + ReadFileBradesco.pathProcessado); //$NON-NLS-1$
//						}
//						else
//						{
//							System.out.println("Failed to move " + file.getName()); //$NON-NLS-1$
//							System.out.println("Trying to delete it "); //$NON-NLS-1$
//							file.deleteOnExit();
//						}
//					}
				}
			}
			if(isConnected)
			{
				ReadFileBradesco.sshClient.disconnect();
			}
		}
	}
	
	public static String convertHtmlToCsv(File file, SshClient sshClient)
	{
		String command = "cd " + ReadFileBradesco.pathProcessarLinux; //$NON-NLS-1$
		System.out.println(command);
		sshClient.executeCommand(command);
		String fileName = ReadFileBradesco.pathProcessarLinux+file.getName();
		String targetFileName = ReadFileBradesco.pathProcessadoLinux+file.getName();
		
		command = "html2csv.py " + fileName; //$NON-NLS-1$
		System.out.println(command);
		System.out.println(sshClient.executeCommandOutput(command));
		
		command = " mv " + fileName + " " + targetFileName; //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println(command);
		System.out.println(sshClient.executeCommandOutput(command));
		return file.getName().replace(".HTML", ".csv").replace(".html", ".csv"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
	
	public static boolean processFileCSVFromHTML(File file)
	{
		boolean wellProcessed = false;
		ArrayList<String> lines = FileHTMLtoCSV.convertBradescoChequeExtratoHTML(file);
		
		/**
		 * IDENTIFICATION OF FUND, CLIENT, SERVER AND ACCOUNT DETAILS
		 */
		String dadosConta = lines.get(1);
		String[] fieldsConta = dadosConta.split(";"); //$NON-NLS-1$
		String agencia = removeLeftZeros(fieldsConta[1].trim()); //$NON-NLS-1$
		String numero = removeLeftZeros(fieldsConta[2].trim());
		System.out.println("Agencia: " + agencia); //$NON-NLS-1$
		System.out.println("NumeroConta: " + numero); //$NON-NLS-1$
		Conta conta = new Conta(agencia,numero,ReadFileBradesco.conn);
		FundoDeInvestimento fundo = ReadFileBradesco.setupFundo(conta.getEntidadeCliente().getIdEntidade());
		System.out.println("Cliente: " + conta.getEntidadeCliente().getNomeCurto() +  " idFundo: " + fundo.getIdFundo()); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("Banco: " + conta.getEntidadeServidor().getNome()); //$NON-NLS-1$
//		
//		System.out.println("Data"  //$NON-NLS-1$
//				+ ";" + "ocorrencia"  //$NON-NLS-1$ //$NON-NLS-2$
//				+ ";" + "numeroCheque"  //$NON-NLS-1$ //$NON-NLS-2$
//				+ ";" + "codigoBanco"  //$NON-NLS-1$ //$NON-NLS-2$
//				+ ";" + "numeroAgencia" //$NON-NLS-1$ //$NON-NLS-2$
//				+ ";" + "numeroConta"											 //$NON-NLS-1$ //$NON-NLS-2$
//				+ ";" + "stringCredito + stringDebito" //$NON-NLS-1$ //$NON-NLS-2$
//				);
		for(String line:lines)
		{
//			System.out.println(line);
//			line = line.toLowerCase();
			if(!line.isEmpty())
			{										
				if(line.contains("/")) //$NON-NLS-1$
				{
					String[] fields = line.split(";"); //$NON-NLS-1$					
					System.out.println(fields.length + " " + line);					 //$NON-NLS-1$
//					Data da Compensação ou Devolução;
//					Histórico;
//					Docto.;
//					Bco.;
//					Ag;
//					Conta;
//					Valor (R$);
//					13/11/2014;Cheque Depositado;21;33;3929;9913057605;48.500,00;
					
					Date dataOcorrencia=null;
					try {
						dataOcorrencia = sdfr.parse(fields[0]);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					String stringOcorrencia = fields[1];
					String numeroCheque = fields[2];
					String codigoBanco = fields[3];
					String numeroAgencia = fields[4];
					String numeroConta = fields[5];
					int numeroMotivoDevolucao = 0;
					double valorNominal = Double.parseDouble(fields[6].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					Entidade entidadeBanco = HandlerPraca.entidadeBanco(codigoBanco, conn);
					if(stringOcorrencia.contains("Devolvido")) //$NON-NLS-1$
					{
						numeroMotivoDevolucao = Integer.parseInt(stringOcorrencia.split(":")[1]); //$NON-NLS-1$
						stringOcorrencia = stringOcorrencia.split(":")[0]; //$NON-NLS-1$
					}
					ArrayList<Integer> idTitulos=new ArrayList<Integer>();
					idTitulos = ReadFileBradesco.checkTitulo(fundo.getIdFundo(), valorNominal, dataOcorrencia, stringOcorrencia, numeroCheque, conn);

					TipoOcorrencia tipoOcorrencia = new TipoOcorrencia(numeroMotivoDevolucao,"Bradesco",conn); //$NON-NLS-1$
					if(idTitulos.size()==0)
					{
						System.out.println("Title is not at database please insert it!"); //$NON-NLS-1$
					}
					else if(idTitulos.size()==1)
					{
						Titulo cheque = new Titulo(idTitulos.get(0), conn);

//						private int idOcorrencia = 0;
//						private Titulo titulo = new Titulo();
//						private TipoOcorrencia tipoOcorrencia = new TipoOcorrencia();
//						private Date data = Calendar.getInstance().getTime();
//						private Entidade cedente = new Entidade();
//						private double valor = 0.0;
//						private int flagCNAB = 0;
//						private int idPraca=0;

						Ocorrencia novaOcorrencia = new Ocorrencia();
						Praca praca = new Praca();
						praca.setEntidadeBanco(entidadeBanco);
						praca.setAgencia(numeroAgencia);
						HandlerPraca.store(praca, conn);
						HandlerPraca.setupIdPraca(praca, conn);
						novaOcorrencia.setTitulo(cheque);
						novaOcorrencia.setData(dataOcorrencia);
						novaOcorrencia.setCedente(cheque.getCedente());
						novaOcorrencia.setValor(valorNominal);
						novaOcorrencia.setPraca(praca);
						novaOcorrencia.setTipoOcorrencia(tipoOcorrencia);
						
						System.out.println("-- Processing: " + cheque.getIdTitulo()); //$NON-NLS-1$
						ArrayList<Ocorrencia> ocorrencias = ReadFileBradesco.checkTableOcorrencia(cheque.getIdTitulo(), conn);
						boolean chequeBaixado=false;
						if(ocorrencias.size()>0)
						{
							if(isBaixado(ocorrencias))
							{
								System.out.println("---Baixado!");				 //$NON-NLS-1$
								chequeBaixado=true;
							}							
						}
						novaOcorrencia.show();
						if(!chequeBaixado)
						{
							HandlerOcorrencia.store(novaOcorrencia, conn);
						}
					}
					else
					{
						System.out.println("Manual identification for this titles please!"); //$NON-NLS-1$
						for(int idTitulo:idTitulos)
						{
							Titulo cheque = new Titulo(idTitulo, conn);							
							System.out.println("idTitulo: " + cheque.getIdTitulo()); //$NON-NLS-1$
						}
					}
					
					System.out.println(sdfr.format(dataOcorrencia) 
										+ ";" + stringOcorrencia  //$NON-NLS-1$
										+ ";" + numeroCheque  //$NON-NLS-1$
										+ ";" + codigoBanco  //$NON-NLS-1$
										+ ";" + numeroAgencia //$NON-NLS-1$
										+ ";" + numeroConta											 //$NON-NLS-1$
										+ ";" + valorNominal //$NON-NLS-1$
										);
				}
			}
		}
		return wellProcessed;
	}
	
	public static FundoDeInvestimento setupFundo(int idEntidadeFundo)
	{
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT idFundo FROM Fundo WHERE" //$NON-NLS-1$
				+ " idEntidade = " + idEntidadeFundo; //$NON-NLS-1$
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idFundo = rs.getInt("idFundo"); //$NON-NLS-1$
				if(idFundo!=0)
				{
					fundo = new FundoDeInvestimento(idFundo, conn);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return fundo;
	}
	
	public static ArrayList<Integer> checkTitulo(int idFundo, double valorNominal, Date dataOcorrencia, String stringOcorrencia, String numeroCheque, Connection conn)
	{
		ArrayList<Integer> idTitulos = new ArrayList<Integer>();
		ArrayList<Titulo> titulos = new ArrayList<Titulo>();
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
//		select * from titulo 
//		where 
//		idFundo=5 
//		and valorNominal=1250 
//		and idTipoTitulo=1 
//		and numeroDocumento like "%16%"
		
		String query = "SELECT idTitulo FROM Titulo " //$NON-NLS-1$
				+ " WHERE" //$NON-NLS-1$
				+ " idFundo = " + idFundo //$NON-NLS-1$
				+ " AND " + "valorNominal=" + valorNominal //$NON-NLS-1$ //$NON-NLS-2$
				+ " AND " + "idTipoTitulo=1" //$NON-NLS-1$ //$NON-NLS-2$
				+ " AND " + "numeroDocumento like '%" + numeroCheque + "%'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				;
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idTitulo = 0;
				idTitulo = rs.getInt("idTitulo"); //$NON-NLS-1$
				if(idTitulo!=0)
				{
					Titulo titulo=new Titulo(idTitulo,conn);
					String numeroDocumento = ReadFileBradesco.removeLeftZeros(titulo.getNumeroDocumento());
					if(numeroDocumento.compareTo(numeroCheque)==0)
					{
						idTitulos.add(idTitulo);
						titulos.add(titulo);
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		
		for(Titulo titulo:titulos)
		{
			if(titulo.getDataVencimento().compareTo(dataOcorrencia)==0 && stringOcorrencia.toLowerCase().contains("depositado")) //$NON-NLS-1$
			{
				idTitulos=new ArrayList<Integer>();
				idTitulos.add(titulo.getIdTitulo());
				break;
			}
		}
		
		return idTitulos;
	}

	public static Date checkOcorrencia(int idTitulo, int idTipoOcorrencia, Connection conn)
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
//		select * from titulo where idFundo=5 and valorNominal=1250 and idTipoTitulo=1 and numeroDocumento like "%16%"			
		String query = "SELECT idOcorrencia, data FROM ocorrencia " //$NON-NLS-1$
				+ " WHERE" //$NON-NLS-1$
				+ " idTitulo = " + idTitulo //$NON-NLS-1$
				+ " AND idTipoOcorrencia = " + idTipoOcorrencia //$NON-NLS-1$
				;
//		System.out.println(query);
		ResultSet rs;
		Date dataOcorrencia = Calendar.getInstance().getTime();
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idOcorrencia = rs.getInt("idOcorrencia"); //$NON-NLS-1$
				if(idOcorrencia!=0)
				{
					dataOcorrencia = rs.getDate("data"); //$NON-NLS-1$
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}					
		return dataOcorrencia;
	}
	
	public static ArrayList<Ocorrencia> checkTableOcorrencia(int idTitulo, Connection conn)
	{
		ArrayList<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
//		select * from titulo where idFundo=5 and valorNominal=1250 and idTipoTitulo=1 and numeroDocumento like "%16%"			
		String query = "SELECT idOcorrencia, idTipoOcorrencia, data, valor FROM ocorrencia " //$NON-NLS-1$
				+ " WHERE" //$NON-NLS-1$
				+ " idTitulo = " + idTitulo //$NON-NLS-1$
				;
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idOcorrencia = rs.getInt("idOcorrencia"); //$NON-NLS-1$
				int idTipoOcorrencia = rs.getInt("idTipoOcorrencia"); //$NON-NLS-1$
				Date dataOcorrencia = rs.getDate("data"); //$NON-NLS-1$
				double valor = rs.getDouble("valor"); //$NON-NLS-1$
				
				TipoOcorrencia tipoOc = new TipoOcorrencia(idTipoOcorrencia,conn);
				//Ocorrencia(int idOcorrencia, int idTitulo, TipoOcorrencia tipoOcorrencia, Date dataOcorrencia, double valor, Connection conn)
				Ocorrencia oc = new Ocorrencia(idOcorrencia, idTitulo, tipoOc, dataOcorrencia, valor, conn);
				ocorrencias.add(oc);				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}					
		return ocorrencias;
	}
	
	public static String removeLeftZeros(String stringNumero)
	{
		String numeroLimpo=""; //$NON-NLS-1$
		numeroLimpo = stringNumero.replaceFirst("^0+(?!$)", ""); //$NON-NLS-1$ //$NON-NLS-2$
		return numeroLimpo;
	}
	public static void processExtratoCheque(ArrayList<String> lines)
	{
		/**
		 * IDENTIFICATION OF FUND, CLIENT, SERVER AND ACCOUNT DETAILS
		 */
		String dadosConta = lines.get(0);
		String[] fieldsConta = dadosConta.split(":"); //$NON-NLS-1$
		String agencia = fieldsConta[2].trim().split(" ")[0]; //$NON-NLS-1$
		String numero = fieldsConta[3].trim();
		
		System.out.println("Agencia: " + agencia); //$NON-NLS-1$
		System.out.println("NumeroConta: " + numero); //$NON-NLS-1$
		Conta conta = new Conta(agencia,numero,ReadFileBradesco.conn);
		
		System.out.println("Cliente: " + conta.getEntidadeCliente().getNome()); //$NON-NLS-1$
		System.out.println("Banco: " + conta.getEntidadeServidor().getNome()); //$NON-NLS-1$
		FundoDeInvestimento fundo = ReadFileBradesco.setupFundo(conta.getEntidadeCliente().getIdEntidade());
		System.out.println("iFundo: " + fundo.getIdFundo()); //$NON-NLS-1$
		
		System.out.println("Data"  //$NON-NLS-1$
				+ ";" + "ocorrencia"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "numeroCheque"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "codigoBanco"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "numeroAgencia" //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "numeroConta"											 //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "stringCredito + stringDebito" //$NON-NLS-1$ //$NON-NLS-2$
				);
		for(String line:lines)
		{
			line = line.toLowerCase();
			if(!line.isEmpty())
			{					
				String[] fields = line.split(";"); //$NON-NLS-1$
//					System.out.println(line);					
				if(line.contains("/")) //$NON-NLS-1$
				{
//						Data da Compensação ou Devolução;
//						Histórico;
//						Docto.;
//						Bco.;
//						Ag;
//						Conta;
//						Crédito (R$);
//						Débito (R$)
//						13/11/2014;Cheque Depositado;21;33;3929;9913057605;48.500,00;						
					Date dataOcorrencia=null;
					try {
						dataOcorrencia = sdfr.parse(fields[0]);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					String stringOcorrencia = fields[1];
					String numeroCheque = fields[2];
					String codigoBanco = fields[3];
					String numeroAgencia = fields[4];
					String numeroConta = fields[5];
					double credito = 0.0;
					double debito = 0.0;
					
					Entidade entidadeBanco = HandlerPraca.entidadeBanco(codigoBanco, conn);
					if(fields[6].length()>0)
					{
						credito = Double.parseDouble(fields[6].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					}
					if(fields.length>7)
					{
//							System.out.println(fields[7]);
						debito = Double.parseDouble(fields[7].replace(".", "").replace(",", "."))*-1; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					}
					if(stringOcorrencia.contains("devolvido")) //$NON-NLS-1$
					{
						stringOcorrencia = "Devolvido"; //$NON-NLS-1$
					}
					else
					{
						stringOcorrencia = "Depositado"; //$NON-NLS-1$
					}										
					String stringCredito=Double.toString(credito);
					String stringDebito=Double.toString(debito);
					if(Double.compare(credito, 0.0)==0)
					{
						stringCredito=""; //$NON-NLS-1$
					}
					if(Double.compare(debito, 0.0)==0)
					{
						stringDebito=""; //$NON-NLS-1$
					}
					
					double valorNominal = Double.parseDouble(stringCredito + stringDebito);
					ArrayList<Integer> idTitulos=new ArrayList<Integer>();
					idTitulos = ReadFileBradesco.checkTitulo(fundo.getIdFundo(), valorNominal, dataOcorrencia, stringOcorrencia, numeroCheque, conn);

					TipoOcorrencia tipoOcorrencia = new TipoOcorrencia();
					if(idTitulos.size()==0)
					{
						System.out.println("Title is not at database please insert it!"); //$NON-NLS-1$
					}
					else if(idTitulos.size()==1)
					{
						Titulo cheque = new Titulo(idTitulos.get(0), conn);

//						private int idOcorrencia = 0;
//						private Titulo titulo = new Titulo();
//						private TipoOcorrencia tipoOcorrencia = new TipoOcorrencia();
//						private Date data = Calendar.getInstance().getTime();
//						private Entidade cedente = new Entidade();
//						private double valor = 0.0;
//						private int flagCNAB = 0;
//						private int idPraca=0;

						Ocorrencia novaOcorrencia = new Ocorrencia();
						Praca praca = new Praca();
						praca.setEntidadeBanco(entidadeBanco);
						praca.setAgencia(numeroAgencia);
						HandlerPraca.store(praca, conn);
						HandlerPraca.setupIdPraca(praca, conn);
						novaOcorrencia.setTitulo(cheque);
						novaOcorrencia.setData(dataOcorrencia);
						novaOcorrencia.setCedente(cheque.getCedente());
						novaOcorrencia.setValor(valorNominal);
						novaOcorrencia.setPraca(praca);
						
						System.out.println("-- Processing: " + cheque.getIdTitulo()); //$NON-NLS-1$
						ArrayList<Ocorrencia> ocorrencias = ReadFileBradesco.checkTableOcorrencia(cheque.getIdTitulo(), conn);
						boolean chequeBaixado=false;
						if(ocorrencias.size()>0)
						{
							if(isBaixado(ocorrencias))
							{
								System.out.println("---Baixado!");				 //$NON-NLS-1$
								chequeBaixado=true;
							}
							else
							{
								System.out.println("---Other ocurrences"); //$NON-NLS-1$
								if(stringOcorrencia.toLowerCase().contains("depositado")) //$NON-NLS-1$
								{
									tipoOcorrencia = new TipoOcorrencia("Cheque Depositado", conn); //$NON-NLS-1$
//									int countDepositos = countDepositos(ocorrencias);
//									System.out.println("countDepositos " + countDepositos); //$NON-NLS-1$
//									if(countDepositos==0)
//									{
//										
//									}
								}
								else if(stringOcorrencia.toLowerCase().contains("devolvido")) //$NON-NLS-1$
								{
									int countDevolvidos = countDevolucao(ocorrencias);
									System.out.println("countDevolvidos " + countDevolvidos); //$NON-NLS-1$
									if(countDevolvidos==0)
									{
										tipoOcorrencia = new TipoOcorrencia("Cheque 1ra Devolucao", conn); //$NON-NLS-1$
									}									
									else if(countDevolvidos==1)
									{
										tipoOcorrencia = new TipoOcorrencia("Cheque 2da Devolucao", conn); //$NON-NLS-1$
									}									
									else
									{
										System.out.println("After second devolution!"); //$NON-NLS-1$
										tipoOcorrencia = new TipoOcorrencia("Cheque 2da Devolucao", conn); //$NON-NLS-1$
									}
								}
							}
							
						}
						else
						{							
							if(stringOcorrencia.toLowerCase().contains("depositado")) //$NON-NLS-1$
							{
								tipoOcorrencia = new TipoOcorrencia("Cheque Depositado", conn); //$NON-NLS-1$							
							}
							else if(stringOcorrencia.toLowerCase().contains("devolvido")) //$NON-NLS-1$
							{
								tipoOcorrencia = new TipoOcorrencia("Cheque 1ra Devolucao", conn); //$NON-NLS-1$
																								
							}
						}
						novaOcorrencia.show();
						if(!chequeBaixado)
						{
							novaOcorrencia.setTipoOcorrencia(tipoOcorrencia);
							HandlerOcorrencia.store(novaOcorrencia, conn);
						}
					}
					else
					{
						System.out.println("Manual identification for this titles please!"); //$NON-NLS-1$
						for(int idTitulo:idTitulos)
						{
							Titulo cheque = new Titulo(idTitulo, conn);							
							System.out.println("idTitulo: " + cheque.getIdTitulo()); //$NON-NLS-1$
						}
					}
					
					System.out.println(sdfr.format(dataOcorrencia) 
										+ ";" + stringOcorrencia  //$NON-NLS-1$
										+ ";" + numeroCheque  //$NON-NLS-1$
										+ ";" + codigoBanco  //$NON-NLS-1$
										+ ";" + numeroAgencia //$NON-NLS-1$
										+ ";" + numeroConta											 //$NON-NLS-1$
										+ ";" + stringCredito + stringDebito //$NON-NLS-1$
										);
				}
			}
		}
		
	}
	
	public static boolean processFile(File file)
	{
		boolean wellProcessed = false;
		ArrayList<String> lines = FileToLines.readLowerCase(file);
		if(lines.size()>0)
		{
			if(file.getName().toLowerCase().contains("html")) //$NON-NLS-1$
			{
				processFileCSVFromHTML(file);
				wellProcessed = true;
			}
			else if(lines.get(0).toLowerCase().contains("saldos")) //$NON-NLS-1$
			{
				System.out.println("Saldo file: " + file.getName()); //$NON-NLS-1$
				System.out.println(lines.get(0));
			}
		}
		return wellProcessed;
	}		

	public static int countDevolucao(ArrayList<Ocorrencia> ocorrencias)
	{
		int countDevolvidos=0;
		for(Ocorrencia ocorrencia:ocorrencias)
		{		
			if(ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("sem fundos") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("devolvido") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("cheque cancelada") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("emitidos por entidades") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("bloqueado") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("furto ou roubo") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("apresentado por participante") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("fraudado") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("apresentado a participante") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("nao compensavel") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("prescrito") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("emitido por entidade") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("cr quando") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("cr com ausencia") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("valor superior") //$NON-NLS-1$
				|| ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("remessa nula") //$NON-NLS-1$
			  ) 
			{
				System.out.println("  -" + sdfd.format(ocorrencia.getData()) + " " + ocorrencia.getTipoOcorrencia().getDescricao()); //$NON-NLS-1$ //$NON-NLS-2$
				countDevolvidos=countDevolvidos+1;
			}
			else
			{
				System.out.println("  --" + ocorrencia.getTipoOcorrencia().getDescricao());				 //$NON-NLS-1$
			}
		}
		System.out.println("    " + countDevolvidos + " devolucoes"); //$NON-NLS-1$ //$NON-NLS-2$
		return countDevolvidos;
	}
	
	
	public static int countDepositos(ArrayList<Ocorrencia> ocorrencias)
	{
		int countDepositados=0;
		for(Ocorrencia ocorrencia:ocorrencias)
		{		
			if(ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("depositado")) //$NON-NLS-1$
			{
				countDepositados=countDepositados+1;
			}
		}
		return countDepositados;
	}
	
	public static boolean isBaixado(ArrayList<Ocorrencia> ocorrencias)
	{
		boolean isBaixado=false;
		for(Ocorrencia ocorrencia:ocorrencias)
		{		
			if(ocorrencia.getTipoOcorrencia().getDescricao().toLowerCase().contains("baixa")) //$NON-NLS-1$
			{
				System.out.println(" Ocorrencia: " + ocorrencia.getIdOcorrencia()  + " " + ocorrencia.getData() + " " + ocorrencia.getTipoOcorrencia().getDescricao());										 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				isBaixado = true;
				break;
			}
		}
		return isBaixado;
	}

	public SshClient getSshClient()
	{
		return sshClient;
	}

	public void setSshClient(SshClient sshClient)
	{
		this.sshClient = sshClient;
	}
}
