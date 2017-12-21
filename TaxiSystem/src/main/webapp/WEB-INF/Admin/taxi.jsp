<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 21.12.2017
  Time: 1:41
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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">

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
    <input type="hidden" name="page" value="Controller?method=userManager&action=getTaxiList"/>
    <input type="submit" value="${rusButton}">
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="en"/>
    <input type="hidden" name="page" value="Controller?method=userManager&action=getTaxiList"/>
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


<div class="container" align="center">
    <table class="table table-striped">
        <thead class="thead-dark">
        <tr>
            <th>$$$taxiID</th>
            <th>$$$taxilogin</th>
            <th>$$$taxifirstName</th>
            <th>$$$taxiLastName</th>
            <th>$$$carNumber</th>
            <th>$$$carName</th>
            <th>$$$carColour</th>
            <th>$$$availableStatus</th>
            <th>$$$banStatus</th>
            <th>###Действие</th>
        </tr>
        </thead>
        <c:forEach var="taxi" items="${requestScope.pageTaxiList}">
            <tr>
                <td>${taxi.id}</td>
                <td>${taxi.login}</td>
                <td>${taxi.firstName}</td>
                <td>${taxi.lastName}</td>
                <td>${taxi.car.number}</td>
                <td>${taxi.car.name}</td>
                <td>${taxi.car.colour}</td>
                <td>${taxi.availableStatus}</td>
                <td>${taxi.banStatus}</td>
                <td>
                    <form action="Controller" method="post">
                        <input type="hidden" name="method" value = "userManager"/>
                        <input type="hidden" name="action" value="ban"/>
                        <input type="hidden" name="id" value="${taxi.id}"/>
                        <input type="hidden" name="role" value="${taxi.role}"/>
                        <button type="submit">$$$Забанить</button>
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
                        <li class="page-item"><a class="page-link" href="/Controller?method=userManager&action=getTaxiList&page=${i}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </nav>
</div>
    забанить, добавить нового
</body>
</html>
