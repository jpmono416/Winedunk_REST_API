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
import javax.servlet.http.HttpSession;

import priceComparison.models.tblUsers;
import priceComparison.models.userEmails;
import priceComparison.models.userPhoneNumbers;
import priceComparison.models.viewUserWinesRatings;
import priceComparison.models.viewUsers;
import priceComparison.models.viewUsersWinesReviews;
import priceComparison.models.viewWines;
import priceComparison.services.FavouriteWinesService;
import priceComparison.services.ProfileService;
import priceComparison.services.UserEmailAddressesService;
import priceComparison.services.UserPhoneNumbersService;
import priceComparison.services.UserRatingsService;
import priceComparison.services.UserReviewsService;
import priceComparison.services.ValidationService;

/**
 * Servlet implementation class Results
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	ProfileService profileService = new ProfileService();
	ValidationService validationService = new ValidationService();
	FavouriteWinesService favWinesService = new FavouriteWinesService();
	UserPhoneNumbersService phoneNumbersService = new UserPhoneNumbersService();
	UserEmailAddressesService emailAddressService = new UserEmailAddressesService();
	UserReviewsService reviewsService = new UserReviewsService(); 
	UserRatingsService ratingsService = new UserRatingsService();
	
    public Profile() { super(); }
    
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
		
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		final String sep = System.getProperty("file.separator");
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		
		viewUsers user = (viewUsers) session.getAttribute("userLoggedIn");
		if(user == null) { request.getRequestDispatcher("/Login").forward(request, response); return; }
		Integer userId = user.getId();
		
		// Check that any user is logged in
		if(!userId.equals("")) { profileService.setUserId(userId); }
		else { return; }
		
		// Set CRUD's URL where needed
		String crudURL = serviceProperties.getProperty("crud.url");
		profileService.setUrlPath(crudURL);
		reviewsService.setUrlPath(crudURL);
		ratingsService.setUrlPath(crudURL);
		validationService.setUrlPath(crudURL);
		validationService.validateUser(request, response);
		
		//Load various user lists
		try
		{
			List<userEmails> emailsList = emailAddressService.loadEmailAddresses();
			if(emailsList != null) { request.setAttribute("emailsList", emailsList); }
			else { request.setAttribute("noEmails", true);}
		} catch(Exception e) { e.printStackTrace(); }
		
		try {
			List<userPhoneNumbers> phoneNumbers = phoneNumbersService.loadPhoneNumbers();
			if(phoneNumbers != null) { request.setAttribute("phoneNumbersList", phoneNumbers); }
			else { request.setAttribute("noPhoneNumbers", true);}
		} catch(Exception e) { e.printStackTrace(); }
		
		try {
			List<viewWines> favouriteWines = profileService.loadFavouriteWines();
			if(favouriteWines != null) { request.setAttribute("favouriteWines", favouriteWines); }
			else { request.setAttribute("noFavourite", true);}
		} catch(Exception e) { e.printStackTrace(); }
		
		try {
			List<viewUsersWinesReviews> reviews = reviewsService.getReviewsForUser(userId);
			if(reviews != null) { request.setAttribute("userReviews", reviews); }
			else { request.setAttribute("noReviews", true); }
		} catch(Exception e) { e.printStackTrace(); }
		
		try {
			List<viewUserWinesRatings> ratingsList = ratingsService.getRatingsForUser(userId);
			if(ratingsList != null) { request.setAttribute("ratingsList", ratingsList); }
			else { request.setAttribute("noRatings", true); }
		} catch(Exception e) { e.printStackTrace(); }
		
		tblUsers userToBeLoaded = profileService.loadUserDetails();
		request.setAttribute("userForDetails", userToBeLoaded);
		
		if(session.getAttribute("sectionToBeDisplayed") == null) { session.setAttribute("sectionToBeDisplayed", "user"); } 
		RequestDispatcher profilePage = request.getRequestDispatcher("WEB-INF/views/profile.jsp");

		profilePage.forward(request, response);
	}

	@Override
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
		
		 // Get the action that needs to be done
		String formChosen = request.getParameter("formChosen");
		if(formChosen == null || formChosen.equals("")) { return; }
		
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		//system's path separator
		final String sep = System.getProperty("file.separator");
		//load winedunk configuration
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		
		profileService.setUrlPath(serviceProperties.getProperty("crud.url"));
		favWinesService.setUrlPath(serviceProperties.getProperty("crud.url"));
		
		viewUsers user = (viewUsers) session.getAttribute("userLoggedIn");
		if(user == null) { response.getWriter().write("False"); return; }
		Integer userId = user.getId();
		
		if(userId > 0 ) { profileService.setUserId(userId); }
		else { return; }
		
		
		if(session.getAttribute("error") != null) { session.removeAttribute("error"); }
		if(session.getAttribute("successful") != null) { session.removeAttribute("successful"); }
		
		
		try
		{
			/*
			 * Here I check for all of the methods that can be executed from the profile page
			 * Or auxiliar pages such as the basket page, to add a wine to favourite,
			 * Which is a user process as well. I do what's relevant to each case.
			 * Session attribute "successful" is used to display a message on screen
			 * Session attribute "sectionToBeDisplayed" is used to display the same settings
			 * That the user was viewing.
			 */
			switch (formChosen)
			{
				case "addPhoneNumber" :
					String phoneNumber = request.getParameter("phoneNumber");
					
					if(phoneNumber.equals("")) { break; }
					
					if(!phoneNumbersService.addPhoneNumber(phoneNumber))
					{
						request.setAttribute("error", true); 
						break;
					}
					
					session.setAttribute("successful", true); 
					session.setAttribute("sectionToBeDisplayed", "contact");

					break;
					
				case "editPhoneNumber" :
					String editPhoneNumber = request.getParameter("phoneNumber");
					String numberId = request.getParameter("numberId");
					if(editPhoneNumber.equals("")) { break; }
					
					if(!phoneNumbersService.editPhoneNumber(editPhoneNumber, numberId))
					{
						request.setAttribute("error", true); 
						break;
					}
					
					session.setAttribute("successful", true);
					session.setAttribute("sectionToBeDisplayed", "contact");
					
					break;
					
				case "deletePhoneNumber" :
					if(request.getParameter("phoneNumberId").equals("")) { break; }
					
					String phoneNumberToDeleteId = request.getParameter("phoneNumberId");
					if(!phoneNumbersService.deletePhoneNumber(phoneNumberToDeleteId))
					{
						request.setAttribute("error", true); 
						break;
					}
					
					session.setAttribute("successful", true); 
					session.setAttribute("sectionToBeDisplayed", "contact");
					
					break;
					
				case "addEmailAddress" :
					String emailAddress = request.getParameter("emailAddress");
					
					if(emailAddress.equals("")) { break; }
					
					if(!emailAddressService.addEmailAddress(emailAddress))
					{
						request.setAttribute("error", true);
						break;
					}
					
					session.setAttribute("successful", true); 
					session.setAttribute("sectionToBeDisplayed", "contact");
					
					break;
				
				case "editEmailAddress" :
					String editEmailAddress = request.getParameter("emailAddress");
					String emailId = request.getParameter("emailId");
					
					if(editEmailAddress.equals("")) { break; }
					if(!profileService.validateEmail(editEmailAddress)) { session.setAttribute("error", true); break; }
					if(!emailAddressService.editEmailAddress(editEmailAddress, emailId))
					{
						request.setAttribute("error", true); 
						break;
					}
					
					session.setAttribute("successful", true); 
					session.setAttribute("sectionToBeDisplayed", "contact");
					
					break;
					
				case "deleteEmailAddress" :
					if(request.getParameter("emailId").equals("")) { break; }
					
					String emailToDeletedId = request.getParameter("emailId");
					if(!emailAddressService.deleteEmailAddress(emailToDeletedId))
					{
						request.setAttribute("error", true); 
						break;
					}
					
					session.setAttribute("successful", true); 
					session.setAttribute("sectionToBeDisplayed", "contact");
					
					break;
					
				case "editReview" :
					String editReview = request.getParameter("review");
					String reviewId = request.getParameter("reviewId");
					String wineId = request.getParameter("reviewWineId");
					
					if(editReview.equals("")) { break; }
					reviewsService.setUserId(userId);
					
					if(!reviewsService.editReview(editReview, reviewId, wineId))
					{
						request.setAttribute("error", true); 
						break;
					}
					
					session.setAttribute("successful", true);
					session.setAttribute("sectionToBeDisplayed", "wineReviews");
				break;
				
				case "deleteReview" :
					if(request.getParameter("reviewId").equals("")) { break; }
					
					String reviewToBeDeletedId = request.getParameter("reviewId");
					if(!reviewsService.deleteReview(reviewToBeDeletedId))
					{
						request.setAttribute("error", true); 
						break;
					}
					
					session.setAttribute("successful", true);
					session.setAttribute("sectionToBeDisplayed", "wineReviews");
				break;
				
				case "editRating" :
					String editRating = request.getParameter("ratingValue");
					String ratingId = request.getParameter("ratingId");
					String ratingWineId = request.getParameter("ratingWineId");
					System.out.println("EDIT: " + editRating + ", ID: " + ratingId + ", WineId: " + ratingWineId); //TODO DELETE
					if(editRating.equals("")) { break; }
					ratingsService.setUserId(userId);
					if(!ratingsService.editRating(editRating, ratingWineId, ratingId))
					{
						request.setAttribute("error", true);
						break;
					}
					
					session.setAttribute("successful", true);
					session.setAttribute("sectionToBeDisplayed", "wineReviews");
				break;
				
				case "deleteRating" : 
					if(request.getParameter("ratingId").equals("")) { break; }
					
					String ratingToBeDeletedId = request.getParameter("ratingId");
					if(!ratingsService.deleteRating(ratingToBeDeletedId))
					{
						request.setAttribute("error", true); 
						break;
					}
					
					session.setAttribute("successful", true);
					session.setAttribute("sectionToBeDisplayed", "wineReviews");
				break;
				case "changePassword" :
					if(request.getParameter("previousPassword").equals("") || request.getParameter("newPassword").equals("")) { break; }
					
					profileService.setPreviousPassword(request.getParameter("previousPassword"));
					profileService.setNewPassword(request.getParameter("newPassword"));
					
					if(!profileService.changePassword())
					{
						request.setAttribute("error", true); 
						break;
					}
					
					session.setAttribute("successful", true); 
					session.setAttribute("sectionToBeDisplayed", "user");
					
					break;
					
				case "editDetails" : 
					
					tblUsers userToEdit = new tblUsers();
					String name = request.getParameter("userName");
					String preferredEmail = request.getParameter("preferredEmail");
					String recoveringEmail = request.getParameter("recoveringEmail");
					
					userToEdit.setId(userId);
					if(name != null && !name.equals("") ) { userToEdit.setName(name); }
					
					if(preferredEmail != null && !preferredEmail.equals("")) 
					{ 
						if(!profileService.validateEmail(preferredEmail)) 
						{ 
							session.setAttribute("error", true); 
							break; 
						}
						userToEdit.setPreferredEmail(preferredEmail); 
					}
					
					if(recoveringEmail != null && !recoveringEmail.equals("")) 
					{ 
						if(!profileService.validateEmail(recoveringEmail)) 
						{ 
							session.setAttribute("error", true); 
							break; 
						}
						userToEdit.setRecoveringPassEmail(recoveringEmail); 
					}
					if(!profileService.updateUser(userToEdit))
					{
						request.setAttribute("error", true); 
						break;
					}

					session.setAttribute("successful", true); 
					session.setAttribute("sectionToBeDisplayed", "user");
					
					break;
					
				case "addFavouriteWine" :
					Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
					
					if(isLoggedIn == null || isLoggedIn == false)
					{
						response.getWriter().write("False");
						return;
					}
					
					Integer favouriteWineId = Integer.parseInt(request.getParameter("wineId"));
					favWinesService.addFavouriteWine(userId, favouriteWineId);
					response.getWriter().write("True");
					return;
					
				case "deleteFavouriteWine" :
					Integer favWineId = Integer.parseInt(request.getParameter("wineId"));
					
					if(!favWinesService.deleteFavouriteWine(userId, favWineId))
					{
						session.setAttribute("error", true); 
						return;
					}
					
					session.setAttribute("successful", true); 
					session.setAttribute("sectionToBeDisplayed", "favouriteWines");
					
					return;
			}
		} catch (Exception e) { e.printStackTrace(); session.setAttribute("error", true); }
		doGet(request, response);
	}
	
}