package br.com.doasangue.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "donation", uniqueConstraints=@UniqueConstraint(columnNames={"donor_id", "receiver_id"}))
public class Donation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "donor_id", referencedColumnName="id", nullable = false)
	private User donor;
	
	@ManyToOne
	@JoinColumn(name = "receiver_id", referencedColumnName="id", nullable = false)
	private User receiver;
	
	@Column(name = "matched")
	private Boolean matched;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "register_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date registerDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getDonor() {
		return donor;
	}

	public void setDonor(User donor) {
		this.donor = donor;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Boolean getMatched() {
		return matched;
	}

	public void setMatched(Boolean matched) {
		this.matched = matched;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
}
