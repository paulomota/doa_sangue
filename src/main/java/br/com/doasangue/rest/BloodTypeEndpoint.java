package br.com.doasangue.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.doasangue.enums.BloodTypeEnum;

@Path("/blood-type")
@Produces(MediaType.APPLICATION_JSON)
public class BloodTypeEndpoint extends AbstractEndpoint{

	@GET
	@Path("/")
	public Response getBloodTypes() throws IOException{
		try{
			
			return getSucessResponse(BloodTypeEnum.values());
			
		} catch(Exception e){
			return getErrorResponse(e.getMessage());
		}
	}
}
