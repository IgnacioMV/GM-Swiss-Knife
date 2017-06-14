package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.TemplatePC;

public class TemplatePCDAOImpl implements TemplatePCDAO {
	
	private static TemplatePCDAOImpl instance;
	private TemplatePCDAOImpl () {
	}
	
	public static TemplatePCDAOImpl getInstance() {
		if (instance == null) {
			instance = new TemplatePCDAOImpl();
		}
		return instance;
	}

	@Override
	public TemplatePC createTemplatePC(EntityManager em, TemplatePC tpc) {
		em.getTransaction().begin();
		em.persist(tpc);
		em.getTransaction().commit();
		return tpc;
	}

	@Override
	public List<TemplatePC> readAll(EntityManager em) {
		Query q = em.createQuery("select c from TemplatePC");
		List<TemplatePC> res = q.getResultList();
		return res;
	}

	@Override
	public TemplatePC readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from TemplatePC c where c.id = :id");
		q.setParameter("id", id);
		TemplatePC res = null;
		List<TemplatePC> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (TemplatePC) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public boolean updateTemplatePC(EntityManager em, TemplatePC tpc) {
		em.getTransaction().begin();
		em.merge(tpc);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteTemplatePC(EntityManager em, TemplatePC tpc) {
		em.getTransaction().begin();
		em.remove(tpc);
		em.getTransaction().commit();
		return true;
	}

}
