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
	    
	    function validateCountry(selectedElement) {
	    	$.ajaxSetup({ cache: false });
	    	$.ajax({
	            // dataType: 'json',
	            type: "POST", 
	            url: "FiltersValidation", 
	            data: { entity : "country",
			    		action: "validateCountryByNameAndReturnRegionsAutocompleteLists",
			    		countryName: $(selectedElement).val()
			   	},
	            success: function(response) {
	            	
	            	if (!$.trim(response)){   
	            		$(selectedElement).val("");
	            	}
	            	else{   
	            		var jsonResponse = $.parseJSON(response);
		            	
		            	$.each(jsonResponse.country, function(key, value){
		            		$(selectedElement).val(value);
		            	});
		            	
		            	var countryTextInput = document.getElementById("country");
		            	var regionTextInput = document.getElementById("region");
		            	var appellationTextInput = document.getElementById("appellation");
		            	var wineryTextInput = document.getElementById("winery");
		            	
		            	regionTextInput.disabled = countryTextInput.val == "";
		            	if (regionTextInput.disabled) {
		            		regionTextInput.placeholder = "Region (select country first)";
		            	} else {
		            		regionTextInput.placeholder = "Region";
		            	}

		            	appellationTextInput.disabled = true;
		            	appellationTextInput.placeholder = "Appellation (select region first)";

		            	wineryTextInput.disabled = true;
		            	wineryTextInput.placeholder = "Winery (select appellation first)";
		            	
		            	$.each(jsonResponse.regions, function(key, value){
		            		$( "#region" ).autocomplete({
			                    source: value,
			                    select: function (e, ui) {
			                    	$("#region").val(ui.item.label);
			                    	validateRegion(document.getElementById("region"));
			                    }
			                });
		            	});
		            	
		            	regionTextInput.focus();
	            	}
	            	
	            }
    		});
	    }
	    
	    function validateRegion(selectedElement) {
	    	$.ajaxSetup({ cache: false });
	    	$.ajax({
	            // dataType: 'json',
	            type: "POST", 
	            url: "FiltersValidation", 
	            data: { entity : "region",
			    		action: "validateRegionByNameAndReturnAppellationsAutocompleteLists",
			    		regionName: $(selectedElement).val()
			   	},
	            success: function(response) {
	            	
	            	if (!$.trim(response)){   
	            		$(selectedElement).val("");
	            	}
	            	else{   
	            		var jsonResponse = $.parseJSON(response);
		            	
		            	$.each(jsonResponse.region, function(key, value){
		            		$(selectedElement).val(value);
		            	});
		            	
		            	var regionTextInput = document.getElementById("region");
		            	var appellationTextInput = document.getElementById("appellation");
		            	var wineryTextInput = document.getElementById("winery");
		            	
		            	appellationTextInput.disabled = regionTextInput.val == "";
		            	if (appellationTextInput.disabled) {
		            		appellationTextInput.placeholder = "Appellation (select region first)";
		            	} else {
		            		appellationTextInput.placeholder = "Appellation";
		            	}
		            	
		            	$.each(jsonResponse.appellations, function(key, value){
		            		$( "#appellation" ).autocomplete({
			                    source: value,
			                    select: function (e, ui) {
			                    	$("#appellation").val(ui.item.label);
			                    	validateAppellation(document.getElementById("appellation"));
			                    }
			                });
		            	});

		            	wineryTextInput.disabled = true;
		            	wineryTextInput.placeholder = "Winery (select appellation first)";
		            	
		            	appellationTextInput.focus();
	            	}
	            }	
    		});
	    }
	    
	    function validateAppellation(selectedElement) {
	    	$.ajaxSetup({ cache: false });
	    	$.ajax({
	            // dataType: 'json',
	            type: "POST", 
	            url: "FiltersValidation", 
	            data: { entity : "appellation",
			    		action: "validateAppellationByNameAndReturnWineriesAutocompleteLists",
			    		appellationName: $(selectedElement).val()
			   	},
	            success: function(response) {
	            	
	            	if (!$.trim(response)){   
	            		$(selectedElement).val("");
	            	}
	            	else{  
	            		var jsonResponse = $.parseJSON(response);
		            	
		            	$.each(jsonResponse.appellation, function(key, value){
		            		$(selectedElement).val(value);
		            	});
		            	
		            	var appellationTextInput = document.getElementById("appellation");
		            	var wineryTextInput = document.getElementById("winery");
		            	
		            	wineryTextInput.disabled = appellationTextInput.val == "";
		            	if (wineryTextInput.disabled) {
		            		wineryTextInput.placeholder = "Winery (select appellation first)";
		            	} else {
		            		wineryTextInput.placeholder = "Winery";
		            	}
		            	
		            	$.each(jsonResponse.wineries, function(key, value){
		            		$( "#winery" ).autocomplete({
			                    source: value,
			                    select: function (e, ui) {
			                    	$("#winery").val(ui.item.label);
			                    	validateWinery(document.getElementById("winery"));
			                    }
			                });
		            	});
		            	
		            	wineryTextInput.focus();
	            	}
	            }	
    		});
	    }
	    
	    function validateWinery(selectedElement) {
	    	$.ajaxSetup({ cache: false });
	    	$.ajax({
	            // dataType: 'json',
	            type: "POST", 
	            url: "FiltersValidation", 
	            data: { entity : "winery",
			    		action: "validateWineryByName",
			    		wineryName: $(selectedElement).val()
			   	},
	            success: function(response) {
	            	
	            	if (!$.trim(response)){   
	            		$(selectedElement).val("");
	            	}
	            	else{  

		            	var jsonResponse = $.parseJSON(response);
		            	
		            	$.each(jsonResponse.winery, function(key, value){
		            		$(selectedElement).val(value);
		            	});
		            	
	            	}
	            }	
    		});
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
        
    	var availableTypes = [
    		<c:forEach var="t" items="${sessionScope.listOfWineTypes.entrySet()}">
    			"<c:out value="${t.getValue()}"/>",
    		</c:forEach>
    	];
        
        var availableCountries = [
        	<c:forEach var="c" items="${sessionScope.listOfCountries.entrySet()}">
				"<c:out value="${c.getValue()}"/>",
			</c:forEach>
        ];
        
        var availableGrapeVarieties = [
        	<c:forEach var="g" items="${sessionScope.listOfGrapeVarieties.entrySet()}">
				"<c:out value="${g.getValue()}"/>",
			</c:forEach>
        ];
        
        $( "#country" ).autocomplete({
          source: availableCountries,
	          	select: function (e, ui) {
	          		$("#country").val(ui.item.label);
	          		validateCountry(document.getElementById("country"));
	      		}
        });

        $( "#grapeVariety" ).autocomplete({
            source: availableGrapeVarieties
        });
        
        $( "#chosenType" ).autocomplete({
        	source: availableTypes
        })
        
      } );
    </script>