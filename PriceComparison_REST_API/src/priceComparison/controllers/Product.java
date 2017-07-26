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
import priceComparison.services.ProductService;
import priceComparison.services.ResultsService;
import priceComparison.services.ValidationService;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Product")
public class Product extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ProductService productService = new ProductService();	
	ValidationService validationService = new ValidationService();
	ResultsService resultsService = new ResultsService();

    public Product() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * On this method I first get the URL of the CRUD API. Then I look for the product ID on the URL
		 * Then I get the wine and its prices' information and set it on request variables
		 * And load the filters panel's information on the left
		 * Finally, I load the page up on screen  
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
		
		RequestDispatcher productPage = request.getRequestDispatcher("WEB-INF/views/product.jsp");
		
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		//system's path separator
		final String sep = System.getProperty("file.separator");
		//load WineDunk configuration
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		
		resultsService.setUrlPath(serviceProperties.getProperty("crud.url"));
		validationService.setUrlPath(serviceProperties.getProperty("crud.url"));
		validationService.validateUser(request, response);
		
		
		if(!request.getParameterMap().containsKey("id")) { return; }
		if(request.getParameter("id").equals("") || request.getParameter("id").equals(null)) { return; }
		
		try  { resultsService.loadFilters(request); } 
		catch (Exception e) { e.printStackTrace(); }
		
		try
		{
			productService.setUrlPath(serviceProperties.getProperty("crud.url"));
			

			// Store the wine to be displayed and its shops's information
			request.setAttribute("wine", productService.getWine(request.getParameter("id")));
			request.setAttribute("priceComparisonList", productService.getPriceComparison(request.getParameter("id")));
			productPage.forward(request, response);
		} catch (Exception e) { e.printStackTrace(); }
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
		
		if(request.getParameter("wineId") == null || request.getParameter("wineId").equals("")) 
		{ 
			response.sendError(HttpServletResponse.SC_BAD_REQUEST); 
			return;
		}
		
		/* 
		 * This is used to add a wine to the basket page. I first get the current wine's info
		 * Then try to get the current list of items in the basket, otherwise just create an empty list
		 * Finally add the wine to the list and persist the list to session.
		 * 
		 * This is accessed from an AJAX function on the product page, upon clicking the "add to basket" button
		 */
		viewWinesMinimumPrice currentWine = productService.getWine(request.getParameter("wineId"));
		
		List<viewWinesMinimumPrice> wines = new ArrayList<viewWinesMinimumPrice>();
		
		if(session.getAttribute("basketList") != null) { wines = (List<viewWinesMinimumPrice>) session.getAttribute("basketList"); }
		
		wines.add(currentWine);
		session.setAttribute("basketList", wines);
		session.setAttribute("amountOfItemsInBasket", wines.size());
		response.getWriter().write(wines.size());
	}
}
