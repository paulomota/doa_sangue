package br.com.doasangue.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.doasangue.bean.ErroBean;
import br.com.doasangue.exception.ValidationException;
import br.com.doasangue.model.ServerResponseBean;
import br.com.doasangue.model.User;
import br.com.doasangue.service.UserService;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserEndpoint extends AbstractEndpoint{

	@Context
	private UriInfo uriInfo;
	
	@Inject
	private UserService userService;
	
	@POST
	@Path("/register")
	public Response cadastrar(User user) throws IOException {
		ServerResponseBean serverResponse = new ServerResponseBean(false, 200);
		
		try{
			log.info(user.toString());
		
			userService.cadastrar(user);
			
			serverResponse.setData(user);
			
		} catch(PersistenceException pe){
			log.error("Provavel: Tentando cadastrar usuário com email já existente");
			pe.printStackTrace();
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
	
	@POST
	@Path("/update-picture/{userId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updatePicture(@PathParam("userId") Long userId, @FormParam("pictureUrl") String pictureUrl) throws IOException{
		try{
			User user = userService.updatePicturePath(userId, pictureUrl);
			
			return getSucessResponse(user);
			
		} catch(ValidationException ve){
			return getErrorResponse(ve.getMsgErrors().get(0));
			
		} catch(Exception e){
			e.printStackTrace();
			return getErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/update-geolocation/{userId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateGeolocation(@PathParam("userId") Long userId, 
									@FormParam("lat") String lat, 
									@FormParam("lng") String lng, 
									@FormParam("city") String city) throws IOException{
		try{
			User user = userService.updateGeolocation(userId, lat, lng, city);
			
			return getSucessResponse(user);
			
		} catch(ValidationException ve){
			return getErrorResponse(ve.getMsgErrors().get(0));
			
		} catch(Exception e){
			e.printStackTrace();
			return getErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/update-role/{userId}/{roleInitial}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateGeolocation(@PathParam("userId") Long userId, @PathParam("roleInitial") String roleInitial) throws IOException{
		try{
			User user = userService.updateRole(userId, roleInitial);
			
			return getSucessResponse(user);
			
		} catch(ValidationException ve){
			return getErrorResponse(ve.getMsgErrors().get(0));
			
		} catch(Exception e){
			e.printStackTrace();
			return getErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/update-receiver-info/{receiverId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateReceiverInfo(@PathParam("receiverId") Long receiverId, 
									@FormParam("hospital") String hospital, 
									@FormParam("urgency") String urgency,
									@FormParam("reason") String reason) throws IOException{
		try{
			User user = userService.updateReceiverInfo(receiverId, hospital, urgency, reason);
			
			return getSucessResponse(user);
			
			
		} catch(ValidationException ve){
			return getErrorResponse(ve.getMsgErrors().get(0));
			
		} catch(Exception e){
			e.printStackTrace();
			return getErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/update-device-token")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateDeviceToken(@FormParam("userId") Long userId, 
									@FormParam("deviceToken") String deviceToken) throws IOException{
		try{
			User user = userService.updateDeviceToken(userId, deviceToken);
			
			return getSucessResponse(user);
			
			
		} catch(ValidationException ve){
			return getErrorResponse(ve.getMsgErrors().get(0));
			
		} catch(Exception e){
			e.printStackTrace();
			return getErrorResponse(e.getMessage());
		}
	}
	
}
