package id.sis.service.accounting.dto;

import java.util.List;

import lombok.Data;

@Data
public class SISResponseDto<T> {
    private String codestatus;
    private String message;
    private List<T> resultdata;
    
	public String getCodestatus() {
		return codestatus;
	}
	public void setCodestatus(String codestatus) {
		this.codestatus = codestatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<T> getResultdata() {
		return resultdata;
	}
	public void setResultdata(List<T> resultdata) {
		this.resultdata = resultdata;
	}
    
    
}




