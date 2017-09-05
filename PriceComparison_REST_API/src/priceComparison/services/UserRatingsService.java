package priceComparison.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import priceComparison.models.tblUserWinesRatings;

public class UserRatingsService {

	RequestsCreator requestCreator = new RequestsCreator();
	
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }

	
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
		/* String relURL = "userWineReviews?action=getAmountOfReviewsForWine";
		String response = requestCreator.createPostRequest(urlPath, relURL, wineId.toString());
		
		return Integer.parseInt(response); */
		return null;
	}
}
