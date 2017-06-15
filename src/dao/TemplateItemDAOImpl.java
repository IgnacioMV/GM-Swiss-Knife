package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.ItemType;
import model.TemplateItem;

public class TemplateItemDAOImpl implements TemplateItemDAO {

	private static TemplateItemDAOImpl instance;
	private TemplateItemDAOImpl () {
	}
	
	public static TemplateItemDAOImpl getInstance() {
		if (instance == null) {
			instance = new TemplateItemDAOImpl();
		}
		return instance;
	}
	
	@Override
	public TemplateItem createTemplateItem(EntityManager em, TemplateItem titem) {
		em.getTransaction().begin();
		em.persist(titem);
		em.getTransaction().commit();
		return titem;
	}

	@Override
	public List<TemplateItem> readAll(EntityManager em) {
		Query q = em.createQuery("select c from TemplateItem c");
		List<TemplateItem> res = q.getResultList();
		return res;
	}

	@Override
	public TemplateItem readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from TemplateItem c where c.id = :id");
		q.setParameter("id", id);
		TemplateItem res = null;
		List<TemplateItem> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (TemplateItem) (q.getResultList().get(0));
		}
		return res;
	}
	
	@Override
	public List<TemplateItem> readByItemType(EntityManager em, ItemType itemType) {
		Query q = em.createQuery("select c from TemplateItem c where c.itemType = :itemType");
		q.setParameter("itemType", itemType);
		TemplateItem res = null;
		List<TemplateItem> ress = q.getResultList();
		return ress;
	}
	
	@Override
	public boolean updateTemplateItem(EntityManager em, TemplateItem titem) {
		em.getTransaction().begin();
		em.merge(titem);
		em.getTransaction().commit();
		return true;
	}

	@Override
	public boolean deleteTemplateItem(EntityManager em, TemplateItem titem) {
		em.getTransaction().begin();
		em.remove(titem);
		em.getTransaction().commit();
		return true;
	}

}
