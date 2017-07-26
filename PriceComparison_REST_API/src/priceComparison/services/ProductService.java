package priceComparison.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import priceComparison.models.viewWinePriceComparison;
import priceComparison.models.viewWinesMinimumPrice;

public class ProductService {
    
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }
	
	private viewWinesMinimumPrice wine;
 	public void setWine(viewWinesMinimumPrice wine) { this.wine = wine; }

 	private List<viewWinePriceComparison> priceComparisonList;
	public List<viewWinePriceComparison> getPriceComparisonList() { return priceComparisonList; }
	public void setPriceComparisonList(List<viewWinePriceComparison> priceComparisonList) { this.priceComparisonList = priceComparisonList; }
	
	RequestsCreator requestCreator = new RequestsCreator();
	public ProductService() {}
    
    public viewWinesMinimumPrice getWine(String id)
    {
    	try
    	{
    		//Create request
    		String relURL = "winesMinPriceView?action=getWine&id=" + id;
    		String responseString = requestCreator.createGetRequest(urlPath, relURL);
		
    		//Convert to object
			ObjectMapper mapper = new ObjectMapper();
			JsonNode responseJson = mapper.readTree(responseString);
			if(responseJson == null) { return null; }
			
			wine = mapper.treeToValue(responseJson, viewWinesMinimumPrice.class);
    	} catch (Exception e) { e.printStackTrace();}
		return wine;
    }
    
    public List<viewWinePriceComparison> getPriceComparison(String id)
    {
    	try
    	{
    		//Create request
    		String relURL = "winePriceComparisonView?action=getComparisonWithQuery&id=" + id;
    		String responseString = requestCreator.createGetRequest(urlPath, relURL);
    		//Convert to object
			ObjectMapper mapper = new ObjectMapper();
			JsonNode responseJson = mapper.readTree(responseString);
			if(responseJson == null) { return null; }

			ArrayNode winesNodes = (ArrayNode) responseJson.get("Shops");
			Iterator<JsonNode> winesIterator = winesNodes.elements();
			
			priceComparisonList = new ArrayList<viewWinePriceComparison>();
			while(winesIterator.hasNext())
			{
				JsonNode wineNode = winesIterator.next();
				viewWinePriceComparison shop = mapper.treeToValue(wineNode, viewWinePriceComparison.class);
				priceComparisonList.add(shop);
			}
    	} catch(Exception e) { e.printStackTrace(); }
		return priceComparisonList;
    }
}
