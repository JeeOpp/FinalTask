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
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.all.rusButton" var="rusButton"/>
    <fmt:message bundle="${loc}" key="local.all.engButton" var="engButton"/>
    <title>$$$User</title>
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
            <a class="nav-link active" href="Controller?method=dispatcher&action=getAllOrders">$$$Архив заказов</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=userManager&action=getTaxiList">$$$Таксисты</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=userManager&action=getClientList">$$$Клиенты</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=taxis&action=getCarList">$$$Автомобили</a>
        </li>
    </ul>

    <div class="btn-group" role="group">
        <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            &&&Dropdown
        </button>
        <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
            <a class="dropdown-item" href="Controller?method=localization&local=ru&page=Controller&#63;method&#61;dispatcher&amp;action&#61;getAllOrders&amp;page&#61;1">${rusButton}</a>
            <a class="dropdown-item" href="Controller?method=localization&local=en&page=Controller&#63;method&#61;dispatcher&amp;action&#61;getAllOrders&amp;page&#61;1">${engButton}</a>
        </div>
        <div class="logOutMenu">
            <span class="welcomeUser">$$$Здравствуйте Администратор</span>
            <a href="Controller?method=signManager&action=logOut">
                <button type="button" class="btn btn-light">$$$LogOut</button>
            </a>
        </div>
    </div>
</nav>
<br/>
<table class="table table-striped table-dark">
    <thead class="thead-dark">
    <tr class="tr-text">
        <th>$$$id</th>
        <th>$$$cID</th>
        <th>$$$clogin</th>
        <th>$$$tId</th>
        <th>$$$tLogin</th>
        <th>$$$src</th>
        <th>$$$dst</th>
        <th>$$$numb</th>
        <th>$$$car</th>
        <th>$$$colour</th>
        <th>$$$price</th>
        <th>$$$status</th>
        <th>###Действие</th>
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
            <td>${order.orderStatus}</td>
            <td>
                <form action="Controller" method="post">
                    <input type="hidden" name="method" value = "dispatcher"/>
                    <input type="hidden" name="action" value="cancelOrder"/>
                    <input type="hidden" name="orderId" value="${order.orderId}"/>
                    <button class="btn btn-light" type="submit">$$$Удалить</button>
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
                    <li class="page-item"><a class="page-link" href="/Controller?method=dispatcher&action=getAllOrders&page=${i}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</nav>
<!--УДАЛИТЬ ВСЕ-->
<form action="Controller" method="post">
    <input type="hidden" name="method" value = "dispatcher"/>
    <input type="hidden" name="action" value="deleteAllOrders"/>
    <button class="btn btn-danger" type="submit">$$$Удалить все заказы</button>
</form>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
