package mvcapital.operation;

import java.util.ArrayList;

import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.relatorio.cessao.Titulo;

public class OperationSummary 
{
	private FundoDeInvestimento fundo = new FundoDeInvestimento();
	private ArrayList<SacadoAttempt> sacadosAttempt = new ArrayList<SacadoAttempt>();
	private CedenteAttempt cedenteAttempt = new CedenteAttempt();
	private ArrayList<TituloAttempt> direitosCreditoriosAttempt = new ArrayList<TituloAttempt>();
	private MaioresCedentesAttempt maioresCedentesAttempt = new MaioresCedentesAttempt();
	private MaioresSacadosAttempt maioresSacadosAttempt = new MaioresSacadosAttempt();
	private boolean aprovado=false;
	private String description="";
	
	public OperationSummary() 
	{
		
	}

	public OperationSummary(Operacao operacao)
	{
		ArrayList<Entidade> sacados = new ArrayList<Entidade>();
		this.fundo = operacao.getFundo();
		this.cedenteAttempt = new CedenteAttempt(operacao.getCedente(), Math.round(operacao.getTotalAquisicao()*100.0)/100.0);
//		System.out.println("DireitosCreditorios: " + operacao.getRelatorio().getBlockDireitosCreditorios().getDireitosCreditorios().size());
		for (Titulo dc:operacao.getRelatorio().getBlockTitulos().getTitulos())
		{
			direitosCreditoriosAttempt.add(new TituloAttempt(dc));
			boolean existSacado=false;
			for(Entidade sacado:sacados)
			{
				if(sacado.getNome().equals(dc.getSacado().getNome()))
				{
					existSacado=true;
					break;
				}
			}
			if(!existSacado)
			{
				sacados.add(dc.getSacado());
				sacadosAttempt.add(new SacadoAttempt(dc.getSacado(),Math.round(dc.getValorAquisicao()*100.0)/100.0));
				//System.out.println("Add Sacado: " + dc.getSacado().getNome() + " Operar: " + dc.getValorAquisicao());
			}
			else
			{
				for(SacadoAttempt sacadoAttempt:sacadosAttempt)
				{
					if(sacadoAttempt.getSacado().getNome().equals(dc.getSacado().getNome()))
					{
						sacadoAttempt.setTotalOperar(Math.round((sacadoAttempt.getTotalOperar()+dc.getValorAquisicao())*100.0)/100.0);
						//System.out.println("Sacado: " + sacadoAttempt.getSacado().getNome() + " Operar: " + sacadoAttempt.getTotalOperar());
					}
				}
			}			
		}
		System.out.println("Sacado\tOperar");
		for(SacadoAttempt sacadoAttempt:sacadosAttempt)
		{
			System.out.println(sacadoAttempt.getSacado().getNome() + "\t" + sacadoAttempt.getTotalOperar());
		}
		System.out.println();
	}

	public ArrayList<SacadoAttempt> getSacadosAttempt() {
		return sacadosAttempt;
	}

	public void setSacadosAttempt(ArrayList<SacadoAttempt> sacadosAttempt) {
		this.sacadosAttempt = sacadosAttempt;
	}

	public CedenteAttempt getCedenteAttempt() {
		return cedenteAttempt;
	}

	public void setCedenteAttempt(CedenteAttempt cedenteAttempt) {
		this.cedenteAttempt = cedenteAttempt;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FundoDeInvestimento getFundo() {
		return fundo;
	}

	public void setFundo(FundoDeInvestimento fundo) {
		this.fundo = fundo;
	}

	public ArrayList<TituloAttempt> getDireitosCreditoriosAttempt() {
		return direitosCreditoriosAttempt;
	}

	public void setDireitosCreditoriosAttempt(
			ArrayList<TituloAttempt> direitosCreditoriosAttempt) {
		this.direitosCreditoriosAttempt = direitosCreditoriosAttempt;
	}

	public MaioresCedentesAttempt getMaioresCedentesAttempt() {
		return maioresCedentesAttempt;
	}

	public void setMaioresCedentesAttempt(
			MaioresCedentesAttempt maioresCedentesAttempt) {
		this.maioresCedentesAttempt = maioresCedentesAttempt;
	}

	public MaioresSacadosAttempt getMaioresSacadosAttempt() {
		return maioresSacadosAttempt;
	}

	public void setMaioresSacadosAttempt(MaioresSacadosAttempt maioresSacadosAttempt) {
		this.maioresSacadosAttempt = maioresSacadosAttempt;
	}
}
