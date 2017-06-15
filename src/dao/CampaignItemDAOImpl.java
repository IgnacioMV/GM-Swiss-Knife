package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Campaign;
import model.CampaignItem;
import model.ItemType;
import model.TemplateItem;

public class CampaignItemDAOImpl implements CampaignItemDAO {

	private static CampaignItemDAOImpl instance;
	private CampaignItemDAOImpl () {
	}
	
	public static CampaignItemDAOImpl getInstance() {
		if (instance == null) {
			instance = new CampaignItemDAOImpl();
		}
		return instance;
	}
	
	@Override
	public CampaignItem createCampaignItem(EntityManager em, CampaignItem campaignItem) {
		em.getTransaction().begin();
		em.persist(campaignItem);
		em.getTransaction().commit();
		return campaignItem;
	}

	@Override
	public List<CampaignItem> readAll(EntityManager em) {
		Query q = em.createQuery("select c from CampaignItem c");
		List<CampaignItem> res = q.getResultList();
		return res;
	}

	@Override
	public CampaignItem readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from CampaignItem c where c.id = :id");
		q.setParameter("id", id);
		CampaignItem res = null;
		List<CampaignItem> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (CampaignItem) (q.getResultList().get(0));
		}
		return res;
	}
	
	@Override
	public List<CampaignItem> readByItemType(EntityManager em, ItemType itemType) {
		Query q = em.createQuery("select c from CampaignItem c where c.itemType = :itemType");
		q.setParameter("itemType", itemType);
		TemplateItem res = null;
		List<CampaignItem> ress = q.getResultList();
		return ress;
	}
	
	@Override
	public List<CampaignItem> readByItemTypeAndCampaign(EntityManager em, ItemType itemType, Campaign campaign) {
		Query q = em.createQuery("select c from CampaignItem c where c.itemType = :itemType and c.campaignId = :campaignId");
		q.setParameter("itemType", itemType);
		q.setParameter("campaignId", campaign.getId());
		TemplateItem res = null;
		List<CampaignItem> ress = q.getResultList();
		return ress;
	}

	@Override
	public List<CampaignItem> readByCampaign(EntityManager em, Campaign campaign) {
		Query q = em.createQuery("select c from CampaignItem c where c.campaignId = :campaignId");
		q.setParameter("campaignId", campaign.getId());
		List<CampaignItem> ress = q.getResultList();
		return ress;
	}

	@Override
	public boolean updateCampaignItem(EntityManager em, CampaignItem campaignItem) {
		em.getTransaction().begin();
		em.merge(campaignItem);
		em.getTransaction().commit();
		return true;
	}

	@Override
	public boolean deleteCampaignItem(EntityManager em, CampaignItem campaignItem) {
		em.getTransaction().begin();
		em.remove(campaignItem);
		em.getTransaction().commit();
		return true;
	}

}
