package id.sis.service.accounting.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "sis.id")
@Configuration("sisIdProperties")
@Data
public class SISIdProperties {
    private String companyid;
    private String locationid;
    private String plantid;
    private String customerid;
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getLocationid() {
		return locationid;
	}
	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}
	public String getPlantid() {
		return plantid;
	}
	public void setPlantid(String plantid) {
		this.plantid = plantid;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
    
    
}
