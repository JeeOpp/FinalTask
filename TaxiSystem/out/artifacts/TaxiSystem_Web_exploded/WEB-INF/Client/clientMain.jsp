<%--
  Created by IntelliJ IDEA.
  User: DNAPC
  Date: 12.11.2017
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCWVlbCzAS1kedMyyEjnnASz9vwaIjOmp8"></script>
    <style>
        <%@include file="mapStyle.css"%>
    </style>
    <script>
        <%@include file="map.js"%>
    </script>
</head>
<body>
    <div id="map" class="map"></div>

    <form action="Controller" method="post">
        <input type="hidden" name="method" value = "callTaxi">
        <input type="hidden" name="position">
        <button type="submit">Заказать такси</button>
    </form>
</body>
</html>
