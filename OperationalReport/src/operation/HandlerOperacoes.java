package operation;

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

import relatorio.cessao.HandlerDireitoCreditorio;
import relatorio.cessao.Relatorio;
import utils.SshClient;
import limites.HandlerLimites;
import mvcapital.bancopaulista.Movimentacao;
import mvcapital.portalfidc.OperadorPortalPaulista;
import mvcapital.portalfidc.OperadorPortalPaulistaChrome;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import conta.Conta;
import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class HandlerOperacoes 
{
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd"); //$NON-NLS-1$
	private static SimpleDateFormat sdfcd = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
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
		System.out.println("-----------------------------"); //$NON-NLS-1$
		System.out.println("Storing Operation " + op.getNomeArquivo());		 //$NON-NLS-1$
		System.out.println("-----------------------------"); //$NON-NLS-1$
		
		int idContaInterna=op.getContaInterna().getIdConta(); 
		int idContaExterna1=op.getContaExterna1().getIdConta(); 
		int idContaExterna2=op.getContaExterna2().getIdConta();
		
		if(op.getContaInterna().getIdConta()!=0)
		{
			System.out.println("Conta 1"); //$NON-NLS-1$
			System.out.println("idCedente: " + op.getCedente().getIdEntidade()); //$NON-NLS-1$
			System.out.println("Banco: " + op.getContaInterna().getCodigoServidor()); //$NON-NLS-1$
			System.out.println("Agencia: " + op.getContaInterna().getAgencia()); //$NON-NLS-1$
			System.out.println("NroConta: " + op.getContaInterna().getNumero()); //$NON-NLS-1$
//			idContaInterna = Conta.getIdConta(op.getCedente().getIdEntidade(), op.getContaInterna().getCodigoServidor(), op.getContaInterna().getAgencia(), op.getContaInterna().getNumero(), conn);
		}
		
		if(op.getContaExterna1().getIdConta()!=0)
		{
			System.out.println("Conta 1"); //$NON-NLS-1$
			System.out.println("idCedente: " + op.getCedente().getIdEntidade()); //$NON-NLS-1$
			System.out.println("Banco: " + op.getContaExterna1().getCodigoServidor()); //$NON-NLS-1$
			System.out.println("Agencia: " + op.getContaExterna1().getAgencia()); //$NON-NLS-1$
			System.out.println("NroConta: " + op.getContaExterna1().getNumero()); //$NON-NLS-1$
//			idContaExterna1 = Conta.getIdConta(op.getCedente().getIdEntidade(), op.getContaExterna1().getCodigoServidor(), op.getContaExterna1().getAgencia(), op.getContaExterna1().getNumero(), conn);
		}
		if(op.getContaExterna1().getIdConta()!=0)
		{
			System.out.println("Conta 2"); //$NON-NLS-1$
			System.out.println("idCedente: " + op.getCedente().getIdEntidade()); //$NON-NLS-1$
			System.out.println("Banco: " + op.getContaExterna2().getCodigoServidor()); //$NON-NLS-1$
			System.out.println("Agencia: " + op.getContaExterna2().getAgencia()); //$NON-NLS-1$
			System.out.println("NroConta: " + op.getContaExterna2().getNumero()); //$NON-NLS-1$
//			idContaExterna1 = Conta.getIdConta(op.getCedente().getIdEntidade(), op.getContaExterna2().getCodigoServidor(), op.getContaExterna2().getAgencia(), op.getContaExterna2().getNumero(),conn);
		}
		
		System.out.println("idFundo: " + op.getFundo().getIdFundo()); //$NON-NLS-1$
		System.out.println("nomeArquivo: " + op.getNomeArquivo()); //$NON-NLS-1$
		System.out.println("idEntidadeCedente: " + op.getCedente().getIdEntidade()); //$NON-NLS-1$
		System.out.println("totalAquisicao: " + op.getTotalAquisicao()); //$NON-NLS-1$
		System.out.println("totalNominal: " + op.getTotalNominal()); //$NON-NLS-1$
		System.out.println("dataDeEntrada: " + sdfd.format(op.getDataDeEntrada()) + " " + op.getDataDeEntrada()); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("remessaComAdiantamento: " + op.getValorTotalComAdiantamento()); //$NON-NLS-1$
		System.out.println("recompraComAdiantamento: " + op.getValorRecompraComAdiantamento()); //$NON-NLS-1$
		System.out.println("reembolsoComAdiantamento: " + op.getReembolsoContaEspecial()); //$NON-NLS-1$
		System.out.println("remessaSemAdiantamento: " + op.getValorRemessaSemAdiantamento()); //$NON-NLS-1$
		System.out.println("recompraSemAdiantamento: " + op.getValorRecompraSemAdiantamento()); //$NON-NLS-1$
		System.out.println("reembolsoSemAdiantamento: " + op.getReembolsoContaNormal()); //$NON-NLS-1$
		System.out.println("valorTEDContaInterna: " + op.getValorTotalContaCorrenteInternaPaulista()); //$NON-NLS-1$
		System.out.println("valorTEDContaExterna1: " + op.getValorTotalCedenteSemAdiantamento1()); //$NON-NLS-1$
		System.out.println("valorTEDContaExterna2: " + op.getValorTotalCedenteSemAdiantamento2()); //$NON-NLS-1$
		System.out.println("idContaInterna: " + op.getContaInterna().getIdConta()); //$NON-NLS-1$
		System.out.println("idContaExterna1: " + op.getContaExterna1().getIdConta()); //$NON-NLS-1$
		System.out.println("idContaExterna2: " + op.getContaExterna2().getIdConta()); //$NON-NLS-1$
		
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int idOperacao=0;
		String query = "SELECT idOperacao FROM operacao WHERE idFundo=" + op.getFundo().getIdFundo() //$NON-NLS-1$
				       + " AND nomeArquivo = \"" + op.getNomeArquivo() + "\"" //$NON-NLS-1$ //$NON-NLS-2$
				       + " AND idEntidadeCedente = " + op.getCedente().getIdEntidade() //$NON-NLS-1$
				       + " AND totalAquisicao = " + op.getTotalAquisicao() //$NON-NLS-1$
				       + " AND dataDeEntrada = " + sdfd.format(op.getDataDeEntrada()) //$NON-NLS-1$
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
				idOperacao = rs.getInt("idOperacao");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(idOperacao!=0)
		{
			System.out.println("Operation already registered"); //$NON-NLS-1$
//			Charge data from database!!
			
			op.setIdOperacao(idOperacao);			
			HandlerStatus.store(op, conn);
			return;
		}

		System.out.println("idContaInterna: " + idContaInterna); //$NON-NLS-1$
		System.out.println("idContaExterna1: " + idContaExterna1); //$NON-NLS-1$
		System.out.println("idContaExterna2: " + idContaExterna2); //$NON-NLS-1$
		
		String sql = ""; //$NON-NLS-1$
		if(idContaInterna!=0 && idContaExterna1!=0 && idContaExterna2!=0)
		{	
			sql = "INSERT INTO `mvcapital`.`operacao` (`idFundo`,`nomeArquivo`,`idEntidadeCedente`,`totalAquisicao`,`totalNominal`,`dataDeEntrada`,`remessaComAdiantamento`,`recompraComAdiantamento`,`reembolsoContaInterna`,`remessaSemAdiantamento`,`recompraSemAdiantamento`,`reembolsoContaExterna`,`valorTEDContaInterna`,`valorTEDContaExterna1`,`valorTEDContaExterna2`,`idContaInterna`,`idContaExterna1`,`idContaExterna2`) " //$NON-NLS-1$
					+ "VALUES (" //$NON-NLS-1$
					+ op.getFundo().getIdFundo()
					+ "," + "\"" + op.getNomeArquivo() + "\"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ "," + op.getCedente().getIdEntidade() //$NON-NLS-1$
					+ "," + op.getTotalAquisicao() //$NON-NLS-1$
					+ "," + op.getTotalNominal() //$NON-NLS-1$
					+ "," + "'" + sdfd.format(op.getDataDeEntrada()) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ "," + op.getValorTotalComAdiantamento() //$NON-NLS-1$
					+ "," + op.getValorRecompraComAdiantamento() //$NON-NLS-1$
					+ "," + op.getReembolsoContaEspecial() //$NON-NLS-1$
					+ "," + op.getValorRemessaSemAdiantamento() //$NON-NLS-1$
					+ "," + op.getValorRecompraSemAdiantamento() //$NON-NLS-1$
					+ "," + op.getReembolsoContaNormal() //$NON-NLS-1$
					+ "," + op.getValorTotalContaCorrenteInternaPaulista() //$NON-NLS-1$
					+ "," + op.getValorTotalCedenteSemAdiantamento1() //$NON-NLS-1$
					+ "," + op.getValorTotalCedenteSemAdiantamento2() //$NON-NLS-1$
					+ "," + idContaInterna //$NON-NLS-1$
					+ "," + idContaExterna1 //$NON-NLS-1$
					+ "," + idContaExterna2 //$NON-NLS-1$
					+ ")"; //$NON-NLS-1$
		}
		else if(idContaInterna==0 && idContaExterna1!=0)
		{	
			if(idContaExterna2!=0)
			{
    			sql = "INSERT INTO `mvcapital`.`operacao` (`idFundo`,`nomeArquivo`,`idEntidadeCedente`,`totalAquisicao`,`totalNominal`,`dataDeEntrada`,`remessaComAdiantamento`,`recompraComAdiantamento`,`reembolsoContaInterna`,`remessaSemAdiantamento`,`recompraSemAdiantamento`,`reembolsoContaExterna`,`valorTEDContaInterna`,`valorTEDContaExterna1`,`valorTEDContaExterna2`,`idContaExterna1`,`idContaExterna2`) " //$NON-NLS-1$
    					+ "VALUES (" //$NON-NLS-1$
    					+ op.getFundo().getIdFundo()
    					+ "," + "\"" + op.getNomeArquivo() + "\"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    					+ "," + op.getCedente().getIdEntidade() //$NON-NLS-1$
    					+ "," + op.getTotalAquisicao() //$NON-NLS-1$
    					+ "," + op.getTotalNominal() //$NON-NLS-1$
    					+ "," + "'" + sdfd.format(op.getDataDeEntrada()) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    					+ "," + op.getValorTotalComAdiantamento() //$NON-NLS-1$
    					+ "," + op.getValorRecompraComAdiantamento() //$NON-NLS-1$
    					+ "," + op.getReembolsoContaEspecial() //$NON-NLS-1$
    					+ "," + op.getValorRemessaSemAdiantamento() //$NON-NLS-1$
    					+ "," + op.getValorRecompraSemAdiantamento() //$NON-NLS-1$
    					+ "," + op.getReembolsoContaNormal() //$NON-NLS-1$
    					+ "," + op.getValorTotalContaCorrenteInternaPaulista() //$NON-NLS-1$
    					+ "," + op.getValorTotalCedenteSemAdiantamento1() //$NON-NLS-1$
    					+ "," + op.getValorTotalCedenteSemAdiantamento2() //$NON-NLS-1$
    					+ "," + idContaExterna1 //$NON-NLS-1$
    					+ "," + idContaExterna2 //$NON-NLS-1$
    					+ ")";	    				 //$NON-NLS-1$
			}
			else
			{
    			sql = "INSERT INTO `mvcapital`.`operacao` (`idFundo`,`nomeArquivo`,`idEntidadeCedente`,`totalAquisicao`,`totalNominal`,`dataDeEntrada`,`remessaComAdiantamento`,`recompraComAdiantamento`,`reembolsoContaInterna`,`remessaSemAdiantamento`,`recompraSemAdiantamento`,`reembolsoContaExterna`,`valorTEDContaInterna`,`valorTEDContaExterna1`,`valorTEDContaExterna2`,`idContaExterna1`) " //$NON-NLS-1$
    					+ "VALUES (" //$NON-NLS-1$
    					+ op.getFundo().getIdFundo()
    					+ "," + "\"" + op.getNomeArquivo() + "\"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    					+ "," + op.getCedente().getIdEntidade() //$NON-NLS-1$
    					+ "," + op.getTotalAquisicao() //$NON-NLS-1$
    					+ "," + op.getTotalNominal() //$NON-NLS-1$
    					+ "," + "'" + sdfd.format(op.getDataDeEntrada()) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    					+ "," + op.getValorTotalComAdiantamento() //$NON-NLS-1$
    					+ "," + op.getValorRecompraComAdiantamento() //$NON-NLS-1$
    					+ "," + op.getReembolsoContaEspecial() //$NON-NLS-1$
    					+ "," + op.getValorRemessaSemAdiantamento() //$NON-NLS-1$
    					+ "," + op.getValorRecompraSemAdiantamento() //$NON-NLS-1$
    					+ "," + op.getReembolsoContaNormal() //$NON-NLS-1$
    					+ "," + op.getValorTotalContaCorrenteInternaPaulista() //$NON-NLS-1$
    					+ "," + op.getValorTotalCedenteSemAdiantamento1() //$NON-NLS-1$
    					+ "," + op.getValorTotalCedenteSemAdiantamento2() //$NON-NLS-1$
    					+ "," + idContaExterna1 //$NON-NLS-1$
    					+ ")";	    				 //$NON-NLS-1$
				
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

		query = "SELECT idOperacao FROM operacao WHERE idFundo=" + op.getFundo().getIdFundo() //$NON-NLS-1$
				       + " AND nomeArquivo = \"" + op.getNomeArquivo() + "\"" //$NON-NLS-1$ //$NON-NLS-2$
				       + " AND idEntidadeCedente = " + op.getCedente().getIdEntidade() //$NON-NLS-1$
				       + " AND totalAquisicao = " + op.getTotalAquisicao() //$NON-NLS-1$
				       + " AND dataDeEntrada = " + sdfd.format(op.getDataDeEntrada()) //$NON-NLS-1$
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
				idOperacao = rs.getInt("idOperacao");				 //$NON-NLS-1$
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
		System.out.println("-----------------------------"); //$NON-NLS-1$
		System.out.println("Storing Update Operation " + op.getNomeArquivo());		 //$NON-NLS-1$
		System.out.println("-----------------------------"); //$NON-NLS-1$
		
		int idContaInterna=op.getContaInterna().getIdConta(); 
		int idContaExterna1=op.getContaExterna1().getIdConta(); 
		int idContaExterna2=op.getContaExterna2().getIdConta();
		
		if(op.getContaInterna().getIdConta()!=0)
		{
			System.out.println("Conta 1"); //$NON-NLS-1$
			System.out.println("idCedente: " + op.getCedente().getIdEntidade()); //$NON-NLS-1$
			System.out.println("Banco: " + op.getContaInterna().getCodigoServidor()); //$NON-NLS-1$
			System.out.println("Agencia: " + op.getContaInterna().getAgencia()); //$NON-NLS-1$
			System.out.println("NroConta: " + op.getContaInterna().getNumero()); //$NON-NLS-1$
//			idContaInterna = Conta.getIdConta(op.getCedente().getIdEntidade(), op.getContaInterna().getCodigoServidor(), op.getContaInterna().getAgencia(), op.getContaInterna().getNumero(), conn);
		}
		
		if(op.getContaExterna1().getIdConta()!=0)
		{
			System.out.println("Conta 1"); //$NON-NLS-1$
			System.out.println("idCedente: " + op.getCedente().getIdEntidade()); //$NON-NLS-1$
			System.out.println("Banco: " + op.getContaExterna1().getCodigoServidor()); //$NON-NLS-1$
			System.out.println("Agencia: " + op.getContaExterna1().getAgencia()); //$NON-NLS-1$
			System.out.println("NroConta: " + op.getContaExterna1().getNumero()); //$NON-NLS-1$
//			idContaExterna1 = Conta.getIdConta(op.getCedente().getIdEntidade(), op.getContaExterna1().getCodigoServidor(), op.getContaExterna1().getAgencia(), op.getContaExterna1().getNumero(), conn);
		}
		if(op.getContaExterna2().getIdConta()!=0)
		{
			System.out.println("Conta 2"); //$NON-NLS-1$
			System.out.println("idCedente: " + op.getCedente().getIdEntidade()); //$NON-NLS-1$
			System.out.println("Banco: " + op.getContaExterna2().getCodigoServidor()); //$NON-NLS-1$
			System.out.println("Agencia: " + op.getContaExterna2().getAgencia()); //$NON-NLS-1$
			System.out.println("NroConta: " + op.getContaExterna2().getNumero()); //$NON-NLS-1$
//			idContaExterna1 = Conta.getIdConta(op.getCedente().getIdEntidade(), op.getContaExterna2().getCodigoServidor(), op.getContaExterna2().getAgencia(), op.getContaExterna2().getNumero(),conn);
		}
		
		System.out.println("idFundo: " + op.getFundo().getIdFundo()); //$NON-NLS-1$
		System.out.println("nomeArquivo: " + op.getNomeArquivo()); //$NON-NLS-1$
		System.out.println("idEntidadeCedente: " + op.getCedente().getIdEntidade()); //$NON-NLS-1$
		System.out.println("totalAquisicao: " + op.getTotalAquisicao()); //$NON-NLS-1$
		System.out.println("totalNominal: " + op.getTotalNominal()); //$NON-NLS-1$
		System.out.println("dataDeEntrada: " + sdfd.format(op.getDataDeEntrada()) + " " + op.getDataDeEntrada()); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("remessaComAdiantamento: " + op.getValorTotalComAdiantamento()); //$NON-NLS-1$
		System.out.println("recompraComAdiantamento: " + op.getValorRecompraComAdiantamento()); //$NON-NLS-1$
		System.out.println("reembolsoComAdiantamento: " + op.getReembolsoContaEspecial()); //$NON-NLS-1$
		System.out.println("remessaSemAdiantamento: " + op.getValorRemessaSemAdiantamento()); //$NON-NLS-1$
		System.out.println("recompraSemAdiantamento: " + op.getValorRecompraSemAdiantamento()); //$NON-NLS-1$
		System.out.println("reembolsoSemAdiantamento: " + op.getReembolsoContaNormal()); //$NON-NLS-1$
		System.out.println("valorTEDContaInterna: " + op.getValorTotalContaCorrenteInternaPaulista()); //$NON-NLS-1$
		System.out.println("valorTEDContaExterna1: " + op.getValorTotalCedenteSemAdiantamento1()); //$NON-NLS-1$
		System.out.println("valorTEDContaExterna2: " + op.getValorTotalCedenteSemAdiantamento2()); //$NON-NLS-1$
		System.out.println("idContaInterna: " + op.getContaInterna().getIdConta()); //$NON-NLS-1$
		System.out.println("idContaExterna1: " + op.getContaExterna1().getIdConta()); //$NON-NLS-1$
		System.out.println("idContaExterna2: " + op.getContaExterna2().getIdConta()); //$NON-NLS-1$
		
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int idOperacao=0;
		String query = "SELECT idOperacao FROM operacao WHERE idFundo=" + op.getFundo().getIdFundo() //$NON-NLS-1$
				       + " AND nomeArquivo = \"" + op.getNomeArquivo() + "\"" //$NON-NLS-1$ //$NON-NLS-2$
				       + " AND idEntidadeCedente = " + op.getCedente().getIdEntidade() //$NON-NLS-1$
				       + " AND totalAquisicao = " + op.getTotalAquisicao() //$NON-NLS-1$
				       + " AND dataDeEntrada = " + sdfd.format(op.getDataDeEntrada()) //$NON-NLS-1$
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
				idOperacao = rs.getInt("idOperacao");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(idOperacao!=0)
		{
			System.out.println("Operation already registered"); //$NON-NLS-1$
//			Charge data from database!!
			
			op.setIdOperacao(idOperacao);			
			HandlerStatus.store(op, conn);
//			return;
		}

		System.out.println("idContaInterna: " + idContaInterna); //$NON-NLS-1$
		System.out.println("idContaExterna1: " + idContaExterna1); //$NON-NLS-1$
		System.out.println("idContaExterna2: " + idContaExterna2); //$NON-NLS-1$
		
		if(idContaInterna==0)
		{
			System.out.println("idContaInterna==0"); //$NON-NLS-1$
		}
		if(idContaExterna1==0)
		{
			System.out.println("idContaExterna1==0"); //$NON-NLS-1$
		}
		if(idContaExterna2==0)
		{
			System.out.println("idContaExterna2==0"); //$NON-NLS-1$
		}
		if(idContaInterna==0 && idContaExterna1!=0)
		{
			System.out.println("idContaInterna==0 and idContaExterna1!=0"); //$NON-NLS-1$
		}
		
		
		String sql = ""; //$NON-NLS-1$
		if(idContaInterna!=0 && idContaExterna1!=0 && idContaExterna2!=0)
		{	
	
			sql = "UPDATE `mvcapital`.`operacao`" //$NON-NLS-1$
					+ " " + "SET" //$NON-NLS-1$ //$NON-NLS-2$
					+ " " + "`remessaComAdiantamento` = " + op.getValorTotalAquisicaoComAdiantamento() //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + "`recompraComAdiantamento` = " + op.getValorRecompraComAdiantamento() //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + "`reembolsoContaInterna` = " + op.getReembolsoContaEspecial() //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + "`remessaSemAdiantamento` = " + op.getValorRemessaSemAdiantamento() //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + "`recompraSemAdiantamento` = " + op.getValorRecompraSemAdiantamento() //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + "`reembolsoContaExterna` = " + op.getReembolsoContaNormal() //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + "`valorTEDContaInterna` = " + op.getValorTotalComAdiantamento() //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + "`valorTEDContaExterna1` = " + op.getValorTotalCedenteSemAdiantamento1() //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + "`valorTEDContaExterna2` = " + op.getValorTotalCedenteSemAdiantamento2() //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + "`idContaInterna` = " + op.getContaInterna().getIdConta() //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + "`idContaExterna1` = " + op.getContaExterna1().getIdConta() //$NON-NLS-1$ //$NON-NLS-2$
					+ "," + "`idContaExterna2` = " + op.getContaExterna2().getIdConta()  //$NON-NLS-1$ //$NON-NLS-2$
					+ " " + "WHERE `idOperacao` = " + op.getIdOperacao(); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		if(idContaInterna==0 && idContaExterna1!=0)
		{	
			System.out.println("- idContaInterna==0 && idContaExterna1!=0 -"); //$NON-NLS-1$
			if(idContaExterna2!=0)
			{
				System.out.println("\tidContaExterna2!=0"); //$NON-NLS-1$
				sql = "UPDATE `mvcapital`.`operacao`" //$NON-NLS-1$
						+ " " + "SET" //$NON-NLS-1$ //$NON-NLS-2$
						+ " " + "`remessaSemAdiantamento` = " + op.getValorRemessaSemAdiantamento() //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + "`recompraSemAdiantamento` = " + op.getValorRecompraSemAdiantamento() //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + "`reembolsoContaExterna` = " + op.getReembolsoContaNormal() //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + "`valorTEDContaExterna1` = " + op.getValorTotalCedenteSemAdiantamento1() //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + "`valorTEDContaExterna2` = " + op.getValorTotalCedenteSemAdiantamento2() //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + "`idContaExterna1` = " + op.getContaExterna1().getIdConta() //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + "`idContaExterna2` = " + op.getContaExterna2().getIdConta()  //$NON-NLS-1$ //$NON-NLS-2$
						+ " " + "WHERE `idOperacao` = " + op.getIdOperacao(); //$NON-NLS-1$ //$NON-NLS-2$
				
			}
			else
			{
				System.out.println("\tidContaExterna2==0"); //$NON-NLS-1$
				sql = "UPDATE `mvcapital`.`operacao`" //$NON-NLS-1$
						+ " " + "SET" //$NON-NLS-1$ //$NON-NLS-2$
						+ " " + "`remessaSemAdiantamento` = " + op.getValorRemessaSemAdiantamento() //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + "`recompraSemAdiantamento` = " + op.getValorRecompraSemAdiantamento() //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + "`reembolsoContaExterna` = " + op.getReembolsoContaNormal() //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + "`valorTEDContaExterna1` = " + op.getValorTotalCedenteSemAdiantamento1() //$NON-NLS-1$ //$NON-NLS-2$
						+ "," + "`idContaExterna1` = " + op.getContaExterna1().getIdConta() //$NON-NLS-1$ //$NON-NLS-2$
						+ " " + "WHERE `idOperacao` = " + op.getIdOperacao();				 //$NON-NLS-1$ //$NON-NLS-2$
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

		query = "SELECT idOperacao FROM operacao WHERE idFundo=" + op.getFundo().getIdFundo() //$NON-NLS-1$
				       + " AND nomeArquivo = \"" + op.getNomeArquivo() + "\"" //$NON-NLS-1$ //$NON-NLS-2$
				       + " AND idEntidadeCedente = " + op.getCedente().getIdEntidade() //$NON-NLS-1$
				       + " AND totalAquisicao = " + op.getTotalAquisicao() //$NON-NLS-1$
				       + " AND dataDeEntrada = " + sdfd.format(op.getDataDeEntrada()) //$NON-NLS-1$
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
				idOperacao = rs.getInt("idOperacao");				 //$NON-NLS-1$
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
		System.out.println("Assessing Operation: " + op.getFundo().getNome() + " " + op.getCedente().getNome() + " " + op.getNomeArquivo()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		HandlerLimites.assessLimites(op, conn);
		System.out.println("Assessing Operation: " + op.getNomeArquivo() + " finished!"); //$NON-NLS-1$ //$NON-NLS-2$
	}	
	
	public static void updateConcentracao(Operacao op)
	{
		OperationSummary operacaoResumo=op.getResumo();
		Statement stmt=null;
		try {
			stmt = (Statement) OperadorPortalPaulistaChrome.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(SacadoAttempt sacadoAttempt:operacaoResumo.getSacadosAttempt())
		{
			double novoMaximoOperar = sacadoAttempt.getMaximoOperar()-sacadoAttempt.getTotalOperar(); 
			String sql = "UPDATE `MVCapital`.`ConcentracaoSacado` SET " //$NON-NLS-1$
						+ "`valorPresente` = " + sacadoAttempt.getNovoValorPresente() //$NON-NLS-1$
						+ ",`concentracao` = " + sacadoAttempt.getNovaConcentracao() //$NON-NLS-1$
						+ ",`operar` = " + novoMaximoOperar //$NON-NLS-1$
						+ ",`updateTime` = CURRENT_TIMESTAMP" //$NON-NLS-1$
						+ " WHERE `idEntidadeSacado` = " + sacadoAttempt.getSacado().getIdEntidade() //$NON-NLS-1$
						+ " and `idFundo` = " + operacaoResumo.getFundo().getIdFundo() //$NON-NLS-1$
						;
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		double novoMaximoOperar = operacaoResumo.getCedenteAttempt().getMaximoOperar()-operacaoResumo.getCedenteAttempt().getTotalOperar(); 
		String sql = "UPDATE `MVCapital`.`ConcentracaoCedente` SET " //$NON-NLS-1$
					+ "`valorPresente` = " + operacaoResumo.getCedenteAttempt().getNovoValorPresente() //$NON-NLS-1$
					+ ",`concentracao` = " + operacaoResumo.getCedenteAttempt().getNovaConcentracao() //$NON-NLS-1$
					+ ",`operar` = " + novoMaximoOperar //$NON-NLS-1$
					+ ",`updateTime` = CURRENT_TIMESTAMP" //$NON-NLS-1$
					+ " WHERE `idEntidadeCedente` = " + operacaoResumo.getCedenteAttempt().getCedente().getIdEntidade() //$NON-NLS-1$
					+ " and `idFundo` = " + operacaoResumo.getFundo().getIdFundo() //$NON-NLS-1$
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
			stmt = (Statement) OperadorPortalPaulistaChrome.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(SacadoAttempt sacadoAttempt:operacaoResumo.getSacadosAttempt())
		{
			double novoMaximoOperar = sacadoAttempt.getMaximoOperar()+sacadoAttempt.getTotalOperar();
			double novoValorPresente = sacadoAttempt.getNovoValorPresente() - sacadoAttempt.getTotalOperar();
			double novaConcentracao = novoValorPresente*sacadoAttempt.getNovaConcentracao()/sacadoAttempt.getNovoValorPresente();
			String sql = "UPDATE `MVCapital`.`ConcentracaoSacado` SET " //$NON-NLS-1$
						+ "`valorPresente` = " + novoValorPresente //$NON-NLS-1$
						+ ",`concentracao` = " + novaConcentracao //$NON-NLS-1$
						+ ",`operar` = " + novoMaximoOperar //$NON-NLS-1$
						+ ",`updateTime` = CURRENT_TIMESTAMP" //$NON-NLS-1$
						+ " WHERE `idEntidadeSacado` = " + sacadoAttempt.getSacado().getIdEntidade() //$NON-NLS-1$
						+ " and `idFundo` = " + operacaoResumo.getFundo().getIdFundo() //$NON-NLS-1$
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
		
		String sql = "UPDATE `MVCapital`.`ConcentracaoCedente` SET " //$NON-NLS-1$
					+ "`valorPresente` = " + novoValorPresente //$NON-NLS-1$
					+ ",`concentracao` = " + novaConcentracao //$NON-NLS-1$
					+ ",`operar` = " + novoMaximoOperar //$NON-NLS-1$
					+ ",`updateTime` = CURRENT_TIMESTAMP" //$NON-NLS-1$
					+ " WHERE `idEntidadeCedente` = " + operacaoResumo.getCedenteAttempt().getCedente().getIdEntidade() //$NON-NLS-1$
					+ " and `idFundo` = " + operacaoResumo.getFundo().getIdFundo() //$NON-NLS-1$
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
		System.out.println("Downloading pdf file for " + operacao.getNomeArquivo()); //$NON-NLS-1$
		System.out.println(rowOperation.get(6).getAttribute("name")); //$NON-NLS-1$
        String fileTxtName = downloadPDFReport(rowOperation.get(6), rootDownloadsLinux, sshClient, clickX, clickY);
        if(fileTxtName=="") //$NON-NLS-1$
        {
        	return;
        }
        fileTxtName = fileTxtName.replace(".pdf", ""); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("Reading text file: " + fileTxtName); //$NON-NLS-1$
        timeAndWait(6);
        ArrayList<String> linesTemp = null;
    	linesTemp = OperadorPortalPaulistaChrome.readTxtFile(fileTxtName);
    	if(linesTemp==null)
    	{
    		return;
    	}
        deletePDFReport(sshClient, rootDownloadsLinux);
        Relatorio relatorio=OperadorPortalPaulistaChrome.buildBlocks(linesTemp);
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
        	System.out.println("Click On: " + wePDF.getAttribute("name")); //$NON-NLS-1$ //$NON-NLS-2$
    		wePDF.click();
    	}
    	else
    	{
    		System.out.println("PDF Link is not displayed"); //$NON-NLS-1$
    		return ""; //$NON-NLS-1$
    	}
    	timeAndWait(3);    	
//	
//	    Robot r=null;
//		try {
//			r = new Robot();
//		} catch (AWTException e) {
//			e.printStackTrace();
//		}
//		
//		if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
//		{   	    
//    	    r.mouseMove(clickX, clickY);//coordinates of save button
//		}
//		else if(System.getProperty("os.name").toLowerCase().contains("linux")) //$NON-NLS-1$ //$NON-NLS-2$
//		{			
//    	    r.mouseMove(790, 530);//coordinates of save button
//		}
//		
//	    r.mousePress(InputEvent.BUTTON1_MASK);
//	    r.mouseRelease(InputEvent.BUTTON1_MASK);
//
//	    //wait for file to download
//	    timeAndWait(4);
	    String command="";	     //$NON-NLS-1$
	    command="ls " + rootDownloadsLinux + "/*.pdf"; //$NON-NLS-1$ //$NON-NLS-2$
	    System.out.println("command: " + command); //$NON-NLS-1$
        String output = sshClient.executeCommandOutput(command);
        
        String fileTxtName = output.split("\n")[0]+".txt"; //$NON-NLS-1$ //$NON-NLS-2$
//        command = "pdftotext -layout " + " " + rootDownloadsLinux + "/"  + output; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        command = "pdftotext -layout " + output; //$NON-NLS-1$ 

        System.out.println("command: " + command); //$NON-NLS-1$
        sshClient.executeCommand(command);
        
	    command="ls " + rootDownloadsLinux + "/*.txt"; //$NON-NLS-1$ //$NON-NLS-2$
	    System.out.println("command: " + command); //$NON-NLS-1$
	    sshClient.executeCommand(command);
	    
        if(System.getProperty("os.name").toLowerCase().contains("windows")) //$NON-NLS-1$ //$NON-NLS-2$
		{
			fileTxtName = "c:\\DownloadsPortal"+"\\"+fileTxtName; //$NON-NLS-1$ //$NON-NLS-2$
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux")) //$NON-NLS-1$ //$NON-NLS-2$
		{			
			fileTxtName = fileTxtName; 
		}       
        return fileTxtName;
    }

    public static void deletePDFReport(SshClient sshClient, String rootDownloadsLinux)
    {
	    String command="rm -rf " + rootDownloadsLinux +"/*"; //$NON-NLS-1$ //$NON-NLS-2$
	    System.out.println(command);
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
			stmt = (Statement) OperadorPortalPaulistaChrome.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT * FROM operacao WHERE " //$NON-NLS-1$
				       + " dataDeEntrada = '" + sdfd.format(Calendar.getInstance().getTime()) + "'" //$NON-NLS-1$ //$NON-NLS-2$
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
				SimpleDateFormat sdfdTemp = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
				 int idOperacaoTemp=0;
				 int idFundoTemp=0;
				 String nomeArquivoTemp=""; //$NON-NLS-1$
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
				
				idOperacaoTemp=rs.getInt("idOperacao"); //$NON-NLS-1$
				idFundoTemp=rs.getInt("idFundo"); //$NON-NLS-1$
				nomeArquivoTemp=rs.getString("nomeArquivo"); //$NON-NLS-1$
				idEntidadeCedenteTemp=rs.getInt("idEntidadeCedente"); //$NON-NLS-1$
				totalAquisicaoTemp=rs.getDouble("totalAquisicao"); //$NON-NLS-1$
				totalNominalTemp=rs.getDouble("totalNominal"); //$NON-NLS-1$
				
				dataDeEntradaTemp=rs.getDate("dataDeEntrada"); //$NON-NLS-1$
				
				remessaComAdiantamentoTemp=rs.getDouble("remessaComAdiantamento"); //$NON-NLS-1$
				recompraComAdiantamentoTemp=rs.getDouble("recompraComAdiantamento"); //$NON-NLS-1$
				reembolsoContaInternaTemp=rs.getDouble("reembolsoContaInterna"); //$NON-NLS-1$
				
				remessaSemAdiantamentoTemp=rs.getDouble("remessaSemAdiantamento"); //$NON-NLS-1$
				recompraSemAdiantamentoTemp=rs.getDouble("recompraSemAdiantamento"); //$NON-NLS-1$
				reembolsoContaExternaTemp=rs.getDouble("reembolsoContaExterna"); //$NON-NLS-1$
				
				valorTEDContaInternaTemp=rs.getDouble("valorTEDContaInterna"); //$NON-NLS-1$
				valorTEDContaExterna1Temp=rs.getDouble("valorTEDContaExterna1"); //$NON-NLS-1$
				valorTEDContaExterna2Temp=rs.getDouble("valorTEDContaExterna2"); //$NON-NLS-1$
				
				idContaInternaTemp=rs.getInt("idContaInterna"); //$NON-NLS-1$
				idContaExterna1Temp=rs.getInt("idContaExterna1"); //$NON-NLS-1$
				idContaExterna2Temp=rs.getInt("idContaExterna2"); //$NON-NLS-1$
				
				System.out.println("--------------------"); //$NON-NLS-1$
				System.out.println("idOperacao: " + idOperacaoTemp); //$NON-NLS-1$
				System.out.println("idFundo: " + idFundoTemp); //$NON-NLS-1$
				System.out.println("NomeArquivo: " + nomeArquivoTemp); //$NON-NLS-1$
				System.out.println("idEntidadeCedente: " + idEntidadeCedenteTemp); //$NON-NLS-1$
				System.out.println("TotalAquisicao: " + totalAquisicaoTemp); //$NON-NLS-1$
				System.out.println("TotalNominal: " + totalNominalTemp); //$NON-NLS-1$
				System.out.println("DataDeEntrada:" + sdfdTemp.format(dataDeEntradaTemp)); //$NON-NLS-1$
				System.out.println("RemessaComAdiantamento: " + remessaComAdiantamentoTemp); //$NON-NLS-1$
				System.out.println("RecompraComAdiantamento: " + recompraComAdiantamentoTemp); //$NON-NLS-1$
				System.out.println("ReembolsoComAdiantamento: " + reembolsoContaInternaTemp); //$NON-NLS-1$
				System.out.println("RemessaSemAdiantamento: " + remessaSemAdiantamentoTemp); //$NON-NLS-1$
				System.out.println("RecompraSemAdiantamento: " + recompraSemAdiantamentoTemp); //$NON-NLS-1$
				System.out.println("ReembolsoSemAdiantamento: " + reembolsoContaExternaTemp); //$NON-NLS-1$
				
				System.out.println("ValorTedContaInterna: " + valorTEDContaInternaTemp); //$NON-NLS-1$
				System.out.println("ValorTedContaExterna1: " + valorTEDContaExterna1Temp); //$NON-NLS-1$
				System.out.println("ValorTedContaExterna2: " + valorTEDContaExterna2Temp); //$NON-NLS-1$
				
				System.out.println("idContaInterna: " + idContaInternaTemp); //$NON-NLS-1$
				System.out.println("idContaExterna1: " + idContaExterna1Temp); //$NON-NLS-1$
				System.out.println("idContaExterna2: " + idContaExterna2Temp); //$NON-NLS-1$
				
				
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

		System.out.println("Found " + idOperacao.size() + " operations"); //$NON-NLS-1$ //$NON-NLS-2$
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
				HandlerDireitoCreditorio.readStored(op, conn);
				OperationSummary operationSummary = new OperationSummary(op);
				op.setResumo(operationSummary);
				HandlerOperacoes.assessOperation(op,conn);
				op.show();
				
				if(op.getRelatorio().getBlockDireitosCreditorios().getDireitosCreditorios().size()>0)
				{
					storedOperations.add(op);
					System.out.println("Skipping: " + op.getNomeArquivo() + " from " + op.getFundo().getNome()); //$NON-NLS-1$ //$NON-NLS-2$
				}

				if(!op.isTedEnviada())
	        	{
	        		System.out.println("--Check electronic transfer--"); //$NON-NLS-1$
	        		System.out.println("--For " + op.getNomeArquivo() + "--"); //$NON-NLS-1$ //$NON-NLS-2$
	        		
	        		boolean haveResourcesSent=false;
	        		for(Status status:op.getStatuses())
	        		{
	        			if(status.getDescription().toLowerCase().contains("recursos enviada")) //$NON-NLS-1$
	        			{
	        				haveResourcesSent=true;
	        			}
	        		}
	        		
	        		if(haveResourcesSent)
	        		{        			
	        			System.out.println("\tHaveResourcesSent"); //$NON-NLS-1$
	        			HandlerOperacoes.transferDone(op, conn);
	        		}
	        		else
	        		{
	        			System.out.println("Skip checking transfer controller"); //$NON-NLS-1$
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
			( status.getDescription().toLowerCase().contains("aguardando assinatura digital") //$NON-NLS-1$
			 || status.getDescription().toLowerCase().contains("gestor de ted")) //$NON-NLS-1$
			{
				alreadyApprouved=true;
				System.out.println("Operation already approuved and OK!"); //$NON-NLS-1$
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
			( status.getDescription().toLowerCase().contains("aprovado manualmente"))			  //$NON-NLS-1$
			{
				approvedByHand=true;
				System.out.println("Operation already approuved by hand probably not being OK!"); //$NON-NLS-1$
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

		String query = "SELECT * FROM movimentacao WHERE" //$NON-NLS-1$
				+ " " + "data='"+ sdfcd.format(op.getDataDeImportacao()) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ " " + "AND idFundo=" + op.getFundo().getIdFundo() //$NON-NLS-1$ //$NON-NLS-2$
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
				
				int idMovimentacao = rs.getInt("idMovimentacao"); //$NON-NLS-1$
				int idTipoDeMovimentacao = rs.getInt("idTipoDeMovimentacao"); //$NON-NLS-1$
				Date data = rs.getDate("data"); //$NON-NLS-1$
				Date hora = rs.getDate("hora"); //$NON-NLS-1$
				String descricao = rs.getString("descricao"); //$NON-NLS-1$
				double valor = rs.getDouble("valor"); //$NON-NLS-1$

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
			System.out.println("Pass query execution!");	 //$NON-NLS-1$
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		ArrayList<Double> valoresTED = new ArrayList<Double>();
		if (op.getValorTotalComAdiantamento()!=0.0)
		{
			valoresTED.add(op.getValorTotalComAdiantamento());
			System.out.println("TED Interna: " + op.getValorTotalComAdiantamento()); //$NON-NLS-1$
		}
		if (op.getValorTotalCedenteSemAdiantamento1()!=0.0)
		{
			valoresTED.add(op.getValorTotalCedenteSemAdiantamento1());
			System.out.println("TED Externa 1: " + op.getValorTotalCedenteSemAdiantamento1()); //$NON-NLS-1$
		}
		if (op.getValorTotalCedenteSemAdiantamento2()!=0.0)
		{
			valoresTED.add(op.getValorTotalCedenteSemAdiantamento2());
			System.out.println("TED Externa 1: " + op.getValorTotalCedenteSemAdiantamento1()); //$NON-NLS-1$
		}		
		
		for(Double valorTED:valoresTED)
		{
			System.out.println("Looking for " + valorTED); //$NON-NLS-1$
			boolean enviada=false;			
			for(Movimentacao movimentacao:movimentacoes)
			{
				movimentacao.show();
				if(Double.compare(valorTED,movimentacao.getValor())==0.0 && movimentacao.getTipoDeMovimentacao().getTipo().equals("D")) //$NON-NLS-1$
				{
					System.out.println("TED " + valorTED + " para " + op.getCedente().getNome() + " enviada"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
    			if(status.getDescription().toLowerCase().contains("ted liquidada")) //$NON-NLS-1$
    			{
    				haveStatusLiquidada=true;
    			}
    			
    		}			
			if(!haveStatusLiquidada)
			{
    			Status status = new Status("TED Liquidada", Calendar.getInstance().getTime(), conn); //$NON-NLS-1$
    			op.getStatuses().add(status);
			}
			else
			{
    			System.out.println("Have status TED Liquidada"); //$NON-NLS-1$
    			int iStatus=0;
    			for(Status status:op.getStatuses())
    			{
    				if(status.getDescription().toLowerCase().contains("ted liquidada")) //$NON-NLS-1$
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
