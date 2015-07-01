package report;

import java.text.DecimalFormat;

public class RowFundo extends Row 
{
	protected String codigo="";
	protected String fundo="";
	protected String instituicao="";
	protected Double quantidadeDeCotas=0.0;
	protected Double quantidadeBloqueada=0.0;
	protected Double valorDaCota=0.0;
	protected Double aplicacaoOuResgate=0.0;
	protected Double valorAtual=0.0;
	protected Double impostos=0.0;
	protected Double valorLiquido=0.0;
	protected Double percentualSobreFundos=0.0;
	protected Double percentualSobreTotal=0.0;
	protected DecimalFormat df = new DecimalFormat("#.########");	
	public RowFundo()
	{
		
	}
	
	public RowFundo(String line)
	{

	}		
	
	public void showRow()
	{
		System.out.println("FUNDOS:" 
				+"\t"+this.codigo
				+"\t"+this.fundo
				+"\t"+this.instituicao
				+"\t"+df.format(this.quantidadeDeCotas)
				+"\t"+df.format(this.quantidadeBloqueada)
				+"\t"+df.format(this.valorDaCota)
				+"\t"+df.format(this.aplicacaoOuResgate)
				+"\t"+df.format(this.valorAtual)
				+"\t"+df.format(this.impostos)
				+"\t"+df.format(this.valorLiquido)
				+"\t"+df.format(this.percentualSobreFundos)
				+"\t"+df.format(this.percentualSobreTotal));
	}
	
	public String getRowCSV()
	{
		String stringCSV="FUNDOS"
				+";"+this.codigo
				+";"+this.fundo
				+";"+this.instituicao
				+";"+df.format(this.quantidadeDeCotas)
				+";"+df.format(this.quantidadeBloqueada)
				+";"+df.format(this.valorDaCota)
				+";"+df.format(this.aplicacaoOuResgate)
				+";"+df.format(this.valorAtual)
				+";"+df.format(this.impostos)
				+";"+df.format(this.valorLiquido)
				+";"+df.format(this.percentualSobreFundos)
				+";"+df.format(this.percentualSobreTotal);
		return stringCSV;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
		return percentualSobreFundos;
	}
	public void setPercentualSobreRendaFixa(Double percentualSobreRendaFixa) {
		this.percentualSobreFundos = percentualSobreRendaFixa;
	}
	public Double getPercentualSobreTotal() {
		return percentualSobreTotal;
	}
	public void setPercentualSobreTotal(Double percentualSobreTotal) {
		this.percentualSobreTotal = percentualSobreTotal;
	}

	public String getNomeDoFundo() {
		return fundo;
	}

	public void setNomeDoFundo(String nomeDoFundo) {
		this.fundo = nomeDoFundo;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public Double getQuantidadeDeCotas() {
		return quantidadeDeCotas;
	}

	public void setQuantidadeDeCotas(Double quantidadeDeCotas) {
		this.quantidadeDeCotas = quantidadeDeCotas;
	}

	public Double getQuantidadeBloqueada() {
		return quantidadeBloqueada;
	}

	public void setQuantidadeBloqueada(Double quantidadeBloqueada) {
		this.quantidadeBloqueada = quantidadeBloqueada;
	}

	public Double getValorDaCota() {
		return valorDaCota;
	}

	public void setValorDaCota(Double valorDaCota) {
		this.valorDaCota = valorDaCota;
	}

	public Double getAplicacaoOuResgate() {
		return aplicacaoOuResgate;
	}

	public void setAplicacaoOuResgate(Double aplicacaoOuResgate) {
		this.aplicacaoOuResgate = aplicacaoOuResgate;
	}

	public Double getValorAtual() {
		return valorAtual;
	}

	public void setValorAtual(Double valorAtual) {
		this.valorAtual = valorAtual;
	}

	public Double getPercentualSobreFundos() {
		return percentualSobreFundos;
	}

	public void setPercentualSobreFundos(Double percentualSobreFundos) {
		this.percentualSobreFundos = percentualSobreFundos;
	}
	
	
}
