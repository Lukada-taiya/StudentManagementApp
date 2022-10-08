package com.lutadam.studentmanagementapp;

public class Course {
    private String name;
    private String description;
    private String degree;

    public Course(String name, String description, String degree) {
        this.name = name;
        this.description = description;
        this.degree = degree;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDegree() {
        return degree;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
