package report.petra;

import report.RowFundo;

public class RowFundoPetra extends RowFundo 
{
	public RowFundoPetra()
	{
		
	}
	
	public RowFundoPetra(String line)
	{
		line = line.trim();
//		System.out.println(line);
		String restLine=line.substring(53, line.length())
				.trim()
				.replace("%", "% ")
				.replace(".", "")
				.replaceAll(" +", " ");

		this.codigo=line.substring(0, 9).trim().toUpperCase();
		this.fundo=line.substring(9, 52).trim().toUpperCase();
		String[] fields = restLine.split(" ");		
		this.instituicao = fields[0].toUpperCase();
		this.quantidadeDeCotas = Double.parseDouble(fields[1].replace(",","."));			
		this.quantidadeBloqueada = Double.parseDouble(fields[2].replace(",","."));
		this.valorDaCota = Double.parseDouble(fields[3].replace(",","."));
		this.aplicacaoOuResgate = Double.parseDouble(fields[4].replace(",","."));
		this.valorAtual = Double.parseDouble(fields[5].replace(",","."));
		this.impostos = Double.parseDouble(fields[6].replace(",","."));
		this.valorLiquido = Double.parseDouble(fields[7].replace(",","."));
		//System.out.println(fields[8]);
		if(fields.length==8)
		{
			this.percentualSobreFundos = 0.0;
			this.percentualSobreTotal = Double.parseDouble(fields[8].replace(",",".").replace("%", ""))/100.0;			
		}
		else
		{
			this.percentualSobreFundos = Double.parseDouble(fields[8].replace(",",".").replace("%", ""))/100.0;
			this.percentualSobreTotal = Double.parseDouble(fields[9].replace(",",".").replace("%", ""))/100.0;
		}
	}			
}
