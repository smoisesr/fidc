package report.socopa;

import report.RowFundo;

public class RowFundoSocopa extends RowFundo 
{
	public RowFundoSocopa()
	{
		
	}
	
	public RowFundoSocopa(String line)
	{
		line = line.trim();
//		System.out.println(line);
		String[] fields = line.split(";");
		this.codigo=fields[0];
		this.fundo=fields[1];	
		this.instituicao = fields[2];
		this.quantidadeDeCotas = Double.parseDouble(
				fields[3]
						.replace(" ", "")
						.replace(".", "")
						.replace(",","."));			
		this.quantidadeBloqueada = Double.parseDouble(
				fields[4]
						.replace(" ", "")
						.replace(".", "")
						.replace(",","."));
		this.valorDaCota = Double.parseDouble(
				fields[5]
						.replace(" ", "")
						.replace(".", "")
						.replace(",","."));
		this.aplicacaoOuResgate = Double.parseDouble(
				fields[6]
						.replace(" ", "")
						.replace(".", "")
						.replace(",","."));
		this.valorAtual = Double.parseDouble(
				fields[7]				
						.replace(" ", "")
						.replace(".", "")
						.replace(",","."));
		this.impostos = Double.parseDouble(
				fields[8]
						.replace(" ", "")
						.replace(".", "")
						.replace(",","."));
		this.valorLiquido = Double.parseDouble(
				fields[9]
						.replace(" ", "")
						.replace(".", "")
						.replace(",","."));
		this.percentualSobreFundos = Double.parseDouble(
				fields[10]
						.replace(" ", "")
						.replace(".", "")
						.replace(",",".")
						.replace("%", ""))/100.0;
		this.percentualSobreTotal = Double.parseDouble(
				fields[11]
						.replace(" ", "")
						.replace(".", "")
						.replace(",",".")
						.replace("%", ""))/100.0;
	}			
}
