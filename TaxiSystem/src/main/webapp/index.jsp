<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 12.11.2017
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
    <meta charset="utf-8">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.all.rusButton" var="rusButton"/>
    <fmt:message bundle="${loc}" key="local.all.engButton" var="engButton"/>
    <fmt:message bundle="${loc}" key="local.index.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.index.logIn" var="logIn"/>
    <fmt:message bundle="${loc}" key="local.index.signUp" var="signUp"/>
    <fmt:message bundle="${loc}" key="local.authorization.authorizationButton" var="authButton"/>

    <title>${title}</title>
    <link href="https://fonts.googleapis.com/css?family=Anton" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
    <a style="font-family: 'Anton', sans-serif;" class="navbar-brand" href="index.jsp">TAXI</a>

    <div class="btn-group" role="group">
        <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            &&&Dropdown
        </button>
        <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
            <a class="dropdown-item" href="Controller?method=localization&local=ru&page=index.jsp">${rusButton}</a>
            <a class="dropdown-item" href="Controller?method=localization&local=en&page=index.jsp">${engButton}</a>
        </div>
    </div>
</nav>


<div class="form">
    <ul class="tab-group">
        <li class="tab active"><a href="#signup">$$$Sign Up</a></li>
        <li class="tab"><a href="#login">$$$Log In</a></li>
    </ul>
    <div class="tab-content">
        <div id="signup">
            <h1>$$$Sign Up for Free</h1>
            </form>
            <form action="Controller" method="post">
                <input type="hidden" name="method" value="signManager"/>
                <input type="hidden" name="action" value="registration"/>
                <input type="hidden" name="role" value="client"/>
                <div class="wrapped-row">
                    <div class="field-wrap">
                        <input type="text" required name="firstName" placeholder="$$$Имя" autocomplete="off"/>
                    </div>
                    <div class="field-wrap">
                        <input type="text" required name="lastName" placeholder="$$$Фамилия" autocomplete="off"/>
                    </div>
                </div>
                <div class="field-wrap">
                    <input type="text" required name="login"  placeholder="$$$Логин" autocomplete="off"/>
                </div>

                <div class="wrapped-row">
                    <div class="field-wrap">
                        <input type="password" required id="pass1" name="password"  placeholder="$$$Пароль" autocomplete="off"/>
                    </div>
                    <div class="field-wrap">
                        <input type="password" required id="pass2" name="password"  placeholder="$$$Пароль" autocomplete="off"/>
                    </div>
                </div>
                <button type="submit" onclick="validatePassword()" class="button button-block">$$$Get Started</button>
            </form>
        </div>
        <div id="login">
            <h1>$$$Welcome Back!</h1>
            <form action="Controller" method="post">
                <input type="hidden" name="method" value="signManager"/>
                <input type="hidden" name="action" value="authorization"/>
                <div class="field-wrap">
                    <input type="text" name="login" value="client" required/>
                </div>
                <div class="field-wrap">
                    <input type="password" name="password" value="root" required/>
                </div>
                <button type="submit" class="button button-block">${authButton}</button>
            </form>
        </div>
    </div>
</div>

<script src="js/formValidator.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
<script src="js/form.js"></script>
</body>
</html>
