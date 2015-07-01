package mvcapital.bancopaulista;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class TipoDeMovimentacao {

	private int idTipoDeMovimentacao=0;
	private String tipo="";
	private String descricao="";
	
	public TipoDeMovimentacao() 
	{
	
	}

	public TipoDeMovimentacao(int idTipoDeMovimentacao, Connection conn) 
	{
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "select * from tipodemovimentacao where idTipoDeMovimentacao=" + idTipoDeMovimentacao;
		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			while(rs.next())
			{
				this.idTipoDeMovimentacao = rs.getInt("idTipoDeMovimentacao");
				this.tipo = rs.getString("tipo");
				this.descricao = rs.getString("descricao");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public TipoDeMovimentacao(String stringTipoDeMovimentacao, Connection conn) 
	{
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "select * from tipodemovimentacao where tipo= \"" + stringTipoDeMovimentacao + "\"";
//		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			while(rs.next())
			{
				this.idTipoDeMovimentacao = rs.getInt("idTipoDeMovimentacao");
				this.tipo = rs.getString("tipo");
				this.descricao = rs.getString("descricao");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int getIdTipoDeMovimentacao() {
		return idTipoDeMovimentacao;
	}

	public void setIdTipoDeMovimentacao(int idTipoDeMovimentacao) {
		this.idTipoDeMovimentacao = idTipoDeMovimentacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
