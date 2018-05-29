package priceComparison.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import priceComparison.models.tblUserSavedSearches;

public class SavedSearchesService {

	private Integer userId;
	public Integer getUserId() { return userId; }
	public void setUserId(Integer userId) { this.userId = userId; }
	
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }
	
	private String searchName;
	public String getSearchName() { return searchName; }
	public void setSearchName(String searchName) { this.searchName = searchName; }

	private String relUrl;
	public String getRelUrl() { return relUrl; }
	public void setRelUrl(String relUrl) { this.relUrl = relUrl; }
	
	private String searchUrl;
	public String getSearchUrl() { return searchUrl; }
	public void setSearchUrl(String searchUrl) { this.searchUrl = searchUrl; }
	
	private tblUserSavedSearches search = new tblUserSavedSearches();
	public tblUserSavedSearches getSearch() { return search; }
	public void setSearch(tblUserSavedSearches search) { this.search = search; }
	
	RequestsCreator requestCreator = new RequestsCreator();
	
	public Boolean addSavedSearch() throws IOException
	{
		relUrl = "userSavedSearches?action=addUserSavedSearch";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = new Date();
    	
    	String dateString = dateFormat.format(date);
    	
    	search.setCreated(dateString);
    	search.setNumericUserId(userId);
		search.setUrlString(searchUrl);
		search.setName(searchName);
		
		String responseString = requestCreator.createPostRequest(urlPath, relUrl, search.toString());
		if(!responseString.equalsIgnoreCase("true")) { return false; }
		return true;
	}
	
	public List<tblUserSavedSearches> getSavedSearchesForUser() throws IOException
	{
		relUrl = "userSavedSearches?action=getUserSavedSearchesForUser";
		String responseString = requestCreator.createPostRequest(urlPath, relUrl, userId.toString());
		if(responseString == null) { return null; }
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	   	
		JsonNode responseJson = mapper.readTree(responseString);
	   	if(responseJson == null) { return null; }
	   	
	   	ArrayNode searchesNodes = (ArrayNode) responseJson.get("SavedSearches");
		if (searchesNodes != null) {
		   	Iterator<JsonNode> searchesIterator = searchesNodes.elements();
		   	List<tblUserSavedSearches> resultsList = new ArrayList<tblUserSavedSearches>();
		   	
		   	while(searchesIterator.hasNext())
		   	{
		   		JsonNode searchNode = searchesIterator.next();
		   		tblUserSavedSearches search = mapper.treeToValue(searchNode, tblUserSavedSearches.class);
		   		resultsList.add(search);
		   	}
		   	
		   	return resultsList;
		} else {
	   		return null;
	   	}
	}
	
	public Boolean deleteSavedSearch(Integer searchId) throws IOException
	{
		relUrl = "userSavedSearches?action=deleteUserSavedSearch";
		
		String responseString = requestCreator.createPostRequest(urlPath, relUrl, searchId.toString());
		
		if(!responseString.equalsIgnoreCase("true")) { return false; }
		return true;
	}
}
