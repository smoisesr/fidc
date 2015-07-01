package cnab.base;

public class RegisterField
{
	private String fieldString=""; //$NON-NLS-1$
	private String name=""; //$NON-NLS-1$
	private int initialPosition=1;
	private int finalPosition=1;
	private String description=""; //$NON-NLS-1$
	private Object value;

	/**
	 * @param initialPosition
	 * @param finalPosition
	 * @param name
	 * @param description
	 */
	public RegisterField (int initialPosition, int finalPosition, String description, String name)
	{
		this.initialPosition = initialPosition;
		this.finalPosition = finalPosition;
		this.description = description;
		this.name = name;
	}
	
	public static Object extractString(int initialPosition, int finalPosition, String line)
	{
		return line.substring(initialPosition-1, finalPosition);
	}
	
	public String getFieldString()
	{		
		return this.fieldString;
	}
	public void setFieldString(String fieldString)
	{
		this.fieldString = fieldString;
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
	public String getDescription()
	{
		return this.description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Object getValue()
	{
		return this.value;
	}
	public void setValue(Object object)
	{
		this.value = object;
		this.fieldString = this.value.toString();
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
