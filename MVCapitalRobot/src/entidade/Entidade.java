package entidade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Entidade 
{
	private int idEntidade;
	private String nome;
	private String nomeCurto;
	private String cadastro;
	private Date dataDeInicio;
	private int idSecaoCNAE;
	private int idSubclasseCNAE;
	private int idGrupoEconomico;
	private int idTipoDeCadastro;
	private String endereco;
	private String complemento;
	private int idCEP;
//	private int idBairro;
//	private int idCidade;
//	private int idUnidadeFederal;
	
	private Connection conn = null;
	
	public Entidade(Connection conn)
	{
		this.setConn(conn);
	}
	
	public Entidade(int idEntidade, Connection conn)
	{
		this.idEntidade = idEntidade;
		this.setConn(conn);
		this.setMembers();		
	}
	
	public void showMembers()
	{
		System.out.println("\tnome:  "+ this.getNome());
		System.out.println("\tnomeCurto:  "+ this.getNomeCurto());
		System.out.println("\tcadastro:  "+ this.getCadastro());
		System.out.println("\tdataDeInicio:  "+ this.getDataDeInicio());
		System.out.println("\tidSecaoCNAE:  "+ this.getIdSecaoCNAE());
		System.out.println("\tidSubclasseCNAE:  "+ this.getIdSubclasseCNAE());
		System.out.println("\tidGrupoEconomico:  "+ this.getIdGrupoEconomico());
		System.out.println("\tidTipoDeCadastro:  "+ this.getIdTipoDeCadastro());
		System.out.println("\tendereco:  "+ this.getEndereco());
		System.out.println("\tcomplemento:  "+ this.getComplemento());
		System.out.println("\tidCEP:  "+ this.getIdCEP());
//		System.out.println("\tidBairro:  "+ this.getIdBairro());
//		System.out.println("\tidCidade:  "+ this.getIdCidade());
//		System.out.println("\tidUnidadeFederal:  "+ this.getIdUnidadeFederal());
	}
	
	protected void setMembers()
	{
		this.setNome();
		this.setNomeCurto();
		this.setCadastro();
		this.setDataDeInicio();
		this.setIdSecaoCNAE();
		this.setIdSubclasseCNAE();
		this.setIdGrupoEconomico();
		this.setIdTipoDeCadastro();
		this.setEndereco();
		this.setIdCEP();
		this.setComplemento();
//		this.setIdBairro();
//		this.setIdCidade();
//		this.setIdUnidadeFederal();
	}
	
	public int getIdEntidade() {
		return idEntidade;
	}

	public String getNome() {
		return nome;
	}
	public void setNome() {
		/**
		 * Finding nome for Entidade
		 */
		//System.out.println("idEntidade within setNome " + idEntidade);
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nome FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
			while (rs.next())
			{				
				this.nome = rs.getString("nome");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	public String getNomeCurto() {
		return nomeCurto;
	}
	public void setNomeCurto() {
		/**
		 * Finding nomeCurto for Entidade
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nomeCurto FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
			while (rs.next())
			{				
				this.nomeCurto = rs.getString("nomeCurto");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}
	public String getCadastro() {
		return cadastro;
	}
	public void setCadastro() {
		/**
		 * Finding cadastro for Entidade
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT cadastro FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
			while (rs.next())
			{				
				this.cadastro = rs.getString("cadastro");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public Date getDataDeInicio() {
		return dataDeInicio;
	}
	public void setDataDeInicio() {
		/**
		 * Finding dataDeInicio for Entidade
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT dataDeInicio FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
			while (rs.next())
			{				
				this.dataDeInicio = rs.getDate("dataDeInicio");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public int getIdSecaoCNAE() {
		return idSecaoCNAE;
	}
	public void setIdSecaoCNAE() {
		/**
		 * Finding idSecaoCNAE for Entidade
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idSecaoCNAE FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
			while (rs.next())
			{				
				this.idSecaoCNAE = rs.getInt("idSecaoCNAE");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public int getIdSubclasseCNAE() {
		return idSubclasseCNAE;
	}
	public void setIdSubclasseCNAE() {
		/**
		 * Finding idSubclasseCNAE for Entidade
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idSubclasseCNAE FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
			while (rs.next())
			{				
				this.idSubclasseCNAE = rs.getInt("idSubclasseCNAE");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}					
	}
	public int getIdGrupoEconomico() {
		return idGrupoEconomico;
	}
	public void setIdGrupoEconomico() {
		/**
		 * Finding idGrupoEconomico for Entidade
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idGrupoEconomico FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
			while (rs.next())
			{				
				this.idGrupoEconomico = rs.getInt("idGrupoEconomico");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}					
	}
	public int getIdTipoDeCadastro() {
		return idTipoDeCadastro;
	}
	public void setIdTipoDeCadastro() {
		/**
		 * Finding idTipoDeCadastro for Entidade
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idTipoDeCadastro FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
			while (rs.next())
			{				
				this.idTipoDeCadastro = rs.getInt("idTipoDeCadastro");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco() {
		/**
		 * Finding endereco for Entidade
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT endereco FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
			while (rs.next())
			{				
				this.endereco = rs.getString("endereco");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}					
	}
	public void setIdCEP() {
		/**
		 * Finding idCep for Entidade
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idCEP FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
			while (rs.next())
			{				
				this.idCEP = rs.getInt("idCEP");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento() {
		/**
		 * Finding complemento for Entidade
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT complemento FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
			while (rs.next())
			{				
				this.complemento = rs.getString("complemento");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
//	public void setIdCidade() {
//		/**
//		 * Finding idCidade for Entidade
//		 */
//		try 
//		{
//			Statement stmt = (Statement) this.conn.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT idCidade FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
//			while (rs.next())
//			{				
//				this.idCidade = rs.getInt("idCidade");				
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}					
//	}
//	public void setIdUnidadeFederal() {
//		/**
//		 * Finding idUnidadeFederal for Entidade
//		 */
//		try 
//		{
//			Statement stmt = (Statement) this.conn.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT idUnidadeFederal FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
//			while (rs.next())
//			{				
//				this.idUnidadeFederal = rs.getInt("idUnidadeFederal");				
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}					
//	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public int getIdCEP() {
		return idCEP;
	}
//	public int getIdCidade() {
//		return idCidade;
//	}
//	public int getIdUnidadeFederal() {
//		return idUnidadeFederal;
//	}

	public void setIdEntidade(int idEntidade) {
		this.idEntidade = idEntidade;
	}
//
//	public int getIdBairro() {
//		return idBairro;
//	}

//	public void setIdBairro() {
//		/**
//		 * Finding idBairro for Entidade
//		 */
//		try 
//		{
//			Statement stmt = (Statement) this.conn.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT idBairro FROM Entidade WHERE idEntidade=" + this.getIdEntidade());
//			while (rs.next())
//			{				
//				this.idBairro = rs.getInt("idBairro");				
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}	}	
}
