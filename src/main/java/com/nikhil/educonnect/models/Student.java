package com.nikhil.educonnect.models;

public class Student extends User {

    private String grade;
    private String city;
    private String parentId;

    public Student(String id, String name, String email,
                   String password, String grade,
                   String city, String parentId) {
        super(id, name, email, password, "STUDENT");
        this.grade = grade;
        this.city = city;
        this.parentId = parentId;
    }

    public String getGrade() { return grade; }
    public String getCity() { return city; }
    public String getParentId() { return parentId; }

    @Override
    public String toString() {
        return "Student{name=" + getName() +
                ", grade=" + grade +
                ", city=" + city + "}";
    }
}