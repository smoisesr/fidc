package cnab.remessa.paulista;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RemessaPaulista444 {

	private ArrayList<RegisterDetailRemessaPaulista444> registersDetail = new ArrayList<RegisterDetailRemessaPaulista444>();
	private RegisterFileHeaderRemessaPaulista444 registerHeader = new RegisterFileHeaderRemessaPaulista444();
	private RegisterFileTrailerRemessaPaulista444 registerTrailer = new RegisterFileTrailerRemessaPaulista444();
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
	public RemessaPaulista444() 
	{
				
	}
	public RemessaPaulista444(RegisterFileHeaderRemessaPaulista444 registerHeader, ArrayList<RegisterDetailRemessaPaulista444> registersDetail, RegisterFileTrailerRemessaPaulista444 registerTrailer) 
	{
		this.registerHeader = registerHeader;
		this.registersDetail = registersDetail;
		this.registerTrailer = registerTrailer;
	}
	public String stringRafaelFormat()
	{
		String content=""; //$NON-NLS-1$
		content= content + "Data;CodigoDoOriginador;"+RegisterDetailRemessaPaulista444.labelsCSV()+"\n"; //$NON-NLS-1$ //$NON-NLS-2$
		for(RegisterDetailRemessaPaulista444 registerDetail:this.registersDetail)
		{
			System.out.println(this.registerHeader.getDataDaGravacaoDoArquivo().getValue() 
								+ ";"+ this.registerHeader.getCodigoDoOriginador().getValue() + ";"  //$NON-NLS-1$ //$NON-NLS-2$
								+ registerDetail.toCSV());
			content = content + (this.registerHeader.getDataDaGravacaoDoArquivo().getValue() 
					+ ";"+ this.registerHeader.getCodigoDoOriginador().getValue() + ";"  //$NON-NLS-1$ //$NON-NLS-2$
					+ registerDetail.toCSV()) + "\n"; //$NON-NLS-1$
			
		}
		return content;
	}
	public ArrayList<RegisterDetailRemessaPaulista444> getRegistersDetail() {
		return this.registersDetail;
	}
	public void setRegistersDetail(
			ArrayList<RegisterDetailRemessaPaulista444> registersDetail) {
		this.registersDetail = registersDetail;
	}
	public RegisterFileHeaderRemessaPaulista444 getRegisterHeader() {
		return this.registerHeader;
	}
	public void setRegisterHeader(RegisterFileHeaderRemessaPaulista444 registerHeader) {
		this.registerHeader = registerHeader;
	}
	public RegisterFileTrailerRemessaPaulista444 getRegisterTrailer() {
		return this.registerTrailer;
	}
	public void setRegisterTrailer(
			RegisterFileTrailerRemessaPaulista444 registerTrailer) {
		this.registerTrailer = registerTrailer;
	}
}
