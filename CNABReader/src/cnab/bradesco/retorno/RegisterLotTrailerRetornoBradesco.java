package cnab.bradesco.retorno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cnab.base.RegisterField;
import cnab.base.Register;

public class RegisterLotTrailerRetornoBradesco extends Register
{
	private RegisterField banco = new RegisterField(1, 3,"Código do Banco na Compensação", "banco");
	private RegisterField lote = new RegisterField(4, 7,"Lote de Serviço", "lote");
	private RegisterField registro = new RegisterField(8, 8,"Tipo de Registro", "registro");
	private RegisterField CNAB = new RegisterField(9, 17,"Uso Exclusivo Febraban/CNAB","CNAB");
	private RegisterField tipoDeInscricao = new RegisterField(18, 18,"Tipo de Inscrição da Empresa","tipoDeInscricao");
	private RegisterField numeroDeInscricao = new RegisterField(19, 32,"Número de Inscrição da Empresa","numeroDeInscricao");
	private RegisterField convenio = new RegisterField(33, 52,"Código do convénio no Banco","convenio");
	private RegisterField codigoAgencia = new RegisterField(53, 57,"Agencia mantenedora da conta","codigoAgencia");
	private RegisterField digitoVerificadorAgencia = new RegisterField(58,58,"Digito verificador da agência","digitoVerificadorAgencia");
	private RegisterField numeroConta = new RegisterField(59, 70,"Número da conta corrente","numeroConta");
	private RegisterField digitoVerificadorConta = new RegisterField(71, 71,"Digito verificador da conta","digitoVerificadorConta");
	private RegisterField digitoVerificadorAgenciaConta = new RegisterField(72, 72,"Digito verificador da agência/conta","digitoVerificadorAgenciaConta");
	private RegisterField nomeDaEmpresa = new RegisterField(73, 102,"Nome da Empresa","nomeDaEmpresa");
	private RegisterField CNAB2 = new RegisterField(103, 106,"Uso Exclusivo Febraban/CNAB","CNAB2");
	//private RegisterField bloqueadoValores = new RegisterField(89, 106, "Saldo Bloqueado Acima 24 horas","bloqueadoValores");
	private RegisterField limiteValores = new RegisterField(107, 124, "Limite da Conta", "limiteValores");
	private RegisterField bloqueadoValores2  = new RegisterField(125, 142, "Saldo Bloqueado Acima 24 horas (2)","bloqueadoValores2");
	private RegisterField dataDoSaldoFinal = new RegisterField(143, 150, "Data do Saldo Final","dataDoSaldoFinal");
	private RegisterField valorDoSaldoFinal = new RegisterField(151, 168, "Valor do Saldo Final", "valorDoSaldoFinal");
	private RegisterField situacaoDoSaldoFinal = new RegisterField(169, 169, "Situação do Saldo Final","situacaoDoSaldoFinal");
	private RegisterField statusDoSaldoFinal = new RegisterField(170, 170, "Posição do Saldo Final", "statusDoSaldoFinal");
	private RegisterField quantidadeDeRegistrosTotal = new RegisterField(171, 176, "Quantidade de Registros do Lote", "quantidadeDeRegistrosTotal");
	private RegisterField valorDebitosTotal = new RegisterField(177, 194, "Somatória dos Valores a Débito", "valorDebitosTotal");
	private RegisterField valorCreditosTotal = new RegisterField(195, 212, "Somatória dos Valores a Débito", "valorCreditosTotal");
	private RegisterField CNAB3 = new RegisterField(213, 240,"Uso exclusivo Febraban/CNAB", "CNAB3");
	
