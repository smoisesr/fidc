package entidade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Entidade 
{
	private int idEntidade=0;
	private String nome=""; //$NON-NLS-1$
	private String nomeCurto=""; //$NON-NLS-1$
	private String cadastro=""; //$NON-NLS-1$
	private Date dataDeInicio=Calendar.getInstance().getTime();
	private int idSecaoCNAE=0;
	private int idSubclasseCNAE=0;
	private int idGrupoEconomico=0;
	private int idTipoDeCadastro=0;
	private String endereco=""; //$NON-NLS-1$
	private String complemento=""; //$NON-NLS-1$
	private int idCEP=0;
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");	 //$NON-NLS-1$
	private Connection conn = null;
	
	public Entidade()
	{
		
	}
	
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

	public Entidade(String nome, Connection conn)	
	{
		this.conn = conn;
		int idEntidade=0;		
//		System.out.println("Constructor for " + nome + " " + conn);
		Statement stmt = null;
		try {
			stmt = (Statement) this.conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT idEntidade FROM entidade WHERE nome like '" + nome + "'" + " limit 1"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		System.out.println(query);
		try 
		{			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				idEntidade = rs.getInt("idEntidade");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		if(idEntidade==0)
		{
			try {
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			String nomeCurto = nome;
			if(nome.length()>45)
			{
				nomeCurto = nome.substring(0, 45);
			}
			String stringDataCadastro = sdfd.format(Calendar.getInstance().getTime());
			String sql = "INSERT INTO `MVCapital`.`Entidade` (`nome`,`nomeCurto`,`dataDeInicio`,`idTipoDeCadastro`) VALUES ('"+nome+"','"+nomeCurto + "','" + stringDataCadastro + "',2)"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			System.out.println("New Cedente: " + nomeCedente);

			query = "SELECT idEntidade FROM entidade WHERE nome like '" + nome + "'"; //$NON-NLS-1$ //$NON-NLS-2$
//			System.out.println(query);
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				while (rs.next())
				{
					idEntidade = rs.getInt("idEntidade");					 //$NON-NLS-1$
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		this.idEntidade=idEntidade;
		this.setConn(conn);
		this.setMembers();		
	}
	
	public Entidade(String nome, String cadastro, Connection conn)	
	{
		this.conn = conn;
		int idEntidade=0;		
//		System.out.println("Constructor for " + nome + " " + conn);
		Statement stmt = null;
		try {
			stmt = (Statement) this.conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT idEntidade FROM entidade WHERE cadastro = '" + cadastro + "'"; //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println(query);
		try 
		{			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				idEntidade = rs.getInt("idEntidade");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		if(idEntidade==0)
		{
			try {
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			String nomeCurto = nome;
			if(nome.length()>45)
			{
				nomeCurto = nome.substring(0, 45);
			}
			String stringDataCadastro = sdfd.format(Calendar.getInstance().getTime());
			String sql = "INSERT INTO `MVCapital`.`Entidade` (`nome`,`nomeCurto`,`cadastro`,`dataDeInicio`,`idTipoDeCadastro`) VALUES ('"+nome+"','"+nomeCurto+"','"+ cadastro + "','" + stringDataCadastro + "',2)"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			System.out.println("New Cedente: " + nomeCedente);

			query = "SELECT idEntidade FROM entidade WHERE nome like '" + nome + "'"; //$NON-NLS-1$ //$NON-NLS-2$
//			System.out.println(query);
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				while (rs.next())
				{
					idEntidade = rs.getInt("idEntidade");					 //$NON-NLS-1$
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		this.idEntidade=idEntidade;
		this.setConn(conn);
		this.setMembers();		
	}
	
	public static int getIdEntidadeBanco(String numeroBanco, Connection conn)
	{
		int idEntidadeBanco=0;
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String query = "SELECT idEntidadeBanco FROM codigobanco WHERE codigo=" + numeroBanco; //$NON-NLS-1$
		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next())
			{
				idEntidadeBanco = rs.getInt("idEntidadeBanco");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(idEntidadeBanco==0)
		{
			System.out.println("Error bringing idEntidadeBanco"); //$NON-NLS-1$
		}
		return idEntidadeBanco;
	}	

	public void showMembers()
	{
		System.out.println("\tnome:  "+ this.getNome()); //$NON-NLS-1$
		System.out.println("\tnomeCurto:  "+ this.getNomeCurto()); //$NON-NLS-1$
		System.out.println("\tcadastro:  "+ this.getCadastro()); //$NON-NLS-1$
		System.out.println("\tdataDeInicio:  "+ this.getDataDeInicio()); //$NON-NLS-1$
		System.out.println("\tidSecaoCNAE:  "+ this.getIdSecaoCNAE()); //$NON-NLS-1$
		System.out.println("\tidSubclasseCNAE:  "+ this.getIdSubclasseCNAE()); //$NON-NLS-1$
		System.out.println("\tidGrupoEconomico:  "+ this.getIdGrupoEconomico()); //$NON-NLS-1$
		System.out.println("\tidTipoDeCadastro:  "+ this.getIdTipoDeCadastro()); //$NON-NLS-1$
		System.out.println("\tendereco:  "+ this.getEndereco()); //$NON-NLS-1$
		System.out.println("\tcomplemento:  "+ this.getComplemento()); //$NON-NLS-1$
		System.out.println("\tidCEP:  "+ this.getIdCEP()); //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT nome FROM entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.nome = rs.getString("nome");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	public String getNomeCurto() {
		return nomeCurto;
	}
	public void setNomeCurto(String nomeCurto) 
	{
		this.nomeCurto = nomeCurto;
	}
	
	public void setNomeCurto() {
		/**
		 * Finding nomeCurto for Entidade
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nomeCurto FROM entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.nomeCurto = rs.getString("nomeCurto");				 //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT cadastro FROM entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.cadastro = rs.getString("cadastro");				 //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT dataDeInicio FROM entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.dataDeInicio = rs.getDate("dataDeInicio");				 //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT idSecaoCNAE FROM entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idSecaoCNAE = rs.getInt("idSecaoCNAE");				 //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT idSubclasseCNAE FROM entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idSubclasseCNAE = rs.getInt("idSubclasseCNAE");				 //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT idGrupoEconomico FROM entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idGrupoEconomico = rs.getInt("idGrupoEconomico");				 //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT idTipoDeCadastro FROM entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idTipoDeCadastro = rs.getInt("idTipoDeCadastro");				 //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT endereco FROM entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.endereco = rs.getString("endereco");				 //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT idCEP FROM entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idCEP = rs.getInt("idCEP");				 //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT complemento FROM entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.complemento = rs.getString("complemento");				 //$NON-NLS-1$
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
//			ResultSet rs = stmt.executeQuery("SELECT idCidade FROM entidade WHERE idEntidade=" + this.getIdEntidade());
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
//			ResultSet rs = stmt.executeQuery("SELECT idUnidadeFederal FROM entidade WHERE idEntidade=" + this.getIdEntidade());
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
//			ResultSet rs = stmt.executeQuery("SELECT idBairro FROM entidade WHERE idEntidade=" + this.getIdEntidade());
//			while (rs.next())
//			{				
//				this.idBairro = rs.getInt("idBairro");				
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}	}	
}
