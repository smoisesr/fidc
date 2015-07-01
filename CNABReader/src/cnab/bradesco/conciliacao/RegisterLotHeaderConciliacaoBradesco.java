package cnab.bradesco.conciliacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cnab.base.RegisterField;
import cnab.base.Register;

public class RegisterLotHeaderConciliacaoBradesco extends Register
{
	private RegisterField banco = new RegisterField(1, 3,"Código do Banco na Compensação", "banco");
	private RegisterField lote = new RegisterField(4, 7,"Lote de Serviço", "lote");
	private RegisterField registro = new RegisterField(8, 8,"Tipo de Registro", "registro");
	private RegisterField operacao = new RegisterField(9, 9, "Tipo da Operação", "operacao");
	private RegisterField servico = new RegisterField(10, 11, "Tipo de Serviço", "servico");
	private RegisterField formaDeLancamento = new RegisterField(12, 13, "Forma de Lançamento", "formaDeLancamento");
	private RegisterField layoutDoLote = new RegisterField(14, 16,"Número da versão do layout do lote", "layoutDoLote");
	private RegisterField CNAB = new RegisterField(17, 17,"Uso Exclusivo Febraban/CNAB","CNAB");
	private RegisterField tipoDeInscricao = new RegisterField(18, 18,"Tipo de Inscrição da Empresa","tipoDeInscricao");
	private RegisterField numeroDeInscricao = new RegisterField(19, 32,"Número de Inscrição da Empresa","numeroDeInscricao");
	private RegisterField convenio = new RegisterField(33, 52,"Código do convénio no Banco","convenio");
	private RegisterField codigoAgencia = new RegisterField(53, 57,"Agencia mantenedora da conta","codigoAgencia");
	private RegisterField digitoVerificadorAgencia = new RegisterField(58,58,"Digito verificador da agência","digitoVerificadorAgencia");
	private RegisterField numeroConta = new RegisterField(59, 70,"Número da conta corrente","numeroConta");
	private RegisterField digitoVerificadorConta = new RegisterField(71, 71,"Digito verificador da conta","digitoVerificadorConta");
	private RegisterField digitoVerificadorAgenciaConta = new RegisterField(72, 72,"Digito verificador da agência/conta","digitoVerificadorAgenciaConta");
	private RegisterField nomeDaEmpresa = new RegisterField(73, 102,"Nome da Empresa","nomeDaEmpresa");
	private RegisterField CNAB2 = new RegisterField(103, 142,"Uso Exclusivo Febraban/CNAB","CNAB2");
	private RegisterField dataDoSaldoInicial = new RegisterField(143, 150, "Data do Saldo Inicial","dataDoSaldoInicial");
	private RegisterField valorDoSaldoInicial = new RegisterField(151, 168, "Valor do Saldo Inicial", "valorDoSaldoInicial");
	private RegisterField situacaoDoSaldoInicial = new RegisterField(169, 169, "Situação do Saldo Inicial","situacaoDoSaldoInicial");
	private RegisterField statusDoSaldoInicial = new RegisterField(170, 170, "Posição do Saldo Inicial", "statusDoSaldoInicial");
	private RegisterField tipoDeMoedaDoSaldoInicial = new RegisterField(171, 173, "Moeda referenciada no Extrato","tipoDeMoedaDoSaldoInicial");
	private RegisterField sequenciaExtratoDoSaldoInicial = new RegisterField(174, 178,"Número sequencial do Extrato", "sequenciaExtratoDoSaldoInicial");
	private RegisterField CNAB3 = new RegisterField(179, 240,"Uso exclusivo Febraban/CNAB", "CNAB3");
	
