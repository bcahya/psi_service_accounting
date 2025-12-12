package id.sis.service.accounting.pojo;

import java.util.ArrayList;
import java.util.List;

public class RB_Req {
	int po_id;
	List<RB_ReqLine> list_line = new ArrayList<RB_ReqLine>();
	List<RB_MOSOH> list_soh = new ArrayList<RB_MOSOH>();
	List<RB_MORouting> list_routing = new ArrayList<RB_MORouting>();
	List<RB_MOBOM> list_bom = new ArrayList<RB_MOBOM>();
	List<RB_MOProduct> list_product = new ArrayList<RB_MOProduct>();
	List<RB_MOProductReplenish> list_replenish = new ArrayList<RB_MOProductReplenish>();
	
	public List<RB_MOProduct> getList_product() {
		return list_product;
	}
	public void setList_product(List<RB_MOProduct> list_product) {
		this.list_product = list_product;
	}
	public List<RB_MOProductReplenish> getList_replenish() {
		return list_replenish;
	}
	public void setList_replenish(List<RB_MOProductReplenish> list_replenish) {
		this.list_replenish = list_replenish;
	}
	public int getPo_id() {
		return po_id;
	}
	public void setPo_id(int po_id) {
		this.po_id = po_id;
	}
	public List<RB_ReqLine> getList_line() {
		return list_line;
	}
	public void setList_line(List<RB_ReqLine> list_line) {
		this.list_line = list_line;
	}
	public List<RB_MOSOH> getList_soh() {
		return list_soh;
	}
	public void setList_soh(List<RB_MOSOH> list_soh) {
		this.list_soh = list_soh;
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
}
