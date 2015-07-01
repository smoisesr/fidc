package mvcapital.indicadores;

import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Connection;

public class PrazosIndicador
{

	public PrazosIndicador()
	{
	}

	public static ArrayList<Double> Calculate(ArrayList<Integer> idTitulos, Date dataEstoque, Connection conn)
	{
//		39, Prazo Medio de Atraso , 2015-02-02 10:59:46
//		45, Maior prazo em dias corridos , 2015-02-02 10:59:46
//		46, Menor prazo em dias corridos , 2015-02-02 10:59:46
//		47, Prazo Medio em dias corridos , 2015-02-02 10:59:46
//		96, Prazo Desvio Padrão, 2015-02-02 10:59:49
		
		ArrayList<Double> valoresIndicadoresPrazos = new ArrayList<Double>();
		double prazoMedioDeAtraso = 0.0;
		double maiorPrazoDiasCorridos = 0.0;
		double menorPrazoDiasCorridos = 0.0;
		double prazoMedioDiasCorridos = 0.0;
		double prazoDesvioPadrao = 0.0;
		
		{
			prazoMedioDeAtraso = HandlerIndicadores.prazoMedioAtraso(idTitulos, dataEstoque);
		}
		{
			maiorPrazoDiasCorridos = HandlerIndicadores.maiorPrazoDiasCorridos(idTitulos, dataEstoque);
		}
		{
			menorPrazoDiasCorridos = HandlerIndicadores.menorPrazoDiasCorridos(idTitulos, dataEstoque);
		}
		{
			prazoMedioDiasCorridos = HandlerIndicadores.prazoMedioDiasCorridos(idTitulos, dataEstoque);
		}
		{
			prazoDesvioPadrao = HandlerIndicadores.prazoDesvioPadrao(idTitulos, prazoMedioDiasCorridos);
		}
		
		valoresIndicadoresPrazos.add(prazoMedioDeAtraso);
		valoresIndicadoresPrazos.add(maiorPrazoDiasCorridos);
		valoresIndicadoresPrazos.add(menorPrazoDiasCorridos);
		valoresIndicadoresPrazos.add(prazoMedioDiasCorridos);
		valoresIndicadoresPrazos.add(prazoDesvioPadrao);
		
		System.out.println("prazoMedioDeAtraso: " + prazoMedioDeAtraso); //$NON-NLS-1$
		System.out.println("maiorPrazoDiasCorridos: " + maiorPrazoDiasCorridos); //$NON-NLS-1$
		System.out.println("menorPrazoDiasCorridos: " + menorPrazoDiasCorridos); //$NON-NLS-1$
		System.out.println("prazoMedioDiasCorridos: " + prazoMedioDiasCorridos); //$NON-NLS-1$
		System.out.println("prazoDesvioPadrao: " + prazoDesvioPadrao); //$NON-NLS-1$
		return valoresIndicadoresPrazos;
	}
		
	
	
}
