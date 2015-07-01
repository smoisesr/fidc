package mvcapital.cnab;


public class Register 
{
	
	protected Campo identificacaoRegistro=new Campo();
	protected Campo numeroSequencialRegistro=new Campo();
	protected static TipoCampo campoNumerico = new TipoCampo();
	protected static TipoCampo campoAlfaNumerico = new TipoCampo();
	
	
	public Register() 
	{		
		Register.setTiposCampo();
		this.identificacaoRegistro.setNumero(1);
		this.identificacaoRegistro.setPosicaoInicial(1);
		this.identificacaoRegistro.setPosicaoFinal(1);
		this.identificacaoRegistro.setTamanho(1);
		this.identificacaoRegistro.setObrigatorio(true);
		this.identificacaoRegistro.setTipo(campoNumerico);
	}

	public static void setTiposCampo()
	{
		campoNumerico.setIdTipoCampo(1);
		campoNumerico.setDescricao("Numerico"); //$NON-NLS-1$
		campoAlfaNumerico.setIdTipoCampo(2);
		campoAlfaNumerico.setDescricao("AlfaNumerico"); //$NON-NLS-1$
	}

	public Campo getIdentificacaoDoRegistro() {
		return this.identificacaoRegistro;
	}

	public void setIdentificacaoDoRegistro(Campo identificacaoDoRegistro) {
		this.identificacaoRegistro = identificacaoDoRegistro;
	}

	public Campo getNumeroSequencialRegistro() {
		return this.numeroSequencialRegistro;
	}

	public void setNumeroSequencialRegistro(Campo numeroSequencialRegistro) {
		this.numeroSequencialRegistro = numeroSequencialRegistro;
	}

	public static TipoCampo getCampoNumerico() {
		return campoNumerico;
	}

	public static void setCampoNumerico(TipoCampo campoNumerico) {
		Register.campoNumerico = campoNumerico;
	}

	public static TipoCampo getCampoAlfaNumerico() {
		return campoAlfaNumerico;
	}

	public static void setCampoAlfaNumerico(TipoCampo campoAlfaNumerico) {
		Register.campoAlfaNumerico = campoAlfaNumerico;
	}

	public Campo getIdentificacaoRegistro()
	{
		return this.identificacaoRegistro;
	}

	public void setIdentificacaoRegistro(Campo identificacaoRegistro)
	{
		this.identificacaoRegistro = identificacaoRegistro;
	}
}
