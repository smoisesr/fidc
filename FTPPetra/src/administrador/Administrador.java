package administrador;

/**
 * @author MVCapital - Moisés
 * Class to define the fund administrator
 */
public class Administrador 
{
	private String name = "";
	private String dirFiles = "";
	
	/**
	 * 
	 * @author MVCapital
	 * Lista de administradores cadastrados
	 *
	 */
	public enum Lista {Petra};

	public Administrador()
	{
		
	}
	/**
	 * 
	 * @param name
	 */
	public Administrador(String name)
	{
		this.setName(name);
	}

	/**
	 * 
	 * @param name: administrator name, like "Petra"
	 * @param nameFolderFiles: path to files after root, for "Petra" the value is "RECEBER"
	 */
	public Administrador(String name, String nameFolderFiles)
	{
		this.name = name;
		this.dirFiles = nameFolderFiles;
	}
	
	public Administrador(Lista name, String nameFolderFiles)
	{
		this.name = name.toString();
		this.dirFiles = nameFolderFiles;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getDirFiles()
	{
		return dirFiles;
	}
	public void setDirFiles(String dirFiles)
	{
		this.dirFiles = dirFiles;
	}

}
