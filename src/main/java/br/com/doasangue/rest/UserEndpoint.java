package br.com.doasangue.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.doasangue.bean.ErroBean;
import br.com.doasangue.exception.ValidationException;
import br.com.doasangue.model.ServerResponseBean;
import br.com.doasangue.model.User;
import br.com.doasangue.repository.UserRepository;
import br.com.doasangue.service.UserService;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserEndpoint extends AbstractEndpoint{

	@Inject
	private UserService userService;
	
	@Inject
	private UserRepository userRepository;
	
	@POST
	@Path("/register")
	public Response cadastrar(User user) throws IOException {
		ServerResponseBean serverResponse = new ServerResponseBean(false, 200);
		
		try{
			log.info(user.toString());
		
			userService.cadastrar(user);
			
			serverResponse.setData(user);
			
		} catch(PersistenceException pe){
			log.error("Tentando cadastrar usuário com email já existente");
			serverResponse = getServerResponseError(ErroBean.USUARIO_EMAIL_JA_CADASTRADO);
			
		} catch(Exception e){
			e.printStackTrace();
			serverResponse = getServerResponseError(ErroBean.ERRO_INESPERADO);
		}
		
		return serverResponse.getResponse();
	}
	
	@POST
	@Path("/update")
	public Response updateUser(User user) throws IOException{
		if(user == null || user.getId() == null){
			return getErrorResponse("Não foi possível receber os dados do usuário");
		}
		
		try{
			log.info(user.toString());
			
			User userUpdated = userService.update(user);
			
			return getSucessResponse(userUpdated);
			
		} catch(ValidationException ve){
			return getErrorResponse(ve.getMsgErrors().get(0));
			
		} catch(Exception e){
			e.printStackTrace();
			return getErrorResponse(e.getMessage());
		}
	}
}
