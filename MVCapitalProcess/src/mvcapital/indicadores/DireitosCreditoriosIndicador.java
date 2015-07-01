package mvcapital.indicadores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mvcapital.fundo.FundoDeInvestimento;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DireitosCreditoriosIndicador extends Indicador
{
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	
	public DireitosCreditoriosIndicador()
	{
		
	}

	public DireitosCreditoriosIndicador(FundoDeInvestimento fundo, TipoIndicador tipoIndicador, Date dataEstoque)
	{
		super(fundo,dataEstoque);
		this.tipoIndicador=tipoIndicador;
	}
	
	public static double Calculate(ArrayList<Integer> idTitulos, Date dataEstoque, Connection conn)
	{
		ArrayList<Double> valoresPresentes = new ArrayList<Double>();

		for(int idTitulo:idTitulos)
		{
			String query = "select valor from valorPresente where " //$NON-NLS-1$
					+ " dataEstoque = " + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ " and idTitulo = " + idTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			ResultSet rs = null;
			try
			{
				rs = stmt.executeQuery(query);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
	
			try
			{
				while (rs.next())
				{
					double valor = rs.getDouble("valor"); //$NON-NLS-1$
	//				System.out.println(idTitulo);
					valoresPresentes.add(valor);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		double sum=0.0;
		for(double valor:valoresPresentes)
		{
			sum = sum + valor;
		}
		System.out.println("DC: " + sum); //$NON-NLS-1$
		valoresPresentes=null;
		return sum;
	}
}
