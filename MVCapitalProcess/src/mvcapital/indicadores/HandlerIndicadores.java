package mvcapital.indicadores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mvcapital.mysql.MySQLAccess;
import mvcapital.relatorio.cessao.Titulo;
import mvcapital.utils.WorkingDays;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class HandlerIndicadores
{
	private static  Connection conn=null;
	private static ArrayList<Date> datasEstoque = new ArrayList<Date>();
	private static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	private static int idTipoDireitosCreditorios = 0;
	private static ArrayList<Integer> idTipoCreditosVencidosNaoPagos = new ArrayList<Integer>();
	private static ArrayList<Integer> idTipoProvisaoParaDevedoresDuvidosos = new ArrayList<Integer>();
	private static ArrayList<Integer> idTipoVolumeTipoTitulo = new ArrayList<Integer>();
	private static ArrayList<Integer> idTipoTaxas = new ArrayList<Integer>();
	private static ArrayList<Integer> idTipoPrazos = new ArrayList<Integer>();
	
	
	private static ArrayList<Integer> idTipoOcorrenciaBaixa = new ArrayList<Integer>();
	
	public HandlerIndicadores()
	{
		
	}
	
	public static void main(String[] args)
	{
		HandlerIndicadores.connect();
		System.out.println("Beginning at " + Calendar.getInstance().getTime());
		HandlerIndicadores.setIdTipoIndicadores();
		HandlerIndicadores.Calculate();
		System.out.println("Ending at " + Calendar.getInstance().getTime());
	}
	
	public static void connect()
	{
		MySQLAccess.readConf();
		MySQLAccess mysqlAccess = new MySQLAccess();
		mysqlAccess.connect();
		conn = (Connection) mysqlAccess.getConn();		
	}

	public static void Calculate()
	{
		String query = "select DISTINCT data from estoque" //$NON-NLS-1$
				+ " where" //$NON-NLS-1$
				+ " data > '2015-02-01'" //$NON-NLS-1$
//				+ " and  data > '2015-01-01'" //$NON-NLS-1$
				; 
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
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
				Date dataEstoque = rs.getDate("data"); //$NON-NLS-1$
				System.out.println(dataEstoque);
				datasEstoque.add(dataEstoque);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		int iData=0;		
		for(iData=0;iData<datasEstoque.size();iData++)
		{
			{
				calculateDate(datasEstoque.get(iData));
			}
			if(iData%2 == 0)
			{
				try
				{					
					System.out.println("Sleeping at " + Calendar.getInstance().getTime()); //$NON-NLS-1$
					Thread.sleep(3000);
					System.out.println("After Sleep" + Calendar.getInstance().getTime()); //$NON-NLS-1$
					System.gc();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void calculateDate(Date dataEstoque)
	{
		System.out.println("-------------------------------"); //$NON-NLS-1$
		System.out.println("Funds for date " + dataEstoque); //$NON-NLS-1$
		String query = "select DISTINCT idFundo from estoque where data = "  //$NON-NLS-1$
						+ "'" + sdfd.format(dataEstoque) + "'"; //$NON-NLS-1$ //$NON-NLS-2$
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		HandlerCalculator.setConn(conn);
//		ArrayList<HandlerCalculator> hcs = new ArrayList<HandlerCalculator>();
		try
		{
			while (rs.next())
			{
				int idFundo = rs.getInt("idFundo"); //$NON-NLS-1$
				System.out.println("Fundo " + idFundo); //$NON-NLS-1$
				{
//					HandlerCalculator hc = new HandlerCalculator();
//					hc.setDataEstoque(dataEstoque);
//					hc.setIdFundo(idFundo);
//					hcs.add(hc);
//					hc.start();					
					calculateFund(dataEstoque,idFundo, conn);
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
//		boolean allDone=false;
//		while(!allDone)
//		{
//			for(HandlerCalculator hc:hcs)
//			{
//				if(!hc.isDone())
//				{
//					break;
//				}
//				allDone=true;				
//			}
//			try
//			{
//				System.out.println("Waiting to process all funds in " + dataEstoque);
//				Thread.sleep(1000);
//			} catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
//		}
	}
	
	public static void calculateFund(Date dataEstoque, int idFundo, Connection conn)
	{
		ArrayList<Integer> idTitulos = new ArrayList<Integer>();
		
//		System.out.println("Funds for date " + dataEstoque); //$NON-NLS-1$
		String query = "select idTitulo from estoque where " //$NON-NLS-1$
						+ " idFundo="+ idFundo //$NON-NLS-1$
						+ " and data = " + "'" 	+ sdfd.format(dataEstoque) + "'";  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
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
				int idTitulo = rs.getInt("idTitulo"); //$NON-NLS-1$
//				System.out.println(idTitulo);	
				idTitulos.add(idTitulo);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}	
		
		System.out.println(idTitulos.size() + " titulos em estoque"); //$NON-NLS-1$
//		double valorDC=0.0;
//		{
//			valorDC=DireitosCreditoriosIndicador.Calculate(idTitulos, dataEstoque, conn);
//		}
//		{
//			HandlerIndicadores.storeIndicador(idFundo, idTipoDireitosCreditorios, valorDC, dataEstoque, conn);
//		}

//		ArrayList<Double> valorVolumeTipoTitulo=null;		
//		{
//			valorVolumeTipoTitulo = VolumePorTipoTitulo.Calculate(idTitulos, HandlerIndicadores.idTipoVolumeTipoTitulo, dataEstoque, conn);
//		}
//		{
//			HandlerIndicadores.storeGrupoIndicadores(idFundo, dataEstoque, valorVolumeTipoTitulo, idTipoVolumeTipoTitulo, conn);			
//		}
//		{
//			ArrayList<Double> valorPDD=PDDIndicador.Calculate(idTitulos, dataEstoque, conn);
//			HandlerIndicadores.storeGrupoIndicadores(idFundo, dataEstoque, valorPDD, idTipoProvisaoParaDevedoresDuvidosos, conn);
//		}
		{
			ArrayList<Double> valorCVNP=CVNPIndicador.Calculate(idTitulos, dataEstoque, conn);
			HandlerIndicadores.storeGrupoIndicadores(idFundo, dataEstoque, valorCVNP, idTipoCreditosVencidosNaoPagos, conn);
		}
//		{
//			ArrayList<Double> valorTaxas=TaxasIndicador.Calculate(idTitulos, dataEstoque, conn);
//			HandlerIndicadores.storeGrupoIndicadores(idFundo, dataEstoque, valorTaxas, idTipoTaxas, conn);			
//		}
//		{
//			ArrayList<Double> valorPrazo = PrazosIndicador.Calculate(idTitulos, dataEstoque, conn);
//			HandlerIndicadores.storeGrupoIndicadores(idFundo, dataEstoque, valorPrazo, idTipoPrazos, conn);			
//		}
		
		
		
		idTitulos=null;
	}

	public static double taxaCDI(Date dataEstoque, Connection conn)
	{

		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		String query = "SELECT * FROM cdiover where" //$NON-NLS-1$
					+ " data = " + "'" + sdfd.format(dataEstoque) + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
//		System.out.println(query);
		ResultSet rs = null;
		double taxaDIOver=0.0;
		try {
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				taxaDIOver=rs.getDouble("taxa"); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		taxaDIOver = Math.pow(taxaDIOver/100.00+1,(1.0/252.0))-1.0;
		
		return taxaDIOver;
	}
	
	public static void setidTipoOcorrenciaBaixa()
	{
//		# idTipoOcorrencia, Descricao, DataAtualizacao
//		'1', 'Remessa', '2014-12-16 12:13:35'
//		'2', 'Baixa Normal', '2014-11-27 17:38:03'
//		'3', 'Baixa por Deposito Cedente', '2014-12-16 12:13:35'
//		'4', 'Baixa por Deposito Sacado', '2014-12-16 12:13:35'
//		'5', 'Baixa por Deposito Consultoria', '2014-12-16 12:13:35'
//		'6', 'Pagamento Parcial', '2014-12-16 12:13:35'
//		'7', 'Abatimento', '2014-11-27 17:38:04'
//		'8', 'Baixa Estoque', '2014-11-27 17:38:04'
//		'9', 'Cheque 1ra Devolucao', '2014-12-16 12:13:35'
//		'10', 'Cheque 2da Devolucao', '2014-12-16 12:13:35'
//		'11', 'Cheque Depositado', '2014-12-16 12:13:35'
//		'12', 'Alteracao de Vencimento', '2014-12-16 12:28:57'
//		'13', 'Baixa por Recompra Paulista', '2014-12-16 12:28:57'
//		'14', 'Recompra Parcial sem Adiantamento', '2014-12-16 12:28:57'
//		'15', 'Recompra Parcial com Adiantamento', '2014-12-16 12:28:57'
//		'16', 'Baixa por Recompra', '2014-12-16 12:28:57'
//		'17', 'Remessa Paulista', '2014-12-16 12:31:19'
//		'18', 'Entrada por Recompra Paulista', '2014-12-16 12:31:19'
//		'19', 'Entrada por Recompra', '2014-12-16 12:31:19'
//		'20', 'Reativacao', '2014-12-16 12:28:57'
//		'21', 'Aquisicao de Contratos Futuros', '2014-12-16 12:28:57'
//		'22', 'Aquisicao de Baixa de Contratos Futuros', '2014-12-16 12:28:57'
//		'23', 'Baixado Conforme Instrucoes da Agencia', '2014-12-29 13:02:30'
//		'24', 'Baixa Automatica', '2014-12-29 13:02:30'
//		'25', 'Liquidacao em Cartorio', '2014-12-29 13:02:30'
//		'26', 'Título Pago em Cheque', '2014-12-29 13:02:30'
//		'27', 'Baixado Automaticamente via Arquivo', '2014-12-29 13:02:30'
//		'28', 'Liquidacao por Compensacao Eletronica', '2014-12-29 13:02:30'
//		'29', 'Liquidacao por Compensacao Convencional', '2014-12-29 13:02:30'
//		'30', 'Baixa Conciliacao', '2014-12-29 13:02:30'
		
		if(HandlerIndicadores.idTipoOcorrenciaBaixa.size()==0)
		{
			int idTipoOcorrencia=0;
			String query = "select idTipoOcorrencia,descricao from tipoOcorrencia where" //$NON-NLS-1$
							+ " descricao= 'Baixa Normal' " //$NON-NLS-1$
							+ " or descricao= 'Baixa por Deposito Cedente' " //$NON-NLS-1$
							+ " or descricao= 'Baixa por Deposito Sacado' " //$NON-NLS-1$
							+ " or descricao= 'Baixa por Deposito Consultoria' " //$NON-NLS-1$
							+ " or descricao= 'Baixa por Recompra Paulista' " //$NON-NLS-1$
							+ " or descricao= 'Baixa por Recompra' " //$NON-NLS-1$
							+ " or descricao= 'Baixado Conforme Instrucoes da Agencia' " //$NON-NLS-1$
							+ " or descricao= 'Baixa Automatica' " //$NON-NLS-1$
							+ " or descricao= 'Liquidacao em Cartorio' " //$NON-NLS-1$
							+ " or descricao= 'Título Pago em Cheque' " //$NON-NLS-1$
							+ " or descricao= 'Baixado Automaticamente via Arquivo' " //$NON-NLS-1$
							+ " or descricao= 'Liquidacao por Compensacao Eletronica' " //$NON-NLS-1$
							+ " or descricao= 'Liquidacao por Compensacao Convencional' " //$NON-NLS-1$
							+ " or descricao= 'Baixa Conciliacao' " //$NON-NLS-1$
							+ ""; //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					idTipoOcorrencia = rs.getInt("idTipoOcorrencia"); //$NON-NLS-1$
					String descricao = rs.getString("descricao"); //$NON-NLS-1$
					if(idTipoOcorrencia!=0)
					{
						HandlerIndicadores.idTipoOcorrenciaBaixa.add(idTipoOcorrencia);
						System.out.println(idTipoOcorrencia + " " + descricao); //$NON-NLS-1$
					}

				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}	
		}
	}	

	public static int setIdTipoIndicador(String descricao)
	{
		int idTipoIndicador=0;
		String query = "select idTipoIndicador from tipoIndicador where descricao=" //$NON-NLS-1$
						+ " " + "'"	+ descricao + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
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
				idTipoIndicador = rs.getInt("idTipoIndicador"); //$NON-NLS-1$
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}	
		return idTipoIndicador;
	}
	
	public static int getIdTipoTitulo(int idTipoIndicadorVolumeTipoTitulo)
	{
		int idTipoIndicador=0;
		String query = "select descricao from tipoIndicador where " //$NON-NLS-1$
						+ " idTipoIndicador=" + idTipoIndicadorVolumeTipoTitulo;//$NON-NLS-1$
		
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		String stringTipoTitulo=""; //$NON-NLS-1$
		try
		{
			while (rs.next())
			{
				stringTipoTitulo = rs.getString("descricao"); //$NON-NLS-1$
				stringTipoTitulo = stringTipoTitulo.replace("Volume ", ""); //$NON-NLS-1$ //$NON-NLS-2$
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}	
		
		int idTipoTitulo=0;
		if(stringTipoTitulo.length() > 0 )
		{
			String query2 = "select idTipoTitulo from tipoTitulo where " //$NON-NLS-1$
					+ " descricao=" + "'" + stringTipoTitulo + "'";//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				
			Statement stmt2 = null;
			try
			{
				stmt2 = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			ResultSet rs2 = null;
			try
			{
				rs2 = stmt2.executeQuery(query2);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			try
			{
				while (rs2.next())
				{
					idTipoTitulo = rs2.getInt("idTipoTitulo"); //$NON-NLS-1$
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}				
		}		
		return idTipoTitulo;
	}
	public static void setIdTipoIndicadoresPrazos()
	{
//		Prazo Medio Fiscal Fundo 
//		Prazo Medio Fiscal Fundo DI
//		Prazo Medio Fiscal NTN-B
//		Prazo Medio Fiscal LFT
//		Prazo Medio Fiscal Compromissada
//		Prazo Medio Fiscal Tesouraria
//		Prazo Medio Fiscal Outros Ativos
		
//		Prazo Medio de Atraso 
//		Maior prazo em dias corridos 
//		Menor prazo em dias corridos 
//		Prazo Medio em dias corridos 
//		Prazo Desvio Padrão
//		double prazoMedioDeAtraso = 0.0;
//		double maiorPrazoDiasCorridos = 0.0;
//		double menorPrazoDiasCorridos = 0.0;
//		double prazoMedioDiasCorridos = 0.0;
//		double prazoDesvioPadrao = 0.0;		
		
		HandlerIndicadores.idTipoPrazos.add(HandlerIndicadores.setIdTipoIndicador("Prazo Medio de Atraso")); //$NON-NLS-1$
		HandlerIndicadores.idTipoPrazos.add(HandlerIndicadores.setIdTipoIndicador("Maior prazo em dias corridos")); //$NON-NLS-1$
		HandlerIndicadores.idTipoPrazos.add(HandlerIndicadores.setIdTipoIndicador("Menor prazo em dias corridos")); //$NON-NLS-1$
		HandlerIndicadores.idTipoPrazos.add(HandlerIndicadores.setIdTipoIndicador("Prazo Medio em dias corridos")); //$NON-NLS-1$
		HandlerIndicadores.idTipoPrazos.add(HandlerIndicadores.setIdTipoIndicador("Prazo Desvio Padrão")); //$NON-NLS-1$
	}	
	
	public static void setIdTipoIndicadoresTaxas()
	{
//		Taxa Media ao ano 
//		Taxa Media sobre CDI 
//		Menor Taxa de Cessao ao ano 
//		Menor Taxa  sobre CDI 
//		Maior Taxa de Cessao ao ano 
//		Taxa Desvio Padrão
//		Taxa Media
//		Taxa Mediana
		HandlerIndicadores.idTipoTaxas.add(HandlerIndicadores.setIdTipoIndicador("Taxa Media ao ano")); //$NON-NLS-1$
		HandlerIndicadores.idTipoTaxas.add(HandlerIndicadores.setIdTipoIndicador("Taxa Media sobre CDI")); //$NON-NLS-1$
		HandlerIndicadores.idTipoTaxas.add(HandlerIndicadores.setIdTipoIndicador("Menor Taxa de Cessao ao ano")); //$NON-NLS-1$
		HandlerIndicadores.idTipoTaxas.add(HandlerIndicadores.setIdTipoIndicador("Menor Taxa  sobre CDI")); //$NON-NLS-1$
		HandlerIndicadores.idTipoTaxas.add(HandlerIndicadores.setIdTipoIndicador("Maior Taxa de Cessao ao ano")); //$NON-NLS-1$
		HandlerIndicadores.idTipoTaxas.add(HandlerIndicadores.setIdTipoIndicador("Taxa Desvio Padrão")); //$NON-NLS-1$
		HandlerIndicadores.idTipoTaxas.add(HandlerIndicadores.setIdTipoIndicador("Taxa Media")); //$NON-NLS-1$
		HandlerIndicadores.idTipoTaxas.add(HandlerIndicadores.setIdTipoIndicador("Taxa Mediana")); //$NON-NLS-1$
	}	
	public static void setIdTipoIndicadoresCVNP()
	{
//		Creditos Vencidos Nao Pagos  Ate 15 dias
//		Creditos Vencidos Nao Pagos De 16 a 30 dias
//		Creditos Vencidos Nao Pagos De 31 a 45 dias
//		Creditos Vencidos Nao Pagos De 46 a 60 dias
//		Creditos Vencidos Nao Pagos De 61 a 90 dias
//		Creditos Vencidos Nao Pagos Acima de 90 dias
//		Creditos Vencidos Nao Pagos
		HandlerIndicadores.idTipoCreditosVencidosNaoPagos.add(HandlerIndicadores.setIdTipoIndicador("Creditos Vencidos Nao Pagos  Ate 15 dias")); //$NON-NLS-1$
		HandlerIndicadores.idTipoCreditosVencidosNaoPagos.add(HandlerIndicadores.setIdTipoIndicador("Creditos Vencidos Nao Pagos De 16 a 30 dias")); //$NON-NLS-1$
		HandlerIndicadores.idTipoCreditosVencidosNaoPagos.add(HandlerIndicadores.setIdTipoIndicador("Creditos Vencidos Nao Pagos De 31 a 45 dias")); //$NON-NLS-1$
		HandlerIndicadores.idTipoCreditosVencidosNaoPagos.add(HandlerIndicadores.setIdTipoIndicador("Creditos Vencidos Nao Pagos De 46 a 60 dias")); //$NON-NLS-1$
		HandlerIndicadores.idTipoCreditosVencidosNaoPagos.add(HandlerIndicadores.setIdTipoIndicador("Creditos Vencidos Nao Pagos De 61 a 90 dias")); //$NON-NLS-1$
		HandlerIndicadores.idTipoCreditosVencidosNaoPagos.add(HandlerIndicadores.setIdTipoIndicador("Creditos Vencidos Nao Pagos Acima de 90 dias")); //$NON-NLS-1$
		HandlerIndicadores.idTipoCreditosVencidosNaoPagos.add(HandlerIndicadores.setIdTipoIndicador("Creditos Vencidos Nao Pagos")); //$NON-NLS-1$
	}
	public static void setIdTipoIndicadoresPDD()
	{
//		Provisao para Devedores Duvidosos Ate 15 dias
//		Provisao para Devedores Duvidosos De 16 a 30 dias
//		Provisao para Devedores Duvidosos De 31 a 45 dias
//		Provisao para Devedores Duvidosos De 46 a 60 dias
//		Provisao para Devedores Duvidosos De 61 a 90 dias
//		Provisao para Devedores Duvidosos Acima de 90 dias
//		Provisao para Devedores Duvidosos
		HandlerIndicadores.idTipoProvisaoParaDevedoresDuvidosos.add(HandlerIndicadores.setIdTipoIndicador("Provisao para Devedores Duvidosos  Ate 15 dias")); //$NON-NLS-1$
		HandlerIndicadores.idTipoProvisaoParaDevedoresDuvidosos.add(HandlerIndicadores.setIdTipoIndicador("Provisao para Devedores Duvidosos De 16 a 30 dias")); //$NON-NLS-1$
		HandlerIndicadores.idTipoProvisaoParaDevedoresDuvidosos.add(HandlerIndicadores.setIdTipoIndicador("Provisao para Devedores Duvidosos De 31 a 45 dias")); //$NON-NLS-1$
		HandlerIndicadores.idTipoProvisaoParaDevedoresDuvidosos.add(HandlerIndicadores.setIdTipoIndicador("Provisao para Devedores Duvidosos De 46 a 60 dias")); //$NON-NLS-1$
		HandlerIndicadores.idTipoProvisaoParaDevedoresDuvidosos.add(HandlerIndicadores.setIdTipoIndicador("Provisao para Devedores Duvidosos De 61 a 90 dias")); //$NON-NLS-1$
		HandlerIndicadores.idTipoProvisaoParaDevedoresDuvidosos.add(HandlerIndicadores.setIdTipoIndicador("Provisao para Devedores Duvidosos Acima de 90 dias")); //$NON-NLS-1$
		HandlerIndicadores.idTipoProvisaoParaDevedoresDuvidosos.add(HandlerIndicadores.setIdTipoIndicador("Provisao para Devedores Duvidosos")); //$NON-NLS-1$
	}
	public static void setIdTipoIndicadoresVolumeTipoTitulo()
	{
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Cheque")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Duplicata")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Duplicata de Servico")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Contrato")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Nota Promissoria")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Outros")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Fatura de Cartao de Credito")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume CCB")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume CPR")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume CRI")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Contrato Fisico")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Nota Promissoria Fisica")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Duplicata de Servico Fisico")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Duplicata de Transporte Digital - CTE")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Duplicata de Transporte Fisica - CTE")); //$NON-NLS-1$
		HandlerIndicadores.idTipoVolumeTipoTitulo.add(HandlerIndicadores.setIdTipoIndicador("Volume Confissao de Divida")); //$NON-NLS-1$
	}	
	public static void setIdTipoIndicadores()
	{
		HandlerIndicadores.idTipoDireitosCreditorios = HandlerIndicadores.setIdTipoIndicador("Direitos Creditorios"); //$NON-NLS-1$
		HandlerIndicadores.setIdTipoIndicadoresCVNP();
		HandlerIndicadores.setIdTipoIndicadoresPDD();
		HandlerIndicadores.setIdTipoIndicadoresVolumeTipoTitulo();
		HandlerIndicadores.setIdTipoIndicadoresTaxas();
		HandlerIndicadores.setIdTipoIndicadoresPrazos();
		/**
		 * Auxiliar values
		 */
		HandlerIndicadores.setidTipoOcorrenciaBaixa();
		VolumePorTipoTitulo.setTiposDeTitulos(HandlerIndicadores.idTipoVolumeTipoTitulo, conn);
	}
	
	@SuppressWarnings("resource")
	public static void storeDireitosCreditorios(int idFundo, double valor, Date dataEstoque, Connection conn)
	{		
		String query = "select idIndicador from Indicador where " //$NON-NLS-1$
						+ " idTipoIndicador=" + HandlerIndicadores.idTipoDireitosCreditorios //$NON-NLS-1$
						+ " and "  //$NON-NLS-1$
						+ " idFundo=" + idFundo //$NON-NLS-1$
						+ " and " //$NON-NLS-1$
						+ " dataEstoque=" + "'" + sdfd.format(dataEstoque)+ "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		int idIndicador=0;
		try
		{
			while (rs.next())
			{
				idIndicador = rs.getInt("idIndicador"); //$NON-NLS-1$
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		try
		{
			stmt.close();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		if(idIndicador==0)
		{
			stmt = null;
			String sql = "INSERT INTO `mvcapitaldc`.`indicador` (`idTipoIndicador`,`idFundo`,`Valor`,`DataEstoque`)" //$NON-NLS-1$
					+ " VALUES (" //$NON-NLS-1$
					+ HandlerIndicadores.idTipoDireitosCreditorios
					+ "," + idFundo //$NON-NLS-1$
					+ "," + valor //$NON-NLS-1$
					+ "," + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ ")"; //$NON-NLS-1$
//			System.out.println(sql);
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			try
			{
				stmt.executeUpdate(sql);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try
			{
				stmt.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			stmt = null;
			String sql = "UPDATE `mvcapitaldc`.`indicador`" //$NON-NLS-1$
					+ " SET `Valor` = " + valor //$NON-NLS-1$
					+ "  WHERE " //$NON-NLS-1$
					+ "`idIndicador` = " + idIndicador; //$NON-NLS-1$
//			System.out.println(sql);
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			try
			{
				stmt.executeUpdate(sql);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try
			{
				stmt.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<Integer> titulosVencidos(ArrayList<Integer> idTitulos, Date dataEstoque, int nDaysBefore)
	{
		ArrayList<Integer> idTitulosVencidos = new ArrayList<Integer>();
		Date dataBase = WorkingDays.beforeWeekDay(dataEstoque, nDaysBefore);
		for(int idTitulo:idTitulos)
		{
			String query = "select dataVencimento from Titulo where " //$NON-NLS-1$
					+ " idTitulo = " + idTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					Date dataVencimento = rs.getDate("dataVencimento"); //$NON-NLS-1$
	//				System.out.println(idTitulo);
					if(dataVencimento.compareTo(dataBase)<0)
					{
						idTitulosVencidos.add(idTitulo);
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}			
		}
		return idTitulosVencidos;
	}
	
	public static ArrayList<Integer> titulosVencidos(ArrayList<Integer> idTitulos, Date dataEstoque, int nDaysBeforeInitial, int nDaysBeforeFinal)
	{
		ArrayList<Integer> idTitulosVencidos = new ArrayList<Integer>();
		Date dataBaseInitial = WorkingDays.beforeWeekDay(dataEstoque, nDaysBeforeInitial);
		Date dataBaseFinal = WorkingDays.beforeWeekDay(dataEstoque, nDaysBeforeFinal);
		for(int idTitulo:idTitulos)
		{
			String query = "select dataVencimento from Titulo where " //$NON-NLS-1$
					+ " idTitulo = " + idTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					Date dataVencimento = rs.getDate("dataVencimento"); //$NON-NLS-1$
	//				System.out.println(idTitulo);
					if(dataVencimento.compareTo(dataBaseInitial)>0 && dataVencimento.compareTo(dataBaseFinal)<0 )
					{
						idTitulosVencidos.add(idTitulo);
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}			
		}
		return idTitulosVencidos;
	}
	public static double menorTaxaSobreCDI(ArrayList<Integer> idTitulos, Date dataEstoque)
	{
		double menorTaxa = 1000000000000.0;
		for(int idTitulo:idTitulos)
		{
			String query = "select valor from TaxaSobreCDI where " //$NON-NLS-1$
					+ " dataEstoque=" + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ " and idTitulo = " + idTitulo;  //$NON-NLS-1$
//			System.out.println(query);
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					double taxaSobreCDI = rs.getDouble("valor"); //$NON-NLS-1$
					if(taxaSobreCDI<menorTaxa)
					{
						menorTaxa = taxaSobreCDI;
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
		return menorTaxa;
	}	
	public static double menorTaxaDeCessaoAoAno(ArrayList<Integer> idTitulos)
	{
		double menorTaxa = 1000000000000.0;
		for(int idTitulo:idTitulos)
		{
			String query = "select taxaAoAno from Titulo where " //$NON-NLS-1$
					+ " idTitulo = " + idTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					double taxaAoAno = rs.getDouble("taxaAoAno"); //$NON-NLS-1$
					if(taxaAoAno<menorTaxa)
					{
						menorTaxa = taxaAoAno;
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
		return menorTaxa;
	}	
	
	public static double maiorTaxaDeCessaoAoAno(ArrayList<Integer> idTitulos)
	{
		double maiorTaxa = 0.0;
		for(int idTitulo:idTitulos)
		{
			String query = "select taxaAoAno from Titulo where " //$NON-NLS-1$
					+ " idTitulo = " + idTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					double taxaAoAno = rs.getDouble("taxaAoAno"); //$NON-NLS-1$
					if(taxaAoAno>maiorTaxa)
					{
						maiorTaxa = taxaAoAno;
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
		return maiorTaxa;
	}	

	public static double prazoDesvioPadrao(ArrayList<Integer> idTitulos, double mediaPrazo)
	{
		double desvioPadrao = 0.0;
		for(int idTitulo:idTitulos)
		{
			String query = "select prazoCorrido from Titulo where " //$NON-NLS-1$
					+ " idTitulo = " + idTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					double taxaAoAno = rs.getDouble("prazoCorrido"); //$NON-NLS-1$
					desvioPadrao = desvioPadrao + (taxaAoAno-mediaPrazo)*(taxaAoAno-mediaPrazo);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
		desvioPadrao = Math.sqrt(desvioPadrao) / idTitulos.size();
		return desvioPadrao;
	}	
	
	public static double taxaDesvioPadrao(ArrayList<Integer> idTitulos, double mediaTaxa)
	{
		double desvioPadrao = 0.0;
		for(int idTitulo:idTitulos)
		{
			String query = "select taxaAoAno from Titulo where " //$NON-NLS-1$
					+ " idTitulo = " + idTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					double taxaAoAno = rs.getDouble("taxaAoAno"); //$NON-NLS-1$
					desvioPadrao = desvioPadrao + (taxaAoAno-mediaTaxa)*(taxaAoAno-mediaTaxa);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
		desvioPadrao = Math.sqrt(desvioPadrao) / idTitulos.size();
		return desvioPadrao;
	}
	
	public static double taxaMediaAoAno(ArrayList<Integer> idTitulos)
	{
		double taxaMedia = 0.0;
		for(int idTitulo:idTitulos)
		{
			String query = "select taxaAoAno from Titulo where " //$NON-NLS-1$
					+ " idTitulo = " + idTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					double taxaAoAno = rs.getDouble("taxaAoAno"); //$NON-NLS-1$
					taxaMedia = taxaMedia + taxaAoAno;
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
		taxaMedia = taxaMedia / idTitulos.size();
		return taxaMedia;
	}
	
	public static double taxaMediaSobreCDI(ArrayList<Integer> idTitulos, Date dataEstoque)
	{
		double taxaMedia = 0.0;
		for(int idTitulo:idTitulos)
		{
			String query = "select valor from TaxaSobreCDI where " //$NON-NLS-1$
					+ " dataEstoque = " + "'" + sdfd.format(dataEstoque) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ " and idTitulo = " + idTitulo;  //$NON-NLS-1$			
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					double valor = rs.getDouble("valor"); //$NON-NLS-1$
					taxaMedia = taxaMedia + valor;
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
		taxaMedia = taxaMedia / idTitulos.size();
		return taxaMedia;
	}
	
	public static ArrayList<Integer> titulosNaoPagos(ArrayList<Integer> idTitulos, Date dataEstoque)
	{
		ArrayList<Integer> idTitulosNaoPagos = new ArrayList<Integer>();
		String queryComplement=" and ("; //$NON-NLS-1$
		//select * from ocorrencia where idTitulo=144 and (idTipoOcorrencia=1 or idTipoOcorrencia=2);
		int iTipoOcorrencia = 0;
		for(int idTipoOcorrencia:HandlerIndicadores.idTipoOcorrenciaBaixa)
		{
			if(iTipoOcorrencia==0)
			{
				queryComplement=queryComplement + " idTipoOcorrencia=" + "'" + idTipoOcorrencia + "'" + " "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
			else
			{
				queryComplement=queryComplement + " or idTipoOcorrencia=" + "'" + idTipoOcorrencia + "'" + " "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
			iTipoOcorrencia++;
		}
		
		queryComplement=queryComplement + ")"; //$NON-NLS-1$
		for(int idTitulo:idTitulos)
		{
			String query = "select idTitulo from ocorrencia where " //$NON-NLS-1$
					+ " idTitulo = " + idTitulo //$NON-NLS-1$
					+ queryComplement;
//			System.out.println(query);
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			ResultSet rs = null;
			try
			{
				rs = stmt.executeQuery(query);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
	
			int tempIdTitulo=0;
			try
			{
				while (rs.next())
				{
					tempIdTitulo = rs.getInt("idTitulo"); //$NON-NLS-1$
	//				System.out.println(idTitulo);
					idTitulosNaoPagos.add(idTitulo);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}			
			if (tempIdTitulo==0)
			{
				idTitulosNaoPagos.add(idTitulo);
			}
		}
		return idTitulosNaoPagos;
	}
	
	public static boolean checkLiquidado(int idTitulo, Date dataEstoque)
	{
		boolean liquidado=false;
		String query = "select dataVencimento from Titulo where " //$NON-NLS-1$
				+ " idTitulo = " + idTitulo;  //$NON-NLS-1$
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
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
				Date dataVencimento = rs.getDate("dataVencimento"); //$NON-NLS-1$
//				System.out.println(idTitulo);
//				idTitulosNaoPagos.add(idTitulo);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}	
		return liquidado;
	}

	public static double valorPDD(ArrayList<Integer> idTitulos, Date dataEstoque)
	{
		ArrayList<Double> valoresPresentes = new ArrayList<Double>();
		for(int idTitulo:idTitulos)
		{
			String query = "select valor from PDD where " //$NON-NLS-1$
					+ " dataEstoque = " + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ " and idTitulo = " + idTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					double valor = rs.getDouble("valor"); //$NON-NLS-1$
	//				System.out.println(idTitulo);
					valoresPresentes.add(valor);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		double sum=0.0;
		for(double valor:valoresPresentes)
		{
			sum = sum + valor;
		}
		return sum;
	}

	public static double valorPresente(ArrayList<Integer> idTitulos, Date dataEstoque)
	{
		ArrayList<Double> valoresPresentes = new ArrayList<Double>();
		
		double sum=0.0;
		
		if(idTitulos.size()>0)
		{
			String query = "select sum(valor) as valorTotal from valorPresente where " //$NON-NLS-1$
						+ " dataEstoque = " + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ " and ("; //$NON-NLS-1$
			int iTitulo=0;
			for(int idTitulo:idTitulos)
			{
				
				if(iTitulo!=0)
				{
					query = query + " or idTitulo = " + idTitulo;  //$NON-NLS-1$
				}
				else
				{
					query = query + " idTitulo = " + idTitulo;  //$NON-NLS-1$
				}
				iTitulo++;
			}		
			query = query + ")"; //$NON-NLS-1$
			
			System.out.println(query);
			
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					sum = rs.getDouble("valorTotal"); //$NON-NLS-1$
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

//		
//		for(int idTitulo:idTitulos)
//		{
//			String query = "select valor from valorPresente where " //$NON-NLS-1$
//					+ " dataEstoque = " + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
//					+ " and idTitulo = " + idTitulo;  //$NON-NLS-1$
//			Statement stmt = null;
//			try
//			{
//				stmt = (Statement) conn.createStatement();
//			} catch (SQLException e)
//			{
//				e.printStackTrace();
//			}
//			ResultSet rs = null;
//			try
//			{
//				rs = stmt.executeQuery(query);
//			} catch (SQLException e)
//			{
//				e.printStackTrace();
//			}
//	
//			try
//			{
//				while (rs.next())
//				{
//					double valor = rs.getDouble("valor"); //$NON-NLS-1$
//	//				System.out.println(idTitulo);
//					valoresPresentes.add(valor);
//				}	
//			} catch (SQLException e)
//			{
//				e.printStackTrace();
//			}
//		}
////		double sum=0.0;
//		for(double valor:valoresPresentes)
//		{
//			sum = sum + valor;
//		}
//		valoresPresentes=null;
		return sum;
	}

	public static ArrayList<Integer> titulosDoTipo(ArrayList<Integer> idTitulos, int idTipoTitulo)
	{
		ArrayList<Integer> idTitulosTipoTitulo = new ArrayList<Integer>();
		for(int idTitulo:idTitulos)
		{
			String query = "select idTitulo from Titulo where " //$NON-NLS-1$
					+ " idTipoTitulo = " + idTipoTitulo //$NON-NLS-1$
					+ " and idTitulo = " + idTitulo; //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					int idTituloPorTipo = rs.getInt("idTitulo"); //$NON-NLS-1$
					if(idTituloPorTipo!=0)
					{
						idTitulosTipoTitulo.add(idTituloPorTipo);
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println(idTitulosTipoTitulo.size() + " do tipo " + idTipoTitulo); //$NON-NLS-1$
		return idTitulosTipoTitulo;
	}
	public static double valorPresente(ArrayList<Integer> idTitulos, int idTipoTitulo, Date dataEstoque)
	{
		ArrayList<Double> valoresPresentes = new ArrayList<Double>();
		for(int idTitulo:idTitulos)
		{
			String query = "select valor from valorPresente where " //$NON-NLS-1$
					+ " dataEstoque = " + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ " and idTitulo = " + idTitulo  //$NON-NLS-1$
					+ " and idTipoTitulo = " + idTipoTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					double valor = rs.getDouble("valor"); //$NON-NLS-1$
	//				System.out.println(idTitulo);
					valoresPresentes.add(valor);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		double sum=0.0;
		for(double valor:valoresPresentes)
		{
			sum = sum + valor;
		}
		return sum;
	}
	
	public static void storeGrupoIndicadores(int idFundo, Date dataEstoque, ArrayList<Double> valores, ArrayList<Integer> idTipoIndicadores, Connection conn)
	{
		for(int i=0;i<valores.size();i++)
		{
			HandlerIndicadores.storeIndicador(idFundo, idTipoIndicadores.get(i), valores.get(i), dataEstoque, conn);
		}
	}
	
	public static void storeIndicador(int idFundo, int idTipoIndicador, double valor, Date dataEstoque, Connection conn)
	{		
		String query = "select idIndicador from Indicador where " //$NON-NLS-1$
						+ " idTipoIndicador=" + idTipoIndicador //$NON-NLS-1$
						+ " and "  //$NON-NLS-1$
						+ " idFundo=" + idFundo //$NON-NLS-1$
						+ " and " //$NON-NLS-1$
						+ " dataEstoque=" + "'" + sdfd.format(dataEstoque)+ "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		int idIndicador=0;
		try
		{
			while (rs.next())
			{
				idIndicador = rs.getInt("idIndicador"); //$NON-NLS-1$
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		try
		{
			stmt.close();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		if(idIndicador==0)
		{
			stmt = null;
			String sql = "INSERT INTO `mvcapitaldc`.`indicador` (`idTipoIndicador`,`idFundo`,`Valor`,`DataEstoque`)" //$NON-NLS-1$
					+ " VALUES (" //$NON-NLS-1$
					+ idTipoIndicador
					+ "," + idFundo //$NON-NLS-1$
					+ "," + valor //$NON-NLS-1$
					+ "," + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ ")"; //$NON-NLS-1$
//			System.out.println(sql);
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			try
			{
				stmt.executeUpdate(sql);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try
			{
				stmt.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			stmt = null;
			String sql = "UPDATE `mvcapitaldc`.`indicador`" //$NON-NLS-1$
					+ " SET `Valor` = " + valor //$NON-NLS-1$
					+ "  WHERE " //$NON-NLS-1$
					+ "`idIndicador` = " + idIndicador; //$NON-NLS-1$
//			System.out.println(sql);
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			try
			{
				stmt.executeUpdate(sql);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try
			{
				stmt.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<Integer> getIdTipoTaxas()
	{
		return idTipoTaxas;
	}

	public static void setIdTipoTaxas(ArrayList<Integer> idTipoTaxas)
	{
		HandlerIndicadores.idTipoTaxas = idTipoTaxas;
	}

	public static double prazoMedioAtraso(ArrayList<Integer> idTitulos, Date dataEstoque)
	{
		double prazoMedio = 0.0;
		int countTitulosPrazo = 0;
		for(int idTitulo:idTitulos)
		{
			String query = "select idPrazo, valorCorrido from prazo where " //$NON-NLS-1$
						+ " dataEstoque=" + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ " AND idTitulo = " + idTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					int idPrazo = 0;
					idPrazo = rs.getInt("idPrazo"); //$NON-NLS-1$
					if(idPrazo!=0)
					{
						int prazoCorrido = rs.getInt("ValorCorrido"); //$NON-NLS-1$
						if(prazoCorrido<0)
						{
							prazoMedio = prazoMedio + prazoCorrido;
							countTitulosPrazo++;
						}
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
		if(prazoMedio!=0)
		{
			prazoMedio = prazoMedio / countTitulosPrazo;
		}
		return prazoMedio*(-1);
	}	
	
	public static double prazoMedioDiasCorridos(ArrayList<Integer> idTitulos, Date dataEstoque)
	{
		double prazoMedio = 0.0;
		int countTitulosPrazo = 0;
		for(int idTitulo:idTitulos)
		{
			String query = "select idPrazo, valorCorrido from prazo where " //$NON-NLS-1$
						+ " dataEstoque=" + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ " AND idTitulo = " + idTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					int idPrazo = 0;
					idPrazo = rs.getInt("idPrazo"); //$NON-NLS-1$
					if(idPrazo!=0)
					{
						int prazoCorrido = rs.getInt("ValorCorrido"); //$NON-NLS-1$
						if(prazoCorrido>0)
						{
							prazoMedio = prazoMedio + prazoCorrido;
							countTitulosPrazo++;
						}
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
		if(countTitulosPrazo!=0)
		{
			prazoMedio = prazoMedio / countTitulosPrazo;
		}
		return prazoMedio;
	}
	
	public static double prazoMedioDiasUteis(ArrayList<Integer> idTitulos, Date dataEstoque)
	{
		double prazoMedio = 0.0;
		int countTitulosPrazo = 0;
		for(int idTitulo:idTitulos)
		{
			String query = "select idPrazo, valorUtil from prazo where " //$NON-NLS-1$
						+ " dataEstoque=" + "'" + sdfd.format(dataEstoque) + "'" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ " AND idTitulo = " + idTitulo;  //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					int idPrazo = 0;
					idPrazo = rs.getInt("idPrazo"); //$NON-NLS-1$
					if(idPrazo!=0)
					{
						int prazoUtil = rs.getInt("ValorUtil"); //$NON-NLS-1$
						prazoMedio = prazoMedio + prazoUtil;
						countTitulosPrazo++;
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
		prazoMedio = prazoMedio / countTitulosPrazo;
		return prazoMedio;
	}		

	public static double volumeCedente(int idEntidadeCedente, int idFundo, Date dataEstoque, Connection conn)
	{
		double volume=0.0;
		
		ArrayList<Integer> idTitulosCedente = titulosCedente(idEntidadeCedente, idFundo, dataEstoque, conn);
		volume = valorPresente(idTitulosCedente, dataEstoque);		
		return volume;
	}

	public static double volumeSacado(int idEntidadeSacado, int idFundo, Date dataEstoque, Connection conn)
	{
		double volume=0.0;
		
		ArrayList<Integer> idTitulosSacado = titulosSacado(idEntidadeSacado, idFundo, dataEstoque, conn);
		volume = valorPresente(idTitulosSacado, dataEstoque);		
		return volume;
	}
	
	
	public static ArrayList<Integer> titulosSacado(int idEntidadeSacado, int idFundo, Date dataEstoque, Connection conn)
	{
		ArrayList<Integer> idTitulos = new ArrayList<Integer>();
		
		String query = "select idTitulo from Estoque where" //$NON-NLS-1$
				+ " idFundo=" + idFundo //$NON-NLS-1$
				+ " AND dataEstoque=" + "'" + sdfd.format(dataEstoque) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				;
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
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
				int idTitulo=0;
				idTitulo = rs.getInt("idTitulo"); //$NON-NLS-1$
				if(idTitulo!=0)
				{
					Titulo titulo = new Titulo(idTitulo, conn);
					if(titulo.getSacado().getIdEntidade() == idEntidadeSacado)
					{
						idTitulos.add(idTitulo);
					}
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return idTitulos;
	}
	
	public static ArrayList<Integer> titulosCedente(int idEntidadeCedente, int idFundo, Date dataEstoque, Connection conn)
	{
		ArrayList<Integer> idTitulos = new ArrayList<Integer>();
		
		String query = "select idTitulo from Estoque where" //$NON-NLS-1$
				+ " idFundo=" + idFundo //$NON-NLS-1$
				+ " AND dataEstoque=" + "'" + sdfd.format(dataEstoque) + "'"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				;
		Statement stmt = null;
		try
		{
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
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
				int idTitulo=0;
				idTitulo = rs.getInt("idTitulo"); //$NON-NLS-1$
				if(idTitulo!=0)
				{
					Titulo titulo = new Titulo(idTitulo, conn);
					if(titulo.getCedente().getIdEntidade() == idEntidadeCedente)
					{
						idTitulos.add(idTitulo);
					}
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return idTitulos;
	}
	
	public static double maiorPrazoDiasCorridos(ArrayList<Integer> idTitulos, Date dataEstoque)
	{
		int maiorPrazo = 0;
		for(int idTitulo:idTitulos)
		{
			String query = "select prazoCorrido from Titulo where" //$NON-NLS-1$
						+ " idTitulo=" + idTitulo; //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					int prazoCorrido=0;
					prazoCorrido = rs.getInt("prazoCorrido"); //$NON-NLS-1$
					if(prazoCorrido!=0)
					{						
						if(prazoCorrido>maiorPrazo)
						{
							maiorPrazo = prazoCorrido;
						}
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
		return maiorPrazo;
	}	
	
	public static double menorPrazoDiasCorridos(ArrayList<Integer> idTitulos, Date dataEstoque)
	{
		int menorPrazo = 1000000;
		for(int idTitulo:idTitulos)
		{
			String query = "select prazoCorrido from Titulo where" //$NON-NLS-1$
						+ " idTitulo=" + idTitulo; //$NON-NLS-1$
			Statement stmt = null;
			try
			{
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
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
					int prazoCorrido = 0;
					prazoCorrido = rs.getInt("prazoCorrido"); //$NON-NLS-1$
					if(prazoCorrido!=0)
					{						
						if(prazoCorrido<menorPrazo)
						{
							menorPrazo = prazoCorrido;
						}
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
		return menorPrazo;
	}

	public static Connection getConn()
	{
		return conn;
	}

	public static void setConn(Connection conn)
	{
		HandlerIndicadores.conn = conn;
	}

	public static ArrayList<Date> getDatasEstoque()
	{
		return datasEstoque;
	}

	public static void setDatasEstoque(ArrayList<Date> datasEstoque)
	{
		HandlerIndicadores.datasEstoque = datasEstoque;
	}

	public static SimpleDateFormat getSdfd()
	{
		return sdfd;
	}

	public static void setSdfd(SimpleDateFormat sdfd)
	{
		HandlerIndicadores.sdfd = sdfd;
	}

	public static int getIdTipoDireitosCreditorios()
	{
		return idTipoDireitosCreditorios;
	}

	public static void setIdTipoDireitosCreditorios(int idTipoDireitosCreditorios)
	{
		HandlerIndicadores.idTipoDireitosCreditorios = idTipoDireitosCreditorios;
	}

	public static ArrayList<Integer> getIdTipoCreditosVencidosNaoPagos()
	{
		return idTipoCreditosVencidosNaoPagos;
	}

	public static void setIdTipoCreditosVencidosNaoPagos(
			ArrayList<Integer> idTipoCreditosVencidosNaoPagos)
	{
		HandlerIndicadores.idTipoCreditosVencidosNaoPagos = idTipoCreditosVencidosNaoPagos;
	}

	public static ArrayList<Integer> getIdTipoProvisaoParaDevedoresDuvidosos()
	{
		return idTipoProvisaoParaDevedoresDuvidosos;
	}

	public static void setIdTipoProvisaoParaDevedoresDuvidosos(
			ArrayList<Integer> idTipoProvisaoParaDevedoresDuvidosos)
	{
		HandlerIndicadores.idTipoProvisaoParaDevedoresDuvidosos = idTipoProvisaoParaDevedoresDuvidosos;
	}

	public static ArrayList<Integer> getIdTipoVolumeTipoTitulo()
	{
		return idTipoVolumeTipoTitulo;
	}

	public static void setIdTipoVolumeTipoTitulo(
			ArrayList<Integer> idTipoVolumeTipoTitulo)
	{
		HandlerIndicadores.idTipoVolumeTipoTitulo = idTipoVolumeTipoTitulo;
	}

	public static ArrayList<Integer> getIdTipoPrazos()
	{
		return idTipoPrazos;
	}

	public static void setIdTipoPrazos(ArrayList<Integer> idTipoPrazos)
	{
		HandlerIndicadores.idTipoPrazos = idTipoPrazos;
	}

	public static ArrayList<Integer> getIdTipoOcorrenciaBaixa()
	{
		return idTipoOcorrenciaBaixa;
	}

	public static void setIdTipoOcorrenciaBaixa(
			ArrayList<Integer> idTipoOcorrenciaBaixa)
	{
		HandlerIndicadores.idTipoOcorrenciaBaixa = idTipoOcorrenciaBaixa;
	}		
}
