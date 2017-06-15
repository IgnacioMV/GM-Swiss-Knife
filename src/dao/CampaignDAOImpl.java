package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Campaign;

public class CampaignDAOImpl implements CampaignDAO {
	
	private static CampaignDAOImpl instance;
	private CampaignDAOImpl () {
	}
	
	public static CampaignDAOImpl getInstance() {
		if (instance == null) {
			instance = new CampaignDAOImpl();
		}
		return instance;
	}
	
	@Override
	public Campaign createCampaign(EntityManager em, Campaign campaign) {
		em.getTransaction().begin();
		em.persist(campaign);
		em.getTransaction().commit();
		return campaign;
	}
	
	@Override
	public List<Campaign> readAll(EntityManager em) {
		Query q = em.createQuery("select c from Campaign c");
		List<Campaign> res = q.getResultList();
		return res;
	}

	@Override
	public Campaign readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from Campaign c where c.id = :id");
		q.setParameter("id", id);
		Campaign res = null;
		List<Campaign> campaigns = q.getResultList();
		if (campaigns.size() > 0) {
			res = (Campaign) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public boolean updateCampaign(EntityManager em, Campaign campaign) {
		em.getTransaction().begin();
		em.merge(campaign);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteCampaign(EntityManager em, Campaign campaign) {
		em.getTransaction().begin();
		em.remove(campaign);
		em.getTransaction().commit();
		return true;
	}
	
	
	
}
