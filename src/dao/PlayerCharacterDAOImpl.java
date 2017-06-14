package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Campaign;
import model.PlayerCharacter;

public class PlayerCharacterDAOImpl implements PlayerCharacterDAO {
	
	private static PlayerCharacterDAOImpl instance;
	private PlayerCharacterDAOImpl () {
	}
	
	public static PlayerCharacterDAOImpl getInstance() {
		if (instance == null) {
			instance = new PlayerCharacterDAOImpl();
		}
		return instance;
	}

	@Override
	public PlayerCharacter createPlayerCharacter(EntityManager em, PlayerCharacter pc) {
		em.getTransaction().begin();
		em.persist(pc);
		em.getTransaction().commit();
		return pc;
	}

	@Override
	public List<PlayerCharacter> readAll(EntityManager em) {
		Query q = em.createQuery("select c from PlayerCharacter");
		List<PlayerCharacter> res = q.getResultList();
		return res;
	}

	@Override
	public PlayerCharacter readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from PlayerCharacter c where c.id = :id");
		q.setParameter("id", id);
		PlayerCharacter res = null;
		List<PlayerCharacter> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (PlayerCharacter) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public List<PlayerCharacter> readByCampaign(EntityManager em, Campaign campaign) {
		Query q = em.createQuery("select c from PlayerCharacter c where c.campaignId = :campaignId");
		q.setParameter("campaignId", campaign.getId());
		List<PlayerCharacter> ress = q.getResultList();
		return ress;
	}

	@Override
	public boolean updatePlayerCharacter(EntityManager em, PlayerCharacter pc) {
		em.getTransaction().begin();
		em.merge(pc);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deletePlayerCharacter(EntityManager em, PlayerCharacter pc) {
		em.getTransaction().begin();
		em.remove(pc);
		em.getTransaction().commit();
		return true;
	}

}
