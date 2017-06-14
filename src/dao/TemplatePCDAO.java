package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.TemplatePC;

public interface TemplatePCDAO {
	public TemplatePC createTemplatePC(EntityManager em, TemplatePC tpc);
    public List<TemplatePC> readAll(EntityManager em);
    public TemplatePC readById(EntityManager em, long id);
    public boolean updateTemplatePC(EntityManager em, TemplatePC tpc);
    public boolean deleteTemplatePC(EntityManager em, TemplatePC tpc);
}
