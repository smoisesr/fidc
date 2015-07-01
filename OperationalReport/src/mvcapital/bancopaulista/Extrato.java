package mvcapital.bancopaulista;

import java.util.ArrayList;

import fundo.FundoDeInvestimento;

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
		String stringExtrato=""; //$NON-NLS-1$
		for (Movimentacao mov:this.movimentacoes)
		{
			stringExtrato=stringExtrato+mov.getString()+"\n"; //$NON-NLS-1$
		}
		return stringExtrato;
	}
	
	public void show()
	{
		if(this.movimentacoes.size()>0)
		{
			for (Movimentacao mov:this.movimentacoes)
			{
				mov.show();
			}
		}
	}
	public FundoDeInvestimento getFundo() {
		return this.fundo;
	}
	public void setFundo(FundoDeInvestimento fundo) {
		this.fundo = fundo;
	}

	public ArrayList<Movimentacao> getMovimentacoes() {
		return this.movimentacoes;
	}

	public void setMovimentacoes(ArrayList<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}
}
