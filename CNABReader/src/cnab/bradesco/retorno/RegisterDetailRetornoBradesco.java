package cnab.bradesco.retorno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cnab.base.RegisterField;
import cnab.base.Register;

public class RegisterDetailRetornoBradesco extends Register
{
	private RegisterField banco = new RegisterField(1, 3,"Código do Banco na Compensação", "banco"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField lote = new RegisterField(4, 7,"Lote de Serviço", "lote"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField registro = new RegisterField(8, 8,"Tipo de Registro", "registro"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField numeroDeRegistroDeServico = new RegisterField(9, 13,"Número sequencial do Registro no lote", "numeroDeRegistroDeServico"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField segmentoDeServico = new RegisterField(14, 14,"Código de Segmento do Reg. Detalhe", "segmentoDeServico"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField CNAB = new RegisterField(15, 17,"Uso Exclusivo Febraban/CNAB","CNAB"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField tipoDeInscricao = new RegisterField(18, 18,"Tipo de Inscrição da Empresa","tipoDeInscricao"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField numeroDeInscricao = new RegisterField(19, 32,"Número de Inscrição da Empresa","numeroDeInscricao"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField convenio = new RegisterField(33, 52,"Código do convénio no Banco","convenio"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField codigoAgencia = new RegisterField(53, 57,"Agencia mantenedora da conta","codigoAgencia"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField digitoVerificadorAgencia = new RegisterField(58,58,"Digito verificador da agência","digitoVerificadorAgencia"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField numeroConta = new RegisterField(59, 70,"Número da conta corrente","numeroConta"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField digitoVerificadorConta = new RegisterField(71, 71,"Digito verificador da conta","digitoVerificadorConta"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField digitoVerificadorAgenciaConta = new RegisterField(72, 72,"Digito verificador da agência/conta","digitoVerificadorAgenciaConta"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField nomeDaEmpresa = new RegisterField(73, 102,"Nome da Empresa","nomeDaEmpresa"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField natureza = new RegisterField(103, 130, "Natureza do Lançamento", "natureza"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField CNAB2 = new RegisterField(131, 142, "Uso Exclusivo Febraban/CNAB","CNAB2"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField dataDoLancamento = new RegisterField(143, 150, "Data do Lançamento", "dataDoLancamento"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField valorDoLancamento = new RegisterField(151, 168, "Valor do Lançamento", "valorDoLancamento"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField tipoDoLancamento = new RegisterField(169, 169, "Tipo do Lançamento", "tipoDoLancamento"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField categoriaDoLancamento = new RegisterField(170, 172, "Categoria do Lançamento", "categoriaDoLancamento"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField codigoHistoricoDoLancamento = new RegisterField(173, 176, "Código Histórico do Lançamento no Banco", "codigoHistoricoDoLancamento"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField historicoDoLancamento = new RegisterField(177, 201, "Descrição do Histórico do Lançamento no Banco", "historicoDoLancamento"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField numeroDoDocumento = new RegisterField(202, 240, "Número Documento/Complemento","numeroDoDocumento"); //$NON-NLS-1$ //$NON-NLS-2$
	
	//private RegisterField tipoDoComplemento = new RegisterField(112, 113, "Tipo do Complemento Lançamento", "tipoDoComplemento");
//	private RegisterField complemento = new RegisterField(114, 133, "Complemento Lançamento", "complemento");
//	private RegisterField cpmf = new RegisterField(134, 134, "Identificação de Isenção do CPMF", "cpmf");
//	private RegisterField dataContabil = new RegisterField(135, 142, "Data Contábil","dataContabil");
		
	public RegisterDetailRetornoBradesco(String line)
	{		
		this.banco.setValue(Integer.parseInt((String) RegisterField.extractString(this.banco.getInitialPosition(), this.banco.getFinalPosition(), line)));
		this.lote.setValue(Integer.parseInt((String) RegisterField.extractString(this.lote.getInitialPosition(), this.lote.getFinalPosition(), line)));
		this.registro.setValue(Integer.parseInt((String) RegisterField.extractString(this.registro.getInitialPosition(), this.registro.getFinalPosition(), line)));
		this.numeroDeRegistroDeServico.setValue(Integer.parseInt((String) RegisterField.extractString(this.numeroDeRegistroDeServico.getInitialPosition(), this.numeroDeRegistroDeServico.getFinalPosition(), line)));
		this.segmentoDeServico.setValue((String)RegisterField.extractString(this.segmentoDeServico.getInitialPosition(), this.segmentoDeServico.getFinalPosition(), line));
		this.CNAB.setValue((String)RegisterField.extractString(this.CNAB.getInitialPosition(), this.CNAB.getFinalPosition(), line));
		this.tipoDeInscricao.setValue(Integer.parseInt((String) RegisterField.extractString(this.tipoDeInscricao.getInitialPosition(), this.tipoDeInscricao.getFinalPosition(), line)));
		this.numeroDeInscricao.setValue(Long.parseLong((String) RegisterField.extractString(this.numeroDeInscricao.getInitialPosition(), this.numeroDeInscricao.getFinalPosition(), line)));
		this.convenio.setValue((String)RegisterField.extractString(this.convenio.getInitialPosition(), this.convenio.getFinalPosition(), line));
		this.codigoAgencia.setValue(Integer.parseInt((String) RegisterField.extractString(this.codigoAgencia.getInitialPosition(), this.codigoAgencia.getFinalPosition(), line)));
		this.digitoVerificadorAgencia.setValue((String) RegisterField.extractString(this.digitoVerificadorAgencia.getInitialPosition(), this.digitoVerificadorAgencia.getFinalPosition(), line));
		this.numeroConta.setValue(Integer.parseInt((String) RegisterField.extractString(this.numeroConta.getInitialPosition(), this.numeroConta.getFinalPosition(), line)));
		this.digitoVerificadorConta.setValue((String) RegisterField.extractString(this.digitoVerificadorConta.getInitialPosition(), this.digitoVerificadorConta.getFinalPosition(), line));
		this.digitoVerificadorAgenciaConta.setValue((String) RegisterField.extractString(this.digitoVerificadorAgenciaConta.getInitialPosition(), this.digitoVerificadorAgenciaConta.getFinalPosition(), line));
		this.nomeDaEmpresa.setValue((String) RegisterField.extractString(this.nomeDaEmpresa.getInitialPosition(), this.nomeDaEmpresa.getFinalPosition(), line));
		this.natureza.setValue((String) RegisterField.extractString(this.natureza.getInitialPosition(), this.natureza.getFinalPosition(), line));
		this.CNAB2.setValue((String) RegisterField.extractString(this.CNAB2.getInitialPosition(), this.CNAB2.getFinalPosition(), line));
		//this.tipoDoComplemento.setValue(Integer.parseInt((String) RegisterField.extractString(this.tipoDoComplemento.getInitialPosition(), this.tipoDoComplemento.getFinalPosition(), line)));
//		this.tipoDoComplemento.setValue(RegisterField.extractString(this.tipoDoComplemento.getInitialPosition(), this.tipoDoComplemento.getFinalPosition(), line));
//		this.complemento.setValue((String) RegisterField.extractString(this.complemento.getInitialPosition(), this.complemento.getFinalPosition(), line));
//		this.cpmf.setValue((String) RegisterField.extractString(this.cpmf.getInitialPosition(), this.cpmf.getFinalPosition(), line));
//		this.dataContabil.setValue(RegisterField.extractString(this.dataContabil.getInitialPosition(), this.dataContabil.getFinalPosition(), line));
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy"); //$NON-NLS-1$
		SimpleDateFormat formatterLocal = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
		Date date = null;
		try {
			date = formatter.parse((String)RegisterField.extractString(this.dataDoLancamento.getInitialPosition(), this.dataDoLancamento.getFinalPosition(), line));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dataDoLancamento.setValue(formatterLocal.format(date));
		this.valorDoLancamento.setValue(Double.parseDouble((String)RegisterField.extractString(this.valorDoLancamento.getInitialPosition(), this.valorDoLancamento.getFinalPosition(), line))/100.0);
		this.tipoDoLancamento.setValue((String) RegisterField.extractString(this.tipoDoLancamento.getInitialPosition(), this.tipoDoLancamento.getFinalPosition(), line));
		this.categoriaDoLancamento.setValue(Integer.parseInt((String) RegisterField.extractString(this.categoriaDoLancamento.getInitialPosition(), this.categoriaDoLancamento.getFinalPosition(), line)));
		this.codigoHistoricoDoLancamento.setValue((String) RegisterField.extractString(this.codigoHistoricoDoLancamento.getInitialPosition(), this.codigoHistoricoDoLancamento.getFinalPosition(), line));
		this.historicoDoLancamento.setValue((String) RegisterField.extractString(this.historicoDoLancamento.getInitialPosition(), this.historicoDoLancamento.getFinalPosition(), line));
		this.numeroDoDocumento.setValue((String) RegisterField.extractString(this.numeroDoDocumento.getInitialPosition(), this.numeroDoDocumento.getFinalPosition(), line));
	}	
	public void showRegister()
	{	
		System.out.print(this.banco.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.lote.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.registro.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.numeroDeRegistroDeServico.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.segmentoDeServico.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.CNAB.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.tipoDeInscricao.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.numeroDeInscricao.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.convenio.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.codigoAgencia.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.digitoVerificadorAgencia.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.numeroConta.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.digitoVerificadorConta.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.digitoVerificadorAgenciaConta.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.nomeDaEmpresa.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.CNAB2.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.natureza.getValue().toString()+"\t"); //$NON-NLS-1$
//		System.out.print(this.tipoDoComplemento.getValue().toString()+"\t");
//		System.out.print(this.complemento.getValue().toString()+"\t");
//		System.out.print(this.cpmf.getValue().toString()+"\t");
//		System.out.print(this.dataContabil.getValue().toString()+"\t");
		System.out.print(this.dataDoLancamento.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.valorDoLancamento.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.tipoDoLancamento.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.categoriaDoLancamento.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.codigoHistoricoDoLancamento.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.historicoDoLancamento.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.numeroDoDocumento.getValue().toString()+"\n"); //$NON-NLS-1$
	}
	public RegisterField getBanco()
	{
		return this.banco;
	}
	public void setBanco(RegisterField banco)
	{
		this.banco = banco;
	}
	public RegisterField getLote()
	{
		return this.lote;
	}
	public void setLote(RegisterField lote)
	{
		this.lote = lote;
	}
	public RegisterField getRegistro()
	{
		return this.registro;
	}
	public void setRegistro(RegisterField registro)
	{
		this.registro = registro;
	}
	public RegisterField getCNAB()
	{
		return this.CNAB;
	}
	public void setCNAB(RegisterField cNAB)
	{
		this.CNAB = cNAB;
	}
	public RegisterField getTipoDeInscricao()
	{
		return this.tipoDeInscricao;
	}
	public void setTipoDeInscricao(RegisterField tipoDeInscricao)
	{
		this.tipoDeInscricao = tipoDeInscricao;
	}
	public RegisterField getNumeroDeInscricao()
	{
		return this.numeroDeInscricao;
	}
	public void setNumeroDeInscricao(RegisterField numeroDeInscricao)
	{
		this.numeroDeInscricao = numeroDeInscricao;
	}
	public RegisterField getConvenio()
	{
		return this.convenio;
	}
	public void setConvenio(RegisterField convenio)
	{
		this.convenio = convenio;
	}
	public RegisterField getCodigoAgencia()
	{
		return this.codigoAgencia;
	}
	public void setCodigoAgencia(RegisterField codigoAgencia)
	{
		this.codigoAgencia = codigoAgencia;
	}
	public RegisterField getDigitoVerificadorAgencia()
	{
		return this.digitoVerificadorAgencia;
	}
	public void setDigitoVerificadorAgencia(RegisterField digitoVerificadorAgencia)
	{
		this.digitoVerificadorAgencia = digitoVerificadorAgencia;
	}
	public RegisterField getNumeroConta()
	{
		return this.numeroConta;
	}
	public void setNumeroConta(RegisterField numeroConta)
	{
		this.numeroConta = numeroConta;
	}
	public RegisterField getDigitoVerificadorConta()
	{
		return this.digitoVerificadorConta;
	}
	public void setDigitoVerificadorConta(RegisterField digitoVerificadorConta)
	{
		this.digitoVerificadorConta = digitoVerificadorConta;
	}
	public RegisterField getDigitoVerificadorAgenciaConta()
	{
		return this.digitoVerificadorAgenciaConta;
	}
	public void setDigitoVerificadorAgenciaConta(RegisterField digitoVerificadorAgenciaConta)
	{
		this.digitoVerificadorAgenciaConta = digitoVerificadorAgenciaConta;
	}
	public RegisterField getNomeDaEmpresa()
	{
		return this.nomeDaEmpresa;
	}
	public void setNomeDaEmpresa(RegisterField nomeDaEmpresa)
	{
		this.nomeDaEmpresa = nomeDaEmpresa;
	}
	public RegisterField getCNAB2()
	{
		return this.CNAB2;
	}
	public void setCNAB2(RegisterField cNAB2)
	{
		this.CNAB2 = cNAB2;
	}
	public RegisterField getNumeroDeRegistroDeServico() {
		return this.numeroDeRegistroDeServico;
	}
	public void setNumeroDeRegistroDeServico(RegisterField numeroDeRegistroDeServico) {
		this.numeroDeRegistroDeServico = numeroDeRegistroDeServico;
	}
	public RegisterField getSegmentoDeServico() {
		return this.segmentoDeServico;
	}
	public void setSegmentoDeServico(RegisterField segmentoDeServico) {
		this.segmentoDeServico = segmentoDeServico;
	}
	public RegisterField getNatureza() {
		return this.natureza;
	}
	public void setNatureza(RegisterField natureza) {
		this.natureza = natureza;
	}
//	public RegisterField getTipoDoComplemento() {
//		return tipoDoComplemento;
//	}
//	public void setTipoDoComplemento(RegisterField tipoDoComplemento) {
//		this.tipoDoComplemento = tipoDoComplemento;
//	}
//	public RegisterField getComplemento() {
//		return complemento;
//	}
//	public void setComplemento(RegisterField complemento) {
//		this.complemento = complemento;
//	}
//	public RegisterField getCpmf() {
//		return cpmf;
//	}
//	public void setCpmf(RegisterField cpmf) {
//		this.cpmf = cpmf;
//	}
//	public RegisterField getDataContabil() {
//		return dataContabil;
//	}
//	public void setDataContabil(RegisterField dataContabil) {
//		this.dataContabil = dataContabil;
//	}
	public RegisterField getDataDoLancamento() {
		return this.dataDoLancamento;
	}
	public void setDataDoLancamento(RegisterField dataDoLancamento) {
		this.dataDoLancamento = dataDoLancamento;
	}
	public RegisterField getValorDoLancamento() {
		return this.valorDoLancamento;
	}
	public void setValorDoLancamento(RegisterField valorDoLancamento) {
		this.valorDoLancamento = valorDoLancamento;
	}
	public RegisterField getTipoDoLancamento() {
		return this.tipoDoLancamento;
	}
	public void setTipoDoLancamento(RegisterField tipoDoLancamento) {
		this.tipoDoLancamento = tipoDoLancamento;
	}
	public RegisterField getCategoriaDoLancamento() {
		return this.categoriaDoLancamento;
	}
	public void setCategoriaDoLancamento(RegisterField categoriaDoLancamento) {
		this.categoriaDoLancamento = categoriaDoLancamento;
	}
	public RegisterField getCodigoHistoricoDoLancamento() {
		return this.codigoHistoricoDoLancamento;
	}
	public void setCodigoHistoricoDoLancamento(RegisterField codigoHistoricoDoLancamento) {
		this.codigoHistoricoDoLancamento = codigoHistoricoDoLancamento;
	}
	public RegisterField getHistoricoDoLancamento() {
		return this.historicoDoLancamento;
	}
	public void setHistoricoDoLancamento(RegisterField historicoDoLancamento) {
		this.historicoDoLancamento = historicoDoLancamento;
	}
	public RegisterField getNumeroDoDocumento() {
		return this.numeroDoDocumento;
	}
	public void setNumeroDoDocumento(RegisterField numeroDoDocumento) {
		this.numeroDoDocumento = numeroDoDocumento;
	}	
}
