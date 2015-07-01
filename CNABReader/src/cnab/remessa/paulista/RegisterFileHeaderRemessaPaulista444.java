package cnab.remessa.paulista;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cnab.base.Register;
import cnab.base.RegisterField;

public class RegisterFileHeaderRemessaPaulista444 extends Register
{

	private RegisterField idDoRegistro = new RegisterField(1,1,"Identificação do registro","idDoRegistro"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField idDoArquivoRemessa = new RegisterField(2,2,"Identificação do arquivo remessa","idDoArquivoRemessa"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField literalRemessa = new RegisterField(3,9,"Literal Remessa","literalRemessa"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField codigoDeServico = new RegisterField(10,11,"Código de Serviço","codigoDeServico"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField literalServico = new RegisterField(12,26,"Literal Servico","literalServico"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField codigoDoOriginador = new RegisterField(27,46,"Codigo do originador","codigoDoOriginador"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField nomeDoOriginador = new RegisterField(47,76,"Nome do originador","nomeDoOriginador"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField numeroDoBancoPaulista = new RegisterField(77,79,"Numero do banco paulista","numeroDoBancoPaulista"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField nomeDoBanco = new RegisterField(80,94,"Nome do banco","nomeDoBanco"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField dataDaGravacaoDoArquivo = new RegisterField(95,100,"Data da gravacao do arquivo","dataDaGravacaoDoArquivo"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField branco = new RegisterField(101,108,"Branco ","branco"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField idDoSistema = new RegisterField(109,110,"Identificação do sistema","idDoSistema"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField seqDoArquivo = new RegisterField(111,117,"Número Sequencial do arquivo","seqDoArquivo"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField branco2 = new RegisterField(118,438,"Branco ","branco2"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField seqDoRegistro = new RegisterField(439,444,"Número Sequencial do registro","seqDoRegistro"); //$NON-NLS-1$ //$NON-NLS-2$
	
	public RegisterFileHeaderRemessaPaulista444()
	{
		
	}
	public RegisterFileHeaderRemessaPaulista444(String line)
	{
		this.idDoRegistro.setValue(Integer.parseInt((String) RegisterField.extractString(this.idDoRegistro.getInitialPosition(),this.idDoRegistro.getFinalPosition(),line)));
		this.idDoArquivoRemessa.setValue(Integer.parseInt((String) RegisterField.extractString(this.idDoArquivoRemessa.getInitialPosition(),this.idDoArquivoRemessa.getFinalPosition(),line)));
		this.literalRemessa.setValue((String) RegisterField.extractString(this.literalRemessa.getInitialPosition(),this.literalRemessa.getFinalPosition(),line));
		this.codigoDeServico.setValue(Integer.parseInt((String) RegisterField.extractString(this.codigoDeServico.getInitialPosition(),this.codigoDeServico.getFinalPosition(),line)));
		this.literalServico.setValue((String) RegisterField.extractString(this.literalServico.getInitialPosition(),this.literalServico.getFinalPosition(),line));
		this.codigoDoOriginador.setValue(Integer.parseInt((String) RegisterField.extractString(this.codigoDoOriginador.getInitialPosition(),this.codigoDoOriginador.getFinalPosition(),line)));
		this.nomeDoOriginador.setValue((String) RegisterField.extractString(this.nomeDoOriginador.getInitialPosition(),this.nomeDoOriginador.getFinalPosition(),line));
		this.numeroDoBancoPaulista.setValue(Integer.parseInt((String) RegisterField.extractString(this.numeroDoBancoPaulista.getInitialPosition(),this.numeroDoBancoPaulista.getFinalPosition(),line)));
		this.nomeDoBanco.setValue((String) RegisterField.extractString(this.nomeDoBanco.getInitialPosition(),this.nomeDoBanco.getFinalPosition(),line));
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy"); //$NON-NLS-1$
		SimpleDateFormat formatterLocal = new SimpleDateFormat("dd/MM/yy"); //$NON-NLS-1$
		Date date = null;
		try {
			date = formatter.parse((String) RegisterField.extractString(this.dataDaGravacaoDoArquivo.getInitialPosition(),this.dataDaGravacaoDoArquivo.getFinalPosition(),line));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.dataDaGravacaoDoArquivo.setValue(formatterLocal.format(date));
		this.branco.setValue((String) RegisterField.extractString(this.branco.getInitialPosition(),this.branco.getFinalPosition(),line));
		this.idDoSistema.setValue((String) RegisterField.extractString(this.idDoSistema.getInitialPosition(),this.idDoSistema.getFinalPosition(),line));
		this.seqDoArquivo.setValue(Integer.parseInt((String) RegisterField.extractString(this.seqDoArquivo.getInitialPosition(),this.seqDoArquivo.getFinalPosition(),line)));
		this.branco2.setValue((String) RegisterField.extractString(this.branco2.getInitialPosition(),this.branco2.getFinalPosition(),line));
		this.seqDoRegistro.setValue(Integer.parseInt((String) RegisterField.extractString(this.seqDoRegistro.getInitialPosition(),this.seqDoRegistro.getFinalPosition(),line)));
	}
	public String toCSV()
	{
		String stringCSV=
				this.idDoRegistro.getValue().toString()
				+ ";" + this.idDoArquivoRemessa.getValue().toString() //$NON-NLS-1$
				+ ";" + this.literalRemessa.getValue().toString() //$NON-NLS-1$
				+ ";" + this.codigoDeServico.getValue().toString() //$NON-NLS-1$
				+ ";" + this.literalServico.getValue().toString() //$NON-NLS-1$
				+ ";" + this.codigoDoOriginador.getValue().toString() //$NON-NLS-1$
				+ ";" + this.nomeDoOriginador.getValue().toString() //$NON-NLS-1$
				+ ";" + this.numeroDoBancoPaulista.getValue().toString() //$NON-NLS-1$
				+ ";" + this.nomeDoBanco.getValue().toString() //$NON-NLS-1$
				+ ";" + this.dataDaGravacaoDoArquivo.getValue().toString() //$NON-NLS-1$
				+ ";" + this.branco.getValue().toString() //$NON-NLS-1$
				+ ";" + this.idDoSistema.getValue().toString() //$NON-NLS-1$
				+ ";" + this.seqDoArquivo.getValue().toString() //$NON-NLS-1$
				+ ";" + this.branco2.getValue().toString() //$NON-NLS-1$
				+ ";" + this.seqDoRegistro.getValue().toString(); //$NON-NLS-1$
		return stringCSV;
	}
	public void showRegister()
	{
		System.out.print(this.idDoRegistro.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.idDoArquivoRemessa.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.literalRemessa.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.codigoDeServico.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.literalServico.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.codigoDoOriginador.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.nomeDoOriginador.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.numeroDoBancoPaulista.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.nomeDoBanco.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.dataDaGravacaoDoArquivo.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.branco.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.idDoSistema.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.seqDoArquivo.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.branco2.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.seqDoRegistro.getValue().toString()+"\n"); //$NON-NLS-1$
	}
	public RegisterField getIdDoRegistro() {
		return this.idDoRegistro;
	}
	public void setIdDoRegistro(RegisterField idDoRegistro) {
		this.idDoRegistro = idDoRegistro;
	}
	public RegisterField getIdDoArquivoRemessa() {
		return this.idDoArquivoRemessa;
	}
	public void setIdDoArquivoRemessa(RegisterField idDoArquivoRemessa) {
		this.idDoArquivoRemessa = idDoArquivoRemessa;
	}
	public RegisterField getLiteralRemessa() {
		return this.literalRemessa;
	}
	public void setLiteralRemessa(RegisterField literalRemessa) {
		this.literalRemessa = literalRemessa;
	}
	public RegisterField getCodigoDeServico() {
		return this.codigoDeServico;
	}
	public void setCodigoDeServico(RegisterField codigoDeServico) {
		this.codigoDeServico = codigoDeServico;
	}
	public RegisterField getLiteralServico() {
		return this.literalServico;
	}
	public void setLiteralServico(RegisterField literalServico) {
		this.literalServico = literalServico;
	}
	public RegisterField getCodigoDoOriginador() {
		return this.codigoDoOriginador;
	}
	public void setCodigoDoOriginador(RegisterField codigoDoOriginador) {
		this.codigoDoOriginador = codigoDoOriginador;
	}
	public RegisterField getNomeDoOriginador() {
		return this.nomeDoOriginador;
	}
	public void setNomeDoOriginador(RegisterField nomeDoOriginador) {
		this.nomeDoOriginador = nomeDoOriginador;
	}
	public RegisterField getNumeroDoBancoPaulista() {
		return this.numeroDoBancoPaulista;
	}
	public void setNumeroDoBancoPaulista(RegisterField numeroDoBancoPaulista) {
		this.numeroDoBancoPaulista = numeroDoBancoPaulista;
	}
	public RegisterField getNomeDoBanco() {
		return this.nomeDoBanco;
	}
	public void setNomeDoBanco(RegisterField nomeDoBanco) {
		this.nomeDoBanco = nomeDoBanco;
	}
	public RegisterField getDataDaGravacaoDoArquivo() {
		return this.dataDaGravacaoDoArquivo;
	}
	public void setDataDaGravacaoDoArquivo(RegisterField dataDaGravacaoDoArquivo) {
		this.dataDaGravacaoDoArquivo = dataDaGravacaoDoArquivo;
	}
	public RegisterField getBranco() {
		return this.branco;
	}
	public void setBranco(RegisterField branco) {
		this.branco = branco;
	}
	public RegisterField getIdDoSistema() {
		return this.idDoSistema;
	}
	public void setIdDoSistema(RegisterField idDoSistema) {
		this.idDoSistema = idDoSistema;
	}
	public RegisterField getSeqDoArquivo() {
		return this.seqDoArquivo;
	}
	public void setSeqDoArquivo(RegisterField seqDoArquivo) {
		this.seqDoArquivo = seqDoArquivo;
	}
	public RegisterField getBranco2() {
		return this.branco2;
	}
	public void setBranco2(RegisterField branco2) {
		this.branco2 = branco2;
	}
	public RegisterField getSeqDoRegistro() {
		return this.seqDoRegistro;
	}
	public void setSeqDoRegistro(RegisterField seqDoRegistro) {
		this.seqDoRegistro = seqDoRegistro;
	}


}
