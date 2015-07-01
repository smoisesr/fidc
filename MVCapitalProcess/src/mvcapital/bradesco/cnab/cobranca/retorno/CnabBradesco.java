package mvcapital.bradesco.cnab.cobranca.retorno;

import java.util.ArrayList;

import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;

import com.mysql.jdbc.Connection;

public class CnabBradesco
{
	private Header header = new Header();
	private ArrayList<Detail> details = new ArrayList<Detail>();
	private Trailler trailler = new Trailler();
	
	public CnabBradesco()
	{
		this.header=new Header();
		this.trailler = new Trailler();
		this.details = new ArrayList<Detail>();		
	}
	
	public CnabBradesco(FundoDeInvestimento fundo, Entidade originador, ArrayList<Detail> details, Connection conn)
	{
		this.details = details;
		int sequencialUltimoRegistro = this.details.size()+2;
		this.trailler.getNumeroSequencialRegistro().setConteudo(Integer.toString(sequencialUltimoRegistro));
	}
	
	public CnabBradesco(ArrayList<String> lines)
	{
		if(lines.size()>0)
		{
			for(int i=0;i<lines.size();i++)
			{
				if(i==0)
				{
					this.header = new Header(lines.get(i));
				}
				else if(i<lines.size()-1)
				{
					this.details.add(new Detail(lines.get(i)));
				}
				else
				{
					this.trailler=new Trailler(lines.get(i));
				}
			}
		}
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder(); 
		sb.append(this.getHeader().toString());
		for(Detail detail:this.getDetails())
		{
			sb.append("\n" + detail.toString()); //$NON-NLS-1$
		}
		sb.append("\n" + this.getTrailler().toString()); //$NON-NLS-1$
		return sb.toString();
	}	

	public String toCSV()
	{
		StringBuilder sb = new StringBuilder(); 
		sb.append(Detail.csvColumns());
		for(Detail detail:this.getDetails())
		{
			sb.append("\n" + detail.toCSV()); //$NON-NLS-1$
		}
		return sb.toString();
	}	
	
	public Header getHeader()
	{
		return this.header;
	}
	
	public void setHeader(Header header)
	{
		this.header = header;
	}
	
	public Trailler getTrailler()
	{
		return this.trailler;
	}
	
	public void setTrailler(Trailler trailler)
	{
		this.trailler = trailler;
	}
	
	public ArrayList<Detail> getDetails()
	{
		return this.details;
	}
	
	public void setDetails(ArrayList<Detail> details)
	{
		this.details = details;
	}
}
