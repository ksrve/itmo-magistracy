# Лабораторная работа №1

## Порядок запуска лаборатораной работы

### Standalone
1. Поднять контейнер PostgreSQL через docker-compose в папке deployment
```shell
docker-compose up -d database
```

2. Собрать всё на свете
```shell
mvn clean install
```

3. Запустить сервер

```shell
java -jar .\server\target\server.jar --dbhost localhost
```

### J2EE
1. Поднять контейнеры через docker-compose в папке deployment
```shell
docker-compose up -d
```

2. Собрать всё на свете
```shell
mvn clean install
```

4. Пробовать функции клиента

#### Базовые параметры
```shell
Usage: client.jar [options]
Options:
* --command
  Command to execute
  --help
  Print usage
  --host
  Server hostname
  Default: localhost
  --port
  Server port
  Default: 8080
```

#### Поиск данных
```shell
Options:
* --filter
  Filter parameters must be specified in the format 'parameter_name operation value'.
  Example: --filter age >= 30 --filter status = active. You can specify multiple filters.
```

Запуск поиска данных на клиенте
```shell
#Standalone
java -jar .\client\target\client.jar --command find --filter "firstName >= Lucas" --filter "lastName <= Lee"
#J2EE
java -jar .\client\target\client.jar --standalone --command find --filter "firstName >= Lucas" --filter "lastName <= Lee"
```
