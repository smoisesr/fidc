package report.petra;

import java.text.ParseException;
import report.RowRendaFixa;
import utils.CleanString;

public class RowRendaFixaPetra extends RowRendaFixa 
{
	public RowRendaFixaPetra()
	{
		
	}
	public RowRendaFixaPetra(String line)
	{
//		System.out.println("\t" +line.toUpperCase().replace("\t", " "));
		String lineClean = line.toUpperCase().replace("\t", " ");
		String preEmitente = lineClean.substring(0,21);
		String[] fieldsPreEmitente = preEmitente.trim().replaceAll(" +", " ").split(" ");
		this.codigo = fieldsPreEmitente[0];
		try {
			this.dataDeAplicacao=ReportPetra.formatterDateShort.parse(fieldsPreEmitente[1]);
		} catch (ParseException e) {
			System.err.println("\nLine: " + lineClean);
			e.printStackTrace();
		}
		
		int indexSlash=0;
		
		boolean existFirstSlash=false;
		for (int i=0;i<lineClean.length();i++)
		{
			//System.out.println(i + " " + lineClean.charAt(i));
			if(lineClean.charAt(i)=='/')
			{
				indexSlash=i;
				if(existFirstSlash)
				{
					break;
				}
				else
				{
					existFirstSlash=true;
				}				
			}
		}
		int beginEmitente = indexSlash+3;
		int endEmitente = beginEmitente + 9;
		
		this.emitente=lineClean.substring(beginEmitente,endEmitente).trim();
		
		int beginPapel=0; 
		for (int i=endEmitente;i<lineClean.length();i++)
		{
			if(Character.isDigit(lineClean.charAt(i)))
			{
				beginPapel=i;
				break;
			}
		}
		
		int endPapel = beginPapel+8;
		this.papel=lineClean.substring(beginPapel,endPapel).trim();

		int beginMtmPercentagemAoAno = 0;
		int endMtmPercentagemAoAno=0;
		 
		for (int i=endPapel;i<lineClean.length();i++)
		{
			if(Character.isDigit(lineClean.charAt(i)))
			{
				beginMtmPercentagemAoAno=i;
				break;
			}
		}		
		for (int i=beginMtmPercentagemAoAno;i<lineClean.length();i++)
		{
			if(lineClean.charAt(i)=='%')
			{
				endMtmPercentagemAoAno=i+1;
				break;
			}
		}

		int countPercentagem=0;
		for (int i=0;i<lineClean.length();i++)
		{
			if(lineClean.charAt(i)=='%')
			{
				countPercentagem=countPercentagem+1;
			}
		}

		this.mtmPercentagemAoAno=Double.parseDouble(lineClean.substring(beginMtmPercentagemAoAno, endMtmPercentagemAoAno)
				.trim()
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".")
				)/100.0;
		
		int beginTaxaOver=0;
		int endTaxaOver=0;
		if(countPercentagem<5)
		{
			this.mtmPercentagemAoAno=0.0;
			beginTaxaOver=beginMtmPercentagemAoAno;
			endTaxaOver=endMtmPercentagemAoAno;
		}
		
		if(beginTaxaOver==0)
		{
			for (int i=endMtmPercentagemAoAno;i<lineClean.length();i++)
			{
				if(Character.isDigit(lineClean.charAt(i)))
				{
					beginTaxaOver=i;
					break;
				}
			}			
			for (int i=beginTaxaOver;i<lineClean.length();i++)
			{
				if(lineClean.charAt(i)=='%')
				{
					endTaxaOver=i+1;
					break;
				}
			}
		}
		
		//System.out.println(lineClean.substring(beginTaxaOver, endTaxaOver).trim().replace("%", "").replace(",", "."));
		this.taxaOver=Double.parseDouble(lineClean.substring(beginTaxaOver, endTaxaOver).trim().replace("%", "").replace(",", "."))/100.0;

		int beginTaxaAoAno=0;
		int endTaxaAoAno=0;
		for (int i=endTaxaOver;i<lineClean.length();i++)
		{
			if(Character.isDigit(lineClean.charAt(i)))
			{
				beginTaxaAoAno=i;
				break;
			}
		}			
		for (int i=beginTaxaAoAno;i<lineClean.length();i++)
		{
			if(lineClean.charAt(i)=='%')
			{
				endTaxaAoAno=i+1;
				break;
			}
		}

