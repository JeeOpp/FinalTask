<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 12.11.2017
  Time: 19:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.all.rusButton" var="rusButton"/>
    <fmt:message bundle="${loc}" key="local.all.engButton" var="engButton"/>
    <title>$$$User</title>
</head>

<body>
<span>$$$Здравствуйте Администратор</span>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="signManager"/>
    <input type="hidden" name="action" value="logOut"/>
    <input type="submit" value="$$$Выйти"/>
</form>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="ru"/>
    <input type="hidden" name="page" value="WEB-INF/Admin/main.jsp"/>
    <input type="submit" value="${rusButton}">
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="en"/>
    <input type="hidden" name="page" value="WEB-INF/Admin/main.jsp"/>
    <input type="submit" value="${engButton}">
</form>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="dispatcher"/>
    <input type="hidden" name="action" value="getAllOrders"/>
    <input type="submit" value="$$$Архив Заказов">
</form>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="userManager"/>
    <input type="hidden" name="action" value="getTaxiList"/>
    <input type="submit" value="$$$Таксисты">
</form>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="userManager"/>
    <input type="hidden" name="action" value="getClientList"/>
    <input type="submit" value="$$$Клиенты">
</form>

</body>
</html>
