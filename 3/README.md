# Лабораторная работа 3 — Spring Boot, REST

RESTful сервис учёта студентов по факультетам: зачисление, отчисление, перевод на другой факультет.

## Модель данных

```json
{
  "firstName": "Иван",
  "lastName": "Петров",
  "department": "ИВТ",
  "enrollment": "2023-09-01"
}
```

Статусы: `ENROLLED` (зачислен), `EXPELLED` (отчислен), `TRANSFERRED` (переведён).

## Запуск

### Локально
```bash
cd 3
mvn spring-boot:run
```

### Docker
Из корня проекта:
```bash
docker compose up --build app3
```

Приложение: **http://localhost:8082** (в Docker) или **http://localhost:8080** (локально).

## API

| Метод | URL | Описание |
|-------|-----|----------|
| GET | /api/students | Список (опционально: ?department=...&status=...) |
| GET | /api/students/{id} | Студент по ID |
| POST | /api/students | Зачислить (тело: StudentRequest) |
| PUT | /api/students/{id} | Обновить данные |
| POST | /api/students/{id}/expel | Отчислить |
| POST | /api/students/{id}/transfer | Перевести (тело: {"department": "ПМИ"}) |
| DELETE | /api/students/{id} | Удалить запись |

## Swagger

- **Swagger UI:** http://localhost:8082/swagger-ui.html (или 8080 при локальном запуске)
- **OpenAPI JSON:** http://localhost:8082/v3/api-docs
