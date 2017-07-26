<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-default navbar-fixed-top" id="generalNav" style="background-color:#F5F5F5;">
	<div class="container">
		<div class="navbar-header">
			<a href="Home" class="navbar-brand navbar-link"> <img alt="Winedunk" src="assets/img/Logo_GrisRojo.jpg" height="100%" style="padding:0;" /></a>
			<button data-toggle="collapse" data-target="#navcol-1" class="navbar-toggle collapsed toggle-animated"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
		</div>
		<div class="collapse navbar-collapse" id="navcol-1">
			<ul class="nav navbar-nav">
			  <c:choose>
				  <c:when test="${sessionScope.isLoggedIn == true}">
				  	<li role="presentation"><a href="Profile"><c:out value="${sessionScope.userLoggedIn.getName()}"/></a></li>
				  	<li role="presentation"><a href="Logout">Log out</a></li>
				  </c:when>
				  <c:otherwise>
			  		<li role="presentation"><a href="Login?action=signUp">Sign up</a></li>
					<li role="presentation"><a href="Login?action=login">Log in </a></li>
			  	  </c:otherwise>
			  </c:choose>
			  <li role="presentation">
				  <a href="Basket">Basket
				  	<span class="badge hidden">
						<c:if test="${sessionScope.amountOfItemsInBasket != null}">
							<c:out value="${sessionScope.amountOfItemsInBasket}"/>
						</c:if>
					 </span>
				  </a>
			  </li>
			  <li role="presentation"><a href="http://blog.winedunk.com">Blog</a></li>
			</ul>
		</div>
	</div>
</nav>
<div class="navbarRibbon"></div>
