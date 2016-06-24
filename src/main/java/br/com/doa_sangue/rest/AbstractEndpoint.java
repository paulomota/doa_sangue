package br.com.doa_sangue.rest;


import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.doa_sangue.bean.ErroBean;
import br.com.doa_sangue.model.ServerResponseBean;

public class AbstractEndpoint {

	@Inject
	protected Logger log;
	
	protected ServerResponseBean getServerResponseError(ErroBean erro){
		log.error("\n" + erro.getMensagem() + "\n");
		
		ServerResponseBean serverResponse = new ServerResponseBean(true, 200);
		serverResponse.addError(erro);
		
		return serverResponse;
	}
}
