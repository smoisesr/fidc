package report;

import java.util.ArrayList;
import utils.DyadKeywordName;

public class BlockRendaFixa extends Block
{
	public DyadKeywordName keywordName = new DyadKeywordName("renda fixa","RENDA_FIXA");

	private ArrayList<RowRendaFixa> rowsRendaFixa = new ArrayList<RowRendaFixa>();

	public BlockRendaFixa()
	{

	}
	public String getBlockCSV()
	{
		String blockCSV="";
		for(RowRendaFixa rowRendaFixa:rowsRendaFixa)
		{
			blockCSV=blockCSV+rowRendaFixa.getRowCSV()+"\n";
		}
		return blockCSV;		
	}
	public ArrayList<RowRendaFixa> getRowsContasAPagarReceber() {
		return rowsRendaFixa;
	}
	public void setRowsFundo(ArrayList<RowRendaFixa> rowsRendaFixa) {
		this.rowsRendaFixa = rowsRendaFixa;
	}
	public DyadKeywordName getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(DyadKeywordName keywordName) {
		this.keywordName = keywordName;
	}
}
