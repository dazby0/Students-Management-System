package com.example.student_management_system.Controllers;

import com.example.student_management_system.StudentManagerImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;

public class MainController {
    @FXML
    private Button btn_addStudentForm;
    @FXML
    private Button btn_calculateAverage;

    @FXML
    public void switchAddStudentFormScene(ActionEvent event) throws IOException {
        Parent sceneRoot = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/com/example/student_management_system/student-form-view.fxml"),
                "FXML file not found"
        ));

        Scene addStudentScene = new Scene(sceneRoot);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(addStudentScene);
    }
}
