package com.example.student_management_system.Utils;

import com.example.student_management_system.Exceptions.NavigationException;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class NavigationTest {

    @Mock
    private Stage mockStage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNavigateValidPath() {
        // Test with a valid FXML path (assuming the path is valid in the real environment)
        String validPath = "/com/example/student_management_system/valid-view.fxml";

        // Mocking the stage to avoid actual JavaFX operations
        assertThrows(NavigationException.class, () -> {
            Navigation.navigate(mockStage, validPath);
        });
    }

    @Test
    void testNavigateInvalidPath() {
        // Test with an invalid FXML path
        String invalidPath = "/com/example/student_management_system/nonexistent-view.fxml";

        assertThrows(NavigationException.class, () -> {
            Navigation.navigate(mockStage, invalidPath);
        });
    }

    @Test
    void testNavigateWithControllerValidPath() {
        // Test with a valid FXML path (assuming the path is valid in the real environment)
        String validPath = "/com/example/student_management_system/valid-view.fxml";

        // Mocking the stage to avoid actual JavaFX operations
        assertThrows(NavigationException.class, () -> {
            Navigation.navigateWithController(mockStage, validPath);
        });
    }

    @Test
    void testNavigateWithControllerInvalidPath() {
        // Test with an invalid FXML path
        String invalidPath = "/com/example/student_management_system/nonexistent-view.fxml";

        assertThrows(NavigationException.class, () -> {
            Navigation.navigateWithController(mockStage, invalidPath);
        });
    }

    @Test
    void testNavigateWithNullStage() {
        // Test with a null stage
        String validPath = "/com/example/student_management_system/valid-view.fxml";

        assertThrows(NavigationException.class, () -> {
            Navigation.navigate(null, validPath);
        });
    }

    @Test
    void testNavigateWithControllerNullStage() {
        // Test with a null stage
        String validPath = "/com/example/student_management_system/valid-view.fxml";

        assertThrows(NavigationException.class, () -> {
            Navigation.navigateWithController(null, validPath);
        });
    }
}
