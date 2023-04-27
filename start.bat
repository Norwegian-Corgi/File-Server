@echo off
echo Running docker-compose...
docker-compose up -d

@echo Waiting for the services to start. Please wait...
:wait
timeout 1
docker-compose exec db pg_isready >nul 2>&1 || goto wait

timeout 10

@echo Running query...
docker-compose exec db psql -U root -h localhost -p 5432 -d server -c "INSERT INTO userz (uuid, email, name, password, role) VALUES ('00000000-0000-0000-0000-000000000000', 'admin@mail.com', 'Admin', '$2a$10$3fdgZxHi2iuuUBId1TipxeoYgu5O2oF1iIZ/a26lnKu.IjHapzzVS', 'ADMIN');"
docker-compose exec backend bash -c "mkdir File\ Server ; cd File\ Server ; mkdir 00000000-0000-0000-0000-000000000000"
