package br.com.doasangue.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.codehaus.jettison.json.JSONException;

import br.com.doasangue.dto.MatchDTO;
import br.com.doasangue.enums.RoleEnum;
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
	
	public Donation register(Long donorId, Long receiverId, Boolean hasMatch) throws ValidationException, IOException, JSONException, JAXBException{
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
		
		PushNotificationService.sendPushNotification(donor, receiver, "Você tem um novo doador");
		
		return donation;
	}

	public List<MatchDTO> findMatches(Long userId, Long lastMatchId) {
		User user = userRepository.findBy(userId);
		
		if(user.getRole() == null || user.getRole() == RoleEnum.D){
			return findMatchedDonors(userId, lastMatchId);
		} else{
			return findMatchedReceivers(userId, lastMatchId);
		}
	}
	
	public List<MatchDTO> findMatchedDonors(Long userId, Long lastMatchId) {
		List<MatchDTO> matchs = new ArrayList<MatchDTO>();

		try{
			String sql = "select d.id, d.receiver, d.registerDate from Donation d where d.donor.id = :donorId and d.matched is true";
			
			if(lastMatchId != null){
				sql += " and d.id > :lastMatchId ";
			}
			
			sql += " order by d.registerDate desc";
			
			Query query = em.createQuery(sql);
			query.setMaxResults(20);
			query.setParameter("donorId", userId);
			
			if(lastMatchId != null){
				query.setParameter("lastMatchId", lastMatchId);
			}
			
			List<Object[]> donations = (List<Object[]>) query.getResultList();
			
			for (Object[] donation : donations) {
				Long id = (Long) donation[0];
				User user = (User) donation[1];
				Date registerDate = (Date) donation[2];
				
				matchs.add(new MatchDTO(id, user, registerDate));
			}
			
			return matchs;
			
		} catch(NoResultException nre){
			return matchs;
		}
		
	}
	
	public List<MatchDTO> findMatchedReceivers(Long userId, Long lastMatchId) {
		List<MatchDTO> matchs = new ArrayList<MatchDTO>();

		try{
			String sql = "select d.id, d.donor, d.registerDate from Donation d where d.receiver.id = :receiverId and d.matched is true";
			
			if(lastMatchId != null){
				sql += " and d.id > :lastMatchId ";
			}
			
			sql += " order by d.registerDate desc";
			
			Query query = em.createQuery(sql);
			query.setMaxResults(20);
			query.setParameter("receiverId", userId);
			
			if(lastMatchId != null){
				query.setParameter("lastMatchId", lastMatchId);
			}
			
			List<Object[]> donations = (List<Object[]>) query.getResultList();
			
			for (Object[] donation : donations) {
				Long id = (Long) donation[0];
				User user = (User) donation[1];
				Date registerDate = (Date) donation[2];
				
				matchs.add(new MatchDTO(id, user, registerDate));
			}
			
			return matchs;
			
		} catch(NoResultException nre){
			return matchs;
		}
		
	}
}
