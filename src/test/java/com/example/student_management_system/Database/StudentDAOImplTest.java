package com.example.student_management_system.Database;

import com.example.student_management_system.Exceptions.DuplicateStudentIDException;
import com.example.student_management_system.Exceptions.StudentNotFoundException;
import com.example.student_management_system.Exceptions.DatabaseOperationException;
import com.example.student_management_system.Models.Student;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentDAOImplTest {

    private StudentDAOImpl studentDAO;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentDAO = new StudentDAOImpl();

        // Set the mock connection for testing
        DatabaseConnection.setMockConnection(mockConnection);
    }

    @AfterEach
    void tearDown() {
        // Reset the mock connection after tests
        DatabaseConnection.resetMockConnection();
    }


    @Test
    void testCreateTableIfNotExistsSuccess() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.execute(anyString())).thenReturn(true); // Simulate successful execution

        DatabaseConnection.setMockConnection(mockConnection);

        assertDoesNotThrow(() -> studentDAO.createTableIfNotExists());

        verify(mockStatement, times(1)).execute(anyString());
    }


    @Test
    void testCreateTableIfNotExistsFailure() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        doThrow(new SQLException("Error creating table")).when(mockStatement).execute(anyString());

        DatabaseConnection.setMockConnection(mockConnection);

        DatabaseOperationException exception = assertThrows(DatabaseOperationException.class, studentDAO::createTableIfNotExists);
        assertTrue(exception.getMessage().contains("Failed to create the students table"));
    }

    @Test
    void testInsertStudentSuccess() throws Exception {
        Student student = new Student("John Doe", 20, 3.5, "S001");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful update

        DatabaseConnection.setMockConnection(mockConnection);

        assertDoesNotThrow(() -> studentDAO.insertStudent(student));

        verify(mockPreparedStatement, times(1)).setString(1, student.getName());
        verify(mockPreparedStatement, times(1)).setInt(2, student.getAge());
        verify(mockPreparedStatement, times(1)).setDouble(3, student.getGrade());
        verify(mockPreparedStatement, times(1)).setString(4, student.getStudentID());
    }


    @Test
    void testInsertStudentDuplicateID() throws Exception {
        Student student = new Student("Jane Doe", 22, 4.0, "S002");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        doThrow(new SQLException("UNIQUE constraint failed")).when(mockPreparedStatement).executeUpdate();

        DatabaseConnection.setMockConnection(mockConnection);

        DuplicateStudentIDException exception = assertThrows(DuplicateStudentIDException.class, () -> studentDAO.insertStudent(student));
        assertTrue(exception.getMessage().contains("A student with ID 'S002' already exists"));
    }

    @Test
    void testLoadStudentsSuccess() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("name")).thenReturn("Alice");
        when(mockResultSet.getInt("age")).thenReturn(21);
        when(mockResultSet.getDouble("grade")).thenReturn(3.8);
        when(mockResultSet.getString("studentID")).thenReturn("S003");

        DatabaseConnection.setMockConnection(mockConnection);

        ObservableList<Student> students = assertDoesNotThrow(() -> studentDAO.loadStudents());

        assertEquals(1, students.size());
        assertEquals("Alice", students.get(0).getName());
    }

    @Test
    void testUpdateStudentSuccess() throws Exception {
        Student student = new Student("Bob", 23, 3.2, "S004");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        DatabaseConnection.setMockConnection(mockConnection);

        assertDoesNotThrow(() -> studentDAO.updateStudent(student));
    }

    @Test
    void testUpdateStudentNotFound() throws Exception {
        Student student = new Student("Bob", 23, 3.2, "S005");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        DatabaseConnection.setMockConnection(mockConnection);

        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class, () -> studentDAO.updateStudent(student));
        assertTrue(exception.getMessage().contains("No student found with ID: S005"));
    }

    @Test
    void testDeleteStudentSuccess() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        DatabaseConnection.setMockConnection(mockConnection);

        assertDoesNotThrow(() -> studentDAO.deleteStudent("S006"));
    }

    @Test
    void testDeleteStudentNotFound() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        DatabaseConnection.setMockConnection(mockConnection);

        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class, () -> studentDAO.deleteStudent("S007"));
        assertTrue(exception.getMessage().contains("No student found with ID: S007"));
    }
}
