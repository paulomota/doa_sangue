package br.com.doasangue.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.doasangue.model.User;
import br.com.doasangue.service.UserService;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginEndpoint extends AbstractEndpoint{

	@Inject
	private UserService userService;
	
	@GET
	@Path("/")
	public Response doLogin(@QueryParam("email") String email, @QueryParam("password") String password) throws IOException{
		try{
			User user = userService.findByEmailAndPassword(email, password);
			
			if(user == null){
				return getErrorResponse("Email ou senha inválidos.");
			}
			
			return getSucessResponse(user);
			
		} catch(Exception e){
			e.printStackTrace();
			return getErrorResponse(e.getMessage());
		}
	}
}
