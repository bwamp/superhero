<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Location Info</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Locations</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation" ><a href="${pageContext.request.contextPath}/hello">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/allheroes">Heroes</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/alllocations">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/allorganizations">Organizations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/allsightings">Sightings</a></li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayUserList">Users</a></li>
                    </sec:authorize>
                </ul>    
            </div>
  
            <div class =" row">
                <div class="col-md-12">
                    <h2>Add a New Location</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="createLocation">
                        <div class="form-group">
                            <label for="addLocationName" class="col-md-4 control-label">Location Name:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="locationName" placeholder="Location Name" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addLocationDesc" class="col-md-4 control-label">Description:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="locationDesc" placeholder="Enter a short description" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addLat" class="col-md-4 control-label">Latitude:</label>
                            <div class="col-md-8">
                                <input type="number" step = "1" class="form-control" name="lat" placeholder="Enter the latitude" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addLong" class="col-md-4 control-label">Longitude:</label>
                            <div class="col-md-8">
                                <input type="number" step = "1" class="form-control" name="longitude" placeholder="Enter the longitude" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addstreet" class="col-md-4 control-label">Street:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="street" placeholder="Street" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addstreet" class="col-md-4 control-label">City:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="city" placeholder="City" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addstreet" class="col-md-4 control-label">State:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="state" placeholder="State" required/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Create Location"/>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="row">
                
                    <!-- 
                        Add a col to hold the summary table - have it take up half the row 
                    -->
                    <div class="col-md-7">
                        <h2>Locations Where Heroes Have Been Seen</h2>
                        <table id="locationTable" class="table table-hover">
                            <tr>
                                <th width="15%">Location Name</th>
                                <th width="40%">Description</th>
                                <th width="15%">Street</th>
                                <th width="15%">City</th>
                                <th width="15%">State</th>
                            </tr>
                            <c:forEach var="currentLocation" items="${locationList}">
                                <tr>
                                    <td>
                                        <a href="editLocationDetails?locationId=${currentLocation.locationId}">
                                            <c:out value="${currentLocation.locationName}"/> 
                                        </a>
                                    </td>
                                    <td>
                                        <c:out value="${currentLocation.locationDesc}"/>
                                    </td>
                                    <td>
                                        <c:out value="${currentLocation.street}"/>
                                    </td>
                                    <td>
                                        <c:out value="${currentLocation.city}"/>
                                    </td>
                                    <td>
                                        <c:out value="${currentLocation.state}"/>
                                    </td>
                                    <td>
                                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="deleteLocation?locationId=${currentLocation.locationId}">
                                            Delete
                                        </a>
                                        </sec:authorize>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>                  
                    </div> <!-- End col div -->
                    <div class="col-md-5">
                        <h2>Find Location by Hero</h2>
                        <form class="form-horizontal" 
                              role="form" method="POST" 
                              action="searchLocation">
                            <div class="form-group">
                                <label for="addPowerName" class="col-md-4 control-label">Hero:</label>
                                <div class="col-md-8">
                                    <select name="heroName">
                                        <c:forEach var="currentHero" items="${heroSearchList}">
                                            <option value="${currentHero.heroName}" name = "locationName">${currentHero.heroName}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="submit" class="btn btn-default" value="Search"/>
                                </div>
                            </div>
                        </form>
                        <table id="locationTable" class="table table-hover">
                            <tr>
                                <th width="20%">Location Name</th>
                                <th width="50%">Description</th>
                                <th width="15%">State</th>
                                <th width="15%">Date</th>
                            </tr>
                            <c:forEach var="searchedLocation" items="${searchedLocations}">
                                <tr>
                                    <td>
                                        <a href="editLocationDetails?locationId=${searchedLocation.locationId}">
                                            <c:out value="${searchedLocation.locationName}"/> 
                                        </a>
                                    </td>
                                    <td>
                                        <c:out value="${searchedLocation.locationDesc}"/>
                                    <td>
                                        <c:out value="${searchedLocation.state}"/>
                                    </td>
                                    <td>
                                        <c:out value="${searchedLocation.sighting.sightingDate}"/>
                                    </td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <td>
                                        <a href="deleteLocation?locationId=${currentLocation.locationId}">
                                            Delete
                                        </a>
                                    </td>
                                    </sec:authorize>
                                </tr>
                            </c:forEach>
                        </table>                  
                    </div> <!-- End col div -->
                </div> <!-- End row div -->
            </div>
            <!-- Placed at the end of the document so the pages load faster -->
            <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

