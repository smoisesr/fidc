package report.petra;

import java.text.ParseException;

import report.RowOperacoesCompromissadas;

public class RowOperacoesCompromissadasPetra extends RowOperacoesCompromissadas 
{
	
	public RowOperacoesCompromissadasPetra()
	{
		
	}
	public RowOperacoesCompromissadasPetra(String[] fields)
	{
		this.setFieldsToMembers(fields);
	}
	public RowOperacoesCompromissadasPetra(String line)
	{
		String[] fields = line.split(" ");
//		System.out.println(line);
		this.setFieldsToMembers(fields);
	}
	public void setFieldsToMembers(String[] fields)
	{
		
		this.codigo = fields[0];
		try {
			this.dataDeAquisicao=ReportPetra.formatterDateShort.parse(fields[1]);
		} catch (ParseException e) {
			e.printStackTrace();
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
				.replace("%", "")
				.replace(",", "."))/100.0;

		this.index=fields[7];
		try {
			this.dataDeEmissao=ReportPetra.formatterDateShort.parse(fields[8].trim());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			this.dataDeVencimento=ReportPetra.formatterDateShort.parse(fields[9].trim());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			this.dataDeResgate=ReportPetra.formatterDateShort.parse(fields[10].trim());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.quantidade=Double.parseDouble(fields[11]
				.trim()
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".")
				);
		this.puMercado=Double.parseDouble(fields[12]
				.trim()
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".")
				);
		this.valorDaAplicacao=Double.parseDouble(fields[13]
				.trim()
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".")
				);
		this.valorDoResgate=Double.parseDouble(fields[14]
				.trim()
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".")
				);
		this.valorBruto=Double.parseDouble(fields[15]
				.trim()
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".")
				);
		this.impostos=Double.parseDouble(fields[16]
				.trim()
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".")
				);
		this.valorLiquido=Double.parseDouble(fields[17]
				.trim()
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".")
				);
		String stringPercentualSobreRendaFixa=fields[18];
		if(stringPercentualSobreRendaFixa.contains("("))
		{
			stringPercentualSobreRendaFixa="-" + stringPercentualSobreRendaFixa
												.replace("(", "")
												.replace(")", "")
												.replace("%", "");
		}
		this.percentualSobreRendaFixa=Double.parseDouble(stringPercentualSobreRendaFixa
				.trim()
				.replace("%", "")
				.replace(".", "")
				.replace(",", "."))/100.0;
		
		String stringPercentualSobreTotal=fields[19];
		if(stringPercentualSobreRendaFixa.contains("("))
		{
			stringPercentualSobreTotal="-" + stringPercentualSobreTotal
												.replace("(", "")
												.replace(")", "")
												.replace("%", "")
												.replace(".", "")
												.replace(",", ".")
												;
		}		
		this.percentualSobreTotal=Double.parseDouble(stringPercentualSobreTotal
				.trim()
				.replace("%", "")
				.replace(",", "."))/100.0;		
	}
}
