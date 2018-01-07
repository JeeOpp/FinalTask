<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 12.11.2017
  Time: 19:05
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
    <fmt:message bundle="${loc}" key="local.client.main.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.client.nav.callTaxi" var="navCallTaxi"/>
    <fmt:message bundle="${loc}" key="local.client.nav.myOrders" var="navMyOrders"/>
    <fmt:message bundle="${loc}" key="local.client.nav.myProfile" var="navMyProfile"/>
    <fmt:message bundle="${loc}" key="local.all.localization" var="languages"/>
    <fmt:message bundle="${loc}" key="local.all.welcome" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.all.logOut" var="logOut"/>
    <fmt:message bundle="${loc}" key="local.client.main.orderNow" var="orderNow"/>
    <fmt:message bundle="${loc}" key="local.client.main.carousel.first" var="carouselFirst"/>
    <fmt:message bundle="${loc}" key="local.client.main.carousel.second" var="carouselSecond"/>
    <fmt:message bundle="${loc}" key="local.client.main.carousel.third" var="carouselThird"/>
    <fmt:message bundle="${loc}" key="local.client.main.carousel.previous" var="previous"/>
    <fmt:message bundle="${loc}" key="local.client.main.carousel.next" var="next"/>
    <title>${title}</title>
    <link href="https://fonts.googleapis.com/css?family=Anton" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
    <link rel="stylesheet " href="../../css/common.css"/>
</head>

<body>
    <jsp:useBean id="user" class="entity.Client" scope="session"/>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
        <a style="font-family: 'Anton', sans-serif;" class="navbar-brand" href="Controller?method=signManager&action=goHomePage">TAXI</a>
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="Controller?method=dispatcher&action=preOrder">${navCallTaxi}</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Controller?method=dispatcher&action=getClientOrders">${navMyOrders}</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Controller?method=userManager&action=preProfile">${navMyOrders}</a>
            </li>
        </ul>

        <div class="btn-group" role="group">
            <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                ${languages}
            </button>
            <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                <a class="dropdown-item" href="Controller?method=localization&local=ru&page=WEB-INF/Client/main.jsp">${rusButton}</a>
                <a class="dropdown-item" href="Controller?method=localization&local=en&page=WEB-INF/Client/main.jsp">${engButton}</a>
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
    <!--/////////////////////////////////////////////////////////-->
    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div  class="carousel-inner">
            <div class="carousel-item active">
                <img class="d-block w-100" src="https://www.walldevil.com/wallpapers/a26/wallpapers-sites-top-gauntlet-aston-background.jpg" alt="Third slide">
                <div class="carousel-caption d-none d-md-block">
                    <a href="Controller?method=dispatcher&action=preOrder" class="callTaxiButton">${orderNow}</a>
                    <h5>${carouselFirst}</h5>
                </div>
            </div>
            <div class="carousel-item">
                <img class="d-block w-100" src="https://w-dog.ru/wallpapers/2/9/337529567428389/ulica-nochnoj-gorod-avtomobili-fonari.jpg" alt="Second slide">
                <div class="carousel-caption d-none d-md-block">
                    <a href="Controller?method=dispatcher&action=preOrder" class="callTaxiButton">${orderNow}</a>
                    <h5>${carouselSecond}</h5>
                </div>
            </div>
            <div class="carousel-item">
                <img class="d-block w-100" src="https://www.belnovosti.by/sites/default/files/article/17-04-2017/taxi.jpg" alt="First slide">
                <div class="carousel-caption d-none d-md-block">
                    <a href="Controller?method=dispatcher&action=preOrder" class="callTaxiButton">${orderNow}</a>
                    <h5>${carouselThird}</h5>
                </div>
            </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">${previous}</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">${next}</span>
        </a>
    </div>


    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
