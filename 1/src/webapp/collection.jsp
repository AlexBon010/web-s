<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>поиск и замена по ключу</title>
    <style>
        body { font-family: sans-serif; max-width: 700px; margin: 2em auto; padding: 0 1em; }
        h1, h2 { color: #333; }
        form { margin: 0.8em 0; }
        input[type="text"] { padding: 6px 10px; width: 180px; margin: 0 4px; }
        button { padding: 6px 12px; cursor: pointer; margin: 0 2px; }
        table { border-collapse: collapse; width: 100%; margin: 1em 0; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        .message { margin: 0.5em 0; padding: 6px 10px; background: #e8f4e8; border-radius: 4px; }
        .error { margin: 0.5em 0; padding: 6px 10px; background: #fde8e8; color: #c00; border-radius: 4px; }
        a { color: #06c; }
    </style>
</head>
<body>
    <p><a href="${pageContext.request.contextPath}/">← На главную</a></p>
    <h1>Поиск и замена в коллекции по ключу</h1>

    <h2>Поиск по ключу</h2>
    <form method="post" action="${pageContext.request.contextPath}/collection">
        <input type="hidden" name="action" value="search">
        <input type="text" name="key" placeholder="Ключ" required>
        <button type="submit">Найти</button>
    </form>
    <c:if test="${not empty requestScope.searchKey}">
        <p class="message">Ключ «${requestScope.searchKey}» → ${requestScope.searchResult}</p>
    </c:if>

    <h2>Замена значения по ключу</h2>
    <form method="post" action="${pageContext.request.contextPath}/collection">
        <input type="hidden" name="action" value="replace">
        <input type="text" name="key" placeholder="Ключ" required>
        <input type="text" name="value" placeholder="Новое значение">
        <button type="submit">Заменить</button>
    </form>
    <c:if test="${not empty requestScope.replaceKey}">
        <p class="message">Заменено: ключ «${requestScope.replaceKey}» → «${requestScope.replaceValue}»</p>
    </c:if>
    <c:if test="${requestScope.replaceError}">
        <p class="error">Ошибка: такого ключа нет.</p>
    </c:if>

    <h2>Добавить запись</h2>
    <form method="post" action="${pageContext.request.contextPath}/collection">
        <input type="hidden" name="action" value="add">
        <input type="text" name="key" placeholder="Ключ" required>
        <input type="text" name="value" placeholder="Значение">
        <button type="submit">Добавить</button>
    </form>

    <h2>Текущая коллекция</h2>
    <table>
        <tr><th>Ключ</th><th>Значение</th><th>Действие</th></tr>
        <c:forEach items="${requestScope.items}" var="entry">
            <tr>
                <td>${entry.key}</td>
                <td>${entry.value}</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/collection" style="display:inline;">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="key" value="${entry.key}">
                        <button type="submit">Удалить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
