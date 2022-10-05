module com.lutadam.studentmanagementapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.lutadam.studentmanagementapp to javafx.fxml;
    exports com.lutadam.studentmanagementapp;
}