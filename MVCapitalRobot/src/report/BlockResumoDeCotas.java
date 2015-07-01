package report;

import utils.DyadKeywordName;

public class BlockResumoDeCotas extends Block
{
	public DyadKeywordName keywordName = new DyadKeywordName("quantidade de cotas","RESUMO_COTAS");
//	Quantidade de Cotas
//	Valor da cota unitária
//	Quantidade de Cotas (Bruta)
//	Valor da cota unitária (Bruta)
//	Valor da cota bruta de performance

	public Double quantidadeDeCotas=0.0;
	public Double valorDaCotaUnitaria=0.0;
	public Double quantidadeDeCotasBruta=0.0;
	public Double valorDaCotaUnitariaBruta=0.0;
	public Double valorDaCotaBrutaDePerformance=0.0;

	public BlockResumoDeCotas()
	{

	}
	
	public String getBlockCSV()
	{
		String blockCSV= "COT;QuantidadeDeCotas;" + this.quantidadeDeCotas+"\n"
						+"COT;ValorDaCotaUnitaria;" + this.valorDaCotaUnitaria+"\n"
						+"COT;QuantidadeDeCotasBruta;" + this.quantidadeDeCotasBruta+"\n"
						+"COT;ValorDaCotaUnitariaBruta;" + this.valorDaCotaUnitariaBruta+"\n"
						+"COT;ValorDaCotaBrutaDePerformance;" + this.valorDaCotaBrutaDePerformance+"\n"
						;
		
		return blockCSV;
	}
	
	public DyadKeywordName getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(DyadKeywordName keywordName) {
		this.keywordName = keywordName;
	}

	public Double getQuantidadeDeCotas() {
		return quantidadeDeCotas;
	}

	public void setQuantidadeDeCotas(Double quantidadeDeCotas) {
		this.quantidadeDeCotas = quantidadeDeCotas;
	}

	public Double getValorDaCotaUnitaria() {
		return valorDaCotaUnitaria;
	}

	public void setValorDaCotaUnitaria(Double valorDaCotaUnitaria) {
		this.valorDaCotaUnitaria = valorDaCotaUnitaria;
	}

	public Double getQuantidadeDeCotasBruta() {
		return quantidadeDeCotasBruta;
	}

	public void setQuantidadeDeCotasBruta(Double quantidadeDeCotasBruta) {
		this.quantidadeDeCotasBruta = quantidadeDeCotasBruta;
	}

	public Double getValorDaCotaUnitariaBruta() {
		return valorDaCotaUnitariaBruta;
	}

	public void setValorDaCotaUnitariaBruta(Double valorDaCotaUnitariaBruta) {
		this.valorDaCotaUnitariaBruta = valorDaCotaUnitariaBruta;
	}

	public Double getValorDaCotaBrutaDePerformance() {
		return valorDaCotaBrutaDePerformance;
	}

	public void setValorDaCotaBrutaDePerformance(
			Double valorDaCotaBrutaDePerformance) {
		this.valorDaCotaBrutaDePerformance = valorDaCotaBrutaDePerformance;
	}
}
