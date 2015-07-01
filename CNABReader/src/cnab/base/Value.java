package cnab.base;

public class Value 
{
    private Object object;

    public void set(Object object) 
    { 
    	this.object = object; 
    }
    
    public Object get() 
    { 
    	return object; 
    }
}