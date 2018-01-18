package priceComparison.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import priceComparison.models.viewRecommendedWines;
import priceComparison.models.viewUsers;
import priceComparison.services.GeneralService;
import priceComparison.services.ResultsService;
import priceComparison.services.SavedSearchesService;
import priceComparison.services.ValidationService;


@WebServlet("/Results")
public class Results extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	ResultsService resultsService = new ResultsService();	
	GeneralService generalService = new GeneralService();
	ValidationService validationService = new ValidationService();
	SavedSearchesService searchesService = new SavedSearchesService();

    public Results() { super(); }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * This method is accessed when the Results page needs to be loaded
		 * First of all I check if the current page of results exists. If not, I create it
		 * I check for any previous results set. If there is none,
		 * I store in session a new list of results (which is a default list) 
		 */
		
		HttpSession session = request.getSession();
		if (request.getParameter("JSESSIONID") != null) {
		    Cookie userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
		    response.addCookie(userCookie);
		} else {
		    String sessionId = session.getId();
		    Cookie userCookie = new Cookie("JSESSIONID", sessionId);
		    response.addCookie(userCookie);
		}
		
		Integer currentPage = 0,
				amountOfPages = 0;
		
		//Set amount of pages and current page from previous searches
		if(session.getAttribute("amountOfPages") != null) { amountOfPages = (Integer) session.getAttribute("amountOfPages"); }
		if(session.getAttribute("currentPage") != null) { currentPage = (Integer) session.getAttribute("currentPage"); } 

		if(currentPage <= 0)
		{
			currentPage = 1;
			session.setAttribute("currentPage", currentPage);
		}
		
		//Get the CRUD URL
		RequestDispatcher resultsPage = request.getRequestDispatcher("WEB-INF/views/results.jsp");
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		final String sep = System.getProperty("file.separator");
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		
		//Set URL path of CRUD API and load filters
		generalService.setCrudURL(serviceProperties.getProperty("crud.url"));
		resultsService.setUrlPath(serviceProperties.getProperty("crud.url"));
		validationService.setUrlPath(serviceProperties.getProperty("crud.url"));
		resultsService.loadFilters(request);
		validationService.validateUser(request, response);
		
		//Avoid the "there are no results" card if there are results
		if(request.getAttribute("noResults") == null) { request.setAttribute("noResults", false); }
		
		//Check if the request comes from a "sorting" action, a normal request or a search
		if(session.getAttribute("sessionResults") == null) 
		{
			if( (Boolean) request.getAttribute("noResults") != true && request.getAttribute("resultsList") == null) 
			{
				amountOfPages = (Integer) resultsService.getCountOfPages("&currentPage=" + currentPage);
				session.setAttribute("amountOfpages", amountOfPages);
				request.setAttribute("resultsList", resultsService.getWines("&currentPage=" + currentPage));
			}
		} else { request.setAttribute("resultsList", session.getAttribute("sessionResults")); }
		
		if(amountOfPages <= 0) { amountOfPages = 0; } //TODO CHECK
		
		//Get recommended wines
		try 
		{ 
			generalService.checkRecommended(request);
		} catch (Exception e) { e.printStackTrace(); }
		
		
		//Load pagination numbers
		List<Integer> paginationNumbersList = new ArrayList<Integer>();
		Integer counter = currentPage - 2;
		
		if(counter < 1 ) { counter = 1; }
		if(counter + 4 > amountOfPages) { counter = amountOfPages - 4; }
		for(int i = counter; i <= counter +4 || i == amountOfPages; i++) { if(i >= 1) { paginationNumbersList.add(i); } }

		//Check there is no variable left for setting
		if(session.getAttribute("currentPage") == null) { session.setAttribute("currentPage", currentPage); }
		if(session.getAttribute("amountOfPages") == null) { session.setAttribute("amountOfPages", amountOfPages); }
		if(session.getAttribute("paginationList") == null) { session.setAttribute("paginationList", paginationNumbersList); }
		if(request.getAttribute("sharingURL") == null) { request.setAttribute("sharingURL", "?filters=default");}
		resultsPage.forward(request, response);
	}

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
		
		
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		//system's path separator
		final String sep = System.getProperty("file.separator");
		//load winedunk configuration
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		
		//Set URL path of CRUD API
		resultsService.setUrlPath(serviceProperties.getProperty("crud.url"));
		generalService.setCrudURL(serviceProperties.getProperty("crud.url"));
		searchesService.setUrlPath(serviceProperties.getProperty("crud.url"));
		
		Integer currentPage = 0,
				amountOfPages = 0;
		String lastSearch 	= "",
				orderBy		= "",
				formChosen 	= "";

		
		// Get session attributes if they're not null, to use the values later
		if(session.getAttribute("currentPage") !=null) 		{ currentPage = (Integer) session.getAttribute("currentPage"); }
		if(session.getAttribute("orderBy") != null) 		{ orderBy = session.getAttribute("orderBy").toString(); }
		if(session.getAttribute("lastSearch") != null) 		{ lastSearch = session.getAttribute("lastSearch").toString(); }
		if(session.getAttribute("amountOfPages") != null) 	{ amountOfPages = (Integer) session.getAttribute("amountOfPages"); }
		
		// Avoid an error in pagination if currentPage has a weird rogue value
		if(currentPage < 1) { currentPage = 1; } 
		
		
		if(request.getParameter("formChosen") != null 
				&& !request.getParameter("formChosen").equals("")) { formChosen = request.getParameter("formChosen"); } 
		
		if(!formChosen.equals(""))
		{
			switch (formChosen)  
			{
				/*
				 * This part of the code is accessed when the current pagination of results changes - change page
				 * This will identify whether it needs to go to the next or previous page and change the session variable 
				 * that states in which page we are, then reload the page. It will not persist a new search.
				 */
				case "next" : 
					currentPage++;
					break;
				case "previous" : 
					if(currentPage > 1) { currentPage--; }
					break;
				case "last" :
					currentPage = amountOfPages;
					break;
				case "first" :
					currentPage = 1;
					break;
				case "sort" : 
					String sortingMethod = "";
					
					switch(request.getParameter("sortingMethod"))
					{
						case "1" :
							sortingMethod = "ASC";
							break;
							
						case "2" :
							sortingMethod = "DESC";
							break;
							
						default : 
							break;
					}
					if(!sortingMethod.equals(""))
					{
						currentPage = 1;
						
						lastSearch += "&order=" + sortingMethod;
						lastSearch += "&currentPage=" + currentPage;
						
						List<Integer> paginationNumbersList = new ArrayList<Integer>();
						Integer counter = currentPage - 2;
						
						if(counter < 1 ) { counter = 1; }
						if(counter + 4 > amountOfPages) { counter = amountOfPages - 4; }
						for(int i = counter; i <= counter +4 || i == amountOfPages; i++) { if(i >= 1) { paginationNumbersList.add(i); } }
						
						session.setAttribute("sessionResults", resultsService.getWines(lastSearch));
						session.setAttribute("currentPage", currentPage);
						session.setAttribute("amountOfPages", amountOfPages);
						session.setAttribute("paginationList", paginationNumbersList);
						session.setAttribute("orderBy", sortingMethod);
						response.getWriter().write("passed");
						return;
					}
					
					break;
					
				case "number" :
					String value = request.getParameter("value");
					currentPage = Integer.parseInt(value);
				break;
				
				case "addSavedSearch" :
					viewUsers userLoggedIn = (viewUsers) request.getSession().getAttribute("userLoggedIn");
					if(userLoggedIn == null || (userLoggedIn != null && userLoggedIn.getId() <= 0 )) 
					{ 
						request.setAttribute("userNotRegistered", true);
						break; 
					}
					
					searchesService.setUserId(userLoggedIn.getId());
					searchesService.setSearchUrl(request.getParameter("searchUrl"));
					searchesService.setSearchName(request.getParameter("savedSearchName"));
					
					if(searchesService.addSavedSearch())
					{
						request.setAttribute("searchSaved", true);
					}
				break;
			}

			lastSearch += "&currentPage=" + currentPage;
			if(!orderBy.equals("")) { lastSearch += "&order=" + orderBy; }
			
			List<Integer> paginationNumbersList = new ArrayList<Integer>();
			Integer counter = currentPage - 2;
			
			if(counter < 1 ) { counter = 1; }
			if(counter + 4 > amountOfPages) { counter = amountOfPages - 4; }
			for(int i = counter; i <= counter + 4 || i == amountOfPages; i++) { if(i >= 1) { paginationNumbersList.add(i); } }
			
			request.setAttribute("resultsList", resultsService.getWines(lastSearch));
			if(request.getAttribute("resultsList") == null) { request.setAttribute("emptyList", true); }
			
			session.removeAttribute("sessionResults"); //Delete results if a previous "sort" was made
			session.setAttribute("amountOfPages", amountOfPages);
			session.setAttribute("paginationList", paginationNumbersList);
			session.setAttribute("currentPage", currentPage);
			RequestDispatcher resultsPage = request.getRequestDispatcher("WEB-INF/views/results.jsp");
			resultsPage.forward(request, response);
			return;
		}
		
		
		/*
		 * This part is accessed when a search is made using the left-hand panel either on Results or Product page
		 * It is also accessed from the Home page's search bar.
		 * I access a general method that gets the parameters of the applied filters and make a search
		 */
		
		resultsService.loadFilters(request);
		generalService.makeSearch(request, serviceProperties.getProperty("crud.url"));
		doGet(request, response);
	}
}