package com.lutadam.studentmanagementapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DashboardController {

    @FXML
    private AnchorPane addStudent_page;

    @FXML
    private ComboBox<String> addStudents_cbCourse;

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
    private TableColumn<Student, String> studentGrade_col_course;

    @FXML
    private TableColumn<Student, String> studentGrade_col_firstSem;

    @FXML
    private TableColumn<Student, String> studentGrade_col_id;

    @FXML
    private TableColumn<Student, String> studentGrade_col_secondSem;

    @FXML
    private TableColumn<Student, String> studentGrade_col_year;

    @FXML
    private TableColumn<Student,String> studentGrade_col_final;

    @FXML
    private AnchorPane studentGrade_page;

    @FXML
    private TextField studentGrade_search;

    @FXML
    private TableView<Student> studentGrade_table;

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
    private ObservableList<Student> studentList;
    private ObservableList<Student> studentGradeList;
    private double x;
    private double y;
    private String query;
    private final int[] yearList = {100,200,300,400,500,600};
    private final String[] genderList = {"Male", "Female"};
    private final String[] statusList = {"Enrolled", "Not Enrolled", "Inactive"};

    public void initialize() {
        btnHome.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
        displayUserName();
        showStudentData();
        populateGenderList();
        populateStatusList();
        populateYearList();
        populateCourseList();
        showCourseData();
        showStudentGradeData();
        showHomeData();
    }

    private void displayUserName() {
        lbUsername.setText(getImageData.username);
    }

    private void homeTotalEnrolledGraph() {
        home_totalEnrolledChart.getData().clear();
        query = "SELECT date,count(id) FROM `student` WHERE status = ? GROUP BY date LIMIT 5";
        XYChart.Series chart = new XYChart.Series();
        result = DBUtils.fetchDb(query, "Enrolled");
        try {
            if(result.isBeforeFirst()) {
                while (result.next()) {
                    chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
                }
            }
        }catch (SQLException e) { e.printStackTrace();}
        home_totalEnrolledChart.getData().add(chart);
        DBUtils.closeAllResources();
    }

    private void homeFemaleEnrolledGraph() {
        home_enrolledFemaleChart.getData().clear();
        query = "SELECT date,count(id) FROM `student` WHERE status = ? AND gender = ? GROUP BY date LIMIT 5";
        XYChart.Series chart = new XYChart.Series();
        result = DBUtils.fetchDb(query, "Enrolled", "Female");
        try {
            if(result.isBeforeFirst()) {
                while (result.next()) {
                    chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
                }
            }
        }catch (SQLException e) { e.printStackTrace();}
        home_enrolledFemaleChart.getData().add(chart);
        DBUtils.closeAllResources();
    }

    private void homeMaleEnrolledGraph() {
        home_enrolledMaleChart.getData().clear();
        query = "SELECT date,count(id) FROM `student` WHERE status = ? AND gender = ? GROUP BY date LIMIT 5";
        XYChart.Series chart = new XYChart.Series();
        result = DBUtils.fetchDb(query, "Enrolled", "Male");
        try {
            if(result.isBeforeFirst()) {
                while (result.next()) {
                    chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
                }
            }
        }catch (SQLException e) { e.printStackTrace();}
        home_enrolledMaleChart.getData().add(chart);
        DBUtils.closeAllResources();
    }

    @FXML
    private void showHomeData() {
        totalEnrolled();
        totalFemaleEnrolled();
        totalMaleEnrolled();
        homeFemaleEnrolledGraph();
        homeMaleEnrolledGraph();
        homeTotalEnrolledGraph();
    }
    private void totalEnrolled() {
        query = "SELECT count(id) FROM student WHERE status = ?";
        result = DBUtils.fetchDb(query, "Enrolled");
        try {
            if(result.next()) {
                int totalEnrolled = result.getInt("count(id)");
                home_totalEnrolled.setText(String.valueOf(totalEnrolled));
            }
        }catch (SQLException e) { e.printStackTrace(); }
        DBUtils.closeAllResources();
    }

    private void totalFemaleEnrolled() {
        query = "SELECT count(id) FROM student WHERE status = ? AND gender = ?";
        result = DBUtils.fetchDb(query, "Enrolled", "Female");
        try {
            if(result.next()) {
                int femaleEnrolled = result.getInt("count(id)");
                home_enrolledFemale.setText(String.valueOf(femaleEnrolled));
            }
        }catch (SQLException e) { e.printStackTrace(); }
        DBUtils.closeAllResources();
    }

    private void totalMaleEnrolled() {
        query = "SELECT count(id) FROM student WHERE status = ? AND gender = ?";
        result = DBUtils.fetchDb(query, "Enrolled", "Male");
        try {
            if(result.next()) {
                int maleEnrolled = result.getInt("count(id)");
                home_enrolledMale.setText(String.valueOf(maleEnrolled));
            }
        }catch (SQLException e) { e.printStackTrace(); }
        DBUtils.closeAllResources();
    }

    @FXML
    private void addStudentSearch() {
//        System.out.println("b====");
        FilteredList<Student> fList = new FilteredList<>(studentList, e -> true);

        addStudents_tfSearch.textProperty().addListener((observableValue, oldVal, newVal) -> fList.setPredicate(student -> {
            if (newVal == null || newVal.isEmpty()) {
                return true;
            }

            String searchKey = newVal.toLowerCase();
//                System.out.println("new " +newVal);
//                System.out.println("stu " + student.getFirstName());
            if (String.valueOf(student.getId()).contains(searchKey)) {
//                    System.out.println("Yes");
                return true;
            } else if (String.valueOf(student.getYear()).contains(searchKey)) {
                return true;
            } else if (student.getCourse().toLowerCase().contains(searchKey)) {
                return true;
            } else if (student.getFirstName().toLowerCase().contains(searchKey)) {
//                    System.out.println("Enters name");
                return true;
            } else if (student.getLastName().toLowerCase().contains(searchKey)) {
                return true;
            } else if (student.getGender().toLowerCase().contains(searchKey)) {
                return true;
            } else //                    System.out.println("false");
                if (String.valueOf(student.getBirthDate()).contains(searchKey)) {
                return true;
            } else return student.getStatus().toLowerCase().contains(searchKey);
//                System.out.println("NOpe false");
//                return false;
        }));

        SortedList<Student> sList = new SortedList<>(fList);
//        System.out.println(fList);
        sList.comparatorProperty().bind(addStudents_table.comparatorProperty());
//        System.out.println(sList);
//        System.out.println(fList.getPredicate());
        addStudents_table.setItems(sList);
//        System.out.println("e========");
    }

    @FXML
    private void studentGradeSearch() {
        FilteredList<Student> filteredList = new FilteredList<>(studentGradeList, e->true);
        studentGrade_search.textProperty().addListener((observable,oldVal, newVal) -> filteredList.setPredicate(stu -> {
            if(newVal == null || newVal.isEmpty()) return true;

            String searchKey = newVal.toLowerCase();
            if(String.valueOf(stu.getId()).contains(searchKey)) return true;
            else if(String.valueOf(stu.getYear()).contains(searchKey)) return true;
            else if(stu.getCourse().toLowerCase().contains(searchKey)) return true;
            else if(String.valueOf(stu.getFirstSem()).contains(searchKey)) return true;
            else return String.valueOf(stu.getSecondSem()).contains(searchKey);
        }));

        SortedList<Student> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(studentGrade_table.comparatorProperty());
        studentGrade_table.setItems(sortedList);
    }

    private void updateStudentTable(Student student) {
        for(Student obj:studentList) {
            if(obj.getId() == student.getId()) {
                obj.setYear(student.getYear());
                obj.setCourse(student.getCourse());
                obj.setFirstName(student.getFirstName());
                obj.setLastName(student.getLastName());
                obj.setGender(student.getGender());
                obj.setBirthDate(student.getBirthDate());
                obj.setStatus(student.getStatus());
                obj.setImage(student.getImage());
            }
        }
    }

    @FXML
    private void updateStudent() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Update Student Error");
        alert.setHeaderText(null);

        String year = addStudents_cbYear.getSelectionModel().getSelectedItem();
        String course = addStudents_cbCourse.getSelectionModel().getSelectedItem();
        String fName = addStudents_tfFName.getText().trim();
        String lName = addStudents_tfLName.getText().trim();
        String gender = addStudents_cbGender.getSelectionModel().getSelectedItem();
        String status = addStudents_cbStatus.getSelectionModel().getSelectedItem();
        String bdate = String.valueOf(addStudents_dtBirthDate.getValue());

        if(addStudents_tfId.getText().trim().isEmpty() || year == null || year.isEmpty() ||  course == null || course.isEmpty() || fName.isEmpty() ||
                lName.isEmpty() || gender == null || gender.isEmpty() || status == null || status.isEmpty() || bdate.isEmpty() || getImageData.path == null) {
            alert.setContentText("Please fill all blank fields");
            alert.show();
        }else {
            int id;
            try {
                id = Integer.parseInt(addStudents_tfId.getText().trim());
            }catch (NumberFormatException e ) {
                alert.setContentText("Invalid Id. All characters must be number digits");
                alert.show();
                return;
            }

            query = "SELECT * FROM student WHERE id_number = ?";
            result = DBUtils.fetchDb(query, String.valueOf(id));
            try {
                if(!result.isBeforeFirst()) {
                    alert.setContentText("This id does not exist.");
                    alert.show();
                }else {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Confirm Student Update");
                    alert.setContentText("Are you sure you want to update student #"+ id);
                    Optional<ButtonType> response = alert.showAndWait();
                    if(response.isPresent() && response.get().equals(ButtonType.OK)) {
                        query = "UPDATE student SET year = ?, course = ?, firstname = ?, lastname = ?, gender = ?, status = ?, image = ?," +
                                "birthdate = ? WHERE id_number = ?";
                        String uri = getImageData.path;
                        uri = uri.replace("\\", "\\\\");
                        DBUtils.insertDb(query, year,course,fName,lName,gender,status, uri, bdate, String.valueOf(id));

                        updateStudentTable(new Student(id, Integer.parseInt(year), course,fName,lName,gender,addStudents_dtBirthDate.getValue(), status, uri));
                        query = "SELECT * FROM student_grades WHERE id_number = ?";
                        result = DBUtils.fetchDb(query,String.valueOf(id));
                        if(result.isBeforeFirst()) {
                            query = "UPDATE student_grades SET year = ?, course = ? WHERE id_number = ?";
                            DBUtils.insertDb(query,year,course,String.valueOf(id));
                        }
                        clearStudentForm();
                        showStudentData();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Student Update Success");
                        alert.setContentText("Student has been updated successfully");
                        alert.show();
                    }
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtils.closeAllResources();
        }
    }

    @FXML
    private void addStudent() {
        String year = addStudents_cbYear.getSelectionModel().getSelectedItem();
        String course = addStudents_cbCourse.getSelectionModel().getSelectedItem();
        String fName = addStudents_tfFName.getText().trim();
        String lName = addStudents_tfLName.getText().trim();
        String gender = addStudents_cbGender.getSelectionModel().getSelectedItem();
        String status = addStudents_cbStatus.getSelectionModel().getSelectedItem();
        String bdate = String.valueOf(addStudents_dtBirthDate.getValue());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Add Student Error");
        alert.setHeaderText(null);
        if(addStudents_tfId.getText().trim().isEmpty() || year == null || year.isEmpty() ||  course == null || course.isEmpty() || fName.isEmpty() ||
            lName.isEmpty() || gender == null || gender.isEmpty() || status == null || status.isEmpty() || bdate.isEmpty() || getImageData.path == null) {
            alert.setContentText("Please fill all blank fields");
            alert.show();
        } else {
            int id;
            try {
                id = Integer.parseInt(addStudents_tfId.getText().trim());
            }catch (NumberFormatException e) {
                alert.setContentText("Invalid ID. All characters must be number digits");
                alert.show();
                return;
            }
            query = "SELECT * FROM student WHERE id_number= ?";
            result = DBUtils.fetchDb(query,String.valueOf(id));
            try {
                if(result.next()) {
                    alert.setContentText("A student with this id already exists");
                }else {
                    query = "INSERT INTO student(id_number,year,course,firstname,lastname,gender, birthdate,status, image,date) VALUES(?,?,?,?,?,?,?,?,?,?)";
                    String uri = getImageData.path;
                    uri = uri.replace("\\", "\\\\");
                    DBUtils.insertDb(query,String.valueOf(id),year,course,fName,lName,gender,bdate,status,uri,String.valueOf(LocalDate.now()));
                    query = "INSERT INTO student_grades(id_number,year,course) VALUES(?,?,?)";
                    DBUtils.insertDb(query, String.valueOf(id), year,course);
                    studentList.add(new Student(id,Integer.parseInt(year),course,fName,lName,gender,addStudents_dtBirthDate.getValue(),status,uri));
                    clearStudentForm();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Add Student Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Student have been added successfully");
                }
                showStudentData();
                alert.show();
            }catch (SQLException e) { e.printStackTrace(); }
            DBUtils.closeAllResources();
        }
    }

    @FXML
    private void insertImage() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Insert Student Picture");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.png", "*.jpg", "*.jpeg"));
        File file = fc.showOpenDialog(main_page.getScene().getWindow());
        if(file != null) {
            addStudents_image.setImage(new Image(file.toURI().toString(), 164,200,false,true));
            getImageData.path = file.getAbsolutePath();
        }


    }

    @FXML
    private void deleteStudent() {
        Student std = addStudents_table.getSelectionModel().getSelectedItem();
        Alert alert;
        if(std != null) {
            try {
                query = "SELECT * FROM student WHERE id_number = ?";
                result = DBUtils.fetchDb(query, String.valueOf(std.getId()));
                if (!result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Delete Student Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Sorry, student does not exist");
                    alert.show();
                }else {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Confirm Delete");
                    alert.setContentText("Are you sure you want to delete this student #" + std.getId());
                    Optional<ButtonType> response = alert.showAndWait();
                    if(response.isPresent() && response.get().equals(ButtonType.OK)) {
                        query = "DELETE FROM student WHERE id_number = ?";
                        DBUtils.insertDb(query,String.valueOf(std.getId()));
                        query = "DELETE FROM student_grades WHERE id_number = ?";
                        DBUtils.insertDb(query, String.valueOf(std.getId()));
                        clearStudentForm();
                        showStudentData();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Student has been successfully deleted");
                        alert.setTitle("Delete Student Success");
                        alert.show();
                    }
                }
            }catch (SQLException e) { e.printStackTrace(); }
            DBUtils.closeAllResources();
        }
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
                query = "DELETE FROM course WHERE name = ?";
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
    private void clearStudentForm() {
        addStudents_tfId.setText("");
        addStudents_cbCourse.getSelectionModel().clearSelection();
        addStudents_cbYear.getSelectionModel().clearSelection();
        addStudents_tfFName.setText("");
        addStudents_tfLName.setText("");
        addStudents_cbGender.getSelectionModel().clearSelection();
        addStudents_dtBirthDate.setValue(null);
        addStudents_cbStatus.getSelectionModel().clearSelection();
        addStudents_image.setImage(null);
        getImageData.path = null;
    }

    @FXML
    private void updateStudentGrade() {
        String id = studentGrade_tfId.getText().trim();
        String fSem = studentGrade_tfFirstSem.getText().trim();
        String sSem = studentGrade_tfSecondSem.getText().trim();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Update Student Error");
        if(id.isEmpty() || fSem.isEmpty() || sSem.isEmpty()) {
            alert.setContentText("Please fill in all blank fields");
            alert.show();
            return;
        }
        query = "SELECT * FROM student_grades WHERE id_number = ?";
        result = DBUtils.fetchDb(query,id);
        try {
            if(result.isBeforeFirst()) {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirm Student Update");
                alert.setContentText("Are you sure you want to update student #"+ id);
                Optional<ButtonType> response = alert.showAndWait();
                if(response.isPresent() && response.get().equals(ButtonType.OK)) {
                    query = "UPDATE student_grades SET first_sem = ?,second_sem = ?, final = ? WHERE id_number = ?";
                    double finals = (Double.parseDouble(fSem) + Double.parseDouble(sSem))/2;
                    DBUtils.insertDb(query,fSem,sSem,String.valueOf(finals), id);
                    showStudentGradeData();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Student Update Success");
                    alert.setContentText("Student has been successfully updated");
                    alert.show();
                }
            }else {
                alert.setContentText("This student does not exist");
                alert.show();
            }
        }catch (SQLException e) { e.printStackTrace();}
        DBUtils.closeAllResources();

    }

    @FXML
    private void clearCourseForm() {
        availableCourse_tfCourse.clear();
        availableCourse_taDescription.clear();
        availableCourse_tfDegree.clear();
        availableCourse_table.getSelectionModel().clearSelection();
    }

    @FXML
    private void clearStudentGradeForm() {
        studentGrade_tfId.setText("");
        studentGrade_tfFirstSem.setText("");
        studentGrade_tfYear.setText("");
        studentGrade_tfCourse.setText("");
        studentGrade_tfFirstSem.setText("");
        studentGrade_tfSecondSem.setText("");
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
            query = "SELECT * FROM course WHERE name = ?";
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
            query = "SELECT * FROM course WHERE name = ?";
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

    private void getCourseData() {
        courseList = FXCollections.observableArrayList();
        query = "SELECT * FROM course";
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
    }

    @FXML
    private void showCourseData() {
        getCourseData();
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

    private void getStudentGradeData() {
        studentGradeList = FXCollections.observableArrayList();
        query = "SELECT * FROM student_grades";
        result = DBUtils.fetchDb(query);
        try {
            if(result.isBeforeFirst()) {
                while (result.next()) {
                    int id = result.getInt("id_number");
                    int year = result.getInt("year");
                    String course = result.getString("course");
                    double firstSem = result.getDouble("first_sem");
                    double secondSem = result.getDouble("second_sem");
                    double finalSem = result.getDouble("final");
                    Student student = new Student(id,year,course,firstSem,secondSem,finalSem);
                    studentGradeList.add(student);
                }
            }
        }catch (SQLException e) { e.printStackTrace();}
        DBUtils.closeAllResources();
    }

    private void getStudentData() {
        studentList = FXCollections.observableArrayList();
        query = "SELECT * FROM student";
        result = DBUtils.fetchDb(query);
        try {
            if(result.isBeforeFirst()) {
                while (result.next()) {
                    Date bdate = result.getDate("birthdate");
                    LocalDate bd = bdate.toLocalDate();
                    Student student = new Student(result.getInt("id_number"), result.getInt("year"),
                            result.getString("course"), result.getString("firstName"),
                            result.getString("lastName"), result.getString("gender"),bd ,
                            result.getString("status"), result.getString("image"));
                    studentList.add(student);
                }
            }
        }catch (SQLException e) { e.printStackTrace(); }
        DBUtils.closeAllResources();
    }

    @FXML
    private void showStudentGradeData() {
        getStudentGradeData();
        studentGrade_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentGrade_col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        studentGrade_col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
        studentGrade_col_firstSem.setCellValueFactory(new PropertyValueFactory<>("firstSem"));
        studentGrade_col_secondSem.setCellValueFactory(new PropertyValueFactory<>("secondSem"));
        studentGrade_col_final.setCellValueFactory(new PropertyValueFactory<>("finals"));
        studentGrade_table.setItems(studentGradeList);
        studentGradeSearch();
    }

    @FXML
    private void showStudentData() {
        getStudentData();
        addStudents_colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addStudents_colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        addStudents_colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        addStudents_colFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addStudents_colLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addStudents_colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addStudents_colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        addStudents_colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        addStudents_table.setItems(studentList);
        populateCourseList();
        addStudentSearch();
    }

    @FXML
    private void showStudentGradeDataInForm() {
        Student student = studentGrade_table.getSelectionModel().getSelectedItem();
        if(student == null) return;
        studentGrade_tfId.setText(String.valueOf(student.getId()));
        studentGrade_tfYear.setText(String.valueOf(student.getYear()));
        studentGrade_tfCourse.setText(String.valueOf(student.getCourse()));
        studentGrade_tfFirstSem.setText(String.valueOf(student.getFirstSem()));
        studentGrade_tfSecondSem.setText(String.valueOf(student.getSecondSem()));
    }

    //Shows student data in form to be edited on add students page
    @FXML
    private void showStudentDataInForm() {
        Student stu = addStudents_table.getSelectionModel().getSelectedItem();
        if(stu != null) {
            addStudents_tfId.setText(String.valueOf(stu.getId()));
            addStudents_tfFName.setText(stu.getFirstName());
            addStudents_tfLName.setText(stu.getLastName());
            getImageData.path = stu.getImage();
            String uri = "file:" + stu.getImage();
            Image image = new Image(uri, 164, 200, false, true);
            addStudents_image.setImage(image);
        }
    }

    private void populateCourseList() {
        query = "SELECT * FROM course";
        List<String> list = new ArrayList<>();
        result = DBUtils.fetchDb(query);
        try {
            while (result.next()) {
                list.add(result.getString("name"));
            }
        }catch (SQLException e) { e.printStackTrace(); }
        DBUtils.closeAllResources();
        ObservableList<String> obList = FXCollections.observableArrayList(list);
        addStudents_cbCourse.setItems(obList);
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

                root.setOnMouseReleased((MouseEvent event) -> stage.setOpacity(1));
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
