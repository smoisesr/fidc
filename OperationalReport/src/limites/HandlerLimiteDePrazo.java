package limites;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import operation.DireitoCreditorioAttempt;
import operation.Operacao;
import relatorio.cessao.TipoDeRecebivel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import fundo.FundoDeInvestimento;

public class HandlerLimiteDePrazo 
{

	public HandlerLimiteDePrazo() 
	{

	}

	public static void assessLimiteDePrazo(Operacao op, Connection conn)
	{
		ArrayList<LimiteDePrazo> limitesDePrazo = readStoredLimiteDePrazo(op, conn);
		for(LimiteDePrazo limiteDePrazo:limitesDePrazo)
		{
//			limiteDePrazo.show();
//			minimoDiasCorridos
//			maximoDiasCorridos
//			minimoDiasUteis
//			maximoDiasUteis
			if(limiteDePrazo.getTipoDeLimite().equals("minimoDiasCorridos"))
			{
				assessLimiteDePrazoMinimoDiasCorridos(op, limiteDePrazo, conn);
			}
			else if(limiteDePrazo.getTipoDeLimite().equals("maximoDiasCorridos"))
			{
				assessLimiteDePrazoMaximoDiasCorridos(op, limiteDePrazo, conn);
			}
			else if(limiteDePrazo.getTipoDeLimite().equals("minimoDiasUteis"))
			{
				assessLimiteDePrazoMinimoDiasUteis(op, limiteDePrazo, conn);
			}
			else if(limiteDePrazo.getTipoDeLimite().equals("maximoDiasUteis"))
			{
				assessLimiteDePrazoMaximoDiasUteis(op, limiteDePrazo, conn);
			}
		}
	}

	public static void assessLimiteDePrazoMaximoDiasCorridos(Operacao op, LimiteDePrazo limiteDePrazo, Connection conn)
	{
	
		boolean haveTipoDeRecebivelComLimiteDePrazoMaximoDiasCorridos=false;
		for(TipoDeRecebivel tipoDeRecebivel:op.getRelatorio().getBlockDireitosCreditorios().getTiposDeRecebivel())
		{
//			System.out.println("Check if Operation " + op.getNomeArquivo() + " have " + limiteDePrazo.getTipoDeRecebivel().getDescricao());
			if(tipoDeRecebivel.getDescricao().equals(limiteDePrazo.getTipoDeRecebivel().getDescricao()))
			{
				haveTipoDeRecebivelComLimiteDePrazoMaximoDiasCorridos=true;
				break;
			}
		}
		
		if(haveTipoDeRecebivelComLimiteDePrazoMaximoDiasCorridos)
		{
			System.out.println("assessLimiteDePrazoMaximoDiasCorridos");			
			limiteDePrazo.show();
			for(DireitoCreditorioAttempt dcAttempt:op.getResumo().getDireitosCreditoriosAttempt())
			{
				if(dcAttempt.getDireitoCreditorio().getTipoDeRecebivel().getDescricao().equals(limiteDePrazo.getTipoDeRecebivel().getDescricao()))
				{
					dcAttempt.getLimites().add(limiteDePrazo);
					assessLimiteDePrazoMaximoDiasCorridosDireitoCreditorio(dcAttempt);
				}
			}
		}
		else
		{
			limiteDePrazo.setOk(true);
		}
		
	}

