package priceComparison.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import priceComparison.models.tblUserBasket;
import priceComparison.models.tblUserWineReviews;
import priceComparison.models.tblUserWinesRatings;
import priceComparison.models.viewUsers;
import priceComparison.models.viewUsersWinesReviews;
import priceComparison.models.viewWinesMinimumPrice;
import priceComparison.services.ProductService;
import priceComparison.services.ResultsService;
import priceComparison.services.UserRatingsService;
import priceComparison.services.UserReviewsService;
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
	UserReviewsService reviewsService = new UserReviewsService(); 
	UserRatingsService ratingsService = new UserRatingsService(); 

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
		
		// Setting CRUD's path where needed
		reviewsService.setUrlPath(serviceProperties.getProperty("crud.url"));
		ratingsService.setUrlPath(serviceProperties.getProperty("crud.url"));
		productService.setUrlPath(serviceProperties.getProperty("crud.url"));
		resultsService.setUrlPath(serviceProperties.getProperty("crud.url"));
		validationService.setUrlPath(serviceProperties.getProperty("crud.url"));
		
		validationService.validateUser(request, response);
		
		String wineId = "";
		
		// Check request atts and parameters for the wineId
		if(request.getAttribute("id") != null)
		{
			// This is only accessed if the page is reloaded after writing a review
			// or giving a rating.
			
			 wineId = request.getAttribute("id").toString();
		} else
		{
			if(!request.getParameterMap().containsKey("id")) { return; }
			if(request.getParameter("id").equals("") || request.getParameter("id").equals(null)) { return; }
			wineId = request.getParameter("id");
		}
		
		if(wineId.equals("")) { return; }
		
		// Load filters for the search
		try { resultsService.loadFilters(request); } 
		catch (Exception e) { e.printStackTrace(); }
		
		
		
		// Load reviews
		try 
		{
			Integer countOfReviews = reviewsService.getCountForWine(Integer.parseInt(wineId)); 
			if( countOfReviews > 0)
			{
				request.setAttribute("amountOfReviews", countOfReviews);
				List<viewUsersWinesReviews> reviews = reviewsService.getReviewsForWine(Integer.parseInt(wineId));
				if(reviews != null) { request.setAttribute("reviewsList", reviews); }
			}
			
		} catch (Exception e) { e.printStackTrace(); }
		
		
		
		try
		{
			viewWinesMinimumPrice wine = productService.getWine(wineId);
			Integer amountOfRatings = ratingsService.getCountForWine(Integer.parseInt(wineId));
			
			// Check the amount of reviews and double check that there are ratings (i.e. not only ratings with value 0)
			if( amountOfRatings == null || amountOfRatings <= 0 ) { request.setAttribute("noRatings", true); }
			else { request.setAttribute("amountOfRatings", amountOfRatings); }
			if(wine.getAvgRating() == null || wine.getAvgRating() <= 0) { request.setAttribute("noRatings", true); }
			
			// Store the wine to be displayed and its shops' information
			request.setAttribute("wine", wine);
			request.setAttribute("priceComparisonList", productService.getPriceComparison(wineId));
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
		
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		//system's path separator
		final String sep = System.getProperty("file.separator");
		//load winedunk configuration
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		
		String formChosen = "";
		
		if(request.getParameter("formChosen") != null 
				&& !request.getParameter("formChosen").equals("")) { formChosen = request.getParameter("formChosen"); } 
		
		reviewsService.setUrlPath(serviceProperties.getProperty("crud.url"));
		ratingsService.setUrlPath(serviceProperties.getProperty("crud.url"));
		
		if(!formChosen.equals(""))
		{
			viewUsers user;
			Integer userId;
			Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
			tblUserWinesRatings rating = new tblUserWinesRatings();
			tblUserWineReviews review = new tblUserWineReviews(); 
			
			switch(formChosen)
			{
				case "basketForm" :
					
					// TODO ADD WINES TO THE DB IF THE USER IS REGISTERED
					
					/* 
					 * This is used to add a wine to the basket page. I first get the current wine's info
					 * Then try to get the current list of items in the basket, otherwise just create an empty list
					 * Finally add the wine to the list and persist the list to session.
					 * 
					 * This is accessed from an AJAX function on the product page, upon clicking the "add to basket" button
					 */
					
					if(request.getParameter("wineId") == null || request.getParameter("wineId").equals("")) 
					{ 
						response.sendError(HttpServletResponse.SC_BAD_REQUEST); 
						return;
					}
					
					viewWinesMinimumPrice currentWine = productService.getWine(request.getParameter("wineId"));
					Integer merchantId = Integer.parseInt(request.getParameter("merchantId"));
					String destinationURL = request.getParameter("destinationURL");
					Float productPrice = Float.valueOf(request.getParameter("productPrice"));
					
					
					List<tblUserBasket> products = new ArrayList<tblUserBasket>();
					tblUserBasket currentProduct = new tblUserBasket();
					
					if(session.getAttribute("basketList") != null) { products = (List<tblUserBasket>) session.getAttribute("basketList"); }
					
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			    	Date date = new Date();
			    	
			    	String dateString = dateFormat.format(date);
					
					currentProduct.setNumericWineId(currentWine.getWineId());
					currentProduct.setNumericShopId(merchantId);
					currentProduct.setDestinationURL(destinationURL);
					currentProduct.setProductPrice(productPrice);
					currentProduct.setDate(dateString);
					currentProduct.setTimestamp(date);
					
					products.add(currentProduct);
					
					session.setAttribute("basketList", products);
					session.setAttribute("amountOfItemsInBasket", products.size());
					response.getWriter().write(products.size());
					
				break;
					
				case "reviewsForm" :
					
					/*
					 * This code adds a new review. It checks whether the user is registered
					 * If it is, it then checks whether the user has already submitted a review 
					 * and asks if he prefers editing if so.
					 * Otherwise it constructs the data structure and sends a request
					 * to the CRUD API. Finally, it checks that it has been inserted.
					 * 
					 * It is called from an AJAX function on the product page, upon clicking the "submit" button.
					 */
					
					if(isLoggedIn == null || isLoggedIn == false)
					{
						request.setAttribute("id", request.getParameter("wineId"));
						request.setAttribute("userNotRegisteredReview", true);
						doGet(request, response);
						return;
					}
					
					// Get the user logged in
					user = (viewUsers) session.getAttribute("userLoggedIn");
					if(user == null) { response.getWriter().write("False"); return; }
					userId = user.getId();
					
					if(request.getParameter("reviewText") == null || request.getParameter("reviewText").equals(""))
					{
						if(request.getParameter("reviewTitle") == null || request.getParameter("reviewTitle").equals(""))
						{
							request.setAttribute("blanReview", true);
							request.setAttribute("id", request.getParameter("wineId"));
							doGet(request, response);
							return;
						}
					}
					
					// Check if the user has previously reviewed the wine
					if(reviewsService.hasReviewed(userId, Integer.parseInt(request.getParameter("wineId"))))
					{
						request.setAttribute("hasReviewed", true);
						request.setAttribute("id", request.getParameter("wineId"));
						doGet(request, response);
						return;
					}
					
					review.setNumericUserId(userId);
					review.setTitle(request.getParameter("reviewTitle"));
					review.setNumericWineId(Integer.parseInt(request.getParameter("wineId")));
					review.setComments(request.getParameter("reviewText"));
					
					
					try {
						
						// Try  to add the review
						if(reviewsService.addReview(review)) 
						{ 
							request.setAttribute("reviewSuccessful", true);
							
							// Check if the user has entered any rating as well and persist it to the DB separately
							if(!ratingsService.hasRated(userId, Integer.parseInt(request.getParameter("wineId"))))
							{
								if(request.getParameter("ratingOnReviewsForm") == null 
										|| request.getParameter("ratingOnReviewsForm").equals("")) { break; }
								
								try 
								{
									rating.setNumericUserId(userId);
									rating.setRating(Integer.parseInt(request.getParameter("ratingOnReviewsForm")));
									rating.setNumericWineId(Integer.parseInt(request.getParameter("wineId")));
									
									// Try to add the rating
									if(ratingsService.addRating(rating)) { request.setAttribute("ratingSuccessful", true); }
								} catch (Exception e) { e.printStackTrace(); }
							} else { request.setAttribute("hasRated", true); }
							
							// Set relevant parameters and reload page
							request.setAttribute("id", request.getParameter("wineId"));
							doGet(request, response);
							return;
						}
					} catch (Exception e) { e.printStackTrace(); }
					
				break;
				
				case "ratingsForm" :
					
					/*
					 * This code adds a new rating. It checks whether the user is registered
					 * If it is, it then checks whether the user has already submitted a rating 
					 * and asks if he prefers editing if so.
					 * Otherwise it constructs the data structure and sends a request
					 * to the CRUD API. Finally, it checks that it has been inserted.
					 * 
					 * It is called from an AJAX function on the product page, upon clicking the "submit" button.
					 */
					
					if(isLoggedIn == null || isLoggedIn == false)
					{
						request.setAttribute("id", request.getParameter("wineId"));
						request.setAttribute("userNotRegisteredRating", true);
						doGet(request, response);
						return;
					}

					// Get the user logged in
					user = (viewUsers) session.getAttribute("userLoggedIn");
					if(user == null) { response.getWriter().write("False"); return; }
					userId = user.getId();
					
					if(request.getParameter("ratingValue") == null ||request.getParameter("ratingValue").equals(""))
					{
						request.setAttribute("blankRating", true);
						request.setAttribute("id", request.getParameter("wineId"));
						doGet(request, response);
						return;
					}
					
					if(ratingsService.hasRated(userId, Integer.parseInt(request.getParameter("wineId"))))
					{
						request.setAttribute("hasRated", true);
						request.setAttribute("id", request.getParameter("wineId"));
						doGet(request, response);
						return;
					}
					
					rating.setNumericUserId(userId);
					rating.setRating(Integer.parseInt(request.getParameter("ratingValue")));
					rating.setNumericWineId(Integer.parseInt(request.getParameter("wineId")));
					
					try {
						if(ratingsService.addRating(rating)) 
						{ 
							request.setAttribute("ratingSuccessful", true);
							// Check if the user has previously reviewed the wine
							if(!reviewsService.hasReviewed(userId, Integer.parseInt(request.getParameter("wineId"))))
							{
								// Check if the user has entered review details
								if(request.getParameter("reviewsOnRatingForm") == null || request.getParameter("reviewsOnRatingForm").equals(""))
								{
									if(request.getParameter("reviewTitleOnRatingForm") == null 
											|| request.getParameter("reviewTitleOnRatingForm").equals("")) { break; }
								}
								
								try
								{
									review.setNumericUserId(userId);
									review.setTitle(request.getParameter("reviewTitleOnRatingForm"));
									review.setNumericWineId(Integer.parseInt(request.getParameter("wineId")));
									review.setComments(request.getParameter("reviewsOnRatingForm"));
									
									// Save the review
									if(reviewsService.addReview(review)) { request.setAttribute("reviewSuccessful", true); }
								} catch(Exception e) { e.printStackTrace(); }
							} else { request.setAttribute("hasReviewed", true); }
							
							request.setAttribute("id", request.getParameter("wineId"));
							doGet(request, response);
							return;
						}
					} catch (Exception e) { e.printStackTrace(); }
				break;
			}
		}
	}
}
