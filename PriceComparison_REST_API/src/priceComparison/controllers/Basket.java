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

import priceComparison.models.viewWinesMinimumPrice;
import priceComparison.services.ResultsService;
import priceComparison.services.ValidationService;

/**
 * Servlet implementation class Results
 */
@WebServlet("/Basket")
public class Basket extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	ValidationService validationService = new ValidationService();
	ResultsService sFService = new ResultsService();
    public Basket() { super(); }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Persist session
		HttpSession session = request.getSession();
		if (request.getParameter("JSESSIONID") != null) {
		    Cookie userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
		    response.addCookie(userCookie);
		} else {
		    String sessionId = session.getId();
		    Cookie userCookie = new Cookie("JSESSIONID", sessionId);
		    response.addCookie(userCookie);
		}

		//Get the CRUD URL
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		final String sep = System.getProperty("file.separator");
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		
		validationService.setUrlPath(serviceProperties.getProperty("crud.url"));
		validationService.validateUser(request, response);
		
		RequestDispatcher basketPage = request.getRequestDispatcher("WEB-INF/views/basket.jsp");
		basketPage.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * Here I manage what happens with the different actions of the basket buttons.
		 * I take them away from the user's basket page if relevant. If not,
		 * I just take them away from the session
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
		
		// If no wines are chosen, do nothing
		if(request.getParameter("action") == null || request.getParameter("listOfWines") == null) { return; } //TODO SEND ERROR TO AJAX
		
		// Get the action that needs to be done and the list of wines, without unwanted characters
		String action = request.getParameter("action");
		// List of IDs, comma separated
		String chosenWines = request.getParameter("listOfWines").replaceAll("[\" \\[ \\]]", "");
		
		switch(action)
		{
			// In either case, remove everything
			case "buyAll" :
			case "removeAll" :
				session.removeAttribute("basketList");
				break;
			
			// Iterate over the list and create a list with only the wines that need to be removed
			case "buy" :
			case "remove" : 
				List<viewWinesMinimumPrice> wines = (List<viewWinesMinimumPrice>) session.getAttribute("basketList");
				List<viewWinesMinimumPrice> winesToBeDeleted = new ArrayList<viewWinesMinimumPrice>();
				
				for (String id : chosenWines.split(","))
				{
					if(wines.get(Integer.parseInt(id)) != null)
					{
						winesToBeDeleted.add(wines.get(Integer.parseInt(id)));
					}
				}
				
				// Remove relevant wines and persist new list of items to basket
				wines.removeAll(winesToBeDeleted);
				session.setAttribute("basketList", wines);
				break;
		}
	}
}