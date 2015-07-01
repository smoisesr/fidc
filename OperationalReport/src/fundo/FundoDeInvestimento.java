package fundo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entidade.Entidade;

/**
 * @author MVCapital - Moisés
 * Class to define the investment fund
 */
public class FundoDeInvestimento extends Entidade
{
	private int idFundo;
	private int idEntidade;
	private int idTipoDeCondominio;
	private int idClasseCVM;
	private String prazoDeDuracao;
	private int idPublicoAlvo;
	private double valorMinimoAplicacao;
	private double valorMinimoResgate;
	private double saldoMinimoCotista;
	private int idEntidadeGestor;
	private int idEntidadeAdministrador;
	private int idEntidadeCustodiante;
	private int idEntidadeBancoCobrador;
	private String codigoAnbima;
	private String codigoCVM;
	private int idCategoriaAnbima;
	private String categoriaGeralAnbima;
	private int idTipoAnbima;
	private int idCotaFechamento;
	private int idFundoAtivo;
	
	private Connection conn = null;
	
	public FundoDeInvestimento()
	{
		
	}
	public FundoDeInvestimento(int idFundo, Connection conn)
	{
		super(conn);
		this.idFundo = idFundo;
		this.setConn(conn);
		this.setMembers();		
	}

	public FundoDeInvestimento(String nomeFundo, Connection conn)
	{
		super(conn);
		this.setConn(conn);
		
		int idFundo = 0;
		
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidade FROM Entidade WHERE nome='" + nomeFundo + "'" ); //$NON-NLS-1$ //$NON-NLS-2$
			while (rs.next())
			{				
				this.idEntidade = rs.getInt("idEntidade");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idFundo FROM fundo WHERE idEntidade=" + this.idEntidade); //$NON-NLS-1$
			while (rs.next())
			{				
				idFundo = rs.getInt("idFundo");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.idFundo = idFundo;
		this.setMembers();		
	}

	public FundoDeInvestimento(String nomeFundo, String cadastro, Connection conn)
	{
		super(conn);
		this.setConn(conn);
		
		int idFundo = 0;
		
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			String query = "SELECT idEntidade FROM Entidade WHERE cadastro='" + cadastro + "'"; //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query); //$NON-NLS-1$ //$NON-NLS-2$
			while (rs.next())
			{				
				this.idEntidade = rs.getInt("idEntidade");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idFundo FROM fundo WHERE idEntidade=" + this.idEntidade); //$NON-NLS-1$
			while (rs.next())
			{				
				idFundo = rs.getInt("idFundo");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.idFundo = idFundo;
		this.setMembers();		
	}
	
	public void showMembers()
	{		
		super.showMembers();
		System.out.println("\tidFundo:  "+ this.getIdFundo()); //$NON-NLS-1$
		System.out.println("\tidEntidade:  "+ this.getIdEntidade()); //$NON-NLS-1$
		System.out.println("\tidTipoDeCondominio:  "+ this.getIdTipoDeCondominio()); //$NON-NLS-1$
		System.out.println("\tidClasseCVM:  "+ this.getIdClasseCVM()); //$NON-NLS-1$
		System.out.println("\tprazoDeDuracao:  "+ this.getPrazoDeDuracao()); //$NON-NLS-1$
		System.out.println("\tidPublicoAlvo:  "+ this.getIdPublicoAlvo()); //$NON-NLS-1$
		System.out.println("\tvalorMinimoAplicacao:  "+ this.getValorMinimoAplicacao()); //$NON-NLS-1$
		System.out.println("\tvalorMinimoResgate:  "+ this.getValorMinimoResgate()); //$NON-NLS-1$
		System.out.println("\tsaldoMinimoCotista:  "+ this.getSaldoMinimoCotista()); //$NON-NLS-1$
		System.out.println("\tidEntidadeGestor:  "+ this.getIdEntidadeGestor()); //$NON-NLS-1$
		System.out.println("\tidEntidadeAdministrador:  "+ this.getIdEntidadeAdministrador()); //$NON-NLS-1$
		System.out.println("\tidEntidadeCustodiante:  "+ this.getIdEntidadeCustodiante()); //$NON-NLS-1$
		System.out.println("\tidEntidadeBancoCobrador:  "+ this.getIdEntidadeBancoCobrador()); //$NON-NLS-1$
		System.out.println("\tcodigoAnbima:  "+ this.getCodigoAnbima()); //$NON-NLS-1$
		System.out.println("\tcodigoCVM:  "+ this.getCodigoCVM()); //$NON-NLS-1$
		System.out.println("\tidCategoriaAnbima:  "+ this.getIdCategoriaAnbima()); //$NON-NLS-1$
		System.out.println("\tcategoriaGeralAnbima:  "+ this.getCategoriaGeralAnbima()); //$NON-NLS-1$
		System.out.println("\tidTipoAnbima:  "+ this.getIdTipoAnbima()); //$NON-NLS-1$
		System.out.println("\tidCotaFechamento:  "+ this.getIdCotaFechamento()); //$NON-NLS-1$
		System.out.println("\tidFundoAtivo:  "+ this.getIdFundoAtivo()); //$NON-NLS-1$
	}
	
	public void setMembers()
	{
		this.setIdEntidade();
		this.setIdTipoDeCondominio();
		this.setIdClasseCVM();
		this.setPrazoDeDuracao();
		this.setIdPublicoAlvo();
		this.setValorMinimoAplicacao();
		this.setValorMinimoResgate();
		this.setSaldoMinimoCotista();
		this.setIdEntidadeGestor();
		this.setIdEntidadeAdministrador();
		this.setIdEntidadeCustodiante();
		this.setIdEntidadeBancoCobrador();
		this.setCodigoAnbima();
		this.setCodigoCVM();
		this.setIdCategoriaAnbima();
		this.setCategoriaGeralAnbima();
		this.setIdTipoAnbima();
		this.setIdCotaFechamento();
		this.setIdFundoAtivo();
		super.setIdEntidade(this.getIdEntidade());
		super.setConn(this.getConn());
		super.setMembers();
	}
	
	public int getIdFundo() {
		return this.idFundo;
	}
	
	public void setIdFundo() 
	{
		
	}
	
	public int getIdEntidade() {
		if (this.idEntidade==0)
		{
			this.setIdEntidade();
		}
		return this.idEntidade;
	}
	public void setIdEntidade() {
		/**
		 * Finding idEntidade for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidade FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idEntidade = rs.getInt("idEntidade");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getIdTipoDeCondominio() {
		return this.idTipoDeCondominio;
	}
	public void setIdTipoDeCondominio() {
		/**
		 * Finding idTipoDeCondominio for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idTipoDeCondominio FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idTipoDeCondominio = rs.getInt("idTipoDeCondominio");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	public int getIdClasseCVM() {
		return this.idClasseCVM;
	}
	public void setIdClasseCVM() {
		/**
		 * Finding idClasseCVM for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idClasseCVM FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idClasseCVM = rs.getInt("idClasseCVM");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	public String getPrazoDeDuracao() {
		return this.prazoDeDuracao;
	}
	public void setPrazoDeDuracao() {
		/**
		 * Finding prazoDeDuracao for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT prazoDeDuracao FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.prazoDeDuracao = rs.getString("prazoDeDuracao");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}
	public int getIdPublicoAlvo() {
		return this.idPublicoAlvo;
	}
	public void setIdPublicoAlvo() {
		/**
		 * Finding idPublicoAlvo for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idPublicoAlvo FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idPublicoAlvo = rs.getInt("idPublicoAlvo");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}
	public double getValorMinimoAplicacao() {
		return this.valorMinimoAplicacao;
	}
	public void setValorMinimoAplicacao() {
		/**
		 * Finding valorMinimoAplicacao for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT valorMinimoAplicacao FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.valorMinimoAplicacao = rs.getDouble("valorMinimoAplicacao");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	public double getValorMinimoResgate() {
		return this.valorMinimoResgate;
	}
	public void setValorMinimoResgate() {
		/**
		 * Finding valorMinimoResgate for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT valorMinimoResgate FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.valorMinimoResgate = rs.getDouble("valorMinimoResgate");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	public double getSaldoMinimoCotista() {
		return this.saldoMinimoCotista;
	}
	public void setSaldoMinimoCotista() {
		/**
		 * Finding saldoMinimoCotista for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT saldoMinimoCotista FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.saldoMinimoCotista = rs.getDouble("saldoMinimoCotista");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public int getIdEntidadeGestor() {
		return this.idEntidadeGestor;
	}
	public void setIdEntidadeGestor() {
		/**
		 * Finding idEntidadeGestor for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidadeGestor FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idEntidadeGestor = rs.getInt("idEntidadeGestor");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}						
	}
	public int getIdEntidadeAdministrador() {
		return this.idEntidadeAdministrador;
	}
	public void setIdEntidadeAdministrador() {
		/**
		 * Finding idEntidadeAdministrador for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidadeAdministrador FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idEntidadeAdministrador = rs.getInt("idEntidadeAdministrador");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public int getIdEntidadeCustodiante() {
		return this.idEntidadeCustodiante;
	}
	public void setIdEntidadeCustodiante() {
		/**
		 * Finding idEntidadeCustodiante for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidadeCustodiante FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idEntidadeCustodiante = rs.getInt("idEntidadeCustodiante");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	public int getIdEntidadeBancoCobrador() {
		return this.idEntidadeBancoCobrador;
	}
	public void setIdEntidadeBancoCobrador() {
		/**
		 * Finding idEntidadeBancoCobrador for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidadeBancoCobrador FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idEntidadeBancoCobrador = rs.getInt("idEntidadeBancoCobrador");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public String getCodigoAnbima() {
		return this.codigoAnbima;
	}
	public void setCodigoAnbima() {
		/**
		 * Finding codigoAnbima for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT codigoAnbima FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.codigoAnbima = rs.getString("codigoAnbima");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public int getIdCategoriaAnbima() {
		return this.idCategoriaAnbima;
	}
	public void setIdCategoriaAnbima() {
		/**
		 * Finding idCategoriaAnbima for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idCategoriaAnbima FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idCategoriaAnbima = rs.getInt("idCategoriaAnbima");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}					
	}
	public void setCategoriaGeralAnbima() {
		/**
		 * Finding categoriaGeralAnbima for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT categoriaGeral FROM categoriaanbima WHERE idCategoriaAnbima=" + this.getIdCategoriaAnbima()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.categoriaGeralAnbima = rs.getString("categoriaGeral");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}					
	}
	
	
	public int getIdTipoAnbima() {
		return this.idTipoAnbima;
	}
	public void setIdTipoAnbima() {
		/**
		 * Finding idTipoAnbima for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idTipoAnbima FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idTipoAnbima = rs.getInt("idTipoAnbima");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	public int getIdCotaFechamento() {
		return this.idCotaFechamento;
	}
	public void setIdCotaFechamento() {
		/**
		 * Finding idCotaFechamento for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idCotaFechamento FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idCotaFechamento = rs.getInt("idCotaFechamento");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public int getIdFundoAtivo() {
		return this.idFundoAtivo;
	}
	public void setIdFundoAtivo() {
		/**
		 * Finding idFundoAtivo for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idFundoAtivo FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idFundoAtivo = rs.getInt("idFundoAtivo");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}

	public Connection getConn() {
		return this.conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getCodigoCVM() {
		return this.codigoCVM;
	}

	public void setCodigoCVM() {
		/**
		 * Finding codigoCVM for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT codigoCVM FROM fundo WHERE idFundo=" + this.getIdFundo()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.codigoCVM = rs.getString("codigoCVM");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}

	public String getCategoriaGeralAnbima() {
		return this.categoriaGeralAnbima;
	}	

}
