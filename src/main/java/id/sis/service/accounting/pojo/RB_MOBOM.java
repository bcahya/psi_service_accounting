package id.sis.service.accounting.pojo;

import java.util.ArrayList;
import java.util.List;

public class RB_MOBOM {
	int product_id = 0;
	int bom_id = 0;
	int bom_source_id = 0;
	int warehouse_id = 0;
	boolean is_fg = false;
	String bomtype = "MF";
	List<RB_MOBOMLine> list_line = new ArrayList<RB_MOBOMLine>();
	
	public String getBomtype() {
		return bomtype;
	}
	public void setBomtype(String bomtype) {
		this.bomtype = bomtype;
	}
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
	public boolean isIs_fg() {
		return is_fg;
	}
	public void setIs_fg(boolean is_fg) {
		this.is_fg = is_fg;
	}
	public int getBom_id() {
		return bom_id;
	}
	public void setBom_id(int bom_id) {
		this.bom_id = bom_id;
	}
	public int getBom_source_id() {
		return bom_source_id;
	}
	public void setBom_source_id(int bom_source_id) {
		this.bom_source_id = bom_source_id;
	}
	public List<RB_MOBOMLine> getList_line() {
		return list_line;
	}
	public void setList_line(List<RB_MOBOMLine> list_line) {
		this.list_line = list_line;
	}
}
