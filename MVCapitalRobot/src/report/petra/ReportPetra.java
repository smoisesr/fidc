package report.petra;

import java.text.ParseException;
import java.util.ArrayList;

import fundo.FundoDeInvestimento;
import report.Block;
import report.Report;
import utils.CleanString;
import utils.DyadKeywordName;
import utils.TextToLines;

public class ReportPetra extends Report
{
	
	public ReportPetra()
	{
		
	}
	public ReportPetra(FundoDeInvestimento fundo, String parsedText)
	{		
		this.fundo=fundo;
		this.lines=TextToLines.extractLines(parsedText);		
		this.buildBlocks(this.checkLines(this.lines));
		this.setReportCSV();		
	}
	public ReportPetra (FundoDeInvestimento fundo, ArrayList<String> lines)
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
		boolean existCodigoDoCliente=false;
		boolean existSaldoEmTesouraria=false;
		boolean existSaldoEmTesourariaReserva=false;
		boolean existTotal=false;
		boolean existPatrimonio=false;
		boolean existQuantidadeDeCotas=false;
		boolean existValorDaCotaUnitaria=false;
		boolean existQuantidadeDeCotasBruta=false;
		boolean existValorDaCotaUnitariaBruta=false;
		boolean existValorDaCotaBrutaDePerformance=false;
		
		Block currentBlock = null;
		
