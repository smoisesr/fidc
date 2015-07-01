package mvcapital.bancopaulista.cnab;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



//import java.util.Calendar;
//import java.util.Date;
import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class CnabPaulista
{
	private Header header = new Header();
	private ArrayList<Detail> details = new ArrayList<Detail>();
	private Trailler trailler = new Trailler();
	private static SimpleDateFormat sdfCnab = new SimpleDateFormat("ddMMyy");
	private static SimpleDateFormat sdfAccess = new SimpleDateFormat("dd/MM/yyyy");
	
	
	public CnabPaulista()
	{
		this.header=new Header();
		this.trailler = new Trailler();
		this.details = new ArrayList<Detail>();		
	}
	
	public CnabPaulista(FundoDeInvestimento fundo, Entidade originador, ArrayList<Detail> details, Connection conn)
	{
		this.details = details;
		setupOriginador(fundo, originador, this.header, conn);
		int sequencialUltimoRegistro = this.details.size()+2;
		this.trailler.getNumeroSequencialRegistro().setConteudo(Integer.toString(sequencialUltimoRegistro));
	}
	
	public CnabPaulista(ArrayList<String> lines)
	{
		if(lines.size()>0)
		{
			for(int i=0;i<lines.size();i++)
			{
//				System.out.println(lines.get(i));
				if(i==0)
				{
					this.header = new Header(lines.get(i));
				}
				else if(i<lines.size()-1)
				{
					this.details.add(new Detail(lines.get(i)));
				}
				else
				{
					this.trailler=new Trailler(lines.get(i));
				}
			}
		}		
	}
	
	public CnabPaulista(ArrayList<String> lines, int length)
	{
		if (length==400)
		{
			if(lines.size()>0)
			{
				for(int i=0;i<lines.size();i++)
				{
	//				System.out.println(lines.get(i));
					String line = lines.get(i);
	//			    0-400
					line = line.substring(0,395)+"                                            " + line.substring(395,400); //$NON-NLS-1$
					if(i==0)
					{
						this.header = new Header(line);
					}
					else if(i<lines.size()-1)
					{
						this.details.add(new Detail(line));
					}
					else
					{
						this.trailler=new Trailler(line);
					}
				}
			}
		}
	}

	public String toCSV()
	{
		StringBuilder sb = new StringBuilder(); 
		sb.append(Detail.csvColumns());
		for(Detail detail:this.getDetails())
		{
			sb.append("\n" + detail.toCSV()); //$NON-NLS-1$
		}
		return sb.toString();
	}		

	public String toCSVAccess()
	{
		StringBuilder sb = new StringBuilder(); 
		sb.append(Detail.csvColumns()  + ";CodigoDoOriginador;Data");
		Date dataCnab = null;
		try
		{
			dataCnab = sdfCnab.parse(this.getHeader().getDataGravacaoArquivo().getConteudo());
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Detail detail:this.getDetails())
		{
			sb.append("\n" + detail.toCSV() + ";" + this.getHeader().getCodigoOriginador().getConteudo() + ";" + sdfAccess.format(dataCnab)); //$NON-NLS-1$
		}
		return sb.toString();
	}		
	
	
	public String toString()
	{
		String stringCnab = this.getHeader().toString();
		for(Detail detail:this.getDetails())
		{
			stringCnab = stringCnab + "\n" + detail.toString(); //$NON-NLS-1$
		}
		stringCnab = stringCnab  + "\n" + this.getTraillerPaulista().toString(); //$NON-NLS-1$
		return stringCnab;
	}
	
	public static void setupOriginador(FundoDeInvestimento fundo, Entidade originador, Header header, Connection conn)
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
//		select * from titulo where idFundo=5 and valorNominal=1250 and idTipoTitulo=1 and numeroDocumento like "%16%"			
		String query = "SELECT idCodigoDoOriginador, codigo, nomeOriginador FROM codigoDoOriginador " //$NON-NLS-1$
				+ " WHERE" //$NON-NLS-1$
				+ " idFundo = " + fundo.getIdFundo() //$NON-NLS-1$
				+ " AND idEntidade = " + originador.getIdEntidade() //$NON-NLS-1$
				;
//		System.out.println(query);
		ResultSet rs;
//		Date dataOcorrencia = Calendar.getInstance().getTime();
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				int idCodigoDoOriginador = rs.getInt("idCodigoDoOriginador"); //$NON-NLS-1$
				if(idCodigoDoOriginador!=0)
				{
					String codigo=rs.getString("codigo"); //$NON-NLS-1$
					String nomeOriginador=rs.getString("nomeOriginador"); //$NON-NLS-1$
					header.getCodigoOriginador().setConteudo(codigo);
					header.getNomeOriginador().setConteudo(nomeOriginador);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}					
	}
	
	public Header getHeader()
	{
		return this.header;
	}
	
	public void setHeader(Header headerPaulista)
	{
		this.header = headerPaulista;
	}
	public Trailler getTraillerPaulista()
	{
		return this.trailler;
	}
	public void setTrailler(Trailler traillerPaulista)
	{
		this.trailler = traillerPaulista;
	}
	public ArrayList<Detail> getDetails()
	{
		return this.details;
	}
	public void setDetails(ArrayList<Detail> detailsPaulista)
	{
		this.details = detailsPaulista;
	}
}
