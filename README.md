Uni Management

Overview
UniManagement is a user-friendly gradebook-like application, designed to allow multiple types of users (e.g., students, teachers, professors/admins) to securely log in and interact with the system through custom interfaces tailored to their roles. Data for the specific roles and related aspects is stored in MongoDB, while the interface is built using JSwing.

Features
User Role-Based Login
Secure login system with MongoDB for data storage
Role-based access to ensure users see only the information relevant to them
Authentication and authorization to enhance security

Interactive User Dashboards
Students: View grades, view CGPA, edit password.
TA: Manage student grades based on their section.
Admins: Manage student grades, TAs overall data, and check analytics data

Tech Stack
Java Swing: For a responsive and clean graphical user interface
MongoDB: As the NoSQL database for data management
JSwing Forms: Custom forms designed for each user type
Java: Core application logic and business rules implementation

Installation
Clone the Repository:

bash
Copy code
git clone https://github.com/your-username/markbook-clone.git
cd markbook-clone
Set Up MongoDB:

Install MongoDB locally or connect to a cloud-hosted MongoDB service
Create a database named UniManagement with collections for users, tas, and professors.
Run the Application:

Open the project in IntelliJ or your preferred IDE.
Build the project and run the main class to start the application.
Usage
Logging In:
Students, Teachers, and Admins can log in with their credentials, which are stored securely in the database.
Dashboards:
After login, each user type will have access to tailored functionality:
Students: View grades, view CGPA, edit password.
TA: Manage student grades based on their section.
Admins: Manage student grades, TAs overall data, and check analytics data
