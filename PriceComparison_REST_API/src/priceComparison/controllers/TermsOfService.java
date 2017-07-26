package priceComparison.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import priceComparison.services.ResultsService;
import priceComparison.services.ValidationService;

/**
 * Servlet implementation class Results
 */
@WebServlet("/TermsOfService")
public class TermsOfService extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	ResultsService sFService = new ResultsService();
	ValidationService validationService = new ValidationService();

    public TermsOfService() { super(); }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Get the CRUD URL
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		final String sep = System.getProperty("file.separator");
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
				
		validationService.setUrlPath(serviceProperties.getProperty("crud.url"));
		validationService.validateUser(request, response);
		
		RequestDispatcher privacyPolicyPage = request.getRequestDispatcher("WEB-INF/views/termsOfService.jsp");
		privacyPolicyPage.forward(request, response);
	}
}