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

import priceComparison.models.viewRecommendedWines;
import priceComparison.services.GeneralService;
import priceComparison.services.HomeService;
import priceComparison.services.ResultsService;
import priceComparison.services.ValidationService;


@WebServlet("/Share")
public class Share extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ResultsService resultsService = new ResultsService();	
	GeneralService generalService = new GeneralService();
	HomeService homeService = new HomeService();
	ValidationService validationService = new ValidationService();

    public Share() { super(); }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		//system's path separator
		final String sep = System.getProperty("file.separator");
		//load winedunk configuration
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		
		//Set URL path of CRUD API
		resultsService.setUrlPath(serviceProperties.getProperty("crud.url"));
		resultsService.loadFilters(request);
		
		//Make search
		generalService.makeSearch(request, serviceProperties.getProperty("crud.url"));
		
		/*
		 * All of the code below is required to load the results page properly
		 */
		Integer currentPage = 0;
		String amountOfPages = "";
		if(request.getAttribute("amountOfPages") != null) { amountOfPages = request.getAttribute("amountOfPages").toString(); }
		
		if(request.getSession().getAttribute("currentPage") != null) { currentPage = (Integer) request.getSession().getAttribute("currentPage"); } 
		
		if(currentPage <= 0)
		{
			currentPage = 1;
			request.getSession().setAttribute("currentPage", currentPage);
		}
		
		if(currentPage < 1) { currentPage = 1; }
		
		RequestDispatcher resultsPage = request.getRequestDispatcher("WEB-INF/views/results.jsp");
		
		//Set URL path of CRUD API
		homeService.setCrudURL(serviceProperties.getProperty("crud.url"));
		resultsService.setUrlPath(serviceProperties.getProperty("crud.url"));

		validationService.setUrlPath(serviceProperties.getProperty("crud.url"));
		validationService.validateUser(request, response);
		
		resultsService.loadFilters(request);
		
		if(request.getAttribute("noResults") == null) { request.setAttribute("noResults", false); }
		
		if( (Boolean) request.getAttribute("noResults") != true && request.getAttribute("resultsList") == null) 
		{
			
			amountOfPages = resultsService.getCountOfPages("&currentPage=" + currentPage).toString();
			Cookie amountOfPagesCookie = new Cookie("amountOfPages", amountOfPages);
			response.addCookie(amountOfPagesCookie);
			
			request.setAttribute("resultsList", resultsService.getWines("&currentPage=" + currentPage));
		}
		
		if(amountOfPages.equals("")) { amountOfPages = "0"; }
		
		try 
		{ 
			generalService.loadRecommendedWines();
			List<viewRecommendedWines> recommendedWines = generalService.getRecommendedWines();
			request.getSession().setAttribute("recommendedWines", recommendedWines);
		} catch (Exception e) { e.printStackTrace(); }
		
		if(request.getAttribute("currentPage") == null) { request.setAttribute("currentPage", currentPage); }
		if(request.getAttribute("amountOfPages") == null) { request.setAttribute("amountOfPages", amountOfPages); }
		
		resultsPage.forward(request, response);
	}
}