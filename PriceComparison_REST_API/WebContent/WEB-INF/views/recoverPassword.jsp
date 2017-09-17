<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<c:import url="../templates/head.jsp"/>
	<link rel="stylesheet" href="assets/css/Google-Style-Login.css">
	<c:import url="../templates/google_analytics.jsp"/>
</head>

<body style="background-color:#e7e7e7;">
	<c:import url="../templates/navbar.jsp"/>
	<c:import url="../templates/google_analytics.jsp"/>
    <div id="fb-root"></div>
    <div id="background"></div>
    <div id="login-form" class="login-card">
        <div class="row">
            <div class="col-xs-12">
                <h3 class="text-center">Recover password </h3>
                <hr class="sep-bar">
                <form class="form-signin" action="Recover" method="POST">
	                <c:if test="${requestScope.passwordsWrong == true}">
		            	<p class="text-center text-danger">The passwords you entered seem to be wrong, please check them</p>
		            </c:if>
		            <p class="text-center text-danger">Format: letters a-z, numbers 0-9. At least 1 uppercase, 1 lowercase and 8 characters.</p>
                	<input class="hidden" name="formChosen" value="recoverPassword">
                    <input class="form-control" type="password" required="" placeholder="New password" id="newPassword1" name="newPassword1">
                    <input class="form-control" type="password" required="" placeholder="Repeat new password" id="newPassword2" name="newPassword2">
                    <button class="btn btn-primary btn-block btn-lg btn-signin" type="submit" id="btnLogin-Login">Save</button>
                </form>
            </div>
        </div>
    </div>
    <c:import url="../templates/footer.jsp"/>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/login.js"></script>
</body>

</html>