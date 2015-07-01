package relatorio.cessao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import operation.Operacao;
import operation.OperationSummary;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class HandlerDireitoCreditorio 
{
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd");
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
	
	public HandlerDireitoCreditorio() 
	{

	}
	public static void readStored(Operacao op, Connection conn)
	{
		ArrayList<DireitoCreditorio> dcs = new ArrayList<DireitoCreditorio>();
		
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

		String query = "SELECT idDireitoCreditorio FROM direitocreditorio"
	 				+ " WHERE idOperacao = " + op.getIdOperacao();
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
				DireitoCreditorio dc = new DireitoCreditorio(rs.getInt("idDireitoCreditorio"), conn);
				dc.show();
				dcs.add(dc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		Relatorio relatorio = new Relatorio(op, dcs);
		op.setRelatorio(relatorio);
		OperationSummary resumo = new OperationSummary(op);
		op.setResumo(resumo);
		
	}

	public static void store(Operacao op, Connection conn)
	{
	//		 `idTipoDeRecebivel`,
	//		 `idEntidadeSacado`,
	//		 `idEntidadeCedente`,
	//		 `idFundo`,
	//		 `campoChave`,
	//		 `numeroDoTitulo`,
	//		 `dataDeAquisicao`,
	//		 `dataDeVencimento`,
	//		 `valorDeAquisicao`,
	//		 `valorDoTitulo`,
	//		 `idOperacao`
		System.out.println("-------------------------");
		System.out.println("Storing DCs");
		System.out.println("-------------------------");
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		 for(DireitoCreditorio dc:op.getRelatorio().getBlockDireitosCreditorios().getDireitosCreditorios())
		 {
			 if(dc.getTaxaAoAno()==0.0 && dc.getPrazoDiasCorridos()==0)
			 {
				 continue;
			 }
			 
			 if(dc.getIdDireitoCreditorio()==0)
			 {
				 String query = "SELECT idDireitoCreditorio FROM direitocreditorio"
				 				+ " WHERE idOperacao = " + op.getIdOperacao()
						 		+ " AND campoChave = \"" + dc.getCampoChave() + "\"";
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
						dc.setIdDireitoCreditorio(rs.getInt("idDireitoCreditorio"));
						System.out.println("DireitoCreditorio already exist!");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			 }
			 
			 if(dc.getIdDireitoCreditorio()==0 
					 )
			 {
//				 if(dc.getValorDeAquisicao()==0.0 && dc.getPrazoDiasCorridos()==0)
//				 {
//					 System.out.println("Baixar titulo aqui!!");
//					 String query = "SELECT idDireitoCreditorio FROM direitocreditorio"
//				 				+ " WHERE numeroDoTitulo = \"" + dc.getNumeroDoTitulo() + "\""
//						 		+ " AND campoChave = \"" + dc.getCampoChave() + "\"";
//					System.out.println(query);
//					ResultSet rs = null;
//					try {
//						rs = stmt.executeQuery(query);
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					try {
//						while (rs.next())
//						{
//							dc.setIdDireitoCreditorio(rs.getInt("idDireitoCreditorio"));
//							System.out.println("DireitoCreditorio already exist and must be retired!");
//						}
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}									 
//				 }
				 
				 String sql = "INSERT INTO `mvcapital`.`direitocreditorio` ("
				 													+ "`idTipoDeRecebivel`,"
				 													+ "`idEntidadeSacado`,"
				 													+ "`idEntidadeCedente`,"
				 													+ "`idFundo`,"
				 													+ "`campoChave`,"
				 													+ "`numeroDoTitulo`,"
				 													+ "`dataDeAquisicao`,"
				 													+ "`dataDeVencimento`,"
				 													+ "`valorDeAquisicao`,"
				 													+ "`valorDoTitulo`,"
				 													+ "`idOperacao`) "
				 													+ "VALUES ("
															 		+ dc.getTipoDeRecebivel().getIdTipoDeRecebivel()
															 		+ "," + dc.getSacado().getIdEntidade()
															 		+ "," + dc.getCedente().getIdEntidade()
															 		+ "," + dc.getFundo().getIdFundo()
															 		+ "," + "\"" + dc.getCampoChave() + "\""
															 		+ "," + "\"" + dc.getNumeroDoTitulo() + "\""
															 		+ "," + sdfd.format(dc.getDataDeAquisicao())
															 		+ "," + sdfd.format(dc.getDataDeVencimento())
															 		+ "," + dc.getValorDeAquisicao()
															 		+ "," + dc.getValorDoTitulo()
															 		+ "," + op.getIdOperacao()
															  		+ ")";
				 
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
