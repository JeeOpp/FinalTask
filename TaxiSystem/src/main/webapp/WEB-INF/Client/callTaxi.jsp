<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 05.12.2017
  Time: 22:50
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
        <fmt:message bundle="${loc}" key="local.client.callTaxi.title" var="title"/>
        <fmt:message bundle="${loc}" key="local.client.nav.callTaxi" var="navCallTaxi"/>
        <fmt:message bundle="${loc}" key="local.client.nav.myOrders" var="navMyOrders"/>
        <fmt:message bundle="${loc}" key="local.client.nav.myProfile" var="navMyProfile"/>
        <fmt:message bundle="${loc}" key="local.all.localization" var="languages"/>
        <fmt:message bundle="${loc}" key="local.all.welcome" var="welcome"/>
        <fmt:message bundle="${loc}" key="local.all.logOut" var="logOut"/>
        <fmt:message bundle="${loc}" key="local.client.callTaxi.prePrice" var="prePrice"/>
        <fmt:message bundle="${loc}" key="local.client.callTaxi.availableBonuses" var="availableBonuses"/>
        <fmt:message bundle="${loc}" key="local.client.callTaxi" var="callTaxi"/>
        <fmt:message bundle="${loc}" key="local.client.callTaxi.availableTaxi" var="availableTaxiText"/>
        <fmt:message bundle="${loc}" key="local.client.callTaxi.bonusCount" var="bonusCount"/>

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
    <jsp:useBean id="user" class="entity.Client" scope="session"/>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
        <a style="font-family: 'Anton', sans-serif;" class="navbar-brand" href="Controller?method=signManager&action=goHomePage">TAXI</a>
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link active" href="Controller?method=dispatcher&action=preOrder">${navCallTaxi}</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Controller?method=dispatcher&action=getClientOrders">${navMyOrders}</a>
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
                <a class="dropdown-item" href="Controller?method=localization&local=ru&page=Controller&#63;method&#61;dispatcher&amp;action&#61;preOrder">${rusButton}</a>
                <a class="dropdown-item" href="Controller?method=localization&local=en&page=Controller&#63;method&#61;dispatcher&amp;action&#61;preOrder">${engButton}</a>
            </div>
            <div class="logOutMenu">
                <span class="welcomeUser">${welcome} ${user.firstName} ${user.lastName}</span>
                <a href="Controller?method=signManager&action=logOut">
                    <button type="button" class="btn btn-light">${logOut}</button>
                </a>
            </div>
        </div>
    </nav>
    <!--/////////////////////////////////////////////////////////-->
    <div class="my-flex-container">
        <form onsubmit="check();" action="Controller" method="post">
            <div class="my-flex-block inner-flex-container">
                <div class="inner-flex-box">
                    <input type="hidden" name="method" value = "dispatcher"/>
                    <input type="hidden" name="action" value="callTaxi"/>
                    <input id="srcCoord" type="hidden" name="sourceCoordinate" value="53.90453,27.56152"/>
                    <input id="dstCoord" type="hidden" name="destinyCoordinate" value="53.90453,27.56152"/>

                    <span class="q-text">${availableTaxiText}</span>

                    <ul class="taxiList">
                        <c:forEach var="taxi" items="${requestScope.availableTaxiList}">
                            <li class="taxiItem">
                                <input type="radio" id="${taxi.id}" name="taxi" value="${taxi.id}" checked>
                                <label for="${taxi.id}">${taxi.firstName} ${taxi.lastName} ${taxi.car.name} ${taxi.car.number}</label>
                            </li>
                        </c:forEach>
                    </ul>
                    <input type="hidden" name="checkedTaxiId"/>
                </div>
                <div class="inner-flex-box">
                    <span class="pre-price">${prePrice}: <span class="price" id="priceView">0</span></span>
                </div>
                <div class="inner-flex-box">
                    <label class="pre-price" for="bonus">${availableBonuses} ${user.bonusPoints}   </label>
                    <input id="bonus" type="number" name="bonus" min="0" max="${user.bonusPoints}" placeholder="0"/>
                    <input id="price" type="hidden" name="price" value="0"><br/>
                    <span class="q-text">${bonusCount}</span>
                </div>
                <div class="inner-flex-box">
                    <button class="big-green-button" type="submit" onclick="checkBonus();">${callTaxi}</button>
                </div>
            </div>
        </form>
        <div id="clientMap" class="map my-flex-block"></div>
    </div>

    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
    </body>
</html>
