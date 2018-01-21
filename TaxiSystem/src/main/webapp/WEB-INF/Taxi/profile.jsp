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
    <fmt:message bundle="${loc}" key="local.all.localization" var="languages"/>
    <fmt:message bundle="${loc}" key="local.all.welcome" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.all.logOut" var="logOut"/>
    <fmt:message bundle="${loc}" key="local.taxi.profile.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.taxi.nav.orders" var="orders"/>
    <fmt:message bundle="${loc}" key="local.taxi.nav.profile" var="myProfile"/>
    <fmt:message bundle="${loc}" key="local.all.changePassword" var="changePasswordButton"/>
    <fmt:message bundle="${loc}" key="local.taxi.profile.myData" var="myData"/>
    <fmt:message bundle="${loc}" key="local.taxi.profile.yourReview" var="reviewOnYou"/>
    <fmt:message bundle="${loc}" key="local.taxi.profile.changePassTitle" var="changePassTitle"/>
    <fmt:message bundle="${loc}" key="local.all.currentPass" var="currentPass"/>
    <fmt:message bundle="${loc}" key="local.all.newPassword" var="newPass"/>
    <fmt:message bundle="${loc}" key="local.all.repeatPass" var="repeatPass"/>
    <fmt:message bundle="${loc}" key="local.all.user.name" var="userName"/>
    <fmt:message bundle="${loc}" key="local.all.user.surname" var="userSurname"/>
    <fmt:message bundle="${loc}" key="local.all.user.login" var="userLogin"/>
    <fmt:message bundle="${loc}" key="local.all.car.car" var="car"/>
    <fmt:message bundle="${loc}" key="local.all.close" var="close"/>

    <title>${title}</title>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCWVlbCzAS1kedMyyEjnnASz9vwaIjOmp8"></script>
    <link href="https://fonts.googleapis.com/css?family=Anton" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
    <link rel="stylesheet " href="../../css/common.css"/>
    <script>
        <%@include file="../../js/clientMap.js"%>
        <%@include file="../../js/radioTaxi.js"%>
    </script>
</head>

<body>

<jsp:useBean id="user" class="entity.Taxi" scope="session"/>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
    <a style="font-family: 'Anton', sans-serif;" class="navbar-brand" href="Controller?method=signManager&action=goHomePage">TAXI</a>
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=dispatcher&action=getTaxiOrders">${orders}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="Controller?method=userManager&action=preProfile">${myProfile}</a>
        </li>
    </ul>

    <div class="btn-group" role="group">
        <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            ${languages}
        </button>
        <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
            <a class="dropdown-item" href="Controller?method=localization&local=ru&page=Controller&#63;method&#61;userManager&amp;action&#61;preProfile">${rusButton}</a>
            <a class="dropdown-item" href="Controller?method=localization&local=en&page=Controller&#63;method&#61;userManager&amp;action&#61;preProfile">${engButton}</a>
        </div>
        <div class="logOutMenu">
            <span class="welcomeUser">${welcome} ${user.firstName} ${user.lastName}</span>
            <a href="Controller?method=signManager&action=logOut">
                <button type="button" class="btn btn-light">${logOut}</button>
            </a>
        </div>
    </div>
</nav>
<!--/////////////////////////////////////////////////////////////////////////////////////-->
<br/>
<div class="my-flex-2page">
    <div class="profileDiv">
        <span class="titleDiv">${myData}</span>
        <ul class="list-group">
            <li class="list-group-item list-group-item-dark"><span class="tr-text">${userName}: </span>${user.firstName}</li>
            <li class="list-group-item list-group-item-dark"><span class="tr-text">${userSurname} </span>${user.lastName}</li>
            <li class="list-group-item list-group-item-dark"><span class="tr-text">${userLogin} </span>${user.login}</li>
            <li class="list-group-item list-group-item-dark"><span class="tr-text">${car} </span>${user.car.number} - ${user.car.name}</li>
        </ul>
        <br/>
        <button type="button" class="btn btn-lg btn-secondary" data-toggle="modal" data-target="#reviewModal">
            ${changePasswordButton}
        </button>
    </div>

    <div class="wightPage">
        <div class="titleDiv">${reviewOnYou}</div>
        <ul class="list-group">
            <c:forEach var="review" items="${requestScope.userReviews}">
                <li class="list-group-item list-group-item-dark"><b>${review.client.firstName} ${review.client.lastName}</b>: ${review.comment} </li>
            </c:forEach>
        </ul>
    </div>
</div>


<div class="modal fade" id="reviewModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="Controller" method="post">              <!--ФОРМА НА ОТПРАВКУ ОТзЫВА-->
                <input type="hidden" name="method" value="userManager"/>  <!--Iinput--->
                <input type="hidden" name="action" value="changePassword"/>  <!--input-->
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">${changePassTitle}</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span class="closeModal" aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <span class="label-line"><label for="previousPass">${currentPass}</label></span><input type="text" id="previousPass" name="previousPass" required/><br/>
                    <span class="label-line"><label for="pass1">${newPass}</label></span><input type="text" id="pass1" name="newPass" required/><br/>
                    <span class="label-line"><label for="pass2">${repeatPass}</label></span><input type="text" id="pass2" name="newPass" required>
                </div>
                <div class="modal-footer">
                    <button onclick="validatePassword()" type="submit" class="btn btn-light">${changePasswordButton}</button>  <!--input-->
                    <button type="button" class="btn btn-dark" data-dismiss="modal">${close}</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
