<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 14.12.2017
  Time: 22:44
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
    <fmt:message bundle="${loc}" key="local.all.welcome" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.all.logOut" var="logOut"/>
    <fmt:message bundle="${loc}" key="local.taxi.orders.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.taxi.nav.orders" var="orders"/>
    <fmt:message bundle="${loc}" key="local.taxi.nav.profile" var="myProfile"/>
    <fmt:message bundle="${loc}" key="local.all.order.orderId" var="orderIdName"/>
    <fmt:message bundle="${loc}" key="local.all.order.price" var="orderPrice"/>
    <fmt:message bundle="${loc}" key="local.all.user.name" var="userName"/>
    <fmt:message bundle="${loc}" key="local.all.user.surname" var="userSurname"/>
    <fmt:message bundle="${loc}" key="local.all.order.action" var="orderAction"/>
    <fmt:message bundle="${loc}" key="local.taxi.orders.accept" var="acceptOrder"/>
    <fmt:message bundle="${loc}" key="local.taxi.orders.reject" var="rejectOrder"/>
    <fmt:message bundle="${loc}" key="local.taxi.orders.completed" var="orderCompleted"/>
    <fmt:message bundle="${loc}" key="local.taxi.orders.rejected" var="orderRejected"/>
    <fmt:message bundle="${loc}" key="local.taxi.orders.withMap" var="withMap"/>
    <fmt:message bundle="${loc}" key="local.taxi.orders.noClient" var="noClient"/>
    <title>${title}</title>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCWVlbCzAS1kedMyyEjnnASz9vwaIjOmp8"></script>
    <link href="https://fonts.googleapis.com/css?family=Anton" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
    <link rel="stylesheet " href="../../css/common.css"/>
    <script>
        <%@include file="../../js/taxiMap.js"%>
        <%@include file="../../js/radioTaxi.js"%>
    </script>
</head>

<body>

<jsp:useBean id="user" class="entity.User" scope="session"/>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
    <a style="font-family: 'Anton', sans-serif;" class="navbar-brand" href="Controller?method=signManager&action=goHomePage">TAXI</a>
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link active" href="Controller?method=dispatcher&action=getTaxiOrders">${orders}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=userManager&action=preProfile">${myProfile}</a>
        </li>
    </ul>

    <div class="btn-group" role="group">
        <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            ${languages}
        </button>
        <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
            <a class="dropdown-item" href="Controller?method=localization&local=ru&page=Controller&#63;method&#61;dispatcher&amp;action&#61;getTaxiOrders">${rusButton}</a>
            <a class="dropdown-item" href="Controller?method=localization&local=en&page=Controller&#63;method&#61;dispatcher&amp;action&#61;getTaxiOrders">${engButton}</a>
        </div>
        <div class="logOutMenu">
            <span class="welcomeUser">${welcome} ${user.firstName} ${user.lastName}</span>
            <a href="Controller?method=signManager&action=logOut">
                <button type="button" class="btn btn-light">${logOut}</button>
            </a>
        </div>
    </div>
</nav>
<!--/////////////////////////////////////////////////////////////////////////////////////--->


<div class="my-flex-container" style="justify-content: space-between; align-items: stretch">
    <div class="wightPage" style="width: 50%; height: 92%">
        <table class="table table-striped table-dark">
            <tr class="tr-text">
                <th>${orderIdName}</th>
                <th>${userName}</th>
                <th>${userSurname}</th>
                <th>${orderPrice}</th>
                <th>${orderAction}</th>
            </tr>
            <c:forEach var="order" items="${requestScope.taxiOrder}">
                <tr class="tr-text">
                    <td>${order.orderId}</td>
                    <td>${order.client.firstName}</td>
                    <td>${order.client.lastName}</td>
                    <td>${order.price}</td>
                    <td style="max-height: 100px"><c:choose>
                        <c:when test = "${order.orderStatus eq 'processed'}">
                            <span>
                                <form style="display: inline-block;" action="Controller" method="post">
                                <input type="hidden" name="method" value = "dispatcher"/>
                                <input type="hidden" name="action" value="acceptOrder"/>
                                <input type="hidden" name="orderId" value="${order.orderId}"/>
                                <button class="btn btn-light" type="submit">${acceptOrder}</button>
                            </form>
                            <form style="display: inline-block;" action="Controller" method="post">
                                <input type="hidden" name="method" value = "dispatcher"/>
                                <input type="hidden" name="action" value="rejectOrder"/>
                                <input type="hidden" name="orderId" value="${order.orderId}"/>
                                <button class="btn btn-light" type="submit">${rejectOrder}</button>
                            </form>
                            </span>
                        </c:when>
                        <c:when test="${order.orderStatus eq 'accepted'}">
                            <span><button class="btn btn-dark" onclick="setCoord(${order.sourceCoordinate},${order.destinyCoordinate})">${withMap}</button>
                            <form style="display: inline-block" action="Controller" method="post">
                                <input type="hidden" name="method" value = "dispatcher"/>
                                <input type="hidden" name="action" value="cancelOrder"/>
                                <input type="hidden" name="orderId" value="${order.orderId}"/>
                                <button class="btn btn-dark" type="submit">${noClient}</button>
                            </form></span>
                        </c:when>
                        <c:when test="${order.orderStatus eq 'rejected'}">
                            <button class="btn btn-secondary" disabled>${orderRejected}</button>
                        </c:when>
                        <c:when test="${order.orderStatus eq 'completed'}">
                            <button class="btn btn-secondary" disabled>${orderCompleted}</button>
                        </c:when>
                    </c:choose></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div id="taxiMap" class="map my-flex-block"></div>
</div>


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
