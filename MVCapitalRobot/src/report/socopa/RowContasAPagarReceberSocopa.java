package report.socopa;

import java.text.ParseException;

import report.Report;
import report.RowContasAPagarReceber;
import report.petra.ReportPetra;

public class RowContasAPagarReceberSocopa extends RowContasAPagarReceber 
{
	public RowContasAPagarReceberSocopa()
	{
		try {
			this.dataDePagamento=ReportPetra.formatterDateShort.parse("01/01/91");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public RowContasAPagarReceberSocopa(String line)
	{
		line = line.replaceAll(" +", " ").replace("\t", " ").toUpperCase();
		String[] fields = line.split(";");
		//line = line.substring(11);
		int iValor=fields.length-3;
		int iPercentualSobreCPR=fields.length-2;
		int iPercentualSobreTotal=fields.length-1;
		
		String description = "";
//		System.out.println(line);
		
		for(int i=0;i<fields.length-4;i++)
		{
			description=description+fields[i]+" ";
		}
		this.descricao=description;
		String stringValor=fields[iValor]
				.trim()
				.replace(" ", "")
				.replace("(", "-")
				.replace(")", "")
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".");
				;
		this.valor=Double.parseDouble(stringValor);

		String stringPercentualSobreCPR=
				fields[iPercentualSobreCPR]
					.trim()
					.replace(" ", "")
					.replace("%", "")
				    .replace("(", "-")
					.replace(")", "")
					.replace(".", "")
					.replace(",", ".");
				;
		this.percentualSobreContasAPagarReceber=Double.parseDouble(stringPercentualSobreCPR);		

		String stringPercentualSobreTotal=
				fields[iPercentualSobreTotal]
						.trim()
						.replace(" ", "")
						.replace("%", "")
					    .replace("(", "-")
						.replace(")", "")
						.replace(".", "")
						.replace(",", ".");
		this.percentualSobreTotal=Double.parseDouble(stringPercentualSobreTotal);
		
		try {
			this.dataDePagamento=Report.formatterDateLong.parse("01/01/1991");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	
		
//		System.out.println(description);
//		if(description.contains("com pagamento"))
//		{
//			try {
//				this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
//			} catch (ParseException e) {
//				System.err.println("\nLine: " + line);
//				e.printStackTrace();
//			}
//			
//			this.descricao = description.substring(0, description.length()-22).trim().toUpperCase();
//		}
//		else if(description.contains("com vencimento"))
//		{
//			try {
//				String stringData=description.substring(description.length()-8, description.length());
//				if (stringData.contains("/"))
//				{
//					this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
//				}
//			} catch (ParseException e) {
//				System.err.println("\nLine: " + line);
//				e.printStackTrace();
//			}
//			
//			this.descricao = description.substring(0, description.length()-23).trim().toUpperCase();
//		}
//		else if(description.contains("despesas com auditoria"))
//		{
//			try {
//				this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
//			} catch (ParseException e) {
//				System.err.println("\nLine: " + line);
//				e.printStackTrace();
//			}
//			
//			this.descricao = description.substring(0, description.length()-11).trim().toUpperCase();
//		}
//		else if(description.contains("a receber em"))		
//		{
//			try {
//				this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
//			} catch (ParseException e) {
//				System.err.println("\nLine: " + line);
//				e.printStackTrace();
//			}			
//			this.descricao = description.substring(0, description.length()-21).trim().toUpperCase();
//		}
//		else if(description.contains("baixas manuais"))
//		{
//			try {
//				this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
//			} catch (ParseException e) {
//				System.err.println("\nLine: " + line);
//				e.printStackTrace();
//			}			
//			this.descricao = description.substring(0, description.length()-11).trim().toUpperCase();
//		}		
//		else if(description.contains("a pagar em"))
//		{
//			int iCountChar=0;
//			for(int i = description.length()-1;i>0;i--)
//			{
//				iCountChar=iCountChar+1;
//				if(description.charAt(i)=='/')
//				{
//					break;
//				}
//			}
//			
//			if(iCountChar>3)
//			{
//				try {
//					this.dataDePagamento=ReportPetra.formatterDateLong.parse(description.substring(description.length()-10, description.length()));
//				} catch (ParseException e) {
//					System.err.println("\nLine: " + line);
//					e.printStackTrace();
//				}
//				this.descricao = description.substring(0, description.length()-21).trim().toUpperCase();				
//			}			
//			else
//			{
//				try {
//					this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
//				} catch (ParseException e) {
//					System.err.println("\nLine: " + line);
//					e.printStackTrace();
//				}
//				this.descricao = description.substring(0, description.length()-19).trim().toUpperCase();
//			}
//			
//			
//		}
//		
//		else if(description.contains("custo cetip") 
//				|| description.contains("custo selic"))
//		{
//			try {
//				this.dataDePagamento=ReportPetra.formatterDateShort.parse(description.substring(description.length()-8, description.length()));
//			} catch (ParseException e) {
//				System.err.println("\nLine: " + line);
//				e.printStackTrace();
//			}
//			
//			this.descricao = description.substring(0, description.length()-8).trim().toUpperCase();
//		}		
//		else
//		{
//			try {
//				this.dataDePagamento=ReportPetra.formatterDateShort.parse("01/01/91");
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			this.descricao=description.toUpperCase();
//		}
			
//		System.out.println("CPR:\t" 
//				+ this.descricao 
//				+ "\t" + Report.formatterDateShort.format(this.dataDePagamento) 
//				+ "\t" + this.valor 
//				+ "\t" + this.percentualSobreContasAPagarReceber 
//				+ "\t" + this.percentualSobreTotal);
	}
}