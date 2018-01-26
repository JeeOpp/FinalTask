<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 15.12.2017
  Time: 1:27
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
    <fmt:message bundle="${loc}" key="local.client.orders.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.client.nav.callTaxi" var="navCallTaxi"/>
    <fmt:message bundle="${loc}" key="local.client.nav.myOrders" var="navMyOrders"/>
    <fmt:message bundle="${loc}" key="local.client.nav.myProfile" var="navMyProfile"/>
    <fmt:message bundle="${loc}" key="local.all.localization" var="languages"/>
    <fmt:message bundle="${loc}" key="local.all.welcome" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.all.logOut" var="logOut"/>
    <fmt:message bundle="${loc}" key="local.all.changePassword" var="changePasswordButton"/>
    <fmt:message bundle="${loc}" key="local.all.currentPass" var="currentPass"/>
    <fmt:message bundle="${loc}" key="local.all.newPassword" var="newPass"/>
    <fmt:message bundle="${loc}" key="local.all.repeatPass" var="repeatPass"/>
    <fmt:message bundle="${loc}" key="local.all.logOut" var="logOut"/>
    <fmt:message bundle="${loc}" key="local.client.profile.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.client.profile.myData" var="myData"/>
    <fmt:message bundle="${loc}" key="local.client.profile.yourReview" var="yourReview"/>
    <fmt:message bundle="${loc}" key="local.client.profile.changePassTitle" var="changePass"/>
    <fmt:message bundle="${loc}" key="local.all.user.name" var="userName"/>
    <fmt:message bundle="${loc}" key="local.all.user.surname" var="userSurname"/>
    <fmt:message bundle="${loc}" key="local.all.user.login" var="userLogin"/>
    <fmt:message bundle="${loc}" key="local.all.client.bonuses" var="bonuses"/>
    <fmt:message bundle="${loc}" key="local.all.close" var="close"/>
    <fmt:message bundle="${loc}" key="local.forms.title.password" var="validPassword"/>


    <title>${title}</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Anton" rel="stylesheet">
    <script type="text/javascript">
        <%@include file="/js/formValidator.js"%>
    </script>
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
            <a class="nav-link active" href="Controller?method=userManager&action=preProfile">${navMyProfile}</a>
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


<br/>
<div class="my-flex-2page">
    <div class="profileDiv">
        <span class="titleDiv">${myData}</span>
        <ul class="list-group">
            <li class="list-group-item list-group-item-dark"><span class="tr-text"><b>${userName}: </b></span>${user.firstName}</li>
            <li class="list-group-item list-group-item-dark"><span class="tr-text"><b>${userSurname}: </b></span>${user.lastName}</li>
            <li class="list-group-item list-group-item-dark"><span class="tr-text"><b>${userLogin}: </b></span>${user.login}</li>
            <li class="list-group-item list-group-item-dark"><span class="tr-text"><b>E-Mail: </b></span>${user.mail}</li>
            <li class="list-group-item list-group-item-dark"><span class="tr-text"><b>${bonuses}: </b></span>${user.bonusPoints}</li>
        </ul>
        <br/>
        <button type="button" class="btn btn-lg btn-secondary" data-toggle="modal" data-target="#reviewModal">
            ${changePasswordButton}
        </button>
    </div>

    <div class="wightPage">
        <div class="titleDiv">${yourReview}</div>
        <ul class="list-group">
            <c:forEach var="review" items="${requestScope.userReviews}">
                <li class="list-group-item list-group-item-dark"><b>${review.taxi.firstName} ${review.taxi.lastName}</b>: ${review.comment} </li>
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
                    <h5 class="modal-title" id="exampleModalLabel">${yourReview}</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span class="closeModal" aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <span class="label-line"><label for="previousPass">${currentPass}</label></span><input type="text" id="previousPass" name="previousPass" required/><br/>
                    <span class="label-line"><label for="pass1">${newPass}</label></span><input type="text" id="pass1" name="newPass" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}$" title="${validPassword}" required/><br/>
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
