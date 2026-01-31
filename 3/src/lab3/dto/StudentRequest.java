package lab3.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Данные студента для создания/обновления")
public class StudentRequest {

    @NotBlank(message = "firstName обязателен")
    @Schema(description = "Имя", example = "Иван", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank(message = "lastName обязателен")
    @Schema(description = "Фамилия", example = "Петров", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @NotBlank(message = "department обязателен")
    @Schema(description = "Факультет", example = "ИВТ", requiredMode = Schema.RequiredMode.REQUIRED)
    private String department;

    @NotNull(message = "enrollment обязателен")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Дата зачисления (yyyy-mm-dd)", example = "2023-09-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate enrollment;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(LocalDate enrollment) {
        this.enrollment = enrollment;
    }
}
