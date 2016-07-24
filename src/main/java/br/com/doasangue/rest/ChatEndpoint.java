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

import br.com.doasangue.service.ChatService;

@Path("/chat")
@Produces(MediaType.APPLICATION_JSON) 
public class ChatEndpoint extends AbstractEndpoint{

	@Inject
	private ChatService chatService;
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response sendChatMessage(@FormParam("senderId") Long senderId, 
									@FormParam("receiverId") Long receiverId,
									@FormParam("message") String message) throws IOException{
		try{
			String response = chatService.sendMessage(senderId, receiverId, message);
			return getSucessResponse(response);
			
		} catch(Exception e){
			return getErrorResponse(e.getMessage());
		}
	}
	
}
