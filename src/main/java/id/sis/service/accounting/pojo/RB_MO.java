package id.sis.service.accounting.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RB_MO {
	int mo_id = 0;
	BigDecimal qty = new BigDecimal(0);
	int warehouse_id = 0;
	int locator_pre_id = 0;
	int locator_post_id = 0;
	int locator_stock_id = 0;
	String is_subcont = "N";
	List<RB_MORouting> list_routing = new ArrayList<>();
	List<RB_MOBOM> list_bom = new ArrayList<>();
	List<RB_MOSOH> list_soh = new ArrayList<>();
	List<RB_MOWH> list_wh = new ArrayList<>();
	List<RB_MOProduct> list_product = new ArrayList<>();
	List<RB_MOProductReplenish> list_replenish = new ArrayList<>();
	
	public String getIs_subcont() {
		return is_subcont;
	}
	public void setIs_subcont(String is_subcont) {
		this.is_subcont = is_subcont;
	}
	public List<RB_MOProductReplenish> getList_replenish() {
		return list_replenish;
	}
	public void setList_replenish(List<RB_MOProductReplenish> list_replenish) {
		this.list_replenish = list_replenish;
	}
	public int getLocator_pre_id() {
		return locator_pre_id;
	}
	public void setLocator_pre_id(int locator_pre_id) {
		this.locator_pre_id = locator_pre_id;
	}
	public int getLocator_post_id() {
		return locator_post_id;
	}
	public void setLocator_post_id(int locator_post_id) {
		this.locator_post_id = locator_post_id;
	}
	public int getLocator_stock_id() {
		return locator_stock_id;
	}
	public void setLocator_stock_id(int locator_stock_id) {
		this.locator_stock_id = locator_stock_id;
	}
	public int getMo_id() {
		return mo_id;
	}
	public void setMo_id(int mo_id) {
		this.mo_id = mo_id;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public int getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(int warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	public List<RB_MORouting> getList_routing() {
		return list_routing;
	}
	public void setList_routing(List<RB_MORouting> list_routing) {
		this.list_routing = list_routing;
	}
	public List<RB_MOBOM> getList_bom() {
		return list_bom;
	}
	public void setList_bom(List<RB_MOBOM> list_bom) {
		this.list_bom = list_bom;
	}
	public List<RB_MOSOH> getList_soh() {
		return list_soh;
	}
	public void setList_soh(List<RB_MOSOH> list_soh) {
		this.list_soh = list_soh;
	}
	public List<RB_MOWH> getList_wh() {
		return list_wh;
	}
	public void setList_wh(List<RB_MOWH> list_wh) {
		this.list_wh = list_wh;
	}
	public List<RB_MOProduct> getList_product() {
		return list_product;
	}
	public void setList_product(List<RB_MOProduct> list_product) {
		this.list_product = list_product;
	}
}
