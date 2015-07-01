package mvcapital.bancopaulista.estoque;

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

import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.indicadores.HandlerIndicadores;
import mvcapital.mysql.MySQLAccess;
import mvcapital.relatorio.cessao.HandlerTitulo;
import mvcapital.relatorio.cessao.Titulo;
import mvcapital.utils.CDIOver;
import mvcapital.utils.FileToLines;
import mvcapital.utils.OperatingSystem;
import mvcapital.utils.WorkingDays;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class HandlerEstoque extends Thread 
{
	public static Connection conn=null;

	private String server = "localhost"; //$NON-NLS-1$
	private int port = 3306;		
	private String userName = "root";  //$NON-NLS-1$
	private String password = "root"; //$NON-NLS-1$
	private String dbName = "root"; //$NON-NLS-1$
	private MySQLAccess mysql = null;	
//	private static String pathProcessar = "W:\\Fundos\\Repositorio\\Processar\\"; //$NON-NLS-1$
	private static String pathProcessar = "W:\\Fundos\\Repositorio\\Estoque\\Preprocessado\\"; //$NON-NLS-1$	
	private static String pathProcessado = "W:\\Fundos\\Repositorio\\Estoque\\Processado\\"; //$NON-NLS-1$
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	private File file=null;
	private static int cores = Runtime.getRuntime().availableProcessors();
	private boolean isComplete = false;
	
	public HandlerEstoque() 
	{
	}

	public static void main(String[] args)
	{
		
		HandlerEstoque le = new HandlerEstoque();
		le.readConf();
		le.mysql = new MySQLAccess(le.server, le.port, le.userName, le.password, le.dbName);
		le.mysql.connect();	
		HandlerEstoque.conn = (Connection) le.mysql.getConn();
		
		System.out.println("Beginning Estoques at: " + Calendar.getInstance().getTime()); //$NON-NLS-1$
		HandlerEstoque.processEstoques();
		System.out.println("Ending Estoques at: " + Calendar.getInstance().getTime()); //$NON-NLS-1$
	}

	public void run()
	{
		this.setComplete(processEstoqueFileLines(this.file, conn));
	}
	
	public static void processEstoques()
	{
		File[] filesArray = new File(pathProcessar).listFiles();

		int iFiles=0;
		System.out.println("filesArray: " + filesArray.length); //$NON-NLS-1$
		while(filesArray.length>0)
		{
			if(filesArray.length>0)
			{

				
				File file = null;
				for(int i=0;i<filesArray.length;i++)
				{
				
					if(filesArray[i].getName().toLowerCase().contains("estoque")) //$NON-NLS-1$
					{
						file = filesArray[i];
						break;
					}
				}
				
				if(file != null)
				{							
					System.out.print(iFiles + " of " + filesArray.length); //$NON-NLS-1$
					iFiles++;
					HandlerEstoque.setConn(conn);
				    if (file.isFile() && file.exists()) 
				    {
				    	{
				    		HandlerEstoque he = new HandlerEstoque();
				    		he.setFile(file);
				    		he.start();
				    	}
//						if(file.renameTo(new File(pathProcessado + file.getName())))
//						{
//							System.out.println(file.getName() + " moved to " + pathProcessado); //$NON-NLS-1$
//						}
//						else
//						{
//							System.out.println("Failed to move " + file.getName()); //$NON-NLS-1$
//							System.out.println("Trying to delete it "); //$NON-NLS-1$
//							file.deleteOnExit();
//						}
				    }
				    filesArray = null;
				    System.out.println("Before GC " + Calendar.getInstance().getTime() + " Mem: " + Runtime.getRuntime().freeMemory()); //$NON-NLS-1$ //$NON-NLS-2$
				    try
					{
						Thread.sleep(2000);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					} 
				    System.gc();
				    
				    System.out.println("After GC " + Calendar.getInstance().getTime() + " Mem: " + Runtime.getRuntime().freeMemory()); //$NON-NLS-1$ //$NON-NLS-2$
				    filesArray = new File(pathProcessar).listFiles();		    
				}
				else
				{
					filesArray = null;
					System.out.println("No estoque file!"); //$NON-NLS-1$
					break;
				}
			}
		}
	}

	public static ArrayList<EstoqueLine> storedEstoqueLines(ArrayList<EstoqueLine> estoqueLines)
	{
		ArrayList<EstoqueLine> storedEstoqueLines = new ArrayList<EstoqueLine>();
		for(EstoqueLine estoqueLine:estoqueLines)
		{
			if(estoqueLine.isStored())
			{
				storedEstoqueLines.add(estoqueLine);
			}
		}
		return storedEstoqueLines;
	}

	public static ArrayList<EstoqueLine> nonStoredEstoqueLines(ArrayList<EstoqueLine> estoqueLines)
	{
		ArrayList<EstoqueLine> nonStoredEstoqueLines = new ArrayList<EstoqueLine>();
		for(EstoqueLine estoqueLine:estoqueLines)
		{
			if(estoqueLine.getTitulo().getIdTitulo()==0)
			{
				estoqueLine.setStored(false);
				nonStoredEstoqueLines.add(estoqueLine);
			}
			else
			{
				estoqueLine.setStored(true);
			}
		}
		return nonStoredEstoqueLines;
	}
	
	public static void storeTitulosFromEstoqueLines(ArrayList<EstoqueLine> estoqueLines, Connection conn)
	{
		ArrayList<EstoqueLine> estoqueLinesToStoreTituloTable = nonStoredEstoqueLines(estoqueLines);
		if(estoqueLinesToStoreTituloTable.size()>0)
		{
			for(EstoqueLine estoqueLine:estoqueLinesToStoreTituloTable)
			{
				HandlerTitulo.store(estoqueLine.getTitulo(), conn);
//				estoqueLines.get(iEstoqueLine).getTitulo().setIdTitulo(HandlerTitulo.store(estoqueLine.getTitulo(), conn));
				estoqueLine.setStored(true);				
			}
		}
	}
		
	public static boolean processEstoqueFileLines(File file, Connection conn)
	{
		ArrayList<String> lines = FileToLines.readLowerCase(file);
		
		int iLine=0;
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		System.out.println("Lines " + lines.size()); //$NON-NLS-1$
		HandlerLineEstoque.setConn(conn);
		SimpleDateFormat sdfE = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
		EstoqueLine.setConn(conn);
		ArrayList<EstoqueLine> estoqueLines = new ArrayList<EstoqueLine>();
		ArrayList<Thread> estoqueLinesThread = new ArrayList<Thread>();
		for(String line:lines)
		{
//			System.out.println(iLine + " " + line);
			if(line.toLowerCase().contains("nome_fundo")) //$NON-NLS-1$
			{
//				System.out.println(file.getName() + " first line begins with nome_fundo");
				continue;
			}
			

			if(iLine==0)
			{
				String[] fields = line.replace("&AMP;", "").replace("&amp;", "").split(";"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				System.out.println(fields[0]);
				String stringFundo=fields[0];
				
				if(fields[0].equals("LEGO NP FUNDO DE INVESTIMENTO EM DIREITOS CREDITORIOS")) //$NON-NLS-1$
				{
					stringFundo="FUNDO DE INVESTIMENTO EM DIREITOS CREDITORIOS MULTISSETORIAL LEGO - LP"; //$NON-NLS-1$
				}
//				else if(fields[0].equals("LEGO NP FUNDO DE INVESTIMENTO EM DIREITOS CREDITORIOS"))
//				{
//					
//				}
				fundo = new FundoDeInvestimento(stringFundo, HandlerEstoque.conn);				
				try {
					sdfE.parse(fields[2]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				System.out.println("Fundo: " + fundo.getNome() + " " + Calendar.getInstance().getTime()); //$NON-NLS-1$ //$NON-NLS-2$				
			}
			
			EstoqueLine estoqueLine = new EstoqueLine(fundo, line, iLine);
			estoqueLine.start();
			estoqueLines.add(estoqueLine);
			estoqueLinesThread.add(estoqueLine);
			
			if(estoqueLines.size()>=cores*cores*cores)
			{
				while(!allFinished(estoqueLinesThread))
				{
					try
					{
						Thread.sleep(20);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				estoqueLinesThread = new ArrayList<Thread>();
			}
			iLine=iLine+1;
		}
		
		while(!allFinished(estoqueLinesThread))
		{
			try
			{
				System.out.println("Still Estoque threads processing " + Calendar.getInstance().getTime()); //$NON-NLS-1$
				Thread.sleep(100);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("File: " + file.getName() + " " + "complete!"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$					
		storeTitulosFromEstoqueLines(estoqueLines, conn);
		
		storeTituloEstoqueFromEstoqueLines(estoqueLines);
		storeTituloPDDFromEstoqueLines(estoqueLines);
		storeTituloValorPresenteFromEstoqueLines(estoqueLines);
//		storeTituloPrazoFromEstoqueLines(estoqueLines);
//		storeTituloTaxaSobreCDIFromEstoqueLines(estoqueLines);

		if(file.renameTo(new File(pathProcessado + file.getName())))
		{
			System.out.println(file.getName() + " moved to " + pathProcessado); //$NON-NLS-1$
		}
		else
		{
			System.out.println("Failed to move " + file.getName()); //$NON-NLS-1$
			System.out.println("Trying to delete it "); //$NON-NLS-1$
			file.deleteOnExit();
		}		
		return true;
	}
	
	
	
	@SuppressWarnings("resource")
	public static void processEstoqueFile(String fileName, Connection conn)
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
					if(!line.startsWith("NOME_FUNDO")) //$NON-NLS-1$
					{
//						System.out.println(line);
						lines.add(line);
					}
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		try
		{
			reader.close();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		int iLine=0;
		
		
		FundoDeInvestimento fundo = new FundoDeInvestimento();
		System.out.println("Lines " + lines.size()); //$NON-NLS-1$
		HandlerLineEstoque.setConn(conn);
		SimpleDateFormat sdfE = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
		
		ArrayList<Thread> hles = new ArrayList<Thread>();
		for(String line:lines)
		{
			if(iLine==0)
			{
				String[] fields = line.replace("&AMP;", "").replace("&amp;", "").split(";"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				System.out.println(fields[0]);
				String stringFundo=fields[0];
				
				if(fields[0].equals("LEGO NP FUNDO DE INVESTIMENTO EM DIREITOS CREDITORIOS")) //$NON-NLS-1$
				{
					stringFundo="FUNDO DE INVESTIMENTO EM DIREITOS CREDITORIOS MULTISSETORIAL LEGO - LP"; //$NON-NLS-1$
				}
				fundo = new FundoDeInvestimento(stringFundo, HandlerEstoque.conn);				
				try {
					sdfE.parse(fields[2]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				System.out.println("Fundo: " + fundo.getNome() + " " + Calendar.getInstance().getTime()); //$NON-NLS-1$ //$NON-NLS-2$				
			}
			HandlerLineEstoque hle = new HandlerLineEstoque();
			hle.setFundo(fundo);
			hle.setLine(line);
			hle.setiLine(iLine);
			hles.add(hle);
			hle.start();
			if(iLine==0)
			{
				fundo=hle.getFundo();
			}
			
			System.out.println("Line " + iLine + " from " + lines.size()  + " " + Calendar.getInstance().getTime()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			
			if(hles.size()>=cores*cores)
			{
				while(!allFinished(hles))
				{
					try
					{
//						System.out.println("Still Estoque threads processing " + Calendar.getInstance().getTime());
						Thread.sleep(100);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				hles = new ArrayList<Thread>();
			}
			iLine=iLine+1;
		}
		
//		try {
//			reader.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
		lines=null;
//		
//		while(!allFinished(hles))
//		{
//			try
//			{
//				System.out.println("Still Estoque threads processing " + Calendar.getInstance().getTime()); //$NON-NLS-1$
//				Thread.sleep(10000);
//			} catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
//		}
		
//		System.out.println("Have " + titulos.size() + " titulos");
//		for(Titulo titulo:titulos)
//		{			
//			if(HandlerTitulo.isStored(titulo, conn))
//			{
////				System.out.println("Existe!");				
//			}
//			else
//			{
//				HandlerTitulo.store(titulo, conn);
//			}			
//			storeTituloEstoque(titulo, dataReferencia,conn);			
//		}
	}

	public static boolean allFinished(ArrayList<Thread> hles)
	{
		for(Thread hle:hles)
		{
			if(hle.isAlive())
			{
				return false;
			}
		}
		return true;
	}
	
	public static void storeTaxaSobreCDI(Titulo titulo, Date dataEstoque, Connection conn)
	{
//		# idPDD, idTitulo, DataEstoque, Valor, Faixa, DataAtualizacao
		Statement stmt=null;
		int idTaxaSobreCDI=0;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT idTaxaSobreCDI FROM TaxaSobreCDI " //$NON-NLS-1$
	 				+ " WHERE " //$NON-NLS-1$
	 				+ " " + " DataEstoque=" + "'" + sdfd.format(dataEstoque) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 				+ " AND " + " idTitulo=" + titulo.getIdTitulo() //$NON-NLS-1$ //$NON-NLS-2$
	 				;
//		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idTaxaSobreCDI = rs.getInt("idTaxaSobreCDI"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		double valor = 0.0;
		if(idTaxaSobreCDI!=0)
		{
//			System.out.println("PDD entry exist!");
		}
		else
		{
			String sql = "INSERT INTO `TaxaSobreCDI` (`DataEstoque`,`idTitulo`,`valor`) " //$NON-NLS-1$
					+ " VALUES (" //$NON-NLS-1$
					+ "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + titulo.getIdTitulo() //$NON-NLS-1$
					+ "," + valor //$NON-NLS-1$
					+ ")";			 //$NON-NLS-1$
//			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	public static void storeTaxaSobreCDI(int idTitulo, Date dataEstoque, double valor, Connection conn)
	{
//		# idPDD, idTitulo, DataEstoque, Valor, Faixa, DataAtualizacao
		Statement stmt=null;
		int idTaxaSobreCDI=0;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT idTaxaSobreCDI FROM TaxaSobreCDI " //$NON-NLS-1$
	 				+ " WHERE " //$NON-NLS-1$
	 				+ " " + " DataEstoque=" + "'" + sdfd.format(dataEstoque) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 				+ " AND " + " idTitulo=" + idTitulo //$NON-NLS-1$ //$NON-NLS-2$
	 				;
//		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idTaxaSobreCDI = rs.getInt("idTaxaSobreCDI"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		if(idTaxaSobreCDI!=0)
		{
//			System.out.println("PDD entry exist!");
			String sql = "UPDATE `taxasobrecdi`" //$NON-NLS-1$
					+ " SET `valor` =  " + valor //$NON-NLS-1$
					+ " WHERE `idtaxaSobreCDI` = " + idTaxaSobreCDI; //$NON-NLS-1$
//			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			String sql = "INSERT INTO `TaxaSobreCDI` (`DataEstoque`,`idTitulo`,`valor`) " //$NON-NLS-1$
					+ " VALUES (" //$NON-NLS-1$
					+ "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + idTitulo //$NON-NLS-1$
					+ "," + valor //$NON-NLS-1$
					+ ")";			 //$NON-NLS-1$
//			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	public static void storeUpdateTaxaSobreCDI(int idTitulo, Date dataEstoque, double valor, Connection conn)
	{
//		# idPDD, idTitulo, DataEstoque, Valor, Faixa, DataAtualizacao
		Statement stmt=null;
		int idTaxaSobreCDI=0;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT idTaxaSobreCDI FROM TaxaSobreCDI " //$NON-NLS-1$
	 				+ " WHERE " //$NON-NLS-1$
	 				+ " " + " DataEstoque=" + "'" + sdfd.format(dataEstoque) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 				+ " AND " + " idTitulo=" + idTitulo //$NON-NLS-1$ //$NON-NLS-2$
	 				;
//		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idTaxaSobreCDI = rs.getInt("idTaxaSobreCDI"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		if(idTaxaSobreCDI!=0)
		{
			String sql = "UPDATE `taxasobrecdi`" //$NON-NLS-1$
					+ " SET `valor` =  " + valor //$NON-NLS-1$
					+ " WHERE `idtaxaSobreCDI` = " + idTaxaSobreCDI; //$NON-NLS-1$
//			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			String sql = "INSERT INTO `TaxaSobreCDI` (`DataEstoque`,`idTitulo`,`valor`) " //$NON-NLS-1$
					+ " VALUES (" //$NON-NLS-1$
					+ "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + idTitulo //$NON-NLS-1$
					+ "," + valor //$NON-NLS-1$
					+ ")";			 //$NON-NLS-1$
//			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public static double taxaCDI(Date dataEstoque, Connection conn)
	{

		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		String query = "SELECT * FROM cdiover where" //$NON-NLS-1$
					+ " data = " + "'" + sdfd.format(dataEstoque) + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
//		System.out.println(query);
		ResultSet rs = null;
		double taxaDIOver=0.0;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				taxaDIOver=rs.getDouble("taxa"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		taxaDIOver = Math.pow(taxaDIOver/100.00+1,(1.0/252.0))-1.0;
		
		return taxaDIOver;
	}	

	public static void storePDD(Titulo titulo, Date dataEstoque, double valor, String faixaPDD, Connection conn)
	{
//		# idPDD, idTitulo, DataEstoque, Valor, Faixa, DataAtualizacao
		Statement stmt=null;
		int idPDD=0;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT idPdd FROM pdd " //$NON-NLS-1$
	 				+ " WHERE " //$NON-NLS-1$
	 				+ " " + " DataEstoque=" + "'" + sdfd.format(dataEstoque) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 				+ " AND " + " idTitulo=" + titulo.getIdTitulo() //$NON-NLS-1$ //$NON-NLS-2$
	 				;
//		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idPDD = rs.getInt("idPdd"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		if(idPDD!=0)
		{
//			System.out.println("PDD entry exist!");
		}
		else
		{
			String sql = "INSERT INTO `pdd` (`DataEstoque`,`idTitulo`,`valor`, `faixa`) " //$NON-NLS-1$
					+ " VALUES (" //$NON-NLS-1$
					+ "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + titulo.getIdTitulo() //$NON-NLS-1$
					+ "," + valor //$NON-NLS-1$
					+ "," + "'" + faixaPDD + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ ")";			 //$NON-NLS-1$
//			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void storeValorPresente(Titulo titulo, Date dataEstoque, double valorPresente, Connection conn)
	{
		Statement stmt=null;
		int idValorPresente=0;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT idValorPresente FROM valorPresente " //$NON-NLS-1$
	 				+ " WHERE " //$NON-NLS-1$
	 				+ " " + " DataEstoque=" + "'" + sdfd.format(dataEstoque) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 				+ " AND " + " idTitulo=" + titulo.getIdTitulo() //$NON-NLS-1$ //$NON-NLS-2$
	 				;
//		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idValorPresente = rs.getInt("idValorPresente"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		if(idValorPresente!=0)
		{
//			System.out.println("ValorPresente entry exist!");
		}
		else
		{
//			System.out.println("Storing new Estoque entry!");
			String sql = "INSERT INTO `valorPresente` (`DataEstoque`,`idTitulo`,`valor`) " //$NON-NLS-1$
					+ " VALUES (" //$NON-NLS-1$
					+ "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + titulo.getIdTitulo() //$NON-NLS-1$
					+ "," + valorPresente //$NON-NLS-1$
					+ ")";			 //$NON-NLS-1$
//			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static boolean alreadyStoredTaxaSobreCDIFromEstoqueLine(EstoqueLine estoqueLine)
	{
		Statement stmt=null;
		int idTaxaSobreCDI=0;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT idTaxaSobreCDI FROM TaxaSobreCDI " //$NON-NLS-1$
	 				+ " WHERE " //$NON-NLS-1$
	 				+ " " + " DataEstoque=" + "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 				+ " AND " + " idTitulo=" + estoqueLine.getTitulo().getIdTitulo() //$NON-NLS-1$ //$NON-NLS-2$
	 				;
//		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idTaxaSobreCDI = rs.getInt("idTaxaSobreCDI"); //$NON-NLS-1$
				System.out.println("idTaxaSobreCDI: " + idTaxaSobreCDI); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				

		if(idTaxaSobreCDI!=0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public static void storeTituloTaxaSobreCDIFromEstoqueLines(ArrayList<EstoqueLine> estoqueLines)
	{
		ArrayList<EstoqueLine> estoqueLinesToTaxaSobreCDITable = new ArrayList<EstoqueLine>();
		for(EstoqueLine estoqueLine:estoqueLines)
		{
			if(!alreadyStoredTaxaSobreCDIFromEstoqueLine(estoqueLine))
			{
				estoqueLinesToTaxaSobreCDITable.add(estoqueLine);
			}
		}
		Statement stmt=null;

		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		INSERT INTO example
//		  (example_id, name, value, other_value)
//		VALUES
//		  (100, 'Name 1', 'Value 1', 'Other 1'),
//		  (101, 'Name 2', 'Value 2', 'Other 2'),
//		  (102, 'Name 3', 'Value 3', 'Other 3'),
//		  (103, 'Name 4', 'Value 4', 'Other 4');		

		if(estoqueLinesToTaxaSobreCDITable.size() == 0)
		{
			System.out.println("No need to store new TaxaSobreCDI entries!"); //$NON-NLS-1$
		}
		else
		{			
			System.out.println("Storing new TaxaSobreCDI entry!"); //$NON-NLS-1$	
			String sql =  "INSERT INTO `TaxaSobreCDI` (`DataEstoque`,`idTitulo`,`valor`) " //$NON-NLS-1$
					+ " VALUES "; //$NON-NLS-1$
			int iEstoqueLine=0;
			double taxaCDIOver = 0.0;
			for(EstoqueLine estoqueLine:estoqueLinesToTaxaSobreCDITable)
			{
				if(taxaCDIOver==0.0)
				{
					taxaCDIOver = HandlerIndicadores.taxaCDI(estoqueLine.getDataReferencia(), conn);
				}

				System.out.println("Data:" + estoqueLine.getDataReferencia() + " CDI: " + taxaCDIOver*100);
				estoqueLine.setTaxaSobreCDI(CDIOver.calculateValue(estoqueLine.getTitulo().getIdTitulo(), estoqueLine.getDataReferencia(), taxaCDIOver, conn));
				if(iEstoqueLine!=0)
				{
					sql = sql
						+ ",\n" //$NON-NLS-1$
						+ "(" //$NON-NLS-1$
						+ "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'" //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + estoqueLine.getTitulo().getIdTitulo() //$NON-NLS-1$
						+ "," + estoqueLine.getTaxaSobreCDI() //$NON-NLS-1$							
						+ ")";			 //$NON-NLS-1$
				}
				else
				{
					sql = sql
						+ "(" //$NON-NLS-1$
						+ "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'" //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + estoqueLine.getTitulo().getIdTitulo() //$NON-NLS-1$
						+ "," + estoqueLine.getTaxaSobreCDI() //$NON-NLS-1$							
						+ ")";			 //$NON-NLS-1$
				}
				iEstoqueLine++;
				
				if(iEstoqueLine!=0)
				{
					System.out.println(sql);
//					try {
//						stmt.executeUpdate(sql);
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
				}
			}
		}		
		
	}	
	
	public static boolean alreadyStoredPrazoFromEstoqueLine(EstoqueLine estoqueLine)
	{
		int idTitulo=estoqueLine.getTitulo().getIdTitulo();
		if(existPrazos(idTitulo, estoqueLine.getDataReferencia()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void storeTituloPrazoFromEstoqueLines(ArrayList<EstoqueLine> estoqueLines)
	{
		ArrayList<EstoqueLine> estoqueLinesToPrazoTable = new ArrayList<EstoqueLine>();
		for(EstoqueLine estoqueLine:estoqueLines)
		{
			if(!alreadyStoredPrazoFromEstoqueLine(estoqueLine))
			{
				estoqueLinesToPrazoTable.add(estoqueLine);
			}
		}

		Statement stmt=null;

		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		INSERT INTO example
//		  (example_id, name, value, other_value)
//		VALUES
//		  (100, 'Name 1', 'Value 1', 'Other 1'),
//		  (101, 'Name 2', 'Value 2', 'Other 2'),
//		  (102, 'Name 3', 'Value 3', 'Other 3'),
//		  (103, 'Name 4', 'Value 4', 'Other 4');		

		if(estoqueLinesToPrazoTable.size() == 0)
		{
			System.out.println("No need to store new PDD entries!"); //$NON-NLS-1$
		}
		else
		{			
			System.out.println("Storing new PDD entry!"); //$NON-NLS-1$	
			String sql =  "INSERT INTO prazo (`idTitulo`,`DataEstoque`,`ValorCorrido`,`ValorUtil`)" //$NON-NLS-1$
					+ " VALUES "; //$NON-NLS-1$
			int iEstoqueLine=0;
			for(EstoqueLine estoqueLine:estoqueLinesToPrazoTable)
			{
				calculatePrazos(estoqueLine, conn);
				if(estoqueLine.getPrazoCorrido()!=0)
				{
					if(iEstoqueLine!=0)
					{
						sql = sql
							+ ",\n" //$NON-NLS-1$
							+ "(" //$NON-NLS-1$
							+ estoqueLine.getTitulo().getIdTitulo() 
							+ "," + "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							+ "," + estoqueLine.getPrazoCorrido() //$NON-NLS-1$
							+ "," + estoqueLine.getPrazoUtil() //$NON-NLS-1$							
							+ ")";			 //$NON-NLS-1$
					}
					else
					{
						sql = sql
							+ "(" //$NON-NLS-1$
							+ estoqueLine.getTitulo().getIdTitulo() 
							+ "," + "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							+ "," + estoqueLine.getPrazoCorrido() //$NON-NLS-1$
							+ "," + estoqueLine.getPrazoUtil() //$NON-NLS-1$								
							+ ")";			 //$NON-NLS-1$
					}
					iEstoqueLine++;
				}
			}
			if(iEstoqueLine!=0)
			{
				System.out.println(sql);
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	public static boolean alreadyStoredValorPresenteFromEstoqueLine(EstoqueLine estoqueLine)
	{
		Statement stmt=null;
		int idValorPresente=0;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT idValorPresente FROM valorPresente " //$NON-NLS-1$
	 				+ " WHERE " //$NON-NLS-1$
	 				+ " " + " DataEstoque=" + "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 				+ " AND " + " idTitulo=" + estoqueLine.getTitulo().getIdTitulo() //$NON-NLS-1$ //$NON-NLS-2$
	 				;
//		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idValorPresente = rs.getInt("idValorPresente"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		if(idValorPresente!=0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}	
	
	public static void storeTituloValorPresenteFromEstoqueLines(ArrayList<EstoqueLine> estoqueLines)
	{
		ArrayList<EstoqueLine> estoqueLinesToValorPresenteTable = new ArrayList<EstoqueLine>();
		
		for(EstoqueLine estoqueLine:estoqueLines)
		{
			if(!alreadyStoredValorPresenteFromEstoqueLine(estoqueLine))
			{
				estoqueLinesToValorPresenteTable.add(estoqueLine);
			}
		}

		Statement stmt=null;

		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		INSERT INTO example
//		  (example_id, name, value, other_value)
//		VALUES
//		  (100, 'Name 1', 'Value 1', 'Other 1'),
//		  (101, 'Name 2', 'Value 2', 'Other 2'),
//		  (102, 'Name 3', 'Value 3', 'Other 3'),
//		  (103, 'Name 4', 'Value 4', 'Other 4');		
		


		if(estoqueLinesToValorPresenteTable.size() == 0)
		{
			System.out.println("No need to store new PDD entries!"); //$NON-NLS-1$
		}
		else
		{
			System.out.println("Storing new PDD entry!"); //$NON-NLS-1$	
			String sql =  "INSERT INTO `valorPresente` (`DataEstoque`,`idTitulo`,`valor`) " //$NON-NLS-1$
					+ " VALUES "; //$NON-NLS-1$
			int iEstoqueLine=0;
			for(EstoqueLine estoqueLine:estoqueLinesToValorPresenteTable)
			{
				if(iEstoqueLine!=0)
				{
					sql = sql
						+ ",\n" //$NON-NLS-1$
						+ "(" //$NON-NLS-1$
						+ "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'" //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + estoqueLine.getTitulo().getIdTitulo() //$NON-NLS-1$
						+ "," + estoqueLine.getValorPresente() //$NON-NLS-1$				
						+ ")";			 //$NON-NLS-1$
				}
				else
				{
					sql = sql
							+ "(" //$NON-NLS-1$
							+ "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'" //$NON-NLS-1$ //$NON-NLS-2$
							+ "," + estoqueLine.getTitulo().getIdTitulo() //$NON-NLS-1$
							+ "," + estoqueLine.getValorPresente() //$NON-NLS-1$							
							+ ")";			 //$NON-NLS-1$
				}
				iEstoqueLine++;
			}
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean alreadyStoredPDDFromEstoqueLine(EstoqueLine estoqueLine)
	{
		Statement stmt=null;
		int idPDD=0;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT idPdd FROM pdd " //$NON-NLS-1$
	 				+ " WHERE " //$NON-NLS-1$
	 				+ " " + " DataEstoque=" + "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 				+ " AND " + " idTitulo=" + estoqueLine.getTitulo().getIdTitulo() //$NON-NLS-1$ //$NON-NLS-2$
	 				;
//		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idPDD = rs.getInt("idPdd"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		if(idPDD!=0)
		{
			return true;
		}		
		else
		{
			return false;
		}
		
	}
	
	public static void storeTituloPDDFromEstoqueLines(ArrayList<EstoqueLine> estoqueLines)
	{
		ArrayList<EstoqueLine> estoqueLinesToPDDTable = new ArrayList<EstoqueLine>();
		
		for(EstoqueLine estoqueLine:estoqueLines)
		{
			if(!alreadyStoredPDDFromEstoqueLine(estoqueLine))
			{
				estoqueLinesToPDDTable.add(estoqueLine);
			}
		}

		Statement stmt=null;

		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		INSERT INTO example
//		  (example_id, name, value, other_value)
//		VALUES
//		  (100, 'Name 1', 'Value 1', 'Other 1'),
//		  (101, 'Name 2', 'Value 2', 'Other 2'),
//		  (102, 'Name 3', 'Value 3', 'Other 3'),
//		  (103, 'Name 4', 'Value 4', 'Other 4');		
		


		if(estoqueLinesToPDDTable.size() == 0)
		{
			System.out.println("No need to store new PDD entries!"); //$NON-NLS-1$
		}
		else
		{
			System.out.println("Storing new PDD entry!"); //$NON-NLS-1$	
			String sql =  "INSERT INTO `pdd` (`DataEstoque`,`idTitulo`,`valor`, `faixa`) " //$NON-NLS-1$
					+ " VALUES "; //$NON-NLS-1$
			int iEstoqueLine=0;
			for(EstoqueLine estoqueLine:estoqueLinesToPDDTable)
			{
				if(iEstoqueLine!=0)
				{
					sql = sql
						+ ",\n" //$NON-NLS-1$
						+ "(" //$NON-NLS-1$
						+ "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'" //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + estoqueLine.getTitulo().getIdTitulo() //$NON-NLS-1$
						+ "," + estoqueLine.getValorPDD() //$NON-NLS-1$
						+ "," + "'" + estoqueLine.getFaixaPDD() + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$						
						+ ")";			 //$NON-NLS-1$
				}
				else
				{
					sql = sql
							+ "(" //$NON-NLS-1$
							+ "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'" //$NON-NLS-1$ //$NON-NLS-2$
							+ "," + estoqueLine.getTitulo().getIdTitulo() //$NON-NLS-1$
							+ "," + estoqueLine.getValorPDD() //$NON-NLS-1$
							+ "," + "'" + estoqueLine.getFaixaPDD() + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$						
							+ ")";			 //$NON-NLS-1$
				}
				iEstoqueLine++;
			}
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static boolean alreadyStoredEstoqueFromEstoqueLine(EstoqueLine estoqueLine)
	{
		Statement stmt=null;
		Date dataReferencia = estoqueLine.getDataReferencia();
		Titulo titulo = estoqueLine.getTitulo();

		int idEstoque=0;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT idEstoque FROM Estoque " //$NON-NLS-1$
	 				+ " WHERE " //$NON-NLS-1$
	 				+ " " + " Data=" + "'" + sdfd.format(dataReferencia) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 				+ " AND " + " idTitulo=" + titulo.getIdTitulo() //$NON-NLS-1$ //$NON-NLS-2$
	 				+ " AND " + " idFundo=" + titulo.getFundo().getIdFundo() //$NON-NLS-1$ //$NON-NLS-2$
	 				;
//		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idEstoque = rs.getInt("idEstoque"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		if(idEstoque!=0)
		{
			return true;
		}		
		else
		{
			return false;
		}
	}
	
	public static void storeTituloEstoqueFromEstoqueLines(ArrayList<EstoqueLine> estoqueLines)
	{
		ArrayList<EstoqueLine> estoqueLinesToEstoqueTable = new ArrayList<EstoqueLine>();

		for(EstoqueLine estoqueLine:estoqueLines)
		{
			if(!alreadyStoredEstoqueFromEstoqueLine(estoqueLine))
			{
				estoqueLinesToEstoqueTable.add(estoqueLine);
			}
		}
		
		
		Statement stmt=null;

		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		INSERT INTO example
//		  (example_id, name, value, other_value)
//		VALUES
//		  (100, 'Name 1', 'Value 1', 'Other 1'),
//		  (101, 'Name 2', 'Value 2', 'Other 2'),
//		  (102, 'Name 3', 'Value 3', 'Other 3'),
//		  (103, 'Name 4', 'Value 4', 'Other 4');		
		


		if(estoqueLinesToEstoqueTable.size() == 0)
		{
			System.out.println("No need to store new Estoque entries!"); //$NON-NLS-1$
		}
		else
		{
			System.out.println("Storing new Estoque entry!"); //$NON-NLS-1$
		
			String sql = "INSERT INTO `estoque` (`Data`,`idTitulo`,`idFundo`) " //$NON-NLS-1$
					+ " VALUES "; //$NON-NLS-1$
			int iEstoqueLine=0;
			for(EstoqueLine estoqueLine:estoqueLinesToEstoqueTable)
			{
				if(iEstoqueLine!=0)
				{
					sql = sql
						+ ",\n" //$NON-NLS-1$
						+ "(" //$NON-NLS-1$
						+ "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'" //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + estoqueLine.getTitulo().getIdTitulo() //$NON-NLS-1$
						+ "," + estoqueLine.getFundo().getIdFundo() //$NON-NLS-1$
						+ ")";			 //$NON-NLS-1$
				}
				else
				{
					sql = sql
							+ "(" //$NON-NLS-1$
							+ "'" + sdfd.format(estoqueLine.getDataReferencia()) + "'" //$NON-NLS-1$ //$NON-NLS-2$
							+ "," + estoqueLine.getTitulo().getIdTitulo() //$NON-NLS-1$
							+ "," + estoqueLine.getFundo().getIdFundo() //$NON-NLS-1$
							+ ")";			 //$NON-NLS-1$
				}
				iEstoqueLine++;
			}
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void storeTituloEstoque(Titulo titulo, Date dataReferencia, Connection conn)
	{
//		idEstoque, 
//		Data, 
//		idTitulo, 
//		idFundo, 
//		DataAtualizacao
		Statement stmt=null;
		int idEstoque=0;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT idEstoque,Data,idTitulo,idFundo FROM Estoque " //$NON-NLS-1$
	 				+ " WHERE " //$NON-NLS-1$
	 				+ " " + " Data=" + "'" + sdfd.format(dataReferencia) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 				+ " AND " + " idTitulo=" + titulo.getIdTitulo() //$NON-NLS-1$ //$NON-NLS-2$
	 				+ " AND " + " idFundo=" + titulo.getFundo().getIdFundo() //$NON-NLS-1$ //$NON-NLS-2$
	 				;
//		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idEstoque = rs.getInt("idEstoque"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		if(idEstoque!=0)
		{
//			System.out.println("Estoque entry exist!");
		}
		else
		{
//			System.out.println("Storing new Estoque entry!");
			String sql = "INSERT INTO `estoque` (`Data`,`idTitulo`,`idFundo`) " //$NON-NLS-1$
					+ " VALUES (" //$NON-NLS-1$
					+ "'" + sdfd.format(dataReferencia) + "'" //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + titulo.getIdTitulo() //$NON-NLS-1$
					+ "," + titulo.getFundo().getIdFundo() //$NON-NLS-1$
					+ ")";			 //$NON-NLS-1$
//			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(titulo.getIdTitulo()==0)
			{
				 query = "SELECT idEstoque,Data,idTitulo,idFundo FROM Estoque " //$NON-NLS-1$
			 				+ " WHERE " //$NON-NLS-1$
			 				+ " " + " Data=" + "'" + sdfd.format(dataReferencia) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			 				+ " AND " + " idTitulo=" + titulo.getIdTitulo() //$NON-NLS-1$ //$NON-NLS-2$
			 				+ " AND " + " idFundo=" + titulo.getFundo().getIdFundo() //$NON-NLS-1$ //$NON-NLS-2$
			 				;
//				System.out.println(query);
				rs = null;
				try {
					rs = stmt.executeQuery(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					while (rs.next())
					{
						idEstoque = rs.getInt("idEstoque");					} //$NON-NLS-1$
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}						
		}
	}

	public static void storePrazo(Titulo titulo, Date dataEstoque, Connection conn)
	{
		int idTitulo=titulo.getIdTitulo();
		if(existPrazos(idTitulo, dataEstoque))
		{
//			System.out.println("Prazo exist!"); //$NON-NLS-1$
		}
		else
		{
			
			ArrayList<Integer> valoresPrazos = calculatePrazos(idTitulo, dataEstoque, conn);				
			double valorCorrido=valoresPrazos.get(0);
			double valorUtil=valoresPrazos.get(1);
			if(valorCorrido!=0)
			{
				System.out.println();
				String sql =  "INSERT INTO prazo (`idTitulo`,`DataEstoque`,`ValorCorrido`,`ValorUtil`)" //$NON-NLS-1$
						+ " VALUES (" //$NON-NLS-1$
						+ idTitulo
						+ "," + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ "," + valorCorrido //$NON-NLS-1$
						+ "," + valorUtil //$NON-NLS-1$
						+ ")"; //$NON-NLS-1$
				Statement stmt = null;
				try
				{
					stmt = (Statement) conn.createStatement();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				try
				{
					stmt.executeUpdate(sql);
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
//				System.out.println(idTitulo + " Corrido: " + valorCorrido + "\tUtil: " + valorUtil);
			}
		}		
	}
	
	public static void calculatePrazos(EstoqueLine estoqueLine, Connection conn)
	{
		int valorCorrido=0;
		int valorUtil=0;
		String query = "select DataVencimento from Titulo" //$NON-NLS-1$
				+ " where " //$NON-NLS-1$
				+ " idTitulo=" + estoqueLine.getTitulo().getIdTitulo(); //$NON-NLS-1$
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		
		try
		{
			while (rs.next())
			{
				Date dataVencimento=rs.getDate("DataVencimento"); //$NON-NLS-1$
//				if(dataVencimento.compareTo(dataEstoque)>0)
//				{
					valorCorrido = (int) WorkingDays.allDays(estoqueLine.getDataReferencia(),dataVencimento);
					valorUtil = (int) WorkingDays.countWorkingDays(estoqueLine.getDataReferencia(),dataVencimento, conn);
//				}
//				System.out.println(idTitulo + " " + sdfd.format(dataVencimento) + " " + sdfd.format(dataEstoque) + " " + valorCorrido); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		estoqueLine.setPrazoCorrido(valorCorrido);
		estoqueLine.setPrazoUtil(valorUtil);
	}	

	public static ArrayList<Integer> calculatePrazos(int idTitulo, Date dataEstoque, Connection conn)
	{
		int valorCorrido=0;
		int valorUtil=0;
		ArrayList<Integer> valores = new ArrayList<Integer>();
		
		String query = "select DataVencimento from Titulo" //$NON-NLS-1$
				+ " where " //$NON-NLS-1$
				+ " idTitulo=" + idTitulo; //$NON-NLS-1$
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		
		try
		{
			while (rs.next())
			{
				Date dataVencimento=rs.getDate("DataVencimento"); //$NON-NLS-1$
//				if(dataVencimento.compareTo(dataEstoque)>0)
//				{
					valorCorrido = (int) WorkingDays.allDays(dataEstoque,dataVencimento);
					valorUtil = (int) WorkingDays.countWorkingDays(dataEstoque,dataVencimento, conn);
//				}
				System.out.println(idTitulo + " " + sdfd.format(dataVencimento) + " " + sdfd.format(dataEstoque) + " " + valorCorrido); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		valores.add(valorCorrido);
		valores.add(valorUtil);
		return valores;
	}	
	
	public static boolean existPrazos(int idTitulo, Date dataEstoque)
	{
		String query = "select idPrazo from Prazo" //$NON-NLS-1$
				+ " where " //$NON-NLS-1$
				+ " idTitulo=" + idTitulo //$NON-NLS-1$
				+ " AND dataEstoque=" + "'" + sdfd.format(dataEstoque) + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		int idPrazo=0;
		try
		{
			while (rs.next())
			{
				idPrazo=rs.getInt("idPrazo"); //$NON-NLS-1$
				if(idPrazo!=0)
				{
					return true;
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public static String removeLeftZeros(String inputString)
	{
		String outputString=""; //$NON-NLS-1$
		int sizeLeftZeros=0;
		for (int i = 0; i < inputString.length(); i++)
		{
		    char c = inputString.charAt(i);
		    if(c=='0')
		    {
		    	sizeLeftZeros=sizeLeftZeros+1;
		    }
		    else
		    {
		    	break;
		    }
		}
		
		if(sizeLeftZeros>0)
		{
			outputString=inputString.substring(sizeLeftZeros-1);
			return outputString;
		}
		else
		{
			return inputString;	
		}
	}
    @SuppressWarnings("resource")
	public void readConf()
	{
		BufferedReader reader = null;
		System.out.println("Reading conf/HandlerEstoque.conf file"); //$NON-NLS-1$
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
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Connection getConn()
	{
		return conn;
	}

	public static void setConn(Connection conn)
	{
		HandlerEstoque.conn = conn;
	}

	public String getServer()
	{
		return this.server;
	}

	public void setServer(String server)
	{
		this.server = server;
	}

	public int getPort()
	{
		return this.port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getUserName()
	{
		return this.userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getDbName()
	{
		return this.dbName;
	}

	public void setDbName(String dbName)
	{
		this.dbName = dbName;
	}

	public MySQLAccess getMysql()
	{
		return this.mysql;
	}

	public void setMysql(MySQLAccess mysql)
	{
		this.mysql = mysql;
	}

	public static String getPathProcessar()
	{
		return pathProcessar;
	}

	public static void setPathProcessar(String pathProcessar)
	{
		HandlerEstoque.pathProcessar = pathProcessar;
	}

	public static String getPathProcessado()
	{
		return pathProcessado;
	}

	public static void setPathProcessado(String pathProcessado)
	{
		HandlerEstoque.pathProcessado = pathProcessado;
	}

	public static SimpleDateFormat getSdfd()
	{
		return sdfd;
	}

	public static void setSdfd(SimpleDateFormat sdfd)
	{
		HandlerEstoque.sdfd = sdfd;
	}

	public File getFile()
	{
		return this.file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}

	public boolean isComplete()
	{
		return this.isComplete;
	}

	public void setComplete(boolean isComplete)
	{
		this.isComplete = isComplete;
	}
	
}
