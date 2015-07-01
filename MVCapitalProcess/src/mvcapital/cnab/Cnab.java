package mvcapital.cnab;

import java.util.ArrayList;

public class Cnab<H,D,T>
{
	protected H header;
	protected ArrayList<D> details;
	protected T trailler;
	
	public Cnab()
	{

	}

	public Cnab(H header, ArrayList<D> details, T trailler)
	{
		this.header=header;
		this.details=details;
		this.trailler=trailler;
	}

	public H getHeader()
	{
		return this.header;
	}

	public void setHeader(H header)
	{
		this.header = header;
	}

	public ArrayList<D> getDetails()
	{
		return this.details;
	}

	public void setDetails(ArrayList<D> details)
	{
		this.details = details;
	}

	public T getTrailler()
	{
		return this.trailler;
	}

	public void setTrailler(T trailler)
	{
		this.trailler = trailler;
	}
	
}
