<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                    	<c:if test="${ requestScope.searchByMerchant == true }">
                    		<c:if test="${ requestScope.noOffers != true }">
	                    		<div class="col-xs-12">
									<div class="card settingsCard">
										<h1 class="text-center"><c:out value="${ requestScope.merchantChosen.getName() }"/></h1>
			                        	<hr class="sep-bar">
			                        	<h3 class="text-muted text-center">Best offers</h3>
									</div>
								</div>
								<div class="col-xs-12">
								<div class="row articles">
									<c:forEach items="${ requestScope.bestOffers }" var="i">
										<div class="col-xs-12 col-sm-6 col-md-4 item">
											<div class="card item">
												<a href="Product?id=<c:out value="${ i.getWineId() }" />">
													<div style="width:100%;height:170px;background:url(<c:out value = "${i.getWineImageURL()}"/>) no-repeat center center;background-size:contain;"></div>
												</a>
												<div class="text-wrapper">
													<a style="text-decoration: none;" href="Product?id=<c:out value="${ i.getWineId() }" />"><h3 style="color:#800000;" class="name"><c:out value="${i.getWineName()}"/></h3></a>
													<p class="description"><c:out value="${i.getWineShortDescription()}"/>
														<c:if test="${i.getWineShortDescription().length() == 169}">
															<a href="Product?id=<c:out value="${ i.getWineId() }" />"> See more</a>
														</c:if>
													</p>
													<div class="row" style="margin-top:15px; bottom:0px;">
													<a style="text-decoration:none;" href="Product?id=<c:out value="${ i.getWineId() }" />" class="action">
														<i class="glyphicon glyphicon-circle-arrow-right"></i>
													</a>
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
                    		</c:if>
							<div class="col-xs-12">
								<div class="card settingsCard">
									<h1 class="text-center"><c:out value="${ requestScope.merchantChosen.getName() }"/></h1>
		                        	<hr class="sep-bar">
		                        	<h3 class="text-muted text-center">Wines</h3>
								</div>
							</div>
                    	</c:if>
						<div class="col-sm-12 result-card">
						    <div class="card">
						        <div class="row">
						            <div class="col-xs-6" style="padding:10px">
						                <div class="dropdown" style="margin-left:15px;">
											<button id="sortingDropdown"class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false" type="button">Sort by  <span class="caret"></span></button>
											<ul class="dropdown-menu" role="menu">
												<li style="cursor:pointer" role="presentation" onclick="changeSorting(this)" value="1"><a>Price: Low to high</a></li>
												<li style="cursor:pointer" role="presentation" onclick="changeSorting(this)" value="2"><a>Price: High to low</a></li>
											</ul>
										</div>
						            </div>
						            <div class="col-xs-6">
					            		<button id="showURLButton" class="btn btn-primary redButton" type="button" style="width:130px;float:right;margin-right:10px;margin-top:10px;">Share search </button>
					            		<input id="urlText" class="form-control text-center" style="float:right;display:none;margin-right:10px;margin-top:10px;" value="winedunk.com/Share<c:out value="${requestScope.sharingURL}"/>">
						            </div>
						        </div>
						    </div>
						</div>
						<c:choose>
							<c:when test="${requestScope.noResults == true}">
								<div class="col-sm-12">
									<div class="card settingsCard">
										<div class="row">
											<div class="col-sm-12">
												<h1 class="red-text text-center">Oops, no wines!</h1>
	                                			<hr class="sep-bar">
	                                			<div class="row"> 
	                                				<div class="col-md-12">
				                                    	<p>
				                                       		We apologise but your specific search criteria didn’t return any results. We’re sure we have something in our extensive database of wines that would suit your preferences so please ensure you haven’t spelt something wrong or adjust the filters you applied to ensure you find what you’re looking for.
				                                       	</p>
				                                	</div>
	                                			</div>											
											</div>
										</div>									
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<c:forEach items = "${requestScope.resultsList}" var = "wine">
			                    	<div class="col-sm-12 result-card">
			                            <div class="card">
			                                <div class="row">
			                                    <div class="col-sm-4">
				                                    <a href="Product?id=<c:out value="${wine.getWineId()}"/>">
				                                    	<div style="width:100%;height:170px;background:url(<c:out value = "${wine.getWineImageURL()}"/>) no-repeat center center;background-size:contain;"></div>
				                                    </a>
			                                    </div>
			                                    <div class="col-md-6 col-sm-8">
			                                        <div class="row">
			                                            <div class="col-sm-12">
				                                            <a style="text-decoration:none;" href="Product?id=<c:out value="${wine.getWineId()}"/>">
					                                            <h2 class="red-text wine-name">
					                                            	<c:out value ="${wine.getWineName()}"/>
					                                            </h2>
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
			                                    <div class="col-md-2">
			                                        <div class="row hidden-xs hidden-sm" style="height:inherit;">
			                                            <div class="col-md-12" style="margin-left:-15px;">
			                                                <h3 class="text-right price"><fmt:formatNumber type="currency" value="${wine.getWineMinimumPrice()}"/></h3>
			                                            </div>
			                                        </div>
			                                        <div class="row hidden-xs hidden-sm">
			                                            <div style="position:relative;" class="col-md-12 nopadding text-center">
			                                              <a href="Product?id=<c:out value="${wine.getWineId()}"/>">
			                                                <button class="btn btn-primary btn-sm btnCompare redButton" type="submit">Compare price</button>
			                                              </a>
			                                            </div>
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
	                    <div class="col-sm-12 result-card">
						   <div class="card">
						       <div class="row">
						           <div class="col-md-4 col-xs-6">
						           <form action="Results" method="POST" style="display:inline">
						            	<input type="hidden" name="formChosen" value="first">
						            	<button class="btn btn-primary redButton navigationButton" type="submit"
						            		<c:if test="${ sessionScope.currentPage == 1 }">disabled </c:if>>First
						            	</button>
						            </form>
						           	<form action="Results" method="POST" style="display:inline">
						            	<input type="hidden" name="formChosen" value="previous">
						                <button class="btn btn-primary redButton navigationButton" type="submit"
						                	<c:if test="${ sessionScope.currentPage == 1 }">disabled </c:if>>Previous
						                </button>
						            </form>
						           </div>
						           <div class="col-md-4 hidden-xs hidden-sm">
						               <div class="text-center">
						               	<span style="line-height:54px;"> |
						               	<c:forEach items="${sessionScope.paginationList}" var="number">
						               		<form style ="display:inline; margin:0; padding:0;" method="POST" action="Results">
						               			<input type="hidden" name="formChosen" value="number">
						               			<input type="hidden" name="value" value="${number}">
						               			<button class="paginationLink btn-link" stlye="margin: 0 !important; padding: 0 !important;"type="submit">
								               		<c:if test="${sessionScope.currentPage == number}"><strong></c:if> 
								               			${number} 
								               		<c:if test="${sessionScope.currentPage == number}"></strong></c:if>
						               			</button> |
						               		</form>
						               	</c:forEach>
						               	</span>
						               </div>
						           </div>
						           <div class="col-md-4 col-xs-6">
									<form action="Results" method="POST" style="display:inline">
									   	<input type="hidden" name="formChosen" value="last">
										<button class="btn btn-primary redButton navigationButton" type="submit" style="float:right"
											<c:if test="${sessionScope.amountOfPages == requestScope.currentPage 
											|| requestScope.noResults == true}">disabled</c:if>>Last
										</button>
									</form>
									<form action="Results" method="POST" style="display:inline">
									   	<input type="hidden" name="formChosen" value="next">
										<button class="btn btn-primary redButton navigationButton" type="submit" style="float:right"
											<c:if test="${sessionScope.amountOfPages == requestScope.currentPage
											|| requestScope.noResults == true}">disabled</c:if>>Next
										</button>
									</form>
						           </div>
						        </div>
						    </div>
						</div>
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
    <c:import url="../templates/footer.jsp"/>
    <div class="snackbar" id="sbLinkCopied">Link copied to clipboard!</div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="assets/js/populate-inputs.js"></script>
    <script>
		
    	$('#showURLButton').click(function(){
			$('#showURLButton').animate({
				height: 'toggle'
			}, 'slow');
			
			var $input = $('#urlText');
			var $temp = $("<input>");
		    $("body").append($temp);
		    $temp.val($input.val()).select();
		    document.execCommand("copy");
		    
		    $temp.remove();
			$input.fadeIn();
			
			var snackBarCopied = document.getElementById("sbLinkCopied")
     	    snackBarCopied.className = "snackbar show";
     	    setTimeout(function(){ snackBarCopied.className = snackBarCopied.className.replace("show", ""); }, 3000);
   		});
    </script>
    <c:import url="../templates/script.jsp"/>
</body>
</html>