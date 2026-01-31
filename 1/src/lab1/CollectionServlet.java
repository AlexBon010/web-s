package lab1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionServlet extends HttpServlet {

    private static final String ATTR_STORAGE = "lab1.collection.storage";

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Map<String, String> storage = getStorage(req);
        req.setAttribute("items", storage);
        req.getRequestDispatcher("/collection.jsp").forward(req, resp);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Map<String, String> storage = getStorage(req);

        String action = req.getParameter("action");
        String key = req.getParameter("key");
        String value = req.getParameter("value");

        if (key != null) {
            key = key.trim();
        }
        if (value != null) {
            value = value.trim();
        }

        if ("search".equals(action) && key != null) {
            String found = storage.get(key);
            req.setAttribute("searchKey", key);
            req.setAttribute("searchResult", found != null ? found : "(не найдено)");
        } else if ("replace".equals(action) && key != null) {
            if (storage.containsKey(key)) {
                storage.put(key, value != null ? value : "");
                req.setAttribute("replaceKey", key);
                req.setAttribute("replaceValue", value != null ? value : "");
            } else {
                req.setAttribute("replaceError", true);
                req.setAttribute("replaceErrorKey", key);
            }
        } else if ("add".equals(action) && key != null && !key.isEmpty()) {
            storage.put(key, value != null ? value : "");
            req.setAttribute("addKey", key);
        } else if ("remove".equals(action) && key != null) {
            storage.remove(key);
            req.setAttribute("removeKey", key);
        }

        req.setAttribute("items", Collections.unmodifiableMap(new LinkedHashMap<>(storage)));
        req.getRequestDispatcher("/collection.jsp").forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getStorage(HttpServletRequest req) {
        Map<String, String> storage = (Map<String, String>) req.getServletContext().getAttribute(ATTR_STORAGE);
        if (storage == null) {
            storage = new ConcurrentHashMap<>();
            initSampleData(storage);
            req.getServletContext().setAttribute(ATTR_STORAGE, storage);
        }
        return storage;
    }

    private void initSampleData(Map<String, String> storage) {
        storage.put("имя", "Иван");
        storage.put("фамилия", "Петров");
        storage.put("город", "Москва");
        storage.put("курс", "3");
        storage.put("группа", "ИВТ-301");
    }
}
