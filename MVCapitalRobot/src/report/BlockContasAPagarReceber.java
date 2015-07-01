package report;

import java.util.ArrayList;

import utils.DyadKeywordName;

public class BlockContasAPagarReceber extends Block
{
	public DyadKeywordName keywordName = new DyadKeywordName("contas a pagar","CONTAS_A_PAGAR_RECEBER");

	private ArrayList<RowContasAPagarReceber> rowsContasAPagarReceber = new ArrayList<RowContasAPagarReceber>();

	public BlockContasAPagarReceber()
	{

	}
	public String getBlockCSV()
	{
		String blockCSV="";
		for(RowContasAPagarReceber rowFundo:rowsContasAPagarReceber)
		{
			blockCSV=blockCSV+rowFundo.getRowCSV()+"\n";
		}
		return blockCSV;		
	}
	public ArrayList<RowContasAPagarReceber> getRowsContasAPagarReceber() {
		return rowsContasAPagarReceber;
	}
	public void setRowsFundo(ArrayList<RowContasAPagarReceber> rowsContasAPagarReceber) {
		this.rowsContasAPagarReceber = rowsContasAPagarReceber;
	}
	public DyadKeywordName getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(DyadKeywordName keywordName) {
		this.keywordName = keywordName;
	}
}
