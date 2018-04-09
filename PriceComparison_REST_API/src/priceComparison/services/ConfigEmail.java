package priceComparison.services;

public class ConfigEmail {
	
	// this class is to be used by Config.java only
	
	private String emailSmtpHost;
	public String getEmailSmtpHost() { return this.emailSmtpHost; }
	public void setEmailSmtpHost(String emailSmtpHost) { this.emailSmtpHost = emailSmtpHost; }
	
	private String emailSmtpPort;
	public String getEmailSmtpPort() { return this.emailSmtpPort; }
	public void setEmailSmtpPort(String emailSmtpPort) { this.emailSmtpPort = emailSmtpPort; }
	
	private String username;
	public String getUsername() { return this.username; }
	public void setUsername(String username) { this.username = username; }
	
	private String emailAddress;
	public String getEmailAddress() { return this.emailAddress; }
	public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
	
	private String emailPassword;
	public String getEmailPassword() { return this.emailPassword; }
	public void setEmailPassword(String emailPassword) { this.emailPassword = emailPassword; }


	public ConfigEmail() {
		
		setEmailSmtpHost("");
		setEmailSmtpPort("");
		setUsername("");
		setEmailAddress("");
		setEmailPassword("");
		
	}
}
