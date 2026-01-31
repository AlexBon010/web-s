package lab2.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachings")
public class Teaching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    @Column(name = "classes_per_week", nullable = false)
    private int classesPerWeek;

    @Column(name = "students_per_class", nullable = false)
    private int studentsPerClass;

    @OneToMany(mappedBy = "teaching", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassSlot> slots = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public int getClassesPerWeek() {
        return classesPerWeek;
    }

    public void setClassesPerWeek(int classesPerWeek) {
        this.classesPerWeek = classesPerWeek;
    }

    public int getStudentsPerClass() {
        return studentsPerClass;
    }

    public void setStudentsPerClass(int studentsPerClass) {
        this.studentsPerClass = studentsPerClass;
    }

    public List<ClassSlot> getSlots() {
        return slots;
    }

    public void setSlots(List<ClassSlot> slots) {
        this.slots = slots;
    }
}
