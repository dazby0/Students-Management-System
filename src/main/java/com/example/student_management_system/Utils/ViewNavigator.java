//package com.example.student_management_system.Utils;
//
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.net.URL;
//
//public class ViewNavigator {
//
//    public static void switchScene(Stage stage, String fxmlPath) throws IOException {
//        // Remove the leading slash and use ClassLoader
//        URL resource = ViewNavigator.class.getClassLoader().getResource(fxmlPath);
//        System.out.println("Resolved path: " + resource);
//        if (resource == null) {
//            throw new IOException("FXML file not found: " + fxmlPath);
//        }
//        FXMLLoader loader = new FXMLLoader(resource);
//        Parent root = loader.load();
//        stage.setScene(new Scene(root));
//    }
//
//
//    public static <T> T switchSceneWithController(Stage stage, String fxmlPath) throws IOException {
//        FXMLLoader loader = new FXMLLoader(ViewNavigator.class.getClassLoader().getResource(fxmlPath));
//        Parent root = loader.load();
//        stage.setScene(new Scene(root));
//        return loader.getController();
//    }
//}
//
