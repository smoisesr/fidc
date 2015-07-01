package mvcapital.cnab;

import java.util.ArrayList;

public class RegisterIdentifyer 
{
	private static ArrayList<Identifier> identifiers = new ArrayList<Identifier>() {
		private static final long serialVersionUID = 1L;

			{
				/**
				 * First whe have the file headers, which will determine the following ones to be used
				 */
				add(new Identifier(1, 19, "02RETORNO01COBRANCA","FILE_HEADER_RETORNO_BRADESCO")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(4, 8, "00000", "FILE_HEADER_CONCILIACAO_BRADESCO")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(1, 19, "01REMESSA01COBRANCA","FILE_HEADER_REMESSA_PAULISTA")); //$NON-NLS-1$ //$NON-NLS-2$
				/**
				 * Here we have the other registers for every kind of cnab file
				 */
				add(new Identifier(1,1,"1","DETAIL_RETORNO_BRADESCO")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(1,7,"9201237","TRAILLER_RETORNO_BRADESCO")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(1,1,"1","DETAIL_REMESSA_PAULISTA")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(1,1,"9","TRAILLER_REMESSA_PAULISTA")); //$NON-NLS-1$ //$NON-NLS-2$
			}
	};
	//public static enum Type {FILE_HEADER, FILE_TRAILER, LOT_HEADER, LOT_TRAILER, DETAIL}
	
	public RegisterIdentifyer()
	{
	
	}
		
	public static Identifier getIdentifier(String line)
	{
		Identifier idReturn = new Identifier();
		for(Identifier id:identifiers)
		{
			//System.out.println(id.getIdentifierString() + " Comp " + line.substring(id.getInitialPosition()-1, id.getFinalPosition()+1));
			if(line.substring(id.getInitialPosition()-1, id.getFinalPosition()).equals(id.getIdentifierString()))
			{			
				idReturn = id;
				//System.out.println("Found!");
				break;
			}
		}
		return idReturn;
	}
}
