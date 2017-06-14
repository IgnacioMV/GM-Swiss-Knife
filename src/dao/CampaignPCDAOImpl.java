package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Campaign;
import model.CampaignPC;
import model.NonPlayerCharacter;

public class CampaignPCDAOImpl implements CampaignPCDAO {
	
	private static CampaignPCDAOImpl instance;
	private CampaignPCDAOImpl () {
	}
	
	public static CampaignPCDAOImpl getInstance() {
		if (instance == null) {
			instance = new CampaignPCDAOImpl();
		}
		return instance;
	}

	@Override
	public CampaignPC createCampaignNPC(EntityManager em, CampaignPC cpc) {
		em.getTransaction().begin();
		em.persist(cpc);
		em.getTransaction().commit();
		return cpc;
	}

	@Override
	public List<CampaignPC> readAll(EntityManager em) {
		Query q = em.createQuery("select c from CampaignPC");
		List<CampaignPC> res = q.getResultList();
		return res;
	}

	@Override
	public CampaignPC readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from CampaignPC c where c.id = :id");
		q.setParameter("id", id);
		CampaignPC res = null;
		List<CampaignPC> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignPC) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public CampaignPC readByCampaign(EntityManager em, Campaign campaign) {
		Query q = em.createQuery("select c from CampaignPC c where c.campaignId = :campaignId");
		q.setParameter("campaignId", campaign.getId());
		CampaignPC res = null;
		List<CampaignPC> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignPC) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public CampaignPC readByPC(EntityManager em, NonPlayerCharacter pc) {
		Query q = em.createQuery("select c from CampaignPC c where c.pcId = :pcId");
		q.setParameter("pcId", pc.getId());
		CampaignPC res = null;
		List<CampaignPC> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignPC) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public CampaignPC readByCampaignAndPC(EntityManager em, Campaign campaign, NonPlayerCharacter pc) {
		Query q = em.createQuery("select c from CampaignPC c where c.campaignId = :campaignId and c.pcId = :pcId");
		q.setParameter("campaignId", campaign.getId());
		q.setParameter("pcId", pc.getId());
		CampaignPC res = null;
		List<CampaignPC> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignPC) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public boolean updateCampaignPC(EntityManager em, CampaignPC cpc) {
		em.getTransaction().begin();
		em.merge(cpc);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteCampaignPC(EntityManager em, CampaignPC cpc) {
		em.getTransaction().begin();
		em.remove(cpc);
		em.getTransaction().commit();
		return true;
	}

}
