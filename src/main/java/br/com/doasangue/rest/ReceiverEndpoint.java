package br.com.doasangue.rest;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.doasangue.model.User;
import br.com.doasangue.service.UserService;

@Path("/receiver")
@Produces(MediaType.APPLICATION_JSON)
public class ReceiverEndpoint extends AbstractEndpoint{

	@Inject
	private UserService userService;
	
	@GET
	@Path("/{userId}")
	public Response searchReceivers(@PathParam("userId") Long userId, 
									@QueryParam("bloodType") String bloodType, 
									@QueryParam("distance") Float distance,
									@QueryParam("urgency") String urgency,
									@QueryParam("lat") String lat,
									@QueryParam("lng") String lng) throws IOException{
		try{
			List<User> receivers = userService.searchReceivers(userId, bloodType, distance, urgency, lat, lng);
			
			return getSucessResponse(receivers);
			
		} catch(Exception e){
			e.printStackTrace();
			return getErrorResponse(e.getMessage());
		}
	}
}
