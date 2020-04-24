package com.company.models;

public class Student extends Person {

    private int grade;

    public Student(int id, String firstName, String lastName, String username, int grade, Enum role) {
        super(id, firstName, lastName, username, role);
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }
}
