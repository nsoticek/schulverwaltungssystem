package com.company.models;

public class Course {

    private int id;
    private String name;
    private int maxSeats;
    private Teacher teacher;
    private int grade;

    public Course(int id, String name, int maxSeats, int grade, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.maxSeats = maxSeats;
        this.grade = grade;
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public int getGrade() {
        return grade;
    }
}