	public RegisterLotHeaderConciliacaoBradesco(String line)
	{
		this.banco.setValue(Integer.parseInt((String) RegisterField.extractString(this.banco.getInitialPosition(), this.banco.getFinalPosition(), line)));
		this.lote.setValue(Integer.parseInt((String) RegisterField.extractString(this.lote.getInitialPosition(), this.lote.getFinalPosition(), line)));
		this.registro.setValue(Integer.parseInt((String) RegisterField.extractString(this.registro.getInitialPosition(), this.registro.getFinalPosition(), line)));
		this.operacao.setValue((String) RegisterField.extractString(this.operacao.getInitialPosition(), this.operacao.getFinalPosition(), line));
		this.servico.setValue(Integer.parseInt((String) RegisterField.extractString(this.servico.getInitialPosition(), this.servico.getFinalPosition(), line)));
		this.formaDeLancamento.setValue(Integer.parseInt((String) RegisterField.extractString(this.formaDeLancamento.getInitialPosition(), this.formaDeLancamento.getFinalPosition(), line)));
		this.layoutDoLote.setValue(Integer.parseInt((String) RegisterField.extractString(this.layoutDoLote.getInitialPosition(), this.layoutDoLote.getFinalPosition(), line)));
		this.CNAB.setValue((String) RegisterField.extractString(this.CNAB.getInitialPosition(), this.CNAB.getFinalPosition(), line));
		this.tipoDeInscricao.setValue(Integer.parseInt((String) RegisterField.extractString(this.tipoDeInscricao.getInitialPosition(), this.tipoDeInscricao.getFinalPosition(), line)));
		this.numeroDeInscricao.setValue(Long.parseLong((String) RegisterField.extractString(this.numeroDeInscricao.getInitialPosition(), this.numeroDeInscricao.getFinalPosition(), line)));
		this.convenio.setValue((String) RegisterField.extractString(this.convenio.getInitialPosition(), this.convenio.getFinalPosition(), line));
		this.codigoAgencia.setValue(Integer.parseInt((String) RegisterField.extractString(this.codigoAgencia.getInitialPosition(), this.codigoAgencia.getFinalPosition(), line)));
		this.digitoVerificadorAgencia.setValue((String) RegisterField.extractString(this.digitoVerificadorAgencia.getInitialPosition(), this.digitoVerificadorAgencia.getFinalPosition(), line));
		this.numeroConta.setValue(Integer.parseInt((String) RegisterField.extractString(this.numeroConta.getInitialPosition(), this.numeroConta.getFinalPosition(), line)));
		this.digitoVerificadorConta.setValue((String) RegisterField.extractString(this.digitoVerificadorConta.getInitialPosition(), this.digitoVerificadorConta.getFinalPosition(), line));
		this.digitoVerificadorAgenciaConta.setValue((String) RegisterField.extractString(this.digitoVerificadorAgenciaConta.getInitialPosition(), this.digitoVerificadorAgenciaConta.getFinalPosition(), line));
		this.nomeDaEmpresa.setValue((String) RegisterField.extractString(this.nomeDaEmpresa.getInitialPosition(), this.nomeDaEmpresa.getFinalPosition(), line));
		this.CNAB2.setValue((String) RegisterField.extractString(this.CNAB2.getInitialPosition(), this.CNAB2.getFinalPosition(), line));
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat formatterLocal = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = formatter.parse((String) RegisterField.extractString(this.dataDoSaldoInicial.getInitialPosition(), this.dataDoSaldoInicial.getFinalPosition(), line));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.dataDoSaldoInicial.setValue(formatterLocal.format(date));
		this.valorDoSaldoInicial.setValue(Double.parseDouble((String) RegisterField.extractString(this.valorDoSaldoInicial.getInitialPosition(), this.valorDoSaldoInicial.getFinalPosition(), line))/100);
		this.situacaoDoSaldoInicial.setValue((String) RegisterField.extractString(this.situacaoDoSaldoInicial.getInitialPosition(), this.situacaoDoSaldoInicial.getFinalPosition(), line));
		this.statusDoSaldoInicial.setValue((String) RegisterField.extractString(this.statusDoSaldoInicial.getInitialPosition(), this.statusDoSaldoInicial.getFinalPosition(), line));
		this.tipoDeMoedaDoSaldoInicial.setValue((String) RegisterField.extractString(this.tipoDeMoedaDoSaldoInicial.getInitialPosition(), this.tipoDeMoedaDoSaldoInicial.getFinalPosition(), line));
		this.sequenciaExtratoDoSaldoInicial.setValue(Integer.parseInt( (String) RegisterField.extractString(this.sequenciaExtratoDoSaldoInicial.getInitialPosition(), this.sequenciaExtratoDoSaldoInicial.getFinalPosition(), line)));
		this.CNAB3.setValue((String) RegisterField.extractString(this.CNAB3.getInitialPosition(), this.CNAB3.getFinalPosition(), line));
	}
	
