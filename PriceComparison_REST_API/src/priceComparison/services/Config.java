package priceComparison.services;

import java.io.File;
import java.io.FileReader;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public class Config {

	// email parameters
	private String emailEmailSmtpHost;
	public String getEmailSmtpHost() { return this.emailEmailSmtpHost; }
	public void setEmailSmtpHost(String emailEmailSmtpHost) { this.emailEmailSmtpHost = emailEmailSmtpHost; }
	
	private String emailEmailSmtpPort;
	public String getEmailSmtpPort() { return this.emailEmailSmtpPort; }
	public void setEmailSmtpPort(String emailEmailSmtpPort) { this.emailEmailSmtpPort = emailEmailSmtpPort; }

	private String emailUsername;
	public String getEmailUsername() { return this.emailUsername; }
	public void setEmailUsername(String emailUsername) { this.emailUsername = emailUsername; }

	private String emailAddress;
	public String getEmailAddress() { return this.emailAddress; }
	public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
	
	private String emailPassword;
	public String getEmailPassword() { return this.emailPassword; }
	public void setEmailPassword(String emailPassword) { this.emailPassword = emailPassword; }
	
	private void LoadEmailConfigData() {
		
		Gson gson = new Gson();
		try {
			
			File configClassFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile());
			String filePath = configClassFile.getParentFile().getAbsolutePath() + File.separator;
			JsonReader reader = new JsonReader(new FileReader(filePath + "configemail.json"));
			ConfigEmail configEmail = gson.fromJson(reader, ConfigEmail.class);
			
			emailEmailSmtpHost = configEmail.getEmailSmtpHost();
			emailEmailSmtpPort = configEmail.getEmailSmtpPort();
			emailUsername = configEmail.getUsername();
			emailAddress = configEmail.getEmailAddress();
			emailPassword = configEmail.getEmailPassword();
		}
		catch (Exception e) {
			
		}
		
	}
	
	// Account status
	private String accountstatusNew;
	public String getAccountstatusNew() { return accountstatusNew; }
	
	private String accountstatusValidated;
	public String getAccountstatusValidated() { return accountstatusValidated; }
	
	private String accountstatusPaused;
	public String getAccountstatusPaused() { return accountstatusPaused; }
	
	private String accountstatusBlocked;
	public String getAccountstatusBlocked() { return accountstatusBlocked; }
	
	
	// User categories
	
	private String usercategoryExchoolAdmin;
	public String getUsercategoryExchoolAdmin() { return usercategoryExchoolAdmin; }
	
	private String usercategoryExchoolStaff;
	public String getUsercategoryExchoolStaff() { return usercategoryExchoolStaff; }

	private String usercategorySchoolAdmin;
	public String getUsercategorySchoolAdmin() { return usercategorySchoolAdmin; }
	
	private String usercategorySchoolStaff;
	public String getUsercategorySchoolStaff() { return usercategorySchoolStaff; }
	
	private String usercategoryParent;
	public String getUsercategoryParent() { return usercategoryParent; }
	
	private String usercategoryStudent;
	public String getUsercategoryStudent() { return usercategoryStudent; }
	
	private void LoadUsercategoriesData() {
		
		Gson gson = new Gson();
		try {
			File configClassFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile());
			String filePath = configClassFile.getParentFile().getAbsolutePath() + File.separator;
			JsonReader reader = new JsonReader(new FileReader(filePath + "configusercategories.json"));
			
		}
		catch (Exception e) {
			
		}
	}
	
	
	public Config() {

		LoadEmailConfigData();
		LoadUsercategoriesData();
		
	}
	
}
