package mvcapital.bancopaulista;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.mysql.jdbc.Connection;

import fundo.FundoDeInvestimento;


public class Movimentacao 
{
	private int idMovimentacao=0;
	private FundoDeInvestimento fundo=new FundoDeInvestimento();
	private Date data=Calendar.getInstance().getTime();
	private Date hora=Calendar.getInstance().getTime();
	private String descricao=""; //$NON-NLS-1$
	private double valor=0.0;
	private TipoDeMovimentacao tipoDeMovimentacao=new TipoDeMovimentacao();
	private static SimpleDateFormat sdfExtrato = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd"); //$NON-NLS-1$
	private static SimpleDateFormat sdfet = new SimpleDateFormat("HH:mm:ss"); //$NON-NLS-1$
	
	public Movimentacao()
	{
		
	}

	public Movimentacao(int idMovimentacao,
			FundoDeInvestimento fundo2, 
			Date data,
			Date hora,
			String descricao, 
			double valor, 
			int idTipoDeMovimentacao,
			Connection conn)
	{
		this.idMovimentacao=idMovimentacao;
		this.fundo=fundo2;
		try {
		this.data=sdfd.parse(sdfd.format(data));
		} catch (ParseException e) {
		e.printStackTrace();
		}		
		this.hora=hora;
		this.descricao=descricao;
		this.valor = valor;
		this.tipoDeMovimentacao=new TipoDeMovimentacao(idTipoDeMovimentacao, conn);
	}
	
	
	public Movimentacao(FundoDeInvestimento fundo2, 
						Date data,
						Date hora,
						String descricao, 
						double valor, 
						String stringTipoDeMovimentacao,
						Connection conn)
	{
		this.fundo=fundo2;
		try {
			this.data=sdfd.parse(sdfd.format(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		this.hora=hora;
		this.descricao=descricao;
		this.valor = valor;
		this.tipoDeMovimentacao=new TipoDeMovimentacao(stringTipoDeMovimentacao, conn);
	}
	
	public String getString()
	{
		DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return	this.fundo.getNomeCurto()
							+ "\t" + sdfExtrato.format(this.data) //$NON-NLS-1$
							+ "\t" + sdfet.format(this.hora) //$NON-NLS-1$
							+ "\t" + this.descricao //$NON-NLS-1$
							+ "\t" + df.format(this.valor) //$NON-NLS-1$
							+ "\t" + this.tipoDeMovimentacao.getTipo() //$NON-NLS-1$
							;
	}
	
	public void show()
	{
		DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		System.out.println(
							this.fundo.getNomeCurto()
							+ "\t" + sdfExtrato.format(this.data) //$NON-NLS-1$
							+ "\t" + sdfet.format(this.hora) //$NON-NLS-1$
							+ "\t" + this.descricao //$NON-NLS-1$
							+ "\t" + df.format(this.valor) //$NON-NLS-1$
							+ "\t" + this.tipoDeMovimentacao.getTipo() //$NON-NLS-1$
							);
	}
	public int getIdMovimentacao() {
		return this.idMovimentacao;
	}
	public void setIdMovimentacao(int idMovimentacao) {
		this.idMovimentacao = idMovimentacao;
	}
	public Date getData() {
		return this.data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDescricao() {
		return this.descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValor() {
		return this.valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public FundoDeInvestimento getFundo() {
		return this.fundo;
	}

	public void setFundo(FundoDeInvestimento fundo) {
		this.fundo = fundo;
	}

	public static SimpleDateFormat getSdfExtrato() {
		return sdfExtrato;
	}

	public static void setSdfExtrato(SimpleDateFormat sdfExtrato) {
		Movimentacao.sdfExtrato = sdfExtrato;
	}

	public TipoDeMovimentacao getTipoDeMovimentacao() {
		return this.tipoDeMovimentacao;
	}

	public void setTipoDeMovimentacao(TipoDeMovimentacao tipoDeMovimentacao) {
		this.tipoDeMovimentacao = tipoDeMovimentacao;
	}

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}
	
	
	
}
