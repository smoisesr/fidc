package cnab.bradesco.retorno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cnab.base.RegisterField;
import cnab.base.Register;

public class RegisterFileHeaderRetornoBradesco extends Register
{
	private RegisterField identificacaoDoRegistro = new RegisterField(1, 1,"Identificacao do Registro", "0");
	private RegisterField lote = new RegisterField(4, 7,"Lote de Serviço", "lote");
	private RegisterField registro = new RegisterField(8, 8,"Tipo de Registro", "registro");
	private RegisterField CNAB = new RegisterField(9, 17,"Uso Exclusivo Febraban/CNAB","CNAB");
	private RegisterField tipoDeInscricao = new RegisterField(18, 18,"Tipo de Inscrição da Empresa","tipoDeInscricao");
	private RegisterField numeroDeInscricao = new RegisterField(19, 32,"Número de Inscrição da Empresa","numeroDeInscricao");
	private RegisterField convenio = new RegisterField(33, 52,"Código do convénio no Banco","convenio");
	private RegisterField codigoAgencia = new RegisterField(53, 57,"Agencia mantenedora da conta","codigoAgencia");
	private RegisterField digitoVerificadorAgencia = new RegisterField(58,58,"Digito verificador da agência","digitoVerificadorAgencia");
	private RegisterField numeroConta = new RegisterField(59, 70,"Número da conta corrente","numeroConta");
	private RegisterField digitoVerificadorConta = new RegisterField(71, 71,"Digito verificador da conta","digitoVerificadorConta");
	private RegisterField digitoVerificadorAgenciaConta = new RegisterField(72, 72,"Digito verificador da agência/conta","digitoVerificadorAgenciaConta");
	private RegisterField nomeDaEmpresa = new RegisterField(73, 102,"Nome da Empresa","nomeDaEmpresa");
	private RegisterField nomeDoBanco = new RegisterField(103, 132,"Nome do Banco","nomeDoBanco");
	private RegisterField CNAB2 = new RegisterField(133, 142,"Uso Exclusivo Febraban/CNAB","CNAB2");
	private RegisterField codigoRemessa = new RegisterField(143, 143,"Código Remessa/Retorno","codigoRemessa");
	private RegisterField dataDeGeracao = new RegisterField(144, 151,"Data de geração do arquivo","dataDeGeracao");
	private RegisterField horaDeGeracao = new RegisterField(152, 157,"Hora de geração do arquivo", "horaDeGeracao");
	private RegisterField sequenciaArquivo = new RegisterField(158, 163,"Número sequencial do arquivo", "sequenciaArquivo");
	private RegisterField layoutDoArquivo = new RegisterField(164, 166,"Número da versão do layout do arquivo", "layoutDoArquivo");
	private RegisterField densidade = new RegisterField(167, 171,"Densidade de gravação do arquivo", "densidade");
	private RegisterField reservadoBanco = new RegisterField(172, 191,"Para uso reservado do banco", "reservadoBanco");
	private RegisterField reservadoEmpresa = new RegisterField(192, 211,"Para uso reservado da empresa", "reservadoEmpresa");
	private RegisterField CNAB3 = new RegisterField(212, 240,"Uso exclusivo Febraban/CNAB", "CNAB3");

