<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 05.12.2017
  Time: 22:50
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
    <script>
        <%@include file="map.js"%>
    </script>
</head>
<body>
    <jsp:useBean id="user" class="entity.User" scope="session"/>
    <span>$$$Здравствуйте ${user.firstName} ${user.lastName}</span>

    <form action="Controller" method="post">
        <input type="hidden" name="method" value="logOut">
        <input type="submit" value="$$$Выйти">
    </form>
    <form action="Controller" method="post">
        <input type="hidden" name="method" value="localization"/>
        <input type="hidden" name="local" value="ru"/>
        <input type="hidden" name="page" value="WEB-INF/Client/clientMain.jsp"/>
        <input type="submit" value="${rusButton}">
    </form>
    <form action="Controller" method="post">
        <input type="hidden" name="method" value="localization"/>
        <input type="hidden" name="local" value="en"/>
        <input type="hidden" name="page" value="WEB-INF/Client/clientMain.jsp"/>
        <input type="submit" value="${engButton}">
    </form>

    <form action="Controller" method="post">
        <input type="hidden" name="method" value = "dispatcher">
        <input type="hidden" name="action" value="callTaxi">
        <input type="hidden" name="position">
        <input type="hidden" name="user" value="${sessionScope.user}">
        <ul>
            <c:forEach var="taxi" items="${requestScope.availableTaxiList}">
                <li><input type="radio" name="taxi" value="${taxi}">${taxi.firstName} ${taxi.lastName} ${taxi.car.name} ${taxi.car.colour}</li>
            </c:forEach> <!-- JAVA SCRIPT!!!!!!!!!!-->
        </ul>
        <button type="submit">$$$Заказать такси</button>
    </form>

    <div id="map" class="map"></div>
</body>
</html>
