package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Campaign;
import model.Location;

public interface LocationDAO {
	public Location createLocation(EntityManager em, Location location);
    public List<Location> readAll(EntityManager em);
    public Location readById(EntityManager em, long id);
    public List<Location> readByCampaign(EntityManager em, Campaign campaign);
    public boolean updateLocation(EntityManager em, Location location);
    public boolean deleteLocation(EntityManager em, Location location);
}
