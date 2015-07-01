package utils;

import java.util.ArrayList;
import java.util.Arrays;

import entidade.Entidade;

public class SortEntidades
{

	public SortEntidades()
	{

	}
	
	public static ArrayList<Entidade> sort(ArrayList<Entidade> entidadesUnsort)
	{
		ArrayList<Entidade> entidadesSorted = new ArrayList<Entidade>();
		ArrayList<String> namesEntidade = new ArrayList<String>();
		String[] namesArrayEntidade = new String[entidadesUnsort.size()];
		System.out.println("Before sort"); //$NON-NLS-1$
		for(int i=0;i<entidadesUnsort.size();i++)
		{
			System.out.println(entidadesUnsort.get(i).getNomeCurto());
			namesArrayEntidade[i]=entidadesUnsort.get(i).getNomeCurto();
		}
//		String[] namesArrayEntidade = namesEntidade.toArray(); 
		Arrays.sort(namesArrayEntidade);
		System.out.println(""); //$NON-NLS-1$
		System.out.println("After sort"); //$NON-NLS-1$
		for(String nameEntidade:namesArrayEntidade)
		{
			System.out.println(nameEntidade);
			for(Entidade entidade:entidadesUnsort)
			{
				if(nameEntidade.compareTo(entidade.getNomeCurto())==0)
				{
					entidadesSorted.add(entidade);
				}
			}
		}
		
		for(Entidade entidade:entidadesSorted)
		{
			System.out.println("Funds: " + entidade.getNomeCurto());
		}
		
		return entidadesSorted;
	}

}
