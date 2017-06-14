package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Campaign;
import model.CampaignNPC;
import model.NonPlayerCharacter;

public interface CampaignNPCDAO {
	public CampaignNPC createCampaignNPC(EntityManager em, CampaignNPC cnpc);
    public List<CampaignNPC> readAll(EntityManager em);
    public CampaignNPC readById(EntityManager em, long id);
    public CampaignNPC readByCampaign(EntityManager em, Campaign campaign);
    public CampaignNPC readByNPC(EntityManager em, NonPlayerCharacter npc);
    public CampaignNPC readByCampaignAndNPC(EntityManager em, Campaign campaign, NonPlayerCharacter npc);
    public boolean updateCampaignNPC(EntityManager em, CampaignNPC cnpc);
    public boolean deleteCampaignNPC(EntityManager em, CampaignNPC cnpc);
}
