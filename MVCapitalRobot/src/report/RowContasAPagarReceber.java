package report;

import java.text.ParseException;
import java.util.Date;

public class RowContasAPagarReceber extends Row 
{
	protected String descricao="";
	protected Date dataDePagamento=null;
	protected Double valor=0.0;
	protected Double percentualSobreContasAPagarReceber=0.0;
	protected Double percentualSobreTotal=0.0;
	
	public RowContasAPagarReceber()
	{
		try {
			this.dataDePagamento=Report.formatterDateShort.parse("01/01/91");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public RowContasAPagarReceber(String line)
	{
		line = line.replaceAll(" +", " ").replace("\t", " ");
		try {
			this.dataDePagamento=Report.formatterDateShort.parse("01/01/91");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
//		System.out.println("CPROr:\t" + line);
		
		int iFirst=0;
		int iSecond=0;
		int iThird=0;		
		
		for(int i=line.length()-1; i >0; i--)
		{
			if(Character.isWhitespace(line.charAt(i)))
			{
				iFirst=i;
				break;
			}
		}
		String stringPercentualSobreTotal=line.substring(iFirst, line.length()).trim().replace("%", "");
//		System.out.println(stringPercentualSobreTotal);
		if(stringPercentualSobreTotal.contains("("))
		{
			stringPercentualSobreTotal="-" + stringPercentualSobreTotal;
		}
		
		stringPercentualSobreTotal=stringPercentualSobreTotal.replace("(", "")
					.replace(")", "")
					.replace(".", "")
					.replace(",", ".");
//		System.out.println(line);
		this.percentualSobreTotal=Double.parseDouble(stringPercentualSobreTotal);
		
		for(int i=iFirst-1; i >0; i--)
		{
			if(Character.isWhitespace(line.charAt(i)))
			{
				iSecond=i;
				break;
			}		
		}
		String stringPercentualSobreCPR=line.substring(iSecond,iFirst).trim().replace("%", "");
//		System.out.println(stringPercentualSobreCPR);
		if(stringPercentualSobreCPR.contains("("))
		{
			stringPercentualSobreCPR="-" + stringPercentualSobreCPR;
		}
		stringPercentualSobreCPR=stringPercentualSobreCPR.replace("(", "")
					.replace(")", "")
					.replace(".", "")
					.replace(",", ".");
		
		
		this.percentualSobreContasAPagarReceber=Double.parseDouble(stringPercentualSobreCPR);		
		for(int i=iSecond-1; i >0; i--)
		{
			if(Character.isWhitespace(line.charAt(i)))
			{
				iThird=i;
				break;
			}		
		}
		String stringValor=line.substring(iThird,iSecond).trim().replace("%", "");

//		System.out.println(stringValor);
		if(stringValor.contains("("))
		{
			stringValor="-" + stringValor;
		}
		stringValor=stringValor.replace("(", "")
					.replace(")", "")
					.replace(".", "")
					.replace(",", ".");
		//System.out.println(stringValor);
		this.valor=Double.parseDouble(stringValor);
		
		String description = line.substring(0,iThird).trim();
//		System.out.println(description);
		if(description.contains("com pagamento"))
		{
			try {
				this.dataDePagamento=Report.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
			} catch (ParseException e) {
				System.err.println("\nLine: " + line);
				e.printStackTrace();
			}
			
			this.descricao = description.substring(0, description.length()-22).trim().toUpperCase();
		}
		else if(description.contains("com vencimento"))
		{
			try {
				String stringData=description.substring(description.length()-8, description.length());
				if (stringData.contains("/"))
				{
					this.dataDePagamento=Report.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
				}
			} catch (ParseException e) {
				System.err.println("\nLine: " + line);
				e.printStackTrace();
			}
			
			this.descricao = description.substring(0, description.length()-23).trim().toUpperCase();
		}
		else if(description.contains("despesas com auditoria"))
		{
			try {
				this.dataDePagamento=Report.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
			} catch (ParseException e) {
				System.err.println("\nLine: " + line);
				e.printStackTrace();
			}
			
			this.descricao = description.substring(0, description.length()-11).trim().toUpperCase();
		}
		else if(description.contains("a receber em"))		
		{
			try {
				this.dataDePagamento=Report.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
			} catch (ParseException e) {
				System.err.println("\nLine: " + line);
				e.printStackTrace();
			}			
			this.descricao = description.substring(0, description.length()-21).trim().toUpperCase();
		}
		else if(description.contains("baixas manuais"))
		{
			try {
				this.dataDePagamento=Report.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
			} catch (ParseException e) {
				System.err.println("\nLine: " + line);
				e.printStackTrace();
			}			
			this.descricao = description.substring(0, description.length()-11).trim().toUpperCase();
		}		
		else if(description.contains("a pagar em"))
		{
			int iCountChar=0;
			for(int i = description.length()-1;i>0;i--)
			{
				iCountChar=iCountChar+1;
				if(description.charAt(i)=='/')
				{
					break;
				}
			}
			
			if(iCountChar>3)
			{
				try {
					this.dataDePagamento=Report.formatterDateLong.parse(description.substring(description.length()-10, description.length()));
				} catch (ParseException e) {
					System.err.println("\nLine: " + line);
					e.printStackTrace();
				}
				this.descricao = description.substring(0, description.length()-21).trim().toUpperCase();				
			}			
			else
			{
				try {
					this.dataDePagamento=Report.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
				} catch (ParseException e) {
					System.err.println("\nLine: " + line);
					e.printStackTrace();
				}
				this.descricao = description.substring(0, description.length()-19).trim().toUpperCase();
			}
			
			
		}
		
		else if(description.contains("custo cetip") 
				|| description.contains("custo selic"))
		{
			try {
				this.dataDePagamento=Report.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
			} catch (ParseException e) {
				System.err.println("\nLine: " + line);
				e.printStackTrace();
			}
			
			this.descricao = description.substring(0, description.length()-8).trim().toUpperCase();
		}		
		else
		{
			try {
				this.dataDePagamento=Report.formatterDateShort.parse("01/01/91");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			this.descricao=description.toUpperCase();
		}
			
//		System.out.println("CPR:\t" 
//				+ this.descricao 
//				+ "\t" + Report.formatterDateShort.format(this.dataDePagamento) 
//				+ "\t" + this.valor 
//				+ "\t" + this.percentualSobreContasAPagarReceber 
//				+ "\t" + this.percentualSobreTotal);
	}
	public void showRow()
	{
		System.out.println("CPR:\t" 
				+ this.descricao 
				+ "\t" + Report.formatterDateShort.format(this.dataDePagamento) 
				+ "\t" + this.valor 
				+ "\t" + this.percentualSobreContasAPagarReceber 
				+ "\t" + this.percentualSobreTotal);		
	}
	public String getRowCSV()
	{
		String rowCSV="CPR;" 
				+ this.descricao 
				+ ";" + Report.formatterDateShort.format(this.dataDePagamento) 
				+ ";" + Report.df.format(this.valor).replace(".", ",") 
				+ ";" + Report.df.format(this.percentualSobreContasAPagarReceber).replace(".", ",") 
				+ ";" + Report.df.format(this.percentualSobreTotal).replace(".", ",");
		return rowCSV;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataDePagamento() {
		return dataDePagamento;
	}
	public void setDataDePagamento(Date dataDePagamento) {
		this.dataDePagamento = dataDePagamento;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getPercentualSobreContasAPagarReceber() {
		return percentualSobreContasAPagarReceber;
	}
	public void setPercentualSobreContasAPagarReceber(
			Double percentualSobreContasAPagarReceber) {
		this.percentualSobreContasAPagarReceber = percentualSobreContasAPagarReceber;
	}
	public Double getPercentualSobreTotal() {
		return percentualSobreTotal;
	}
	public void setPercentualSobreTotal(Double percentualSobreTotal) {
		this.percentualSobreTotal = percentualSobreTotal;
	}
}