package model;

public class DummyItem implements GenericItem {

	private long id;
	private String name;
	private ItemType itemType;
	
	public DummyItem(String name, ItemType itemType) {
		this.name = name;
		this.itemType = itemType;
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
	@Override
	public String toString() {
		return this.name;
	}
	@Override
	public String getText() {
		return this.name;
	}
	@Override
	public void setText(String text) {
		this.name = text;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return -1;
	}
}
