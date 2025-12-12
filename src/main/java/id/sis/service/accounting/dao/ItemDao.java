package id.sis.service.accounting.dao;

import lombok.Data;

@Data
public class ItemDao {
    private String ItemID;
    private String EDXID;
    private String Item_Code;
    
	public String getItemID() {
		return ItemID;
	}
	public void setItemID(String itemID) {
		ItemID = itemID;
	}
	public String getEDXID() {
		return EDXID;
	}
	public void setEDXID(String eDXID) {
		EDXID = eDXID;
	}
	public String getItem_Code() {
		return Item_Code;
	}
	public void setItem_Code(String item_Code) {
		Item_Code = item_Code;
	}
    
    
}
