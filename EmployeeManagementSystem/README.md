Employee Management System
Overview
The Employee Management System is a simple Java web application that allows users to add, view, update, and delete employee records. The system uses HTML for the front-end, CSS for styling, Java for back-end processing, JDBC for database connectivity, and MySQL for storing employee information.

Features:
Add new employee records.
View all employee records in a table.
Update existing employee records.
Delete employee records.
Technologies Used
Frontend: HTML, CSS
Backend: Java (JDBC for database connectivity)
Database: MySQL
Prerequisites
Before running the application, ensure that you have the following installed on your system:

MySQL: To store and manage employee records.
JDK (Java Development Kit): To compile and run the Java code.
Tomcat: (Optional) To host the Java Servlet-based application (if running on a web server).
A web browser: To view the HTML pages.


Setup and Installation

1. Setting Up the Database
To get started, you'll need to set up the EmployeeDB database and create the Employees table in MySQL.

Open MySQL and run the following queries to create the database and table.
-- Create database
CREATE DATABASE EmployeeDB;

-- Use the database
USE EmployeeDB;

-- Create the Employees table
CREATE TABLE Employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL
);

-- Insert sample employee records
INSERT INTO Employees (name, position, salary) 
VALUES 
('Borsha', 'Manager', 75000),
('Ferdaus', 'Developer', 50000),
('Sadia', 'Designer', 45000),
('Tanzim', 'MD', 40000),
('Tamim', 'HR', 55000);



2. Java Application Setup
The backend Java code is designed to handle database operations like adding, viewing, updating, and deleting employee records. Below is the general structure:

Database Connection (JDBC): The Java application connects to MySQL using JDBC. Make sure to add the JDBC driver (mysql-connector-java) to your project.

Java Servlet or Application: The backend logic handles requests from the frontend (HTML forms) and performs the necessary database operations using SQL queries.


Functionality

1. Add Employee
Page: add_employee.html
Function: Allows the user to add a new employee by entering their name, position, and salary.
Backend: The AddEmployee.java class processes the form data and inserts the new employee into the Employees table in the database.
Database Query:
INSERT INTO Employees (name, position, salary)
VALUES ('EmployeeName', 'EmployeePosition', EmployeeSalary);


2. View Employees
Page: view_employees.html
Function: Displays a table of all employees fetched from the database.
Backend: The ViewEmployee.java class retrieves all records from the Employees table and displays them in the HTML table.
Database Query:
SELECT * FROM Employees;

3. Update Employee
Page: update_employee.html
Function: Allows the user to select an employee by ID and update their details (name, position, salary).
Backend: The UpdateEmployee.java class processes the updated data and modifies the employee record in the database.
Database Query:
UPDATE Employees
SET name = 'UpdatedName', position = 'UpdatedPosition', salary = UpdatedSalary
WHERE id = EmployeeID;

4. Delete Employee
Page: delete_employee.html
Function: Allows the user to delete an employee record by entering the employee's ID.
Backend: The DeleteEmployee.java class processes the request and deletes the selected employee from the database.
Database Query:
DELETE FROM Employees WHERE id = EmployeeID;
Usage
Open the view_employees.html file in your browser to view all employees.
To add a new employee, navigate to the add_employee.html page and fill out the form.
Use the update_employee.html page to modify an existing employee's details.
If you want to remove an employee, use the delete_employee.html page.
Example Queries
Here are the SQL queries to insert the five employees' information into the Employees table:

INSERT INTO Employees (name, position, salary) 
VALUES 
('Borsha', 'Manager', 75000),
('Ferdaus', 'Developer', 50000),
('Sadia', 'Designer', 45000),
('Tanzim', 'MD', 40000),
('Tamim', 'HR', 55000);
Flowcharts
Add Employee Process:

Start
Fill out the form with employee details
Submit form
Add employee record to the database
Show success message
End
View Employees Process:

Start
Fetch employee records from the database
Display records in the table
End
Update Employee Process:

Start
Select employee to update
Modify employee details
Update record in the database
Show success message
End
Delete Employee Process:

Start
Enter employee ID to delete
Delete employee from the database
Show success message
End
License
This project is licensed under the MIT License - see the LICENSE file for details.

Acknowledgments
MySQL for database management.
Java Swing for GUI development (if needed).
JDBC for database connection.
HTML/CSS for building the frontend pages.