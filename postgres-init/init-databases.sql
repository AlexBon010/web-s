-- Создание двух БД (имена из POSTGRES_DB и POSTGRES_DB_2).
-- Создаём только если ещё не существуют.
SELECT 'CREATE DATABASE webdb' WHERE NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'webdb')\gexec
SELECT 'CREATE DATABASE lab4middle' WHERE NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'lab4middle')\gexec
