package br.com.doasangue.dto;

import br.com.doasangue.model.User;

public class MatchDTO {

	private Long id;
	
	private User user;

	public MatchDTO(){
		
	}
	
	public MatchDTO(Long id, User user){
		this.id = id;
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
