package report;

import java.util.ArrayList;

import utils.DyadKeywordName;

public class BlockFundo extends Block
{
	public DyadKeywordName keywordName = new DyadKeywordName("fundos de investimento","FUNDOS_DE_INVESTIMENTO");

	private ArrayList<RowFundo> rowsFundo = new ArrayList<RowFundo>();

	public BlockFundo()
	{

	}
	public String getBlockCSV()
	{
		String blockCSV="";
		for(RowFundo rowFundo:rowsFundo)
		{
			blockCSV=blockCSV+rowFundo.getRowCSV()+"\n";
		}
		return blockCSV;		
	}
	public ArrayList<RowFundo> getRowsFundo() {
		return rowsFundo;
	}
	public void setRowsFundo(ArrayList<RowFundo> rowsFundo) {
		this.rowsFundo = rowsFundo;
	}
	public DyadKeywordName getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(DyadKeywordName keywordName) {
		this.keywordName = keywordName;
	}
}
