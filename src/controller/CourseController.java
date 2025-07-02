package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import database.model.Course;
import service.CourseService;

public class CourseController {

    private static CourseService courseService;

    static {
        courseService = new CourseService();
    }

    public static List<Course> list() throws SQLException {
        List<Course> courses = courseService.list();
        return courses;
    }

    public static Course getById(int id) throws SQLException {
		Course course = courseService.getById(id);
        return course;
    }

    public static Course getByName(String name) throws SQLException {
        Course course = courseService.getByName(name);
        return course;
    }

    public static Course insert(String name, LocalDate processingDate, String startPhase, String endPhase, int sequence, String layout) throws SQLException {
        Course course = courseService.create(name, processingDate, startPhase, endPhase, sequence, layout);
        return course;
    }

    public static Course update(int id, String name, LocalDate processingDate, String startPhase, String endPhase, int sequence, String layout) throws SQLException {
        Course course = courseService.update(id, name, processingDate, startPhase, endPhase, sequence, layout);
        return course;
    }

    public static void delete(int id) throws SQLException {
        courseService.delete(id);
    }
}
