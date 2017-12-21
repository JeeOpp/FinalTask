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
    <title>$$$Taxi</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <script type="text/javascript">
        <%@include file="/WEB-INF/formValidator.js"%>
    </script>

</head>



<body>
<jsp:useBean id="user" class="entity.Taxi" scope="session"/>
<span>$$$Здравствуйте ${user.firstName} ${user.lastName}</span>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="signManager"/>
    <input type="hidden" name="action" value="logOut"/>
    <input type="submit" value="$$$Выйти"/>
</form>

<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="ru"/>
    <input type="hidden" name="page" value="Controller?method=userManager&action=preProfile"/>
    <input type="submit" value="${rusButton}">
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value="localization"/>
    <input type="hidden" name="local" value="en"/>
    <input type="hidden" name="page" value="Controller?method=userManager&action=preProfile"/>
    <input type="submit" value="${engButton}">
</form>

<!--///////////////////////////////////////////////////////////////////////////////// -->
<form action="Controller" method="post">
    <input type="hidden" name="method" value = "dispatcher"/>
    <input type="hidden" name="action" value="getTaxiOrders"/>
    <button type="submit">$$$Заказы</button>
</form>
<form action="Controller" method="post">
    <input type="hidden" name="method" value = "userManager"/>
    <input type="hidden" name="action" value="preProfile"/>
    <button type="submit">$$$Мой профиль</button>
</form>
<!--/////////////////////////////////////////////////////////////////////////////////////-->
<ul>
    <c:forEach var="review" items="${requestScope.userReviews}">
    <li><c:out value="${review.client.id} ${review.client.firstName} ${review.client.lastName} ${review.comment}"/><li>
    </c:forEach>
</ul>

Ваши данные
<ul>
    <li>${user.firstName}</li>
    <li>${user.lastName}</li>
    <li>${user.login}</li>
    <li>${user.car.number} - ${user.car.name}</li>
</ul>
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#reviewModal">
    $$$сменить пароль
</button>


<div class="modal fade" id="reviewModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="Controller" method="post">              <!--ФОРМА НА ОТПРАВКУ ОТзЫВА-->
                <input type="hidden" name="method" value="profile"/>  <!--Iinput--->
                <input type="hidden" name="action" value="changePassword"/>  <!--input-->
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">$$$Modal title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <label for="previousPass">$$$текущий пароль</label><input type="text" id="previousPass" name="previousPass" required/><br/>
                    <label for="pass1">$$$новый пароль</label><input type="text" id="pass1" name="newPass" required/><br/>
                    <label for="pass2">$$$повторите пароль</label><input type="text" id="pass2" name="newPass" required>
                </div>
                <div class="modal-footer">
                    <button onclick="validatePassword()" type="submit" class="btn btn-primary">$$$Cменить пароль</button>  <!--input-->
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">$$$Close</button>
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
