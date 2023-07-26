### skypro-hogwarts
Simple RESTful app with CRUD operations for students and faculties. PostgreSQL is used to store and operate data.

#### Stack
```
Java 11
Spring Boot 2
Maven
SpringDoc OpenAPI UI
Liquibase
PostreSQL
H2 in-memory DB (for tests)
```

#### Functionality
This app is able to perform:
- CRUD operations for Students, Faculties and Avatars(for students);
- calculate the total number of students and their average age;
- get all students for dedicated faculty.

The application's external interface is represented as an HTTP API (REST).
List of HTTP URL methods can be viewed at Swagger (Open API UI) at http://localhost:8080/api/swagger-ui/index.html
