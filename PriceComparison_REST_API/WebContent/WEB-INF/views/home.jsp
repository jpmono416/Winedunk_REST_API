<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <c:import url="../templates/head.jsp"/>
    <c:import url="../templates/google_analytics.jsp"/>
</head>

<body style="background-color: #e7e7e7;">
	<img class="hidden" src="http://uat.static.winedunk.com/img/REST/+.jpg">
    <c:import url="../templates/navbar.jsp"/>
    <c:import url="../templates/google_analytics.jsp"/>
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
			                <p>Register now to Winedunk! You will enjoy lots of benefits being a member. <b>Join now!</b></p>
			                <a href="http://www.winedunk.com/Login?action=signUp" style="float:right;" class="btn btn-primary btn-md redButton" type="submit">Register</a>
	                	</div>
	                </div>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${ sessionScope.passwordRecoverySuccessful != null }" >
    	<div class="modal fade" role="dialog" tabindex="-1" id="modal-recovery">
	        <div class="modal-dialog" role="document">
	            <div class="modal-content">
	                <div class="modal-header" style="background:#f7f7f7;">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	                </div>
	                <div class="modal-body" style="background:#f7f7f7;">
		                <div class="row">
		                	<div class="col-xs-12">
			                	<h3 class="text-center">Password recovery</h3>
				                <hr class="sep-bar">
				                <p>Your request to recover your password has been 
				                	<c:if test="${ sessionScope.passwordRecoverySuccessful == true }"> 
				                		successful. <br/><b>Please remember to check your spam inbox if you don't see the email.</b>
				                	</c:if>
				                	<c:if test="${ sessionScope.passwordRecoverySuccessful == false }">
				                		unsuccessful. Please try again and check the email address. If there is a technical issue we will fix it as soon as possible. 
				                	</c:if>
				                </p>
		                	</div>
		                </div>
	                </div>
	            </div>
	        </div>
	    </div>
    </c:if>
    <section id="top-section" style="padding-top:100px;">
        <div class="container">
            <div class="row">
                <div class="col-sm-7 col-xs-12">
                    <h2 class="text-center" id="user-comment"><em>Let Winedunk help you find the right wine</em></h2>
                    <div class="card">
                        <form id="search-form" action="Results" method="POST">
                        	<input class="hidden" name="currentPage" value="1">
						    <input class="hidden" name="lastSearch" value="">
                            <div class="row">
                                <div class="col-md-9 col-xs-12">
                                    <input name="name" class="form-control input-md" type="text" placeholder="Find your wine">
                                </div>
                                <div class="col-xs-12 col-md-3">
	                                <button class="btn btn-primary btn-block btn-md" type="submit" id="submitSearch"><i class="glyphicon glyphicon-search"></i> Find wines</button>
                                </div>
                            </div>
                            <div class="row">
                            	<div class="col-md-12">
								    <a href="Results" class="btn btn-link" type="button" id="advancedSearch"><i class="glyphicon glyphicon-search"></i> Advanced search</a>
								</div>
							</div>
                        </form>
                    </div>
                </div>
                <!--  
                <div class="col-sm-4 col-xs-12">
                    <h2 class="text-center" id="user-comment"><em>"Great to find the best prices"</em></h2>
                    <h2 class="text-center" id="user-comment"><span id="stars"><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i></span></h2>
                </div>
                 -->
            </div>
        </div>
    </section>

	<section>
	    <h1 class="text-center" id="best-offers">Recommended Wines</h1>
	    <hr class="sep-bar">
	    <div class="container pictures-container">
	        <div class="row articles">
	        	<c:forEach items= "${sessionScope.recommendedWines}" var="i">
					<div class="col-xs-12 col-sm-6 col-md-4 item">
						<div class="card item">
							<a href="Product?id=<c:out value="${ i.getId() }" />">
								<div style="width:100%;height:170px;background:url(<c:out value = "${i.getImageURL()}"/>) no-repeat center center;background-size:contain;"></div>
							</a>
							<div class="text-wrapper">
								<a style="text-decoration: none;" href="Product?id=<c:out value="${ i.getId() }" />"><h3 style="color:#800000;" class="name"><c:out value="${i.getName()}"/></h3></a>
								<p class="description"><c:out value="${i.getShortDescription()}"/>
									<c:if test="${i.getShortDescription().length() == 169}">
										<a href="Product?id=<c:out value="${ i.getId() }" />"> See more</a>
									</c:if>
								</p>
								<div class="row" style="margin-top:15px; bottom:0px;">
								<a style="text-decoration:none;" href="Product?id=<c:out value="${ i.getId() }" />" class="action">
									<i class="glyphicon glyphicon-circle-arrow-right"></i>
								</a>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
	        </div>
	    </div>
	</section>
	
	<c:if test="${ sessionScope.merchantsWithOffers != null }">
		<c:forEach items="${ sessionScope.merchantsWithOffers }" var="merchant">
			<section id="mid-section">
				<h1 class="text-center"><c:out value="${merchant.getName() }"/> - Best offers</h1>
			    <hr class="sep-bar">
			    <div class="container pictures-container">
			    	<div class="row articles">
				    	<c:forEach items= "${merchant.bestOffers}" var="offer">
							<div class="col-xs-12 col-sm-6 col-md-4 item">
								<div class="card item">
									<a href="Product?id=<c:out value="${ offer.getWineId() }"/>">
										<div style="width:100%;height:170px;background:url(<c:out value = "${offer.getWineImageURL()}"/>) no-repeat center center;background-size:contain;"></div>
									</a>
									<div class="text-wrapper">
										<a style="text-decoration: none;" href="Product?id=<c:out value="${ offer.getWineId() }"/>">
											<h3 style="color:#800000;" class="name"><c:out value="${offer.getWineName()}"/></h3>
										</a>
										<p class="description"><c:out value="${offer.getWineShortDescription()}"/>
											<c:if test="${offer.getWineShortDescription().length() == 169}">
												<a href="Product?id=<c:out value="${ offer.getWineId() }"/>"> See more</a>
											</c:if>
										</p>
										<div class="row" style="margin-top:15px; bottom:0px;">
											<a style="text-decoration:none;" href="Product?id=<c:out value="${ offer.getWineId() }"/>" class="action">
												<i class="glyphicon glyphicon-circle-arrow-right"></i>
											</a>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						<div class="col-xs-12 col-sm-6 col-md-4 item">
							<div class="card item">
								<form class="nopadding" id="merchant<c:out value="${ merchant.getId() }"/>Form" action="Results" method="POST">
									<input type="hidden" id="chosenShop" name="chosenShop" value="<c:out value="${ merchant.getId() }"/>">
									<a onclick="document.getElementById('merchant<c:out value="${ merchant.getId() }"/>Form').submit();">
										<div class="opacityDiv" style="width:100%;height:170px;background:url(https://tqbezw.dm2304.livefilestore.com/y4m7ImibB8RghV-NvoPSEWTgE7iHpyoKD8g7ubiIuhCkdJb16QKVw6cWSuio5dbn52nNCCOCGyGfoUAuUDi-VhM8Rau4ITh2Qw5GmbWfIT6qI104pntVSZWEqoGx6RE1KhCKM_9jCN-fPKBeUJ5KEwSsWc4BhNiWLS5E1cc3lKsuUJJKLo78B_v7OPu3NwLVfag?width=454&height=255&cropmode=none) no-repeat center center;background-size:contain;"></div>
									</a>
									<div class="text-wrapper">
										<a style="text-decoration: none;" onclick="document.getElementById('merchant<c:out value="${ merchant.getId() }"/>Form').submit();">
											<h3 style="color:#800000;" class="name">View more</h3>
										</a>
										<p class="description">Offers from ${ merchant.getName() }</p>
										<div class="row" style="margin-top:15px; bottom:0px;">
											<a style="text-decoration:none;" onclick="document.getElementById('merchant<c:out value="${ merchant.getId() }"/>Form').submit();" class="action">
												<i class="glyphicon glyphicon-circle-arrow-right"></i>
											</a>
										</div>
									</div>
								</form>
							</div>
						</div>
			    	</div>
				</div>
		    </section>
	    </c:forEach>
	</c:if>
	<c:if test="${ sessionScope.typesWithOffers != null }">
		<c:forEach items="${ sessionScope.typesWithOffers }" var="type">
			<section id="mid-section">
				<h1 class="text-center"><c:out value="${type.getWineTypeName() }"/> - Best offers</h1>
			    <hr class="sep-bar">
			    <div class="container pictures-container">
			    	<div class="row articles">
				    	<c:forEach items= "${type.bestOffers}" var="offer">
							<div class="col-xs-12 col-sm-6 col-md-4 item">
								<div class="card item">
									<a href="Product?id=<c:out value="${ offer.getWineId() }"/>">
										<div style="width:100%;height:170px;background:url(<c:out value = "${offer.getWineImageURL()}"/>) no-repeat center center;background-size:contain;"></div>
									</a>
									<div class="text-wrapper">
										<a style="text-decoration: none;" href="Product?id=<c:out value="${ offer.getWineId() }"/>">
											<h3 style="color:#800000;" class="name"><c:out value="${offer.getWineName()}"/></h3>
										</a>
										<p class="description"><c:out value="${offer.getWineShortDescription()}"/>
											<c:if test="${offer.getWineShortDescription().length() == 169}">
												<a href="Product?id=<c:out value="${ offer.getWineId() }"/>"> See more</a>
											</c:if>
										</p>
										<div class="row" style="margin-top:15px; bottom:0px;">
											<a style="text-decoration:none;" href="Product?id=<c:out value="${ offer.getWineId() }"/>" class="action">
												<i class="glyphicon glyphicon-circle-arrow-right"></i>
											</a>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						<div class="col-xs-12 col-sm-6 col-md-4 item">
							<div class="card item">
								<form class="nopadding" id="type<c:out value="${ type.getId() }"/>Form" action="Results" method="POST">
									<input type="hidden" id="chosenType" name="chosenType" value="<c:out value="${ type.getId() }"/>">
									<a onclick="document.getElementById('type<c:out value="${ type.getId() }"/>Form').submit();">
										<div class="opacityDiv" style="width:100%;height:170px;background:url(https://tqbezw.dm2304.livefilestore.com/y4m7ImibB8RghV-NvoPSEWTgE7iHpyoKD8g7ubiIuhCkdJb16QKVw6cWSuio5dbn52nNCCOCGyGfoUAuUDi-VhM8Rau4ITh2Qw5GmbWfIT6qI104pntVSZWEqoGx6RE1KhCKM_9jCN-fPKBeUJ5KEwSsWc4BhNiWLS5E1cc3lKsuUJJKLo78B_v7OPu3NwLVfag?width=454&height=255&cropmode=none) no-repeat center center;background-size:contain;"></div>
									</a>
									<div class="text-wrapper">
										<a style="text-decoration: none;" onclick="document.getElementById('type<c:out value="${ type.getId() }"/>Form').submit();">
											<h3 style="color:#800000;" class="name">View more</h3>
										</a>
										<p class="description">Offers from ${ type.getWineTypeName() }</p>
										<div class="row" style="margin-top:15px; bottom:0px;">
											<a style="text-decoration:none;" onclick="document.getElementById('type<c:out value="${ type.getId() }"/>Form').submit();" class="action">
												<i class="glyphicon glyphicon-circle-arrow-right"></i>
											</a>
										</div>
									</div>
								</form>
							</div>
						</div>
			    	</div>
				</div>
		    </section>
	    </c:forEach>
	</c:if>
	<c:if test="${ sessionScope.countriesWithOffers != null }">
		<c:forEach items="${ sessionScope.countriesWithOffers }" var="country">
			<section id="mid-section">
				<h1 class="text-center" id="best-offers"><c:out value="${country.getCountryName() }"/> - Best offers</h1>
			    <hr class="sep-bar">
			    <div class="container pictures-container">
			    	<div class="row articles">
				    	<c:forEach items= "${country.bestOffers}" var="offer">
							<div class="col-xs-12 col-sm-6 col-md-4 item">
								<div class="card item">
									<a href="Product?id=<c:out value="${ offer.getWineId() }"/>">
										<div style="width:100%;height:170px;background:url(<c:out value = "${offer.getWineImageURL()}"/>) no-repeat center center;background-size:contain;"></div>
									</a>
									<div class="text-wrapper">
										<a style="text-decoration: none;" href="Product?id=<c:out value="${ offer.getWineId() }"/>">
											<h3 style="color:#800000;" class="name"><c:out value="${offer.getWineName()}"/></h3>
										</a>
										<p class="description"><c:out value="${offer.getWineShortDescription()}"/>
											<c:if test="${offer.getWineShortDescription().length() == 169}">
												<a href="Product?id=<c:out value="${ offer.getWineId() }"/>"> See more</a>
											</c:if>
										</p>
										<div class="row" style="margin-top:15px; bottom:0px;">
											<a style="text-decoration:none;" href="Product?id=<c:out value="${ offer.getWineId() }"/>" class="action">
												<i class="glyphicon glyphicon-circle-arrow-right"></i>
											</a>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						<div class="col-xs-12 col-sm-6 col-md-4 item">
							<div class="card item">
								<form class="nopadding" id="country<c:out value="${ country.getId() }"/>Form" action="Results" method="POST">
									<input type="hidden" name="country" value="<c:out value="${ country.getCountryName() }"/>">
									<a onclick="document.getElementById('country<c:out value="${ country.getId() }"/>Form').submit();">
										<div class="opacityDiv" style="width:100%;height:170px;background:url(https://tqbezw.dm2304.livefilestore.com/y4m7ImibB8RghV-NvoPSEWTgE7iHpyoKD8g7ubiIuhCkdJb16QKVw6cWSuio5dbn52nNCCOCGyGfoUAuUDi-VhM8Rau4ITh2Qw5GmbWfIT6qI104pntVSZWEqoGx6RE1KhCKM_9jCN-fPKBeUJ5KEwSsWc4BhNiWLS5E1cc3lKsuUJJKLo78B_v7OPu3NwLVfag?width=454&height=255&cropmode=none) no-repeat center center;background-size:contain;"></div>
									</a>
									<div class="text-wrapper">
										<a style="text-decoration: none;" onclick="document.getElementById('country<c:out value="${ country.getId() }"/>Form').submit();">
											<h3 style="color:#800000;" class="name">View more</h3>
										</a>
										<p class="description">Offers from ${ country.getCountryName() }</p>
										<div class="row" style="margin-top:15px; bottom:0px;">
											<a style="text-decoration:none;" onclick="document.getElementById('country<c:out value="${ country.getId() }"/>Form').submit();" class="action">
												<i class="glyphicon glyphicon-circle-arrow-right"></i>
											</a>
										</div>
									</div>
								</form>
							</div>
						</div>
			    	</div>
				</div>
		    </section>
	    </c:forEach>
	</c:if>
    <hr class="sep-bar">
	<c:import url="../templates/footer.jsp"/>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/navbar-appear.js"></script>
    <script>
    	<c:if test="${ sessionScope.displayMessage == true }">
    		setTimeout(showRegisterModal, 3000);
    		function showRegisterModal() { $("#modal-register").modal('show'); }
    	</c:if>
    	
    	<c:if test="${ sessionScope.passwordRecoverySuccessful != null }">
    		setTimeout(showRecoveryModal, 1500);
    		function showRecoveryModal() { $("#modal-recovery").modal('show'); }
    		<c:remove var="passwordRecoverySuccessful" scope="session"/>
    	</c:if>
    </script>
</body>

</html>