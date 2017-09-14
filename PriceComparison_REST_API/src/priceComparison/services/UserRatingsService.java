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
    	
    	// Getting date with timestamp
    	rating.setAddedTimestamp(new Date().getTime());
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = new Date();
    	
    	String dateString = dateFormat.format(date);
    	
    	rating.setAddedDate(dateString);
    	
    	String relURL = "userWineRatings?action=addUserWineRating";
    	String response = requestCreator.createPostRequest(urlPath, relURL, rating.toString());
    	
    	if(!response.equalsIgnoreCase("true")) { return false; }
    	return true;
    }
	
	public Integer getCountForWine(Integer wineId) throws IOException
	{
		String relURL = "userWineRatings?action=getCountForWine";
		String response = requestCreator.createPostRequest(urlPath, relURL, wineId.toString());
		
		if(!response.equals("") && response != null) {return Integer.parseInt(response); }
		return null;
	}
	
	public List<viewUserWinesRatings> getRatingsForUser(Integer userId) throws IOException
	{
		String relURL = "usersWinesRatingsView?action=getRatingsForUser&id=" + userId;
		String response = requestCreator.createGetRequest(urlPath, relURL);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	   	
		JsonNode responseJson = mapper.readTree(response);
		if(responseJson == null) { return null; }
		
		ArrayNode ratingsNodes = (ArrayNode) responseJson.get("Ratings");
		Iterator<JsonNode> ratingsIterator = ratingsNodes.elements();
		List<viewUserWinesRatings> resultsList = new ArrayList<viewUserWinesRatings>();
		
		while(ratingsIterator.hasNext())
		{
			JsonNode ratingNode = ratingsIterator.next();
			viewUserWinesRatings rating = mapper.treeToValue(ratingNode, viewUserWinesRatings.class);
			resultsList.add(rating);
		}
		
		return resultsList;
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
		String relUrl = "userWineRatings?action=updateUserWineRating";
		tblUserWinesRatings rating = new tblUserWinesRatings();
		
		// Set all the parameters normally, then get the object's string
		// And send it to the crud
		
		rating.setId(Integer.parseInt(ratingId));
		rating.setNumericWineId(Integer.parseInt(wineId));
		rating.setRating(Integer.parseInt(newRating));
		rating.setNumericUserId(userId);
		rating.setAddedTimestamp(new Date().getTime());
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = new Date();
    	String dateString = dateFormat.format(date);
    	rating.setAddedDate(dateString);
    	System.out.println("ALL SET 3"); //TODO DELETE
    	String contentString = rating.toString();
    	System.out.println("Being sent: " + contentString);
    	System.out.println("ALREADY OUT 4"); //TODO DELETE
    	String response = requestCreator.createPostRequest(urlPath, relUrl, contentString);
    	System.out.println("RESPONSE GOTTEN 5"); //TODO DELETE
    	if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
}
