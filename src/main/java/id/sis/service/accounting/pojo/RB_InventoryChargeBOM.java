package id.sis.service.accounting.pojo;

import java.util.List;

public class RB_InventoryChargeBOM {
	int product_kemas_id;
	List<RB_InventoryChargeBOMMaterial> list_material;
	
	public int getProduct_kemas_id() {
		return product_kemas_id;
	}
	public void setProduct_kemas_id(int product_kemas_id) {
		this.product_kemas_id = product_kemas_id;
	}
	public List<RB_InventoryChargeBOMMaterial> getList_material() {
		return list_material;
	}
	public void setList_material(List<RB_InventoryChargeBOMMaterial> list_material) {
		this.list_material = list_material;
	}
}
