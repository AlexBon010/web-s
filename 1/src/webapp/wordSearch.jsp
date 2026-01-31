<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Поиск слова в файле</title>
    <style>
        body { font-family: sans-serif; max-width: 700px; margin: 2em auto; padding: 0 1em; }
        h1 { color: #333; }
        form { margin: 1em 0; }
        input[type="text"] { padding: 6px 10px; width: 250px; }
        button { padding: 6px 14px; cursor: pointer; }
        a.btn { display: inline-block; padding: 6px 14px; background: #06c; color: #fff; text-decoration: none; border-radius: 4px; }
        a.btn:hover { background: #05a; }
        .result { margin-top: 1.5em; padding: 1em; background: #f5f5f5; border-radius: 6px; }
        .frequency { font-size: 1.2em; font-weight: bold; color: #06c; }
        ul.context { margin: 0.5em 0; padding-left: 1.2em; }
        a { color: #06c; }
    </style>
</head>
<body>
    <p><a href="${pageContext.request.contextPath}/">← На главную</a></p>
    <h1>Поиск слова в текстовом файле</h1>
    <p>Поиск и определение частоты встречаемости слова в файле <code>sample.txt</code> на сервере.</p>

    <p>
        <a href="${pageContext.request.contextPath}/word-search?download=1" class="btn">Получить этот файл</a>
    </p>

    <form method="post" action="${pageContext.request.contextPath}/word-search">
        <label>Слово: <input type="text" name="word" value="${requestScope.word != null ? requestScope.word : ''}" placeholder="Введите слово"></label>
        <button type="submit">Искать</button>
    </form>

    <c:if test="${requestScope.frequency != null}">
        <div class="result">
            <p class="frequency">Частота встречаемости: ${requestScope.frequency}</p>
            <c:if test="${not empty requestScope.contextLines}">
                <p>Строки, содержащие слово:</p>
                <ul class="context">
                    <c:forEach items="${requestScope.contextLines}" var="line">
                        <li>${line}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    </c:if>
</body>
</html>
