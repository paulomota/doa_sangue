package br.com.doasangue.rest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import br.com.doasangue.bean.ErroBean;
import br.com.doasangue.model.ServerResponseBean;

public class AbstractEndpoint {

	@Inject
	protected Logger log;
	
	protected Response getSucessResponse(Object dataResponse) throws IOException{
		ServerResponseBean serverResponse = new ServerResponseBean(false, 200);
		serverResponse.setData(dataResponse);
		
		return serverResponse.getResponse();
	}
	
	protected Response getErrorResponse(String message) throws IOException{
		List<ErroBean> erros = new ArrayList<ErroBean>();
		erros.add(new ErroBean(message));
		
		ServerResponseBean serverResponse = new ServerResponseBean(true, 200);
		serverResponse.setErros(erros);
		
		return serverResponse.getResponse();
	}
	
	protected ServerResponseBean getServerResponseError(ErroBean erro){
		log.error("\n" + erro.getMensagem() + "\n");
		
		ServerResponseBean serverResponse = new ServerResponseBean(true, 200);
		serverResponse.addError(erro);
		
		return serverResponse;
	}
}
