package cnab.base;

import java.util.ArrayList;

public class RegisterIdentifyer 
{
	private static ArrayList<Identifier> identifiers = new ArrayList<Identifier>() {
		private static final long serialVersionUID = 1L;

			{
				add(new Identifier(1,26,"02RETORNO01COBRANCA","FILE_HEADER_RETORNO_BRADESCO")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(4, 8, "00000", "FILE_HEADER_CONCILIACAO_BRADESCO")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(4, 8, "99999","FILE_TRAILER_CONCILIACAO_BRADESCO")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(8, 11, "1E04","LOT_HEADER_CONCILIACAO_BRADESCO")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(8, 8, "5","LOT_TRAILER_CONCILIACAO_BRADESCO")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(8, 8, "3","DETAIL_CONCILIACAO_BRADESCO")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(1, 19, "01REMESSA01COBRANCA","FILE_HEADER_REMESSA_PAULISTA")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(1, 394, "9                                                                                                                                                                                                                                                                                                                                                                                                         ","FILE_TRAILER_REMESSA_PAULISTA")); //$NON-NLS-1$ //$NON-NLS-2$
				add(new Identifier(1, 20, "1                   ","DETAIL_REMESSA_PAULISTA")); //$NON-NLS-1$ //$NON-NLS-2$
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
