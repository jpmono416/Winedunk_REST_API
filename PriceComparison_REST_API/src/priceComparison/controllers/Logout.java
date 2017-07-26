package priceComparison.controllers;

import java.io.FileInputStream;
import java.io.IOException;
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

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HomeService homeService = new HomeService();
	GeneralService generalService = new GeneralService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * This simple method is called when the user clicks the "log out" link on the page
		 * And destroys all login evidence on the users's device
		 */
        request.getSession(true).invalidate();
        
        
        RequestDispatcher homePage = request.getRequestDispatcher("WEB-INF/views/home.jsp");
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		//system's path separator
		final String sep = System.getProperty("file.separator");
		//load winedunk configuration
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		
		//Set URL path of CRUD API for 
		homeService.setCrudURL(serviceProperties.getProperty("crud.url"));
		
		//Delete user validation token cookie
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) 
		{
			if (cookie.getName().equals("uvt")) { cookie.setMaxAge(0); }
		}
		
		try 
		{ 
			generalService.loadRecommendedWines();
			List<viewRecommendedWines> recommendedWines = generalService.getRecommendedWines();
			request.getSession().setAttribute("recommendedWines", recommendedWines);
		} 
		catch (Exception e) { e.printStackTrace(); }
		
		homePage.forward(request, response);
	}
}