package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.TemplateNPC;

public interface TemplateNPCDAO {
	public TemplateNPC createTemplateNPC(EntityManager em, TemplateNPC tnpc);
    public List<TemplateNPC> readAll(EntityManager em);
    public TemplateNPC readById(EntityManager em, long id);
    public boolean updateTemplateNPC(EntityManager em, TemplateNPC tnpc);
    public boolean deleteTemplateNPC(EntityManager em, TemplateNPC tnpc);
}
