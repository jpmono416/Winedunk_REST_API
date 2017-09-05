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

public class UserReviewsService {

	RequestsCreator requestCreator = new RequestsCreator();
	
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }

	
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
	
	public List<tblUserWineReviews> getReviewsForWine(Integer wineId) throws IOException
	{
		/*
		 * This method is for getting the list of reviews that a wine has.
		 */
		
		List<tblUserWineReviews> reviewsList = new ArrayList<tblUserWineReviews>();
		
		String relURL = "userWineReviews?action=getReviewsForWine";
		String response= requestCreator.createPostRequest(urlPath, relURL, wineId.toString());
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	   	
		JsonNode responseJson = mapper.readTree(response);
		if(responseJson == null) { return null; }
	   	
	   	ArrayNode reviewsNodes = (ArrayNode) responseJson.get("Reviews");
	   	Iterator<JsonNode> reviewsIterator = reviewsNodes.elements();
	   	List<tblUserWineReviews> resultsList = new ArrayList<tblUserWineReviews>();
	   	
	   	while(reviewsIterator.hasNext())
	   	{
	   		JsonNode reviewNode = reviewsIterator.next();
	   		tblUserWineReviews review = mapper.treeToValue(reviewNode, tblUserWineReviews.class);
	   		resultsList.add(review);
	   	}
	   	
	   	return resultsList;
	}
	
	public Integer getCountForWine(Integer wineId) throws IOException
	{
		String relURL = "userWineReviews?action=getAmountOfReviewsForWine";
		String response = requestCreator.createPostRequest(urlPath, relURL, wineId.toString());
		
		return Integer.parseInt(response);
	}
}
