<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Sighting</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Edit Sighting Info</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/hello">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/allheroes">Heroes</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/alllocations">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/allorganizations">Organizations</a></li>
                    <li role="presentation" class = "active"><a href="${pageContext.request.contextPath}/allsightings">Sightings</a></li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayUserList">Users</a></li>
                    </sec:authorize>
                </ul>    
            </div>

            <div class =" row">
                <div class="col-md-12">
                    <h2>Please Update Sight Info</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="editSighting">
                        <div class="form-group">
                            <label for="updateHero" class="col-md-4 control-label">Hero:</label>
                            <div class="col-md-8">
                                <select name="heroId" selected="${hero.heroName}">
                                    <option value="${hero.heroId}">${hero.heroName}</option>
                                    <c:forEach var="currentHero" items="${heroList}">
                                        <option value="${currentHero.heroId}" name="heroId">${currentHero.heroName}</option>
                                    </c:forEach>
                                </select>
                            </div>           
                        </div>
                        <div class="form-group">
                            <label for="updateLocation" class="col-md-4 control-label">Location:</label>
                            <div class="col-md-8">
                                <select name="locationId" selected="${location.locationName}">
                                    <option value="${location.locationId}">${location.locationName}</option>
                                    <c:forEach var="currentHero" items="${heroList}">
                                        <option value="${currentLocation.locationId}" name="locationId">${currentLocation.locationName}</option>
                                    </c:forEach>
                                </select>
                            </div>           
                        </div>
                        <div class="form-group">
                            <label for="sightDate" class="col-md-4 control-label">Sighting Date:</label>
                            <div class="col-md-2">
                                <input value="${date}" type="date" class="form-control" name="sightDate" required/>
                                <input value="${sight.sightingId}" type="hidden" class="form-control" name="sightingId"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Update Sighting"/>
                            </div>
                        </div>               
                    </form>
                    <div class="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <a href="${pageContext.request.contextPath}/cancelSighting">
                                <button type="cancel" class="btn btn-default " id="/allsightings">Cancel</button>
                            </a> 
                        </div>
                    </div>
                </div> <!-- End col div -->
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
