package limites;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.mysql.jdbc.Connection;

import entidade.Entidade;
import operation.CedenteAttempt;
import operation.DireitoCreditorioAttempt;
import operation.MaioresCedentesAttempt;
import operation.MaioresSacadosAttempt;
import operation.Operacao;
import operation.SacadoAttempt;

public class HandlerLimites 
{
	private static DecimalFormat df = new DecimalFormat("#.", DecimalFormatSymbols.getInstance(Locale.ENGLISH)); //$NON-NLS-1$
		
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
		
		for(DireitoCreditorioAttempt dca:op.getResumo().getDireitosCreditoriosAttempt())
		{
			boolean allLimitesDC=true;
			for(Limite limite:dca.getLimites())
			{
				if(!limite.isOk() && limite.getClass().getName().toLowerCase().contains("prazo")) //$NON-NLS-1$
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
		
		for(DireitoCreditorioAttempt dca:op.getResumo().getDireitosCreditoriosAttempt())
		{
			boolean allLimitesDC=true;
			for(Limite limite:dca.getLimites())
			{
				System.out.println("Limite " + limite.isOk()); //$NON-NLS-1$
				System.out.println("TipoLimite " + limite.getClass().getName()); //$NON-NLS-1$
				System.out.println("TipoLimiteString " + limite.getTipoDeLimite()); //$NON-NLS-1$
				
				if(!limite.isOk() && limite.getClass().getName().toLowerCase().contains("taxa")) //$NON-NLS-1$
				{
					allParticipantsTaxaOK=false;
					allLimitesDC=false;
					
					System.out.println("Something bad!"); //$NON-NLS-1$
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
		
		for(DireitoCreditorioAttempt dca:op.getResumo().getDireitosCreditoriosAttempt())
		{
			boolean allLimitesValorDC=true;
			for(Limite limite:dca.getLimites())
			{
				if(!limite.isOk() && limite.getClass().getName().toLowerCase().contains("valor")) //$NON-NLS-1$
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
		for(DireitoCreditorioAttempt dca:op.getResumo().getDireitosCreditoriosAttempt())
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
		String descriptionMaioresCedentes=""; //$NON-NLS-1$
		String stringAprovado = "Aprovado"; //$NON-NLS-1$
		String stringOKConcentracao = "OK"; //$NON-NLS-1$
		
		if(maioresCedentesAttempt.getLimiteDeConcentracao().getValor()==0)
		{
			return ""; //$NON-NLS-1$
		}
		else
		{
			/**
			 * Description regarding concentration
			 */
			if(!maioresCedentesAttempt.getLimiteDeConcentracao().isOk())
			{
				stringOKConcentracao ="BAD!"; //$NON-NLS-1$
			}
	
	
			String stringConcentracao = "Concentracao" //$NON-NLS-1$
					+ "," + df.format(maioresCedentesAttempt.getValorPresente()) //$NON-NLS-1$
					+ "," + df.format(maioresCedentesAttempt.getMaximoOperar()) //$NON-NLS-1$
					+ "," + df.format(maioresCedentesAttempt.getTotalOperar()) //$NON-NLS-1$
					+ "," + df.format(maioresCedentesAttempt.getNovoValorPresente()) //$NON-NLS-1$
					+ "," + df.format(maioresCedentesAttempt.getNovoMaximoOperar()) //$NON-NLS-1$
					+ "," + df.format(maioresCedentesAttempt.getExcesso()) //$NON-NLS-1$
					+ "," + stringOKConcentracao //$NON-NLS-1$
					; 
	
			/**
			 * Description regarding approve
			 */		
			if(!maioresCedentesAttempt.isAprovado())
			{
				stringAprovado = "Reprovado"; //$NON-NLS-1$
			}
	
			String nomeCedentes=""; //$NON-NLS-1$
			int iCedente=0;
			for(Entidade cedente:maioresCedentesAttempt.getCedentes())
			{
				if(iCedente==0)
				{
					nomeCedentes=nomeCedentes+cedente.getNome();
				}
				else
				{
					nomeCedentes=nomeCedentes+"%"+cedente.getNome(); //$NON-NLS-1$
				}
				iCedente=iCedente+1;
			}
			
			descriptionMaioresCedentes = "MaioresCedentes" //$NON-NLS-1$
										+ "#" + nomeCedentes //$NON-NLS-1$
										+ "#" + stringAprovado //$NON-NLS-1$
										+ "#" + stringConcentracao;		 //$NON-NLS-1$
			return descriptionMaioresCedentes;
		}
	}
	
	public static String descriptionMaioresSacados(MaioresSacadosAttempt maioresSacadosAttempt)
	{
		String descriptionMaioresSacados=""; //$NON-NLS-1$
		String stringAprovado = "Aprovado"; //$NON-NLS-1$
		String stringOKConcentracao = "OK"; //$NON-NLS-1$
		
		if(maioresSacadosAttempt.getLimiteDeConcentracao().getValor()==0)
		{
			return ""; //$NON-NLS-1$
		}
		else
		{		
			/**
			 * Description regarding concentration
			 */
			if(!maioresSacadosAttempt.getLimiteDeConcentracao().isOk())
			{
				stringOKConcentracao ="BAD!"; //$NON-NLS-1$
			}
	
	
			String stringConcentracao = "Concentracao" //$NON-NLS-1$
					+ "," + df.format(maioresSacadosAttempt.getValorPresente()) //$NON-NLS-1$
					+ "," + df.format(maioresSacadosAttempt.getMaximoOperar()) //$NON-NLS-1$
					+ "," + df.format(maioresSacadosAttempt.getTotalOperar()) //$NON-NLS-1$
					+ "," + df.format(maioresSacadosAttempt.getNovoValorPresente()) //$NON-NLS-1$
					+ "," + df.format(maioresSacadosAttempt.getNovoMaximoOperar()) //$NON-NLS-1$
					+ "," + df.format(maioresSacadosAttempt.getExcesso()) //$NON-NLS-1$
					+ "," + stringOKConcentracao //$NON-NLS-1$
					; 
	
			/**
			 * Description regarding approve
			 */		
			if(!maioresSacadosAttempt.isAprovado())
			{
				stringAprovado = "Reprovado"; //$NON-NLS-1$
			}
			
			String nomeSacados=""; //$NON-NLS-1$
			int iSacado=0;
			for(Entidade sacado:maioresSacadosAttempt.getSacados())
			{
				if(iSacado==0)
				{
					nomeSacados=nomeSacados+sacado.getNome();
				}
				else
				{
					nomeSacados=nomeSacados+"%"+sacado.getNome(); //$NON-NLS-1$
				}
				iSacado=iSacado+1;
			}
						
	
			descriptionMaioresSacados = "MaioresSacados" //$NON-NLS-1$
										+ "#" + nomeSacados //$NON-NLS-1$
										+ "#" + stringAprovado //$NON-NLS-1$
										+ "#" + stringConcentracao;		 //$NON-NLS-1$
			return descriptionMaioresSacados;	
		}
	}
	
	public static String descriptionSacado(SacadoAttempt sacadoAttempt)
	{
//		Sacado#DANIEL SOARES DE SOUZA#Aprovado#828.04#814770.79#OK;
		
		String descriptionSacado = ""; //$NON-NLS-1$
		String stringAprovado = "Aprovado"; //$NON-NLS-1$
		String stringOKConcentracao = "OK"; //$NON-NLS-1$

		/**
		 * Description regarding concentration
		 */
		if(!sacadoAttempt.getLimiteDeConcentracao().isOk())
		{
			stringOKConcentracao ="BAD!"; //$NON-NLS-1$
		}
		
		String stringConcentracao = "Concentracao" //$NON-NLS-1$
									+ "," + sacadoAttempt.getValorPresente() //$NON-NLS-1$
									+ "," + sacadoAttempt.getMaximoOperar() //$NON-NLS-1$
									+ "," + sacadoAttempt.getTotalOperar() //$NON-NLS-1$
									+ "," + sacadoAttempt.getNovoValorPresente() //$NON-NLS-1$
									+ "," + sacadoAttempt.getNovoMaximoOperar() //$NON-NLS-1$
									+ "," + sacadoAttempt.getExcesso()				 //$NON-NLS-1$
									+ "," + stringOKConcentracao //$NON-NLS-1$
									; 

		/**
		 * Description regarding approve
		 */
		if(!sacadoAttempt.isAprovado())
		{			
			stringAprovado = "Reprovado"; //$NON-NLS-1$
		}

		descriptionSacado = "Sacado" //$NON-NLS-1$
							+ "#" + sacadoAttempt.getSacado().getNome()   //$NON-NLS-1$
							+ "#" + stringAprovado //$NON-NLS-1$
							+ "#" + stringConcentracao; 	 //$NON-NLS-1$
		
		return descriptionSacado;
	}

	public static String descriptionCedente(CedenteAttempt cedenteAttempt)
	{
		String descriptionCedente=""; //$NON-NLS-1$
		String stringAprovado = "Aprovado"; //$NON-NLS-1$
		String stringOKConcentracao = "OK"; //$NON-NLS-1$

		/**
		 * Description regarding concentration
		 */
		if(!cedenteAttempt.getLimiteDeConcentracao().isOk())
		{
			stringOKConcentracao ="BAD!"; //$NON-NLS-1$
		}


		String stringConcentracao = "Concentracao" //$NON-NLS-1$
									+ "," + cedenteAttempt.getValorPresente() //$NON-NLS-1$
									+ "," + cedenteAttempt.getMaximoOperar() //$NON-NLS-1$
									+ "," + cedenteAttempt.getTotalOperar() //$NON-NLS-1$
									+ "," + cedenteAttempt.getNovoValorPresente() //$NON-NLS-1$
									+ "," + cedenteAttempt.getNovoMaximoOperar() //$NON-NLS-1$
									+ "," + cedenteAttempt.getExcesso()				 //$NON-NLS-1$
									+ "," + stringOKConcentracao //$NON-NLS-1$
									; 

		/**
		 * Description regarding approve
		 */		
		if(!cedenteAttempt.isAprovado())
		{
			stringAprovado = "Reprovado"; //$NON-NLS-1$
		}

		descriptionCedente = "Cedente" //$NON-NLS-1$
							+ "#" + cedenteAttempt.getCedente().getNome() //$NON-NLS-1$
							+ "#" + stringAprovado //$NON-NLS-1$
							+ "#" + stringConcentracao;		 //$NON-NLS-1$
		
		return descriptionCedente;
	}
	
	public static String descriptionDireitoCreditorio(DireitoCreditorioAttempt dca)
	{
		String stringLimitePrazo = ""; //$NON-NLS-1$
		String stringLimiteValor = ""; //$NON-NLS-1$
		String stringAprovado = "Aprovado"; //$NON-NLS-1$
		
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
				if(limite.getTipoDeLimite().equals("minimoDiasCorridos")) //$NON-NLS-1$
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMinimoDiasCorridos" //$NON-NLS-1$ //$NON-NLS-2$
								+ "%" + dca.getDireitoCreditorio().getPrazoDiasCorridos() //$NON-NLS-1$
								+ "%" + ((LimiteDePrazo)limite).getValor() //$NON-NLS-1$
								+ "%" + "OK"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				else if(limite.getTipoDeLimite().equals("maximoDiasCorridos")) //$NON-NLS-1$
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMaximoDiasCorridos" //$NON-NLS-1$ //$NON-NLS-2$
								+ "%" + dca.getDireitoCreditorio().getPrazoDiasCorridos() //$NON-NLS-1$
								+ "%" + ((LimiteDePrazo)limite).getValor() //$NON-NLS-1$
								+ "%" + "OK"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				else if(limite.getTipoDeLimite().equals("minimoDiasUteis")) //$NON-NLS-1$
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMinimoDiasUteis" //$NON-NLS-1$ //$NON-NLS-2$
								+ "%" + dca.getDireitoCreditorio().getPrazoDiasUteis() //$NON-NLS-1$
								+ "%" + ((LimiteDePrazo)limite).getValor() //$NON-NLS-1$
								+ "%" + "OK"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				else if(limite.getTipoDeLimite().equals("maximoDiasUteis")) //$NON-NLS-1$
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMaximoDiasUteis" //$NON-NLS-1$ //$NON-NLS-2$
								+ "%" + dca.getDireitoCreditorio().getPrazoDiasUteis() //$NON-NLS-1$
								+ "%" + ((LimiteDePrazo)limite).getValor() //$NON-NLS-1$
								+ "%" + "OK"; //$NON-NLS-1$ //$NON-NLS-2$
				}				
				else if(limite.getTipoDeLimite().equals("valorMinimo")) //$NON-NLS-1$
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "ValorMinimo" //$NON-NLS-1$ //$NON-NLS-2$
							+ "%" + dca.getDireitoCreditorio().getValorDeAquisicao() //$NON-NLS-1$
							+ "%" + ((LimiteDeValor)limite).getValor() //$NON-NLS-1$
							+ "%" + "OK";					 //$NON-NLS-1$ //$NON-NLS-2$
				}
				else if(limite.getTipoDeLimite().equals("valorMaximo")) //$NON-NLS-1$
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "ValorMaximo" //$NON-NLS-1$ //$NON-NLS-2$
							+ "%" + dca.getDireitoCreditorio().getValorDeAquisicao() //$NON-NLS-1$
							+ "%" + ((LimiteDeValor)limite).getValor() //$NON-NLS-1$
							+ "%" + "OK";					 //$NON-NLS-1$ //$NON-NLS-2$
				}
				else if(limite.getTipoDeLimite().equals("taxaMinimaAoAno")) //$NON-NLS-1$
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "TaxaMinimaAoAno" //$NON-NLS-1$ //$NON-NLS-2$
							+ "%" + dca.getDireitoCreditorio().getTaxaAoAno() //$NON-NLS-1$
							+ "%" + ((LimiteDeTaxa)limite).getValor() //$NON-NLS-1$
							+ "%" + "OK";					 //$NON-NLS-1$ //$NON-NLS-2$
				}
				else if(limite.getTipoDeLimite().equals("taxaMinimaDiariaSobreCDI")) //$NON-NLS-1$
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "TaxaMinimaDiariaSobreCDI" //$NON-NLS-1$ //$NON-NLS-2$
							+ "%" + dca.getTaxaDiariaSobreCDI() //$NON-NLS-1$
							+ "%" + ((LimiteDeTaxa)limite).getValor() //$NON-NLS-1$
							+ "%" + "OK";					 //$NON-NLS-1$ //$NON-NLS-2$
				}								
			}
			else
			{
				if(limite.getTipoDeLimite().equals("minimoDiasCorridos")) //$NON-NLS-1$
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMinimoDiasCorridos" //$NON-NLS-1$ //$NON-NLS-2$
								+ "%" + dca.getDireitoCreditorio().getPrazoDiasCorridos() //$NON-NLS-1$
								+ "%" + ((LimiteDePrazo)limite).getValor() //$NON-NLS-1$
								+ "%" + "BAD!"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				else if(limite.getTipoDeLimite().equals("maximoDiasCorridos")) //$NON-NLS-1$
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMaximoDiasCorridos" //$NON-NLS-1$ //$NON-NLS-2$
								+ "%" + dca.getDireitoCreditorio().getPrazoDiasCorridos() //$NON-NLS-1$
								+ "%" + ((LimiteDePrazo)limite).getValor()							 //$NON-NLS-1$
								+ "%" + "BAD!"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				else if(limite.getTipoDeLimite().equals("minimoDiasUteis")) //$NON-NLS-1$
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMinimoDiasUteis" //$NON-NLS-1$ //$NON-NLS-2$
								+ "%" + dca.getDireitoCreditorio().getPrazoDiasUteis() //$NON-NLS-1$
								+ "%" + ((LimiteDePrazo)limite).getValor()							 //$NON-NLS-1$
								+ "%" + "BAD!"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				else if(limite.getTipoDeLimite().equals("maximoDiasUteis")) //$NON-NLS-1$
				{
					stringLimitePrazo=stringLimitePrazo
								+ "#" + "PrazoMaximoDiasUteis" //$NON-NLS-1$ //$NON-NLS-2$
								+ "%" + dca.getDireitoCreditorio().getPrazoDiasUteis() //$NON-NLS-1$
								+ "%" + ((LimiteDePrazo)limite).getValor()							 //$NON-NLS-1$
								+ "%" + "BAD!"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				else if(limite.getTipoDeLimite().equals("valorMinimo")) //$NON-NLS-1$
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "ValorMinimo" //$NON-NLS-1$ //$NON-NLS-2$
							+ "%" + dca.getDireitoCreditorio().getValorDeAquisicao() //$NON-NLS-1$
							+ "%" + ((LimiteDeValor)limite).getValor() //$NON-NLS-1$
							+ "%" + "BAD!";					 //$NON-NLS-1$ //$NON-NLS-2$
				}
				else if(limite.getTipoDeLimite().equals("valorMaximo")) //$NON-NLS-1$
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "ValorMaximo" //$NON-NLS-1$ //$NON-NLS-2$
							+ "%" + dca.getDireitoCreditorio().getValorDeAquisicao() //$NON-NLS-1$
							+ "%" + ((LimiteDeValor)limite).getValor() //$NON-NLS-1$
							+ "%" + "BAD!";				 //$NON-NLS-1$ //$NON-NLS-2$
				}			
				else if(limite.getTipoDeLimite().equals("taxaMinimaAoAno")) //$NON-NLS-1$
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "TaxaMinimaAoAno" //$NON-NLS-1$ //$NON-NLS-2$
							+ "%" + dca.getDireitoCreditorio().getTaxaAoAno() //$NON-NLS-1$
							+ "%" + ((LimiteDeTaxa)limite).getValor() //$NON-NLS-1$
							+ "%" + "BAD!";					 //$NON-NLS-1$ //$NON-NLS-2$
				}
				else if(limite.getTipoDeLimite().equals("taxaMinimaDiariaSobreCDI")) //$NON-NLS-1$
				{
					stringLimiteValor=stringLimiteValor
							+ "#" + "TaxaMinimaDiariaSobreCDI" //$NON-NLS-1$ //$NON-NLS-2$
							+ "%" + dca.getTaxaDiariaSobreCDI() //$NON-NLS-1$
							+ "%" + ((LimiteDeTaxa)limite).getValor() //$NON-NLS-1$
							+ "%" + "BAD!";				 //$NON-NLS-1$ //$NON-NLS-2$
				}												
			}
		}
		
		if(!dca.isAprovado())
		{
			stringAprovado="Reprovado"; //$NON-NLS-1$
		}
		
		String descriptionDCA = "Recebivel" //$NON-NLS-1$
								+ "#" + dca.getDireitoCreditorio().getTipoDeRecebivel().getDescricao()  //$NON-NLS-1$
										+ "%" + dca.getDireitoCreditorio().getCampoChave() //$NON-NLS-1$
								+ "#" + stringAprovado //$NON-NLS-1$
								+ stringLimitePrazo
								+ stringLimiteValor
								;		
		
		return descriptionDCA;
	}
	
	public static void writeDescription(Operacao op)
	{		
		String description=""; //$NON-NLS-1$
		
		description=description+"Operacao"; //$NON-NLS-1$
		if(op.isConcentracaoOK())
		{
			description=description + "#" + "Concentracao,OK"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
		{
			description=description+ "#" + "Concentracao,BAD!";			 //$NON-NLS-1$ //$NON-NLS-2$
		}
		if(op.isPrazoOK())
		{
			description=description + "#" + "Prazo,OK"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
		{
			description=description+ "#" + "Prazo,BAD!";			 //$NON-NLS-1$ //$NON-NLS-2$
		}
		if(op.isValorOK())
		{
			description=description + "#" + "Valor,OK"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
		{
			description=description+ "#" + "Valor,BAD!";			 //$NON-NLS-1$ //$NON-NLS-2$
		}		
		if(op.isTaxaOK())
		{
			description=description + "#" + "Taxa,OK"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
		{
			description=description+ "#" + "Taxa,BAD!";			 //$NON-NLS-1$ //$NON-NLS-2$
		}		
		
		for(SacadoAttempt sacadoAttempt:op.getResumo().getSacadosAttempt())
		{
			description=description + ";" + descriptionSacado(sacadoAttempt); //$NON-NLS-1$
		}
		
		description = description + ";" + descriptionCedente(op.getResumo().getCedenteAttempt()); //$NON-NLS-1$
		
		if(!descriptionMaioresCedentes(op.getResumo().getMaioresCedentesAttempt()).equals("")) //$NON-NLS-1$
		{
			description = description + ";" + descriptionMaioresCedentes(op.getResumo().getMaioresCedentesAttempt());		 //$NON-NLS-1$
			description = description + ";" + descriptionMaioresSacados(op.getResumo().getMaioresSacadosAttempt()); //$NON-NLS-1$
		}
		
		for(DireitoCreditorioAttempt dca:op.getResumo().getDireitosCreditoriosAttempt())
		{
			description = description + ";" + descriptionDireitoCreditorio(dca); //$NON-NLS-1$
		}
		
		System.out.println();
		op.getResumo().setDescription(description);
		System.out.println(op.getResumo().getDescription());

	}
}
