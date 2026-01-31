package lab3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на перевод студента на другой факультет")
public class TransferRequest {

    @NotBlank(message = "department обязателен")
    @Schema(description = "Новый факультет", example = "ПМИ", requiredMode = Schema.RequiredMode.REQUIRED)
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
