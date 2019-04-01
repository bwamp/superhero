<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Hero</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Edit a Hero</h1>
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
                    <h2>Please Update Hero Info</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="editHero">
                        <div class="form-group">
                            <label for="editHeroName" class="col-md-4 control-label">Hero Name:</label>
                            <div class="col-md-8">
                                <input value="${updateHero.heroName}" type="text" class="form-control" name="heroName" placeholder="Hero Name" required/>
                                <input value="${updateHero.heroId}" type="hidden" class="form-control" name="heroId"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addHeroDesc" class="col-md-4 control-label">Description:</label>
                            <div class="col-md-8">
                                <input value="${updateHero.heroDesc}" type="text" class="form-control" name="heroDesc" placeholder="Enter a short description" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addPowerName" class="col-md-4 control-label">Power:</label>
                            <div class="col-md-8">
                                <select name="superpowerName" selected="${currentPower.superpowerName}">
                                    <option value="${power.superpowerName}">${power.superpowerName}</option>
                                    <c:forEach var="currentPower" items="${powerList}">
                                        <option value="${currentPower.superpowerName}" name="superpowerName">${currentPower.superpowerName}</option>
                                    </c:forEach>
                                </select>
                            </div>           
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Update Hero"/>
                            </div>
                        </div>               
                    </form>
                    <div class="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <a href="${pageContext.request.contextPath}/cancelHero">
                                <button type="cancel" class="btn btn-default " id="/allheroes">Cancel</button>
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
