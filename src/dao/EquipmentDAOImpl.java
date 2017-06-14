package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Campaign;
import model.Equipment;

public class EquipmentDAOImpl implements EquipmentDAO {
	
	private static EquipmentDAOImpl instance;
	private EquipmentDAOImpl () {
	}
	
	public static EquipmentDAOImpl getInstance() {
		if (instance == null) {
			instance = new EquipmentDAOImpl();
		}
		return instance;
	}

	@Override
	public Equipment createEquipment(EntityManager em, Equipment equipment) {
		em.getTransaction().begin();
		em.persist(equipment);
		em.getTransaction().commit();
		return equipment;
	}

	@Override
	public List<Equipment> readAll(EntityManager em) {
		Query q = em.createQuery("select c from Equipment");
		List<Equipment> res = q.getResultList();
		return res;
	}

	@Override
	public Equipment readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from Equipment c where c.id = :id");
		q.setParameter("id", id);
		Equipment res = null;
		List<Equipment> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (Equipment) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public List<Equipment> readByCampaign(EntityManager em, Campaign campaign) {
		Query q = em.createQuery("select c from Equipment c where c.campaignId = :campaignId");
		q.setParameter("campaignId", campaign.getId());
		List<Equipment> ress = q.getResultList();
		return ress;
	}

	@Override
	public boolean updateEquipment(EntityManager em, Equipment equipment) {
		em.getTransaction().begin();
		em.merge(equipment);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteEquipment(EntityManager em, Equipment equipment) {
		em.getTransaction().begin();
		em.remove(equipment);
		em.getTransaction().commit();
		return true;
	}

}
