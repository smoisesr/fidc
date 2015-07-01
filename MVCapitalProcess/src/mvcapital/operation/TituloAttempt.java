package mvcapital.operation;

import java.util.ArrayList;

import mvcapital.limites.Limite;
import mvcapital.relatorio.cessao.Titulo;

public class TituloAttempt 
{
	private boolean aprovado=false;
	private Titulo titulo = new Titulo();
	private ArrayList<Limite> limites = new ArrayList<Limite>();
	private double taxaDiariaSobreCDI = 0.0;
	
	public TituloAttempt() 
	{
		
	}

	public TituloAttempt(Titulo titulo) 
	{
		this.titulo=titulo;
	}

	public TituloAttempt(Titulo titulo, ArrayList<Limite> limites) 
	{
		this.titulo=titulo;
		this.limites=limites;
	}

	
	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public ArrayList<Limite> getLimites() {
		return limites;
	}

	public void setLimites(ArrayList<Limite> limites) {
		this.limites = limites;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public double getTaxaDiariaSobreCDI() {
		return taxaDiariaSobreCDI;
	}

	public void setTaxaDiariaSobreCDI(double taxaDiariaSobreCDI) {
		this.taxaDiariaSobreCDI = taxaDiariaSobreCDI;
	}

}
