package model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CampaignItem implements GenericItem {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	private String name;
	private String text;
	@Enumerated(EnumType.STRING)
	private ItemType itemType;
	private long tEquipmentId;
	private long campaignId;
	
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
	public long gettEquipmentId() {
		return tEquipmentId;
	}
	public void settEquipmentId(long tEquipmentId) {
		this.tEquipmentId = tEquipmentId;
	}
	public long getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
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
