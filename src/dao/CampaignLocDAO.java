package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Campaign;
import model.CampaignLoc;
import model.Location;

public interface CampaignLocDAO {
	public CampaignLoc createCampaignLoc(EntityManager em, CampaignLoc cel);
    public List<CampaignLoc> readAll(EntityManager em);
    public CampaignLoc readById(EntityManager em, long id);
    public CampaignLoc readByCampaign(EntityManager em, Campaign campaign);
    public CampaignLoc readByLocation(EntityManager em, Location location);
    public CampaignLoc readByCampaignAndLocation(EntityManager em, Campaign campaign, Location location);
    public boolean updateCampaignLoc(EntityManager em, CampaignLoc cel);
    public boolean deleteCampaignLoc(EntityManager em, CampaignLoc cel);
}
