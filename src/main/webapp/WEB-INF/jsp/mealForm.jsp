<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>Meal</title>
    <link rel="stylesheet" href="resources/css/style.css">
    <c:set var="createMeal"><spring:message code="meal.add"/></c:set>
    <c:set var="updateMeal"><spring:message code="meal.update"/></c:set>
</head>
<body>
<section>
    <h3><a href="http://localhost:8080/topjava/">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? createMeal : updateMeal}</h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals/update">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meal.dateTime"/></dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/></dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/></dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="meal.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="meal.cancel"/></button>
    </form>
</section>
    <jsp:include page="fragments/footer.jsp"/>
</body>
</html>
