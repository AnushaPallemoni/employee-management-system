# 👥 Employee Management System
**Full Stack Web Application | Spring Boot + PostgreSQL + HTML/CSS/JS**

---

## 📁 Project Structure
```
EmployeeManagementSystem/
├── src/
│   ├── main/
│   │   ├── java/com/ems/
│   │   │   ├── EmployeeManagementSystemApplication.java  ← Main class
│   │   │   ├── model/
│   │   │   │   └── Employee.java                         ← Entity (DB table)
│   │   │   ├── repository/
│   │   │   │   └── EmployeeRepository.java               ← DB queries
│   │   │   ├── service/
│   │   │   │   ├── EmployeeService.java                  ← Interface
│   │   │   │   └── EmployeeServiceImpl.java              ← Business logic
│   │   │   ├── controller/
│   │   │   │   └── EmployeeController.java               ← REST API endpoints
│   │   │   └── exception/
│   │   │       ├── ResourceNotFoundException.java
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       ├── application.properties                    ← DB config
│   │       └── static/
│   │           ├── index.html                            ← Frontend UI
│   │           ├── css/style.css
│   │           └── js/app.js
│   └── test/
│       └── java/com/ems/
│           └── EmployeeManagementSystemApplicationTests.java
└── pom.xml                                               ← Maven dependencies
```

---

## ⚙️ Setup Instructions

### STEP 1: Install Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL 13+
- Eclipse IDE (with Spring Tools Suite plugin)

### STEP 2: Create PostgreSQL Database
Open pgAdmin or psql and run:
```sql
CREATE DATABASE employee_db;
```

### STEP 3: Configure Database
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=postgres
spring.datasource.password=YOUR_ACTUAL_PASSWORD
```

### STEP 4: Import into Eclipse
1. Open Eclipse
2. File → Import → Maven → Existing Maven Projects
3. Browse to the `EmployeeManagementSystem` folder
4. Click Finish
5. Right-click project → Maven → Update Project

### STEP 5: Run the Application
- Right-click `EmployeeManagementSystemApplication.java`
- Run As → Java Application
- Open browser: **http://localhost:8080**

---

## 🌐 REST API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST   | /api/employees | Create new employee |
| GET    | /api/employees | Get all employees |
| GET    | /api/employees/{id} | Get employee by ID |
| PUT    | /api/employees/{id} | Update employee |
| DELETE | /api/employees/{id} | Delete employee |
| GET    | /api/employees/search?keyword=John | Search employees |
| GET    | /api/employees/count | Get total count |
| GET    | /api/employees/department/{dept} | Filter by department |

---

## 🧪 Test with Postman

**Create Employee (POST):**
```json
POST http://localhost:8080/api/employees
Content-Type: application/json

{
  "firstName": "Rahul",
  "lastName": "Sharma",
  "email": "rahul@company.com",
  "phoneNumber": "9876543210",
  "department": "Engineering",
  "jobTitle": "Software Engineer",
  "salary": 75000,
  "status": "Active"
}
```

---

## 🚀 Push to GitHub

```bash
cd EmployeeManagementSystem
git init
git add .
git commit -m "Initial commit: Employee Management System"
git remote add origin https://github.com/YOUR_USERNAME/employee-management-system.git
git push -u origin main
```

---

## 🛠 Tech Stack
- **Backend:** Spring Boot 3.2, Spring Data JPA, Spring Validation
- **Database:** PostgreSQL
- **Frontend:** HTML5, CSS3, Vanilla JavaScript (Fetch API)
- **Build Tool:** Maven
- **IDE:** Eclipse
