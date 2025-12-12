package id.sis.service.accounting.pojo;

import java.math.BigDecimal;

public class RB_MOSOH {
	int product_id = 0;
	int warehouse_id = 0;
	int locator_id = 0;
	BigDecimal qty = new BigDecimal(0);
	
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(int warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	public int getLocator_id() {
		return locator_id;
	}
	public void setLocator_id(int locator_id) {
		this.locator_id = locator_id;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
}
