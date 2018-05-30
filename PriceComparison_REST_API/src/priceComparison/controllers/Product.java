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
import priceComparison.models.tblUsers;
import priceComparison.models.tblWines;
import priceComparison.models.viewUsers;
import priceComparison.models.viewWinePriceComparison;
import priceComparison.models.viewWinesMinimumPrice;
import priceComparison.services.FavouriteWinesService;
import priceComparison.services.ProductService;
import priceComparison.services.ResultsService;
import priceComparison.services.UserRatingsService;
import priceComparison.services.UserReviewsService;
import priceComparison.services.UsersService;
import priceComparison.services.ValidationService;
import priceComparison.services.WinesService;

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
	WinesService winesService = new WinesService();
	UsersService usersService = new UsersService();
	FavouriteWinesService favouriteWinesService = new FavouriteWinesService();
	

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
		favouriteWinesService.setUrlPath(serviceProperties.getProperty("crud.url"));
		
		validationService.validateUser(request, response);

		String wineIdStr = "";
		Integer wineId = 0;
		
		if(request.getAttribute("currentWineId") != null) {
			 wineIdStr = request.getAttribute("currentWineId").toString();
		} else {
			
			if ( (request.getParameter("id") != null) && (request.getParameterMap().containsKey("id")) && (!request.getParameter("id").equals(null)) ) {
				try {
					wineIdStr = request.getParameter("id");
				} catch (Exception e) {
					wineIdStr = "";
				}
			}
		}
		
		try {
			wineId = Integer.parseInt(wineIdStr);
		} catch (Exception e) {
			wineId = 0;
		}
		
		
		if (wineId > 0) {
			
			try { 
				// Load filters for the search
				resultsService.loadStaticFilters(request); 
				resultsService.loadCountriesFilters(request);
				
				viewWinesMinimumPrice wine = productService.getWine(wineId.toString());
				tblUsers user = new tblUsers();
				
				// user
				if ( (session.getAttribute("isLoggedIn") != null) && (session.getAttribute("userLoggedIn") != null) ){
					try {
						viewUsers viewUser = (viewUsers) session.getAttribute("userLoggedIn");
						if ( (viewUser != null) && (viewUser.getId() > 0) ){
							user.setId(viewUser.getId());
						} else {
							user = null;
						}
					} catch (Exception e) {
						user = null;
					}
				}
				
				// rating management 
				
				//amount of ratings (from DB)
				Integer amountOfRatings = ratingsService.getCountForWine(wineId);
				if( amountOfRatings == null || amountOfRatings <= 0 ) { amountOfRatings = 0; }
				request.setAttribute("ratingsTotalAmount", amountOfRatings);
				
				// average rating (from DB)
				Float AvgRatings = ratingsService.getAvgForWine(wineId);
				if( AvgRatings == null || AvgRatings <= 0 ) { AvgRatings = (float) 0; }
				request.setAttribute("ratingsAvg", AvgRatings);
				
				// user rating value (from DB)
				Integer userRatingsValue = null;
				if ( (user != null) && (user.getId() != null) ) { userRatingsValue = ratingsService.getUserRatingValue(wineId, user.getId()); }
				if( userRatingsValue == null || userRatingsValue <= 0 ) { userRatingsValue = 0; }
				request.setAttribute("ratingsUserRatingValue", userRatingsValue);
				

				// reviews management 
				
				//amount of reviews (from DB)
				Integer amountOfReviews = reviewsService.getCountForWine(wineId); 
				if( amountOfReviews == null || amountOfReviews <= 0 ) { amountOfReviews = 0; }
				request.setAttribute("reviewsTotalAmount", amountOfReviews);
				
				// Load reviews
				if( amountOfReviews > 0) {
					List<tblUserWineReviews> productReviews = reviewsService.getReviewsForWine(wineId);
				
					if (productReviews != null) {
						request.setAttribute("reviewsList", productReviews);
					} else {
						request.setAttribute("reviewsList", null);
					}
				}
				
				// user has provide a review (from DB)
				Boolean userHasReviewed = reviewsService.hasReviewed(user.getId(), wineId);
				if ( userHasReviewed == null ) { userHasReviewed = false; }
				request.setAttribute("reviewsUserHasReviewed", userHasReviewed);
			
				// favourite wines management
				
				// amount of time flagged as favourite
				Integer amountOfTimesAsFavourite = favouriteWinesService.getCountForWine(wineId); 
				if( amountOfTimesAsFavourite == null || amountOfTimesAsFavourite <= 0 ) { amountOfTimesAsFavourite = 0; }
				request.setAttribute("favouritedTotalAmount", amountOfTimesAsFavourite);
				
				// wine flagged as favourite by this user (from DB)
				Boolean wineInUserFavouriteWines = false;
				if ( (user != null) && (user.getId() != null) && (user.getId() > 0) ) { wineInUserFavouriteWines = favouriteWinesService.isWineFlaggedAsFavouriteWineForUser(user.getId(), wineId); };
				if (wineInUserFavouriteWines==null) { wineInUserFavouriteWines = false; }
				request.setAttribute("wineFlaggedAsFavourite", wineInUserFavouriteWines);
				
				// Store the wine to be displayed and its shops' information
				request.setAttribute("wine", wine);
				List<viewWinePriceComparison> priceComparison = productService.getPriceComparison(wineId.toString());
				if (priceComparison != null) { request.setAttribute("priceComparisonList", priceComparison); }
				productPage.forward(request, response);
				
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} else {

			// redirecting to results page
			RequestDispatcher resultsPage = request.getRequestDispatcher("WEB-INF/views/results.jsp");
			resultsPage.forward(request, response); 
		}
		
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
		winesService.setUrlPath(serviceProperties.getProperty("crud.url"));
		usersService.setUrlPath(serviceProperties.getProperty("crud.url"));
		
		if(!formChosen.equals("")) {
			
			// aripe: I'm changing the original logic of this process considering it is not working as expected

			// basic and mandatory values
			tblUsers user = new tblUsers();
			tblWines wine = new tblWines();

			tblUserWinesRatings rating = new tblUserWinesRatings();
			tblUserWineReviews review = new tblUserWineReviews(); 
			
			// wine
			if (request.getParameter("wineId") != null) {
				Integer wineId = 0;
				try {
					wineId = Integer.parseInt(request.getParameter("wineId"));
				} catch (Exception e) {
					wineId = 0;
					System.out.println("/Product/DoPost() | Error while parsing request.getParameter(\"wineId\") into Integer value");
				}
				if (wineId > 0 ) {
					try {
						wine = winesService.getWineById(wineId);
					} catch (Exception e) {
						wine = null;
					}
				} else {
					wine = null;
				}
			}
			
			// user
			if ( (session.getAttribute("isLoggedIn") != null) && (session.getAttribute("userLoggedIn") != null) ){
				try {
					viewUsers viewUser = (viewUsers) session.getAttribute("userLoggedIn");
					if ( (viewUser != null) && (viewUser.getId() > 0) ){
						user.setId(viewUser.getId());
					} else {
						user = null;
					}
				} catch (Exception e) {
					user = null;
				}
			}
			
			if ( (user != null) && (user.getId() > 0) && (wine != null) && (wine.getId() > 0) ){
				// at least both user and wine are ok, so we move forward

				request.setAttribute("currentWineId", wine.getId());
				
				switch(formChosen) {
				
					case "feedbackForm" : {
						
						// managing rating
						Integer ratingInt = 0;
						if ( (request.getParameter("ratingValue") != null) && (!request.getParameter("ratingValue").equals("")) ) {
						
							try {
								ratingInt = Integer.parseInt(request.getParameter("ratingValue"));
							} catch (Exception e) {
								ratingInt = 0;
							}
							if ( ratingInt > 0 ) {
								
								// checking if user has already rated this wine
								if (!ratingsService.hasRated(user.getId(), wine.getId())) {
									
									rating.setUserId(user);
									rating.setWineId(wine);
									rating.setRating(ratingInt);
	
									try {
										
										ratingsService.addRating(rating);
										
									} catch (Exception e) {
										e.printStackTrace();
									}	
								}
							}
							
						}
						
						// managing review
						// Check if the user has previously reviewed the wine
						if (!reviewsService.hasReviewed(user.getId(), wine.getId())) { // there is no previous review
							
							// Check if the user has entered review details
							String reviewTitle = "";
							String reviewText = "";
							
							if ( (request.getParameter("reviewTitle") != null) && (request.getParameter("reviewText") != null) ){
								
								try {
									reviewTitle = request.getParameter("reviewTitle");
									reviewText = request.getParameter("reviewText");
								} catch (Exception e) {
									reviewTitle = "";
									reviewText = "";
								}
								
								if ( (!reviewTitle.equals("")) && (!reviewText.equals("")) ) {
									
									// checking if user has already reviewed this wine
									if (!reviewsService.hasReviewed(user.getId(), wine.getId())) {
										// no previous review
										
										review.setUserId(user);
										review.setWineId(wine);
										review.setTitle(reviewTitle);
										review.setComments(reviewText);

										// Adding review
										try {
											reviewsService.addReview(review);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
							}
						}

						doGet(request, response);
						return;
						
					} // case "feedbackForm"

					
					case "basketForm" : {
						
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
						
					} // end of case "basketForm"
					
			
				} // end of switch
				
			} else {
				response.getWriter().write("False");
				doGet(request, response);
				return;
			}
			
		} // end of if(!formChosen.equals(""))
	}
}
