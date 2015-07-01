package mvcapital.bancopaulista;

import java.util.ArrayList;

public class HandlerExtrato 
{

	public HandlerExtrato() 
	{
	
	}

	public static void sort(Extrato extrato)
	{
		ArrayList<Movimentacao> sorted = new ArrayList<Movimentacao>();
		for(Movimentacao m:extrato.getMovimentacoes())
		{
			sorted.add(m);
		}
		
		for(int i=0;i<sorted.size();i++)
		{
			Movimentacao ms = new Movimentacao();
			for(int j=i+1;j<sorted.size();j++)
			{
				if(sorted.get(i).getHora().after(sorted.get(j).getHora()))
				{
					ms = sorted.get(j);
					sorted.set(j,sorted.get(i));
					sorted.set(i, ms);					
				}
			}
		}
		
		System.out.println("Sort elements for " + extrato.getFundo().getNome());
		for(Movimentacao m:sorted)
		{
			m.show();
		}
		extrato.setMovimentacoes(sorted);
	}
}
