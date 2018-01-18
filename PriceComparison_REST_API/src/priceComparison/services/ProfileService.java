package priceComparison.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import priceComparison.models.tblUserFavouriteWines;
import priceComparison.models.tblUserSavedSearches;
import priceComparison.models.tblUsers;
import priceComparison.models.viewWines;

public class ProfileService {
	
	private String relUrl;
	public String getRelUrl() { return relUrl; }
	public void setRelUrl(String relUrl) { this.relUrl = relUrl; }
	
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }
	
	private String userString;
	public String getUserString() { return userString; }
	public void setUserString(String userString) { this.userString = userString; }
	
	public RequestsCreator getRequestCreator() { return requestCreator; }
	public void setRequestCreator(RequestsCreator requestCreator) { this.requestCreator = requestCreator; }

	private Integer userId;
	public Integer getUserId() { return userId; }
	public void setUserId(Integer userId) { this.userId = userId; }
	
	private String newPassword;
	public String getNewPassword() { return newPassword; }
	public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
	
	private String previousPassword;
	public String getPreviousPassword() { return previousPassword; }
	public void setPreviousPassword(String previousPassword) { this.previousPassword = previousPassword; }
	
	public ProfileService() {}
	RequestsCreator requestCreator = new RequestsCreator();
	GeneralService generalService = new GeneralService();
	
	public tblUsers loadUserDetails() throws IOException 
	{ 
		relUrl = "users?action=getUser&id=" + userId;
		
		String response = requestCreator.createGetRequest(urlPath, relUrl);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
	   	JsonNode responseJson = mapper.readTree(response);
	   	if(responseJson == null) { return null; }
	   	
	   	tblUsers user = mapper.treeToValue(responseJson, tblUsers.class);
	   	return user;
	}
	
	public List<viewWines> loadFavouriteWines() throws IOException
	{
		try
		{
			relUrl = "userFavouriteWines?action=getFavouriteWinesForUser";
			String response = requestCreator.createPostRequest(urlPath, relUrl, userId.toString());
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
		   	JsonNode responseJson = mapper.readTree(response);
		   	if(responseJson == null) { return null; }
		   	
		   	ArrayNode favouriteNode = (ArrayNode) responseJson.get("FavouriteWines");
		   	Iterator<JsonNode> favouriteWinesIterator = favouriteNode.elements();
		   	
		   	/*
		   	 * Create and populate the list of favouriteWines. These act like a pointer to the
		   	 * Actual wine. They contain the ID, which I will use to then get the wines
		   	 */
		   	List<tblUserFavouriteWines> resultsList = new ArrayList<tblUserFavouriteWines>();
		   	
		   	while(favouriteWinesIterator.hasNext())
		   	{
		   		JsonNode favWineNode = favouriteWinesIterator.next();
		   		tblUserFavouriteWines wine = mapper.treeToValue(favWineNode, tblUserFavouriteWines.class);
		   		resultsList.add(wine);
		   	}
		   	
		   	/*
		   	 * Create the list of wines that will be returned and stored to request
		   	 * These have the actual information about the wine that will be shown
		   	 * On screen. Then iterate over the favouriteWines to get each wine
		   	 * And populate the actual list that will be used 
		   	 */
		   	
		   	List<viewWines> actualWines = new ArrayList<viewWines>();
		   	
		   	for(tblUserFavouriteWines favWine : resultsList)
		   	{
		   		relUrl = "winesView?action=getWine&id=";
		   		String fullRelUrl = relUrl += favWine.getNumericWineId();
		   		String actualWineString = requestCreator.createGetRequest(urlPath, fullRelUrl);
		   		
		   		viewWines actualWine = mapper.readValue(actualWineString, viewWines.class);
		   		actualWines.add(actualWine);
		   	}
		   	
		   	return actualWines;
		} catch(Exception e) { e.printStackTrace(); return null; }
	}
	
	public void getUserById(Integer id) 
	{
		try 
		{
			relUrl = "?action=getUserById&id=";
			this.setUserString(requestCreator.createGetRequest(urlPath, relUrl));
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public Boolean changePassword() throws IOException
	{	
		
		try { if(!checkAndEncryptPasswords()) { return false; } } 
		catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
		
		relUrl = "users?action=getAuth&id=" + userId;
		String storedPassword = requestCreator.createGetRequest(urlPath, relUrl);
		
		if(!storedPassword.equals(previousPassword)) { return false; }
		
		relUrl = "users?action=changePassword";
		String content = userId + "," + newPassword;
		String response = requestCreator.createPostRequest(urlPath, relUrl, content);
		
		if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
	
	public Boolean checkAndEncryptPasswords() throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		if(previousPassword.equals(newPassword)) { return false; }
		
		// Define password requirements and check the format
		Pattern pattern;
		Matcher matcher;
		String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,20})";
		
		pattern = Pattern.compile(passwordPattern);
		matcher = pattern.matcher(newPassword);
		
		if(!matcher.matches()) { return false; }
		
		//Converting to bytes and encrypting password
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] bytesPassword;
		
		bytesPassword = newPassword.getBytes("UTF-8");
		byte[] hashed = md.digest(bytesPassword);
		
		//Get MD5 string
		newPassword = generalService.bytesToHex(hashed);
		
		//Repeat to get previous password encrypted too
		bytesPassword = previousPassword.getBytes("UTF-8");
		hashed = md.digest(bytesPassword);
		previousPassword = generalService.bytesToHex(hashed);
		
		return true;
	}
	
	public Boolean validateEmail(String email) throws AddressException
	{
		// Declare email address for validation
		InternetAddress emailAddress = new InternetAddress(email);
		
		try { emailAddress.validate(); return true; } 
		catch (AddressException e) { e.printStackTrace(); }
		return false;
	}
	
	public Boolean updateUser(tblUsers user) throws IOException
	{
		relUrl = "users?action=updateUserDetails";
		String content = user.toString();
		String responseString = requestCreator.createPostRequest(urlPath, relUrl, content);
		if(!responseString.equalsIgnoreCase("true")) { return false; }
		return true;
	}
}