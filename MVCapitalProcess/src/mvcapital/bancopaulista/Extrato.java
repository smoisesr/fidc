package mvcapital.bancopaulista;

import java.util.ArrayList;

import mvcapital.fundo.FundoDeInvestimento;

public class Extrato 
{
	private FundoDeInvestimento fundo=new FundoDeInvestimento();
	private ArrayList<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();
	
	public Extrato()
	{
		
	}
	
	public Extrato(FundoDeInvestimento fundo, ArrayList<Movimentacao> movimentacoes)
	{
		this.fundo=fundo;
		this.movimentacoes=movimentacoes;
	}
	
	public Extrato(String nomeFundo, ArrayList<Movimentacao> movimentacoes)
	{
		this.fundo.setNomeCurto(nomeFundo);
		this.movimentacoes=movimentacoes;
	}
	
	public String getString()
	{
		String stringExtrato="";
		for (Movimentacao mov:movimentacoes)
		{
			stringExtrato=stringExtrato+mov.getString()+"\n";
		}
		return stringExtrato;
	}
	
	public void show()
	{
		if(movimentacoes.size()>0)
		{
			for (Movimentacao mov:movimentacoes)
			{
				mov.show();
			}
		}
	}
	public FundoDeInvestimento getFundo() {
		return fundo;
	}
	public void setFundo(FundoDeInvestimento fundo) {
		this.fundo = fundo;
	}

	public ArrayList<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(ArrayList<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}
}
