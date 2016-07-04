package br.com.doasangue.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.deltaspike.core.util.StringUtils;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

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
			
		} else{
			User userWithEmail = userRespository.findOptionalByEmail(user.getEmail());
				
			if(userWithEmail != null){
				errors.add("Já existe um outro usuário cadastrado com o e-mail informado");
			}
			
		}
		
		if(errors.size() > 0){
			throw new ValidationException(errors);
		}
	}
	
}