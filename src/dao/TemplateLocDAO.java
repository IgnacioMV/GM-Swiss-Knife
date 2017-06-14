package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.TemplateLoc;

public interface TemplateLocDAO {
	public TemplateLoc createTemplateLoc(EntityManager em, TemplateLoc tloc);
    public List<TemplateLoc> readAll(EntityManager em);
    public TemplateLoc readById(EntityManager em, long id);
    public boolean updateTemplateLoc(EntityManager em, TemplateLoc tloc);
    public boolean deleteTemplateLoc(EntityManager em, TemplateLoc tloc);
}
