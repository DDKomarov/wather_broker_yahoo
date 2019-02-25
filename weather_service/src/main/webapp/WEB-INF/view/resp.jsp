<%--
  Created by IntelliJ IDEA.
  User: DDKomarov
  Date: 25.02.2019
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title align="center"> Forecast</title>
</head>
<body>
<h1 align="center">Forecast</h1>
<h3 align="center">Date</h3>
<div align="center"><c:out value="${weatherView.date}"/></div>
<h3 align="center">City</h3>
<div align="center"><c:out value="${weatherView.city}"/></div>
<h3 align="center">Day</h3>
<div align="center"><c:out value="${weatherView.day}"/></div>
<h3 align="center">Temperature</h3>
<div align="center"><c:out value="${weatherView.lowTemp}"/> - <c:out value="${weatherView.highTemp}"/></div>
<h3 align="center">Description</h3>
<div align="center"><c:out value="${weatherView.description}"/></div>
