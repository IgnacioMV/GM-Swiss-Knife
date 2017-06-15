package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.ItemType;
import model.TemplateItem;


public interface TemplateItemDAO {
	public TemplateItem createTemplateItem(EntityManager em, TemplateItem titem);
    public List<TemplateItem> readAll(EntityManager em);
    public TemplateItem readById(EntityManager em, long id);
    public List<TemplateItem> readByItemType(EntityManager em, ItemType itemType);
    public boolean updateTemplateItem(EntityManager em, TemplateItem titem);
    public boolean deleteTemplateItem(EntityManager em, TemplateItem titem);
}
