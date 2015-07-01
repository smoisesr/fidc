package relatorio.cessao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import utils.WorkingDays;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import entidade.Entidade;
import fundo.FundoDeInvestimento;

public class DireitoCreditorio 
{
	private static SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
	
	private int idDireitoCreditorio=0;
	private TipoDeRecebivel tipoDeRecebivel=new TipoDeRecebivel();
	private Entidade sacado=new Entidade();
	private Entidade cedente=new Entidade();
	private Entidade consultoria=new Entidade();
	private FundoDeInvestimento fundo=new FundoDeInvestimento();
	private String campoChave=""; 
	private String numeroDoTitulo="";
	private Date dataDeAquisicao=Calendar.getInstance().getTime(); 
	private Date dataDeVencimento=Calendar.getInstance().getTime();
	private double valorDeAquisicao=0;
	private double valorDoTitulo=0;
	private double valorDiarioUtil=0;
	private double taxaAoAno=0;
	private double taxaDiaUtil=0;
	private int prazoDiasCorridos=0;
	private int prazoDiasUteis=0;
	private int idOperacao=0;
	
	
//	  tipoDeRecebivel="";	
//	 `idDireitoCreditorio`,
//	 `idTipoDeRecebivel`,
//	 `idEntidadeSacado`,
//	 `idEntidadeCedente`,
//	 `idFundo`,
//	 `campoChave`,
//	 `numeroDoTitulo`,
//	 `dataDeAquisicao`,
//	 `dataDeVencimento`,
//	 `valorDeAquisicao`,
//	 `valorDoTitulo`,  	
//	 `idOperacao`	
	
	public DireitoCreditorio()
	{
		
	}
	
	public DireitoCreditorio(int idDireitoCreditorio, Connection conn)
	{
		this.idDireitoCreditorio=idDireitoCreditorio;

		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "SELECT * from direitocreditorio WHERE idDireitoCreditorio = " + idDireitoCreditorio;		
		
//		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				this.tipoDeRecebivel = new TipoDeRecebivel(rs.getInt("idTipoDeRecebivel"), conn);
				this.sacado = new Entidade(rs.getInt("idEntidadeSacado"), conn);
				this.cedente = new Entidade(rs.getInt("idEntidadeCedente"), conn);
				this.fundo = new FundoDeInvestimento(rs.getInt("idFundo"), conn);
				this.campoChave = rs.getString("campoChave");
				this.numeroDoTitulo = rs.getString("numeroDoTitulo");
				this.dataDeAquisicao = rs.getDate("dataDeAquisicao");
				this.dataDeVencimento = rs.getDate("dataDeVencimento");
				this.valorDeAquisicao = rs.getDouble("valorDeAquisicao");
				this.valorDoTitulo = rs.getDouble("valorDoTitulo");
				this.idOperacao = rs.getInt("idOperacao");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		this.setAnotherMembers(conn);
	}

	public DireitoCreditorio(
			String tipoDeRecebivel,
			Entidade sacado,
			Entidade cedente,
			FundoDeInvestimento fundo,
			String seuNumero, 
			String numeroDoDocumento,
			Date dataDeAquisicao,
			Date dataDeVencimento,
			double valorDeAquisicao, 
			double valorNominal,
			Connection conn
			
			)
	{
		this.tipoDeRecebivel=new TipoDeRecebivel(tipoDeRecebivel, conn);
		this.sacado=sacado;
		this.cedente=cedente;
		this.fundo=fundo;
		this.campoChave=seuNumero;
		this.numeroDoTitulo=numeroDoDocumento;
		this.dataDeAquisicao=dataDeAquisicao;
		this.dataDeVencimento=dataDeVencimento;
		this.valorDeAquisicao=valorDeAquisicao;
		this.valorDoTitulo=valorNominal;

		this.setAnotherMembers(conn);
	}

