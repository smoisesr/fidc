package limites;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvcapital.portalfidc.OperadorPortalPaulistaChrome;
import operation.CedenteAttempt;
import operation.HandlerOperacoes;
import operation.MaioresCedentesAttempt;
import operation.MaioresSacadosAttempt;
import operation.Operacao;
import operation.SacadoAttempt;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class HandlerLimiteDeConcentracao 
{

	public HandlerLimiteDeConcentracao() 
	{
	
	}
	
	public static void assessLimiteDeConcentracao(Operacao op, Connection conn)
	{
		ArrayList<LimiteDeConcentracao> limitesDeConcentracao = HandlerLimiteDeConcentracao.readStoredLimiteDeConcentracao(op, conn);
		System.out.println("Found " + limitesDeConcentracao.size() + " concentration limits");  //$NON-NLS-1$//$NON-NLS-2$
		
		for(LimiteDeConcentracao limiteDeConcentracao:limitesDeConcentracao)
		{
			limiteDeConcentracao.show();
			if(limiteDeConcentracao.getTipoDeLimite().equals("maximoCedente")) //$NON-NLS-1$
			{
				assessLimiteDeConcentracaoMaximoCedente(op, limiteDeConcentracao);
			}
			else if(limiteDeConcentracao.getTipoDeLimite().equals("maximoSacado")) //$NON-NLS-1$
			{
				assessLimiteDeConcentracaoMaximoSacado(op, limiteDeConcentracao);
			}
			else if(limiteDeConcentracao.getTipoDeLimite().equals("maximoQuatroMaioresCedentes")) //$NON-NLS-1$
			{
				assessLimiteDeConcentracaoMaximoQuatroMaioresCedentes(op, limiteDeConcentracao, conn);
			}
			else if(limiteDeConcentracao.getTipoDeLimite().equals("maximoQuatroMaioresSacados")) //$NON-NLS-1$
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
		
		String query = "SELECT valor FROM PLFundo WHERE idFundo="+fundo.getIdFundo()+" ORDER BY idPLFundo DESC LIMIT 1"; //$NON-NLS-1$ //$NON-NLS-2$
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while (rs.next())
			{
				patrimonioFundo = rs.getDouble("valor"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		query = "SELECT idEntidadeCedente, valorPresente FROM concentracaocedente WHERE idFundo=" + op.getFundo().getIdFundo() + " ORDER BY valorPresente DESC LIMIT 4"; //$NON-NLS-1$ //$NON-NLS-2$
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
				idsEntidadeCedente.add(rs.getInt("idEntidadeCedente")); //$NON-NLS-1$
				maiorValorPresente.add(rs.getDouble("valorPresente")); //$NON-NLS-1$
				sumValorPresente=sumValorPresente + maiorValorPresente.get(iValorPresente);
				iValorPresente=iValorPresente+1;				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Cedente: " + op.getResumo().getCedenteAttempt().getCedente().getNome()); //$NON-NLS-1$
		boolean isOnG4=false;
		
		for(int idEntidadeCedente:idsEntidadeCedente)
		{
			Entidade cedente = new Entidade(idEntidadeCedente,conn);
			maioresCedentesAttempt.getCedentes().add(cedente);
			System.out.println("Cedente4G: " + cedente.getNome()); //$NON-NLS-1$
			
			if(idEntidadeCedente==op.getResumo().getCedenteAttempt().getCedente().getIdEntidade())
			{
				isOnG4=true;
				break;
			}
		}

		valorPresente = sumValorPresente;
		
		System.out.println("IsOnG4: " + isOnG4); //$NON-NLS-1$

		if(alreadyApprouved && !approvedByHand)
		{
			novoValorPresente = valorPresente;
			maioresCedentesAttempt.setLimiteDeConcentracao(limiteDeConcentracaoMaximoQuatroMaioresCedentes);
		}
		else
		{		
			if(isOnG4)
			{
				System.out.println("Cedente entre os quatro maiores!"); //$NON-NLS-1$
				novoValorPresente = valorPresente  + op.getResumo().getCedenteAttempt().getTotalOperar();
				maioresCedentesAttempt.setLimiteDeConcentracao(limiteDeConcentracaoMaximoQuatroMaioresCedentes);
			}		
			else if(op.getResumo().getCedenteAttempt().getNovoValorPresente() >= maiorValorPresente.get(3))
			{
				System.out.println("Novo cedente entre os quatro maiores!"); //$NON-NLS-1$
				novoValorPresente = sumMaiorValues(maiorValorPresente, op.getResumo().getCedenteAttempt().getNovoValorPresente());
				maioresCedentesAttempt.setLimiteDeConcentracao(limiteDeConcentracaoMaximoQuatroMaioresCedentes);
				maioresCedentesAttempt.getCedentes().set(3, op.getResumo().getCedenteAttempt().getCedente());
			}
			else
			{
				System.out.println("Cedente fora dos quatro maiores!"); //$NON-NLS-1$
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
		
		System.out.println("PatrimonioFundo: " + patrimonioFundo); //$NON-NLS-1$
		System.out.println("ValorPresente: " + maioresCedentesAttempt.getValorPresente()); //$NON-NLS-1$
		System.out.println("Limite: " + limite); //$NON-NLS-1$
		System.out.println("Concentracao: " + maioresCedentesAttempt.getConcentracao());			 //$NON-NLS-1$
		System.out.println("MaximoParaOperar: " + maioresCedentesAttempt.getMaximoOperar()); //$NON-NLS-1$
		System.out.println("NovoValorPresente: " + maioresCedentesAttempt.getNovoValorPresente()); //$NON-NLS-1$
		System.out.println("NovaConcentracao: " + maioresCedentesAttempt.getNovaConcentracao()); //$NON-NLS-1$
		System.out.println("NovoMaximoParaOperar: " + maioresCedentesAttempt.getNovoMaximoOperar()); //$NON-NLS-1$
		System.out.println("Excesso: " + maioresCedentesAttempt.getExcesso()); //$NON-NLS-1$

		if(novaConcentracao < limite)
		{
			System.out.println("Concentracao Quatro Maiores Cedentes Ok!");		 //$NON-NLS-1$
			maioresCedentesAttempt.getLimiteDeConcentracao().setOk(true);
		}
		else
		{
			System.out.println("Concentracao Quatro Maiores Cedentes exceeded!"); //$NON-NLS-1$
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
		
		String query = "SELECT valor FROM PLFundo WHERE idFundo="+fundo.getIdFundo()+" ORDER BY idPLFundo DESC LIMIT 1"; //$NON-NLS-1$ //$NON-NLS-2$
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while (rs.next())
			{
				patrimonioFundo = rs.getDouble("valor"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		query = "SELECT idEntidadeSacado, valorPresente FROM concentracaosacado WHERE idFundo=" + op.getFundo().getIdFundo() + " ORDER BY valorPresente DESC LIMIT 4"; //$NON-NLS-1$ //$NON-NLS-2$
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
				idsEntidadeSacado.add(rs.getInt("idEntidadeSacado")); //$NON-NLS-1$
				maiorValorPresente.add(rs.getDouble("valorPresente")); //$NON-NLS-1$
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
					System.out.println("It is in G4 and have a concentration limit"); //$NON-NLS-1$
				}		
				else if(op.getResumo().getCedenteAttempt().getNovoValorPresente() >= maiorValorPresente.get(3))
				{
					novoValorPresente = sumMaiorValues(maiorValorPresente, op.getResumo().getCedenteAttempt().getNovoValorPresente());
					maioresSacadosAttempt.setLimiteDeConcentracao(limiteDeConcentracaoMaximoQuatroMaioresSacados);
					maioresSacadosAttempt.getSacados().set(3, sacadoAttempt.getSacado());
					System.out.println("Will be in G4 and have a concentration limit"); //$NON-NLS-1$
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
			
			System.out.println("PatrimonioFundo: " + patrimonioFundo); //$NON-NLS-1$
			System.out.println("ValorPresente: " + maioresSacadosAttempt.getValorPresente()); //$NON-NLS-1$
			System.out.println("Limite: " + limite); //$NON-NLS-1$
			System.out.println("Concentracao: " + maioresSacadosAttempt.getConcentracao());			 //$NON-NLS-1$
			System.out.println("MaximoParaOperar: " + maioresSacadosAttempt.getMaximoOperar()); //$NON-NLS-1$
			System.out.println("NovoValorPresente: " + maioresSacadosAttempt.getNovoValorPresente()); //$NON-NLS-1$
			System.out.println("NovaConcentracao: " + maioresSacadosAttempt.getNovaConcentracao()); //$NON-NLS-1$
			System.out.println("NovoMaximoParaOperar: " + maioresSacadosAttempt.getNovoMaximoOperar()); //$NON-NLS-1$
			System.out.println("Excesso: " + maioresSacadosAttempt.getExcesso()); //$NON-NLS-1$
			
			if(novaConcentracao < limite)
			{
				System.out.println("Concentracao Quatro Maiores Sacados para " + sacadoAttempt.getSacado().getNome() + " Ok!");		 //$NON-NLS-1$ //$NON-NLS-2$
				maioresSacadosAttempt.getLimiteDeConcentracao().setOk(true);
			}
			else
			{
				if(!isOnG4)
				{
					maioresSacadosAttempt.getLimiteDeConcentracao().setOk(true);
					continue;
				}
				System.out.println("Concentracao Quatro Maiores Sacados para " + sacadoAttempt.getSacado().getNome() + " exceeded!"); //$NON-NLS-1$ //$NON-NLS-2$
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


		String query = "SELECT * FROM limitedeconcentracao WHERE idFundo=" + op.getFundo().getIdFundo() + "  ORDER BY idTipoDeConcentracao"; //$NON-NLS-1$ //$NON-NLS-2$
//		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				LimiteDeConcentracao limite = new LimiteDeConcentracao();
				int idLimiteDeConcentracao = rs.getInt("idLimiteDeConcentracao"); //$NON-NLS-1$
				int idTipoDeConcentracao = rs.getInt("idTipoDeConcentracao"); //$NON-NLS-1$
				int idFundo = rs.getInt("idFundo"); //$NON-NLS-1$
				double valor = rs.getDouble("valor"); //$NON-NLS-1$
				
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

				query = "SELECT tipo FROM tipodeconcentracao WHERE idTipoDeConcentracao=" + idTipoDeConcentracao; //$NON-NLS-1$
				
//				System.out.println(query);
				ResultSet rs2 = null;
				try {
					rs2 = stmt2.executeQuery(query);
					while (rs2.next())
					{
						limite.setTipoDeLimite(rs2.getString("tipo")); //$NON-NLS-1$
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
		
		System.out.println("\nAssessing ConcentracaoSacado for " + sacadoAttempt.getSacado().getNome() + " Trying: " + sacadoAttempt.getTotalOperar()); //$NON-NLS-1$ //$NON-NLS-2$
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulistaChrome.conn.createStatement();
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
		
		String query = "SELECT idConcentracaoSacado, valorPresente FROM ConcentracaoSacado WHERE idEntidadeSacado=" + sacadoAttempt.getSacado().getIdEntidade() + " and idFundo=" + fundo.getIdFundo(); //$NON-NLS-1$ //$NON-NLS-2$
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
				idConcentracaoSacado = rs.getInt("idConcentracaoSacado");				 //$NON-NLS-1$
				valorPresente = rs.getDouble("valorPresente"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "SELECT valor FROM PLFundo WHERE idFundo="+fundo.getIdFundo()+" ORDER BY idPLFundo DESC LIMIT 1"; //$NON-NLS-1$ //$NON-NLS-2$
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while (rs.next())
			{
				patrimonioFundo = rs.getDouble("valor"); //$NON-NLS-1$
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
			
			System.out.println("PatrimonioFundo: " + patrimonioFundo); //$NON-NLS-1$
			System.out.println("ValorPresente: " + sacadoAttempt.getValorPresente()); //$NON-NLS-1$
			System.out.println("Limite: " + limite); //$NON-NLS-1$
			System.out.println("Concentracao: " + sacadoAttempt.getConcentracao());			 //$NON-NLS-1$
			System.out.println("MaximoParaOperar: " + sacadoAttempt.getMaximoOperar()); //$NON-NLS-1$
			System.out.println("NovoValorPresente: " + sacadoAttempt.getNovoValorPresente()); //$NON-NLS-1$
			System.out.println("NovaConcentracao: " + sacadoAttempt.getNovaConcentracao()); //$NON-NLS-1$
			System.out.println("NovoMaximoParaOperar: " + sacadoAttempt.getNovoMaximoOperar()); //$NON-NLS-1$
			System.out.println("Excesso: " + sacadoAttempt.getExcesso()); //$NON-NLS-1$
			
			if(novaConcentracao < limite)
			{
				System.out.println("Concentracao Sacado Ok!");		 //$NON-NLS-1$
				sacadoAttempt.getLimiteDeConcentracao().setOk(true);
			}
			else
			{
				System.out.println("Concentracao Sacado exceeded!"); //$NON-NLS-1$
			}			
		}
		else						
		{
			System.out.println("Sacado sem posição atual na carteira"); //$NON-NLS-1$
			valorPresente = 0.0;
			novoValorPresente = sacadoAttempt.getTotalOperar();
			concentracao = 0.0;
			novaConcentracao = novoValorPresente/patrimonioFundo;
			
			if(limite < concentracao)
			{
				novoMaximoOperar=0;
				System.out.println("Valor acima do limite"); //$NON-NLS-1$
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
				System.out.println("Valor dentro do limite"); //$NON-NLS-1$
				String stringValues = sacadoAttempt.getSacado().getIdEntidade() 
										+ "," + fundo.getIdFundo()  //$NON-NLS-1$
										+ "," + valorPresente  //$NON-NLS-1$
										+ "," + limite  //$NON-NLS-1$
										+ "," + concentracao  //$NON-NLS-1$
										+ "," + maximoOperar; //$NON-NLS-1$
				String sql = "INSERT INTO `MVCapital`.`ConcentracaoSacado` (`idEntidadeSacado`,`idFundo`,`valorPresente`,`limite`,`concentracao`,`operar`) " //$NON-NLS-1$
							+ "VALUES ("+ stringValues +")"; //$NON-NLS-1$ //$NON-NLS-2$
				
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
				
				System.out.println("PatrimonioFundo: " + patrimonioFundo); //$NON-NLS-1$
				System.out.println("ValorPresente: " + sacadoAttempt.getValorPresente()); //$NON-NLS-1$
				System.out.println("Limite: " + limite); //$NON-NLS-1$
				System.out.println("Concentracao: " + sacadoAttempt.getConcentracao());			 //$NON-NLS-1$
				System.out.println("MaximoParaOperar: " + sacadoAttempt.getMaximoOperar()); //$NON-NLS-1$
				System.out.println("NovoValorPresente: " + sacadoAttempt.getNovoValorPresente()); //$NON-NLS-1$
				System.out.println("NovaConcentracao: " + sacadoAttempt.getNovaConcentracao()); //$NON-NLS-1$
				System.out.println("NovoMaximoParaOperar: " + sacadoAttempt.getNovoMaximoOperar()); //$NON-NLS-1$
				System.out.println("Excesso: " + sacadoAttempt.getExcesso()); //$NON-NLS-1$
			
				if(novaConcentracao < limite)
				{
					System.out.println("Concentracao Sacado Ok!");		 //$NON-NLS-1$
					sacadoAttempt.getLimiteDeConcentracao().setOk(true);
				}
				else
				{
					System.out.println("Concentracao Sacado exceeded!"); //$NON-NLS-1$
				}
			}
		}
	}

	public static void assessCedente(CedenteAttempt cedenteAttempt, boolean alreadyApprouved, boolean approvedByHand)
	{
		FundoDeInvestimento fundo = cedenteAttempt.getLimiteDeConcentracao().getFundo();
		
		System.out.println("\nAssessing ConcentracaoCedente for " + cedenteAttempt.getCedente().getNome() + " Trying: " + cedenteAttempt.getTotalOperar()); //$NON-NLS-1$ //$NON-NLS-2$
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulistaChrome.conn.createStatement();
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
		
		String query = "SELECT idConcentracaoCedente, valorPresente FROM ConcentracaoCedente WHERE idEntidadeCedente=" + cedenteAttempt.getCedente().getIdEntidade() + " and idFundo=" + fundo.getIdFundo(); //$NON-NLS-1$ //$NON-NLS-2$
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
				idConcentracaoCedente = rs.getInt("idConcentracaoCedente"); //$NON-NLS-1$
				valorPresente = rs.getDouble("valorPresente"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "SELECT valor FROM PLFundo WHERE idFundo="+fundo.getIdFundo()+" ORDER BY idPLFundo DESC LIMIT 1"; //$NON-NLS-1$ //$NON-NLS-2$
//		System.out.println(query);
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while (rs.next())
			{
				patrimonioFundo = rs.getDouble("valor"); //$NON-NLS-1$
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
			
			System.out.println("PatrimonioFundo: " + patrimonioFundo); //$NON-NLS-1$
			System.out.println("ValorPresente: " + cedenteAttempt.getValorPresente()); //$NON-NLS-1$
			System.out.println("Limite: " + limite); //$NON-NLS-1$
			System.out.println("Concentracao: " + cedenteAttempt.getConcentracao());			 //$NON-NLS-1$
			System.out.println("MaximoParaOperar: " + cedenteAttempt.getMaximoOperar()); //$NON-NLS-1$
			System.out.println("NovoValorPresente: " + cedenteAttempt.getNovoValorPresente()); //$NON-NLS-1$
			System.out.println("NovaConcentracao: " + cedenteAttempt.getNovaConcentracao()); //$NON-NLS-1$
			System.out.println("NovoMaximoParaOperar: " + cedenteAttempt.getNovoMaximoOperar()); //$NON-NLS-1$
			System.out.println("Excesso: " + cedenteAttempt.getExcesso()); //$NON-NLS-1$
			
			if(novaConcentracao < limite)
			{
				System.out.println("Concentracao Cedente Ok!");		 //$NON-NLS-1$
				cedenteAttempt.getLimiteDeConcentracao().setOk(true);
			}
			else
			{
				
				System.out.println("Concentracao Cedente exceeded!"); //$NON-NLS-1$
			}
		}
		else						
		{
			System.out.println("Cedente sem posição atual na carteira"); //$NON-NLS-1$
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
								+ "," + fundo.getIdFundo()  //$NON-NLS-1$
								+ "," + valorPresente  //$NON-NLS-1$
								+ "," + limite  //$NON-NLS-1$
								+ "," + concentracao  //$NON-NLS-1$
								+ "," + maximoOperar; //$NON-NLS-1$
			String sql = "INSERT INTO `MVCapital`.`ConcentracaoCedente` (`idEntidadeCedente`,`idFundo`,`valorPresente`,`limite`,`concentracao`,`operar`) " //$NON-NLS-1$
					+ "		VALUES ("+ stringValues +")"; //$NON-NLS-1$ //$NON-NLS-2$
			
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
			
			System.out.println("PatrimonioFundo: " + patrimonioFundo); //$NON-NLS-1$
			System.out.println("ValorPresente: " + cedenteAttempt.getValorPresente()); //$NON-NLS-1$
			System.out.println("Limite: " + limite); //$NON-NLS-1$
			System.out.println("Concentracao: " + cedenteAttempt.getConcentracao());			 //$NON-NLS-1$
			System.out.println("MaximoParaOperar: " + cedenteAttempt.getMaximoOperar()); //$NON-NLS-1$
			System.out.println("NovoValorPresente: " + cedenteAttempt.getNovoValorPresente()); //$NON-NLS-1$
			System.out.println("NovaConcentracao: " + cedenteAttempt.getNovaConcentracao()); //$NON-NLS-1$
			System.out.println("NovoMaximoParaOperar: " + cedenteAttempt.getNovoMaximoOperar()); //$NON-NLS-1$
			System.out.println("Excesso: " + cedenteAttempt.getExcesso()); //$NON-NLS-1$
			
			if(novaConcentracao < limite)
			{
				System.out.println("Concentracao Cedente Ok!");		 //$NON-NLS-1$
				cedenteAttempt.getLimiteDeConcentracao().setOk(true);
			}			
			else
			{
				System.out.println("Concentracao Cedente exceeded!"); //$NON-NLS-1$
			}
		}
	}	
	
	
}
