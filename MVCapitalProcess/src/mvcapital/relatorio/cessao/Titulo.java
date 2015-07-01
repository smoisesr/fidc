package mvcapital.relatorio.cessao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mvcapital.entidade.Entidade;
import mvcapital.fundo.FundoDeInvestimento;
import mvcapital.utils.WorkingDays;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Titulo
{
	private static SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$

	// `titulo`.`idTitulo`,
	// `titulo`.`idFundo`,
	// `titulo`.`idEntidadeOriginador`,
	// `titulo`.`idEntidadeCedente`,
	// `titulo`.`idEntidadeSacado`,
	// `titulo`.`idTipoTitulo`,
	// `titulo`.`SeuNumero`,
	// `titulo`.`NumeroDocumento`,
	// `titulo`.`DataAquisicao`,
	// `titulo`.`DataVencimento`,
	// `titulo`.`PrazoCorrido`,
	// `titulo`.`PrazoUtil`,
	// `titulo`.`ValorAquisicao`,
	// `titulo`.`ValorNominal`,
	// `titulo`.`ValorDiarioUtil`,
	// `titulo`.`NFeChaveAcesso`,
	// `titulo`.`DataAtualizacao`

	private int idTitulo = 0;
	private FundoDeInvestimento fundo = new FundoDeInvestimento();
	private Entidade originador = new Entidade();
	private Entidade cedente = new Entidade();
	private Entidade sacado = new Entidade();
	private TipoTitulo tipoTitulo = new TipoTitulo();
	private String seuNumero = ""; //$NON-NLS-1$
	private String numeroDocumento = ""; //$NON-NLS-1$
	private Date dataAquisicao = Calendar.getInstance().getTime();
	private Date dataVencimento = Calendar.getInstance().getTime();
	private int prazoCorrido = 0;
	private int prazoUtil = 0;
	private double valorAquisicao = 0;
	private double valorNominal = 0;
	private double valorDiarioUtil = 0;
	private String NFeChaveAcesso = ""; //$NON-NLS-1$
	private double taxaAoAno = 0;
	private double taxaDiaUtil = 0;

	public Titulo()
	{

	}

	public Titulo(int idTitulo, Connection conn)
	{
		this.idTitulo = idTitulo;
		String tableTitulo = "titulo"; //$NON-NLS-1$
		String primaryKey = "idTitulo"; //$NON-NLS-1$

		// `titulo`.`idTitulo`,
		// `titulo`.`idFundo`,
		// `titulo`.`idEntidadeOriginador`,
		// `titulo`.`idEntidadeCedente`,
		// `titulo`.`idEntidadeSacado`,
		// `titulo`.`idTipoTitulo`,
		// `titulo`.`SeuNumero`,
		// `titulo`.`NumeroDocumento`,
		// `titulo`.`DataAquisicao`,
		// `titulo`.`DataVencimento`,
		// `titulo`.`PrazoCorrido`,
		// `titulo`.`PrazoUtil`,
		// `titulo`.`ValorAquisicao`,
		// `titulo`.`ValorNominal`,
		// `titulo`.`ValorDiarioUtil`,
		// `titulo`.`NFeChaveAcesso`,
		// `titulo`.`DataAtualizacao`
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		String query = "SELECT * from " + tableTitulo + " WHERE " + primaryKey //$NON-NLS-1$ //$NON-NLS-2$
				+ " = " + idTitulo; //$NON-NLS-1$

		// System.out.println(query);
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
				this.fundo = new FundoDeInvestimento(rs.getInt("idFundo"), conn); //$NON-NLS-1$
				this.originador = new Entidade(
						rs.getInt("idEntidadeOriginador"), conn); //$NON-NLS-1$
				this.cedente = new Entidade(rs.getInt("idEntidadeCedente"), //$NON-NLS-1$
						conn);
				this.sacado = new Entidade(rs.getInt("idEntidadeSacado"), conn); //$NON-NLS-1$
				this.tipoTitulo = new TipoTitulo(rs.getInt("idTipoTitulo"), //$NON-NLS-1$
						conn);
				this.seuNumero = rs.getString("SeuNumero"); //$NON-NLS-1$
				this.numeroDocumento = rs.getString("NumeroDocumento"); //$NON-NLS-1$
				this.dataAquisicao = rs.getDate("DataAquisicao"); //$NON-NLS-1$
				this.dataVencimento = rs.getDate("DataVencimento"); //$NON-NLS-1$
				this.prazoCorrido = rs.getInt("PrazoCorrido"); //$NON-NLS-1$
				this.prazoUtil = rs.getInt("PrazoUtil"); //$NON-NLS-1$
				this.valorAquisicao = rs.getDouble("valorAquisicao"); //$NON-NLS-1$
				this.valorNominal = rs.getDouble("valorNominal"); //$NON-NLS-1$
				this.valorDiarioUtil = rs.getDouble("valorDiarioUtil"); //$NON-NLS-1$
				this.NFeChaveAcesso = rs.getString("NFeChaveAcesso"); //$NON-NLS-1$
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		this.setAnotherMembers(conn);
	}

	// `titulo`.`idTitulo`,
	// `titulo`.`idFundo`,
	// `titulo`.`idEntidadeOriginador`,
	// `titulo`.`idEntidadeCedente`,
	// `titulo`.`idEntidadeSacado`,
	// `titulo`.`idTipoTitulo`,
	// `titulo`.`SeuNumero`,
	// `titulo`.`NumeroDocumento`,
	// `titulo`.`DataAquisicao`,
	// `titulo`.`DataVencimento`,
	// `titulo`.`PrazoCorrido`,
	// `titulo`.`PrazoUtil`,
	// `titulo`.`ValorAquisicao`,
	// `titulo`.`ValorNominal`,
	// `titulo`.`ValorDiarioUtil`,
	// `titulo`.`NFeChaveAcesso`,
	// `titulo`.`DataAtualizacao`
	public Titulo(FundoDeInvestimento fundo, Entidade originador,
			Entidade cedente, Entidade sacado, TipoTitulo tipoTitulo,
			String seuNumero, String numeroDocumento, Date dataAquisicao,
			Date dataVencimento, double valorAquisicao, double valorNominal,
			String NFeChaveAcesso, Connection conn)
	{
		this.fundo = fundo;
		this.originador = originador;
		this.cedente = cedente;
		this.sacado = sacado;
		this.tipoTitulo = tipoTitulo;
		this.seuNumero = seuNumero;
		this.numeroDocumento = numeroDocumento;
		this.dataAquisicao = dataAquisicao;
		this.dataVencimento = dataVencimento;
		this.valorAquisicao = valorAquisicao;
		this.valorNominal = valorNominal;
		this.setAnotherMembers(conn);
	}

	public Titulo(String tipoTitulo, Entidade sacado, Entidade cedente,
			FundoDeInvestimento fundo, String seuNumero,
			String numeroDoDocumento, Date dataDeAquisicao,
			Date dataDeVencimento, double valorDeAquisicao,
			double valorNominal, Connection conn

	)
	{
		this.tipoTitulo = new TipoTitulo(tipoTitulo, conn);
		this.sacado = sacado;
		this.cedente = cedente;
		this.fundo = fundo;
		this.seuNumero = seuNumero;
		this.numeroDocumento = numeroDoDocumento;
		this.dataAquisicao = dataDeAquisicao;
		this.dataVencimento = dataDeVencimento;
		this.valorAquisicao = valorDeAquisicao;
		this.valorNominal = valorNominal;
		this.setAnotherMembers(conn);
	}

	public Titulo(Connection conn, String tipoDeRecebivel, String nomeSacado,
			String nomeCedente, String nomeFundo, String seuNumero,
			String numeroDoDocumento, Date dataDeEntrada, int prazo,
			double valorDeAquisicao, double valorNominal)
	{
		System.out.println("Creating Direito Creditorio"); //$NON-NLS-1$
		int idEntidadeSacado = 0;
		int idEntidadeCedente = 0;
		int idEntidadeFundo = 0;
		int idFundo = 0;

		try
		{

			// Check if this item exist already
			// System.out.println("TipoDeRecebivel: " + tipoDeRecebivel);
			this.tipoTitulo = new TipoTitulo(tipoDeRecebivel, conn);
			Statement stmt = (Statement) conn.createStatement();

			stmt = (Statement) conn.createStatement();
			String query = "SELECT idEntidade FROM Entidade WHERE nome ='" //$NON-NLS-1$
					+ nomeSacado + "'"; //$NON-NLS-1$
			// System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				idEntidadeSacado = rs.getInt("idEntidade"); //$NON-NLS-1$
			}

			if (idEntidadeSacado == 0)
			{
				System.out.println("Sacado não cadastrado"); //$NON-NLS-1$
				String stringDataCadastro = sdfd.format(Calendar.getInstance()
						.getTime());
				String nomeCurtoSacado = nomeSacado;

				if (nomeSacado.length() > 45)
				{
					nomeCurtoSacado = nomeSacado.substring(0, 45);
				}
				try
				{
					stmt = (Statement) conn.createStatement();
					String sql = "INSERT INTO `Entidade` (`nome`,`nomeCurto`,`dataDeInicio`,`idTipoDeCadastro`) VALUES ('" //$NON-NLS-1$
							+ nomeSacado
							+ "','" //$NON-NLS-1$
							+ nomeCurtoSacado
							+ "','" //$NON-NLS-1$
							+ stringDataCadastro + "',2)"; //$NON-NLS-1$
					// System.out.println(sql);
					stmt.executeUpdate(sql);
					System.out.println("New Sacado: " + nomeSacado); //$NON-NLS-1$

					stmt = (Statement) conn.createStatement();
					query = "SELECT idEntidade FROM Entidade WHERE nome ='" //$NON-NLS-1$
							+ nomeSacado + "'"; //$NON-NLS-1$
					// System.out.println(query);
					rs = stmt.executeQuery(query);
					while (rs.next())
					{
						idEntidadeSacado = rs.getInt("idEntidade"); //$NON-NLS-1$
					}

				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			// System.out.println("Sacado: " + nomeSacado +
			// " idEntidadeSacado: " + idEntidadeSacado);
			this.sacado = new Entidade(idEntidadeSacado, conn);
			stmt = (Statement) conn.createStatement();
			// query = "SELECT idEntidade FROM Entidade WHERE nome ='" +
			// nomeCedente + "'";
			// System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				idEntidadeCedente = rs.getInt("idEntidade"); //$NON-NLS-1$
			}

			if (idEntidadeCedente == 0)
			{
				// System.out.println("Cedente não cadastrado");
				String stringDataCadastro = sdfd.format(Calendar.getInstance()
						.getTime());
				String nomeCurtoCedente = nomeCedente;

				if (nomeCedente.length() > 45)
				{
					nomeCurtoCedente = nomeCedente.substring(0, 45);
				}
				try
				{
					stmt = (Statement) conn.createStatement();
					String sql = "INSERT INTO `Entidade` (`nome`,`nomeCurto`,`dataDeInicio`,`idTipoDeCadastro`) VALUES ('" //$NON-NLS-1$
							+ nomeCedente
							+ "','" //$NON-NLS-1$
							+ nomeCurtoCedente
							+ "','" //$NON-NLS-1$
							+ stringDataCadastro + "',2)"; //$NON-NLS-1$
					// System.out.println(sql);
					stmt.executeUpdate(sql);
					// System.out.println("New Cedente: " + nomeCedente);

					stmt = (Statement) conn.createStatement();
					query = "SELECT idEntidade FROM Entidade WHERE nome ='" //$NON-NLS-1$
							+ nomeCedente + "'"; //$NON-NLS-1$
					// System.out.println(query);
					rs = stmt.executeQuery(query);
					while (rs.next())
					{
						idEntidadeCedente = rs.getInt("idEntidade"); //$NON-NLS-1$
					}

				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			// System.out.println("Cedente: " + nomeCedente +
			// " idEntidadeCedente: " + idEntidadeCedente);
			this.cedente = new Entidade(idEntidadeCedente, conn);
			stmt = (Statement) conn.createStatement();
			query = "SELECT idEntidade FROM Entidade WHERE nome like '" //$NON-NLS-1$
					+ nomeFundo + "%'"; //$NON-NLS-1$
			// System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				idEntidadeFundo = rs.getInt("idEntidade"); //$NON-NLS-1$
			}
			// System.out.println("Fundo: " + nomeFundo + " idEntidadeFundo: " +
			// idEntidadeFundo);

			stmt = (Statement) conn.createStatement();
			query = "SELECT idFundo FROM Fundo WHERE idEntidade = " //$NON-NLS-1$
					+ idEntidadeFundo + ""; //$NON-NLS-1$
			// System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				idFundo = rs.getInt("idFundo"); //$NON-NLS-1$
			}
			// System.out.println("Fundo: " + nomeFundo + " idFundo: " + idFundo
			// + " idEntidadeFundo: " + idEntidadeFundo);
			this.fundo = new FundoDeInvestimento(idFundo, conn);
			this.seuNumero = seuNumero;
			this.numeroDocumento = numeroDoDocumento;
			this.dataAquisicao = dataDeEntrada;
			this.dataVencimento = WorkingDays.afterWorkDays(dataDeEntrada,
					prazo, conn);
			this.valorAquisicao = valorDeAquisicao;
			this.valorNominal = valorNominal;
			this.show();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		this.setAnotherMembers(conn);
	}

	public Titulo(FundoDeInvestimento fundo, Entidade cedente,
			Entidade sacado, String seuNumero,
			String numeroDocumento, Date dataVencimento,
			double valorAquisicao, Connection conn)
	{
		this.idTitulo = 0;
		String tableTitulo = "titulo"; //$NON-NLS-1$

		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		String query = "SELECT * from " + tableTitulo + " WHERE "   //$NON-NLS-1$ //$NON-NLS-2$
														+ " idFundo=" + fundo.getIdFundo() //$NON-NLS-1$
														+ " AND " + "idEntidadeCedente=" + cedente.getIdEntidade() //$NON-NLS-1$ //$NON-NLS-2$
														+ " AND " + " idEntidadeSacado=" + sacado.getIdEntidade() //$NON-NLS-1$ //$NON-NLS-2$
														+ " AND " + " seuNumero=" + "'" + seuNumero + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
														+ " AND " + " valorAquisicao=" + valorAquisicao //$NON-NLS-1$ //$NON-NLS-2$
														+ " AND " + " dataVencimento=" + "'" + sdfd.format(dataVencimento) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
														+ " "; //$NON-NLS-1$

		System.out.println(query);
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
				this.idTitulo = rs.getInt("idTitulo"); //$NON-NLS-1$
				this.fundo = new FundoDeInvestimento(rs.getInt("idFundo"), conn); //$NON-NLS-1$
				this.originador = new Entidade(
						rs.getInt("idEntidadeOriginador"), conn); //$NON-NLS-1$
				this.cedente = new Entidade(rs.getInt("idEntidadeCedente"), //$NON-NLS-1$
						conn);
				this.sacado = new Entidade(rs.getInt("idEntidadeSacado"), conn); //$NON-NLS-1$
				this.tipoTitulo = new TipoTitulo(rs.getInt("idTipoTitulo"), //$NON-NLS-1$
						conn);
				this.seuNumero = rs.getString("SeuNumero"); //$NON-NLS-1$
				this.numeroDocumento = rs.getString("NumeroDocumento"); //$NON-NLS-1$
				this.dataAquisicao = rs.getDate("DataAquisicao"); //$NON-NLS-1$
				this.dataVencimento = rs.getDate("DataVencimento"); //$NON-NLS-1$
				this.prazoCorrido = rs.getInt("PrazoCorrido"); //$NON-NLS-1$
				this.prazoUtil = rs.getInt("PrazoUtil"); //$NON-NLS-1$
				this.valorAquisicao = rs.getDouble("valorAquisicao"); //$NON-NLS-1$
				this.valorNominal = rs.getDouble("valorNominal"); //$NON-NLS-1$
				this.valorDiarioUtil = rs.getDouble("valorDiarioUtil"); //$NON-NLS-1$
				this.NFeChaveAcesso = rs.getString("NFeChaveAcesso"); //$NON-NLS-1$
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		this.setAnotherMembers(conn);
	}

	public void show()
	{
		// `titulo`.`idTitulo`,
		// `titulo`.`idFundo`,
		// `titulo`.`idEntidadeOriginador`,
		// `titulo`.`idEntidadeCedente`,
		// `titulo`.`idEntidadeSacado`,
		// `titulo`.`idTipoTitulo`,
		// `titulo`.`SeuNumero`,
		// `titulo`.`NumeroDocumento`,
		// `titulo`.`DataAquisicao`,
		// `titulo`.`DataVencimento`,
		// `titulo`.`PrazoCorrido`,
		// `titulo`.`PrazoUtil`,
		// `titulo`.`ValorAquisicao`,
		// `titulo`.`ValorNominal`,
		// `titulo`.`ValorDiarioUtil`,
		// `titulo`.`NFeChaveAcesso`,
		// `titulo`.`DataAtualizacao`
		System.out.println(
				this.tipoTitulo.getDescricao()
				+ "\t" + this.fundo.getNomeCurto()  //$NON-NLS-1$
				+ "\t" + this.originador.getNome() //$NON-NLS-1$
				+ "\t" + this.cedente.getNome()  //$NON-NLS-1$
				+ "\t" + this.sacado.getNome() //$NON-NLS-1$
				+ "\t" + this.seuNumero  //$NON-NLS-1$
				+ "\t" + this.numeroDocumento  //$NON-NLS-1$
				+ "\t" + sdfr.format(this.dataAquisicao)  //$NON-NLS-1$
				+ "\t" + sdfr.format(this.dataVencimento)  //$NON-NLS-1$
				+ "\t" + this.prazoCorrido //$NON-NLS-1$
				+ "\t" + this.prazoUtil  //$NON-NLS-1$
				+ "\t" + this.valorAquisicao  //$NON-NLS-1$
				+ "\t" + this.valorNominal  //$NON-NLS-1$
				+ "\t" + this.valorDiarioUtil  //$NON-NLS-1$
				+ "\t" + this.NFeChaveAcesso  //$NON-NLS-1$
				+ "\t" + this.taxaAoAno  //$NON-NLS-1$
				+ "\t" + this.taxaDiaUtil); //$NON-NLS-1$
	}
	
	public static String csvHeader()
	{
		String stringCSV=""; //$NON-NLS-1$
		stringCSV= "TipoTitulo" //$NON-NLS-1$
				+ ";" + "Fundo"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "Originador" //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "Cedente"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "Sacado" //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "SeuNumero"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "NumeroDocumento"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "DataAquisicao"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "DataVencimento"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "PrazoCorrido" //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "PrazoUtil"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "ValorAquisicao"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "ValorNominal"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "ValorDiarioUtil"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "NFeChaveAcesso"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "TaxaAoAno"  //$NON-NLS-1$ //$NON-NLS-2$
				+ ";" + "TaxaDiaUtil"; //$NON-NLS-1$ //$NON-NLS-2$
		return stringCSV;
	}

	public String toCSV()
	{
		String stringCSV=""; //$NON-NLS-1$
		stringCSV=this.tipoTitulo.getDescricao()
				+ ";" + this.fundo.getNomeCurto()  //$NON-NLS-1$
				+ ";" + this.originador.getNome() //$NON-NLS-1$
				+ ";" + this.cedente.getNome()  //$NON-NLS-1$
				+ ";" + this.sacado.getNome() //$NON-NLS-1$
				+ ";" + this.seuNumero  //$NON-NLS-1$
				+ ";" + this.numeroDocumento  //$NON-NLS-1$
				+ ";" + sdfr.format(this.dataAquisicao)  //$NON-NLS-1$
				+ ";" + sdfr.format(this.dataVencimento)  //$NON-NLS-1$
				+ ";" + this.prazoCorrido //$NON-NLS-1$
				+ ";" + this.prazoUtil  //$NON-NLS-1$
				+ ";" + this.valorAquisicao  //$NON-NLS-1$
				+ ";" + this.valorNominal  //$NON-NLS-1$
				+ ";" + this.valorDiarioUtil  //$NON-NLS-1$
				+ ";" + this.NFeChaveAcesso  //$NON-NLS-1$
				+ ";" + this.taxaAoAno  //$NON-NLS-1$
				+ ";" + this.taxaDiaUtil; //$NON-NLS-1$
		return stringCSV;
	}

	public void setAnotherMembers(Connection conn)
	{
		this.prazoCorrido = ((int) WorkingDays.allDays(this.dataAquisicao,
				this.dataVencimento));
		this.prazoUtil = ((int) WorkingDays.countWorkingDays(
				this.dataAquisicao, this.dataVencimento, conn));
		double taxaOperacao = 0.0;
		double taxaAoAno = 0.0;
		double taxaDiaUtil = 0.0;
		double valorDiarioUtil = 0.0;

		if (this.valorAquisicao != 0 && this.prazoUtil != 0)
		{
			taxaOperacao = (this.valorNominal / this.valorAquisicao - 1);
			taxaAoAno = taxaOperacao * 252 / this.prazoUtil;
			taxaDiaUtil = taxaOperacao / this.prazoUtil;
		}
		valorDiarioUtil = (this.valorNominal - this.valorAquisicao)
				/ this.prazoUtil;
		this.taxaAoAno = taxaAoAno;
		this.taxaDiaUtil = taxaDiaUtil;
		this.valorDiarioUtil = valorDiarioUtil;
	}

	public static double calcTaxa(Titulo titulo)
	{

		double taxaDaOperacao = titulo.valorNominal / titulo.valorAquisicao - 1;
		double taxaAoAno = taxaDaOperacao * 252 / titulo.prazoCorrido;
		return taxaAoAno;
	}

	public static SimpleDateFormat getSdfr()
	{
		return sdfr;
	}

	public static void setSdfr(SimpleDateFormat sdfr)
	{
		Titulo.sdfr = sdfr;
	}

	public static SimpleDateFormat getSdfd()
	{
		return sdfd;
	}

	public static void setSdfd(SimpleDateFormat sdfd)
	{
		Titulo.sdfd = sdfd;
	}

	public int getIdTitulo()
	{
		return this.idTitulo;
	}

	public void setIdTitulo(int idTitulo)
	{
		this.idTitulo = idTitulo;
	}

	public FundoDeInvestimento getFundo()
	{
		return this.fundo;
	}

	public void setFundo(FundoDeInvestimento fundo)
	{
		this.fundo = fundo;
	}

	public Entidade getOriginador()
	{
		return this.originador;
	}

	public void setOriginador(Entidade originador)
	{
		this.originador = originador;
	}

	public Entidade getCedente()
	{
		return this.cedente;
	}

	public void setCedente(Entidade cedente)
	{
		this.cedente = cedente;
	}

	public Entidade getSacado()
	{
		return this.sacado;
	}

	public void setSacado(Entidade sacado)
	{
		this.sacado = sacado;
	}

	public TipoTitulo getTipoTitulo()
	{
		return this.tipoTitulo;
	}

	public void setTipoTitulo(TipoTitulo tipoTitulo)
	{
		this.tipoTitulo = tipoTitulo;
	}

	public String getSeuNumero()
	{
		return this.seuNumero;
	}

	public void setSeuNumero(String seuNumero)
	{
		this.seuNumero = seuNumero;
	}

	public String getNumeroDocumento()
	{
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento)
	{
		this.numeroDocumento = numeroDocumento;
	}

	public Date getDataAquisicao()
	{
		return this.dataAquisicao;
	}

	public void setDataAquisicao(Date dataAquisicao)
	{
		this.dataAquisicao = dataAquisicao;
	}

	public Date getDataVencimento()
	{
		return this.dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento)
	{
		this.dataVencimento = dataVencimento;
	}

	public int getPrazoCorrido()
	{
		return this.prazoCorrido;
	}

	public void setPrazoCorrido(int prazoCorrido)
	{
		this.prazoCorrido = prazoCorrido;
	}

	public int getPrazoUtil()
	{
		return this.prazoUtil;
	}

	public void setPrazoUtil(int prazoUtil)
	{
		this.prazoUtil = prazoUtil;
	}

	public double getValorAquisicao()
	{
		return this.valorAquisicao;
	}

	public void setValorAquisicao(double valorAquisicao)
	{
		this.valorAquisicao = valorAquisicao;
	}

	public double getValorNominal()
	{
		return this.valorNominal;
	}

	public void setValorNominal(double valorNominal)
	{
		this.valorNominal = valorNominal;
	}

	public double getValorDiarioUtil()
	{
		return this.valorDiarioUtil;
	}

	public void setValorDiarioUtil(double valorDiarioUtil)
	{
		this.valorDiarioUtil = valorDiarioUtil;
	}

	public String getNFeChaveAcesso()
	{
		return this.NFeChaveAcesso;
	}

	public void setNFeChaveAcesso(String nFeChaveAcesso)
	{
		this.NFeChaveAcesso = nFeChaveAcesso;
	}

	public double getTaxaAoAno()
	{
		return this.taxaAoAno;
	}

	public void setTaxaAoAno(double taxaAoAno)
	{
		this.taxaAoAno = taxaAoAno;
	}

	public double getTaxaDiaUtil()
	{
		return this.taxaDiaUtil;
	}

	public void setTaxaDiaUtil(double taxaDiaUtil)
	{
		this.taxaDiaUtil = taxaDiaUtil;
	}

}
