package report;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fundo.FundoDeInvestimento;
import utils.CleanString;
import utils.TextToLines;

public class Report 
{
	protected ArrayList<Block> blocks = new ArrayList<Block>();
	protected ArrayList<String> lines = new ArrayList<String>();
	protected FundoDeInvestimento fundo = null;
	protected Date dataDaPosicao=null;
	protected Date dataDeEmissao=null;
	protected String cliente="";
	protected String codigoDoCliente="";
	protected String reportCSV="";
	public static SimpleDateFormat formatterDateShort = new SimpleDateFormat("dd/MM/yy");
	public static SimpleDateFormat formatterDateShortNumber = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat formatterDateLong = new SimpleDateFormat("dd/MM/yyyy");
	public static DecimalFormat df = new DecimalFormat( "0.00000000" );
	public static DecimalFormat dfPorcentagem = new DecimalFormat( "0.00000000%" );
	
	protected BlockIdentificacao blockIdentificacao = new BlockIdentificacao();
	protected BlockFundo blockFundo = new BlockFundo();
	protected BlockRendaFixa blockRendaFixa = new BlockRendaFixa();
	protected BlockOperacoesCompromissadas blockOperacoesCompromissadas = new BlockOperacoesCompromissadas();
	protected BlockContasAPagarReceber blockContasAPagarReceber = new BlockContasAPagarReceber();
	protected BlockTesouraria blockTesouraria = new BlockTesouraria();
	protected BlockPatrimonio blockPatrimonio = new BlockPatrimonio();
	protected BlockRentabilidadeAcumulada blockRentabilidadeAcumulada = new BlockRentabilidadeAcumulada();
	protected BlockResumoDeCotas blockResumoDeCotas = new BlockResumoDeCotas();
	
	
	public Report()
	{
		
	}	
	public Report(FundoDeInvestimento fundo, String parsedText)
	{		
		this.fundo=fundo;
		this.lines=TextToLines.extractLines(parsedText);		
		this.buildBlocks(this.checkLines(this.lines));
		this.setReportCSV();			
	}
	public Report (FundoDeInvestimento fundo, ArrayList<String> lines)
	{
		this.fundo=fundo;
		this.buildBlocks(this.checkLines(lines));
		this.setReportCSV();
	}
	public static String cleanNumber(String string)
	{
		return string
				.replace("%", "")
				.replace(".", "")
				.replace(",", ".");
	}
	
