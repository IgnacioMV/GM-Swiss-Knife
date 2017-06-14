package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.TemplateLoc;

public class TemplateLocDAOImpl implements TemplateLocDAO {

	private static TemplateLocDAOImpl instance;
	private TemplateLocDAOImpl () {
	}
	
	public static TemplateLocDAOImpl getInstance() {
		if (instance == null) {
			instance = new TemplateLocDAOImpl();
		}
		return instance;
	}
	
	@Override
	public TemplateLoc createTemplateLoc(EntityManager em, TemplateLoc tloc) {
		em.getTransaction().begin();
		em.persist(tloc);
		em.getTransaction().commit();
		return tloc;
	}

	@Override
	public List<TemplateLoc> readAll(EntityManager em) {
		Query q = em.createQuery("select c from TemplateLoc");
		List<TemplateLoc> res = q.getResultList();
		return res;
	}

	@Override
	public TemplateLoc readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from TemplateLoc c where c.id = :id");
		q.setParameter("id", id);
		TemplateLoc res = null;
		List<TemplateLoc> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (TemplateLoc) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public boolean updateTemplateLoc(EntityManager em, TemplateLoc tloc) {
		em.getTransaction().begin();
		em.merge(tloc);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteTemplateLoc(EntityManager em, TemplateLoc tloc) {
		em.getTransaction().begin();
		em.remove(tloc);
		em.getTransaction().commit();
		return true;
	}

}
