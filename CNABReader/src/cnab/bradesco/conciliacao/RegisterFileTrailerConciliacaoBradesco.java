package cnab.bradesco.conciliacao;

import cnab.base.RegisterField;
import cnab.base.Register;

public class RegisterFileTrailerConciliacaoBradesco extends Register
{
	private RegisterField banco = new RegisterField(1, 3,"Código do Banco na Compensação", "banco");
	private RegisterField lote = new RegisterField(4, 7,"Lote de Serviço", "lote");
	private RegisterField registro = new RegisterField(8, 8,"Tipo de Registro", "registro");
	private RegisterField CNAB = new RegisterField(9, 17,"Uso Exclusivo Febraban/CNAB","CNAB");
	private RegisterField quantidadeDeLotes = new RegisterField(18, 23, "Quantidade de lotes do arquivo", "quantidadeDeLotes");
	private RegisterField quantidadeDeRegistros = new RegisterField(24, 29, "Quantidade de registros do arquivo", "quantidadeDeRegistros");
	private RegisterField quantidadeDeContasConciliadas = new RegisterField(30, 35, "Quantidade de registros do arquivo", "quantidadeDeContasConciliadas");	
	private RegisterField CNAB2 = new RegisterField(36, 240,"Uso exclusivo Febraban/CNAB", "CNAB2");
	
	public RegisterFileTrailerConciliacaoBradesco(String line)
	{
		this.banco.setValue(Integer.parseInt((String) RegisterField.extractString(this.banco.getInitialPosition(), this.banco.getFinalPosition(), line)));
		this.lote.setValue(Integer.parseInt((String) RegisterField.extractString(this.lote.getInitialPosition(), this.lote.getFinalPosition(), line)));
		this.registro.setValue(Integer.parseInt((String) RegisterField.extractString(this.registro.getInitialPosition(), this.registro.getFinalPosition(), line)));
		this.CNAB.setValue((String) RegisterField.extractString(this.CNAB.getInitialPosition(), this.CNAB.getFinalPosition(), line));
		this.quantidadeDeLotes.setValue(Integer.parseInt((String) RegisterField.extractString(this.quantidadeDeLotes.getInitialPosition(), this.quantidadeDeLotes.getFinalPosition(), line)));
		this.quantidadeDeRegistros.setValue(Integer.parseInt((String) RegisterField.extractString(this.quantidadeDeRegistros.getInitialPosition(), this.quantidadeDeRegistros.getFinalPosition(), line)));
		this.quantidadeDeContasConciliadas.setValue(Integer.parseInt((String) RegisterField.extractString(this.quantidadeDeContasConciliadas.getInitialPosition(), this.quantidadeDeContasConciliadas.getFinalPosition(), line)));
		this.CNAB2.setValue((String) RegisterField.extractString(this.CNAB2.getInitialPosition(), this.CNAB2.getFinalPosition(), line));
	}
	
	public void showRegister()
	{
		System.out.print(this.banco.getValue().toString()+"\t");
		System.out.print(this.lote.getValue().toString()+"\t");
		System.out.print(this.registro.getValue().toString()+"\t");
		System.out.print(this.CNAB.getValue().toString()+"\t");
		System.out.print(this.quantidadeDeLotes.getValue().toString()+"\t");
		System.out.print(this.quantidadeDeRegistros.getValue().toString()+"\t");
		System.out.print(this.quantidadeDeContasConciliadas.getValue().toString()+"\t");
		System.out.print(this.CNAB2.getValue().toString()+"\n");

	}
	public RegisterField getBanco()
	{
		return banco;
	}
	public void setBanco(RegisterField banco)
	{
		this.banco = banco;
	}
	public RegisterField getLote()
	{
		return lote;
	}
	public void setLote(RegisterField lote)
	{
		this.lote = lote;
	}
	public RegisterField getRegistro()
	{
		return registro;
	}
	public void setRegistro(RegisterField registro)
	{
		this.registro = registro;
	}
	public RegisterField getCNAB()
	{
		return CNAB;
	}
	public void setCNAB(RegisterField cNAB)
	{
		CNAB = cNAB;
	}
	public RegisterField getCNAB2()
	{
		return CNAB2;
	}
	public void setCNAB2(RegisterField cNAB2)
	{
		CNAB2 = cNAB2;
	}
	public RegisterField getQuantidadeDeLotes()
	{
		return quantidadeDeLotes;
	}
	public void setQuantidadeDeLotes(RegisterField quantidadeDeLotes)
	{
		this.quantidadeDeLotes = quantidadeDeLotes;
	}
	public RegisterField getQuantidadeDeRegistros()
	{
		return quantidadeDeRegistros;
	}
	public void setQuantidadeDeRegistros(RegisterField quantidadeDeRegistros)
	{
		this.quantidadeDeRegistros = quantidadeDeRegistros;
	}
	public RegisterField getQuantidadeDeContasConciliadas()
	{
		return quantidadeDeContasConciliadas;
	}
	public void setQuantidadeDeContasConciliadas(RegisterField quantidadeDeContasConciliadas)
	{
		this.quantidadeDeContasConciliadas = quantidadeDeContasConciliadas;
	}
}
