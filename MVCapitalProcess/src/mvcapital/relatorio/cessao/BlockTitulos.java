package mvcapital.relatorio.cessao;

import java.util.ArrayList;

public class BlockTitulos extends Block 
{
	private ArrayList<Titulo> titulos = new ArrayList<Titulo>();	
	private ArrayList<TipoTitulo> tiposTitulo = new ArrayList<TipoTitulo>();
	
	public BlockTitulos() 
	{

	}
	
	public BlockTitulos(ArrayList<Titulo> dcs) 
	{
		this.titulos=dcs;
		
		for(Titulo titulo:this.titulos)
		{
			boolean exist=false;
			for(TipoTitulo tipoTitulo:this.tiposTitulo)
			{
				if(tipoTitulo.getDescricao().equals(titulo.getTipoTitulo().getDescricao()))
				{
					exist=true;
					break;
				}				
			}
			if(!exist)
			{
				this.tiposTitulo.add(titulo.getTipoTitulo());
			}
		}
		this.show();
	}
	
	public void show()
	{
		System.out.println("TITULOS");
		for (Titulo titulo:titulos)
		{
			titulo.show();
		}
		System.out.println("Tipos de TITULOS");
		for(TipoTitulo tr:this.tiposTitulo)
		{
			System.out.println(tr.getDescricao());
		}
	}
	public ArrayList<Titulo> getTitulos() {
		return titulos;
	}
	public void setTitulos(ArrayList<Titulo> titulos) {
		this.titulos = titulos;
	}

	public ArrayList<TipoTitulo> getTiposTitulo() {
		return tiposTitulo;
	}

	public void setTiposTitulo(ArrayList<TipoTitulo> tiposTitulo) {
		this.tiposTitulo = tiposTitulo;
	}

}
