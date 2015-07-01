package mvcapital.cheque;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mvcapital.bancopaulista.cnab.CnabPaulista;
import mvcapital.bancopaulista.cnab.Detail;
import mvcapital.bancopaulista.liquidadosebaixados.Ocorrencia;
import mvcapital.bancopaulista.liquidadosebaixados.TipoOcorrencia;
import mvcapital.bradesco.cheque.ReadFileBradesco;
import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.mysql.MySQLAccess;
import mvcapital.relatorio.cessao.Titulo;
import mvcapital.utils.WorkingDays;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class HandlerBaixas
{
	public static Connection conn=null;
	public static int idOcorrencia1raDevolucao=0;
	public static int idOcorrenciaDeposito=0;
	public static ArrayList<Integer> idBaixas = new ArrayList<Integer>();
	private static ArrayList<FundoDeInvestimento> fundos = new ArrayList<FundoDeInvestimento>();
	private static String pathProcessar = "W:\\Fundos\\Repositorio\\Cheques\\Processar\\"; //$NON-NLS-1$
	public static SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss"); //$NON-NLS-1$
	private static int numeroCessao=50;
	public static SimpleDateFormat sdfd= new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	
	public static void main(String[] args)
	{
		System.out.println("Lets take down some checks!"); //$NON-NLS-1$
		MySQLAccess.readConf();
		MySQLAccess mysqla = new MySQLAccess();
		mysqla.connect();
		HandlerBaixas.conn = (Connection) mysqla.getConn();
		HandlerBaixas.setupIdOcorrencias();
		HandlerBaixas.setupFundos();
		for(FundoDeInvestimento fundo:HandlerBaixas.fundos)
		{
			System.out.println("--Processing : " + fundo.getNome()); //$NON-NLS-1$			
			Entidade originador = new Entidade();
			int idEntidadeOriginador=0;
			if(fundo.getIdFundo()==6)
			{
				idEntidadeOriginador=28063;
			}
			else if(fundo.getIdFundo()==7)
			{
				idEntidadeOriginador=33193;
			}
			else if(fundo.getIdFundo()==3)
			{
				idEntidadeOriginador=27994;
			}
			else if(fundo.getIdFundo()==2)
			{
				idEntidadeOriginador=28016;
			}			
			else if(fundo.getIdFundo()==5)
			{
				idEntidadeOriginador=10085;
			}			
			originador = new Entidade(idEntidadeOriginador, conn);
			gerarCnabBaixa(originador, fundo);
			gerarListasDeDevolucao(fundo);
		}		
	}
	
	public HandlerBaixas()
	{
					
	}
	
	public static void processBaixas()
	{
		System.out.println("Lets take down some checks!"); //$NON-NLS-1$
		MySQLAccess.readConf();
		MySQLAccess mysqla = new MySQLAccess();
		mysqla.connect();
		HandlerBaixas.conn = (Connection) mysqla.getConn();
		HandlerBaixas.setupIdOcorrencias();
		HandlerBaixas.setupFundos();
		for(FundoDeInvestimento fundo:HandlerBaixas.fundos)
		{
			System.out.println("--Processing : " + fundo.getNome()); //$NON-NLS-1$			
			Entidade originador = new Entidade();
			int idEntidadeOriginador=0;
			if(fundo.getIdFundo()==6)
			{
				idEntidadeOriginador=28063;
			}
			else if(fundo.getIdFundo()==7)
			{
				idEntidadeOriginador=33193;
			}
			else if(fundo.getIdFundo()==3)
			{
				idEntidadeOriginador=27994;
			}
			else if(fundo.getIdFundo()==2)
			{
				idEntidadeOriginador=28016;
			}			
			else if(fundo.getIdFundo()==5)
			{
				idEntidadeOriginador=10085;
			}			
			originador = new Entidade(idEntidadeOriginador, conn);
			gerarCnabBaixa(originador, fundo);
//			gerarListasDeDevolucao(fundo);
		}
	}

	public static void gerarListasDeDevolucao(FundoDeInvestimento fundo)
	{
		HandlerBaixas.listaChequesDevolvidosUmaVez(fundo);
		HandlerBaixas.listaChequesDevolvidosOutros(fundo);
	}

	
	public static void gerarCnabBaixa(Entidade originador, FundoDeInvestimento fundo)
	{
		ArrayList<Titulo> chequesParaBaixar = HandlerBaixas.listaChequesCompensados(fundo);
		System.out.println(chequesParaBaixar.size() + " cheques para baixar"); //$NON-NLS-1$
		if(chequesParaBaixar.size()>0)
		{
			TipoOcorrencia tipoOcorrencia = new TipoOcorrencia(31, conn); 
			ArrayList<mvcapital.bancopaulista.cnab.Detail> details = new ArrayList<mvcapital.bancopaulista.cnab.Detail>();
			int countCheque=0;
			int sequenciaDetail=2;
			for(Titulo cheque:chequesParaBaixar)
			{
				if(cheque.getSacado().getEndereco()==null)
				{
					System.out.println("Building detail " + countCheque++ //$NON-NLS-1$
							+	";" + cheque.getSacado().getNome() //$NON-NLS-1$
							+	";'" + cheque.getSacado().getCadastro() //$NON-NLS-1$
							+	";" + cheque.getSacado().getIdEntidade() //$NON-NLS-1$
							);
				}
				Detail detail = new Detail(fundo, cheque, tipoOcorrencia, cheque.getValorNominal(), 0, numeroCessao, sequenciaDetail++,conn);
				details.add(detail);
			}
			
			
			CnabPaulista cnab = new CnabPaulista(fundo, originador, details, conn);
			System.out.println("Header"); //$NON-NLS-1$
			System.out.println(cnab.getHeader().toString());
			for(Detail detail:cnab.getDetails())
			{
				System.out.println(detail.toString());
			}
			System.out.println(cnab.getTraillerPaulista().toString());
			System.out.println(cnab.toString());
			String nameCnabFile = HandlerBaixas.pathProcessar + "cnabBaixaCheque"+HandlerBaixas.sdf.format(Calendar.getInstance().getTime())+ fundo.getNomeCurto().trim() + ".rem"; //$NON-NLS-1$ //$NON-NLS-2$
			String nameCSVFile = nameCnabFile.replace("BaixaCheque","BaixaListaCheques").replace(".rem", ".csv"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			File fileCnab = new File(nameCnabFile);
			File fileCSV = new File(nameCSVFile);
			String contentCSV=""; //$NON-NLS-1$
			if(chequesParaBaixar.size()>0)
			{
				contentCSV=contentCSV+Titulo.csvHeader()+"\n"; //$NON-NLS-1$
				for(Titulo cheque:chequesParaBaixar)
				{
					contentCSV=contentCSV+cheque.toCSV()+"\n"; //$NON-NLS-1$
				}
				
				try (PrintWriter output = new PrintWriter(fileCSV))
				{
					output.print(contentCSV);
					output.close();						
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
				} 
				
			}	
			if(cnab.getDetails().size()>0)
			{
				try (PrintWriter output = new PrintWriter(fileCnab))
				{
					output.print(cnab.toString());
					output.close();
					numeroCessao++;								
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
				} 
			}
		}
	}

	public static ArrayList<Titulo> listaChequesCompensados(FundoDeInvestimento fundo)
	{
		ArrayList<Titulo> titulosParaBaixar = new ArrayList<Titulo>();
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String query = "SELECT DISTINCT Ocorrencia.idTitulo,Titulo.idFundo FROM Ocorrencia JOIN Titulo ON Titulo.idTitulo=Ocorrencia.idTitulo" //$NON-NLS-1$
						+ " WHERE idTipoOcorrencia=" + idOcorrenciaDeposito //$NON-NLS-1$
						+ " AND idFundo=" + fundo.getIdFundo(); //$NON-NLS-1$
//		
//		String query = "SELECT DISTINCT idTitulo FROM Ocorrencia where" //$NON-NLS-1$
//				+ " idTipoOcorrencia=" + idOcorrenciaDeposito //$NON-NLS-1$
//				;
		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idTitulo = rs.getInt("idTitulo"); //$NON-NLS-1$
				if(idTitulo!=0)
				{
					Titulo titulo = new Titulo(idTitulo,conn);
					if(titulo.getFundo().getIdFundo()==fundo.getIdFundo())
					{						
						ArrayList<Ocorrencia> ocorrencias = ReadFileBradesco.checkTableOcorrencia(idTitulo, conn);
						int countOcorrencias = ocorrencias.size();
						if(countOcorrencias==1)
						{
							if(WorkingDays.afterWorkDays(titulo.getDataVencimento(), 8,conn).compareTo(Calendar.getInstance().getTime())<0)
							{
//								System.out.println("Cheque apressentado e não devolvido!"); //$NON-NLS-1$
								System.out.println(titulo.getTipoTitulo().getDescricao() 
													+ ";" + titulo.getNumeroDocumento()  //$NON-NLS-1$
													+ ";" + titulo.getValorNominal()  //$NON-NLS-1$
													+ ";" + titulo.getDataVencimento()); //$NON-NLS-1$
								titulosParaBaixar.add(titulo);
							}
						}
					}					
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return titulosParaBaixar;
	}

	public static void listaChequesDevolvidosUmaVez(FundoDeInvestimento fundo)
	{
		ArrayList<Titulo> titulosDevolvidosUmaVezPorFaltaDeFundos = new ArrayList<Titulo>();
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String query = "SELECT DISTINCT Ocorrencia.idTitulo,Titulo.idFundo FROM Ocorrencia JOIN Titulo ON Titulo.idTitulo=Ocorrencia.idTitulo" //$NON-NLS-1$
						+ " WHERE idTipoOcorrencia=" + idOcorrencia1raDevolucao //$NON-NLS-1$
						+ " AND idFundo=" + fundo.getIdFundo(); //$NON-NLS-1$
		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idTitulo = rs.getInt("idTitulo"); //$NON-NLS-1$
				if(idTitulo!=0)
				{
					Titulo titulo = new Titulo(idTitulo,conn);
					if(titulo.getFundo().getIdFundo()==fundo.getIdFundo())
					{						
						ArrayList<Ocorrencia> ocorrencias = ReadFileBradesco.checkTableOcorrencia(idTitulo, conn);
						int countOcorrencias = ocorrencias.size();
						System.out.println("# Cheque: " + titulo.getIdTitulo() + " "  + sdfd.format(titulo.getDataVencimento()) + " " + countOcorrencias + " ocorrencias"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$						
						int countDevolucoes = ReadFileBradesco.countDevolucao(ocorrencias);
						if(countDevolucoes==1)
						{
							Ocorrencia ocorrencia = ocorrencias.get(0);
							Date dataPrimeiraDevolucao = ReadFileBradesco.checkOcorrencia(idTitulo, idOcorrencia1raDevolucao, conn);
							if(WorkingDays.afterWorkDays(dataPrimeiraDevolucao, 4+8,conn).compareTo(Calendar.getInstance().getTime())>0)
							{
								System.out.println(" - " + sdfd.format(ocorrencia.getData()) + " " + ocorrencia.getTipoOcorrencia().getDescricao()); //$NON-NLS-1$ //$NON-NLS-2$
								System.out.println(titulo.getTipoTitulo().getDescricao() 
													+ ";" + titulo.getNumeroDocumento()  //$NON-NLS-1$
													+ ";" + titulo.getValorNominal()  //$NON-NLS-1$
													+ ";" + titulo.getDataVencimento()); //$NON-NLS-1$
								titulosDevolvidosUmaVezPorFaltaDeFundos.add(titulo);
							}
						}
					}					
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		
		System.out.println(titulosDevolvidosUmaVezPorFaltaDeFundos.size() + " cheques devolvidos uma vez"); //$NON-NLS-1$

		File fileApresentadosUmaVez = new File(HandlerBaixas.pathProcessar + "listaChequesDevolvidosUmaVez"+HandlerBaixas.sdf.format(Calendar.getInstance().getTime())+ fundo.getNomeCurto().trim() + ".csv"); //$NON-NLS-1$ //$NON-NLS-2$

		if(titulosDevolvidosUmaVezPorFaltaDeFundos.size()>0)
		{
			for(Titulo cheque:titulosDevolvidosUmaVezPorFaltaDeFundos)
			{
				cheque.show();
			}
			
			String contentCSV=""; //$NON-NLS-1$
			if(titulosDevolvidosUmaVezPorFaltaDeFundos.size()>0)
			{
				contentCSV=contentCSV+Titulo.csvHeader()+"\n"; //$NON-NLS-1$
				for(Titulo cheque:titulosDevolvidosUmaVezPorFaltaDeFundos)
				{
					contentCSV=contentCSV+cheque.toCSV()+"\n"; //$NON-NLS-1$
				}
				
				try (PrintWriter output = new PrintWriter(fileApresentadosUmaVez))
				{
					output.print(contentCSV);
					output.close();						
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
				} 				
			}			
		}		
	}
	
	public static void listaChequesDevolvidosOutros(FundoDeInvestimento fundo)
	{
		ArrayList<Titulo> titulosDevolvidosMaidDeUmaVez = new ArrayList<Titulo>();
		Statement stmt = null;
		try 
		{
			stmt = (Statement) conn.createStatement();
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}

		String query = "SELECT DISTINCT Ocorrencia.idTitulo,Titulo.idFundo FROM Ocorrencia JOIN Titulo ON Titulo.idTitulo=Ocorrencia.idTitulo" //$NON-NLS-1$
						+ " WHERE idTipoOcorrencia=" + idOcorrencia1raDevolucao //$NON-NLS-1$
						+ " AND idFundo=" + fundo.getIdFundo(); //$NON-NLS-1$
		System.out.println(query);
		
		
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idTitulo = rs.getInt("idTitulo"); //$NON-NLS-1$
				if(idTitulo!=0)
				{
					Titulo titulo = new Titulo(idTitulo,conn);
					if(titulo.getFundo().getIdFundo()==fundo.getIdFundo())
					{						
						ArrayList<Ocorrencia> ocorrencias = ReadFileBradesco.checkTableOcorrencia(idTitulo, conn);
						int countOcorrencias = ocorrencias.size();
						System.out.println("$ Cheque: " + titulo.getIdTitulo() + " " + countOcorrencias + " ocorrencias"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						int countDevolucoes = ReadFileBradesco.countDevolucao(ocorrencias);		
						if(countDevolucoes>1)
						{
							titulosDevolvidosMaidDeUmaVez.add(titulo);
						}
					}					
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		System.out.println(titulosDevolvidosMaidDeUmaVez.size() + " cheques devolvidos mais de uma vez"); //$NON-NLS-1$
		File fileDevolvidosMaisDeUmaVez = new File(HandlerBaixas.pathProcessar + "listaChequesDevolvidosMaisDeUmaVez"+HandlerBaixas.sdf.format(Calendar.getInstance().getTime())+ fundo.getNomeCurto().trim() + ".csv"); //$NON-NLS-1$ //$NON-NLS-2$

		if(titulosDevolvidosMaidDeUmaVez.size()>0)
		{
			for(Titulo cheque:titulosDevolvidosMaidDeUmaVez)
			{
				cheque.show();
			}
			
			String contentCSV=""; //$NON-NLS-1$
			if(titulosDevolvidosMaidDeUmaVez.size()>0)
			{
				contentCSV=contentCSV+Titulo.csvHeader()+"\n"; //$NON-NLS-1$
				for(Titulo cheque:titulosDevolvidosMaidDeUmaVez)
				{
					String stringOcorrencias=""; //$NON-NLS-1$
					ArrayList<Ocorrencia> ocorrencias = ReadFileBradesco.checkTableOcorrencia(cheque.getIdTitulo(), conn);
					for(Ocorrencia ocorrencia:ocorrencias)
					{
						System.out.println("[" + sdfd.format(ocorrencia.getData()) + "][" + ocorrencia.getTipoOcorrencia().getDescricao() + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						stringOcorrencias+="[" + sdfd.format(ocorrencia.getData()) + "][" + ocorrencia.getTipoOcorrencia().getDescricao()+"]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					contentCSV=contentCSV+cheque.toCSV()+";" + stringOcorrencias + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				
				try (PrintWriter output = new PrintWriter(fileDevolvidosMaisDeUmaVez))
				{
					output.print(contentCSV);
					output.close();						
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
				} 				
			}			
		}		
	}

	
	
	public static ArrayList<Titulo> setupTitlesByFund(FundoDeInvestimento fundo)
	{
		ArrayList<Titulo> titulosParaBaixar = new ArrayList<Titulo>();
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT DISTINCT idTitulo FROM Ocorrencia where" //$NON-NLS-1$
				+ " idTipoOcorrencia=" + idOcorrenciaDeposito //$NON-NLS-1$
				+ " OR idTipoOcorrencia=" + idOcorrencia1raDevolucao //$NON-NLS-1$
				;
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idTitulo = rs.getInt("idTitulo"); //$NON-NLS-1$
				if(idTitulo!=0)
				{
					Titulo titulo = new Titulo(idTitulo,conn);
					if(titulo.getFundo().getIdFundo()==fundo.getIdFundo())
					{						
						ArrayList<Ocorrencia> ocorrencias = ReadFileBradesco.checkTableOcorrencia(idTitulo, conn);
						int countDevolucao = ReadFileBradesco.countDevolucao(ocorrencias);
						if(countDevolucao==0)
						{
							if(WorkingDays.afterWorkDays(titulo.getDataVencimento(), 8,conn).compareTo(Calendar.getInstance().getTime())>0)
							{
//								System.out.println("Cheque apressentado e não devolvido!"); //$NON-NLS-1$
								System.out.println(titulo.getTipoTitulo().getDescricao() 
													+ ";" + titulo.getNumeroDocumento()  //$NON-NLS-1$
													+ ";" + titulo.getValorNominal()  //$NON-NLS-1$
													+ ";" + titulo.getDataVencimento()); //$NON-NLS-1$
								titulosParaBaixar.add(titulo);
							}
						}
						else if(countDevolucao==1)
						{
							Date dataPrimeiraDevolucao = ReadFileBradesco.checkOcorrencia(idTitulo, idOcorrencia1raDevolucao, conn);
							if(WorkingDays.afterWorkDays(dataPrimeiraDevolucao, 4+8,conn).compareTo(Calendar.getInstance().getTime())>0)
							{
//								System.out.println("Cheque apressentado pela 2da vez e não devolvido!"); //$NON-NLS-1$
								System.out.println(titulo.getTipoTitulo().getDescricao() 
													+ ";" + titulo.getNumeroDocumento()  //$NON-NLS-1$
													+ ";" + titulo.getValorNominal()  //$NON-NLS-1$
													+ ";" + titulo.getDataVencimento()); //$NON-NLS-1$
								titulosParaBaixar.add(titulo);
							}
							
						}
					}					
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return titulosParaBaixar;
	}
	
	public static void setupFundos()
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT idFundo FROM Fundo where idFundoAtivo=1"; //$NON-NLS-1$
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idFundo = rs.getInt("idFundo"); //$NON-NLS-1$
				if(idFundo!=0)
				{
					FundoDeInvestimento fundo = new FundoDeInvestimento(idFundo,conn);
					fundos.add(fundo);
					System.out.println("Fund: " + (new Entidade(fundo.getIdEntidade(),conn)).getNome()); //$NON-NLS-1$
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		
	}
	
	public static void setupIdOcorrencias()
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT idTipoOcorrencia, descricao FROM TipoOcorrencia"; //$NON-NLS-1$
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idTipoOcorrencia = rs.getInt("idTipoOcorrencia"); //$NON-NLS-1$
				String descricao = rs.getString("descricao"); //$NON-NLS-1$
				if(idTipoOcorrencia!=0)
				{
					if(descricao.toLowerCase().contains("cheque depositado")) //$NON-NLS-1$
					{
						System.out.println(descricao + " " + idTipoOcorrencia); //$NON-NLS-1$
						idOcorrenciaDeposito = idTipoOcorrencia;
					}
					else if(descricao.toLowerCase().contains("cheque sem fundos 1ra apresentacao")) //$NON-NLS-1$
					{
						System.out.println(descricao + " " + idTipoOcorrencia); //$NON-NLS-1$
						idOcorrencia1raDevolucao = idTipoOcorrencia;
					}
					else if(descricao.toLowerCase().contains("baixa")) //$NON-NLS-1$
					{
						System.out.println(descricao + " " + idTipoOcorrencia); //$NON-NLS-1$
						idBaixas.add(idTipoOcorrencia);
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		
	}
	
	public static ArrayList<FundoDeInvestimento> setFundosAtivos()
	{
		ArrayList<FundoDeInvestimento> fundos = new ArrayList<FundoDeInvestimento>();
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT idFundo FROM Fundo " //$NON-NLS-1$
				+ " WHERE " //$NON-NLS-1$
				+ " idFundoAtivo = " + 1; //$NON-NLS-1$
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idFundo = rs.getInt("idFundo"); //$NON-NLS-1$
				if(idFundo!=0)
				{
					FundoDeInvestimento fundo = new FundoDeInvestimento(idFundo, conn);
					fundos.add(fundo);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return fundos;
	}

}
