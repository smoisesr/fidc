package mvcapital.indicadores;

public class TipoIndicador
{
	int idTipoIndicador=0;
	String descricao="";
	
	public TipoIndicador()
	{

	}
	public int getIdTipoIndicador()
	{
		return idTipoIndicador;
	}
	public void setIdTipoIndicador(int idTipoIndicador)
	{
		this.idTipoIndicador = idTipoIndicador;
	}
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

}
