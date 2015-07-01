package report;

import java.util.Date;

import report.BlockIdentificacao;

import utils.DyadKeywordName;

public class BlockIdentificacao extends Block
{
	public DyadKeywordName keywordName = new DyadKeywordName("relatorio de carteira diaria","IDENTIFICACAO");
	public Date dataDaPosicao=null;
	public Date dataDeEmissao=null;
	public String cliente="";
	public String codigoDoCliente="";

	public BlockIdentificacao()
	{
		super.setName(this.keywordName.getName());
	}
	public String getBlockCSV()
	{
		String blockCSV="";
		blockCSV=blockCSV
				+ "ID;DataDaPosissao;" + Report.formatterDateShort.format(this.dataDaPosicao)+"\n"
				+ "ID;DataDeEmissao;" + Report.formatterDateShort.format(this.dataDeEmissao)+"\n"
				+ "ID;Cliente;" + this.cliente+"\n"
				+ "ID;CodigoDoCliente;" + this.codigoDoCliente+"\n";
		return blockCSV;
	}
	public void showBlock()
	{
		System.out.println("ID:\tDataDaPosissao:\t" + Report.formatterDateShort.format(this.dataDaPosicao));
		System.out.println("ID:\tDataDeEmissao:\t" + Report.formatterDateShort.format(this.dataDeEmissao));
		System.out.println("ID:\tCliente:\t" + this.cliente);
		System.out.println("ID:\tCodigoDoCliente:\t" + this.codigoDoCliente);
	}
	public Date getDataDaPosicao() {
		return dataDaPosicao;
	}
	public void setDataDaPosicao(Date dataDaPosicao) {
		this.dataDaPosicao = dataDaPosicao;
	}
	public Date getDataDeEmissao() {
		return dataDeEmissao;
	}
	public void setDataDeEmissao(Date dataDeEmissao) {
		this.dataDeEmissao = dataDeEmissao;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCodigoDoCliente() {
		return codigoDoCliente;
	}
	public void setCodigoDoCliente(String codigoDoCliente) {
		this.codigoDoCliente = codigoDoCliente;
	}
	public DyadKeywordName getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(DyadKeywordName keywordName) {
		this.keywordName = keywordName;
	}
}
