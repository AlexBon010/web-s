## Docker Compose (все сервисы)

> **Предупреждение:** БД (сервис `db`) сохраняет данные только до остановки `docker compose`. После `docker compose down` данные в PostgreSQL теряются. Для постоянного хранения нужны внешние volumes или хост-директории.

**Шаги перед запуском:** клонировать репозиторий, перейти в каталог проекта и только там выполнять команды Docker:

```bash
git clone https://github.com/AlexBon010/web-s
cd web-s
```

**Запуск всех приложений:**
```bash
docker compose up -d --build
```

**Запуск по отдельности** (лабы 1–3):

```bash
docker compose up -d --build app
```

```bash
docker compose up -d --build app2
```

```bash
docker compose up -d --build app3
```

**Запуск по отдельности** (лаба 4: app4, app5).

```bash
docker compose up -d --build app4
```

```bash
docker compose up -d --build app5
```

**Остановка и удаление контейнеров и volumes:**
```bash
docker compose down -v
```

Порты задаются в `.env`.