	public RegisterFileHeaderRetornoBradesco(String line)
	{
		this.identificacaoDoRegistro.setValue(Integer.parseInt((String) RegisterField.extractString(this.identificacaoDoRegistro.getInitialPosition(), this.identificacaoDoRegistro.getFinalPosition(), line)));
		this.lote.setValue(Integer.parseInt((String) RegisterField.extractString(this.lote.getInitialPosition(), this.lote.getFinalPosition(), line)));
		this.registro.setValue(Integer.parseInt((String) RegisterField.extractString(this.registro.getInitialPosition(), this.registro.getFinalPosition(), line)));
		this.CNAB.setValue((String) RegisterField.extractString(this.CNAB.getInitialPosition(), this.CNAB.getFinalPosition(), line));
		this.tipoDeInscricao.setValue(Integer.parseInt((String) RegisterField.extractString(this.tipoDeInscricao.getInitialPosition(), this.tipoDeInscricao.getFinalPosition(), line)));	
		this.numeroDeInscricao.setValue(Long.parseLong((String) RegisterField.extractString(this.numeroDeInscricao.getInitialPosition(), this.numeroDeInscricao.getFinalPosition(), line)));		
		this.convenio.setValue((String) RegisterField.extractString(this.convenio.getInitialPosition(), this.convenio.getFinalPosition(), line));		
		this.codigoAgencia.setValue(Integer.parseInt((String) RegisterField.extractString(this.codigoAgencia.getInitialPosition(), this.codigoAgencia.getFinalPosition(), line)));		
		this.digitoVerificadorAgencia.setValue((String) RegisterField.extractString(this.digitoVerificadorAgencia.getInitialPosition(), this.digitoVerificadorAgencia.getFinalPosition(), line));		
		this.numeroConta.setValue(Integer.parseInt((String) RegisterField.extractString(this.numeroConta.getInitialPosition(), this.numeroConta.getFinalPosition(), line)));
		this.digitoVerificadorConta.setValue((String) RegisterField.extractString(this.digitoVerificadorConta.getInitialPosition(), this.digitoVerificadorConta.getFinalPosition(), line));
		this.digitoVerificadorAgenciaConta.setValue((String) RegisterField.extractString(this.digitoVerificadorAgenciaConta.getInitialPosition(), this.digitoVerificadorAgenciaConta.getFinalPosition(), line));		
		this.nomeDaEmpresa.setValue(RegisterField.extractString(this.nomeDaEmpresa.getInitialPosition(), this.nomeDaEmpresa.getFinalPosition(), line));
		this.nomeDoBanco.setValue(RegisterField.extractString(this.nomeDoBanco.getInitialPosition(), this.nomeDoBanco.getFinalPosition(), line));
		this.CNAB2.setValue(RegisterField.extractString(this.CNAB2.getInitialPosition(), this.CNAB2.getFinalPosition(), line));
		this.codigoRemessa.setValue(RegisterField.extractString(this.codigoRemessa.getInitialPosition(), this.codigoRemessa.getFinalPosition(), line));
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat formatterLocal = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = formatter.parse((String)RegisterField.extractString(this.dataDeGeracao.getInitialPosition(), this.dataDeGeracao.getFinalPosition(), line));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dataDeGeracao.setValue(formatterLocal.format(date));
		SimpleDateFormat formatterHour = new SimpleDateFormat("hhmmss");
		SimpleDateFormat formatterHourLocal = new SimpleDateFormat("hh:mm:ss");
		Date dateHour=null;
		try 
		{
			 dateHour= (Date)formatterHour.parse((String) RegisterField.extractString(this.horaDeGeracao.getInitialPosition(), this.horaDeGeracao.getFinalPosition(), line));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		this.horaDeGeracao.setValue(formatterHourLocal.format(dateHour));
		this.sequenciaArquivo.setValue(Integer.parseInt((String) RegisterField.extractString(this.sequenciaArquivo.getInitialPosition(), this.sequenciaArquivo.getFinalPosition(), line)));
		this.layoutDoArquivo.setValue(Integer.parseInt((String) RegisterField.extractString(this.layoutDoArquivo.getInitialPosition(), this.layoutDoArquivo.getFinalPosition(), line)));
		this.densidade.setValue(Integer.parseInt((String) RegisterField.extractString(this.densidade.getInitialPosition(), this.densidade.getFinalPosition(), line)));
		this.reservadoBanco.setValue((String) RegisterField.extractString(this.reservadoBanco.getInitialPosition(), this.reservadoBanco.getFinalPosition(), line));
		this.reservadoEmpresa.setValue((String) RegisterField.extractString(this.reservadoEmpresa.getInitialPosition(), this.reservadoEmpresa.getFinalPosition(), line));
		this.CNAB3.setValue((String) RegisterField.extractString(this.CNAB3.getInitialPosition(), this.CNAB3.getFinalPosition(), line));
	}
	
	public void showRegister()
	{
		System.out.print(this.identificacaoDoRegistro.getValue().toString()+"\t");
		System.out.print(this.lote.getValue().toString()+"\t");
		System.out.print(this.registro.getValue().toString()+"\t");
		System.out.print(this.CNAB.getValue().toString()+"\t");
		System.out.print(this.tipoDeInscricao.getValue().toString()+"\t");
		System.out.print(this.numeroDeInscricao.getValue().toString()+"\t");
		System.out.print(this.convenio.getValue().toString()+"\t");
		System.out.print(this.codigoAgencia.getValue().toString()+"\t");
		System.out.print(this.digitoVerificadorAgencia.getValue().toString()+"\t");
		System.out.print(this.numeroConta.getValue().toString()+"\t");
		System.out.print(this.digitoVerificadorConta.getValue().toString()+"\t");
		System.out.print(this.digitoVerificadorAgenciaConta.getValue().toString()+"\t");
		System.out.print(this.nomeDaEmpresa.getValue().toString()+"\t");
		System.out.print(this.nomeDoBanco.getValue().toString()+"\t");
		System.out.print(this.CNAB2.getValue().toString()+"\t");
		System.out.print(this.codigoRemessa.getValue().toString()+"\t");
		System.out.print(this.dataDeGeracao.getValue().toString()+"\t");
		System.out.print(this.horaDeGeracao.getValue().toString()+"\t");
		System.out.print(this.sequenciaArquivo.getValue().toString()+"\t");
		System.out.print(this.layoutDoArquivo.getValue().toString()+"\t");
		System.out.print(this.densidade.getValue().toString()+"\t");
		System.out.print(this.reservadoBanco.getValue().toString()+"\t");
		System.out.print(this.reservadoEmpresa.getValue().toString()+"\t");
		System.out.print(this.CNAB3.getValue().toString()+"\n");
	}
	
	
	public RegisterField getBanco()
	{
		return identificacaoDoRegistro;
	}
	public void setBanco(RegisterField banco)
	{
		this.identificacaoDoRegistro = banco;
	}
	public RegisterField getLote()
	{
		return lote;
	}
	public void setLote(RegisterField lote)
	{
		this.lote = lote;
	}
	public RegisterField getRegistro()
	{
		return registro;
	}
	public void setRegistro(RegisterField registro)
	{
		this.registro = registro;
	}
	public RegisterField getCNAB()
	{
		return CNAB;
	}
	public void setCNAB(RegisterField cNAB)
	{
		CNAB = cNAB;
	}
	public RegisterField getTipoDeInscricao()
	{
		return tipoDeInscricao;
	}
	public void setTipoDeInscricao(RegisterField tipoDeInscricao)
	{
		this.tipoDeInscricao = tipoDeInscricao;
	}
	public RegisterField getNumeroDeInscricao()
	{
		return numeroDeInscricao;
	}
	public void setNumeroDeInscricao(RegisterField numeroDeInscricao)
	{
		this.numeroDeInscricao = numeroDeInscricao;
	}
	public RegisterField getConvenio()
	{
		return convenio;
	}
	public void setConvenio(RegisterField convenio)
	{
		this.convenio = convenio;
	}
	public RegisterField getCodigoAgencia()
	{
		return codigoAgencia;
	}
	public void setCodigoAgencia(RegisterField codigoAgencia)
	{
		this.codigoAgencia = codigoAgencia;
	}
	public RegisterField getDigitoVerificadorAgencia()
	{
		return digitoVerificadorAgencia;
	}
	public void setDigitoVerificadorAgencia(RegisterField digitoVerificadorAgencia)
	{
		this.digitoVerificadorAgencia = digitoVerificadorAgencia;
	}
	public RegisterField getNumeroConta()
	{
		return numeroConta;
	}
	public void setNumeroConta(RegisterField numeroConta)
	{
		this.numeroConta = numeroConta;
	}
	public RegisterField getDigitoVerificadorConta()
	{
		return digitoVerificadorConta;
	}
	public void setDigitoVerificadorConta(RegisterField digitoVerificadorConta)
	{
		this.digitoVerificadorConta = digitoVerificadorConta;
	}
	public RegisterField getDigitoVerificadorAgenciaConta()
	{
		return digitoVerificadorAgenciaConta;
	}
	public void setDigitoVerificadorAgenciaConta(RegisterField digitoVerificadorAgenciaConta)
	{
		this.digitoVerificadorAgenciaConta = digitoVerificadorAgenciaConta;
	}
	public RegisterField getNomeDaEmpresa()
	{
		return nomeDaEmpresa;
	}
	public void setNomeDaEmpresa(RegisterField nomeDaEmpresa)
	{
		this.nomeDaEmpresa = nomeDaEmpresa;
	}
	public RegisterField getNomeDoBanco()
	{
		return nomeDoBanco;
	}
	public void setNomeDoBanco(RegisterField nomeDoBanco)
	{
		this.nomeDoBanco = nomeDoBanco;
	}
	public RegisterField getCNAB2()
	{
		return CNAB2;
	}
	public void setCNAB2(RegisterField cNAB2)
	{
		CNAB2 = cNAB2;
	}
	public RegisterField getCodigoRemessa()
	{
		return codigoRemessa;
	}
	public void setCodigoRemessa(RegisterField codigoRemessa)
	{
		this.codigoRemessa = codigoRemessa;
	}
	public RegisterField getDataDeGeracao()
	{
		return dataDeGeracao;
	}
	public void setDataDeGeracao(RegisterField dataDeGeracao)
	{
		this.dataDeGeracao = dataDeGeracao;
	}
	public RegisterField getHoraDeGeracao()
	{
		return horaDeGeracao;
	}
	public void setHoraDeGeracao(RegisterField horaDeGeracao)
	{
		this.horaDeGeracao = horaDeGeracao;
	}
	public RegisterField getSequenciaArquivo()
	{
		return sequenciaArquivo;
	}
	public void setSequenciaArquivo(RegisterField sequenciaArquivo)
	{
		this.sequenciaArquivo = sequenciaArquivo;
	}
	public RegisterField getLayoutDoArquivo()
	{
		return layoutDoArquivo;
	}
	public void setLayoutDoArquivo(RegisterField layoutDoArquivo)
	{
		this.layoutDoArquivo = layoutDoArquivo;
	}
	public RegisterField getDensidade()
	{
		return densidade;
	}
	public void setDensidade(RegisterField densidade)
	{
		this.densidade = densidade;
	}
	public RegisterField getReservadoBanco()
	{
		return reservadoBanco;
	}
	public void setReservadoBanco(RegisterField reservadoBanco)
	{
		this.reservadoBanco = reservadoBanco;
	}
	public RegisterField getReservadoEmpresa()
	{
		return reservadoEmpresa;
	}
	public void setReservadoEmpresa(RegisterField reservadoEmpresa)
	{
		this.reservadoEmpresa = reservadoEmpresa;
	}
	public RegisterField getCNAB3()
	{
		return CNAB3;
	}
	public void setCNAB3(RegisterField cNAB3)
	{
		CNAB3 = cNAB3;
	}
}
