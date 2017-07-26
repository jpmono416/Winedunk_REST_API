package priceComparison.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import priceComparison.services.LoginService;
import priceComparison.services.ValidationService;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	LoginService loginService = new LoginService();
	
	ValidationService validationService = new ValidationService();
	
    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * On this method, I first of all get the URL of the CRUD API from the .properties file
		 * Then I set its value to a property of the user validation class
		 * Finally, I try to validate whether the user is logged in and then redirect
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
		
		RequestDispatcher loginPage = request.getRequestDispatcher("WEB-INF/views/login.jsp");
		
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		//system's path separator
		final String sep = System.getProperty("file.separator");
		//load winedunk configuration
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		validationService.setUrlPath(serviceProperties.getProperty("crud.url"));
		
		if(validationService.validateUser(request, response)) { response.sendRedirect("Home"); return; }
		
		//Identify which form to be loaded
		if(!request.getParameterMap().containsKey("action")) 		{ request.getSession().setAttribute("signUp", false); }
		else if(request.getParameter("action").equals("signUp")) 	{ request.getSession().setAttribute("signUp", true);  }
		else if(request.getParameter("action").equals("login")) 	{ request.getSession().setAttribute("signUp", false); }
		
		loginPage.forward(request, response);
	}

}
