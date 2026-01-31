package lab3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lab3.dto.StudentRequest;
import lab3.dto.StudentResponse;
import lab3.dto.TransferRequest;
import lab3.entity.Student;
import lab3.entity.Student.StudentStatus;
import lab3.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Студенты", description = "Учёт зачисленных, отчисленных и переведённых студентов")
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @Operation(summary = "Список всех студентов", description = "Возвращает всех студентов с возможностью фильтра по факультету и статусу")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentResponse> list(
            @Parameter(description = "Факультет (опционально)") @RequestParam(required = false) String department,
            @Parameter(description = "Статус: ENROLLED, EXPELLED, TRANSFERRED") @RequestParam(required = false) StudentStatus status
    ) {
        List<Student> list;
        if (department != null && !department.isBlank()) {
            list = status != null
                    ? service.findByDepartment(department).stream().filter(s -> s.getStatus() == status).toList()
                    : service.findByDepartment(department);
        } else if (status != null) {
            list = service.findByStatus(status);
        } else {
            list = service.findAll();
        }
        return list.stream().map(StudentResponse::from).collect(Collectors.toList());
    }

    @Operation(summary = "Получить студента по ID")
    @ApiResponse(responseCode = "200", description = "Найден")
    @ApiResponse(responseCode = "404", description = "Не найден")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentResponse> getById(
            @Parameter(description = "ID студента") @PathVariable Long id
    ) {
        return service.findById(id)
                .map(StudentResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Зачислить студента", description = "Создаёт запись о новом студенте со статусом ENROLLED")
    @ApiResponse(responseCode = "201", description = "Создан")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentResponse> enroll(@Valid @RequestBody StudentRequest request) {
        Student created = service.enroll(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(StudentResponse.from(created));
    }

    @Operation(summary = "Обновить данные студента")
    @ApiResponse(responseCode = "200", description = "Обновлён")
    @ApiResponse(responseCode = "404", description = "Не найден")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentResponse> update(
            @Parameter(description = "ID студента") @PathVariable Long id,
            @Valid @RequestBody StudentRequest request
    ) {
        return service.update(id, request)
                .map(StudentResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Отчислить студента", description = "Меняет статус на EXPELLED")
    @ApiResponse(responseCode = "200", description = "Отчислен")
    @ApiResponse(responseCode = "404", description = "Не найден или уже отчислен/переведён")
    @PostMapping(value = "/{id}/expel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentResponse> expel(
            @Parameter(description = "ID студента") @PathVariable Long id
    ) {
        return service.expel(id)
                .map(StudentResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Перевести на другой факультет", description = "Меняет факультет и статус на TRANSFERRED")
    @ApiResponse(responseCode = "200", description = "Переведён")
    @ApiResponse(responseCode = "404", description = "Не найден или не зачислен")
    @PostMapping(value = "/{id}/transfer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentResponse> transfer(
            @Parameter(description = "ID студента") @PathVariable Long id,
            @Valid @RequestBody TransferRequest request
    ) {
        return service.transfer(id, request)
                .map(StudentResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить запись о студенте")
    @ApiResponse(responseCode = "204", description = "Удалён")
    @ApiResponse(responseCode = "404", description = "Не найден")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID студента") @PathVariable Long id
    ) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
