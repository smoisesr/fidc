package report.socopa;

import java.text.ParseException;
import java.util.Calendar;

import report.Report;
import report.RowRendaFixa;
import report.petra.ReportPetra;
import utils.CleanString;

public class RowRendaFixaSocopa extends RowRendaFixa 
{
	public RowRendaFixaSocopa()
	{
		
	}
	public RowRendaFixaSocopa(String line)
	{
//		System.out.println("\t" +line.toUpperCase().replace("\t", " "));
		String lineClean = line.toUpperCase().replace("\t", " ");
//		System.out.println(lineClean);
		String[] fields = lineClean.trim().replaceAll(" +", " ").split(";");
		if(!fields[0].contains("/"))
		{
			this.codigo = fields[0];
			
			if(fields[1].contains("."))
			{
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(Report.formatterDateLong.parse("01/01/1900"));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
	
				c.add(Calendar.DATE, (int)Double.parseDouble(fields[1])); // Adding 5 days
				
				this.dataDeAplicacao=c.getTime();
			}
			else if(fields[1].length()==10)
			{
				try {
					this.dataDeAplicacao=ReportPetra.formatterDateLong.parse(fields[1]);
				} catch (ParseException e) {
					System.err.println("\nLine: " + lineClean);
					e.printStackTrace();
				}
			}
			else
			{
				try {
					this.dataDeAplicacao=ReportPetra.formatterDateShort.parse(fields[1]);
				} catch (ParseException e) {
					System.err.println("\nLine: " + lineClean);
					e.printStackTrace();
				}
			}
			
			this.emitente=fields[2];
			this.papel=fields[3];
			this.mtmPercentagemAoAno=Double.parseDouble(fields[4]
					.trim()
					.replace("%", "")
					.replace(".", "")
					.replace(",", ".")
					)/100.0;
			this.taxaOver=Double.parseDouble(fields[5]
					.trim()
					.replace("%", "")
					.replace(",", "."))/100.0;
			this.taxaAoAno=Double.parseDouble(fields[6]
					.trim()
					.replace("(", "-")
					.replace(")", "")
					.replace("%", "")
					.replace(",", "."))/100.0;
	
			this.index=fields[7];
	
			if(fields[8].contains("."))
			{
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(Report.formatterDateLong.parse("01/01/1900"));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				c.add(Calendar.DATE, (int)Double.parseDouble(fields[8])); // Adding 5 days			
				this.dataDeEmissao=c.getTime();
			}
			else
			{
				try {
					this.dataDeEmissao=ReportPetra.formatterDateLong.parse(
							fields[8]);
				} catch (ParseException e) {
					System.err.println("Line: " + lineClean);
					e.printStackTrace();
				}
			}
			
			if(fields[9].contains("."))
			{
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(Report.formatterDateLong.parse("01/01/1900"));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				c.add(Calendar.DATE, (int)Double.parseDouble(fields[9])); // Adding 5 days
				
				this.dataDeVencimento=c.getTime();
			}
			else
			{
				try {
					this.dataDeVencimento=ReportPetra.formatterDateLong.parse(
							fields[9]);
				} catch (ParseException e) {
					System.err.println("Line: " + lineClean);
					e.printStackTrace();
				}
			}
			this.quantidade=Double.parseDouble(fields[10]
					.trim()
					.replace("%", "")
					.replace(".", "")
					.replace(",", "."));
			
			this.puMercado=Double.parseDouble(
					fields[11]
					.trim()
					.replace("%", "")
					.replace(".", "")
					.replace(",", "."));
	
			String stringValorDaAplicacao = CleanString.removeDoublePoint(
					fields[12]
					.trim()
					.replace("%", "")
					.replace(".", "")
					.replace(",", "."));
			
			if(stringValorDaAplicacao.contains("("))
			{
				stringValorDaAplicacao="-" + stringValorDaAplicacao
						.replace("(", "")
						.replace(")", "");
			}
			
			this.valorDaAplicacao=Double.parseDouble(stringValorDaAplicacao);		
			
			String stringValorBruto = CleanString.removeDoublePoint(
					fields[13]
					.trim().replace("%", "").replace(".", "").replace(",", "."));
			if(stringValorBruto.contains("("))
			{
				stringValorBruto="-" + stringValorBruto
						.replace("(", "")
						.replace(")", "");
			}
			this.valorBruto=Double.parseDouble(stringValorBruto);		
			
			String stringImpostos = CleanString.removeDoublePoint(
					fields[14]
					.trim().replace("%", "").replace(".", "").replace(",", "."));
			if(stringImpostos.contains("("))
			{
				stringImpostos="-" + stringImpostos
						.replace("(", "")
						.replace(")", "");
			}
			this.impostos=Double.parseDouble(stringImpostos);		
			
			String stringValorLiquido = CleanString.removeDoublePoint(
					fields[15]
					.trim().replace("%", "").replace(".", "").replace(",", "."));
			if(stringValorLiquido.contains("("))
			{
				stringValorLiquido="-" + stringValorLiquido
						.replace("(", "")
						.replace(")", "");
			}
			this.valorLiquido=Double.parseDouble(stringValorLiquido);		
	
			
			String stringPercentualSobreRendaFixa = CleanString.removeDoublePoint(
					fields[16]
					.trim().replace("%", "").replace(".", "").replace(",", "."));
			if(stringPercentualSobreRendaFixa.contains("("))
			{
				stringPercentualSobreRendaFixa="-" + stringPercentualSobreRendaFixa
						.replace("(", "")
						.replace(")", "");
			}
			this.percentualSobreRendaFixa=Double.parseDouble(stringPercentualSobreRendaFixa)/100.0;		
					
			String stringPercentualSobreTotal = CleanString.removeDoublePoint(
					fields[17]
					.trim().replace("%", "").replace(".", "").replace(",", "."));
			if(stringPercentualSobreTotal.contains("("))
			{
				stringPercentualSobreTotal="-" + stringPercentualSobreTotal
						.replace("(", "")
						.replace(")", "");
			}
			this.percentualSobreTotal=Double.parseDouble(stringPercentualSobreTotal)/100.0;				
		}
		else
		{
//			System.out.println("\t" +line.toUpperCase().replace("\t", " "));
			lineClean = line.toUpperCase().replace("\t", " ");
//			System.out.println(lineClean);
			fields = lineClean.trim().replaceAll(" +", " ").split(";");
			if(fields[4].contains("/"))
			{
				if(fields[4].contains("."))
				{
					Calendar c = Calendar.getInstance();
					try {
						c.setTime(Report.formatterDateLong.parse("01/01/1900"));
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
		
					c.add(Calendar.DATE, (int)Double.parseDouble(fields[4])); // Adding 5 days
					
					this.dataDeAplicacao=c.getTime();
				}
				else if(fields[4].length()==10)
				{
					try {
						this.dataDeAplicacao=ReportPetra.formatterDateLong.parse(fields[4]);
					} catch (ParseException e) {
						System.err.println("\nLine: " + lineClean);
						e.printStackTrace();
					}
				}
				else
				{
					try {
						this.dataDeAplicacao=ReportPetra.formatterDateShort.parse(fields[4]);
					} catch (ParseException e) {
						System.err.println("\nLine: " + lineClean);
						e.printStackTrace();
					}
				}
				this.codigo = fields[2];				
				this.emitente=fields[5];
				this.papel=fields[7];
				this.mtmPercentagemAoAno=Double.parseDouble(fields[8]
						.trim()
						.replace("%", "")
						.replace(".", "")
						.replace(",", ".")
						)/100.0;
				this.taxaOver=Double.parseDouble(fields[9]
						.trim()
						.replace("%", "")
						.replace(",", "."))/100.0;
				this.taxaAoAno=Double.parseDouble(fields[10]
						.trim()
						.replace("(", "-")
						.replace(")", "")
						.replace("%", "")
						.replace(",", "."))/100.0;
		
				this.index=fields[11];
		
				if(fields[12].contains("."))
				{
					Calendar c = Calendar.getInstance();
					try {
						c.setTime(Report.formatterDateLong.parse("01/01/1900"));
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					c.add(Calendar.DATE, (int)Double.parseDouble(fields[8])); // Adding 5 days			
					this.dataDeEmissao=c.getTime();
				}
				else if(fields[12].length()==10)
				{
					try {
						this.dataDeEmissao=Report.formatterDateLong.parse(fields[12]);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				else
				{
					try {
						this.dataDeEmissao=ReportPetra.formatterDateLong.parse(
								fields[12]);
					} catch (ParseException e) {
						System.err.println("Line: " + lineClean);
						e.printStackTrace();
					}
				}
				
				if(fields[13].contains("."))
				{
					Calendar c = Calendar.getInstance();
					try {
						c.setTime(Report.formatterDateLong.parse("01/01/1900"));
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					c.add(Calendar.DATE, (int)Double.parseDouble(fields[9])); // Adding 5 days
					
					this.dataDeVencimento=c.getTime();
				}
				else if(fields[13].length()==10)
				{
					try {
						this.dataDeVencimento=Report.formatterDateLong.parse(fields[13]);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				else
				{
					try {
						this.dataDeVencimento=ReportPetra.formatterDateLong.parse(
								fields[13]);
					} catch (ParseException e) {
						System.err.println("Line: " + lineClean);
						e.printStackTrace();
					}
				}
				this.quantidade=Double.parseDouble(fields[14]
						.trim()
						.replace("%", "")
						.replace(".", "")
						.replace(",", "."));
				
				this.puMercado=Double.parseDouble(
						fields[15]
						.trim()
						.replace("%", "")
						.replace(".", "")
						.replace(",", "."));
		
				String stringValorDaAplicacao = CleanString.removeDoublePoint(
						fields[16]
						.trim()
						.replace("%", "")
						.replace(".", "")
						.replace(",", "."));
				
				if(stringValorDaAplicacao.contains("("))
				{
					stringValorDaAplicacao="-" + stringValorDaAplicacao
							.replace("(", "")
							.replace(")", "");
				}
				
				this.valorDaAplicacao=Double.parseDouble(stringValorDaAplicacao);		
				
				String stringValorBruto = CleanString.removeDoublePoint(
						fields[17]
						.trim().replace("%", "").replace(".", "").replace(",", "."));
				if(stringValorBruto.contains("("))
				{
					stringValorBruto="-" + stringValorBruto
							.replace("(", "")
							.replace(")", "");
				}
				this.valorBruto=Double.parseDouble(stringValorBruto);		
				
				String stringImpostos = CleanString.removeDoublePoint(
						fields[18]
						.trim().replace("%", "").replace(".", "").replace(",", "."));
				if(stringImpostos.contains("("))
				{
					stringImpostos="-" + stringImpostos
							.replace("(", "")
							.replace(")", "");
				}
				this.impostos=Double.parseDouble(stringImpostos);		
				
				String stringValorLiquido = CleanString.removeDoublePoint(
						fields[19]
						.trim().replace("%", "").replace(".", "").replace(",", "."));
				if(stringValorLiquido.contains("("))
				{
					stringValorLiquido="-" + stringValorLiquido
							.replace("(", "")
							.replace(")", "");
				}
				this.valorLiquido=Double.parseDouble(stringValorLiquido);		
		
				
				String stringPercentualSobreRendaFixa = CleanString.removeDoublePoint(
						fields[20]
						.trim().replace("%", "").replace(".", "").replace(",", "."));
				if(stringPercentualSobreRendaFixa.contains("("))
				{
					stringPercentualSobreRendaFixa="-" + stringPercentualSobreRendaFixa
							.replace("(", "")
							.replace(")", "");
				}
				this.percentualSobreRendaFixa=Double.parseDouble(stringPercentualSobreRendaFixa)/100.0;		
						
				String stringPercentualSobreTotal = CleanString.removeDoublePoint(
						fields[21]
						.trim().replace("%", "").replace(".", "").replace(",", "."));
				if(stringPercentualSobreTotal.contains("("))
				{
					stringPercentualSobreTotal="-" + stringPercentualSobreTotal
							.replace("(", "")
							.replace(")", "");
				}
				this.percentualSobreTotal=Double.parseDouble(stringPercentualSobreTotal)/100.0;
			}
		}
	}
}
