package br.com.doasangue.interceptor;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import br.com.doasangue.model.User;
import br.com.doasangue.service.UserService;
import br.com.doasangue.util.BasicAuthUtil;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter{

	@Inject
	private UserService userService;
	
	@Override
	public void filter(ContainerRequestContext containerRequest) throws IOException {
		String path = containerRequest.getUriInfo().getPath();

		System.out.println("\npath: " + path);
		
		if(path.equals("/user/register") ||
				path.equals("/login") || 
				path.equals("/blood-type") ||
				path.equals("/about")
				){
			
			return;
		}
		
		String auth = containerRequest.getHeaderString("Authorization");
		
		System.out.println("authorization: " + auth);
		
		if (auth == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		// lap : loginAndPassword
		String[] lap = BasicAuthUtil.decode(auth);

		// If login or password fail
		if (lap == null || lap.length != 2) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		
		User user = userService.findByEmailAndPassword(lap[0], lap[1]);
		
		if(user == null){
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
	}

}
