package priceComparison.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import priceComparison.models.ViewWineReviewsAndRatingByUsers;

public class ViewWineReviewsAndRatingByUsersService {

	RequestsCreator requestCreator = new RequestsCreator();
	
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }

	private Integer userId;
	public Integer getUserId() { return userId; }
	public void setUserId(Integer userId) { this.userId = userId; }
	
	
	public List<ViewWineReviewsAndRatingByUsers> getAllForUser(Integer userId) {
		
		if ( (userId != null) && (userId > 0) ) {
		
			try {
				String relURL = "WineReviewsAndRatingsByUsers?action=getAllByUserId";
				String response = requestCreator.createPostRequest(urlPath, relURL, userId.toString());
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				JsonNode responseJson = mapper.readTree(response);
				
				if (responseJson != null) { 
				
					ArrayNode reviewsNodes = (ArrayNode) responseJson.get("RatingsAndReviews");
				   	if (reviewsNodes != null) {
				   		
				   		Iterator<JsonNode> reviewsIterator = reviewsNodes.elements();
					   	List<ViewWineReviewsAndRatingByUsers> resultsList = new ArrayList<ViewWineReviewsAndRatingByUsers>();
					   	
					   	while (reviewsIterator.hasNext()) {
					   		JsonNode reviewNode = reviewsIterator.next();
					   		ViewWineReviewsAndRatingByUsers review = null;
					   		review = mapper.treeToValue(reviewNode, ViewWineReviewsAndRatingByUsers.class);
					   		resultsList.add(review);
					   	}
					   	
					   	return resultsList;
					   	
				   	} else {
					   	return null;
				   	}
				   	
				} else {
					return null;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			} 
		   	
		} else {
			
			return null;
		}
	   	
	}
	
	public Boolean deleteRating(Integer ratingId) {
		
		if ( (ratingId != null) && (ratingId > 0) ) {
			
			try {
				String relURL = "WineReviewsAndRatingsByUsers?action=deleteRating";
				String response = requestCreator.createPostRequest(urlPath, relURL, ratingId.toString());
				boolean booleanResponse = false;
				try {
					booleanResponse = Boolean.parseBoolean(response);
				} catch (Exception e) {
					booleanResponse = false;
				}
				
				return booleanResponse;
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else return false;
	}
	
	public Boolean updateRating(Integer ratingId, Integer ratingValue) {
		
		if ( (ratingId != null) && (ratingId > 0) ) {
			
			try {
				String relURL = "WineReviewsAndRatingsByUsers?action=updateRating";
				String response = requestCreator.createPostRequest(urlPath, relURL, ratingId.toString().concat(",").concat(ratingValue.toString()));
				boolean booleanResponse = false;
				try {
					booleanResponse = Boolean.parseBoolean(response);
				} catch (Exception e) {
					booleanResponse = false;
				}
				
				return booleanResponse;
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else return false;
	}

	public Boolean InsertRating(Integer userId, Integer wineId, Integer ratingValue) {
		
		if ( (userId != null) && (userId > 0) && (wineId != null) && (wineId > 0) && (ratingValue != null) && (ratingValue > 0)) {
			
			try {
				String relURL = "WineReviewsAndRatingsByUsers?action=insertRating";
				String response = requestCreator.createPostRequest(urlPath, relURL, userId.toString().concat(",").concat(wineId.toString().concat(",").concat(ratingValue.toString())));
				boolean booleanResponse = false;
				try {
					booleanResponse = Boolean.parseBoolean(response);
				} catch (Exception e) {
					booleanResponse = false;
				}
				
				return booleanResponse;
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else return false;
	}
	
	public boolean deleteReview(Integer reviewId) {
		
		if ( (reviewId != null) && (reviewId > 0) ) {
			
			try {
				String relURL = "WineReviewsAndRatingsByUsers?action=deleteReview";
				String response = requestCreator.createPostRequest(urlPath, relURL, reviewId.toString());
				boolean booleanResponse = false;
				try {
					booleanResponse = Boolean.parseBoolean(response);
				} catch (Exception e) {
					booleanResponse = false;
				}
				
				return booleanResponse;
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else return false;
	}
	
	public boolean updateReview(Integer reviewId, String reviewTitle, String reviewComments) {
		
		if ( (reviewId != null) && (reviewId > 0) ) {
			
			if ( (reviewComments != null) && (!reviewComments.equals("")) ) {
			
				try {
					
					String relURL = "WineReviewsAndRatingsByUsers?action=updateReview";
					String response = requestCreator.createPostRequest(urlPath, relURL, reviewId.toString().concat(Character.toString ((char) 30)).concat(reviewTitle).concat(Character.toString ((char) 30)).concat(reviewComments));
					boolean booleanResponse = false;
					try {
						booleanResponse = Boolean.parseBoolean(response);
					} catch (Exception e) {
						booleanResponse = false;
					}
					
					return booleanResponse;
					
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			} else {
				return deleteReview(reviewId);
			}
			
		} else return false;
	}

	public Boolean InsertReview(Integer userId, Integer wineId, String reviewTitle, String reviewComments) {
		
		if ( (userId != null) && (userId > 0) && (wineId != null) && (wineId > 0) && (reviewComments != null) && (!reviewComments.equals(""))) {
			
			try {
				String relURL = "WineReviewsAndRatingsByUsers?action=insertReview";
				String response = requestCreator.createPostRequest(urlPath, relURL, userId.toString().concat(Character.toString ((char) 30)).concat(wineId.toString().concat(Character.toString ((char) 30)).concat(reviewTitle).concat(Character.toString ((char) 30)).concat(reviewComments)));
				boolean booleanResponse = false;
				try {
					booleanResponse = Boolean.parseBoolean(response);
				} catch (Exception e) {
					booleanResponse = false;
				}
				
				return booleanResponse;
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else return false;
	}

}
