package operation;

import limites.LimiteDeConcentracao;
import entidade.Entidade;

public class SacadoAttempt 
{
	private Entidade sacado = new Entidade();
	private boolean aprovado = false;
	private double totalOperar = 0.0;
	
	private double maximoOperar = 0.0;
	private double valorPresente = 0.0;
	private double concentracao = 0.0;
	
	private double novoMaximoOperar = 0.0;
	private double novoValorPresente = 0.0;
	private double novaConcentracao = 0.0;
	
	private double excesso=0.0;
	
	private LimiteDeConcentracao limiteDeConcentracao = new LimiteDeConcentracao();
	
	public SacadoAttempt() 
	{
		
	}

	public SacadoAttempt(Entidade sacado, double totalOperar) 
	{
		this.sacado = sacado;
		this.totalOperar = totalOperar;
	}
	
	public Entidade getSacado() {
		return sacado;
	}

	public void setSacado(Entidade sacado) {
		this.sacado = sacado;
	}

	public double getTotalOperar() {
		return totalOperar;
	}

	public void setTotalOperar(double totalOperar) {
		this.totalOperar = totalOperar;
	}

	public double getNovoValorPresente() {
		return novoValorPresente;
	}

	public void setNovoValorPresente(double novoValorPresente) {
		this.novoValorPresente = novoValorPresente;
	}

	public double getNovaConcentracao() {
		return novaConcentracao;
	}

	public void setNovaConcentracao(double novaConcentracao) {
		this.novaConcentracao = novaConcentracao;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public double getMaximoOperar() {
		return maximoOperar;
	}

	public void setMaximoOperar(double maximoOperar) {
		this.maximoOperar = maximoOperar;
	}

	public double getValorPresente() {
		return valorPresente;
	}

	public void setValorPresente(double valorPresente) {
		this.valorPresente = valorPresente;
	}

	public double getConcentracao() {
		return concentracao;
	}

	public void setConcentracao(double concentracao) {
		this.concentracao = concentracao;
	}

	public double getNovoMaximoOperar() {
		return novoMaximoOperar;
	}

	public void setNovoMaximoOperar(double novoMaximoOperar) {
		this.novoMaximoOperar = novoMaximoOperar;
	}

	public LimiteDeConcentracao getLimiteDeConcentracao() {
		return limiteDeConcentracao;
	}

	public void setLimiteDeConcentracao(LimiteDeConcentracao limiteDeConcentracao) {
		this.limiteDeConcentracao = limiteDeConcentracao;
	}

	public double getExcesso() {
		return excesso;
	}

	public void setExcesso(double excesso) {
		this.excesso = excesso;
	}	
}
