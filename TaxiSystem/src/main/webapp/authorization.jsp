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
    <fmt:message bundle="${loc}" key="local.index.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.all.rusButton" var="rusButton"/>
    <fmt:message bundle="${loc}" key="local.all.engButton" var="engButton"/>
    <fmt:message bundle="${loc}" key="local.authorization.authorizationButton" var="authButton"/>

    <title>$$$</title>
</head>
<body>
    <form action="Controller" method="post">
        <input type="hidden" name="method" value="localization"/>
        <input type="hidden" name="local" value="ru"/>
        <input type="hidden" name="page" value="authorization.jsp"/>
        <input type="submit" value="${rusButton}">
    </form>
    <form action="Controller" method="post">
        <input type="hidden" name="method" value="localization"/>
        <input type="hidden" name="local" value="en"/>
        <input type="hidden" name="page" value="authorization.jsp"/>
        <input type="submit" value="${engButton}">
    </form>


    <form action="Controller" method="post">
        <input type="hidden" name="method" value="signManager"/>
        <input type="hidden" name="action" value="authorization"/>
        <input type="text" name="login" value="client"/>
        <input type="password" name="password" value="root"/>
        <button type="submit">${authButton}</button>
    </form>
</body>
</html>
