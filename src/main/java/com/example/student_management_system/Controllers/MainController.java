package com.example.student_management_system.Controllers;

import com.example.student_management_system.Exceptions.DatabaseOperationException;
import com.example.student_management_system.Exceptions.NavigationException;
import com.example.student_management_system.Models.Student;
import com.example.student_management_system.Database.StudentDAOImpl;
import com.example.student_management_system.Utils.CalculateAverage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the main view of the application.
 * Handles the display of all students and navigation to the student form view.
 */
public class MainController {

    @FXML
    private Button btn_addStudentForm;

    @FXML
    private TableView<Student> table_allStudents;

    @FXML
    private TableColumn<Student, String> col_studentID;

    @FXML
    private TableColumn<Student, String> col_name;

    @FXML
    private TableColumn<Student, Integer> col_age;

    @FXML
    private TableColumn<Student, Double> col_grade;

    private final StudentDAOImpl studentDAO = new StudentDAOImpl();

    /**
     * Initializes the controller and sets up the table and event handlers.
     */
    @FXML
    public void initialize() {
        setupTableColumns();
        loadStudentsIntoTable();
        setupTableDoubleClickHandler();
    }

    /**
     * Configures the table columns to bind to properties of the Student model.
     */
    private void setupTableColumns() {
        col_studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        col_grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
    }

    /**
     * Loads all students from the database and populates the table view.
     */
    private void loadStudentsIntoTable() {
        try {
            ObservableList<Student> students = studentDAO.loadStudents();
            table_allStudents.setItems(students);
        } catch (DatabaseOperationException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }

    /**
     * Sets up a double-click event handler for the table rows to edit a student.
     */
    private void setupTableDoubleClickHandler() {
        table_allStudents.setOnMouseClicked(this::handleRowDoubleClick);
    }

    /**
     * Handles a double-click event on a table row and navigates to the student form view.
     *
     * @param event The mouse event triggered by a double-click.
     */
    private void handleRowDoubleClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            Student selectedStudent = table_allStudents.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                navigateToStudentForm(selectedStudent);
            }
        }
    }

    /**
     * Navigates to the student form view in adding mode.
     *
     * @param event The action event triggered by clicking the "Add Student" button.
     */
    @FXML
    private void handleAddStudentForm(ActionEvent event) {
        try {
            navigateToView("/com/example/student_management_system/student-form-view.fxml", event, "add");
        } catch (NavigationException e) {
            showAlert("Navigation Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Navigates to the student form view in editing mode with the selected student's data.
     *
     * @param student The selected student to edit.
     */
    private void navigateToStudentForm(Student student) {
        try {
            navigateToView("/com/example/student_management_system/student-form-view.fxml", student, "edit");
        } catch (NavigationException e) {
            showAlert("Navigation Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Generic method to handle navigation to a specific view.
     *
     * @param fxmlPath The path to the FXML file.
     * @param data     The data to pass to the new view (optional).
     * @param mode     The mode of the view (e.g., "add" or "edit").
     * @throws NavigationException If an error occurs during navigation.
     */
    private void navigateToView(String fxmlPath, Object data, String mode) throws NavigationException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Pass data to the controller if needed
            if ("edit".equals(mode) && data instanceof Student) {
                StudentFormController controller = loader.getController();
                controller.setEditingMode((Student) data);
            } else if ("add".equals(mode)) {
                StudentFormController controller = loader.getController();
                controller.setAddingMode();
            }

            Stage stage = (Stage) table_allStudents.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new NavigationException("Failed to load the view: " + fxmlPath, e);
        }
    }

    /**
     * Calculates the average grade of all students and displays it in an alert dialog.
     *
     * @param event The action event triggered by clicking the "Calculate Average" button.
     */
    @FXML
    private void handleCalculateAverage(ActionEvent event) {
        try {
            ObservableList<Student> students = studentDAO.loadStudents();
            double averageGrade = CalculateAverage.calculateAverageGrade(students);

            if (averageGrade == -1) {
                showAlert("No Students", "There are no students to calculate the average grade.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Average Grade", "The average grade of all students is: " + String.format("%.2f", averageGrade), Alert.AlertType.INFORMATION);
            }
        } catch (DatabaseOperationException e) {
            showAlert("Database Error", "Failed to calculate the average grade: " + e.getMessage(), Alert.AlertType.ERROR);
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
