package report;

import utils.DyadKeywordName;

public class BlockTesouraria extends Block
{
	public DyadKeywordName keywordName = new DyadKeywordName("tesouraria","TESOURARIA");
	
	public Double saldoEmTesouraria=0.0;
	public Double saldoEmTesourariaReserva=0.0;
	public Double total=0.0;

	public BlockTesouraria()
	{

	}
	public String getBlockCSV()
	{
		String blockCSV="";
		blockCSV=blockCSV
				+"TES;SaldoEmTesouraria;" + Report.df.format(this.saldoEmTesouraria).replace(".", ",")+"\n"
				+"TES;SaldoEmTesourariaReserva;" + Report.df.format(this.saldoEmTesourariaReserva).replace(".", ",")+"\n"
				+"TES;Total;" + Report.df.format(this.total).replace(".", ",")+"\n";
		
//		System.out.print("TES;SaldoEmTesouraria;" + Report.df.format(this.saldoEmTesouraria).replace(".", ",")+"\n"
//				+"TES;SaldoEmTesourariaReserva;" + Report.df.format(this.saldoEmTesourariaReserva).replace(".", ",")+"\n"
//				+"TES;Total;" + Report.df.format(this.total).replace(".", ",")+"\n");
		return blockCSV;
	}
	public DyadKeywordName getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(DyadKeywordName keywordName) {
		this.keywordName = keywordName;
	}

	public Double getSaldoEmTesouraria() {
		return saldoEmTesouraria;
	}

	public void setSaldoEmTesouraria(Double saldoEmTesouraria) {
		this.saldoEmTesouraria = saldoEmTesouraria;
	}

	public Double getSaldoEmTesourariaReserva() {
		return saldoEmTesourariaReserva;
	}

	public void setSaldoEmTesourariaReserva(Double saldoEmTesourariaReserva) {
		this.saldoEmTesourariaReserva = saldoEmTesourariaReserva;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
