package com.example.student_management_system.Controllers;

import com.example.student_management_system.Models.Student;
import com.example.student_management_system.Database.StudentDAOImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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

    @FXML
    public void initialize() {
        setupTableColumns();
        loadStudentsIntoTable();
        setupTableDoubleClickHandler();
    }

    private void setupTableColumns() {
        col_studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        col_grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
    }

    private void loadStudentsIntoTable() {
        try {
            ObservableList<Student> students = studentDAO.loadStudents();
            table_allStudents.setItems(students);
        } catch (SQLException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }

    private void setupTableDoubleClickHandler() {
        table_allStudents.setOnMouseClicked(this::handleRowDoubleClick);
    }

    private void handleRowDoubleClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            Student selectedStudent = table_allStudents.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                navigateToStudentForm(selectedStudent);
            }
        }
    }

    private void navigateToStudentForm(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/student_management_system/student-form-view.fxml"));
            Parent root = loader.load();
            StudentFormController controller = loader.getController();
            controller.setEditingMode(student);

            Stage stage = (Stage) table_allStudents.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Error navigating to student form: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddStudentForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/student_management_system/student-form-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            StudentFormController controller = loader.getController();
            controller.setAddingMode();
        } catch (IOException e) {
            System.out.println("Error navigating to add student form: " + e.getMessage());
        }
    }
}
