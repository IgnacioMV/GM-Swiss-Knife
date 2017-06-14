package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Campaign;
import model.Location;

public class LocationDAOImpl implements LocationDAO {
	
	private static LocationDAOImpl instance;
	private LocationDAOImpl () {
	}
	
	public static LocationDAOImpl getInstance() {
		if (instance == null) {
			instance = new LocationDAOImpl();
		}
		return instance;
	}

	@Override
	public Location createLocation(EntityManager em, Location location) {
		em.getTransaction().begin();
		em.persist(location);
		em.getTransaction().commit();
		return location;
	}

	@Override
	public List<Location> readAll(EntityManager em) {
		Query q = em.createQuery("select c from Location");
		List<Location> res = q.getResultList();
		return res;
	}

	@Override
	public Location readById(EntityManager em, long id) {
		Query q = em.createQuery("select c from Location c where c.id = :id");
		q.setParameter("id", id);
		Location res = null;
		List<Location> ress = q.getResultList();
		if (ress.size() > 0) {
			res = (Location) (q.getResultList().get(0));
		}
		return res;
	}

	@Override
	public List<Location> readByCampaign(EntityManager em, Campaign campaign) {
		Query q = em.createQuery("select c from Location c where c.campaignId = :campaignId");
		q.setParameter("campaignId", campaign.getId());
		List<Location> ress = q.getResultList();
		return ress;
	}

	@Override
	public boolean updateLocation(EntityManager em, Location location) {
		em.getTransaction().begin();
		em.merge(location);
		em.getTransaction().commit();
		
		return true;
	}

	@Override
	public boolean deleteLocation(EntityManager em, Location location) {
		em.getTransaction().begin();
		em.remove(location);
		em.getTransaction().commit();
		return true;
	}

}
