<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 06.12.2017
  Time: 0:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="mytag" uri="/WEB-INF/tld/taglib.tld" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.all.rusButton" var="rusButton"/>
    <fmt:message bundle="${loc}" key="local.all.engButton" var="engButton"/>
    <fmt:message bundle="${loc}" key="local.client.orders.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.client.nav.callTaxi" var="navCallTaxi"/>
    <fmt:message bundle="${loc}" key="local.client.nav.myOrders" var="navMyOrders"/>
    <fmt:message bundle="${loc}" key="local.client.nav.myProfile" var="navMyProfile"/>
    <fmt:message bundle="${loc}" key="local.all.localization" var="languages"/>
    <fmt:message bundle="${loc}" key="local.all.welcome" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.all.logOut" var="logOut"/>
    <fmt:message bundle="${loc}" key="local.client.orders.payment" var="paymentTitle"/>
    <fmt:message bundle="${loc}" key="local.client.orders.toPay" var="toPay"/>
    <fmt:message bundle="${loc}" key="local.client.orders.payNow" var="payNow"/>
    <fmt:message bundle="${loc}" key="local.client.orders.reviewTitle" var="reviewTitle"/>
    <fmt:message bundle="${loc}" key="local.client.orders.giveReview" var="giveReview"/>
    <fmt:message bundle="${loc}" key="local.all.close" var="close"/>
    <fmt:message bundle="${loc}" key="local.all.order.orderId" var="orderId"/>
    <fmt:message bundle="${loc}" key="local.all.order.source" var="orderSrc"/>
    <fmt:message bundle="${loc}" key="local.all.order.destiny" var="orderDst"/>
    <fmt:message bundle="${loc}" key="local.all.order.price" var="orderPrice"/>
    <fmt:message bundle="${loc}" key="local.all.order.orderStatus" var="orderStatus"/>
    <fmt:message bundle="${loc}" key="local.all.order.action" var="orderAction"/>
    <fmt:message bundle="${loc}" key="local.all.car.number" var="carNumber"/>
    <fmt:message bundle="${loc}" key="local.all.car.name" var="carName"/>
    <fmt:message bundle="${loc}" key="local.client.orders.cancelOrder" var="cancelOrder"/>
    <fmt:message bundle="${loc}" key="local.client.main.placeholderText" var="text"/>

    <title>${title}</title>
    <link href="https://fonts.googleapis.com/css?family=Anton" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCWVlbCzAS1kedMyyEjnnASz9vwaIjOmp8"></script>
    <script>
        <%@include file="../../js/support.js"%>
    </script>
    <link rel="stylesheet " href="../../css/common.css"/>
</head>
<body>
<jsp:useBean id="user" class="entity.User" scope="session"/>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
    <a style="font-family: 'Anton', sans-serif;" class="navbar-brand" href="Controller?method=signManager&action=goHomePage">TAXI</a>
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=dispatcher&action=preOrder">${navCallTaxi}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="Controller?method=dispatcher&action=getClientOrders">${navMyOrders}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=userManager&action=preProfile">${navMyProfile}</a>
        </li>
    </ul>

    <div class="btn-group" role="group">
        <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            ${languages}
        </button>
        <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
            <a class="dropdown-item" href="Controller?method=localization&local=ru&page=Controller&#63;method&#61;dispatcher&amp;action&#61;getClientOrders">${rusButton}</a>
            <a class="dropdown-item" href="Controller?method=localization&local=en&page=Controller&#63;method&#61;dispatcher&amp;action&#61;getClientOrders">${engButton}</a>
        </div>
        <div class="logOutMenu">
            <span class="welcomeUser">${welcome} ${user.firstName} ${user.lastName}</span>
            <a href="Controller?method=signManager&action=logOut">
                <button type="button" class="btn btn-light">${logOut}</button>
            </a>
        </div>
    </div>
</nav>

<!-- ВСплывающее окно -->
<div class="modal fade" id="payModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="Controller" method="post">              <!--Оплата не работает, так что прост -->
                <input type="hidden" name="method" value="dispatcher">   <!--input-->
                <input type="hidden" name="action" value="payOrder">  <!--input-->
                <input type="hidden" name="orderId">                    <!--input-->
                <div class="modal-header">
                    <h5 class="modal-title" id="payModalLabel">${paymentTitle}</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span class="closeModal" aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <label for="payText">${toPay}:</label>
                    <input type="text" id="payText" name="payText" readonly>  <!--input-->
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-light">${payNow}</button>  <!--input-->
                    <button type="button" class="btn btn-dark" data-dismiss="modal">${close}</button>
                </div>
            </form>
        </div>
    </div>
</div>


<div class="modal fade" id="reviewModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="Controller" method="post">              <!--ФОРМА НА ОТПРАВКУ ОТзЫВА-->
                <input type="hidden" name="method" value="feedback"/>   <!--input-->
                <input type="hidden" name="action" value="writeReview"/>  <!--input-->
                <input type="hidden" name="taxiId"/>                    <!--input-->
                <input id="orderId" type="hidden" name="orderId"/>
                <div class="modal-header">
                    <h5 class="modal-title" id="reviewModalLabel">${reviewTitle}</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span class="closeModal" aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <textarea name="review" cols="62" rows="3" placeholder="${text}" required></textarea>  <!--input-->
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-light">${giveReview}</button>  <!--input-->
                    <button type="button" class="btn btn-dark" data-dismiss="modal">${close}</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!--/////////////////////////////////////////////////////////-->
<br/>
<table class="table table-striped table-dark">
    <thead>
        <tr class="tr-text">
            <th>${orderId}</th>
            <th>${orderSrc}</th>
            <th>${orderDst}</th>
            <th>${carNumber}</th>
            <th>${carName}</th>
            <th>${orderPrice}</th>
            <th>${orderStatus}</th>
            <th>${orderAction}</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${requestScope.clientOrder}">
        <tr class="tr-text">
            <td>${order.orderId}</td>
            <td>${order.sourceCoordinate}</td>
            <td>${order.destinyCoordinate}</td>
            <td>${order.taxi.car.number}</td>
            <td>${order.taxi.car.name}</td>
            <td>${order.price}</td>
            <mytag:orderColour orderStatus="${order.orderStatus}" locale="${sessionScope.local}"/>
            <td><c:choose>
                <c:when test = "${order.orderStatus eq 'processed'}">
                    <form action="Controller" method="post">
                        <input type="hidden" name="method" value = "dispatcher"/>
                        <input type="hidden" name="action" value="cancelOrder"/>
                        <input type="hidden" name="orderId" value="${order.orderId}"/>
                        <button class="simple-white-button" type="submit">${cancelOrder}</button>
                    </form>
                </c:when>
                <c:when test = "${order.orderStatus eq 'accepted'}">
                    <button onclick="setOrder(${order.orderId},${order.price})" type="button" class="simple-white-button" data-toggle="modal" data-target="#payModal">
                        ${payNow}
                    </button>
                </c:when>
                <c:when test="${order.orderStatus eq 'completed'}">
                    <button onclick="setReview(${order.orderId},${order.taxi.id})" type="button" class="simple-white-button" data-toggle="modal" data-target="#reviewModal">
                        ${giveReview}
                    </button>
                </c:when>
            </c:choose></td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
