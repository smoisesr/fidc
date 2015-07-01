package mvcapital.bancopaulista.cnab;

import mvcapital.cnab.Campo;
import mvcapital.cnab.Register;

public class Trailler extends Register
{
	private Campo branco=new Campo();
	
	public Trailler() 
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
		this.identificacaoRegistro.setConteudo("9"); //$NON-NLS-1$
		this.branco=new Campo(2,2,438,437,true,Register.campoAlfaNumerico,0,""); //$NON-NLS-1$
		this.numeroSequencialRegistro=new Campo(3,439,444,6,true,Register.campoNumerico,0,"");		 //$NON-NLS-1$
	}

	public Trailler(String line) 
	{
		this();
		this.identificacaoRegistro.setConteudo(line.substring(this.identificacaoRegistro.getPosicaoInicial()-1,this.identificacaoRegistro.getPosicaoFinal()));
		this.branco.setConteudo(line.substring(this.branco.getPosicaoInicial()-1,this.branco.getPosicaoFinal()));
		this.numeroSequencialRegistro.setConteudo(line.substring(this.numeroSequencialRegistro.getPosicaoInicial()-1,this.numeroSequencialRegistro.getPosicaoFinal()));		
	}
		
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.identificacaoRegistro.getConteudo());
		sb.append(this.branco.getConteudo());
		sb.append(this.numeroSequencialRegistro.getConteudo());
		return sb.toString();
	}
	
	public String toCSV()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.identificacaoRegistro.getConteudo());
		sb.append(";"+this.branco.getConteudo()); //$NON-NLS-1$
		sb.append(";"+this.numeroSequencialRegistro.getConteudo()); //$NON-NLS-1$
		return sb.toString();
	}

	
	public Campo getBranco() {
		return this.branco;
	}

	public void setBranco(Campo branco) {
		this.branco = branco;
	}

}
