package id.sis.service.accounting.pojo;

import java.math.BigDecimal;

public class RB_ReqLine {
	int locator_id = 0;
	int warehouse_id = 0;
	int reqline_id = 0;
	int requisition_id;
	int product_id = 0;
	int bom_id = 0;
	int bom_source_id = 0;
	BigDecimal qty = new BigDecimal(0);
	
	public int getBom_source_id() {
		return bom_source_id;
	}
	public void setBom_source_id(int bom_source_id) {
		this.bom_source_id = bom_source_id;
	}
	public int getBom_id() {
		return bom_id;
	}
	public void setBom_id(int bom_id) {
		this.bom_id = bom_id;
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
	public int getRequisition_id() {
		return requisition_id;
	}
	public void setRequisition_id(int requisition_id) {
		this.requisition_id = requisition_id;
	}
	public int getReqline_id() {
		return reqline_id;
	}
	public void setReqline_id(int reqline_id) {
		this.reqline_id = reqline_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
}
