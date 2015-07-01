package utils;

public class DyadKeywordName {
	private String keyword="";
	private String name="";
	
	public DyadKeywordName()
	{
		
	}
	public DyadKeywordName(String keyword, String name)
	{
		this.keyword=keyword;
		this.name=name;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
