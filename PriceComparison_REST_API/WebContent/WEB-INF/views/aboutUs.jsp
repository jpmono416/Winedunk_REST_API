<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <c:import url="../templates/head.jsp"/>
    <c:import url="../templates/google_analytics.jsp"/>
</head>

<body style="background:#f7f7f7;">
    <div id="background"></div>
    <c:import url="../templates/navbar.jsp"/>
    <div style="position:fixed;top:80px;width:100%;height:3px;background-color:#800000;z-index:2;"></div>
    <c:import url="../templates/google_analytics.jsp"/>
    <section style="margin-top:150px;">
        <div class="container">
            <div class="row">
                <c:import url="../templates/sidePannelLinks.jsp"/>
                <div class="col-sm-9 col-xs-12">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="card settingsCard">
                                <h1 class="red-text text-center">About Us</h1>
                                <hr class="sep-bar">
                                <div class="row">
                                    <div class="col-md-12">
                                        <p class="text-justify">The purpose of winedunk is to enable wine consumers to easier make informed decision on what wine to buy and drink.</p>
										<p class="text-justify">Winedunk is sourcing information about available wines to buy online from the largest supermarket, specialist wine merchants and even direct from wine producers.</p>
										<p class="text-justify">As a consumer, you will be having all the tools to find the right wine for you. Maybe you are just after your favourite wine at the best price or you want to see how a specific wine is rated by the professional wine critics as well as the winedunk community or maybe just a friend that you trust.</p>
										<p class="text-justify">Using winedunk makes it easy and fun for you to store information of what wine you drink so that you can remember what wines you like and what ones you might not want to drink again. </p>
										<p class="text-justify">Winedunk will also do its best to recommend wines for you to try based on your preferences and inform you about any wine deals that you don't want to miss.</p>
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
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="assets/js/navbar-appear.js"></script>
    <script src="assets/js/nouislider.min.js"></script>
    <script src="assets/js/populate-inputs.js"></script>
</body>

</html>