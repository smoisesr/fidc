package report.socopa;

import report.RowRentabilidadeAcumulada;

public class RowRentabilidadeAcumuladaSocopa extends RowRentabilidadeAcumulada 
{
	public RowRentabilidadeAcumuladaSocopa()
	{
		
	}
	public RowRentabilidadeAcumuladaSocopa(String[] fields)
	{
		this.setFieldsToMembers(fields);
	}
	public RowRentabilidadeAcumuladaSocopa(String line)
	{
		//System.out.println(line);
		String[] fields = line.split(";");
		this.setFieldsToMembers(fields);
	}
	public void setFieldsToMembers(String[] fields)
	{
		
		this.indexador = fields[0].toUpperCase();
		this.benchmark=Double.parseDouble(fields[1]
				.trim()
				.replace(" ", "")
				.replace("(", "-")
				.replace(")", "")
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".")
				)/100.0;
		this.rentabilidadeReal=Double.parseDouble(fields[2]
				.trim()
				.replace(" ", "")
				.replace("(", "-")
				.replace(")", "")
				.replace("%", "")
				.replace(",", "."))/100.0;
		this.variacaoDiaria=Double.parseDouble(fields[3]
				.trim()
				.replace(" ", "")
				.replace("(", "-")
				.replace(")", "")
				.replace("%", "")
				.replace(",", "."))/100.0;
		this.variacaoMensal=Double.parseDouble(fields[4]
				.trim()
				.replace(" ", "")
				.replace("(", "-")
				.replace(")", "")				
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".")
				);
		this.variacaoAnual=Double.parseDouble(fields[5]
				.trim()
				.replace(" ", "")
				.replace("(", "-")
				.replace(")", "")				
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".")
				);
		this.ultimosSeisMeses=Double.parseDouble(fields[6]
				.trim()
				.replace(" ", "")
				.replace("(", "-")
				.replace(")", "")				
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".")
				);
		this.ultimosDozeMeses=Double.parseDouble(fields[7]
				.trim()
				.replace(" ", "")
				.replace("(", "-")
				.replace(")", "")				
				.replace("%", "")
				.replace(",", "."))/100.0;		
	}
}
