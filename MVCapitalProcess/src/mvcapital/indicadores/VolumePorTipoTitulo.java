package mvcapital.indicadores;

import java.util.ArrayList;
import java.util.Date;

import mvcapital.fundo.FundoDeInvestimento;

import com.mysql.jdbc.Connection;

public class VolumePorTipoTitulo extends Indicador
{
	private static ArrayList<Integer> idTipoDeTitulos = new ArrayList<Integer>();
	public VolumePorTipoTitulo()
	{
		
	}

	public VolumePorTipoTitulo(FundoDeInvestimento fundo, TipoIndicador tipoIndicador, Date dataEstoque)
	{
		super(fundo,dataEstoque);
		this.tipoIndicador=tipoIndicador;
	}
	
	public static void setTiposDeTitulos(ArrayList<Integer> idTipoIndicadorVolumeTipoTitulo, Connection conn)
	{
		for(int idTipoIndicador:idTipoIndicadorVolumeTipoTitulo)
		{
			VolumePorTipoTitulo.idTipoDeTitulos.add(HandlerIndicadores.getIdTipoTitulo(idTipoIndicador));
			
		}
	}
	
	public static ArrayList<Double> Calculate(ArrayList<Integer> idTitulos, ArrayList<Integer> idTipoIndicadorVolumeTipoTitulo, Date dataEstoque, Connection conn)
	{
		ArrayList<Double> volumeTitulo = new ArrayList<Double>();	
//		if(idTipoDeTitulos.size()==0)
//		{
//			VolumePorTipoTitulo.setTiposDeTitulos(idTipoIndicadorVolumeTipoTitulo, conn);
//		}
		for(int idTipoTitulo:VolumePorTipoTitulo.idTipoDeTitulos)
		{
			{
				ArrayList<Integer> idTitulosPorTipo = null;
				{
					idTitulosPorTipo = HandlerIndicadores.titulosDoTipo(idTitulos, idTipoTitulo);
				}				
				{
					volumeTitulo.add(HandlerIndicadores.valorPresente(idTitulosPorTipo, dataEstoque));
				}
				idTitulosPorTipo=null;
			}
		}
		return volumeTitulo;
	}
}
