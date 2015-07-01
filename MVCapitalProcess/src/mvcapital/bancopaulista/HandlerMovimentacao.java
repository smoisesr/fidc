package mvcapital.bancopaulista;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mvcapital.fundo.FundoDeInvestimento;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class HandlerMovimentacao 
{
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdftd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public HandlerMovimentacao() 
	{
		
	}

	public static ArrayList<Movimentacao> read(FundoDeInvestimento fundo, Date data, Connection conn)
	{
		ArrayList<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT * FROM movimentacao WHERE "
						+ " " + "idFundo = " + fundo.getIdFundo()
 						+ " " + "AND data='"+ sdfd.format(data) +"'"
						;
		System.out.println(query);

		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idMovimentacao=rs.getInt("idMovimentacao");
				Date hora = rs.getTime("hora");
				String descricao = rs.getString("descricao");
				double valor = rs.getDouble("valor");
				TipoDeMovimentacao tipoDeMovimentacao = new TipoDeMovimentacao(rs.getInt("idTipoDeMovimentacao"), conn);

				Movimentacao movimentacao = new Movimentacao();
				movimentacao.setIdMovimentacao(idMovimentacao);
				movimentacao.setFundo(fundo);
				movimentacao.setData(data);
				movimentacao.setHora(hora);
				movimentacao.setDescricao(descricao);				
				movimentacao.setValor(valor);
				movimentacao.setTipoDeMovimentacao(tipoDeMovimentacao);

				movimentacoes.add(movimentacao);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movimentacoes;
	}
	
	public static void store(Movimentacao movimentacao, Connection conn)
	{
//		private int idMovimentacao=0;
//		private Entidade fundo=new Entidade();
//		private Date data=Calendar.getInstance().getTime();
//		private Date hora=Calendar.getInstance().getTime();
//		private String descricao="";
//		private Double valor=0.0;
//		private TipoDeMovimentacao tipoDeMovimentacao=new TipoDeMovimentacao();

		System.out.println("Mov: " + sdfd.format(movimentacao.getData()) + " " + movimentacao.getTipoDeMovimentacao().getTipo() + " " + movimentacao.getDescricao());
		if(movimentacao.getTipoDeMovimentacao().getTipo().equals("S"))
		{
			Statement stmt=null;
			try {
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
			String query = "SELECT idMovimentacao FROM movimentacao WHERE"
							+ " " + "idFundo = " + movimentacao.getFundo().getIdFundo()
							+ " " + "AND descricao = \"" + movimentacao.getDescricao() + "\""
	 						+ " " + "AND data='"+ sdfd.format(movimentacao.getData()) +"'"
							;
			System.out.println(query);
			int idMovimentacao=0;
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(query);
				while (rs.next())
				{
					idMovimentacao=rs.getInt("idMovimentacao");
				}
	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(idMovimentacao==0)
			{
				Statement stmt2=null;
				try {
					stmt2 = (Statement) conn.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				String sql = "INSERT INTO `mvcapital`.`movimentacao` (`idFundo`, `idtipoDeMovimentacao`, `data`, `hora`, `descricao`, `valor`)"
							+ " " + "VALUES ("
									+ movimentacao.getFundo().getIdFundo()
									+ "," + movimentacao.getTipoDeMovimentacao().getIdTipoDeMovimentacao()
									+ "," + "'" + sdfd.format(movimentacao.getData()) + "'"
									+ "," + "'" + sdftd.format(movimentacao.getHora()) + "'"
									+ "," + "'" + movimentacao.getDescricao() + "'"
									+ "," + movimentacao.getValor()
									+ ")";
				System.out.println(sql);
				try {
					stmt2.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else
			{
				
				String sql = "UPDATE `mvcapital`.`movimentacao` "
							+ "SET "
							+ "`hora` = " + "'" + sdftd.format(movimentacao.getHora()) + "'"
							+ ", " + "`valor` = " + movimentacao.getValor()
							+ " WHERE `idMovimentacao` = " + idMovimentacao;
							
				System.out.println(sql);
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				System.out.println("Movimentacao existente " + movimentacao.getFundo().getNomeCurto() + " " + movimentacao.getDescricao() + " " + movimentacao.getValor());
			}			
		}
		else
		{
			Statement stmt=null;
			try {
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
			String query = "SELECT idMovimentacao FROM movimentacao WHERE"
							+ " " + "idFundo = " + movimentacao.getFundo().getIdFundo()
							+ " " + " AND descricao=\""+ movimentacao.getDescricao() +"\""
	 						+ " " + " AND data='"+ sdfd.format(movimentacao.getData()) +"'"
							+ " " + " AND valor=" + movimentacao.getValor()
							;
			System.out.println(query);
			int idMovimentacao=0;
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(query);
				while (rs.next())
				{
					idMovimentacao=rs.getInt("idMovimentacao");
				}
	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(idMovimentacao==0)
			{
				Statement stmt2=null;
				try {
					stmt2 = (Statement) conn.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				String sql = "INSERT INTO `mvcapital`.`movimentacao` (`idFundo`, `idtipoDeMovimentacao`, `data`, `hora`, `descricao`, `valor`)"
							+ " " + "VALUES ("
									+ movimentacao.getFundo().getIdFundo()
									+ "," + movimentacao.getTipoDeMovimentacao().getIdTipoDeMovimentacao()
									+ "," + "'" + sdfd.format(movimentacao.getData()) + "'"
									+ "," + "'" + sdftd.format(movimentacao.getHora()) + "'"
									+ "," + "'" + movimentacao.getDescricao() + "'"
									+ "," + movimentacao.getValor()
									+ ")";
				System.out.println(sql);
				try {
					stmt2.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("Movimentacao existente " + movimentacao.getFundo().getNomeCurto() + " " + movimentacao.getDescricao() + " " + movimentacao.getValor());
			}
		}
	}

	public static SimpleDateFormat getSdfd() {
		return sdfd;
	}

	public static void setSdfd(SimpleDateFormat sdfd) {
		HandlerMovimentacao.sdfd = sdfd;
	}

	public static SimpleDateFormat getSdftd() {
		return sdftd;
	}

	public static void setSdftd(SimpleDateFormat sdftd) {
		HandlerMovimentacao.sdftd = sdftd;
	}

}
