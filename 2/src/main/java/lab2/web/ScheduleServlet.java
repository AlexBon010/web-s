package lab2.web;

import lab2.dao.ScheduleDao;
import lab2.entity.Teacher;
import lab2.init.DataInit;
import lab2.persistence.EMFListener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ScheduleServlet extends HttpServlet {

    private static final String[] DAY_NAMES = { "", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота" };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManagerFactory emf = (EntityManagerFactory) req.getServletContext().getAttribute(EMFListener.ATTR_EMF);
        if (emf == null) {
            req.setAttribute("error", "База данных недоступна.");
            req.getRequestDispatcher("/schedule.jsp").forward(req, resp);
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            DataInit.ensureData(em);
            ScheduleDao dao = new ScheduleDao(em);

            String action = req.getParameter("action");
            if (action != null) {
                em.getTransaction().begin();
                try {
                    if ("byDayRoom".equals(action)) {
                        int day = parseInt(req.getParameter("day"), 1);
                        String room = req.getParameter("room");
                        if (room != null && !room.trim().isEmpty()) {
                            List<Teacher> list = dao.findTeachersByDayAndRoom(day, room.trim());
                            req.setAttribute("resultTeachers", list);
                            req.setAttribute("query1Day", day);
                            req.setAttribute("query1Room", room.trim());
                        }
                    } else if ("notOnDay".equals(action)) {
                        int day = parseInt(req.getParameter("day"), 1);
                        List<Teacher> list = dao.findTeachersNotTeachingOnDay(day);
                        req.setAttribute("resultTeachersNot", list);
                        req.setAttribute("query2Day", day);
                    } else if ("daysByClasses".equals(action)) {
                        int n = parseInt(req.getParameter("count"), 1);
                        List<Integer> days = dao.findDaysWithClassCount(n);
                        req.setAttribute("resultDaysClasses", days);
                        req.setAttribute("query3Count", n);
                    } else if ("daysByRooms".equals(action)) {
                        int n = parseInt(req.getParameter("count"), 1);
                        List<Integer> days = dao.findDaysWithRoomCount(n);
                        req.setAttribute("resultDaysRooms", days);
                        req.setAttribute("query4Count", n);
                    }
                    em.getTransaction().commit();
                } catch (Exception e) {
                    if (em.getTransaction().isActive()) em.getTransaction().rollback();
                    req.setAttribute("error", e.getMessage());
                }
            }

            req.setAttribute("dayNames", DAY_NAMES);
            req.getRequestDispatcher("/schedule.jsp").forward(req, resp);
        } finally {
            em.close();
        }
    }

    private static int parseInt(String s, int def) {
        if (s == null || s.trim().isEmpty()) return def;
        try {
            int v = Integer.parseInt(s.trim());
            return Math.max(1, Math.min(6, v));
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
