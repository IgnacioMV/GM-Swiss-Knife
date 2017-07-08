package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.CampaignItem;
import model.CampaignCategory;

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
	public List<CampaignItem> readAllByCategory(EntityManager em, CampaignCategory category) {
		Query q = em.createQuery("select c from CampaignItem c where c.categoryId = :categoryId");
		q.setParameter("categoryId", category.getId());
		List<CampaignItem> res = q.getResultList();
		return res;
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
