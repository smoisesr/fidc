package mvcapital.limites;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.operation.Operacao;
import mvcapital.operation.TituloAttempt;
import mvcapital.relatorio.cessao.TipoTitulo;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class HandlerLimiteDeTaxa 
{

	public HandlerLimiteDeTaxa() 
	{

	}

	public static void assessLimiteDeTaxa(Operacao op, Connection conn)
	{
		ArrayList<LimiteDeTaxa> limitesDeTaxa = readStoredLimiteDeTaxa(op, conn);
		for(LimiteDeTaxa limiteDePrazo:limitesDeTaxa)
		{
//			limiteDePrazo.show();
//			minimoDiasCorridos
//			maximoDiasCorridos
//			minimoDiasUteis
//			maximoDiasUteis
			
			if(limiteDePrazo.getTipoDeLimite().equals("taxaMinimaAoAno"))
			{
				assessLimiteDeTaxaMinimaAoAno(op, limiteDePrazo, conn);
			}
			else if(limiteDePrazo.getTipoDeLimite().equals("taxaMinimaDiariaSobreCDI"))
			{
				assessLimiteDeTaxaMinimaDiariaSobreCDI(op, limiteDePrazo, conn);
			}
		}
	}

	public static void assessLimiteDeTaxaMinimaDiariaSobreCDI(Operacao op, LimiteDeTaxa limiteDeTaxa, Connection conn)
	{
	
		boolean haveTipoDeRecebivelComLimiteDeTaxaMinimaDiariaSobreCDI=false;
		double taxaDIOver=0.0;
		
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT * FROM cdiover ORDER BY data DESC LIMIT 1";
//		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				taxaDIOver=rs.getDouble("taxa");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		taxaDIOver = Math.pow(taxaDIOver/100.00+1,(1.0/252.0))-1.0;
		
		for(TipoTitulo tipoDeRecebivel:op.getRelatorio().getBlockTitulos().getTiposTitulo())
		{
//			System.out.println("Check if Operation " + op.getNomeArquivo() + " have " + limiteDePrazo.getTipoDeRecebivel().getDescricao());
			if(tipoDeRecebivel.getDescricao().equals(limiteDeTaxa.getTipoTitulo().getDescricao()))
			{
				haveTipoDeRecebivelComLimiteDeTaxaMinimaDiariaSobreCDI=true;
				break;
			}
		}
		
		if(haveTipoDeRecebivelComLimiteDeTaxaMinimaDiariaSobreCDI)
		{
			System.out.println("assessLimiteDeTaxaMinimaDiariaSobreCDI");			
			limiteDeTaxa.show();
			for(TituloAttempt dcAttempt:op.getResumo().getDireitosCreditoriosAttempt())
			{
				if(dcAttempt.getTitulo().getTipoTitulo().getDescricao().equals(limiteDeTaxa.getTipoTitulo().getDescricao()))
				{
					dcAttempt.getLimites().add(limiteDeTaxa);
					System.out.println("TaxaCDIOver: " + taxaDIOver);
					dcAttempt.setTaxaDiariaSobreCDI(dcAttempt.getTitulo().getTaxaDiaUtil()/taxaDIOver);
					assessLimiteDeTaxaMinimaDiariaSobreCDI(dcAttempt);
				}
			}
		}
		else
		{
			limiteDeTaxa.setOk(true);
		}
	}
	
	public static void assessLimiteDeTaxaMinimaDiariaSobreCDI(TituloAttempt dca)
	{
		LimiteDeTaxa limiteDeTaxaMinimaDiariaSobreCDI=new LimiteDeTaxa();
//		System.out.println("Limit for " + dca.getDireitoCreditorio().getSeuNumero());
//		System.out.println(dca.getLimites().get(0).getClass().getName());
		for(Limite limite:dca.getLimites())
		{
			if(limite.getTipoDeLimite().equals("taxaMinimaDiariaSobreCDI"))
			{
				System.out.println("TaxaAoAno: " + dca.getTitulo().getTaxaAoAno());
				System.out.println("TaxaDiaUtil: " + dca.getTitulo().getTaxaDiaUtil());

				limiteDeTaxaMinimaDiariaSobreCDI=(LimiteDeTaxa)limite;
				if(dca.getTaxaDiariaSobreCDI()>=limiteDeTaxaMinimaDiariaSobreCDI.getValor())
				{
					System.out.println("Taxa minima diaria sobre CDI para "
										+ " " + dca.getTitulo().getSeuNumero()										
										+ " " + dca.getTaxaDiariaSobreCDI()
										+ " >= " + limiteDeTaxaMinimaDiariaSobreCDI.getValor()
										+ " OK!"
										);
					limite.setOk(true);
				}
				else
				{
					if(dca.getTitulo().getPrazoUtil()==0)
					{
						limite.setOk(true);
					}
					else
					{
						System.out.println("Taxa minima diaria sobre CDI para "
								+ " " + dca.getTitulo().getSeuNumero()										
								+ " " + dca.getTaxaDiariaSobreCDI()
								+ " < " + limiteDeTaxaMinimaDiariaSobreCDI.getValor()
								+ " BAD!"
								);
					}
				}
//				System.out.println("Found LimiteDePrazo to assess dca");
				break;
			}
		}
	}	
	
	public static void assessLimiteDeTaxaMinimaAoAno(Operacao op, LimiteDeTaxa limiteDeTaxa, Connection conn)
	{
	
		boolean haveTipoDeRecebivelComLimiteDeTaxaMinimaAoAno=false;
		for(TipoTitulo tipoDeRecebivel:op.getRelatorio().getBlockTitulos().getTiposTitulo())
		{
//			System.out.println("Check if Operation " + op.getNomeArquivo() + " have " + limiteDePrazo.getTipoDeRecebivel().getDescricao());
			if(tipoDeRecebivel.getDescricao().equals(limiteDeTaxa.getTipoTitulo().getDescricao()))
			{
				haveTipoDeRecebivelComLimiteDeTaxaMinimaAoAno=true;
				break;
			}
		}
		
		if(haveTipoDeRecebivelComLimiteDeTaxaMinimaAoAno)
		{
			System.out.println("assessLimiteDeTaxaMinimaAoAno");			
			limiteDeTaxa.show();
			for(TituloAttempt dcAttempt:op.getResumo().getDireitosCreditoriosAttempt())
			{
				if(dcAttempt.getTitulo().getTipoTitulo().getDescricao().equals(limiteDeTaxa.getTipoTitulo().getDescricao()))
				{
					dcAttempt.getLimites().add(limiteDeTaxa);
					assessLimiteDeTaxaMinimaAoAno(dcAttempt);
				}
			}
		}
		else
		{
			limiteDeTaxa.setOk(true);
		}
	}
	
	public static void assessLimiteDeTaxaMinimaAoAno(TituloAttempt dca)
	{
		LimiteDeTaxa limiteDeTaxaMinimaAoAno=new LimiteDeTaxa();
//		System.out.println("Limit for " + dca.getDireitoCreditorio().getSeuNumero());
//		System.out.println(dca.getLimites().get(0).getClass().getName());
		for(Limite limite:dca.getLimites())
		{
			if(limite.getTipoDeLimite().equals("taxaMinimaAoAno"))
			{
				limiteDeTaxaMinimaAoAno=(LimiteDeTaxa)limite;
				if(dca.getTitulo().getTaxaAoAno()>=limiteDeTaxaMinimaAoAno.getValor())
				{
					System.out.println("Taxa minima ao ano para "
										+ " " + dca.getTitulo().getSeuNumero()										
										+ " " + dca.getTitulo().getTaxaAoAno()
										+ " >= " + limiteDeTaxaMinimaAoAno.getValor()
										+ " OK!"
										);
					limite.setOk(true);
				}
				else
				{
					if(dca.getTitulo().getPrazoCorrido()==0)
					{
						limite.setOk(true);
					}
					else
					{
						System.out.println("Taxa minima ao ano para "
								+ " " + dca.getTitulo().getSeuNumero()										
								+ " " + dca.getTitulo().getTaxaAoAno()
								+ " < " + limiteDeTaxaMinimaAoAno.getValor()
								+ " BAD!"
								);
					}
				}
//				System.out.println("Found LimiteDePrazo to assess dca");
				break;
			}
		}
	}

	
	public static ArrayList<LimiteDeTaxa> readStoredLimiteDeTaxa(Operacao op, Connection conn)
	{
		ArrayList<LimiteDeTaxa> limitesDeTaxa = new ArrayList<LimiteDeTaxa>();
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		String query = "SELECT * FROM limitedetaxa WHERE idFundo=" + op.getFundo().getIdFundo();
//		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				LimiteDeTaxa limite = new LimiteDeTaxa();
				int idLimiteDeTaxa = rs.getInt("idLimiteDeTaxa");
				int idTipoDeTaxa = rs.getInt("idTipoDeTaxa");
				int idFundo = rs.getInt("idFundo");
				int idTipoDeRecebivel = rs.getInt("idTipoDeRecebivel");
				int valor = rs.getInt("valor");
				
				limite.setIdLimite(idLimiteDeTaxa);
				limite.setIdTipoDeLimite(idTipoDeTaxa);
				limite.setFundo(new FundoDeInvestimento(idFundo,conn));
				limite.setTipoTitulo(new TipoTitulo(idTipoDeRecebivel, conn));
				limite.setValor(valor);
				
				Statement stmt2=null;
				try {
					stmt2 = (Statement) conn.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				query = "SELECT tipo FROM tipodetaxa WHERE idTipoDeTaxa=" + idTipoDeTaxa;
				
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
				limitesDeTaxa.add(limite);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return limitesDeTaxa;
	}
	
}
