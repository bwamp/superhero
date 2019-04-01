<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hero Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Hero Info</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/hello">Home</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/allheroes">Heroes</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/alllocations">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/allorganizations">Organizations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/allsightings">Sightings</a></li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayUserList">Users</a></li>
                    </sec:authorize>
                </ul>    
            </div>

            <div class =" row">
                <div class="col-md-12">
                    <h2>Add a New Hero</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="createHero">
                        <div class="form-group">
                            <label for="addHeroName" class="col-md-4 control-label">Hero Name:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="heroName" placeholder="Hero Name" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addHeroDesc" class="col-md-4 control-label">Description:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="heroDesc" placeholder="Enter a short description" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addPowerName" class="col-md-4 control-label">Power:</label>
                            <div class="col-md-8">
                                <select name="chosePower">
                                    <c:forEach var="currentPower" items="${powerList}">
                                        <option value="${currentPower.superpowerName}">${currentPower.superpowerName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addOrgName" class="col-md-4 control-label">Organization:</label>
                            <div class="col-md-8">
                                <select name="orgId">
                                    <c:forEach var="currentOrg" items="${orgList}">
                                        <option value="${currentOrg.organizationId}">${currentOrg.organizationName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Create Hero"/>
                            </div>
                        </div>
                    </form>

                </div> <!-- End col div -->
            </div>
            <div class="row">

                <!-- 
                    Add a col to hold the summary table - have it take up half the row 
                -->
                <div class="col-md-8">
                    <h2>All Confirmed Heros</h2>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="35%">Hero Name</th>
                            <th width="50%">Description</th>
                            <th width="15%">Power</th>
                        </tr>
                        <c:forEach var="currentHero" items="${heroList}">
                            <tr>
                                <td>
                                    <a href="editHeroDetails?heroId=${currentHero.heroId}">

                                        <c:out value="${currentHero.heroName}"/> 
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${currentHero.heroDesc}"/>
                                </td>
                                <td>
                                    <c:out value="${currentHero.superpower.superpowerName}"/>
                                </td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <td>
                                    <a href="deleteHero?heroId=${currentHero.heroId}">
                                        Delete
                                    </a>
                                </td>
                                </sec:authorize>
                            </tr>
                        </c:forEach>
                    </table>                  
                </div> <!-- End col div -->
                <div class="col-md-4">
                    <h2>Search by Location</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="searchHero">
                    <div class="form-group">
                            <label for="locationName" class="col-md-4 control-label">Location:</label>
                            <div class="col-md-8">
                                <select name="locationName">
                                    <c:forEach var="currentLocation" items="${locationList}">
                                        <option value="${currentLocation.locationName}" name = "locationName">${currentLocation.locationName}</option>
                                    </c:forEach>
                                </select>
                                <input type="submit" class="btn btn-default" value="Search"/>
                            </div>
                        </div>
                    </form>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="100%">Hero:</th>
                        </tr>
                    </table>                  
                </div> <!-- End col div -->
            </div> <!-- End row div -->
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

