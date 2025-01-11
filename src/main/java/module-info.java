module com.example.student_management_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.student_management_system.Models to javafx.base;
    exports com.example.student_management_system.Models;
    opens com.example.student_management_system.Controllers to javafx.fxml;
    exports com.example.student_management_system.Controllers;
    exports com.example.student_management_system;
    exports com.example.student_management_system.Database;
}