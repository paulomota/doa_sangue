package br.com.doasangue.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.doasangue.dto.MatchDTO;
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

	public List<MatchDTO> findMatchedReceivers(Long donorId, Long lastMatchId) {
		List<MatchDTO> matchs = new ArrayList<MatchDTO>();
		
		String sql = "select d.id, d.receiver from Donation d where d.donor.id = :donorId and d.matched is true";
		
		if(lastMatchId != null){
			sql += " and d.id > :lastMatchId ";
		}
		
		sql += " order by d.registerDate desc";
		
		Query query = em.createQuery(sql);
		query.setMaxResults(20);
		query.setParameter("donorId", donorId);
		
		if(lastMatchId != null){
			query.setParameter("lastMatchId", lastMatchId);
		}
		
		List<Object[]> donations = (List<Object[]>) query.getResultList();
		
		for (Object[] donation : donations) {
			Long id = (Long) donation[0];
			User user = (User) donation[1];
			
			matchs.add(new MatchDTO(id, user));
		}
		
		return matchs;
	}
}
