package priceComparison.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import priceComparison.services.ViewWineReviewsAndRatingByUsersService;

/**
 * Servlet implementation class ReviewsAndRatingByUsers
 */
@WebServlet("/reviewsAndRatingByUsers")
public class ReviewsAndRatingByUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ViewWineReviewsAndRatingByUsersService viewWineReviewsAndRatingByUsersService = new ViewWineReviewsAndRatingByUsersService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewsAndRatingByUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if ( (!request.getParameterMap().containsKey("entity")) || (!request.getParameterMap().containsKey("action")) ) { return; }
		
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

		viewWineReviewsAndRatingByUsersService.setUrlPath(serviceProperties.getProperty("crud.url"));
		
		String entity = request.getParameter("entity");
		String action = request.getParameter("action");
		Boolean result = false;
		
		switch(entity) {
			case "rating" : {

				Integer ratingId = 0;
				try {
					ratingId = Integer.parseInt(request.getParameter("ratingId"));
				} catch (Exception e) {
					ratingId = 0;
				}
			    
		    	switch(action)  {
					case "update" : {
												
					    if ( (ratingId != null) && (ratingId > 0) ) {
					    
					    	// updating existing rating
					    	Integer ratingValue = null;
					    	try {
								ratingValue = Integer.parseInt(request.getParameter("ratingValue"));
							} catch (Exception e) {
								ratingValue = null;
							}
							
							if (ratingValue != null) {
								
								if (ratingValue > 0) {
									
									result = viewWineReviewsAndRatingByUsersService.updateRating(ratingId, ratingValue);
									response.setStatus(200);
									response.getWriter().write(result.toString());
									
								}
								
							} else {
								response.setStatus(204);
								response.getWriter().write("false");
							}
					    } else {
					    
					    	// inserting new rating
					    	
					    	Integer userId = 0;
					    	Integer wineId = 0;
					    	Integer ratingValue = 0;
							try {
								userId = Integer.parseInt(request.getParameter("userId"));
								wineId = Integer.parseInt(request.getParameter("wineId"));
								ratingValue = Integer.parseInt(request.getParameter("ratingValue"));
							} catch (Exception e) {
								userId = 0;
								wineId = 0;
								ratingValue = 0;
							}
							
							if ( (userId != null) && (userId > 0) && (wineId != null) && (wineId > 0) ) {
								
								result = viewWineReviewsAndRatingByUsersService.InsertRating(userId, wineId, ratingValue);
								response.setStatus(200);
								response.getWriter().write(result.toString());
								
							} else {
								response.setStatus(204);
								response.getWriter().write("false");
							}
					    }
						
						break;
						
					}
				
					case "delete" : {

					    if ( (ratingId != null) && (ratingId > 0) ) {
							result = DeleteRating(ratingId);
							response.setStatus(200);
							response.getWriter().write(result.toString());
					    } else {
							response.setStatus(204);
							response.getWriter().write("false");
						}
					    
						break;
					}
	
				}	
			
			}
			case "review" : {

				Integer reviewId = 0;
				try {
					reviewId = Integer.parseInt(request.getParameter("reviewId"));
				} catch (Exception e) {
					reviewId = 0;
				}
			    
		    	switch(action)  {
					case "update" : {

						String reviewTitle = null;
						String reviewComments = null;
						
						try {
							reviewTitle = request.getParameter("reviewTitle");
							reviewComments = request.getParameter("reviewComments");
						} catch (Exception e) {
							reviewTitle = null;
							reviewComments = null;
						}
						
						if (reviewComments != null) {
						
							if ( (reviewId != null) && (reviewId > 0) ) {
							
								// updating existing review
								if (!reviewComments.equals("")) {
									
									result = viewWineReviewsAndRatingByUsersService.updateReview(reviewId, reviewTitle, reviewComments);
									response.setStatus(200);
									response.getWriter().write(result.toString());
									
								}
							} else {
								
								// inserting new review
								Integer userId = 0;
						    	Integer wineId = 0;
								try {
									userId = Integer.parseInt(request.getParameter("userId"));
									wineId = Integer.parseInt(request.getParameter("wineId"));
								} catch (Exception e) {
									userId = 0;
									wineId = 0;
								}
								
								if ( (userId != null) && (userId > 0) && (wineId != null) && (wineId > 0) && (!reviewComments.equals(""))) {
									
									result = viewWineReviewsAndRatingByUsersService.InsertReview(userId, wineId, reviewTitle, reviewComments);
									response.setStatus(200);
									response.getWriter().write(result.toString());
									
								} else {
									response.setStatus(204);
									response.getWriter().write("false");
								}
							}
							
						} else {
							response.setStatus(204);
							response.getWriter().write("false");
						}
						
						break;
						
					}
				
					case "delete" : {
				
						if ( (reviewId != null) && (reviewId > 0) ) {
							result = DeleteReview(reviewId);
							response.setStatus(200);
							response.getWriter().write(result.toString());
						} else {
							response.setStatus(204);
							response.getWriter().write("false");
						}
						
						break;
					}
	
				}
			
			}
		}
				
	}

	private Boolean DeleteRating(Integer ratingId) {
		if ( (ratingId != null) && (ratingId > 0) ) {
			return viewWineReviewsAndRatingByUsersService.deleteRating(ratingId);
		} else {
			return false;	
		}
	}

	private Boolean DeleteReview(Integer reviewId) {
		if ( (reviewId != null) && (reviewId > 0) ) {
			return viewWineReviewsAndRatingByUsersService.deleteReview(reviewId);
		} else {
			return false;	
		}
	}

}
