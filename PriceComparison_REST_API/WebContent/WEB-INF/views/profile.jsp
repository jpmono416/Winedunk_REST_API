<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
<c:import url="../templates/head.jsp" />
<c:import url="../templates/google_analytics.jsp" />
</head>

<body style="background: #F7F7F7;">
	<div id="background"></div>
	<c:import url="../templates/navbar.jsp" />
	<c:import url="../templates/google_analytics.jsp" />
	<div class="container" id="main">
		<div class="row">
			<div class="col-sm-3">
				<div class="card leftPannel">
					<h3 class="text-center">Navigation</h3>
					<hr class="sep-bar">
					<ul class="list-unstyled" style="margin-bottom: 0;">
						<li class="settingsElement" onclick="showUser()"><a href="#"><i
								class="fa fa-user linkIcon"></i>User settings</a></li>
						<li class="settingsElement" onclick="showContact()"><a
							href="#"><i class="fa fa-phone linkIcon"></i>Contact details</a></li>
					</ul>
					<!--  
                       <hr class="sep-bar" style="margin-bottom:0;margin-top:0;">
                       <ul class="list-unstyled" style="margin-bottom:0;">
                           <li class="settingsElement" onclick="showNewsletter()"><a href="#"><i class="fa fa-envelope linkIcon"></i>Newsletter settings</a> </li>
                           <li class="settingsElement" onclick="showNotifications()"><a href="#"><i class="fa fa-bell linkIcon"></i>Notification settings</a> </li>
                           <li class="settingsElement" onclick="showDevices()"><a href="#"><i class="fa fa-desktop linkIcon"></i>Devices settings</a></li>
                       </ul>
                       <hr class="sep-bar" style="margin-bottom:0;">
                       -->
					<ul class="list-unstyled" style="margin-bottom: 0;">
						<li class="settingsElement" onclick="showFavouriteWines()"><a href="#"><i class="fa fa-heart linkIcon"></i>Favourite wines</a></li>
                        <li class="settingsElement" onclick="showWineReviews()"><a href="#"><i class="fa fa-commenting linkIcon"></i>Wine reviews</a></li>
                        <li class="settingsElement" onclick="showSavedSearches()"><a href="#"><i class="fa fa-search linkIcon"></i>Saved searches</a></li>
					</ul>
					<!--
					<hr class="sep-bar" style="margin-bottom: 0;">
					<ul class="list-unstyled" style="margin-bottom: 0;">
						 
                           <li class="settingsElement" onclick="showRegionSettings()"><a href="#"><i class="fa fa-gbp linkIcon"></i>Region settings</a></li>
                        
							<li class="settingsElement" onclick="showAccountSettings()"><a
							href="#"><i class="fa fa-lock linkIcon"></i>Account settings</a></li>
					</ul>
					-->
				</div>
			</div>
			<div class="col-sm-9">
				<c:if
					test="${sessionScope.error == true || sessionScope.successful == true}">
					<div id="informationArea" class="card settingsCard">
						<c:if test="${sessionScope.error == true}">
							<div role="alert" class="alert alert-danger" style="margin: 0;">
								<span><strong>We are sorry, the details were not
										saved. Please try again later.</strong></span>
							</div>
						</c:if>
						<c:if test="${sessionScope.successful == true}">
							<div role="alert" class="alert alert-success" style="margin: 0;">
								<span><strong>The details were saved
										successfully!</strong></span>
							</div>
						</c:if>
					</div>
				</c:if>
				<div id="userSettingsArea" <c:if test="${!sessionScope.sectionToBeDisplayed.equals(\"user\")}">style="display: none;"</c:if>>
					<div
						class="card settingsCard <c:if test="${sessionScope.sectionToBeDisplayed.equals(\"user\")}"> default</c:if>">
						<h1 class="text-center">Personal details</h1>
						<hr class="sep-bar">
						<form action="Profile" method="POST">
						<input type="hidden" name="formChosen" value="editDetails">
							<div class="row">
								<div class="col-sm-4 col-xs-12">
									<h4>Name:</h4>
								</div>
								<div class="col-sm-8 col-xs-12">
								<h4 id="wellName"><c:out value="${requestScope.userForDetails.getName()}"/>
									<span class="badge" style="background: #800000;">  <span class="glyphicon glyphicon-pencil redText"></span></span>
								</h4>
									<input id="inputName" name="userName" class="form-control" type="text"
										placeholder="New name" style="display:none">
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4 col-xs-12">
									<h4>Preferred email:</h4>
								</div>
								<div class="col-sm-8 col-xs-12">
								<h4 id="wellEmail"><c:out value="${requestScope.userForDetails.getPreferredEmail()}" />
									<span class="badge" style="background: #800000;">  <span class="glyphicon glyphicon-pencil redText"></span></span>
								</h4>
									<input id="inputEmail" name="preferredEmail" class="form-control" type="text"
										placeholder="New email" style="display:none">
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4 col-xs-12">
									<h4>Preferred email to recover password:</h4>
								</div>
								<div class="col-sm-8 col-xs-12">
								<h4 id="wellRecoveringEmail"><c:out value="${requestScope.userForDetails.getRecoveringPassEmail()}" />
									<span class="badge" style="background: #800000;">  <span class="glyphicon glyphicon-pencil redText"></span></span>
								</h4>
									<input id="inputRecoveringEmail" name="recoveringEmail" class="form-control" type="text"
										placeholder="New recovery email" style="display:none">
								</div>
							</div>
							<div class="row hidden">
								<div class="col-sm-4 col-xs-12">
									<h4>Preferred phone number:</h4>
								</div>
								<div class="col-sm-8 col-xs-12">
								<h4 id="wellPhoneNumber"><c:out value="${requestScope.userForDetails.getPreferredPhoneNumber()}" />
									<span class="badge" style="background: #800000;">  <span class="glyphicon glyphicon-pencil redText"></span></span>
								</h4>
									<input id="inputPhoneNumber" class="form-control regionInput hidden" type="text"
										placeholder="New phone number" style="display:none">
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<button class="btn btn-primary" type="submit" id="saveUser">Save
										settings</button>
								</div>
							</div>
						</form>
					</div>
					<div class="card settingsCard"
						<c:if test="${sessionScope.sectionToBeDisplayed.equals(\"user\")}">style="display: block;"</c:if>
						<c:if test="${!sessionScope.sectionToBeDisplayed.equals(\"user\")}">style="display: none;"</c:if>>
						<h1 class="text-center">Login details</h1>
						<hr class="sep-bar">
						<form>
							<div class="row">
								<div class="text-center">
									<a data-toggle="modal" data-target="#editPasswordModal"
										class="btn btn-primary btn-large" style="background: #800000;">Change
										password</a>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div id="contactDetailsArea"
					class="card settingsCard <c:if test="${sessionScope.sectionToBeDisplayed.equals(\"contact\")}"> default</c:if>"
					<c:if test="${!sessionScope.sectionToBeDisplayed.equals(\"contact\")}">style="display: none;"</c:if>>
					<h1 class="text-center">Contact details</h1>
					<hr class="sep-bar">
					<div class="row">
						<div class="col-md-12">
							<div class="panel panel-default panel-table">
								<div class="panel-heading">
									<div class="row">
										<div class="col-md-8">
											<h3 class="panel-title title-text">Phone Numbers</h3>
										</div>
										<div class="col-md-4">
											<button class="btn btn-primary btn-sm redButton"
												type="button" data-toggle="modal"
												data-target="#newPhoneModal" style="float: right;">New
											</button>
										</div>
									</div>
								</div>
								<div class="panel-body">
									<div class="table-responsive">
										<table class="table table-striped table-bordered">
											<thead>
												<tr>
													<th class="text-center"><i class="fa fa-gear"></i></th>
													<th>Phone Number</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${ requestScope.noPhoneNumbers != true }">
													<c:forEach items="${requestScope.phoneNumbersList}"
														var="phoneNumber">
														<tr>
															<td>
																<form action="Profile" method="POST"
																	style="padding: 0; margin: 0; display: inline !important;">
																	<input class="hidden" name="phoneNumberId"
																		value="${phoneNumber.getId()}"> <a
																		onclick="populatePhoneEdition(this)"
																		class="btn btn-default"
																		data-id="${phoneNumber.getId()}" data-toggle="modal"
																		data-target="#editPhoneModal"> <i
																		class="fa fa-pencil"></i>
																	</a>
																</form>
																<form action="Profile" method="POST"
																	style="padding: 0; margin: 0; display: inline !important;">
																	<input class="hidden" name="phoneNumberId"
																		value="${phoneNumber.getId()}"> <input
																		class="hidden" name="formChosen"
																		value="deletePhoneNumber">
																	<button class="btn btn-danger" type="submit">
																		<i class="fa fa-trash"></i>
																	</button>
																</form>
															</td>
															<td>${phoneNumber.getPhoneNumber()}</td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="panel panel-default panel-table">
								<div class="panel-heading">
									<div class="row">
										<div class="col-md-8">
											<h3 class="panel-title title-text">Email Addresses</h3>
										</div>
										<div class="col-md-4">
											<button class="btn btn-primary btn-sm redButton"
												type="button" data-toggle="modal"
												data-target="#newEmailModal" style="float: right;">New
											</button>
										</div>
									</div>
								</div>
								<div class="panel-body">
									<div class="table-responsive">
										<table class="table table-striped table-bordered">
											<thead>
												<tr>
													<th class="text-center"><i class="fa fa-gear"></i></th>
													<th>Email Address</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${ requestScope.noEmails != true }">
													<c:forEach items="${requestScope.emailsList}" var="email">
														<tr>
															<td>
																<form action="Profile" method="POST"
																	style="display: inline !important">
																	<input class="hidden" name="emailId"
																		value="${email.getId()}"> <a
																		onclick="populateEmailEdition(this)"
																		class="btn btn-default" data-id="${email.getId()}"
																		data-toggle="modal" data-target="#editEmailModal">
																		<i class="fa fa-pencil"></i>
																	</a>
																</form>
																<form action="Profile" method="POST"
																	style="display: inline !important">
																	<input class="hidden" name="emailId"
																		value="${email.getId()}"> <input class="hidden"
																		name="formChosen" value="deleteEmailAddress">
																	<button class="btn btn-danger" type="submit">
																		<i class="fa fa-trash"></i>
																	</button>
																</form>
															</td>
															<td>${email.getEmailAddress()}</td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--
                   <div id="newsletterSettingsArea" class="card settingsCard" style="display:none;">
                       <h1 class="text-center">Newsletter settings</h1>
                       <hr class="sep-bar">
                       <section></section>
                       <form>
                           <div class="checkbox">
                               <label for="chkNewsletter">
                                   <input type="checkbox" checked="" id="chkNewsletter">I want to receive news from WineDunk</label>
                           </div>
                           <div class="checkbox">
                               <label for="chkAssociates">
                                   <input type="checkbox" checked="" id="chkAssociates">I want to receive news from WineDunk associates related to best offers</label>
                           </div>
                           <button class="btn btn-primary redButton" type="button" id="btnSaveNewslettes" style="float:right;">Save changes</button>
                       </form>
                   </div>
                   -->
				<!--  
                   <div id="notificationSettingsArea" class="card settingsCard" style="display:none;">
                       <h1 class="text-center">Notification settings</h1>
                       <hr class="sep-bar">
                   </div>
                   -->
				<!--  
                   <div id="devicesSettingsArea" class="card settingsCard" style="display: none;">
                       <h1 class="text-center">Devices </h1>
                       <hr class="sep-bar">
                       <div class="row">
                           <div class="col-md-12">
                               <div class="panel panel-default panel-table">
                                   <div class="panel-heading">
                                       <div class="row">
                                           <div class="col-md-8">
                                               <h3 class="panel-title title-text">Devices </h3></div>
                                           <div class="col-md-4">
                                               <button class="btn btn-primary btn-sm redButton" type="button" style="float:right;">New </button>
                                           </div>
                                       </div>
                                   </div>
                                   <div class="panel-body">
                                       <div class="table-responsive">
                                           <table class="table table-striped table-bordered">
                                               <thead>
                                                   <tr>
                                                       <th class="text-center"> <i class="fa fa-gear"></i></th>
                                                       <th>Name</th>
                                                       <th>Type </th>
                                                   </tr>
                                               </thead>
                                               <tbody>
                                                   <tr>
                                                       <td>
                                                           <a href="#" class="btn btn-default"> <i class="fa fa-pencil"></i></a>
                                                           <a href="#" class="btn btn-danger"> <i class="fa fa-trash"></i></a>
                                                       </td>
                                                       <td>Android Phone</td>
                                                       <td>Phone </td>
                                                   </tr>
                                                   <tr>
                                                       <td>
                                                           <a href="#" class="btn btn-default"> <i class="fa fa-pencil"></i></a>
                                                           <a href="#" class="btn btn-danger"> <i class="fa fa-trash"></i></a>
                                                       </td>
                                                       <td>Laptop 1</td>
                                                       <td>PC </td>
                                                   </tr>
                                               </tbody>
                                           </table>
                                       </div>
                                   </div>
                               </div>
                           </div>
                       </div>
                   </div>
                   -->
				<div id="favouriteWinesArea" <c:if test="${!sessionScope.sectionToBeDisplayed.equals(\"favouriteWines\")}">style="display: none;"</c:if>>
					<div class="card settingsCard <c:if test="${sessionScope.sectionToBeDisplayed.equals(\"favouriteWines\")}"> default </c:if>"
					>
						<h1 class="text-center">Favourite wines</h1>
						<hr class="sep-bar">
					</div>
					<div>
						<div class="row articles">
							<c:if test="${ requestScope.noFavourites != true }">
								<c:forEach items="${requestScope.favouriteWines}" var="i">
									<div class="col-xs-12 col-sm-6 col-md-4">
										<div class="card item">
											<a href="Product?id=<c:out value="${ i.getId() }" />">
												<div style="width:100%;height:170px;background:url(<c:out value = "${i.getImageURL()}"/>) no-repeat center center;background-size:contain;"></div>
											</a>
											<div class="text-wrapper">
												<a style="text-decoration: none;"
													href="Product?id=<c:out value="${ i.getId() }" />"><h3 style="color: #800000;" class="name">
														<c:out value="${i.getName()}" />
													</h3></a>
												<p class="description">
													<c:out value="${i.getShortDescription()}" />
													<c:if test="${i.getShortDescription().length() == 169}">
														<a href="Product?id=<c:out value="${ i.getId() }" />">
															See more</a>
													</c:if>
												</p>
											</div>
											<div class="row" style="position:absolute; bottom:0; width:100% !important;">
												<div class="col-xs-6">
													<a style="text-decoration: none; margin-top:-50px;"
													href="Product?id=<c:out value="${ i.getId() }" />"
													class="action" data-toggle="tooltip" 
												    title="Go to product page"><i
													class="glyphicon glyphicon-circle-arrow-right"></i>
													</a>												
												</div>
												<div class="col-xs-6">
													<a style="text-decoration: none; margin-top:-50px; cursor:pointer;"
													class="action" data-toggle="tooltip" 
												    title="Remove from favourite" onclick="deleteWine(<c:out value="${ i.getId() }" />)"><i
													class="glyphicon glyphicon-remove"></i>
													</a>												
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</c:if>
						</div>
					</div>
				</div>
				
                <div id="wineReviewsArea" <c:if test="${!sessionScope.sectionToBeDisplayed.equals(\"wineReviews\")}"> style="display: none" </c:if>>
	                <div class="card settingsCard 
	               	<c:if test="${sessionScope.sectionToBeDisplayed.equals(\"wineReviews\")}"> default </c:if>">
	                    <h1 class="text-center">Reviews </h1>
	                    <hr class="sep-bar">
	                </div>
	                <div class="card settingsCard
	                <c:if test="${sessionScope.sectionToBeDisplayed.equals(\"wineReviews\")}"> default </c:if>">
	                    <div class="row">
	                        <div class="col-md-12">
	                            <div class="panel panel-default panel-table">
	                                <div class="panel-heading">
	                                    <div class="row">
	                                        <div class="col-md-12">
	                                            <h3 class="panel-title title-text text-center">Reviews </h3></div>
	                                    </div>
	                                </div>
	                                <div class="panel-body">
	                                    <div class="table-responsive">
	                                        <table class="table table-striped table-bordered">
	                                            <thead>
	                                                <tr>
	                                                    <th class="text-center"> <i class="fa fa-gear"></i></th>
	                                                    <th>Comment </th>
	                                                    <th>Wine </th>
	                                                    <th>Your score </th>
	                                                </tr>
	                                            </thead>
	                                            <tbody>
		                                            <c:if test="${ requestScope.noReviews != true }">
		                                            	<c:forEach items="${ requestScope.userReviews }" var="review">
			                                            	<tr>
			                                                    <td class="text-center">
																	<form action="Profile" method="POST"
																		style="display: inline !important">
																		<input class="hidden" name="reviewId"
																			value="${review.getId()}"> <a
																			onclick="populateReviewEdition(this)"
																			class="btn btn-default" data-id="${ review.getId() }"
																			data-wineId="${ review.getWineId() }"
																			data-toggle="modal" data-target="#editReviewModal">
																			<i class="fa fa-pencil"></i>
																		</a>
																	</form>
																	<form action="Profile" method="POST"
																		style="display: inline !important">
																		<input class="hidden" name="reviewId"
																			value="${review.getId()}"> <input class="hidden"
																			name="formChosen" value="deleteReview">
																		<button class="btn btn-danger" type="submit">
																			<i class="fa fa-trash"></i>
																		</button>
																	</form>
			                                                    </td>
			                                                    <td><c:out value="${ review.getReviewComments() }"/></td>
			                                                    <td>${ review.getWineName() }</td>
			                                                    <td><c:out value="${ review.getUserRating() }"/></td>
			                                                </tr>
		                                            	</c:forEach>
		                                            </c:if>
	                                            </tbody>
	                                        </table>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	                <div class="card settingsCard">
	                	<h1 class="text-center">Ratings </h1>
	                    <hr class="sep-bar">
	                </div>
	                <div class="card settingsCard">
	                	<table class="table">
					        <thead class="text-center">
					            <tr>
					                <th>Details</th>
					                <th>Actions</th>
					            </tr>
					        </thead>
					        <tbody>
					        	<c:if test="${ requestScope.noRatings != true }">
					        		<c:forEach items="${requestScope.ratingsList}" var="rating">
								        <tr>
							                <td>
								                <div class="col-xs-12 col-md-3">
								            		<a style="text-decoration:none;color:#800000;" href="http://www.winedunk.com/Product?id=<c:out value="${rating.getWineId()}"/>"><div style="width:100%;height:170px;background:url(<c:out value="${rating.getWineImageURL()}"/>) no-repeat center center;background-size:contain;"></div></a>
								            	</div>
							                    <div class="col-xs-12 col-md-9">
							                        <a style="text-decoration:none;color:#800000;" href="http://www.winedunk.com/Product?id=<c:out value="${rating.getWineId()}"/>"><h3 class="text-center"><c:out value="${ rating.getWineName() }"/></h3></a>
							                    </div>
							                    <div class="col-xs-12 col-md-9">
							                        <h1 class="text-center">
														<span id="stars">
															<c:forEach varStatus="loop" begin="1" end="${ rating.getUserRating() }"><i class="glyphicon glyphicon-star"></i></c:forEach>
														</span>
													</h1>
							                    </div>
							                </td>
							                <td>
						                    	<form action="Profile" method="POST"
													style="display: inline !important;">
													<input class="hidden" name="reviewId"
														value="${ rating.getId() }"> <a style="margin-top: 50px"
														onclick="populateRatingEdition(this)"
														class="btn btn-default" data-id="${ rating.getId() }"
														data-wineId="${ rating.getWineId() }"
														data-toggle="modal" data-target="#editRatingModal">
														<i class="fa fa-pencil"></i>
													</a>
												</form>
												<form action="Profile" method="POST"
													style="display: inline !important">
													<input class="hidden" name="ratingId"
														value="${ rating.getId() }"> <input class="hidden"
														name="formChosen" value="deleteRating">
													<button class="btn btn-danger" type="submit" style="margin-top: 50px">
														<i class="fa fa-trash"></i>
													</button>
												</form>
							                </td>
							            </tr>
							        </c:forEach>
					        	</c:if>
					        </tbody>
					    </table>
	                </div>
                </div>
                <div id="savedSearchesArea" class="card settingsCard <c:if test="${sessionScope.sectionToBeDisplayed.equals(\"savedSearches\")}"> default </c:if>" <c:if test="${!sessionScope.sectionToBeDisplayed.equals(\"savedSearches\")}"> style="display: none" </c:if>>
                    <h1 class="text-center">Saved searches</h1>
                    <hr class="sep-bar">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-default panel-table">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <h3 class="panel-title title-text">Saved searches</h3></div>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                                <tr>
                                                    <th class="text-center"> <i class="fa fa-gear"></i></th>
                                                    <th>Name </th>
                                                    <th>Creation date</th>
                                                    <th>Replicate</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            	<c:if test="${ requestScope.noSearches != true }">
	                                            	<c:forEach items="${ requestScope.savedSearches }" var="search">
		                                            	<tr>
		                                                    <td class="text-center">
		                                                    	<form action="Profile" method="POST">
		                                                    		<button class="btn btn-danger" type="submit"><i class="fa fa-trash"></i></button>
		                                                    		<input type="hidden" name="formChosen" value="deleteSavedSearch">
		                                                    		<input type="hidden" name="searchId" value="${ search.getId() }">
		                                                    	</form>
		                                                    </td>
		                                                    <td><c:out value="${ search.getName() }"/></td>
		                                                    <td><c:out value = "${ search.getCreated() }"/></td>
		                                                    <td class="text-center">
		                                                    	<a href="winedunk.com/share<c:out value="${ search.getUrlString() }"/>" class="btn btn-danger redButton">Go!</a>
		                                                   	</td>
		                                                </tr>
		                                            </c:forEach>
                                            	</c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
				<!--  
                   <div id="regionSettingsArea" class="card settingsCard" style="display:none;">
                       <h1 class="text-center">Region settings</h1>
                       <hr class="sep-bar">
                       <form method="post">
                           <div class="row">
                               <div class="col-sm-6 col-xs-6">
                                   <h3>Country </h3><span class="label label-default" style="background:#800000;">Current: Spain</span></div>
                               <div class="col-xs-6">
                                   <input class="form-control regionInput" type="text" placeholder="Country" id="country">
                               </div>
                           </div>
                           <div class="row">
                               <div class="col-sm-6 col-xs-6">
                                   <h3>Language </h3><span class="label label-danger" style="background:#800000;">Current: Spanish</span></div>
                               <div class="col-xs-6">
                                   <input class="form-control regionInput" type="text" placeholder="Language" id="language">
                               </div>
                           </div>
                           <div class="row">
                               <div class="col-sm-6 col-xs-6">
                                   <h3>Currency </h3><span class="label label-danger" style="background:#800000;">Current: Euro</span></div>
                               <div class="col-xs-6">
                                   <input class="form-control regionInput" type="text" placeholder="Currency" id="currency">
                               </div>
                           </div>
                           <div class="row">
                               <div class="col-md-12">
                                   <button class="btn btn-primary" type="submit" id="saveRegion">Save settings</button>
                               </div>
                           </div>
                       </form>
                   </div>
                   
				<div id="accountSettingsArea" class="card settingsCard"
					style="display: none;">
					<h1 class="text-center">Account settings</h1>
					<hr class="sep-bar">
					<div class="text-center">
						<a href="#" class="btn btn-primary btn-large"
							style="background: #800000;">Delete account</a>
					</div>
				</div>
				-->
			</div>
		</div>
	</div>
	<div class="modal fade" id="newPhoneModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Add phone number</h4>
				</div>
				<div class="modal-body">
					<form method="POST" action="Profile">

						<input class="hidden" name="formChosen" type="text"
							value="addPhoneNumber"> <input class="form-control"
							name="phoneNumber" type="text" placeholder="New phone number">
						<div class="form-group"></div>
						<button type="submit" class="btn btn-primary redButton">Save
							changes</button>
						<button type="button" class="btn btn-secondary"
							style="float: right" data-dismiss="modal">Close</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editPhoneModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Edit phone number</h4>
				</div>
				<div class="modal-body">
					<form method="POST" action="Profile">
						<input class="hidden" name="formChosen" type="text"
							value="editPhoneNumber"> <input class="hidden"
							name="numberId" id="numberId" type="text"> <input
							class="form-control" name="phoneNumber" type="text"
							placeholder="Phone number">
						<div class="form-group"></div>
						<button type="submit" class="btn btn-primary redButton">Save
							changes</button>
						<button type="button" class="btn btn-secondary"
							style="float: right" data-dismiss="modal">Close</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editPasswordModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Change password</h4>
				</div>
				<div class="modal-body">
					<form method="POST" action="Profile">
						<input class="hidden" name="formChosen" type="text"
							value="changePassword"> <input class="form-control"
							name="previousPassword" type="password"
							placeholder="Previous password" required="">
						<div class="form-group"></div>
						<input class="form-control" name="newPassword" type="password"
							placeholder="New password" required="">
						<div class="form-group"></div>
						<button type="submit" class="btn btn-primary redButton">Save
							changes</button>
						<button type="button" class="btn btn-secondary"
							style="float: right" data-dismiss="modal">Close</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="newEmailModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Add email address</h4>
				</div>
				<div class="modal-body">
					<form method="POST" action="Profile">
						<input class="hidden" name="formChosen" type="text"
							value="addEmailAddress"> <input class="form-control"
							name="emailAddress" type="text" placeholder="New email address">
						<div class="form-group"></div>
						<button type="submit" class="btn btn-primary redButton">Save
							changes</button>
						<button type="button" class="btn btn-secondary"
							style="float: right" data-dismiss="modal">Close</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editEmailModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Edit email address</h4>
				</div>
				<div class="modal-body">
					<form method="POST" action="Profile">
						<input class="hidden" name="formChosen" type="text"
							value="editEmailAddress"> <input class="hidden"
							name="emailId" id="emailId" type="text"> <input
							class="form-control" name="emailAddress" type="text"
							placeholder="Email address">
						<div class="form-group"></div>
						<button type="submit" class="btn btn-primary redButton">Save
							changes</button>
						<button type="button" class="btn btn-secondary"
							style="float: right" data-dismiss="modal">Close</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editReviewModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Edit review</h4>
				</div>
				<div class="modal-body">
					<form method="POST" action="Profile">
						<input class="hidden" name="formChosen" type="text"
							value="editReview"> 
						<input class="hidden" name="reviewId" id="reviewId" type="text">
						<input class="hidden" name="reviewWineId" id="reviewWineId" type="text"> 
						<input
							class="form-control" name="review" type="text"
							placeholder="Review">
						<div class="form-group"></div>
						<button type="submit" class="btn btn-primary redButton">Save
							changes</button>
						<button type="button" class="btn btn-secondary"
							style="float: right" data-dismiss="modal">Close</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editRatingModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Edit rating</h4>
				</div>
				<div class="modal-body">
					<h3 class="text-center">Edit rating</h3>
	                <hr class="sep-bar">
					<form method="POST" action="Profile">
						<h1 class="text-center">
		                	<span id="formStars">
		                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar1" class="glyphicon glyphicon-star"></i>
		                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar2" class="glyphicon glyphicon-star"></i>
		                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar3" class="glyphicon glyphicon-star"></i>
		                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar4" class="glyphicon glyphicon-star"></i>
		                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar5" class="glyphicon glyphicon-star"></i>
		                	</span>
				        </h1>
						<input class="hidden" name="formChosen" type="text" value="editRating"> 
						<input class="hidden" name="ratingId" id="ratingId" type="text">
						<input class="hidden" name="ratingWineId" id="ratingWineId" type="text">
						<input id="ratingValue" name="ratingValue" type="hidden">
						<div class="form-group"></div>
						<button type="submit" class="btn btn-primary redButton">Save changes</button>
						<button type="button" class="btn btn-secondary" style="float: right" data-dismiss="modal">Close</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<c:import url="../templates/footer.jsp" />
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/js/userPage.js"></script>
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
	<script>
		var user            = document.getElementById('userSettingsArea').style;
		var contact         = document.getElementById('contactDetailsArea').style;
		var favouriteWines  = document.getElementById('favouriteWinesArea').style;
		var wineReviews     = document.getElementById('wineReviewsArea').style;
		var savedSearches   = document.getElementById('savedSearchesArea').style;
		/*var newsletter      = document.getElementById('newsletterSettingsArea').style;
		var notifications   = document.getElementById('notificationSettingsArea').style;
		var devices         = document.getElementById('devicesSettingsArea').style;
		var regionSettings  = document.getElementById('regionSettingsArea').style;
		var accountSettings = document.getElementById('accountSettingsArea').style;*/
	
		<c:if test="${sessionScope.sectionToBeDisplayed.equals(\"user\")}"> var active = user; </c:if>
		<c:if test="${sessionScope.sectionToBeDisplayed.equals(\"contact\")}"> var active = contact; </c:if>
		<c:if test="${sessionScope.sectionToBeDisplayed.equals(\"favouriteWines\")}"> var active = favouriteWines; </c:if>
		<c:if test="${sessionScope.sectionToBeDisplayed.equals(\"wineReviews\")}"> var active = wineReviews; </c:if>
		<c:if test="${sessionScope.sectionToBeDisplayed.equals(\"savedSearches\")}"> var active = savedSearches; </c:if>
		
		function showUser()
		{
		    if(user.display != '' || user.display != 'none')
		    {
		        active.display = 'none';
		        user.display = 'block';
		        active = user;
		    }
		}
	
		function showContact()
		{
		    if(contact.display != '' || contact.display != 'none')
		    {
		        active.display = 'none';
		        contact.display = 'block';
		        active = contact;
		    }
		}
		/*
		function showNewsletter()
		{
		    if(newsletter.display != '' || newsletter.display != 'none')
		    {
		        active.display = 'none';
		        newsletter.display = 'block';
		        active = newsletter;
		    }
		}
	
		function showNotifications()
		{
		    if(notifications.display != '' || notifications.display != 'none')
		    {
		        active.display = 'none';
		        notifications.display = 'block';
		        active = notifications;
		    }
		}
	
		function showDevices()
		{
		    if(devices.display != '' || devices.display != 'none')
		    {
		        active.display = 'none';
		        devices.display = 'block';
		        active = devices;
		    }
		}
	*/
		function showFavouriteWines()
		{
		    if(favouriteWines.display != '' || favouriteWines.display != 'none')
		    {
		        active.display = 'none';
		        favouriteWines.display = 'block';
		        active = favouriteWines;
		    }
		}
	
		function showWineReviews()
		{
		    if(wineReviews.display != '' || wineReviews.display != 'none')
		    {
		        active.display = 'none';
		        wineReviews.display = 'block';
		        active = wineReviews;
		    }
		}
	
		function showSavedSearches()
		{
		    if(savedSearches.display != '' || savedSearches.display != 'none')
		    {
		        active.display = 'none';
		        savedSearches.display = 'block';
		        active = savedSearches;
		    }
		}
	/*
		function showRegionSettings()
		{
		    if(regionSettings.display != '' || regionSettings.display != 'none')
		    {
		        active.display = 'none';
		        regionSettings.display = 'block';
		        active = regionSettings;
		    }
		}
	*/
		function showAccountSettings()
		{
		    if(accountSettings.display != '' || accountSettings.display != 'none')
		    {
		        active.display = 'none';
		        accountSettings.display = 'block';
		        active = accountSettings;
		    }
		}
		
		/* Control the "saved successfully/there was an error" messages' fade out*/
		$(document).ready(function() 
    	{ 
    		setTimeout(function() {
    		    $('#informationArea').fadeOut('slow');
    		}, 3000);
    	});
	
		/*  
		 *	Populate a hidden input on the modal forms for phone numbers
		 *	and email addresses when the user clicks on the "edit" button
		 */
		function populatePhoneEdition(ele)
		{
			var id = ele.getAttribute('data-id');
			document.getElementById('numberId').value = id;
		}
		
		function populateEmailEdition(ele)
		{
			var id = ele.getAttribute('data-id');
			document.getElementById('emailId').value = id;
		}
		
		function populateRatingEdition(ele)
		{
			var id = ele.getAttribute('data-id');
			alert('DATA ID: ' + id);
			var wineId = ele.getAttribute('data-wineId');
			alert('DATA WID: ' + wineId);
			document.getElementById('ratingId').value = id;
			document.getElementById('ratingWineId').value = wineId;
		}
		
	</script>
	<script>
		function deleteWine(wineId)
	   	{ 
	   		var id = wineId;
	   		$.ajax({
	           url:'Profile',
	           type:'POST',
	           data: 
	           { 
					wineId : id,
					formChosen : 'deleteFavouriteWine'
	           },
	           success: function()
	       	   {
	       	   		location.reload();
	       	   }});
	   	}
	</script>
	<script>
		$('#wellName').click(function(){
			$('#wellName').fadeOut();
			$('#inputName').delay(1000).fadeIn();
		});
		
		$('#wellEmail').click(function(){
			$('#wellEmail').fadeOut();
			$('#inputEmail').delay(1000).fadeIn();
		});
		
		$('#wellRecoveringEmail').click(function(){
			$('#wellRecoveringEmail').fadeOut();
			$('#inputRecoveringEmail').delay(1000).fadeIn();
		});
		
		$('#wellPhoneNumber').click(function(){
			$('#wellPhoneNumber').fadeOut();
			$('#inputPhoneNumber').delay(1000).fadeIn();
		});
	</script>
	<c:import url="../templates/enableTooltips.jsp"/>
</body>

</html>