package com.lutadam.studentmanagementapp;

import java.sql.Date;
import java.time.LocalDate;

public class Student {
    private int id;
    private int year;
    private String course;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthDate;
    private String status;
    private String image;

    public Student(int id, int year, String course, String firstName, String lastName, String gender, LocalDate birthDate, String status, String image) {
        this.id = id;
        this.year = year;
        this.course = course;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.status = status;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public String getCourse() {
        return course;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
