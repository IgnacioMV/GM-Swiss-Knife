package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.TemplateEq;

public interface TemplateEqDAO {
	public TemplateEq createTemplateEq(EntityManager em, TemplateEq teq);
    public List<TemplateEq> readAll(EntityManager em);
    public TemplateEq readById(EntityManager em, long id);
    public boolean updateTemplateEq(EntityManager em, TemplateEq teq);
    public boolean deleteTemplateEq(EntityManager em, TemplateEq teq);
}
