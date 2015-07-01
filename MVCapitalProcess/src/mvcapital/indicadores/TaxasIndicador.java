package mvcapital.indicadores;

import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Connection;

public class TaxasIndicador
{

	public TaxasIndicador()
	{
		
	}
	
	public static ArrayList<Double> Calculate(ArrayList<Integer> idTitulos, Date dataEstoque, Connection conn)
	{
//		Taxa Media ao ano 
//		Taxa Media sobre CDI 
//		Menor Taxa de Cessao ao ano 
//		Menor Taxa  sobre CDI 
//		Maior Taxa de Cessao ao ano 
//		Taxa Desvio Padrão
//		Taxa Media
//		Taxa Mediana		
		ArrayList<Double> valoresIndicadoresTaxas = new ArrayList<Double>();
		double taxaMediaAoAno = 0.0;
		double taxaMediaSobreCDI = 0.0;
		double menorTaxaDeCessaoAoAno = 0.0;
		double menorTaxaSobreCDI = 0.0;
		double maiorTaxaDeCessaoAoAno = 0.0;
		double taxaDesvioPadrao = 0.0;
		{
			taxaMediaAoAno = HandlerIndicadores.taxaMediaAoAno(idTitulos);
		}
		{
			taxaMediaSobreCDI = HandlerIndicadores.taxaMediaSobreCDI(idTitulos, dataEstoque);
		}
		{
			menorTaxaDeCessaoAoAno = HandlerIndicadores.menorTaxaDeCessaoAoAno(idTitulos);
		}
		{
			menorTaxaSobreCDI = HandlerIndicadores.menorTaxaSobreCDI(idTitulos, dataEstoque);
		}
		{
			maiorTaxaDeCessaoAoAno = HandlerIndicadores.maiorTaxaDeCessaoAoAno(idTitulos);
		}
		{
			taxaDesvioPadrao = HandlerIndicadores.taxaDesvioPadrao(idTitulos, taxaMediaAoAno);
		}
		
		valoresIndicadoresTaxas.add(taxaMediaAoAno);
		valoresIndicadoresTaxas.add(taxaMediaSobreCDI);
		valoresIndicadoresTaxas.add(menorTaxaDeCessaoAoAno);
		valoresIndicadoresTaxas.add(menorTaxaSobreCDI);
		valoresIndicadoresTaxas.add(maiorTaxaDeCessaoAoAno);
		valoresIndicadoresTaxas.add(taxaDesvioPadrao);
		
		System.out.println("TaxaMediaAoAno: " + taxaMediaAoAno); //$NON-NLS-1$
		System.out.println("TaxaMediaSobreCDI: " + taxaMediaSobreCDI); //$NON-NLS-1$
		System.out.println("menorTaxaDeCessaoAoAno: " + menorTaxaDeCessaoAoAno); //$NON-NLS-1$
		System.out.println("menorTaxaSobreCDI: " + menorTaxaSobreCDI); //$NON-NLS-1$
		System.out.println("maiorTaxaDeCessaoAoAno: " + maiorTaxaDeCessaoAoAno); //$NON-NLS-1$
		System.out.println("TaxaDesvioPadrao: " + taxaDesvioPadrao); //$NON-NLS-1$
		return valoresIndicadoresTaxas;
	}
		
}
