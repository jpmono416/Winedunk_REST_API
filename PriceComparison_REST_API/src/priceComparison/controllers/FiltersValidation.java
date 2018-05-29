package priceComparison.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import priceComparison.services.ResultsService;

/**
 * Servlet implementation class FiltersValidation
 */
@WebServlet("/FiltersValidation")
public class FiltersValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ResultsService resultsService = new ResultsService();	
       
    public FiltersValidation() {
        super();
        // constructor
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		if (request.getParameter("JSESSIONID") != null) {
		    Cookie userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
		    response.addCookie(userCookie);
		} else {
		    String sessionId = session.getId();
		    Cookie userCookie = new Cookie("JSESSIONID", sessionId);
		    response.addCookie(userCookie);
		}
		
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if (request.getParameter("JSESSIONID") != null) {
		    Cookie userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
		    response.addCookie(userCookie);
		} else {
		    String sessionId = session.getId();
		    Cookie userCookie = new Cookie("JSESSIONID", sessionId);
		    response.addCookie(userCookie);
		}
		
		if ( (!request.getParameterMap().containsKey("entity")) || (!request.getParameterMap().containsKey("action")) ) { return; }
		
		String entity = request.getParameter("entity");
		String action = request.getParameter("action");
		
		switch(entity) {
			case "country" : {
		
				switch(action)  {
					case "validateCountryByNameAndReturnRegionsAutocompleteLists" : {
						
						// initializing listOfRegions, listOfAppellations and listOfWineries
						request.getSession().setAttribute("listOfRegions", null);
				 		request.getSession().setAttribute("listOfAppellations", null);
				 		request.getSession().setAttribute("listOfWineries", null);
						
						response.setContentType("text/plain");
						if (request.getParameterMap().containsKey("countryName")) {
							
							String countryName;
							try {
								countryName = request.getParameter("countryName");
							} catch (Exception e) {
								countryName = "";
							}
							
						    if ( (countryName != null) && (!countryName.equals("")) ) {
								
						    	Map<Integer, String> countryMap = new HashMap<Integer, String>();
								countryMap = (Map<Integer, String>) request.getSession().getAttribute("listOfCountries");
								if (countryMap != null) {
									
									boolean countryFound = false;
									Integer foundCountryId = 0;
									String foundCountryName = "";
									
									Iterator<Entry<Integer, String>> iterator = countryMap.entrySet().iterator();
								    while ( (!countryFound) && (iterator.hasNext()) ) {
								        @SuppressWarnings("rawtypes")
										Map.Entry pair = (Map.Entry)iterator.next();
								        countryFound = pair.getValue().toString().toLowerCase().equals(countryName.toLowerCase());
								        if (countryFound) { 
								        	foundCountryId = (Integer) pair.getKey(); 
								        	foundCountryName = (String) pair.getValue();
								        } else {
								        	foundCountryId = 0;
								        	foundCountryName = "";
								        }
								    }
								    if (countryFound) {
										
								    	//Get the CRUD URL
										final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
										final String sep = System.getProperty("file.separator");
										final Properties serviceProperties = new Properties();
										serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
								    	resultsService.setUrlPath(serviceProperties.getProperty("crud.url"));
										
										// loading listOfRegions
								    	resultsService.loadRegionFilters(request, foundCountryId);
								    	
								    	ArrayList<String> regionsList = resultsService.getRegionFilters(foundCountryId);
								    	
								    	String formattedRegionsList = "[";
								    	for (String region : regionsList)
								    		formattedRegionsList = formattedRegionsList.concat("\"").concat(region).concat("\",");
								    	formattedRegionsList = formattedRegionsList.concat("[END]").replace(",[END]", "]");
								    	
								    	String responseJson = "{\"country\": {\"countryName\": \""+foundCountryName+"\"}, \"regions\": {\"list\": "+formattedRegionsList+"}}";
								    	
								    	response.setStatus(200);
								    	response.getWriter().write(responseJson);
										break;
								    
								    } else {
								    	response.setStatus(204);
								    	response.getWriter().write("");
										break;
								    }
									
								} else {
							    	response.setStatus(204);
									response.getWriter().write("");
									break;
								}
								
							} else {	
						    	response.setStatus(204);
								response.getWriter().write("");
								break;
							}
							
						} else {
					    	response.setStatus(204);
							response.getWriter().write("");
							break;
						}
					}
					
				}
				break;
			}
			
			case "region" : {
		
				switch(action)  {
					case "validateRegionByNameAndReturnAppellationsAutocompleteLists" : {
						
						// initializing listOfAppellations and listOfWineries
				 		request.getSession().setAttribute("listOfAppellations", null);
				 		request.getSession().setAttribute("listOfWineries", null);
						
						response.setContentType("text/plain");
						if (request.getParameterMap().containsKey("regionName")) {
							
							String regionName;
							try {
								regionName = request.getParameter("regionName");
							} catch (Exception e) {
								regionName = "";
							}
							
						    if ( (regionName != null) && (!regionName.equals("")) ) {
								
						    	Map<Integer, String> regionMap = new HashMap<Integer, String>();
						    	regionMap = (Map<Integer, String>) request.getSession().getAttribute("listOfRegions");
								if (regionMap != null) {
									
									boolean regionFound = false;
									Integer foundId = 0;
									String foundName = "";
									
									Iterator<Entry<Integer, String>> iterator = regionMap.entrySet().iterator();
								    while ( (!regionFound) && (iterator.hasNext()) ) {
								        @SuppressWarnings("rawtypes")
										Map.Entry pair = (Map.Entry)iterator.next();
								        regionFound = pair.getValue().toString().toLowerCase().equals(regionName.toLowerCase());
								        if (regionFound) { 
								        	foundId = (Integer) pair.getKey(); 
								        	foundName = (String) pair.getValue();
								        } else {
								        	foundId = 0;
								        	foundName = "";
								        }
								    }
								    if (regionFound) {
										
								    	//Get the CRUD URL
										final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
										final String sep = System.getProperty("file.separator");
										final Properties serviceProperties = new Properties();
										serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
								    	resultsService.setUrlPath(serviceProperties.getProperty("crud.url"));
										
										// loading listOfAppellations
								    	resultsService.loadAppellationFilters(request, foundId);
								    	
								    	ArrayList<String> appellationsList = resultsService.getAppellationFilters(foundId);
								    	String formattedAppellationsList = "[";
								    	for (String appellation : appellationsList)
								    		formattedAppellationsList = formattedAppellationsList.concat("\"").concat(appellation).concat("\",");
								    	formattedAppellationsList = formattedAppellationsList.concat("[END]").replace(",[END]", "]");
								    	
								    	String responseJson = "{\"region\": {\"regionName\": \""+foundName+"\"}, \"appellations\": {\"list\": "+formattedAppellationsList+"}}";
								    	
								    	response.setStatus(200);
								    	response.getWriter().write(responseJson);
										break;
								    
								    } else {
								    	response.setStatus(204);
								    	response.getWriter().write("");
										break;
								    }
									
								} else {
							    	response.setStatus(204);
									response.getWriter().write("");
									break;
								}
								
							} else {	
						    	response.setStatus(204);
								response.getWriter().write("");
								break;
							}
							
						} else {
					    	response.setStatus(204);
							response.getWriter().write("");
							break;
						}
					}
					
				}
				break;
			}
			
			case "appellation" : {
		
				switch(action)  {
					case "validateAppellationByNameAndReturnWineriesAutocompleteLists" : {
						
						// initializing listOfAppellations and listOfWineries
				 		request.getSession().setAttribute("listOfWineries", null);
						
						response.setContentType("text/plain");
						if (request.getParameterMap().containsKey("appellationName")) {
							
							String appellationName;
							try {
								appellationName = request.getParameter("appellationName");
							} catch (Exception e) {
								appellationName = "";
							}
							
						    if ( (appellationName != null) && (!appellationName.equals("")) ) {
								
						    	Map<Integer, String> appellationMap = new HashMap<Integer, String>();
						    	appellationMap = (Map<Integer, String>) request.getSession().getAttribute("listOfAppellations");
								if (appellationMap != null) {
									
									boolean appellationFound = false;
									Integer foundId = 0;
									String foundName = "";
									
									Iterator<Entry<Integer, String>> iterator = appellationMap.entrySet().iterator();
								    while ( (!appellationFound) && (iterator.hasNext()) ) {
								        @SuppressWarnings("rawtypes")
										Map.Entry pair = (Map.Entry)iterator.next();
								        appellationFound = pair.getValue().toString().toLowerCase().equals(appellationName.toLowerCase());
								        if (appellationFound) { 
								        	foundId = (Integer) pair.getKey(); 
								        	foundName = (String) pair.getValue();
								        } else {
								        	foundId = 0;
								        	foundName = "";
								        }
								    }
								    if (appellationFound) {
										
								    	//Get the CRUD URL
										final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
										final String sep = System.getProperty("file.separator");
										final Properties serviceProperties = new Properties();
										serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
								    	resultsService.setUrlPath(serviceProperties.getProperty("crud.url"));
										
										// loading listOfWineries
								    	resultsService.loadWineryFilters(request, foundId);
								    	
								    	ArrayList<String> wineriesList = resultsService.getWineryFilters(foundId);
								    	String formattedWineriesList = "[";
								    	for (String winery : wineriesList)
								    		formattedWineriesList = formattedWineriesList.concat("\"").concat(winery).concat("\",");
								    	formattedWineriesList = formattedWineriesList.concat("[END]").replace(",[END]", "]");
								    	
								    	String responseJson = "{\"appellation\": {\"appellationName\": \""+foundName+"\"}, \"wineries\": {\"list\": "+formattedWineriesList+"}}";
								    	
								    	response.setStatus(200);
								    	response.getWriter().write(responseJson);
										break;
								    
								    } else {
								    	response.setStatus(204);
								    	response.getWriter().write("");
										break;
								    }
									
								} else {
							    	response.setStatus(204);
									response.getWriter().write("");
									break;
								}
								
							} else {	
						    	response.setStatus(204);
								response.getWriter().write("");
								break;
							}
							
						} else {
					    	response.setStatus(204);
							response.getWriter().write("");
							break;
						}
					}
					
				}
				break;
			}
			
			case "winery" : {
		
				switch(action)  {
					case "validateWineryByName" : {
						
						response.setContentType("text/plain");
						if (request.getParameterMap().containsKey("wineryName")) {
							
							String wineryName;
							try {
								wineryName = request.getParameter("wineryName");
							} catch (Exception e) {
								wineryName = "";
							}
							
						    if ( (wineryName != null) && (!wineryName.equals("")) ) {
								
						    	Map<Integer, String> wineryMap = new HashMap<Integer, String>();
						    	wineryMap = (Map<Integer, String>) request.getSession().getAttribute("listOfWineries");
								if (wineryMap != null) {
									
									boolean wineryFound = false;
									Integer foundId = 0;
									String foundName = "";
									
									Iterator<Entry<Integer, String>> iterator = wineryMap.entrySet().iterator();
								    while ( (!wineryFound) && (iterator.hasNext()) ) {
								        @SuppressWarnings("rawtypes")
										Map.Entry pair = (Map.Entry)iterator.next();
								        wineryFound = pair.getValue().toString().toLowerCase().equals(wineryName.toLowerCase());
								        if (wineryFound) { 
								        	foundId = (Integer) pair.getKey(); 
								        	foundName = (String) pair.getValue();
								        } else {
								        	foundId = 0;
								        	foundName = "";
								        }
								    }
								    if (wineryFound) {
								    	
								    	String responseJson = "{\"winery\": {\"wineryName\": \""+foundName+"\"}}";
								    	
								    	response.setStatus(200);
								    	response.getWriter().write(responseJson);
										break;
								    
								    } else {
								    	response.setStatus(204);
								    	response.getWriter().write("");
										break;
								    }
									
								} else {
							    	response.setStatus(204);
									response.getWriter().write("");
									break;
								}
								
							} else {	
						    	response.setStatus(204);
								response.getWriter().write("");
								break;
							}
							
						} else {
					    	response.setStatus(204);
							response.getWriter().write("");
							break;
						}
					}
					
				}
				break;
			}
			
		}
	}

}
