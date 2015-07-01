package conta;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entidade.Entidade;

public class Conta 
{
	private int idConta=0;
	private Entidade entidadeProprietario=new Entidade();
	private Entidade entidadeServidor=new Entidade();
	private String codigoServidor="";
	private String numero="";
	private String agencia="";
	private String usuario="";
	private String senha="";
	private int idTipoDeConta=0;
	
	public Conta()
	{
		
	}
	public Conta(int idConta, Connection conn)
	{
		this.idConta = idConta;
		this.setMembers(idConta, conn);
	}
	
	public Conta(Entidade entidadeProprietario, Entidade entidadeServidor, String agencia, String numero, int idTipoDeConta, Connection conn)
	{
		this.entidadeProprietario = entidadeProprietario;
		this.entidadeServidor = entidadeServidor;
		this.agencia = agencia;
		this.numero = numero;
		this.idTipoDeConta = idTipoDeConta;
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT codigo FROM codigobanco WHERE idEntidadeBanco= " + this.entidadeServidor.getIdEntidade();
//			System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.codigoServidor = rs.getString("codigo");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		

	}

	public Conta(Entidade entidadeProprietario, Entidade entidadeServidor, String usuario, String senha, String agencia, String numero, int idTipoDeConta, Connection conn)
	{
		this.entidadeProprietario = entidadeProprietario;
		this.entidadeServidor = entidadeServidor;
		this.agencia = agencia;
		this.numero = numero;
		this.idTipoDeConta = idTipoDeConta;
		this.usuario = usuario;
		this.senha = senha;
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT codigo FROM codigobanco WHERE idEntidadeBanco= " + this.entidadeServidor.getIdEntidade();
//			System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.codigoServidor = rs.getString("codigo");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		

	}
	
	public Conta(Entidade entidadeProprietario, String codigoServidor, String agencia, String numero, Connection conn)
	{
		this.entidadeProprietario = entidadeProprietario;
		this.codigoServidor = codigoServidor;
		this.agencia = agencia;
		this.numero = numero;
		Statement stmt = null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		String query = "SELECT * FROM codigobanco WHERE codigo= \"" + this.codigoServidor + "\"";
		System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.entidadeServidor = new Entidade(rs.getInt("idEntidadeBanco"), conn);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		this.idConta = getIdConta(this.getEntidadeProprietario().getIdEntidade(), this.getCodigoServidor(), this.getAgencia(), this.getNumero(), conn);		
	}
	
	public Conta(   int idConta, 
					int idEntidadeProprietario, 
					int idEntidadeServidor, 
					String numero, 
					String agencia, 
					String usuario, 
					String senha, 
					int idTipoDeConta
					, Connection conn)
	{
		this.idConta=idConta;
		this.entidadeProprietario = new Entidade(idEntidadeProprietario, conn);
		this.entidadeServidor = new Entidade(idEntidadeServidor, conn);
		this.numero=numero;
		this.agencia=agencia;
		this.usuario=usuario;
		this.senha=senha;
		this.idTipoDeConta=idTipoDeConta;
	}
	
	public static int getIdConta(int idEntidadeCliente, String numeroBanco, String numeroAgencia, String numeroConta, Connection conn)
	{
//		+ ";" + op.getNumeroBancoContaCedenteSemAdiantamento1()
//		+ ";" + op.getNumeroAgenciaContaCedenteSemAdiantamento1()
//		+ ";" + op.getNumeroContaCorrenteContaCedenteSemAdiantamento1()
		System.out.println("Try to get idEntidadeBanco");
		int idEntidadeBanco = Entidade.getIdEntidadeBanco(numeroBanco, conn);
		int idConta=0;
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Inside getIdConta");
		String query = "SELECT * FROM `mvcapital`.`conta` WHERE idEntidadeProprietario="+idEntidadeCliente+ " AND idEntidadeServidor=" + idEntidadeBanco + " AND numero=\"" + numeroConta + "\"" +  " AND agencia=\"" + numeroAgencia + "\"";
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
				idConta = rs.getInt("idConta");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(idConta==0)
		{
			System.out.println("New Account!");
			String sql = "INSERT INTO `mvcapital`.`conta` (`idEntidadeProprietario`, `idEntidadeServidor`, `numero`,`agencia`,`idTipoDeConta`)" + 
							"VALUES (" 
							+ idEntidadeCliente
							+ "," + idEntidadeBanco
							+ "," + "\"" + numeroConta + "\""
							+ "," + "\"" + numeroAgencia + "\"" 
							+ "," + 4
							+ ")";
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}			
			
			query = "SELECT * FROM `mvcapital`.`conta` WHERE idEntidadeProprietario="+idEntidadeCliente+ " AND idEntidadeServidor=" + idEntidadeBanco + " AND numero=\"" + numeroConta + "\"" +  " AND agencia=\"" + numeroAgencia + "\"";
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
					idConta = rs.getInt("idConta");				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		System.out.println("idConta: " + idConta);
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
			
		String query = "SELECT * FROM conta WHERE idConta= " + idConta;
//			System.out.println(query);
		try 
		{			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.entidadeProprietario = new Entidade(rs.getInt("idEntidadeProprietario"), conn);
				this.entidadeServidor = new Entidade(rs.getInt("idEntidadeServidor"), conn);
				this.numero = rs.getString("numero");
				this.agencia = rs.getString("agencia");
				this.usuario = rs.getString("usuario");
				this.senha = rs.getString("senha");
				this.idTipoDeConta = rs.getInt("idTipoDeConta");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		query = "SELECT codigo FROM codigobanco WHERE idEntidadeBanco= " + this.entidadeServidor.getIdEntidade();
	//		System.out.println(query);
		try 
		{			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{				
				this.codigoServidor = rs.getString("codigo");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}					
	}
	
	public void show()
	{
		System.out.println(	 "idConta: " + this.idConta + "\n" 
							+"entidadeProprietario: " + this.entidadeProprietario.getNome() + "\n" 
							+"entidadeServidor" + this.entidadeServidor.getNome() + "\n"
							+"numero: " + this.numero + "\n"
							+"agencia: " + this.agencia + "\n"
							+"usuario: " + this.usuario + "\n"
							+"senha: " + this.senha + "\n"
							+"idTipoDeConta: " + this.idTipoDeConta
		);
		
	}
	public void showShort()
	{
		System.out.println( 
							"numero: " + this.numero + "\n"
							+"agencia: " + this.agencia + "\n"
							+"usuario: " + this.usuario + "\n"
							+"senha: " + this.senha + "\n"
		);
		
	}	
	
	public int getIdConta() {
		return idConta;
	}
	public void setIdConta(int idConta) {
		this.idConta = idConta;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getIdTipoDeConta() {
		return idTipoDeConta;
	}
	public void setIdTipoDeConta(int idTipoDeConta) {
		this.idTipoDeConta = idTipoDeConta;
	}
	public Entidade getEntidadeProprietario() {
		return entidadeProprietario;
	}
	public void setEntidadeProprietario(Entidade entidadeProprietario) {
		this.entidadeProprietario = entidadeProprietario;
	}
	public Entidade getEntidadeServidor() {
		return entidadeServidor;
	}
	public void setEntidadeServidor(Entidade entidadeServidor) {
		this.entidadeServidor = entidadeServidor;
	}
	public String getCodigoServidor() {
		return codigoServidor;
	}
	public void setCodigoServidor(String codigoServidor) {
		this.codigoServidor = codigoServidor;
	}
	
	
}
