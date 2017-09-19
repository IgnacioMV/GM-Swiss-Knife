package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.TemplateCategory;

public interface TemplateCategoryDAO {
	public TemplateCategory createTemplateCategory(EntityManager em, TemplateCategory category);
    public List<TemplateCategory> readAll(EntityManager em);
    public TemplateCategory readById(EntityManager em, long id);
    public boolean updateTemplateCategory(EntityManager em, TemplateCategory category);
    public boolean deleteTemplateCategory(EntityManager em, TemplateCategory category);
}
