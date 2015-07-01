package mvcapital.entidade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Entidade 
{
//	 idEntidade
//	 Nome
//	 NomeCurto
//	 Cadastro
//	 DataDeInicio
//	 idSubclasseCNAE
//	 idGrupoEconomico
//	 idTipoCadastro
//	 Endereco
//	 Numero
//	 Complemento
//	 idCEP
//	 DataAtualizacao
	
	private int idEntidade=0;
	private String nome=""; //$NON-NLS-1$
	private String nomeCurto=""; //$NON-NLS-1$
	private String cadastro=""; //$NON-NLS-1$
	private Date dataDeInicio=Calendar.getInstance().getTime();
	private int idSubclasseCNAE=0;
	private int idGrupoEconomico=0;
	private int idTipoCadastro=0;
	private String endereco=""; //$NON-NLS-1$
	private String numero=""; //$NON-NLS-1$
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
			
		String query = "SELECT idEntidade FROM Entidade WHERE cadastro = '" + cadastro + "'" + " limit 1"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
//			System.out.println(query);
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
			String sql = "INSERT INTO `Entidade` (`nome`,`nomeCurto`,`dataDeInicio`,`idTipoCadastro`,`cadastro`) VALUES ('"+nome+"','"+nomeCurto + "','" + stringDataCadastro + "',2,'" + cadastro +"')"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
//			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			System.out.println("New Cedente: " + nomeCedente);

			query = "SELECT idEntidade FROM Entidade WHERE nome like '" + nome + "'"; //$NON-NLS-1$ //$NON-NLS-2$
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
			
		String query = "SELECT idEntidade FROM Entidade WHERE nome like '" + nome + "'" + " limit 1"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
//			System.out.println(query);
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
			String sql = "INSERT INTO `Entidade` (`nome`,`nomeCurto`,`dataDeInicio`,`idTipoCadastro`) VALUES ('"+nome+"','"+nomeCurto + "','" + stringDataCadastro + "',2)"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
//			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			System.out.println("New Cedente: " + nomeCedente);

			query = "SELECT idEntidade FROM Entidade WHERE nome like '" + nome + "'"; //$NON-NLS-1$ //$NON-NLS-2$
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

	public static void updateCadastro(int idEntidade, String cadastro, Connection conn)	
	{
//		System.out.println("Constructor for " + nome + " " + conn);
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String sql = "UPDATE `Entidade` SET `cadastro` = '"+ cadastro + "' WHERE idEntidade = " + idEntidade; //$NON-NLS-1$ //$NON-NLS-2$
//			System.out.println(sql);
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//			System.out.println("New Cedente: " + nomeCedente);

	}
	
	public static void updateEndereco(int idEntidade, String endereco, String stringCEP, Connection conn)	
	{
//		System.out.println("Constructor for " + nome + " " + conn);
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		int idCEP = obtainCEP(stringCEP, conn);
		
		
		String sql = "UPDATE `Entidade` SET " //$NON-NLS-1$
							+ "`endereco` = " + "'"+ endereco + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							+ ",`idCEP` = "+ idCEP //$NON-NLS-1$
							+ " WHERE idEntidade = " + idEntidade; //$NON-NLS-1$
//		System.out.println(sql);
		
		if(idCEP!=0)
		{
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("NewCEP:"+stringCEP+ "\t"+endereco);
		}
//			System.out.println("New Cedente: " + nomeCedente);

	}	

	public static int obtainCEP(String stringCEP, Connection conn)	
	{
		int idCEP=0;
//		System.out.println("Constructor for " + nome + " " + conn);
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String query = "SELECT idCEP from cep where CEP=" + "'" + stringCEP  + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
				idCEP = rs.getInt("idCEP");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idCEP;
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
			ResultSet rs = stmt.executeQuery("SELECT nome FROM Entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT nomeCurto FROM Entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT cadastro FROM Entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT dataDeInicio FROM Entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.dataDeInicio = rs.getDate("dataDeInicio");				 //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT idSubclasseCNAE FROM Entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT idGrupoEconomico FROM Entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idGrupoEconomico = rs.getInt("idGrupoEconomico");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}					
	}
	public int getIdTipoDeCadastro() {
		return idTipoCadastro;
	}
	public void setIdTipoDeCadastro() {
		/**
		 * Finding idTipoDeCadastro for Entidade
		 */
		try 
		{
			Statement stmt = (Statement) this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idTipoCadastro FROM Entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.idTipoCadastro = rs.getInt("idTipoCadastro");				 //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT endereco FROM Entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT idCEP FROM Entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
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
			ResultSet rs = stmt.executeQuery("SELECT complemento FROM Entidade WHERE idEntidade=" + this.getIdEntidade()); //$NON-NLS-1$
			while (rs.next())
			{				
				this.complemento = rs.getString("complemento");				 //$NON-NLS-1$
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

	public int getIdCEP() {
		return idCEP;
	}

	public void setIdEntidade(int idEntidade) {
		this.idEntidade = idEntidade;
	}

	public static SimpleDateFormat getSdfd() {
		return sdfd;
	}

	public static void setSdfd(SimpleDateFormat sdfd) {
		Entidade.sdfd = sdfd;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCadastro(String cadastro) {
		this.cadastro = cadastro;
	}

	public void setDataDeInicio(Date dataDeInicio) {
		this.dataDeInicio = dataDeInicio;
	}

	public void setIdSubclasseCNAE(int idSubclasseCNAE) {
		this.idSubclasseCNAE = idSubclasseCNAE;
	}

	public void setIdGrupoEconomico(int idGrupoEconomico) {
		this.idGrupoEconomico = idGrupoEconomico;
	}

	public void setIdTipoDeCadastro(int idTipoDeCadastro) {
		this.idTipoCadastro = idTipoDeCadastro;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setIdCEP(int idCEP) {
		this.idCEP = idCEP;
	}

	public int getIdTipoCadastro() {
		return idTipoCadastro;
	}

	public void setIdTipoCadastro(int idTipoCadastro) {
		this.idTipoCadastro = idTipoCadastro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
}
