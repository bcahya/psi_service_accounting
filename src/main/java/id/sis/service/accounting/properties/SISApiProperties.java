package id.sis.service.accounting.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "sis.api")
@Configuration("SISApiProperties")
@Data
public class SISApiProperties {
    String directory;
    Integer ad_client_id;
    Integer ad_org_id;
    Integer c_currency_id;
    Integer ad_user_id;
    Integer c_charge_id;
    Integer c_doctype_id;
    String dir_cimb;

	public String getDir_cimb() {
		return dir_cimb;
	}

	public void setDir_cimb(String dir_cimb) {
		this.dir_cimb = dir_cimb;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public Integer getAd_client_id() {
		return ad_client_id;
	}

	public void setAd_client_id(Integer ad_client_id) {
		this.ad_client_id = ad_client_id;
	}

	public Integer getAd_org_id() {
		return ad_org_id;
	}

	public void setAd_org_id(Integer ad_org_id) {
		this.ad_org_id = ad_org_id;
	}

	public Integer getC_currency_id() {
		return c_currency_id;
	}

	public void setC_currency_id(Integer c_currency_id) {
		this.c_currency_id = c_currency_id;
	}

	public Integer getAd_user_id() {
		return ad_user_id;
	}

	public void setAd_user_id(Integer ad_user_id) {
		this.ad_user_id = ad_user_id;
	}

	public Integer getC_charge_id() {
		return c_charge_id;
	}

	public void setC_charge_id(Integer c_charge_id) {
		this.c_charge_id = c_charge_id;
	}

	public Integer getC_doctype_id() {
		return c_doctype_id;
	}

	public void setC_doctype_id(Integer c_doctype_id) {
		this.c_doctype_id = c_doctype_id;
	}

}
