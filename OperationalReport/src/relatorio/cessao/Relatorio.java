package relatorio.cessao;

import java.util.ArrayList;
import operation.Operacao;

public class Relatorio 
{
	private BlockIdentificacao blockIdentificacao =  new BlockIdentificacao();
	private BlockDireitosCreditorios blockDireitosCreditorios = new BlockDireitosCreditorios();
	
	public Relatorio()
	{
		
	}
	
	public Relatorio(Operacao op, ArrayList<DireitoCreditorio> dcs)
	{
		this.blockIdentificacao = new BlockIdentificacao(op);
		this.blockDireitosCreditorios =  new BlockDireitosCreditorios(dcs);
	}
	
	public Relatorio(BlockIdentificacao blockIdentificacao, BlockDireitosCreditorios blockTitulos)
	{
		this.blockIdentificacao=blockIdentificacao;
		this.blockDireitosCreditorios=blockTitulos;
	}

	public BlockIdentificacao getBlockIdentificacao() {
		return blockIdentificacao;
	}

	public void setBlockIdentificacao(BlockIdentificacao blockIdentificacao) {
		this.blockIdentificacao = blockIdentificacao;
	}

	public BlockDireitosCreditorios getBlockDireitosCreditorios() {
		return blockDireitosCreditorios;
	}

	public void setBlockDireitosCreditorios(BlockDireitosCreditorios blockTitulos) {
		this.blockDireitosCreditorios = blockTitulos;
	}
	
}
