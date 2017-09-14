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

import priceComparison.models.tblUserWineReviews;
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
		if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}

	
	public Boolean addReview(tblUserWineReviews review) throws IOException, ParseException
    {
	 	/*
	 	 * This method is for composing the JSON of a userReview object
	 	 * And sending it to the CRUD for inserting it into the DB.
	 	 * Then it checks it's done properly.
	 	 */
	 
    	if(review == null) { return false; }
    	
    	// Getting date with timestamp
    	review.setAddedTimestamp(new Date().getTime());
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = new Date();
    	
    	String dateString = dateFormat.format(date);
    	
    	review.setAddedDate(dateString);
    	
    	String relURL = "userWineReviews?action=addUserWineReview";
    	String response = requestCreator.createPostRequest(urlPath, relURL, review.toString());
    	
    	if(!response.equalsIgnoreCase("true")) { return false; }
    	return true;
    }
	
	public List<viewUsersWinesReviews> getReviewsForWine(Integer wineId) throws IOException
	{
		/*
		 * This method is for getting the list of reviews that a wine has.
		 */
		
		String relURL = "usersWinesReviewsView?action=getReviewsForWine&id=" + wineId;
		String response= requestCreator.createGetRequest(urlPath, relURL);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	   	
		JsonNode responseJson = mapper.readTree(response);
		if(responseJson == null) { return null; }
	   	
	   	ArrayNode reviewsNodes = (ArrayNode) responseJson.get("Reviews");
	   	Iterator<JsonNode> reviewsIterator = reviewsNodes.elements();
	   	List<viewUsersWinesReviews> resultsList = new ArrayList<viewUsersWinesReviews>();
	   	
	   	while(reviewsIterator.hasNext())
	   	{
	   		JsonNode reviewNode = reviewsIterator.next();
	   		viewUsersWinesReviews review = mapper.treeToValue(reviewNode, viewUsersWinesReviews.class);
	   		resultsList.add(review);
	   	}
	   	
	   	return resultsList;
	}
	
	/**
	 * @param userId is the id of the user to query for
	 * @return the list of reviews a user has made
	 * @accessed from the Profile servlet
	 */
	
	public List<viewUsersWinesReviews> getReviewsForUser(Integer userId) throws IOException
	{
		String relURL = "usersWinesReviewsView?action=getReviewsForUser&id=" + userId;
		String response= requestCreator.createGetRequest(urlPath, relURL);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	   	
		JsonNode responseJson = mapper.readTree(response);
		if(responseJson == null) { return null; }
	   	
	   	ArrayNode reviewsNodes = (ArrayNode) responseJson.get("Reviews");
	   	Iterator<JsonNode> reviewsIterator = reviewsNodes.elements();
	   	List<viewUsersWinesReviews> resultsList = new ArrayList<viewUsersWinesReviews>();
	   	
	   	while(reviewsIterator.hasNext())
	   	{
	   		JsonNode reviewNode = reviewsIterator.next();
	   		viewUsersWinesReviews review = mapper.treeToValue(reviewNode, viewUsersWinesReviews.class);
	   		resultsList.add(review);
	   	}
	   	
	   	return resultsList;
	}
	
	public Integer getCountForWine(Integer wineId) throws IOException
	{
		String relURL = "usersWinesReviewsView?action=getAmountOfReviewsForWine&id=" + wineId;
		String response = requestCreator.createGetRequest(urlPath, relURL);
		
		return Integer.parseInt(response);
	}
	
	public Boolean editReview(String reviewComment, String reviewId, String wineId) throws IOException
	{
		String relUrl = "userWineReviews?action=updateUserWineReview";
		tblUserWineReviews review = new tblUserWineReviews();
		
		
		// Set all the parameters normally, then get the object's string
		// And send it to the crud
		review.setComments(reviewComment);
		review.setId(Integer.parseInt(reviewId));
		review.setNumericWineId(Integer.parseInt(wineId));
		review.setNumericUserId(userId);
		review.setAddedTimestamp(new Date().getTime());
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = new Date();
    	String dateString = dateFormat.format(date);
    	review.setAddedDate(dateString);
    	
		String contentString = review.toString();
		String response = requestCreator.createPostRequest(urlPath, relUrl, contentString);
		
		if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
	
	public Boolean deleteReview(String reviewId) throws IOException
	{
		String relUrl = "userWineReviews?action=deleteUserWineReview";
		String response = requestCreator.createPostRequest(urlPath, relUrl, reviewId);
		
		if(response.equalsIgnoreCase("true")) { return true; }
		return false;
	}
}
