package id.sis.service.accounting.pojo;

public class RB_MORouting {
	int routing_id = 0;
	int routingline_id = 0;
	String action = "";
	String operation_type = "";
	int warehousefrom_id = 0;
	int locatorfrom_id = 0;
	int warehouseto_id = 0;
	int locatorto_id = 0;
	String is_subcont = "N";
	
	public String getIs_subcont() {
		return is_subcont;
	}
	public void setIs_subcont(String is_subcont) {
		this.is_subcont = is_subcont;
	}
	public String getOperation_type() {
		return operation_type;
	}
	public void setOperation_type(String operation_type) {
		this.operation_type = operation_type;
	}
	public int getRouting_id() {
		return routing_id;
	}
	public void setRouting_id(int routing_id) {
		this.routing_id = routing_id;
	}
	public int getRoutingline_id() {
		return routingline_id;
	}
	public void setRoutingline_id(int routingline_id) {
		this.routingline_id = routingline_id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getWarehousefrom_id() {
		return warehousefrom_id;
	}
	public void setWarehousefrom_id(int warehousefrom_id) {
		this.warehousefrom_id = warehousefrom_id;
	}
	public int getLocatorfrom_id() {
		return locatorfrom_id;
	}
	public void setLocatorfrom_id(int locatorfrom_id) {
		this.locatorfrom_id = locatorfrom_id;
	}
	public int getWarehouseto_id() {
		return warehouseto_id;
	}
	public void setWarehouseto_id(int warehouseto_id) {
		this.warehouseto_id = warehouseto_id;
	}
	public int getLocatorto_id() {
		return locatorto_id;
	}
	public void setLocatorto_id(int locatorto_id) {
		this.locatorto_id = locatorto_id;
	}
}
