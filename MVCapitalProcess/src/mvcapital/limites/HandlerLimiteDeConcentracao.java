package mvcapital.limites;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.operation.CedenteAttempt;
import mvcapital.operation.HandlerOperacoes;
import mvcapital.operation.MaioresCedentesAttempt;
import mvcapital.operation.MaioresSacadosAttempt;
import mvcapital.operation.Operacao;
import mvcapital.operation.SacadoAttempt;
import mvcapital.portalfidc.OperadorPortalPaulista;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class HandlerLimiteDeConcentracao 
{

	public HandlerLimiteDeConcentracao() 
	{
	
	}
	
	public static void assessLimiteDeConcentracao(Operacao op, Connection conn)
	{
		ArrayList<LimiteDeConcentracao> limitesDeConcentracao = HandlerLimiteDeConcentracao.readStoredLimiteDeConcentracao(op, conn);
		
		for(LimiteDeConcentracao limiteDeConcentracao:limitesDeConcentracao)
		{
			limiteDeConcentracao.show();
			if(limiteDeConcentracao.getTipoDeLimite().equals("maximoCedente"))
			{
				assessLimiteDeConcentracaoMaximoCedente(op, limiteDeConcentracao);
			}
			else if(limiteDeConcentracao.getTipoDeLimite().equals("maximoSacado"))
			{
				assessLimiteDeConcentracaoMaximoSacado(op, limiteDeConcentracao);
			}
			else if(limiteDeConcentracao.getTipoDeLimite().equals("maximoQuatroMaioresCedentes"))
			{
				assessLimiteDeConcentracaoMaximoQuatroMaioresCedentes(op, limiteDeConcentracao, conn);
			}
			else if(limiteDeConcentracao.getTipoDeLimite().equals("maximoQuatroMaioresSacados"))
			{
				assessLimiteDeConcentracaoMaximoQuatroMaioresSacados(op, limiteDeConcentracao, conn);
			}
		}
	}

	public static void assessLimiteDeConcentracaoMaximoQuatroMaioresCedentes(Operacao op, LimiteDeConcentracao limiteDeConcentracaoMaximoQuatroMaioresCedentes, Connection conn)
	{
		
		boolean alreadyApprouved = HandlerOperacoes.alreadyApprouved(op);
		boolean approvedByHand=HandlerOperacoes.approuvedByHand(op);
		MaioresCedentesAttempt maioresCedentesAttempt = new MaioresCedentesAttempt();
		double limite=limiteDeConcentracaoMaximoQuatroMaioresCedentes.getValor();
		double maximoOperar = 0.0;
		double valorPresente = 0.0;
		double concentracao = 0.0;
		double novoMaximoOperar = 0.0;
		double novoValorPresente = 0.0;
		double novaConcentracao = 0.0;
		double patrimonioFundo=0.0;
		double excesso=0.0;

		FundoDeInvestimento fundo = op.getFundo();
		
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String query = "SELECT valor FROM PLFundo WHERE idFundo="+fundo.getIdFundo()+" ORDER BY idPLFundo DESC LIMIT 1";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while (rs.next())
			{
				patrimonioFundo = rs.getDouble("valor");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		query = "SELECT idEntidadeCedente, valorPresente FROM concentracaocedente WHERE idFundo=" + op.getFundo().getIdFundo() + " ORDER BY valorPresente DESC LIMIT 4";
//		System.out.println(query);
		rs = null;
		ArrayList<Double> maiorValorPresente = new ArrayList<Double>();
		ArrayList<Integer> idsEntidadeCedente = new ArrayList<Integer>();
		double sumValorPresente=0;
		int iValorPresente=0;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				idsEntidadeCedente.add(rs.getInt("idEntidadeCedente"));
				maiorValorPresente.add(rs.getDouble("valorPresente"));
				sumValorPresente=sumValorPresente + maiorValorPresente.get(iValorPresente);
				iValorPresente=iValorPresente+1;				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Cedente: " + op.getResumo().getCedenteAttempt().getCedente().getNome());
		boolean isOnG4=false;
		
		for(int idEntidadeCedente:idsEntidadeCedente)
		{
			Entidade cedente = new Entidade(idEntidadeCedente,conn);
			maioresCedentesAttempt.getCedentes().add(cedente);
			System.out.println("Cedente4G: " + cedente.getNome());
			
			if(idEntidadeCedente==op.getResumo().getCedenteAttempt().getCedente().getIdEntidade())
			{
				isOnG4=true;
				break;
			}
		}

		valorPresente = sumValorPresente;
		
		System.out.println("IsOnG4: " + isOnG4);

		if(alreadyApprouved && !approvedByHand)
		{
			novoValorPresente = valorPresente;
			maioresCedentesAttempt.setLimiteDeConcentracao(limiteDeConcentracaoMaximoQuatroMaioresCedentes);
		}
		else
		{		
			if(isOnG4)
			{
				System.out.println("Cedente entre os quatro maiores!");
				novoValorPresente = valorPresente  + op.getResumo().getCedenteAttempt().getTotalOperar();
				maioresCedentesAttempt.setLimiteDeConcentracao(limiteDeConcentracaoMaximoQuatroMaioresCedentes);
			}		
			else if(op.getResumo().getCedenteAttempt().getNovoValorPresente() >= maiorValorPresente.get(3))
			{
				System.out.println("Novo cedente entre os quatro maiores!");
				novoValorPresente = sumMaiorValues(maiorValorPresente, op.getResumo().getCedenteAttempt().getNovoValorPresente());
				maioresCedentesAttempt.setLimiteDeConcentracao(limiteDeConcentracaoMaximoQuatroMaioresCedentes);
				maioresCedentesAttempt.getCedentes().set(3, op.getResumo().getCedenteAttempt().getCedente());
			}
			else
			{
				System.out.println("Cedente fora dos quatro maiores!");
				novoValorPresente = valorPresente;				
			}
		}		
		maximoOperar=limite*patrimonioFundo-valorPresente;
		concentracao = valorPresente/patrimonioFundo;
		maximoOperar = limite*patrimonioFundo-valorPresente;		
		novoMaximoOperar = limite*patrimonioFundo-novoValorPresente;
		novaConcentracao = novoValorPresente/patrimonioFundo;
		
		if(maximoOperar<0.0)
		{
			maximoOperar=0.0;
		}
		
		if(novoMaximoOperar<0.0)
		{
			novoMaximoOperar=0.0;
			excesso=novoValorPresente-limite*patrimonioFundo;
		}

		
		maioresCedentesAttempt.setValorPresente(Math.round(valorPresente*100.0)/100.0);
		maioresCedentesAttempt.setMaximoOperar(Math.round(maximoOperar*100.0)/100.0);			
		maioresCedentesAttempt.setConcentracao(concentracao);
		maioresCedentesAttempt.setNovoValorPresente(Math.round(novoValorPresente*100.0)/100.0);
		maioresCedentesAttempt.setNovaConcentracao(novaConcentracao);
		maioresCedentesAttempt.setNovoMaximoOperar(Math.round(novoMaximoOperar*100.0)/100.0);
		maioresCedentesAttempt.setExcesso(Math.round(excesso*100.0)/100.0);
		
		System.out.println("PatrimonioFundo: " + patrimonioFundo);
		System.out.println("ValorPresente: " + maioresCedentesAttempt.getValorPresente());
		System.out.println("Limite: " + limite);
		System.out.println("Concentracao: " + maioresCedentesAttempt.getConcentracao());			
		System.out.println("MaximoParaOperar: " + maioresCedentesAttempt.getMaximoOperar());
		System.out.println("NovoValorPresente: " + maioresCedentesAttempt.getNovoValorPresente());
		System.out.println("NovaConcentracao: " + maioresCedentesAttempt.getNovaConcentracao());
		System.out.println("NovoMaximoParaOperar: " + maioresCedentesAttempt.getNovoMaximoOperar());
		System.out.println("Excesso: " + maioresCedentesAttempt.getExcesso());

		if(novaConcentracao < limite)
		{
			System.out.println("Concentracao Quatro Maiores Cedentes Ok!");		
			maioresCedentesAttempt.getLimiteDeConcentracao().setOk(true);
		}
		else
		{
			System.out.println("Concentracao Quatro Maiores Cedentes exceeded!");
			if(!isOnG4)
			{
				maioresCedentesAttempt.getLimiteDeConcentracao().setOk(true);
			}
		}		
		op.getResumo().setMaioresCedentesAttempt(maioresCedentesAttempt);
	}
	
	
	public static void assessLimiteDeConcentracaoMaximoQuatroMaioresSacados(Operacao op, LimiteDeConcentracao limiteDeConcentracaoMaximoQuatroMaioresSacados, Connection conn)
	{
		boolean alreadyApprouved = HandlerOperacoes.alreadyApprouved(op);
		boolean approvedByHand=HandlerOperacoes.approuvedByHand(op);
		MaioresSacadosAttempt maioresSacadosAttempt = new MaioresSacadosAttempt();
		
		double limite=limiteDeConcentracaoMaximoQuatroMaioresSacados.getValor();
		double maximoOperar = 0.0;
		double valorPresente = 0.0;
		double concentracao = 0.0;
		double novoMaximoOperar = 0.0;
		double novoValorPresente = 0.0;
		double novaConcentracao = 0.0;
		double patrimonioFundo=0.0;
		double excesso=0.0;

		FundoDeInvestimento fundo = op.getFundo();
		
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String query = "SELECT valor FROM PLFundo WHERE idFundo="+fundo.getIdFundo()+" ORDER BY idPLFundo DESC LIMIT 1";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while (rs.next())
			{
				patrimonioFundo = rs.getDouble("valor");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		query = "SELECT idEntidadeSacado, valorPresente FROM concentracaosacado WHERE idFundo=" + op.getFundo().getIdFundo() + " ORDER BY valorPresente DESC LIMIT 4";
//		System.out.println(query);
		rs = null;
		ArrayList<Double> maiorValorPresente = new ArrayList<Double>();
		ArrayList<Integer> idsEntidadeSacado = new ArrayList<Integer>();
		double sumValorPresente=0;
		int iValorPresente=0;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				idsEntidadeSacado.add(rs.getInt("idEntidadeSacado"));
				maiorValorPresente.add(rs.getDouble("valorPresente"));
				sumValorPresente=sumValorPresente + maiorValorPresente.get(iValorPresente);
				iValorPresente=iValorPresente+1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		valorPresente = sumValorPresente;
		
		for(SacadoAttempt sacadoAttempt:op.getResumo().getSacadosAttempt())
		{
			boolean isOnG4=false;
			for(int idEntidadeSacado:idsEntidadeSacado)
			{
				maioresSacadosAttempt.getSacados().add(new Entidade(idEntidadeSacado, conn));
				if(idEntidadeSacado==sacadoAttempt.getSacado().getIdEntidade())
				{
					isOnG4=true;
					break;
				}
			}
			
			if(alreadyApprouved && !approvedByHand)
			{
				novoValorPresente = valorPresente;
				maioresSacadosAttempt.setLimiteDeConcentracao(limiteDeConcentracaoMaximoQuatroMaioresSacados);
			}
			else
			{
				if(isOnG4)
				{
					novoValorPresente = valorPresente  + op.getResumo().getCedenteAttempt().getTotalOperar();
					maioresSacadosAttempt.setLimiteDeConcentracao(limiteDeConcentracaoMaximoQuatroMaioresSacados);
					System.out.println("It is in G4 and have a concentration limit");
				}		
				else if(op.getResumo().getCedenteAttempt().getNovoValorPresente() >= maiorValorPresente.get(3))
				{
					novoValorPresente = sumMaiorValues(maiorValorPresente, op.getResumo().getCedenteAttempt().getNovoValorPresente());
					maioresSacadosAttempt.setLimiteDeConcentracao(limiteDeConcentracaoMaximoQuatroMaioresSacados);
					maioresSacadosAttempt.getSacados().set(3, sacadoAttempt.getSacado());
					System.out.println("Will be in G4 and have a concentration limit");
				}
				else
				{
					novoValorPresente = valorPresente;
				}
			}			
			maximoOperar=limite*patrimonioFundo-valorPresente;
			concentracao = valorPresente/patrimonioFundo;
			maximoOperar = limite*patrimonioFundo-valorPresente;		
			novoMaximoOperar = limite*patrimonioFundo-novoValorPresente;
			novaConcentracao = novoValorPresente/patrimonioFundo;
			
			if(maximoOperar<0.0)
			{
				maximoOperar=0.0;
			}
			
			if(novoMaximoOperar<0.0)
			{
				novoMaximoOperar=0.0;
				excesso = novoValorPresente-limite*patrimonioFundo;
			}

			
			maioresSacadosAttempt.setValorPresente(Math.round(valorPresente*100.0)/100.0);
			maioresSacadosAttempt.setMaximoOperar(Math.round(maximoOperar*100.0)/100.0);			
			maioresSacadosAttempt.setConcentracao(concentracao);
			maioresSacadosAttempt.setNovoValorPresente(Math.round(novoValorPresente*100.0)/100.0);
			maioresSacadosAttempt.setNovaConcentracao(novaConcentracao);
			maioresSacadosAttempt.setNovoMaximoOperar(Math.round(novoMaximoOperar*100.0)/100.0);
			maioresSacadosAttempt.setExcesso(Math.round(excesso*100.0)/100.0);
			
			System.out.println("PatrimonioFundo: " + patrimonioFundo);
			System.out.println("ValorPresente: " + maioresSacadosAttempt.getValorPresente());
			System.out.println("Limite: " + limite);
			System.out.println("Concentracao: " + maioresSacadosAttempt.getConcentracao());			
			System.out.println("MaximoParaOperar: " + maioresSacadosAttempt.getMaximoOperar());
			System.out.println("NovoValorPresente: " + maioresSacadosAttempt.getNovoValorPresente());
			System.out.println("NovaConcentracao: " + maioresSacadosAttempt.getNovaConcentracao());
			System.out.println("NovoMaximoParaOperar: " + maioresSacadosAttempt.getNovoMaximoOperar());
			System.out.println("Excesso: " + maioresSacadosAttempt.getExcesso());
			
			if(novaConcentracao < limite)
			{
				System.out.println("Concentracao Quatro Maiores Sacados para " + sacadoAttempt.getSacado().getNome() + " Ok!");		
				maioresSacadosAttempt.getLimiteDeConcentracao().setOk(true);
			}
			else
			{
				if(!isOnG4)
				{
					maioresSacadosAttempt.getLimiteDeConcentracao().setOk(true);
					continue;
				}
				System.out.println("Concentracao Quatro Maiores Sacados para " + sacadoAttempt.getSacado().getNome() + " exceeded!");
				maioresSacadosAttempt.getLimiteDeConcentracao().setOk(false);
				break;
			}						
		}
		op.getResumo().setMaioresSacadosAttempt(maioresSacadosAttempt);
	}
	
	public static double sumMaiorValues(ArrayList<Double> maiores, double novoMaior)
	{
		double sum=0.0;
		
		for(int i=0;i<maiores.size()-1;i++)
		{
			sum = sum + maiores.get(i);
		}
		
		sum = sum + novoMaior;
		
		return sum;
	}
	
	public static void assessLimiteDeConcentracaoMaximoCedente(Operacao op, LimiteDeConcentracao limiteDeConcentracaoMaximoCedente)
	{
		boolean alreadyApprouved = HandlerOperacoes.alreadyApprouved(op);
		boolean approuvedByHand = HandlerOperacoes.approuvedByHand(op);
		op.getResumo().getCedenteAttempt().setLimiteDeConcentracao(limiteDeConcentracaoMaximoCedente);
		assessCedente(op.getResumo().getCedenteAttempt(),  alreadyApprouved, approuvedByHand);
	}

	public static void assessLimiteDeConcentracaoMaximoSacado(Operacao op, LimiteDeConcentracao limiteDeConcentracaoMaximoSacado)
	{
		boolean alreadyApprouved = HandlerOperacoes.alreadyApprouved(op);
		boolean approuvedByHand = HandlerOperacoes.approuvedByHand(op);
		for(SacadoAttempt sacadoAttempt:op.getResumo().getSacadosAttempt())
		{			
			sacadoAttempt.setLimiteDeConcentracao(limiteDeConcentracaoMaximoSacado);
			assessSacado(sacadoAttempt, alreadyApprouved, approuvedByHand);
		}
	}
	
	public static ArrayList<LimiteDeConcentracao> readStoredLimiteDeConcentracao(Operacao op, Connection conn)
	{
		ArrayList<LimiteDeConcentracao> limitesDeConcentracao = new ArrayList<LimiteDeConcentracao>();
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		idLimiteDeConcentracao, 
//		idTipoDeConcentracao, 
//		idFundo, 
//		valor
		
//		private int idLimiteDeConcentracao=0;
//		private int idTipoDeConcentracao=0;
//		private FundoDeInvestimento fundo=new FundoDeInvestimento();		
//		private double valor;
//		private String tipoDeConcentracao="";	


		String query = "SELECT * FROM limitedeconcentracao WHERE idFundo=" + op.getFundo().getIdFundo() + "  ORDER BY idTipoDeConcentracao";
//		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				LimiteDeConcentracao limite = new LimiteDeConcentracao();
				int idLimiteDeConcentracao = rs.getInt("idLimiteDeConcentracao");
				int idTipoDeConcentracao = rs.getInt("idTipoDeConcentracao");
				int idFundo = rs.getInt("idFundo");
				double valor = rs.getDouble("valor");
				
				limite.setIdLimite(idLimiteDeConcentracao);
				limite.setIdTipoDeLimite(idTipoDeConcentracao);
				limite.setFundo(new FundoDeInvestimento(idFundo,conn));
				limite.setValor(valor);
				
				Statement stmt2=null;
				try {
					stmt2 = (Statement) conn.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				query = "SELECT tipo FROM tipodeconcentracao WHERE idTipoDeConcentracao=" + idTipoDeConcentracao;
				
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
				
				limitesDeConcentracao.add(limite);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return limitesDeConcentracao;
	}

	
	public static void assessSacado(SacadoAttempt sacadoAttempt, boolean alreadyApprouved, boolean approvedByHand)
	{
		FundoDeInvestimento fundo = sacadoAttempt.getLimiteDeConcentracao().getFundo();
		
		System.out.println("\nAssessing ConcentracaoSacado for " + sacadoAttempt.getSacado().getNome() + " Trying: " + sacadoAttempt.getTotalOperar());
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulista.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		double limite=sacadoAttempt.getLimiteDeConcentracao().getValor();
		double maximoOperar = 0.0;
		double valorPresente = 0.0;
		double concentracao = 0.0;
		double novoMaximoOperar = 0.0;
		double novoValorPresente = 0.0;
		double novaConcentracao = 0.0;
		double patrimonioFundo=0.0;
		double excesso=0.0;
		
		String query = "SELECT idConcentracaoSacado, valorPresente FROM ConcentracaoSacado WHERE idEntidadeSacado=" + sacadoAttempt.getSacado().getIdEntidade() + " and idFundo=" + fundo.getIdFundo();
//		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int idConcentracaoSacado=0;
		try {
			while (rs.next())
			{
				idConcentracaoSacado = rs.getInt("idConcentracaoSacado");				
				valorPresente = rs.getDouble("valorPresente");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "SELECT valor FROM PLFundo WHERE idFundo="+fundo.getIdFundo()+" ORDER BY idPLFundo DESC LIMIT 1";
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while (rs.next())
			{
				patrimonioFundo = rs.getDouble("valor");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(idConcentracaoSacado!=0)
		{
//			System.out.println("idConcentracaoSacado: " + idConcentracaoSacado);

			if(alreadyApprouved && !approvedByHand)
			{
				novoValorPresente = valorPresente;
			}
			else
			{
				novoValorPresente = (valorPresente + sacadoAttempt.getTotalOperar());
			}
			
			concentracao = valorPresente/patrimonioFundo;
			maximoOperar = limite*patrimonioFundo-valorPresente;			
			novoMaximoOperar = limite*patrimonioFundo-novoValorPresente;
			novaConcentracao = novoValorPresente/patrimonioFundo;
			
			if(maximoOperar<0.0)
			{
				maximoOperar=0.0;
			}
			
			if(novoMaximoOperar<0.0)
			{
				novoMaximoOperar=0.0;
				excesso = novoValorPresente-limite*patrimonioFundo;
			}
			
			sacadoAttempt.setValorPresente(Math.round(valorPresente*100.0)/100.0);
			sacadoAttempt.setMaximoOperar(Math.round(maximoOperar*100.0)/100.0);			
			sacadoAttempt.setConcentracao(concentracao);
			sacadoAttempt.setNovoValorPresente(Math.round(novoValorPresente*100.0)/100.0);
			sacadoAttempt.setNovaConcentracao(novaConcentracao);
			sacadoAttempt.setNovoMaximoOperar(Math.round(novoMaximoOperar*100.0)/100.0);
			sacadoAttempt.setExcesso(Math.round(excesso*100.0)/100.0);
			
			System.out.println("PatrimonioFundo: " + patrimonioFundo);
			System.out.println("ValorPresente: " + sacadoAttempt.getValorPresente());
			System.out.println("Limite: " + limite);
			System.out.println("Concentracao: " + sacadoAttempt.getConcentracao());			
			System.out.println("MaximoParaOperar: " + sacadoAttempt.getMaximoOperar());
			System.out.println("NovoValorPresente: " + sacadoAttempt.getNovoValorPresente());
			System.out.println("NovaConcentracao: " + sacadoAttempt.getNovaConcentracao());
			System.out.println("NovoMaximoParaOperar: " + sacadoAttempt.getNovoMaximoOperar());
			System.out.println("Excesso: " + sacadoAttempt.getExcesso());
			
			if(novaConcentracao < limite)
			{
				System.out.println("Concentracao Sacado Ok!");		
				sacadoAttempt.getLimiteDeConcentracao().setOk(true);
			}
			else
			{
				System.out.println("Concentracao Sacado exceeded!");
			}			
		}
		else						
		{
			System.out.println("Sacado sem posição atual na carteira");
			valorPresente = 0.0;
			novoValorPresente = sacadoAttempt.getTotalOperar();
			concentracao = 0.0;
			novaConcentracao = novoValorPresente/patrimonioFundo;
			
			if(limite < concentracao)
			{
				novoMaximoOperar=0;
				System.out.println("Valor acima do limite");
			}
			else
			{
				maximoOperar=patrimonioFundo*limite;
				novoMaximoOperar=maximoOperar-valorPresente;
				if(novoMaximoOperar<0)
				{
					novoMaximoOperar=0;
					excesso=novoValorPresente-maximoOperar;
				}
				System.out.println("Valor dentro do limite");
				String stringValues = sacadoAttempt.getSacado().getIdEntidade() 
										+ "," + fundo.getIdFundo() 
										+ "," + valorPresente 
										+ "," + limite 
										+ "," + concentracao 
										+ "," + maximoOperar;
				String sql = "INSERT INTO `MVCapital`.`ConcentracaoSacado` (`idEntidadeSacado`,`idFundo`,`valorPresente`,`limite`,`concentracao`,`operar`) "
							+ "VALUES ("+ stringValues +")";
				
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				//System.out.println(sql);
	
				sacadoAttempt.setMaximoOperar(Math.round(maximoOperar*100.0)/100.0);
				sacadoAttempt.setValorPresente(Math.round(valorPresente*100.0)/100.0);
				sacadoAttempt.setConcentracao(concentracao);
				sacadoAttempt.setNovoValorPresente(Math.round(novoValorPresente*100.0)/100.0);
				sacadoAttempt.setNovaConcentracao(novaConcentracao);
				sacadoAttempt.setNovoMaximoOperar(Math.round(novoMaximoOperar*100.0)/100.0);
				sacadoAttempt.setExcesso(Math.round(excesso*100.0)/100.0);
				
				System.out.println("PatrimonioFundo: " + patrimonioFundo);
				System.out.println("ValorPresente: " + sacadoAttempt.getValorPresente());
				System.out.println("Limite: " + limite);
				System.out.println("Concentracao: " + sacadoAttempt.getConcentracao());			
				System.out.println("MaximoParaOperar: " + sacadoAttempt.getMaximoOperar());
				System.out.println("NovoValorPresente: " + sacadoAttempt.getNovoValorPresente());
				System.out.println("NovaConcentracao: " + sacadoAttempt.getNovaConcentracao());
				System.out.println("NovoMaximoParaOperar: " + sacadoAttempt.getNovoMaximoOperar());
				System.out.println("Excesso: " + sacadoAttempt.getExcesso());
			
				if(novaConcentracao < limite)
				{
					System.out.println("Concentracao Sacado Ok!");		
					sacadoAttempt.getLimiteDeConcentracao().setOk(true);
				}
				else
				{
					System.out.println("Concentracao Sacado exceeded!");
				}
			}
		}
	}

	public static void assessCedente(CedenteAttempt cedenteAttempt, boolean alreadyApprouved, boolean approvedByHand)
	{
		FundoDeInvestimento fundo = cedenteAttempt.getLimiteDeConcentracao().getFundo();
		
		System.out.println("\nAssessing ConcentracaoCedente for " + cedenteAttempt.getCedente().getNome() + " Trying: " + cedenteAttempt.getTotalOperar());
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulista.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		double limite=cedenteAttempt.getLimiteDeConcentracao().getValor();
		double maximoOperar = 0.0;
		double valorPresente = 0.0;
		double concentracao = 0.0;
		double novoMaximoOperar = 0.0;
		double novoValorPresente = 0.0;
		double novaConcentracao = 0.0;
		double patrimonioFundo=0.0;
		double excesso=0.0;
		
		String query = "SELECT idConcentracaoCedente, valorPresente FROM ConcentracaoCedente WHERE idEntidadeCedente=" + cedenteAttempt.getCedente().getIdEntidade() + " and idFundo=" + fundo.getIdFundo();
//		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int idConcentracaoCedente=0;
		try {
			while (rs.next())
			{
				idConcentracaoCedente = rs.getInt("idConcentracaoCedente");
				valorPresente = rs.getDouble("valorPresente");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "SELECT valor FROM PLFundo WHERE idFundo="+fundo.getIdFundo()+" ORDER BY idPLFundo DESC LIMIT 1";
//		System.out.println(query);
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while (rs.next())
			{
				patrimonioFundo = rs.getDouble("valor");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		System.out.println("Readed idConcentracaoCedente: " + idConcentracaoCedente);	
		if(idConcentracaoCedente!=0)
		{
			if(alreadyApprouved && !approvedByHand)
			{
				novoValorPresente = valorPresente;
			}
			else
			{
				novoValorPresente = (valorPresente + cedenteAttempt.getTotalOperar());
			}
			concentracao = valorPresente/patrimonioFundo;
			maximoOperar = limite*patrimonioFundo - valorPresente;
			novaConcentracao = novoValorPresente/patrimonioFundo;
			novoMaximoOperar = limite*patrimonioFundo - novoValorPresente;
			
			if(maximoOperar<0.0)
			{
				maximoOperar=0.0;
			}
			
			if(novoMaximoOperar<0.0)
			{
				novoMaximoOperar=0.0;
				excesso=novoValorPresente-limite*patrimonioFundo;
			}
			
			cedenteAttempt.setValorPresente(Math.round(valorPresente*100.0)/100.0);
			cedenteAttempt.setConcentracao(concentracao);
			cedenteAttempt.setMaximoOperar(Math.round(maximoOperar*100.0)/100.0);
			cedenteAttempt.setNovoValorPresente(Math.round(novoValorPresente*100.0)/100.0);
			cedenteAttempt.setNovaConcentracao(novaConcentracao);
			cedenteAttempt.setNovoMaximoOperar(Math.round(novoMaximoOperar*100.0)/100.0);
			cedenteAttempt.setExcesso(Math.round(excesso*100.0)/100.0);
			
			System.out.println("PatrimonioFundo: " + patrimonioFundo);
			System.out.println("ValorPresente: " + cedenteAttempt.getValorPresente());
			System.out.println("Limite: " + limite);
			System.out.println("Concentracao: " + cedenteAttempt.getConcentracao());			
			System.out.println("MaximoParaOperar: " + cedenteAttempt.getMaximoOperar());
			System.out.println("NovoValorPresente: " + cedenteAttempt.getNovoValorPresente());
			System.out.println("NovaConcentracao: " + cedenteAttempt.getNovaConcentracao());
			System.out.println("NovoMaximoParaOperar: " + cedenteAttempt.getNovoMaximoOperar());
			System.out.println("Excesso: " + cedenteAttempt.getExcesso());
			
			if(novaConcentracao < limite)
			{
				System.out.println("Concentracao Cedente Ok!");		
				cedenteAttempt.getLimiteDeConcentracao().setOk(true);
			}
			else
			{
				
				System.out.println("Concentracao Cedente exceeded!");
			}
		}
		else						
		{
			System.out.println("Cedente sem posição atual na carteira");
			valorPresente = 0;
			concentracao = 0;
			maximoOperar = limite*patrimonioFundo;
			novoValorPresente = cedenteAttempt.getTotalOperar();
			novaConcentracao = novoValorPresente/patrimonioFundo;
			novoMaximoOperar = limite*patrimonioFundo-novoValorPresente;

			if(maximoOperar<0.0)
			{
				maximoOperar=0.0;
			}
			
			if(novoMaximoOperar<0.0)
			{
				novoMaximoOperar=0.0;
				excesso=novoValorPresente-limite*patrimonioFundo;
			}
			
			String stringValues = cedenteAttempt.getCedente().getIdEntidade() 
								+ "," + fundo.getIdFundo() 
								+ "," + valorPresente 
								+ "," + limite 
								+ "," + concentracao 
								+ "," + maximoOperar;
			String sql = "INSERT INTO `MVCapital`.`ConcentracaoCedente` (`idEntidadeCedente`,`idFundo`,`valorPresente`,`limite`,`concentracao`,`operar`) "
					+ "		VALUES ("+ stringValues +")";
			
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//System.out.println(sql);
			
			cedenteAttempt.setValorPresente(Math.round(valorPresente*100.0)/100.0);
			cedenteAttempt.setConcentracao(concentracao);
			cedenteAttempt.setMaximoOperar(Math.round(maximoOperar*100.0)/100.0);
			cedenteAttempt.setNovoValorPresente(Math.round(novoValorPresente*100.0)/100.0);
			cedenteAttempt.setNovaConcentracao(novaConcentracao);
			cedenteAttempt.setNovoMaximoOperar(Math.round(novoMaximoOperar*100.0)/100.0);
			cedenteAttempt.setExcesso(Math.round(excesso*100.0)/100.0);
			
			System.out.println("PatrimonioFundo: " + patrimonioFundo);
			System.out.println("ValorPresente: " + cedenteAttempt.getValorPresente());
			System.out.println("Limite: " + limite);
			System.out.println("Concentracao: " + cedenteAttempt.getConcentracao());			
			System.out.println("MaximoParaOperar: " + cedenteAttempt.getMaximoOperar());
			System.out.println("NovoValorPresente: " + cedenteAttempt.getNovoValorPresente());
			System.out.println("NovaConcentracao: " + cedenteAttempt.getNovaConcentracao());
			System.out.println("NovoMaximoParaOperar: " + cedenteAttempt.getNovoMaximoOperar());
			System.out.println("Excesso: " + cedenteAttempt.getExcesso());
			
			if(novaConcentracao < limite)
			{
				System.out.println("Concentracao Cedente Ok!");		
				cedenteAttempt.getLimiteDeConcentracao().setOk(true);
			}			
			else
			{
				System.out.println("Concentracao Cedente exceeded!");
			}
		}
	}	
	
	
}
