package report.socopa;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import fundo.FundoDeInvestimento;
import report.Block;
import report.Report;
import report.socopa.RowContasAPagarReceberSocopa;
import report.socopa.RowFundoSocopa;
import report.socopa.RowOperacoesCompromissadasSocopa;
import report.socopa.RowRendaFixaSocopa;
import report.socopa.RowRentabilidadeAcumuladaSocopa;
import utils.CleanString;
import utils.DyadKeywordName;
import utils.TextToLines;

public class ReportSocopa extends Report
{
	
	public ReportSocopa()
	{
		
	}
	public ReportSocopa(FundoDeInvestimento fundo, File fileXLS)
	{
		this.fundo=fundo;		
	}
	public ReportSocopa(FundoDeInvestimento fundo, String parsedText)
	{		
		this.fundo=fundo;
		this.lines=TextToLines.extractLines(parsedText);		
		this.buildBlocks(this.checkLines(this.lines));
		this.setReportCSV();		
	}
	public ReportSocopa (FundoDeInvestimento fundo, ArrayList<String> lines)
	{
		this.fundo=fundo;
		this.buildBlocks(this.checkLines(lines));
		this.setReportCSV();
	}
	public static String cleanNumber(String string)
	{
		return string
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".");
	}
	
	public ArrayList<String> checkLines(ArrayList<String> lines)
	{
		ArrayList<String> newLines = new ArrayList<String>();
		for (String line:lines)
		{
			//System.out.println(line);
			line = line
					.replaceAll("%0", "% ")
					.replaceAll("%1", "% ")
					.replaceAll("%2", "% ")
					.replaceAll("%3", "% ")
					.replaceAll("%4", "% ")
					.replaceAll("%5", "% ")
					.replaceAll("%6", "% ")
					.replaceAll("%7", "% ")
					.replaceAll("%8", "% ")
					.replaceAll("%9", "% ")
					.replaceAll("%-", "% -");
//			System.out.println(line);
			String[] allFields = line.split(" ");
			ArrayList<String> newFields = new ArrayList<String>();
			boolean existDoubleComma=false;
			for(int i = 0; i< allFields.length;i++)
			{
				
				existDoubleComma=CleanString.checkDoubleComma(allFields[i]);
				if(existDoubleComma)
				{					
					//System.out.println("Trouble: " + line);
					int iFirstComma=0;
					boolean existFirstComma=false;
					String troubleString = allFields[i];
					
								
					for(int j=0;j<troubleString.length();j++)
					{
//						System.out.println("Finding firstComma " + troubleString.charAt(j));
						if(troubleString.charAt(j)==',')
						{
							iFirstComma=j;
							existFirstComma=true;
//							System.out.println("iFirstComma " + iFirstComma);
							break;
						}
					}
					int iSecondNumber=0;
					if (existFirstComma)
					{						
						for(int j=iFirstComma+1;j<troubleString.length();j++)
						{
	//						System.out.println("Finding firstNumber different from zero " + troubleString.charAt(j));
							if(troubleString.charAt(j)!='0')
							{							
								iSecondNumber=j;
	//							System.out.println("BeginSecondNumber " + troubleString.charAt(iSecondNumber));
								break;
							}					
						}
					}
					String firstNumber = allFields[i].substring(0, iSecondNumber);
					String secondNumber = allFields[i].substring(iSecondNumber,allFields[i].length());
					newFields.add(firstNumber);
					newFields.add(secondNumber);
//					System.out.println(firstNumber + " " + secondNumber);
				}
				else
				{
					newFields.add(allFields[i]);
				}
			}
			if(newFields.size()>0)
			{
				String newLine = "";
				int iField=0;
				for(String field:newFields)
				{
					if (iField==0)
					{
						newLine=newLine+field;
					}
					else
					{
						newLine=newLine+" "+field;	
					}
					iField=iField+1;
				}	
				//System.out.println("Solve: " + newLine);
				newLines.add(newLine);
			}
			else
			{
				newLines.add(line);
			}
//			System.out.println(newLines.get(newLines.size()-1));
		}
		return newLines;
	}
	public void buildBlocks(ArrayList<String> lines)
	{		
//		System.out.println("***************************************************************");
//		System.out.println("Begin report");
//		System.out.println("***************************************************************");
		ArrayList<Block> blocks = new ArrayList<Block>();
		boolean existDataDaPosicao = false;
		boolean existDataDeEmissao = false;
		boolean existCliente=false;
//		boolean existCodigoDoCliente=true;
		boolean existSaldoEmTesouraria=false;
		boolean existSaldoEmTesourariaReserva=false;
		boolean existTotal=false;
		boolean existPatrimonio=false;
		boolean existQuantidadeDeCotas=false;
		boolean existValorDaCotaUnitaria=false;
//		boolean existQuantidadeDeCotasBruta=false;
//		boolean existValorDaCotaUnitariaBruta=false;
		boolean existValorDaCotaBrutaDePerformance=false;
		
		Block currentBlock = null;
		
		for(int iLine=0;iLine<lines.size(); iLine++)
		{
			String line=lines.get(iLine);
			String lineLower = line.trim().toLowerCase();
			String lineClean = CleanString.cleanWithSpace(lineLower);
					//.replaceAll("(?m)^[\t]*\r?\n", "");
//			System.out.println(lineClean);
			if(lineClean.matches(".*\\W.*")&&!lineClean.isEmpty())
			{	
//				System.out.println(lineClean);
				for (DyadKeywordName keywordName:Block.keywordName)
				{	
//					System.out.println("Testing " + lineClean);
//					System.out.println("For " + keywordName.getKeyword());
					if(lineClean.startsWith(keywordName.getKeyword()) ||
							lineClean.contains(keywordName.getKeyword()))
					{
						if(blocks.size()==0)
						{
							
							//System.out.println("--- ");
//							System.out.println("--- " + keywordName.getName());
							//System.out.println("--- ");
//							System.out.println("KeywordName:" + keywordName.getName());
							if(keywordName.getName().equals("IDENTIFICACAO"))
							{
//								BlockCabecalho blockCabecalho = new BlockCabecalho();
								blocks.add(this.blockIdentificacao);
								currentBlock = this.blockIdentificacao;
							}							
//							System.out.println("Cabecalho Begin:" + currentBlock.keywordName);
							break;
						}
						else
						{
							boolean existBlock=false;						
							for (Block block:blocks)
							{
								if(keywordName.getName().equals(block.getName()))
								{
									currentBlock=block;
									if(!keywordName.getName().equals("IDENTIFICACAO"))
									{
										//System.out.println("--- ");
										System.out.println("--- " + keywordName.getName() + " continuation...");
										//System.out.println("--- ");
									}
									existBlock=true;
									break;
								}								
							}
							if(!existBlock)
							{								
								blocks.add(new Block(keywordName.getName()));
								currentBlock = blocks.get(blocks.size()-1);
//								System.out.println("NewBlock: " + currentBlock.getName());
								//System.out.println("--- ");
								System.out.println("--- " + keywordName.getName());
								//System.out.println("--- ");								
							}							
							break;
						}
					}					
				}				
				if(currentBlock!=null)
				{		
					lineClean=lineClean.replace("\t", " ").replaceAll(" +"," ").trim();
					String[] words = null;
					if(lineClean.contains(";"))
					{
						words=lineClean.split(";");
					}
					else
					{
						words=lineClean.split(" ");
					}
					
					System.out.println(lineClean);
//					System.out.println("currentBlockNotNull:" + currentBlock.getName());
					if (currentBlock.getName().equals("IDENTIFICACAO"))
					{	
//						BlockCabecalho blockCabecalho = (BlockCabecalho) currentBlock;
//						System.out.println("ID:" + lineClean);
						if (!lineClean.startsWith("carteira"))
						{	
							if(!existDataDeEmissao)
							{
//								System.out.println(lineClean);
//								System.out.println(words[1]);
								if(words[0].contains("emiss"))
								{							
									try {
//										System.out.println(lineClean);
										
										if(words[1].contains("."))
										{
											Calendar c = Calendar.getInstance();
											c.setTime(formatterDateLong.parse("01/01/1900")); // Now use today date.
//											System.out.println("words[1] " + words[1]);
											c.add(Calendar.DATE, (int)Double.parseDouble(words[1])); // Adding 5 days
											
											this.dataDeEmissao=c.getTime();
											this.blockIdentificacao.setDataDeEmissao(c.getTime());											
										}
										else
										{
											this.dataDeEmissao=formatterDateLong.parse(words[1]);
											this.blockIdentificacao.setDataDeEmissao(formatterDateLong.parse(words[1]));
										}
									} catch (ParseException e) {
										e.printStackTrace();
									}								
//									System.out.println("DataDeEmissao: " + formatterDateLong.format(dataDeEmissao));
									existDataDeEmissao = true;
								}								
							}
							
							if(!existDataDaPosicao)
							{							
								if(words[0].contains("posi"))
								{		
//									System.out.println(words[1]);									
									try {										
										if(words[1].contains("."))
										{
											Calendar c = Calendar.getInstance();
											c.setTime(formatterDateLong.parse("01/01/1900")); // Now use today date.
//											System.out.println("words[1] " + words[1]);
											c.add(Calendar.DATE, (int)Double.parseDouble(words[1])); // Adding 5 days
											this.dataDaPosicao=c.getTime();
											this.blockIdentificacao.setDataDaPosicao(c.getTime());
										}
										else
										{
											this.dataDaPosicao=formatterDateLong.parse(words[1]);
											this.blockIdentificacao.setDataDaPosicao(formatterDateLong.parse(words[1]));
										}
									} catch (ParseException e) {
										e.printStackTrace();
									}								
//									System.out.println("DataDaPosicao: " + formatterDateLong.format(dataDaPosicao));
									existDataDaPosicao = true;
								}								
							}
						
							if(!existCliente)
							{
								if(words[0].contains("cliente"))
								{
									String stringCliente="";
									for(int iWordCliente=1;iWordCliente<words.length;iWordCliente++)
									{
										stringCliente=stringCliente+words[iWordCliente].toUpperCase();
									}
									this.cliente=stringCliente;
									this.blockIdentificacao.setCliente(words[1].toUpperCase());
//									System.out.println("Cliente: " + this.cliente);
									existCliente=true;
								}
							}
						}
					}					
					else if(currentBlock.getName().equals("FUNDOS_DE_INVESTIMENTO"))
					{
//						System.out.println(lineClean);
						int countPercFundos=0;
						for(int iCharFundos=0;iCharFundos<lineClean.length();iCharFundos++)
						{
							if(lineClean.charAt(iCharFundos)=='%')
							{
								countPercFundos=countPercFundos+1;
							}
						}
						String lineCompleteFundo = lineClean;
						if(countPercFundos<2)
						{
							
							String lineTemp=lines.get(iLine+1);
							String lineLowerTemp = lineTemp.trim().toLowerCase();
							String lineCleanTemp = CleanString.cleanWithSpace(lineLowerTemp);
							lineCompleteFundo=lineCompleteFundo+" "+lineCleanTemp.trim();
							iLine=iLine+1;
						}
						if(!lineCompleteFundo.startsWith("fundos")
								&&!lineClean.startsWith("total")
								&&!lineClean.startsWith("codigo")
								&&!lineClean.startsWith("indexador")
								&&!lineClean.startsWith("rentabilidade")
								&&!lineClean.startsWith("descricao")
								&&!lineClean.startsWith("data")
								&&!lineClean.substring(0, 9).trim().toUpperCase().matches("^\\d.*?\\d$"))
						{
//							System.out.println(lineCompleteFundo);
							RowFundoSocopa rowFundo = new RowFundoSocopa(lineCompleteFundo.toUpperCase());
							this.blockFundo.getRowsFundo().add(rowFundo);
//							rowFundo.showRow();
						}
					}					
					else if(currentBlock.getName().equals("RENDA_FIXA"))
					{
						String lineComplete=CleanString.cleanWithSpace(lineClean).toLowerCase();
	
						if(!lineComplete.startsWith("renda fixa")
								&&!lineComplete.startsWith("codigo")
								&&!lineComplete.startsWith("subtotal")
								&&!lineComplete.startsWith("total")
								&&!lineComplete.startsWith("operacoes")
								&&!lineComplete.startsWith("opera")
								&&!lineComplete.startsWith("data")
								&&!lineComplete.endsWith(";;;;;"))
						{
//							System.out.println("RF:\t" + lineComplete);							
//							currentBlock.getRows().add(rowRendaFixa);
							RowRendaFixaSocopa rowRendaFixa = new RowRendaFixaSocopa(lineComplete);
							this.blockRendaFixa.getRowsContasAPagarReceber().add(rowRendaFixa);
//							rowRendaFixa.showRow();
						}
					}
					else if(currentBlock.getName().equals("OPERACOES_COMPROMISSADAS"))
					{
//						line=lines.get(iLine);
						String lineCleanOC=CleanString.cleanWithSpace(line.toLowerCase().trim());
//						System.out.println(lineCleanOC);
						if(!lineCleanOC.startsWith("operacoes compromissadas")
								&& !lineCleanOC.startsWith("mercado nacional")
								&& !lineCleanOC.startsWith("valor da")
								&& !lineCleanOC.startsWith("op compromissada")
								&& !lineCleanOC.startsWith("total")
								&& !lineCleanOC.startsWith("codigo")
								&& lineCleanOC.contains("stnc")
								)
						{
							int countPercent=0;
							for(int iChar=0;iChar<line.length();iChar++)
							{
								if(line.charAt(iChar)=='%')
								{
									countPercent=countPercent+1;
								}
							}
							String lineComplete=line;
							if(countPercent < 5)
							{
								String preLine = line;
								String tempLine = lines.get(iLine+1).trim();
								if(line.endsWith("pre"))
								{
									String[] fieldsLine = preLine.replaceAll(" +", " ").split(" ");
									if(fieldsLine.length==7)
									{
										preLine=preLine.replace("pre", "0,0% pre");
									}
									int iTemp=0;
									for(int iBeginString=0;iBeginString<tempLine.length();iBeginString++)
									{
										if(Character.isWhitespace(tempLine.charAt(iBeginString)))
										{
											iTemp=iBeginString;
											break;
										}
									}
									tempLine=tempLine.substring(iTemp);
								}
								lineComplete=preLine+" "+tempLine;
								iLine=iLine+1;
							}
							
							int iFirstPerc=0;
							for(int iPerc=0;iPerc<lineComplete.length();iPerc++)
							{
								if(lineComplete.charAt(iPerc)=='%')
								{
									iFirstPerc=iPerc;
									break;
								}
							}
							
							String beforePercentage=lineComplete.substring(0, iFirstPerc+1)
													.trim()
													.replaceAll(" +", " ")
													;
							String afterPercentage=lineComplete.substring(iFirstPerc+1)
									.trim()
									.replaceAll(" +", " ")
									;
							String[] fieldsBeforePercentage = beforePercentage.split(" ");
							if(fieldsBeforePercentage.length<5)
							{
								String newBeforePercentage = fieldsBeforePercentage[0]
															+" "+fieldsBeforePercentage[1]
															+" "+fieldsBeforePercentage[2]
															+" " +"___" 
															+" "+fieldsBeforePercentage[3];
//								System.out.println("newBeforePerc:" + newBeforePercentage);
								lineComplete=newBeforePercentage + " " + afterPercentage;
							}
							
							int countPercent2=0;
							for(int iChar=0;iChar<lineComplete.length();iChar++)
							{
								if(lineComplete.charAt(iChar)=='%')
								{
									countPercent2=countPercent2+1;
								}
							}
							if(countPercent2 < 5)
							{
								lineComplete=lineComplete.replace("___", "___ 0,0%");
							}
							else
							{
								lineComplete=lineComplete
										.toUpperCase()
										.replaceAll(" +", " ");
							}
							//System.out.println("OC:\t" + lineComplete);
							RowOperacoesCompromissadasSocopa rowOperacoesCompromissadas = new RowOperacoesCompromissadasSocopa(lineComplete);
							this.blockOperacoesCompromissadas.getRowsContasAPagarReceber().add(rowOperacoesCompromissadas);
//							rowOperacoesCompromissadas.showRow();
						}
					}
					else if(currentBlock.getName().equals("CONTAS_A_PAGAR_RECEBER"))
					{	
						String lineCleanContas = CleanString.cleanWithSpace(line.toLowerCase().trim());
						//System.out.println(lineCleanContas);
						if(!lineCleanContas.startsWith("contas a pagar")
								&& !lineCleanContas.startsWith("total geral")
								&& !lineCleanContas.startsWith("descricao")
								&& !lineCleanContas.startsWith("data")
								)
						{
							RowContasAPagarReceberSocopa rowContasAPagarReceber = new RowContasAPagarReceberSocopa(lineCleanContas);
//							currentBlock.getRows().add(rowContasAPagarReceber);
							this.blockContasAPagarReceber.getRowsContasAPagarReceber().add(rowContasAPagarReceber);
//							rowContasAPagarReceber.showRow();
						}
					}
					else if(currentBlock.getName().equals("TESOURARIA"))
					{
						String lineCleanTesouraria = CleanString.cleanWithSpace(line.toLowerCase().trim());						
						if(!lineCleanTesouraria.startsWith("tesouraria")
								&& !lineCleanTesouraria.startsWith("descricao")
								)
						{
							
//							System.out.println(lineCleanTesouraria);
							String[] fields=lineCleanTesouraria.split(";");
							if(!existSaldoEmTesourariaReserva)
							{
								if(lineClean.contains("saldo em tesouraria reserva"))
								{
									this.blockTesouraria.saldoEmTesourariaReserva=Double.parseDouble(
											fields[fields.length-1]
													.trim()
													.replace(" ", "")
													.replace("(", "-")
													.replace(")", "")
													.replace("%", "")
													.replace(".", "")
													.replace(",", ".")
													);
									existSaldoEmTesourariaReserva=true;
								}	
							}
							if(!existSaldoEmTesouraria)
							{
								if(lineClean.contains("saldo em tesouraria"))
								{
									String stringSaldoEmTesouraria=fields[fields.length-1]
											.trim()
											.replace(" ", "")
											.replace("(", "-")
											.replace(")", "")
											.replace("%", "")
											.replace(".", "")																
											.replace(",", ".");
									System.out.println(lineClean);
									this.blockTesouraria.saldoEmTesouraria=Double.parseDouble(stringSaldoEmTesouraria);
									existSaldoEmTesouraria=true;
								}	
							}
							if(!existTotal)
							{
								if(lineClean.contains("total"))
								{
									String stringTotal=fields[fields.length-1]
											.trim()
											.replace(" ", "")
											.replace("(", "-")
											.replace(")", "")
											.replace("%", "")
											.replace(".", "")																
											.replace(",", ".");
											;
									this.blockTesouraria.total=Double.parseDouble(stringTotal);
									existTotal=true;
								}	
							}
						}
					}
					else if(currentBlock.getName().equals("PATRIMONIO"))
					{						
						String lineCleanPatrimonio=CleanString.cleanWithSpace(line)
								.toLowerCase()
								.replace("\t"," ")
								.replaceAll(" +", " ");

						if(!lineCleanPatrimonio.startsWith("patrimonio"))
						{
//							System.out.println(lineCleanPatrimonio);							
							if(!existPatrimonio)
							{
								String[] fields = lineCleanPatrimonio.split(";");
								this.blockPatrimonio.valor=Double.parseDouble(fields[fields.length-1]
															.replace(".", "")
															.replace(",", ".")
															);
	//							System.out.println("PAT:" + this.blockPatrimonio.getValor());
								existPatrimonio=true;
							}
						}						
					}	
					else if(currentBlock.getName().equals("RENTABILIDADE_ACUMULADA"))
					{
						String lineCleanRent = CleanString.cleanWithSpace(line)
								.toLowerCase()
								.replaceAll(" +", " ")
								;
						if(!lineCleanRent.startsWith("indexador")
								&&!lineCleanRent.startsWith("rentabilidade")
								&&!lineCleanRent.startsWith("data")
								&&!lineCleanRent.startsWith("valor")
								)
						{
//							System.out.println(lineCleanRent);
							if(lineCleanRent.startsWith("cota"))
							{
								lineCleanRent=lineCleanRent.replace("cota;;;", "cota;0,0%;0,0%;");
							}
							RowRentabilidadeAcumuladaSocopa rowRentabilidadeAcumulada = new RowRentabilidadeAcumuladaSocopa(lineCleanRent);
							this.blockRentabilidadeAcumulada.getRowsRentabilidadeAcumulada().add(rowRentabilidadeAcumulada);
//							rowRentabilidadeAcumulada.showRow();
						}
					}
					else if(currentBlock.getName().equals("RESUMO_COTAS"))
					{
						//System.out.println(line);
						String lineCleanRC=line.toLowerCase().trim();
//						System.out.println(lineCleanRC);
						String[] fields = lineCleanRC.split(";");
						if(!existValorDaCotaBrutaDePerformance)
						{
							if(fields[0].contains("valor da cota bruta de performance"))
							{
								this.blockResumoDeCotas.valorDaCotaBrutaDePerformance=Double.parseDouble(fields[fields.length-1]
										.trim()
										.replace(" ", "")
										.replace(".", "")
										.replace(",", ".")
										);
								existValorDaCotaBrutaDePerformance=true;
							}	
						}
						if(!existQuantidadeDeCotas)
						{
							if(fields[0].contains("quantidade de cotas")&&fields[0].contains("liquida"))
							{
								this.blockResumoDeCotas.quantidadeDeCotas=Double.parseDouble(fields[fields.length-1]
										.trim()
										.replace(" ", "")
										.replace(".", "")
										.replace(",", ".")
										);
								existQuantidadeDeCotas=true;
							}	
						}						
						if(!existValorDaCotaUnitaria)
						{
							if(fields[0].contains("valor da cota unitaria")&&fields[0].contains("liquida"))
							{
								this.blockResumoDeCotas.valorDaCotaUnitaria=Double.parseDouble(fields[fields.length-1]
										.trim()
										.replace(" ", "")
										.replace(".", "")
										.replace(",", ".")
										);
								existValorDaCotaUnitaria=true;
							}	
						}

					}
					else
					{
//						System.out.println(line);
					}
				}
			}			
		}	
//		System.out.println("*******************************************");
//		System.out.println("End Report");
//		System.out.println("*******************************************");
	}	
}
