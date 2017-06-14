package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.TemplateEq;

public class TemplateEqDAOImpl implements TemplateEqDAO {

	private static TemplateEqDAOImpl instance;
	private TemplateEqDAOImpl () {
	}
	
	public static TemplateEqDAOImpl getInstance() {
		if (instance == null) {
			instance = new TemplateEqDAOImpl();
		}
		return instance;
	}
	
	@Override
	public TemplateEq createTemplateEq(EntityManager em, TemplateEq teq) {
		em.getTransaction().begin();
		em.persist(teq);
		em.getTransaction().commit();
		return teq;
	}

	@Override
	public List<TemplateEq> readAll(EntityManager em) {
		Query q = em.createQuery("select c from TemplateEq");
		List<TemplateEq> res = q.getResultList();
		return res;
	}

	@Override
	public TemplateEq readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from TemplateEq c where c.id = :id");
		q.setParameter("id", id);
		TemplateEq res = null;
		List<TemplateEq> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (TemplateEq) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public boolean updateTemplateEq(EntityManager em, TemplateEq teq) {
		em.getTransaction().begin();
		em.merge(teq);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteTemplateEq(EntityManager em, TemplateEq teq) {
		em.getTransaction().begin();
		em.remove(teq);
		em.getTransaction().commit();
		return true;
	}

}
