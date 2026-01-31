<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>DB Snapshot — данные в БД</title>
    <style>
        body { font-family: sans-serif; max-width: 1000px; margin: 2em auto; padding: 0 1em; }
        h1, h2 { color: #333; }
        table { border-collapse: collapse; width: 100%; margin: 1em 0; font-size: 14px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #e0e0e0; }
        .error { color: #c00; background: #fde8e8; padding: 0.5em; border-radius: 4px; }
        a { color: #06c; }
        .section { margin: 2em 0; }
    </style>
</head>
<body>
    <p><a href="${pageContext.request.contextPath}/">← На главную</a> | <a href="${pageContext.request.contextPath}/schedule">Запросы</a></p>
    <h1>DB Snapshot — визуализация данных в БД</h1>

    <c:if test="${not empty requestScope.error}">
        <p class="error">${requestScope.error}</p>
    </c:if>

    <div class="section">
        <h2>Преподаватели (teachers)</h2>
        <table>
            <tr><th>ID</th><th>ФИО</th></tr>
            <c:forEach items="${requestScope.teachers}" var="t">
                <tr><td>${t.id}</td><td>${t.fullName}</td></tr>
            </c:forEach>
        </table>
    </div>

    <div class="section">
        <h2>Дисциплины (disciplines)</h2>
        <table>
            <tr><th>ID</th><th>Название</th></tr>
            <c:forEach items="${requestScope.disciplines}" var="d">
                <tr><td>${d.id}</td><td>${d.name}</td></tr>
            </c:forEach>
        </table>
    </div>

    <div class="section">
        <h2>Ведение дисциплин (teachings)</h2>
        <table>
            <tr><th>ID</th><th>Преподаватель</th><th>Дисциплина</th><th>Пар в неделю</th><th>Студентов на паре</th></tr>
            <c:forEach items="${requestScope.teachings}" var="te">
                <tr>
                    <td>${te.id}</td>
                    <td>${te.teacher.fullName}</td>
                    <td>${te.discipline.name}</td>
                    <td>${te.classesPerWeek}</td>
                    <td>${te.studentsPerClass}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="section">
        <h2>Занятия по дням и аудиториям (class_slots)</h2>
        <table>
            <tr><th>ID</th><th>День</th><th>Аудитория</th><th>Преподаватель</th><th>Дисциплина</th></tr>
            <c:forEach items="${requestScope.slots}" var="s">
                <tr>
                    <td>${s.id}</td>
                    <td>${requestScope.dayNames[s.dayOfWeek]} (${s.dayOfWeek})</td>
                    <td>${s.room}</td>
                    <td>${s.teaching.teacher.fullName}</td>
                    <td>${s.teaching.discipline.name}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
