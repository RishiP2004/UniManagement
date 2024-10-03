## Uni Management

# Overview
UniManagement is a user-friendly gradebook-like application, designed to allow multiple types of roles (students, teachers, professors, admins) to securely log in and interact with the system through custom interfaces tailored to their roles. Data for the specific roles and related aspects is stored in MongoDB, while the interface is built using JSwing. 

# Features
User Role-Based Login
Secure login system with MongoDB for data storage
Role-based access to ensure users see only the information relevant to them
Authentication and authorization to enhance security

# Interactive User Dashboards
Students: View grades, view CGPA, edit password.
TA: Manage student grades based on their section.
Professors: Manage student grades, TAs overall data, and check analytics data.
Admins: Handle import/export of data through MSExcel files.

# Tech Stack
MongoDB: As the NoSQL database for data management
JSwing Forms: For a responsive and clean graphical user interface
Java: Core application logic and business rules implementation

# Testing
Tested for over 25k users

Mockito: Used for Unit testing
testcontainers: Used for Integration testing


# Installation
Clone the Repository:

bash
Copy code
git clone https://github.com/RishiP2004/UniManagement.git
cd UniManagement

Set Up MongoDB:

Install MongoDB locally or connect to a cloud-hosted MongoDB service.
Create a database named UniManagement with collections for users, tas, professors.
(Recommended) Manually insert the admin collection with a user and password. 
Have MSExcel files for student, professor, and ta data ready to import following role structure.

Set up JFreeChart:

Install JFreeChart library

#Run the Application:

Open the project in IntelliJ or your preferred IDE.
Build the project and run the main class to start the application.

# Usage
Logging In:
Students, Teachers, Professors and Admins can log in with their credentials, which are stored securely in the database.
Dashboards:
After login, each user type will have access to tailored functionality:
Students: View grades, view CGPA, edit password.
TA: Manage student grades based on their section.
Professors: Manage student grades, TAs overall data, and check analytics data.
Admins: Import specific MSExcel file data for a specific role.
