package priceComparison.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import priceComparison.models.viewWines;

public class ResultsService {
	
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }
	
	RequestsCreator requestCreator = new RequestsCreator();
	public ResultsService() {}
    
    public Map<Integer, String> GetFilters(String action)
    {
    	try
    	{
    		String url = action.toLowerCase() + "?action=get" + action;
    		System.out.println("URL on class: " + url);
    		String responseString = requestCreator.createGetRequest(urlPath, url);
			JsonNode responseJson = new ObjectMapper().readTree(responseString);
			Map<Integer, String> resultsMap = new HashMap<Integer, String>();
			for (Integer i = 0; i < responseJson.size(); i++)
			{
				switch(responseJson.get(i).get("name").asText().toLowerCase()) {
				case "" : 
					break;
				case " " :
					break;
				case "no region" :
					break;
					
				case "11502" :
					break;
					
				case "no colour" :
					break;
					
				case "no winery" :
					break;
					
				case "no appellation" : 
					break;
					
				case "no country" :
					break;
					
				case "no variety" :
					break;
					
				default :
					String value = responseJson.get(i).get("name").asText();
					/*TRIMMING FUNCTIONALITY SO THE TEXT DOESN'T GET OUT OF THE BOX - REMOVED SO THE "VIEW MORE" CARDS WORK
					 * 
					 * if(value.length() > 20)
					{
						value = value.substring(0, Math.min(responseJson.get(i).get("name").asText().length(), 20)) + "...";	
					}*/
					resultsMap.put(responseJson.get(i).get("id").asInt(), value);
					break;
				}    				
			}
			return resultsMap;
    		
    	} catch (Exception e) { e.printStackTrace(); return null; }
    }
    
    
     public List<viewWines> getWines(String urlParameters) throws IOException
    {
    	 
    	 String relURL = "winesView?action=getWinesWithQuery" + urlParameters;
    	 String responseString = requestCreator.createGetRequest(urlPath, relURL);
    	 ObjectMapper mapper = new ObjectMapper();
    	 
    	 JsonNode responseJson = mapper.readTree(responseString);
    	 if(responseJson == null) { return null; }
		
    	 ArrayNode winesNodes = (ArrayNode) responseJson.get("Wines");
    	 Iterator<JsonNode> winesIterator = winesNodes.elements();
    	 List<viewWines> resultsList = new ArrayList<viewWines>();
		
    	 while(winesIterator.hasNext())
    	 {
    		 JsonNode wineNode = winesIterator.next();
    		 viewWines wine = mapper.treeToValue(wineNode, viewWines.class);
    		 resultsList.add(wine);
    	 }
    	 return resultsList;
    }
     
    public Integer getCountOfPages(String urlParameters) throws IOException
    {
    	String relURL = "winesView?action=getCountWithQuery" + urlParameters;
    	Integer amountOfPages = 0;
    	try {
    		amountOfPages = Integer.parseInt(requestCreator.createGetRequest(urlPath, relURL));
    	} catch (Exception e) {
    		amountOfPages = 0;
    	}

		if ( (amountOfPages == null) || (amountOfPages < 0) ) { 
			return null; 
		} else {
			return amountOfPages;
		}
    } 
    
    public void loadFilters(HttpServletRequest request)
 	{
 		String[] filtersToGet = new String[]
 				{"Regions", "WineTypes", "Colours", "Wineries", "Appellations", "Countries", "GrapeVarieties", "ShopsView"};
 		try 
 		{ 
 			for(String urlParam : filtersToGet) 
 			{ 
 				request.getSession().setAttribute("listOf" + urlParam, GetFilters(urlParam));
 			}
 		} catch (Exception e) { e.printStackTrace(); }
 	}
}
