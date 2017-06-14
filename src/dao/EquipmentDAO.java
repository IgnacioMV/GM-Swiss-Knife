package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Campaign;
import model.Equipment;

public interface EquipmentDAO {
	public Equipment createEquipment(EntityManager em, Equipment equipment);
    public List<Equipment> readAll(EntityManager em);
    public Equipment readById(EntityManager em, long id);
    public List<Equipment> readByCampaign(EntityManager em, Campaign campaign);
    public boolean updateEquipment(EntityManager em, Equipment equipment);
    public boolean deleteEquipment(EntityManager em, Equipment equipment);
}
