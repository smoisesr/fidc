package concentracao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class ConcentracaoCedente 
{
	private Entidade cedente = new Entidade();
	private FundoDeInvestimento fundo = new FundoDeInvestimento();
	double valorPresente = 0.0;
	double limite = 0.0;
	double concentracao = 0.0;
	double operar = 0.0;
	Date updateTime = Calendar.getInstance().getTime();

	public ConcentracaoCedente() 
	{
		
	}
	
	public ConcentracaoCedente(Entidade cedente, FundoDeInvestimento fundo, double valorPresente, double limite, double concentracao, double operar, Date updateTime)
	{
		this.cedente=cedente;
		this.fundo=fundo;
		this.valorPresente=valorPresente;
		this.limite=limite;
		this.concentracao=concentracao;
		this.operar=operar;
		this.updateTime=updateTime;
	}
	
	public ConcentracaoCedente(Entidade cedente, FundoDeInvestimento fundo, Connection conn)
	{	
		this.cedente = cedente;
		this.fundo = fundo;
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "Select * from ConcentracaoCedente where idEntidadeCedente = " + cedente.getIdEntidade() + " and idFundo = " + fundo.getIdFundo();
		try 
		{
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				this.valorPresente = rs.getDouble("valorPresente");
				this.limite = rs.getDouble("limite");
				this.concentracao = rs.getDouble("concentracado");
				this.operar = rs.getDouble("operar");
				this.updateTime = rs.getDate("updateTime");
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
			

	public Entidade getCedente() {
		return cedente;
	}

	public void setCedente(Entidade cedente) {
		this.cedente = cedente;
	}

	public FundoDeInvestimento getFundo() {
		return fundo;
	}

	public void setFundo(FundoDeInvestimento fundo) {
		this.fundo = fundo;
	}

	public double getValorPresente() {
		return valorPresente;
	}

	public void setValorPresente(double valorPresente) {
		this.valorPresente = valorPresente;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	public double getConcentracao() {
		return concentracao;
	}

	public void setConcentracao(double concentracao) {
		this.concentracao = concentracao;
	}

	public double getOperar() {
		return operar;
	}

	public void setOperar(double operar) {
		this.operar = operar;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
