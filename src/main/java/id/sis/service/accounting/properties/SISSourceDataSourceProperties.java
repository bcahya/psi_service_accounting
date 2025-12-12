package id.sis.service.accounting.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "source.sis.datasource")
@Data
public class SISSourceDataSourceProperties {
    private String url;
    private String username;
    private String password;
    private String maxpool;
    private String port;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMaxpool() {
		return maxpool;
	}
	public void setMaxpool(String maxpool) {
		this.maxpool = maxpool;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
    
    
}
