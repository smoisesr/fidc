package organize;

import java.io.File;

import organize.petra.OrganizeCarteiraPetra;
import organize.socopa.OrganizeCarteiraSocopa;
import utils.Login;

import com.mysql.jdbc.Connection;

import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class OrganizeCarteira extends Organize 
{
	protected String rootCarteira = "";
	protected String rootCarteiraProcessar = "";
	private String rootCarteiraProcessado = "";
	private String rootCarteiraProcessadoOriginal = "";

	public OrganizeCarteira()
	{
		super();
	}
	
	public OrganizeCarteira(FundoDeInvestimento fundo, int idEntidadeServidor, Login login, Connection conn)
	{
		super(fundo,idEntidadeServidor,login,conn);
		this.setFolders();
	}
	
	public void setFolders()
	{
		super.setFolders();
		this.rootCarteira = super.getRootLocalDir()+Organize.separator+super.getRootFundLocal()+Organize.separator+"Carteira";
		this.rootCarteiraProcessar = rootCarteira+Organize.separator+"Processar";
		this.rootCarteiraProcessado = rootCarteira+Organize.separator+"Processado";
		this.rootCarteiraProcessadoOriginal = rootCarteiraProcessado+Organize.separator+"Original";	
		/*
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println("rootFundoLocal: " + super.getRootFundLocal());
		System.out.println("rootCarteira: " + this.rootCarteira);
		System.out.println("rootCarteiraProcessar: " + this.rootCarteiraProcessar);
		System.out.println("rootCarteiraProcessado: " + this.rootCarteiraProcessado);
		System.out.println("rootCarteiraProcessadoOriginal: " + this.rootCarteiraProcessadoOriginal);
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
				OrganizeCarteiraPetra organizeCarteiraPetra = new OrganizeCarteiraPetra(this.fundo, super.idEntidadeServidor,this.login,this.conn);
				organizeCarteiraPetra.organizeFile(file, folder);
				break;
			case "Socopa":
				OrganizeCarteiraSocopa organizeCarteiraPaulista = new OrganizeCarteiraSocopa(this.fundo, super.idEntidadeServidor,this.login,this.conn);
				organizeCarteiraPaulista.organizeFile(file, folder);
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
	
	public String getRootCarteira() {
		return rootCarteira;
	}

	public void setRootCarteira(String rootCarteira) {
		this.rootCarteira = rootCarteira;
	}

	public String getRootCarteiraProcessar() {
		return rootCarteiraProcessar;
	}

	public void setRootCarteiraProcessar(String rootCarteiraProcessar) {
		this.rootCarteiraProcessar = rootCarteiraProcessar;
	}

	public String getRootCarteiraProcessado() {
		return rootCarteiraProcessado;
	}

	public void setRootCarteiraProcessado(String rootCarteiraProcessado) {
		this.rootCarteiraProcessado = rootCarteiraProcessado;
	}

	public String getRootCarteiraProcessadoOriginal() {
		return rootCarteiraProcessadoOriginal;
	}

	public void setRootCarteiraProcessadoOriginal(
			String rootCarteiraProcessadoOriginal) {
		this.rootCarteiraProcessadoOriginal = rootCarteiraProcessadoOriginal;
	}
}
