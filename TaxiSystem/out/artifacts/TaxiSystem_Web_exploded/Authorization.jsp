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
    <title>Вход в систему</title>
</head>
<body>
    <form action="Controller" method="post">
        <input type="hidden" name="method" value="ClientAuthorization">
        <input type="text" name="login" value="client">
        <input type="password" name="password" value="root">
        <button type="submit">Я клиент</button>
    </form>

    <form action="Controller" method="post">
        <input type="hidden" name="method" value="TaxiAuthorization">
        <input type="text" name="login" value="taxi">
        <input type="password" name="password" value="root">
        <button type="submit">Я таксист</button>
    </form>
</body>
</html>
