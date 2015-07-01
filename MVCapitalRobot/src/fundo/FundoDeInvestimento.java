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
	
	public FundoDeInvestimento(int idFundo, Connection conn)
	{
		super(conn);
		this.idFundo = idFundo;
		this.setConn(conn);
		this.setMembers();		
	}
	
	public void showMembers()
	{		
		super.showMembers();
		System.out.println("\tidFundo:  "+ this.getIdFundo());
		System.out.println("\tidEntidade:  "+ this.getIdEntidade());
		System.out.println("\tidTipoDeCondominio:  "+ this.getIdTipoDeCondominio());
		System.out.println("\tidClasseCVM:  "+ this.getIdClasseCVM());
		System.out.println("\tprazoDeDuracao:  "+ this.getPrazoDeDuracao());
		System.out.println("\tidPublicoAlvo:  "+ this.getIdPublicoAlvo());
		System.out.println("\tvalorMinimoAplicacao:  "+ this.getValorMinimoAplicacao());
		System.out.println("\tvalorMinimoResgate:  "+ this.getValorMinimoResgate());
		System.out.println("\tsaldoMinimoCotista:  "+ this.getSaldoMinimoCotista());
		System.out.println("\tidEntidadeGestor:  "+ this.getIdEntidadeGestor());
		System.out.println("\tidEntidadeAdministrador:  "+ this.getIdEntidadeAdministrador());
		System.out.println("\tidEntidadeCustodiante:  "+ this.getIdEntidadeCustodiante());
		System.out.println("\tidEntidadeBancoCobrador:  "+ this.getIdEntidadeBancoCobrador());
		System.out.println("\tcodigoAnbima:  "+ this.getCodigoAnbima());
		System.out.println("\tcodigoCVM:  "+ this.getCodigoCVM());
		System.out.println("\tidCategoriaAnbima:  "+ this.getIdCategoriaAnbima());
		System.out.println("\tcategoriaGeralAnbima:  "+ this.getCategoriaGeralAnbima());
		System.out.println("\tidTipoAnbima:  "+ this.getIdTipoAnbima());
		System.out.println("\tidCotaFechamento:  "+ this.getIdCotaFechamento());
		System.out.println("\tidFundoAtivo:  "+ this.getIdFundoAtivo());
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
		return idFundo;
	}
	public void setIdFundo() {
	}
	public int getIdEntidade() {
		if (this.idEntidade==0)
		{
			this.setIdEntidade();
		}
		return idEntidade;
	}
	public void setIdEntidade() {
		/**
		 * Finding idEntidade for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidade FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.idEntidade = rs.getInt("idEntidade");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getIdTipoDeCondominio() {
		return idTipoDeCondominio;
	}
	public void setIdTipoDeCondominio() {
		/**
		 * Finding idTipoDeCondominio for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idTipoDeCondominio FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.idTipoDeCondominio = rs.getInt("idTipoDeCondominio");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	public int getIdClasseCVM() {
		return idClasseCVM;
	}
	public void setIdClasseCVM() {
		/**
		 * Finding idClasseCVM for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idClasseCVM FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.idClasseCVM = rs.getInt("idClasseCVM");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	public String getPrazoDeDuracao() {
		return prazoDeDuracao;
	}
	public void setPrazoDeDuracao() {
		/**
		 * Finding prazoDeDuracao for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT prazoDeDuracao FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.prazoDeDuracao = rs.getString("prazoDeDuracao");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}
	public int getIdPublicoAlvo() {
		return idPublicoAlvo;
	}
	public void setIdPublicoAlvo() {
		/**
		 * Finding idPublicoAlvo for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idPublicoAlvo FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.idPublicoAlvo = rs.getInt("idPublicoAlvo");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}
	public double getValorMinimoAplicacao() {
		return valorMinimoAplicacao;
	}
	public void setValorMinimoAplicacao() {
		/**
		 * Finding valorMinimoAplicacao for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT valorMinimoAplicacao FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.valorMinimoAplicacao = rs.getDouble("valorMinimoAplicacao");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	public double getValorMinimoResgate() {
		return valorMinimoResgate;
	}
	public void setValorMinimoResgate() {
		/**
		 * Finding valorMinimoResgate for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT valorMinimoResgate FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.valorMinimoResgate = rs.getDouble("valorMinimoResgate");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	public double getSaldoMinimoCotista() {
		return saldoMinimoCotista;
	}
	public void setSaldoMinimoCotista() {
		/**
		 * Finding saldoMinimoCotista for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT saldoMinimoCotista FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.saldoMinimoCotista = rs.getDouble("saldoMinimoCotista");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public int getIdEntidadeGestor() {
		return idEntidadeGestor;
	}
	public void setIdEntidadeGestor() {
		/**
		 * Finding idEntidadeGestor for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidadeGestor FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.idEntidadeGestor = rs.getInt("idEntidadeGestor");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}						
	}
	public int getIdEntidadeAdministrador() {
		return idEntidadeAdministrador;
	}
	public void setIdEntidadeAdministrador() {
		/**
		 * Finding idEntidadeAdministrador for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidadeAdministrador FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.idEntidadeAdministrador = rs.getInt("idEntidadeAdministrador");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public int getIdEntidadeCustodiante() {
		return idEntidadeCustodiante;
	}
	public void setIdEntidadeCustodiante() {
		/**
		 * Finding idEntidadeCustodiante for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidadeCustodiante FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.idEntidadeCustodiante = rs.getInt("idEntidadeCustodiante");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	public int getIdEntidadeBancoCobrador() {
		return idEntidadeBancoCobrador;
	}
	public void setIdEntidadeBancoCobrador() {
		/**
		 * Finding idEntidadeBancoCobrador for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idEntidadeBancoCobrador FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.idEntidadeBancoCobrador = rs.getInt("idEntidadeBancoCobrador");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public String getCodigoAnbima() {
		return codigoAnbima;
	}
	public void setCodigoAnbima() {
		/**
		 * Finding codigoAnbima for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT codigoAnbima FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.codigoAnbima = rs.getString("codigoAnbima");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public int getIdCategoriaAnbima() {
		return idCategoriaAnbima;
	}
	public void setIdCategoriaAnbima() {
		/**
		 * Finding idCategoriaAnbima for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idCategoriaAnbima FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.idCategoriaAnbima = rs.getInt("idCategoriaAnbima");				
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
			ResultSet rs = stmt.executeQuery("SELECT categoriaGeral FROM CategoriaAnbima WHERE idCategoriaAnbima=" + this.getIdCategoriaAnbima());
			while (rs.next())
			{				
				this.categoriaGeralAnbima = rs.getString("categoriaGeral");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}					
	}
	
	
	public int getIdTipoAnbima() {
		return idTipoAnbima;
	}
	public void setIdTipoAnbima() {
		/**
		 * Finding idTipoAnbima for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idTipoAnbima FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.idTipoAnbima = rs.getInt("idTipoAnbima");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	public int getIdCotaFechamento() {
		return idCotaFechamento;
	}
	public void setIdCotaFechamento() {
		/**
		 * Finding idCotaFechamento for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idCotaFechamento FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.idCotaFechamento = rs.getInt("idCotaFechamento");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public int getIdFundoAtivo() {
		return idFundoAtivo;
	}
	public void setIdFundoAtivo() {
		/**
		 * Finding idFundoAtivo for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idFundoAtivo FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.idFundoAtivo = rs.getInt("idFundoAtivo");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getCodigoCVM() {
		return codigoCVM;
	}

	public void setCodigoCVM() {
		/**
		 * Finding codigoCVM for Fund
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT codigoCVM FROM Fundo WHERE idFundo=" + this.getIdFundo());
			while (rs.next())
			{				
				this.codigoCVM = rs.getString("codigoCVM");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}

	public String getCategoriaGeralAnbima() {
		return categoriaGeralAnbima;
	}	

}
