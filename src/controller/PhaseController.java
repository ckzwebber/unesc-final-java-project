package controller;

import java.sql.SQLException;
import java.util.List;

import database.dao.PhaseDAO;
import database.model.Course;
import database.model.Phase;

public class PhaseController {

    private static PhaseDAO phaseDAO;

    static {
        try {
            phaseDAO = new PhaseDAO();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static List<Phase> list() throws SQLException {
        return phaseDAO.selectAll();
    }

    public static Phase getById(int id) throws SQLException {
        return phaseDAO.selectById(id);
    }

    public static void insert(String name, Course course) throws SQLException {
        Phase phase = new Phase(name, course);
        phaseDAO.insert(phase);
    }

    public static void update(int id, String name, Course course) throws SQLException {
        Phase phase = new Phase(id, name, course);
        phaseDAO.update(phase);
    }

    public static void delete(int id) throws SQLException {
        phaseDAO.delete(id);
    }

}
