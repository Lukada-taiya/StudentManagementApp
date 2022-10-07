package com.lutadam.studentmanagementapp;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    private Button availableGrade_btnAdd;

    @FXML
    private Button availableGrade_btnClear;

    @FXML
    private Button availableGrade_btnDelete;

    @FXML
    private Button availableGrade_btnUpdate;

    @FXML
    private TableColumn<?, ?> availableGrade_colCourse;

    @FXML
    private TableColumn<?, ?> availableGrade_colCouse;

    @FXML
    private TableColumn<?, ?> availableGrade_colDegree;

    @FXML
    private AnchorPane availableGrade_page;

    @FXML
    private TextField availableGrade_taDegree;

    @FXML
    private TextArea availableGrade_taDescription;

    @FXML
    private TableView<?> availableGrade_table;

    @FXML
    private TextField availableGrade_tfCourse;

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

}
