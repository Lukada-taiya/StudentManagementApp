package com.lutadam.studentmanagementapp;

import java.sql.Date;

public class Student {
    private int id;
    private int year;
    private String course;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDate;
    private String status;
    private String image;

    public Student(int id, int year, String course, String firstName, String lastName, String gender, Date birthDate, String status, String image) {
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

    public Date getBirthDate() {
        return birthDate;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }
}
