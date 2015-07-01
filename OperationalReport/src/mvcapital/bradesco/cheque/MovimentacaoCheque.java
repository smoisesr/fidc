package mvcapital.bradesco.cheque;

import java.util.Calendar;
import java.util.Date;

public class MovimentacaoCheque 
{
//	Data da Compensação ou Devolução;
//	Histórico;
//	Docto.;
//	Bco.;
//	Ag;
//	Conta;
//	Crédito (R$);
//	Débito (R$)
//	13/11/2014;Cheque Depositado;21;33;3929;9913057605;48.500,00;

	private Date data=Calendar.getInstance().getTime();	
	private String description="";
	private String documento="";
	private String codigoBanco="";
	private String agencia="";
	private String conta="";
	private double valor=0.0;
	
	public MovimentacaoCheque() 
	{
	
	}
	

   	
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
