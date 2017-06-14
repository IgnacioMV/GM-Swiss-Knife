package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Campaign;
import model.CampaignEq;
import model.Equipment;

public class CampaignEqDAOImpl implements CampaignEqDAO {
	
	private static CampaignEqDAOImpl instance;
	private CampaignEqDAOImpl () {
	}
	
	public static CampaignEqDAOImpl getInstance() {
		if (instance == null) {
			instance = new CampaignEqDAOImpl();
		}
		return instance;
	}

	@Override
	public CampaignEq createCampaignEq(EntityManager em, CampaignEq ceq) {
		em.getTransaction().begin();
		em.persist(ceq);
		em.getTransaction().commit();
		return ceq;
	}

	@Override
	public List<CampaignEq> readAll(EntityManager em) {
		Query q = em.createQuery("select c from CampaignEq");
		List<CampaignEq> res = q.getResultList();
		return res;
	}

	@Override
	public CampaignEq readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from CampaignEq c where c.id = :id");
		q.setParameter("id", id);
		CampaignEq res = null;
		List<CampaignEq> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignEq) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public CampaignEq readByCampaign(EntityManager em, Campaign campaign) {
		Query q = em.createQuery("select c from CampaignEq c where c.campaignId = :campaignId");
		q.setParameter("campaignId", campaign.getId());
		CampaignEq res = null;
		List<CampaignEq> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignEq) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public CampaignEq readByEquipment(EntityManager em, Equipment equipment) {
		Query q = em.createQuery("select c from CampaignEq c where c.equipmentId = :equipmentId");
		q.setParameter("equipmentId", equipment.getId());
		CampaignEq res = null;
		List<CampaignEq> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignEq) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public CampaignEq readByCampaignAndEquipment(EntityManager em, Campaign campaign, Equipment equipment) {
		Query q = em.createQuery("select c from CampaignEq c where c.campaignId = :campaignId and c.equipmentId = :equipmentId");
		q.setParameter("campaignId", campaign.getId());
		q.setParameter("equipmentId", equipment.getId());
		CampaignEq res = null;
		List<CampaignEq> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignEq) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public boolean updateCampaignEq(EntityManager em, CampaignEq ceq) {
		em.getTransaction().begin();
		em.merge(ceq);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteCampaignEq(EntityManager em, CampaignEq ceq) {
		em.getTransaction().begin();
		em.remove(ceq);
		em.getTransaction().commit();
		return true;
	}

}