		for(int iLine=0;iLine<lines.size(); iLine++)
		{
			String line=lines.get(iLine);
			String lineLower = line.trim().toLowerCase();
			String lineClean = CleanString.cleanWithSpace(lineLower);
					//.replaceAll("(?m)^[\t]*\r?\n", "");
			System.out.println(lineClean);
			if(lineClean.matches(".*\\D.*")||lineClean.matches(".*\\W.*")&&!lineClean.isEmpty())
			{	
				
				for (DyadKeywordName keywordName:Block.keywordName)
				{				
					if(lineClean.startsWith(keywordName.getKeyword()))
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
//										System.out.println("--- " + keywordName.getName() + " continuation...");
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
								//System.out.println("--- " + keywordName.getName());
								//System.out.println("--- ");
								
							}							
							break;
						}
					}					
				}				
				if(currentBlock!=null)
				{		
					String[] words = lineClean.split(" ");
//					System.out.println(lineClean);
//					System.out.println("currentBlockNotNull:" + currentBlock.getName());
					if (currentBlock.getName().equals("IDENTIFICACAO"))
					{	
//						BlockCabecalho blockCabecalho = (BlockCabecalho) currentBlock;
//						System.out.println("*********:" + lineClean);
						if (words.length>3&& this.dataDaPosicao==null)
						{
							if(!existDataDeEmissao)
							{
								if(words[2].contains("emissao"))
								{							
									try {
//										System.out.println(words[3]);
										this.dataDeEmissao=formatterDateShort.parse(words[3]);
										this.blockIdentificacao.setDataDeEmissao(formatterDateShort.parse(words[3]));
									} catch (ParseException e) {
										e.printStackTrace();
									}								
//									System.out.println("DataDeEmissao: " + formatterDateShort.format(dataDeEmissao));
									existDataDeEmissao = true;
								}								
							}
							if(!existDataDaPosicao)
							{							
								if(words[2].contains("posicao"))
								{							
									try {
//										System.out.println(words[3]);
										this.dataDaPosicao=formatterDateShort.parse(words[3]);
										this.blockIdentificacao.setDataDaPosicao(formatterDateShort.parse(words[3]));
									} catch (ParseException e) {
										e.printStackTrace();
									}								
//									System.out.println("DataDaPosicao: " + formatterDateShort.format(dataDaPosicao));
									existDataDaPosicao = true;
								}								
							}
						}
						else if(!existCliente)
						{
							if(lineClean.contains("cliente"))
							{
								int iBeginCliente=0;
								int iEndCliente=0;
								for(int i=0;i<lineClean.length();i++)
								{
									if(lineClean.charAt(i)==':')
									{
										iBeginCliente=i+1;
										break;
									}
								}
								for(int i=iBeginCliente+1;i<lineClean.length();i++)
								{
									if(lineClean.charAt(i)=='[')
									{
										iEndCliente=i;
										break;
									}
								}
								this.cliente=lineClean.substring(iBeginCliente, iEndCliente).trim().toUpperCase();
								this.blockIdentificacao.setCliente(lineClean.substring(iBeginCliente, iEndCliente).trim().toUpperCase());
//								System.out.println("Cliente: " + this.cliente);
								existCliente=true;
								int iBeginCodigoDoCliente=iEndCliente+1;
								int iEndCodigoDoCliente=0;
								for(int i=iEndCliente+1;i<lineClean.length();i++)
								{
									if(lineClean.charAt(i)==']')
									{
										iEndCodigoDoCliente=i;
										break;
									}
								}
								if(!existCodigoDoCliente)
								{
									this.codigoDoCliente=lineClean.substring(iBeginCodigoDoCliente, iEndCodigoDoCliente).trim();
									this.blockIdentificacao.setCodigoDoCliente(lineClean.substring(iBeginCodigoDoCliente, iEndCodigoDoCliente).trim());
	//								System.out.println("CodigoDoCliente: " + this.codigoDoCliente);
									existCodigoDoCliente=true;
								}
							}
							else
							{
								//System.out.println(lineClean);
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
								&&!lineClean.startsWith("codigo")
								&&!lineClean.startsWith("indexador")
								&&!lineClean.startsWith("rentabilidade")
								&&!lineClean.substring(0, 9).trim().toUpperCase().matches("^\\d.*?\\d$"))
						{
							RowFundoPetra rowFundo = new RowFundoPetra(lineCompleteFundo);
							this.blockFundo.getRowsFundo().add(rowFundo);
//							rowFundo.showRow();
						}
					}					
					else if(currentBlock.getName().equals("RENDA_FIXA"))
					{						
						if(!lineClean.replace("%", " ").toLowerCase().startsWith("renda fixa")
								&&!lineClean.startsWith("mercado nacional")
								&&!lineClean.startsWith("codigo")
								&&!lineClean.startsWith("valor da")
								&&!lineClean.startsWith("nota tes nacional")
								&&!lineClean.startsWith("operacoes")
								&&!lineClean.startsWith("total")
								&&!lineClean.startsWith("cota")
								&&!lineClean.startsWith("prov")
								&&!lineClean.startsWith("comp pl subordinada")
								&&!lineClean.startsWith("cdb-pos")
								//&&lineClean.matches("\\W")
								)
						{
							String lineComplete=lineClean.trim();
							String[] fieldsRF = lineClean.replaceAll(" +", " ").split(" ");
							if (fieldsRF.length < 17)
							{															
								//System.out.println("Incompleto: " + lineComplete);
								String lineTemp=lines.get(iLine+1);
								lineLower = lineTemp.trim().toLowerCase();
								lineClean = CleanString.cleanWithSpace(lineLower);
								lineComplete = lineComplete + " " + lineClean.trim();
								//System.out.println("Completo?: " + lineComplete);
								
								iLine=iLine+1;
							}	
							int countPercRendaFixa=0;
							for(int iCharRf=0;iCharRf<lineComplete.length();iCharRf++)
							{
								if(lineComplete.charAt(iCharRf)=='%')
								{
									countPercRendaFixa=countPercRendaFixa+1;
								}
							}
							
							if (countPercRendaFixa<5)
							{
								String lineTemp=lines.get(iLine+1);
								lineLower = lineTemp.trim().toLowerCase();
								lineClean = CleanString.cleanWithSpace(lineLower);
								lineComplete = lineComplete + " " + lineClean.trim();
								
								
								if(!lineComplete.replace("%", " ")
										.toLowerCase()
										.contains("renda fixa")
										&&!lineComplete.contains("mercado nacional")
										&&!lineComplete.contains("codigo")
										&&!lineComplete.contains("valor da")
										&&!lineComplete.contains("nota tes nacional")
										&&!lineComplete.contains("operacoes")
										&&!lineComplete.contains("total")
										&&!lineComplete.contains("cota")
										&&!lineComplete.contains("prov")
										&&!lineComplete.contains("comp pl subordinada")
										&&!lineComplete.contains("cdb-pos")
										//&&lineClean.matches("\\W")
										)
								{
									iLine=iLine+1;
								}
								else
								{
									iLine=iLine+1;
									continue;
								}
							}
							
							RowRendaFixaPetra rowRendaFixa = new RowRendaFixaPetra(lineComplete);
//							currentBlock.getRows().add(rowRendaFixa);
							this.blockRendaFixa.getRowsContasAPagarReceber().add(rowRendaFixa);
//							rowRendaFixa.showRow();								
						}
					}
					else if(currentBlock.getName().equals("OPERACOES_COMPROMISSADAS"))
					{
//						line=lines.get(iLine);
						String lineCleanOC=CleanString.cleanWithSpace(line.toLowerCase().trim());
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
							RowOperacoesCompromissadasPetra rowOperacoesCompromissadas = new RowOperacoesCompromissadasPetra(lineComplete);
							this.blockOperacoesCompromissadas.getRowsContasAPagarReceber().add(rowOperacoesCompromissadas);
//							rowOperacoesCompromissadas.showRow();
						}
					}
					else if(currentBlock.getName().equals("CONTAS_A_PAGAR_RECEBER"))
					{	
						String lineCleanContas = CleanString.cleanWithSpace(line.toLowerCase().trim());
						if(!lineCleanContas.startsWith("contas a pagar")
								&& !lineCleanContas.startsWith("total geral")
								&& !lineCleanContas.startsWith("descricao")
								)
						{
							int countPercentCPR=0;
							for (int iCharCPR=0;iCharCPR<lineCleanContas.length();iCharCPR++)
							{
								if(lineCleanContas.charAt(iCharCPR)=='%')
								{
									countPercentCPR=countPercentCPR+1;
								}
							}
							
							if(countPercentCPR<2 
									&& !lines.get(iLine-1).matches("[a-zA-Z]+"))
							{
								String lineTemp = CleanString.cleanWithSpace(lines.get(iLine+1).toLowerCase().trim());
//								System.out.println(lineTemp + "\n:" + lineTemp.contains("fechamento"));
								if(!lineTemp.contains("fechamento")
										&&!lineTemp.contains("data")
										&&!lineTemp.contains("cliente")
										&&!lineTemp.contains("/00")
										&&!lineTemp.contains("tesouraria")
										&&!lineTemp.contains("patrimonio")
										)
								{
									lineCleanContas = lineCleanContas + " " +lineTemp;
									iLine=iLine+1;
								}
								else
								{
									iLine=iLine+1;
									continue;
								}
							}
							RowContasAPagarReceberPetra rowContasAPagarReceber = new RowContasAPagarReceberPetra(lineCleanContas);
//							currentBlock.getRows().add(rowContasAPagarReceber);
							this.blockContasAPagarReceber.getRowsContasAPagarReceber().add(rowContasAPagarReceber);
//							rowContasAPagarReceber.showRow();
						}
					}
					else if(currentBlock.getName().equals("TESOURARIA"))
					{
						String lineCleanTesouraria = CleanString.cleanWithSpace(line.toLowerCase().trim());
//						System.out.println(lineCleanTesouraria);
						if(!lineCleanTesouraria.startsWith("tesouraria")
								&& !lineCleanTesouraria.startsWith("descricao")
								)						
						{		
							System.out.println(lineCleanTesouraria);
							//RowTesouraria rowTesouraria = new RowTesouraria(lineCleanTesouraria);
							//currentBlock.getRows().add(rowTesouraria);
							if(!existSaldoEmTesourariaReserva)
							{
								if(lineClean.contains("saldo em tesouraria reserva"))
								{
									int iBeginSaldoEmTesourariaReserva=0;
									int iEndSaldoEmTesourariaReserva=0;
									for(int i=0;i<lineClean.length();i++)
									{
										if(Character.isDigit(lineClean.charAt(i)))
										{
											iBeginSaldoEmTesourariaReserva=i;
											break;
										}
									}
									for(int i=iBeginSaldoEmTesourariaReserva+1;i<lineClean.length();i++)
									{
										if(Character.isWhitespace(lineClean.charAt(i)))
										{
											iEndSaldoEmTesourariaReserva=i;
											break;
										}
									}
									String stringSaldoEmTesourariaReserva=lineClean.substring(iBeginSaldoEmTesourariaReserva, iEndSaldoEmTesourariaReserva)
																			.trim()
																			.toUpperCase()
																			.replace(".", "")
																			.replace(",", ".")
																			;
									this.blockTesouraria.saldoEmTesourariaReserva=Double.parseDouble(stringSaldoEmTesourariaReserva);
									
//									System.out.println("SaldoEmTesourariaReserva: " + this.blockTesouraria.getSaldoEmTesourariaReserva());
									existSaldoEmTesourariaReserva=true;
								}	
							}
							if(!existSaldoEmTesouraria)
							{
								if(lineClean.contains("saldo em tesouraria"))
								{
									int iBeginSaldoEmTesouraria=0;
									int iEndSaldoEmTesouraria=0;
									for(int i=0;i<lineClean.length();i++)
									{
										if(Character.isDigit(lineClean.charAt(i)))
										{
											iBeginSaldoEmTesouraria=i;
											break;
										}
									}
									for(int i=iBeginSaldoEmTesouraria+1;i<lineClean.length();i++)
									{
										if(Character.isWhitespace(lineClean.charAt(i)))
										{
											iEndSaldoEmTesouraria=i;
											break;
										}
									}
									String stringSaldoEmTesouraria=lineClean.substring(iBeginSaldoEmTesouraria, iEndSaldoEmTesouraria)
																			.trim()
																			.toUpperCase()
																			.replace(".", "")
																			.replace(",", ".")
																			;
									if (stringSaldoEmTesouraria.contains(")"))
									{
										stringSaldoEmTesouraria = "-" + stringSaldoEmTesouraria.replace(")",""); 
									}
									this.blockTesouraria.saldoEmTesouraria=Double.parseDouble(stringSaldoEmTesouraria);
									
//									System.out.println("Saldo em tesouraria: " + this.blockTesouraria.getSaldoEmTesouraria());
									existSaldoEmTesouraria=true;
								}	
							}
							if(!existTotal)
							{
								if(lineClean.contains("total"))
								{
									int iBeginTotal=0;
									int iEndTotal=0;
									for(int i=0;i<lineClean.length();i++)
									{
										if(Character.isDigit(lineClean.charAt(i)))
										{
											iBeginTotal=i;
											break;
										}
									}
									for(int i=iBeginTotal+1;i<lineClean.length();i++)
									{
										if(Character.isWhitespace(lineClean.charAt(i)))
										{
											iEndTotal=i;
											break;
										}
									}
									String stringTotal=lineClean.substring(iBeginTotal, iEndTotal)
																			.trim()
																			.toUpperCase()
																			.replace(".", "")
																			.replace(",", ".")
																			;
									if (stringTotal.contains(")"))
									{
										stringTotal = "-" + stringTotal.replace(")",""); 
									}
									this.blockTesouraria.total=Double.parseDouble(stringTotal);
									
//									System.out.println("Total: " + this.blockTesouraria.getTotal());
									existSaldoEmTesouraria=true;
								}	
							}
							
//							System.out.println(lineCleanTesouraria);
						}
					}
					else if(currentBlock.getName().equals("PATRIMONIO"))
					{						
						String lineCleanPatrimonio=CleanString.cleanWithSpace(line).replaceAll(" +", " ");
						//System.out.println(lineCleanPatrimonio);
						if(!existPatrimonio)
						{
							String[] fields = lineCleanPatrimonio.split(" ");
							this.blockPatrimonio.valor=Double.parseDouble(fields[1]
														.replace(".", "")
														.replace(",", ".")
														);
//							System.out.println("PAT:" + this.blockPatrimonio.getValor());
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
								)
						{
							//System.out.println(lineCleanRent);
							if(lineCleanRent.startsWith("cota"))
							{
								lineCleanRent=lineCleanRent.replace("cota", "cota 0,0% 0,0%");
							}
							RowRentabilidadeAcumuladaPetra rowRentabilidadeAcumulada = new RowRentabilidadeAcumuladaPetra(lineCleanRent);
							this.blockRentabilidadeAcumulada.getRowsRentabilidadeAcumulada().add(rowRentabilidadeAcumulada);
//							rowRentabilidadeAcumulada.showRow();
						}
					}
					else if(currentBlock.getName().equals("RESUMO_COTAS"))
					{
						//System.out.println(line);
						String lineCleanRC=line.toLowerCase().trim();
						if(!existQuantidadeDeCotasBruta)
						{
							if(lineClean.contains("quantidade de cotas")&&lineClean.contains("bruta"))
							{
								int iBegin=0;
								for(int i = lineCleanRC.length()-1;i>0;i--)
								{
									if(Character.isWhitespace(lineClean.charAt(i)))
									{
										iBegin=i;
										break;
									}
								}
								String stringQuantidadeDeCotasBruta=lineCleanRC
																.substring(iBegin)
																.trim()
																.toUpperCase()
																.replace(".", "")
																.replace(",", ".")
																;
								if (stringQuantidadeDeCotasBruta.contains(")"))
								{
									stringQuantidadeDeCotasBruta = "-" + stringQuantidadeDeCotasBruta.replace(")",""); 
								}
								this.blockResumoDeCotas.quantidadeDeCotasBruta=Double.parseDouble(stringQuantidadeDeCotasBruta);
								
//								System.out.println("QuantidadeDeCotasBruta: " + this.blockResumoCotas.quantidadeDeCotasBruta);
								existQuantidadeDeCotasBruta=true;
							}	
						}
						if(!existQuantidadeDeCotas)
						{
							if(lineClean.contains("quantidade de cotas")&&!lineClean.contains("bruta"))
							{
								int iBegin=0;
								for(int i = lineCleanRC.length()-1;i>0;i--)
								{
									if(Character.isWhitespace(lineClean.charAt(i)))
									{
										iBegin=i;
										break;
									}
								}
								String stringQuantidadeDeCotas=lineCleanRC
																.substring(iBegin)
																.trim()
																.toUpperCase()
																.replace(".", "")
																.replace(",", ".")
																;
								if (stringQuantidadeDeCotas.contains(")"))
								{
									stringQuantidadeDeCotas = "-" + stringQuantidadeDeCotas.replace(")",""); 
								}
								this.blockResumoDeCotas.quantidadeDeCotas=Double.parseDouble(stringQuantidadeDeCotas);
								
//								System.out.println("QuantidadeDeCotas: " + this.blockResumoCotas.quantidadeDeCotas);
								existQuantidadeDeCotas=true;
							}	
						}
						if(!existValorDaCotaBrutaDePerformance)
						{
							if(lineClean.contains("valor da cota bruta")&&lineClean.contains("performance"))
							{
								int iBegin=0;
								for(int i = lineCleanRC.length()-1;i>0;i--)
								{
									if(Character.isWhitespace(lineClean.charAt(i)))
									{
										iBegin=i;
										break;
									}
								}
								String stringValorDaCotaBrutaDePerformance=lineCleanRC
																.substring(iBegin)
																.trim()
																.toUpperCase()
																.replace(".", "")
																.replace(",", ".")
																;
								if (stringValorDaCotaBrutaDePerformance.contains(")"))
								{
									stringValorDaCotaBrutaDePerformance = "-" + stringValorDaCotaBrutaDePerformance.replace(")",""); 
								}
								this.blockResumoDeCotas.valorDaCotaBrutaDePerformance=Double.parseDouble(stringValorDaCotaBrutaDePerformance);
								
//								System.out.println("ValorDaCotaBrutaDePerformance: " + this.blockResumoCotas.valorDaCotaBrutaDePerformance);
								existValorDaCotaBrutaDePerformance=true;
							}	
						}
						
						if(!existValorDaCotaUnitariaBruta)
						{
							if(lineClean.contains("valor da cota unitaria")&&lineClean.contains("bruta"))
							{
								int iBegin=0;
								for(int i = lineCleanRC.length()-1;i>0;i--)
								{
									if(Character.isWhitespace(lineClean.charAt(i)))
									{
										iBegin=i;
										break;
									}
								}
								String stringValorDeCotaUnitariaBruta=lineCleanRC
																.substring(iBegin)
																.trim()
																.toUpperCase()
																.replace(".", "")
																.replace(",", ".")
																;
								if (stringValorDeCotaUnitariaBruta.contains(")"))
								{
									stringValorDeCotaUnitariaBruta = "-" + stringValorDeCotaUnitariaBruta.replace(")",""); 
								}
								this.blockResumoDeCotas.valorDaCotaUnitariaBruta=Double.parseDouble(stringValorDeCotaUnitariaBruta);
								
//								System.out.println("ValorDaCotaUnitariaBruta: " + this.blockResumoCotas.valorDaCotaUnitariaBruta);
								existValorDaCotaUnitariaBruta=true;
							}	
						}
						if(!existValorDaCotaUnitaria)
						{
							if(lineClean.contains("valor da cota unitaria")&&!lineClean.contains("bruta"))
							{
								int iBegin=0;
								for(int i = lineCleanRC.length()-1;i>0;i--)
								{
									if(Character.isWhitespace(lineClean.charAt(i)))
									{
										iBegin=i;
										break;
									}
								}
								String stringValorDeCotaUnitaria=lineCleanRC
																.substring(iBegin)
																.trim()
																.toUpperCase()
																.replace(".", "")
																.replace(",", ".")
																;
								if (stringValorDeCotaUnitaria.contains(")"))
								{
									stringValorDeCotaUnitaria = "-" + stringValorDeCotaUnitaria.replace(")",""); 
								}
								this.blockResumoDeCotas.valorDaCotaUnitaria=Double.parseDouble(stringValorDeCotaUnitaria);
								
//								System.out.println("ValorDaCotaUnitaria: " + this.blockResumoCotas.valorDaCotaUnitaria);
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
