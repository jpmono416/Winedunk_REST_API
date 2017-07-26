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
                                <h1 class="red-text text-center">Advertise with us</h1>
                                <hr class="sep-bar">
                                <div class="row">
                                    <div class="col-md-12">
                                        <p>Please email <a href="mailto:info@winedunk.com">info@winedunk.com</a> if you would like to advertise with Winedunk. We run advertising across the website, newsletters and social media.</p>
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