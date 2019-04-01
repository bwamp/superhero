<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Index Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Superhero Sighting Home Page</h1>
            <hr/>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p>Hello : ${pageContext.request.userPrincipal.name}
                    | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                </p>
            </c:if>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/hello">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/allheroes">Heroes</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/alllocations">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/allorganizations">Organizations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/allsightings">Sightings</a></li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/displayUserList">Users</a></li>
                    </sec:authorize>
                </ul>    
            </div>


            <div class="onoffswitch3">
                <label class="onoffswitch3-label" for="headline">
                    <span class="headline">MOST RECENT SIGHTING <span class="glyphicon"></span></span>
                    <marquee class="scroll-text"> ${recentSighting.hero.heroName} was recently seen at ${recentSighting.location.locationName}. Be on the lookout!                          
                    </marquee> 
                </label>
            </div>



            <p>Welcome to the most up-to-date sight that tracks the latest Hero sightings. We aren't talking your "normal heroes" like the ones you did your reports on
                when you were growing up(e.g. your mom/dad or your favorite athlete). We are talking about real Heros with real powers. 
                If you are here it is because you have seen one
                of these famous, or infamous, individuals and are wanting to collect the reward for the sighting. If adding a new Superhero, be sure to add a power
                a location of where you saw them. Otherwise, how would we know if they were really a superhero if you didn't see their power, or even see them at all.
                We are only adding confirmed superheroes to our database.
            </p>
            <hr>
            <h2>Top 10 Recent Sightings from all over</h2>

            <div class="row">
                <div class="col-md-8">
                    <table id="table" class="table table-hover">
                        <tr>
                            <th width="20%">Hero Name</th>
                            <th width="40%">Description</th>
                            <th width="20%">Location</th>
                            <th width="20%">Date</th>
                        </tr>
                        <c:forEach var="currentSight" items="${tenSightings}">
                            <tr>
                                <td>
                                    <c:out value="${currentSight.hero.heroName}"/> 

                                </td>
                                <td>
                                    <c:out value="${currentSight.hero.heroDesc}"/>
                                </td>
                                <td>
                                    <c:out value="${currentSight.location.locationName}"/>
                                </td>
                                <td>
                                    <c:out value="${currentSight.sightingDate}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                  
                </div> <!-- End col div -->
            </div>

        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

