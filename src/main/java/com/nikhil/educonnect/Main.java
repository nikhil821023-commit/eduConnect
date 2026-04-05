package com.nikhil.educonnect;

import com.nikhil.educonnect.models.Parent;
import com.nikhil.educonnect.models.School;
import com.nikhil.educonnect.models.Teacher;

public class Main {
    public static void main(String[] args){

        Teacher teacher = new Teacher("T001","Nikhil","nikhil@1gmail.com","pass123","Math",2,"Beg");

        School school = new School("S001","DPS","DPS@1gmail.com","pass123","newtown","kolkata","ICSE");

        Parent parent = new Parent("P001","Mithlesh","m@1gmail.com","pass123","munger","3");

        System.out.println(teacher);
        System.out.println(school.getSchoolName()+ " - " + school.getBoard());
        System.out.println(parent.getName()+ " - " + parent.getChildGrade());
    }
}
