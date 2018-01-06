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
    <link href="https://fonts.googleapis.com/css?family=Anton" rel="stylesheet">
    <link rel="stylesheet" href="../../css/common.css"/>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCWVlbCzAS1kedMyyEjnnASz9vwaIjOmp8"></script>
    <script>
        <%@include file="../../js/getCarAjax.js"%>
        <%@include file="/js/formValidator.js"%>
    </script>
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
    <a style="font-family: 'Anton', sans-serif;" class="navbar-brand" href="Controller?method=signManager&action=goHomePage">TAXI</a>
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=dispatcher&action=getAllOrders">$$$Архив заказов</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="Controller?method=userManager&action=getTaxiList">$$$Таксисты</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=userManager&action=getClientList">$$$Клиенты</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?method=taxis&action=getCarList">$$$Автомобили</a>
        </li>
    </ul>

    <div class="btn-group" role="group">
        <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            &&&Dropdown
        </button>
        <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
            <a class="dropdown-item" href="Controller?method=localization&local=ru&page=Controller&#63;method&#61;userManager&amp;action&#61;getTaxiList&amp;page&#61;1">${rusButton}</a>
            <a class="dropdown-item" href="Controller?method=localization&local=en&page=Controller&#63;method&#61;userManager&amp;action&#61;getTaxiList&amp;page&#61;1">${engButton}</a>
        </div>
        <div class="logOutMenu">
            <span class="welcomeUser">$$$Здравствуйте Администратор</span>
            <a href="Controller?method=signManager&action=logOut">
                <button type="button" class="btn btn-light">$$$LogOut</button>
            </a>
        </div>
    </div>
</nav>
<br/>
<table class="table table-striped table-dark">
    <thead class="thead-dark">
    <tr class="tr-text">
        <th>$$$taxiID</th>
        <th>$$$login</th>
        <th>$$$firstName</th>
        <th>$$$LastName</th>
        <th>$$$Number</th>
        <th>$$$carName</th>
        <th>$$$carColour</th>
        <th>$$$Status</th>
        <th>$$$banStatus</th>
    </tr>
    </thead>
    <c:forEach var="taxi" items="${requestScope.pageTaxiList}">
        <tr class="tr-text">
            <td>${taxi.id}</td>
            <td>${taxi.login}</td>
            <td>${taxi.firstName}</td>
            <td>${taxi.lastName}</td>
            <td>${taxi.car.number}
                <button onclick="getCarList('change');changeTaxiId(${taxi.id});" type="button" class="btn btn-light" data-toggle="modal" data-target="#changeCarModal">
                    $$$Поменять авто
                </button>
            </td>
            <td>${taxi.car.name}</td>
            <td>${taxi.car.colour}</td>
            <td>${taxi.availableStatus}</td>
            <td>
                <form action="Controller" method="post">
                    <input type="hidden" name="method" value = "userManager"/>
                    <input type="hidden" name="action" value="changeBanStatus"/>
                    <input type="hidden" name="id" value="${taxi.id}"/>
                    <input type="hidden" name="banStatus" value="${taxi.banStatus}"/>
                    <input type="hidden" name="role" value="${taxi.role}"/>
                    <button class="btn btn-light" type="submit">
                        <c:choose>
                            <c:when test="${!taxi.banStatus}">
                                $$$Забанить
                            </c:when>
                            <c:otherwise>
                                $$$Разбанить
                            </c:otherwise>
                        </c:choose>
                    </button>
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


<div class="modal fade" id="changeCarModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form onsubmit="check()"  action="Controller" method="post">
                <input type="hidden" name="method" value="userManager"/>  <!--Iinput--->
                <input type="hidden" name="action" value="changeTaxiCar"/>  <!--input-->
                <div class="modal-header">
                    <h5 class="modal-title" id="changeCarModalLabel">$$$Modal title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="changeCheckedCar" name="checkedCarNumber"/>
                    <input type="hidden" id="changeTaxiId" name="changeTaxiId"/>
                    <div class="cars"></div>
                </div>
                <div class="modal-footer">
                    <button  type="submit" class="btn btn-primary">$$$Поменять</button>  <!--input-->
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">$$$Close</button>
                </div>
            </form>
        </div>
    </div>
</div>


<div class="modal fade" id="regTaxiModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form onsubmit="check()"  action="Controller" method="post">
                <input type="hidden" name="method" value="signManager"/>  <!--Iinput--->
                <input type="hidden" name="action" value="registration"/>  <!--input-->
                <div class="modal-header">
                    <h5 class="modal-title" id="regTaxiModalLabel">$$$Modal title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <span class="label-line"><label for="login">$$$логин</label></span><input type="text" id="login" name="login" required/><br/>
                    <span class="label-line"><label for="pass1">$$$пароль</label></span><input type="text" id="pass1" name="password" required/><br/>
                    <span class="label-line"><label for="pass2">$$$повторите пароль</label></span><input type="text" id="pass2" name="password" required/><br/>
                    <span class="label-line"><label for="name">$$$имя</label></span><input type="text" id="name" name="name" required><br/>
                    <span class="label-line"><label for="surname">$$$фамилия</label></span><input type="text" id="surname" name="surname" required/><br/>
                    <input type="hidden" name="role" value="taxi"/>
                    <input type="hidden" id="regCheckedCar" name="checkedCarNumber"/>
                    <div class="cars"></div>
                </div>
                <div class="modal-footer">
                    <button onclick="validatePassword()" type="submit" class="btn btn-primary">$$$Зарегистрировать</button>  <!--input-->
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">$$$Close</button>
                </div>
            </form>
        </div>
    </div>
</div>

<button onclick="getCarList('reg');" type="button" class="btn btn-primary" data-toggle="modal" data-target="#regTaxiModal">
    $$$Зарегистрировать таксиста
</button>

<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
