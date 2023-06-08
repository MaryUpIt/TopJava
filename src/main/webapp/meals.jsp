<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/styles_meals.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2 class="title">Meals</h2>
<table class="table">
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Каллории</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${requestScope.meals}" var="meals">
    <jsp:useBean id="meals" type="ru.javawebinar.topjava.model.MealTo"/>
    <tr class=${meals.excess ? 'excess' : 'normal'}>
        <td><%=TimeUtil.format(meals.getDateTime())%>
        </td>
        <td>${meals.description}</td>
        <td>${meals.calories}</td>
        <td title="редактировать"><a href="meals?action=edit&id=${meals.id}">редактировать</a></td>
        <td title="удалить еду"><a href="meals?action=delete&id=${meals.id}">удалить</a></td>
        </c:forEach>
</table>

<div class="add-meal" title="добавить еду"><a href="meals?action=add">Добавить еду</a></div>
</body>
</html>
