Electro Bill - Electricity Bill Payment System

Overview
Electro Bill is a Java-based application for electricity bill payment designed to enhance the management of electricity bill payments, user interactions, and service appointments. It serves utility companies and consumers needing efficient management of billing processes, enhancing operational efficiency and customer satisfaction.

Purpose
The primary goal of this system is to offer a comprehensive solution for electricity billing that includes:
- Efficient bill processing and payment
- Managing whitelists and blacklists
- Appointment scheduling for services like meter readings or repairs
- Feedback collection to improve service

Architecture
The project uses the Model-View-Controller (MVC) architectural pattern, which separates data handling, user interfaces, and control logic to enhance code organization:

Model
Includes data-related logic with classes such as User, Client, Bill, and Appointment, managing data interactions and ensuring robust database connectivity.

View
Responsible for the graphical interface, providing tailored interfaces for different user roles:
- AdminUI
- ManagerUI
- TechnicianUI
- ClientUI

Controller
Handles business logic, responding to user inputs and updating models. Key controllers include:
- Login - Manages user authentication
- Signup - Handles user registration
- Controllers for managing user interactions, bill processing, and payment functionalities

Key Features
- Bill Management: Supports automated and manual billing processes.
- Payment System: Facilitates secure and timely payments of electricity bills.
- User Management: Manages various user roles with specific functionalities and access levels.
- Appointment System: Handles scheduling and management of service-related appointments.
- Feedback System: Collects and manages user feedback to improve services and customer satisfaction.

Conclusion
This Electricity Bill Payment System is designed to simplify and automate the billing and payment processes for utility companies and consumers, enhancing administrative efficiency and customer service through an integrated management platform.
