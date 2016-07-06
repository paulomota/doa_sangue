package br.com.doasangue.service;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.doasangue.exception.ValidationException;
import br.com.doasangue.model.Donation;
import br.com.doasangue.model.User;
import br.com.doasangue.repository.UserRepository;

@Transactional
@RequestScoped
public class DonationService {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private UserRepository userRepository;
	
	public Donation register(Long donorId, Long receiverId, Boolean hasMatch) throws ValidationException{
		User donor = userRepository.findBy(donorId);
		User receiver = userRepository.findBy(receiverId);
		
		if(donor == null){
			throw new ValidationException("Não foi possível identificar o usuário doador");
		}
		
		if(receiver == null){
			throw new ValidationException("Não foi possível identificar o usuário recebedor");
		}
		
		Donation donation = new Donation();
		donation.setDonor(donor);
		donation.setReceiver(receiver);
		donation.setMatched(hasMatch);
		donation.setRegisterDate(new Date());
		
		em.persist(donation);
		em.flush();
		
		return donation;
	}
}
