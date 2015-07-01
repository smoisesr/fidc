package organize;

import java.io.File;

import organize.petra.OrganizeCotistasPetra;
import organize.socopa.OrganizeCotistasSocopa;
import utils.Login;

import com.mysql.jdbc.Connection;

import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class OrganizeCotistas extends Organize 
{
	protected String rootCotistas = "";
	protected String rootCotistasProcessar = "";
	private String rootCotistasProcessado = "";
	private String rootCotistasProcessadoOriginal = "";

	public OrganizeCotistas()
	{
		super();
	}
	
	public OrganizeCotistas(FundoDeInvestimento fundo, int idEntidadeServidor, Login login, Connection conn)
	{
		super(fundo,idEntidadeServidor,login,conn);
		this.setFolders();
	}
	
	public void setFolders()
	{
		super.setFolders();
		this.rootCotistas = super.getRootLocalDir()+Organize.separator+super.getRootFundLocal()+Organize.separator+"Cotistas";
		this.rootCotistasProcessar = rootCotistas+Organize.separator+"Processar";
		this.rootCotistasProcessado = rootCotistas+Organize.separator+"Processado";
		this.rootCotistasProcessadoOriginal = rootCotistasProcessado+Organize.separator+"Original";	
		/*
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println("rootFundoLocal: " + super.getRootFundLocal());
		System.out.println("rootCotistas: " + this.rootCotistas);
		System.out.println("rootCotistasProcessar: " + this.rootCotistasProcessar);
		System.out.println("rootCotistasProcessado: " + this.rootCotistasProcessado);
		System.out.println("rootCotistasProcessadoOriginal: " + this.rootCotistasProcessadoOriginal);
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
				OrganizeCotistasPetra organizeCotistasPetra = new OrganizeCotistasPetra(this.fundo, super.idEntidadeServidor,this.login,this.conn);
				organizeCotistasPetra.organizeFile(file, folder);
				break;
			case "Socopa":
				OrganizeCotistasSocopa organizeCotistasSocopa = new OrganizeCotistasSocopa(this.fundo, super.idEntidadeServidor,this.login,this.conn);
				organizeCotistasSocopa.organizeFile(file, folder);
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
	
	public String getRootCotistas() {
		return rootCotistas;
	}

	public void setRootCotistas(String rootCotistas) {
		this.rootCotistas = rootCotistas;
	}

	public String getRootCotistasProcessar() {
		return rootCotistasProcessar;
	}

	public void setRootCotistasProcessar(String rootCotistasProcessar) {
		this.rootCotistasProcessar = rootCotistasProcessar;
	}

	public String getRootCotistasProcessado() {
		return rootCotistasProcessado;
	}

	public void setRootCotistasProcessado(String rootCotistasProcessado) {
		this.rootCotistasProcessado = rootCotistasProcessado;
	}

	public String getRootCotistasProcessadoOriginal() {
		return rootCotistasProcessadoOriginal;
	}

	public void setRootCotistasProcessadoOriginal(
			String rootCotistasProcessadoOriginal) {
		this.rootCotistasProcessadoOriginal = rootCotistasProcessadoOriginal;
	}
}
