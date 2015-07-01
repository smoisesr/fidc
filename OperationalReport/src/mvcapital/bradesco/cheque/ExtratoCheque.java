package mvcapital.bradesco.cheque;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtratoCheque 
{
	private static SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
	public ExtratoCheque() 
	{
		this.readExtratoCheque();
	}
	
	public static void main(String[] args)
	{
		ExtratoCheque ec = new ExtratoCheque();
	}

	public void readExtratoCheque()
	{
		BufferedReader reader = null;
		System.out.println("Reading conf/automata.conf file");
		System.out.println("------------------");
		try 
		{
			reader = new BufferedReader(new FileReader("C:\\DownloadsPortal\\Reports\\Bradesco_28112014_171050.CSV"));
		} catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		String line = null;
		try 
		{
			while ((line = reader.readLine()) != null) 
			{
				line = line.toLowerCase();
				if(!line.isEmpty())
				{					
					String[] fields = line.split(";");
//					System.out.println(line);					
					if(line.contains("/"))
					{
//						Data da Compensação ou Devolução;
//						Histórico;
//						Docto.;
//						Bco.;
//						Ag;
//						Conta;
//						Crédito (R$);
//						Débito (R$)
//						13/11/2014;Cheque Depositado;21;33;3929;9913057605;48.500,00;						
						Date data=null;
						try {
							data = sdfr.parse(fields[0]);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						String ocorrencia = fields[1];
						String numeroCheque = fields[2];
						String codigoBanco = fields[3];
						String numeroAgencia = fields[4];
						String numeroConta = fields[5];
						double credito = 0.0;
						double debito = 0.0;
						if(fields[6].length()>0)
						{
							credito = Double.parseDouble(fields[6].replace(".", "").replace(",", "."));
						}
						if(fields.length>7)
						{
//							System.out.println(fields[7]);
							debito = Double.parseDouble(fields[7].replace(".", "").replace(",", "."))*-1;
						}
						if(ocorrencia.contains("devolvido"))
						{
							ocorrencia = "Devolvido";
						}
						else
						{
							ocorrencia = "Depositado";
						}
						
						
						String stringCredito=Double.toString(credito);
						String stringDebito=Double.toString(debito);
						if(Double.compare(credito, 0.0)==0)
						{
							stringCredito="";
						}
						if(Double.compare(debito, 0.0)==0)
						{
							stringDebito="";
						}
						System.out.println(sdfr.format(data) 
											+ ";" + ocorrencia 
											+ ";" + numeroCheque 
											+ ";" + codigoBanco 
											+ ";" + numeroAgencia
											+ ";" + numeroConta											
											+ ";" + stringCredito + stringDebito
											);
					}
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
