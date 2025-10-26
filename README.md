
***

# ToDo-app

A simple Java web application demonstrating CRUD (Create, Read, Update, Delete) operations for a Todo list using JDBC and MySQL. Built as a beginner-friendly project with clear code and modular structure, suitable for learning full-stack web development.

***

## Features

- **Add Todo:** Create new tasks with titles and descriptions
- **View Todos:** See all existing tasks in a list
- **Update Todos:** Edit title or description of tasks
- **Delete Todos:** Remove tasks from the list
- **User Authentication (Advanced Option):** Secure login and registration using BCrypt password hashing
- **Database Integration:** All data persisted using MySQL via JDBC

***

## Tech Stack

- **Backend:** Java (Servlets/JSP, JDBC)
- **Frontend:** JSP, HTML, CSS
- **Database:** MySQL
- **Password Security:** BCrypt (org.mindrot.jbcrypt)
- **Build:** Maven or traditional Eclipse build (see below)
- **Web Server:** Apache Tomcat 9/10+

***

## Getting Started

### Prerequisites

- JDK 8 or later
- Apache Tomcat 9+ (for Java EE 8, Servlet 4.0) or Tomcat 10+ (for Jakarta EE, Servlet 5.0)
- MySQL Server
- Maven (optional, recommended)
- Eclipse (recommended IDE for beginners)

***

### Installation

1. **Clone the Repository**

```bash
git clone https://github.com/DheerajKumarR/ToDo-app.git
cd ToDo-app
```

2. **Configure MySQL Database**

- Create a database called `todo_app` and the required table:
- SQL example:
```sql
CREATE DATABASE todo_app;
USE todo_app;
CREATE TABLE todos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

3. **Set MySQL Connection in Source Code**

- Edit the database credentials (usually in `DatabaseConnection.java`):

```java
private static final String URL = "jdbc:mysql://localhost:3306/todo_app";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_mysql_password";
```

4. **Add Required Libraries**

- **MySQL Connector/J:**  
  - Download from [MySQL](https://dev.mysql.com/downloads/connector/j/) and add the JAR to `WEB-INF/lib`
- **BCrypt for password hashing:**  
  - Download [jbcrypt-0.4.jar](https://repo1.maven.org/maven2/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar) and add to `WEB-INF/lib`

5. **Import the Project in Eclipse**

- File → Import → Existing Projects into Workspace → Select the cloned folder
- Right-click project → Properties → Java Build Path → Add JARs
- Add `mysql-connector-java-x.x.xx.jar` and `jbcrypt-0.4.jar` from `WEB-INF/lib`

6. **Configure Apache Tomcat**

- Add server in Eclipse (Window → Servers → New → Server → Apache Tomcat)
- Add project to server (Right-click Tomcat server → Add and Remove…)

7. **Deploy and Run**

- Right-click project → Run As → Run on Server
- Navigate to [http://localhost:8080/ToDo-app/](http://localhost:8080/ToDo-app/) in your browser

***

## Usage

1. **Add a Todo**: Fill in the form and submit to add a new item.
2. **Edit a Todo**: Click "Edit" next to an item to update.
3. **Delete a Todo**: Click "Delete" to remove the item.
4. **View All Todos**: See all tasks listed with creation date.

***

## Project Structure

```
ToDo-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/todo/   # Models, DAO, utils, servlets
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml
│   │       │   └── lib/   # JARs (MySQL, BCrypt)
│   │       ├── index.jsp
│   │       ├── login.jsp
│   │       ├── register.jsp
│   │       └── dashboard.jsp
├── pom.xml     # If Maven-enabled
└── README.md
```

***

## Security Notes

- User passwords are securely hashed and salted with BCrypt before storing in the DB.
- PreparedStatements and input validation used to prevent SQL injection.

***

## Contribution

Pull requests and issues welcome!  
If you find a bug or want a new feature, open an issue or submit a PR.

***


## Credits

Developed by [Dheeraj Kumar R](https://github.com/DheerajKumarR)  
For educational demonstration of Java-JDBC CRUD operations.

***

## Snapshots
    
<img width="1487" height="853" alt="image" src="https://github.com/user-attachments/assets/fd4422c6-8512-4ccc-9b86-8bdb1481373d" />

                    fig1:Login page
                    
<img width="1442" height="876" alt="image" src="https://github.com/user-attachments/assets/0f01882b-b15a-41a0-9b52-a244261efbff" />

                    fig2:Register page
                    
<img width="1531" height="876" alt="image" src="https://github.com/user-attachments/assets/d161fc5d-bb89-44eb-bc6e-b5ef436cfe20" />

                    fig3:Dashboard page

                    
***


Let me know if you want a version with markdown badges, screenshots, or sample data!

[1](https://github.com/DheerajKumarR/ToDo-app)
