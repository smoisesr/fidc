package report;

import java.util.Date;

public class RowOperacoesCompromissadas extends Row 
{
	protected String codigo="";
	protected Date dataDeAquisicao=null;
	protected String emitente="";
	protected String papel="";
	protected Double mtmPercentagemAoAno=0.0;
	protected Double taxaOver=0.0;
	protected Double taxaAoAno=0.0;
	protected String index="";
	protected Date dataDeEmissao=null;
	protected Date dataDeVencimento=null;
	protected Date dataDeResgate=null;
	protected Double quantidade=0.0;
	protected Double puMercado=0.0;
	protected Double valorDaAplicacao=0.0;
	protected Double valorDoResgate=0.0;
	protected Double valorBruto=0.0;
	protected Double impostos=0.0;
	protected Double valorLiquido=0.0;
	protected Double percentualSobreRendaFixa=0.0;
	protected Double percentualSobreTotal=0.0;
	
	public RowOperacoesCompromissadas()
	{
		
	}
	public RowOperacoesCompromissadas(String[] fields)
	{
	}
	public RowOperacoesCompromissadas(String line)
	{
	}
	public void showRow()
	{
		System.out.println("OC:\t" 
				+ this.codigo 
				+ "\t" + Report.formatterDateShort.format(this.dataDeAquisicao) 
				+ "\t" + this.emitente 
				+ "\t" + this.papel 
				+ "\t" + Report.dfPorcentagem.format(this.mtmPercentagemAoAno)
				+ "\t" + Report.dfPorcentagem.format(this.taxaOver) 
				+ "\t" + Report.dfPorcentagem.format(this.taxaAoAno)
				+ "\t" + this.index
				+ "\t" + Report.formatterDateShort.format(this.dataDeEmissao)
				+ "\t" + Report.formatterDateShort.format(this.dataDeVencimento)
				+ "\t" + Report.formatterDateShort.format(this.dataDeResgate)
				+ "\t" + Report.df.format(this.quantidade)
				+ "\t" + Report.df.format(this.puMercado)
				+ "\t" + Report.df.format(this.valorDaAplicacao)
				+ "\t" + Report.df.format(this.valorDoResgate)
				+ "\t" + Report.df.format(this.valorBruto)
				+ "\t" + Report.df.format(this.impostos)
				+ "\t" + Report.df.format(this.valorLiquido)
				+ "\t" + Report.df.format(this.percentualSobreRendaFixa)
				+ "\t" + Report.df.format(this.percentualSobreTotal)
				);
	}
	
