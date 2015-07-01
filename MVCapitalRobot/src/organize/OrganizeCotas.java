package organize;

import java.io.File;

import organize.petra.OrganizeCotasPetra;
import organize.socopa.OrganizeCotasSocopa;
import utils.Login;

import com.mysql.jdbc.Connection;

import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class OrganizeCotas extends Organize 
{
	protected String rootCotas = "";
	protected String rootCotasProcessar = "";
	private String rootCotasProcessado = "";
	private String rootCotasProcessadoOriginal = "";

	public OrganizeCotas()
	{
		super();
	}
	
	public OrganizeCotas(FundoDeInvestimento fundo, int idEntidadeServidor, Login login, Connection conn)
	{
		super(fundo,idEntidadeServidor,login,conn);
		this.setFolders();
	}
	
	public void setFolders()
	{
		super.setFolders();
		this.rootCotas = super.getRootLocalDir()+Organize.separator+super.getRootFundLocal()+Organize.separator+"Cotas";
		this.rootCotasProcessar = rootCotas+Organize.separator+"Processar";
		this.rootCotasProcessado = rootCotas+Organize.separator+"Processado";
		this.rootCotasProcessadoOriginal = rootCotasProcessado+Organize.separator+"Original";	
		/*
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println("rootFundoLocal: " + super.getRootFundLocal());
		System.out.println("rootCotas: " + this.rootCotas);
		System.out.println("rootCotasProcessar: " + this.rootCotasProcessar);
		System.out.println("rootCotasProcessado: " + this.rootCotasProcessado);
		System.out.println("rootCotasProcessadoOriginal: " + this.rootCotasProcessadoOriginal);
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
				OrganizeCotasPetra organizeCotasPetra = new OrganizeCotasPetra(this.fundo, super.idEntidadeServidor,this.login,this.conn);
				organizeCotasPetra.organizeFile(file, folder);
				break;
			case "Socopa":
				OrganizeCotasSocopa organizeCotasSocopa = new OrganizeCotasSocopa(this.fundo, super.idEntidadeServidor,this.login,this.conn);
				organizeCotasSocopa.organizeFile(file, folder);
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
	
	public String getRootCotas() {
		return rootCotas;
	}

	public void setRootCotas(String rootCotas) {
		this.rootCotas = rootCotas;
	}

	public String getRootCotasProcessar() {
		return rootCotasProcessar;
	}

	public void setRootCotasProcessar(String rootCotasProcessar) {
		this.rootCotasProcessar = rootCotasProcessar;
	}

	public String getRootCotasProcessado() {
		return rootCotasProcessado;
	}

	public void setRootCotasProcessado(String rootCotasProcessado) {
		this.rootCotasProcessado = rootCotasProcessado;
	}

	public String getRootCotasProcessadoOriginal() {
		return rootCotasProcessadoOriginal;
	}

	public void setRootCotasProcessadoOriginal(
			String rootCotasProcessadoOriginal) {
		this.rootCotasProcessadoOriginal = rootCotasProcessadoOriginal;
	}
}
