# Car Showroom Management System

## Overview

The Car Showroom Management System is a web-based application for managing car showrooms and their associated vehicles. Users can perform various operations such as adding, editing, and deleting showrooms and cars. The application supports role-based access control, ensuring only authorized users can access specific functionalities.

---

## Project Structure

### Backend (`showroom-management`)
The backend is a Spring Boot application that serves as the API provider for the system. It includes the following components:

1. **Controllers** (`/rest`):
    - Handles HTTP requests and responses.
    - Example: `CarRest.java`, `CarShowroomRest.java`.

2. **Services** (`/api`):
    - Contains business logic.
    - Example: `CarService.java`, `CarShowroomService.java`.

3. **Repositories** (`/dao/repository`):
    - Handles database interactions using Spring Data JPA.
    - Example: `CarRepository.java`, `CarShowroomRepository.java`.

4. **Entities** (`/dao/models`):
    - Represents database tables as Java objects.
    - Example: `CarEntity.java`, `CarShowroomEntity.java`.

5. **DTOs** (`/api/models`):
    - Data transfer objects for encapsulating data sent between client and server.
    - Example: `CarDTO.java`, `CarShowroomDTO.java`.

6. **Specifications** (`/specification`):
    - Used for dynamic filtering and querying.
    - Example: `CarSpecification.java`.

### Frontend (`/car-showroom-frontend`)
The frontend is an Angular application for the user interface. It includes:

1. **Components** (`/app/pages`):
    - Encapsulates UI elements and logic.
    - Example: `ShowroomListComponent`, `CarListComponent`.

2. **Services** (`/app/services`):
    - Manages API calls and data handling.
    - Example: `CarService`, `CarShowroomService`.

3. **Models** (`/app/models`):
    - Defines data structures used in the application.
    - Example: `CarModel`, `ShowroomModel`.

4. **Routing** (`/app/app-routing.module.ts`):
    - Defines application routes.
    - Example:
      ```typescript
      { path: 'showrooms', component: ShowroomListComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
      ```

5. **Guards** (`/app/guards`):
    - Implements role-based access control.
    - Example: `AuthGuard`.
---

## Setup Instructions

### Prerequisites
- **Backend**:
   - Java 17+
   - Maven 3.8+
   - MySQL 8.0+
- **Frontend**:
   - Node.js 16+
   - Angular CLI 13+
  
  ---

### Database Setup

1. **Create a MySQL database**:
   ```sql
   CREATE DATABASE car_showroom_db;
   
2. **Update the application.properties file with your database credentials**:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/car_showroom_db
   spring.datasource.username=root
   spring.datasource.password=pass@123

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

3. **Populate the database**:
   - The application uses Hibernate for automatic schema generation. On the first run, it will create the required tables in the database.
   - To insert initial data:
     - Use the SQL scripts provided for inserting dummy data, or
     - Access the admin dashboard to manually create showrooms and cars.

### Backend Setup
1. **Navigate to the backend directory**:
   ```bash
   cd backend
   
2. **Build the application using Maven**:
   ```bash
   mvn clean install
   
3. **Start the Spring Boot application**:
   ```bash
   mvn spring-boot:run
   
4. **By default, the backend API will run on** http://localhost:8080.


### Frontend Setup
1. **Navigate to the frontend directory**:
   ```bash
   cd frontend

2. **Install the dependencies**:
   ```bash
   npm install
   
3. **Start the Angular development server**:
   ```bash
   ng serve

4. **The frontend will be accessible at** http://localhost:4200.

---

## Database Migrations with Flyway
This project uses Flyway for database version control. Flyway helps manage and apply schema changes and seed data across different environments in a consistent manner.

**How Flyway Works**:
1. **Migration Scripts**:
   - Migration scripts are stored in the `src/main/resources/db/migration` directory.
   - Each script is named in the format `V{version}__{description}.sql`, e.g., `V1__Initial_Schema.sql`.
   - These scripts are applied sequentially based on their version numbers.

2. **Baseline and Migrations**:
   - If the database already exists, you can use `baseline-on-migrate` to start managing migrations without applying initial scripts.
   - New migration scripts are automatically applied during application startup.
   
**Adding a New Migration**:
   1. Create a new SQL file in the `db/migration` directory.
   2. Name it using the `V{version}__{description}.sql` format, where:
      - `version`: Incremental version number (e.g., `V2` for the next migration).
      - `description`: A short description of the migration's purpose.
   3. Add your SQL statements to the script.

**Running Migrations**:

Flyway migrations are automatically executed when the application starts. To apply migrations manually:
1. Run the following Maven command:
    ```bash
    mvn flyway:migrate
   
**Validating and Repairing Migrations**:
- To check the status of migrations:
    ```bash
    mvn flyway:info
- To validate migration scripts:
    ```bash
    mvn flyway:validate
- To repair the Flyway schema history table in case of mismatches:
    ```bash
    mvn flyway:repair

**Troubleshooting**:
- If a migration fails, check the error logs to identify the problematic script.
- Use flyway:repair to fix checksum mismatches.

---

# Testing the Application
1. Open a browser and navigate to http://localhost:4200.
2. Use the provided credentials for login:
   - Admin: admin / test@123
   - User: user / test@123
3. Test the functionality:
   - As an Admin, you can:
      - Add, edit, and delete showrooms.
      - Add, edit, and delete cars.
   - As a User, you can:
     - View the list of showrooms and cars.
     - Search for specific showrooms or cars.

---

# Assumptions

1. Role-Based Access Control:
   - Two roles are implemented: ADMIN and USER.
   - Admins have full access, while Users have limited read-only access.
2. Soft Deletion:
   - When deleting a showroom or a car, the system marks the entry as "deleted" instead of permanently removing it.
3. Search and Pagination:
   - Showrooms and cars can be filtered using search inputs.
   - Both listings are paginated for optimal performance.
4. Dynamic Filters:
   - Filters can be applied through query parameters to narrow down the list of cars or showrooms.

---
# Troubleshooting
- Cannot Connect to Database:
  - Ensure that the database credentials in application.properties are correct.
  - Verify that the MySQL server is running.
- Frontend Does Not Start:
  - Ensure that all dependencies are installed by running npm install.
  - Check for any Angular-specific errors in the console and resolve them.
- Backend API Errors:
  - Check the console logs for detailed error messages.
  - Ensure the database schema is up-to-date.

---
## Contact

For any issues or further assistance, feel free to contact the project maintainers:

- **Email**: support@carshowroomapp.com


Thank you for using the Car Showroom Management System!
