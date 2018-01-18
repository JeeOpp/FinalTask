<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 21.12.2017
  Time: 1:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="mytag" uri="/WEB-INF/tld/taglib.tld" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.all.rusButton" var="rusButton"/>
    <fmt:message bundle="${loc}" key="local.all.engButton" var="engButton"/>
    <fmt:message bundle="${loc}" key="local.all.localization" var="languages"/>
    <fmt:message bundle="${loc}" key="local.admin.welcome" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.all.logOut" var="logOut"/>
    <fmt:message bundle="${loc}" key="local.admin.orders.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.admin.nav.orders" var="orders"/>
    <fmt:message bundle="${loc}" key="local.admin.nav.taxi" var="taxi"/>
    <fmt:message bundle="${loc}" key="local.admin.nav.client" var="client"/>
    <fmt:message bundle="${loc}" key="local.admin.nav.cars" var="cars"/>
    <fmt:message bundle="${loc}" key="local.all.order.orderId" var="orderId"/>
    <fmt:message bundle="${loc}" key="local.all.order.source" var="orderSrc"/>
    <fmt:message bundle="${loc}" key="local.all.order.destiny" var="orderDst"/>
    <fmt:message bundle="${loc}" key="local.all.order.price" var="orderPrice"/>
    <fmt:message bundle="${loc}" key="local.all.order.orderStatus" var="orderStatus"/>
    <fmt:message bundle="${loc}" key="local.all.order.action" var="orderAction"/>
    <fmt:message bundle="${loc}" key="local.all.client.id" var="clientId"/>
    <fmt:message bundle="${loc}" key="local.all.client.login" var="clientLogin"/>
    <fmt:message bundle="${loc}" key="local.all.taxi.id" var="taxiId"/>
    <fmt:message bundle="${loc}" key="local.all.taxi.login" var="taxiLogin"/>
    <fmt:message bundle="${loc}" key="local.all.car.number" var="carNumber"/>
    <fmt:message bundle="${loc}" key="local.all.car.name" var="carName"/>
    <fmt:message bundle="${loc}" key="local.all.car.colour" var="colour"/>
    <fmt:message bundle="${loc}" key="local.admin.orders.deleteOrder" var="deleteOrder"/>
    <fmt:message bundle="${loc}" key="local.admin.orders.deleteAll" var="deleteAll"/>
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
            <a class="nav-link active" href="Controller?method=dispatcher&action=getAllOrders">${orders}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=userManager&action=getTaxiList">${taxi}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=userManager&action=getClientList">${client}</a>
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
            <a class="dropdown-item" href="Controller?method=localization&local=ru&page=Controller&#63;method&#61;dispatcher&amp;action&#61;getAllOrders">${rusButton}</a>
            <a class="dropdown-item" href="Controller?method=localization&local=en&page=Controller&#63;method&#61;dispatcher&amp;action&#61;getAllOrders">${engButton}</a>
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
        <th>${orderId}</th>
        <th>${clientId}</th>
        <th>${clientLogin}</th>
        <th>${taxiId}</th>
        <th>${taxiLogin}</th>
        <th>${orderSrc}</th>
        <th>${orderDst}</th>
        <th>${carNumber}</th>
        <th>${carName}</th>
        <th>${colour}</th>
        <th>${orderPrice}</th>
        <th>${orderStatus}</th>
        <th>${orderAction}</th>
    </tr>
    </thead>
    <c:forEach var="order" items="${requestScope.pageOrderList}">
        <tr class="tr-text">
            <td>${order.orderId}</td>
            <td>${order.client.id}</td>
            <td>${order.client.login}</td>
            <td>${order.taxi.id}</td>
            <td>${order.taxi.login}</td>
            <td>${order.sourceCoordinate}</td>
            <td>${order.destinyCoordinate}</td>
            <td>${order.taxi.car.number}</td>
            <td>${order.taxi.car.name}</td>
            <td>${order.taxi.car.colour}</td>
            <td>${order.price}</td>
            <mytag:orderColour orderStatus="${order.orderStatus}" locale="${sessionScope.local}"/>
            <td>
                <form action="Controller" method="post">
                    <input type="hidden" name="method" value = "dispatcher"/>
                    <input type="hidden" name="action" value="cancelOrder"/>
                    <input type="hidden" name="orderId" value="${order.orderId}"/>
                    <button class="btn btn-light" type="submit">${deleteOrder}</button>
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
                    <li class="page-item"><a class="page-link" href="/Controller?method=dispatcher&action=getAllOrders&numPage=${i}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</nav>
<!--УДАЛИТЬ ВСЕ-->
<form action="Controller" method="post">
    <input type="hidden" name="method" value = "dispatcher"/>
    <input type="hidden" name="action" value="deleteAllOrders"/>
    <button class="btn btn-danger" type="submit">${deleteAll}</button>
</form>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
