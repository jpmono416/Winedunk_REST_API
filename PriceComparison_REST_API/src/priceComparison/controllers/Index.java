package priceComparison.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Index
 */
@WebServlet("/555")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Index() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * On this servlet I get all the requests incoming to the page and then redirect to the relevant page 
		 * Or to an error page in case the URL was not found.
		 * I do this to allow the user to type the URL address without the worry of case sensitivity issues 
		 */
		String path = request.getPathInfo();
		System.out.println(path);
		System.out.println(response.isCommitted());
		switch(path.toLowerCase())
		{
			case "/home" : { response.sendRedirect("Home"); break; }
			case "/aboutus" : { response.sendRedirect("AboutUs"); break; }
			case "/login" : { response.sendRedirect("Login"); break; }
			case "/logout" : { response.sendRedirect("Logout"); break; }
			case "/privacypolicy" : { response.sendRedirect("PrivacyPolicy"); break; }
			case "/product" : { response.sendRedirect("Product"); break; }
			case "/results" : { response.sendRedirect("Results"); break; }
			case "/termsofuse" : { response.sendRedirect("TermsOfUse"); break; }
		}
	}
}
