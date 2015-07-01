package mvcapital.bancopaulista.liquidadosebaixados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.StandardCopyOption;
//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.text.html.Option;

import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.mysql.MySQLAccess;
import mvcapital.relatorio.cessao.Titulo;
import mvcapital.utils.OperatingSystem;

import com.mysql.jdbc.Connection;
import static java.nio.file.StandardCopyOption.*;

public class HandlerLiquidadosEBaixados extends Thread
{

	private static String server=""; //$NON-NLS-1$
	private static int port=0;
	private static String userName=""; //$NON-NLS-1$
	private static String password=""; //$NON-NLS-1$
	private static String dbName=""; //$NON-NLS-1$
	public static Connection conn=null;
//	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$	
//	private static String pathProcessar = "W:\\Fundos\\Repositorio\\Processar\\"; //$NON-NLS-1$
	private static String pathProcessar = "W:\\Fundos\\Repositorio\\Estoque\\Preprocessado\\"; //$NON-NLS-1$	
	private static String pathProcessado = "W:\\Fundos\\Repositorio\\Estoque\\Processado\\"; //$NON-NLS-1$
	private static MySQLAccess mysql;
	private File file = null;
	

	public HandlerLiquidadosEBaixados()
	{
//		HandlerLiquidadosEBaixados.readConf();
//		HandlerLiquidadosEBaixados.mysql = new MySQLAccess(server, port, userName, password, dbName);
//		mysql.connect();	
//		HandlerLiquidadosEBaixados.conn = (Connection) mysql.getConn();		
	}
	
	public void run()
	{
		processLiquidadosEBaixadosFile(this.file, conn);
		if(this.file.renameTo(new File(HandlerLiquidadosEBaixados.pathProcessado + this.file.getName())))
		{
			System.out.println(this.file.getName() + " moved to " + HandlerLiquidadosEBaixados.pathProcessado); //$NON-NLS-1$
		}
		else
		{
			System.out.println("Failed to move " + this.file.getName()); //$NON-NLS-1$
			System.out.println("Trying to delete it "); //$NON-NLS-1$
			this.file.deleteOnExit();
		}
	}
	
	public static void main(String[] args)
	{
//		HandlerLiquidadosEBaixados hlb = new HandlerLiquidadosEBaixados();
		HandlerLiquidadosEBaixados.readConf();
		HandlerLiquidadosEBaixados.mysql = new MySQLAccess(server, port, userName, password, dbName);
		mysql.connect();	
		HandlerLiquidadosEBaixados.conn = (Connection) mysql.getConn();				
		HandlerLiquidadosEBaixados.processLiquidadosEBaixados();
		System.out.println("End at: " + Calendar.getInstance().getTime()); //$NON-NLS-1$
	}
	
	public static void processLiquidadosEBaixados()
	{
		ArrayList<File> filesList = new ArrayList<File>();
		File[] filesArray = new File(HandlerLiquidadosEBaixados.pathProcessar).listFiles();

		if(filesArray.length>0)
		{
			for (File file : filesArray) 
			{
			    if (file.isFile() && file.exists()) 
			    {
			    	if(file.getName().toLowerCase().contains("titulo")) //$NON-NLS-1$
			    	{
			    		filesList.add(file);
			    	}
			        System.out.println(file.getName());
			        System.out.println(file.getAbsolutePath());
			    }
			}
			
			for(File file:filesList)
			{
				processLiquidadosEBaixadosFile(file.getAbsolutePath());		
			}
		
		
			for(File file:filesList)
			{			
				File targetFile = new File(pathProcessado + file.getName());
				OperatingSystem.copyFileUsingFileChannels(file, targetFile);
				System.out.println(file.getName() + " copied to " + pathProcessado); //$NON-NLS-1$
				System.out.println("Trying to delete it "); //$NON-NLS-1$
				file.deleteOnExit();
				file.delete();
			}
		}
	}

