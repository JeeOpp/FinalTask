<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 12.11.2017
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
    <form action="Controller" method="post">
        <input type="hidden" name="method" value="registration">
        <input type="hidden" name="role" value="client">

        <input type="text" name="login" value="Ivan">
        <input type="text" name="password" value="Ivanov">
        <input type="text" name="firstName" value="">
        <input type="text" name="lastName" value="">
        <button type="submit">Зарегистрироваться</button>
    </form>
</body>
</html>
