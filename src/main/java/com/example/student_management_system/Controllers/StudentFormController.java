package com.example.student_management_system.Controllers;

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
import java.sql.SQLException;

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

    @FXML
    public void initialize() {
        try {
            studentDAO.createTableIfNotExists();
        } catch (SQLException e) {
            labelError.setText("Error initializing database: " + e.getMessage());
        }
    }

    public void setAddingMode() {
        this.isEditing = false;
        this.currentStudent = null;
        btnDelete.setVisible(false);
        labelHeader.setText("Add Student");
    }

    public void setEditingMode(Student student) {
        this.isEditing = true;
        this.currentStudent = student;
        populateFormFields(student);
        btnDelete.setVisible(true);
        labelHeader.setText("Edit Student");
    }

    private void populateFormFields(Student student) {
        inputStudentId.setText(student.getStudentID());
        inputName.setText(student.getName());
        inputAge.setText(String.valueOf(student.getAge()));
        inputGrade.setText(String.valueOf(student.getGrade()));
        inputStudentId.setDisable(true);
    }

    @FXML
    private void handleFormSubmission() {
        try {
            String studentID = inputStudentId.getText().trim();
            String name = inputName.getText().trim();
            String ageText = inputAge.getText().trim();
            String gradeText = inputGrade.getText().trim();

            Validator.validateStudentFields(studentID, name, ageText, gradeText);

            int age = Integer.parseInt(ageText);
            double grade = Double.parseDouble(gradeText);

            if (isEditing) {
                updateStudent(new Student(name, age, grade, studentID));
            } else {
                addStudent(new Student(name, age, grade, studentID));
            }
        } catch (IllegalArgumentException e) {
            labelError.setText(e.getMessage());
        } catch (SQLException e) {
            labelError.setText("Database error: " + e.getMessage());
        }
    }

    private void addStudent(Student student) throws SQLException {
        studentDAO.insertStudent(student);
        showAlert("Success", "Student added successfully!", Alert.AlertType.INFORMATION);
        navigateToMainView();
    }

    private void updateStudent(Student student) throws SQLException {
        studentDAO.updateStudent(student);
        showAlert("Success", "Student updated successfully!", Alert.AlertType.INFORMATION);
        navigateToMainView();
    }

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
                } catch (SQLException e) {
                    labelError.setText("Error deleting student: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void navigateToMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/student_management_system/main-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnSwitchToMain.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            labelError.setText("Error navigating to main view: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
