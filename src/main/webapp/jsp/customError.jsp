<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Oops</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <!-- SWC Icon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icon.png">
    </head>
    <body>
        <div class="container">
            <h1>Looks like someone is in need of rescuing</h1>
            <hr/>
            <div>
                <h1>Where is a Hero when you need one?</h1>
                <p>None of our Heroes could have saved you from this one. Looks like something went wrong. Return home or to the previous
                page to try something else.</p>
                <br>
                <h3>${errorMessage}</h3>
                <br>
                <h1><a href="${pageContext.request.contextPath}/hello">To the Batcave!</a></h1>
            </div>
        </div>
            <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
            <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    </body>
</html>