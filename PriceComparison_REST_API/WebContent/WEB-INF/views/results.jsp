<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
    <c:import url="../templates/head.jsp"/>
    <c:import url="../templates/google_analytics.jsp"/>
</head>

<body>
    <div id="background"></div>
    <c:import url="../templates/navbar.jsp"/>
    <c:import url="../templates/google_analytics.jsp"/>
    <fmt:setLocale value="en_GB"/>
    <section style="margin-top:150px; width:100%;">
        <div class="container">
            <div class="row">
            	<div class="col-sm-3">
	            	<div class="row">
	            	<c:import url="../templates/filtersPanel.jsp"/>
	            	</div>
            	</div>
                <div class="col-sm-9">
                    <div class="row">
                		
                		<%-- Search header --%>
						<div class="col-sm-12">
							<div class="card settingsCard">
								<div class="row">
							
									<div class="col-sm-8"> 
			                			<h3 class="red-text">Search criteria</h3>
										<div class="h4">
											<c:choose>
												<c:when test="${fn:length(sessionScope.selectedSearchCriteria) gt 0}">
													<c:forEach var="SearchCriteria" items="${ sessionScope.selectedSearchCriteria }">
														<span class="label label-default"><c:out value = "${ SearchCriteria }"/></span>
												 	</c:forEach>
												</c:when>
												<c:otherwise>
													<span class="label label-default">All products</span>
												</c:otherwise>
											</c:choose>
			                			</div>
			                		</div>
			                		
			                		<div class="col-sm-4">  
			                			<button id="addSavedSearchButton" data-toggle="modal" data-target="#saveSearchModal" class="btn btn-primary redButton btn-block" style="margin-top:10px;" type="button">Save this search</button>
						            	<button id="showURLButton" class="btn btn-primary secondaryButton btn-block" type="button" style="margin-top:10px;">Share search</button>
						            	<input id="urlText" class="form-control text-center" style="display:none;margin-top:10px;" value="winedunk.com/Share<c:out value="${requestScope.sharingURL}"/>">
						            		
			                			<div class="dropdown">

											<c:choose>
												<c:when test="${empty sessionScope.sortingDropdownText}">
													<button id="sortingDropdown"class="btn btn-default dropdown-toggle btn-block" style="margin-top:10px;" data-toggle="dropdown" aria-expanded="false" type="button">Sort results by  <span class="caret"></span></button>
												</c:when>
												<c:otherwise>
													<button id="sortingDropdown"class="btn btn-default dropdown-toggle btn-block" style="margin-top:10px;" data-toggle="dropdown" aria-expanded="false" type="button"><c:out value="${ sessionScope.sortingDropdownText }"></c:out>  <span class="caret"></span></button>
												</c:otherwise>
											</c:choose>

											<ul class="dropdown-menu" role="menu">
												<li style="cursor:pointer" role="presentation" onclick="changeSorting(this)" value="1"><a>Best deal: percentage off</a></li>
												<li style="cursor:pointer" role="presentation" onclick="changeSorting(this)" value="2"><a>Best deal: money saving</a></li>
												<li style="cursor:pointer" role="presentation" onclick="changeSorting(this)" value="3"><a>Price: Low to high</a></li>
												<li style="cursor:pointer" role="presentation" onclick="changeSorting(this)" value="4"><a>Price: High to low</a></li>
												<li style="cursor:pointer" role="presentation" onclick="changeSorting(this)" value="5"><a>Name: A-Z</a></li>
												<li style="cursor:pointer" role="presentation" onclick="changeSorting(this)" value="6"><a>Name: Z-A</a></li>
											</ul>
										</div>
										
										<c:if test="${ requestScope.searchSaved == true }">
										    <div id="informationArea" class="card settingsCard">
										    	<div role="alert" class="alert alert-success" style="margin-bottom: 0;"><span><strong>Added to saved searches!</strong></span></div>
										    </div>
									    </c:if>
									    <c:if test="${ requestScope.userNotRegistered == true }">
											<div id="informationArea" class="card settingsCard">
												<div role="alert" class="alert alert-danger" style="margin-bottom: 0;"><span><strong>Please <a href="/Login?action=signUp">register</a>!</strong></span></div>
											</div>						    
									    </c:if> 
			                    	</div>
	                    		</div>
						    </div>
						</div>
						    
						
						<%-- Merchant best offers --%>
						<c:if test="${not empty requestScope.merchantBestOffers}">
							<div class="col-xs-12">
								<div class="row articles">
									<c:set var="merchantOffersCount" value="0" scope="page" />
									<c:forEach items="${ requestScope.merchantBestOffers }" var="bestofferItem">
										<%-- Limiting merchant best offers to first 3 only --%>
										<c:set var="merchantOffersCount" value="${merchantOffersCount + 1}" scope="page"/>
										<c:if test="${merchantOffersCount <= 3 }"> 
											<div class="col-xs-12 col-sm-6 col-md-4 item">
												<div class="card item">
													<div>
												        <div class="row">
												            <div class="col-xs-7">
																<a href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />">
																	<div style="width:100%;height:170px;background:url(<c:out value = "${bestofferItem.getWineImageURL()}"/>) no-repeat center center;background-size:contain;"></div>
																</a>
												            </div>
												            <div class="col-xs-5" align="center">
												            
													            <%-- Making sure both % off and was are displayed only if saving --%>
												                <c:choose>
																	<c:when test="${bestofferItem.getWineMoneySaving() > 0}">
														                <div class="row"> <span class="label label-default"><small>Save <fmt:formatNumber type="currency" value="${bestofferItem.getWineMoneySaving()}"/> (-<c:out value="${bestofferItem.getWinePercentageOff()}"/>%)</small></span> </div>
														                <div class="row"> <h6 class="text-center" style="text-decoration: line-through;">Was: <fmt:formatNumber type="currency" value="${bestofferItem.getWinePreviousMaxPrice()}"/></h6> </div>
														                <div class="row"> <h5 class="text-center red-text" style="margin-top:0;">Now: <fmt:formatNumber type="currency" value="${bestofferItem.getWineMinimumPrice()}"/></h5> </div>
																	</c:when>
																	<c:otherwise>
																	    <div class="row"> <h3 class="text-center red-text" style="margin-top:0;"><fmt:formatNumber type="currency" value="${bestofferItem.getWineMinimumPrice()}"/></h3> </div>
																	</c:otherwise>
																</c:choose>			
																
																<div class="row">
												                	<a class="btn btn-primary redButton" target="_blank" style="text-decoration: none;" href="<c:out value = "${bestofferItem.getWineMinimumPriceClicktag()}"/>">Buy now</a>
												                	<a class="btn btn-primary secondaryButton" target="_blank" style="text-decoration: none;" href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />">Compare</a>
												                </div>

												            </div>
												        </div>
													</div>
													<div class="text-wrapper">
														<a style="text-decoration: none;" href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />"><h3 style="color:#800000;" class="name"><c:out value="${bestofferItem.getWineName()}"/></h3></a>
														<p class="description"><c:out value="${bestofferItem.getWineShortDescription()}"/>
															<c:if test="${bestofferItem.getWineShortDescription().length() == 169}">
																<a href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />"> See more</a>
															</c:if>
														</p>
														<div class="row" style="margin-top:15px; bottom:0px;">
														<a style="text-decoration:none;" href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />" class="action">
															<i class="glyphicon glyphicon-circle-arrow-right"></i>
														</a>
														</div>
													</div>
												</div>
											</div>
										</c:if>
									</c:forEach>
								</div>
							</div>
                    	</c:if>

                    	<%-- Country best offers --%>
                    	<c:if test="${not empty requestScope.countryBestOffers}">
							<div class="col-xs-12">
								<div class="row articles">
									<c:set var="countryOffersCount" value="0" scope="page" />
									<c:forEach items="${ requestScope.countryBestOffers }" var="bestofferItem">
										<%-- Limiting country best offers to first 3 only --%>
										<c:set var="countryOffersCount" value="${countryOffersCount + 1}" scope="page"/>
										<c:if test="${countryOffersCount <= 3 }"> 
											<div class="col-xs-12 col-sm-6 col-md-4 item">
												<div class="card item">

													<div>
												        <div class="row">
												            <div class="col-xs-7">
																<a href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />">
																	<div style="width:100%;height:170px;background:url(<c:out value = "${bestofferItem.getWineImageURL()}"/>) no-repeat center center;background-size:contain;"></div>
																</a>
												            </div>
												            <div class="col-xs-5" align="center">

													            <%-- Making sure both % off and was are displayed only if saving --%>
												                <c:choose>
																	<c:when test="${bestofferItem.getWineMoneySaving() > 0}">
														                <div class="row"> <span class="label label-default"><small>Save <fmt:formatNumber type="currency" value="${bestofferItem.getWineMoneySaving()}"/> (-<c:out value="${bestofferItem.getWinePercentageOff()}"/>%)</small></span> </div>
														                <div class="row"> <h6 class="text-center" style="text-decoration: line-through;">Was: <fmt:formatNumber type="currency" value="${bestofferItem.getWinePreviousMaxPrice()}"/></h6> </div>
														                <div class="row"> <h5 class="text-center red-text" style="margin-top:0;">Now: <fmt:formatNumber type="currency" value="${bestofferItem.getWineMinimumPrice()}"/></h5></div>
																	</c:when>
																	<c:otherwise>
																	    <div class="row"> <h3 class="text-center red-text" style="margin-top:0;"><fmt:formatNumber type="currency" value="${bestofferItem.getWineMinimumPrice()}"/></h3></div>
																	</c:otherwise>
																</c:choose>
																
																<div class="row">
												                	<a class="btn btn-primary redButton" target="_blank" style="text-decoration: none;" href="<c:out value = "${bestofferItem.getWineMinimumPriceClicktag()}"/>">Buy now</a>
												                	<a class="btn btn-primary secondaryButton" target="_blank" style="text-decoration: none;" href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />">Compare</a>
												                </div>
																
												            </div>
												        </div>
													</div>
														
													<div class="text-wrapper">
														<a style="text-decoration: none;" href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />"><h3 style="color:#800000;" class="name"><c:out value="${bestofferItem.getWineName()}"/></h3></a>
														<p class="description"><c:out value="${bestofferItem.getWineShortDescription()}"/>
															<c:if test="${bestofferItem.getWineShortDescription().length() == 169}">
																<a href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />"> See more</a>
															</c:if>
														</p>
														<div class="row" style="margin-top:15px; bottom:0px;">
														<a style="text-decoration:none;" href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />" class="action">
															<i class="glyphicon glyphicon-circle-arrow-right"></i>
														</a>
														</div>
													</div>
												</div>
											</div>
										</c:if>
									</c:forEach>
								</div>
							</div>
                   		</c:if>
                    	
                    	<%-- Wine type best offers --%>
                    	<c:if test="${not empty requestScope.winetypeBestOffers}">
							<div class="col-xs-12">
								<div class="row articles">
									<c:set var="winetypeOffersCount" value="0" scope="page" />
									<c:forEach items="${ requestScope.winetypeBestOffers }" var="bestofferItem">
										<%-- Limiting wine type best offers to first 3 only --%>
										<c:set var="winetypeOffersCount" value="${winetypeOffersCount + 1}" scope="page"/>
										<c:if test="${winetypeOffersCount <= 3 }"> 
											<div class="col-xs-12 col-sm-6 col-md-4 item">
												<div class="card item">
												
													<div>
												        <div class="row">
												            <div class="col-xs-7">
																<a href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />">
																	<div style="width:100%;height:170px;background:url(<c:out value = "${bestofferItem.getWineImageURL()}"/>) no-repeat center center;background-size:contain;"></div>
																</a>
												            </div>
												            <div class="col-xs-5" align="center">
												            
													            <%-- Making sure both % off and was are displayed only if saving --%>
												                <c:choose>
																	<c:when test="${bestofferItem.getWineMoneySaving() > 0}">
														                <div class="row"> <span class="label label-default"><small>Save <fmt:formatNumber type="currency" value="${bestofferItem.getWineMoneySaving()}"/> (-<c:out value="${bestofferItem.getWinePercentageOff()}"/>%)</small></span> </div>
														                <div class="row"> <h6 class="text-center" style="text-decoration: line-through;">Was: <fmt:formatNumber type="currency" value="${bestofferItem.getWinePreviousMaxPrice()}"/></h6> </div>
														                <div class="row"> <h5 class="text-center red-text" style="margin-top:0;">Now: <fmt:formatNumber type="currency" value="${bestofferItem.getWineMinimumPrice()}"/></h5> </div>
																	</c:when>
																	<c:otherwise>
																	    <div class="row"> <h3 class="text-center red-text" style="margin-top:0;"><fmt:formatNumber type="currency" value="${bestofferItem.getWineMinimumPrice()}"/></h3> </div>
																	</c:otherwise>
																</c:choose>
																
																<div class="row">
												                	<a class="btn btn-primary redButton" target="_blank" style="text-decoration: none;" href="<c:out value = "${bestofferItem.getWineMinimumPriceClicktag()}"/>">Buy now</a>
												                	<a class="btn btn-primary secondaryButton" target="_blank" style="text-decoration: none;" href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />">Compare</a>
												                </div>

												            </div>
												        </div>
													</div>													

													<div class="text-wrapper">
														<a style="text-decoration: none;" href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />"><h3 style="color:#800000;" class="name"><c:out value="${bestofferItem.getWineName()}"/></h3></a>
														<p class="description"><c:out value="${bestofferItem.getWineShortDescription()}"/>
															<c:if test="${bestofferItem.getWineShortDescription().length() == 169}">
																<a href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />"> See more</a>
															</c:if>
														</p>
														<div class="row" style="margin-top:15px; bottom:0px;">
														<a style="text-decoration:none;" href="Product?id=<c:out value="${ bestofferItem.getWineId() }" />" class="action">
															<i class="glyphicon glyphicon-circle-arrow-right"></i>
														</a>
														</div>
													</div>
												</div>
											</div>
										</c:if>
									</c:forEach>
								</div>
							</div>
                   		</c:if>
						
						<%-- Search results --%>
						<c:choose>
							<c:when test="${requestScope.noResults == true}"> <%-- No wines to be displayed --%>
								<div class="col-sm-12">
									<div class="card settingsCard">
										<div class="row">
											<div class="col-sm-12">
												<h1 class="red-text text-center">Oops, no wines!</h1>
	                                			<hr class="sep-bar">
	                                			<div class="row"> 
	                                				<div class="col-md-12">
				                                    	<p>
				                                       		We apologise but your specific search criteria didn’t return any results. We’re sure we have something in our extensive database of wines that would suit your preferences so please ensure you haven’t spelled something wrong or adjust the filters you applied to ensure you find what you’re looking for.
				                                       	</p>
				                                	</div>
	                                			</div>											
											</div>
										</div>									
									</div>
								</div>
							</c:when>
							<c:otherwise> <%-- Displaying wines --%>
							
								<c:forEach items = "${requestScope.resultsList}" var = "wine">
			                    	<div class="col-sm-12 result-card">
			                            <div class="card">
			                                <div class="row">
			                                    <div class="col-sm-3">
				                                    <a href="Product?id=<c:out value="${wine.getWineId()}"/>">
				                                    	<div style="width:100%;height:170px;background:url(<c:out value = "${wine.getWineImageURL()}"/>) no-repeat center center;background-size:contain;"></div>
				                                    </a>
			                                    </div>
			                                    <div class="col-md-7 col-sm-8">
			                                        <div class="row">
			                                            <div class="col-sm-12">
				                                            <a style="text-decoration:none;" href="Product?id=<c:out value="${wine.getWineId()}"/>">
					                                            <h3 class="red-text wine-name">
					                                            	<c:out value ="${wine.getWineName()}"/>
					                                            </h3>
					                                        </a>
			                                            </div>
			                                        </div>
			                                        <p style="margin-left:10px;">
			                                        	<c:out value="${wine.getWineShortDescription()}"/>
														<c:if test="${wine.getWineShortDescription().length() == 169}">
															<a href="Product?id=<c:out value="${wine.getWineId()}" />"> See more</a>
														</c:if>
													</p>
			                                    </div>
			                                    <div class="col-md-2" align="center">
													<%-- Making sure both % off and was are displayed only if saving --%>
									                <c:choose>
													  <c:when test="${wine.getWineMoneySaving() > 0}">
													    <div class="row"> <span class="label label-default"><small>Save <fmt:formatNumber type="currency" value="${wine.getWineMoneySaving()}"/> (-<c:out value="${wine.getWinePercentageOff()}"/>%)</small></span> </div>
										                <div class="row"> <h6 class="text-center" style="text-decoration: line-through;">Was: <fmt:formatNumber type="currency" value="${wine.getWinePreviousMaxPrice()}"/></h6> </div>
										                <div class="row"> <h5 class="text-center red-text" style="margin-top:0;">Now: <fmt:formatNumber type="currency" value="${wine.getWineMinimumPrice()}"/></h5> </div>
													  </c:when>
													  <c:otherwise>
													    <div class="row"> <h3 class="text-center red-text" style="margin-top:0;"><fmt:formatNumber type="currency" value="${wine.getWineMinimumPrice()}"/></h3> </div>
													  </c:otherwise>
													</c:choose>
													
									                <div class="row">
									                	<a class="btn btn-primary redButton" target="_blank" style="text-decoration: none;" href="<c:out value = "${wine.getWineMinimumPriceClicktag()}"/>">Buy now</a>
									                	<a class="btn btn-primary secondaryButton" target="_blank" style="text-decoration: none;" href="Product?id=<c:out value="${ wine.getWineId() }" />">Compare</a>
									                </div>

			                                        <div class="row visible-xs-block visible-sm-block">
			                                            <div class="col-xs-12">
			                                                <hr class="sep-bar">
			                                            </div>
			                                            <div class="col-xs-4">
			                                                <h4 class="text-center">&pound<c:out value="${wine.getWineMinimumPrice()}"/></h4></div>
			                                            <div class="col-xs-4 nopadding">
				                                            <a href="Product?id=<c:out value="${wine.getWineId()}"/>">
				                                                <button class="btn btn-primary btn-sm btnGoToShop redButton" type="button">Compare price</button>
				                                            </a>
			                                            </div>
			                                        </div>
			                                        
			                                	</div>
			                            	</div>
		                        		</div>
			                        </div>
			                    </c:forEach>

							</c:otherwise>
						</c:choose>
	                     
	                    <%-- pagination bar --%>
	                    <c:if test="${sessionScope.amountOfPages > 1}"> <%-- pagination is displayed only if there are more than 1 page --%>

		                    <div class="col-sm-12 result-card">
							   <div class="card">
							       <div class="row">
							           
							           <%-- left hand buttons --%>
							           <div class="col-md-3 col-xs-6">
							           
								           <%-- "First page" button --%>
								           <form action="Results" method="POST" style="display:inline">
								            	<input type="hidden" name="formChosen" value="first">
								            	<button class="btn btn-primary btn-sm redButton navigationButton" type="submit"
								            		<c:if test="${ (sessionScope.currentPage == 1) || (requestScope.noResults == true) }"> disabled </c:if>>&laquo; First
								            	</button>
								           </form>
								           
								           <%-- "Previous page" button --%>
								           <form action="Results" method="POST" style="display:inline">
								            	<input type="hidden" name="formChosen" value="previous">
								                <button class="btn btn-primary btn-sm redButton navigationButton" type="submit"
								                	<c:if test="${ (sessionScope.currentPage == 1) || (requestScope.noResults == true) }"> disabled </c:if>>&#8249; Previous
								                </button>
								           </form>
								           
							           </div>
							           
							           
							           <%-- page numbers --%>
							           <div class="col-md-6 hidden-xs hidden-sm">
							           	   <%-- "page numbers management: main idea is to have "1 .. currentpage .. amountOfPages" --%>
							               <div class="text-center">
							               		<span style="line-height:54px;">
								               		
							               			<c:choose>
														
														<c:when test="${ sessionScope.amountOfPages <= 6}">
															
															<%-- if there is a maximum of 6 pages we display them directly --%>
															
															<c:forEach var="index" begin="1" end="${ sessionScope.amountOfPages }">
															    
															    <c:choose>
															    
															    	<c:when test="${index == sessionScope.currentPage}">
															    		<span><strong>${index}</strong></span>
															    	</c:when>
															    	
															    	<c:otherwise>
															    		<form action="Results" method="POST" style="display:inline">
																			<input type="hidden" name="formChosen" value="number">
																    		<input type="hidden" name="pageNumber" value="${index}">
													               			<button class="paginationLink btn-link" stlye="margin: 0 !important; padding: 0 !important;"type="submit">${ index }</button>
													               		</form>
															    	</c:otherwise>
															    	
															    </c:choose>
														
															</c:forEach>
														</c:when>
														
														<c:otherwise>
															<%-- if there is more than 6 pages, we are following below logic 
															if currentpage within the first 5 pages: "1 2 3 4 5 ...  amountOfPages" - where any of the first 5 page number could be currentpage
															if currentpage within the last 5 pages: "1 ... amountOfPages-4 amountOfPages-3 amountOfPages-2 amountOfPages-1 amountOfPages"
															otherwise "1 ... currentpage-1 currentpage currentpage+1 ... amountOfPages"  --%>
															
															<c:choose>
																<c:when test="${ sessionScope.currentPage < 5}">
																	<%-- first 5 page numbers  --%>
																	<c:forEach var="index" begin="1" end="5">
																		<c:choose>
																	    	<c:when test="${index == sessionScope.currentPage}">
																	    		<span><strong>${index}</strong></span>
																	    	</c:when>
																	    	<c:otherwise>
																	    		<c:choose>
																	    			<c:when test="${index == 1}">
																	    				<form action="Results" method="POST" style="display:inline">
																							<input type="hidden" name="formChosen" value="first">
																	               			<button class="paginationLink btn-link" stlye="margin: 0 !important; padding: 0 !important;"type="submit">1</button>
																	               		</form>
																	    			</c:when>
																	    			<c:otherwise>
																	    				<form action="Results" method="POST" style="display:inline">
																							<input type="hidden" name="formChosen" value="number">
																				    		<input type="hidden" name="pageNumber" value="${index}">
																	               			<button class="paginationLink btn-link" stlye="margin: 0 !important; padding: 0 !important;"type="submit">${ index }</button>
																	               		</form>
																	    			</c:otherwise>
																	    		</c:choose>	
																	    	</c:otherwise>
																	    </c:choose>
																	</c:forEach>
																	
																	<span> ... </span>
																	
																	<%-- last page number --%>
																	<form action="Results" method="POST" style="display:inline">
																		<input type="hidden" name="formChosen" value="last">
												               			<button class="paginationLink btn-link" stlye="margin: 0 !important; padding: 0 !important;"type="submit">${ sessionScope.amountOfPages }</button>
												               		</form>
															               		
																</c:when>
																<c:when test="${ sessionScope.amountOfPages - sessionScope.currentPage < 4}">
																
																	<%-- first page number (so 1)  --%>
																	<form action="Results" method="POST" style="display:inline">
																		<input type="hidden" name="formChosen" value="first">
												               			<button class="paginationLink btn-link" stlye="margin: 0 !important; padding: 0 !important;"type="submit">1</button>
												               		</form>
												               		
																	<span> ... </span>
																	
												               		<%-- last 5 pages  --%>
																	<c:forEach var="index" begin="${ sessionScope.amountOfPages - 4 }" end="${ sessionScope.amountOfPages }">
																		<c:choose>
																	    	<c:when test="${index == sessionScope.currentPage}">
																	    		<span><strong>${index}</strong></span>
																	    	</c:when>
																	    	<c:otherwise>
																	    	
																	    		<c:choose>
																	    			<c:when test="${index == sessionScope.amountOfPages}">
																	    				<form action="Results" method="POST" style="display:inline">
																							<input type="hidden" name="formChosen" value="last">
																	               			<button class="paginationLink btn-link" stlye="margin: 0 !important; padding: 0 !important;"type="submit">${ sessionScope.amountOfPages }</button>
																	               		</form>
																	    			</c:when>
																	    			<c:otherwise>
																						<form action="Results" method="POST" style="display:inline">
																							<input type="hidden" name="formChosen" value="number">
																				    		<input type="hidden" name="pageNumber" value="${index}">
																	               			<button class="paginationLink btn-link" stlye="margin: 0 !important; padding: 0 !important;"type="submit">${ index }</button>
																	               		</form>
																	    			</c:otherwise>
																	    		</c:choose>	
																	    	</c:otherwise>
																	    </c:choose>
																	</c:forEach>
																	
																</c:when>
																<c:otherwise>
																	
																	<%-- first page number (so 1)  --%>
																	<form action="Results" method="POST" style="display:inline">
																		<input type="hidden" name="formChosen" value="first">
												               			<button class="paginationLink btn-link" stlye="margin: 0 !important; padding: 0 !important;"type="submit">1</button>
												               		</form>
												               		
																	<span> ... </span>
																	
																	<%-- "currentpage-1 currentpage currentpage+1"  --%>
												               		<c:forEach var="index" begin="${ sessionScope.currentPage - 1 }" end="${ sessionScope.currentPage + 1 }">
																		<c:choose>
																	    	<c:when test="${index == sessionScope.currentPage}">
																	    		<span><strong>${index}</strong></span>
																	    	</c:when>
																	    	<c:otherwise>
																	    		<form action="Results" method="POST" style="display:inline">
																					<input type="hidden" name="formChosen" value="number">
																		    		<input type="hidden" name="pageNumber" value="${index}">
															               			<button class="paginationLink btn-link" stlye="margin: 0 !important; padding: 0 !important;"type="submit">${ index }</button>
															               		</form>
																	    	</c:otherwise>
																	    </c:choose>
																	</c:forEach>
																	
																	<span> ... </span>
																	
																	<%-- last page number --%>
																	<form action="Results" method="POST" style="display:inline">
																		<input type="hidden" name="formChosen" value="last">
												               			<button class="paginationLink btn-link" stlye="margin: 0 !important; padding: 0 !important;"type="submit">${ sessionScope.amountOfPages }</button>
												               		</form>
																	
																</c:otherwise>
																
															</c:choose>

															
														</c:otherwise>
													</c:choose>

							               		</span>
							               </div>
							           </div>
							           
							           
							           <%-- right hand buttons --%>
							           <div class="col-md-3 col-xs-6">
							           		<%-- "Last page" button --%>
											<form action="Results" method="POST" style="display:inline">
											   	<input type="hidden" name="formChosen" value="last">
												<button class="btn btn-primary btn-sm redButton navigationButton" style="float:right;" <c:if test="${ (sessionScope.currentPage >= sessionScope.amountOfPages) || (requestScope.noResults == true) }"> disabled</c:if> type="submit" >Last &raquo;</button>
											</form>
											
											
							           		<%-- "Next page" button --%>
											<form action="Results" method="POST" style="display:inline">
											   	<input type="hidden" name="formChosen" value="next">
												<button class="btn btn-primary btn-sm redButton navigationButton" style="float:right;" <c:if test="${ (sessionScope.currentPage >= sessionScope.amountOfPages) }"> disabled </c:if> type="submit" >Next &#8250;</button>
											</form>
											
							           </div>
							           
							           
							        </div>
							    </div>
							</div>
	                    
	                    </c:if> 
	               		
	                    <%-- Recommended wines --%>
	                    <div class="col-xs-12">
		                    <div class="card settingsCard">
			                    <h1 class="text-center" id="best-offers">Recommended wines</h1>
		                        <hr class="sep-bar">
		                    </div>
                            <div class="row articles">
                                <c:import url="../templates/recommendedWines.jsp"/>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </section>
    <div class="modal fade" id="saveSearchModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Add search</h4>
				</div>
				<div class="modal-body">
					<h3 class="text-center">Save search</h3>
	                <hr class="sep-bar">
					<form method="POST" action="Results">
						<input class="hidden" name="formChosen" type="text" value="addSavedSearch"> 
						<input id="savedSearchName" name="savedSearchName" class="form-control text-center" placeholder="Give it a name">
						<input id="searchForSaving" name="searchUrl" type="hidden" value="<c:out value="${requestScope.sharingURL}"/>">
						<div class="form-group"></div>
						<button type="submit" class="btn btn-primary redButton">Save</button>
						<button type="button" class="btn btn-secondary" style="float: right" data-dismiss="modal">Close</button>
					</form>
				</div>
			</div>
		</div>
	</div>
    <c:import url="../templates/footer.jsp"/>
    <div class="snackbar" id="sbLinkCopied">Link copied to clipboard!</div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="assets/js/populate-inputs.js"></script>
    <script>
		
    	$('#showURLButton').click(function(){
			
			var $input = $('#urlText');
			var $temp = $("<input>");
		    $("body").append($temp);
		    $temp.val($input.val()).select();
		    document.execCommand("copy");
		    
		    $temp.remove();
			
			var snackBarCopied = document.getElementById("sbLinkCopied")
     	    snackBarCopied.className = "snackbar show";
     	    setTimeout(function(){ snackBarCopied.className = snackBarCopied.className.replace("show", ""); }, 5000);
   		});
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
    	
		/* Control the "saved successfully/there was an error" messages' fade out*/
		$(document).ready(function() 
    	{ 
    		setTimeout(function() {
    		    $('#informationArea').fadeOut('slow');
    		}, 3000);
    	});
		
    </script>
    <c:import url="../templates/script.jsp"/>
</body>
</html>