<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<%--    https://stackoverflow.com/questions/14739350/the-function-must-be-used-with-a-prefix-when-a-default-namespace-is-not-specifie--%>
    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
    <title>Meals</title>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<section>
    <h3><a href="http://localhost:8080/topjava/">Home</a></h3>
    <hr/>
    <h2><spring:message code="meal.title"/></h2>
    <form method="get" action="meals/filter">
<%--        <input type="hidden" name="action" value="filter">--%>
        <dl>
            <dt><spring:message code="meal.startDate"/></dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.endDate"/></dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.startTime"/></dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.endTime"/></dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit"><spring:message code="meal.filter"/></button>
    </form>
    <hr/>
<%--    <a href="meals?action=create">Add Meal</a>--%>
    <form:form action="meals?action=create" method="post">
        <button type="submit"><spring:message code="meal.add"/></button>
    </form:form>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="meal.dateTime"/></th>
            <th><spring:message code="meal.description"/></th>
            <th><spring:message code="meal.calories"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr data-mealExcess="${meal.excess}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>
<%--                    <a href="meals?action=update&id=${meal.id}">Update</a>--%>
                    <form:form action="meals?action=update&id=${meal.id}" method="post">
                        <button type="submit"><spring:message code="meal.update"/>
                    </form:form>
                </td>
<%--                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>--%>
                <td>
                    <form:form action="meals/${meal.id}" method="delete">
                        <button type="submit"><spring:message code="meal.delete"/>
                    </form:form>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
    <jsp:include page="fragments/footer.jsp"/>
</body>
</html>