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
    <title>$$$Taxi</title>
</head>

<body>
<jsp:useBean id="user" class="entity.Taxi" scope="session"/>
<span>$$$Здравствуйте ${user.firstName} ${user.lastName}</span>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="signManager"/>
    <input type="hidden" name="action" value="logOut"/>
    <input type="submit" value="$$$Выйти"/>
</form>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="ru"/>
    <input type="hidden" name="page" value="Controller?method=dispatcher&action=getTaxiOrders"/>
    <input type="submit" value="${rusButton}">
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="en"/>
    <input type="hidden" name="page" value="Controller?method=dispatcher&action=getTaxiOrders"/>
    <input type="submit" value="${engButton}">
</form>

<!--///////////////////////////////////////////////////////////////////////////////// -->
<form action="Controller" method="post">
    <input type="hidden" name="method" value = "dispatcher"/>
    <input type="hidden" name="action" value="getTaxiOrders"/>
    <button type="submit">$$$Заказы</button>
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value = "profile"/>
    <input type="hidden" name="action" value="preProfile"/>
    <button type="submit">$$$Мой профиль</button>
</form>

<!--/////////////////////////////////////////////////////////////////////////////////////--->

<table>
    <tr>
        <th>$$$id</th>
        <th>$$$name</th>
        <th>$$$surname</th>
        <th>$$$src</th>
        <th>$$$dst</th>
        <th>$$$price</th>
        <th>###Действие</th>
    </tr>
    <c:forEach var="order" items="${requestScope.taxiOrder}">
        <tr>
            <td>${order.orderId}</td>
            <td>${order.client.firstName}</td>
            <td>${order.client.lastName}</td>
            <td>${order.sourceCoordinate}</td>
            <td>${order.destinyCoordinate}</td>
            <td>${order.price}</td>
            <td><c:choose>
                <c:when test = "${order.orderStatus eq 'processed'}">
                    <form action="Controller" method="post">
                        <input type="hidden" name="method" value = "dispatcher"/>
                        <input type="hidden" name="action" value="acceptOrder"/>
                        <input type="hidden" name="orderId" value="${order.orderId}"/>
                        <button type="submit">$$$Подтвердить</button>
                    </form>
                    <form action="Controller" method="post">
                        <input type="hidden" name="method" value = "dispatcher"/>
                        <input type="hidden" name="action" value="rejectOrder"/>
                        <input type="hidden" name="orderId" value="${order.orderId}"/>
                        <button type="submit">$$$Отклонить</button>
                    </form>
                </c:when>
                <c:when test="${order.orderStatus eq 'accepted'}">
                    <button disabled>$$$Заказ принят</button>
                </c:when>
                <c:when test="${order.orderStatus eq 'rejected'}">
                    <button disabled>$$$Заказ отклонен</button>
                </c:when>
                <c:when test="${order.orderStatus eq 'completed'}">
                    <button disabled>$$$Заказ выполнен</button>
                </c:when>
            </c:choose></td>
        </tr>
    </c:forEach>


</table>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
