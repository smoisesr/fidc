package mvcapital.conta;

import java.sql.ResultSet;
import java.sql.SQLException;

import mvcapital.entidade.Entidade;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Conta 
{
	private int idConta=0;
	private int idTipoConta=0;
	private Entidade entidadeCliente=new Entidade();
	private Entidade entidadeServidor=new Entidade();
	private String codigoServidor=""; //$NON-NLS-1$
	private String numero=""; //$NON-NLS-1$
	private String agencia=""; //$NON-NLS-1$
	private String usuario=""; //$NON-NLS-1$
	private String senha=""; //$NON-NLS-1$
	
	
	public Conta()
	{
		
	}
	public Conta(int idConta, Connection conn)
	{
		this.idConta = idConta;
		this.setMembers(idConta, conn);
	}

	public Conta(String agencia, String numeroConta, Connection conn)
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT * FROM conta WHERE" //$NON-NLS-1$
				+ " agencia = " + "'" + agencia + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ " AND " //$NON-NLS-1$
				+ " numero like " + "'" + numeroConta + "%'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
//		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.idConta=rs.getInt("idConta"); //$NON-NLS-1$
				this.idTipoConta=rs.getInt("idTipoConta"); //$NON-NLS-1$
				this.entidadeCliente=new Entidade(rs.getInt("idEntidadeCliente"), conn); //$NON-NLS-1$
				this.entidadeServidor=new Entidade(rs.getInt("idEntidadeServidor"), conn); //$NON-NLS-1$
				this.numero=numeroConta;
				this.agencia=agencia;
				this.usuario=rs.getString("usuario"); //$NON-NLS-1$
				this.senha=rs.getString("senha"); //$NON-NLS-1$
				this.setupCodigoServidor(conn);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		

	}
	
	
	public Conta(Entidade entidadeCliente, Entidade entidadeServidor, String agencia, String numero, int idTipoDeConta, Connection conn)
	{
		this.entidadeCliente = entidadeCliente;
		this.entidadeServidor = entidadeServidor;
		this.agencia = agencia;
		this.numero = numero;
		this.idTipoConta = idTipoDeConta;
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT codigo FROM codigobanco WHERE idEntidadeBanco= " + this.entidadeServidor.getIdEntidade(); //$NON-NLS-1$
//			System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.codigoServidor = rs.getString("codigo"); //$NON-NLS-1$
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		

	}

	public Conta(Entidade entidadeCliente, Entidade entidadeServidor, String usuario, String senha, String agencia, String numero, int idTipoDeConta, Connection conn)
	{
		this.entidadeCliente = entidadeCliente;
		this.entidadeServidor = entidadeServidor;
		this.agencia = agencia;
		this.numero = numero;
		this.idTipoConta = idTipoDeConta;
		this.usuario = usuario;
		this.senha = senha;
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT codigo FROM codigobanco WHERE idEntidadeBanco= " + this.entidadeServidor.getIdEntidade(); //$NON-NLS-1$
//			System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.codigoServidor = rs.getString("codigo"); //$NON-NLS-1$
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		

	}
	
	public Conta(Entidade entidadeCliente, String codigoServidor, String agencia, String numero, Connection conn)
	{
		this.entidadeCliente = entidadeCliente;
		this.codigoServidor = codigoServidor;
		this.agencia = agencia;
		this.numero = numero;
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT * FROM codigobanco WHERE codigo= \"" + this.codigoServidor + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.entidadeServidor = new Entidade(rs.getInt("idEntidadeBanco"), conn); //$NON-NLS-1$
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		this.idConta = getIdConta(this.getEntidadeCliente().getIdEntidade(), this.getCodigoServidor(), this.getAgencia(), this.getNumero(), conn);		
	}
	
	public Conta(   int idConta, 
					int idEntidadeCliente, 
					int idEntidadeServidor, 
					String numero, 
					String agencia, 
					String usuario, 
					String senha, 
					int idTipoDeConta
					, Connection conn)
	{
		this.idConta=idConta;
		this.entidadeCliente = new Entidade(idEntidadeCliente, conn);
		this.entidadeServidor = new Entidade(idEntidadeServidor, conn);
		this.numero=numero;
		this.agencia=agencia;
		this.usuario=usuario;
		this.senha=senha;
		this.idTipoConta=idTipoDeConta;
	}
	
	public void setupCodigoServidor(Connection conn)
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT codigo FROM codigobanco WHERE idEntidadeBanco= " + this.entidadeServidor.getIdEntidade(); //$NON-NLS-1$
//			System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.codigoServidor = rs.getString("codigo"); //$NON-NLS-1$
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		

	}
	
	public static int getIdConta(int idEntidadeCliente, String numeroBanco, String numeroAgencia, String numeroConta, Connection conn)
	{
//		+ ";" + op.getNumeroBancoContaCedenteSemAdiantamento1()
//		+ ";" + op.getNumeroAgenciaContaCedenteSemAdiantamento1()
//		+ ";" + op.getNumeroContaCorrenteContaCedenteSemAdiantamento1()
		System.out.println("Try to get idEntidadeBanco"); //$NON-NLS-1$
		int idEntidadeBanco = Entidade.getIdEntidadeBanco(numeroBanco, conn);
		int idConta=0;
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Inside getIdConta"); //$NON-NLS-1$
		String query = "SELECT * FROM `mvcapital`.`conta` WHERE idEntidadeCliente="+idEntidadeCliente+ " AND idEntidadeServidor=" + idEntidadeBanco + " AND numero=\"" + numeroConta + "\"" +  " AND agencia=\"" + numeroAgencia + "\""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
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
				idConta = rs.getInt("idConta");				 //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(idConta==0)
		{
			System.out.println("New Account!"); //$NON-NLS-1$
			String sql = "INSERT INTO `conta` (`idEntidadeCliente`, `idEntidadeServidor`, `numero`,`agencia`,`idTipoDeConta`)" +  //$NON-NLS-1$
							"VALUES ("  //$NON-NLS-1$
							+ idEntidadeCliente
							+ "," + idEntidadeBanco //$NON-NLS-1$
							+ "," + "\"" + numeroConta + "\"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							+ "," + "\"" + numeroAgencia + "\""  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							+ "," + 4 //$NON-NLS-1$
							+ ")"; //$NON-NLS-1$
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}			
			
			query = "SELECT * FROM `mvcapital`.`conta` WHERE idEntidadeCliente="+idEntidadeCliente+ " AND idEntidadeServidor=" + idEntidadeBanco + " AND numero=\"" + numeroConta + "\"" +  " AND agencia=\"" + numeroAgencia + "\""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			System.out.println(query);
			rs = null;
			try {
				rs = stmt.executeQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				while (rs.next())
				{
					idConta = rs.getInt("idConta");				 //$NON-NLS-1$
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		System.out.println("idConta: " + idConta); //$NON-NLS-1$
		return idConta;
	}
	
	public void setMembers(int idConta, Connection conn)
	{
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT * FROM conta WHERE idConta= " + idConta; //$NON-NLS-1$
//			System.out.println(query);
		try 
		{			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.entidadeCliente = new Entidade(rs.getInt("idEntidadeCliente"), conn); //$NON-NLS-1$
				this.entidadeServidor = new Entidade(rs.getInt("idEntidadeServidor"), conn); //$NON-NLS-1$
				this.numero = rs.getString("numero"); //$NON-NLS-1$
				this.agencia = rs.getString("agencia"); //$NON-NLS-1$
				this.usuario = rs.getString("usuario"); //$NON-NLS-1$
				this.senha = rs.getString("senha"); //$NON-NLS-1$
				this.idTipoConta = rs.getInt("idTipoDeConta"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		query = "SELECT codigo FROM codigobanco WHERE idEntidadeBanco= " + this.entidadeServidor.getIdEntidade(); //$NON-NLS-1$
	//		System.out.println(query);
		try 
		{			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.codigoServidor = rs.getString("codigo"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}					
	}
	
	public void show()
	{
		System.out.println(	 "idConta: " + this.idConta + "\n"  //$NON-NLS-1$ //$NON-NLS-2$
							+"entidadeCliente: " + this.entidadeCliente.getNome() + "\n"  //$NON-NLS-1$ //$NON-NLS-2$
							+"entidadeServidor" + this.entidadeServidor.getNome() + "\n" //$NON-NLS-1$ //$NON-NLS-2$
							+"numero: " + this.numero + "\n" //$NON-NLS-1$ //$NON-NLS-2$
							+"agencia: " + this.agencia + "\n" //$NON-NLS-1$ //$NON-NLS-2$
							+"usuario: " + this.usuario + "\n" //$NON-NLS-1$ //$NON-NLS-2$
							+"senha: " + this.senha + "\n" //$NON-NLS-1$ //$NON-NLS-2$
							+"idTipoDeConta: " + this.idTipoConta //$NON-NLS-1$
		);
		
	}
	public void showShort()
	{
		System.out.println( 
							"numero: " + this.numero + "\n" //$NON-NLS-1$ //$NON-NLS-2$
							+"agencia: " + this.agencia + "\n" //$NON-NLS-1$ //$NON-NLS-2$
							+"usuario: " + this.usuario + "\n" //$NON-NLS-1$ //$NON-NLS-2$
							+"senha: " + this.senha + "\n" //$NON-NLS-1$ //$NON-NLS-2$
		);
		
	}	
	
	public int getIdConta() {
		return this.idConta;
	}
	public void setIdConta(int idConta) {
		this.idConta = idConta;
	}
	public String getNumero() {
		return this.numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getAgencia() {
		return this.agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getUsuario() {
		return this.usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return this.senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getIdTipoDeConta() {
		return this.idTipoConta;
	}
	public void setIdTipoDeConta(int idTipoDeConta) {
		this.idTipoConta = idTipoDeConta;
	}
	public Entidade getEntidadeCliente() {
		return this.entidadeCliente;
	}
	public void setEntidadeCliente(Entidade entidadeCliente) {
		this.entidadeCliente = entidadeCliente;
	}
	public Entidade getEntidadeServidor() {
		return this.entidadeServidor;
	}
	public void setEntidadeServidor(Entidade entidadeServidor) {
		this.entidadeServidor = entidadeServidor;
	}
	public String getCodigoServidor() {
		return this.codigoServidor;
	}
	public void setCodigoServidor(String codigoServidor) {
		this.codigoServidor = codigoServidor;
	}
	
	
}
