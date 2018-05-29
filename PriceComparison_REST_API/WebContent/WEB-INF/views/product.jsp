<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
    <c:import url="../templates/head.jsp"/>
    <c:import url="../templates/google_analytics.jsp"/>
</head>

<body style="background:#f7f7f7;">
    <div id="background"></div>
    <c:import url="../templates/navbar.jsp"/>
    <fmt:setLocale value="en_GB"/>
    <section style="margin-top:150px;">
        <div class="container">
        
           	<%-- left-hand column --%>
           	<div class="col-sm-3">
           	
           		<%-- Product summary panel --%>
           		<div class="row">
           		
           			<%-- lowest price and main actions --%>
         				<div class="col-md-12">
						<div class="card settingsCard">
							<h3 class="text-center">Lowest price:</h3>
							<div align="center">
					            <%-- Making sure both % off and was are displayed only if saving --%>
				                <c:choose>
									<c:when test="${requestScope.wine.getMoneySaving() > 0}">
						                <div class="row"> <span class="label label-default">Save <fmt:formatNumber type="currency" value="${requestScope.wine.getMoneySaving()}"/> ( -<c:out value="${requestScope.wine.getPercentageOff()}"/>% )</span> </div>
						                <div class="row"> <h5 class="text-center" style="text-decoration: line-through;">Was: <fmt:formatNumber type="currency" value="${requestScope.wine.getPreviousMaxPrice()}"/></h5> </div>
						                <div class="row"> <h4 class="text-center red-text" style="margin-top:0;">Now: <fmt:formatNumber type="currency" value="${requestScope.wine.getMinimumPrice()}"/></h4> </div>
									</c:when>
									<c:otherwise>
									    <div class="row"> <h3 class="text-center red-text" style="margin-top:0;"><fmt:formatNumber type="currency" value="${requestScope.wine.getMinimumPrice()}"/></h3> </div>
									</c:otherwise>
								</c:choose>
				            </div> 
							<p class="text-center">At: <strong><c:out value="${requestScope.wine.getMinimumPriceShopName()}"/></strong></p>
							<hr class="sep-bar">
							<button class="btn btn-primary btn-block secondaryButton" id="toBasketButton" onclick="addToCart(<c:out value="${requestScope.wine.getWineId()}"/>)">To basket</button>
							<button class="btn btn-primary btn-block secondaryButton" id="toFavouriteButton" onclick="addToFav(<c:out value="${requestScope.wine.getWineId()}"/>)"><i class="fa fa-heart linkIcon"></i>Add to favourites</button>
							<a class="btn btn-primary btn-block redButton" target="_blank" style="text-decoration: none;" href="<c:out value="${requestScope.wine.getMinimumPriceDestinationURL() }"/>">Buy now</a>
							<div id="successfulMessage" role="alert" class="alert alert-success" style="margin-top:10px; margin-bottom: 0; display:none;"><span><strong>Added to basket!</strong></span></div>
							<div id="errorMessage" role="alert" class="alert alert-danger" style="margin-top:10px; margin-bottom: 0; display:none;"><span><strong>We encountered a problem</strong></span></div>
							
							<div id="successfulFavMessage" role="alert" class="alert alert-success text-center" style="margin-top:10px; margin-bottom: 0; display:none;" data-toggle="tooltip" title="To review your favourite wines please refer to your profile page."><a href="Profile?section=favouriteWines">Added to favourites! <i class="fa fa-thumbs-o-up red-text" style="font-size: 20px;"></i></a></div>
							
						</div>
					</div>
					
           			<%-- rating --%>
           			<div class="col-md-12">
						<div class="card settingsCard">
							<h3 class="text-center">Ratings </h3>
							<hr class="sep-bar" />
							
							<%-- ratingsTotalAmount --%>
							<c:if test="${ requestScope.ratingsTotalAmount > 0 }">
								<c:set var = "bicolourStarNumber" value = "${requestScope.ratingsAvg - (requestScope.ratingsAvg % 1)}"/>
								<c:set var = "colourchangePercentage" value = "${(requestScope.ratingsAvg % 1) * 100}"/>
								
								
								<h1 class="text-center">
									<span id="stars">
									
										<%-- full yellow stars--%>	
										<c:forEach varStatus="loop" begin="1" end="${bicolourStarNumber}">
											<i class="glyphicon glyphicon-star" style="color: #f1c40f;"></i> 
										</c:forEach>
									
										<c:if test="${bicolourStarNumber < 5}">
										
											<%-- two-colours star  stars--%>
											<i class="glyphicon glyphicon-star" style="background:-webkit-linear-gradient(left, #f1c40f 0%, #f1c40f <c:out value="${ colourchangePercentage }"></c:out>%, #bdc3c7 <c:out value="${ colourchangePercentage }"></c:out>%, #bdc3c7 100%); -webkit-background-clip:text; -webkit-text-fill-color:transparent;"></i> 
											
											<%-- full gray stars--%>
											<c:forEach varStatus="loop" begin="${bicolourStarNumber + 2}" end="5">
												<i class="glyphicon glyphicon-star" style="color: #bdc3c7;"></i> 
											</c:forEach>
											
										</c:if>
																			
									</span>
								</h1>
								<p class="text-center">Avg rating: <strong><c:out value="${ requestScope.ratingsAvg }"/></strong> (<c:out value="${ requestScope.ratingsTotalAmount }"/> rating<c:if test="${ requestScope.ratingsTotalAmount > 1 }">s</c:if>)</p>
								
							</c:if>
							
							<%-- ratingsUserRatingValue --%>
							
							<c:choose>
         
					        	<c:when test = "${requestScope.ratingsUserRatingValue <= 0}">
						        	<c:choose>
							        	<c:when test = "${ !sessionScope.isLoggedIn }">
											<div  id="userNotRegisteredRating" role="alert" class="alert alert-danger" style="margin-top:10px; margin-bottom: 0;"><span><strong>Please <a href="Login?action=signUp">register</a> / <a href="Login?action=login">login</a></strong></span></div>
										</c:when>
										<c:otherwise>
						            		<a data-toggle="modal" data-target="#modal-feedback" class="btn btn-primary btn-block redButton" type="button">Give a rating</a>
						         		</c:otherwise>
					         		</c:choose>
					         	</c:when>
					         
					         	<c:otherwise>
					            	<div id="userHasRated" role="alert" class="alert alert-success text-center" style="margin-top:10px; margin-bottom: 0;" data-toggle="tooltip" title="To edit or delete your rating please refer to your profile page."><a href="Profile?section=wineReviews">Your rating: <strong><c:out value="${ratingsUserRatingValue}"></c:out></strong>! <i class="fa fa-thumbs-o-up red-text" style="font-size: 20px;"></i></a></div>
					         	</c:otherwise>

					      	</c:choose>
							
						</div>
					</div> 
					
					<%-- Reviews --%>
					<div class="col-md-12">
						<div class="card settingsCard">
							<h3 class="text-center">Reviews </h3>
							<hr class="sep-bar" />
						
							<%-- reviewsTotalAmount --%>
							<c:if test="${ requestScope.reviewsTotalAmount > 0 }">
								<p class="text-center"><strong><c:out value="${ requestScope.reviewsTotalAmount }" /></strong> review<c:if test="${ requestScope.reviewsTotalAmount > 1 }">s</c:if> available<c:if test="${ requestScope.reviewsTotalAmount > 1 }">s</c:if></p>
							</c:if>
							
							<%-- reviewsUserHasReviewed --%>
							
							<c:choose>
         
					        	<c:when test = "${requestScope.reviewsUserHasReviewed == false}">
						        	<c:choose>
							        	<c:when test = "${ !sessionScope.isLoggedIn }">
											<div id="userNotRegisteredReview" role="alert" class="alert alert-danger" style="margin-top:10px; margin-bottom: 0;"><span><strong>Please <a href="Login?action=signUp">register</a> / <a href="Login?action=login">login</a></strong></span></div>
										</c:when>
										<c:otherwise>
						            		<a data-toggle="modal" data-target="#modal-feedback" class="btn btn-primary btn-block redButton" type="button">Write a review</a>
						         		</c:otherwise>
					         		</c:choose>
					         	</c:when>
					         
					         	<c:otherwise>
					            	<div id="userHasReviewed" role="alert" class="alert alert-success text-center" style="margin-top:10px; margin-bottom: 0;" data-toggle="tooltip" title="To edit or delete the review please refer to your profile page."><a href="Profile?section=wineReviews">You've reviewed this product! </a><i class="fa fa-thumbs-o-up red-text" style="font-size: 20px;"></i></div>
					         	</c:otherwise>

					      	</c:choose>
											
						</div>
					</div>
					
           		</div>
           		
           		<%-- Search panel --%>
            	<div class="row">
            		<c:import url="../templates/filtersPanel.jsp"/>
            	</div>
            	
           	</div>
            
           	<%-- main / product column --%>
               <div class="col-sm-9 col-xs-12">
                   
                   <%-- product information summary (top panel) --%>
                   <div class="row">
	                  <div class="card settingsCard">
	                  	  
	                  	  <%-- product name --%>
	                  	  <div class="row">
	                  	  	<div class="col-xs-12">
		                  	  <h1 class="text-center"><c:out value="${requestScope.wine.getName()}"/></h1>
		                      <hr class="sep-bar">
		                    </div>
	                      </div>
	                  	  
	                      <div class="row">
	                      
	                          <%-- product image --%>
	                          <div class="col-xs-3">
	                      	  	  <div style="width:100%;height:220px;background:url(<c:out value = "${wine.getImageURL()}"/>) no-repeat center center;/*background-repeat:no-repeat;*/background-size:contain;"></div>
	                      	  </div>
	                      	      
	                          <%-- product details --%>
	                          <div class="col-xs-9">
	                              <div class="table-responsive" id="detailsTable">
	                                  <table class="table">
	                                      <tbody>
	                                      <c:if test="${requestScope.wine.getWineTypeName() != null && !requestScope.wine.getWineTypeName().equals(\"\") }">
	                                       <c:if test="${!requestScope.wine.getWineTypeName().toLowerCase().equals(\"11502\")
	                                       	&& !requestScope.wine.getWineTypeName().toLowerCase().equals(\"wine\") }">
	                                       	<tr>
	                                               <td><strong>Type</strong> </td>
	                                               <td><c:out value="${requestScope.wine.getWineTypeName()}"/></td>
	                                           </tr>
	                                       </c:if>
	                                      </c:if>
	                                      <c:if test="${requestScope.wine.getCountryName() != null && !requestScope.wine.getCountryName().equals(\"\") }">
	                                       <c:if test="${!requestScope.wine.getCountryName().toLowerCase().equals(\"no country\")}">
	                                       	<tr>
	                                               <td><strong>Country</strong> </td>
	                                               <td><c:out value="${requestScope.wine.getCountryName()}"/></td>
	                                           </tr>
	                                       </c:if>
	                                      </c:if>
	                                      <c:if test="${requestScope.wine.getRegionName() != null && !requestScope.wine.getRegionName().equals(\"\") }">
	                                       <c:if test="${!requestScope.wine.getRegionName().toLowerCase().equals(\"no region\")}">
	                                       	<tr>
	                                               <td><strong>Region</strong> </td>
	                                               <td><c:out value="${requestScope.wine.getRegionName()}"/></td>
	                                           </tr>
	                                       </c:if>
	                                      </c:if>
	                                      <c:if test="${requestScope.wine.getWineryName() != null && !requestScope.wine.getWineryName().equals(\"\") }">
	                                       <c:if test="${!requestScope.wine.getWineryName().toLowerCase().equals(\"no winery\")}">
	                                           <tr>
	                                               <td><strong>Winery</strong> </td>
	                                               <td><c:out value="${requestScope.wine.getWineryName()}"/></td>
	                                           </tr>
	                                       </c:if>
	                                      </c:if>
	                                      <c:if test="${requestScope.wine.getBottleSize() != null }">
	                                          <tr>
	                                              <td><strong>Bottle size</strong> </td>
	                                              <td><c:out value="${requestScope.wine.getBottleSize()}"/> cl</td>
	                                          </tr>
	                                      </c:if>
	                                      <c:if test="${requestScope.wine.getColourName() !=null && !requestScope.wine.getColourName().equals(\"\") }">
	                                      	<c:if test="${!requestScope.wine.getColourName().toLowerCase().equals(\"no colour\")}">
	                                           <tr>
	                                               <td><strong>Colour</strong> </td>
	                                               <td><c:out value="${requestScope.wine.getColourName()}"/></td>
	                                           </tr>
	                                          </c:if>
	                                      </c:if>
	                                      <c:if test="${requestScope.wine.getAppellationName() !=null && !requestScope.wine.getAppellationName().equals(\"\") }">
	                                      	<c:if test="${!requestScope.wine.getAppellationName().toLowerCase().equals(\"no appellation\")}">
	                                           <tr>
	                                               <td><strong>Appellation</strong> </td>
	                                               <td><c:out value="${requestScope.wine.getAppellationName()}"/></td>
	                                           </tr>
	                                          </c:if>
	                                      </c:if>
	                                      <c:if test="${requestScope.wine.getVintage() !=null && !requestScope.wine.getVintage().equals(\"\") }" >
	                                          <tr>
	                                              <td><strong>Vintage</strong> </td>
	                                              <td><c:out value="${requestScope.wine.getVintage()}"/></td>
	                                          </tr>
	                                      </c:if>
	                                      <c:if test="${requestScope.wine.getGrapeVarietyName() !=null && !requestScope.wine.getGrapeVarietyName().equals(\"\") }">
	                                       <c:if test="${!requestScope.wine.getGrapeVarietyName().toLowerCase().equals(\"no variety\")}">
	                                           <tr>
	                                               <td><strong>Grape variety</strong> </td>
	                                               <td><c:out value="${requestScope.wine.getGrapeVarietyName()}"/></td>
	                                           </tr>
	                                       </c:if>
	                                      </c:if>
	                                      <c:if test="${requestScope.wine.getClosureName() !=null && !requestScope.wine.getClosureName().equals(\"\") }">
	                                       <c:if test="${!requestScope.wine.getClosureName().toLowerCase().equals(\"no closure\")}">
	                                           <tr>
	                                               <td><strong>Closure</strong> </td>
	                                               <td><c:out value="${requestScope.wine.getClosureName()}"/></td>
	                                           </tr>
	                                       </c:if>
	                                      </c:if>
	                                      <c:if test="${requestScope.wine.getAbv() !=null && !requestScope.wine.getAbv().equals(\"\") }">
	                                          <tr>
	                                              <td><strong>Alcohol by volumes (ABV)</strong> </td>
	                                              <td><c:out value="${requestScope.wine.getAbv()}"/>%</td>
	                                          </tr>
	                                      </c:if>
	                                      </tbody>
	                                      <%-- <caption class="text-center">Details </caption> --%>
	                                  </table>
	                              </div>
	                          </div>
	                      </div>
	                      
	                      <%-- Description --%>
	                      <div class="row">
	                      	<div class="col-xs-12">
                             	<c:if test="${requestScope.wine.getDefaultDescription() !=null && !requestScope.wine.getDefaultDescription().equals(\"\") }">
                             		<strong>Description: </strong><c:out value="${requestScope.wine.getDefaultDescription()}"/>
                             	</c:if>
                          	</div>   	
	                      </div>
	                      
	                  </div>
                   </div>
                   
                   <%-- Price comparison header --%>
                   <div class="row">
                   		<div class="col-xs-12">
	                   		<div class="card settingsCard">
                                <h1 class="text-center">Price comparison</h1>
                           		<hr class="sep-bar">
	                   		</div>
	                   	</div>
                   </div>
                   
                    <%-- Price comparison merchant offers --%>
                   <div class="row">
                   		<div class="col-xs-12">
                   			<c:forEach items="${requestScope.priceComparisonList}" var="shop">
                             	<div class="card settingsCard">
                             		<div class="row">
		                             			
                             			<%-- Making sure both % off and was are displayed only if saving --%>
						                
						                <div class="col-xs-4">
                             				<div class="price-comparison-column-content-container" style="background:url(<c:out value="${shop.getShopImageURL()}"/>) no-repeat center center;/*background-repeat:no-repeat;*/background-size:contain;"></div>
                             			</div>
                             			
                             			<div class="col-xs-2">
                             				<c:if test="${ shop.getMoneySaving() > 0 }">
                             					<div class="price-comparison-column-content-container"><span class="label label-default price-comparison-column-content price-comparison-column-content-label">Save <fmt:formatNumber type="currency" value="${shop.getMoneySaving()}"/> (-<c:out value="${shop.getPercentageOff()}"/>%)</span></div> 
                             				</c:if>
                             			</div>
                             		
                             			<div class="col-xs-2">
                             				<c:if test="${ shop.getMoneySaving() > 0 }">
                             					<div class="price-comparison-column-content-container"><span class="price-comparison-column-content price-comparison-column-content-was-price h4">Was <fmt:formatNumber type="currency" value="${shop.getOldProductPrice() }"/></span></div>
                             				</c:if>
                             			</div>
								
										<div class="col-xs-2">
                             				<div class="price-comparison-column-content-container"><span class="red-text h3"><fmt:formatNumber type="currency" value="${shop.getProductPrice()}"/></span></div>
                             			</div>
                             		
                             			<div class="col-xs-2">
                             				<div class="price-comparison-column-content-container"><a target="_blank" href="<c:out value="${shop.getDestinationURL()}"/>"><button class="btn btn-primary redButton" type="button" >Buy now</button></a></div>
                             			</div>
                             			
                             		</div>
									
                                </div>
                            </c:forEach>
	                   	</div>
                   </div>
                   
                   
                   <%-- All product reviews --%>
                   
                   <c:if test="${ requestScope.reviewsTotalAmount > 0 }">
                    <div class="row">
                    	<div class="col-xs-12">
                    		<div class="card settingsCard">
                    			<h1 class="text-center">Product reviews</h1>
	                            <hr class="sep-bar">
                    		</div>
                    	</div>
                    	<c:forEach items="${ requestScope.reviewsList }" var="review">
	                    	<div class="col-xs-12">
	                    		<div data-aos="fade-up" data-aos-duration="800" data-aos-delay="50" data-aos-once="true" class="card settingsCard aos-init aos-animate">
	                                <c:if test="${ review.getComments() != null && !review.getComments().equals('') }">
	                                	<div class="row">
	                                		<div class="col-md-10">
	                                			<span><strong><c:out value="${ review.getTitle() }"></c:out></strong></span>
	                                		 	<span><small> - by <c:out value="${ review.getUserId().getName() }"></c:out></small></span>
	                                		</div>
	                                		<div class="col-md-2">
	                                		 	<span class="text-right text-muted"><fmt:formatDate type = "date" value = "${ review.getAddedDate() }" /></span>
	                                		</div>
	                                	</div>
		                                <div class="row">
		                                    <div class="col-md-12">
		                                        <h5 style="color:#800000;"><c:out value="${ review.getComments() }"/></h5>
		                                    </div>
		                                </div>
	                                </c:if>
	                            </div>
	                    	</div>
                    	</c:forEach>
                    </div>
                   </c:if>
                   
               </div>      

        </div>
    </section>
    <div class="modal fade" role="dialog" tabindex="-1" id="modal-register">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header" style="background:#f7f7f7;">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                </div>
                <div class="modal-body" style="background:#f7f7f7;">
	                <div class="row">
	                	<div class="col-xs-12">
		                	<h3 class="text-center">Register now!</h3>
			                <hr class="sep-bar">
			                <p>We are sorry but you can't add a wine to your favourites list unless you're registered! Are you already a user? Please <a href="http://www.winedunk.com/Login">Log in here</a>. Not a user yet? Then come <a href="http://www.winedunk.com/Login?action=signUp">join</a> a community of wine lovers</p>
			                <a href="http://www.winedunk.com/Login?action=signUp" style="float:right;" class="btn btn-primary btn-md redButton" type="submit">Register</a>
	                	</div>
	                </div>
                </div>
            </div>
        </div>
    </div>
    
    <%-- Give rating & review modal form--%>
    <div class="modal fade" role="dialog" tabindex="-1" id="modal-feedback">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header" style="background:#f7f7f7;">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                </div>
                <div class="modal-body" style="background:#f7f7f7;">
	                <div class="row">
	                	<div class="col-xs-12">
		                	<h3 class="text-center">Many thanks for your feedback!!! <i class="fa fa-thumbs-o-up red-text"></i> </h3>
			                <hr class="sep-bar">
			                <form method="POST" action="Product">
			                
			                	<%-- making the decision whether show rating or not based on if user has already rated the product --%>
				                <c:choose>
						        	<c:when test = "${ratingsUserRatingValue <= 0}">
										<h1 class="text-center">
					                	<span id="formStars">
					                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar1" class="glyphicon glyphicon-star"></i>
					                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar2" class="glyphicon glyphicon-star"></i>
					                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar3" class="glyphicon glyphicon-star"></i>
					                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar4" class="glyphicon glyphicon-star"></i>
					                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar5" class="glyphicon glyphicon-star"></i>
					                	</span>
						                </h1>
						         	</c:when>
						         	<c:otherwise>
						            	<div id="userHasRated" role="alert" class="alert alert-success text-center" style="margin-top:10px; margin-bottom: 0;"><span>Your rating: <c:out value="${ratingsUserRatingValue}"></c:out>,<strong> thanks!</strong></span></div>
						         	</c:otherwise>
						      	</c:choose>
			                
								<hr>
								
				                <p>Providing some information about this product you are helping winedunk community to make right decisions!</a></p>
				                <input class="form-control" style="margin-bottom: 10px;" id="reviewTitle" name="reviewTitle" placeholder="Give it a short title!">
								<textarea id="reviewText" name="reviewText" class="form-control" placeholder="Enter your review" rows="3"></textarea>
				                
						        <input type="hidden" id="ratingValue" name="ratingValue">
				                <input name="wineId" value="${requestScope.wine.getWineId()}" type="hidden">
				                <input name="formChosen" value="feedbackForm" type="hidden">
				                
			                	<hr>
				                <button style="float: right;" class="btn btn-primary btn-md redButton" type="submit">Submit </button>
				                		                
			                </form>
	                	</div>
	                </div>
                </div>
            </div>
        </div>
    </div>
    
    <hr class="sep-bar">
   	<c:import url="../templates/footer.jsp"/>
	<script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/populate-inputs.js"></script>
    <script>
    	
	    /*
	    	This script handles the addition of a wine to favourites or basket
	    	And manages the feedback message the user receives.
	    */
	    
	    function addToCart(wineId)
	   	{ 
	   		var id = wineId;
	   		$.ajax({
               url:'Product',
               type:'POST',
               data: 
               { 
            	   wineId : id,
            	   formChosen : 'basketForm'
               },
               success : function(data)
               {
            	   $('#toBasketButton').animate({
            		  height: 'toggle'
            	   });
            	   
            	   $('#successfulMessage').fadeIn();
               },
               error: function() 
               {
            	   $('#toBasketButton').animate({
	            		  height: 'toggle'
	            	   });
            	   
            	   $('#errorMessage').fadeIn() 
               }
             });
	   	}
    	
	    function addToFav(wineId)
	   	{ 
	   		var id = wineId;
	   		$.ajax({
               url:'Profile',
               type:'POST',
               data: 
               { 
            	   wineId : id,
            	   formChosen : 'addFavouriteWine'
               },
               success : function(responseText)
               {
            	   if(responseText == 'True') 
            	   { 
            		   $('#toFavouriteButton').animate({
                 		  height: 'toggle'
                 	   });
                 	   
                 	   $('#successfulFavMessage').fadeIn();
            	   } else
           		   {
            		   $("#modal-register").modal('show');
           		   }
               },
               error: function() 
               {
            	   $('#toFavouriteButton').animate({
	            		  height: 'toggle'
	            	   });
            	   
            	   $('errorMessage').fadeIn() 
               }
             });
	   	}
    </script>
    <script>
	
       	
    </script>
    <script>
    	
    	/*
    		This function controls the colouring of the rating stars on hover.
    		It gets the star that's hovered over and illuminates up to it.
    	*/
    	
    	var defaultName;
    	var elemId;
    	
    	function illuminateStars(elem)
    	{
   			defaultName = elem.getAttribute('id').slice(0, -1);
   			elemId = elem.getAttribute('id').slice(-1)
   			
   			var i = 0;
    		for(i = 1; i <= 5; i++)
   			{
    			var star = document.getElementById(defaultName + i);
    			if(i > elemId) { star.setAttribute("style", "color: #bdc3c7"); } 
    			else { star.setAttribute("style", "color: #f1c40f"); }
   			}
    	}
    
    	/*
	   		This function is used for populating the hidden inputs
	   		of the rating value. It populates both hidden inputs 
	   		on both forms, since it can be called from either set of stars.
	   		It also blocks the appropiate colour of the stars so they don't
	   		change colour on mouse hover.
		*/
	
		function populateRating(elem)
	   	{
   			defaultName = elem.getAttribute('id').slice(0, -1);
   			elemId = elem.getAttribute('id').slice(-1)
   			var i = 0;
    		for(i = 1; i <= 5; i++)
   			{
    			var star = document.getElementById(defaultName + i);
    			star.removeAttribute("onmouseover");
    			/* if(i > elemId) { star.setAttribute("style", "color: #bdc3c7 !important;"); } 
    			else { star.setAttribute("style", "color: #f1c40f !important;"); } */
   			}
    		
    		document.getElementById("ratingValue").value = elemId;
	   	}
    	
    </script>
    <c:import url="../templates/script.jsp"/>
    <c:import url="../templates/enableTooltips.jsp"/>
</body>

</html>