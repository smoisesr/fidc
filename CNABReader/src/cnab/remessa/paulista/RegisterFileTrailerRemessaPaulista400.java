package cnab.remessa.paulista;

import cnab.base.Register;
import cnab.base.RegisterField;

public class RegisterFileTrailerRemessaPaulista400  extends Register
{
	private RegisterField idDoRegistro = new RegisterField(1,1,"Identificação do registro","idDoRegistro"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField branco = new RegisterField(2,394,"Identificação do arquivo remessa","branco"); //$NON-NLS-1$ //$NON-NLS-2$
	private RegisterField seqDoRegistro = new RegisterField(395,400,"Literal Remessa","seqDoRegistro"); //$NON-NLS-1$ //$NON-NLS-2$

	public RegisterFileTrailerRemessaPaulista400()
	{
	}
	
	public RegisterFileTrailerRemessaPaulista400(String line)
	{
		this.idDoRegistro.setValue(Integer.parseInt((String) RegisterField.extractString(this.idDoRegistro.getInitialPosition(),this.idDoRegistro.getFinalPosition(),line)));
		this.branco.setValue((String) RegisterField.extractString(this.branco.getInitialPosition(),this.branco.getFinalPosition(),line));
		this.seqDoRegistro.setValue(Integer.parseInt((String) RegisterField.extractString(this.seqDoRegistro.getInitialPosition(),this.seqDoRegistro.getFinalPosition(),line)));
	}

	public String toCSV()
	{
		String stringCSV=
		this.idDoRegistro.getValue().toString()
		+";"+ this.branco.getValue().toString() //$NON-NLS-1$
		+";"+this.seqDoRegistro.getValue().toString(); //$NON-NLS-1$
		return stringCSV;
	}
	
	public void showRegister()
	{
		System.out.print(this.idDoRegistro.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.branco.getValue().toString()+"\t"); //$NON-NLS-1$
		System.out.print(this.seqDoRegistro.getValue().toString()+"\n"); //$NON-NLS-1$
	}
	
	public RegisterField getIdDoRegistro() {
		return this.idDoRegistro;
	}

	public void setIdDoRegistro(RegisterField idDoRegistro) {
		this.idDoRegistro = idDoRegistro;
	}

	public RegisterField getBranco() {
		return this.branco;
	}

	public void setBranco(RegisterField branco) {
		this.branco = branco;
	}

	public RegisterField getSeqDoRegistro() {
		return this.seqDoRegistro;
	}

	public void setSeqDoRegistro(RegisterField seqDoRegistro) {
		this.seqDoRegistro = seqDoRegistro;
	}
}
