package cnab;

public class Field 
{
	private String name="";
	private int begin=0;
	private int end=0;
	private int size=0;
	private boolean required=false;
	private KindOfField kindOfField=new KindOfField();
	private String defaultContent="";
	
	public Field() 
	{
	
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getBegin() {
		return begin;
	}


	public void setBegin(int begin) {
		this.begin = begin;
	}


	public int getEnd() {
		return end;
	}


	public void setEnd(int end) {
		this.end = end;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public boolean isRequired() {
		return required;
	}


	public void setRequired(boolean required) {
		this.required = required;
	}


	public KindOfField getKindOfField() {
		return kindOfField;
	}


	public void setKindOfField(KindOfField kindOfField) {
		this.kindOfField = kindOfField;
	}


	public String getDefaultContent() {
		return defaultContent;
	}


	public void setDefaultContent(String defaultContent) {
		this.defaultContent = defaultContent;
	}

}
