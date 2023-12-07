# TaskManagementSystem

Микросервис на SpringBoot с БД - PostgreSQL, Swagger подключен как зависимость, добавлены аннотации с описанием.
Спецификация API сервиса: openapi.yaml

Для запуска сервиса необходимо выполнить в терминале из корневой папки проекта:
mvn clean package

После завершения работы maven при запущенном Docker ввести команду: 
docker-compose up

Будут развернуты 2 контейнера Docker: для сервиса и базы данных. Доступ к сервису по: localhost:8080

По умолчанию созданы пользователи, которые могут авторизоваться на сервисе и получить токены.
name: admin@tms.ru
password: admin

name: user@tms.ru
password: user

name: user2@tms.ru
password: user2



