 package report;

import java.util.ArrayList;

import utils.DyadKeywordName;

public class Block 
{
	protected String name="";
//	public DyadKeywordName keywordName = new DyadKeywordName("","");
	public static ArrayList<DyadKeywordName> keywordName = new ArrayList<DyadKeywordName>();
	static
	{
		keywordName.add(new DyadKeywordName("relatorio de carteira diaria","IDENTIFICACAO"));
		keywordName.add(new DyadKeywordName("carteira diaria","IDENTIFICACAO"));
		keywordName.add(new DyadKeywordName("fundos de investimento","FUNDOS_DE_INVESTIMENTO"));
		keywordName.add(new DyadKeywordName("renda fixa - opera","OPERACOES_COMPROMISSADAS"));
		keywordName.add(new DyadKeywordName("renda fixa","RENDA_FIXA"));
		keywordName.add(new DyadKeywordName("operacoes compromissadas","OPERACOES_COMPROMISSADAS"));		
//		keywordName.add(new DyadKeywordName("descricao","CONTAS_A_PAGAR_RECEBER"));
		keywordName.add(new DyadKeywordName("contas a pagar","CONTAS_A_PAGAR_RECEBER"));
		keywordName.add(new DyadKeywordName("tesouraria","TESOURARIA"));
		keywordName.add(new DyadKeywordName("patrimonio","PATRIMONIO"));
		keywordName.add(new DyadKeywordName("indexador","RENTABILIDADE_ACUMULADA"));
		keywordName.add(new DyadKeywordName("rentabilidade acumulada","RENTABILIDADE_ACUMULADA"));
		keywordName.add(new DyadKeywordName("valor da cota bruta","RESUMO_COTAS"));
		keywordName.add(new DyadKeywordName("quantidade de cotas","RESUMO_COTAS"));
	}
	public Block()
	{

	}
	public Block(String name)
	{
		this.name=name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
