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
 		 if (winesNodes != null) {
	    	 Iterator<JsonNode> winesIterator = winesNodes.elements();
	    	 List<viewWines> resultsList = new ArrayList<viewWines>();
			
	    	 while(winesIterator.hasNext())
	    	 {
	    		 JsonNode wineNode = winesIterator.next();
	    		 viewWines wine = mapper.treeToValue(wineNode, viewWines.class);
	    		 resultsList.add(wine);
	    	 }
	    	 return resultsList;
 		} else {
	   		return null;
	   	}
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
    
    public void loadStaticFilters(HttpServletRequest request)
 	{
 		String[] filtersToGet = new String[]
 				{"WineTypes", "Colours", "ShopsView", "GrapeVarieties"};
 		
 		try 
 		{ 
 			for(String urlParam : filtersToGet) 
 			{ 
 				request.getSession().setAttribute("listOf" + urlParam, GetFilters(urlParam));
 			}
 		} catch (Exception e) { e.printStackTrace(); }
 		
 		// Regions, Appellations and Wineries will be loaded dynamically
 		request.getSession().setAttribute("listOfRegions", null);request.getSession().setAttribute("listOfRegions", null);
 		request.getSession().setAttribute("listOfAppellations", null);
 		request.getSession().setAttribute("listOfWineries", null);
 		
 	}
    

    public void loadCountriesFilters(HttpServletRequest request) {
    	// loading countries with wine only rather than all countries
    	try {
    		
			String responseString = requestCreator.createGetRequest(urlPath, "countries?action=getCountryWithWines" );
			JsonNode responseJson = new ObjectMapper().readTree(responseString);
			
			Map<Integer, String> dbRecordsMap = new HashMap<Integer, String>();
			for (Integer i = 0; i < responseJson.size(); i++) {
				dbRecordsMap.put(responseJson.get(i).get("id").asInt(), responseJson.get(i).get("name").asText());
			}
			
	 		request.getSession().setAttribute("listOfCountries", dbRecordsMap);
			
    	} catch (Exception e) {
    		request.getSession().setAttribute("listOfCountries", null);
    	}
    }
	
    
    public void loadRegionFilters(HttpServletRequest request, Integer countryId) {
		    		
		if (countryId > 0) {

			try {
		
				String responseString = requestCreator.createGetRequest(urlPath, "regions?action=getRegionsByCountryId&countryId="+countryId );
				JsonNode responseJson = new ObjectMapper().readTree(responseString);
				
				Map<Integer, String> dbRecordsMap = new HashMap<Integer, String>();
				for (Integer i = 0; i < responseJson.size(); i++) {
					dbRecordsMap.put(responseJson.get(i).get("id").asInt(), responseJson.get(i).get("name").asText());
				}
				
		 		request.getSession().setAttribute("listOfRegions", dbRecordsMap);
				
	    	} catch (Exception e) {
	    		request.getSession().setAttribute("listOfRegions", null);request.getSession().setAttribute("listOfRegions", null);
	    	}
						
		} else {
			request.getSession().setAttribute("listOfRegions", null);request.getSession().setAttribute("listOfRegions", null);
		}
	}
    
    public void loadAppellationFilters(HttpServletRequest request, Integer regionId) {
    	
    	if (regionId > 0) {

    		String responseString;
			try {
				responseString = requestCreator.createGetRequest(urlPath, "appellations?action=getAppellationsByRegionId&regionId="+regionId );
				JsonNode responseJson = new ObjectMapper().readTree(responseString);
				Map<Integer, String> dbRecordsMap = new HashMap<Integer, String>();
				for (Integer i = 0; i < responseJson.size(); i++) {
					dbRecordsMap.put(responseJson.get(i).get("id").asInt(), responseJson.get(i).get("name").asText());
				}

		 		request.getSession().setAttribute("listOfAppellations", dbRecordsMap);
		 		
			} catch (IOException e) {
				request.getSession().setAttribute("listOfAppellations", null);
				e.printStackTrace();
			}
			
    	} else {
			request.getSession().setAttribute("listOfAppellations", null);
		}
    }
    
    public void loadWineryFilters(HttpServletRequest request, Integer appellationId) {
    	
    	if (appellationId > 0) {    		
			try {
		
				String responseString = requestCreator.createGetRequest(urlPath, "wineries?action=getWineriesByAppellationId&appellationId="+appellationId );
				JsonNode responseJson = new ObjectMapper().readTree(responseString);
				Map<Integer, String> dbRecordsMap = new HashMap<Integer, String>();
				for (Integer i = 0; i < responseJson.size(); i++) {
					dbRecordsMap.put(responseJson.get(i).get("id").asInt(), responseJson.get(i).get("name").asText());
				}

		 		request.getSession().setAttribute("listOfWineries", dbRecordsMap);
				
	    	} catch (Exception e) {
	    		request.getSession().setAttribute("listOfWineries", null);
	    	}
						
		} else {
			request.getSession().setAttribute("listOfWineries", null);
		}
	}
    
    public ArrayList<String> getRegionFilters(Integer countryId) {
		
    	if ( (countryId != null) && (countryId > 0) ) {
    	
    		ArrayList<String> regionsList = new ArrayList<String>();
			try {
		
				String responseString = requestCreator.createGetRequest(urlPath, "regions?action=getRegionsByCountryId&countryId="+countryId );
				JsonNode responseJson = new ObjectMapper().readTree(responseString);
				for (Integer i = 0; i < responseJson.size(); i++) {
					regionsList.add(responseJson.get(i).get("name").asText());
				}
				return regionsList;
				
	    	} catch (Exception e) {
	    		return null;
	    	}
						
		} else {
			return null;
		}
	}
    
	public ArrayList<String> getAppellationFilters(Integer regionId) {
		
    	if ( (regionId != null) && (regionId > 0) ) {
    	
    		ArrayList<String> appellationsList = new ArrayList<String>();
			try {
		
				String responseString = requestCreator.createGetRequest(urlPath, "appellations?action=getAppellationsByRegionId&regionId="+regionId );
				JsonNode responseJson = new ObjectMapper().readTree(responseString);
				for (Integer i = 0; i < responseJson.size(); i++) {
					appellationsList.add(responseJson.get(i).get("name").asText());
				}
				return appellationsList;
				
	    	} catch (Exception e) {
	    		return null;
	    	}
						
		} else {
			return null;
		}
	}
	
	public ArrayList<String> getWineryFilters(Integer appellationId) {
		
    	if ( (appellationId != null) && (appellationId > 0) ) {
    	
    		ArrayList<String> regionsList = new ArrayList<String>();
			try {
		
				String responseString = requestCreator.createGetRequest(urlPath, "wineries?action=getWineriesByAppellationId&appellationId="+appellationId );
				JsonNode responseJson = new ObjectMapper().readTree(responseString);
				for (Integer i = 0; i < responseJson.size(); i++) {
					regionsList.add(responseJson.get(i).get("name").asText());
				}
				return regionsList;
				
	    	} catch (Exception e) {
	    		return null;
	    	}
						
		} else {
			return null;
		}
	}
    
    
}