	public void showRegister()
	{
		System.out.print(this.banco.getValue().toString()+"\t");
		System.out.print(this.lote.getValue().toString()+"\t");
		System.out.print(this.registro.getValue().toString()+"\t");
		System.out.print(this.operacao.getValue().toString()+"\t");
		System.out.print(this.servico.getValue().toString()+"\t");
		System.out.print(this.formaDeLancamento.getValue().toString()+"\t");
		System.out.print(this.layoutDoLote.getValue().toString()+"\t");
		System.out.print(this.CNAB.getValue().toString()+"\t");
		System.out.print(this.tipoDeInscricao.getValue().toString()+"\t");
		System.out.print(this.numeroDeInscricao.getValue().toString()+"\t");
		System.out.print(this.convenio.getValue().toString()+"\t");
		System.out.print(this.codigoAgencia.getValue().toString()+"\t");
		System.out.print(this.digitoVerificadorAgencia.getValue().toString()+"\t");
		System.out.print(this.numeroConta.getValue().toString()+"\t");
		System.out.print(this.digitoVerificadorConta.getValue().toString()+"\t");
		System.out.print(this.digitoVerificadorAgenciaConta.getValue().toString()+"\t");
		System.out.print(this.nomeDaEmpresa.getValue().toString()+"\t");
		System.out.print(this.CNAB2.getValue().toString()+"\t");
		System.out.print(this.dataDoSaldoInicial.getValue().toString()+"\t");
		System.out.print(this.valorDoSaldoInicial.getValue().toString()+"\t");
		System.out.print(this.situacaoDoSaldoInicial.getValue().toString()+"\t");
		System.out.print(this.statusDoSaldoInicial.getValue().toString()+"\t");
		System.out.print(this.tipoDeMoedaDoSaldoInicial.getValue().toString()+"\t");
		System.out.print(this.sequenciaExtratoDoSaldoInicial.getValue().toString()+"\t");
		System.out.print(this.CNAB3.getValue().toString()+"\n");
	}
	public RegisterField getBanco()
	{
		return banco;
	}
	public void setBanco(RegisterField banco)
	{
		this.banco = banco;
	}
	public RegisterField getLote()
	{
		return lote;
	}
	public void setLote(RegisterField lote)
	{
		this.lote = lote;
	}
	public RegisterField getRegistro()
	{
		return registro;
	}
	public void setRegistro(RegisterField registro)
	{
		this.registro = registro;
	}
	public RegisterField getCNAB()
	{
		return CNAB;
	}
	public void setCNAB(RegisterField cNAB)
	{
		CNAB = cNAB;
	}
	public RegisterField getTipoDeInscricao()
	{
		return tipoDeInscricao;
	}
	public void setTipoDeInscricao(RegisterField tipoDeInscricao)
	{
		this.tipoDeInscricao = tipoDeInscricao;
	}
	public RegisterField getNumeroDeInscricao()
	{
		return numeroDeInscricao;
	}
	public void setNumeroDeInscricao(RegisterField numeroDeInscricao)
	{
		this.numeroDeInscricao = numeroDeInscricao;
	}
	public RegisterField getConvenio()
	{
		return convenio;
	}
	public void setConvenio(RegisterField convenio)
	{
		this.convenio = convenio;
	}
	public RegisterField getCodigoAgencia()
	{
		return codigoAgencia;
	}
	public void setCodigoAgencia(RegisterField codigoAgencia)
	{
		this.codigoAgencia = codigoAgencia;
	}
	public RegisterField getDigitoVerificadorAgencia()
	{
		return digitoVerificadorAgencia;
	}
	public void setDigitoVerificadorAgencia(RegisterField digitoVerificadorAgencia)
	{
		this.digitoVerificadorAgencia = digitoVerificadorAgencia;
	}
	public RegisterField getNumeroConta()
	{
		return numeroConta;
	}
	public void setNumeroConta(RegisterField numeroConta)
	{
		this.numeroConta = numeroConta;
	}
	public RegisterField getDigitoVerificadorConta()
	{
		return digitoVerificadorConta;
	}
	public void setDigitoVerificadorConta(RegisterField digitoVerificadorConta)
	{
		this.digitoVerificadorConta = digitoVerificadorConta;
	}
	public RegisterField getDigitoVerificadorAgenciaConta()
	{
		return digitoVerificadorAgenciaConta;
	}
	public void setDigitoVerificadorAgenciaConta(RegisterField digitoVerificadorAgenciaConta)
	{
		this.digitoVerificadorAgenciaConta = digitoVerificadorAgenciaConta;
	}
	public RegisterField getNomeDaEmpresa()
	{
		return nomeDaEmpresa;
	}
	public void setNomeDaEmpresa(RegisterField nomeDaEmpresa)
	{
		this.nomeDaEmpresa = nomeDaEmpresa;
	}
	public RegisterField getCNAB2()
	{
		return CNAB2;
	}
	public void setCNAB2(RegisterField cNAB2)
	{
		CNAB2 = cNAB2;
	}	
	public RegisterField getCNAB3()
	{
		return CNAB3;
	}
	public void setCNAB3(RegisterField cNAB3)
	{
		CNAB3 = cNAB3;
	}
	public RegisterField getOperacao() {
		return operacao;
	}
	public void setOperacao(RegisterField operacao) {
		this.operacao = operacao;
	}
	public RegisterField getServico() {
		return servico;
	}
	public void setServico(RegisterField servico) {
		this.servico = servico;
	}
	public RegisterField getFormaDeLancamento() {
		return formaDeLancamento;
	}
	public void setFormaDeLancamento(RegisterField formaDeLancamento) {
		this.formaDeLancamento = formaDeLancamento;
	}
	public RegisterField getLayoutDoLote() {
		return layoutDoLote;
	}
	public void setLayoutDoLote(RegisterField layoutDoLote) {
		this.layoutDoLote = layoutDoLote;
	}
	public RegisterField getDataDoSaldoInicial() {
		return dataDoSaldoInicial;
	}
	public void setDataDoSaldoInicial(RegisterField dataDoSaldoInicial) {
		this.dataDoSaldoInicial = dataDoSaldoInicial;
	}
	public RegisterField getValorDoSaldoInicial() {
		return valorDoSaldoInicial;
	}
	public void setValorDoSaldoInicial(RegisterField valorDoSaldoInicial) {
		this.valorDoSaldoInicial = valorDoSaldoInicial;
	}
	public RegisterField getSituacaoDoSaldoInicial() {
		return situacaoDoSaldoInicial;
	}
	public void setSituacaoDoSaldoInicial(RegisterField situacaoDoSaldoInicial) {
		this.situacaoDoSaldoInicial = situacaoDoSaldoInicial;
	}
	public RegisterField getStatusDoSaldoInicial() {
		return statusDoSaldoInicial;
	}
	public void setStatusDoSaldoInicial(RegisterField statusDoSaldoInicial) {
		this.statusDoSaldoInicial = statusDoSaldoInicial;
	}
	public RegisterField getTipoDeMoedaDoSaldoInicial() {
		return tipoDeMoedaDoSaldoInicial;
	}
	public void setTipoDeMoedaDoSaldoInicial(RegisterField tipoDeMoedaDoSaldoInicial) {
		this.tipoDeMoedaDoSaldoInicial = tipoDeMoedaDoSaldoInicial;
	}
	public RegisterField getSequenciaExtratoDoSaldoInicial() {
		return sequenciaExtratoDoSaldoInicial;
	}
	public void setSequenciaExtratoDoSaldoInicial(
			RegisterField sequenciaExtratoDoSaldoInicial) {
		this.sequenciaExtratoDoSaldoInicial = sequenciaExtratoDoSaldoInicial;
	}
}
