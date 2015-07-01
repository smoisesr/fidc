package mvcapital.bradesco.cnab.cobranca.retorno;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Header extends Register 
{
	private Campo identificacaoArquivoRetorno=new Campo();
	private Campo literalRetorno=new Campo();
	private Campo codigoServico=new Campo();
	private Campo literalServico=new Campo();
	private Campo codigoEmpresa=new Campo();
	private Campo nomeEmpresa=new Campo();
	private Campo numeroBancoCamaraCompensacao=new Campo();
	private Campo nomeBanco=new Campo();
	private Campo dataGravacaoArquivo=new Campo();
	private Campo densidadeGravacao=new Campo();
	private Campo numeroAvisoBancario=new Campo();
	private Campo branco=new Campo();
	private Campo dataCredito=new Campo();
	private Campo branco1=new Campo();
	
	
	public Header() 
	{
		super();
//		private int numero=0;
//		private int posicaoInicial=0;
//		private int posicaoFinal=0;
//		private int tamanho=0;
//		private boolean obrigatorio=true;
//		private TipoCampo tipo=new TipoCampo();
//		private int decimais=0;
//		private String conteudo="";		
		this.identificacaoRegistro.setConteudo("0"); //$NON-NLS-1$
		this.identificacaoArquivoRetorno=new Campo(2,2,2,1,true,Register.campoNumerico,0,"2"); //$NON-NLS-1$
		this.literalRetorno=new Campo(3,3,9,7,true,Register.campoAlfaNumerico,0,"RETORNO"); //$NON-NLS-1$
		this.codigoServico=new Campo(4,10,11,2,true,Register.campoNumerico,0,"01"); //$NON-NLS-1$
		this.literalServico=new Campo(5,12,26,15,true,Register.campoAlfaNumerico,0,"COBRANCA"); //$NON-NLS-1$
		this.codigoEmpresa=new Campo(6,27,46,20,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.nomeEmpresa=new Campo(7,47,76,30,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.numeroBancoCamaraCompensacao=new Campo(8,77,79,3,true,Register.campoNumerico,0,"237"); //$NON-NLS-1$
		this.nomeBanco=new Campo(9,80,94,15,true,Register.campoAlfaNumerico,0,"BRADESCO"); //$NON-NLS-1$
		this.dataGravacaoArquivo=new Campo(10,95,100,6,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.densidadeGravacao=new Campo(11,101,108,8,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.numeroAvisoBancario=new Campo(12,109,113,5,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.branco=new Campo(13,114,379,266,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.dataCredito=new Campo(14,380,385,6,true,Register.campoNumerico,0,""); //$NON-NLS-1$
		this.branco1=new Campo(15,386,394,9,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.numeroSequencialRegistro=new Campo(16,395,400,6,true,Register.campoNumerico,0,""); //$NON-NLS-1$
	}


	public Campo getIdentificacaoRegistro()
	{
		return this.identificacaoRegistro;
	}


	public void setIdentificacaoRegistro(Campo identificacaoRegistro)
	{
		this.identificacaoRegistro = identificacaoRegistro;
	}


	public Campo getIdentificacaoArquivoRetorno()
	{
		return this.identificacaoArquivoRetorno;
	}


	public void setIdentificacaoArquivoRetorno(Campo identificacaoArquivoRetorno)
	{
		this.identificacaoArquivoRetorno = identificacaoArquivoRetorno;
	}


	public Campo getLiteralRetorno()
	{
		return this.literalRetorno;
	}


	public void setLiteralRetorno(Campo literalRetorno)
	{
		this.literalRetorno = literalRetorno;
	}


	public Campo getCodigoServico()
	{
		return this.codigoServico;
	}


	public void setCodigoServico(Campo codigoServico)
	{
		this.codigoServico = codigoServico;
	}


	public Campo getLiteralServico()
	{
		return this.literalServico;
	}


	public void setLiteralServico(Campo literalServico)
	{
		this.literalServico = literalServico;
	}


	public Campo getCodigoEmpresa()
	{
		return this.codigoEmpresa;
	}


	public void setCodigoEmpresa(Campo codigoEmpresa)
	{
		this.codigoEmpresa = codigoEmpresa;
	}


	public Campo getNomeEmpresa()
	{
		return this.nomeEmpresa;
	}


	public void setNomeEmpresa(Campo nomeEmpresa)
	{
		this.nomeEmpresa = nomeEmpresa;
	}


	public Campo getNumeroBancoCamaraCompensacao()
	{
		return this.numeroBancoCamaraCompensacao;
	}


	public void setNumeroBancoCamaraCompensacao(Campo numeroBancoCamaraCompensacao)
	{
		this.numeroBancoCamaraCompensacao = numeroBancoCamaraCompensacao;
	}


	public Campo getNomeBanco()
	{
		return this.nomeBanco;
	}


	public void setNomeBanco(Campo nomeBanco)
	{
		this.nomeBanco = nomeBanco;
	}


	public Campo getDataGravacaoArquivo()
	{
		return this.dataGravacaoArquivo;
	}


	public void setDataGravacaoArquivo(Campo dataGravacaoArquivo)
	{
		this.dataGravacaoArquivo = dataGravacaoArquivo;
	}


	public Campo getDensidadeGravacao()
	{
		return this.densidadeGravacao;
	}


	public void setDensidadeGravacao(Campo densidadeGravacao)
	{
		this.densidadeGravacao = densidadeGravacao;
	}


	public Campo getNumeroAvisoBancario()
	{
		return this.numeroAvisoBancario;
	}


	public void setNumeroAvisoBancario(Campo numeroAvisoBancario)
	{
		this.numeroAvisoBancario = numeroAvisoBancario;
	}


	public Campo getBranco()
	{
		return this.branco;
	}


	public void setBranco(Campo branco)
	{
		this.branco = branco;
	}


	public Campo getDataCredito()
	{
		return this.dataCredito;
	}


	public void setDataCredito(Campo dataCredito)
	{
		this.dataCredito = dataCredito;
	}


	public Campo getBranco1()
	{
		return this.branco1;
	}


	public void setBranco1(Campo branco1)
	{
		this.branco1 = branco1;
	}

}
