package id.sis.service.accounting.pojo;

import java.math.BigDecimal;

public class RB_InventoryChargeDetail {
	String type;
	Integer category_seqno;
	Integer category_id;
	Integer subcategory_seqno;
	Integer subcategory_id;
	Integer line;
	Integer m_inventoryline_id;
	Integer m_product_id;
	BigDecimal qty;
	BigDecimal price;
	BigDecimal price_netto;
	BigDecimal percent;
	boolean is_dibebankan;
	boolean is_kemas;
	
	public boolean isIs_kemas() {
		return is_kemas;
	}
	public void setIs_kemas(boolean is_kemas) {
		this.is_kemas = is_kemas;
	}
	public boolean isIs_dibebankan() {
		return is_dibebankan;
	}
	public void setIs_dibebankan(boolean is_dibebankan) {
		this.is_dibebankan = is_dibebankan;
	}
	public BigDecimal getPrice_netto() {
		return price_netto;
	}
	public void setPrice_netto(BigDecimal price_netto) {
		this.price_netto = price_netto;
	}
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	public Integer getLine() {
		return line;
	}
	public void setLine(Integer line) {
		this.line = line;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getCategory_seqno() {
		return category_seqno;
	}
	public void setCategory_seqno(Integer category_seqno) {
		this.category_seqno = category_seqno;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public Integer getSubcategory_seqno() {
		return subcategory_seqno;
	}
	public void setSubcategory_seqno(Integer subcategory_seqno) {
		this.subcategory_seqno = subcategory_seqno;
	}
	public Integer getSubcategory_id() {
		return subcategory_id;
	}
	public void setSubcategory_id(Integer subcategory_id) {
		this.subcategory_id = subcategory_id;
	}
	public Integer getM_inventoryline_id() {
		return m_inventoryline_id;
	}
	public void setM_inventoryline_id(Integer m_inventoryline_id) {
		this.m_inventoryline_id = m_inventoryline_id;
	}
	public Integer getM_product_id() {
		return m_product_id;
	}
	public void setM_product_id(Integer m_product_id) {
		this.m_product_id = m_product_id;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
}
