package mvcapital.bancopaulista.cnab;

public class Campo 
{
	private int numero=0;
	private int posicaoInicial=0;
	private int posicaoFinal=0;
	private int tamanho=0;
	private boolean obrigatorio=true;
	private TipoCampo tipo=new TipoCampo();
	private int decimais=0;
	private String conteudo="";
	private double valor=0.0;
	private String conteudoLimpo="";
	
	public Campo() 
	{
		
	}
	
	public Campo(int numero, int posicaoInicial, int posicaoFinal, int tamanho, boolean obrigatorio, TipoCampo tipo, int decimais, String conteudo)
	{
		this.numero=numero;
		this.posicaoInicial=posicaoInicial;
		this.posicaoFinal=posicaoFinal;
		this.tamanho=tamanho;
		this.obrigatorio=obrigatorio;
		this.tipo=tipo;
		this.decimais=decimais;	
		if(conteudo.length()>0)
		{
			this.setConteudo(conteudo);
		}
		else
		{
			this.conteudo=conteudo;	
		}
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getPosicaoInicial() {
		return posicaoInicial;
	}

	public void setPosicaoInicial(int posicaoInicial) {
		this.posicaoInicial = posicaoInicial;
	}

	public int getPosicaoFinal() {
		return posicaoFinal;
	}

	public void setPosicaoFinal(int posicaoFinal) {
		this.posicaoFinal = posicaoFinal;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public boolean isObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public TipoCampo getTipo() {
		return tipo;
	}

	public void setTipo(TipoCampo tipo) {
		this.tipo = tipo;
	}

	public int getDecimais() {
		return decimais;
	}

	public void setDecimais(int decimais) {
		this.decimais = decimais;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo.trim();
		if(this.tipo.getDescricao().toLowerCase().contains("alfa"))
		{
			int currentSize=this.conteudo.length();
			if(currentSize>this.tamanho)
			{
				this.conteudo=this.conteudo.substring(0, this.tamanho);			
			}
			else if(currentSize<this.tamanho)
			{
				int blankSize=this.tamanho-currentSize;
				
				for(int i=0;i<blankSize;i++)
				{
					this.conteudo=this.conteudo+" ";
				}
				
			}
			this.setConteudoLimpo(this.conteudo.trim());
		}
		else
		{
			this.conteudo=removeLeftZeros(this.conteudo);
			int currentSize=this.conteudo.length();
			if(currentSize>this.tamanho)
			{
				this.conteudo=this.conteudo.substring(currentSize-this.tamanho, currentSize);
			}
			else if(currentSize<this.tamanho)
			{
				int blankSize=this.tamanho-currentSize;
				for(int i=0;i<blankSize;i++)
				{
					this.conteudo="0"+this.conteudo;
				}
			}
			this.setConteudoLimpo(removeLeftZeros(this.conteudo));
			if(this.getDecimais()!=0)
			{
				double decimalDivisor=Math.pow(10.0, this.getDecimais());
				this.valor=Double.parseDouble(this.getConteudoLimpo())/decimalDivisor;
			}
		}
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getConteudoLimpo() {
		return conteudoLimpo;
	}

	public void setConteudoLimpo(String conteudoLimpo) {
		this.conteudoLimpo = conteudoLimpo;
	}
	
	public static String removeLeftZeros(String inputString)
	{
		String outputString="";
		int sizeLeftZeros=0;
		for (int i = 0; i < inputString.length(); i++)
		{
		    char c = inputString.charAt(i);
		    if(c=='0')
		    {
		    	sizeLeftZeros=sizeLeftZeros+1;
		    }
		    else
		    {
		    	break;
		    }
		}
		
		if(sizeLeftZeros>0)
		{
			outputString=inputString.substring(sizeLeftZeros-1);
			return outputString;
		}
		else
		{
			return inputString;	
		}
	}
}
