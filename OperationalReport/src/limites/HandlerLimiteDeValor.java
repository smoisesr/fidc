package limites;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import operation.DireitoCreditorioAttempt;
import operation.Operacao;
import relatorio.cessao.TipoDeRecebivel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import fundo.FundoDeInvestimento;

public class HandlerLimiteDeValor 
{

	public HandlerLimiteDeValor() 
	{
		
	}

	public static void assessLimiteDeValor(Operacao op, Connection conn)
	{
		ArrayList<LimiteDeValor> limitesDeValor = HandlerLimiteDeValor.readStoredLimiteDeValor(op, conn);
		
		for(LimiteDeValor limiteDeValor:limitesDeValor)
		{
			limiteDeValor.show();			
			if(limiteDeValor.getTipoDeLimite().equals("valorMinimo"))
			{
				assessLimiteDeValorMinimo(op, limiteDeValor);
			}
			if(limiteDeValor.getTipoDeLimite().equals("valorMaximo"))
			{
				assessLimiteDeValorMinimo(op, limiteDeValor);
			}
		}
	}
	
	public static void assessLimiteDeValorMinimo(Operacao op, Limite limiteDeValor)
	{
		LimiteDeValor limiteDeValorMinimo=(LimiteDeValor)limiteDeValor;
		for(DireitoCreditorioAttempt dca:op.getResumo().getDireitosCreditoriosAttempt())
		{
			if(dca.getDireitoCreditorio().getTipoDeRecebivel().getDescricao().equals(limiteDeValorMinimo.getTipoDeRecebivel().getDescricao()))
			{
				if(dca.getDireitoCreditorio().getValorDeAquisicao() >= limiteDeValorMinimo.getValor())
				{
					System.out.println("Valor minimo para "
							+ " " + dca.getDireitoCreditorio().getCampoChave()										
							+ " " + dca.getDireitoCreditorio().getValorDeAquisicao()
							+ " >= " + limiteDeValorMinimo.getValor()
							+ " OK!"
							);					
					limiteDeValorMinimo.setOk(true);					
				}
				else
				{
					System.out.println("Valor minimo para "
							+ " " + dca.getDireitoCreditorio().getCampoChave()										
							+ " " + dca.getDireitoCreditorio().getValorDeAquisicao()
							+ " < " + limiteDeValorMinimo.getValor()
							+ " BAD!"
							);					
				}
				dca.getLimites().add(limiteDeValorMinimo);
			}
		}
		
	}

	public static void assessLimiteDeValorMaximo(Operacao op, Limite limiteDeValor)
	{
		LimiteDeValor limiteDeValorMaximo=(LimiteDeValor)limiteDeValor;
		for(DireitoCreditorioAttempt dca:op.getResumo().getDireitosCreditoriosAttempt())
		{
			if(dca.getDireitoCreditorio().getTipoDeRecebivel().getDescricao().equals(limiteDeValorMaximo.getTipoDeRecebivel().getDescricao()))
			{
				if(dca.getDireitoCreditorio().getValorDeAquisicao() <= limiteDeValorMaximo.getValor())
				{
					System.out.println("Valor maximo para "
							+ " " + dca.getDireitoCreditorio().getCampoChave()										
							+ " " + dca.getDireitoCreditorio().getValorDeAquisicao()
							+ " <= " + limiteDeValorMaximo.getValor()
							+ " OK!"
							);					
					
					limiteDeValorMaximo.setOk(true);					
				}
				else
				{
					System.out.println("Valor maximo para "
							+ " " + dca.getDireitoCreditorio().getCampoChave()										
							+ " " + dca.getDireitoCreditorio().getValorDeAquisicao()
							+ " > " + limiteDeValorMaximo.getValor()
							+ " BAD!"
							);					
					
				}
				dca.getLimites().add(limiteDeValorMaximo);
			}
		}
		
	}
	
	
	public static ArrayList<LimiteDeValor> readStoredLimiteDeValor(Operacao op, Connection conn)
	{
		ArrayList<LimiteDeValor> limitesDeValor = new ArrayList<LimiteDeValor>();
				
		Statement stmt=null;
		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(TipoDeRecebivel tipoDeRecebivel:op.getRelatorio().getBlockDireitosCreditorios().getTiposDeRecebivel())
		{			
			String query = "SELECT * FROM limitedevalor WHERE"
							+ " " + "idFundo=" + op.getFundo().getIdFundo()
							+ " " + "AND idTipoDeRecebivel=" + tipoDeRecebivel.getIdTipoDeRecebivel()
							+ " " + "ORDER BY idTipoDeValor";
//			System.out.println(query);
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(query);
				while (rs.next())
				{
					LimiteDeValor limite = new LimiteDeValor();
					int idLimiteDeValor = rs.getInt("idLimiteDeValor");
					int idTipoDeValor = rs.getInt("idTipoDeValor");
					int idFundo = rs.getInt("idFundo");
					int idTipoDeRecebivel = rs.getInt("idTipoDeRecebivel");
					
					double valor = rs.getDouble("valor");
					
					limite.setIdLimite(idLimiteDeValor);
					limite.setIdTipoDeLimite(idTipoDeValor);
					limite.setFundo(new FundoDeInvestimento(idFundo,conn));
					limite.setTipoDeRecebivel(new TipoDeRecebivel(idTipoDeRecebivel, conn));
					limite.setValor(valor);
					
					
					Statement stmt2=null;
					try {
						stmt2 = (Statement) conn.createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
					}
	
					query = "SELECT tipo FROM tipodevalor WHERE idTipoDeValor=" + idTipoDeValor;
					
	//				System.out.println(query);
					ResultSet rs2 = null;
					try {
						rs2 = stmt2.executeQuery(query);
						while (rs2.next())
						{
							limite.setTipoDeLimite(rs2.getString("tipo"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					limitesDeValor.add(limite);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return limitesDeValor;
	}	
}
