# E-commerce Application

This is a **Java-based e-commerce application** designed for managing products, orders, and user interactions. Built using the **Spring Boot framework**, it provides robust features for backend development and seamless integration.

---

## Features
- **User Authentication and Authorization**
- **Product Management** (CRUD operations)
- **Order Processing and Management**
- **RESTful APIs** for frontend integration
- **Database Persistence** using JPA and Hibernate

---

## Project Structure
- `src/`: Contains the source code for the application.
- `pom.xml`: Maven configuration file for dependency management.
- `.mvn/` and `mvnw*`: Maven wrapper for consistent builds.
- `target/`: Directory for compiled files and artifacts.
- `.idea/`: IntelliJ IDEA project configuration files.

---

## Prerequisites
- **Java** 17 or higher
- **Maven** 3.8+
- **Database**: MySQL/PostgreSQL (or any other configured database)

---

## To ENABLE GOOGLE OAUTH2
1. ADD CLIENT ID AND SECRET KEY TO application.properties 

##  Enable Mail 
1. Add your mail configuration in application.properties to use mail features in the api.
---

## Getting Started
**1. Clone the repository:**
git clone <repository-url>
cd Ecommerce

**2. Install dependencies:**
./mvnw clean install

**3. Set up the database:**

Update the application.properties file in src/main/resources with your database details.

**4. Run the application:**
./mvnw spring-boot:run


