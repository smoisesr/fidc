package mvcapital.limites;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import mvcapital.entidade.Entidade;
import mvcapital.operation.CedenteAttempt;
import mvcapital.operation.MaioresCedentesAttempt;
import mvcapital.operation.MaioresSacadosAttempt;
import mvcapital.operation.Operacao;
import mvcapital.operation.SacadoAttempt;
import mvcapital.operation.TituloAttempt;

import com.mysql.jdbc.Connection;

public class HandlerLimites 
{
	private static DecimalFormat df = new DecimalFormat("#.", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		
	public HandlerLimites() 
	{
		
	}

	public static void assessLimites(Operacao op, Connection conn)
	{
		df.setMaximumFractionDigits(2);
		HandlerLimiteDeConcentracao.assessLimiteDeConcentracao(op, conn);
		HandlerLimiteDePrazo.assessLimiteDePrazo(op, conn);		
		HandlerLimiteDeValor.assessLimiteDeValor(op, conn);
		HandlerLimiteDeTaxa.assessLimiteDeTaxa(op, conn);
		checkLimites(op);
		writeDescription(op);
	}

	public static void checkLimites(Operacao op)
	{
		/**
		 * Checking concentration limits
		 */
		boolean allParticipantsConcentrationOK=true;
		
		if(op.getResumo().getCedenteAttempt().getLimiteDeConcentracao().isOk())
		{
			/**
			 * This is because the only assessment to do for cedente is
			 * the concentration limit
			 */
			op.getResumo().getCedenteAttempt().setAprovado(true);
		}
		else
		{
			allParticipantsConcentrationOK=false;
		}
		
		for(SacadoAttempt sacadoAttempt:op.getResumo().getSacadosAttempt())
		{			
			if(sacadoAttempt.getLimiteDeConcentracao().isOk())
			{
				/**
				 * This is because the only assessment to do for every sacado is
				 * the concentration limit
				 */
				sacadoAttempt.setAprovado(true);
			}
			else
			{
				allParticipantsConcentrationOK=false;
			}
		}
		
		if(op.getResumo().getMaioresCedentesAttempt().getLimiteDeConcentracao().getValor()!=0.0)
		{
			if(op.getResumo().getMaioresCedentesAttempt().getLimiteDeConcentracao().isOk())
			{
				/**
				 * Assessing accumulate concentration for cedentes
				 */
				op.getResumo().getMaioresCedentesAttempt().setAprovado(true);
			}
			else
			{
				allParticipantsConcentrationOK=false;
			}
		}
		else
		{
			op.getResumo().getMaioresCedentesAttempt().setAprovado(true);
		}

		if(op.getResumo().getMaioresSacadosAttempt().getLimiteDeConcentracao().getValor()!=0.0)
		{
			if(op.getResumo().getMaioresSacadosAttempt().getLimiteDeConcentracao().isOk())				
			{
				/**
				 * Assessing accumulate concentration for sacados
				 */				
				op.getResumo().getMaioresSacadosAttempt().setAprovado(true);
			}
			else
			{
				allParticipantsConcentrationOK=false;
			}
		}		
		else
		{
			op.getResumo().getMaioresSacadosAttempt().setAprovado(true);
		}
		
		op.setConcentracaoOK(allParticipantsConcentrationOK);
		
		/**
		 * Checking deadline limits
		 */
		
		boolean allParticipantsPrazoOK=true;
		
		for(TituloAttempt dca:op.getResumo().getDireitosCreditoriosAttempt())
		{
			boolean allLimitesDC=true;
			for(Limite limite:dca.getLimites())
			{
				if(!limite.isOk() && limite.getClass().getName().toLowerCase().contains("prazo"))
				{
					allParticipantsPrazoOK=false;
					allLimitesDC=false;
					break;
					
				}
			}
			
			if(allLimitesDC)
			{
				dca.setAprovado(true);
			}
		}
		
		op.setPrazoOK(allParticipantsPrazoOK);

		/**
		 * Checking tax rate limits
		 */
		
		boolean allParticipantsTaxaOK=true;
		
		for(TituloAttempt dca:op.getResumo().getDireitosCreditoriosAttempt())
		{
			boolean allLimitesDC=true;
			for(Limite limite:dca.getLimites())
			{
				if(!limite.isOk() && limite.getClass().getName().toLowerCase().contains("taxa"))
				{
					allParticipantsTaxaOK=false;
					allLimitesDC=false;
					break;
					
				}
			}
			
			if(allLimitesDC)
			{
				dca.setAprovado(true);
			}
		}
		
		
		op.setTaxaOK(allParticipantsTaxaOK);
		
		/**
		 * Checking value limits
		 */
		
		boolean allParticipantsValorOK=true;
		
		for(TituloAttempt dca:op.getResumo().getDireitosCreditoriosAttempt())
		{
			boolean allLimitesValorDC=true;
			for(Limite limite:dca.getLimites())
			{
				if(!limite.isOk() && limite.getClass().getName().toLowerCase().contains("valor"))
				{
					allParticipantsValorOK=false;
					allLimitesValorDC=false;
					break;
				}
			}
			
			if(allLimitesValorDC)
			{
				dca.setAprovado(true);
			}
		}

		op.setValorOK(allParticipantsValorOK);
		
		/**
		 * Checking if all participants are ok
		 */
		boolean allSacadosAprovado=true;
		/**
		 * Checking for sacados
		 */
		for(SacadoAttempt sacadoAttempt:op.getResumo().getSacadosAttempt())
		{			
			if(!sacadoAttempt.isAprovado())
			{
				allSacadosAprovado = false;
				break;
			}
		}
		
		if(op.getResumo().getSacadosAttempt().size()==0)
		{
			allSacadosAprovado=false;
		}
		
		/**
		 * Checking for all DCA 
		 */
		boolean allDireitosCreditoriosAttempt=true;
		for(TituloAttempt dca:op.getResumo().getDireitosCreditoriosAttempt())
		{
			if(!dca.isAprovado())
			{
				allDireitosCreditoriosAttempt=false;
				break;
			}
		}
		
		/**
		 * Checking 
		 * Limite de concentracao
		 * for cedente 
		 * and maiorescedentes 
		 * and maioressacados
		 *  
		 * Limite de prazo
		 * for DCA
		 */
		if(op.getResumo().getCedenteAttempt().isAprovado() 
				&& allSacadosAprovado
				&& op.getResumo().getMaioresCedentesAttempt().isAprovado()
				&& op.getResumo().getMaioresSacadosAttempt().isAprovado()
				&& allDireitosCreditoriosAttempt
				)
		{
			op.getResumo().setAprovado(true);
			op.setAprovado(true);
		}
		else
		{
			op.getResumo().setAprovado(false);
			op.setAprovado(false);
		}		
	}

	public static String descriptionMaioresCedentes(MaioresCedentesAttempt maioresCedentesAttempt)
	{
		String descriptionMaioresCedentes="";
		String stringAprovado = "Aprovado";
		String stringOKConcentracao = "OK";
		
		if(maioresCedentesAttempt.getLimiteDeConcentracao().getValor()==0)
		{
			return "";
		}
		else
		{
			/**
			 * Description regarding concentration
			 */
			if(!maioresCedentesAttempt.getLimiteDeConcentracao().isOk())
			{
				stringOKConcentracao ="BAD!";
			}
	
	
			String stringConcentracao = "Concentracao"
					+ "," + df.format(maioresCedentesAttempt.getValorPresente())
					+ "," + df.format(maioresCedentesAttempt.getMaximoOperar())
					+ "," + df.format(maioresCedentesAttempt.getTotalOperar())
					+ "," + df.format(maioresCedentesAttempt.getNovoValorPresente())
					+ "," + df.format(maioresCedentesAttempt.getNovoMaximoOperar())
					+ "," + df.format(maioresCedentesAttempt.getExcesso())
					+ "," + stringOKConcentracao
					; 
	
			/**
			 * Description regarding approve
			 */		
			if(!maioresCedentesAttempt.isAprovado())
			{
				stringAprovado = "Reprovado";
			}
	
			String nomeCedentes="";
			int iCedente=0;
			for(Entidade cedente:maioresCedentesAttempt.getCedentes())
			{
				if(iCedente==0)
				{
					nomeCedentes=nomeCedentes+cedente.getNome();
				}
				else
				{
					nomeCedentes=nomeCedentes+"%"+cedente.getNome();
				}
				iCedente=iCedente+1;
			}
			
			descriptionMaioresCedentes = "MaioresCedentes"
										+ "#" + nomeCedentes
										+ "#" + stringAprovado
										+ "#" + stringConcentracao;		
			return descriptionMaioresCedentes;
		}
	}
	
	public static String descriptionMaioresSacados(MaioresSacadosAttempt maioresSacadosAttempt)
	{
		String descriptionMaioresSacados="";
		String stringAprovado = "Aprovado";
		String stringOKConcentracao = "OK";
		
		if(maioresSacadosAttempt.getLimiteDeConcentracao().getValor()==0)
		{
			return "";
		}
		else
		{		
			/**
			 * Description regarding concentration
			 */
			if(!maioresSacadosAttempt.getLimiteDeConcentracao().isOk())
			{
				stringOKConcentracao ="BAD!";
			}
	
	
			String stringConcentracao = "Concentracao"
					+ "," + df.format(maioresSacadosAttempt.getValorPresente())
					+ "," + df.format(maioresSacadosAttempt.getMaximoOperar())
					+ "," + df.format(maioresSacadosAttempt.getTotalOperar())
					+ "," + df.format(maioresSacadosAttempt.getNovoValorPresente())
					+ "," + df.format(maioresSacadosAttempt.getNovoMaximoOperar())
					+ "," + df.format(maioresSacadosAttempt.getExcesso())
					+ "," + stringOKConcentracao
					; 
	
			/**
			 * Description regarding approve
			 */		
			if(!maioresSacadosAttempt.isAprovado())
			{
				stringAprovado = "Reprovado";
			}
			
			String nomeSacados="";
			int iSacado=0;
			for(Entidade sacado:maioresSacadosAttempt.getSacados())
			{
				if(iSacado==0)
				{
					nomeSacados=nomeSacados+sacado.getNome();
				}
				else
				{
					nomeSacados=nomeSacados+"%"+sacado.getNome();
				}
				iSacado=iSacado+1;
			}
						
	
			descriptionMaioresSacados = "MaioresSacados"
										+ "#" + nomeSacados
										+ "#" + stringAprovado
										+ "#" + stringConcentracao;		
			return descriptionMaioresSacados;	
		}
	}
	
	public static String descriptionSacado(SacadoAttempt sacadoAttempt)
	{
//		Sacado#DANIEL SOARES DE SOUZA#Aprovado#828.04#814770.79#OK;
		
		String descriptionSacado = "";
		String stringAprovado = "Aprovado";
		String stringOKConcentracao = "OK";

		/**
		 * Description regarding concentration
		 */
		if(!sacadoAttempt.getLimiteDeConcentracao().isOk())
		{
			stringOKConcentracao ="BAD!";
		}
		
		String stringConcentracao = "Concentracao"
									+ "," + sacadoAttempt.getValorPresente()
									+ "," + sacadoAttempt.getMaximoOperar()
									+ "," + sacadoAttempt.getTotalOperar()
									+ "," + sacadoAttempt.getNovoValorPresente()
									+ "," + sacadoAttempt.getNovoMaximoOperar()
									+ "," + sacadoAttempt.getExcesso()				
									+ "," + stringOKConcentracao
									; 

		/**
		 * Description regarding approve
		 */
		if(!sacadoAttempt.isAprovado())
		{			
			stringAprovado = "Reprovado";
		}

		descriptionSacado = "Sacado"
							+ "#" + sacadoAttempt.getSacado().getNome()  
							+ "#" + stringAprovado
							+ "#" + stringConcentracao; 	
		
		return descriptionSacado;
	}

	public static String descriptionCedente(CedenteAttempt cedenteAttempt)
	{
		String descriptionCedente="";
		String stringAprovado = "Aprovado";
		String stringOKConcentracao = "OK";

		/**
		 * Description regarding concentration
		 */
		if(!cedenteAttempt.getLimiteDeConcentracao().isOk())
		{
			stringOKConcentracao ="BAD!";
		}


		String stringConcentracao = "Concentracao"
									+ "," + cedenteAttempt.getValorPresente()
									+ "," + cedenteAttempt.getMaximoOperar()
									+ "," + cedenteAttempt.getTotalOperar()
									+ "," + cedenteAttempt.getNovoValorPresente()
									+ "," + cedenteAttempt.getNovoMaximoOperar()
									+ "," + cedenteAttempt.getExcesso()				
									+ "," + stringOKConcentracao
									; 

		/**
		 * Description regarding approve
		 */		
		if(!cedenteAttempt.isAprovado())
		{
			stringAprovado = "Reprovado";
		}

		descriptionCedente = "Cedente"
							+ "#" + cedenteAttempt.getCedente().getNome()
							+ "#" + stringAprovado
							+ "#" + stringConcentracao;		
		
		return descriptionCedente;
	}
	
	public static String descriptionDireitoCreditorio(TituloAttempt dca)
	{
		String stringLimitePrazo = "";
		String stringLimiteValor = "";
		String stringAprovado = "Aprovado";
		
		/**
		 * Description regarding prazo
		 */
		//		minimoDiasCorridos
		//		maximoDiasCorridos
		//		minimoDiasUteis
		//		maximoDiasUteis		
		
		for(Limite limite:dca.getLimites())
		{
			if(limite.isOk())
			{
				if(limite.getTipoDeLimite().equals("minimoDiasCorridos"))
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMinimoDiasCorridos"
								+ "%" + dca.getTitulo().getPrazoCorrido()
								+ "%" + ((LimiteDePrazo)limite).getValor()
								+ "%" + "OK";
				}
				else if(limite.getTipoDeLimite().equals("maximoDiasCorridos"))
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMaximoDiasCorridos"
								+ "%" + dca.getTitulo().getPrazoCorrido()
								+ "%" + ((LimiteDePrazo)limite).getValor()
								+ "%" + "OK";
				}
				else if(limite.getTipoDeLimite().equals("minimoDiasUteis"))
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMinimoDiasUteis"
								+ "%" + dca.getTitulo().getPrazoUtil()
								+ "%" + ((LimiteDePrazo)limite).getValor()
								+ "%" + "OK";
				}
				else if(limite.getTipoDeLimite().equals("maximoDiasUteis"))
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMaximoDiasUteis"
								+ "%" + dca.getTitulo().getPrazoUtil()
								+ "%" + ((LimiteDePrazo)limite).getValor()
								+ "%" + "OK";
				}				
				else if(limite.getTipoDeLimite().equals("valorMinimo"))
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "ValorMinimo"
							+ "%" + dca.getTitulo().getValorAquisicao()
							+ "%" + ((LimiteDeValor)limite).getValor()
							+ "%" + "OK";					
				}
				else if(limite.getTipoDeLimite().equals("valorMaximo"))
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "ValorMaximo"
							+ "%" + dca.getTitulo().getValorAquisicao()
							+ "%" + ((LimiteDeValor)limite).getValor()
							+ "%" + "OK";					
				}
				else if(limite.getTipoDeLimite().equals("taxaMinimaAoAno"))
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "TaxaMinimaAoAno"
							+ "%" + dca.getTitulo().getTaxaAoAno()
							+ "%" + ((LimiteDeTaxa)limite).getValor()
							+ "%" + "OK";					
				}
				else if(limite.getTipoDeLimite().equals("taxaMinimaDiariaSobreCDI"))
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "TaxaMinimaDiariaSobreCDI"
							+ "%" + dca.getTaxaDiariaSobreCDI()
							+ "%" + ((LimiteDeTaxa)limite).getValor()
							+ "%" + "OK";					
				}								
			}
			else
			{
				if(limite.getTipoDeLimite().equals("minimoDiasCorridos"))
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMinimoDiasCorridos"
								+ "%" + dca.getTitulo().getPrazoCorrido()
								+ "%" + ((LimiteDePrazo)limite).getValor()
								+ "%" + "BAD!";
				}
				else if(limite.getTipoDeLimite().equals("maximoDiasCorridos"))
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMaximoDiasCorridos"
								+ "%" + dca.getTitulo().getPrazoCorrido()
								+ "%" + ((LimiteDePrazo)limite).getValor()							
								+ "%" + "BAD!";
				}
				else if(limite.getTipoDeLimite().equals("minimoDiasUteis"))
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMinimoDiasUteis"
								+ "%" + dca.getTitulo().getPrazoUtil()
								+ "%" + ((LimiteDePrazo)limite).getValor()							
								+ "%" + "BAD!";
				}
				else if(limite.getTipoDeLimite().equals("maximoDiasUteis"))
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMaximoDiasUteis"
								+ "%" + dca.getTitulo().getPrazoUtil()
								+ "%" + ((LimiteDePrazo)limite).getValor()							
								+ "%" + "BAD!";
				}
				else if(limite.getTipoDeLimite().equals("valorMinimo"))
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "ValorMinimo"
							+ "%" + dca.getTitulo().getValorAquisicao()
							+ "%" + ((LimiteDeValor)limite).getValor()
							+ "%" + "BAD!";					
				}
				else if(limite.getTipoDeLimite().equals("valorMaximo"))
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "ValorMaximo"
							+ "%" + dca.getTitulo().getValorAquisicao()
							+ "%" + ((LimiteDeValor)limite).getValor()
							+ "%" + "BAD!";				
				}			
				else if(limite.getTipoDeLimite().equals("taxaMinimaAoAno"))
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "TaxaMinimaAoAno"
							+ "%" + dca.getTitulo().getTaxaAoAno()
							+ "%" + ((LimiteDeTaxa)limite).getValor()
							+ "%" + "BAD!";					
				}
				else if(limite.getTipoDeLimite().equals("taxaMinimaDiariaSobreCDI"))
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "TaxaMinimaDiariaSobreCDI"
							+ "%" + dca.getTaxaDiariaSobreCDI()
							+ "%" + ((LimiteDeTaxa)limite).getValor()
							+ "%" + "BAD!";				
				}												
			}
		}
		
		if(!dca.isAprovado())
		{
			stringAprovado="Reprovado";
		}
		
		String descriptionDCA = "Recebivel"
								+ "#" + dca.getTitulo().getTipoTitulo().getDescricao() 
										+ "%" + dca.getTitulo().getSeuNumero()
								+ "#" + stringAprovado
								+ stringLimitePrazo
								+ stringLimiteValor
								;		
		
		return descriptionDCA;
	}
	
	public static void writeDescription(Operacao op)
	{		
		String description="";
		
		description=description+"Operacao";
		if(op.isConcentracaoOK())
		{
			description=description + "#" + "Concentracao,OK";
		}
		else
		{
			description=description+ "#" + "Concentracao,BAD!";			
		}
		if(op.isPrazoOK())
		{
			description=description + "#" + "Prazo,OK";
		}
		else
		{
			description=description+ "#" + "Prazo,BAD!";			
		}
		if(op.isValorOK())
		{
			description=description + "#" + "Valor,OK";
		}
		else
		{
			description=description+ "#" + "Valor,BAD!";			
		}		
		if(op.isTaxaOK())
		{
			description=description + "#" + "Taxa,OK";
		}
		else
		{
			description=description+ "#" + "Taxa,BAD!";			
		}		
		
		for(SacadoAttempt sacadoAttempt:op.getResumo().getSacadosAttempt())
		{
			description=description + ";" + descriptionSacado(sacadoAttempt);
		}
		
		description = description + ";" + descriptionCedente(op.getResumo().getCedenteAttempt());
		
		if(!descriptionMaioresCedentes(op.getResumo().getMaioresCedentesAttempt()).equals(""))
		{
			description = description + ";" + descriptionMaioresCedentes(op.getResumo().getMaioresCedentesAttempt());		
			description = description + ";" + descriptionMaioresSacados(op.getResumo().getMaioresSacadosAttempt());
		}
		
		for(TituloAttempt dca:op.getResumo().getDireitosCreditoriosAttempt())
		{
			description = description + ";" + descriptionDireitoCreditorio(dca);
		}
		
		System.out.println();
		op.getResumo().setDescription(description);
		System.out.println(op.getResumo().getDescription());

	}
}
