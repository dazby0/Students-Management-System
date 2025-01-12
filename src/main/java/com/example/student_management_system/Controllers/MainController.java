package com.example.student_management_system.Controllers;

import com.example.student_management_system.Exceptions.DatabaseOperationException;
import com.example.student_management_system.Exceptions.NavigationException;
import com.example.student_management_system.Models.Student;
import com.example.student_management_system.Database.StudentDAOImpl;
import com.example.student_management_system.Utils.CalculateAverage;
import com.example.student_management_system.Utils.Navigation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller class for the main view of the application.
 * Handles the display of all students and navigation to the student form view.
 */
public class MainController {

    // Table view for displaying all students
    @FXML
    TableView<Student> table_allStudents;

    // Table columns for student properties
    @FXML
    TableColumn<Student, String> col_studentID;
    @FXML
    TableColumn<Student, String> col_name;
    @FXML
    TableColumn<Student, Integer> col_age;
    @FXML
    TableColumn<Student, Double> col_grade;

    // DAO for interacting with the database
    private final StudentDAOImpl studentDAO = new StudentDAOImpl();

    /**
     * Initializes the controller, setting up the table and event handlers.
     */
    @FXML
    public void initialize() {
        setupTableColumns();
        loadStudentsIntoTable();
        setupTableDoubleClickHandler();
    }

    /**
     * Configures the table columns to bind to the properties of the Student model.
     */
    private void setupTableColumns() {
        col_studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        col_grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
    }

    /**
     * Loads all students from the database and populates the table view.
     * Handles database errors by logging them to the console.
     */
    void loadStudentsIntoTable() {
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
     * Allows editing the selected student's details.
     *
     * @param event The mouse event triggered by a double-click.
     */
    private void handleRowDoubleClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            Student selectedStudent = table_allStudents.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                navigateToEditStudentForm(selectedStudent);
            }
        }
    }

    /**
     * Navigates to the student form view in adding mode.
     *
     * @param event The action event triggered by clicking the "Add Student" button.
     */
    @FXML
    void navigateToAddStudentForm(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            StudentFormController controller = Navigation.navigateWithController(stage, "/com/example/student_management_system/student-form-view.fxml");
            controller.setAddingMode();
        } catch (NavigationException e) {
            showAlert("Navigation Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Navigates to the student form view in editing mode.
     * Passes the selected student's data to the form for editing.
     *
     * @param student The student to edit.
     */
    void navigateToEditStudentForm(Student student) {
        try {
            Stage stage = (Stage) table_allStudents.getScene().getWindow();
            StudentFormController controller = Navigation.navigateWithController(stage, "/com/example/student_management_system/student-form-view.fxml");
            controller.setEditingMode(student);
        } catch (NavigationException e) {
            showAlert("Navigation Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Calculates the average grade of all students and displays it in an alert dialog.
     * If no students are present, shows a message indicating this.
     *
     * @param event The action event triggered by clicking the "Calculate Average" button.
     */
    @FXML
    void handleCalculateAverage(ActionEvent event) {
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
