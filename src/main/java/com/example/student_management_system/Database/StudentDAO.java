package com.example.student_management_system.Database;

import com.example.student_management_system.Exceptions.DuplicateStudentIDException;
import com.example.student_management_system.Models.Student;
import com.example.student_management_system.Exceptions.StudentNotFoundException;
import com.example.student_management_system.Exceptions.DatabaseOperationException;
import javafx.collections.ObservableList;

/**
 * Interface for student database operations.
 */
public interface StudentDAO {

    /**
     * Creates the students table if it does not exist.
     *
     * @throws DatabaseOperationException If there is a problem during table creation.
     */
    void createTableIfNotExists() throws DatabaseOperationException;

    /**
     * Inserts a new student into the database.
     *
     * @param student The student to insert.
     * @throws DatabaseOperationException If there is a problem during the insertion.
     * @throws DuplicateStudentIDException If there is a try of adding student with already existing ID.
     */
    void insertStudent(Student student) throws DatabaseOperationException, DuplicateStudentIDException;

    /**
     * Loads all students from the database.
     *
     * @return An observable list of students.
     * @throws DatabaseOperationException If there is a problem during the query.
     */
    ObservableList<Student> loadStudents() throws DatabaseOperationException;

    /**
     * Updates a student in the database.
     *
     * @param student The student to update.
     * @throws StudentNotFoundException   If the student with the given ID is not found.
     * @throws DatabaseOperationException If there is a problem during the update.
     */
    void updateStudent(Student student) throws StudentNotFoundException, DatabaseOperationException;

    /**
     * Deletes a student from the database.
     *
     * @param studentID The ID of the student to delete.
     * @throws StudentNotFoundException   If the student with the given ID is not found.
     * @throws DatabaseOperationException If there is a problem during the deletion.
     */
    void deleteStudent(String studentID) throws StudentNotFoundException, DatabaseOperationException;
}
