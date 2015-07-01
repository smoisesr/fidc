package cnab;

public class Register 
{
	private KindOfRegister kindOfRegister = new KindOfRegister();
	
	public Register() 
	{
	
	}

	public KindOfRegister getKindOfRegister() {
		return kindOfRegister;
	}

	public void setKindOfRegister(KindOfRegister kindOfRegister) {
		this.kindOfRegister = kindOfRegister;
	}
}
