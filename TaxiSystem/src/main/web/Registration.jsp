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
        <input type="text" name="login" value="Ivan">
        <input type="text" name="password" value="Ivanov">
        <button type="submit">Зарегистрироваться</button>
    </form>
</body>
</html>
