package id.sis.service.accounting.response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SISResponse {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	String status;
	String time;
	String message;
	List<Map<String, Object>> listdata = new ArrayList<Map<String,Object>>();
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTime() {
		if (time == null) {
			time = sdf.format(new Date());
		}
		return time;
	}
	public void setTime(Date time) {
		if (time == null) {
			time = new Date();
		}
		this.time = sdf.format(time);
	}
	public String getMessage() {
		if (message == null) {
			message = "";
		}
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Map<String, Object>> getListdata() {
		return listdata;
	}
	public void setListdata(List<Map<String, Object>> listdata) {
		this.listdata = listdata;
	}
	
	public static SISResponse successResponse(List<Map<String, Object>> listResult) {
		SISResponse response = new SISResponse();
		response.setStatus("S");
		response.setTime(new Date());
		response.setMessage(listResult.size()+" data found!");
		response.setListdata(listResult);
		return response;
	}
	
	public static SISResponse errorResponse(String errorMessage) {
		SISResponse response = new SISResponse();
		response.setStatus("E");
		response.setTime(new Date());
		response.setMessage(errorMessage);
		response.setListdata(new ArrayList<Map<String,Object>>());
		return response;
	}
}
