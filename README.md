## Name

Basic Blogging Application

## Description

This is a fundamental RESTful web application built using Java, Spring Boot, and Hibernate ORM. It allows users to create, read, update, and delete blog posts and comments. Java Blogging App: Master Spring Boot &amp; Hibernate. Build RESTful CRUD for User, Post, Comment entities. Validate data, handle errors, test with JUnit/Mockito, document endpoints. Submit via Gitlab to showcase Java, Hibernate, Spring Boot skills.

### Technologies Used

- Java
- Spring Boot
- Hibernate ORM
- Maven
- JUnit 5
- Mockito
- Swagger UI API (for API documentation)

### Prerequisites

Make sure you have the following installed on your system:

- Java JDK 17
- Maven
- Your favorite IDE (e.g., IntelliJ IDEA, Eclipse)
- Any browser to view the swagger UI documentation and access REST API
- **DEFAULT LOGIN CREDENTIALS:**
- email: admin@mail.com
- password: admin
- http://localhost:8080/swagger-ui/index.html => Swagger UI API
- SQL Workbench & Server(to serve and view database):
  - Create simple database "basic_blog"
  - Tables will be auto created by spring _(after api has been executed at least once)_
  - _Before running the rest api, you need to insert some default values in the database (consult Appendex A - before proceeding)_
  - Ensure SQL server is running before exposing blog rest endpoints
  - View table bellow for database configuration:
    - If you have different SQL configurations (username and password)
    - Change the application.properties file in spring

| Username | Password |                Endpoint                |
| :------: | :------: | :------------------------------------: |
|   root   |   root   | jdbc:mysql://localhost:3306/basic_blog |

### Exception Handling

Implemented basic exception handling to cater for:

- ResourceNotFoundException => when an id related to a post or comment is not found
- APIException => triggered when an invalid request is made, comment id provided is not child of post id provided
- MethodArgumentTypeMismatchException => triggered when invalid characters are used in the request endpoint

### Running the REST API(terminal)

- NB: Ensure that you are inside the project folder _(cd springboot-hibernate-restful-web-app\basic-blog)_ before following the steps below:

```sh
mvnw test
mvnw clean
mvnw install
mvnw clean install
cd target
java -jar basic-blog-0.0.1-SNAPSHOT.jar
```

---

## Endpoints

### Users

|   Description   | Http Method | Request Type | Response Type | Authorised Users | Successful Http Status | Unsuccessful Http Status |      Endpoint       |
| :-------------: | :---------: | :----------: | :-----------: | :--------------: | :--------------------- | :----------------------: | :-----------------: |
|    New User     |    POST     |     JSON     |     JSON      |       NONE       | 201 CREATED            |     400 BAD REQUEST      | /api/auth/register  |
|  Get All Users  |     GET     |     JSON     |     JSON      |      ADMIN       | 200 OK                 |      404 NOT FOUND       |     /api/users      |
| Get Single User |     GET     |     JSON     |     JSON      |       ALL        | 200 OK                 |      404 NOT FOUND       | /api/users/{userId} |
|   Update User   |     PUT     |     JSON     |     JSON      |       ALL        | 200 OK                 |     401 UNAUTHORIZED     |     /api/users      |
|   Delete User   |   DELETE    |     JSON     |     TEXT      |       ALL        | 200 OK                 |     401 UNAUTHORIZED     | /api/users/{userId} |

---

### Posts

|   Description   | Http Method | Request Type | Response Type | Authorised Users | Successful Http Status | Unsuccessful Http Status |      Endpoint       |
| :-------------: | :---------: | :----------: | :-----------: | :--------------: | :--------------------- | :----------------------: | :-----------------: |
|    New Post     |    POST     |     JSON     |     JSON      |      ADMIN       | 201 CREATED            |     401 UNAUTHORIZED     |     /api/posts      |
|  Get All Posts  |     GET     |     JSON     |     JSON      |       ALL        | 200 OK                 |      404 NOT FOUND       |     /api/posts      |
| Get Single Post |     GET     |     JSON     |     JSON      |       ALL        | 200 OK                 |      404 NOT FOUND       | /api/posts/{postId} |
|   Update Post   |     PUT     |     JSON     |     JSON      |      ADMIN       | 200 OK                 |     401 UNAUTHORIZED     | /api/posts/{postId} |
|   Delete Post   |   DELETE    |     JSON     |     TEXT      |      ADMIN       | 200 OK                 |     401 UNAUTHORIZED     | /api/posts/{postId} |

#### Create New Post

- Required data when attempting to create a new Post:

  - Don't provide an id it will be automatically created
  - Post Title:
    - Should not be empty
    - Must be 2 or more characters long
  - Post Content:
    - Should not be empty
    - Must be 5 or more characters long

- NB: Only Admin users are allowed to CREAT NEW POSTS
- NB: Please provide admin credentials
  - Username: admin@mail.com
  - Password: admin
- NB: in postman use Basic Auth

#### Update Post

- Required data when attempting to update a Post:

  - Don't provide an id (in the request body) it will be provided in the URL
  - Post Title:
    - Should not be empty
    - Must be 2 or more characters long
  - Post Content:
    - Should not be empty
    - Must be 5 or more characters long

#### Delete Post

- Required data when deleting a Post:

  - Specify postId at the end of the URL: /api/posts/{postId}

---

### Comments

|    Description     | Http Method | Request Type | Response Type | Http Status |               Endpoint               |
| :----------------: | :---------: | :----------: | :-----------: | :---------: | :----------------------------------: |
|    New Comment     |    POST     |     JSON     |     JSON      | 201 CREATED |       /posts/{postId}/comments       |
|  Get All Comments  |     GET     |     JSON     |     JSON      |   200 OK    |       /posts/{postId}/comments       |
| Get Single Comment |     GET     |     JSON     |     JSON      |   200 OK    | /posts/{postId}/comments/{commentId} |
|   Update Comment   |     PUT     |     JSON     |     JSON      |   200 OK    | /posts/{postId}/comments/{commentId} |
|   Delete Comment   |   DELETE    |     JSON     |     TEXT      |   200 OK    | /posts/{postId}/comments/{commentId} |

### Appendex A

- In this section, you will create a default admin account
- Please ensure that you created an empty database _basic_blog_ in any sql editor or management service

```sh
CREATE DATABASE basic_blog;
```

- Run the rest api at least once and then stop the server _this will auto create the tables needed_
- In your prefed sql editor or management service, run the code below:

- _Create defualt admin:_
- You can modify the default values as you please
  - Ensure that the password is encrypted using BCryptEncoder 64Bit:
    - Open the spring app in your favourite IDE:
      - Open the _utils_ folder
      - Open the _PasswordGeneratorEncoder class_
      - Change _admin_ with desired password in line 9
      - Only run this class and in the terminal output copy the encrypted password into into the sql script below
      - If you want the default settings, **leave sql script as is**

```sh
USE basic_blog;
INSERT INTO users (email, name, password, date_created)
VALUES ('admin@mail.com', 'administrator', '$2a$10$WMbE5Z875BtBmuf6U2IViuzGO/ePB78YjBINW9vNFYDd0aTsKb1oy', '2023-09-17 09:36:26.076091');

SELECT * FROM basic_blog.users;
```

- _Add roles to the roles table:_
- Don't modify the code below, **leave as is**

```sh
USE basic_blog;
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

INSERT INTO roles (name)
VALUES ('ROLE_USER');

SELECT * FROM basic_blog.roles;
```

- _Add admin role to user_roles:_
- Don't modify the code below, **leave as is**

```sh
USE basic_blog;
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);

SELECT * FROM basic_blog.users_roles;
```
