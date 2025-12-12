package id.sis.service.accounting.pojo;

import java.math.BigDecimal;

public class RB_MOProductReplenish {
	int product_id = 0;
	int warehouse_id = 0;
	BigDecimal min = new BigDecimal(0);
	BigDecimal max = new BigDecimal(0);
	
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
	public BigDecimal getMin() {
		return min;
	}
	public void setMin(BigDecimal min) {
		this.min = min;
	}
	public BigDecimal getMax() {
		return max;
	}
	public void setMax(BigDecimal max) {
		this.max = max;
	}
}
