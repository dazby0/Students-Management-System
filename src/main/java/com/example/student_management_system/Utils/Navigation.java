package com.example.student_management_system.Utils;

import com.example.student_management_system.Exceptions.NavigationException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Utility class for handling navigation between views in the application.
 */
public class Navigation {

    /**
     * Navigates to the specified FXML view and sets the scene.
     *
     * @param stage    The stage to set the scene on.
     * @param fxmlPath The path to the FXML file.
     * @throws NavigationException If an error occurs during navigation.
     */
    public static void navigate(Stage stage, String fxmlPath) throws NavigationException {
        try {
            FXMLLoader loader = new FXMLLoader(Navigation.class.getResource(fxmlPath));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new NavigationException("Failed to load the view: " + fxmlPath, e);
        }
    }

    /**
     * Navigates to the specified FXML view, sets the scene, and returns the controller for further configuration.
     *
     * @param stage    The stage to set the scene on.
     * @param fxmlPath The path to the FXML file.
     * @param <T>      The type of the controller.
     * @return The controller of the loaded FXML view.
     * @throws NavigationException If an error occurs during navigation.
     */
    public static <T> T navigateWithController(Stage stage, String fxmlPath) throws NavigationException {
        try {
            FXMLLoader loader = new FXMLLoader(Navigation.class.getResource(fxmlPath));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            return loader.getController();
        } catch (IOException e) {
            throw new NavigationException("Failed to load the view: " + fxmlPath, e);
        }
    }
}
