package dao;

import java.util.List;
import model.Campaign;
import javax.persistence.EntityManager;

public interface CampaignDAO {

	public Campaign createCampaign(EntityManager em, Campaign campaign);
    public List<Campaign> readAll(EntityManager em);
    public Campaign readById(EntityManager em, long id);
    public boolean updateCampaign(EntityManager em, Campaign campaign);
    public boolean deleteCampaign(EntityManager em, Campaign campaign);
	
}
