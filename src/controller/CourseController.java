package controller;

import java.sql.SQLException;
import java.util.List;

import database.dao.CourseDAO;
import database.model.Course;

public class CourseController {

    private static CourseDAO courseDAO;

    static {
        try {
            courseDAO = new CourseDAO();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static List<Course> list() throws SQLException {
        return courseDAO.selectAll();
    }

    public static Course getById(int id) throws SQLException {
        return courseDAO.selectById(id);
    }

    public static Course getByName(String name) throws SQLException {
        return courseDAO.selectByName(name);
    }

    public static void insert(String name) throws SQLException {
        Course course = new Course(name);
        courseDAO.insert(course);
    }

    public static void update(int id, String name) throws SQLException {
        Course course = new Course(id, name);
        courseDAO.update(course);
    }

    public static void delete(int id) throws SQLException {
        courseDAO.delete(id);
    }
}
