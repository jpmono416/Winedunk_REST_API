package priceComparison.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	private String crudURL;
	public String getCrudURL() { return crudURL; }
	public void setCrudURL(String crudURL) { this.crudURL = crudURL; }

	ObjectMapper mapper = new ObjectMapper();
	
	RequestsCreator requestCreator = new RequestsCreator();
	GeneralService generalService = new GeneralService();
	
	public Boolean recoverPassword() 
	{
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try 
		{
			// Get user from the entered email
			getUserByEmail();
			System.out.println("User:" + user.toString());
			userId = user.getId();
			
			// Create recovery token
			String userToken = createToken();
			
			// Set the token on the user
			user.setRecoveringPassToken(userToken);
			
			// Update on DB
			if(!updateUser()) { return false;}
			
			// Build and send email
			String composedEmail = composeEmailWithToken();
			generalService.sendEmail(composedEmail, user.getLoginEmail());
			return true;
		} catch (Exception e) { e.printStackTrace(); }
		return false;
	}
	
	public tblUsers getUserByEmail() throws IOException
	{
		relUrl = "users?action=getUserByEmail";
		
		try
		{
			String responseString = requestCreator.createPostRequest(crudURL, relUrl, userEmail);
			if(responseString == null) { return null; }

			user = mapper.readValue(responseString, tblUsers.class);
			return user;
			
		} catch(Exception e) { e.printStackTrace(); }
		
		return null;
	}
	
	public tblUsers getUserById(Integer userId) throws IOException
	{
		relUrl = "users?action=getUser&id=" + userId;
		String responseString = requestCreator.createGetRequest(crudURL, relUrl);
		if(responseString == null) { return null; }

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
		relUrl = "users?action=updateUser";
		try
		{
			String responseString = requestCreator.createPostRequest(crudURL, relUrl, user.toString());
			if(responseString == null || !responseString.equalsIgnoreCase("true")) { return false; }
			
			return true;
		} catch(Exception e) { e.printStackTrace(); }
		return false;
	}
	
	private String composeEmailWithToken()
	{
		String email = "<p> Dear " + user.getName() + ", <br/> "
				+ "You are receiving this email because you marked to recover your password on <a href=\"http://winedunk.com\">Winedunk</a><br/>"
				+ "Please click <a href=\"http://localhost:8080/PriceComparison_REST_API/Recover?token=" + user.getRecoveringPassToken() + "&uid=" + user.getId() + "\">here</a> to recover your password</p>";
		
		return email;
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
	
	public Boolean removeTokenAndSaveUser(tblUsers user) throws IOException
	{
		relUrl = "users?action=deleteTokenAndSaveUser";
		String response = requestCreator.createPostRequest(crudURL, relUrl, user.toString());
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
