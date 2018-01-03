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
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCWVlbCzAS1kedMyyEjnnASz9vwaIjOmp8"></script>
    <script>
        <%@include file="../../js/getCarAjax.js"%>
        <%@include file="/js/formValidator.js"%>
    </script>
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
    <input type="hidden" name="page" value="Controller?method=userManager&action=getTaxiList&page=1"/>
    <input type="submit" value="${rusButton}">
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="en"/>
    <input type="hidden" name="page" value="Controller?method=userManager&action=getTaxiList$page=1"/>
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
                        <input type="hidden" name="action" value="changeBanStatus"/>
                        <input type="hidden" name="id" value="${taxi.id}"/>
                        <input type="hidden" name="banStatus" value="${taxi.banStatus}"/>
                        <input type="hidden" name="role" value="${taxi.role}"/>
                        <button type="submit">
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
                    <label for="login">$$$логин</label><input type="text" id="login" name="login" required/><br/>
                    <label for="pass1">$$$пароль</label><input type="text" id="pass1" name="password" required/><br/>
                    <label for="pass2">$$$повторите пароль</label><input type="text" id="pass2" name="password" required/><br/>
                    <label for="name">$$$имя</label><input type="text" id="name" name="name" required><br/>
                    <label for="surname">$$$фамилия</label><input type="text" id="surname" name="surname" required/><br/>
                    <input type="hidden" name="role" value="taxi"/>
                    <input type="hidden" name="checkedCarNumber"/>
                    <div id="cars"></div>
                </div>
                <div class="modal-footer">
                    <button onclick="validatePassword()" type="submit" class="btn btn-primary">$$$Зарегистрировать</button>  <!--input-->
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">$$$Close</button>
                </div>
            </form>
        </div>
    </div>
</div>

<button onclick="getCarList()" type="button" class="btn btn-primary" data-toggle="modal" data-target="#regTaxiModal">
    $$$Зарегистрировать таксиста
</button>

<p id="ajax">!!!</p>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>