	public ArrayList<String> checkLines(ArrayList<String> lines)
	{
		ArrayList<String> newLines = new ArrayList<String>();
		for (String line:lines)
		{
			//System.out.println(line);
			line = line
					.replaceAll("%0", "% ")
					.replaceAll("%1", "% ")
					.replaceAll("%2", "% ")
					.replaceAll("%3", "% ")
					.replaceAll("%4", "% ")
					.replaceAll("%5", "% ")
					.replaceAll("%6", "% ")
					.replaceAll("%7", "% ")
					.replaceAll("%8", "% ")
					.replaceAll("%9", "% ")
					.replaceAll("%-", "% -");
//			System.out.println(line);
			String[] allFields = line.split(" ");
			ArrayList<String> newFields = new ArrayList<String>();
			boolean existDoubleComma=false;
			for(int i = 0; i< allFields.length;i++)
			{
				
				existDoubleComma=CleanString.checkDoubleComma(allFields[i]);
				if(existDoubleComma)
				{					
					//System.out.println("Trouble: " + line);
					int iFirstComma=0;
					boolean existFirstComma=false;
					String troubleString = allFields[i];
					
								
					for(int j=0;j<troubleString.length();j++)
					{
//						System.out.println("Finding firstComma " + troubleString.charAt(j));
						if(troubleString.charAt(j)==',')
						{
							iFirstComma=j;
							existFirstComma=true;
//							System.out.println("iFirstComma " + iFirstComma);
							break;
						}
					}
					int iSecondNumber=0;
					if (existFirstComma)
					{						
						for(int j=iFirstComma+1;j<troubleString.length();j++)
						{
	//						System.out.println("Finding firstNumber different from zero " + troubleString.charAt(j));
							if(troubleString.charAt(j)!='0')
							{							
								iSecondNumber=j;
	//							System.out.println("BeginSecondNumber " + troubleString.charAt(iSecondNumber));
								break;
							}					
						}
					}
					String firstNumber = allFields[i].substring(0, iSecondNumber);
					String secondNumber = allFields[i].substring(iSecondNumber,allFields[i].length());
					newFields.add(firstNumber);
					newFields.add(secondNumber);
//					System.out.println(firstNumber + " " + secondNumber);
				}
				else
				{
					newFields.add(allFields[i]);
				}
			}
			if(newFields.size()>0)
			{
				String newLine = "";
				int iField=0;
				for(String field:newFields)
				{
					if (iField==0)
					{
						newLine=newLine+field;
					}
					else
					{
						newLine=newLine+" "+field;	
					}
					iField=iField+1;
				}	
				//System.out.println("Solve: " + newLine);
				newLines.add(newLine);
			}
			else
			{
				newLines.add(line);
			}
//			System.out.println(newLines.get(newLines.size()-1));
		}
		return newLines;
	}
	public void buildBlocks(ArrayList<String> lines)
	{		

	}	
	public void setReportCSV()
	{		
		this.reportCSV=this.addDataECodigoCSV(this.blockIdentificacao.getBlockCSV())
				+this.addDataECodigoCSV(this.blockFundo.getBlockCSV())
				+this.addDataECodigoCSV(this.blockRendaFixa.getBlockCSV())
				+this.addDataECodigoCSV(this.blockOperacoesCompromissadas.getBlockCSV())
				+this.addDataECodigoCSV(this.blockContasAPagarReceber.getBlockCSV())
				+this.addDataECodigoCSV(this.blockTesouraria.getBlockCSV())
				+this.addDataECodigoCSV(this.blockPatrimonio.getBlockCSV())
				+this.addDataECodigoCSV(this.blockRentabilidadeAcumulada.getBlockCSV())
				+this.addDataECodigoCSV(this.blockResumoDeCotas.getBlockCSV());		
	}
	public String getReportCSV()
	{
		return 	 this.reportCSV;		
	}
	public String addDataECodigoCSV(String textCSV)
	{	
//		System.out.println(textCSV);	
		String newCSV="";
		String[] lines = textCSV.split("\n");
		//System.out.println(this.blockCabecalho.getBlockCSV());
		if(this.blockIdentificacao.getCodigoDoCliente().equals(""))
		{
			this.blockIdentificacao.codigoDoCliente=this.fundo.getNomeCurto() + this.fundo.getCodigoCVM();
		}
		for(int i=0;i<lines.length;i++)
		{
			if(!lines[i].isEmpty())
			{
//				System.out.println(lines[i]);
				newCSV=newCSV
						+formatterDateShort.format(this.blockIdentificacao.getDataDaPosicao()) 
						+";" + this.blockIdentificacao.getCodigoDoCliente()
						+";" + lines[i]
						+"\n";
			}
		}
		return newCSV;
	}
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}
	public ArrayList<String> getLines() {
		return lines;
	}
	public void setLines(ArrayList<String> lines) {
		this.lines = lines;
	}
	public Date getDataDaPosicao() {
		return dataDaPosicao;
	}
	public void setDataDaPosicao(Date dataDaPosicao) {
		this.dataDaPosicao = dataDaPosicao;
	}
	public Date getDataDeEmissao() {
		return dataDeEmissao;
	}
	public void setDataDeEmissao(Date dataDeEmissao) {
		this.dataDeEmissao = dataDeEmissao;
	}
	public static SimpleDateFormat getFormatter() {
		return formatterDateShort;
	}
	public static void setFormatter(SimpleDateFormat formatter) {
		Report.formatterDateShort = formatter;
	}
	public static DecimalFormat getDf() {
		return df;
	}
	public static void setDf(DecimalFormat df) {
		Report.df = df;
	}
	public static DecimalFormat getDfPorcentagem() {
		return dfPorcentagem;
	}
	public static void setDfPorcentagem(DecimalFormat dfPorcentagem) {
		Report.dfPorcentagem = dfPorcentagem;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCodigoDoCliente() {
		return codigoDoCliente;
	}
	public void setCodigoDoCliente(String codigoDoCliente) {
		this.codigoDoCliente = codigoDoCliente;
	}
	public static SimpleDateFormat getFormatterDateShort() {
		return formatterDateShort;
	}
	public static void setFormatterDateShort(SimpleDateFormat formatterDateShort) {
		Report.formatterDateShort = formatterDateShort;
	}
	public static SimpleDateFormat getFormatterDateLong() {
		return formatterDateLong;
	}
	public static void setFormatterDateLong(SimpleDateFormat formatterDateLong) {
		Report.formatterDateLong = formatterDateLong;
	}
	public BlockIdentificacao getBlockCabecalho() {
		return blockIdentificacao;
	}
	public void setBlockCabecalho(BlockIdentificacao blockCabecalho) {
		this.blockIdentificacao = blockCabecalho;
	}
	public BlockFundo getBlockFundo() {
		return blockFundo;
	}
	public void setBlockFundo(BlockFundo blockFundo) {
		this.blockFundo = blockFundo;
	}
	public BlockRendaFixa getBlockRendaFixa() {
		return blockRendaFixa;
	}
	public void setBlockRendaFixa(BlockRendaFixa blockRendaFixa) {
		this.blockRendaFixa = blockRendaFixa;
	}
	public BlockOperacoesCompromissadas getBlockOperacoesCompromissadas() {
		return blockOperacoesCompromissadas;
	}
	public void setBlockOperacoesCompromissadas(
			BlockOperacoesCompromissadas blockOperacoesCompromissadas) {
		this.blockOperacoesCompromissadas = blockOperacoesCompromissadas;
	}
	public BlockContasAPagarReceber getBlockContasAPagarReceber() {
		return blockContasAPagarReceber;
	}
	public void setBlockContasAPagarReceber(
			BlockContasAPagarReceber blockContasAPagarReceber) {
		this.blockContasAPagarReceber = blockContasAPagarReceber;
	}
	public BlockTesouraria getBlockTesouraria() {
		return blockTesouraria;
	}
	public void setBlockTesouraria(BlockTesouraria blockTesouraria) {
		this.blockTesouraria = blockTesouraria;
	}
	public BlockPatrimonio getBlockPatrimonio() {
		return blockPatrimonio;
	}
	public void setBlockPatrimonio(BlockPatrimonio blockPatrimonio) {
		this.blockPatrimonio = blockPatrimonio;
	}
	public BlockRentabilidadeAcumulada getBlockRentabilidadeAcumulada() {
		return blockRentabilidadeAcumulada;
	}
	public void setBlockRentabilidadeAcumulada(
			BlockRentabilidadeAcumulada blockRentabilidadeAcumulada) {
		this.blockRentabilidadeAcumulada = blockRentabilidadeAcumulada;
	}
	public BlockResumoDeCotas getBlockResumoCotas() {
		return blockResumoDeCotas;
	}
	public void setBlockResumoCotas(BlockResumoDeCotas blockResumoCotas) {
		this.blockResumoDeCotas = blockResumoCotas;
	}
}