	public RegisterLotTrailerRetornoBradesco(String line)
	{
		this.banco.setValue(Integer.parseInt((String) RegisterField.extractString(this.banco.getInitialPosition(), this.banco.getFinalPosition(), line)));
		this.lote.setValue(Integer.parseInt((String) RegisterField.extractString(this.lote.getInitialPosition(), this.lote.getFinalPosition(), line)));
		this.registro.setValue(Integer.parseInt((String) RegisterField.extractString(this.registro.getInitialPosition(), this.registro.getFinalPosition(), line)));
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
		//this.bloqueadoValores.setValue(Double.parseDouble((String) RegisterField.extractString(this.bloqueadoValores.getInitialPosition(), this.bloqueadoValores.getFinalPosition(), line))/100);
		this.limiteValores.setValue(Double.parseDouble((String) RegisterField.extractString(this.limiteValores.getInitialPosition(), this.limiteValores.getFinalPosition(), line))/100);
		this.bloqueadoValores2.setValue(Double.parseDouble((String) RegisterField.extractString(this.bloqueadoValores2.getInitialPosition(), this.bloqueadoValores2.getFinalPosition(), line))/100);
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat formatterLocal = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = formatter.parse((String) RegisterField.extractString(this.dataDoSaldoFinal.getInitialPosition(), this.dataDoSaldoFinal.getFinalPosition(), line));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.dataDoSaldoFinal.setValue(formatterLocal.format(date));
		this.valorDoSaldoFinal.setValue(Double.parseDouble((String) RegisterField.extractString(this.valorDoSaldoFinal.getInitialPosition(), this.valorDoSaldoFinal.getFinalPosition(), line))/100);
		this.situacaoDoSaldoFinal.setValue((String) RegisterField.extractString(this.situacaoDoSaldoFinal.getInitialPosition(), this.situacaoDoSaldoFinal.getFinalPosition(), line));
		this.statusDoSaldoFinal.setValue((String) RegisterField.extractString(this.statusDoSaldoFinal.getInitialPosition(), this.statusDoSaldoFinal.getFinalPosition(), line));
		this.quantidadeDeRegistrosTotal.setValue(Integer.parseInt((String) RegisterField.extractString(this.quantidadeDeRegistrosTotal.getInitialPosition(), this.quantidadeDeRegistrosTotal.getFinalPosition(), line)));
		this.valorDebitosTotal.setValue(Double.parseDouble((String) RegisterField.extractString(this.valorDebitosTotal.getInitialPosition(), this.valorDebitosTotal.getFinalPosition(), line))/100);
		this.valorCreditosTotal.setValue(Double.parseDouble((String) RegisterField.extractString(this.valorCreditosTotal.getInitialPosition(), this.valorCreditosTotal.getFinalPosition(), line))/100);
		this.CNAB3.setValue((String) RegisterField.extractString(this.CNAB3.getInitialPosition(), this.CNAB3.getFinalPosition(), line));
	}
	public void showRegister()
	{
		System.out.print(this.banco.getValue().toString()+"\t");
		System.out.print(this.lote.getValue().toString()+"\t");
		System.out.print(this.registro.getValue().toString()+"\t");
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
		//System.out.print(this.bloqueadoValores.getValue().toString()+"\t");
		System.out.print(this.limiteValores.getValue().toString()+"\t");
		System.out.print(this.bloqueadoValores2.getValue().toString()+"\t");
		System.out.print(this.dataDoSaldoFinal.getValue().toString()+"\t");
		System.out.print(this.valorDoSaldoFinal.getValue().toString()+"\t");
		System.out.print(this.situacaoDoSaldoFinal.getValue().toString()+"\t");
		System.out.print(this.statusDoSaldoFinal.getValue().toString()+"\t");
		System.out.print(this.quantidadeDeRegistrosTotal.getValue().toString()+"\t");
		System.out.print(this.valorDebitosTotal.getValue().toString()+"\t");
		System.out.print(this.valorCreditosTotal.getValue().toString()+"\t");
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
	public RegisterField getDataDoSaldoFinal() {
		return dataDoSaldoFinal;
	}
	public void setDataDoSaldoFinal(RegisterField dataDoSaldoFinal) {
		this.dataDoSaldoFinal = dataDoSaldoFinal;
	}
	public RegisterField getValorDoSaldoFinal() {
		return valorDoSaldoFinal;
	}
	public void setValorDoSaldoFinal(RegisterField valorDoSaldoFinal) {
		this.valorDoSaldoFinal = valorDoSaldoFinal;
	}
	public RegisterField getSituacaoDoSaldoFinal() {
		return situacaoDoSaldoFinal;
	}
	public void setSituacaoDoSaldoFinal(RegisterField situacaoDoSaldoFinal) {
		this.situacaoDoSaldoFinal = situacaoDoSaldoFinal;
	}
	public RegisterField getStatusDoSaldoFinal() {
		return statusDoSaldoFinal;
	}
	public void setStatusDoSaldoFinal(RegisterField statusDoSaldoFinal) {
		this.statusDoSaldoFinal = statusDoSaldoFinal;
	}
//	public RegisterField getBloqueadoValores() {
//		return bloqueadoValores;
//	}
//	public void setBloqueadoValores(RegisterField bloqueadoValores) {
//		this.bloqueadoValores = bloqueadoValores;
//	}
	public RegisterField getLimiteValores() {
		return limiteValores;
	}
	public void setLimiteValores(RegisterField limiteValores) {
		this.limiteValores = limiteValores;
	}
	public RegisterField getBloqueadoValores2() {
		return bloqueadoValores2;
	}
	public void setBloqueadoValores2(RegisterField bloqueadoValores2) {
		this.bloqueadoValores2 = bloqueadoValores2;
	}
	public RegisterField getQuantidadeDeRegistrosTotal() {
		return quantidadeDeRegistrosTotal;
	}
	public void setQuantidadeDeRegistrosTotal(RegisterField quantidadeDeRegistrosTotal) {
		this.quantidadeDeRegistrosTotal = quantidadeDeRegistrosTotal;
	}
	public RegisterField getValorDebitosTotal() {
		return valorDebitosTotal;
	}
	public void setValorDebitosTotal(RegisterField valorDebitosTotal) {
		this.valorDebitosTotal = valorDebitosTotal;
	}
	public RegisterField getValorCreditosTotal() {
		return valorCreditosTotal;
	}
	public void setValorCreditosTotal(RegisterField valorCreditosTotal) {
		this.valorCreditosTotal = valorCreditosTotal;
	}
}
