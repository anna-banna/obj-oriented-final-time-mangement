module com.example.time_management_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.time_management_project to javafx.fxml;
    exports com.example.time_management_project;
}