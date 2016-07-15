package br.com.doasangue.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.doasangue.enums.BloodTypeEnum;
import br.com.doasangue.enums.GenderEnum;
import br.com.doasangue.enums.RoleEnum;
import br.com.doasangue.enums.UrgencyEnum;

@Entity
@Table(name = "user")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private RoleEnum role;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private GenderEnum gender;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "birthdate")
	private Date birthdate;
	
	@Column(name = "weight")
	private Float weight;

	@Column(name="city")
	private String city;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "urgency")
	private UrgencyEnum urgency;
	
	@Column(name="hospital")
	private String hospital;
	
	@Column(name="reason")
	private String reason;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "blood_type")
	private BloodTypeEnum bloodType;
	
	private String lat;
	
	private String lng;
	
	@Column(name="picture_path")
	private String picturePath;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public Long getBirthdate(){
		if(birthdate != null){
			return birthdate.getTime();
		}
		
		return null;
	}
	
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BloodTypeEnum getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodTypeEnum bloodType) {
		this.bloodType = bloodType;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public UrgencyEnum getUrgency() {
		return urgency;
	}

	public void setUrgency(UrgencyEnum urgency) {
		this.urgency = urgency;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
	
	
}
