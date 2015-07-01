package ftpclient;

import java.util.Calendar;
import java.util.Date;

public class CDIOver 
{
	private Date data=Calendar.getInstance().getTime();
	private double taxa=0; 

	public CDIOver() 
	{

	}
	
	public CDIOver(Date data, double taxa)
	{
		this.data = data;
		this.taxa = taxa;
	}
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public double getTaxa() {
		return taxa;
	}
	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}

}
