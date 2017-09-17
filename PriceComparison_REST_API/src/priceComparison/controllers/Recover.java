package priceComparison.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import priceComparison.models.tblUsers;
import priceComparison.services.GeneralService;
import priceComparison.services.ProfileService;
import priceComparison.services.RecoveringPasswordService;
import priceComparison.services.ValidationService;

/**
 * Servlet implementation class Recover
 */
@WebServlet("/Recover")
public class Recover extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ValidationService validationService = new ValidationService();
    RecoveringPasswordService recoveringService = new RecoveringPasswordService(); 
    ProfileService profileService = new ProfileService();
    GeneralService generalService = new GeneralService();
    
    public Recover() { super(); }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * On this method I get the requests coming from the emails
		 * that allow the user to recover their password. I first get
		 * the parameters, then get the relevant user from the CRUD and 
		 * finally display to the user the form to set new password.
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
		
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		//system's path separator
		final String sep = System.getProperty("file.separator");
		//load winedunk configuration
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		
		validationService.setUrlPath(serviceProperties.getProperty("crud.url"));
		profileService.setUrlPath(serviceProperties.getProperty("crud.url"));
		
		String validationToken = request.getParameter("token");
		Integer userId = Integer.parseInt(request.getParameter("uid"));
		
		if(validationToken == null || userId == null) { return; }
		
		session.setAttribute("recoveryToken", validationToken);
		session.setAttribute("recoveryUserId", userId);
		
		profileService.setUserId(userId);
		tblUsers user = profileService.loadUserDetails();
		
		if(user == null) { return; }
		
		if(user.getRecoveringPassToken().equals(validationToken))
		{
			RequestDispatcher recoverPasswordPage = request.getRequestDispatcher("WEB-INF/views/recoverPassword.jsp");
			recoverPasswordPage.forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * On this method I receive the passwords from the form
		 * Then I check that they're valid and encrypt them
		 * Finally I send a request to the CRUD to delete the token
		 * And edit the new passwords
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
		
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		//system's path separator
		final String sep = System.getProperty("file.separator");
		//load winedunk configuration
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		
		recoveringService.setCrudURL(serviceProperties.getProperty("crud.url"));
		
		if(!request.getParameter("formChosen").equals("recoverPassword")) { return; }
		if(request.getParameter("newPassword1") == null || request.getParameter("newPassword2") == null) { return; }
		
		String password1 = request.getParameter("newPassword1");
		String password2 = request.getParameter("newPassword2");
		
		if(!recoveringService.passwordsOK(password1, password2)) 
		{
			request.setAttribute("passwordsWrong", true);
			RequestDispatcher recoverPasswordPage = request.getRequestDispatcher("WEB-INF/views/recoverPassword.jsp");
			recoverPasswordPage.forward(request, response);
			return;
		} 
		
		try 
		{ 
			String encryptedNewPassword = recoveringService.encryptPassword(password1); 
			tblUsers user = new tblUsers();
			
			Integer userId = (Integer) session.getAttribute("recoveryUserId");
			String recoveryToken = (String) session.getAttribute("recoveryToken");
			
			user.setId(userId);
			user.setRecoveringPassToken(recoveryToken);
			user.setLoginPassword(encryptedNewPassword);
			if(!recoveringService.removeTokenAndSaveUser(user))
			{
				request.setAttribute("passedRecovery", false);
				RequestDispatcher recoverPasswordPage = request.getRequestDispatcher("WEB-INF/views/recoverPassword.jsp");
				recoverPasswordPage.forward(request, response);
				return;
			}
			request.setAttribute("passedRecovery", true);
			
			String email = recoveringService.composeEmailWithConfirmation();
			tblUsers actualUser = recoveringService.getUserById(userId);
			
			generalService.sendEmail(email, actualUser.getLoginEmail());
			RequestDispatcher loginPage = request.getRequestDispatcher("WEB-INF/views/login.jsp");
			loginPage.forward(request, response);
		} 
		catch (Exception e) { e.printStackTrace(); return; }
	}
}
