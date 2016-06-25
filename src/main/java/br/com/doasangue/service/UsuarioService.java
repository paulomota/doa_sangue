package br.com.doasangue.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.doasangue.model.User;

public class UsuarioService {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void cadastrar(User usuario) {
		em.persist(usuario);
	}
	
}
