<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Сервлеты</title>
    <style>
        body { font-family: sans-serif; max-width: 600px; margin: 2em auto; padding: 0 1em; }
        h1 { color: #333; }
        ul { list-style: none; padding: 0; }
        li { margin: 0.8em 0; }
        a { color: #06c; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Лабораторная работа 1</h1>
    <ul>
        <li><a href="${pageContext.request.contextPath}/word-search">1. Поиск слова в текстовом файле (частота встречаемости)</a></li>
        <li><a href="${pageContext.request.contextPath}/collection">2. Поиск и замена в коллекции по ключу</a></li>
        <li><a href="${pageContext.request.contextPath}/font">3. Вывод текста шрифтами разного размера</a></li>
    </ul>
</body>
</html>
