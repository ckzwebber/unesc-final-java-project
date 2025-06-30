package controller;

import java.sql.SQLException;
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

    public static Course insert(String name) throws SQLException {
        Course course = courseService.create(name);
        return course;
    }

    public static Course update(int id, String name) throws SQLException {
        Course course = courseService.update(id, name);
        return course;
    }

    public static void delete(int id) throws SQLException {
        courseService.delete(id);
    }
}