		int beginIndex=0;
		int endIndex=0;
		for (int i=endTaxaAoAno+1;i<lineClean.length();i++)
		{
			if(!Character.isWhitespace(lineClean.charAt(i)))
			{
				beginIndex=i;
				break;
			}
		}			
		for (int i=beginIndex;i<lineClean.length();i++)
		{
			//System.out.println(lineClean.charAt(i));
			if(lineClean.charAt(i)=='/')
			{
				endIndex=i-3;
				break;
			}
		}		
//		System.out.println("Begin: " + beginIndex);
//		System.out.println("End: " + endIndex);
		if(endIndex<beginIndex)
		{
			this.index="     ";
		}
		else
		{
//			System.out.println(lineClean);
//			System.out.println(lineClean.substring(beginIndex,endIndex));
			this.index=lineClean.substring(beginIndex,endIndex).trim();
		}

		int beginDataDeEmissao=0;
		int endDataDeEmissao=0;
		for (int i=endIndex+1;i<lineClean.length();i++)
		{
			if(!Character.isWhitespace(lineClean.charAt(i)))
			{
				beginDataDeEmissao=i;
				break;
			}
		}			
		for (int i=beginDataDeEmissao;i<lineClean.length();i++)
		{
			//System.out.println(lineClean.charAt(i));
			if(Character.isWhitespace(lineClean.charAt(i)))
			{
				endDataDeEmissao=i;
				break;
			}
		}		
		
		try {
			this.dataDeEmissao=ReportPetra.formatterDateShort.parse(lineClean.substring(beginDataDeEmissao,endDataDeEmissao).trim());
		} catch (ParseException e) {
			System.err.println("Line: " + lineClean);
			e.printStackTrace();
		}

		int beginDataDeVencimento=0;
		int endDataDeVencimento=0;
		for (int i=endDataDeEmissao+1;i<lineClean.length();i++)
		{
			if(!Character.isWhitespace(lineClean.charAt(i)))
			{
				beginDataDeVencimento=i;
				break;
			}
		}			
		for (int i=beginDataDeVencimento;i<lineClean.length();i++)
		{
			//System.out.println(lineClean.charAt(i));
			if(Character.isWhitespace(lineClean.charAt(i)))
			{
				endDataDeVencimento=i;
				break;
			}
		}		

		try {
			this.dataDeVencimento=ReportPetra.formatterDateShort.parse(lineClean.substring(beginDataDeVencimento,endDataDeVencimento).trim());
		} catch (ParseException e) {
			System.err.println("Line: " + lineClean);
			e.printStackTrace();
		}

		int beginQuantidade=0;
		int endQuantidade=0;
		for (int i=endDataDeVencimento+1;i<lineClean.length();i++)
		{
			if(!Character.isWhitespace(lineClean.charAt(i)))
			{
				beginQuantidade=i;
				break;
			}
		}			
		for (int i=beginQuantidade;i<lineClean.length();i++)
		{
			//System.out.println(lineClean.charAt(i));
			if(Character.isWhitespace(lineClean.charAt(i)))
			{
				endQuantidade=i;
				break;
			}
		}		
		String stringQuantidade = CleanString.removeDoublePoint(lineClean.substring(beginQuantidade, endQuantidade).trim().replace("%", "").replace(".", "").replace(",", "."));
		this.quantidade=Double.parseDouble(stringQuantidade);
		
		int beginPuMercado=0;
		int endPuMercado=0;
		for (int i=endQuantidade+1;i<lineClean.length();i++)
		{
			if(!Character.isWhitespace(lineClean.charAt(i)))
			{
				beginPuMercado=i;
				break;
			}
		}			
		for (int i=beginPuMercado;i<lineClean.length();i++)
		{
			//System.out.println(lineClean.charAt(i));
			if(Character.isWhitespace(lineClean.charAt(i)))
			{
				endPuMercado=i;
				break;
			}
		}		
		
		this.puMercado=Double.parseDouble(lineClean.substring(beginPuMercado, endPuMercado).trim().replace("%", "").replace(".", "").replace(",", "."));

		int beginValorDaAplicacao=0;
		int endValorDaAplicacao=0;
		for (int i=endPuMercado+1;i<lineClean.length();i++)
		{
			if(!Character.isWhitespace(lineClean.charAt(i)))
			{
				beginValorDaAplicacao=i;
				break;
			}
		}			
		for (int i=beginValorDaAplicacao;i<lineClean.length();i++)
		{
			//System.out.println(lineClean.charAt(i));
			if(Character.isWhitespace(lineClean.charAt(i)))
			{
				endValorDaAplicacao=i;
				break;
			}
		}		
		String stringValorDaAplicacao = CleanString.removeDoublePoint(lineClean
				.substring(beginValorDaAplicacao, endValorDaAplicacao)
				.trim().replace("%", "").replace(".", "").replace(",", "."));
		
