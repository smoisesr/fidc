package report;

public class RowRentabilidadeAcumulada extends Row 
{
	protected String indexador="";	
	protected Double benchmark=0.0;
	protected Double rentabilidadeReal=0.0;
	protected Double variacaoDiaria=0.0;
	protected Double variacaoMensal=0.0;
	protected Double variacaoAnual=0.0;
	protected Double ultimosSeisMeses=0.0;
	protected Double ultimosDozeMeses=0.0;
	
	public RowRentabilidadeAcumulada()
	{
		
	}
	public RowRentabilidadeAcumulada(String[] fields)
	{
	
	}
	public RowRentabilidadeAcumulada(String line)
	{
		
	}
	public void showRow()
	{
		System.out.println("RAC:\t" 
				+ this.indexador 
				+ "\t" + Report.dfPorcentagem.format(this.benchmark)
				+ "\t" + Report.dfPorcentagem.format(this.rentabilidadeReal) 
				+ "\t" + Report.dfPorcentagem.format(this.variacaoDiaria)
				+ "\t" + Report.dfPorcentagem.format(this.variacaoMensal)
				+ "\t" + Report.dfPorcentagem.format(this.variacaoAnual)
				+ "\t" + Report.dfPorcentagem.format(this.ultimosSeisMeses)
				+ "\t" + Report.dfPorcentagem.format(this.ultimosDozeMeses)
				);
	}
	public String getRowCSV()
	{
		String rowCSV="RAC;" 
				+ this.indexador 
				+ ";" + Report.dfPorcentagem.format(this.benchmark)
				+ ";" + Report.dfPorcentagem.format(this.rentabilidadeReal) 
				+ ";" + Report.dfPorcentagem.format(this.variacaoDiaria)
				+ ";" + Report.dfPorcentagem.format(this.variacaoMensal)
				+ ";" + Report.dfPorcentagem.format(this.variacaoAnual)
				+ ";" + Report.dfPorcentagem.format(this.ultimosSeisMeses)
				+ ";" + Report.dfPorcentagem.format(this.ultimosDozeMeses)
				;
		return rowCSV;
	}
	public String getCodigo() {
		return indexador;
	}
	public void setCodigo(String codigo) {
		this.indexador = codigo;
	}
	public String getIndexador() {
		return indexador;
	}
	public void setIndexador(String indexador) {
		this.indexador = indexador;
	}
	public Double getBenchmark() {
		return benchmark;
	}
	public void setBenchmark(Double benchmark) {
		this.benchmark = benchmark;
	}
	public Double getRentabilidadeReal() {
		return rentabilidadeReal;
	}
	public void setRentabilidadeReal(Double rentabilidadeReal) {
		this.rentabilidadeReal = rentabilidadeReal;
	}
	public Double getVariacaoDiaria() {
		return variacaoDiaria;
	}
	public void setVariacaoDiaria(Double variacaoDiaria) {
		this.variacaoDiaria = variacaoDiaria;
	}
	public Double getVariacaoMensal() {
		return variacaoMensal;
	}
	public void setVariacaoMensal(Double variacaoMensal) {
		this.variacaoMensal = variacaoMensal;
	}
	public Double getVariacaoAnual() {
		return variacaoAnual;
	}
	public void setVariacaoAnual(Double variacaoAnual) {
		this.variacaoAnual = variacaoAnual;
	}
	public Double getUltimosSeisMeses() {
		return ultimosSeisMeses;
	}
	public void setUltimosSeisMeses(Double ultimosSeisMeses) {
		this.ultimosSeisMeses = ultimosSeisMeses;
	}
	public Double getUltimosDozeMeses() {
		return ultimosDozeMeses;
	}
	public void setUltimosDozeMeses(Double ultimosDozeMeses) {
		this.ultimosDozeMeses = ultimosDozeMeses;
	}	
}
