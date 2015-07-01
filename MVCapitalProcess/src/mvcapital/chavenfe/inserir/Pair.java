package mvcapital.chavenfe.inserir;

public class Pair
{
	private String nroDocumento=""; //$NON-NLS-1$
	private String nroChaveNFE=""; //$NON-NLS-1$
	
	public Pair()
	{
		
	}

	public Pair(String nroDocumento, String nroChaveNFE)
	{
		this.nroDocumento=nroDocumento;
		this.nroChaveNFE=nroChaveNFE;
	}	
	
	public String getNroDocumento()
	{
		return this.nroDocumento;
	}

	public void setNroDocumento(String nroDocumento)
	{
		this.nroDocumento = nroDocumento;
	}

	public String getNroChaveNFE()
	{
		return this.nroChaveNFE;
	}

	public void setNroChaveNFE(String nroChaveNFE)
	{
		this.nroChaveNFE = nroChaveNFE;
	}

}