		if(stringValorDaAplicacao.contains("("))
		{
			stringValorDaAplicacao="-" + stringValorDaAplicacao
					.replace("(", "")
					.replace(")", "");
		}
		
		this.valorDaAplicacao=Double.parseDouble(stringValorDaAplicacao);		
		
		String restLine = lineClean.substring(endValorDaAplicacao, lineClean.length()).trim().replaceAll(" +", " ");
		
		String[] restFields = restLine.split(" ");

		int beginValorDeResgate=0;
		int endValorDeResgate=0;
		
		if(restFields.length==5)
		{
			beginValorDeResgate=endValorDaAplicacao+1;
			endValorDeResgate=endValorDaAplicacao+1;
			this.valorDoResgate=0.0;
		}		
		else
		{
			for (int i=endValorDaAplicacao+1;i<lineClean.length();i++)
			{
				if(!Character.isWhitespace(lineClean.charAt(i)))
				{
					beginValorDeResgate=i;
					break;
				}
			}			
			for (int i=beginValorDeResgate;i<lineClean.length();i++)
			{
				//System.out.println(lineClean.charAt(i));
				if(Character.isWhitespace(lineClean.charAt(i)))
				{
					endValorDeResgate=i;
					break;
				}
			}		
			String stringValorDeResgate = CleanString.removeDoublePoint(lineClean
					.substring(beginValorDeResgate, endValorDeResgate)
					.trim().replace("%", "").replace(".", "").replace(",", "."));
			if(stringValorDeResgate.contains("("))
			{
				stringValorDeResgate="-" + stringValorDeResgate
						.replace("(", "")
						.replace(")", "");
			}				
			this.valorDoResgate=Double.parseDouble(stringValorDeResgate);		
		}
		int beginValorBruto=0;
		int endValorBruto=0;
		for (int i=endValorDeResgate+1;i<lineClean.length();i++)
		{
			if(!Character.isWhitespace(lineClean.charAt(i)))
			{
				beginValorBruto=i;
				break;
			}
		}			
		for (int i=beginValorBruto;i<lineClean.length();i++)
		{
			//System.out.println(lineClean.charAt(i));
			if(Character.isWhitespace(lineClean.charAt(i)))
			{
				endValorBruto=i;
				break;
			}
		}		
		String stringValorBruto = CleanString.removeDoublePoint(lineClean
				.substring(beginValorBruto, endValorBruto)
				.trim().replace("%", "").replace(".", "").replace(",", "."));
		if(stringValorBruto.contains("("))
		{
			stringValorBruto="-" + stringValorBruto
					.replace("(", "")
					.replace(")", "");
		}
		this.valorBruto=Double.parseDouble(stringValorBruto);		
		
		int beginImpostos=0;
		int endImpostos=0;
		for (int i=endValorBruto+1;i<lineClean.length();i++)
		{
			if(!Character.isWhitespace(lineClean.charAt(i)))
			{
				beginImpostos=i;
				break;
			}
		}			
		for (int i=beginImpostos;i<lineClean.length();i++)
		{
			//System.out.println(lineClean.charAt(i));
			if(Character.isWhitespace(lineClean.charAt(i)))
			{
				endImpostos=i;
				break;
			}
		}		
		String stringImpostos = CleanString.removeDoublePoint(lineClean
				.substring(beginImpostos, endImpostos)
				.trim().replace("%", "").replace(".", "").replace(",", "."));
		if(stringImpostos.contains("("))
		{
			stringImpostos="-" + stringImpostos
					.replace("(", "")
					.replace(")", "");
		}
		this.impostos=Double.parseDouble(stringImpostos);		
		
