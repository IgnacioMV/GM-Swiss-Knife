package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.CampaignCategory;


public interface CampaignCategoryDAO {
	public CampaignCategory createCategory(EntityManager em, CampaignCategory category);
    public List<CampaignCategory> readAll(EntityManager em);
    public List<CampaignCategory> readByCampaign(EntityManager em, long campaignId);
    public CampaignCategory readById(EntityManager em, long id);
    public boolean updateCategory(EntityManager em, CampaignCategory category);
    public boolean deleteCategory(EntityManager em, CampaignCategory category);
}
