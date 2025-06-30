package service;

import java.sql.SQLException;
import java.util.List;

import database.dao.CourseDAO;
import database.model.Course;

public class CourseService {

    private static CourseDAO courseDAO;

    static {
        try {
            courseDAO = new CourseDAO();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public List<Course> list() {
        try {
            return courseDAO.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Course getById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        }

        try {
            return courseDAO.selectById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Course getByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        try {
            return courseDAO.selectByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        }

        try {
            courseDAO.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Course create(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Name must be at least 3 characters long");
        }

        Course courseOnDatabase = null;
        try {
            courseOnDatabase = courseDAO.selectByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (courseOnDatabase != null) {
            throw new IllegalArgumentException("Course already exists");
        }

        Course course = new Course();
        course.setName(name);

        try {
            courseDAO.insert(course);
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Course update(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Name must be at least 3 characters long");
        }

        Course courseOnDatabase = courseOnDatabase(id);
        if (courseOnDatabase == null) {
            throw new IllegalArgumentException("Course not found");
        }

        Course course = new Course();
        course.setId(id);
        course.setName(name);

        try {
            courseDAO.update(course);
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Course courseOnDatabase(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        }

        try {
            Course course = courseDAO.selectById(id);
            if (course == null) {
                throw new SQLException("Course not found");
            }
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