	@SuppressWarnings("resource")
	public static void processLiquidadosEBaixadosFile(File file, Connection conn)
	{
		BufferedReader reader = null;
		String fileName = file.getAbsolutePath();
		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
		System.out.println("------------------"); //$NON-NLS-1$
		System.out.println("File: " + fileName);				 //$NON-NLS-1$
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
					String[] fields = line.split(";"); //$NON-NLS-1$
					if(!fields[0].equals("FUNDO")) //$NON-NLS-1$
					{
						System.out.println(line);
						lines.add(line);
					}
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		System.out.println(lines.size() + " lines readed"); //$NON-NLS-1$
		
		int iLine=0;
		
		
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		@SuppressWarnings("unused")
		Date dataFundo = null;
		@SuppressWarnings("unused")
		Date dataReferencia = null;
		
		boolean firstLine=true;
		for(String line:lines)
		{
			int fieldsSize=0;
			SimpleDateFormat sdfE = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
			String[] fields = line.replace("&AMP;", "").replace("&amp;", "").split(";"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			fieldsSize = fields.length;
			if(firstLine)
			{
				System.out.println(fieldsSize + " Columns"); //$NON-NLS-1$
			}

			if(iLine==0)
			{
				System.out.println(fields[0]);
				String stringFundo=fields[0];
				
				if(fields[0].equals("LEGO NP FUNDO DE INVESTIMENTO EM DIREITOS CREDITORIOS")) //$NON-NLS-1$
				{
					stringFundo="FUNDO DE INVESTIMENTO EM DIREITOS CREDITORIOS MULTISSETORIAL LEGO - LP"; //$NON-NLS-1$
				}
				fundo = new FundoDeInvestimento(stringFundo, HandlerLiquidadosEBaixados.conn);				
				System.out.println("Fundo: " + fundo.getNome() + " " + Calendar.getInstance().getTime()); //$NON-NLS-1$ //$NON-NLS-2$
			}

			Date dataMovimento = null;
			try
			{
				dataMovimento = sdfE.parse(fields[1]);
			} catch (ParseException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String cadastroCedente = fields[3].replace(".", "").replace("/", "").replace("-", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			Entidade cedente = new Entidade(fields[2], cadastroCedente, HandlerLiquidadosEBaixados.conn);
			if(cedente.getCadastro()==null)
			{
				cedente.setCadastro(cadastroCedente);
				Entidade.updateCadastro(cedente.getIdEntidade(), cadastroCedente, HandlerLiquidadosEBaixados.conn);
			}
			
			String stringOcorrencia = fields[4].toLowerCase()
										.replace("Ç","c") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ç","c") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("á","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Á","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ã","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Ã","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("â","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Â","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("à","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("À","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("é","e") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("É","e") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("í","i") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Í","i") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ó","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Ó","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("õ","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Õ","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ô","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Ô","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ò","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Ò","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ú","u") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Ú","u") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ù","u") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Ù","u")										 //$NON-NLS-1$ //$NON-NLS-2$
										;
					
//			0 FUNDO
//			1 DATA MOVIMENTO
//			2 CEDENTE
//			3 CPF/CNPJ
//			4 OCORRÊNCIA
//			5 SITUACÃO DO RECEBÍVEL
//			6 DOCUMENTO
//			7 SACADO
//			8 CPF/CNPJ
//			9 TAXA
//			10 VALOR DE AQUISICÃO
//			11 VALOR DE VENCIMENTO
//			12 DATA DA AQUISIÇÃO
//			13 DATA DE VENCIMENTO
//			14 VALOR DE PAGO
//			15 AJUSTE
//			16 SEU NUMERO
//			17 NUMERO CORRESPONDENTE
//			18 TIPO RECEBIVEL
			TipoOcorrencia tipoOcorrencia = new TipoOcorrencia(stringOcorrencia, conn);
			@SuppressWarnings("unused")
			String situacaoRecebivel = fields[5];			
			String numeroDocumento = fields[6];
			
			String cadastroSacado = fields[8].replace(".", "").replace("/", "").replace("-", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			Entidade sacado = new Entidade(fields[7], cadastroSacado, conn);
			if(sacado.getCadastro()==null)
			{
				sacado.setCadastro(cadastroSacado);
				Entidade.updateCadastro(sacado.getIdEntidade(), cadastroSacado, conn);
			}				
//			System.out.println(fields[18]);
//			TipoTitulo tipoTitulo = new TipoTitulo(fields[18], conn);
			String seuNumero = fields[16];
			double valorAquisicao = Double.parseDouble(fields[10].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			Date dataVencimento = null;
			double valorPago = 0.0;
			if(fieldsSize==18)
			{
				System.out.println();
				if(fields[13].contains("/")) //$NON-NLS-1$
				{
					System.out.println("New template"); //$NON-NLS-1$
					try {
						dataVencimento = sdfE.parse(fields[13]);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					valorPago = Double.parseDouble(fields[14].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}
				else
				{
					System.out.println("Old template"); //$NON-NLS-1$
					try {
						dataVencimento = sdfE.parse(fields[12]);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					valorPago = Double.parseDouble(fields[13].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}
			}
			else
			{
				try {
					dataVencimento = sdfE.parse(fields[13]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				@SuppressWarnings("unused")
				Date dataAquisicao = null;
				try {
					dataAquisicao = sdfE.parse(fields[12]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				valorPago = Double.parseDouble(fields[14].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}

			
			Titulo titulo = new Titulo (
					fundo,
					cedente,
					sacado,
					seuNumero,
					numeroDocumento,
					dataVencimento,
					valorAquisicao,
					conn);			
			titulo.show();

			if(titulo.getIdTitulo()!=0)
			{
				Ocorrencia ocorrencia = new Ocorrencia();
				
				ocorrencia.setTitulo(titulo);
				ocorrencia.setTipoOcorrencia(tipoOcorrencia);
				ocorrencia.setData(dataMovimento);
				ocorrencia.setCedente(cedente);
				ocorrencia.setValor(valorPago);
				
				ocorrencias.add(ocorrencia);
			}
			iLine=iLine+1;
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Have " + ocorrencias.size() + " ocurrences"); //$NON-NLS-1$ //$NON-NLS-2$
		for(Ocorrencia ocorrencia:ocorrencias)
		{
			HandlerOcorrencia.store(ocorrencia, conn);
		}
		
	}
	
	
	@SuppressWarnings("resource")
	public static void processLiquidadosEBaixadosFile(String fileName)
	{
		BufferedReader reader = null;
		
		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
		System.out.println("------------------"); //$NON-NLS-1$
		System.out.println("File: " + fileName);				 //$NON-NLS-1$
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
					String[] fields = line.split(";"); //$NON-NLS-1$
					if(!fields[0].equals("FUNDO")) //$NON-NLS-1$
					{
						System.out.println(line);
						lines.add(line);
					}
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		System.out.println(lines.size() + " lines readed"); //$NON-NLS-1$
		
		int iLine=0;
		
		
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		@SuppressWarnings("unused")
		Date dataFundo = null;
		@SuppressWarnings("unused")
		Date dataReferencia = null;
		
		boolean firstLine=true;
		for(String line:lines)
		{
			int fieldsSize=0;
			SimpleDateFormat sdfE = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
			String[] fields = line.replace("&AMP;", "").replace("&amp;", "").split(";"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			fieldsSize = fields.length;
			if(firstLine)
			{
				System.out.println(fieldsSize + " Columns"); //$NON-NLS-1$
			}

			if(iLine==0)
			{
				System.out.println(fields[0]);
				String stringFundo=fields[0];
				
				if(fields[0].equals("LEGO NP FUNDO DE INVESTIMENTO EM DIREITOS CREDITORIOS")) //$NON-NLS-1$
				{
					stringFundo="FUNDO DE INVESTIMENTO EM DIREITOS CREDITORIOS MULTISSETORIAL LEGO - LP"; //$NON-NLS-1$
				}
				fundo = new FundoDeInvestimento(stringFundo, HandlerLiquidadosEBaixados.conn);				
				System.out.println("Fundo: " + fundo.getNome() + " " + Calendar.getInstance().getTime()); //$NON-NLS-1$ //$NON-NLS-2$
			}

			Date dataMovimento = null;
			try
			{
				dataMovimento = sdfE.parse(fields[1]);
			} catch (ParseException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String cadastroCedente = fields[3].replace(".", "").replace("/", "").replace("-", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			Entidade cedente = new Entidade(fields[2], cadastroCedente, HandlerLiquidadosEBaixados.conn);
			if(cedente.getCadastro()==null)
			{
				cedente.setCadastro(cadastroCedente);
				Entidade.updateCadastro(cedente.getIdEntidade(), cadastroCedente, HandlerLiquidadosEBaixados.conn);
			}
			
			String stringOcorrencia = fields[4].toLowerCase()
										.replace("Ç","c") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ç","c") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("á","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Á","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ã","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Ã","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("â","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Â","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("à","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("À","a") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("é","e") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("É","e") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("í","i") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Í","i") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ó","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Ó","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("õ","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Õ","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ô","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Ô","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ò","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Ò","o") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ú","u") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Ú","u") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("ù","u") //$NON-NLS-1$ //$NON-NLS-2$
										.replace("Ù","u")										 //$NON-NLS-1$ //$NON-NLS-2$
										;
					
//			0 FUNDO
//			1 DATA MOVIMENTO
//			2 CEDENTE
//			3 CPF/CNPJ
//			4 OCORRÊNCIA
//			5 SITUACÃO DO RECEBÍVEL
//			6 DOCUMENTO
//			7 SACADO
//			8 CPF/CNPJ
//			9 TAXA
//			10 VALOR DE AQUISICÃO
//			11 VALOR DE VENCIMENTO
//			12 DATA DA AQUISIÇÃO
//			13 DATA DE VENCIMENTO
//			14 VALOR DE PAGO
//			15 AJUSTE
//			16 SEU NUMERO
//			17 NUMERO CORRESPONDENTE
//			18 TIPO RECEBIVEL
			TipoOcorrencia tipoOcorrencia = new TipoOcorrencia(stringOcorrencia, conn);
			@SuppressWarnings("unused")
			String situacaoRecebivel = fields[5];			
			String numeroDocumento = fields[6];
			
			String cadastroSacado = fields[8].replace(".", "").replace("/", "").replace("-", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			Entidade sacado = new Entidade(fields[7], cadastroSacado, conn);
			if(sacado.getCadastro()==null)
			{
				sacado.setCadastro(cadastroSacado);
				Entidade.updateCadastro(sacado.getIdEntidade(), cadastroSacado, conn);
			}				
//			System.out.println(fields[18]);
//			TipoTitulo tipoTitulo = new TipoTitulo(fields[18], conn);
			String seuNumero = fields[16];
			double valorAquisicao = Double.parseDouble(fields[10].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			Date dataVencimento = null;
			double valorPago = 0.0;
			if(fieldsSize==18)
			{
				System.out.println();
				if(fields[13].contains("/")) //$NON-NLS-1$
				{
					System.out.println("New template"); //$NON-NLS-1$
					try {
						dataVencimento = sdfE.parse(fields[13]);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					valorPago = Double.parseDouble(fields[14].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}
				else
				{
					System.out.println("Old template"); //$NON-NLS-1$
					try {
						dataVencimento = sdfE.parse(fields[12]);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					valorPago = Double.parseDouble(fields[13].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}
			}
			else
			{
				try {
					dataVencimento = sdfE.parse(fields[13]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				@SuppressWarnings("unused")
				Date dataAquisicao = null;
				try {
					dataAquisicao = sdfE.parse(fields[12]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				valorPago = Double.parseDouble(fields[14].replace(".", "").replace(",", ".")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}

			
			Titulo titulo = new Titulo (
					fundo,
					cedente,
					sacado,
					seuNumero,
					numeroDocumento,
					dataVencimento,
					valorAquisicao,
					conn);			
			titulo.show();

			if(titulo.getIdTitulo()!=0)
			{
				Ocorrencia ocorrencia = new Ocorrencia();
				
				ocorrencia.setTitulo(titulo);
				ocorrencia.setTipoOcorrencia(tipoOcorrencia);
				ocorrencia.setData(dataMovimento);
				ocorrencia.setCedente(cedente);
				ocorrencia.setValor(valorPago);
				
				ocorrencias.add(ocorrencia);
			}
			iLine=iLine+1;
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Have " + ocorrencias.size() + " ocurrences"); //$NON-NLS-1$ //$NON-NLS-2$
		for(Ocorrencia ocorrencia:ocorrencias)
		{
			HandlerOcorrencia.store(ocorrencia, conn);
		}
		
	}
	
    @SuppressWarnings("resource")
	public static void readConf()
	{
		BufferedReader reader = null;
		System.out.println("Reading conf/automata.conf file"); //$NON-NLS-1$
		System.out.println("------------------"); //$NON-NLS-1$
		try 
		{
			reader = new BufferedReader(new FileReader("conf/extratobancopaulista.conf")); //$NON-NLS-1$
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
					            	HandlerLiquidadosEBaixados.server = fields[1];
					                break;
					            case "port": //$NON-NLS-1$
					            	HandlerLiquidadosEBaixados.port = Integer.parseInt(fields[1].replace(" ", "")); //$NON-NLS-1$ //$NON-NLS-2$
					                break;				            	
					            case "userName": //$NON-NLS-1$
					            	HandlerLiquidadosEBaixados.userName = fields[1];
					                break;
					            case "password": //$NON-NLS-1$
					            	HandlerLiquidadosEBaixados.password = fields[1];
					                break;
					            case "dbName": //$NON-NLS-1$
					            	HandlerLiquidadosEBaixados.dbName = fields[1];
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
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getServer()
	{
		return server;
	}

	public static void setServer(String server)
	{
		HandlerLiquidadosEBaixados.server = server;
	}

	public static int getPort()
	{
		return port;
	}

	public static void setPort(int port)
	{
		HandlerLiquidadosEBaixados.port = port;
	}

	public static String getUserName()
	{
		return userName;
	}

	public static void setUserName(String userName)
	{
		HandlerLiquidadosEBaixados.userName = userName;
	}

	public static String getPassword()
	{
		return password;
	}

	public static void setPassword(String password)
	{
		HandlerLiquidadosEBaixados.password = password;
	}

	public static String getDbName()
	{
		return dbName;
	}

	public static void setDbName(String dbName)
	{
		HandlerLiquidadosEBaixados.dbName = dbName;
	}

	public static String getPathProcessar()
	{
		return pathProcessar;
	}

	public static void setPathProcessar(String pathProcessar)
	{
		HandlerLiquidadosEBaixados.pathProcessar = pathProcessar;
	}

	public static String getPathProcessado()
	{
		return pathProcessado;
	}

	public static void setPathProcessado(String pathProcessado)
	{
		HandlerLiquidadosEBaixados.pathProcessado = pathProcessado;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}

	public static Connection getConn()
	{
		return conn;
	}

	public static void setConn(Connection conn)
	{
		HandlerLiquidadosEBaixados.conn = conn;
	}

	public static MySQLAccess getMysql()
	{
		return mysql;
	}

	public static void setMysql(MySQLAccess mysql)
	{
		HandlerLiquidadosEBaixados.mysql = mysql;
	}
	
}
