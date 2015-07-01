package report;

import utils.DyadKeywordName;

public class BlockPatrimonio extends Block
{
	public DyadKeywordName keywordName = new DyadKeywordName("tesouraria","TESOURARIA");
	
	public Double valor=0.0;

	public BlockPatrimonio()
	{

	}
	
	public String getBlockCSV()
	{
		String blockCSV="";
		blockCSV=blockCSV
				+"PAT;Valor;" + Report.df.format(this.valor).replace(".", ",")+"\n";
		return blockCSV;
	}
	
	public DyadKeywordName getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(DyadKeywordName keywordName) {
		this.keywordName = keywordName;
	}


	public Double getValor() {
		return valor;
	}

	public void setValor(Double total) {
		this.valor = total;
	}
}
