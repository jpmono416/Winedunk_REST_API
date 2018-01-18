package priceComparison.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import priceComparison.models.viewBestOffersbyCountries;
import priceComparison.models.viewBestOffersbyMerchants;
import priceComparison.models.viewBestOffersbyWineTypes;
import priceComparison.models.viewCountriesWithBestOffers;
import priceComparison.models.viewMerchants;
import priceComparison.models.viewMerchantsWithBestOffers;
import priceComparison.models.viewRecommendedWines;
import priceComparison.models.viewWineTypesWithBestOffers;
import priceComparison.models.viewWines;

public class GeneralService {
	
	private String crudURL;
	public String getCrudURL() { return crudURL; }
	public void setCrudURL(String crudURL) { this.crudURL = crudURL; }
	
	private List<viewRecommendedWines> recommendedWines = new ArrayList<viewRecommendedWines>();
	public List<viewRecommendedWines> getRecommendedWines() { return recommendedWines; }
	public void setRecommendedWines(List<viewRecommendedWines> recommendedWines) { this.recommendedWines = recommendedWines; }

	RequestsCreator requestCreator = new RequestsCreator();
	
	public void checkRecommended(HttpServletRequest request)
	{
		if(request.getSession().getAttribute("recommendedWines") == null)
		{
			try 
			{ 
				loadRecommendedWines();
				request.getSession().setAttribute("recommendedWines", recommendedWines);
			}	catch (Exception e) { e.printStackTrace(); }	
		}
	}
	
	public void loadRecommendedWines() throws IOException
	{
		String relURL = "recommendedWinesView?action=getRecommendedWines";
		String responseString = requestCreator.createGetRequest(crudURL, relURL);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode responseJson = mapper.readTree(responseString);
		if(responseJson == null) { return; }
		
		ArrayNode winesNodes = (ArrayNode) responseJson.get("Wines");
		Iterator<JsonNode> winesIterator = winesNodes.elements();
		
		while(winesIterator.hasNext())
		{
			JsonNode wineNode = winesIterator.next();
			viewRecommendedWines wine = mapper.treeToValue(wineNode, viewRecommendedWines.class);
			recommendedWines.add(wine);
		}
	}
	
