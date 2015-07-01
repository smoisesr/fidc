package organize;

import java.io.File;

import organize.petra.OrganizeCaixaPetra;

import utils.Login;

import com.mysql.jdbc.Connection;

import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class OrganizeCaixa extends Organize 
{
	protected String rootCaixa = "";
	protected String rootCaixaProcessar = "";
	private String rootCaixaProcessado = "";
	private String rootCaixaProcessadoOriginal = "";

	public OrganizeCaixa()
	{
		super();
	}
	
	public OrganizeCaixa(FundoDeInvestimento fundo, int idEntidadeServidor, Login login, Connection conn)
	{
		super(fundo,idEntidadeServidor,login,conn);
		this.setFolders();
	}
	
	public void setFolders()
	{
		super.setFolders();
		this.rootCaixa = super.getRootLocalDir()+Organize.separator+super.getRootFundLocal()+Organize.separator+"ContaCaixa";
		this.rootCaixaProcessar = rootCaixa+Organize.separator+"Processar";
		this.rootCaixaProcessado = rootCaixa+Organize.separator+"Processado";
		this.rootCaixaProcessadoOriginal = rootCaixaProcessado+Organize.separator+"Original";	
		/*
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println("rootFundoLocal: " + super.getRootFundLocal());
		System.out.println("rootCaixa: " + this.rootCaixa);
		System.out.println("rootCaixaProcessar: " + this.rootCaixaProcessar);
		System.out.println("rootCaixaProcessado: " + this.rootCaixaProcessado);
		System.out.println("rootCaixaProcessadoOriginal: " + this.rootCaixaProcessadoOriginal);
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
				OrganizeCaixaPetra organizeCaixa = new OrganizeCaixaPetra(this.fundo, super.idEntidadeServidor,this.login,this.conn);
				organizeCaixa.organizeFile(file, folder);
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
	
	public String getRootCaixa() {
		return rootCaixa;
	}

	public void setRootCaixa(String rootCaixa) {
		this.rootCaixa = rootCaixa;
	}

	public String getRootCaixaProcessar() {
		return rootCaixaProcessar;
	}

	public void setRootCaixaProcessar(String rootCaixaProcessar) {
		this.rootCaixaProcessar = rootCaixaProcessar;
	}

	public String getRootCaixaProcessado() {
		return rootCaixaProcessado;
	}

	public void setRootCaixaProcessado(String rootCaixaProcessado) {
		this.rootCaixaProcessado = rootCaixaProcessado;
	}

	public String getRootCaixaProcessadoOriginal() {
		return rootCaixaProcessadoOriginal;
	}

	public void setRootCaixaProcessadoOriginal(
			String rootCaixaProcessadoOriginal) {
		this.rootCaixaProcessadoOriginal = rootCaixaProcessadoOriginal;
	}
}
