package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Campaign;
import model.CampaignItem;
import model.CampaignCategory;
import model.ItemType;
import model.TemplateItem;

public interface CampaignItemDAO {
	public CampaignItem createCampaignItem(EntityManager em, CampaignItem campaignItem);
    public List<CampaignItem> readAll(EntityManager em);
    public CampaignItem readById(EntityManager em, long id);
    public List<CampaignItem> readAllByCategory(EntityManager em, CampaignCategory category);
    public boolean updateCampaignItem(EntityManager em, CampaignItem campaignItem);
    public boolean deleteCampaignItem(EntityManager em, CampaignItem campaignItem);
}
