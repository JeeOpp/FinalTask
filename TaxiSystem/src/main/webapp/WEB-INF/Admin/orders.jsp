<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 21.12.2017
  Time: 1:38
  To change this template use File | Settings | File Templates.
--%>
<<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="en"/>
    <input type="hidden" name="page" value="WEB-INF/Admin/main.jsp"/>
    <input type="submit" value="$$$Таксисты">
</form>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="en"/>
    <input type="hidden" name="page" value="WEB-INF/Admin/main.jsp"/>
    <input type="submit" value="$$$Клиенты">
</form>


<div class="container" align="center">
    <table class="table table-striped">
        <thead class="thead-dark">
            <tr>
                <th>$$$orderid</th>
                <th>$$$clientID</th>
                <th>$$$clientlogin</th>
                <th>$$$clientfirstName</th>
                <th>$$$clientLastName</th>
                <th>$$$taxiId</th>
                <th>$$$taxiLogin</th>
                <th>$$$taxiFirstName</th>
                <th>$$$taxiLastName</th>
                <th>$$$src</th>
                <th>$$$dst</th>
                <th>$$$number</th>
                <th>$$$car</th>
                <th>$$$colour</th>
                <th>$$$price</th>
                <th>$$$status</th>
                <th>###Действие</th>
            </tr>
        </thead>
        <c:forEach var="order" items="${requestScope.pageOrderList}">
            <tr>
                <td>${order.orderId}</td>
                <td>${order.client.id}</td>
                <td>${order.client.login}</td>
                <td>${order.client.firstName}</td>
                <td>${order.client.lastName}</td>
                <td>${order.taxi.id}</td>
                <td>${order.taxi.login}</td>
                <td>${order.taxi.firstName}</td>
                <td>${order.taxi.lastName}</td>
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
                        <button type="submit">$$$Удалить</button>
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
</div>


<!--УДАЛИТЬ ВСЕ-->



<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
