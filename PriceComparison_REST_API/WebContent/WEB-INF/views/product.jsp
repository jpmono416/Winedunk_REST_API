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
            <div class="row">
            	<div class="col-sm-3">
	            	<div class="row">
	            		<c:import url="../templates/filtersPanel.jsp"/>
	            	</div>
            	</div>
                <div class="col-sm-9 col-xs-12">
                    <div class="row">
                        <div class="col-sm-8 col-xs-12">
                            <div class="card settingsCard">
                                <h1 class="text-center"><c:out value="${requestScope.wine.getName()}"/></h1>
                                <hr class="sep-bar">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div style="width:100%;height:170px;background:url(<c:out value = "${wine.getImageURL()}"/>) no-repeat center center;/*background-repeat:no-repeat;*/background-size:contain;"></div>
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
                                                        <td><strong>Alcohol percentage (Abv)</strong> </td>
                                                        <td><c:out value="${requestScope.wine.getAbv()}"/>%</td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${requestScope.wine.getDefaultDescription() !=null && !requestScope.wine.getDefaultDescription().equals(\"\") }">
                                                	<tr>
                                                        <td><strong>Description</strong> </td>
                                                        <td><c:out value="${requestScope.wine.getDefaultDescription()}"/></td>
                                                    </tr>
                                                </c:if>
                                                </tbody>
                                                <caption class="text-center">Details </caption>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <div class="row">
								<div class="col-md-12">
									<div class="card settingsCard">
										<h2 class="text-center">Lowest price:</h2>
										<h3 class="text-center red-text" style="margin-top:0;"><fmt:formatNumber type="currency" value="${requestScope.wine.getMinimumPrice()}"/></h3>
										<p class="text-center">At <c:out value="${requestScope.wine.getMinimumPriceShopName()}"/></p>
										<hr class="sep-bar">
										<button class="btn btn-primary btn-block secondaryButton" id="toBasketButton" onclick="addToCart(<c:out value="${requestScope.wine.getWineId()}"/>)">To basket</button>
										<button class="btn btn-primary btn-block secondaryButton" id="toFavouriteButton" onclick="addToFav(<c:out value="${requestScope.wine.getWineId()}"/>)"><i class="fa fa-heart linkIcon"></i>Add to favourites</button>
										<a class="btn btn-primary btn-block redButton" target="_blank" style="text-decoration: none;" href="<c:out value="${requestScope.wine.getMinimumPriceDestinationURL() }"/>">Buy now</a>
										<div id="successfulMessage" role="alert" class="alert alert-success" style="margin-top:10px; margin-bottom: 0; display:none;"><span><strong>Added to basket!</strong></span></div>
										<div id="errorMessage" role="alert" class="alert alert-danger" style="margin-top:10px; margin-bottom: 0; display:none;"><span><strong>We encountered a problem</strong></span></div>
										<div id="successfulFavMessage" role="alert" class="alert alert-success" style="margin-top:10px; margin-bottom: 0; display:none;"><span><strong>Added to favourites!</strong></span></div>
									</div>
								</div>
								<!--  REVIEWS
								<div class="col-md-12">
									<div class="card settingsCard">
										<h1 class="text-center"><span id="stars"><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i></span></h1>
										<h2 class="text-center">Reviews </h2>
										<hr class="sep-bar" />
										<p class="text-center">0 reviews</p>
										<button class="btn btn-link btn-block" type="button">See all reviews</button>
										<button class="btn btn-primary btn-block redButton" type="button">Write a review</button>
									</div>
								</div>
								-->
							</div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card settingsCard">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="row">
	                                        <h1 class="text-center"><c:out value="Price comparison"/></h1>
	                                		<hr class="sep-bar">
	                                        <c:forEach items="${requestScope.priceComparisonList}" var="shop">
	                                        	<div class="col-sm-4 col-xs-12 rightBar">
													<div style="width:100%;height:170px;background:url(<c:out value="${shop.getShopImageURL()}"/>) no-repeat center center;/*background-repeat:no-repeat;*/background-size:contain;"></div>
	                                                <h1 class="text-center" style="margin-bottom:30px;">&pound<c:out value="${shop.getProductPrice()}"/></h1>
	                                                <div class="row hidden-sm text-center">
	                                                    <div class="col-xs-12">
	                                                        <button class="btn btn-link btn-block" type="button" style="margin-bottom:5px;">To basket</button>
	                                                    </div>
	                                                    <div class="col-xs-12">
	                                                        <a target="_blank" href="<c:out value="${shop.getDestinationURL()}"/>"><button class="btn btn-primary btn-block redButton" type="button">Buy now</button></a>
	                                                    </div>
	                                                </div>
	                                                <div class="row hidden-xs hidden-md hidden-lg text-center">
	                                                    <div class="col-xs-12">
	                                                        <button class="btn btn-primary btn-sm redButton" type="button">Add to basket</button>
	                                                    </div>
	                                                    <div class="col-xs-12">
	                                                        <a target="_blank" href="<c:out value="${shop.getDestinationURL()}"/>"><button class="btn btn-primary btn-sm redButton" type="button" style="margin-top:10px;">Buy now</button></a>
	                                                    </div>
	                                                </div>
													<hr class="visible-xs-block sep-bar" style="margin-top:15px;">
	                                            </div>
	                                        </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
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
    <script>
    	
	    function addToCart(wineId)
	   	{ 
	   		var id = wineId;
	   		$.ajax({
               url:'Product',
               type:'POST',
               data: { wineId : id },
               success : function(data)
               {
            	   $('#toBasketButton').animate({
            		  height: 'toggle'
            	   });
            	   
            	   $('#successfulMessage').fadeIn();
            	   
            	   if(data.length == 1)
           		   {
           		   	
           		   }
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
            		   alert('modal?');
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
    <hr class="sep-bar">
   	<c:import url="../templates/footer.jsp"/>
	<script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/populate-inputs.js"></script>
    <c:import url="../templates/script.jsp"/>
</body>

</html>