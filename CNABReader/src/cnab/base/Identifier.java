package cnab.base;

public class Identifier 
{
	private int initialPosition=1;
	private int finalPosition=1;
	private String identifierString="";
	private String identifierDescription="";
	
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
		return initialPosition;
	}

	public void setInitialPosition(int initialPosition) 
	{
		this.initialPosition = initialPosition;
	}

	public int getFinalPosition() 
	{
		return finalPosition;
	}

	public void setFinalPosition(int finalPosition) 
	{
		this.finalPosition = finalPosition;
	}

	public String getIdentifierString() 
	{
		return identifierString;
	}

	public void setIdentifierString(String identifierString) 
	{
		this.identifierString = identifierString;
	}

	public String getIdentifierDescription() {
		return identifierDescription;
	}

	public void setIdentifierDescription(String identifierDescription) {
		this.identifierDescription = identifierDescription;
	}
}
