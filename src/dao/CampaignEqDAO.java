package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Campaign;
import model.CampaignEq;
import model.Equipment;

public interface CampaignEqDAO {
	public CampaignEq createCampaignEq(EntityManager em, CampaignEq ceq);
    public List<CampaignEq> readAll(EntityManager em);
    public CampaignEq readById(EntityManager em, long id);
    public CampaignEq readByCampaign(EntityManager em, Campaign campaign);
    public CampaignEq readByEquipment(EntityManager em, Equipment equipment);
    public CampaignEq readByCampaignAndEquipment(EntityManager em, Campaign campaign, Equipment equipment);
    public boolean updateCampaignEq(EntityManager em, CampaignEq ceq);
    public boolean deleteCampaignEq(EntityManager em, CampaignEq ceq);
}
