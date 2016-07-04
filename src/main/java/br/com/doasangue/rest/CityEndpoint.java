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
import org.apache.deltaspike.jpa.api.transaction.TransactionScoped;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.doasangue.enums.StateEnum;
import br.com.doasangue.repository.CityRepository;

@Path("/city")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class CityEndpoint extends AbstractEndpoint{

	@Inject
	private CityRepository cityRepository;
	
	@GET
	@Path("/")
	public Response getCities(@QueryParam("state") String stateInitials) throws IOException{
		try{
			
			if(StringUtils.isEmpty(stateInitials)){
				return getSucessResponse(cityRepository.findAll());

			} else{
				StateEnum state = StateEnum.getByInitials(stateInitials);
				return getSucessResponse(cityRepository.findByState(state));
			}
			
		} catch(Exception e){
			return getErrorResponse(e.getMessage());
		}
	}
	
}
