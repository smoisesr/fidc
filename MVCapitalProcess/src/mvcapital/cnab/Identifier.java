package mvcapital.cnab;

public class Identifier 
{
	private int initialPosition=1;
	private int finalPosition=1;
	private String identifierString=""; //$NON-NLS-1$
	private String identifierDescription=""; //$NON-NLS-1$
	
	public Identifier()
	{
		
	}
	 
	public Identifier(int initialPosition, int finalPosition, String identifierString, String identifierDescription)
	{
		this.initialPosition=initialPosition;
		this.finalPosition=finalPosition;
		this.identifierString=identifierString;
		this.identifierDescription=identifierDescription;
	}

	public int getInitialPosition() 
	{
		return this.initialPosition;
	}

	public void setInitialPosition(int initialPosition) 
	{
		this.initialPosition = initialPosition;
	}

	public int getFinalPosition() 
	{
		return this.finalPosition;
	}

	public void setFinalPosition(int finalPosition) 
	{
		this.finalPosition = finalPosition;
	}

	public String getIdentifierString() 
	{
		return this.identifierString;
	}

	public void setIdentifierString(String identifierString) 
	{
		this.identifierString = identifierString;
	}

	public String getIdentifierDescription() {
		return this.identifierDescription;
	}

	public void setIdentifierDescription(String identifierDescription) {
		this.identifierDescription = identifierDescription;
	}
}