	public static void assessLimiteDePrazoMaximoDiasUteis(Operacao op, LimiteDePrazo limiteDePrazo, Connection conn)
	{
	
		boolean haveTipoDeRecebivelComLimiteDePrazoMaximoDiasUteis=false;
		for(TipoDeRecebivel tipoDeRecebivel:op.getRelatorio().getBlockDireitosCreditorios().getTiposDeRecebivel())
		{
//			System.out.println("Check if Operation " + op.getNomeArquivo() + " have " + limiteDePrazo.getTipoDeRecebivel().getDescricao());
			if(tipoDeRecebivel.getDescricao().equals(limiteDePrazo.getTipoDeRecebivel().getDescricao()))
			{
				haveTipoDeRecebivelComLimiteDePrazoMaximoDiasUteis=true;
				break;
			}
		}
		
		if(haveTipoDeRecebivelComLimiteDePrazoMaximoDiasUteis)
		{
			System.out.println("assessLimiteDePrazoMaximoDiasUteis");			
			limiteDePrazo.show();
			for(DireitoCreditorioAttempt dcAttempt:op.getResumo().getDireitosCreditoriosAttempt())
			{
				if(dcAttempt.getDireitoCreditorio().getTipoDeRecebivel().getDescricao().equals(limiteDePrazo.getTipoDeRecebivel().getDescricao()))
				{				
					dcAttempt.getLimites().add(limiteDePrazo);
					assessLimiteDePrazoMaximoDiasUteisDireitoCreditorio(dcAttempt);
				}
			}
		}
		else
		{
			limiteDePrazo.setOk(true);
		}
		
	}
	
	
	public static void assessLimiteDePrazoMaximoDiasCorridosDireitoCreditorio(DireitoCreditorioAttempt dca)
	{
		LimiteDePrazo limiteDePrazoDiasCorridosDireitoCreditorio=new LimiteDePrazo();
//		System.out.println("Limit for " + dca.getDireitoCreditorio().getCampoChave());
//		System.out.println(dca.getLimites().get(0).getClass().getName());
		for(Limite limite:dca.getLimites())
		{
			if(limite.getTipoDeLimite().equals("maximoDiasCorridos"))
			{
				limiteDePrazoDiasCorridosDireitoCreditorio=(LimiteDePrazo)limite;
				if(dca.getDireitoCreditorio().getPrazoDiasCorridos()<=limiteDePrazoDiasCorridosDireitoCreditorio.getValor())
				{
					System.out.println("Prazo maximo para "
										+ " " + dca.getDireitoCreditorio().getCampoChave()										
										+ " " + dca.getDireitoCreditorio().getPrazoDiasCorridos()
										+ " <= " + limiteDePrazoDiasCorridosDireitoCreditorio.getValor()
										+ " OK!"
										);
					limite.setOk(true);
				}
				else
				{
					System.out.println("Prazo maximo para "
							+ " " + dca.getDireitoCreditorio().getCampoChave()										
							+ " " + dca.getDireitoCreditorio().getPrazoDiasCorridos()
							+ " < " + limiteDePrazoDiasCorridosDireitoCreditorio.getValor()
							+ " BAD!"
							);
				}
//				System.out.println("Found LimiteDePrazo to assess dca");
				break;
			}
		}
	}

	
	public static void assessLimiteDePrazoMaximoDiasUteisDireitoCreditorio(DireitoCreditorioAttempt dca)
	{
		LimiteDePrazo limiteDePrazoDiasUteisDireitoCreditorio=new LimiteDePrazo();
//		System.out.println("Limit for " + dca.getDireitoCreditorio().getCampoChave());
//		System.out.println(dca.getLimites().get(0).getClass().getName());
		for(Limite limite:dca.getLimites())
		{
			if(limite.getTipoDeLimite().equals("maximoDiasUteis"))
			{
				limiteDePrazoDiasUteisDireitoCreditorio=(LimiteDePrazo)limite;
				if(dca.getDireitoCreditorio().getPrazoDiasUteis()<=limiteDePrazoDiasUteisDireitoCreditorio.getValor())
				{
					System.out.println("Prazo maximo para "
										+ " " + dca.getDireitoCreditorio().getCampoChave()										
										+ " " + dca.getDireitoCreditorio().getPrazoDiasUteis()
										+ " <= " + limiteDePrazoDiasUteisDireitoCreditorio.getValor()
										+ " OK!"
										);
					limite.setOk(true);
				}
				else
				{
					System.out.println("Prazo maximo para "
							+ " " + dca.getDireitoCreditorio().getCampoChave()										
							+ " " + dca.getDireitoCreditorio().getPrazoDiasUteis()
							+ " < " + limiteDePrazoDiasUteisDireitoCreditorio.getValor()
							+ " BAD!"
							);
				}
//				System.out.println("Found LimiteDePrazo to assess dca");
				break;
			}
		}
	}
	
	
	public static void assessLimiteDePrazoMinimoDiasCorridos(Operacao op, LimiteDePrazo limiteDePrazo, Connection conn)
	{
	
		boolean haveTipoDeRecebivelComLimiteDePrazoMinimoDiasCorridos=false;
		for(TipoDeRecebivel tipoDeRecebivel:op.getRelatorio().getBlockDireitosCreditorios().getTiposDeRecebivel())
		{
//			System.out.println("Check if Operation " + op.getNomeArquivo() + " have " + limiteDePrazo.getTipoDeRecebivel().getDescricao());
			if(tipoDeRecebivel.getDescricao().equals(limiteDePrazo.getTipoDeRecebivel().getDescricao()))
			{
				haveTipoDeRecebivelComLimiteDePrazoMinimoDiasCorridos=true;
				break;
			}
		}
		
		if(haveTipoDeRecebivelComLimiteDePrazoMinimoDiasCorridos)
		{
			System.out.println("assessLimiteDePrazoMinimoDiasCorridos");			
			limiteDePrazo.show();
			for(DireitoCreditorioAttempt dcAttempt:op.getResumo().getDireitosCreditoriosAttempt())
			{
				if(dcAttempt.getDireitoCreditorio().getTipoDeRecebivel().getDescricao().equals(limiteDePrazo.getTipoDeRecebivel().getDescricao()))
				{				
					dcAttempt.getLimites().add(limiteDePrazo);
					assessLimiteDePrazoMinimoDiasCorridosDireitoCreditorio(dcAttempt);
				}
			}
		}
		else
		{
			limiteDePrazo.setOk(true);
		}
		
	}
	
