package report;

import java.util.ArrayList;

import utils.DyadKeywordName;

public class BlockRentabilidadeAcumulada extends Block
{
	public DyadKeywordName keywordName = new DyadKeywordName("rentabilidade acumulada","RENTABILIDADE_ACUMULADA");

	private ArrayList<RowRentabilidadeAcumulada> rowsRentabilidadeAcumulada = new ArrayList<RowRentabilidadeAcumulada>();

	public BlockRentabilidadeAcumulada()
	{

	}
	public String getBlockCSV()
	{
		String blockCSV="";
		for(RowRentabilidadeAcumulada rowFundo:rowsRentabilidadeAcumulada)
		{
			blockCSV=blockCSV+rowFundo.getRowCSV()+"\n";
		}
		return blockCSV;		
	}
	public DyadKeywordName getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(DyadKeywordName keywordName) {
		this.keywordName = keywordName;
	}
	public ArrayList<RowRentabilidadeAcumulada> getRowsRentabilidadeAcumulada() {
		return rowsRentabilidadeAcumulada;
	}
	public void setRowsRentabilidadeAcumulada(
			ArrayList<RowRentabilidadeAcumulada> rowsRentabilidadeAcumulada) {
		this.rowsRentabilidadeAcumulada = rowsRentabilidadeAcumulada;
	}
}
