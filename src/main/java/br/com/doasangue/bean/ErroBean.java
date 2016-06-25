package br.com.doasangue.bean;

public class ErroBean {

	private long codigo;
	
	private String mensagem;

	public static final ErroBean ERRO_INESPERADO = new ErroBean();
	
	public static final ErroBean USUARIO_EMAIL_JA_CADASTRADO = new ErroBean();
	
	static{
		USUARIO_EMAIL_JA_CADASTRADO.setCodigo(2);
		USUARIO_EMAIL_JA_CADASTRADO.setMensagem("Já existe um cadastro para o email informado.");
	}
	
	static{
		ERRO_INESPERADO.setCodigo(2);
		ERRO_INESPERADO.setMensagem("Ocorreu um erro inesperado.");
	}
	
	
	public ErroBean(){
		
	}
	
	public ErroBean(String mensagem){
		this.mensagem = mensagem;
	}
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	
	
}
