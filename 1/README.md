# Лабораторная работа 1 — Сервлеты

## Цель
Создать сервлеты и JSP-страницы для:
1. Поиска слова в текстовом файле на сервере с определением частоты встречаемости
2. Поиска и замены информации в коллекции по ключу
3. Вывода фрагментов текста шрифтами разного размера (размер и количество строк задаются клиентом)

## Сборка и запуск

### Docker
```bash
cd 1
docker build -t servlets-lab1 .
docker run -p 8080:8080 servlets-lab1
```
Приложение будет доступно по адресу **http://localhost:8080/** (главная, `/word-search`, `/collection`, `/font`).

### Сборка WAR
```bash
mvn clean package
```
Артефакт: `target/servlets-lab1.war`

### Запуск
Развернуть WAR в любом сервлет-контейнере (Tomcat 9+, Jetty и т.д.) или из IDE (Add configuration → Tomcat Server → Deployment → Artifact → servlets-lab1:war exploded).

После запуска открыть в браузере:
- Главная: `http://localhost:8080/servlets-lab1/`
- Поиск слова: `http://localhost:8080/servlets-lab1/word-search`
- Коллекция: `http://localhost:8080/servlets-lab1/collection`
- Шрифты: `http://localhost:8080/servlets-lab1/font`

## Структура проекта
- `src/main/java/lab1/` — сервлеты: `WordSearchServlet`, `CollectionServlet`, `FontServlet`
- `src/main/webapp/` — JSP: `index.jsp`, `wordSearch.jsp`, `collection.jsp`, `fontDisplay.jsp`
- `src/main/resources/sample.txt` — текстовый файл для поиска слова
