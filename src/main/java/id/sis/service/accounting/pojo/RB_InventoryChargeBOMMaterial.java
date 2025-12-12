package id.sis.service.accounting.pojo;

import java.math.BigDecimal;

public class RB_InventoryChargeBOMMaterial {
	int m_product_id;
	BigDecimal qty;
	
	public int getM_product_id() {
		return m_product_id;
	}
	public void setM_product_id(int m_product_id) {
		this.m_product_id = m_product_id;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	
}
