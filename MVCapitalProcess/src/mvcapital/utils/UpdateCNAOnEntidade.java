package mvcapital.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UpdateCNAOnEntidade
{

	public UpdateCNAOnEntidade()
	{
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args)
	{
		String registro="13971519000169"; //$NON-NLS-1$
		readInfoplex(registro);
//		Document doc = connect();
	}
	
	private static Document connect() {
	    String url = "https://www.infoplex.com.br"; //$NON-NLS-1$
	    Document doc = null;
	    try {
	        doc = Jsoup.connect(url).get();
	    } catch (NullPointerException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (HttpStatusException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return doc;
	} 	
	
	public static ArrayList<String> readInfoplex(String registro)
	{
		ArrayList<String> lines = new ArrayList<String>();
		String url = "https://www.infoplex.com.br/perfil/" + registro; //$NON-NLS-1$
				
        Document doc;
        try {
        	
            doc = Jsoup.connect(url)
            		  .data("query", "Java") //$NON-NLS-1$ //$NON-NLS-2$
            		  .userAgent("Mozilla") //$NON-NLS-1$
            		  .cookie("auth", "token") //$NON-NLS-1$ //$NON-NLS-2$
            		  .timeout(3000)
            		  .post();
//                    .get();
            Element cnpj = doc.select("#cnpj").first(); //$NON-NLS-1$
            Element razaoSocial = doc.select("#razao-social").first(); //$NON-NLS-1$
            Element quantidadeEstabelecimentos=doc.select("#qtd-estab").first(); //$NON-NLS-1$
            Element dataAbertura = doc.select("#data-abertura").first(); //$NON-NLS-1$
            Element nomeFantasia = doc.select("#nome-fantasia").first(); //$NON-NLS-1$
            Element naturezaJuridica = doc.select("#nat-jur").first();             //$NON-NLS-1$
            Elements endereco = doc.select("#endereco").select("li"); //$NON-NLS-1$ //$NON-NLS-2$
            Elements telefones = doc.select("#telefones").select("li"); //$NON-NLS-1$ //$NON-NLS-2$
            Elements emails = doc.select("#emails").select("li"); //$NON-NLS-1$ //$NON-NLS-2$
            Element situacaoCadastral = doc.select("#sit-cad").first(); //$NON-NLS-1$
            Element motivoSituacaoCadastral = doc.select("#motivo-sit-cad").first(); //$NON-NLS-1$
            Element atividadeEconomicaPrincipal = doc.select("#cnae-primario").first(); //$NON-NLS-1$
            Elements atividadeEconomicaSecundaria = doc.select("#cnae-secundario").select("li"); //$NON-NLS-1$ //$NON-NLS-2$
            Element situacaoEspecial = doc.select("#sit-esp").first(); //$NON-NLS-1$
            Element enteFederativoResponsavel = doc.select("#efr").first(); //$NON-NLS-1$
            Elements inscricoesEstaduais = doc.select("#possui-ies").select("li"); //$NON-NLS-1$ //$NON-NLS-2$
            Elements inscricoesEstaduaisHabilidatas = doc.select("#ies-habilitadas").select("li"); //$NON-NLS-1$ //$NON-NLS-2$
            
            System.out.println("Razao Social: " + razaoSocial.text()); //$NON-NLS-1$
            System.out.println("CNPJ: " + cnpj.text()); //$NON-NLS-1$
            System.out.println("NroEstab: " + quantidadeEstabelecimentos.text()); //$NON-NLS-1$
            System.out.println("DataAbertura: " + dataAbertura.text()); //$NON-NLS-1$
            System.out.println("NomeFantasia: " + nomeFantasia.text()); //$NON-NLS-1$
            System.out.println("NaturezaJuridica: " + naturezaJuridica.text()); //$NON-NLS-1$

            if(endereco.size()>0)
            {
                System.out.print("Endereco: "); //$NON-NLS-1$
	            for(Element linhaEndereco:endereco)
	            {
	            	System.out.print(linhaEndereco.text().replace(";", "")+";"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	            }
	            System.out.println();
            }
            
            if(telefones.size()>0)
            {
            	System.out.print("Telefone: "); //$NON-NLS-1$
            	for(Element phone:telefones)
            	{
            		System.out.print(phone.text()+";"); //$NON-NLS-1$
            	}
            	System.out.println();
            }
            if(emails.size()>0)
            {
            	System.out.print("Email: "); //$NON-NLS-1$
            	for(Element email:emails)
            	{
            		System.out.print(email.text() + ";"); //$NON-NLS-1$
            	}
            	System.out.println();
            }
            System.out.println("SituacaoCadastral: " + situacaoCadastral.text()); //$NON-NLS-1$
            System.out.println("MotivoSituacaoCadastral: " + motivoSituacaoCadastral.text()); //$NON-NLS-1$
            System.out.println("AtividadeEconomicaPrincipal: " + atividadeEconomicaPrincipal.text()); //$NON-NLS-1$
            if(atividadeEconomicaSecundaria.size()>0)
            {
            	System.out.print("AtividadeEconomicaSecundaria: "); //$NON-NLS-1$
            	for(Element ativSec:atividadeEconomicaSecundaria)
            	{
            		System.out.print(ativSec.text() + ";"); //$NON-NLS-1$
            	}
            	System.out.println();
            }            
            System.out.println("SituacaoEspecial: " + situacaoEspecial.text()); //$NON-NLS-1$
            System.out.println("EnteFederativoResponsavel: " + enteFederativoResponsavel.text()); //$NON-NLS-1$
            if(inscricoesEstaduais.size()>0)
            {
            	System.out.print("InscricaoEstadual: "); //$NON-NLS-1$
            	for(Element inscEstad:inscricoesEstaduais)
            	{
            		System.out.print(inscEstad.text() + ";"); //$NON-NLS-1$
            	}
            	System.out.println();
            }
            if(inscricoesEstaduaisHabilidatas.size()>0)
            {
            	System.out.print("InscricaoEstadualHabilitada: "); //$NON-NLS-1$
            	for(Element inscEstadHab:inscricoesEstaduaisHabilidatas)
            	{
            		System.out.print(inscEstadHab.text() + ";"); //$NON-NLS-1$
            	}
            	System.out.println();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lines;
	}

}
