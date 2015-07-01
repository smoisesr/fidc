package report.petra;

import java.text.ParseException;

import report.RowContasAPagarReceber;

public class RowContasAPagarReceberPetra extends RowContasAPagarReceber 
{
	public RowContasAPagarReceberPetra()
	{
		try {
			this.dataDePagamento=ReportPetra.formatterDateShort.parse("01/01/91");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public RowContasAPagarReceberPetra(String line)
	{
		line = line.replaceAll(" +", " ").replace("\t", " ");
		try {
			this.dataDePagamento=ReportPetra.formatterDateShort.parse("01/01/91");
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
				this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
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
					this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
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
				this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
			} catch (ParseException e) {
				System.err.println("\nLine: " + line);
				e.printStackTrace();
			}
			
			this.descricao = description.substring(0, description.length()-11).trim().toUpperCase();
		}
		else if(description.contains("a receber em"))		
		{
			try {
				this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
			} catch (ParseException e) {
				System.err.println("\nLine: " + line);
				e.printStackTrace();
			}			
			this.descricao = description.substring(0, description.length()-21).trim().toUpperCase();
		}
		else if(description.contains("baixas manuais"))
		{
			try {
				this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
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
					this.dataDePagamento=ReportPetra.formatterDateLong.parse(description.substring(description.length()-10, description.length()));
				} catch (ParseException e) {
					System.err.println("\nLine: " + line);
					e.printStackTrace();
				}
				this.descricao = description.substring(0, description.length()-21).trim().toUpperCase();				
			}			
			else
			{
				try {
					this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
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
				this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
			} catch (ParseException e) {
				System.err.println("\nLine: " + line);
				e.printStackTrace();
			}
			
			this.descricao = description.substring(0, description.length()-8).trim().toUpperCase();
		}		
		else
		{
			try {
				this.dataDePagamento=ReportPetra.formatterDateShort.parse("01/01/91");
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
}