package br.com.doasangue.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import br.com.doasangue.model.User;

@Repository(forEntity = User.class)
public interface UserRepository extends EntityRepository<User, Long>{

	User findByEmailAndPassword(String email, String password);

	User findOptionalByEmail(String email);
	
}
