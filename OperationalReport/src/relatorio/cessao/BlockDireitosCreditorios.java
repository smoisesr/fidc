package relatorio.cessao;

import java.util.ArrayList;

public class BlockDireitosCreditorios extends Block 
{
	private ArrayList<DireitoCreditorio> direitosCreditorios = new ArrayList<DireitoCreditorio>();	
	private ArrayList<TipoDeRecebivel> tiposDeRecebivel = new ArrayList<TipoDeRecebivel>();
	
	public BlockDireitosCreditorios() 
	{

	}
	
	public BlockDireitosCreditorios(ArrayList<DireitoCreditorio> dcs) 
	{
		this.direitosCreditorios=dcs;
		
		for(DireitoCreditorio dc:this.direitosCreditorios)
		{
			boolean exist=false;
			for(TipoDeRecebivel tipoDeRecebivel:this.tiposDeRecebivel)
			{
				if(tipoDeRecebivel.getDescricao().equals(dc.getTipoDeRecebivel().getDescricao()))
				{
					exist=true;
					break;
				}				
			}
			if(!exist)
			{
				this.tiposDeRecebivel.add(dc.getTipoDeRecebivel());
			}
		}
		this.show();
	}
	
	public void show()
	{
		System.out.println("TITULOS");
		for (DireitoCreditorio titulo:direitosCreditorios)
		{
			titulo.show();
		}
		System.out.println("Tipos de TITULOS");
		for(TipoDeRecebivel tr:this.tiposDeRecebivel)
		{
			System.out.println(tr.getDescricao());
		}
	}
	public ArrayList<DireitoCreditorio> getDireitosCreditorios() {
		return direitosCreditorios;
	}
	public void setDireitosCreditorios(ArrayList<DireitoCreditorio> titulos) {
		this.direitosCreditorios = titulos;
	}

	public ArrayList<TipoDeRecebivel> getTiposDeRecebivel() {
		return tiposDeRecebivel;
	}

	public void setTiposDeRecebivel(ArrayList<TipoDeRecebivel> tiposDeRecebivel) {
		this.tiposDeRecebivel = tiposDeRecebivel;
	}

}
