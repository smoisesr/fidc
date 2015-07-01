package report;

import java.util.ArrayList;

import utils.DyadKeywordName;

public class BlockOperacoesCompromissadas extends Block
{
	public DyadKeywordName keywordName = new DyadKeywordName("renda fixa","RENDA_FIXA");

	private ArrayList<RowOperacoesCompromissadas> rowsOperacoesCompromissadas = new ArrayList<RowOperacoesCompromissadas>();

	public BlockOperacoesCompromissadas()
	{

	}
	public String getBlockCSV()
	{
		String blockCSV="";
		for(RowOperacoesCompromissadas rowOperacoesCompromissadas:rowsOperacoesCompromissadas)
		{
			blockCSV=blockCSV+rowOperacoesCompromissadas.getRowCSV()+"\n";
		}
		return blockCSV;		
	}
	public ArrayList<RowOperacoesCompromissadas> getRowsContasAPagarReceber() {
		return rowsOperacoesCompromissadas;
	}
	public void setRowsFundo(ArrayList<RowOperacoesCompromissadas> rowsOperacoesCompromissadas) {
		this.rowsOperacoesCompromissadas = rowsOperacoesCompromissadas;
	}
	public DyadKeywordName getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(DyadKeywordName keywordName) {
		this.keywordName = keywordName;
	}
}
