package com.example.student_management_system.Controllers;

import com.example.student_management_system.Exceptions.DatabaseOperationException;
import com.example.student_management_system.Exceptions.DuplicateStudentIDException;
import com.example.student_management_system.Exceptions.StudentNotFoundException;
import com.example.student_management_system.Exceptions.ValidationException;
import com.example.student_management_system.Models.Student;
import com.example.student_management_system.Database.StudentDAOImpl;
import com.example.student_management_system.Utils.Validator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the student form view.
 * Handles adding, editing, and deleting student data.
 */
public class StudentFormController {

    @FXML
    private TextField inputStudentId;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputAge;

    @FXML
    private TextField inputGrade;

    @FXML
    private Label labelError;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnSwitchToMain;

    @FXML
    private Label labelHeader;

    @FXML
    private Button btnDelete;

    private final StudentDAOImpl studentDAO = new StudentDAOImpl();

    private boolean isEditing = false;
    private Student currentStudent;

    /**
     * Initializes the controller and sets up the form.
     */
    @FXML
    public void initialize() {
        try {
            studentDAO.createTableIfNotExists();
        } catch (DatabaseOperationException e) {
            showAlert("Initialization Error", "Error initializing database: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Configures the form for adding a new student.
     */
    public void setAddingMode() {
        this.isEditing = false;
        this.currentStudent = null;
        btnDelete.setVisible(false);
        labelHeader.setText("Add Student");
    }

    /**
     * Configures the form for editing an existing student.
     *
     * @param student The student to edit.
     */
    public void setEditingMode(Student student) {
        this.isEditing = true;
        this.currentStudent = student;
        populateFormFields(student);
        btnDelete.setVisible(true);
        labelHeader.setText("Edit Student");
    }

    /**
     * Populates the form fields with the data of the given student.
     *
     * @param student The student whose data is used to populate the form.
     */
    private void populateFormFields(Student student) {
        inputStudentId.setText(student.getStudentID());
        inputName.setText(student.getName());
        inputAge.setText(String.valueOf(student.getAge()));
        inputGrade.setText(String.valueOf(student.getGrade()));
        inputStudentId.setDisable(true);
    }

    /**
     * Handles the submission of the form, adding or updating a student.
     */
    @FXML
    private void handleFormSubmission() {
        try {
            String studentID = inputStudentId.getText().trim();
            String name = inputName.getText().trim();
            String ageText = inputAge.getText().trim();
            String gradeText = inputGrade.getText().trim();

            // Validate the input fields
            Validator.validateStudentFields(studentID, name, ageText, gradeText);

            int age = Integer.parseInt(ageText);
            double grade = Double.parseDouble(gradeText);

            if (isEditing) {
                updateStudent(new Student(name, age, grade, studentID));
            } else {
                addStudent(new Student(name, age, grade, studentID));
            }
        } catch (ValidationException e) {
            showAlert("Validation Error", e.getMessage(), Alert.AlertType.WARNING);
        } catch (DuplicateStudentIDException e) {
            showAlert("Duplicate ID Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (DatabaseOperationException | StudentNotFoundException e) {
            showAlert("Database Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    /**
     * Adds a new student to the database.
     *
     * @param student The student to add.
     * @throws DatabaseOperationException If a database error occurs.
     */
    private void addStudent(Student student) throws DatabaseOperationException, DuplicateStudentIDException {
        studentDAO.insertStudent(student);
        showAlert("Success", "Student added successfully!", Alert.AlertType.INFORMATION);
        navigateToMainView();
    }

    /**
     * Updates the data of an existing student in the database.
     *
     * @param student The student with updated data.
     * @throws StudentNotFoundException   If the student with the given ID is not found.
     * @throws DatabaseOperationException If a database error occurs.
     */
    private void updateStudent(Student student) throws StudentNotFoundException, DatabaseOperationException {
        studentDAO.updateStudent(student);
        showAlert("Success", "Student updated successfully!", Alert.AlertType.INFORMATION);
        navigateToMainView();
    }

    /**
     * Handles deleting the current student from the database.
     */
    @FXML
    private void handleDelete() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this student?", ButtonType.YES, ButtonType.NO);
        confirmationAlert.setTitle("Delete Confirmation");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    studentDAO.deleteStudent(currentStudent.getStudentID());
                    showAlert("Success", "Student deleted successfully!", Alert.AlertType.INFORMATION);
                    navigateToMainView();
                } catch (StudentNotFoundException e) {
                    showAlert("Student Not Found", e.getMessage(), Alert.AlertType.ERROR);
                } catch (DatabaseOperationException e) {
                    showAlert("Error Deleting Student", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }

    /**
     * Navigates back to the main view.
     */
    @FXML
    private void navigateToMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/student_management_system/main-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnSwitchToMain.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert("Navigation Error", "Error navigating to main view: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Displays an alert dialog with the specified title, content, and type.
     *
     * @param title     The title of the alert.
     * @param content   The content of the alert.
     * @param alertType The type of the alert (e.g., information, error).
     */
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
