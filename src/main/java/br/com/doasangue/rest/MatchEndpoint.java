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

import br.com.doasangue.dto.MatchDTO;
import br.com.doasangue.service.DonationService;

@Path("/match")
@Produces(MediaType.APPLICATION_JSON)
public class MatchEndpoint extends AbstractEndpoint{

	@Inject
	private DonationService donationService;
	
	@GET
	@Path("/{donorId}")
	public Response getMyMatchs(@PathParam("donorId") Long donorId, @QueryParam("lastMatchId") Long lastMatchId) throws IOException{
		try{
			
			List<MatchDTO> matchs = donationService.findMatchedReceivers(donorId, lastMatchId);
			
			return getSucessResponse(matchs);
			
		} catch(Exception e){
			e.printStackTrace();
			return getErrorResponse(e.getMessage());
		}
	}
}
