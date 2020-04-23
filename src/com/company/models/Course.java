package com.company.models;

public class Course {

    private int id;
    private String name;
    private int maxSeats;
    private Teacher teacher;

    public Course(int id, String name, int maxSeats, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.maxSeats = maxSeats;
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
}
