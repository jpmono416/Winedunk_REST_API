package priceComparison.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import priceComparison.models.tblUserFavouriteWines;

public class FavouriteWinesService {
	public FavouriteWinesService() {}
	
	private String relUrl;
	public String getRelUrl() { return relUrl; }
 	public void setRelUrl(String relUrl) { this.relUrl = relUrl; }
	
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }

	JsonChecker jsonChecker = new JsonChecker();
	RequestsCreator requestCreator = new RequestsCreator();
	
	/*
	 * This class manages all the actions that need to be taken with the favourite wines.
	 * These methods will be invoked from the Product servlet and will make requests to the CRUD API. 
	 */
	
	public Boolean addFavouriteWine(Integer userId, Integer wineId) throws IOException
	{
		if ( (userId != null) && (userId > 0) && (wineId != null) && (wineId > 0)) {
			
			relUrl = "userFavouriteWines?action=addUserFavouriteWine";

			String dateString = new SimpleDateFormat("yyyy-MM-dd").format((new Date()));
			String content = "{"
					+ " \"userId\" : { \"id\" : "+ userId +" },"
					+ " \"wineId\" : { \"id\" : "+ wineId +" },"
					+ " \"addedDate\" : \"" + dateString + "\", "
					+ " \"addedTimestamp\" : \"" + new Date().getTime()
					+ "\" }";
			
			String response = requestCreator.createPostRequest(urlPath, relUrl, content);
			
			return (response != null) && (response.equals("True"));
			
		} else {
			return false;
		}
		
	}
	
	public Boolean deleteFavouriteWine(Integer userId, Integer wineId) throws IOException
	{
		/*
		 * Get the favourite wine to be deleted based on wineId and userId,
		 * Then we have the ID so we can delete it using another request
		 * To the CRUD 
		 */
		relUrl = "userFavouriteWines?action=getFavouriteWineForUser";
		String content = userId + "," + wineId;
		
		String response = requestCreator.createPostRequest(urlPath, relUrl, content);
		if(response == null) { return false; }
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
	   	JsonNode responseJson = mapper.readTree(response);
	   	if(responseJson == null) { return false; }
	   	
	   	tblUserFavouriteWines wine = mapper.treeToValue(responseJson, tblUserFavouriteWines.class);
	   	if(wine == null) { return false; }
	   	
	   	Integer wineToBeDeletedId = wine.getId();
	   	
	   	relUrl = "userFavouriteWines?action=deleteUserFavouriteWine";
	   	String deleteResponse = requestCreator.createPostRequest(urlPath, relUrl, wineToBeDeletedId.toString());
	   	
	   	if(deleteResponse == null) { return false; }
	   	return true;
	}
	
	public Boolean isWineFlaggedAsFavouriteWineForUser(Integer userId, Integer wineId) {
		
		relUrl = "userFavouriteWines?action=isWineFlaggedAsFavouriteWineForUser";
		String content = userId + "," + wineId;
		
		try {
		
			String response = requestCreator.createPostRequest(urlPath, relUrl, content);
			return (response.equalsIgnoreCase("true"));
			
		} catch (Exception e) {
			return false;
		}
	}
	public Integer getCountForWine(Integer wineId) {
		try {
			String relURL = "userFavouriteWines?action=getAmountOfForWine";
			String response = requestCreator.createPostRequest(urlPath, relURL, wineId.toString());
			return Integer.parseInt(response);
		} catch (Exception e) {
			return 0;
		}
		
	}
}
