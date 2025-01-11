package com.example.student_management_system.Database;

import com.example.student_management_system.Models.Student;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface StudentDAO {
    void createTableIfNotExists() throws SQLException;

    void insertStudent(Student student) throws SQLException;

    ObservableList<Student> loadStudents() throws SQLException;

    void updateStudent(Student student) throws SQLException;

    void deleteStudent(String studentID) throws SQLException;
}
