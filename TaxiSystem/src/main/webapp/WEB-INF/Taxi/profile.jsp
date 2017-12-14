<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 14.12.2017
  Time: 22:38
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
    <input type="hidden" name="method" value="logOut">
    <input type="hidden" name="role" value="taxi">
    <input type="submit" value="$$$Выйти">
</form>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="ru"/>
    <input type="hidden" name="page" value="WEB-INF/Taxi/profile$$$"/>
    <input type="submit" value="${rusButton}">
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="en"/>
    <input type="hidden" name="page" value="WEB-INF/Taxi/profile$$$"/>
    <input type="submit" value="${engButton}">
</form>

<!--///////////////////////////////////////////////////////////////////////////////// -->
<form action="Controller" method="post">
    <input type="hidden" name="method" value = "dispatcher"/>
    <input type="hidden" name="action" value="getOrders"/>
    <button type="submit">$$$Заказы</button>
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value = "profile">
    <button type="submit">$$$Мой профиль</button>
</form>

</body>
</html>
