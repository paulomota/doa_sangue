package br.com.doasangue.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.deltaspike.core.util.StringUtils;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.doasangue.exception.ValidationException;
import br.com.doasangue.model.User;
import br.com.doasangue.repository.UserRepository;
import br.com.doasangue.service.UserService;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginEndpoint extends AbstractEndpoint{

	@Inject
	private UserService userService;
	
	@Inject
	private UserRepository userRepository;
	
	@GET
	@Path("/")
	public Response doLogin(@QueryParam("email") String email, @QueryParam("password") String password) throws IOException{
		try{
			if(StringUtils.isEmpty(email)){
				throw new ValidationException("Informe o seu e-mail");
			}
			
			if(StringUtils.isEmpty(password)){
				throw new ValidationException("Informe a sua senha");
			}
			
			User user = userService.findByEmailAndPassword(email, password);
			
			if(user == null){
				return getErrorResponse("E-mail ou senha inválidos.");
			}
			
			return getSucessResponse(user);
			
		} catch(ValidationException ve){
			return getErrorResponse(ve.getMsgErrors().get(0));
			
		} catch(Exception e){
			e.printStackTrace();
			return getErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/retrieve-password")
	@Transactional
	public Response retrievePassword(@QueryParam("email") String email) throws IOException{
		try{
			if(StringUtils.isEmpty(email)){
				throw new ValidationException("Informe o seu e-mail");
			}
			
			User user = userRepository.findOptionalByEmail(email);
			
			if(user == null){
				throw new ValidationException("Nenhum usuário encontrado com o e-mail informado");
			}
			
			return getSucessResponse("Enviamos um e-mail com as instruções para recuperar sua senha.");
			
		} catch(Exception e){
			e.printStackTrace();
			return getErrorResponse(e.getMessage());
		}
	}
}
