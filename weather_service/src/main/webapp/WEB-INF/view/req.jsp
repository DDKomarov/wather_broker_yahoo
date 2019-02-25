<%--
  Created by IntelliJ IDEA.
  User: DDKomarov
  Date: 25.02.2019
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
</head>
<body>
<div class="container">
    <div>
        <div>
            <h1 class="page-header">Weather Broker</h1>
            <spring:form action="forecast/submit" method="post" modelAttribute="filter">
                <div class="form-group">
                    <label>Date</label>
                    <spring:input type= "date" path="date"/>
                </div>
                <div class="form-group">
                    <label>City</label>
                    <spring:input type="text" path="city"/>
                </div>

                <button type="submit" class="btn btn-default">Ok</button>
            </spring:form>
        </div>
    </div>
</div>
</body>
</html>