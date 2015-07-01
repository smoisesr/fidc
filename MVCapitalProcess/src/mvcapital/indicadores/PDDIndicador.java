package mvcapital.indicadores;

import java.util.ArrayList;
import java.util.Date;

import mvcapital.fundo.FundoDeInvestimento;

import com.mysql.jdbc.Connection;

public class PDDIndicador extends Indicador
{
	public PDDIndicador()
	{
		
	}

	public PDDIndicador(FundoDeInvestimento fundo, TipoIndicador tipoIndicador, Date dataEstoque)
	{
		super(fundo,dataEstoque);
		this.tipoIndicador=tipoIndicador;
	}
	
	public static ArrayList<Double> Calculate(ArrayList<Integer> idTitulos, Date dataEstoque, Connection conn)
	{
		ArrayList<Double> provisaoParaDevedoresDuvidososTodos = new ArrayList<Double>();
		ArrayList<Integer> idProvisaoParaDevedoresDuvidososUpto15 = new ArrayList<Integer>();
		ArrayList<Integer> idProvisaoParaDevedoresDuvidososFrom16Upto30 = new ArrayList<Integer>();
		ArrayList<Integer> idProvisaoParaDevedoresDuvidososFrom31Upto45 = new ArrayList<Integer>();
		ArrayList<Integer> idProvisaoParaDevedoresDuvidososFrom46Upto60 = new ArrayList<Integer>();
		ArrayList<Integer> idProvisaoParaDevedoresDuvidososFrom61Upto90 = new ArrayList<Integer>();
		ArrayList<Integer> idProvisaoParaDevedoresDuvidososFromAbove90 = new ArrayList<Integer>();
		double valorProvisaoParaDevedoresDuvidososUpto15=0.0;
		double valorProvisaoParaDevedoresDuvidosos16Upto30=0.0;
		double valorProvisaoParaDevedoresDuvidosos31Upto45=0.0;
		double valorProvisaoParaDevedoresDuvidosos46Upto60=0.0;
		double valorProvisaoParaDevedoresDuvidosos61Upto90=0.0;
		double valorProvisaoParaDevedoresDuvidososAbove90=0.0;
		
		double valorProvisaoParaDevedoresDuvidosos=0.0;
		
		{
			idProvisaoParaDevedoresDuvidososUpto15 = HandlerIndicadores.titulosVencidos(idTitulos, dataEstoque, 15);
			System.out.println(idProvisaoParaDevedoresDuvidososUpto15.size() + " titulos Upto15 "); //$NON-NLS-1$
		}
		{
			idProvisaoParaDevedoresDuvidososFrom16Upto30 = HandlerIndicadores.titulosVencidos(idTitulos, dataEstoque, 30, 16);
			System.out.println(idProvisaoParaDevedoresDuvidososFrom16Upto30.size() + " titulos From16Upto30 "); //$NON-NLS-1$
		}
		{
			idProvisaoParaDevedoresDuvidososFrom31Upto45 = HandlerIndicadores.titulosVencidos(idTitulos, dataEstoque, 45, 31);
			System.out.println(idProvisaoParaDevedoresDuvidososFrom31Upto45.size() + " titulos From31Upto45 "); //$NON-NLS-1$
		}
		{
			idProvisaoParaDevedoresDuvidososFrom46Upto60 = HandlerIndicadores.titulosVencidos(idTitulos, dataEstoque, 60, 46);
			System.out.println(idProvisaoParaDevedoresDuvidososFrom46Upto60.size() + " titulos From46Upto60 "); //$NON-NLS-1$
		}
		{
			idProvisaoParaDevedoresDuvidososFrom61Upto90 = HandlerIndicadores.titulosVencidos(idTitulos, dataEstoque, 90, 61);
			System.out.println(idProvisaoParaDevedoresDuvidososFrom61Upto90.size() + " titulos From61Upto90 "); //$NON-NLS-1$
		}
		{
			idProvisaoParaDevedoresDuvidososFromAbove90 = HandlerIndicadores.titulosVencidos(idTitulos, dataEstoque, 9000, 91);
			System.out.println(idProvisaoParaDevedoresDuvidososFromAbove90.size() + " titulos FromAbove90 "); //$NON-NLS-1$
		}
		
		if(idProvisaoParaDevedoresDuvidososUpto15.size()>0)
		{			
			valorProvisaoParaDevedoresDuvidososUpto15=HandlerIndicadores.valorPresente(idProvisaoParaDevedoresDuvidososUpto15, dataEstoque);
		}
		System.out.println("provisaoParaDevedoresDuvidososUpto15:" + valorProvisaoParaDevedoresDuvidososUpto15); //$NON-NLS-1$
		if(idProvisaoParaDevedoresDuvidososFrom16Upto30.size()>0)
		{
			valorProvisaoParaDevedoresDuvidosos16Upto30=HandlerIndicadores.valorPresente(idProvisaoParaDevedoresDuvidososFrom16Upto30, dataEstoque);
		}		
		System.out.println("provisaoParaDevedoresDuvidososFrom16Upto30:" + valorProvisaoParaDevedoresDuvidosos16Upto30); //$NON-NLS-1$
		if(idProvisaoParaDevedoresDuvidososFrom31Upto45.size()>0)
		{
			valorProvisaoParaDevedoresDuvidosos31Upto45=HandlerIndicadores.valorPresente(idProvisaoParaDevedoresDuvidososFrom31Upto45, dataEstoque);
		}
		System.out.println("provisaoParaDevedoresDuvidosos31Upto45:" + valorProvisaoParaDevedoresDuvidosos31Upto45); //$NON-NLS-1$
		
		if(idProvisaoParaDevedoresDuvidososFrom46Upto60.size()>0)
		{
			valorProvisaoParaDevedoresDuvidosos46Upto60=HandlerIndicadores.valorPresente(idProvisaoParaDevedoresDuvidososFrom46Upto60, dataEstoque);
		}
		System.out.println("provisaoParaDevedoresDuvidosos46Upto60:" + valorProvisaoParaDevedoresDuvidosos46Upto60); //$NON-NLS-1$
		if(idProvisaoParaDevedoresDuvidososFrom16Upto30.size()>0)
		{
			valorProvisaoParaDevedoresDuvidosos61Upto90=HandlerIndicadores.valorPresente(idProvisaoParaDevedoresDuvidososFrom61Upto90, dataEstoque);
		}
		System.out.println("provisaoParaDevedoresDuvidosos61Upto90:" + valorProvisaoParaDevedoresDuvidosos61Upto90); //$NON-NLS-1$
		if(idProvisaoParaDevedoresDuvidososFromAbove90.size()>0)
		{			
			valorProvisaoParaDevedoresDuvidososAbove90=HandlerIndicadores.valorPresente(idProvisaoParaDevedoresDuvidososFromAbove90, dataEstoque);
		}
		System.out.println("provisaoParaDevedoresDuvidososAbove90:" + valorProvisaoParaDevedoresDuvidososAbove90); //$NON-NLS-1$
		
		idProvisaoParaDevedoresDuvidososUpto15 = null;
		idProvisaoParaDevedoresDuvidososFrom16Upto30 = null;
		idProvisaoParaDevedoresDuvidososFrom31Upto45 = null;
		idProvisaoParaDevedoresDuvidososFrom46Upto60 = null;
		idProvisaoParaDevedoresDuvidososFrom61Upto90 = null;
		idProvisaoParaDevedoresDuvidososFromAbove90 = null;
		
		valorProvisaoParaDevedoresDuvidosos = valorProvisaoParaDevedoresDuvidososUpto15 
										+ valorProvisaoParaDevedoresDuvidosos16Upto30
										+ valorProvisaoParaDevedoresDuvidosos31Upto45
										+ valorProvisaoParaDevedoresDuvidosos46Upto60
										+ valorProvisaoParaDevedoresDuvidosos61Upto90
										+ valorProvisaoParaDevedoresDuvidososAbove90;
		
		
		provisaoParaDevedoresDuvidososTodos.add(valorProvisaoParaDevedoresDuvidososUpto15);
		provisaoParaDevedoresDuvidososTodos.add(valorProvisaoParaDevedoresDuvidosos16Upto30);
		provisaoParaDevedoresDuvidososTodos.add(valorProvisaoParaDevedoresDuvidosos31Upto45);
		provisaoParaDevedoresDuvidososTodos.add(valorProvisaoParaDevedoresDuvidosos46Upto60);
		provisaoParaDevedoresDuvidososTodos.add(valorProvisaoParaDevedoresDuvidosos61Upto90);
		provisaoParaDevedoresDuvidososTodos.add(valorProvisaoParaDevedoresDuvidososAbove90);
		provisaoParaDevedoresDuvidososTodos.add(valorProvisaoParaDevedoresDuvidosos);
		return provisaoParaDevedoresDuvidososTodos;
	}
}