	//Used for encryption and HEX decoding from bytes
	public String bytesToHex(byte[] bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public void makeSearch(HttpServletRequest request, String crudUrl)
	{
		ResultsService resultsService = new ResultsService();
		Integer currentPage = 0,
				amountOfPages;
		
		/*
		 * This part is accessed when a search is made using the left-hand panel either on Results or Product page
		 * It is also accessed from the Home page's search bar.
		 * First, I check which of the filters have been entered and compose a URL with the relevant parameters
		 * I need to get the ID of some of these parameters to pass them on to the query
		 * Then, I check for any other set of results previously loaded on the session and delete it.
		 * Finally, I set the new set of results and forward the user to result page.
		 */
		
		resultsService.setUrlPath(crudUrl);
		resultsService.loadFilters(request);
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			Map<Integer, String> generalMap = new HashMap<Integer, String>();
			String urlParameters = "",
			name 			= request.getParameter("name"),
			country 		= request.getParameter("country"),
			region 			= request.getParameter("region"),
			winery 			= request.getParameter("winery"),
			appellation 	= request.getParameter("appellation"),
			chosenColour 	= request.getParameter("chosenColour"),
			vintageMin 		= request.getParameter("vintageMin"),
			vintageMax 		= request.getParameter("vintageMax"),
			abvMin 			= request.getParameter("abvMin"),
			abvMax 			= request.getParameter("abvMax"),
			minPrice 		= request.getParameter("minPrice"),
			maxPrice 		= request.getParameter("maxPrice"),
			wineType 		= request.getParameter("chosenType"),
			grapeVariety 	= request.getParameter("grapeVariety"),
			merchant 		= request.getParameter("chosenShop"),
			rating 			= request.getParameter("ratingValue");
			
			String[] filtersToGet = new String[] 
					{ "listOfCountries", "listOfRegions", "listOfWineries", "listOfAppellations", "listOfGrapeVarieties" };
			
			String[] variablesList = new String[]
					{ country, region, winery, appellation, grapeVariety };
			
			Integer currentTurn = 0;
			
			for( String s : filtersToGet )
			{
				if(variablesList[currentTurn] != null && !variablesList[currentTurn].equals("")) 
				{ 

					
					generalMap = (Map<Integer, String>) request.getSession().getAttribute(s);
					Integer result = MapGetter.getKeyFromValue(generalMap, variablesList[currentTurn]);
					switch (s)
					{
						case "listOfCountries":
							country = result.toString();
							break;
						case "listOfRegions":
							region = result.toString();
							break;
						case "listOfWineries":
							winery = result.toString();
							break;
						case "listOfAppellations":
							 appellation = result.toString();
							break;
						case "listOfGrapeVarieties":
							grapeVariety = result.toString();
							break;
					}
				}
				currentTurn++;
			}
			
			if(name != null && !name.equals("")) 					{ urlParameters += "&name=" + name; 					}
			if(region != null && !region.equals("")) 				{ urlParameters += "&chosenRegion=" + region; 			}
			if(winery != null && !winery.equals("")) 		 		{ urlParameters += "&wineryId=" + winery; 				}
			if(appellation != null && !appellation.equals(""))		{ urlParameters += "&appellationId=" + appellation;		}
			if(chosenColour != null && !chosenColour.equals("0")) 	{ urlParameters += "&chosenColour=" + chosenColour; 	}		
			if(vintageMin != null && !vintageMin.equals("")) 		{ urlParameters += "&vintageMin=" + vintageMin; 		}
			if(vintageMax != null && !vintageMax.equals("")) 		{ urlParameters += "&vintageMax=" + vintageMax; 		}
			if(abvMin != null && !abvMin.equals("")) 				{ urlParameters += "&abvMin=" + abvMin; 				}
			if(abvMax != null && !abvMax.equals("")) 				{ urlParameters += "&abvMax=" + abvMax; 				}
			if(grapeVariety != null && !grapeVariety.equals("")) 	{ urlParameters += "&grapeVarietyId=" + grapeVariety; 	}
			if(minPrice != null && !minPrice.equals(""))			{ urlParameters += "&minPrice=" + minPrice; 			}
			if(maxPrice != null && !maxPrice.equals("")) 			{ urlParameters += "&maxPrice=" + maxPrice; 			}
			if(rating != null && !rating.equals(""))				{ urlParameters += "&ratingValue=" + rating;			}
			if(merchant != null && !merchant.equals("0"))
			{ 
				urlParameters +="&merchant=" + merchant;
				
				String merchantUrl = "merchantsView?action=getMerchant&id=" + merchant;
				String merchantString = requestCreator.createGetRequest(crudUrl, merchantUrl); 
				
		    	JsonNode merchantJson = mapper.readTree(merchantString);
		    	viewMerchants merchantWithOffersObject = mapper.treeToValue(merchantJson, viewMerchants.class);
		    	if(merchantWithOffersObject.getId() == null || merchantWithOffersObject.getId() <= 0) 
		    	{
		    		// This avoids putting up merchant info if there isn't
		    		request.setAttribute("noMerchant", true);
		    	}
		    	
		    	//Get the best offers for the merchant
		    	List<viewBestOffersbyMerchants> bestOffers = this.getBestOffersByMerchant(Integer.parseInt(merchant));
		    	if(bestOffers != null) { request.setAttribute("bestMerchantOffers", bestOffers); }
		    	else { request.setAttribute("noMerchantOffers", true); }
		    	
		    	// Set a couple of required variables to display dynamic information on Results page
		    	request.setAttribute("searchByMerchant", true);
		    	request.setAttribute("merchantChosen", merchantWithOffersObject);
		    	
		    	// Check the length of the search, 12 includes a merchant between 1 and 99 and no more params
		    	if(urlParameters.length() > 12) { request.setAttribute("severalAttributes", true); }
			}
			if(country != null && !country.equals("")) 				
			{ 
				System.out.println("Came into country"); // TODO DELETE
				
				urlParameters += "&chosenCountry=" + country;
				
				String countriesUrl = "countriesWithBestOffersView?action=getCountryWithBestOffers&id=" + country;
				String countryString = requestCreator.createGetRequest(crudUrl, countriesUrl);
				JsonNode countryJson = mapper.readTree(countryString);
				
				if(countryJson == null) { request.setAttribute("noResults", true); }
				viewCountriesWithBestOffers countryWithOffersObject = mapper.treeToValue(countryJson, viewCountriesWithBestOffers.class);
				if(countryWithOffersObject.getId() == null || countryWithOffersObject.getId() <= 0)
				{
					request.setAttribute("noCountry", true);
				}
				
				List<viewBestOffersbyCountries> bestOffers = this.getBestOffersByCountry(Integer.parseInt(country));
				if(bestOffers != null) { request.setAttribute("bestCountryOffers", bestOffers); }
				else { request.setAttribute("noCountryOffers", true); }
				
				// Set a couple of variables
				request.setAttribute("searchByCountry", true);
				request.setAttribute("countryChosen", countryWithOffersObject);
			}
			if(wineType != null && !wineType.equals(""))
			{ 
				urlParameters += "&chosenType=" + wineType;
				
				String typesUrl = "wineTypesWithBestOffersView?action=getWineTypeWithBestOffers&id=" + wineType;
				String typeString = requestCreator.createGetRequest(crudUrl, typesUrl);
				JsonNode typeJson = mapper.readTree(typeString);
				
				if(typeJson == null) { request.setAttribute("noResults", true); }
				viewWineTypesWithBestOffers typeWithOffersObject = mapper.treeToValue(typeJson, viewWineTypesWithBestOffers.class);
				if(typeWithOffersObject.getId() == null || typeWithOffersObject.getId() <= 0)
				{
					request.setAttribute("noType", true);
				}
				List<viewBestOffersbyWineTypes> bestOffers = this.getBestOffersByWineType(Integer.parseInt(wineType));
				if(bestOffers != null) { request.setAttribute("bestWineTypeOffers", bestOffers); }
				else { request.setAttribute("noWineTypeOffers", true); }
				
				// Set a couple of variables
				request.setAttribute("searchByWineType", true);
				request.setAttribute("wineTypeChosen", typeWithOffersObject);
			}
			
			//Set the sharing URL in a correct format (change the first & character into a ?)
			request.setAttribute("sharingURL", ("?" + urlParameters).replace("?&", "?"));
			
			// Security checks and default values
			if(abvMin != null && abvMax !=null)
			{
				if(!abvMin.equals("") && abvMax.equals("")) { urlParameters += "&abvMax=100.00"; }
				if(!abvMax.equals("") && abvMin.equals("")) { urlParameters += "&abvMin=0.00"; }
			}
			
			if(vintageMin != null && vintageMax != null)
			{
				if(!vintageMin.equals("") && vintageMax.equals("")) { urlParameters += "&vintageMax=" + Calendar.getInstance().get(Calendar.YEAR); }
				if(!vintageMax.equals("") && vintageMin.endsWith("")) { urlParameters += "&vintageMin=1995"; }	
			}
			
			if(minPrice != null && maxPrice != null)
			{
				if(!minPrice.equals("") && maxPrice.equals("")) { urlParameters += "&maxPrice=10000"; }
				if(!maxPrice.equals("") && minPrice.equals("")) { urlParameters += "&minPrice=0"; }
			}
						
			if(currentPage < 1 ) { currentPage = 1; }
			
			//Set the last search with the current filters (without page number)
			request.getSession().setAttribute("lastSearch", urlParameters);
			
			urlParameters += "&currentPage=1";
			amountOfPages = resultsService.getCountOfPages(urlParameters);
			List<viewWines> results = resultsService.getWines(urlParameters);
			
			request.getSession().setAttribute("currentPage", 1);
			request.getSession().removeAttribute("sessionResults"); //Delete results if a previous "sort" was made
			request.getSession().setAttribute("amountOfPages", amountOfPages);
			
			// Check if more than 1 "best offers" filters are selected to make changes on Results
			Boolean moreThanOne = urlParameters.contains("&merchant=") ? 
					(urlParameters.contains("&chosenCountry=") || urlParameters.contains("&chosenType=")) :
					(urlParameters.contains("&chosenCountry=") && urlParameters.contains("&chosenType="));
					
			if(moreThanOne) 
			{
				request.getSession().setAttribute("notDisplayTitleCards", true);
			}
			
			if(!results.isEmpty()) { request.setAttribute("resultsList", results); }
			else { request.setAttribute("noResults", true); }
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public Boolean sendEmail(String content, String email)
	{
		
		// Recipient's email ID needs to be mentioned.
		String to = email;
	
		// Sender's email ID needs to be mentioned
	    String from = "no-reply@winedunk.com";
	
	    // Assuming you are sending email from localhost
	    String host = "localhost";
	
	    // Get system properties
	    Properties properties = System.getProperties();
	
	    // Setup mail server
	    properties.setProperty("mail.smtp.host", host);
	
	    // Get the default Session object.
	    Session session = Session.getDefaultInstance(properties);

	    try 
	    {
	    	// Create a default MimeMessage object.
	        MimeMessage message = new MimeMessage(session);

	        // Set From: header field of the header.
	        message.setFrom(new InternetAddress(from));

	        // Set To: header field of the header.
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	        // Set Subject: header field
	        message.setSubject("Thank you for registering!");

	        // Send the actual HTML message, as big as you like
	        message.setContent(content, "text/html");


	        // Send message
	        Transport.send(message);
	        System.out.println("Sent message successfully");
	      } catch (MessagingException mex) { mex.printStackTrace(); }
	    
		return false;
	}
	
	
	/*
	 * This method is executed to check if the user has registered to the page
	 * Or has already seen the message telling them to register. If not,
	 * It returns true if there is need to display the message.
	 */
	
	public Boolean checkForRegistration(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			if(request.getSession().getAttribute("isLoggedIn") != null)
			{
				Boolean isLoggedIn = Boolean.valueOf(request.getSession().getAttribute("isLoggedIn").toString());
				if(isLoggedIn == true ) { return false; }	
			}
			
			if(request.getCookies() != null) 
			{
				Cookie[] cookies = request.getCookies();
				
				if(cookies != null)
				{
					for(Cookie cookie : cookies)
					{
						if(cookie.getName().equals("nsu") 
								&& cookie.getValue().equals("true")) //Retrieve the No Sign Up cookie
						{
							return false;
						}
					}
				}
				return true;
			}
			return true;
		} catch(Exception e) { e.printStackTrace(); return null; }
	}
	
	public List<viewBestOffersbyMerchants> getBestOffersByMerchant(Integer id) throws IOException
	{
		String relURL = "bestOffersByMerchantView?action=getOffersForMerchant&id=" + id;
		String offersResponse = requestCreator.createGetRequest(crudURL, relURL);
		ObjectMapper mapper = new ObjectMapper();
		
    	if(offersResponse != null && !offersResponse.equals(""))
    	{
    		JsonNode offersJson = mapper.readTree(offersResponse);
	    	if(offersJson == null) { return null; }
	    	
	    	ArrayNode offersNodes = (ArrayNode) offersJson.get("BestOffers");
		   	Iterator<JsonNode> offersIterator = offersNodes.elements();
			List<viewBestOffersbyMerchants> bestOffers = new ArrayList<viewBestOffersbyMerchants>();
			
			while(offersIterator.hasNext())
			{
		   		JsonNode offerNode = offersIterator.next();
		   		viewBestOffersbyMerchants offer = mapper.treeToValue(offerNode, viewBestOffersbyMerchants.class);
		   		bestOffers.add(offer);
			}
			
			return bestOffers;
    	}
		return null;
	}
	
	public List<viewBestOffersbyWineTypes> getBestOffersByWineType(Integer id) throws IOException
	{
		String relURL = "bestOffersByWineTypeView?action=getOffersForWineType&id=" + id;
		String offersResponse = requestCreator.createGetRequest(crudURL, relURL);
		ObjectMapper mapper = new ObjectMapper();
		
    	if(offersResponse != null && !offersResponse.equals(""))
    	{
    		JsonNode offersJson = mapper.readTree(offersResponse);
	    	if(offersJson == null) { return null; }
	    	
	    	ArrayNode offersNodes = (ArrayNode) offersJson.get("BestOffers");
		   	Iterator<JsonNode> offersIterator = offersNodes.elements();
			List<viewBestOffersbyWineTypes> bestOffers = new ArrayList<viewBestOffersbyWineTypes>();
			
			while(offersIterator.hasNext())
			{
		   		JsonNode offerNode = offersIterator.next();
		   		viewBestOffersbyWineTypes offer = mapper.treeToValue(offerNode, viewBestOffersbyWineTypes.class);
		   		bestOffers.add(offer);
			}
			
			return bestOffers;
    	}
		return null;
	}
	
	public List<viewBestOffersbyCountries> getBestOffersByCountry(Integer id) throws IOException
	{
		String relURL = "bestOffersByCountryView?action=getOffersForCountry&id=" + id;
		String offersResponse = requestCreator.createGetRequest(crudURL, relURL);
		ObjectMapper mapper = new ObjectMapper();
		
    	if(offersResponse != null && !offersResponse.equals(""))
    	{
    		JsonNode offersJson = mapper.readTree(offersResponse);
	    	if(offersJson == null) { return null; }
	    	
	    	ArrayNode offersNodes = (ArrayNode) offersJson.get("BestOffers");
		   	Iterator<JsonNode> offersIterator = offersNodes.elements();
		   	List<viewBestOffersbyCountries> bestOffers = new ArrayList<viewBestOffersbyCountries>();
		   	
		   	while(offersIterator.hasNext())
		   	{
		   		JsonNode offerNode = offersIterator.next();
		   		viewBestOffersbyCountries offer = mapper.treeToValue(offerNode, viewBestOffersbyCountries.class);
		   		bestOffers.add(offer);
		   	}
		   	return bestOffers;
    	}
		return null;
	}
	
	public List<viewMerchantsWithBestOffers> getMerchantsWithBestOffers() throws IOException
	{
		String merchantsUrl = "merchantsWithBestOffersView?action=getMerchantsWithBestOffers";
		String responseString = requestCreator.createGetRequest(crudURL, merchantsUrl);
		ObjectMapper mapper = new ObjectMapper();
		
		if(responseString != null && !responseString.equals(""))
		{
			JsonNode merchantsNode = mapper.readTree(responseString);
			
			ArrayNode merchantNodes = (ArrayNode) merchantsNode.get("Merchants");
		   	Iterator<JsonNode> merchantsIterator = merchantNodes.elements();
		   	List<viewMerchantsWithBestOffers> merchants = new ArrayList<viewMerchantsWithBestOffers>();
		   	
		   	while(merchantsIterator.hasNext())
			{
		   		JsonNode merchantNode = merchantsIterator.next();
		   		viewMerchantsWithBestOffers merchant = mapper.treeToValue(merchantNode, viewMerchantsWithBestOffers.class);
		   		merchants.add(merchant);
			}
		   	return merchants;
		}
		return null;
	}
	
	public List<viewWineTypesWithBestOffers> getWineTypesWithBestOffers() throws IOException
	{
		String typesUrl = "wineTypesWithBestOffersView?action=getWineTypesWithBestOffers";
		String responseString = requestCreator.createGetRequest(crudURL, typesUrl);
		ObjectMapper mapper = new ObjectMapper();
		
		if(responseString != null && !responseString.equals(""))
		{
			JsonNode wineTypesNode = mapper.readTree(responseString);
			
			ArrayNode wineTypesNodes = (ArrayNode) wineTypesNode.get("WineTypes");
			Iterator<JsonNode> wineTypesIterator = wineTypesNodes.elements();
			List<viewWineTypesWithBestOffers> wineTypes = new ArrayList<viewWineTypesWithBestOffers>();
			
			while(wineTypesIterator.hasNext())
			{
				JsonNode wineTypeNode = wineTypesIterator.next();
				viewWineTypesWithBestOffers wineType = mapper.treeToValue(wineTypeNode, viewWineTypesWithBestOffers.class);
				wineTypes.add(wineType);
			}
			return wineTypes;
		}
		return null;
	}
	
	public List<viewCountriesWithBestOffers> getCountriesWithBestoffers() throws IOException
	{
		String countriesUrl = "countriesWithBestOffersView?action=getCountriesWithBestOffers";
		String responseString = requestCreator.createGetRequest(crudURL, countriesUrl);
		ObjectMapper mapper = new ObjectMapper();
		
		if(responseString != null && !responseString.equals(""))
		{
			JsonNode countriesNode = mapper.readTree(responseString);
			
			ArrayNode countriesNodes = (ArrayNode) countriesNode.get("Countries");
			Iterator<JsonNode> countriesIterator = countriesNodes.elements();
			List<viewCountriesWithBestOffers> countries = new ArrayList<viewCountriesWithBestOffers>();
			
			while(countriesIterator.hasNext())
			{
				JsonNode countryNode = countriesIterator.next();
				viewCountriesWithBestOffers country = mapper.treeToValue(countryNode, viewCountriesWithBestOffers.class);
				countries.add(country);
			}
			return countries;
		}
		return null;
	}
}
