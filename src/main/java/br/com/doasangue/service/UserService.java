package br.com.doasangue.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.doasangue.model.User;
import br.com.doasangue.repository.UserRepository;

@RequestScoped
public class UserService {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserRepository userRespository;
	
	@Transactional
	public void cadastrar(User usuario) {
		em.persist(usuario);
	}
	
	@Transactional
	public User findByEmailAndPassword(String email, String password){
		try{
			return userRespository.findByEmailAndPassword(email, password);
			
		} catch(NoResultException nre){
			return null;
		}
	}
	
}
