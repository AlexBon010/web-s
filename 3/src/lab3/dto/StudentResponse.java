package lab3.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lab3.entity.Student;

import java.time.LocalDate;

@Schema(description = "Данные студента в ответе API")
public class StudentResponse {

    @Schema(description = "ID студента")
    private Long id;
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Факультет")
    private String department;
    @Schema(description = "Дата зачисления (yyyy-mm-dd)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate enrollment;
    @Schema(description = "Статус: ENROLLED, EXPELLED, TRANSFERRED")
    private String status;

    public static StudentResponse from(Student s) {
        StudentResponse r = new StudentResponse();
        r.setId(s.getId());
        r.setFirstName(s.getFirstName());
        r.setLastName(s.getLastName());
        r.setDepartment(s.getDepartment());
        r.setEnrollment(s.getEnrollment());
        r.setStatus(s.getStatus().name());
        return r;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public LocalDate getEnrollment() { return enrollment; }
    public void setEnrollment(LocalDate enrollment) { this.enrollment = enrollment; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