	public static void assessLimiteDePrazoMinimoDiasCorridosDireitoCreditorio(DireitoCreditorioAttempt dca)
	{
		LimiteDePrazo limiteDePrazoDiasCorridosDireitoCreditorio=new LimiteDePrazo();
//		System.out.println("Limit for " + dca.getDireitoCreditorio().getCampoChave());
//		System.out.println(dca.getLimites().get(0).getClass().getName());
		for(Limite limite:dca.getLimites())
		{
			if(limite.getTipoDeLimite().equals("minimoDiasCorridos"))
			{
				limiteDePrazoDiasCorridosDireitoCreditorio=(LimiteDePrazo)limite;				
				if(dca.getDireitoCreditorio().getPrazoDiasCorridos()>=limiteDePrazoDiasCorridosDireitoCreditorio.getValor())
				{
					System.out.println("Prazo minimo para "
										+ " " + dca.getDireitoCreditorio().getCampoChave()										
										+ " " + dca.getDireitoCreditorio().getPrazoDiasCorridos()
										+ " >= " + limiteDePrazoDiasCorridosDireitoCreditorio.getValor()
										+ " OK!"
										);
					
					limite.setOk(true);
				}
				else
				{
					if(dca.getDireitoCreditorio().getTaxaAoAno()==0.0)
					{
						System.out.println("Recompra"+ " " + dca.getDireitoCreditorio().getCampoChave() + " OK!");
						limite.setOk(true);
					}
					else
					{					
						System.out.println("Prazo minimo para "
								+ " " + dca.getDireitoCreditorio().getCampoChave()										
								+ " " + dca.getDireitoCreditorio().getPrazoDiasCorridos()
								+ " < " + limiteDePrazoDiasCorridosDireitoCreditorio.getValor()
								+ " BAD!"
								);
					}
				}
//				System.out.println("Found LimiteDePrazo to assess dca");
				break;
			}
		}
	}

	public static void assessLimiteDePrazoMinimoDiasUteis(Operacao op, LimiteDePrazo limiteDePrazo, Connection conn)
	{
	
		boolean haveTipoDeRecebivelComLimiteDePrazoMinimoDiasUteis=false;
		for(TipoDeRecebivel tipoDeRecebivel:op.getRelatorio().getBlockDireitosCreditorios().getTiposDeRecebivel())
		{
//			System.out.println("Check if Operation " + op.getNomeArquivo() + " have " + limiteDePrazo.getTipoDeRecebivel().getDescricao());
			if(tipoDeRecebivel.getDescricao().equals(limiteDePrazo.getTipoDeRecebivel().getDescricao()))
			{
				haveTipoDeRecebivelComLimiteDePrazoMinimoDiasUteis=true;
				break;
			}
		}
		
		if(haveTipoDeRecebivelComLimiteDePrazoMinimoDiasUteis)
		{
			System.out.println("assessLimiteDePrazoMinimoDiasUteis");			
			limiteDePrazo.show();
			for(DireitoCreditorioAttempt dcAttempt:op.getResumo().getDireitosCreditoriosAttempt())
			{
				if(dcAttempt.getDireitoCreditorio().getTipoDeRecebivel().getDescricao().equals(limiteDePrazo.getTipoDeRecebivel().getDescricao()))
				{				
					dcAttempt.getLimites().add(limiteDePrazo);
					assessLimiteDePrazoMinimoDiasUteisDireitoCreditorio(dcAttempt);
				}
			}
		}
		else
		{
			limiteDePrazo.setOk(true);
		}
		
	}
	
