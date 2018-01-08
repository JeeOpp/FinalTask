<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 21.12.2017
  Time: 1:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.all.rusButton" var="rusButton"/>
    <fmt:message bundle="${loc}" key="local.all.engButton" var="engButton"/>
    <fmt:message bundle="${loc}" key="local.all.localization" var="languages"/>
    <fmt:message bundle="${loc}" key="local.admin.welcome" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.all.logOut" var="logOut"/>
    <fmt:message bundle="${loc}" key="local.admin.client.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.admin.nav.orders" var="orders"/>
    <fmt:message bundle="${loc}" key="local.admin.nav.taxi" var="taxi"/>
    <fmt:message bundle="${loc}" key="local.admin.nav.client" var="client"/>
    <fmt:message bundle="${loc}" key="local.admin.nav.cars" var="cars"/>
    <fmt:message bundle="${loc}" key="local.all.user.id" var="userId"/>
    <fmt:message bundle="${loc}" key="local.all.user.name" var="userName"/>
    <fmt:message bundle="${loc}" key="local.all.user.surname" var="userSurname"/>
    <fmt:message bundle="${loc}" key="local.all.user.login" var="userLogin"/>
    <fmt:message bundle="${loc}" key="local.all.user.banStatus" var="banStatus"/>
    <fmt:message bundle="${loc}" key="local.all.client.bonuses" var="bonuses"/>
    <fmt:message bundle="${loc}" key="local.admin.client.changeOn" var="changeOn"/>
    <fmt:message bundle="${loc}" key="local.admin.client.changeButton" var="changeButton"/>
    <fmt:message bundle="${loc}" key="local.admin.ban" var="ban"/>
    <fmt:message bundle="${loc}" key="local.admin.unBan" var="unban"/>

    <title>${title}</title>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCWVlbCzAS1kedMyyEjnnASz9vwaIjOmp8"></script>
    <link href="https://fonts.googleapis.com/css?family=Anton" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
    <link rel="stylesheet" href="../../css/common.css"/>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
    <a style="font-family: 'Anton', sans-serif;" class="navbar-brand" href="Controller?method=signManager&action=goHomePage">TAXI</a>
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=dispatcher&action=getAllOrders">${orders}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=userManager&action=getTaxiList">${taxi}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="Controller?method=userManager&action=getClientList">${client}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=taxis&action=getCarList">${cars}</a>
        </li>
    </ul>
    <div class="btn-group" role="group">
        <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            ${languages}
        </button>
        <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
            <a class="dropdown-item" href="Controller?method=localization&local=ru&page=Controller&#63;method&#61;userManager&amp;action&#61;getClientList">${rusButton}</a>
            <a class="dropdown-item" href="Controller?method=localization&local=en&page=Controller&#63;method&#61;userManager&amp;action&#61;getClientList">${engButton}</a>
        </div>
        <div class="logOutMenu">
            <span class="welcomeUser">${welcome}</span>
            <a href="Controller?method=signManager&action=logOut">
                <button type="button" class="btn btn-light">${logOut}</button>
            </a>
        </div>
    </div>
</nav>

<br/>
<table class="table table-striped table-dark">
    <thead class="thead-dark">
    <tr class="tr-text">
        <th>${userId}</th>
        <th>${userLogin}</th>
        <th>${userName}</th>
        <th>${userSurname}</th>
        <th>${bonuses}</th>
        <th>${banStatus}</th>
    </tr>
    </thead>
    <c:forEach var="client" items="${requestScope.pageClientList}">
        <tr class="tr-text">
            <td>${client.id}</td>
            <td>${client.login}</td>
            <td>${client.firstName}</td>
            <td>${client.lastName}</td>
            <td>
                <form action="Controller" method="post">
                    <input type="hidden" name="method" value = "userManager"/>
                    <input type="hidden" name="action" value="changeBonusCount"/>
                    <input type="hidden" name="clientId" value="${client.id}"/>
                    <label for="changeBonusText">${client.bonusPoints} </label>
                    <input type="text" id="changeBonusText" name="bonusPoints" placeholder="${changeOn}" required/>
                    <button class="btn btn-light" type="submit">${changeButton}</button>
                </form>
            </td>
            <td>
                <form action="Controller" method="post">
                    <input type="hidden" name="method" value = "userManager"/>
                    <input type="hidden" name="action" value="changeBanStatus"/>
                    <input type="hidden" name="id" value="${client.id}"/>
                    <input type="hidden" name="banStatus" value="${client.banStatus}"/>
                    <input type="hidden" name="role" value="${client.role}"/>
                    <button class="btn btn-light" type="submit">
                        <c:choose>
                            <c:when test="${!client.banStatus}">
                                ${ban}
                            </c:when>
                            <c:otherwise>
                                ${unban}
                            </c:otherwise>
                        </c:choose>
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<nav aria-label="Page navigation example">    <!-- table -->
    <ul class="pagination">                   <!-- tr -->
        <c:forEach begin="1" end="${requestScope.countPages}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPage eq i}">
                    <li class="page-item"><a class="page-link">${i}</a></li>      <!-- td-->
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="/Controller?method=userManager&action=getClientList&numPage=${i}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</nav>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
