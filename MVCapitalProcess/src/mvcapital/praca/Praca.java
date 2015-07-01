package mvcapital.praca;

import com.mysql.jdbc.Connection;

import mvcapital.entidade.Entidade;

public class Praca
{
//	idPraca, 
//	idEntidadeBanco, 
//	Agencia, 
//	idCEP, 
//	DataAtualizacao

	private int idPraca=0;
	private Entidade entidadeBanco=new Entidade();
	private String agencia=""; //$NON-NLS-1$
	private int idCEP=0;
	
	
	public Praca()
	{

	}

	public Praca(Entidade entidadeBanco, String agencia, Connection conn)
	{
		this.entidadeBanco = entidadeBanco;
		this.agencia = agencia;
	}
	
	

	public int getIdPraca()
	{
		return this.idPraca;
	}


	public void setIdPraca(int idPraca)
	{
		this.idPraca = idPraca;
	}


	public Entidade getEntidadeBanco()
	{
		return this.entidadeBanco;
	}


	public void setEntidadeBanco(Entidade entidadeBanco)
	{
		this.entidadeBanco = entidadeBanco;
	}


	public String getAgencia()
	{
		return this.agencia;
	}


	public void setAgencia(String agencia)
	{
		this.agencia = agencia;
	}


	public int getIdCEP()
	{
		return this.idCEP;
	}


	public void setIdCEP(int idCEP)
	{
		this.idCEP = idCEP;
	}

}
