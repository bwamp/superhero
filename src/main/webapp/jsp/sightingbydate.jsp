<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Sightings Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Sightings Info</h1>
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
                        <h2>Add a New Sighting</h2>
                        <form class="form-horizontal" 
                              role="form" method="POST" 
                              action="createSighting">
                            <div class="form-group">
                                <label for="addHero" class="col-md-4 control-label">Hero:</label>
                                <div class="col-md-8">
                                    <select name="heroId">
                                        <c:forEach var="currentHero" items="${heroList}">
                                            <option value="${currentHero.heroId}" name = "heroId">${currentHero.heroName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addLocation" class="col-md-4 control-label">Location:</label>
                                <div class="col-md-8">
                                    <select name="locationId">
                                        <c:forEach var="currentLocation" items="${locationList}">
                                            <option value="${currentLocation.locationId}" name = "locationId">${currentLocation.locationName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sightDate" class="col-md-4 control-label">Sighting Date:</label>
                                <div class="col-md-2">
                                    <input type="date" class="form-control" name="sightDate" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <input type="submit" class="btn btn-default" value="Create Sighting"/>
                                </div>
                            </div>
                        </form>

                    </div> <!-- End col div -->
                </div>
                <div class="row">
                         


                <div class="col-md-8">
                    <h2>All Confirmed Sightings</h2>
                    <table id="sightTable" class="table table-hover">
                        <tr>
                            <th width="20%">Hero Name</th>
                            <th width="20%">Location</th>
                            <th width="40%">Location Description</th>
                            <th width="20%">Sighting Date</th>
                        </tr>
                        <c:forEach var="currentSight" items="${sightList}">
                            <tr>
                                <td>
                                    <a href="editSightDetails?sightingId=${currentSight.sightingId}&heroId=${currentSight.hero.heroId}&locationId=${currentSight.location.locationId}">
                                    
                                        <c:out value="${currentSight.hero.heroName}"/> 
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${currentSight.location.locationName}"/>
                                </td>
                                <td>
                                    <c:out value="${currentSight.location.locationDesc}"/>
                                </td>
                                <td>
                                    <c:out value="${currentSight.sightingDate}"/>
                                </td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <td>
                                    <a href="deleteSighting?sightingId=${currentSight.sightingId}">
                                        Delete
                                    </a>
                                </td>
                                </sec:authorize>
                            </tr>
                        </c:forEach>
                    </table>                  
                </div> 
                <div class="col-md-4">
                    <h2>Search by Date</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="searchSightings">
                        <div class="form-group">
                            <label for="heroName" class="col-md-4 control-label">Hero:</label>
                            <div class="col-md-8">
                                <input type="date" class="form-control" name="sightDate" required/>
                                <input type="submit" class="btn btn-default" value="Search"/>
                            </div>
                        </div>
                    </form>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="25%">Hero Name:</th>
                            <th width="25%">Location:</th>
                            <th width="50%">Location Description:</th>
                        </tr>
                        <c:forEach var="searched" items="${searchList}">
                            <tr>
                                <td>
                                        <a href="editSightDetails?sightingId=${searched.sighting.sightingId}&heroId=${searched.hero.heroId}&locationId=${searched.locationId}">
                                    <c:out value="${searched.hero.heroName}"/>
                                        </a>
                                </td>
                                <td>
                                    <c:out value="${searched.locationName}"/>
                                </td>
                                <td>
                                    <c:out value="${searched.locationDesc}"/>
                                </td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <td>
                                        <a href="deleteSighting?sightingId=${currentSight.sightingId}">
                                            Delete
                                        </a>
                                    </td>
                                </sec:authorize>  
                            </tr>
                        </c:forEach>
                    </table>                  
                </div> 
            </div>  
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

