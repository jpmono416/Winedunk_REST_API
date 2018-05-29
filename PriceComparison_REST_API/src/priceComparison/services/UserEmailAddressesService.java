package priceComparison.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import priceComparison.models.userEmails;

public class UserEmailAddressesService {

	private String relUrl;
	public String getRelUrl() { return relUrl; }
	public void setRelUrl(String relUrl) { this.relUrl = relUrl; }
	
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }
	
	private Integer userId;
	public Integer getUserId() { return userId; }
	public void setUserId(Integer userId) { this.userId = userId; }
	
	RequestsCreator requestCreator = new RequestsCreator();
	
	public List<userEmails> loadEmailAddresses() throws IOException
	{
		relUrl = "userEmails?action=getUserEmailAddressesForUser";
	   	String response = requestCreator.createPostRequest(urlPath, relUrl, userId.toString());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	   	
		JsonNode responseJson = mapper.readTree(response);
	   	if(responseJson == null) { return null; }
	   	
	   	ArrayNode emailsNodes = (ArrayNode) responseJson.get("EmailAddresses");
		if (emailsNodes != null) {
		   	Iterator<JsonNode> emailsIterator = emailsNodes.elements();
	   	 	List<userEmails> resultsList = new ArrayList<userEmails>();
	   	 	
		   	while(emailsIterator.hasNext())
			{
		   		JsonNode emailNode = emailsIterator.next();
		   		userEmails email = mapper.treeToValue(emailNode, userEmails.class);
		   		resultsList.add(email);
			}
		   	
		   	return resultsList;
		} else {
	   		return null;
	   	}
	}
	
	public Boolean addEmailAddress(String emailAddress) throws IOException
	{
		relUrl = "userEmails?action=addUserEmail";
		String contentString = "{ "
				+ "\"userId\" : " + userId + ", "
				+ "\"emailAddress\" : \"" + emailAddress + "\" }";
		
		String response = requestCreator.createPostRequest(urlPath, relUrl, contentString);
		
		if (response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
	
	public Boolean editEmailAddress(String emailAddress, String addressId) throws IOException
	{
		relUrl = "userEmails?action=updateUserEmail";
		String contentString = "{ "
				+ "\"id\" : " + addressId + ", "
				+ "\"userId\" : " + userId + ", "
				+ "\"emailAddress\" : \"" + emailAddress + "\" }";
		String response = requestCreator.createPostRequest(urlPath, relUrl, contentString);
		
		if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
	
	public Boolean deleteEmailAddress(String id) throws IOException
	{
		relUrl = "userEmails?action=deleteUserEmail";
		String response = requestCreator.createPostRequest(urlPath, relUrl, id);
		
		if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
}
