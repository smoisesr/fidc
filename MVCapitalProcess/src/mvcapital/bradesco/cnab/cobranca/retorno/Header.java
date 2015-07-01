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
	
	public Header(String line)
	{
		this();
		this.identificacaoArquivoRetorno.setConteudo(line.substring(this.identificacaoArquivoRetorno.getPosicaoInicial()-1,this.identificacaoArquivoRetorno.getPosicaoFinal()));
		this.literalRetorno.setConteudo(line.substring(this.literalRetorno.getPosicaoInicial()-1,this.literalRetorno.getPosicaoFinal()));
		this.codigoServico.setConteudo(line.substring(this.codigoServico.getPosicaoInicial()-1,this.codigoServico.getPosicaoFinal()));
		this.literalServico.setConteudo(line.substring(this.literalServico.getPosicaoInicial()-1,this.literalServico.getPosicaoFinal()));
		this.codigoEmpresa.setConteudo(line.substring(this.codigoEmpresa.getPosicaoInicial()-1,this.codigoEmpresa.getPosicaoFinal()));
		this.nomeEmpresa.setConteudo(line.substring(this.nomeEmpresa.getPosicaoInicial()-1,this.nomeEmpresa.getPosicaoFinal()));
		this.numeroBancoCamaraCompensacao.setConteudo(line.substring(this.numeroBancoCamaraCompensacao.getPosicaoInicial()-1,this.numeroBancoCamaraCompensacao.getPosicaoFinal()));
		this.nomeBanco.setConteudo(line.substring(this.nomeBanco.getPosicaoInicial()-1,this.nomeBanco.getPosicaoFinal()));
		this.dataGravacaoArquivo.setConteudo(line.substring(this.dataGravacaoArquivo.getPosicaoInicial()-1,this.dataGravacaoArquivo.getPosicaoFinal()));
		this.densidadeGravacao.setConteudo(line.substring(this.densidadeGravacao.getPosicaoInicial()-1,this.densidadeGravacao.getPosicaoFinal()));
		this.numeroAvisoBancario.setConteudo(line.substring(this.numeroAvisoBancario.getPosicaoInicial()-1,this.numeroAvisoBancario.getPosicaoFinal()));
		this.branco.setConteudo(line.substring(this.branco.getPosicaoInicial()-1,this.branco.getPosicaoFinal()));
		this.dataCredito.setConteudo(line.substring(this.dataCredito.getPosicaoInicial()-1,this.dataCredito.getPosicaoFinal()));
		this.branco1.setConteudo(line.substring(this.branco1.getPosicaoInicial()-1,this.branco1.getPosicaoFinal()));
		this.numeroSequencialRegistro.setConteudo(line.substring(this.numeroSequencialRegistro.getPosicaoInicial()-1,this.numeroSequencialRegistro.getPosicaoFinal()));
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.identificacaoRegistro.getConteudo());
		sb.append(this.identificacaoArquivoRetorno.getConteudo());
		sb.append(this.literalRetorno.getConteudo());
		sb.append(this.codigoServico.getConteudo());
		sb.append(this.literalServico.getConteudo());
		sb.append(this.codigoEmpresa.getConteudo());
		sb.append(this.nomeEmpresa.getConteudo());
		sb.append(this.numeroBancoCamaraCompensacao.getConteudo());
		sb.append(this.nomeBanco.getConteudo());
		sb.append(this.dataGravacaoArquivo.getConteudo());
		sb.append(this.densidadeGravacao.getConteudo());
		sb.append(this.numeroAvisoBancario.getConteudo());
		sb.append(this.branco.getConteudo());
		sb.append(this.dataCredito.getConteudo());
		sb.append(this.branco1.getConteudo());
		sb.append(this.numeroSequencialRegistro.getConteudo());
		return sb.toString();
	}

	public String toCSV()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.identificacaoRegistro.getConteudo());
		sb.append(";"+this.identificacaoArquivoRetorno.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.literalRetorno.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.codigoServico.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.literalServico.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.codigoEmpresa.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.nomeEmpresa.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroBancoCamaraCompensacao.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.nomeBanco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.dataGravacaoArquivo.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.densidadeGravacao.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroAvisoBancario.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.branco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.dataCredito.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.branco1.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroSequencialRegistro.getConteudo()); //$NON-NLS-1$
		return sb.toString();
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
