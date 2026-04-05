package com.nikhil.educonnect.models;

public class School extends User{
    private String schoolName;
    private String address;
    private String city;
    private String board;

    public School(String id,String schoolName,String email,String password,String address,String city,String board){
        super(id,schoolName,email,password,"SCHOOL");
        this.schoolName=schoolName;
        this.address= address;
        this.city = city;
        this.board = board;


    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getBoard() {
        return board;
    }
}
