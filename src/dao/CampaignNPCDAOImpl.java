package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Campaign;
import model.CampaignNPC;
import model.NonPlayerCharacter;

public class CampaignNPCDAOImpl implements CampaignNPCDAO {
	
	private static CampaignNPCDAOImpl instance;
	private CampaignNPCDAOImpl () {
	}
	
	public static CampaignNPCDAOImpl getInstance() {
		if (instance == null) {
			instance = new CampaignNPCDAOImpl();
		}
		return instance;
	}

	@Override
	public CampaignNPC createCampaignNPC(EntityManager em, CampaignNPC cnpc) {
		em.getTransaction().begin();
		em.persist(cnpc);
		em.getTransaction().commit();
		return cnpc;
	}

	@Override
	public List<CampaignNPC> readAll(EntityManager em) {
		Query q = em.createQuery("select c from CampaignNPC");
		List<CampaignNPC> res = q.getResultList();
		return res;
	}

	@Override
	public CampaignNPC readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from CampaignNPC c where c.id = :id");
		q.setParameter("id", id);
		CampaignNPC res = null;
		List<CampaignNPC> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignNPC) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public CampaignNPC readByCampaign(EntityManager em, Campaign campaign) {
		Query q = em.createQuery("select c from CampaignNPC c where c.campaignId = :campaignId");
		q.setParameter("campaignId", campaign.getId());
		CampaignNPC res = null;
		List<CampaignNPC> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignNPC) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public CampaignNPC readByNPC(EntityManager em, NonPlayerCharacter npc) {
		Query q = em.createQuery("select c from CampaignNPC c where c.npcId = :npcId");
		q.setParameter("npcId", npc.getId());
		CampaignNPC res = null;
		List<CampaignNPC> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignNPC) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public CampaignNPC readByCampaignAndNPC(EntityManager em, Campaign campaign, NonPlayerCharacter npc) {
		Query q = em.createQuery("select c from CampaignNPC c where c.campaignId = :campaignId and c.npcId = :npcId");
		q.setParameter("campaignId", campaign.getId());
		q.setParameter("npcId", npc.getId());
		CampaignNPC res = null;
		List<CampaignNPC> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignNPC) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public boolean updateCampaignNPC(EntityManager em, CampaignNPC cnpc) {
		em.getTransaction().begin();
		em.merge(cnpc);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteCampaignNPC(EntityManager em, CampaignNPC cnpc) {
		em.getTransaction().begin();
		em.remove(cnpc);
		em.getTransaction().commit();
		return true;
	}

}
