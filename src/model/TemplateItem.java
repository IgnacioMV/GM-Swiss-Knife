package model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TemplateItem implements GenericItem {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	private String name;
	private String text;
	@Enumerated(EnumType.STRING)
	private ItemType itemType;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return name;
	}
	@Override
	public String getText() {
		return text;
	}
}
