<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 12.11.2017
  Time: 19:06
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
            <a class="nav-link" href="Controller?method=dispatcher&action=getTaxiOrders">$$$Заказы</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=userManager&action=preProfile">$$$Мой профиль</a>
        </li>
    </ul>

    <div class="btn-group" role="group">
        <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            &&&Dropdown
        </button>
        <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
            <a class="dropdown-item" href="Controller?method=localization&local=ru&page=WEB-INF/Taxi/main.jsp">${rusButton}</a>
            <a class="dropdown-item" href="Controller?method=localization&local=en&page=WEB-INF/Taxi/main.jsp">${engButton}</a>
        </div>
        <div class="logOutMenu">
            <span class="welcomeUser">$$$Здравствуйте ${user.firstName} ${user.lastName}</span>
            <a href="Controller?method=signManager&action=logOut">
                <button type="button" class="btn btn-light">$$$LogOut</button>
            </a>
        </div>
    </div>
</nav>

<div class="my-flex-2page pageHeight">
    <div class="ruleListDiv">
        <div class="ruleListTitle">$$$Водителю  автомобиля-такси  запрещается:</div>
        <div class="rileListText">
            - оставлять  автомобиль и установленное на него оборудование фирмы  без  присмотра;<br/>
            - перевозить  большее  количество  пассажиров, чем  положено  по   техническим характеристикам автомобиля  и не пристегнутых ремнями безопасности;<br/>
            - перевозить детей  до  12  лет без специального удерживающего устройства;<br/>
            - передавать  управление  автомобилем  кому  бы  то  ни  было;<br/>
            - превышать  установленную  скорость  движения;<br/>
            - подавать автомобиль клиенту в темное время суток без включенного плафона такси;<br/>
        </div>
    </div>
    <div class="wightPage">
        <a href="Controller?method=dispatcher&action=getTaxiOrders">
            <button class="big-green-button">$$$Поехали</button>
        </a>
    </div>
</div>

<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
