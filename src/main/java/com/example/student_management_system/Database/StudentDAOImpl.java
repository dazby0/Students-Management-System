package com.example.student_management_system.Database;

import com.example.student_management_system.Models.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public void createTableIfNotExists() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                "name TEXT NOT NULL, " +
                "age INTEGER NOT NULL, " +
                "grade REAL NOT NULL, " +
                "studentID TEXT NOT NULL UNIQUE PRIMARY KEY" +
                ");";

        Connection connection = null;
        try {
            connection = DatabaseConnection.connect();
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTableSQL);
                System.out.println("Table 'students' is ready.");
            }
        } finally {
            DatabaseConnection.close(connection);
        }
    }

    @Override
    public void insertStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (name, age, grade, studentID) VALUES (?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = DatabaseConnection.connect();
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, student.getName());
                pstmt.setInt(2, student.getAge());
                pstmt.setDouble(3, student.getGrade());
                pstmt.setString(4, student.getStudentID());
                pstmt.executeUpdate();
            }
        } finally {
            DatabaseConnection.close(connection);
        }
    }

    @Override
    public ObservableList<Student> loadStudents() throws SQLException {
        ObservableList<Student> students = FXCollections.observableArrayList();
        String sql = "SELECT * FROM students";

        Connection connection = null;
        try {
            connection = DatabaseConnection.connect();
            try (PreparedStatement pstmt = connection.prepareStatement(sql);
                 ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    double grade = resultSet.getDouble("grade");
                    String studentID = resultSet.getString("studentID");

                    students.add(new Student(name, age, grade, studentID));
                }
            }
        } finally {
            DatabaseConnection.close(connection);
        }
        return students;
    }

    @Override
    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET name = ?, age = ?, grade = ? WHERE studentID = ?";

        Connection connection = null;
        try {
            connection = DatabaseConnection.connect();
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, student.getName());
                pstmt.setInt(2, student.getAge());
                pstmt.setDouble(3, student.getGrade());
                pstmt.setString(4, student.getStudentID());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("No student found with ID: " + student.getStudentID());
                }
            }
        } finally {
            DatabaseConnection.close(connection);
        }
    }

    @Override
    public void deleteStudent(String studentID) throws SQLException {
        String sql = "DELETE FROM students WHERE studentID = ?";

        Connection connection = null;
        try {
            connection = DatabaseConnection.connect();
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, studentID);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("No student found with ID: " + studentID);
                }
            }
        } finally {
            DatabaseConnection.close(connection);
        }
    }
}
