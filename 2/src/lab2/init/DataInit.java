package lab2.init;

import lab2.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DataInit {

    private static final String[] DAY_NAMES = { "", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота" };

    public static void ensureData(EntityManager em) {
        Long count = em.createQuery("SELECT COUNT(t) FROM Teacher t", Long.class).getSingleResult();
        if (count > 0) return;

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Teacher t1 = new Teacher();
            t1.setFullName("Иванов Иван Иванович");
            em.persist(t1);

            Teacher t2 = new Teacher();
            t2.setFullName("Петрова Мария Сергеевна");
            em.persist(t2);

            Teacher t3 = new Teacher();
            t3.setFullName("Сидоров Алексей Петрович");
            em.persist(t3);

            Discipline d1 = new Discipline();
            d1.setName("Математика");
            em.persist(d1);

            Discipline d2 = new Discipline();
            d2.setName("Информатика");
            em.persist(d2);

            Discipline d3 = new Discipline();
            d3.setName("Физика");
            em.persist(d3);

            addTeaching(em, t1, d1, 4, 25, new int[]{1, 2, 4, 5}, new String[]{"101", "101", "102", "201"});
            addTeaching(em, t1, d2, 2, 20, new int[]{1, 3}, new String[]{"301", "301"});
            addTeaching(em, t2, d2, 3, 22, new int[]{2, 3, 4}, new String[]{"301", "302", "301"});
            addTeaching(em, t2, d3, 2, 18, new int[]{1, 5}, new String[]{"201", "201"});
            addTeaching(em, t3, d1, 2, 30, new int[]{3, 5}, new String[]{"102", "101"});
            addTeaching(em, t3, d3, 3, 20, new int[]{2, 4, 5}, new String[]{"201", "202", "201"});

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    private static void addTeaching(EntityManager em, Teacher t, Discipline d, int perWeek, int students,
                                    int[] days, String[] rooms) {
        Teaching te = new Teaching();
        te.setTeacher(t);
        te.setDiscipline(d);
        te.setClassesPerWeek(perWeek);
        te.setStudentsPerClass(students);
        em.persist(te);
        t.getTeachings().add(te);
        d.getTeachings().add(te);
        for (int i = 0; i < days.length; i++) {
            ClassSlot s = new ClassSlot();
            s.setTeaching(te);
            s.setDayOfWeek(days[i]);
            s.setRoom(rooms[i]);
            em.persist(s);
            te.getSlots().add(s);
        }
    }

    public static String dayName(int day) {
        return day >= 1 && day <= 6 ? DAY_NAMES[day] : String.valueOf(day);
    }
}
