package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Campaign;
import model.CampaignLoc;
import model.CampaignPC;
import model.NonPlayerCharacter;

public interface CampaignPCDAO {
	public CampaignPC createCampaignNPC(EntityManager em, CampaignPC cpc);
    public List<CampaignPC> readAll(EntityManager em);
    public CampaignPC readById(EntityManager em, long id);
    public CampaignPC readByCampaign(EntityManager em, Campaign campaign);
    public CampaignPC readByPC(EntityManager em, NonPlayerCharacter pc);
    public CampaignPC readByCampaignAndPC(EntityManager em, Campaign campaign, NonPlayerCharacter pc);
    public boolean updateCampaignPC(EntityManager em, CampaignPC cpc);
    public boolean deleteCampaignPC(EntityManager em, CampaignPC cpc);
}
