<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Текст разным размером шрифта</title>
    <style>
        body { font-family: sans-serif; max-width: 700px; margin: 2em auto; padding: 0 1em; }
        h1 { color: #333; }
        form { margin: 1em 0; }
        label { display: inline-block; margin-right: 1em; }
        input[type="number"] { width: 60px; padding: 6px; margin: 0 4px; }
        button { padding: 6px 14px; cursor: pointer; }
        .output { margin-top: 1.5em; padding: 1em; border: 1px solid #ddd; border-radius: 6px; }
        a { color: #06c; }
    </style>
</head>
<body>
    <p><a href="${pageContext.request.contextPath}/">← На главную</a></p>
    <h1>Вывод текста шрифтами разного размера</h1>
    <p>Размер шрифта (8–72 px) и количество строк (1–10) задаются на стороне клиента.</p>

    <form method="get" action="${pageContext.request.contextPath}/font">
        <label>Размер шрифта (px): <input type="number" name="fontSize" min="8" max="72" value="${requestScope.fontSize != null ? requestScope.fontSize : 14}"></label>
        <label>Количество строк: <input type="number" name="lineCount" min="1" max="10" value="${requestScope.lineCount != null ? requestScope.lineCount : 5}"></label>
        <button type="submit">Показать</button>
    </form>

    <c:if test="${not empty requestScope.lines}">
        <div class="output" style="font-size: ${requestScope.fontSize}px;">
            <c:forEach items="${requestScope.lines}" var="line">
                <div>${line}</div>
            </c:forEach>
        </div>
    </c:if>
</body>
</html>
