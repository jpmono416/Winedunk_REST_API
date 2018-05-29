package priceComparison.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

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
import priceComparison.models.tblWineTypes;
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
		
		recommendedWines.clear();
		
		String relURL = "recommendedWinesView?action=getRecommendedWines";
		String responseString = requestCreator.createGetRequest(crudURL, relURL);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode responseJson = mapper.readTree(responseString);
		if(responseJson == null) { return; }
		
		ArrayNode winesNodes = (ArrayNode) responseJson.get("Wines");
	   	if (winesNodes != null) {
			Iterator<JsonNode> winesIterator = winesNodes.elements();
			
			while(winesIterator.hasNext())
			{
				JsonNode wineNode = winesIterator.next();
				viewRecommendedWines wine = mapper.treeToValue(wineNode, viewRecommendedWines.class);
				recommendedWines.add(wine);
			}
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
	
	@SuppressWarnings("unchecked")
	public void makeSearch(HttpServletRequest request)
	{

		//Get the CRUD URL
		final String webInfPath = request.getServletContext().getRealPath("/WEB-INF/");
		final String sep = System.getProperty("file.separator");
		final Properties serviceProperties = new Properties();
		ResultsService resultsService = new ResultsService();
		try {
			serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
			resultsService.setUrlPath(serviceProperties.getProperty("crud.url"));
		} catch (Exception e) {
			resultsService = null;
			e.printStackTrace();
		}
		
		if (resultsService != null) {
			
			ArrayList<String> selectedSearchCriteria = new ArrayList<String>();
			
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

				String[] listOfEntities = new String[] 
						{ "listOfCountries", "listOfRegions", "listOfWineries", "listOfAppellations", "listOfGrapeVarieties" };
				
				String[] entityNames = new String[]
						{ country, region, winery, appellation, grapeVariety };
				
				Integer index = 0;
				
				boolean itemFound = false;
				Integer foundItemId = 0;
				String foundItemName = "";
				
				for( String listOfEntity : listOfEntities )
				{	
					if(entityNames[index] != null && !entityNames[index].equals("")) 
					{ 
						
						generalMap = (Map<Integer, String>) request.getSession().getAttribute(listOfEntity);
						
						itemFound = false;
						foundItemId = 0;
						foundItemName = "";
						
						if (generalMap != null) {
							
							Iterator<Entry<Integer, String>> iterator = generalMap.entrySet().iterator();
						    while ( (!itemFound) && (iterator.hasNext()) ) {
						        @SuppressWarnings("rawtypes")
								Map.Entry pair = (Map.Entry)iterator.next();
						        itemFound = pair.getValue().toString().toLowerCase().equals(entityNames[index].toLowerCase());
						        if (itemFound) { 
						        	foundItemId = (Integer) pair.getKey(); 
						        	foundItemName = (String) pair.getValue();
						        }
						    }	
						}
						
						switch (listOfEntity)
						{
							case "listOfCountries":
								if (foundItemId > 0) {
									country = foundItemId.toString();
									urlParameters += "&chosenCountry=" + foundItemId.toString();
									selectedSearchCriteria.add("Country: " + foundItemName);
									// looking for best offer for current country
									List<viewBestOffersbyCountries> bestOffers = this.getBestOffersByCountry(foundItemId);
									request.setAttribute("countryBestOffers", bestOffers);
								} else {
									country = null;
									request.setAttribute("countryBestOffers", null);
								}
								break;
							case "listOfRegions":
								if (foundItemId > 0) {
									region = foundItemId.toString();
									urlParameters += "&chosenRegion=" + foundItemId.toString();
									selectedSearchCriteria.add("Region: " + foundItemName);
								} else {
									region = null;
								}
								break;
							case "listOfAppellations":
								if (foundItemId > 0) { 
									appellation = foundItemId.toString();
									urlParameters += "&appellationId=" + foundItemId.toString();
									selectedSearchCriteria.add("Appellation: " + foundItemName);
								} else {
									appellation = null;
								}
								break;
							case "listOfWineries":
								if (foundItemId > 0) {
									winery = foundItemId.toString();
									urlParameters += "&wineryId=" + foundItemId.toString();
									selectedSearchCriteria.add("Winery: " + foundItemName);
								} else {
									winery = null;
								}
								break;
							case "listOfGrapeVarieties":
								if (foundItemId > 0) {
									grapeVariety = foundItemId.toString();
									urlParameters += "&grapeVarietyId=" + foundItemId.toString();
									selectedSearchCriteria.add("Grape Variety: " + foundItemName);
								} else {
									grapeVariety = null;
								}
								break;
						}
					}
					index++;
				}
				
				if (name != null && !name.equals("")) {
					urlParameters += "&name=" + name;
					selectedSearchCriteria.add("Name: "+ name);
				}

				int intValue = 0;
				int minIntValue = 0;
				int maxIntValue = 0;
				float minFloatValue = 0;
				float maxFloatValue = 0;
				NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
				
				// Colour
				intValue = 0;
				if (chosenColour != null && !chosenColour.equals("0")) {
					try {
						intValue = Integer.parseInt(chosenColour);
					} catch (Exception e) {
						intValue = 0;
						e.printStackTrace();
					}	
					if (intValue > 0) { 
						urlParameters += "&chosenColour=" + chosenColour;
						selectedSearchCriteria.add("Colour: " + getColourName(intValue));
					}
				}

				// Vintage
				minIntValue = 0;
				maxIntValue = 0;
				if (vintageMin != null && !vintageMin.equals("")) {
					try {
						minIntValue = Integer.parseInt(vintageMin);
					} catch (Exception e) {
						minIntValue = 0;
						e.printStackTrace();
					}	
				}
				
				if (vintageMax != null && !vintageMax.equals("")) {
					try {
						maxIntValue = Integer.parseInt(vintageMax);
					} catch (Exception e) {
						maxIntValue = 0;
						e.printStackTrace();
					}	
				}
				
				if ( (minIntValue > 0) || ((maxIntValue > 0)) ) {
				
					if ( (minIntValue > 0) && ((maxIntValue > 0)) ) {
						
						urlParameters += "&vintageMin=" + vintageMin;
						urlParameters += "&vintageMax=" + vintageMax;
						selectedSearchCriteria.add("Vintage between " + vintageMin + " and " + vintageMax);
						
					} else {
						if (minIntValue > 0) { 
							urlParameters += "&vintageMin=" + vintageMin;
							selectedSearchCriteria.add("Vintage greater than " + vintageMin);
						} else {
							urlParameters += "&vintageMax=" + vintageMax;
							selectedSearchCriteria.add("Vintage less than " + vintageMax);
						}
					}
					
				}

				// ABV
				minFloatValue = 0;
				maxFloatValue = 0;
				if (abvMin != null && !abvMin.equals("")) {
					try {
						minFloatValue = Float.parseFloat(abvMin);
					} catch (Exception e) {
						minFloatValue = 0;
						e.printStackTrace();
					}	
				}
				
				if (abvMax != null && !abvMax.equals("")) {
					try {
						maxFloatValue = Float.parseFloat(abvMax);
					} catch (Exception e) {
						maxFloatValue = 0;
						e.printStackTrace();
					}	
				}
				
				if ( (minFloatValue > 0) || ((maxFloatValue > 0)) ) {
				
					if ( (minFloatValue > 0) && ((maxFloatValue > 0)) ) {
						
						urlParameters += "&abvMin=" + abvMin;
						urlParameters += "&abvMax=" + abvMax;
						selectedSearchCriteria.add("ABV between " + abvMin + " and " + abvMax);
						
					} else {
						if (minFloatValue > 0) { 
							urlParameters += "&abvMin=" + abvMin;
							selectedSearchCriteria.add("ABV greater than " + abvMin);
						} else {
							urlParameters += "&abvMax=" + abvMax;
							selectedSearchCriteria.add("ABV less than " + abvMax);
						}
					}
					
				}
				

				// price
				minFloatValue = 0;
				maxFloatValue = 0;
				
				if (minPrice != null && !minPrice.equals("")) {
					try {
						minFloatValue = Float.parseFloat(minPrice);
					} catch (Exception e) {
						minFloatValue = 0;
						e.printStackTrace();
					}	
				}
				
				if (maxPrice != null && !maxPrice.equals("")) {
					try {
						maxFloatValue = Float.parseFloat(maxPrice);
					} catch (Exception e) {
						maxFloatValue = 0;
						e.printStackTrace();
					}	
				}
				
				if ( (minFloatValue > 0) || ((maxFloatValue > 0)) ) {
					
					if ( (minFloatValue > 0) && ((maxFloatValue > 0)) ) {
						
						urlParameters += "&minPrice=" + minPrice;
						urlParameters += "&maxPrice=" + maxPrice;
						selectedSearchCriteria.add("Price between " + formatter.format(minFloatValue) + " and " + formatter.format(maxFloatValue));
						
					} else {
						if (minFloatValue > 0) { 
							urlParameters += "&minPrice=" + minPrice;
							selectedSearchCriteria.add("Price greater than " + formatter.format(minFloatValue));
						} else {
							urlParameters += "&maxPrice=" + maxPrice;
							selectedSearchCriteria.add("Price less than " + formatter.format(maxFloatValue));
						}
					}
					
				}
				
				
				if (rating != null && !rating.equals("")) {
					urlParameters += "&ratingValue=" + rating;
					selectedSearchCriteria.add("Rating Value: " + rating);
				}

				// merchant
				if(merchant != null && !merchant.equals("0")) { 
					int merchantInt = 0;
					try {
						merchantInt = Integer.parseInt(merchant);
					} catch (Exception e) {
						merchantInt = 0;
						System.out.println("REST / services / GeneralService / makeSearch: Exception parsing merchant = ["+merchant+"] to Int");
						e.printStackTrace();
					}
					if (merchantInt > 0) {
						// looking for merchant object based on merchantInt
						String merchantUrl = "merchantsView?action=getMerchant&id=" + merchant;
						String merchantString = requestCreator.createGetRequest(crudURL, merchantUrl); 
						JsonNode merchantJson = mapper.readTree(merchantString);
						if (merchantJson != null) {
							viewMerchants viewMerchant = mapper.treeToValue(merchantJson, viewMerchants.class);
							if ( (viewMerchant != null) && (viewMerchant.getId() > 0) ) {
								
								urlParameters +="&merchant=" + viewMerchant.getId();
								selectedSearchCriteria.add("Merchant: " + viewMerchant.getName());
								
								// looking for best offer for current merchant
						    	List<viewBestOffersbyMerchants> bestOffers = this.getBestOffersByMerchant(viewMerchant.getId());
						    	request.setAttribute("merchantBestOffers", bestOffers);
								
							} else { request.setAttribute("merchantBestOffers", null); }
						
						} else { request.setAttribute("merchantBestOffers", null); }
						
					} else { request.setAttribute("merchantBestOffers", null); }
			    	
			    	// Check the length of the search, 12 includes a merchant between 1 and 99 and no more params
			    	if(urlParameters.length() > 12) { request.setAttribute("severalAttributes", true); }
				}
				
				// wine type
				if(wineType != null && !wineType.equals(""))
				{ 
					int wineTypeInt = 0;
					try {
						wineTypeInt = Integer.parseInt(wineType);
					} catch (Exception e) {
						wineTypeInt = 0;
						System.out.println("REST / services / GeneralService / makeSearch: Exception parsing wineType = ["+wineType+"] to Int");
						e.printStackTrace();
					}
					if (wineTypeInt > 0) {
						// looking for wine type object based on wineTypeInt
						String typesUrl = "winetypes?action=getWineType&id=" + wineTypeInt;
						String typeString = requestCreator.createGetRequest(crudURL, typesUrl);
						JsonNode typeJson = mapper.readTree(typeString);
						if (typeJson != null) {
							tblWineTypes tblwineType= mapper.treeToValue(typeJson, tblWineTypes.class);
							if ( (tblwineType != null) && (tblwineType.getId() > 0) ) {
								
								urlParameters += "&chosenType=" + tblwineType.getId();
								selectedSearchCriteria.add("Product type: " + tblwineType.getName());
								
								// looking for best offer for current wine type
								List<viewBestOffersbyWineTypes> bestOffers = this.getBestOffersByWineType(tblwineType.getId());
								request.setAttribute("winetypeBestOffers", bestOffers);
								
							} else { request.setAttribute("winetypeBestOffers", null); }
							
						} else { request.setAttribute("winetypeBestOffers", null); }
						
					} else { request.setAttribute("winetypeBestOffers", null); }
				}
				
				// saving selectedSearchCriteria into a session variable 
				request.getSession().setAttribute("selectedSearchCriteria", selectedSearchCriteria);
				
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
				
				// getting wine search result
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
		
	}


	private String getColourName(int id) {
		if (id > 0 ) {
			try {
				String relURL = "colours?action=getColourNameById&id="+id;
				return requestCreator.createGetRequest(crudURL, relURL);
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}	
		} else { return ""; }
	}
	
	public Boolean sendEmail(String content, String email)
	{
		
		// Recipient's email ID needs to be mentioned.
		String to = email;
	
		// Sender's email ID needs to be mentioned
	    String from = "noreply@winedunk.com";
	
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
	        message.setSubject("Password changed successfully.");

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
		   	if (offersNodes != null) {
			   	Iterator<JsonNode> offersIterator = offersNodes.elements();
				List<viewBestOffersbyMerchants> bestOffers = new ArrayList<viewBestOffersbyMerchants>();
				
				while(offersIterator.hasNext())
				{
			   		JsonNode offerNode = offersIterator.next();
			   		viewBestOffersbyMerchants offer = mapper.treeToValue(offerNode, viewBestOffersbyMerchants.class);
			   		bestOffers.add(offer);
				}
				
				return bestOffers;
		   	} else {
		   		return null;
		   	}
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
		   	if (offersNodes != null) {
			   	Iterator<JsonNode> offersIterator = offersNodes.elements();
				List<viewBestOffersbyWineTypes> bestOffers = new ArrayList<viewBestOffersbyWineTypes>();
				
				while(offersIterator.hasNext())
				{
			   		JsonNode offerNode = offersIterator.next();
			   		viewBestOffersbyWineTypes offer = mapper.treeToValue(offerNode, viewBestOffersbyWineTypes.class);
			   		bestOffers.add(offer);
				}
				
				return bestOffers;
		   	} else {
		   		return null;
		   	}
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
			if (offersNodes != null) {
			   	Iterator<JsonNode> offersIterator = offersNodes.elements();
			   	List<viewBestOffersbyCountries> bestOffers = new ArrayList<viewBestOffersbyCountries>();
			   	
			   	while(offersIterator.hasNext())
			   	{
			   		JsonNode offerNode = offersIterator.next();
			   		viewBestOffersbyCountries offer = mapper.treeToValue(offerNode, viewBestOffersbyCountries.class);
			   		bestOffers.add(offer);
			   	}
			   	return bestOffers;
			} else {
				return null;
			}
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
			if (merchantNodes != null) {
			   	Iterator<JsonNode> merchantsIterator = merchantNodes.elements();
			   	List<viewMerchantsWithBestOffers> merchants = new ArrayList<viewMerchantsWithBestOffers>();
			   	
			   	while(merchantsIterator.hasNext())
				{
			   		JsonNode merchantNode = merchantsIterator.next();
			   		viewMerchantsWithBestOffers merchant = mapper.treeToValue(merchantNode, viewMerchantsWithBestOffers.class);
			   		merchants.add(merchant);
				}
			   	return merchants;
			} else {
		   		return null;
		   	}
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
			if (wineTypesNodes != null) {
				Iterator<JsonNode> wineTypesIterator = wineTypesNodes.elements();
				List<viewWineTypesWithBestOffers> wineTypes = new ArrayList<viewWineTypesWithBestOffers>();
				
				while(wineTypesIterator.hasNext())
				{
					JsonNode wineTypeNode = wineTypesIterator.next();
					viewWineTypesWithBestOffers wineType = mapper.treeToValue(wineTypeNode, viewWineTypesWithBestOffers.class);
					wineTypes.add(wineType);
				}
				return wineTypes;
			} else {
		   		return null;
		   	}
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
			if (countriesNodes != null) {
				Iterator<JsonNode> countriesIterator = countriesNodes.elements();
				List<viewCountriesWithBestOffers> countries = new ArrayList<viewCountriesWithBestOffers>();
				
				while(countriesIterator.hasNext())
				{
					JsonNode countryNode = countriesIterator.next();
					viewCountriesWithBestOffers country = mapper.treeToValue(countryNode, viewCountriesWithBestOffers.class);
					countries.add(country);
				}
				return countries;
			} else {
		   		return null;
		   	}
		}
		return null;
	}
}
