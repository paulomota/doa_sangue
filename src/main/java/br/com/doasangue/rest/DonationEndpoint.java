package br.com.doasangue.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.doasangue.model.Donation;
import br.com.doasangue.service.DonationService;

@Path("/donation")
@Produces(MediaType.APPLICATION_JSON)
public class DonationEndpoint extends AbstractEndpoint{

	@Inject
	private DonationService donationService;
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response registerDonation(@FormParam("donorId") Long donorId, @FormParam("receiverId") Long receiverId, @FormParam("match") Boolean match) 
			throws IOException{
		try{
			Donation donation = donationService.register(donorId, receiverId, match);
			return getSucessResponse(donation);
			
		} catch(Exception e){
			e.printStackTrace();
			return getErrorResponse(e.getMessage());
		}
	}
}
