package mvcapital.bancopaulista.liquidadosebaixados;

import java.util.Calendar;
import java.util.Date;

import mvcapital.entidade.Entidade;
import mvcapital.praca.Praca;
import mvcapital.relatorio.cessao.Titulo;

import com.mysql.jdbc.Connection;

public class Ocorrencia
{
	
//	idOcorrencia, 
//	idTitulo, 
//	idTipoOcorrencia, 
//	Data, 
//	Valor, 
//	FlagCNAB, 
//	idPraca, 
//	DataAtualizacao

	
	// FUNDO
	
	// DATA MOVIMENTO
	
	// CEDENTE
	// CPF/CNPJ
	
	// OCORRÊNCIA
	
	// SITUACÃO DO RECEBÍVEL
	
	// DOCUMENTO
	// SACADO
	// CPF/CNPJ
	// TAXA
	// VALOR DE AQUISICÃO
	// VALOR DE VENCIMENTO
	// DATA DA AQUISIÇÃO
	// DATA DE VENCIMENTO
	
	// VALOR DE PAGO
	
	// AJUSTE
	// SEU NUMERO
	// NUMERO CORRESPONDENTE

	private int idOcorrencia = 0;
	private Titulo titulo = new Titulo();
	private TipoOcorrencia tipoOcorrencia = new TipoOcorrencia();
	private Date data = Calendar.getInstance().getTime();
	private Entidade cedente = new Entidade();
	private double valor = 0.0;
	private int flagCNAB = 0;
	private Praca praca = new Praca();
	
	
	public Ocorrencia()
	{

	}
	
	public Ocorrencia(int idOcorrencia, int idTitulo, TipoOcorrencia tipoOcorrencia, Date dataOcorrencia, double valor, Connection conn)
	{
		this.idOcorrencia = idOcorrencia;
		this.titulo = new Titulo(idTitulo,conn);
		this.tipoOcorrencia = tipoOcorrencia;
		this.data = dataOcorrencia;
		this.valor = valor;
	}
	
	public void show()
	{
		System.out.println( this.data 
							+ " " + this.getTitulo().getTipoTitulo().getDescricao()  //$NON-NLS-1$
							+ " " + this.titulo.getIdTitulo() //$NON-NLS-1$
							+ " " + this.getTipoOcorrencia().getDescricao() //$NON-NLS-1$
							+ " " + this.valor //$NON-NLS-1$
							);
	}


	public int getIdOcorrencia()
	{
		return this.idOcorrencia;
	}


	public void setIdOcorrencia(int idOcorrencia)
	{
		this.idOcorrencia = idOcorrencia;
	}


	public Titulo getTitulo()
	{
		return this.titulo;
	}


	public void setTitulo(Titulo titulo)
	{
		this.titulo = titulo;
	}


	public Date getData()
	{
		return this.data;
	}


	public void setData(Date data)
	{
		this.data = data;
	}


	public Entidade getCedente()
	{
		return this.cedente;
	}


	public void setCedente(Entidade cedente)
	{
		this.cedente = cedente;
	}


	public double getValor()
	{
		return this.valor;
	}


	public void setValor(double valor)
	{
		this.valor = valor;
	}


	public int getFlagCNAB()
	{
		return this.flagCNAB;
	}


	public void setFlagCNAB(int flagCNAB)
	{
		this.flagCNAB = flagCNAB;
	}


	public TipoOcorrencia getTipoOcorrencia()
	{
		return this.tipoOcorrencia;
	}


	public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia)
	{
		this.tipoOcorrencia = tipoOcorrencia;
	}

	public Praca getPraca()
	{
		return this.praca;
	}

	public void setPraca(Praca praca)
	{
		this.praca = praca;
	}

}
