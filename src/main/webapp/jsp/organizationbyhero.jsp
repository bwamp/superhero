<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Organization Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Organization Info</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/hello">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/allheroes">Heroes</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/alllocations">Locations</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/allorganizations">Organizations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/allsightings">Sightings</a></li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayUserList">Users</a></li>
                    </sec:authorize>
                </ul>    
            </div>

                    <sec:authorize access="hasRole('ROLE_ADMIN')">
            <div class =" row">
                <div class="col-md-12">
                    <h2>Add a New Organization</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="createOrg">
                        <div class="form-group">
                            <label for="addOrgName" class="col-md-4 control-label">Organization Name:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="organizationName" placeholder="Organization Name" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addHeroDesc" class="col-md-4 control-label">Description:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="organizationDesc" placeholder="Enter a short description" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addPowerName" class="col-md-4 control-label">Location:</label>
                            <div class="col-md-8">
                                <select name="chooseLocation">
                                    <c:forEach var="currentLocation" items="${locationList}">
                                        <option value="${currentLocation.locationId}" name="chooseLocation">${currentLocation.locationName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addphone" class="col-md-4 control-label">Phone:</label>
                            <div class="col-md-8">
                                <input minlength="10" maxlength="10" type="number" class="form-control" name="phone" placeholder="Phone" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addphone" class="col-md-4 control-label">Email:</label>
                            <div class="col-md-8">
                                <input type="email" class="form-control" name="email" placeholder="Email" required/>
                            </div>
                        </div>
        
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Create Org"/>
                            </div>
                        </div>
                    </form>

                </div> <!-- End col div -->
            </div>
            <div class="row">
                </sec:authorize>
                
                <div class="col-md-8">
                        <h2> Assign an Org to a Hero</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="assignOrg">
                        <div class="form-group">
                            <label for="orgName" class="col-md-4 control-label">Organization and Hero</label>
                            <div class="col-md-8">
                                <select name="myorg">
                                    <c:forEach var="recentOrg" items="${orgList}">
                                        <option value="${recentOrg.organizationId}" name = "myorg">${recentOrg.organizationName}</option>
                                    </c:forEach>
                                </select>
                                <select name="myhero">
                                    <c:forEach var="recentHero" items="${heroList}">
                                        <option value="${recentHero.heroId}" name = "myhero">${recentHero.heroName}</option>
                                    </c:forEach>
                                </select>
                                <input type="submit" class="btn btn-default" value="Assign"/>
                            </div>
                        </div>
                    </form>
                    </div>
                <!-- 
                    Add a col to hold the summary table - have it take up half the row 
                -->
                <div class="col-md-8">
                    <h2>All Current Organizations</h2>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="20%">Org Name</th>
                            <th width="40%">Description</th>
                            <th width="20%">Phone</th>
                            <th width="20%">Email</th>
                        </tr>
                        <c:forEach var="currentOrg" items="${orgList}">
                            <tr>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <a href="editOrganizationDetails?organizationId=${currentOrg.organizationId}">
                                        </sec:authorize>
                                        <c:out value="${currentOrg.organizationName}"/> 
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${currentOrg.organizationDesc}"/>
                                </td>
                                <td>
                                    <c:out value="${currentOrg.phone}"/>
                                </td>
                                <td>
                                    <c:out value="${currentOrg.email}"/>
                                </td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <td>
                                    <a href="deleteOrg?organizationId=${currentOrg.organizationId}">
                                        Delete
                                    </a>
                                </td>
                                </sec:authorize>
                            </tr>
                        </c:forEach>
                    </table>                  
                </div> <!-- End col div -->
                <div class="col-md-4">
                    <h2>Search by Hero</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="searchOrg">
                    <div class="form-group">
                            <label for="heroName" class="col-md-4 control-label">Hero:</label>
                            <div class="col-md-8">
                                <select name="heroName">
                                    <c:forEach var="currentHero" items="${heroList}">
                                        <option value="${currentHero.heroName}" name = "heroName">${currentHero.heroName}</option>
                                    </c:forEach>
                                </select>
                                <input type="submit" class="btn btn-default" value="Search"/>
                            </div>
                        </div>
                    </form>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="100%">Org Name:</th>
                        </tr>
                        <c:forEach var="searchedOrganizations" items="${searchedOrganizations}">
                            <tr>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <a href="editOrganizationDetails?organizationId=${searchedOrganizations.organizationId}">
                                    </sec:authorize>
                                        <c:out value="${searchedOrganizations.organizationName}"/> 
                                    </a>
                                </td>
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

