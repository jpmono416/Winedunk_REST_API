<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-xs-12">
	<div id="quickSearchDiv" class="card item">
	    <form id="search-form" action="Results" method="POST">
		    <h2 class="text-center red-text">Advanced search </h2>
		    <hr class="sep-bar">
		    <input class="form-control" id ="name" name = "name" type="text" placeholder="Name of the wine">
		    <div class="form-group"></div>
		    <div class="dropdown">
				<button id="typesDropdown"class="btn btn-default btn-block dropdown-toggle" data-toggle="dropdown" aria-expanded="false" type="button">Wine type <span class="caret"></span></button>
				<ul class="dropdown-menu" role="menu">
					<li style="cursor:pointer" role="presentation" onclick="changeType(this)" value="0"><a>All types</a></li>	                        	
					<c:forEach var = "i" items = "${sessionScope.listOfWineTypes.entrySet()}">
						<li style="cursor:pointer" role="presentation" onclick="changeType(this)" value = <c:out value ="${ i.getKey() }"/>>
							<a><c:out value="${i.getValue()}"></c:out></a>
						</li>
					</c:forEach>
				</ul>
			</div>
		    <input class="form-control" autocomplete="off" type="hidden" id="chosenType" name="chosenType">
		    <div class="form-group"></div>
		    <div class="dropdown">
				<button id="coloursDropdown"class="btn btn-default btn-block dropdown-toggle" data-toggle="dropdown" aria-expanded="false" type="button">Colour <span class="caret"></span></button>
				<ul class="dropdown-menu" role="menu">
					<li style="cursor:pointer" role="presentation" onclick="changeColour(this)" value="0"><a>All colours</a></li>	                        	
					<c:forEach var = "i" items = "${sessionScope.listOfColours.entrySet()}">
						<li style="cursor:pointer" role="presentation" onclick="changeColour(this)" value = <c:out value ="${ i.getKey() }"/>>
							<a><c:out value="${i.getValue()}"></c:out></a>
						</li>
					</c:forEach>
				</ul>
			</div>
			
			<input class="form-control" autocomplete="off" type="hidden" id="chosenShop" name="chosenShop" value="0">
			<div class="form-group"></div>
			<div class="dropdown">
				<button id="shopsDropdown"class="btn btn-default btn-block dropdown-toggle" data-toggle="dropdown" aria-expanded="false" type="button">Merchant <span class="caret"></span></button>
				<ul class="dropdown-menu" role="menu">
					<li style="cursor:pointer" role="presentation" onclick="changeShop(this)" value="0"><a>All merchants</a></li>	                        	
					<c:forEach var = "i" items = "${sessionScope.listOfShopsView.entrySet()}">
						<li style="cursor:pointer" role="presentation" onclick="changeShop(this)" value = <c:out value ="${ i.getKey() }"/>>
							<a><c:out value="${ i.getValue() }"></c:out></a>
						</li>
					</c:forEach>
				</ul>
			</div>
			
			
			<div class="form-group"></div>
			<div class="row">
				<div class="col-xs-6">
					<input class="form-control" type="text" placeholder="£ Min" id="minPrice" name="minPrice">
				</div>
				<div class="col-xs-6">
					<input class="form-control" type="text" placeholder="£ Max" id="maxPrice" name="maxPrice">
				</div>
			</div>
			<div class="form-group"></div>
			<input name="chosenColour" id="chosenColour" type="hidden" value="0"/>
			<input class="form-control" autocomplete="off" type="text" placeholder="Grape variety" id="grapeVariety" name="grapeVariety">
			<div class="form-group"></div>
			<input class="form-control" autocomplete="off" type="text" placeholder="Country" name="country" id = "country"/>
			<div class="form-group"></div>
			<input class="form-control" autocomplete="off" type="text" placeholder="Region" name="region" id="region"/>
			<div class="form-group"></div>
			<input class="form-control" autocomplete="off" type="text" placeholder="Appellation" id="appellation" name="appellation">
			<div class="form-group"></div>
			<input class="form-control" autocomplete="off" type="text" placeholder="Winery" id="winery" name="winery">
			<div class="form-group"></div>
			<div class="row">
				<div class="col-xs-6">
					<input class="form-control" type="number" placeholder="Min abv" step="0.5" id="abvMin" name="abvMin">
				</div>
				<div class="col-xs-6">
					<input class="form-control" type="number" placeholder="Max abv" step="0.5" id="abvMax" name="abvMax">
				</div>
			</div>
			<div class="form-group"></div>
			<div class="row">
				<div class="col-xs-6">
					<input class="form-control" type="number" placeholder="Min vintage" step="1" id="vintageMin" name="vintageMin">
				</div>
				<div class="col-xs-6">
					<input class="form-control" type="number" placeholder="Max vintage" step="1" id="vintageMax" name="vintageMax">
				</div>
			</div>
			
			<%-- Rating (commented till we got some ratings ) --%>
			<%-- 
			<div class="form-group"></div>
				<h2 class="text-center red-text">Rating</h2>
				<hr class="sep-bar"/>
				<h2 class="text-center">
                	<span id="formStars">
                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar1" class="glyphicon glyphicon-star"></i>
                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar2" class="glyphicon glyphicon-star"></i>
                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar3" class="glyphicon glyphicon-star"></i>
                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar4" class="glyphicon glyphicon-star"></i>
                		<i onclick="populateRating(this)" onmouseover="illuminateStars(this)" id="formStar5" class="glyphicon glyphicon-star"></i>
                	</span>
		        </h2>
		        <input id="ratingValue" name="ratingValue" type="hidden">
		    --%>
		        
			<div class="form-group"></div>
			<hr class="sep-bar">
			<div class="row" style="text-align:center;">
				<div class="col-md-12">
					<button class="btn btn-primary btn-lg redButton" type="submit" id="advanced-search"><i class="glyphicon glyphicon-search"></i> Find wines</button>
				</div>
			</div>
		</form>
	</div>
</div>
