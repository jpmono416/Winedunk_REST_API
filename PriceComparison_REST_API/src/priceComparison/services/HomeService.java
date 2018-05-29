package priceComparison.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import priceComparison.models.viewBestOffersbyMerchants;
import priceComparison.models.viewBestOffersbyWineTypes;
import priceComparison.models.viewRecommendedWines;

public class HomeService {
	
	private String crudURL;
	public String getCrudURL() { return crudURL; }
	public void setCrudURL(String crudURL) { this.crudURL = crudURL; }

	RequestsCreator requestCreator = new RequestsCreator();

	public List<viewRecommendedWines> loadRecommendedWines() throws IOException
	{
		String relURL = "recommendedWinesView?action=getRecommendedWines";
		String responseString = requestCreator.createGetRequest(crudURL, relURL);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode responseJson = mapper.readTree(responseString);
		if(responseJson == null) { return null; }
		
		ArrayNode winesNodes = (ArrayNode) responseJson.get("Wines");
		if (winesNodes != null) {
			Iterator<JsonNode> winesIterator = winesNodes.elements();
			List<viewRecommendedWines> resultsList = new ArrayList<viewRecommendedWines>();
			
			while(winesIterator.hasNext())
			{
				JsonNode wineNode = winesIterator.next();
				viewRecommendedWines wine = mapper.treeToValue(wineNode, viewRecommendedWines.class);
				resultsList.add(wine);
			}
			
			return resultsList;
		} else {
	   		return null;
	   	}
	}
	
	public List<viewBestOffersbyMerchants> getBestOffersByMerchant(Integer merchantId) throws IOException
	{
		String relURL = "bestOffersByMerchantView?action=getOffersForMerchant&id=" + merchantId;
		String offersResponse = requestCreator.createGetRequest(crudURL, relURL);
		ObjectMapper mapper = new ObjectMapper();
		
    	if(offersResponse != null && !offersResponse.equals(""))
    	{
    		JsonNode offersJson = mapper.readTree(offersResponse);
	    	if(offersJson == null) { return null; }
	    	else
	    	{
	    		@SuppressWarnings("unchecked")
				List<viewBestOffersbyMerchants> bestOffers =  
		    			(List<viewBestOffersbyMerchants>) mapper.treeToValue(offersJson, viewBestOffersbyMerchants.class);
				
				return bestOffers;
	    	}
    	}
		return null;
	}
	
	public List<viewBestOffersbyWineTypes> getBestOffersByWineType(Integer wineTypeId) throws IOException
	{
		String relURL = "bestOffersByWineTypeView?action=getOffersForWineType&id=" + wineTypeId;
		String offersResponse = requestCreator.createGetRequest(crudURL, relURL);
		ObjectMapper mapper = new ObjectMapper();
		
		if(offersResponse != null && !offersResponse.equals(""))
		{
			JsonNode offersJson = mapper.readTree(offersResponse);
	    	if(offersJson == null) { return null; }
	    	else
	    	{
	    		@SuppressWarnings("unchecked")
				List<viewBestOffersbyWineTypes> bestOffers =  
		    			(List<viewBestOffersbyWineTypes>) mapper.treeToValue(offersJson, viewBestOffersbyMerchants.class);
				
				return bestOffers;
	    	}
		}
		return null;
	}
}