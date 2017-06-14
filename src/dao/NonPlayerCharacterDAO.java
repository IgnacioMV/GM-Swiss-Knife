package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Campaign;
import model.NonPlayerCharacter;

public interface NonPlayerCharacterDAO {
	public NonPlayerCharacter createNonPlayerCharacter(EntityManager em, NonPlayerCharacter npc);
    public List<NonPlayerCharacter> readAll(EntityManager em);
    public NonPlayerCharacter readById(EntityManager em, long id);
    public List<NonPlayerCharacter> readByCampaign(EntityManager em, Campaign campaign);
    public boolean updateNonPlayerCharacter(EntityManager em, NonPlayerCharacter npc);
    public boolean deleteNonPlayerCharacter(EntityManager em, NonPlayerCharacter npc);
}
