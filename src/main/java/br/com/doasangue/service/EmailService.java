package br.com.doasangue.service;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.util.Base64;

import br.com.doasangue.model.User;

@RequestScoped
public class EmailService {

	public void sendEmail(String destinaray, String subject, String str) throws NamingException, AddressException, MessagingException {
		Context initCtx = new InitialContext();
		Session session = (Session) initCtx.lookup("java:jboss/mail/doa_sangue");
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("prolife.contato@gmail.com"));
		
		InternetAddress to[] = new InternetAddress[1];
		to[0] = new InternetAddress(destinaray);
		message.setRecipients(Message.RecipientType.TO, to);
		
		message.setSubject(subject);	
		message.setContent(str, "text/html; charset=\"UTF-8\"");
		Transport.send(message);
	}
	
	public void sendEmail(User user, String subject, String message) throws AddressException, NamingException, MessagingException{
		this.sendEmail(user.getEmail(), subject, message);
	}
	
	public String getUrlRedefinirSenhaByUser(UriInfo uriInfo, User usuario){
		String baseUrl = uriInfo.getBaseUri().toString();
		baseUrl = baseUrl.replace("rest/", "");
		
		String url = baseUrl + "#/redefinir-senha?id="+getIdHashUrlByUser(usuario)+"&token="+getTokenUrlByUser(usuario);
		
		return url;
	}
	
	private String getIdHashUrlByUser(User user){
		String chaveId = user.getId() + "#" + user.getEmail();
		
		String idBase64 = Base64.encodeBytes(chaveId.getBytes());
		
		return idBase64;
	}
	
	public String getTokenUrlByUser(User user){
		String strintEmail = user.getEmail() + user.getId() + new Date();
		
		String emailBase64 = Base64.encodeBytes(strintEmail.getBytes());
		
		return emailBase64;
	}
}
