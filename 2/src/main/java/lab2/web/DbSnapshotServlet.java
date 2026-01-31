package lab2.web;

import lab2.entity.*;
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

public class DbSnapshotServlet extends HttpServlet {

    private static final String[] DAY_NAMES = { "", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб" };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());

        EntityManagerFactory emf = (EntityManagerFactory) req.getServletContext().getAttribute(EMFListener.ATTR_EMF);
        if (emf == null) {
            req.setAttribute("error", "База данных недоступна.");
            req.getRequestDispatcher("/dbSnapshot.jsp").forward(req, resp);
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            DataInit.ensureData(em);

            @SuppressWarnings("unchecked")
            List<Teacher> teachers = em.createQuery("SELECT DISTINCT t FROM Teacher t LEFT JOIN FETCH t.teachings").getResultList();
            @SuppressWarnings("unchecked")
            List<Discipline> disciplines = em.createQuery("FROM Discipline d ORDER BY d.id").getResultList();
            @SuppressWarnings("unchecked")
            List<Teaching> teachings = em.createQuery(
                    "SELECT te FROM Teaching te JOIN FETCH te.teacher JOIN FETCH te.discipline ORDER BY te.id").getResultList();
            @SuppressWarnings("unchecked")
            List<ClassSlot> slots = em.createQuery(
                    "SELECT s FROM ClassSlot s JOIN FETCH s.teaching te JOIN FETCH te.teacher JOIN FETCH te.discipline ORDER BY s.dayOfWeek, s.room").getResultList();

            req.setAttribute("teachers", teachers);
            req.setAttribute("disciplines", disciplines);
            req.setAttribute("teachings", teachings);
            req.setAttribute("slots", slots);
            req.setAttribute("dayNames", DAY_NAMES);
            req.getRequestDispatcher("/dbSnapshot.jsp").forward(req, resp);
        } finally {
            em.close();
        }
    }
}
