package lab3.repository;

import lab3.entity.Student;
import lab3.entity.Student.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByDepartment(String department);

    List<Student> findByStatus(StudentStatus status);

    List<Student> findByDepartmentAndStatus(String department, StudentStatus status);
}
