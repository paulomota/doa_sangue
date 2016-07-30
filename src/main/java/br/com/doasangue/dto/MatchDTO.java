package br.com.doasangue.dto;

import java.util.Date;

import br.com.doasangue.model.User;

public class MatchDTO {

	private Long id;
	
	private User user;

	private Date matchDate;
	
	public MatchDTO(){
		
	}
	
	public MatchDTO(Long id, User user, Date matchDate){
		this.id = id;
		this.user = user;
		this.matchDate = matchDate;
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

	public Date getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}
	
}