		int beginValorLiquido=0;
		int endValorLiquido=0;
		for (int i=endImpostos+1;i<lineClean.length();i++)
		{
			if(!Character.isWhitespace(lineClean.charAt(i)))
			{
				beginValorLiquido=i;
				break;
			}
		}			
		for (int i=beginValorLiquido;i<lineClean.length();i++)
		{
			//System.out.println(lineClean.charAt(i));
			if(Character.isWhitespace(lineClean.charAt(i)))
			{
				endValorLiquido=i;
				break;
			}
		}	
		
//		System.out.println(lineClean);
		String stringValorLiquido = CleanString.removeDoublePoint(lineClean
				.substring(beginValorLiquido, endValorLiquido)
				.trim().replace("%", "").replace(".", "").replace(",", "."));
		if(stringValorLiquido.contains("("))
		{
			stringValorLiquido="-" + stringValorLiquido
					.replace("(", "")
					.replace(")", "");
		}
		this.valorLiquido=Double.parseDouble(stringValorLiquido);		

		
		int beginPercentualSobreRendaFixa=0;
		int endPercentualSobreRendaFixa=0;
		for (int i=endValorLiquido+1;i<lineClean.length();i++)
		{
			if(!Character.isWhitespace(lineClean.charAt(i)))
			{
				beginPercentualSobreRendaFixa=i;
				break;
			}
		}			
		for (int i=beginPercentualSobreRendaFixa;i<lineClean.length();i++)
		{
			//System.out.println(lineClean.charAt(i));
			if(Character.isWhitespace(lineClean.charAt(i)))
			{
				endPercentualSobreRendaFixa=i;
				break;
			}
		}				
		String stringPercentualSobreRendaFixa = CleanString.removeDoublePoint(lineClean
				.substring(beginPercentualSobreRendaFixa, endPercentualSobreRendaFixa)
				.trim().replace("%", "").replace(".", "").replace(",", "."));
		if(stringPercentualSobreRendaFixa.contains("("))
		{
			stringPercentualSobreRendaFixa="-" + stringPercentualSobreRendaFixa
					.replace("(", "")
					.replace(")", "");
		}
		this.percentualSobreRendaFixa=Double.parseDouble(stringPercentualSobreRendaFixa)/100.0;		
				
		int beginPercentualSobreTotal=0;
		int endPercentualSobreTotal=0;
		for (int i=endPercentualSobreRendaFixa+1;i<lineClean.length();i++)
		{
			if(!Character.isWhitespace(lineClean.charAt(i)))
			{
				beginPercentualSobreTotal=i;
				break;
			}
		}			
		for (int i=beginPercentualSobreTotal;i<lineClean.length();i++)
		{
			//System.out.println(lineClean.charAt(i));
			if(Character.isWhitespace(lineClean.charAt(i)))
			{
				endPercentualSobreTotal=i;
				break;
			}
		}		

//		System.out.println(lineClean);
//		System.out.println(beginPercentualSobreTotal);
//		System.out.println(endPercentualSobreTotal);
		
		endPercentualSobreTotal=lineClean.length();
		String stringPercentualSobreTotal = CleanString.removeDoublePoint(lineClean
				.substring(beginPercentualSobreTotal, endPercentualSobreTotal)
				.trim().replace("%", "").replace(".", "").replace(",", "."));
		if(stringPercentualSobreTotal.contains("("))
		{
			stringPercentualSobreTotal="-" + stringPercentualSobreTotal
					.replace("(", "")
					.replace(")", "");
		}
		this.percentualSobreTotal=Double.parseDouble(stringPercentualSobreTotal)/100.0;		
		
		//System.out.println("RFOr:\t" + lineClean);
//		System.out.println("RF:\t" 
//				+ this.codigo 
//				+ "\t" + Report.formatter.format(this.dataDeAplicacao) 
//				+ "\t" + this.emitente 
//				+ "\t" + this.papel 
//				+ "\t" + Report.dfPorcentagem.format(this.mtmPercentagemAoAno)
//				+ "\t" + Report.dfPorcentagem.format(this.taxaOver) 
//				+ "\t" + Report.dfPorcentagem.format(this.taxaAoAno)
//				+ "\t" + this.index
//				+ "\t" + Report.formatter.format(this.dataDeEmissao)
//				+ "\t" + Report.formatter.format(this.dataDeVencimento)
//				+ "\t" + Report.df.format(this.quantidade)
//				+ "\t" + Report.df.format(this.puMercado)
//				+ "\t" + Report.df.format(this.valorDaAplicacao)
//				+ "\t" + Report.df.format(this.valorDoResgate)
//				+ "\t" + Report.df.format(this.valorBruto)
//				+ "\t" + Report.df.format(this.impostos)
//				+ "\t" + Report.df.format(this.valorLiquido)
//				+ "\t" + Report.df.format(this.percentualSobreRendaFixa)
//				+ "\t" + Report.df.format(this.percentualSobreTotal)
//				);
	}		
	
	}