	public static void assessLimiteDePrazoMinimoDiasUteisDireitoCreditorio(DireitoCreditorioAttempt dca)
	{
		LimiteDePrazo limiteDePrazoDiasUteisDireitoCreditorio=new LimiteDePrazo();
//		System.out.println("Limit for " + dca.getDireitoCreditorio().getCampoChave());
//		System.out.println(dca.getLimites().get(0).getClass().getName());
		for(Limite limite:dca.getLimites())
		{
			if(limite.getTipoDeLimite().equals("minimoDiasUteis"))
			{
				limiteDePrazoDiasUteisDireitoCreditorio=(LimiteDePrazo)limite;
				if(dca.getDireitoCreditorio().getPrazoDiasUteis()>=limiteDePrazoDiasUteisDireitoCreditorio.getValor())
				{
					System.out.println("Prazo minimo para "
										+ " " + dca.getDireitoCreditorio().getCampoChave()										
										+ " " + dca.getDireitoCreditorio().getPrazoDiasUteis()
										+ " >= " + limiteDePrazoDiasUteisDireitoCreditorio.getValor()
										+ " OK!"
										);
					limite.setOk(true);
				}
				else
				{
					if(dca.getDireitoCreditorio().getTaxaAoAno()==0.0)
					{
						System.out.println("Recompra"+ " " + dca.getDireitoCreditorio().getCampoChave() + " OK!");
						limite.setOk(true);
					}
					else
					{
						System.out.println("Prazo minimo para "
								+ " " + dca.getDireitoCreditorio().getCampoChave()										
								+ " " + dca.getDireitoCreditorio().getPrazoDiasUteis()
								+ " < " + limiteDePrazoDiasUteisDireitoCreditorio.getValor()
								+ " BAD!"
								);
					}
				}
//				System.out.println("Found LimiteDePrazo to assess dca");
				break;
			}
		}
	}
	
	public static ArrayList<LimiteDePrazo> readStoredLimiteDePrazo(Operacao op, Connection conn)
	{
		ArrayList<LimiteDePrazo> limitesDePrazo = new ArrayList<LimiteDePrazo>();
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT * FROM limitedeprazo WHERE idFundo=" + op.getFundo().getIdFundo();
//		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				LimiteDePrazo limite = new LimiteDePrazo();
				int idLimiteDePrazo = rs.getInt("idLimiteDePrazo");
				int idTipoDePrazo = rs.getInt("idTipoDePrazo");
				int idFundo = rs.getInt("idFundo");
				int idTipoDeRecebivel = rs.getInt("idTipoDeRecebivel");
				int valor = rs.getInt("valor");
				
				limite.setIdLimite(idLimiteDePrazo);
				limite.setIdTipoDeLimite(idTipoDePrazo);
				limite.setFundo(new FundoDeInvestimento(idFundo,conn));
				limite.setTipoDeRecebivel(new TipoDeRecebivel(idTipoDeRecebivel, conn));
				limite.setValor(valor);
				
				Statement stmt2=null;
				try {
					stmt2 = (Statement) conn.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				query = "SELECT tipo FROM tipodeprazo WHERE idTipoDePrazo=" + idTipoDePrazo;
				
//				System.out.println(query);
				ResultSet rs2 = null;
				try {
					rs2 = stmt2.executeQuery(query);
					while (rs2.next())
					{
						limite.setTipoDeLimite(rs2.getString("tipo"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
//				limite.show();
				limitesDePrazo.add(limite);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return limitesDePrazo;
	}
	
}
