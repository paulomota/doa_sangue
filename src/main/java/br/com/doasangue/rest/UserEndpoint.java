package br.com.doasangue.rest;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.com.doasangue.bean.ErroBean;
import br.com.doasangue.dto.MultipartFormDTO;
import br.com.doasangue.exception.ValidationException;
import br.com.doasangue.model.ServerResponseBean;
import br.com.doasangue.model.User;
import br.com.doasangue.repository.UserRepository;
import br.com.doasangue.service.UserService;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserEndpoint extends AbstractEndpoint{

	@Context
	private UriInfo uriInfo;
	
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
	public Response updateGeolocation(@PathParam("userId") Long userId, @FormParam("lat") String lat, @FormParam("lng") String lng) throws IOException{
		try{
			User user = userService.updateGeolocation(userId, lat, lng);
			
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
	
	@PUT
	@Path("/update-picture") 
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Transactional
	public Response updatePictureWithFile(@MultipartForm MultipartFormDTO pictureForm) throws IOException{
		try{
			User user = userRepository.findByEmail(pictureForm.getEmail());
			
			if(user == null){
				return getErrorResponse("Não foi possível encontrar o usuário para atualizar a foto de perfil");
			}
			
			String pictureFileName = "profile_picture_user_"+user.getId()+".png";
			
			savePicture(pictureForm.getFiledata(), pictureFileName);
			
			user.setPicturePath(pictureFileName);
			user = userRepository.saveAndFlush(user);
			
			return getSucessResponse(user);
			
		} catch(Exception e){
			return getErrorResponse(e.getMessage());
		}
	}
	
	// save uploaded file to a defined location on the server
    private void savePicture(byte[] bytes, String fileName) {
    	System.out.println("absolute: " + uriInfo.getAbsolutePath());
    	System.out.println("baseUri: " + uriInfo.getBaseUri());
    	
    	String baseUrl = "/users/paulomota/Projetos/freelas/doa_sangue_images/profile_pictures/";
    	
    	String imagesPath = baseUrl + fileName;
    	
    	System.out.println("imagePath: " + imagesPath);
    	
        try {
			FileOutputStream out = new FileOutputStream(imagesPath);
			out.write(bytes);
			out.close();
        	
//            OutputStream outpuStream = new FileOutputStream(new File(imagesPath));
//            outpuStream = new FileOutputStream(new File(imagesPath));
//            outpuStream.write(bytes);
//            
//            outpuStream.flush();
//            outpuStream.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
