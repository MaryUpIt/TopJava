<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Meal</title>
    <link rel="stylesheet" href="css/styles_meals.css">
</head>
<body>
<div class="form">
    <form method="post" action="meals">
        <h2 class="title">${param.action=="add"? "Добавить прием пищи" : "Редеактировать прием пищи"}</h2>
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
        <input type="hidden" name="id" value="${meal.id}">
        <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
        <input type="text" name="description" value="${meal.description}">
        <input type="number" name="calories" value="${meal.calories}">

        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</div>

</body>
</html>
