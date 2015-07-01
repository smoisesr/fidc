package cnab.remessa.paulista;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RemessaPaulista400 {

	private ArrayList<RegisterDetailRemessaPaulista400> registersDetail = new ArrayList<RegisterDetailRemessaPaulista400>();
	private RegisterFileHeaderRemessaPaulista400 registerHeader = new RegisterFileHeaderRemessaPaulista400();
	private RegisterFileTrailerRemessaPaulista400 registerTrailer = new RegisterFileTrailerRemessaPaulista400();
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$
	public RemessaPaulista400() 
	{
				
	}
	public RemessaPaulista400(RegisterFileHeaderRemessaPaulista400 registerHeader, ArrayList<RegisterDetailRemessaPaulista400> registersDetail, RegisterFileTrailerRemessaPaulista400 registerTrailer) 
	{
		this.registerHeader = registerHeader;
		this.registersDetail = registersDetail;
		this.registerTrailer = registerTrailer;
	}
	public String stringRafaelFormat()
	{
		String content=""; //$NON-NLS-1$
		content= content + "Data;CodigoDoOriginador;"+RegisterDetailRemessaPaulista400.labelsCSV()+"\n"; //$NON-NLS-1$ //$NON-NLS-2$
		for(RegisterDetailRemessaPaulista400 registerDetail:this.registersDetail)
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
	public ArrayList<RegisterDetailRemessaPaulista400> getRegistersDetail() {
		return this.registersDetail;
	}
	public void setRegistersDetail(
			ArrayList<RegisterDetailRemessaPaulista400> registersDetail) {
		this.registersDetail = registersDetail;
	}
	public RegisterFileHeaderRemessaPaulista400 getRegisterHeader() {
		return this.registerHeader;
	}
	public void setRegisterHeader(RegisterFileHeaderRemessaPaulista400 registerHeader) {
		this.registerHeader = registerHeader;
	}
	public RegisterFileTrailerRemessaPaulista400 getRegisterTrailer() {
		return this.registerTrailer;
	}
	public void setRegisterTrailer(
			RegisterFileTrailerRemessaPaulista400 registerTrailer) {
		this.registerTrailer = registerTrailer;
	}
}
