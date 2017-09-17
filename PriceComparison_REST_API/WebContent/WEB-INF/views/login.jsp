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
    <div class="modal fade" role="dialog" tabindex="-1" id="modal-recoverPassword">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header" style="background:#f7f7f7;">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                </div>
                <div class="modal-body" style="background:#f7f7f7;">
                    <div id="recover-password-form" class="login-card" style="padding-top:30px;margin-top:20px;">
                        <h3 class="text-center">Recover your password</h3>
                        <hr class="sep-bar">
                        <form class="form-signin" method="POST" action="Home">
                        	<input class="hidden" name="formChosen" value="recoverPasswordForm">
                            <input class="form-control" type="email" required="" placeholder="Email address" id="recoverInputEmail" name="emailAddress">
                            <button class="btn btn-primary btn-block btn-lg btn-signin" type="submit" id="btnRecoverPassword">Recover your password</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="login-form" class="login-card"
    	<c:if test="${sessionScope.signUp == true}"><c:out value="style=display:none"></c:out></c:if>>
        <div class="row">
            <div class="col-xs-12">
                <h3 class="text-center">Login </h3>
                <hr class="sep-bar">
                
                <c:if test="${ requestScope.passedRecovery == true }">
                	<p class="text-center text-success">The password was resetted successfully</p>
                </c:if>
	            <c:if test="${sessionScope.isLoggedIn == false}">
	            	<p class="text-center text-danger">The details you entered seem to be wrong, please check them</p>
	            </c:if>
	            
                <form class="form-signin" action="Home" method="POST">
                	<input class="hidden" name="formChosen" value="loginForm">
                    <input class="form-control" type="email" required="" placeholder="Email address" autofocus="" id="loginInputEmail" name ="loginInputEmail" value=<c:out value="${requestScope.emailEntered}"/>>
                    <input class="form-control" type="password" required="" placeholder="Password" id="loginInputPassword" name="loginInputPassword">
                    <div class="checkbox">
                        <div class="checkbox">
                            <label for="chkRememberMe">
                                <input type="checkbox" name="chkRememberMe" id="chkRememberMe">Remember me
                            </label>
                        </div>
                    </div><a href="#" class="form-text" style="text-decoration: none;" data-toggle="modal" data-target="#modal-recoverPassword">Forgot your password?</a>
                    <button class="btn btn-primary btn-block btn-lg btn-signin" type="submit" id="btnLogin-Login">Login</button>
                    <div class="fb-login-button" data-max-rows="1" data-size="large" data-show-faces="false" data-auto-logout-link="false"></div>
                </form>
            </div>
        </div>
    </div>
    <div class="login-card" style="margin-top:0 !important;
    <c:if test="${sessionScope.signUp == true}"><c:out value="display:none"></c:out></c:if>
    <c:if test="${sessionScope.signUp == false}"><c:out value="style=display:block"></c:out></c:if>" 
    id="registerNewUserBtnContainer">
        <div class="row">
            <div class="col-xs-12">
            	<a class="form-text" style="text-decoration: none;">Not yet signed up?</a>
                <button class="btn btn-primary btn-block btn-lg" type="submit" id="btnLogin-Register" onclick="swapToRegisterForm()">Sign up </button>
            </div>
        </div>
    </div>
    <div id="register-form" class="login-card" <c:if test="${sessionScope.signUp == true}"><c:out value="style=display:block"></c:out></c:if>>
        <h3 class="text-center">Register </h3>
        <hr class="sep-bar">
        <form class="form-signin" action="Home" method="POST">
        	<input class="hidden" name="formChosen" value="registerForm">
        	<c:if test="${requestScope.nameNotEntered == true}">
            	<p class="text-center text-danger">Please type in your full name</p>
            </c:if>
            <input class="form-control" type="text" placeholder="First name" id="inputName" name="inputName" value=<c:out value="${requestScope.previousinputName}"/>>
            <input class="form-control" type="text" placeholder="Last name" id="inputSurname" name="inputSurname" value=<c:out value="${requestScope.previousinputSurname}"/>>
            <c:if test="${requestScope.emailsNotEntered == true}">
            	<p class="text-center text-danger">Please fill in both email address fields</p>
            </c:if>
            
            <c:if test="${requestScope.emailsDontMatch == true}">
            	<p class="text-center text-danger">Please make sure both email addresses match</p>
            </c:if>
            
            <c:if test="${requestScope.wrongEmailFormat == true}">
            	<p class="text-center text-danger">Please enter a valid email address</p>
            </c:if>
            
            <c:if test="${requestScope.emailAlreadyExists == true}">
            	<p class="text-center text-danger">This email address already exists</p>
            </c:if>
            
            <input class="form-control" type="email" required="" placeholder="Email address" id="registerInputEmail" name="registerInputEmail" value=<c:out value="${requestScope.previousregisterInputEmail}"/>>
            <input class="form-control" type="email" required="" placeholder="Repeat email address" id="registerInputEmail2" name="registerInputEmail2" value=<c:out value="${requestScope.previousregisterInputEmail2}"/>>
            
            <c:if test="${requestScope.passwordsNotEntered == true}">
           		<p class="text-center text-danger">Please fill in both password fields</p>
            </c:if>
            
            <c:if test="${requestScope.passwordsDontMatch == true}">
            	<p class="text-center text-danger">Please make sure both passwords match</p>
            </c:if>
            
            <c:if test="${requestScope.passwordWrongFormatted == true}">
            	<p class="text-center text-danger">Please make sure password has the right format</p>
            </c:if>
            
            <input class="form-control" type="password" required="" placeholder="Password" id="inputPassword" name="inputPassword" value=<c:out value="${requestScope.previousinputPassword}"/>>
            <input class="form-control" type="password" required="" placeholder="Retype password" id="inputPassword2" name="inputPassword2" value=<c:out value="${requestScope.previousinputPassword2}"/>>
            <p class="text-center text-danger">Format: letters a-z, numbers 0-9. At least 1 uppercase, 1 lowercase and 8 characters.</p>
            <div class="row">
                <div class="col-md-12">
                    <p class="text-center form-text">Date of birth</p>
                </div>
                
                <c:if test="${requestScope.dateNotEntered == true}">
                	<p class="text-center text-danger">Please fill in all date fields</p>
                </c:if>
                
                <c:if test="${requestScope.notOldEnough == true}">
                	<p class="text-center text-danger">You must be at least 18 years old to register</p>
                </c:if>
                
                <c:if test="${requestScope.invalidDate == true}">
                	<p class="text-center text-danger">We encountered a problem, please check the date</p>
                </c:if>
                
                <div class="col-xs-4">
                    <input class="form-control" type="text" required="" placeholder="DD" name="day" value=<c:out value="${requestScope.previousday}"/>>
                </div>
                <div class="col-xs-4">
                    <input class="form-control" type="text" required="" placeholder="MM" name="month" value=<c:out value="${requestScope.previousmonth}"/>>
                </div>
                <div class="col-xs-4">
                    <input class="form-control" type="text" required="" placeholder="YYYY" name="year" value=<c:out value="${requestScope.previousyear}"/>>
                </div>
            </div>
            <button class="btn btn-primary btn-block btn-lg btn-signin" type="submit">Register </button>
        </form>
    </div>
	<div class="login-card" style="margin-top:0 !important; 
		<c:if test="${sessionScope.signUp == false}"><c:out value="display:none"></c:out></c:if>
		<c:if test="${sessionScope.signUp == true}" ><c:out value="display:block"></c:out></c:if>"
	id="loginBtnContainer">
		<div class="row">
            <a class="form-text" style="text-decoration: none;">Want to login instead?</a>
            <button class="btn btn-primary btn-block btn-lg" type="submit" id="btnRegister-Login" onclick="swapToLoginForm()">Login </button>
        </div>
	</div>
    <c:import url="../templates/footer.jsp"/>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/login.js"></script>
</body>

</html>