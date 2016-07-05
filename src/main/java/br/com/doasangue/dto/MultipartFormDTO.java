package br.com.doasangue.dto;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class MultipartFormDTO {

	@FormParam("email")
	@PartType(MediaType.TEXT_PLAIN)
	private String email;
	
	@FormParam("filedata")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
	private byte[] filedata;
 
	public MultipartFormDTO(){
		
	}
	
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getFiledata() {
		return filedata;
	}

	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}

}
