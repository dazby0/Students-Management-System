package com.example.student_management_system.Database;

import com.example.student_management_system.Exceptions.DuplicateStudentIDException;
import com.example.student_management_system.Models.Student;
import com.example.student_management_system.Exceptions.StudentNotFoundException;
import com.example.student_management_system.Exceptions.DatabaseOperationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Implementation of the StudentDAO interface for managing student records in the database.
 * Provides methods for creating, inserting, updating, retrieving, and deleting student data.
 */
public class StudentDAOImpl implements StudentDAO {

    /**
     * Creates the 'students' table if it does not exist in the database.
     *
     * @throws DatabaseOperationException If a database error occurs while creating the table.
     */
    @Override
    public void createTableIfNotExists() throws DatabaseOperationException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                "name TEXT NOT NULL, " +
                "age INTEGER NOT NULL, " +
                "grade REAL NOT NULL, " +
                "studentID TEXT NOT NULL UNIQUE PRIMARY KEY" +
                ");";

        try (Connection connection = DatabaseConnection.connect();
             Statement stmt = connection.createStatement()) {

            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create the students table.", e);
        }
    }

    /**
     * Inserts a new student into the database.
     *
     * @param student The student to be inserted.
     * @throws DatabaseOperationException    If a database error occurs during the insertion.
     * @throws DuplicateStudentIDException If a student with the same ID already exists.
     */
    @Override
    public void insertStudent(Student student) throws DatabaseOperationException, DuplicateStudentIDException {
        String sql = "INSERT INTO students (name, age, grade, studentID) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());
            pstmt.setDouble(3, student.getGrade());
            pstmt.setString(4, student.getStudentID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                throw new DuplicateStudentIDException("A student with ID '" + student.getStudentID() + "' already exists.");
            }
            throw new DatabaseOperationException("Failed to insert the student into the database.", e);
        }
    }

    /**
     * Retrieves all students from the database.
     *
     * @return An observable list of students.
     * @throws DatabaseOperationException If a database error occurs during the retrieval.
     */
    @Override
    public ObservableList<Student> loadStudents() throws DatabaseOperationException {
        ObservableList<Student> students = FXCollections.observableArrayList();
        String sql = "SELECT * FROM students";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                double grade = resultSet.getDouble("grade");
                String studentID = resultSet.getString("studentID");

                students.add(new Student(name, age, grade, studentID));
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to load students from the database.", e);
        }
        return students;
    }

    /**
     * Updates an existing student in the database.
     *
     * @param student The updated student data.
     * @throws StudentNotFoundException   If the student with the given ID is not found.
     * @throws DatabaseOperationException If a database error occurs during the update.
     */
    @Override
    public void updateStudent(Student student) throws StudentNotFoundException, DatabaseOperationException {
        String sql = "UPDATE students SET name = ?, age = ?, grade = ? WHERE studentID = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());
            pstmt.setDouble(3, student.getGrade());
            pstmt.setString(4, student.getStudentID());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new StudentNotFoundException("No student found with ID: " + student.getStudentID());
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update the student in the database.", e);
        }
    }

    /**
     * Deletes a student from the database.
     *
     * @param studentID The ID of the student to delete.
     * @throws StudentNotFoundException   If the student with the given ID is not found.
     * @throws DatabaseOperationException If a database error occurs during the deletion.
     */
    @Override
    public void deleteStudent(String studentID) throws StudentNotFoundException, DatabaseOperationException {
        String sql = "DELETE FROM students WHERE studentID = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, studentID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new StudentNotFoundException("No student found with ID: " + studentID);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete the student from the database.", e);
        }
    }
}
