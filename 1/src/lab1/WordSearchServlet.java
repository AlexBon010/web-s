package lab1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class WordSearchServlet extends HttpServlet {

    private static final String SAMPLE_FILE = "/sample.txt";
    private static final String DEFAULT_CONTENT =
            "Сервлеты представляют собой Java-классы, которые выполняются на стороне сервера.\n"
            + "Сервлет обрабатывает запросы от клиента и формирует ответ. Каждый сервлет наследуется от класса HttpServlet.\n"
            + "При разработке веб-приложений сервлеты используются для обработки HTTP-запросов.\n"
            + "Сервлет сервлет сервлет — повторение слова для проверки частоты.\n"
            + "Текст текст текст и ещё раз текст. Поиск слова в текстовом файле на сервере.\n"
            + "Лабораторная работа по сервлетам. Изучение сервлетов и JSP-страниц.\n"
            + "Коллекции в Java: Map, List, Set. Поиск по ключу в коллекции.\n"
            + "Размер шрифта и количество строк задаются на стороне клиента.\n";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        if ("1".equals(req.getParameter("download")) || "true".equalsIgnoreCase(req.getParameter("download"))) {
            sendFile(resp);
            return;
        }

        req.getRequestDispatcher("/wordSearch.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String word = req.getParameter("word");
        if (word == null) {
            word = "";
        }
        word = word.trim();

        String text = loadTextFromFile();
        int frequency = 0;
        List<String> contextLines = new ArrayList<>();

        if (!word.isEmpty() && !text.isEmpty()) {
            frequency = countWordFrequency(text, word);
            contextLines = findContextLines(text, word);
        }

        req.setAttribute("word", word);
        req.setAttribute("frequency", frequency);
        req.setAttribute("contextLines", contextLines);
        req.setAttribute("fileContent", text);
        req.getRequestDispatcher("/wordSearch.jsp").forward(req, resp);
    }

    private void sendFile(HttpServletResponse resp) throws IOException {
        byte[] content = loadTextFromFile().getBytes(StandardCharsets.UTF_8);
        resp.setContentType("text/plain; charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=\"sample.txt\"");
        resp.setContentLength(content.length);
        resp.getOutputStream().write(content);
    }

    private java.io.File getServerFile() {
        String base = getServletContext().getRealPath("/");
        if (base == null) {
            return null;
        }
        java.io.File dataDir = new java.io.File(base, "data");
        return new java.io.File(dataDir, "sample.txt");
    }

    private void ensureFileOnServer() throws IOException {
        java.io.File file = getServerFile();
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            java.io.File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            Files.writeString(file.toPath(), DEFAULT_CONTENT, StandardCharsets.UTF_8);
        }
    }

    private String loadTextFromFile() throws IOException {
        ensureFileOnServer();
        java.io.File serverFile = getServerFile();
        if (serverFile != null && serverFile.isFile()) {
            return Files.readString(serverFile.toPath(), StandardCharsets.UTF_8);
        }
        try (InputStream is = getClass().getResourceAsStream(SAMPLE_FILE)) {
            if (is != null) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        }
        return DEFAULT_CONTENT;
    }

    private int countWordFrequency(String text, String word) {
        if (word.isEmpty()) return 0;
        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        int count = 0;
        java.util.regex.Matcher m = pattern.matcher(text);
        while (m.find()) count++;
        return count;
    }

    private List<String> findContextLines(String text, String word) {
        List<String> result = new ArrayList<>();
        String[] lines = text.split("\\r?\\n");
        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        for (String line : lines) {
            if (pattern.matcher(line).find()) {
                result.add(line);
            }
        }
        return result;
    }
}
