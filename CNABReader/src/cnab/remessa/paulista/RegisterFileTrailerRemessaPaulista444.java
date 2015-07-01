package cnab.remessa.paulista;

import cnab.base.Register;
import cnab.base.RegisterField;

public class RegisterFileTrailerRemessaPaulista444  extends Register
{
	private RegisterField idDoRegistro = new RegisterField(1,1,"Identificação do registro","idDoRegistro");
	private RegisterField branco = new RegisterField(2,438,"Identificação do arquivo remessa","branco");
	private RegisterField seqDoRegistro = new RegisterField(439,444,"Literal Remessa","seqDoRegistro");

	public RegisterFileTrailerRemessaPaulista444()
	{
	}
	
	public RegisterFileTrailerRemessaPaulista444(String line)
	{
		this.idDoRegistro.setValue(Integer.parseInt((String) RegisterField.extractString(this.idDoRegistro.getInitialPosition(),this.idDoRegistro.getFinalPosition(),line)));
		this.branco.setValue((String) RegisterField.extractString(this.branco.getInitialPosition(),this.branco.getFinalPosition(),line));
		this.seqDoRegistro.setValue(Integer.parseInt((String) RegisterField.extractString(this.seqDoRegistro.getInitialPosition(),this.seqDoRegistro.getFinalPosition(),line)));
	}

	public String toCSV()
	{
		String stringCSV=
		this.idDoRegistro.getValue().toString()
		+";"+ this.branco.getValue().toString()
		+";"+this.seqDoRegistro.getValue().toString();
		return stringCSV;
	}
	
	public void showRegister()
	{
		System.out.print(this.idDoRegistro.getValue().toString()+"\t");
		System.out.print(this.branco.getValue().toString()+"\t");
		System.out.print(this.seqDoRegistro.getValue().toString()+"\n");
	}
	
	public RegisterField getIdDoRegistro() {
		return idDoRegistro;
	}

	public void setIdDoRegistro(RegisterField idDoRegistro) {
		this.idDoRegistro = idDoRegistro;
	}

	public RegisterField getBranco() {
		return branco;
	}

	public void setBranco(RegisterField branco) {
		this.branco = branco;
	}

	public RegisterField getSeqDoRegistro() {
		return seqDoRegistro;
	}

	public void setSeqDoRegistro(RegisterField seqDoRegistro) {
		this.seqDoRegistro = seqDoRegistro;
	}
}
