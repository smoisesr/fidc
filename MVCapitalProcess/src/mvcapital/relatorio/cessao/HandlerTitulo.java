package mvcapital.relatorio.cessao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import mvcapital.bancopaulista.estoque.EstoqueLine;
import mvcapital.entidade.Entidade;
import mvcapital.operation.Operacao;
import mvcapital.operation.OperationSummary;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class HandlerTitulo 
{
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd"); //$NON-NLS-1$
//	tipoDeRecebivel="";
//	idDireitoCreditorio=0;
//	idTipoDeRecebivel=0;
//	sacado=new Entidade();
//	cedente=new Entidade();
//	fundo=new FundoDeInvestimento();
//	campoChave=""; //Campo chave
//	numeroDoTitulo="";
//	dataDeAquisicao=Calendar.getInstance().getTime(); 
//	dataDeVencimento=Calendar.getInstance().getTime();
//	valorDeAquisicao=0;
//	valorDoTitulo=0;
//	idOperacao;
	
	public HandlerTitulo() 
	{

	}

	public static boolean isStored(Titulo titulo, Connection conn)
	{
    
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

//		`titulo`.`idTitulo`,
//	    `titulo`.`idFundo`,
//	    `titulo`.`idEntidadeOriginador`,
//	    `titulo`.`idEntidadeCedente`,
//	    `titulo`.`idEntidadeSacado`,
//	    `titulo`.`idTipoTitulo`,
//	    `titulo`.`SeuNumero`,
//	    `titulo`.`NumeroDocumento`,
//	    `titulo`.`DataAquisicao`,
//	    `titulo`.`DataVencimento`,
//	    `titulo`.`PrazoCorrido`,
//	    `titulo`.`PrazoUtil`,
//	    `titulo`.`ValorAquisicao`,
//	    `titulo`.`ValorNominal`,
//	    `titulo`.`ValorDiarioUtil`,
//	    `titulo`.`NFeChaveAcesso`,
//	    `titulo`.`DataAtualizacao`		
		String query = "SELECT idTitulo, idEntidadeOriginador FROM titulo " //$NON-NLS-1$
	 				+ " WHERE" //$NON-NLS-1$
	 				+ " " + " idFundo=" + titulo.getFundo().getIdFundo() //$NON-NLS-1$ //$NON-NLS-2$
	 				+ " AND " + " idEntidadeCedente=" + titulo.getCedente().getIdEntidade() //$NON-NLS-1$ //$NON-NLS-2$
	 				+ " AND " + " idEntidadeSacado=" + titulo.getSacado().getIdEntidade() //$NON-NLS-1$ //$NON-NLS-2$
	 				+ " AND " + " SeuNumero=" + "'" + titulo.getSeuNumero() + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 				+ " AND " + " NumeroDocumento=" + "'" + titulo.getNumeroDocumento() + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 				;
//		System.out.println(query);
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int idTitulo=0;
		int idEntidadeOriginador=0;
		try {
			while (rs.next())
			{
				idTitulo = rs.getInt("idTitulo"); //$NON-NLS-1$
				idEntidadeOriginador = rs.getInt("idEntidadeOriginador"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		if(idTitulo!=0)
		{
			titulo.setIdTitulo(idTitulo);
			if(idEntidadeOriginador!=0)
			{
				if(idEntidadeOriginador!=titulo.getOriginador().getIdEntidade())
				{
					System.out.println("Updating originador " + titulo.getOriginador().getNome()); //$NON-NLS-1$
					updateOriginador(titulo.getIdTitulo(), titulo.getOriginador(), conn);
				}
			}
			return true;
		}
		else
		{
			return false;
		}
		
	}

	public static void updateOriginador(int idTitulo, Entidade originador, Connection conn)	
	{
//		System.out.println("Constructor for " + nome + " " + conn);
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String sql = "UPDATE `Titulo` SET `idEntidadeOriginador` = "+ originador.getIdEntidade() + " WHERE idTitulo = " + idTitulo; //$NON-NLS-1$ //$NON-NLS-2$
//			System.out.println(sql);
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//			System.out.println("New Cedente: " + nomeCedente);

	}
	public static void readStored(Operacao op, Connection conn)
	{
		ArrayList<Titulo> titulos = new ArrayList<Titulo>();
		
//		`direitocreditorio`.`idDireitoCreditorio`,
//	    `direitocreditorio`.`idTipoDeRecebivel`,
//	    `direitocreditorio`.`idEntidadeSacado`,
//	    `direitocreditorio`.`idEntidadeCedente`,
//	    `direitocreditorio`.`idFundo`,
//	    `direitocreditorio`.`campoChave`,
//	    `direitocreditorio`.`numeroDoTitulo`,
//	    `direitocreditorio`.`dataDeAquisicao`,
//	    `direitocreditorio`.`dataDeVencimento`,
//	    `direitocreditorio`.`valorDeAquisicao`,
//	    `direitocreditorio`.`valorDoTitulo`,
//	    `direitocreditorio`.`idOperacao`
		

//		*private Relatorio relatorio=new Relatorio();
//		*private OperationSummary resumo = new OperationSummary();
//		*private Date registerTime=Calendar.getInstance().getTime();
		
//		private String certificadora="";
	    
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT idTitulo FROM titulo " //$NON-NLS-1$
	 				+ " WHERE idOperacao = " + op.getIdOperacao(); //$NON-NLS-1$
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
				Titulo titulo = new Titulo(rs.getInt("idTitulo"), conn); //$NON-NLS-1$
				titulo.show();
				titulos.add(titulo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		Relatorio relatorio = new Relatorio(op, titulos);
		op.setRelatorio(relatorio);
		OperationSummary resumo = new OperationSummary(op);
		op.setResumo(resumo);
		
	}

	
	
	public static int store(Titulo titulo, Connection conn)
	{
//		`titulo`.`idTitulo`,
//	    `titulo`.`idFundo`,
//	    `titulo`.`idEntidadeOriginador`,
//	    `titulo`.`idEntidadeCedente`,
//	    `titulo`.`idEntidadeSacado`,
//	    `titulo`.`idTipoTitulo`,
//	    `titulo`.`SeuNumero`,
//	    `titulo`.`NumeroDocumento`,
//	    `titulo`.`DataAquisicao`,
//	    `titulo`.`DataVencimento`,
//	    `titulo`.`PrazoCorrido`,
//	    `titulo`.`PrazoUtil`,
//	    `titulo`.`ValorAquisicao`,
//	    `titulo`.`ValorNominal`,
//	    `titulo`.`ValorDiarioUtil`,
//	    `titulo`.`NFeChaveAcesso`,
//	    `titulo`.`DataAtualizacao`	
		System.out.println("-------------------------"); //$NON-NLS-1$
		System.out.println("Storing Titulo"); //$NON-NLS-1$
		System.out.println("-------------------------"); //$NON-NLS-1$
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		 if(titulo.getIdTitulo()==0)
		 {
			 String query = "SELECT idTitulo, idEntidadeOriginador FROM titulo " //$NON-NLS-1$
		 				+ " WHERE" //$NON-NLS-1$
		 				+ " " + " idFundo=" + titulo.getFundo().getIdFundo() //$NON-NLS-1$ //$NON-NLS-2$
		 				+ " AND " + " idEntidadeCedente=" + titulo.getCedente().getIdEntidade() //$NON-NLS-1$ //$NON-NLS-2$
		 				+ " AND " + " idEntidadeSacado=" + titulo.getSacado().getIdEntidade() //$NON-NLS-1$ //$NON-NLS-2$
		 				+ " AND " + " SeuNumero=" + "'" + titulo.getSeuNumero() + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		 				+ " AND " + " NumeroDocumento=" + "'" + titulo.getNumeroDocumento() + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		 				;
			System.out.println(query);
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				while (rs.next())
				{
					titulo.setIdTitulo(rs.getInt("idTitulo")); //$NON-NLS-1$
					System.out.println("Titulo already exist!"); //$NON-NLS-1$
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		 }
		 
		 if(titulo.getIdTitulo()==0)
		 {
//				`titulo`.`idTitulo`,
//			    `titulo`.`idFundo`,
//			    `titulo`.`idEntidadeOriginador`,
//			    `titulo`.`idEntidadeCedente`,
//			    `titulo`.`idEntidadeSacado`,
//			    `titulo`.`idTipoTitulo`,
//			    `titulo`.`SeuNumero`,
//			    `titulo`.`NumeroDocumento`,
//			    `titulo`.`DataAquisicao`,
//			    `titulo`.`DataVencimento`,
//			    `titulo`.`PrazoCorrido`,
//			    `titulo`.`PrazoUtil`,
//			    `titulo`.`ValorAquisicao`,
//			    `titulo`.`ValorNominal`,
//			    `titulo`.`ValorDiarioUtil`,
//			    `titulo`.`NFeChaveAcesso`,
//			    `titulo`.`DataAtualizacao`	
			 
			 String sql = "INSERT INTO `titulo` (" //$NON-NLS-1$
					 								+ "`idFundo`,"	 													 //$NON-NLS-1$
 													+ "`idEntidadeOriginador`," //$NON-NLS-1$
 													+ "`idEntidadeCedente`," //$NON-NLS-1$
 													+ "`idEntidadeSacado`," //$NON-NLS-1$
 													+ "`idTipoTitulo`,"	 													 //$NON-NLS-1$
 													+ "`SeuNumero`," //$NON-NLS-1$
 													+ "`NumeroDocumento`," //$NON-NLS-1$
 													+ "`DataAquisicao`," //$NON-NLS-1$
 													+ "`DataVencimento`," //$NON-NLS-1$
 													+ "`PrazoCorrido`," //$NON-NLS-1$
 													+ "`PrazoUtil`," //$NON-NLS-1$
 													+ "`ValorAquisicao`," //$NON-NLS-1$
 													+ "`ValorNominal`," //$NON-NLS-1$
 													+ "`ValorDiarioUtil`," //$NON-NLS-1$
 													+ "`NFeChaveAcesso`," //$NON-NLS-1$
 													+ "`taxaAoAno`," //$NON-NLS-1$
 													+ "`taxaDiaUtil`" //$NON-NLS-1$
 													+ ") " //$NON-NLS-1$
 													+ "VALUES (" //$NON-NLS-1$
 													+ titulo.getFundo().getIdFundo()
 													+ "," + titulo.getOriginador().getIdEntidade() //$NON-NLS-1$
 													+ "," + titulo.getCedente().getIdEntidade() //$NON-NLS-1$
 													+ "," + titulo.getSacado().getIdEntidade() //$NON-NLS-1$
											 		+ "," + titulo.getTipoTitulo().getIdTipoTitulo() //$NON-NLS-1$
											 		+ "," + "\"" + titulo.getSeuNumero() + "\"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
											 		+ "," + "\"" + titulo.getNumeroDocumento() + "\"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
											 		+ "," + sdfd.format(titulo.getDataAquisicao()) //$NON-NLS-1$
											 		+ "," + sdfd.format(titulo.getDataVencimento()) //$NON-NLS-1$
											 		+ "," + titulo.getPrazoCorrido() //$NON-NLS-1$
											 		+ "," + titulo.getPrazoUtil() //$NON-NLS-1$
											 		+ "," + titulo.getValorAquisicao() //$NON-NLS-1$
											 		+ "," + titulo.getValorNominal() //$NON-NLS-1$
											 		+ "," + titulo.getValorDiarioUtil() //$NON-NLS-1$
											 		+ "," +  "\"" + titulo.getNFeChaveAcesso() +  "\"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
											 		+ "," +  titulo.getTaxaAoAno() //$NON-NLS-1$
											 		+ "," +  titulo.getTaxaDiaUtil() //$NON-NLS-1$
											  		+ ")"; //$NON-NLS-1$
			 
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			 if(titulo.getIdTitulo()==0)
			 {
				 String query = "SELECT idTitulo, idEntidadeOriginador FROM titulo " //$NON-NLS-1$
			 				+ " WHERE" //$NON-NLS-1$
			 				+ " " + " idFundo=" + titulo.getFundo().getIdFundo() //$NON-NLS-1$ //$NON-NLS-2$
			 				+ " AND " + " idEntidadeCedente=" + titulo.getCedente().getIdEntidade() //$NON-NLS-1$ //$NON-NLS-2$
			 				+ " AND " + " idEntidadeSacado=" + titulo.getSacado().getIdEntidade() //$NON-NLS-1$ //$NON-NLS-2$
			 				+ " AND " + " SeuNumero=" + "'" + titulo.getSeuNumero() + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			 				+ " AND " + " NumeroDocumento=" + "'" + titulo.getNumeroDocumento() + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			 				;
				System.out.println(query);
				ResultSet rs = null;
				try {
					rs = stmt.executeQuery(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					while (rs.next())
					{
						titulo.setIdTitulo(rs.getInt("idTitulo")); //$NON-NLS-1$
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			 }			
		 }	
		 return titulo.getIdTitulo();
	 }

	public static void store(Operacao op, Connection conn) 
	{
		for(Titulo titulo:op.getRelatorio().getBlockTitulos().getTitulos())
		{
			store(titulo, conn);
		}
		
	}

}
