package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Campaign;
import model.NonPlayerCharacter;

public class NonPlayerCharacterDAOImpl implements NonPlayerCharacterDAO {
	
	private static NonPlayerCharacterDAOImpl instance;
	private NonPlayerCharacterDAOImpl () {
	}
	
	public static NonPlayerCharacterDAOImpl getInstance() {
		if (instance == null) {
			instance = new NonPlayerCharacterDAOImpl();
		}
		return instance;
	}

	@Override
	public NonPlayerCharacter createNonPlayerCharacter(EntityManager em, NonPlayerCharacter npc) {
		em.getTransaction().begin();
		em.persist(npc);
		em.getTransaction().commit();
		return npc;
	}

	@Override
	public List<NonPlayerCharacter> readAll(EntityManager em) {
		Query q = em.createQuery("select c from NonPlayerCharacter");
		List<NonPlayerCharacter> res = q.getResultList();
		return res;
	}

	@Override
	public NonPlayerCharacter readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from NonPlayerCharacter c where c.id = :id");
		q.setParameter("id", id);
		NonPlayerCharacter res = null;
		List<NonPlayerCharacter> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (NonPlayerCharacter) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public List<NonPlayerCharacter> readByCampaign(EntityManager em, Campaign campaign) {
		Query q = em.createQuery("select c from NonPlayerCharacter c where c.campaignId = :campaignId");
		q.setParameter("campaignId", campaign.getId());
		List<NonPlayerCharacter> ress = q.getResultList();
		return ress;
	}

	@Override
	public boolean updateNonPlayerCharacter(EntityManager em, NonPlayerCharacter npc) {
		em.getTransaction().begin();
		em.merge(npc);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteNonPlayerCharacter(EntityManager em, NonPlayerCharacter npc) {
		em.getTransaction().begin();
		em.remove(npc);
		em.getTransaction().commit();
		return true;
	}

}
