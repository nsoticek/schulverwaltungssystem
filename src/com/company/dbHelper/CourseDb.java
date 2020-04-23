package com.company.dbHelper;

import com.company.models.Course;
import com.company.models.Student;
import com.company.models.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDb {

    private DbConnector dbConnector = new DbConnector();
    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Course> studentCourses = new ArrayList<>();
    private ArrayList<Student> studentsOfCourse = new ArrayList<>();

    public ArrayList<Course> getCourses() {
        if (courses.isEmpty()) {
            fetchCourses();
        }
        return courses;
    }

    public ArrayList<Course> getStudentCourses(int id) {
        if (studentCourses.isEmpty()) {
            fetchStudentCourse(id);
        }
        return studentCourses;
    }

    public ArrayList<Student> getStudentsOfCourse(int courseId) {
        if (studentsOfCourse.isEmpty()) {
            fetchStudentsOfCourse(courseId);
        }
        return studentsOfCourse;
    }

    private void fetchCourses() {
        // Get all courses from DB and save them to arrayList "courses"
        // delete all old entries
        courses.clear();

        ResultSet rs = dbConnector.fetchData("SELECT course.id, course.name, " +
                "course.max_seats, user.id AS teacher_id, user.first_name, user.last_name FROM course " +
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
                String teacherFirstName = rs.getString("first_name");
                String teacherLastName = rs.getString("last_name");

                courses.add(new Course(courseId, name, maxSeats, new Teacher(teacherId, teacherFirstName, teacherLastName)));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }

    public void insertCourse(Course course) {
        // Insert new Course in table "course"
        // delete all old entries
        courses.clear();

        boolean isInserted = dbConnector.insertData("INSERT INTO `course`(`name`, `max_seats`, `teacher`) " +
                "VALUES ('" + course.getName() + "', " + course.getMaxSeats() + ", " + course.getTeacher().getId() + ")");
        if (isInserted) {
            System.out.println("Daten werden aktualisiert");
        }
        fetchCourses();
    }

    public void insertCourseStudent(String selectedCourseID, int studentId) {
        // insert in table course_student
        // delete all old entries
        studentCourses.clear();

        boolean isInserted = dbConnector.insertData("INSERT INTO `course_student`(`course`, `student`) " +
                "VALUES (" + Integer.valueOf(selectedCourseID) + "," + studentId + ")");
        if (isInserted) {
            System.out.println("Daten werden aktualisiert");
        }
        fetchStudentCourse(studentId);
    }

    private void fetchStudentCourse(int studentId) {
        // Get all courses of current student and save them to arrayList "studentCourses"
        // delete all old entries
        studentCourses.clear();

        ResultSet rs = dbConnector.fetchData("SELECT course.id, course.name, course.teacher, course.max_seats " +
                "FROM course_student INNER JOIN course ON course_student.course = course.id WHERE student = " + studentId);
        if (rs == null) {
            System.out.println("Error bei fetchStudentCourse! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int teacherId = rs.getInt("teacher");
                int maxSeats = rs.getInt("max_seats");

                studentCourses.add(new Course(id, name, maxSeats, new Teacher(teacherId)));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchStudentCourse!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }

    public int fetchOccupiedSeats(int courseId) {
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

    private void fetchStudentsOfCourse(int courseId) {
        // Get all students of current course and save them to arrayList "studentsOfCourse"
        // delete all old entries
        studentsOfCourse.clear();

        ResultSet rs = dbConnector.fetchData("SELECT user.id, user.first_name, user.last_name " +
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

                studentsOfCourse.add(new Student(id, firstName, lastName));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchStudentsOfCourse!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }
}
