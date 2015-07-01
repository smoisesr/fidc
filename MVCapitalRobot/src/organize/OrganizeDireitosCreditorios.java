package organize;

import java.io.File;

import organize.petra.OrganizeDireitosCreditoriosPetra;
import organize.socopa.OrganizeDireitosCreditoriosSocopa;
import utils.Login;

import com.mysql.jdbc.Connection;

import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class OrganizeDireitosCreditorios extends Organize 
{
	protected String rootDireitosCreditorios = "";
	protected String rootDireitosCreditoriosProcessar = "";
	private String rootDireitosCreditoriosProcessado = "";
	private String rootDireitosCreditoriosProcessadoOriginal = "";

	public OrganizeDireitosCreditorios()
	{
		super();
	}
	
	public OrganizeDireitosCreditorios(FundoDeInvestimento fundo, int idEntidadeServidor, Login login, Connection conn)
	{
		super(fundo,idEntidadeServidor,login,conn);
		this.setFolders();
	}
	
	public void setFolders()
	{
		super.setFolders();
		this.rootDireitosCreditorios = super.getRootLocalDir()+Organize.separator+super.getRootFundLocal()+Organize.separator+"DireitosCreditorios";
		this.rootDireitosCreditoriosProcessar = rootDireitosCreditorios+Organize.separator+"Processar";
		this.rootDireitosCreditoriosProcessado = rootDireitosCreditorios+Organize.separator+"Processado";
		this.rootDireitosCreditoriosProcessadoOriginal = rootDireitosCreditoriosProcessado+Organize.separator+"Original";	
		/*
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println("rootFundoLocal: " + super.getRootFundLocal());
		System.out.println("rootDireitosCreditorios: " + this.rootDireitosCreditorios);
		System.out.println("rootDireitosCreditoriosProcessar: " + this.rootDireitosCreditoriosProcessar);
		System.out.println("rootDireitosCreditoriosProcessado: " + this.rootDireitosCreditoriosProcessado);
		System.out.println("rootDireitosCreditoriosProcessadoOriginal: " + this.rootDireitosCreditoriosProcessadoOriginal);
		System.out.println("**********************************");
		System.out.println("**********************************");
		*/
	}
	
	public void organizeFile(File file, File folder)
	{
		this.chooseOrganizeByEntidade(this.getEntidadeServidor(), file, folder);
	}
	
	public void chooseOrganizeByEntidade(Entidade entidade, File file, File folder)
	{
		switch (entidade.getNomeCurto())
		{
			case "Petra":
				OrganizeDireitosCreditoriosPetra organizeDireitosCreditoriosPetra = new OrganizeDireitosCreditoriosPetra(this.fundo, super.idEntidadeServidor,this.login,this.conn);
				organizeDireitosCreditoriosPetra.organizeFile(file, folder);
				break;
			case "Socopa":
				OrganizeDireitosCreditoriosSocopa organizeDireitosCreditoriosSocopa = new OrganizeDireitosCreditoriosSocopa(this.fundo, super.idEntidadeServidor,this.login,this.conn);
				organizeDireitosCreditoriosSocopa.organizeFile(file, folder);
				break;
			default:
				break;
		}

	}
	
	public Entidade getEntidadeServidor()
	{
		Entidade entidade=null;
		entidade = new Entidade(super.login.getIdEntidadeServidor(), super.conn);
		return entidade;
	}
	
	public String getRootDireitosCreditorios() {
		return rootDireitosCreditorios;
	}

	public void setRootDireitosCreditorios(String rootDireitosCreditorios) {
		this.rootDireitosCreditorios = rootDireitosCreditorios;
	}

	public String getRootDireitosCreditoriosProcessar() {
		return rootDireitosCreditoriosProcessar;
	}

	public void setRootDireitosCreditoriosProcessar(String rootDireitosCreditoriosProcessar) {
		this.rootDireitosCreditoriosProcessar = rootDireitosCreditoriosProcessar;
	}

	public String getRootDireitosCreditoriosProcessado() {
		return rootDireitosCreditoriosProcessado;
	}

	public void setRootDireitosCreditoriosProcessado(String rootDireitosCreditoriosProcessado) {
		this.rootDireitosCreditoriosProcessado = rootDireitosCreditoriosProcessado;
	}

	public String getRootDireitosCreditoriosProcessadoOriginal() {
		return rootDireitosCreditoriosProcessadoOriginal;
	}

	public void setRootDireitosCreditoriosProcessadoOriginal(
			String rootDireitosCreditoriosProcessadoOriginal) {
		this.rootDireitosCreditoriosProcessadoOriginal = rootDireitosCreditoriosProcessadoOriginal;
	}
}
