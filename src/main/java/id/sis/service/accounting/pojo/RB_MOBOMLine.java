package id.sis.service.accounting.pojo;

import java.math.BigDecimal;

public class RB_MOBOMLine {
	int product_id = 0;
	int bom_ref_id = 0;
	BigDecimal qty = new BigDecimal(0);
	
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getBom_ref_id() {
		return bom_ref_id;
	}
	public void setBom_ref_id(int bom_ref_id) {
		this.bom_ref_id = bom_ref_id;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
}
