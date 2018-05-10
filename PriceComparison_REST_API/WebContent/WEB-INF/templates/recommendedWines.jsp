<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach items= "${sessionScope.recommendedWines}" var="i">
	<div class="col-xs-12 col-sm-6 col-md-4 item">
		<div class="card item">
		
			<div>
		        <div class="row">
		            <div class="col-md-6">
						<a href="Product?id=<c:out value="${ i.getId() }" />">
							<div style="width:100%;height:170px;background:url(<c:out value = "${i.getImageURL()}"/>) no-repeat center center;background-size:contain;"></div>
						</a>
		            </div>
		            <div class="col-md-6" align="center">
		            
			            <%-- Making sure both % off and was are displayed only if saving --%>
		                <c:choose>
							<c:when test="${i.getSaving() > 0}">
				                <div class="row"> <span class="label label-default">Save <fmt:formatNumber type="currency" value="${i.getSaving()}"/> ( -<c:out value="${i.getPercentageOff()}"/>% )</span> </div>
				                <div class="row"> <h5 class="text-center" style="text-decoration: line-through;">Was: <fmt:formatNumber type="currency" value="${i.getPreviousMaxPrice()}"/></h5> </div>
				                <div class="row"> <h4 class="text-center red-text" style="margin-top:0;">Now: <fmt:formatNumber type="currency" value="${i.getMinimumPrice()}"/></h4> </div>
							</c:when>
							<c:otherwise>
							    <div class="row"> <h3 class="text-center red-text" style="margin-top:0;"><fmt:formatNumber type="currency" value="${i.getMinimumPrice()}"/></h3> </div>
							</c:otherwise>
						</c:choose>
						
		                <div class="row">
		                	<a class="btn btn-primary redButton" target="_blank" style="text-decoration: none;" href="<c:out value = "${i.getMinimumPriceClicktag()}"/>">Buy now</a>
		                	<a class="btn btn-primary secondaryButton" target="_blank" style="text-decoration: none;" href="Product?id=<c:out value="${ i.getId() }" />">Compare</a>
		                </div>
		            </div>
		        </div>
			</div>
				
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