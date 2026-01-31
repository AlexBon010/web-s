package lab3.service;

import lab3.dto.StudentRequest;
import lab3.dto.TransferRequest;
import lab3.entity.Student;
import lab3.entity.Student.StudentStatus;
import lab3.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Student> findByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    @Transactional(readOnly = true)
    public List<Student> findByStatus(StudentStatus status) {
        return repository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Student enroll(StudentRequest request) {
        Student s = new Student();
        s.setFirstName(request.getFirstName().trim());
        s.setLastName(request.getLastName().trim());
        s.setDepartment(request.getDepartment().trim());
        s.setEnrollment(request.getEnrollment());
        s.setStatus(StudentStatus.ENROLLED);
        return repository.save(s);
    }

    @Transactional
    public Optional<Student> update(Long id, StudentRequest request) {
        return repository.findById(id)
                .map(s -> {
                    s.setFirstName(request.getFirstName().trim());
                    s.setLastName(request.getLastName().trim());
                    s.setDepartment(request.getDepartment().trim());
                    s.setEnrollment(request.getEnrollment());
                    return repository.save(s);
                });
    }

    @Transactional
    public Optional<Student> expel(Long id) {
        return repository.findById(id)
                .filter(s -> s.getStatus() == StudentStatus.ENROLLED)
                .map(s -> {
                    s.setStatus(StudentStatus.EXPELLED);
                    return repository.save(s);
                });
    }

    @Transactional
    public Optional<Student> transfer(Long id, TransferRequest request) {
        return repository.findById(id)
                .filter(s -> s.getStatus() == StudentStatus.ENROLLED)
                .map(s -> {
                    s.setDepartment(request.getDepartment().trim());
                    s.setStatus(StudentStatus.TRANSFERRED);
                    return repository.save(s);
                });
    }

    @Transactional
    public boolean delete(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
