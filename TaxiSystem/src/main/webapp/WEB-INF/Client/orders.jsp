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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCWVlbCzAS1kedMyyEjnnASz9vwaIjOmp8"></script>
    <script>
        <%@include file="support.js"%>
    </script>
</head>
<body>
<jsp:useBean id="user" class="entity.Client" scope="session"/>
<span>$$$Здравствуйте ${user.firstName} ${user.lastName}</span>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="signManager"/>
    <input type="hidden" name="action" value="logOut"/>
    <input type="submit" value="$$$Выйти"/>
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="ru"/>
    <input type="hidden" name="page" value="Controller?method=dispatcher&action=getClientOrders"/>
    <input type="submit" value="${rusButton}">
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="en"/>
    <input type="hidden" name="page" value="Controller?method=dispatcher&action=getClientOrders"/>
    <input type="submit" value="${engButton}">
</form>
<!--/////////////////////////////////////////////////////////-->

<form action="Controller" method="post">
    <input type="hidden" name="method" value = "dispatcher"/>
    <input type="hidden" name="action" value="preOrder"/>
    <button type="submit">$$$Заказать такси</button>
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value = "dispatcher">
    <input type="hidden" name="action" value="getClientOrders">
    <button type="submit">$$$Мои заказы</button>
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value = "profile">
    <input type="hidden" name="action" value="preProfile"/>
    <button type="submit">$$$Мой профиль</button>
</form>
<!-- ВСплывающее окно -->
<div class="modal fade" id="payModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="Controller" method="post">              <!--Оплата не работает, так что прост -->
                <input type="hidden" name="method" value="dispatcher">   <!--input-->
                <input type="hidden" name="action" value="payOrder">  <!--input-->
                <input type="hidden" name="orderId">                    <!--input-->
                <div class="modal-header">
                    <h5 class="modal-title" id="payModalLabel">$$$Modal pay title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <label for="payText">$$$ К оплате:</label>
                    <input type="text" id="payText" name="payText" readonly>  <!--input-->
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">$$$Оплатить</button>  <!--input-->
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">$$$Close</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="reviewModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="Controller" method="post">              <!--ФОРМА НА ОТПРАВКУ ОТзЫВА-->
                <input type="hidden" name="method" value="feedback">   <!--input-->
                <input type="hidden" name="action" value="writeReview">  <!--input-->
                <input type="hidden" name="taxiId">                    <!--input-->
                <div class="modal-header">
                    <h5 class="modal-title" id="reviewModalLabel">$$$Modal review title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <textarea name="review" cols="62" rows="3" placeholder="$$$Напишите сюда отзыв" required></textarea>  <!--input-->
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">$$$Добавить отзыв</button>  <!--input-->
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">$$$Close</button>
                </div>
            </form>
        </div>
    </div>
</div>
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
                <form action="Controller" method="post">
                    <input type="hidden" name="method" value = "dispatcher"/>
                    <input type="hidden" name="action" value="cancelOrder"/>
                    <input type="hidden" name="orderId" value="${order.orderId}"/>
                    <button type="submit">$$$Отменить</button>
                </form>
            </c:when>
            <c:when test = "${order.orderStatus eq 'accepted'}">
                <button onclick="setOrder(<c:out value="${order.orderId}"/>, <c:out value="${order.price}"/>)" type="button" class="btn btn-primary" data-toggle="modal" data-target="#payModal">
                    $$$Оплатить
                </button>
            </c:when>
            <c:when test="${order.orderStatus eq 'completed'}">
                <button onclick="setTaxi(<c:out value="${order.taxi.id}"/>)" type="button" class="btn btn-primary" data-toggle="modal" data-target="#reviewModal">
                    $$$Добавить отзыв
                </button>
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
