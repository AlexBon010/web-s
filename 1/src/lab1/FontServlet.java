package lab1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FontServlet extends HttpServlet {

    private static final String[] SAMPLE_LINES = {
            "Первая строка текста для отображения.",
            "Вторая строка с разным размером шрифта.",
            "Третья строка — размер задаётся клиентом.",
            "Четвёртая строка. Количество строк тоже задаётся клиентом.",
            "Пятая строка примера.",
            "Шестая строка.",
            "Седьмая строка.",
            "Восьмая строка.",
            "Девятая строка.",
            "Десятая строка."
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        int fontSize = parseFontSize(req.getParameter("fontSize"));
        int lineCount = parseLineCount(req.getParameter("lineCount"));

        List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount && i < SAMPLE_LINES.length; i++) {
            lines.add(SAMPLE_LINES[i]);
        }

        req.setAttribute("fontSize", fontSize);
        req.setAttribute("lineCount", lineCount);
        req.setAttribute("lines", lines);
        req.getRequestDispatcher("/fontDisplay.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private int parseFontSize(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 14;
        }
        try {
            int size = Integer.parseInt(value.trim());
            return Math.max(8, Math.min(72, size));
        } catch (NumberFormatException e) {
            return 14;
        }
    }

    private int parseLineCount(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 5;
        }
        try {
            int count = Integer.parseInt(value.trim());
            return Math.max(1, Math.min(SAMPLE_LINES.length, count));
        } catch (NumberFormatException e) {
            return 5;
        }
    }
}
