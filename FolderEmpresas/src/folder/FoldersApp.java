package folder;

public class FoldersApp 
{

	public FoldersApp() 
	{
	
	}

	public static void main(String[] args) 
	{
		HandlerFolders hf=new HandlerFolders();
		hf.createFolders();
		hf.addCreatedFolders();
		hf.removeCreateList();
	}

}
