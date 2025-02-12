

# Expense Tracker API

A robust REST API for tracking personal expenses built with Spring Boot, featuring JWT authentication and comprehensive expense management capabilities.
## Features
- User authentication with JWT
- Expense tracking with categories
- Date range filtering for expenses
- Category-based expense filtering
- Secure API endpoints
- User-specific expense management
## Technologies
- Java 17
- Spring Boot 3
- PostreSQL
- Spring Security
- JWT Authentication
- Spring Data JPA
- H2/MySQL Database
- Maven
- JUnit 5
- Mockito
##  Prerequisites
- Java JDK 17 or higher
- Maven 
- Your favorite IDE 
- Postman (for testing API endpoints)

## Getting Started

1. Clone the repository:
```bash
git clone https://github.com/mahmudmammad/Simbrella.git
```

2. Navigate to the project directory:
```bash
cd <Folder_Path>
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`
## ## API Endpoints
## Request/Response Examples

### Register User
```json
POST /api/auth/register
{
    "username": "user1",
    "password": "password123"
}
```

### Login
```json
POST /api/auth/login
{
    "username": "user1",
    "password": "password123"
}
```
### Create Expense
```json
POST /api/expenses
Request:
{
    "amount": 100.00,
    "description": "Grocery shopping",
    "category": "Food"
}

Response: (201 Created)
{
    "id": 1,
    "amount": 100.00,
    "description": "Grocery shopping",
    "category": "Food",
    "createdAt": "2024-01-20T10:30:00",
    "updatedAt": "2024-01-20T10:30:00"
}
```

### Get All Expenses
```json
GET /api/expenses

Response: (200 OK)
[
    {
        "id": 1,
        "amount": 100.00,
        "description": "Grocery shopping",
        "category": "Food",
        "createdAt": "2024-01-20T10:30:00",
        "updatedAt": "2024-01-20T10:30:00"
    },
    {
        "id": 2,
        "amount": 50.00,
        "description": "Gas",
        "category": "Transportation",
        "createdAt": "2024-01-20T11:30:00",
        "updatedAt": "2024-01-20T11:30:00"
    }
]
```

### Get Expense by ID
```json
GET /api/expenses/{id}

Response: (200 OK)
{
    "id": 1,
    "amount": 100.00,
    "description": "Grocery shopping",
    "category": "Food",
    "createdAt": "2024-01-20T10:30:00",
    "updatedAt": "2024-01-20T10:30:00"
}
```

### Update Expense
```json
PUT /api/expenses/{id}
Request:
{
    "amount": 120.00,
    "description": "Grocery shopping and household items",
    "category": "Food"
}

Response: (200 OK)
{
    "id": 1,
    "amount": 120.00,
    "description": "Grocery shopping and household items",
    "category": "Food",
    "createdAt": "2024-01-20T10:30:00",
    "updatedAt": "2024-01-20T12:30:00"
}
```

### Delete Expense
```json
DELETE /api/expenses/{id}

Response: (200 OK)
{
    "message": "Expense with ID 1 has been successfully deleted."
}
```

### Get Expenses by Date Range
```json
GET /api/expenses/date-range?startDate=2024-01-01&endDate=2024-01-31

Response: (200 OK)
[
    {
        "id": 1,
        "amount": 100.00,
        "description": "Grocery shopping",
        "category": "Food",
        "createdAt": "2024-01-20T10:30:00",
        "updatedAt": "2024-01-20T10:30:00"
    },
    {
        "id": 2,
        "amount": 50.00,
        "description": "Gas",
        "category": "Transportation",
        "createdAt": "2024-01-20T11:30:00",
        "updatedAt": "2024-01-20T11:30:00"
    }
]
```

### Get Expenses by Category
```json
GET /api/expenses/category?category=Food

Response: (200 OK)
[
    {
        "id": 1,
        "amount": 100.00,
        "description": "Grocery shopping",
        "category": "Food",
        "createdAt": "2024-01-20T10:30:00",
        "updatedAt": "2024-01-20T10:30:00"
    },
    {
        "id": 3,
        "amount": 75.00,
        "description": "Restaurant dinner",
        "category": "Food",
        "createdAt": "2024-01-21T19:30:00",
        "updatedAt": "2024-01-21T19:30:00"
    }
]
```
## Testing
Run the tests using:
```bash
mvn test
```

The project includes:
- Unit tests
- Integration tests
- Controller tests
- Service layer tests
# JPA Properties
 - spring.jpa.hibernate.ddl-auto=update
 - spring.jpa.show-sql=true

# JWT Configuration
jwt.expiration=864000000
```

# Error Handling

The API includes comprehensive error handling for:
- Resource not found
- Authentication failures
- Validation errors
- Duplicate resources
- General server errors
