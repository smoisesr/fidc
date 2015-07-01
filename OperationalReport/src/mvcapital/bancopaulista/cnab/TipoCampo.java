package mvcapital.bancopaulista.cnab;

public class TipoCampo 
{
	private int idTipoCampo=0;
	private String descricao="";
	
	public TipoCampo() 
	{
		
	}

	public int getIdTipoCampo() {
		return idTipoCampo;
	}

	public void setIdTipoCampo(int idTipoCampo) {
		this.idTipoCampo = idTipoCampo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
