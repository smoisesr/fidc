package mvcapital.operation;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebElement;

import mvcapital.bancopaulista.Movimentacao;
import mvcapital.conta.Conta;
import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.limites.HandlerLimites;
import mvcapital.portalfidc.OperadorPortalPaulista;
import mvcapital.relatorio.cessao.HandlerTitulo;
import mvcapital.relatorio.cessao.Relatorio;
import mvcapital.utils.SshClient;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class HandlerOperacoes 
{
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdfcd = new SimpleDateFormat("yyyy-MM-dd");
	public HandlerOperacoes() 
	{
		
	}
/**
 * This method store the whole operation details
 * @param op
 * @param conn
 */
	public static void store(Operacao op, Connection conn)
	{
		
		
//		idFundo, 
//		nomeArquivo, 
//		idEntidadeCedente, 
//		totalAquisicao, 
//		totalNominal, 
//		dataDeEntrada, 
		
//		remessaComAdiantamento, 
//		recompraComAdiantamento, 
//		reembolsoContaInterna,
		
//		remessaSemAdiantamento, 
//		recompraSemAdiantamento, 
//		reembolsoContaExterna,
		
//		valorTEDContaInterna, 
//		valorTEDContaExterna1, 
//		valorTEDContaExterna2,
//		idContaInterna 
//		idContaExterna1 
//		idContaExterna2
		System.out.println("-----------------------------");
		System.out.println("Storing Operation " + op.getNomeArquivo());		
		System.out.println("-----------------------------");
		
		int idContaInterna=op.getContaInterna().getIdConta(); 
		int idContaExterna1=op.getContaExterna1().getIdConta(); 
		int idContaExterna2=op.getContaExterna2().getIdConta();
		
		if(op.getContaInterna().getIdConta()!=0)
		{
			System.out.println("Conta 1");
			System.out.println("idCedente: " + op.getCedente().getIdEntidade());
			System.out.println("Banco: " + op.getContaInterna().getCodigoServidor());
			System.out.println("Agencia: " + op.getContaInterna().getAgencia());
			System.out.println("NroConta: " + op.getContaInterna().getNumero());
//			idContaInterna = Conta.getIdConta(op.getCedente().getIdEntidade(), op.getContaInterna().getCodigoServidor(), op.getContaInterna().getAgencia(), op.getContaInterna().getNumero(), conn);
		}
		
		if(op.getContaExterna1().getIdConta()!=0)
		{
			System.out.println("Conta 1");
			System.out.println("idCedente: " + op.getCedente().getIdEntidade());
			System.out.println("Banco: " + op.getContaExterna1().getCodigoServidor());
			System.out.println("Agencia: " + op.getContaExterna1().getAgencia());
			System.out.println("NroConta: " + op.getContaExterna1().getNumero());
//			idContaExterna1 = Conta.getIdConta(op.getCedente().getIdEntidade(), op.getContaExterna1().getCodigoServidor(), op.getContaExterna1().getAgencia(), op.getContaExterna1().getNumero(), conn);
		}
		if(op.getContaExterna1().getIdConta()!=0)
		{
			System.out.println("Conta 2");
			System.out.println("idCedente: " + op.getCedente().getIdEntidade());
			System.out.println("Banco: " + op.getContaExterna2().getCodigoServidor());
			System.out.println("Agencia: " + op.getContaExterna2().getAgencia());
			System.out.println("NroConta: " + op.getContaExterna2().getNumero());
//			idContaExterna1 = Conta.getIdConta(op.getCedente().getIdEntidade(), op.getContaExterna2().getCodigoServidor(), op.getContaExterna2().getAgencia(), op.getContaExterna2().getNumero(),conn);
		}
		
		System.out.println("idFundo: " + op.getFundo().getIdFundo());
		System.out.println("nomeArquivo: " + op.getNomeArquivo());
		System.out.println("idEntidadeCedente: " + op.getCedente().getIdEntidade());
		System.out.println("totalAquisicao: " + op.getTotalAquisicao());
		System.out.println("totalNominal: " + op.getTotalNominal());
		System.out.println("dataDeEntrada: " + sdfd.format(op.getDataDeEntrada()) + " " + op.getDataDeEntrada());
		System.out.println("remessaComAdiantamento: " + op.getValorTotalComAdiantamento());
		System.out.println("recompraComAdiantamento: " + op.getValorRecompraComAdiantamento());
		System.out.println("reembolsoComAdiantamento: " + op.getReembolsoContaEspecial());
		System.out.println("remessaSemAdiantamento: " + op.getValorRemessaSemAdiantamento());
		System.out.println("recompraSemAdiantamento: " + op.getValorRecompraSemAdiantamento());
		System.out.println("reembolsoSemAdiantamento: " + op.getReembolsoContaNormal());
		System.out.println("valorTEDContaInterna: " + op.getValorTotalContaCorrenteInternaPaulista());
		System.out.println("valorTEDContaExterna1: " + op.getValorTotalCedenteSemAdiantamento1());
		System.out.println("valorTEDContaExterna2: " + op.getValorTotalCedenteSemAdiantamento2());
		System.out.println("idContaInterna: " + op.getContaInterna().getIdConta());
		System.out.println("idContaExterna1: " + op.getContaExterna1().getIdConta());
		System.out.println("idContaExterna2: " + op.getContaExterna2().getIdConta());
		
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int idOperacao=0;
		String query = "SELECT idOperacao FROM operacao WHERE idFundo=" + op.getFundo().getIdFundo()
				       + " AND nomeArquivo = \"" + op.getNomeArquivo() + "\""
				       + " AND idEntidadeCedente = " + op.getCedente().getIdEntidade()
				       + " AND totalAquisicao = " + op.getTotalAquisicao()
				       + " AND dataDeEntrada = " + sdfd.format(op.getDataDeEntrada())
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
				idOperacao = rs.getInt("idOperacao");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(idOperacao!=0)
		{
			System.out.println("Operation already registered");
//			Charge data from database!!
			
			op.setIdOperacao(idOperacao);			
			HandlerStatus.store(op, conn);
			return;
		}

		System.out.println("idContaInterna: " + idContaInterna);
		System.out.println("idContaExterna1: " + idContaExterna1);
		System.out.println("idContaExterna2: " + idContaExterna2);
		
		String sql = "";
		if(idContaInterna!=0 && idContaExterna1!=0 && idContaExterna2!=0)
		{	
			sql = "INSERT INTO `mvcapital`.`operacao` (`idFundo`,`nomeArquivo`,`idEntidadeCedente`,`totalAquisicao`,`totalNominal`,`dataDeEntrada`,`remessaComAdiantamento`,`recompraComAdiantamento`,`reembolsoContaInterna`,`remessaSemAdiantamento`,`recompraSemAdiantamento`,`reembolsoContaExterna`,`valorTEDContaInterna`,`valorTEDContaExterna1`,`valorTEDContaExterna2`,`idContaInterna`,`idContaExterna1`,`idContaExterna2`) "
					+ "VALUES ("
					+ op.getFundo().getIdFundo()
					+ "," + "\"" + op.getNomeArquivo() + "\""
					+ "," + op.getCedente().getIdEntidade()
					+ "," + op.getTotalAquisicao()
					+ "," + op.getTotalNominal()
					+ "," + "'" + sdfd.format(op.getDataDeEntrada()) + "'"
					+ "," + op.getValorTotalComAdiantamento()
					+ "," + op.getValorRecompraComAdiantamento()
					+ "," + op.getReembolsoContaEspecial()
					+ "," + op.getValorRemessaSemAdiantamento()
					+ "," + op.getValorRecompraSemAdiantamento()
					+ "," + op.getReembolsoContaNormal()
					+ "," + op.getValorTotalContaCorrenteInternaPaulista()
					+ "," + op.getValorTotalCedenteSemAdiantamento1()
					+ "," + op.getValorTotalCedenteSemAdiantamento2()
					+ "," + idContaInterna
					+ "," + idContaExterna1
					+ "," + idContaExterna2
					+ ")";
		}
		else if(idContaInterna==0 && idContaExterna1!=0)
		{	
			if(idContaExterna2!=0)
			{
    			sql = "INSERT INTO `mvcapital`.`operacao` (`idFundo`,`nomeArquivo`,`idEntidadeCedente`,`totalAquisicao`,`totalNominal`,`dataDeEntrada`,`remessaComAdiantamento`,`recompraComAdiantamento`,`reembolsoContaInterna`,`remessaSemAdiantamento`,`recompraSemAdiantamento`,`reembolsoContaExterna`,`valorTEDContaInterna`,`valorTEDContaExterna1`,`valorTEDContaExterna2`,`idContaExterna1`,`idContaExterna2`) "
    					+ "VALUES ("
    					+ op.getFundo().getIdFundo()
    					+ "," + "\"" + op.getNomeArquivo() + "\""
    					+ "," + op.getCedente().getIdEntidade()
    					+ "," + op.getTotalAquisicao()
    					+ "," + op.getTotalNominal()
    					+ "," + "'" + sdfd.format(op.getDataDeEntrada()) + "'"
    					+ "," + op.getValorTotalComAdiantamento()
    					+ "," + op.getValorRecompraComAdiantamento()
    					+ "," + op.getReembolsoContaEspecial()
    					+ "," + op.getValorRemessaSemAdiantamento()
    					+ "," + op.getValorRecompraSemAdiantamento()
    					+ "," + op.getReembolsoContaNormal()
    					+ "," + op.getValorTotalContaCorrenteInternaPaulista()
    					+ "," + op.getValorTotalCedenteSemAdiantamento1()
    					+ "," + op.getValorTotalCedenteSemAdiantamento2()
    					+ "," + idContaExterna1
    					+ "," + idContaExterna2
    					+ ")";	    				
			}
			else
			{
    			sql = "INSERT INTO `mvcapital`.`operacao` (`idFundo`,`nomeArquivo`,`idEntidadeCedente`,`totalAquisicao`,`totalNominal`,`dataDeEntrada`,`remessaComAdiantamento`,`recompraComAdiantamento`,`reembolsoContaInterna`,`remessaSemAdiantamento`,`recompraSemAdiantamento`,`reembolsoContaExterna`,`valorTEDContaInterna`,`valorTEDContaExterna1`,`valorTEDContaExterna2`,`idContaExterna1`) "
    					+ "VALUES ("
    					+ op.getFundo().getIdFundo()
    					+ "," + "\"" + op.getNomeArquivo() + "\""
    					+ "," + op.getCedente().getIdEntidade()
    					+ "," + op.getTotalAquisicao()
    					+ "," + op.getTotalNominal()
    					+ "," + "'" + sdfd.format(op.getDataDeEntrada()) + "'"
    					+ "," + op.getValorTotalComAdiantamento()
    					+ "," + op.getValorRecompraComAdiantamento()
    					+ "," + op.getReembolsoContaEspecial()
    					+ "," + op.getValorRemessaSemAdiantamento()
    					+ "," + op.getValorRecompraSemAdiantamento()
    					+ "," + op.getReembolsoContaNormal()
    					+ "," + op.getValorTotalContaCorrenteInternaPaulista()
    					+ "," + op.getValorTotalCedenteSemAdiantamento1()
    					+ "," + op.getValorTotalCedenteSemAdiantamento2()
    					+ "," + idContaExterna1
    					+ ")";	    				
				
			}
		}
		
		System.out.println(sql);
		stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}			

		query = "SELECT idOperacao FROM operacao WHERE idFundo=" + op.getFundo().getIdFundo()
				       + " AND nomeArquivo = \"" + op.getNomeArquivo() + "\""
				       + " AND idEntidadeCedente = " + op.getCedente().getIdEntidade()
				       + " AND totalAquisicao = " + op.getTotalAquisicao()
				       + " AND dataDeEntrada = " + sdfd.format(op.getDataDeEntrada())
				       ;
		
		System.out.println(query);
		rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idOperacao = rs.getInt("idOperacao");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		op.setIdOperacao(idOperacao);
	}

	public static void updateStore(Operacao op, Connection conn)
	{
//		idFundo, 
//		nomeArquivo, 
//		idEntidadeCedente, 
//		totalAquisicao, 
//		totalNominal, 
//		dataDeEntrada, 
		
//		remessaComAdiantamento, 
//		recompraComAdiantamento, 
//		reembolsoContaInterna,
		
//		remessaSemAdiantamento, 
//		recompraSemAdiantamento, 
//		reembolsoContaExterna,
		
//		valorTEDContaInterna, 
//		valorTEDContaExterna1, 
//		valorTEDContaExterna2,
//		idContaInterna 
//		idContaExterna1 
//		idContaExterna2
		System.out.println("-----------------------------");
		System.out.println("Storing Update Operation " + op.getNomeArquivo());		
		System.out.println("-----------------------------");
		
		int idContaInterna=op.getContaInterna().getIdConta(); 
		int idContaExterna1=op.getContaExterna1().getIdConta(); 
		int idContaExterna2=op.getContaExterna2().getIdConta();
		
		if(op.getContaInterna().getIdConta()!=0)
		{
			System.out.println("Conta 1");
			System.out.println("idCedente: " + op.getCedente().getIdEntidade());
			System.out.println("Banco: " + op.getContaInterna().getCodigoServidor());
			System.out.println("Agencia: " + op.getContaInterna().getAgencia());
			System.out.println("NroConta: " + op.getContaInterna().getNumero());
//			idContaInterna = Conta.getIdConta(op.getCedente().getIdEntidade(), op.getContaInterna().getCodigoServidor(), op.getContaInterna().getAgencia(), op.getContaInterna().getNumero(), conn);
		}
		
		if(op.getContaExterna1().getIdConta()!=0)
		{
			System.out.println("Conta 1");
			System.out.println("idCedente: " + op.getCedente().getIdEntidade());
			System.out.println("Banco: " + op.getContaExterna1().getCodigoServidor());
			System.out.println("Agencia: " + op.getContaExterna1().getAgencia());
			System.out.println("NroConta: " + op.getContaExterna1().getNumero());
//			idContaExterna1 = Conta.getIdConta(op.getCedente().getIdEntidade(), op.getContaExterna1().getCodigoServidor(), op.getContaExterna1().getAgencia(), op.getContaExterna1().getNumero(), conn);
		}
		if(op.getContaExterna2().getIdConta()!=0)
		{
			System.out.println("Conta 2");
			System.out.println("idCedente: " + op.getCedente().getIdEntidade());
			System.out.println("Banco: " + op.getContaExterna2().getCodigoServidor());
			System.out.println("Agencia: " + op.getContaExterna2().getAgencia());
			System.out.println("NroConta: " + op.getContaExterna2().getNumero());
//			idContaExterna1 = Conta.getIdConta(op.getCedente().getIdEntidade(), op.getContaExterna2().getCodigoServidor(), op.getContaExterna2().getAgencia(), op.getContaExterna2().getNumero(),conn);
		}
		
		System.out.println("idFundo: " + op.getFundo().getIdFundo());
		System.out.println("nomeArquivo: " + op.getNomeArquivo());
		System.out.println("idEntidadeCedente: " + op.getCedente().getIdEntidade());
		System.out.println("totalAquisicao: " + op.getTotalAquisicao());
		System.out.println("totalNominal: " + op.getTotalNominal());
		System.out.println("dataDeEntrada: " + sdfd.format(op.getDataDeEntrada()) + " " + op.getDataDeEntrada());
		System.out.println("remessaComAdiantamento: " + op.getValorTotalComAdiantamento());
		System.out.println("recompraComAdiantamento: " + op.getValorRecompraComAdiantamento());
		System.out.println("reembolsoComAdiantamento: " + op.getReembolsoContaEspecial());
		System.out.println("remessaSemAdiantamento: " + op.getValorRemessaSemAdiantamento());
		System.out.println("recompraSemAdiantamento: " + op.getValorRecompraSemAdiantamento());
		System.out.println("reembolsoSemAdiantamento: " + op.getReembolsoContaNormal());
		System.out.println("valorTEDContaInterna: " + op.getValorTotalContaCorrenteInternaPaulista());
		System.out.println("valorTEDContaExterna1: " + op.getValorTotalCedenteSemAdiantamento1());
		System.out.println("valorTEDContaExterna2: " + op.getValorTotalCedenteSemAdiantamento2());
		System.out.println("idContaInterna: " + op.getContaInterna().getIdConta());
		System.out.println("idContaExterna1: " + op.getContaExterna1().getIdConta());
		System.out.println("idContaExterna2: " + op.getContaExterna2().getIdConta());
		
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int idOperacao=0;
		String query = "SELECT idOperacao FROM operacao WHERE idFundo=" + op.getFundo().getIdFundo()
				       + " AND nomeArquivo = \"" + op.getNomeArquivo() + "\""
				       + " AND idEntidadeCedente = " + op.getCedente().getIdEntidade()
				       + " AND totalAquisicao = " + op.getTotalAquisicao()
				       + " AND dataDeEntrada = " + sdfd.format(op.getDataDeEntrada())
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
				idOperacao = rs.getInt("idOperacao");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(idOperacao!=0)
		{
			System.out.println("Operation already registered");
//			Charge data from database!!
			
			op.setIdOperacao(idOperacao);			
			HandlerStatus.store(op, conn);
//			return;
		}

		System.out.println("idContaInterna: " + idContaInterna);
		System.out.println("idContaExterna1: " + idContaExterna1);
		System.out.println("idContaExterna2: " + idContaExterna2);
		
		if(idContaInterna==0)
		{
			System.out.println("idContaInterna==0");
		}
		if(idContaExterna1==0)
		{
			System.out.println("idContaExterna1==0");
		}
		if(idContaExterna2==0)
		{
			System.out.println("idContaExterna2==0");
		}
		if(idContaInterna==0 && idContaExterna1!=0)
		{
			System.out.println("idContaInterna==0 and idContaExterna1!=0");
		}
		
		
		String sql = "";
		if(idContaInterna!=0 && idContaExterna1!=0 && idContaExterna2!=0)
		{	
	
			sql = "UPDATE `mvcapital`.`operacao`"
					+ " " + "SET"
					+ " " + "`remessaComAdiantamento` = " + op.getValorTotalAquisicaoComAdiantamento()
					+ "," + "`recompraComAdiantamento` = " + op.getValorRecompraComAdiantamento()
					+ "," + "`reembolsoContaInterna` = " + op.getReembolsoContaEspecial()
					+ "," + "`remessaSemAdiantamento` = " + op.getValorRemessaSemAdiantamento()
					+ "," + "`recompraSemAdiantamento` = " + op.getValorRecompraSemAdiantamento()
					+ "," + "`reembolsoContaExterna` = " + op.getReembolsoContaNormal()
					+ "," + "`valorTEDContaInterna` = " + op.getValorTotalComAdiantamento()
					+ "," + "`valorTEDContaExterna1` = " + op.getValorTotalCedenteSemAdiantamento1()
					+ "," + "`valorTEDContaExterna2` = " + op.getValorTotalCedenteSemAdiantamento2()
					+ "," + "`idContaInterna` = " + op.getContaInterna().getIdConta()
					+ "," + "`idContaExterna1` = " + op.getContaExterna1().getIdConta()
					+ "," + "`idContaExterna2` = " + op.getContaExterna2().getIdConta() 
					+ " " + "WHERE `idOperacao` = " + op.getIdOperacao();
		}
		
		if(idContaInterna==0 && idContaExterna1!=0)
		{	
			System.out.println("- idContaInterna==0 && idContaExterna1!=0 -");
			if(idContaExterna2!=0)
			{
				System.out.println("\tidContaExterna2!=0");
				sql = "UPDATE `mvcapital`.`operacao`"
						+ " " + "SET"
						+ " " + "`remessaSemAdiantamento` = " + op.getValorRemessaSemAdiantamento()
						+ "," + "`recompraSemAdiantamento` = " + op.getValorRecompraSemAdiantamento()
						+ "," + "`reembolsoContaExterna` = " + op.getReembolsoContaNormal()
						+ "," + "`valorTEDContaExterna1` = " + op.getValorTotalCedenteSemAdiantamento1()
						+ "," + "`valorTEDContaExterna2` = " + op.getValorTotalCedenteSemAdiantamento2()
						+ "," + "`idContaExterna1` = " + op.getContaExterna1().getIdConta()
						+ "," + "`idContaExterna2` = " + op.getContaExterna2().getIdConta() 
						+ " " + "WHERE `idOperacao` = " + op.getIdOperacao();
				
			}
			else
			{
				System.out.println("\tidContaExterna2==0");
				sql = "UPDATE `mvcapital`.`operacao`"
						+ " " + "SET"
						+ " " + "`remessaSemAdiantamento` = " + op.getValorRemessaSemAdiantamento()
						+ "," + "`recompraSemAdiantamento` = " + op.getValorRecompraSemAdiantamento()
						+ "," + "`reembolsoContaExterna` = " + op.getReembolsoContaNormal()
						+ "," + "`valorTEDContaExterna1` = " + op.getValorTotalCedenteSemAdiantamento1()
						+ "," + "`idContaExterna1` = " + op.getContaExterna1().getIdConta()
						+ " " + "WHERE `idOperacao` = " + op.getIdOperacao();				
			}
		}
		
		System.out.println(sql);
		stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}			

		query = "SELECT idOperacao FROM operacao WHERE idFundo=" + op.getFundo().getIdFundo()
				       + " AND nomeArquivo = \"" + op.getNomeArquivo() + "\""
				       + " AND idEntidadeCedente = " + op.getCedente().getIdEntidade()
				       + " AND totalAquisicao = " + op.getTotalAquisicao()
				       + " AND dataDeEntrada = " + sdfd.format(op.getDataDeEntrada())
				       ;
		
		System.out.println(query);
		rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idOperacao = rs.getInt("idOperacao");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		op.setIdOperacao(idOperacao);
		if(op.getContaInterna().getIdConta()!=0
				|| op.getContaExterna1().getIdConta() !=0
				)
		{
			op.setHaveTransferDetailsChecked(true);
		}
	}
	
	
	public static void assessOperation(Operacao op, Connection conn)
	{
		System.out.println("Assessing Operation: " + op.getFundo().getNome() + " " + op.getCedente().getNome() + " " + op.getNomeArquivo());
		HandlerLimites.assessLimites(op, conn);
		System.out.println("Assessing Operation: " + op.getNomeArquivo() + " finished!");
	}	
	
	public static void updateConcentracao(Operacao op)
	{
		OperationSummary operacaoResumo=op.getResumo();
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulista.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(SacadoAttempt sacadoAttempt:operacaoResumo.getSacadosAttempt())
		{
			double novoMaximoOperar = sacadoAttempt.getMaximoOperar()-sacadoAttempt.getTotalOperar(); 
			String sql = "UPDATE `MVCapital`.`ConcentracaoSacado` SET "
						+ "`valorPresente` = " + sacadoAttempt.getNovoValorPresente()
						+ ",`concentracao` = " + sacadoAttempt.getNovaConcentracao()
						+ ",`operar` = " + novoMaximoOperar
						+ ",`updateTime` = CURRENT_TIMESTAMP"
						+ " WHERE `idEntidadeSacado` = " + sacadoAttempt.getSacado().getIdEntidade()
						+ " and `idFundo` = " + operacaoResumo.getFundo().getIdFundo()
						;
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		double novoMaximoOperar = operacaoResumo.getCedenteAttempt().getMaximoOperar()-operacaoResumo.getCedenteAttempt().getTotalOperar(); 
		String sql = "UPDATE `MVCapital`.`ConcentracaoCedente` SET "
					+ "`valorPresente` = " + operacaoResumo.getCedenteAttempt().getNovoValorPresente()
					+ ",`concentracao` = " + operacaoResumo.getCedenteAttempt().getNovaConcentracao()
					+ ",`operar` = " + novoMaximoOperar
					+ ",`updateTime` = CURRENT_TIMESTAMP"
					+ " WHERE `idEntidadeCedente` = " + operacaoResumo.getCedenteAttempt().getCedente().getIdEntidade()
					+ " and `idFundo` = " + operacaoResumo.getFundo().getIdFundo()
					;
		System.out.println(sql);
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public static void updateConcentracaoCancelado(Operacao op)
	{
		OperationSummary operacaoResumo=op.getResumo();
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulista.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(SacadoAttempt sacadoAttempt:operacaoResumo.getSacadosAttempt())
		{
			double novoMaximoOperar = sacadoAttempt.getMaximoOperar()+sacadoAttempt.getTotalOperar();
			double novoValorPresente = sacadoAttempt.getNovoValorPresente() - sacadoAttempt.getTotalOperar();
			double novaConcentracao = novoValorPresente*sacadoAttempt.getNovaConcentracao()/sacadoAttempt.getNovoValorPresente();
			String sql = "UPDATE `MVCapital`.`ConcentracaoSacado` SET "
						+ "`valorPresente` = " + novoValorPresente
						+ ",`concentracao` = " + novaConcentracao
						+ ",`operar` = " + novoMaximoOperar
						+ ",`updateTime` = CURRENT_TIMESTAMP"
						+ " WHERE `idEntidadeSacado` = " + sacadoAttempt.getSacado().getIdEntidade()
						+ " and `idFundo` = " + operacaoResumo.getFundo().getIdFundo()
						;
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		double novoMaximoOperar = operacaoResumo.getCedenteAttempt().getMaximoOperar()+operacaoResumo.getCedenteAttempt().getTotalOperar();
		
		double novoValorPresente = operacaoResumo.getCedenteAttempt().getNovoValorPresente() - operacaoResumo.getCedenteAttempt().getTotalOperar();
		double novaConcentracao = novoValorPresente*operacaoResumo.getCedenteAttempt().getNovaConcentracao()/operacaoResumo.getCedenteAttempt().getNovoValorPresente();
		
		String sql = "UPDATE `MVCapital`.`ConcentracaoCedente` SET "
					+ "`valorPresente` = " + novoValorPresente
					+ ",`concentracao` = " + novaConcentracao
					+ ",`operar` = " + novoMaximoOperar
					+ ",`updateTime` = CURRENT_TIMESTAMP"
					+ " WHERE `idEntidadeCedente` = " + operacaoResumo.getCedenteAttempt().getCedente().getIdEntidade()
					+ " and `idFundo` = " + operacaoResumo.getFundo().getIdFundo()
					;
		System.out.println(sql);
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static boolean equals(Operacao op1, Operacao op2)
	{
		boolean equals=false;
		
		if(op1.getFundo().getIdFundo()==op2.getFundo().getIdFundo()
			&& op1.getCedente().getIdEntidade()==op2.getCedente().getIdEntidade()
			&& op1.getNomeArquivo().equals(op2.getNomeArquivo())
				)
		{
			equals = true;
		}
		
		return equals; 
	}	

	public static void readOperationRelatorio(List<WebElement> rowOperation, Operacao operacao, String rootDownloadsLinux, SshClient sshClient, int clickX, int clickY)
	{
		System.out.println("Downloading pdf file for " + operacao.getNomeArquivo());
		System.out.println(rowOperation.get(6).getAttribute("name"));
        String fileTxtName = downloadPDFReport(rowOperation.get(6), rootDownloadsLinux, sshClient, clickX, clickY);
        if(fileTxtName=="")
        {
        	return;
        }
        fileTxtName = fileTxtName.replace(".pdf", "");
        System.out.println("Reading pdf file: " + fileTxtName);
        timeAndWait(6);
        ArrayList<String> linesTemp = null;
    	linesTemp = OperadorPortalPaulista.readTxtFile(fileTxtName);
    	if(linesTemp==null)
    	{
    		return;
    	}
        deletePDFReport(sshClient, rootDownloadsLinux);
        Relatorio relatorio=OperadorPortalPaulista.buildBlocks(linesTemp);
        //System.out.println(this.readTxtFile(fileTxtName));
    	operacao.setTotalAquisicao(relatorio.getBlockIdentificacao().getTotalAquisicao());
    	operacao.setTotalNominal(relatorio.getBlockIdentificacao().getTotalNominal());
    	operacao.setRelatorio(relatorio);
	}
	
    public static String downloadPDFReport(WebElement wePDF, String rootDownloadsLinux, SshClient sshClient, int clickX, int clickY)
    {
    	//driver.findElement(By.name("pdfLiquidacao")).click();
    	if(wePDF.isDisplayed())
    	{
        	System.out.println("Click On: " + wePDF.getAttribute("name"));
    		wePDF.click();
    	}
    	else
    	{
    		System.out.println("PDF Link is not displayed");
    		return "";
    	}
    	timeAndWait(3);    	
	
	    Robot r=null;
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{   	    
    	    r.mouseMove(clickX, clickY);//coordinates of save button
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux"))
		{			
    	    r.mouseMove(790, 530);//coordinates of save button
		}
		
	    r.mousePress(InputEvent.BUTTON1_MASK);
	    r.mouseRelease(InputEvent.BUTTON1_MASK);

	    //wait for file to download
	    timeAndWait(4);
	    String command="";	    
	    command="ls " + rootDownloadsLinux + "/";
//	    System.out.println("command: " + command);
        String output = sshClient.executeCommandOutput(command);
        
        String fileTxtName = output.split("\n")[0]+".txt";
        command = "pdftotext -layout " + " " + rootDownloadsLinux + "/"  + output;

        System.out.println("command: " + command);
        sshClient.executeCommand(command);
        
	    command="ls " + rootDownloadsLinux + "/*.txt";
	    System.out.println("command: " + command);
	    sshClient.executeCommand(command);
	    
        if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			fileTxtName = "c:\\DownloadsPortal"+"\\"+fileTxtName;
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux"))
		{			
			fileTxtName = rootDownloadsLinux+"/"+fileTxtName;
		}       
        return fileTxtName;
    }

    public static void deletePDFReport(SshClient sshClient, String rootDownloadsLinux)
    {
	    String command="rm " + rootDownloadsLinux +"/*";
	    sshClient.executeCommand(command);
    }

    public static void timeAndWait(int n)
    {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
//        System.out.println( sdf.format(cal.getTime()) );
//        System.out.println("Waiting " +  (n) + " seconds " + sdf.format(cal.getTime()));
    	for(int i=0;i<n;i++)
    	{
	        cal = Calendar.getInstance();
	        cal.getTime();
//	        System.out.println( sdf.format(cal.getTime()) );

//    		System.out.println("Waiting " +  (n-i) + " seconds " + sdf.format(cal.getTime()));
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
    
	 public static ArrayList<Operacao> readStoredOperations(Connection conn)
	 {

		ArrayList<Operacao> storedOperations = new ArrayList<Operacao>();
		
		ArrayList<Integer> idOperacao = new ArrayList<Integer>();
		ArrayList<Integer> idFundo = new ArrayList<Integer>();
		ArrayList<String> nomeArquivo = new ArrayList<String>();  
		ArrayList<Integer> idEntidadeCedente = new ArrayList<Integer>(); 
		ArrayList<Double> totalAquisicao = new ArrayList<Double>();  
		ArrayList<Double> totalNominal =  new ArrayList<Double>();  
		ArrayList<Date> dataDeEntrada = new ArrayList<Date>();
		
		ArrayList<Double> remessaComAdiantamento = new ArrayList<Double>();  
		ArrayList<Double> recompraComAdiantamento = new ArrayList<Double>(); 
		ArrayList<Double> reembolsoContaInterna = new ArrayList<Double>();
		
		ArrayList<Double> remessaSemAdiantamento = new ArrayList<Double>(); 
		ArrayList<Double> recompraSemAdiantamento = new ArrayList<Double>(); 
		ArrayList<Double> reembolsoContaExterna = new ArrayList<Double>();
		
		ArrayList<Double> valorTEDContaInterna = new ArrayList<Double>(); 
		ArrayList<Double> valorTEDContaExterna1 = new ArrayList<Double>(); 
		ArrayList<Double> valorTEDContaExterna2 = new ArrayList<Double>();
		ArrayList<Integer> idContaInterna = new ArrayList<Integer>(); 
		ArrayList<Integer> idContaExterna1 = new ArrayList<Integer>(); 
		ArrayList<Integer> idContaExterna2 = new ArrayList<Integer>();

		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulista.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT * FROM operacao WHERE "
				       + " dataDeEntrada = '" + sdfd.format(Calendar.getInstance().getTime()) + "'"
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
				SimpleDateFormat sdfdTemp = new SimpleDateFormat("yyyy-MM-dd");
				 int idOperacaoTemp=0;
				 int idFundoTemp=0;
				 String nomeArquivoTemp="";
				 int idEntidadeCedenteTemp=0;
				 double totalAquisicaoTemp=0.0;
				 double totalNominalTemp=0.0;
				 Date dataDeEntradaTemp=null;
				 double remessaComAdiantamentoTemp=0.0;
				 double recompraComAdiantamentoTemp=0.0;
				 double reembolsoContaInternaTemp=0.0;
				 double remessaSemAdiantamentoTemp=0.0;
				 double recompraSemAdiantamentoTemp=0.0;
				 double reembolsoContaExternaTemp=0.0;
				 double valorTEDContaInternaTemp=0.0;
				 double valorTEDContaExterna1Temp=0.0;
				 double valorTEDContaExterna2Temp=0.0;
				 int idContaInternaTemp=0;
				 int idContaExterna1Temp=0;
				 int idContaExterna2Temp=0;
				
				idOperacaoTemp=rs.getInt("idOperacao");
				idFundoTemp=rs.getInt("idFundo");
				nomeArquivoTemp=rs.getString("nomeArquivo");
				idEntidadeCedenteTemp=rs.getInt("idEntidadeCedente");
				totalAquisicaoTemp=rs.getDouble("totalAquisicao");
				totalNominalTemp=rs.getDouble("totalNominal");
				
				dataDeEntradaTemp=rs.getDate("dataDeEntrada");
				
				remessaComAdiantamentoTemp=rs.getDouble("remessaComAdiantamento");
				recompraComAdiantamentoTemp=rs.getDouble("recompraComAdiantamento");
				reembolsoContaInternaTemp=rs.getDouble("reembolsoContaInterna");
				
				remessaSemAdiantamentoTemp=rs.getDouble("remessaSemAdiantamento");
				recompraSemAdiantamentoTemp=rs.getDouble("recompraSemAdiantamento");
				reembolsoContaExternaTemp=rs.getDouble("reembolsoContaExterna");
				
				valorTEDContaInternaTemp=rs.getDouble("valorTEDContaInterna");
				valorTEDContaExterna1Temp=rs.getDouble("valorTEDContaExterna1");
				valorTEDContaExterna2Temp=rs.getDouble("valorTEDContaExterna2");
				
				idContaInternaTemp=rs.getInt("idContaInterna");
				idContaExterna1Temp=rs.getInt("idContaExterna1");
				idContaExterna2Temp=rs.getInt("idContaExterna2");
				
				System.out.println("--------------------");
				System.out.println("idOperacao: " + idOperacaoTemp);
				System.out.println("idFundo: " + idFundoTemp);
				System.out.println("NomeArquivo: " + nomeArquivoTemp);
				System.out.println("idEntidadeCedente: " + idEntidadeCedenteTemp);
				System.out.println("TotalAquisicao: " + totalAquisicaoTemp);
				System.out.println("TotalNominal: " + totalNominalTemp);
				System.out.println("DataDeEntrada:" + sdfdTemp.format(dataDeEntradaTemp));
				System.out.println("RemessaComAdiantamento: " + remessaComAdiantamentoTemp);
				System.out.println("RecompraComAdiantamento: " + recompraComAdiantamentoTemp);
				System.out.println("ReembolsoComAdiantamento: " + reembolsoContaInternaTemp);
				System.out.println("RemessaSemAdiantamento: " + remessaSemAdiantamentoTemp);
				System.out.println("RecompraSemAdiantamento: " + recompraSemAdiantamentoTemp);
				System.out.println("ReembolsoSemAdiantamento: " + reembolsoContaExternaTemp);
				
				System.out.println("ValorTedContaInterna: " + valorTEDContaInternaTemp);
				System.out.println("ValorTedContaExterna1: " + valorTEDContaExterna1Temp);
				System.out.println("ValorTedContaExterna2: " + valorTEDContaExterna2Temp);
				
				System.out.println("idContaInterna: " + idContaInternaTemp);
				System.out.println("idContaExterna1: " + idContaExterna1Temp);
				System.out.println("idContaExterna2: " + idContaExterna2Temp);
				
				
				idOperacao.add(idOperacaoTemp);
				idFundo.add(idFundoTemp);
				nomeArquivo.add(nomeArquivoTemp);
				idEntidadeCedente.add(idEntidadeCedenteTemp);
				totalAquisicao.add(totalAquisicaoTemp);
				totalNominal.add(totalNominalTemp);
				dataDeEntrada.add(dataDeEntradaTemp);
				remessaComAdiantamento.add(remessaComAdiantamentoTemp);
				recompraComAdiantamento.add(recompraComAdiantamentoTemp);
				reembolsoContaInterna.add(reembolsoContaInternaTemp);
				
				remessaSemAdiantamento.add(remessaSemAdiantamentoTemp);
				recompraSemAdiantamento.add(recompraSemAdiantamentoTemp);
				reembolsoContaExterna.add(reembolsoContaExternaTemp);
				valorTEDContaInterna.add(valorTEDContaInternaTemp);
				valorTEDContaExterna1.add(valorTEDContaExterna1Temp);
				valorTEDContaExterna2.add(valorTEDContaExterna2Temp);
				idContaInterna.add(idContaInternaTemp);
				idContaExterna1.add(idContaExterna1Temp);
				idContaExterna2.add(idContaExterna2Temp);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Found " + idOperacao.size() + " operations");
		if(idOperacao.size()!=0)
		{
			for(int i=0;i<idOperacao.size();i++)
			{

//				 int idOperacaoTemp=0;
//				 int idFundoTemp=0;
//				 String nomeArquivoTemp="";
//				 int idEntidadeCedenteTemp=0;
//				 double totalAquisicaoTemp=0.0;
//				 double totalNominalTemp=0.0;
//				 String dataDeEntradaTemp="";
//				 double remessaComAdiantamentoTemp=0.0;
//				 double recompraComAdiantamentoTemp=0.0;
//				 double reembolsoContaInternaTemp=0.0;
//				 double remessaSemAdiantamentoTemp=0.0;
//				 double recompraSemAdiantamentoTemp=0.0;
//				 double reembolsoContaExternaTemp=0.0;
//				 double valorTEDContaInternaTemp=0.0;
//				 double valorTEDContaExterna1Temp=0.0;
//				 double valorTEDContaExterna2Temp=0.0;
//				 int idContaInternaTemp=0;
//				 int idContaExterna1Temp=0;
//				 int idContaExterna2Temp=0;

//				*---------------------------------------				
// 				*Readed from database				
//				*---------------------------------------				
//				*private int idOperacao=0;
//				*private FundoDeInvestimento fundo=new FundoDeInvestimento();
//				*private String nomeArquivo="";
//				*private Entidade cedente=new Entidade();
//				*private double totalAquisicao=0;
//				*private double totalNominal=0;
//				*private Date dataDeEntrada=Calendar.getInstance().getTime();				
//				*private Date dataDeImportacao=Calendar.getInstance().getTime();
//				*private double valorTotalAquisicaoComAdiantamento=0;
//				*private double valorRecompraComAdiantamento=0;
//				*private double reembolsoContaEspecial=0;
//				*private double valorTotalComAdiantamento=0;	
//				*private double valorTotalContaCorrenteInternaPaulista=0;	
//				*private double valorRemessaSemAdiantamento=0;
//				*private double valorRecompraSemAdiantamento=0;
//				*private double reembolsoContaNormal=0;
//				*private double valorTotalSemAdiantamento=0;	
//				*private Conta contaInterna = new Conta();
//				*private Conta contaExterna1 = new Conta();
//				*private Conta contaExterna2 = new Conta();
//				*private String contaCorrenteInternaPaulista="";				
//				*private String contaCedenteSemAdiantamento1="";
//				*private double valorTotalCedenteSemAdiantamento1=0;
//				*private String contaCedenteSemAdiantamento2="";
//				*private double valorTotalCedenteSemAdiantamento2=0;

				Operacao op = new Operacao();
				
				op.setIdOperacao(idOperacao.get(i));
				op.setFundo(new FundoDeInvestimento(idFundo.get(i), conn));
				op.setNomeArquivo(nomeArquivo.get(i));
				op.setCedente(new Entidade(idEntidadeCedente.get(i), conn));
				op.setTotalAquisicao(totalAquisicao.get(i));
				op.setTotalNominal(totalNominal.get(i));
				op.setDataDeEntrada(dataDeEntrada.get(i));
				op.setDataDeImportacao(dataDeEntrada.get(i));
				op.setValorTotalAquisicaoComAdiantamento(remessaComAdiantamento.get(i));
				op.setValorRecompraComAdiantamento(recompraComAdiantamento.get(i));
				op.setReembolsoContaEspecial(reembolsoContaInterna.get(i));
				op.setValorTotalComAdiantamento(valorTEDContaInterna.get(i));
				op.setValorRemessaSemAdiantamento(remessaSemAdiantamento.get(i));
				op.setValorRecompraSemAdiantamento(recompraSemAdiantamento.get(i));
				op.setReembolsoContaNormal(reembolsoContaExterna.get(i));				
				op.setValorTotalSemAdiantamento(valorTEDContaExterna1.get(i)+valorTEDContaExterna2.get(i));
				op.setValorTotalCedenteSemAdiantamento1(valorTEDContaExterna1.get(i));
				op.setValorTotalCedenteSemAdiantamento2(valorTEDContaExterna2.get(i));
				
				Conta contaInterna = new Conta(idContaInterna.get(i),conn);
				Conta contaExterna1 = new Conta(idContaExterna1.get(i),conn);
				Conta contaExterna2 = new Conta(idContaExterna2.get(i),conn);
				
				op.setContaInterna(contaInterna);
				op.setContaExterna1(contaExterna1);
				op.setContaExterna2(contaExterna2);
				
				HandlerStatus.readStored(op, conn);
				HandlerTitulo.readStored(op, conn);
				OperationSummary operationSummary = new OperationSummary(op);
				op.setResumo(operationSummary);
				HandlerOperacoes.assessOperation(op,conn);
				op.show();
				
				if(op.getRelatorio().getBlockTitulos().getTitulos().size()>0)
				{
					storedOperations.add(op);
					System.out.println("Skipping: " + op.getNomeArquivo() + " from " + op.getFundo().getNome());
				}

				if(!op.isTedEnviada())
	        	{
	        		System.out.println("--Check electronic transfer--");
	        		System.out.println("--For " + op.getNomeArquivo() + "--");
	        		
	        		boolean haveResourcesSent=false;
	        		for(Status status:op.getStatuses())
	        		{
	        			if(status.getDescription().toLowerCase().contains("recursos enviada"))
	        			{
	        				haveResourcesSent=true;
	        			}
	        		}
	        		
	        		if(haveResourcesSent)
	        		{        			
	        			System.out.println("\tHaveResourcesSent");
	        			HandlerOperacoes.transferDone(op, conn);
	        		}
	        		else
	        		{
	        			System.out.println("Skip checking transfer controller");
	        		}
	        	}				
			}
		}
		return storedOperations;
	}
	 
	public static boolean alreadyApprouved(Operacao op)
	{
		boolean alreadyApprouved=false;
		
		for (Status status: op.getStatuses())
		{
			if
			( status.getDescription().toLowerCase().contains("aguardando assinatura digital")
			 || status.getDescription().toLowerCase().contains("gestor de ted"))
			{
				alreadyApprouved=true;
				System.out.println("Operation already approuved and OK!");
				break;
			}
		}
		
		return alreadyApprouved;
	}

	public static boolean approuvedByHand(Operacao op)
	{
		boolean approvedByHand=false;
		
		for (Status status: op.getStatuses())
		{
			if
			( status.getDescription().toLowerCase().contains("aprovado manualmente"))			 
			{
				approvedByHand=true;
				System.out.println("Operation already approuved by hand probably not being OK!");
				break;
			}
		}
		
		return approvedByHand;
	}
	
	public static void transferDone(Operacao op, Connection conn)
	{
		boolean done = true;
		Statement stmt=null;
		
		ArrayList<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT * FROM movimentacao WHERE"
				+ " " + "data='"+ sdfcd.format(op.getDataDeImportacao()) + "'"
				+ " " + "AND idFundo=" + op.getFundo().getIdFundo()
				       ;		
		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
//				idMovimentacao, 
//				idFundo, 
//				idtipoDeMovimentacao, 
//				data, 
//				hora, 
//				descricao, 
//				valor		
				
				int idMovimentacao = rs.getInt("idMovimentacao");
				int idTipoDeMovimentacao = rs.getInt("idTipoDeMovimentacao");
				Date data = rs.getDate("data");
				Date hora = rs.getDate("hora");
				String descricao = rs.getString("descricao");
				double valor = rs.getDouble("valor");

//				System.out.println("idMovimentacao: " + idMovimentacao);
//				System.out.println("idTipoDeMovimentacao: " + idTipoDeMovimentacao);
//				System.out.println("data: " + data);
//				System.out.println("hora: " + hora);
//				System.out.println("descricao: " + descricao);
//				System.out.println("valor: " + valor);
				Movimentacao movimentacao = new Movimentacao(idMovimentacao,op.getFundo(),data,hora,descricao,valor,idTipoDeMovimentacao, conn);
				movimentacoes.add(movimentacao);
//				movimentacao.show();				
			}			
			System.out.println("Pass query execution!");	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		ArrayList<Double> valoresTED = new ArrayList<Double>();
		if (op.getValorTotalComAdiantamento()!=0.0)
		{
			valoresTED.add(op.getValorTotalComAdiantamento());
			System.out.println("TED Interna: " + op.getValorTotalComAdiantamento());
		}
		if (op.getValorTotalCedenteSemAdiantamento1()!=0.0)
		{
			valoresTED.add(op.getValorTotalCedenteSemAdiantamento1());
			System.out.println("TED Externa 1: " + op.getValorTotalCedenteSemAdiantamento1());
		}
		if (op.getValorTotalCedenteSemAdiantamento2()!=0.0)
		{
			valoresTED.add(op.getValorTotalCedenteSemAdiantamento2());
			System.out.println("TED Externa 1: " + op.getValorTotalCedenteSemAdiantamento1());
		}		
		
		for(Double valorTED:valoresTED)
		{
			System.out.println("Looking for " + valorTED);
			boolean enviada=false;			
			for(Movimentacao movimentacao:movimentacoes)
			{
				movimentacao.show();
				if(Double.compare(valorTED,movimentacao.getValor())==0.0 && movimentacao.getTipoDeMovimentacao().getTipo().equals("D"))
				{
					System.out.println("TED " + valorTED + " para " + op.getCedente().getNome() + " enviada");
					enviada=true;					
					break;
				}
			}
			if(!enviada)
			{
				done=false;
			}
		}
		
		op.setTedEnviada(done);
		if(op.isTedEnviada())
		{
			boolean haveStatusLiquidada=false;
    		for(Status status:op.getStatuses())
    		{
    			if(status.getDescription().toLowerCase().contains("ted liquidada"))
    			{
    				haveStatusLiquidada=true;
    			}
    			
    		}			
			if(!haveStatusLiquidada)
			{
    			Status status = new Status("TED Liquidada", Calendar.getInstance().getTime(), conn);
    			op.getStatuses().add(status);
			}
			else
			{
    			System.out.println("Have status TED Liquidada");
    			int iStatus=0;
    			for(Status status:op.getStatuses())
    			{
    				if(status.getDescription().toLowerCase().contains("ted liquidada"))
    				{
    					Status tempS = status;
    					tempS.setEndDate(Calendar.getInstance().getTime());
    					status = op.getStatuses().get(op.getStatuses().size()-1);
    					op.getStatuses().set(op.getStatuses().size()-1, tempS);
    				}
    				iStatus=iStatus+1;
    			}
			}
		}
	}
	public static SimpleDateFormat getSdfd() {
		return sdfd;
	}
	public static void setSdfd(SimpleDateFormat sdfd) {
		HandlerOperacoes.sdfd = sdfd;
	}
	public static SimpleDateFormat getSdfcd() {
		return sdfcd;
	}
	public static void setSdfcd(SimpleDateFormat sdfcd) {
		HandlerOperacoes.sdfcd = sdfcd;
	}
	
}
