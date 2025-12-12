package id.sis.service.accounting.pojo;

import java.util.List;

public class RB_InventoryCharge {
	Integer m_inventory_id;
	List<RB_InventoryChargeDetail> list_detail;
	List<RB_InventoryChargeBOM> list_bom;
	
	public Integer getM_inventory_id() {
		return m_inventory_id;
	}
	public void setM_inventory_id(Integer m_inventory_id) {
		this.m_inventory_id = m_inventory_id;
	}
	public List<RB_InventoryChargeDetail> getList_detail() {
		return list_detail;
	}
	public void setList_detail(List<RB_InventoryChargeDetail> list_detail) {
		this.list_detail = list_detail;
	}
	public List<RB_InventoryChargeBOM> getList_bom() {
		return list_bom;
	}
	public void setList_bom(List<RB_InventoryChargeBOM> list_bom) {
		this.list_bom = list_bom;
	}
	
}
