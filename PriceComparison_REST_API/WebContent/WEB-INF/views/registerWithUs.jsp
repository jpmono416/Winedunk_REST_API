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
    <c:import url="../templates/google_analytics.jsp"/>
    <section style="margin-top:150px;">
        <div class="container">
            <div class="row">
                <c:import url="../templates/sidePannelLinks.jsp"/>
                <div class="col-sm-9 col-xs-12">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="card settingsCard">
                                <h1 class="red-text text-center">Register</h1>
                                <hr class="sep-bar">
                                <div class="row">
                                    <div class="col-md-12">
										<p>
										Hello friend!
 
										Don’t miss the chance to join our growing community of wine lovers to find the best wines at the best prices and never miss a great deal!
										<br><br>
										Best Regards <br>
										Ulrik <br><br>
										CEO/Founder
										</p>
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