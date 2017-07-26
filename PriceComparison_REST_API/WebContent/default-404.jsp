<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <c:import url="/WEB-INF/templates/head.jsp"/>
</head>

<body style="background:#f7f7f7;">
    <div id="background"></div>
	<nav class="navbar navbar-default navbar-fixed-top" id="generalNav" style="background-color:#F5F5F5;">
		<div class="container">
			<div class="navbar-header">
				<a href="Home" class="navbar-brand navbar-link"> <img src="assets/img/Logo_GrisRojo.jpg" height="100%" style="padding:0;" /></a>
			</div>
		</div>
	</nav>
	<div style="position:fixed;top:80px;width:100%;height:3px;background-color:#800000;z-index:2;"></div>    <div style="position:fixed;top:80px;width:100%;height:3px;background-color:#800000;z-index:2;"></div>
    <section style="margin-top:150px;">
        <div class="container">
            <div class="row">
                <div class="col-sm-10 col-sm-offset-1 col-xs-12">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="card settingsCard">
                                <h1 class="red-text text-center">Oops, page not found!</h1>
                                <hr class="sep-bar">
                                <div class="row">
                                    <div class="col-md-12">
                                        <p>
                                        	We are sorry the page you were trying to access could not be found. If you entered the URL manually, please check there are no errors. Alternatively you can go back to <a href="/Home">Home</a>
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
</body>

</html>