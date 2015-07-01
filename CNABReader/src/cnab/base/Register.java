package cnab.base;

import java.util.ArrayList;

public class Register
{
	private ArrayList<RegisterField> fields = new ArrayList<RegisterField>();
	private RegisterIdentifyer identifyer = new RegisterIdentifyer(); 
	
	public Register ()
	{
		
	}

	public void showRegister()
	{
		
	}
	
	public ArrayList<RegisterField> getFields()
	{
		return fields;
	}

	public void setFields(ArrayList<RegisterField> fields)
	{
		this.fields = fields;
	}

	public RegisterIdentifyer getIdentifyer() {
		return identifyer;
	}

	public void setIdentifyer(RegisterIdentifyer identifyer) {
		this.identifyer = identifyer;
	}
}
