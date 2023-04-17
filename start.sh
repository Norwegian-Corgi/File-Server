#!/bin/bash

echo "Running docker-compose..."
docker-compose up -d

echo "Waiting for the services to start. Please wait..."
while true; do
  sleep 1
  docker-compose exec db pg_isready > /dev/null 2>&1 || continue
  break
done

echo "Running query..."
docker-compose exec db psql -U root -h localhost -p 5432 -d server -c "INSERT INTO userz (uuid, email, name, password, role) VALUES ('00000000-0000-0000-0000-000000000000', 'test@email.com', 'test', '$2a$10$N/U/9jfIblU4wBYHr9Miv.T/6T1XdZ9vYlI17tIY83Ppe5he8SLa.', 'ADMIN');"
docker-compose exec back bash -c "mkdir File\ Server ; cd File\ Server ; mkdir 00000000-0000-0000-0000-000000000000"
