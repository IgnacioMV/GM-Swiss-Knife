package model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CampaignLoc {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	private long campaignId;
	private long locationId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
	}
	public long getLocationId() {
		return locationId;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	
}
