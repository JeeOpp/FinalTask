<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 12.11.2017
  Time: 19:15
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
    <title>Регистрация</title>
</head>
<body>
<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="ru"/>
    <input type="hidden" name="page" value="registration.jsp"/>
    <input type="submit" value="${rusButton}">
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="en"/>
    <input type="hidden" name="page" value="registration.jsp"/>
    <input type="submit" value="${engButton}">
</form>
    <form action="Controller" method="post">
        <input type="hidden" name="method" value="registration">
        <input type="hidden" name="role" value="client">

        <input type="text" name="login" value="Ivan">
        <input type="text" name="password" value="Ivanov">
        <input type="text" name="firstName" value="">
        <input type="text" name="lastName" value="">
        <button type="submit">Зарегистрироваться</button>
    </form>
</body>
</html>