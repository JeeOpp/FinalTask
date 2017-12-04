<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 12.11.2017
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.all.rusButton" var="rusButton"/>
    <fmt:message bundle="${loc}" key="local.all.engButton" var="engButton"/>
    <fmt:message bundle="${loc}" key="local.index.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.index.logIn" var="logIn"/>
    <fmt:message bundle="${loc}" key="local.index.signUp" var="signUp"/>

    <title>${title}</title>
</head>
<body>
    <%=session.getAttribute("local")%>
    <form action="Controller" method="post">
        <input type="hidden" name="method" value="localization"/>
        <input type="hidden" name="local" value="ru"/>
        <input type="hidden" name="page" value="index.jsp"/>
        <input type="submit" value="${rusButton}">
    </form>
    <form action="Controller" method="post">
        <input type="hidden" name="method" value="localization"/>
        <input type="hidden" name="local" value="en"/>
        <input type="hidden" name="page" value="index.jsp"/>
        <input type="submit" value="${engButton}">
    </form>


    <button><a href="authorization.jsp">${logIn}</a></button>
    <button><a href="registration.jsp">${signUp}</a></button>
</body>
</html>
