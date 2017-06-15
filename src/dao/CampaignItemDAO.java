package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Campaign;
import model.CampaignItem;
import model.ItemType;
import model.TemplateItem;

public interface CampaignItemDAO {
	public CampaignItem createCampaignItem(EntityManager em, CampaignItem campaignItem);
    public List<CampaignItem> readAll(EntityManager em);
    public CampaignItem readById(EntityManager em, long id);
    public List<CampaignItem> readByItemType(EntityManager em, ItemType itemType);
    public List<CampaignItem> readByItemTypeAndCampaign(EntityManager em, ItemType itemType, Campaign campaign);
    public List<CampaignItem> readByCampaign(EntityManager em, Campaign campaign);
    public boolean updateCampaignItem(EntityManager em, CampaignItem campaignItem);
    public boolean deleteCampaignItem(EntityManager em, CampaignItem campaignItem);
}
