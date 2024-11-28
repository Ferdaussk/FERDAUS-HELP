1. Create Database
CREATE DATABASE EmployeeDB;

2. Use Database
USE EmployeeDB;

3. Create Employees Table
CREATE TABLE Employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL
);

4. Insert Employees
INSERT INTO Employees (name, position, salary) VALUES 
('Borsha', 'Manager', 60000),
('Sadia', 'Designer', 45000),
('Tanzim', 'MD', 40000),
('Tamim', 'HR', 55000);
('Ferdaus', 'Developer', 50000),

5. View All Employees
SELECT * FROM Employees;

6. Update an Employee
UPDATE Employees 
SET name = 'Borsha', position = 'Senior Developer', salary = 66000 
WHERE id = 1;

7. Delete an Employee
DELETE FROM Employees WHERE id = 1;
