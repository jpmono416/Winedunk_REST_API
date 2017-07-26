<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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