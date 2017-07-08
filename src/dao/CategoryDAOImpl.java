package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.CampaignCategory;

public class CategoryDAOImpl implements CategoryDAO {

	private static CategoryDAOImpl instance;
	private CategoryDAOImpl () {
	}
	
	public static CategoryDAOImpl getInstance() {
		if (instance == null) {
			instance = new CategoryDAOImpl();
		}
		return instance;
	}
	
	@Override
	public CampaignCategory createCategory(EntityManager em, CampaignCategory category) {
		em.getTransaction().begin();
		em.persist(category);
		em.getTransaction().commit();
		return category;
	}

	@Override
	public List<CampaignCategory> readAll(EntityManager em) {
		Query q = em.createQuery("select c from CampaignCategory c");
		List<CampaignCategory> res = q.getResultList();
		return res;
	}

	@Override
	public List<CampaignCategory> readByCampaign(EntityManager em, long campaignId) {
		Query q = em.createQuery("select c from CampaignCategory c where c.campaignId = :campaignId");
		q.setParameter("campaignId", campaignId);
		List<CampaignCategory> res = q.getResultList();
		return res;
	}

	@Override
	public CampaignCategory readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from CampaignCategory c where c.id = :id");
		q.setParameter("id", id);
		CampaignCategory res = null;
		List<CampaignCategory> categories = q.getResultList();
		if (categories.size() > 0) {
			res = (CampaignCategory) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public boolean updateCategory(EntityManager em, CampaignCategory category) {
		em.getTransaction().begin();
		em.merge(category);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteCategory(EntityManager em, CampaignCategory category) {
		em.getTransaction().begin();
		em.remove(category);
		em.getTransaction().commit();
		return true;
	}

}
