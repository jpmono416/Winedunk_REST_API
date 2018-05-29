package priceComparison.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import priceComparison.models.tblUserWineReviews;
import priceComparison.models.tblUsers;
import priceComparison.models.tblWines;
import priceComparison.models.viewRecommendedWines;
import priceComparison.models.viewUsersWinesReviews;

public class UserReviewsService {  

	RequestsCreator requestCreator = new RequestsCreator();
	
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }

	private Integer userId;
	public Integer getUserId() { return userId; }
	public void setUserId(Integer userId) { this.userId = userId; }
	
	public Boolean hasReviewed(Integer userId, Integer wineId) throws IOException
	{
		String relURL = "userWineReviews?action=checkUserHasReviewed";
		String response = requestCreator.createPostRequest(urlPath, relURL, userId + "," + wineId);
		if (response != null) { return response.equalsIgnoreCase("true"); } else { return false; }
	}

	
	public Boolean addReview(tblUserWineReviews review) throws IOException, ParseException
    {
	 	/*
	 	 * This method is for composing the JSON of a userReview object
	 	 * And sending it to the CRUD for inserting it into the DB.
	 	 * Then it checks it's done properly.
	 	 */
		
    	if( (review != null) && (review.getUserId() != null) && (review.getWineId() != null) ) {
    	
    		String reviewJSON = "{"
					  + "\"id\": \"null\","
					  + "\"userId\": { \"id\" : "+review.getUserId().getId()+"},"
					  + "\"wineId\": { \"id\" : "+review.getWineId().getId()+"},"
					  + "\"title\": \""+review.getTitle()+"\","
	    			  + "\"comments\": \""+review.getComments()+"\""
					  + "}";
			
			String relURL = "userWineReviews?action=addUserWineReview";
			String response = requestCreator.createPostRequest(urlPath, relURL, reviewJSON);
			
			return (response.equalsIgnoreCase("true"));
	
    	} else {
    		return false;
    	}
    	
    }
	
	public List<tblUserWineReviews> getReviewsForWine(Integer wineId) throws IOException
	{ 
		/*
		 * This method is for getting the list of reviews that a wine has.
		 */
		
		String relURL = "userWineReviews?action=getReviewsForWine";
		String responseString = requestCreator.createPostRequest(urlPath, relURL, wineId.toString());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JsonNode responseJson = mapper.readTree(responseString);
		if(responseJson == null) { return null; }

		ArrayNode reviewsNodes = (ArrayNode) responseJson.get("Reviews");
	   	
		if (reviewsNodes != null) {
			Iterator<JsonNode> reviewsIterator = reviewsNodes.elements();

		   	List<tblUserWineReviews> resultsList = new ArrayList<tblUserWineReviews>();
			while(reviewsIterator.hasNext())
			{
		   		JsonNode reviewNode = reviewsIterator.next();
		   		
		   		tblUserWineReviews review = mapper.treeToValue(reviewNode, tblUserWineReviews.class);
		   		resultsList.add(review);
			}
		   	
			return resultsList;
		   	
	   	} else {
	   		return null;
	   	}
		
	}
	
	/**
	 * @param userId is the id of the user to query for
	 * @return the list of reviews a user has made
	 * @accessed from the Profile servlet
	 */
	
	public List<tblUserWineReviews> getReviewsForUser(Integer userId) throws IOException {
		
		String relURL = "userWineReviews?action=getReviewsForUser";
		String response= requestCreator.createPostRequest(urlPath, relURL, userId.toString()); 
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	   	
		JsonNode responseJson = mapper.readTree(response);
		if(responseJson == null) { return null; }
	   	
	   	ArrayNode reviewsNodes = (ArrayNode) responseJson.get("Reviews");
	   	if (reviewsNodes != null) {
	   		
	   		Iterator<JsonNode> reviewsIterator = reviewsNodes.elements();
		   	List<tblUserWineReviews> resultsList = new ArrayList<tblUserWineReviews>();
		   	
		   	while(reviewsIterator.hasNext())
		   	{
		   		JsonNode reviewNode = reviewsIterator.next();
		   		tblUserWineReviews review = mapper.treeToValue(reviewNode, tblUserWineReviews.class);
		   		resultsList.add(review);
		   	}
		   	
		   	return resultsList;
		   	
	   	} else {
		   	return null;
	   	}
	   	
	}
	
	public Integer getCountForWine(Integer wineId) throws IOException
	{
		try {
			String relURL = "userWineReviews?action=getAmountOfReviewsForWine&wineId=" + wineId;
			String response = requestCreator.createGetRequest(urlPath, relURL);
			
			return Integer.parseInt(response);
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public Boolean editReview(String reviewComment, String reviewId, String wineId) throws IOException
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
			
			String relUrl = "userWineReviews?action=updateUserWineReview";
			tblUserWineReviews review = new tblUserWineReviews();
			
			review.setComments(reviewComment);
			review.setId(Integer.parseInt(reviewId));
			review.setUserId(user);
			review.setWineId(wine);
			String contentString = review.toString();
			String response = requestCreator.createPostRequest(urlPath, relUrl, contentString);
			
			return (response.equalsIgnoreCase("true"));
			
		} else {
			return false;
		}
	}
	
	public Boolean deleteReview(String reviewId) throws IOException
	{
		String relUrl = "userWineReviews?action=deleteUserWineReview";
		String response = requestCreator.createPostRequest(urlPath, relUrl, reviewId);
		
		if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
}
