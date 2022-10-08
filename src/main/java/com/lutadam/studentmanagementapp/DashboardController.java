package com.lutadam.studentmanagementapp;

import javafx.collections.FXCollections;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    private ComboBox<String> addStudents_cbGender;

    @FXML
    private ComboBox<String> addStudents_cbStatus;

    @FXML
    private ComboBox<String> addStudents_cbYear;

    @FXML
    private TableColumn<Student, String> addStudents_colBirthDate;

    @FXML
    private TableColumn<Student, String> addStudents_colCourse;

    @FXML
    private TableColumn<Student, String> addStudents_colFName;

    @FXML
    private TableColumn<Student, String> addStudents_colGender;

    @FXML
    private TableColumn<Student, String> addStudents_colId;

    @FXML
    private TableColumn<Student, String> addStudents_colLName;

    @FXML
    private TableColumn<Student, String> addStudents_colStatus;

    @FXML
    private TableColumn<Student, String> addStudents_colYear;

    @FXML
    private DatePicker addStudents_dtBirthDate;

    @FXML
    private ImageView addStudents_image;

    @FXML
    private TableView<Student> addStudents_table;

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
    private TableColumn<Course,String> availableCourse_colDescription;

    @FXML
    private TableColumn<Course,String> availableCourse_colCourse;

    @FXML
    private TableColumn<Course,String> availableCourse_colDegree;

    @FXML
    private TextField availableCourse_tfDegree;

    @FXML
    private TextArea availableCourse_taDescription;

    @FXML
    private TableView<Course> availableCourse_table;

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
    private TableColumn<Student, String> studentGrade_col_course;

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


    private ResultSet result;
    private ObservableList<Course> courseList;
    private double x;
    private double y;
    private Image image;
    private int[] yearList = {100,200,300,400,500,600};
    private String[] genderList = {"Male", "Female"};
    private String[] statusList = {"Enrolled", "Not Enrolled", "Inactive"};

    public void initialize() {
        btnHome.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
        showStudentData();
        populateGenderList();
        populateStatusList();
        populateYearList();
        showCourseData();
    }

    @FXML
    private void deleteCourse() {
        Course course = availableCourse_table.getSelectionModel().getSelectedItem();
        if(course != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete " + course.getName());
            Optional<ButtonType> response = alert.showAndWait();

            if(response.isPresent() && response.get().equals(ButtonType.OK)) {
                String query = "DELETE FROM course WHERE name = ?";
                DBUtils.insertDb(query, course.getName());
                courseList.remove(course);
                clearCourseForm();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Course Deleted");
                alert.setHeaderText(null);
                alert.setContentText("Course has been deleted successfully");
                alert.show();
            }
        }
    }

    private void updateCourseTable(Course course) {
        for(Course obj:courseList) {
            if(obj.getName().equals(course.getName())) {
                obj.setDegree(course.getDegree());
                obj.setDescription(course.getDescription());
            }
        }
    }

    @FXML
    private void clearCourseForm() {
        availableCourse_tfCourse.clear();
        availableCourse_taDescription.clear();
        availableCourse_tfDegree.clear();
        availableCourse_table.getSelectionModel().clearSelection();
    }

    @FXML
    private void updateCourse() {
        String name = availableCourse_tfCourse.getText();
        String description = availableCourse_taDescription.getText();
        String degree = availableCourse_tfDegree.getText();
        Alert alert;
        if(name.isEmpty() || description.isEmpty() || degree.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Course Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.show();
        }else {
            String query = "SELECT * FROM course WHERE name = ?";
            result = DBUtils.fetchDb(query, name);
            try {
                if(!result.isBeforeFirst()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Update Course Error");
                    alert.setHeaderText(null);
                    alert.setContentText("This course does not exist");
                    alert.show();
                }else {
                    String updateQuery = "UPDATE course SET description = ?,degree = ? WHERE name = ?";
                    DBUtils.insertDb(updateQuery, description, degree, name);
                    clearCourseForm();
                    updateCourseTable(new Course(name, description, degree));
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Course Updated");
                    alert.setHeaderText(null);
                    alert.setContentText("Course has been updated successfully");
                    alert.show();
                }
            }catch (SQLException e) { e.printStackTrace(); }
            DBUtils.closeAllResources();
        }
    }


    @FXML
    private void addCourse() {
        String name = availableCourse_tfCourse.getText().trim();
        String description = availableCourse_taDescription.getText().trim();
        String degree = availableCourse_tfDegree.getText().trim();
        Alert alert;
        if(name.isEmpty() || description.isEmpty() || degree.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add New Course Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.show();
        }else {
            String query = "SELECT * FROM course WHERE name = ?";
            result = DBUtils.fetchDb(query, name);
            try {
                if(result.isBeforeFirst()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Add New Course Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Course already exists");
                    alert.show();
                }else {
                    query = "INSERT INTO course(name,description,degree) VALUES(?,?,?)";
                    DBUtils.insertDb(query,name,description,degree);
                    clearCourseForm();
                    courseList.add(new Course(name, description, degree));
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Course Added");
                    alert.setHeaderText(null);
                    alert.setContentText("Course has been successfully added");
                    alert.show();
                }
            }catch (SQLException e) {e.printStackTrace();}
            DBUtils.closeAllResources();
        }

    }

    private ObservableList<Course> getCourseData() {
        courseList = FXCollections.observableArrayList();
        String query = "SELECT * FROM course";
        result = DBUtils.fetchDb(query);
        try {
            if(result.isBeforeFirst()) {
                while (result.next()) {
                    String name = result.getString("name");
                    String description = result.getString("description");
                    String degree = result.getString("degree");
                    Course course = new Course(name,description,degree);
                    courseList.add(course);
                }
            }
        }catch (SQLException e) {e.printStackTrace();}
        return  courseList;
    }

    @FXML
    private void showCourseData() {
        courseList = getCourseData();
        if(courseList.isEmpty()) return;
        availableCourse_colCourse.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableCourse_colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        availableCourse_colDegree.setCellValueFactory(new PropertyValueFactory<>("degree"));
        availableCourse_table.setItems(courseList);
    }

    @FXML
    private void showCourseDataInForm() {
        Course course = availableCourse_table.getSelectionModel().getSelectedItem();
        if(course != null) {
            availableCourse_tfCourse.setText(course.getName());
            availableCourse_tfDegree.setText(course.getDegree());
            availableCourse_taDescription.setText(course.getDescription());
        }
    }

    private ObservableList<Student> getStudentData() {
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        String query = "SELECT * FROM student";
        result = DBUtils.fetchDb(query);
        try {
            if(result.isBeforeFirst()) {
                while (result.next()) {
                    Student student = new Student(result.getInt("id_number"), result.getInt("year"),
                            result.getString("course"), result.getString("firstName"),
                            result.getString("lastName"), result.getString("gender"), result.getDate("birthdate"),
                            result.getString("status"), result.getString("image"));
                    studentList.add(student);
                }
            }
        }catch (SQLException e) { e.printStackTrace(); }
        DBUtils.closeAllResources();
        return studentList;
    }

    @FXML
    private void showStudentData() {
        ObservableList<Student> studentD = getStudentData();
        if(studentD.isEmpty()) return;
        addStudents_colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addStudents_colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        addStudents_colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        addStudents_colFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addStudents_colLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addStudents_colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addStudents_colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        addStudents_colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        addStudents_table.setItems(studentD);
    }

    //Shows student data in form to be edited on add students page
    @FXML
    private void showStudentDataInForm() {
        Student stu = addStudents_table.getSelectionModel().getSelectedItem();
        if(stu != null) {
            addStudents_tfId.setText(String.valueOf(stu.getId()));
            addStudents_tfFName.setText(stu.getFirstName());
            addStudents_tfLName.setText(stu.getLastName());
            String uri = "file:/home/lukada/Documents/workspace/StudentManagementApp/src/main/resources/com/lutadam/studentmanagementapp/images/"
                    + stu.getImage();
            image = new Image(uri, 164,200, false, true);
            addStudents_image.setImage(image);
        }
    }

    private void populateYearList() {

        List<String> list = new ArrayList<>();
        for(int year: yearList) {
            list.add(String.valueOf(year));
        }
        ObservableList<String> obList = FXCollections.observableArrayList(list);
        addStudents_cbYear.setItems(obList);
    }

    private void populateStatusList() {
        List<String> list = new ArrayList<>();
        for(String status: statusList) {
            list.add(String.valueOf(status));
        }
        ObservableList<String> obList = FXCollections.observableArrayList(list);
        addStudents_cbStatus.setItems(obList);
    }

    private void populateGenderList() {
        List<String> list = new ArrayList<>();
        for(String gender: genderList) {
            list.add(String.valueOf(gender));
        }
        ObservableList<String> obList = FXCollections.observableArrayList(list);
        addStudents_cbGender.setItems(obList);
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
