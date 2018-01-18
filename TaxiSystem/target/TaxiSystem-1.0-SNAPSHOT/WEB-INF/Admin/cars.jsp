<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 03.01.2018
  Time: 2:56
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
    <fmt:message bundle="${loc}" key="local.admin.welcome" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.all.logOut" var="logOut"/>
    <fmt:message bundle="${loc}" key="local.all.close" var="close"/>
    <fmt:message bundle="${loc}" key="local.all.car.number" var="carNumber"/>
    <fmt:message bundle="${loc}" key="local.all.car.name" var="carName"/>
    <fmt:message bundle="${loc}" key="local.all.car.colour" var="colour"/>
    <fmt:message bundle="${loc}" key="local.admin.cars.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.admin.cars.addCarTitle" var="addCar"/>
    <fmt:message bundle="${loc}" key="local.admin.cars.addCarButton" var="addCarButton"/>
    <fmt:message bundle="${loc}" key="local.admin.cars.deleteCar" var="deleteCar"/>
    <fmt:message bundle="${loc}" key="local.all.car.action" var="action"/>
    <fmt:message bundle="${loc}" key="local.admin.nav.orders" var="orders"/>
    <fmt:message bundle="${loc}" key="local.admin.nav.taxi" var="taxi"/>
    <fmt:message bundle="${loc}" key="local.admin.nav.client" var="client"/>
    <fmt:message bundle="${loc}" key="local.admin.nav.cars" var="cars"/>
    <title>${title}</title>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCWVlbCzAS1kedMyyEjnnASz9vwaIjOmp8"></script>
    <link href="https://fonts.googleapis.com/css?family=Anton" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
    <link rel="stylesheet" href="../../css/common.css"/>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
    <a style="font-family: 'Anton', sans-serif;" class="navbar-brand" href="Controller?method=signManager&action=goHomePage">TAXI</a>
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=dispatcher&action=getAllOrders">${orders}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=userManager&action=getTaxiList">${taxi}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=userManager&action=getClientList">${client}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="Controller?method=taxis&action=getCarList">${cars}</a>
        </li>
    </ul>

    <div class="btn-group" role="group">
        <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            ${languages}
        </button>
        <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
            <a class="dropdown-item" href="Controller?method=localization&local=ru&page=Controller&#63;method&#61;taxis&amp;action&#61;getCarList">${rusButton}</a>
            <a class="dropdown-item" href="Controller?method=localization&local=en&page=Controller&#63;method&#61;taxis&amp;action&#61;getCarList">${engButton}</a>
        </div>
        <div class="logOutMenu">
            <span class="welcomeUser">${welcome}</span>
            <a href="Controller?method=signManager&action=logOut">
                <button type="button" class="btn btn-light">${logOut}</button>
            </a>
        </div>
    </div>
</nav>
<br/>
<table class="table table-striped table-dark">
    <thead class="thead-dark">
    <tr class="tr-text">
        <th>${carNumber}</th>
        <th>${carName}</th>
        <th>${colour}</th>
        <th>${action}</th>
    </tr>
    </thead>
    <c:forEach var="car" items="${requestScope.carList}">
        <tr class="tr-text">
            <td>${car.number}</td>
            <td>${car.name}</td>
            <td>${car.colour}</td>
            <td>
                <form action="Controller" method="post">
                    <input type="hidden" name="method" value = "taxis"/>
                    <input type="hidden" name="action" value="removeCar"/>
                    <input type="hidden" name="carNumber" value="${car.number}"/>
                    <button class="btn btn-light" type="submit">${deleteCar}</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>


<div class="modal fade" id="addCar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form  action="Controller" method="post">
                <input type="hidden" name="method" value="taxis"/>  <!--Iinput--->
                <input type="hidden" name="action" value="addCar"/>  <!--input-->
                <div class="modal-header">
                    <h5 class="modal-title" id="regTaxiModalLabel">${addCar}</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <span class="label-line"><label for="carNumber">${carNumber}</label></span><input type="text" id="carNumber" name="carNumber" required/><br/>
                    <span class="label-line"><label for="carName">${carName}</label></span><input type="text" id="carName" name="carName" required><br/>
                    <span class="label-line"><label for="carColour">${colour}</label></span><input type="text" id="carColour" name="carColour" required/><br/>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-light">${addCarButton}</button>  <!--input-->
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">${close}</button>
                </div>
            </form>
        </div>
    </div>
</div>


<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addCar">
    ${addCarButton}
</button>

<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
