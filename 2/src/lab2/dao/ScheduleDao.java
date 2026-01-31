package lab2.dao;

import lab2.entity.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleDao {

    private final EntityManager em;

    public ScheduleDao(EntityManager em) {
        this.em = em;
    }

    public List<Teacher> findTeachersByDayAndRoom(int dayOfWeek, String room) {
        TypedQuery<Teacher> q = em.createQuery(
                "SELECT DISTINCT t FROM Teacher t " +
                "JOIN t.teachings te JOIN te.slots s " +
                "WHERE s.dayOfWeek = :day AND s.room = :room",
                Teacher.class);
        q.setParameter("day", dayOfWeek);
        q.setParameter("room", room);
        return q.getResultList();
    }

    public List<Teacher> findTeachersNotTeachingOnDay(int dayOfWeek) {
        TypedQuery<Teacher> q = em.createQuery(
                "SELECT t FROM Teacher t WHERE t.id NOT IN (" +
                "SELECT DISTINCT te.teacher.id FROM Teaching te JOIN te.slots s WHERE s.dayOfWeek = :day)",
                Teacher.class);
        q.setParameter("day", dayOfWeek);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Integer> findDaysWithClassCount(int count) {
        List<?> raw = em.createNativeQuery(
                "SELECT day_of_week FROM class_slots " +
                "GROUP BY day_of_week HAVING COUNT(*) = ?1")
                .setParameter(1, count)
                .getResultList();
        return raw.stream().map(o -> ((Number) o).intValue()).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Integer> findDaysWithRoomCount(int count) {
        List<?> raw = em.createNativeQuery(
                "SELECT day_of_week FROM class_slots " +
                "GROUP BY day_of_week HAVING COUNT(DISTINCT room) = ?1")
                .setParameter(1, count)
                .getResultList();
        return raw.stream().map(o -> ((Number) o).intValue()).collect(Collectors.toList());
    }
}
