package operation;

import java.util.ArrayList;

import limites.Limite;
import relatorio.cessao.DireitoCreditorio;

public class DireitoCreditorioAttempt 
{
	private boolean aprovado=false;
	private DireitoCreditorio direitoCreditorio = new DireitoCreditorio();
	private ArrayList<Limite> limites = new ArrayList<Limite>();
	private double taxaDiariaSobreCDI = 0.0;
	
	public DireitoCreditorioAttempt() 
	{
		
	}

	public DireitoCreditorioAttempt(DireitoCreditorio direitoCreditorio) 
	{
		this.direitoCreditorio=direitoCreditorio;
	}

	public DireitoCreditorioAttempt(DireitoCreditorio direitoCreditorio, ArrayList<Limite> limites) 
	{
		this.direitoCreditorio=direitoCreditorio;
		this.limites=limites;
	}

	
	public DireitoCreditorio getDireitoCreditorio() {
		return direitoCreditorio;
	}

	public void setDireitoCreditorio(DireitoCreditorio direitoCreditorio) {
		this.direitoCreditorio = direitoCreditorio;
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
