package priceComparison.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import priceComparison.models.userPhoneNumbers;

public class UserPhoneNumbersService {
	
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
	
	public List<userPhoneNumbers> loadPhoneNumbers() throws IOException
	{
		relUrl = "userPhoneNumbers?action=getUserPhoneNumbersForUser";
		String response = requestCreator.createPostRequest(urlPath, relUrl, userId.toString());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
	   	JsonNode responseJson = mapper.readTree(response);
	   	if(responseJson == null) { return null; }
	   	
	   	ArrayNode phonesNodes = (ArrayNode) responseJson.get("PhoneNumbers");
		if (phonesNodes != null) {
		   	Iterator<JsonNode> phoneNumbersIterator = phonesNodes.elements();
	   	 	List<userPhoneNumbers> resultsList = new ArrayList<userPhoneNumbers>();
	   	 	
		   	 while(phoneNumbersIterator.hasNext())
			 {
				 JsonNode phoneNumberNode = phoneNumbersIterator.next();
				 userPhoneNumbers phoneNumber = mapper.treeToValue(phoneNumberNode, userPhoneNumbers.class);
				 resultsList.add(phoneNumber);
			 }
			
			 return resultsList;
		} else {
	   		return null;
	   	}
	}
	
	public Boolean addPhoneNumber(String phoneNumber) throws IOException
	{
		relUrl = "userPhoneNumbers?action=addUserPhoneNumber";
		String contentString = "{ "
				+ "\"userId\" : " + userId + ", "
				+ "\"phoneNumber\" : \"" + phoneNumber + "\" }";
		
		String response = requestCreator.createPostRequest(urlPath, relUrl, contentString);
		
		if (response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
	
	public Boolean editPhoneNumber(String phoneNumber, String numberId) throws IOException
	{
		relUrl = "userPhoneNumbers?action=updateUserPhoneNumber";
		String contentString = "{ "
				+ "\"id\" : " + numberId + ", "
				+ "\"userId\" : " + userId + ", "
				+ "\"phoneNumber\" : \"" + phoneNumber + "\" }";
		
		String response = requestCreator.createPostRequest(urlPath, relUrl, contentString);
		
		if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
	
	public Boolean deletePhoneNumber(String id) throws IOException
	{
		relUrl = "userPhoneNumbers?action=deleteUserPhoneNumber";
		String response = requestCreator.createPostRequest(urlPath, relUrl, id);
		
		if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
}
