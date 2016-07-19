package br.com.doasangue.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.deltaspike.core.util.StringUtils;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.doasangue.enums.BloodTypeEnum;
import br.com.doasangue.enums.GenderEnum;
import br.com.doasangue.enums.RoleEnum;
import br.com.doasangue.enums.UrgencyEnum;
import br.com.doasangue.exception.ValidationException;
import br.com.doasangue.model.User;
import br.com.doasangue.repository.UserRepository;
import br.com.doasangue.util.EmailUtil;

@Transactional
@RequestScoped
public class UserService {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserRepository userRespository;
	
	public void cadastrar(User usuario) {
		em.persist(usuario);
	}
	
	public User findByEmailAndPassword(String email, String password){
		try{
			return userRespository.findByEmailAndPassword(email, password);
			
		} catch(NoResultException nre){
			return null;
		}
	}

	public User update(User user) throws ValidationException {
		validate(user);
		
		User userFromDB = userRespository.findBy(user.getId());
		
		userFromDB.setName(user.getName());
		userFromDB.setEmail(user.getEmail());
		userFromDB.setLat(user.getLat());
		userFromDB.setLng(user.getLng());
		userFromDB.setWeight(user.getWeight());
		
		userFromDB = em.merge(userFromDB);
		
		return userFromDB;
	}

	private void validate(User user) throws ValidationException {
		List<String> errors = new ArrayList<String>();
		
		if(StringUtils.isEmpty(user.getName())){
			errors.add("Informe o nome");
		}
		
		if(StringUtils.isEmpty(user.getEmail())){
			errors.add("Informe o e-mail");
			
		} else if(!EmailUtil.isValid(user.getEmail())){
			errors.add("O e-mail informado não é um e-mail válido.");
			
		} 

		//Validação apenas para novos usuarios
		if(user.getId() == null){
			User userWithEmail = userRespository.findOptionalByEmail(user.getEmail());
				
			if(userWithEmail != null){
				errors.add("Já existe um outro usuário cadastrado com o e-mail informado");
			}
			
		}
		
		if(errors.size() > 0){
			throw new ValidationException(errors);
		}
	}

	public User updatePicturePath(Long userId, String pictureUrl) throws ValidationException {
		User user = userRespository.findBy(userId);
		
		if(user == null){
			throw new ValidationException("Não foi possível encontrar o usuário para atualizar a foto de perfil");
		}
		
		user.setPicturePath(pictureUrl);
		
		user = userRespository.save(user);
		
		return user;
	}

	public User updateGeolocation(Long userId, String lat, String lng, String city) throws ValidationException {
		User user = userRespository.findBy(userId);
		
		if(user == null){
			throw new ValidationException("Não foi possível encontrar o usuário para atualizar a geo localização");
		}
		
		user.setLat(lat);
		user.setLng(lng);
		user.setCity(city);
		
		user = userRespository.save(user);
		
		return user;
	}
	
	public User updateRole(Long userId, String roleInitial) throws ValidationException {
		User user = userRespository.findBy(userId);
		
		if(user == null){
			throw new ValidationException("Não foi possível encontrar o usuário para atualizar a geo localização");
		}
		
		if(StringUtils.isEmpty(roleInitial)){
			throw new ValidationException("Informe a nova função do usuário");
		}
		
		RoleEnum role = RoleEnum.valueOf(roleInitial);
		
		user.setRole(role);
		
		user = userRespository.save(user);
		
		return user;
	}
	
	public List<User> searchReceivers(Long userId, String bloodType, Float distance, String urgency) {
		
		String sqlString = " select id, name, email, gender, birthdate, weight, urgency, blood_type, picture_path, hospital, reason, city " +
					" from user where id <> :userId " +
					" and id not in ( " +
					" 	select receiver_id from donation where donor_id = :userId" +
					")" ;
		
		if(urgency != null){
			sqlString += " and urgency = " + urgency + " ";
		}
		
		if(bloodType != null){
			sqlString += " and blood_type = " + bloodType + " ";
		}
		
		Query query = em.createNativeQuery(sqlString);
		query.setParameter("userId", userId);
		query.setMaxResults(12);
		
		List<Object[]> usersArray = (List<Object[]>) query.getResultList();
		
		List<User> usersList = new ArrayList<User>();

		for (Object[] item : usersArray) {
			User user = new User();
			
			user.setId(((BigInteger) item[0]).longValue());
			user.setName((String) item[1]);
			user.setEmail((String) item[2]);
			
			String genderString = (String) item[3];
			if(genderString != null){
				GenderEnum gender = GenderEnum.valueOf(genderString);
				user.setGender(gender);
			}
			
			user.setBirthdate((Date) item[4]);
			user.setWeight((Float) item[5]);

			String urgencyString = (String) item[6];
			if(urgencyString != null){
				UrgencyEnum urgencyEnum = UrgencyEnum.valueOf(urgencyString);
				user.setUrgency(urgencyEnum);
			}
			
			String bloodTypeString = (String) item[7];
			if(bloodTypeString != null){
				BloodTypeEnum bloodTypeEnum = BloodTypeEnum.valueOf(bloodTypeString);
				user.setBloodType(bloodTypeEnum);
			}
			
			user.setPicturePath((String) item[8]);
			
			user.setHospital((String) item[9]);
			user.setReason((String) item[10]);
			user.setCity((String) item[11]);
			
			usersList.add(user);
		}
		
		return usersList;
	}

	public User updateReceiverInfo(Long receiverId, String hospital, String urgency, String reason) throws ValidationException {
		User user = userRespository.findBy(receiverId);
		
		if(user == null){
			throw new ValidationException("Não foi possível encontrar o usuário para atualizar as informações");
		}

		UrgencyEnum urgencyEnum = UrgencyEnum.valueOf(urgency);
		
		user.setUrgency(urgencyEnum);
		user.setHospital(hospital);
		user.setReason(reason);
		
		userRespository.save(user);
		
		return user;
	}

}
