# Student Management System

The **Student Management System** is a desktop application designed to streamline the management of student records, providing a simple and intuitive interface for adding, updating, retrieving, and deleting student information. This system ensures data consistency and accuracy while allowing easy database interaction.

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Database Setup](#database-setup)
- [Usage](#usage)

---

## Features

The Student Management System offers the following functionalities:

1. **Add Students**: Add new student records with validation for proper inputs.
2. **View Students**: Retrieve and display a list of all students in the system.
3. **Update Student Information**: Modify existing student records.
4. **Delete Students**: Remove student records based on their unique student ID.
5. **Average Grade Calculation**: Calculate the average grade of all students.


## Installation

Follow these steps to set up the project:

1. **Install IntelliJ IDEA**  
   Download and install [IntelliJ IDEA](https://www.jetbrains.com/idea/download/), an integrated development environment for Java projects.

2. **Download Java Development Kit (JDK)**  
   Ensure you have JDK installed. You can download it from [Oracle](https://www.oracle.com/java/technologies/downloads/). The recommended version is JDK 22.

3. **Download JavaFX SDK (22.0.1)**  
   - Download the JavaFX package from [Gluon](https://gluonhq.com/products/javafx/).
   - Configure JavaFX in your IDE:
     - Go to `File -> Project Structure`.
     - Under `Libraries`, add the JavaFX `lib` folder.

4. **SQLite JDBC Driver**  
   - Download the SQLite JDBC driver from [Maven Repository](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc).
   - Add the JAR file to your project:
     - Place it in a `libs` directory in your project.
     - Add it to the project configuration under `Libraries`.

5. **JUnit**  
   The project uses JUnit 5 for testing. Add the following dependencies to your `pom.xml` file if you're using Maven:
   ```xml
   <dependency>
       <groupId>org.junit.jupiter</groupId>
       <artifactId>junit-jupiter</artifactId>
       <version>5.10.0</version>
       <scope>test</scope>
   </dependency>
   <dependency>
       <groupId>org.xerial</groupId>
       <artifactId>sqlite-jdbc</artifactId>
       <version>3.41.2</version>
   </dependency>


## Database Setup
The project uses SQLite as its database. The students table is created automatically when the application is first launched. If necessary, you can manually execute the following SQL command to set up the table:
```sql
CREATE TABLE IF NOT EXISTS students (
    name TEXT NOT NULL,
    age INTEGER NOT NULL,
    grade REAL NOT NULL,
    studentID TEXT NOT NULL UNIQUE PRIMARY KEY
);
```

# Usage

1. **Launch the Application**:
   * Open the project in IntelliJ IDEA
   * Run the `Main` class

2. **Navigate Through the Interface**:
   * Add, update, or delete student records via the user-friendly interface
   * Calculate the average grade for all students

3. **Testing**:
   * Unit tests are available in the `test` directory. To run tests:
     * Right-click the `src/test` folder and select `Run All Tests`

# Technologies Used

* **JavaFX**: For creating the user interface
* **SQLite**: As the lightweight relational database
* **JUnit 5**: For unit testing


# Code Architecture

Provide a brief overview of the architecture of your application:

* **MVC Pattern:** The project follows the Model-View-Controller design pattern to ensure separation of concerns
* **Models:** Represent the data structure (e.g., `Student`)
* **Views:** JavaFX FXML files for the graphical user interface
* **Controllers:** Handle the logic for user interactions and updates to the view


# Screenshots

![image](https://github.com/user-attachments/assets/cb275424-3722-4a03-b4fb-e55793d7f702)
![image](https://github.com/user-attachments/assets/d6ba18e9-b326-425c-a76a-670a767dfee0)
![image](https://github.com/user-attachments/assets/13eeb5cc-d45e-4dc4-9cb5-3d395c47b8c4)
![image](https://github.com/user-attachments/assets/f9d71ade-3b5a-4609-9a39-0e3035cb9561)
![image](https://github.com/user-attachments/assets/17e83240-998d-4693-938b-48914a88e50e)


# License

This project is licensed under the MIT License. See the LICENSE file for details.
