package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Campaign;
import model.PlayerCharacter;

public interface PlayerCharacterDAO {
	public PlayerCharacter createPlayerCharacter(EntityManager em, PlayerCharacter pc);
    public List<PlayerCharacter> readAll(EntityManager em);
    public PlayerCharacter readById(EntityManager em, long id);
    public List<PlayerCharacter> readByCampaign(EntityManager em, Campaign campaign);
    public boolean updatePlayerCharacter(EntityManager em, PlayerCharacter pc);
    public boolean deletePlayerCharacter(EntityManager em, PlayerCharacter pc);
}
