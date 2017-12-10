<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 06.12.2017
  Time: 0:59
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
    <style>
        <%@include file="mapStyle.css"%>
    </style>
</head>
<body>
<jsp:useBean id="user" class="entity.Client" scope="session"/>
<span>$$$Здравствуйте ${user.firstName} ${user.lastName}</span>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="logOut">
    <input type="submit" value="$$$Выйти">
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="ru"/>
    <input type="hidden" name="page" value="Controller?method=profile&action=getOrders"/>
    <input type="submit" value="${rusButton}">
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="en"/>
    <input type="hidden" name="page" value="Controller?method=profile&action=getOrders"/>
    <input type="submit" value="${engButton}">
</form>
<!--/////////////////////////////////////////////////////////-->

<form action="Controller" method="post">
    <input type="hidden" name="method" value = "dispatcher"/>
    <input type="hidden" name="action" value="preOrder"/>
    <button type="submit">$$$Заказать такси</button>
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value = "profile">
    <input type="hidden" name="action" value="getOrders">
    <button type="submit">$$$Мой профиль</button>
</form>
<!--/////////////////////////////////////////////////////////-->
<table>
    <tr>
        <th>$$$id</th>
        <th>$$$src</th>
        <th>$$$dst</th>
        <th>$$$number</th>
        <th>$$$car</th>
        <th>$$$colour</th>
        <th>$$$price</th>
        <th>$$$status</th>
        <th>###Действие</th>
    </tr>
    <c:forEach var="order" items="${requestScope.clientOrder}">
    <tr>
        <td>${order.orderId}</td>
        <td>${order.sourceCoordinate}</td>
        <td>${order.destinyCoordinate}</td>
        <td>${order.taxi.car.number}</td>
        <td>${order.taxi.car.name}</td>
        <td>${order.taxi.car.colour}</td>
        <td>${order.price}</td>
        <td>${order.orderStatus}</td>
        <td><c:choose>
            <c:when test = "${order.orderStatus eq 'processed'}">
                <form>
                    <button type="submit">$$$Отменить</button>
                </form>
            </c:when>
            <c:when test = "${order.orderStatus eq 'accepted'}">
                <form>
                    <button type="submit">$$$Отзыв</button>
                </form>
            </c:when>
        </c:choose></td>
    </tr>
    </c:forEach>

</table>

</body>
</html>
