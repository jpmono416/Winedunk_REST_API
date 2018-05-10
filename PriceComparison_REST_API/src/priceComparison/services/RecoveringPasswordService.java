package priceComparison.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import priceComparison.models.tblUsers;

public class RecoveringPasswordService {

	tblUsers user;
	public tblUsers getUser() { return user; }
	public void setUser(tblUsers user) { this.user = user; }

	private String userRecoveringToken;
	public String getUserRecoveringToken() { return userRecoveringToken; }
	public void setUserRecoveringToken(String userRecoveringToken) { this.userRecoveringToken = userRecoveringToken; }

	private String userEmail;
	public String getUserEmail() { return userEmail; }
	public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
	
	private Integer userId;
	public Integer getUserId() { return userId; }
	public void setUserId(Integer userId) { this.userId = userId; }
	
	private String relUrl;
	public String getRelUrl() { return relUrl; }
	public void setRelUrl(String relUrl) { this.relUrl = relUrl; }
	
	private ServletContext servletContext;
	public ServletContext getServletContext() { return servletContext; }
	public void setServletContext(ServletContext servletContext) { this.servletContext = servletContext; }
	
	private String crudURL;
	public String getCrudURL() { return crudURL; }
	public void setCrudURL(String crudURL) { this.crudURL = crudURL; }

	private String userToken;
	public String getUserToken() { return userToken; }
	public void setUserToken(String userToken) { this.userToken = userToken; }

	
	ObjectMapper mapper = new ObjectMapper();
	RequestsCreator requestCreator = new RequestsCreator();
	GeneralService generalService = new GeneralService();
	
	public Boolean recoverPassword(ServletContext servletContext) 
	{
		setServletContext(servletContext);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try 
		{
			// Get user from the entered email
			getUserByEmail();
			userId = user.getId();
			
			// Create recovery token
			userToken = createToken();
			
			// Update on DB
			if(!updateUser()) { return false;}
			
			// loading email content from external HTML template
			String htmlContent = this.getRecoverPasswordHTMLEmailTemplate();
			
			// replacing dinamyc data
			htmlContent = htmlContent.replace("[[NAME-OF-USER]]", user.getName())
									 .replace("[[USER-TOKEN]]", userToken)
									 .replace("[[USER-ID]]", user.getId().toString());
			
			// sending "recover password" email
			EmailSender emailSender = new EmailSender();

			// emailSender.set
			emailSender.setReceiverEmailAddress(user.getLoginEmail());
			emailSender.setEmailSubject("Reset your winedunk password");

			emailSender.setEmailText(htmlContent);
			emailSender.send();
			return true;
		} catch (Exception e) { e.printStackTrace(); }
		return false;
	}
	
	private String getRecoverPasswordHTMLEmailTemplate() {

		String templateFile = servletContext.getRealPath("/WEB-INF/HTML-Email-Templates/Recover-password.html");
		Charset encoding = Charset.defaultCharset();
		byte[] encodedByteArray;
		try {
			encodedByteArray = Files.readAllBytes(Paths.get(templateFile));
			return new String(encodedByteArray, encoding);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public tblUsers getUserByEmail() throws IOException
	{
		relUrl = "users?action=getUserByEmail";
		
		try
		{
			String responseString = requestCreator.createPostRequest(crudURL, relUrl, userEmail);
			System.out.println(responseString); // TODO DELETE
			if(responseString == null) { System.out.println("Returning null"); // TODO DELETE
			
			return null; }
			

			user = mapper.readValue(responseString, tblUsers.class);
			System.out.println("User name:" + user.getName()); // TODO DELETE
			return user;
			
		} catch(Exception e) {e.printStackTrace(); System.out.println("Returning null"); // TODO DELETE
		}
		
		System.out.println("Returning null"); // TODO DELETE
		return null;
	}
	
	public tblUsers getUserById(Integer userId) throws IOException
	{
		relUrl = "users?action=getUser&id=" + userId;
		String responseString = requestCreator.createGetRequest(crudURL, relUrl);
		if(responseString == null) { return null; }

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		tblUsers user = mapper.readValue(responseString, tblUsers.class);
		return user;
	}
	
	private String createToken() throws UnsupportedEncodingException, NoSuchAlgorithmException
	{ 
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		// Create token string encrypted in byte array form
		String toBeHashed = userEmail + new Date().toString();
		byte[] hashed = md.digest(toBeHashed.getBytes());
		
		// Get actual MD5 string
		String tokenString = generalService.bytesToHex(hashed);
		return tokenString;
	}

	private Boolean updateUser() throws IOException
	{
		relUrl = "users?action=setRecoveryToken";
		try
		{
			String responseString = requestCreator.createPostRequest(crudURL, relUrl, userId.toString() + "," + userToken);
			if(responseString == null || !responseString.equalsIgnoreCase("true")) { return false; }
			
			return true;
		} catch(Exception e) { e.printStackTrace(); }
		return false;
	}
	
	public String composeEmailWithConfirmation()
	{
		String email = "<p> Dear user, <br/> "
				+ "You are receiving this email because you successfully changed your password on <a href=\"winedunk.com\">Winedunk.com</a></p>";
		
		return email;
	}
	
	public Boolean passwordsOK(String password, String repeatPassword)
	{
		// Define password requirements
		Pattern pattern;
		Matcher matcher;
		String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,20})";
		
		pattern = Pattern.compile(passwordPattern);
		matcher = pattern.matcher(password);
		
		if(!matcher.matches()) { return false; }
		if(!password.equals(repeatPassword)) { return false; }
		return true;
	}
	
	public Boolean removeTokenAndSaveUser(Integer userId, String oldToken, String newPassword) throws IOException
	{
		relUrl = "users?action=deleteTokenAndSaveUser";
		String content = userId + "," + oldToken + "," + newPassword;
		String response = requestCreator.createPostRequest(crudURL, relUrl, content);
		if(response == null) { return false; }
		if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
	
	public String encryptPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		// Create token string encrypted in byte array form
		byte[] hashed = md.digest(password.getBytes());
		
		// Get actual MD5 string
		String tokenString = generalService.bytesToHex(hashed);
		return tokenString;
	}
}
