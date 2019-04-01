<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Organization</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Edit an Organization</h1>
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


            <div class =" row">
                <div class="col-md-12">
                    <h2>Please Update Organization Info</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="editOrg">
                        <div class="form-group">
                            <label for="editOrgName" class="col-md-4 control-label">Organization Name:</label>
                            <div class="col-md-8">
                                <input value="${updateOrg.organizationName}" type="text" class="form-control" name="organizationName" placeholder="Organization Name" required/>
                                <input value="${updateOrg.organizationId}" type="hidden" class="form-control" name="organizationId"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addOrgDesc" class="col-md-4 control-label">Description:</label>
                            <div class="col-md-8">
                                <input value="${updateOrg.organizationDesc}" type="text" class="form-control" name="organizationDesc" placeholder="Enter a short description" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addOrgPhone" class="col-md-4 control-label">Phone:</label>
                            <div class="col-md-8">
                                <input minlength="10" maxlength="10" value="${updateOrg.phone}" type="number" class="form-control" name="phone" placeholder="Phone" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addemailDesc" class="col-md-4 control-label">Email:</label>
                            <div class="col-md-8">
                                <input value="${updateOrg.email}" type="text" class="form-control" name="email" placeholder="Email" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="locationId" class="col-md-4 control-label">Location:</label>
                            <div class="col-md-8">
                                <select name="locationId" selected="${location.locationName}">
                                    <option value="${location.locationId}" name="locationId">${location.locationName}</option>
                                    <c:forEach var="currentLocation" items="${locationList}">
                                        <option value="${currentLocation.locationId}" name="locationId">${currentLocation.locationName}</option>
                                    </c:forEach>
                                </select>
                            </div>           
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Update Organization"/>
                            </div>
                        </div>               
                    </form>
                    <div class="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <a href="${pageContext.request.contextPath}/cancelOrg">
                                <button type="cancel" class="btn btn-default " id="/allorganizations">Cancel</button>
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
