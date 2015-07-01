package mvcapital.bancopaulista.cnab;

public class Register 
{
	protected Campo identificacaoDoRegistro=new Campo();
	protected Campo numeroSequencialRegistro=new Campo();
	protected static TipoCampo campoNumerico = new TipoCampo();
	protected static TipoCampo campoAlfaNumerico = new TipoCampo();
	
	
	public Register() 
	{		
		Register.setTiposCampo();
		this.identificacaoDoRegistro.setNumero(1);
		this.identificacaoDoRegistro.setPosicaoInicial(1);
		this.identificacaoDoRegistro.setPosicaoFinal(1);
		this.identificacaoDoRegistro.setTamanho(1);
		this.identificacaoDoRegistro.setObrigatorio(true);
		this.identificacaoDoRegistro.setTipo(campoNumerico);
	}

	public static void setTiposCampo()
	{
		campoNumerico.setIdTipoCampo(1);
		campoNumerico.setDescricao("Numerico");
		campoAlfaNumerico.setIdTipoCampo(2);
		campoAlfaNumerico.setDescricao("AlfaNumerico");
	}

	public Campo getIdentificacaoDoRegistro() {
		return identificacaoDoRegistro;
	}

	public void setIdentificacaoDoRegistro(Campo identificacaoDoRegistro) {
		this.identificacaoDoRegistro = identificacaoDoRegistro;
	}

	public Campo getNumeroSequencialRegistro() {
		return numeroSequencialRegistro;
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
}
