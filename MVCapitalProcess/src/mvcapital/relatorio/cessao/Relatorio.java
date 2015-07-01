package mvcapital.relatorio.cessao;

import java.util.ArrayList;

import mvcapital.operation.Operacao;

public class Relatorio 
{
	private BlockIdentificacao blockIdentificacao =  new BlockIdentificacao();
	private BlockTitulos blockTitulos = new BlockTitulos();
	
	public Relatorio()
	{
		
	}
	
	public Relatorio(Operacao op, ArrayList<Titulo> dcs)
	{
		this.blockIdentificacao = new BlockIdentificacao(op);
		this.blockTitulos =  new BlockTitulos(dcs);
	}
	
	public Relatorio(BlockIdentificacao blockIdentificacao, BlockTitulos blockTitulos)
	{
		this.blockIdentificacao=blockIdentificacao;
		this.blockTitulos=blockTitulos;
	}

	public BlockIdentificacao getBlockIdentificacao() {
		return blockIdentificacao;
	}

	public void setBlockIdentificacao(BlockIdentificacao blockIdentificacao) {
		this.blockIdentificacao = blockIdentificacao;
	}

	public BlockTitulos getBlockTitulos() {
		return blockTitulos;
	}

	public void setBlockTitulos(BlockTitulos blockTitulos) {
		this.blockTitulos = blockTitulos;
	}
	
}
