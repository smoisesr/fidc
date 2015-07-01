package mvcapital.bradesco.cnab.cheque.protocolo.host;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Detail extends Register
{
	private Campo agenciaBradesco = new Campo();
	private Campo contaBradesco = new Campo();
	private Campo filial = new Campo();
	private Campo nomeDoArquivo = new Campo();
	private Campo naoProtocolo = new Campo();
	private Campo brancos = new Campo();
	public Detail() 
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
		this.agenciaBradesco = new Campo(1,1,5,5,true,Register.campoNumerico,0,"");
		this.contaBradesco = new Campo(2,6,13,8,true,Register.campoNumerico,0,"");
		this.filial = new Campo(3,14,17,4,true,Register.campoNumerico,0,"");
		this.nomeDoArquivo = new Campo(4,18,57,40,true,Register.campoNumerico,0,"");
		this.naoProtocolo = new Campo(5,58,77,20,true,Register.campoNumerico,0,"");
		this.brancos = new Campo(6,78,130,53,true,Register.campoAlfaNumerico,0,"");
		this.numeroSequencialRegistro = new Campo(7,131,140,10,true,Register.campoNumerico,0,"");
	}
	public Campo getAgenciaBradesco()
	{
		return agenciaBradesco;
	}
	public void setAgenciaBradesco(Campo agenciaBradesco)
	{
		this.agenciaBradesco = agenciaBradesco;
	}
	public Campo getContaBradesco()
	{
		return contaBradesco;
	}
	public void setContaBradesco(Campo contaBradesco)
	{
		this.contaBradesco = contaBradesco;
	}
	public Campo getFilial()
	{
		return filial;
	}
	public void setFilial(Campo filial)
	{
		this.filial = filial;
	}
	public Campo getNomeDoArquivo()
	{
		return nomeDoArquivo;
	}
	public void setNomeDoArquivo(Campo nomeDoArquivo)
	{
		this.nomeDoArquivo = nomeDoArquivo;
	}
	public Campo getNaoProtocolo()
	{
		return naoProtocolo;
	}
	public void setNaoProtocolo(Campo naoProtocolo)
	{
		this.naoProtocolo = naoProtocolo;
	}
	public Campo getBrancos()
	{
		return brancos;
	}
	public void setBrancos(Campo brancos)
	{
		this.brancos = brancos;
	}
}
