# Spring Boot CRUD (User ↔ Post) + Thymeleaf

**What's inside** - Spring Boot 3, Java 17, Maven - Tables: `users` and
`posts` (one-to-many relationship) - CRUD via Thymeleaf pages and simple
REST endpoints (`/users/api`, `/posts/api`) - Dev: H2 (in-memory) ---
run without installing a database - Prod: MySQL (profile `mysql`)

## Quick Start (H2)

``` bash
mvn spring-boot:run
# Open: http://localhost:8080
# H2 console: http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:testdb, user: sa, pass: )
```

## Running with MySQL

1.  Create the database:

``` sql
CREATE DATABASE finalproject CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2.  Edit `src/main/resources/application-mysql.properties` (set your
    login and password).
3.  Start with the profile:

``` bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## Exporting a Dump

``` bash
mysqldump -u root -p finalproject > db_dump.sql
```

Place `db_dump.sql` in the root of the repository.

## Project Structure

    src/main/java/org/example/finalproject
      ├── FinalProjectApplication.java
      ├── controller
      │   ├── IndexController.java
      │   ├── UserController.java
      │   └── PostController.java
      ├── entity
      │   ├── User.java
      │   └── Post.java
      └── repository
          ├── UserRepository.java
          └── PostRepository.java
    src/main/resources
      ├── templates
      │   ├── index.html
      │   ├── user/list.html
      │   └── post/list.html
      ├── static/css/style.css
      ├── application.properties         # H2
      └── application-mysql.properties   # MySQL profile
