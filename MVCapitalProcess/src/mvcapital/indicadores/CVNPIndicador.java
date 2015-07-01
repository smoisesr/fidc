package mvcapital.indicadores;

import java.util.ArrayList;
import java.util.Date;

import mvcapital.fundo.FundoDeInvestimento;

import com.mysql.jdbc.Connection;

public class CVNPIndicador extends Indicador
{
	public CVNPIndicador()
	{
		
	}

	public CVNPIndicador(FundoDeInvestimento fundo, TipoIndicador tipoIndicador, Date dataEstoque)
	{
		super(fundo,dataEstoque);
		this.tipoIndicador=tipoIndicador;
	}
	
	public static ArrayList<Double> Calculate(ArrayList<Integer> idTitulos, Date dataEstoque, Connection conn)
	{
		ArrayList<Double> creditosVencidosNaoPagosTodos = new ArrayList<Double>();
		ArrayList<Integer> idTitulosVencidosUpto15 = new ArrayList<Integer>();
		ArrayList<Integer> idTitulosVencidosFrom16Upto30 = new ArrayList<Integer>();
		ArrayList<Integer> idTitulosVencidosFrom31Upto45 = new ArrayList<Integer>();
		ArrayList<Integer> idTitulosVencidosFrom46Upto60 = new ArrayList<Integer>();
		ArrayList<Integer> idTitulosVencidosFrom61Upto90 = new ArrayList<Integer>();
		ArrayList<Integer> idTitulosVencidosFromAbove90 = new ArrayList<Integer>();
		double valorCreditosVencidosNaoPagosUpto15=0.0;
		double valorCreditosVencidosNaoPagos16Upto30=0.0;
		double valorCreditosVencidosNaoPagos31Upto45=0.0;
		double valorCreditosVencidosNaoPagos46Upto60=0.0;
		double valorCreditosVencidosNaoPagos61Upto90=0.0;
		double valorCreditosVencidosNaoPagosAbove90=0.0;
		
		double valorCreditosVencidosNaoPagos=0.0;
		
		{
			idTitulosVencidosUpto15 = HandlerIndicadores.titulosVencidos(idTitulos, dataEstoque, 15);
			System.out.println(idTitulosVencidosUpto15.size() + " titulos Upto15 "); //$NON-NLS-1$
		}
		{
			idTitulosVencidosFrom16Upto30 = HandlerIndicadores.titulosVencidos(idTitulos, dataEstoque, 30, 16);
			System.out.println(idTitulosVencidosFrom16Upto30.size() + " titulos From16Upto30 "); //$NON-NLS-1$
		}
		{
			idTitulosVencidosFrom31Upto45 = HandlerIndicadores.titulosVencidos(idTitulos, dataEstoque, 45, 31);
			System.out.println(idTitulosVencidosFrom31Upto45.size() + " titulos From31Upto45 "); //$NON-NLS-1$
		}
		{
			idTitulosVencidosFrom46Upto60 = HandlerIndicadores.titulosVencidos(idTitulos, dataEstoque, 60, 46);
			System.out.println(idTitulosVencidosFrom46Upto60.size() + " titulos From46Upto60 "); //$NON-NLS-1$
		}
		{
			idTitulosVencidosFrom61Upto90 = HandlerIndicadores.titulosVencidos(idTitulos, dataEstoque, 90, 61);
			System.out.println(idTitulosVencidosFrom61Upto90.size() + " titulos From61Upto90 "); //$NON-NLS-1$
		}
		{
			idTitulosVencidosFromAbove90 = HandlerIndicadores.titulosVencidos(idTitulos, dataEstoque, 9000, 91);
			System.out.println(idTitulosVencidosFromAbove90.size() + " titulos FromAbove90 "); //$NON-NLS-1$
		}
		
		if(idTitulosVencidosUpto15.size()>0)
		{			
			valorCreditosVencidosNaoPagosUpto15=HandlerIndicadores.valorPresente(idTitulosVencidosUpto15, dataEstoque);
		}
		System.out.println("valorCreditosVencidosNaoPagosUpto15:" + valorCreditosVencidosNaoPagosUpto15); //$NON-NLS-1$
		if(idTitulosVencidosFrom16Upto30.size()>0)
		{
			valorCreditosVencidosNaoPagos16Upto30=HandlerIndicadores.valorPresente(idTitulosVencidosFrom16Upto30, dataEstoque);
		}		
		System.out.println("valorCreditosVencidosNaoPagos16Upto30:" + valorCreditosVencidosNaoPagos16Upto30); //$NON-NLS-1$
		if(idTitulosVencidosFrom31Upto45.size()>0)
		{
			valorCreditosVencidosNaoPagos31Upto45=HandlerIndicadores.valorPresente(idTitulosVencidosFrom31Upto45, dataEstoque);
		}
		System.out.println("valorCreditosVencidosNaoPagos31Upto45:" + valorCreditosVencidosNaoPagos31Upto45); //$NON-NLS-1$
		
		if(idTitulosVencidosFrom46Upto60.size()>0)
		{
			valorCreditosVencidosNaoPagos46Upto60=HandlerIndicadores.valorPresente(idTitulosVencidosFrom46Upto60, dataEstoque);
		}
		System.out.println("valorCreditosVencidosNaoPagos46Upto60:" + valorCreditosVencidosNaoPagos46Upto60); //$NON-NLS-1$
		if(idTitulosVencidosFrom16Upto30.size()>0)
		{
			valorCreditosVencidosNaoPagos61Upto90=HandlerIndicadores.valorPresente(idTitulosVencidosFrom61Upto90, dataEstoque);
		}
		System.out.println("valorCreditosVencidosNaoPagos61Upto90:" + valorCreditosVencidosNaoPagos61Upto90); //$NON-NLS-1$
		if(idTitulosVencidosFromAbove90.size()>0)
		{			
			valorCreditosVencidosNaoPagosAbove90=HandlerIndicadores.valorPresente(idTitulosVencidosFromAbove90, dataEstoque);
		}
		System.out.println("valorCreditosVencidosNaoPagosAbove90:" + valorCreditosVencidosNaoPagosAbove90); //$NON-NLS-1$
		
		idTitulosVencidosUpto15 = null;
		idTitulosVencidosFrom16Upto30 = null;
		idTitulosVencidosFrom31Upto45 = null;
		idTitulosVencidosFrom46Upto60 = null;
		idTitulosVencidosFrom61Upto90 = null;
		idTitulosVencidosFromAbove90 = null;
		
		valorCreditosVencidosNaoPagos = valorCreditosVencidosNaoPagosUpto15 
										+ valorCreditosVencidosNaoPagos16Upto30
										+ valorCreditosVencidosNaoPagos31Upto45
										+ valorCreditosVencidosNaoPagos46Upto60
										+ valorCreditosVencidosNaoPagos61Upto90
										+ valorCreditosVencidosNaoPagosAbove90;
		
		
		creditosVencidosNaoPagosTodos.add(valorCreditosVencidosNaoPagosUpto15);
		creditosVencidosNaoPagosTodos.add(valorCreditosVencidosNaoPagos16Upto30);
		creditosVencidosNaoPagosTodos.add(valorCreditosVencidosNaoPagos31Upto45);
		creditosVencidosNaoPagosTodos.add(valorCreditosVencidosNaoPagos46Upto60);
		creditosVencidosNaoPagosTodos.add(valorCreditosVencidosNaoPagos61Upto90);
		creditosVencidosNaoPagosTodos.add(valorCreditosVencidosNaoPagosAbove90);
		creditosVencidosNaoPagosTodos.add(valorCreditosVencidosNaoPagos);
		return creditosVencidosNaoPagosTodos;
	}
}
