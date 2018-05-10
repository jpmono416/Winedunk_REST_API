<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script>
	    function changeColour(ele)
	    {
	    	var colourValue = ele.value;
	    	var colourText = ele.innerText;
	    	
	    	document.getElementById('chosenColour').value = colourValue;
	    	$('#coloursDropdown').html(colourText + '<span class="caret"></span>');
	    }
	    
	    function changeType(ele)
	    {
	    	var typeValue = ele.value;
	    	var typeText = ele.innerText;
	    	
	    	document.getElementById('chosenType').value = typeValue;
	    	$('#typesDropdown').html(typeText + '<span class="caret"></span>');
	    }
	    
	    function changeShop(ele)
	    {
	    	var shopValue = ele.value;
	    	var shopText = ele.innerText;
	    	
	    	document.getElementById('chosenShop').value = shopValue;
	    	$('#shopsDropdown').html(shopText + '<span class="caret"></span>');
	    }
	    
	    function changeSorting(ele)
	    {
	    	var sortingValue = ele.value;
	    	var sortingText = ele.innerText;
	    	
	    	$('#sortingDropdown').html(sortingText + '<span class="caret"></span>');

	    	$.ajax({
               url:'Results',
               type:'POST',
               data: 
               { 
            	   formChosen : 'sort',
            	   orderBy: sortingValue
               },
               success : function(data)
               { 
            	  if(data == 'passed') { window.location = "Results"; }
            	  
               }
             });
	    	
	    }
    </script>
    <script>
    $( function() {
    	
    	var availableWineries = [
    		<c:forEach var="p" items="${sessionScope.listOfWineries.entrySet()}">
    			"<c:out value="${p.getValue()}"/>",
			</c:forEach>
    	];
        
    	var availableTypes = [
    		<c:forEach var="t" items="${sessionScope.listOfWineTypes.entrySet()}">
    			"<c:out value="${t.getValue()}"/>",
    		</c:forEach>
    	];
    	
    	var availableAppellations = [
    		<c:forEach var="a" items="${sessionScope.listOfAppellations.entrySet()}">
				"<c:out value="${a.getValue()}"/>",
			</c:forEach>
    	];
        
        var availableCountries = [
        	<c:forEach var="c" items="${sessionScope.listOfCountries.entrySet()}">
				"<c:out value="${c.getValue()}"/>",
			</c:forEach>
        ];
        
        var availableRegions = [
        	<c:forEach var="r" items="${sessionScope.listOfRegions.entrySet()}">
				"<c:out value="${r.getValue()}"/>",
			</c:forEach>
        ];
        
        var availableGrapeVarieties = [
        	<c:forEach var="g" items="${sessionScope.listOfGrapeVarieties.entrySet()}">
				"<c:out value="${g.getValue()}"/>",
			</c:forEach>
        ];
        
        $( "#winery" ).autocomplete({
          source: availableWineries
        });
        
        $( "#appellation" ).autocomplete({
          source: availableAppellations
        });
        
        $( "#country" ).autocomplete({
          source: availableCountries
        });
        
        $( "#region" ).autocomplete({
            source: availableRegions
        });

        $( "#grapeVariety" ).autocomplete({
            source: availableGrapeVarieties
        });
        
        $( "#chosenType" ).autocomplete({
        	source: availableTypes
        })
        
      } );
    </script>