	public DireitoCreditorio(Connection conn, 
							String tipoDeRecebivel,
							String nomeSacado, 
							String nomeCedente, 
							String nomeFundo,
							String seuNumero, 
							String numeroDoDocumento, 
							Date dataDeEntrada,
							int prazo, 
							double valorDeAquisicao, 
							double valorNominal) 
	{
		System.out.println("Creating Direito Creditorio");
		
//		private int idDireitoCreditorio=0;
//		private TipoDeRecebivel tipoDeRecebivel=new TipoDeRecebivel();
//		private Entidade sacado=new Entidade();
//		private Entidade cedente=new Entidade();
//		private FundoDeInvestimento fundo=new FundoDeInvestimento();
//		private String campoChave=""; 
//		private String numeroDoTitulo="";
//		private Date dataDeAquisicao=Calendar.getInstance().getTime(); 
//		private Date dataDeVencimento=Calendar.getInstance().getTime();
//		private double valorDeAquisicao=0;
//		private double valorDoTitulo=0;
//		private double taxaAoAno=0;
//		private double taxaDiaUtil=0;
//		private int prazoDiasCorridos=0;
//		private int prazoDiasUteis=0;
//		private int idOperacao=0;
//		
//		try 
//		{
//			//Check if this item exist already
//			Statement stmt = (Statement) conn.createStatement();
//			String query = "SELECT idDireitoCreditorio FROM DireitoCreditorio WHERE campoChave ='" + seuNumero + "'";
////			System.out.println(query);
//			ResultSet rs = stmt.executeQuery(query);
//			while (rs.next())
//			{
//				this.idDireitoCreditorio = rs.getInt("idDireitoCreditorio");
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

		int idEntidadeSacado=0;
		int idEntidadeCedente=0;
		int idEntidadeFundo=0;
		int idFundo=0;

		try 
		{
			
			//Check if this item exist already
			//System.out.println("TipoDeRecebivel: " + tipoDeRecebivel);
			this.tipoDeRecebivel= new TipoDeRecebivel(tipoDeRecebivel, conn);
			Statement stmt = (Statement) conn.createStatement();

			stmt = (Statement) conn.createStatement();
			String query = "SELECT idEntidade FROM Entidade WHERE nome ='" + nomeSacado + "'";
//				System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				idEntidadeSacado = rs.getInt("idEntidade");					
			}

			if(idEntidadeSacado==0)
			{
				System.out.println("Sacado não cadastrado");
				String stringDataCadastro = sdfd.format(Calendar.getInstance().getTime());
				String nomeCurtoSacado = nomeSacado;
				
				if(nomeSacado.length()>45)
				{
					nomeCurtoSacado = nomeSacado.substring(0, 45);
				}
				try 
				{	
					stmt = (Statement) conn.createStatement();
					String sql = "INSERT INTO `MVCapital`.`Entidade` (`nome`,`nomeCurto`,`dataDeInicio`,`idTipoDeCadastro`) VALUES ('"+nomeSacado+"','"+nomeCurtoSacado+"','"+stringDataCadastro+"',2)";
//						System.out.println(sql);
					stmt.executeUpdate(sql);
					System.out.println("New Sacado: " + nomeSacado);

					stmt = (Statement) conn.createStatement();
					query = "SELECT idEntidade FROM Entidade WHERE nome ='" + nomeSacado + "'";
//						System.out.println(query);
					rs = stmt.executeQuery(query);
					while (rs.next())
					{
						idEntidadeSacado = rs.getInt("idEntidade");					
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
//				System.out.println("Sacado: " + nomeSacado + " idEntidadeSacado: " + idEntidadeSacado);
			this.sacado = new Entidade(idEntidadeSacado,conn);
			stmt = (Statement) conn.createStatement();
//				query = "SELECT idEntidade FROM Entidade WHERE nome ='" + nomeCedente + "'";
//				System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				idEntidadeCedente = rs.getInt("idEntidade");					
			}

			if(idEntidadeCedente==0)
			{
//					System.out.println("Cedente não cadastrado");
				String stringDataCadastro = sdfd.format(Calendar.getInstance().getTime());
				String nomeCurtoCedente = nomeCedente;
				
				if(nomeCedente.length()>45)
				{
					nomeCurtoCedente = nomeCedente.substring(0, 45);
				}
				try 
				{	
					stmt = (Statement) conn.createStatement();
					String sql = "INSERT INTO `MVCapital`.`Entidade` (`nome`,`nomeCurto`,`dataDeInicio`,`idTipoDeCadastro`) VALUES ('"+nomeCedente+"','"+nomeCurtoCedente+"','"+stringDataCadastro+"',2)";
//						System.out.println(sql);
					stmt.executeUpdate(sql);
//						System.out.println("New Cedente: " + nomeCedente);

					stmt = (Statement) conn.createStatement();
					query = "SELECT idEntidade FROM Entidade WHERE nome ='" + nomeCedente + "'";
//						System.out.println(query);
					rs = stmt.executeQuery(query);
					while (rs.next())
					{
						idEntidadeCedente = rs.getInt("idEntidade");					
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
//				System.out.println("Cedente: " + nomeCedente + " idEntidadeCedente: " + idEntidadeCedente);
			this.cedente = new Entidade(idEntidadeCedente,conn);
			stmt = (Statement) conn.createStatement();
			query = "SELECT idEntidade FROM Entidade WHERE nome like '" + nomeFundo + "%'";
//				System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				idEntidadeFundo = rs.getInt("idEntidade");					
			}
//				System.out.println("Fundo: " + nomeFundo + " idEntidadeFundo: " + idEntidadeFundo);

			stmt = (Statement) conn.createStatement();
			query = "SELECT idFundo FROM Fundo WHERE idEntidade = " + idEntidadeFundo + "";
//				System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				idFundo = rs.getInt("idFundo");					
			}
//				System.out.println("Fundo: " + nomeFundo + " idFundo: " + idFundo + " idEntidadeFundo: " + idEntidadeFundo);
			this.fundo = new FundoDeInvestimento(idFundo,conn);
			this.campoChave = seuNumero;
			this.numeroDoTitulo = numeroDoDocumento;
			this.dataDeAquisicao = dataDeEntrada;
			this.dataDeVencimento=WorkingDays.afterWorkDays(dataDeEntrada, prazo, conn);				
			this.valorDeAquisicao=valorDeAquisicao;
			this.valorDoTitulo = valorNominal;
			this.show();
		} catch (SQLException e) {
			e.printStackTrace();
		}			

		this.setAnotherMembers(conn);
	}

	public void show()
	{
		System.out.println(
					this.tipoDeRecebivel.getDescricao()
					+"\t"+this.sacado.getNome()
					+"\t"+this.cedente.getNome()
					+"\t"+this.fundo.getNomeCurto()
					+"\t"+this.campoChave
					+"\t"+this.numeroDoTitulo
					+"\t"+sdfr.format(this.dataDeAquisicao)
					+"\t"+sdfr.format(this.dataDeVencimento)
					+"\t"+this.valorDeAquisicao
					+"\t"+this.valorDoTitulo
					+"\t"+this.prazoDiasUteis
					+"\t"+this.prazoDiasCorridos
					+"\t"+this.taxaAoAno
					+"\t"+this.taxaDiaUtil
					);
	}
	
	public void setAnotherMembers(Connection conn)
	{
		this.setPrazoDiasCorridos((int) WorkingDays.allDays(this.dataDeAquisicao, this.dataDeVencimento));
		this.setPrazoDiasUteis((int) WorkingDays.countWorkingDays(this.dataDeAquisicao, this.dataDeVencimento, conn));
		double taxaOperacao = 0.0;
		double taxaAoAno = 0.0;		
		double taxaDiaUtil = 0.0;
		double valorDiarioUtil = 0.0;
		
		if(this.valorDeAquisicao!=0 && this.prazoDiasUteis !=0 )
		{
			taxaOperacao=(this.valorDoTitulo/this.valorDeAquisicao-1);
			taxaAoAno=taxaOperacao*252/this.prazoDiasUteis;
			taxaDiaUtil=taxaOperacao/this.prazoDiasUteis;
		}
		valorDiarioUtil = (this.getValorDoTitulo()-this.getValorDeAquisicao())/this.getPrazoDiasUteis();
		this.setTaxaAoAno(taxaAoAno);
		this.setTaxaDiaUtil(taxaDiaUtil);	
		this.setValorDiarioUtil(valorDiarioUtil);
	}
	
	public static double calcTaxa(DireitoCreditorio dc)
	{
		
		double taxaDaOperacao=dc.getValorDoTitulo()/dc.getValorDeAquisicao()-1;
		double taxaAoAno=taxaDaOperacao*252/dc.getPrazoDiasCorridos();
		return taxaAoAno;
	}
	
	public double getValorDeAquisicao() {
		return valorDeAquisicao;
	}
	public void setValorDeAquisicao(double valorDeAquisicao) {
		this.valorDeAquisicao = valorDeAquisicao;
	}
	
	public Date getDataDeAquisicao() {
		return dataDeAquisicao;
	}

	public void setDataDeAquisicao(Date dataDeAquisicao) {
		this.dataDeAquisicao = dataDeAquisicao;
	}

	public Date getDataDeVencimento() {
		return dataDeVencimento;
	}

	public void setDataDeVencimento(Date dataDeVencimento) {
		this.dataDeVencimento = dataDeVencimento;
	}

	public FundoDeInvestimento getFundo() {
		return fundo;
	}

	public void setFundo(FundoDeInvestimento fundo) {
		this.fundo = fundo;
	}

	public Entidade getSacado() {
		return sacado;
	}

	public void setSacado(Entidade sacado) {
		this.sacado = sacado;
	}

	public Entidade getCedente() {
		return cedente;
	}

	public void setCedente(Entidade cedente) {
		this.cedente = cedente;
	}

	public static SimpleDateFormat getSdfr() {
		return sdfr;
	}

	public static void setSdfr(SimpleDateFormat sdfr) {
		DireitoCreditorio.sdfr = sdfr;
	}

	public static SimpleDateFormat getSdfd() {
		return sdfd;
	}

	public static void setSdfd(SimpleDateFormat sdfd) {
		DireitoCreditorio.sdfd = sdfd;
	}

	public String getCampoChave() {
		return campoChave;
	}

	public void setCampoChave(String campoChave) {
		this.campoChave = campoChave;
	}

	public String getNumeroDoTitulo() {
		return numeroDoTitulo;
	}

	public void setNumeroDoTitulo(String numeroDoTitulo) {
		this.numeroDoTitulo = numeroDoTitulo;
	}

	public double getValorDoTitulo() {
		return valorDoTitulo;
	}

	public void setValorDoTitulo(double valorDoTitulo) {
		this.valorDoTitulo = valorDoTitulo;
	}

	public int getIdDireitoCreditorio() {
		return idDireitoCreditorio;
	}

	public void setIdDireitoCreditorio(int idDireitoCreditorio) {
		this.idDireitoCreditorio = idDireitoCreditorio;
	}

	public int getIdOperacao() {
		return idOperacao;
	}

	public void setIdOperacao(int idOperacao) {
		this.idOperacao = idOperacao;
	}

	public int getPrazoDiasCorridos() {
		return prazoDiasCorridos;
	}

	public void setPrazoDiasCorridos(int prazoDiasCorridos) {
		this.prazoDiasCorridos = prazoDiasCorridos;
	}

	public int getPrazoDiasUteis() {
		return prazoDiasUteis;
	}

	public void setPrazoDiasUteis(int prazoDiasUteis) {
		this.prazoDiasUteis = prazoDiasUteis;
	}

	public double getTaxaAoAno() {
		return taxaAoAno;
	}

	public void setTaxaAoAno(double taxaAoAno) {
		this.taxaAoAno = taxaAoAno;
	}

	public double getTaxaDiaUtil() {
		return taxaDiaUtil;
	}

	public void setTaxaDiaUtil(double taxaDiaUtil) {
		this.taxaDiaUtil = taxaDiaUtil;
	}

	public TipoDeRecebivel getTipoDeRecebivel() {
		return tipoDeRecebivel;
	}

	public void setTipoDeRecebivel(TipoDeRecebivel tipoDeRecebivel) {
		this.tipoDeRecebivel = tipoDeRecebivel;
	}

	public double getValorDiarioUtil() {
		return valorDiarioUtil;
	}

	public void setValorDiarioUtil(double valorDiarioUtil) {
		this.valorDiarioUtil = valorDiarioUtil;
	}

	public Entidade getConsultoria() {
		return consultoria;
	}

	public void setConsultoria(Entidade consultoria) {
		this.consultoria = consultoria;
	}
	
}
