# Employee Management System - Version 2

## Overview
This version of the Employee Management System introduces improvements in database interactions and API handling using **JDBC** and **PostgreSQL**. The system allows CRUD operations (Create, Read, Update, Delete) on employee records through a **Java Servlet-based REST API**.

## Features
- **Database Integration:** Uses JDBC to connect with a PostgreSQL database.
- **Structured API Endpoints:** RESTful API to manage employee records.
- **Efficient Data Handling:** Employees' hobbies are stored as an array in the database.
- **Improved Error Handling:** Enhanced exception management for stability.

## Database Connection
The system connects to a **PostgreSQL** database using **JDBC**. The connection is handled via a dedicated class that ensures a stable connection. The database schema includes an `employees` table with fields for ID, name, gender, date of birth, phone number, and hobbies.

## API Endpoints
The API follows RESTful principles and allows interaction with employee records. Below are the main endpoints:

- **GET /api/v1/employees/** → Retrieve all employees.
- **GET /api/v1/employees/{id}** → Retrieve an employee by ID.
- **POST /api/v1/employees/** → Add a new employee.
- **PUT /api/v1/employees/{id}** → Update an existing employee.
- **DELETE /api/v1/employees/{id}** → Remove an employee from the database.

## How to Test with Postman
1. **Start the Server:** Ensure the application is running.
2. **Send Requests:** Use **Postman** to test API operations:
   - **Retrieve All Employees:** Send a `GET` request to `/api/v1/employees/`.
   - **Retrieve by ID:** Send a `GET` request to `/api/v1/employees/{id}`.
   - **Create a New Employee:** Send a `POST` request with a JSON body.
   - **Update an Employee:** Send a `PUT` request with updated fields.
   - **Delete an Employee:** Send a `DELETE` request to `/api/v1/employees/{id}`.

## Summary
This version improves database interaction and API stability. It provides a well-structured, maintainable, and scalable solution for managing employee data using JDBC and PostgreSQL.