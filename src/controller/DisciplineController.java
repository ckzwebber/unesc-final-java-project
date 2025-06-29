package controller;

import java.sql.SQLException;
import java.util.List;

import database.dao.DisciplineDAO;
import database.model.Discipline;
import database.model.Phase;

public class DisciplineController {

    private static DisciplineDAO disciplineDAO;

    static {
        try {
            disciplineDAO = new DisciplineDAO();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static List<Discipline> list() throws SQLException {
        return disciplineDAO.selectAll();
    }

    public static Discipline getById(int id) throws SQLException {
        return disciplineDAO.selectById(id);
    }

    public static Discipline getByName(String name) throws SQLException {
        return disciplineDAO.selectByName(name);
    }

    public static void insert(String code, String name, int weekDay, Phase phase) throws SQLException {
        Discipline discipline = new Discipline(code, name, weekDay, phase);
        disciplineDAO.insert(discipline);
    }

    public static void update(int id, String code, String name, int weekDay, Phase phase) throws SQLException {
        Discipline discipline = new Discipline(id, code, name, weekDay, phase);
        disciplineDAO.update(discipline);
    }

    public static void delete(int id) throws SQLException {
        disciplineDAO.delete(id);
    }

}
