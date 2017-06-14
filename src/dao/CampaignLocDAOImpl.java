package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Campaign;
import model.CampaignLoc;
import model.Location;

public class CampaignLocDAOImpl implements CampaignLocDAO {
	
	private static CampaignLocDAOImpl instance;
	private CampaignLocDAOImpl () {
	}
	
	public static CampaignLocDAOImpl getInstance() {
		if (instance == null) {
			instance = new CampaignLocDAOImpl();
		}
		return instance;
	}

	@Override
	public CampaignLoc createCampaignLoc(EntityManager em, CampaignLoc cel) {
		em.getTransaction().begin();
		em.persist(cel);
		em.getTransaction().commit();
		return cel;
	}

	@Override
	public List<CampaignLoc> readAll(EntityManager em) {
		Query q = em.createQuery("select c from CampaignLoc");
		List<CampaignLoc> res = q.getResultList();
		return res;
	}

	@Override
	public CampaignLoc readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from CampaignLoc c where c.id = :id");
		q.setParameter("id", id);
		CampaignLoc res = null;
		List<CampaignLoc> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignLoc) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public CampaignLoc readByCampaign(EntityManager em, Campaign campaign) {
		Query q = em.createQuery("select c from CampaignLoc c where c.campaignId = :campaignId");
		q.setParameter("campaignId", campaign.getId());
		CampaignLoc res = null;
		List<CampaignLoc> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignLoc) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public CampaignLoc readByLocation(EntityManager em, Location location) {
		Query q = em.createQuery("select c from CampaignLoc c where c.locationId = :locationId");
		q.setParameter("locationId", location.getId());
		CampaignLoc res = null;
		List<CampaignLoc> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignLoc) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public CampaignLoc readByCampaignAndLocation(EntityManager em, Campaign campaign, Location location) {
		Query q = em.createQuery("select c from CampaignLoc c where c.campaignId = :campaignId and c.locationId = :locationId");
		q.setParameter("campaignId", campaign.getId());
		q.setParameter("locationId", location.getId());
		CampaignLoc res = null;
		List<CampaignLoc> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignLoc) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public boolean updateCampaignLoc(EntityManager em, CampaignLoc cel) {
		em.getTransaction().begin();
		em.merge(cel);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteCampaignLoc(EntityManager em, CampaignLoc cel) {
		em.getTransaction().begin();
		em.remove(cel);
		em.getTransaction().commit();
		return true;
	}

}
