# Лабораторная работа №4-6

#### Standalone
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

### Базовые параметры
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

### Поиск данных
```shell
Options:
* --filter
  Filter parameters must be specified in the format 'parameter_name operation value'.
  Example: --filter age >= 30 --filter status = active. You can specify multiple filters.
```

Запуск поиска данных на клиенте
```shell
#Standalone
java -jar .\client\target\client.jar --standalone --command find --filter "firstName >= Lucas" --filter "lastName <= Lee"
#J2EE
java -jar .\client\target\client.jar --command find --filter "firstName >= Lucas" --filter "lastName <= Lee"
```

### Обновление данных
```shell
Options:
* --id
  Identifier of employee to delete. Example: --id 1
  --data
  Parameters for insertion must be specified in the format 'parameter_name=value'
  Example: --data 'firstName=Lucas, lastName=Lee, email=lucas.lee@example.com, hireDate=2019-04-07, departmentId=101'
```

Запуск обновления данных на клиенте
1. Частичное обновление данных
```shell
#Standalone
java -jar .\client\target\client.jar --standalone --command update --id 21 --data "department=IT"
#J2EE
java -jar .\client\target\client.jar --command update --id 21 --data "department=IT"
```
2. Полное обновление данных
```shell
#Standalone
java -jar .\client\target\client.jar --standalone --command update --id 21 --data "firstName=Kate, lastName=Kosareva, email=ksrve@mail.ru, hireDate=2024-12-01, department=IT"
#J2EE
java -jar .\client\target\client.jar --command update --id 21 --data "firstName=Kate, lastName=Kosareva, email=ksrve@mail.ru, hireDate=2024-12-01, department=IT"
```

### Запись данных

```shell
Options:
* --data
  Parameters for insertion must be specified in the format 'parameter_name=value'
  Example: --data 'firstName=Lucas, lastName=Lee, email=lucas.lee@example.com, hireDate=2019-04-07, departmentId=101'
```
Запуск записи данных на клиенте
```shell
#Standalone
java -jar .\client\target\client.jar --standalone --command create --data "firstName=Kate, lastName=Kosareva, email=ksrve@mail.ru, hireDate=2024-12-01, department=IT"
#J2EE
java -jar .\client\target\client.jar --command create --data "firstName=Kate, lastName=Kosareva, email=ksrve@mail.ru, hireDate=2024-12-01, department=IT"
```

### Удаление данных
```shell
Options:
* --id
  Identifier of employee to delete. Example: --id 1
```
Запуск удаления данных на клиенте
```shell
#Standalone
java -jar .\client\target\client.jar --standalone --command create --id 1
#J2EE
java -jar .\client\target\client.jar --command create --id 1
```