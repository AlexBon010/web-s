<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Запросы — преподаватели и расписание</title>
    <style>
        body { font-family: sans-serif; max-width: 800px; margin: 2em auto; padding: 0 1em; }
        h1, h2 { color: #333; }
        form { margin: 0.5em 0 1em; }
        input[type="number"], input[type="text"] { padding: 6px 10px; width: 120px; margin: 0 4px; }
        button { padding: 6px 14px; cursor: pointer; }
        .block { margin: 1.5em 0; padding: 1em; background: #f8f8f8; border-radius: 6px; }
        .result { margin-top: 0.5em; padding: 0.5em; background: #e8f4e8; border-radius: 4px; }
        .error { color: #c00; background: #fde8e8; padding: 0.5em; border-radius: 4px; }
        ul { margin: 0.3em 0; padding-left: 1.2em; }
        a { color: #06c; }
    </style>
</head>
<body>
    <p><a href="${pageContext.request.contextPath}/">← На главную</a> | <a href="${pageContext.request.contextPath}/db-snapshot">DB Snapshot</a></p>
    <h1>Запросы по преподавателям и расписанию</h1>

    <c:if test="${not empty requestScope.error}">
        <p class="error">${requestScope.error}</p>
    </c:if>

    <div class="block">
        <h2>1. Преподаватели в заданный день в заданной аудитории</h2>
        <form method="post" action="${pageContext.request.contextPath}/schedule">
            <input type="hidden" name="action" value="byDayRoom">
            День недели:
            <select name="day">
                <c:forEach begin="1" end="6" var="d">
                    <option value="${d}" ${requestScope.query1Day == d ? 'selected' : ''}>${requestScope.dayNames[d]}</option>
                </c:forEach>
            </select>
            Аудитория: <input type="text" name="room" placeholder="например 101" value="${requestScope.query1Room}">
            <button type="submit">Найти</button>
        </form>
        <c:if test="${requestScope.resultTeachers != null}">
            <div class="result">
                <c:choose>
                    <c:when test="${empty requestScope.resultTeachers}">
                        Никого не найдено.
                    </c:when>
                    <c:otherwise>
                        <ul>
                            <c:forEach items="${requestScope.resultTeachers}" var="t">
                                <li>${t.fullName}</li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>

    <div class="block">
        <h2>2. Преподаватели, не ведущие занятия в заданный день</h2>
        <form method="post" action="${pageContext.request.contextPath}/schedule">
            <input type="hidden" name="action" value="notOnDay">
            День недели:
            <select name="day">
                <c:forEach begin="1" end="6" var="d">
                    <option value="${d}" ${requestScope.query2Day == d ? 'selected' : ''}>${requestScope.dayNames[d]}</option>
                </c:forEach>
            </select>
            <button type="submit">Найти</button>
        </form>
        <c:if test="${requestScope.resultTeachersNot != null}">
            <div class="result">
                <c:choose>
                    <c:when test="${empty requestScope.resultTeachersNot}">
                        Нет таких преподавателей.
                    </c:when>
                    <c:otherwise>
                        <ul>
                            <c:forEach items="${requestScope.resultTeachersNot}" var="t">
                                <li>${t.fullName}</li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>

    <div class="block">
        <h2>3. Дни недели с заданным количеством занятий</h2>
        <form method="post" action="${pageContext.request.contextPath}/schedule">
            <input type="hidden" name="action" value="daysByClasses">
            Количество занятий: <input type="number" name="count" min="1" value="${requestScope.query3Count != null ? requestScope.query3Count : 3}">
            <button type="submit">Найти</button>
        </form>
        <c:if test="${requestScope.resultDaysClasses != null}">
            <div class="result">
                <c:choose>
                    <c:when test="${empty requestScope.resultDaysClasses}">
                        Нет таких дней.
                    </c:when>
                    <c:otherwise>
                        <ul>
                            <c:forEach items="${requestScope.resultDaysClasses}" var="d">
                                <li>${requestScope.dayNames[d]}</li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>

    <div class="block">
        <h2>4. Дни недели с заданным количеством занятых аудиторий</h2>
        <form method="post" action="${pageContext.request.contextPath}/schedule">
            <input type="hidden" name="action" value="daysByRooms">
            Количество аудиторий: <input type="number" name="count" min="1" value="${requestScope.query4Count != null ? requestScope.query4Count : 2}">
            <button type="submit">Найти</button>
        </form>
        <c:if test="${requestScope.resultDaysRooms != null}">
            <div class="result">
                <c:choose>
                    <c:when test="${empty requestScope.resultDaysRooms}">
                        Нет таких дней.
                    </c:when>
                    <c:otherwise>
                        <ul>
                            <c:forEach items="${requestScope.resultDaysRooms}" var="d">
                                <li>${requestScope.dayNames[d]}</li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>
</body>
</html>
