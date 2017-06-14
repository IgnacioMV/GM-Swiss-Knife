package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.TemplateNPC;

public class TemplateNPCDAOImpl implements TemplateNPCDAO {
	
	private static TemplateNPCDAOImpl instance;
	private TemplateNPCDAOImpl () {
	}
	
	public static TemplateNPCDAOImpl getInstance() {
		if (instance == null) {
			instance = new TemplateNPCDAOImpl();
		}
		return instance;
	}

	@Override
	public TemplateNPC createTemplateNPC(EntityManager em, TemplateNPC tnpc) {
		em.getTransaction().begin();
		em.persist(tnpc);
		em.getTransaction().commit();
		return tnpc;
	}

	@Override
	public List<TemplateNPC> readAll(EntityManager em) {
		Query q = em.createQuery("select c from TemplateNPC");
		List<TemplateNPC> res = q.getResultList();
		return res;
	}

	@Override
	public TemplateNPC readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from TemplateNPC c where c.id = :id");
		q.setParameter("id", id);
		TemplateNPC res = null;
		List<TemplateNPC> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (TemplateNPC) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public boolean updateTemplateNPC(EntityManager em, TemplateNPC tnpc) {
		em.getTransaction().begin();
		em.merge(tnpc);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteTemplateNPC(EntityManager em, TemplateNPC tnpc) {
		em.getTransaction().begin();
		em.remove(tnpc);
		em.getTransaction().commit();
		return true;
	}

}
