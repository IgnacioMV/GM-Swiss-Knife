package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.CampaignCategory;
import model.TemplateCategory;

public class TemplateCategoryDAOImpl implements TemplateCategoryDAO {

	private static TemplateCategoryDAOImpl instance;
	private TemplateCategoryDAOImpl () {
	}
	
	public static TemplateCategoryDAOImpl getInstance() {
		if (instance == null) {
			instance = new TemplateCategoryDAOImpl();
		}
		return instance;
	}
	
	@Override
	public TemplateCategory createTemplateCategory(EntityManager em, TemplateCategory category) {
		em.getTransaction().begin();
		em.persist(category);
		em.getTransaction().commit();
		return category;
	}

	@Override
	public List<TemplateCategory> readAll(EntityManager em) {
		Query q = em.createQuery("select c from TemplateCategory c");
		List<TemplateCategory> res = q.getResultList();
		return res;
	}

	@Override
	public TemplateCategory readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from TemplateCategory c where c.id = :id");
		q.setParameter("id", id);
		TemplateCategory res = null;
		List<TemplateCategory> categories = q.getResultList();
		if (categories.size() > 0) {
			res = (TemplateCategory) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public boolean updateTemplateCategory(EntityManager em, TemplateCategory category) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTemplateCategory(EntityManager em, TemplateCategory category) {
		// TODO Auto-generated method stub
		return false;
	}

}
