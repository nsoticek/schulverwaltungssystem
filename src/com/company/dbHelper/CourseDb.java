package com.company.dbHelper;

import com.company.models.Course;
import com.company.models.Role;
import com.company.models.Student;
import com.company.models.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDb {

    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Course> studentCourses = new ArrayList<>();
    private ArrayList<Student> studentsOfCourse = new ArrayList<>();

    public ArrayList<Course> getCourses(DbConnector dbConnector) {
        if (courses.isEmpty()) {
            fetchCourses(dbConnector);
        }
        return courses;
    }

    public ArrayList<Course> getStudentCourses(int studentId, DbConnector dbConnector) {
        if (studentCourses.isEmpty()) {
            fetchStudentCourse(studentId, dbConnector);
        }
        return studentCourses;
    }

    public ArrayList<Student> getStudentsOfCourse(int courseId, DbConnector dbConnector) {
        if (studentsOfCourse.isEmpty()) {
            fetchStudentsOfCourse(courseId, dbConnector);
        }
        return studentsOfCourse;
    }

    private void fetchCourses(DbConnector dbConnector) {
        // Get all courses from DB and save them to arrayList "courses"
        // delete all old entries
        courses.clear();

        ResultSet rs = dbConnector.fetchData("SELECT course.id, course.name, " +
                "course.max_seats, user.id AS teacher_id, user.user_name, user.first_name, user.last_name FROM course " +
                "INNER JOIN user ON course.teacher = user.id;");
        if (rs == null) {
            System.out.println("Error bei fetchCourses! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int courseId = rs.getInt("id");
                String name = rs.getString("name");
                int maxSeats = rs.getInt("max_seats");
                int teacherId = rs.getInt("teacher_id");
                String teacherUsername = rs.getString("user_name");
                String teacherFirstName = rs.getString("first_name");
                String teacherLastName = rs.getString("last_name");

                courses.add(new Course(courseId, name, maxSeats, new Teacher(teacherId, teacherFirstName,
                        teacherLastName, teacherUsername, Role.TEACHER)));

            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }

    public void insertCourse(String name, int maxSeats, int teacherId, DbConnector dbConnector) {
        // Insert new Course in table "course"
        // delete all old entries
        courses.clear();

        boolean isInserted = dbConnector.insertData("INSERT INTO `course`(`name`, `max_seats`, `teacher`) " +
                "VALUES ('" + name + "', " + maxSeats + ", " + teacherId + ")");
        if (isInserted) {
            System.out.println("Daten werden aktualisiert");
        }
        fetchCourses(dbConnector);
    }

    public void insertCourseStudent(String selectedCourseID, int studentId, DbConnector dbConnector) {
        // insert in table course_student
        // delete all old entries
        studentCourses.clear();

        boolean isInserted = dbConnector.insertData("INSERT INTO `course_student`(`course`, `student`) " +
                "VALUES (" + Integer.valueOf(selectedCourseID) + "," + studentId + ")");
        if (isInserted) {
            System.out.println("Daten werden aktualisiert");
        }
        fetchStudentCourse(studentId, dbConnector);
    }

    private void fetchStudentCourse(int studentId, DbConnector dbConnector) {
        // Get all courses of current student and save them to arrayList "studentCourses"
        // delete all old entries
        studentCourses.clear();

        ResultSet rs = dbConnector.fetchData("SELECT course.id, course.name, course.teacher, " +
                "user.user_name, user.first_name, user.last_name, course.max_seats " +
                "FROM ((course_student INNER JOIN course ON course_student.course = course.id) " +
                "INNER JOIN user ON course.teacher = user.id) WHERE course_student.student = " + studentId);
        if (rs == null) {
            System.out.println("Error bei fetchStudentCourse! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int courseId = rs.getInt("id");
                String name = rs.getString("name");
                int teacherId = rs.getInt("teacher");
                String teacherUsername = rs.getString("user_name");
                String teacherFirstName = rs.getString("first_name");
                String teacherLastName = rs.getString("last_name");
                int maxSeats = rs.getInt("max_seats");

                studentCourses.add(new Course(courseId, name, maxSeats, new Teacher(teacherId, teacherFirstName,
                        teacherLastName, teacherUsername, Role.TEACHER)));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchStudentCourse!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }

    public int fetchOccupiedSeats(int courseId, DbConnector dbConnector) {
        // Get occupied seats of current course
        int occupiedSeats = 0;
        ResultSet rs = dbConnector.fetchData("SELECT COUNT(*) AS occupied_seats FROM course_student " +
                "WHERE course = " + courseId);
        if (rs == null) {
            System.out.println("Error bei fetchStudentCourse! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                occupiedSeats = rs.getInt("occupied_seats");
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchStudentCourse!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
        return occupiedSeats;
    }

    private void fetchStudentsOfCourse(int courseId, DbConnector dbConnector) {
        // Get all students of current course and save them to arrayList "studentsOfCourse"
        // delete all old entries
        studentsOfCourse.clear();

        ResultSet rs = dbConnector.fetchData("SELECT user.id, user.first_name, user.last_name, user.user_name " +
                "FROM course_student INNER JOIN user ON course_student.student = user.id " +
                "WHERE user.role = 'STUDENT' AND course = " + courseId);
        if (rs == null) {
            System.out.println("Error bei fetchStudentsOfCourse! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String userName = rs.getString("user_name");

                studentsOfCourse.add(new Student(id, firstName, lastName, userName, Role.STUDENT));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchStudentsOfCourse!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }
}
