package controller;

import java.sql.SQLException;
import java.util.List;

import database.dao.TeacherDAO;
import database.model.Teacher;

public class TeacherController {

    private static TeacherDAO teacherDAO;

    static {
        try {
            teacherDAO = new TeacherDAO();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static List<Teacher> list() throws SQLException {
        return teacherDAO.selectAll();
    }

    public Teacher getById(int id) throws SQLException {
        return teacherDAO.selectById(id);
    }

    public Teacher getByName(String name) throws SQLException {
        return teacherDAO.selectByName(name);
    }

    public void insert(String name, int id) throws SQLException {
        Teacher teacher = new Teacher(name, id);
        teacherDAO.insert(teacher);
    }

    public void update(int id, String name, int title) throws SQLException {
        Teacher teacher = new Teacher(id, name, title);
        teacherDAO.update(teacher);
    }

    public void delete(int id) throws SQLException {
        teacherDAO.delete(id);
    }

}
