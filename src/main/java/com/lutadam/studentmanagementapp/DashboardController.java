package com.lutadam.studentmanagementapp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class DashboardController {

    @FXML
    private AnchorPane addStudent_page;

    @FXML
    private Button addStudents_btnAdd;

    @FXML
    private Button addStudents_btnClear;

    @FXML
    private Button addStudents_btnDelete;

    @FXML
    private Button addStudents_btnInsert;

    @FXML
    private Button addStudents_btnUpdate;

    @FXML
    private ComboBox<?> addStudents_cbCourse;

    @FXML
    private ComboBox<?> addStudents_cbGender;

    @FXML
    private ComboBox<?> addStudents_cbStatus;

    @FXML
    private ComboBox<?> addStudents_cbYear;

    @FXML
    private TableColumn<?, ?> addStudents_colBirthDate;

    @FXML
    private TableColumn<?, ?> addStudents_colCourse;

    @FXML
    private TableColumn<?, ?> addStudents_colFName;

    @FXML
    private TableColumn<?, ?> addStudents_colGender;

    @FXML
    private TableColumn<?, ?> addStudents_colId;

    @FXML
    private TableColumn<?, ?> addStudents_colLName;

    @FXML
    private TableColumn<?, ?> addStudents_colStatus;

    @FXML
    private TableColumn<?, ?> addStudents_colYear;

    @FXML
    private DatePicker addStudents_dtBirthDate;

    @FXML
    private ImageView addStudents_image;

    @FXML
    private TableView<?> addStudents_table;

    @FXML
    private TextField addStudents_tfFName;

    @FXML
    private TextField addStudents_tfId;

    @FXML
    private TextField addStudents_tfLName;

    @FXML
    private TextField addStudents_tfSearch;

    @FXML
    private AnchorPane availableCourse_page;

    @FXML
    private Button availableCourse_btnAdd;

    @FXML
    private Button availableCourse_btnClear;

    @FXML
    private Button availableCourse_btnDelete;

    @FXML
    private Button availableCourse_btnUpdate;

    @FXML
    private TableColumn<?, ?> availableCourse_colCouse;

    @FXML
    private TableColumn<?, ?> availableCourse_colDegree;

    @FXML
    private TextField availableCourse_taDegree;

    @FXML
    private TextArea availableCourse_taDescription;

    @FXML
    private TableView<?> availableCourse_table;

    @FXML
    private TextField availableCourse_tfCourse;


    @FXML
    private Button btnAddStudents;

    @FXML
    private Button btnAddStudentsGrades;

    @FXML
    private Button btnAvailableCourses;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button close;

    @FXML
    private AnchorPane dashbooard;

    @FXML
    private Label home_enrolledFemale;

    @FXML
    private AreaChart<?, ?> home_enrolledFemaleChart;

    @FXML
    private Label home_enrolledMale;

    @FXML
    private LineChart<?, ?> home_enrolledMaleChart;

    @FXML
    private AnchorPane home_page;

    @FXML
    private Label home_totalEnrolled;

    @FXML
    private BarChart<?, ?> home_totalEnrolledChart;

    @FXML
    private Label lbUsername;

    @FXML
    private AnchorPane main_page;

    @FXML
    private Button minimize;

    @FXML
    private Button studentGrade_btnUpdate;

    @FXML
    private TableColumn<?, ?> studentGrade_col_course;

    @FXML
    private TableColumn<?, ?> studentGrade_col_firstSem;

    @FXML
    private TableColumn<?, ?> studentGrade_col_id;

    @FXML
    private TableColumn<?, ?> studentGrade_col_secondSem;

    @FXML
    private TableColumn<?, ?> studentGrade_col_year;

    @FXML
    private AnchorPane studentGrade_page;

    @FXML
    private TextField studentGrade_search;

    @FXML
    private TableView<?> studentGrade_table;

    @FXML
    private Button studentGrade_tfClear;

    @FXML
    private Label studentGrade_tfCourse;

    @FXML
    private TextField studentGrade_tfFirstSem;

    @FXML
    private TextField studentGrade_tfId;

    @FXML
    private TextField studentGrade_tfSecondSem;

    @FXML
    private Label studentGrade_tfYear;


    private double x;
    private double y;

    public void initialize() {
        btnHome.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
    }

    public ObservableList<Student> getStudentData() {
        String query = "SELECT * FROM student";
        DBUtils.fetchDb(query);
    }

    @FXML
    private void logOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get().equals(ButtonType.OK)) {
            try{
                Parent root = FXMLLoader.load(getClass().getResource("mainview.fxml"));
                Scene scene = new Scene(root, 600, 400);
                Stage stage = new Stage();
                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.5);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setTitle("Student Management App");
                stage.setScene(scene);
                main_page.getScene().getWindow().hide();
                stage.show();
            }catch (IOException e) { e.printStackTrace();}


        }
    }

    private void setNavHover(Button button) {
        if(button == btnHome) {
            btnHome.setOnMouseEntered((MouseEvent event) -> {
                btnHome.setStyle("-fx-cursor: hand;");
                btnHome.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            });
            btnHome.setOnMouseExited((MouseEvent event) -> {
                btnHome.setStyle("-fx-cursor: default;");
                btnHome.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            });
            btnAddStudentsGrades.setOnMouseEntered((MouseEvent event) -> {
                btnAddStudentsGrades.setStyle("-fx-cursor: hand;");
                btnAddStudentsGrades.setStyle("-fx-background-color: #1c535e;");
            });
            btnAddStudentsGrades.setOnMouseExited((MouseEvent event) -> {
                btnAddStudentsGrades.setStyle("-fx-cursor: default;");
                btnAddStudentsGrades.setStyle("-fx-background-color: transparent;");
            });
            btnAddStudents.setOnMouseEntered((MouseEvent event) -> {
                btnAddStudents.setStyle("-fx-cursor: hand;");
                btnAddStudents.setStyle("-fx-background-color: #1c535e;");
            });
            btnAddStudents.setOnMouseExited((MouseEvent event) -> {
                btnAddStudents.setStyle("-fx-cursor: default;");
                btnAddStudents.setStyle("-fx-background-color: transparent;");
            });
            btnAvailableCourses.setOnMouseEntered((MouseEvent event) -> {
                btnAvailableCourses.setStyle("-fx-cursor: hand;");
                btnAvailableCourses.setStyle("-fx-background-color: #1c535e;");
            });
            btnAvailableCourses.setOnMouseExited((MouseEvent event) -> {
                btnAvailableCourses.setStyle("-fx-cursor: default;");
                btnAvailableCourses.setStyle("-fx-background-color: transparent;");
            });
        }else if(button == btnAddStudents) {
            btnAddStudents.setOnMouseEntered((MouseEvent event) -> {
                btnAddStudents.setStyle("-fx-cursor: hand;");
                btnAddStudents.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            });
            btnAddStudents.setOnMouseExited((MouseEvent event) -> {
                btnAddStudents.setStyle("-fx-cursor: default;");
                btnAddStudents.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            });

            btnAddStudentsGrades.setOnMouseEntered((MouseEvent event) -> {
                btnAddStudentsGrades.setStyle("-fx-cursor: hand;");
                btnAddStudentsGrades.setStyle("-fx-background-color: #1c535e;");
            });
            btnAddStudentsGrades.setOnMouseExited((MouseEvent event) -> {
                btnAddStudentsGrades.setStyle("-fx-cursor: default;");
                btnAddStudentsGrades.setStyle("-fx-background-color: transparent;");
            });
            btnHome.setOnMouseEntered((MouseEvent event) -> {
                btnHome.setStyle("-fx-cursor: hand;");
                btnHome.setStyle("-fx-background-color: #1c535e;");
            });
            btnHome.setOnMouseExited((MouseEvent event) -> {
                btnHome.setStyle("-fx-cursor: default;");
                btnHome.setStyle("-fx-background-color: transparent;");
            });
            btnAvailableCourses.setOnMouseEntered((MouseEvent event) -> {
                btnAvailableCourses.setStyle("-fx-cursor: hand;");
                btnAvailableCourses.setStyle("-fx-background-color: #1c535e;");
            });
            btnAvailableCourses.setOnMouseExited((MouseEvent event) -> {
                btnAvailableCourses.setStyle("-fx-cursor: default;");
                btnAvailableCourses.setStyle("-fx-background-color: transparent;");
            });
        }else if(button == btnAddStudentsGrades) {
            btnAddStudentsGrades.setOnMouseEntered((MouseEvent event) -> {
                btnAddStudentsGrades.setStyle("-fx-cursor: hand;");
                btnAddStudentsGrades.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            });
            btnAddStudentsGrades.setOnMouseExited((MouseEvent event) -> {
                btnAddStudentsGrades.setStyle("-fx-cursor: default;");
                btnAddStudentsGrades.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            });

            btnHome.setOnMouseEntered((MouseEvent event) -> {
                btnHome.setStyle("-fx-cursor: hand;");
                btnHome.setStyle("-fx-background-color: #1c535e;");
            });
            btnHome.setOnMouseExited((MouseEvent event) -> {
                btnHome.setStyle("-fx-cursor: default;");
                btnHome.setStyle("-fx-background-color: transparent;");
            });
            btnAddStudents.setOnMouseEntered((MouseEvent event) -> {
                btnAddStudents.setStyle("-fx-cursor: hand;");
                btnAddStudents.setStyle("-fx-background-color: #1c535e;");
            });
            btnAddStudents.setOnMouseExited((MouseEvent event) -> {
                btnAddStudents.setStyle("-fx-cursor: default;");
                btnAddStudents.setStyle("-fx-background-color: transparent;");
            });
            btnAvailableCourses.setOnMouseEntered((MouseEvent event) -> {
                btnAvailableCourses.setStyle("-fx-cursor: hand;");
                btnAvailableCourses.setStyle("-fx-background-color: #1c535e;");
            });
            btnAvailableCourses.setOnMouseExited((MouseEvent event) -> {
                btnAvailableCourses.setStyle("-fx-cursor: default;");
                btnAvailableCourses.setStyle("-fx-background-color: transparent;");
            });
        }else if(button == btnAvailableCourses) {
            btnAvailableCourses.setOnMouseEntered((MouseEvent event) -> {
            btnAvailableCourses.setStyle("-fx-cursor: hand;");
                btnAvailableCourses.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            });
            btnAvailableCourses.setOnMouseExited((MouseEvent event) -> {
                btnAvailableCourses.setStyle("-fx-cursor: default;");
                btnAvailableCourses.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            });

            btnAddStudentsGrades.setOnMouseEntered((MouseEvent event) -> {
                btnAddStudentsGrades.setStyle("-fx-cursor: hand;");
                btnAddStudentsGrades.setStyle("-fx-background-color: #1c535e;");
            });
            btnAddStudentsGrades.setOnMouseExited((MouseEvent event) -> {
                btnAddStudentsGrades.setStyle("-fx-cursor: default;");
                btnAddStudentsGrades.setStyle("-fx-background-color: transparent;");
            });
            btnAddStudents.setOnMouseEntered((MouseEvent event) -> {
                btnAddStudents.setStyle("-fx-cursor: hand;");
                btnAddStudents.setStyle("-fx-background-color: #1c535e;");
            });
            btnAddStudents.setOnMouseExited((MouseEvent event) -> {
                btnAddStudents.setStyle("-fx-cursor: default;");
                btnAddStudents.setStyle("-fx-background-color: transparent;");
            });
            btnHome.setOnMouseEntered((MouseEvent event) -> {
                btnHome.setStyle("-fx-cursor: hand;");
                btnHome.setStyle("-fx-background-color: #1c535e;");
            });
            btnHome.setOnMouseExited((MouseEvent event) -> {
                btnHome.setStyle("-fx-cursor: default;");
                btnHome.setStyle("-fx-background-color: transparent;");
            });
        }
    }

    @FXML
    public void switchForm(MouseEvent event) {
        Button clickedBtn = (Button) event.getSource();
        AnchorPane currentPage;
        if(addStudent_page.isVisible()) {
            currentPage = addStudent_page;
        }else if(availableCourse_page.isVisible()) {
            currentPage = availableCourse_page;
        }else if(studentGrade_page.isVisible()) {
            currentPage = studentGrade_page;
        }else {
            currentPage = home_page;
        }

        if(clickedBtn == btnHome) {
            currentPage.setVisible(false);
            btnAddStudentsGrades.setStyle("-fx-background-color: transparent;");
            btnAddStudents.setStyle("-fx-background-color: transparent;");
            btnAvailableCourses.setStyle("-fx-background-color: transparent;");
            btnHome.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            setNavHover(btnHome);
            home_page.setVisible(true);
        }else if(clickedBtn == btnAddStudents) {
            currentPage.setVisible(false);
            btnAddStudentsGrades.setStyle("-fx-background-color: transparent;");
            btnHome.setStyle("-fx-background-color: transparent;");
            btnAvailableCourses.setStyle("-fx-background-color: transparent;");
            btnAddStudents.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            setNavHover(btnAddStudents);
            addStudent_page.setVisible(true);
        }else if(clickedBtn == btnAvailableCourses) {
            currentPage.setVisible(false);
            btnAddStudentsGrades.setStyle("-fx-background-color: transparent;");
            btnAddStudents.setStyle("-fx-background-color: transparent;");
            btnHome.setStyle("-fx-background-color: transparent;");
            btnAvailableCourses.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            setNavHover(btnAvailableCourses);
            availableCourse_page.setVisible(true);
        }else if(clickedBtn == btnAddStudentsGrades) {
            currentPage.setVisible(false);
            btnHome.setStyle("-fx-background-color: transparent;");
            btnAddStudents.setStyle("-fx-background-color: transparent;");
            btnAvailableCourses.setStyle("-fx-background-color: transparent;");
            btnAddStudentsGrades.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            setNavHover(btnAddStudentsGrades);
            studentGrade_page.setVisible(true);
        }

    }

    @FXML
    public void close() {
        System.exit(0);
    }
    @FXML
    public void minimize() {
        Stage stage = (Stage) main_page.getScene().getWindow();
        stage.setIconified(true);
    }

}
