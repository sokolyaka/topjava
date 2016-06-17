<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
    <title>mealList</title>
</head>
<body>
<table style=" border-style: solid; border-width:1px; width: 600px; border-collapse: collapse;">
    <thead>
    <tr style="background-color: gray;">
        <td style="width: 30px;">date</td>
        <td style="width: 80px;">description</td>
        <td style="width: 80px;">calories</td>
    </tr>
    </thead>
    <c:forEach var="meal" items="${mealList}">

        <tr style="color: <c:out value="${meal.exceed ? 'red':'green'}"/>">
            <td>${f:matches(meal.dateTime, 'dd-MM-yyyy HH:mm')}</td>
            <td><c:out value="${meal.description}" /></td>
            <td><c:out value="${meal.calories}" /></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
