package br.com.doa_sangue.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.doa_sangue.bean.ErroBean;
import br.com.doa_sangue.model.ServerResponseBean;
import br.com.doa_sangue.model.User;
import br.com.doa_sangue.service.UsuarioService;

@Path("/usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioEndpoint extends AbstractEndpoint{

	@Inject
	private UsuarioService usuarioService;
	
	@POST
	@Path("/cadastrar")
	public Response cadastrar(User usuario) throws IOException {
		ServerResponseBean serverResponse = new ServerResponseBean(false, 200);
		
		log.info(usuario.toString());
		
		try{
			usuarioService.cadastrar(usuario);
			
			serverResponse.setData(usuario);
			
		} catch(PersistenceException pe){
			log.error("Tentando cadastrar usuário com email já existente");
			serverResponse = getServerResponseError(ErroBean.USUARIO_EMAIL_JA_CADASTRADO);
			
		} catch(Exception e){
			e.printStackTrace();
			serverResponse = getServerResponseError(ErroBean.ERRO_INESPERADO);
		}
		
		return serverResponse.getResponse();
	}
}
