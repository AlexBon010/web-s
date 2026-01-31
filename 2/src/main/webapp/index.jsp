<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Лабораторная работа 2 — БД, Hibernate</title>
    <style>
        body { font-family: sans-serif; max-width: 600px; margin: 2em auto; padding: 0 1em; }
        h1 { color: #333; }
        a { color: #06c; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Лабораторная работа 2 — БД, Hibernate</h1>
    <p>Информация о преподавателях и дисциплинах.</p>
    <ul>
        <li><a href="${pageContext.request.contextPath}/schedule">Перейти к запросам</a></li>
        <li><a href="${pageContext.request.contextPath}/db-snapshot">DB Snapshot</a> — данные в БД (таблицы)</li>
    </ul>
</body>
</html>
