package com.nikhil.educonnect.models;

public class Teacher extends User{
     private String subject;
     private int experienceYears;
     private String city;
     private double rating;

     public Teacher(String id,String name,String email,String password,String subject,int experienceYears,String city){
         super(id,name,email,password,"TEACHER");
         this.subject = subject;
         this.experienceYears = experienceYears;
         this.city= city;
         this.rating = 0.0;
     }

     public String getSubject(){return subject;}
    public int getExperienceYears(){return experienceYears;}
    public String getCity(){return city;}
    public double getRating(){return rating;}

    @Override
    public String toString() {
        return "Teacher{" +
                "subject='" + subject + '\'' +
                ", experienceYears=" + experienceYears +
                ", city='" + city + '\'' +
                ", rating=" + rating +
                '}';
    }
}