	public String getRowCSV()
	{
		String rowCSV="OC;" 
				+ this.codigo 
				+ ";" + Report.formatterDateShort.format(this.dataDeAquisicao) 
				+ ";" + this.emitente 
				+ ";" + this.papel 
				+ ";" + Report.dfPorcentagem.format(this.mtmPercentagemAoAno)
				+ ";" + Report.dfPorcentagem.format(this.taxaOver) 
				+ ";" + Report.dfPorcentagem.format(this.taxaAoAno)
				+ ";" + this.index
				+ ";" + Report.formatterDateShort.format(this.dataDeEmissao)
				+ ";" + Report.formatterDateShort.format(this.dataDeVencimento)
				+ ";" + Report.formatterDateShort.format(this.dataDeResgate)
				+ ";" + Report.df.format(this.quantidade)
				+ ";" + Report.df.format(this.puMercado)
				+ ";" + Report.df.format(this.valorDaAplicacao)
				+ ";" + Report.df.format(this.valorDoResgate)
				+ ";" + Report.df.format(this.valorBruto)
				+ ";" + Report.df.format(this.impostos)
				+ ";" + Report.df.format(this.valorLiquido)
				+ ";" + Report.df.format(this.percentualSobreRendaFixa)
				+ ";" + Report.df.format(this.percentualSobreTotal)
				;
		return rowCSV;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Date getAplicacao() {
		return dataDeAquisicao;
	}
	public void setAplicacao(Date aplicacao) {
		this.dataDeAquisicao = aplicacao;
	}
	public String getEmitente() {
		return emitente;
	}
	public void setEmitente(String emitente) {
		this.emitente = emitente;
	}
	public String getPapel() {
		return papel;
	}
	public void setPapel(String papel) {
		this.papel = papel;
	}
	public Double getMtmPercAa() {
		return mtmPercentagemAoAno;
	}
	public void setMtmPercAa(Double mtmPercAa) {
		this.mtmPercentagemAoAno = mtmPercAa;
	}
	public Double getTaxaOver() {
		return taxaOver;
	}
	public void setTaxaOver(Double taxaOver) {
		this.taxaOver = taxaOver;
	}
	public Double getTaxaAa() {
		return taxaAoAno;
	}
	public void setTaxaAa(Double taxaAa) {
		this.taxaAoAno = taxaAa;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public Date getEmissao() {
		return dataDeEmissao;
	}
	public void setEmissao(Date emissao) {
		this.dataDeEmissao = emissao;
	}
	public Date getVencimento() {
		return dataDeVencimento;
	}
	public void setVencimento(Date vencimento) {
		this.dataDeVencimento = vencimento;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public Double getPuMercado() {
		return puMercado;
	}
	public void setPuMercado(Double puMercado) {
		this.puMercado = puMercado;
	}
	public Double getValorDaAplicacao() {
		return valorDaAplicacao;
	}
	public void setValorDaAplicacao(Double valorDaAplicacao) {
		this.valorDaAplicacao = valorDaAplicacao;
	}
	public Double getValorDeResgate() {
		return valorDoResgate;
	}
	public void setValorDeResgate(Double valorDeResgate) {
		this.valorDoResgate = valorDeResgate;
	}
	public Double getValorBruto() {
		return valorBruto;
	}
	public void setValorBruto(Double valorBruto) {
		this.valorBruto = valorBruto;
	}
	public Double getImpostos() {
		return impostos;
	}
	public void setImpostos(Double impostos) {
		this.impostos = impostos;
	}
	public Double getValorLiquido() {
		return valorLiquido;
	}
	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}
	public Double getPercentualSobreRendaFixa() {
		return percentualSobreRendaFixa;
	}
	public void setPercentualSobreRendaFixa(Double percentualSobreRendaFixa) {
		this.percentualSobreRendaFixa = percentualSobreRendaFixa;
	}
	public Double getPercentualSobreTotal() {
		return percentualSobreTotal;
	}
	public void setPercentualSobreTotal(Double percentualSobreTotal) {
		this.percentualSobreTotal = percentualSobreTotal;
	}
	public Date getDataDeAquisicao() {
		return dataDeAquisicao;
	}
	public void setDataDeAquisicao(Date dataDeAquisicao) {
		this.dataDeAquisicao = dataDeAquisicao;
	}
	public Double getMtmPercentagemAoAno() {
		return mtmPercentagemAoAno;
	}
	public void setMtmPercentagemAoAno(Double mtmPercentagemAoAno) {
		this.mtmPercentagemAoAno = mtmPercentagemAoAno;
	}
	public Double getTaxaAoAno() {
		return taxaAoAno;
	}
	public void setTaxaAoAno(Double taxaAoAno) {
		this.taxaAoAno = taxaAoAno;
	}
	public Date getDataDeEmissao() {
		return dataDeEmissao;
	}
	public void setDataDeEmissao(Date dataDeEmissao) {
		this.dataDeEmissao = dataDeEmissao;
	}
	public Date getDataDeVencimento() {
		return dataDeVencimento;
	}
	public void setDataDeVencimento(Date dataDeVencimento) {
		this.dataDeVencimento = dataDeVencimento;
	}
	public Date getDataDeResgate() {
		return dataDeResgate;
	}
	public void setDataDeResgate(Date dataDeResgate) {
		this.dataDeResgate = dataDeResgate;
	}
	public Double getValorDoResgate() {
		return valorDoResgate;
	}
	public void setValorDoResgate(Double valorDoResgate) {
		this.valorDoResgate = valorDoResgate;
	}
	
	
}
