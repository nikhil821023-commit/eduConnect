package com.nikhil.educonnect.models;

public class Parent extends User{
    private String city;
    private String childGrade;

    public Parent(String id,String name,String email,String password,String city,String childGrade){
        super(id,name,email,password,"PARENT");
        this.city=city;
        this.childGrade=childGrade;

    }

    public String getCity() {
        return city;
    }

    public String getChildGrade() {
        return childGrade;
    }
}
