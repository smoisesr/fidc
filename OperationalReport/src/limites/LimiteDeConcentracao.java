package limites;

import fundo.FundoDeInvestimento;

public class LimiteDeConcentracao extends Limite
{
//	idLimiteDeConcentracao, 
//	idTipoDeConcentracao, 
//	idFundo, 
//	valor
	
	private double valor=0.0;

	public LimiteDeConcentracao()
	{
		
	}
	
	public LimiteDeConcentracao(int idLimiteDeConcentracao, int idTipoDeConcentracao, FundoDeInvestimento fundo, String tipoDeConcentracao) 
	{
		super(idLimiteDeConcentracao, idTipoDeConcentracao, fundo, tipoDeConcentracao);
	}
	
	public void show()
	{
		System.out.println("----------");
		System.out.println("tipoDeConcentracao: " + tipoDeLimite);
		System.out.println("fundo: " + fundo.getNome());
		System.out.println("valor: " + valor);
		System.out.println("----------");
	}
	
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
}
