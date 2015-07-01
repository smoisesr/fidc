package cnab.bradesco.conciliacao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteDetailConciliacaoBradescoToCSV 
{
	public static void writeCsvFile(String outputFileAbsolutePath, ArrayList<RegisterDetailConciliacaoBradesco> details)
	  {
	     try
	     {
	         FileWriter writer = new FileWriter(outputFileAbsolutePath);
	    
	         writer.append("banco;");
	         writer.append("lote;");
	         writer.append("registro;");
	         writer.append("numeroDeRegistroDeServico;");
	         writer.append("segmentoDeServico;");
	         writer.append("CNAB;");
	         writer.append("tipoDeInscricao;");
	         writer.append("numeroDeInscricao;");
	         writer.append("convenio;");
	         writer.append("codigoAgencia;");
	         writer.append("digitoVerificadorAgencia;");
	         writer.append("numeroConta;");
	         writer.append("digitoVerificadorConta;");
	         writer.append("digitoVerificadorAgenciaConta;");
	         writer.append("nomeDaEmpresa;");
	         writer.append("natureza;");
	         writer.append("CNAB2;");
	         writer.append("dataDoLancamento;");
	         writer.append("valorDoLancamento;");
	         writer.append("tipoDoLancamento;");
	         writer.append("categoriaDoLancamento;");
	         writer.append("codigoHistoricoDoLancamento;");
	         writer.append("historicoDoLancamento;");
	         writer.append("numeroDoDocumento\n");

	         for (RegisterDetailConciliacaoBradesco detail : details) 
	         {
	        	 writer.append(detail.getBanco().getValue()+";");
	        	 writer.append(detail.getLote().getValue()+";");
	        	 writer.append(detail.getRegistro().getValue()+";");
	        	 writer.append(detail.getNumeroDeRegistroDeServico().getValue()+";");
	        	 writer.append(detail.getSegmentoDeServico().getValue()+";");
	        	 writer.append(detail.getCNAB().getValue()+";");
	        	 writer.append(detail.getTipoDeInscricao().getValue()+";");
	        	 writer.append(detail.getNumeroDeInscricao().getValue()+";");
	        	 writer.append(detail.getConvenio().getValue()+";");
	        	 writer.append(detail.getCodigoAgencia().getValue()+";");
	        	 writer.append(detail.getDigitoVerificadorAgencia().getValue()+";");
	        	 writer.append(detail.getNumeroConta().getValue()+";");
	        	 writer.append(detail.getDigitoVerificadorConta().getValue()+";");
	        	 writer.append(detail.getDigitoVerificadorAgenciaConta().getValue()+";");
	        	 writer.append(detail.getNomeDaEmpresa().getValue()+";");
	        	 writer.append(detail.getNatureza().getValue()+";");
	        	 writer.append(detail.getCNAB2().getValue()+";");
	        	 writer.append(detail.getDataDoLancamento().getValue()+";");
	        	 writer.append(detail.getValorDoLancamento().getValue()+";");
	        	 writer.append(detail.getTipoDoLancamento().getValue()+";");
	        	 writer.append(detail.getCategoriaDoLancamento().getValue()+";");
	        	 writer.append(detail.getCodigoHistoricoDoLancamento().getValue()+";");
	        	 writer.append(detail.getHistoricoDoLancamento().getValue()+";");
	        	 writer.append(detail.getNumeroDoDocumento().getValue()+"\n");
	         }
	         writer.flush();
	         writer.close();
	     }
	     catch(IOException e)
	     {
	          e.printStackTrace();
	     } 
	   }
}
