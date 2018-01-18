<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <c:import url="../templates/head.jsp"/>
    <c:import url="../templates/google_analytics.jsp"/>
</head>

<body>
    <div id="background"></div>
    <fmt:setLocale value="en_GB"/>
    <c:import url="../templates/navbar.jsp"/>
    <c:import url="../templates/google_analytics.jsp"/>
    <section style="margin-top:150px;">
        <div class="container">
            <div class="row">
                <div class="col-sm-10 col-sm-offset-1 col-xs-12">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="card settingsCard">
                                <h1 class="red-text text-center">Basket</h1>
                                <hr class="sep-bar">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="table-responsive">
										    <table class="table">
										        <thead class="text-center">
										            <tr>
										            	<th>Select</th>
										                <th>Product details</th>
										                <th class="text-center">Price </th>
										                <th class="text-center">Shop </th>
										                <th class="text-center">Go to shop </th>
										            </tr>
										        </thead>
										        <tbody>
											        <c:forEach items="${sessionScope.basketList}" var="wine">
												        <tr>
												        	<td class="text-center">
												        		<label class="switch" style="position:relative; top: 85px;">
																  <input type="checkbox">
																  <div class="slider round" data-id="<c:out value="${wine.getWineId()}"/>"
																  data-redir="<c:out value="${wine.getMinimumPriceDestinationURL()}"/>"></div>
																</label>
												        	</td>
											                <td>
												                <div class="col-xs-12 col-md-3">
												            		<a style="text-decoration:none;color:#800000;" href="http://www.winedunk.com/Product?id=<c:out value="${wine.getWineId()}"/>"><div style="width:100%;height:170px;background:url(<c:out value="${wine.getImageURL()}"/>) no-repeat center center;background-size:contain;"></div></a>
												            	</div>
											                    <div class="col-xs-12 col-md-9">
											                        <a style="text-decoration:none;color:#800000;" href="http://www.winedunk.com/Product?id=<c:out value="${wine.getWineId()}"/>"><h3><c:out value="${wine.getName()}"/></h3></a>
											                        <p><c:out value="${wine.getDefaultDescription()}"/></p>
											                    </div>
											                </td>
											                <td><strong><fmt:formatNumber type="currency" value="${wine.getMinimumPrice()}"/></strong> </td>
											                <td style="min-width:90px;"><c:out value="${wine.getMinimumPriceShopName()}"/></td>
											                <td><button class="btn redButton"><a href="<c:out value="${wine.getMinimumPriceDestinationURL()}"/>">Go</a></button> </td>
											            </tr>
											        </c:forEach>
										        </tbody>
										    </table>
										    <hr class="sep-bar">
										    <div class="col-md-10 col-md-offset-1 text-center">
											    <button onclick="removeWines()"class="btn btn-primary redButton">Remove selected</button>
											    <button onclick="removeAll()"class="btn btn-primary redButton">Remove all</button>
										    </div>
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
    <hr class="sep-bar">
    <c:import url="../templates/footer.jsp"/>
    <div class="snackbar" id="sbRemoved">Wines removed successfully</div>
    <div class="snackbar" id="sbOpened">Shops opened on new tabs, enjoy your wine!</div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/json.js"></script>
    <script>
    	var allSliders = document.getElementsByClassName("slider");
    	var chosenIds = [];
    	var toBeDeleted = [];
    	
    	function showSnackOpened() {
     	    var snackBarOpened = document.getElementById("sbOpened")
     	    snackBarOpened.className = "snackbar show";
     	    setTimeout(function(){ snackBarOpened.className = snackBarOpened.className.replace("show", ""); }, 3000);
     	}
    	
    	function showSnackRemoved() {
    		var snackBarRemoved = document.getElementById("sbRemoved")
     	    snackBarRemoved.className = "snackbar show";
     	    setTimeout(function(){ snackBarRemoved.className = snackBarRemoved.className.replace("show", ""); }, 3000);
    	}
    	var myFunction = function() 
    	{ 
    		var attribute = this.getAttribute("data-id");
    		
    		if(!chosenIds.includes(attribute)) { chosenIds.push(attribute); } 
    		else if(chosenIds.includes(attribute)) 
    		{ 
    			var index = chosenIds.indexOf(attribute);
    			chosenIds.splice(index, 1);
    		}
    	};
    	
    	for (var i = 0; i < allSliders.length; i++) 
    	{
    		allSliders[i].id = allSliders[i].getAttribute("data-id");
    		allSliders[i].addEventListener('click', myFunction, false);
    	}
    	
    	function removeWines()
    	{	
    		$.ajax({
                url:'Basket',
                type:'POST',
                data: { listOfWines : chosenIds, action : 'remove' },
                success : function(responseText)
                {
                 	for (var i = 0; i < allSliders.length; i++) 
                 	{
                 		var att = allSliders[i].getAttribute("data-id");
    					if(chosenIds.includes(att)) { toBeDeleted.push(allSliders[i]); }
                 	}
                 	
                 	for(var j = 0; j < toBeDeleted.length; j++)
               		{
                 		var cellsCollection = document.getElementById(toBeDeleted[j].id).closest('tr').children;
                 		var row = document.getElementById(toBeDeleted[j].id).closest('tr');
                 		$(cellsCollection)
                 		.animate({ padding: 0 }, 800)
                        .wrapInner('<div />')
                        .children()
                        .slideUp(function() { $(row).remove(); });
               		}
                 	showSnackRemoved();
                }
              });
    	}
    	
    	function buyWines()
    	{
    		for (var i = 0; i < allSliders.length; i++) 
         	{
         		var att = allSliders[i].getAttribute("data-id");
				if(chosenIds.includes(att)) { toBeDeleted.push(allSliders[i]); }
         	}
    		
    		$.ajax({
                url:'Basket',
                type:'POST',
                data: { listOfWines : $.toJSON(chosenIds), action : 'buy' },
                success : function()
                {
                 	for(var j = 0; j < toBeDeleted.length; j++)
               		{
                 		var cellsCollection = document.getElementById(toBeDeleted[j].id).closest('tr').children;
                 		var row = document.getElementById(toBeDeleted[j].id).closest('tr');
                 		$(cellsCollection)
                 		.animate({ padding: 0 }, 800)
                        .wrapInner('<div />')
                        .children()
                        .slideUp(function() { $(row).remove(); });
               		}
                 	showSnackOpened();
                }
              });
    		for(i = 0; i < toBeDeleted.length; i++)
    		{
    			var url = toBeDeleted[i].getAttribute('data-redir');
         		var newWindow = window.open(url, '_blank');
    		}
    	}
    	
    	function buyAll()
    	{
    		$.ajax({
                url:'Basket',
                type:'POST',
                data: { listOfWines : $.toJSON(chosenIds), action : 'buyAll' },
                success : function(responseText)
                {
                	for (var i = 0; i < allSliders.length; i++) 
                 	{
                		var cellsCollection = document.getElementById(allSliders[i].id).closest('tr').children;
                 		var row = document.getElementById(allSliders[i].id).closest('tr');
                 		$(cellsCollection)
                 		.animate({ padding: 0 }, 800)
                        .wrapInner('<div />')
                        .children()
                        .slideUp(function() { $(row).remove(); });
                 	}
                	showSnackOpened();
                }
              });	
    		
    		for(i = 0; i < allSliders.length; i++)
       		{
       			var url = allSliders[i].getAttribute('data-redir');
            		window.open(url, '_blank');
       		}
    	}
		
    	function removeAll()
    	{
    		$.ajax({
                url:'Basket',
                type:'POST',
                data: { listOfWines : $.toJSON(chosenIds), action : 'removeAll' },
                success : function(responseText)
                {
                	for (var i = 0; i < allSliders.length; i++) 
                 	{
                		var cellsCollection = document.getElementById(allSliders[i].id).closest('tr').children;
                 		var row = document.getElementById(allSliders[i].id).closest('tr');
                 		$(cellsCollection)
                 		.animate({ padding: 0 }, 800)
                        .wrapInner('<div />')
                        .children()
                        .slideUp(function() { $(row).remove(); });
                 	}
                	showSnackRemoved()
                }
              });	
    	}
    	
    </script>
    <c:import url="../templates/enableTooltips.jsp"/>
</body>

</html>