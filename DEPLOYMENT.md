# Deployment Guide

## Prerequisites

- Payara Server 6
- MySQL 8 running on localhost:3306
- Java 21
- Maven 3.8+

## Step 1 — Set Up MySQL Database

Open MySQL Workbench and run:

```sql
CREATE DATABASE IF NOT EXISTS HR_Db;
USE HR_Db;

CREATE TABLE IF NOT EXISTS department (
    dept_id   INT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS employee (
    emp_id    INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(150) NOT NULL,
    title     VARCHAR(100) NOT NULL,
    dept_id   INT NOT NULL,
    salary    DECIMAL(10,2) NOT NULL,
    hire_date DATE NOT NULL,
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

INSERT INTO department (dept_id, dept_name) VALUES (1,'Engineering'),(2,'Human Resources'),(3,'Marketing'),(4,'Finance'),(5,'Operations');

INSERT INTO employee (emp_id, full_name, title, dept_id, salary, hire_date) VALUES
(1,'Alice Johnson','Software Engineer',1,95000.00,'2021-03-15'),
(2,'Bob Smith','Senior Developer',1,115000.00,'2019-07-01'),
(3,'Carol White','HR Manager',2,82000.00,'2020-01-10'),
(4,'David Brown','Recruiter',2,67000.00,'2022-05-20'),
(5,'Eva Martinez','Marketing Lead',3,88000.00,'2020-09-14'),
(6,'Frank Lee','Content Strategist',3,72000.00,'2021-11-03'),
(7,'Grace Kim','Financial Analyst',4,91000.00,'2018-06-25'),
(8,'Henry Davis','Accountant',4,76000.00,'2023-02-01'),
(9,'Isla Thompson','Operations Manager',5,98000.00,'2017-04-18'),
(10,'James Wilson','Logistics Coordinator',5,63000.00,'2022-08-30');
```

## Step 2 — Configure Payara JDBC Resource

In Payara Admin Console (`http://localhost:4848`):

1. **Resources → JDBC → JDBC Connection Pools → MySqlHrPool**
    - DatabaseName: `HR_Db`
    - ServerName: `localhost`
    - portNumber: `3306`
    - user: `root`
2. **Resources → JDBC → JDBC Resources** — confirm `jdbc/HrDS` points to `MySqlHrPool`
3. Click **Ping** on `MySqlHrPool` to verify the connection

## Step 3 — Create Payara File Realm Users

In Payara Admin Console:

**Configurations → server-config → Security → Realms → file → Manage Users**

| Username | Password | Group |
|----------|----------|-------|
| user1 | password | USER |
| admin1 | password | ADMIN |

## Step 4 — Build and Deploy

```bash
mvn clean package
```

Then in Payara Admin Console:
- **Applications → Deploy → Upload** `target/FinalProject.war`

## Step 5 — Quick Test

| Action | URL | Login |
|--------|-----|-------|
| View departments | `http://localhost:8080/FinalProject/departments.xhtml` | user1 or admin1 |
| View employees | `http://localhost:8080/FinalProject/employees.xhtml` | user1 or admin1 |
| Edit employee | Click Edit on any employee row | admin1 only |
| Denied test | Try edit URL as user1 | → 403 Forbidden |