package priceComparison.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import priceComparison.models.tblUserWinesRatings;
import priceComparison.models.tblUsers;
import priceComparison.models.tblWines;
import priceComparison.models.viewUserWinesRatings;

public class UserRatingsService {

	RequestsCreator requestCreator = new RequestsCreator();
	
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }

	private Integer userId;
	public Integer getUserId() { return userId; }
	public void setUserId(Integer userId) { this.userId = userId; }
	
	
	public Boolean hasRated(Integer userId, Integer wineId) throws IOException
	{
		String relURL = "userWineRatings?action=checkUserHasRated";
		String response = requestCreator.createPostRequest(urlPath, relURL, userId + "," + wineId);
		if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
	
	public Boolean addRating(tblUserWinesRatings rating) throws IOException, ParseException
    {
	 	/*
	 	 * This method is for composing the JSON of a userReview object
	 	 * And sending it to the CRUD for inserting it into the DB.
	 	 * Then it checks it's done properly.
	 	 */
	 
    	if(rating == null) { return false; }
    	
    	String ratingJSON = "{"
    					  + "\"id\": \"null\","
    					  + "\"userId\": { \"id\" : "+rating.getUserId().getId()+"},"
    					  + "\"wineId\": { \"id\" : "+rating.getWineId().getId()+"},"
    					  + "\"rating\": " + rating.getRating()
    					  + "}";
    	
    	String relURL = "userWineRatings?action=addUserWineRating";
    	String response = requestCreator.createPostRequest(urlPath, relURL, ratingJSON);
    	
    	return (response.equalsIgnoreCase("true"));
    }
	
	public Integer getUserRatingValue(Integer wineId, Integer userId) {
		if ( (wineId != null) && (wineId > 0) && (userId != null) && (userId > 0)) {

			String relURL = "userWineRatings?action=getUserRatingValue";
			try {
				String response = requestCreator.createPostRequest(urlPath, relURL, userId + "," + wineId);

				return Integer.parseInt(response);
				
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
			
		} else {
			return 0;
		}
	}
	
	public Integer getCountForWine(Integer wineId) throws IOException
	{
		if ( (wineId != null) && (wineId > 0) ) {
			String relURL = "userWineRatings?action=getCountForWine";
			String response = requestCreator.createPostRequest(urlPath, relURL, wineId.toString());
			Integer amount = 0;
			try {
				if ( (response != null) && (!response.equals("")) ) {
					amount = Integer.parseInt(response);
				} else {
					amount = 0;
				}
			} catch (Exception e) {
				amount = 0;
			}
			return amount;	
		} else {
			return 0;
		}
		
	}
	
	public Float getAvgForWine(Integer wineId) throws IOException
	{
		if ( (wineId != null) && (wineId > 0) ) {
			String relURL = "userWineRatings?action=getAVGForWine";
			String response = requestCreator.createPostRequest(urlPath, relURL, wineId.toString());
			
			Float amount = (float) 0;
			try {
				if ( (response != null) && (!response.equals("")) ) {
					amount = Float.parseFloat(response);
				} else {
					amount = (float) 0;
				}
			} catch (Exception e) {
				amount = (float) 0;
			}
			return amount;	
		} else {
			return (float) 0;
		}
		
	}
	
	public List<viewUserWinesRatings> getRatingsForUser(Integer userId) throws IOException
	{
		String relURL = "userWineRatings?action=getRatingsForUser&userId=" + userId;
		String response = requestCreator.createGetRequest(urlPath, relURL);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	   	
		JsonNode responseJson = mapper.readTree(response);
		if(responseJson == null) { return null; }
		
		ArrayNode ratingsNodes = (ArrayNode) responseJson.get("Ratings");
	   	if (ratingsNodes != null) {
			Iterator<JsonNode> ratingsIterator = ratingsNodes.elements();
			List<viewUserWinesRatings> resultsList = new ArrayList<viewUserWinesRatings>();
			
			while(ratingsIterator.hasNext())
			{
				JsonNode ratingNode = ratingsIterator.next();
				viewUserWinesRatings rating = mapper.treeToValue(ratingNode, viewUserWinesRatings.class);
				resultsList.add(rating);
			}
			
			return resultsList;
	   	} else {
	   		return null;
	   	}
	}
	
	public Boolean deleteRating(String ratingId) throws IOException
	{
		String relUrl = "userWineRatings?action=deleteUserWineRating";
		String response = requestCreator.createPostRequest(urlPath, relUrl, ratingId);
		
		if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
	
	public Boolean editRating(String newRating, String wineId, String ratingId) throws IOException
	{

		tblWines wine = new tblWines();
		tblUsers user = new tblUsers();
		Integer wineIdInt = 0;
		try {
			wineIdInt = Integer.parseInt(wineId);
		} catch (Exception e) {
			wineIdInt = 0;
		}
		if ( (wineIdInt > 0) && (userId > 0) ) {

			wine.setId(wineIdInt);
			user.setId(userId);
			
			String relUrl = "userWineRatings?action=updateUserWineRating";
			tblUserWinesRatings rating = new tblUserWinesRatings();
			
			rating.setId(Integer.parseInt(ratingId));
			rating.setUserId(user);
			rating.setWineId(wine);
			rating.setRating(Integer.parseInt(newRating));
	    	String contentString = rating.toString();
	    	String response = requestCreator.createPostRequest(urlPath, relUrl, contentString);
	    	return (response.equalsIgnoreCase("true"));
		} else {
			return false;
		}
		
	}
}
