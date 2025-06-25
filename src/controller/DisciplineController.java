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

    public Discipline getById(int id) throws SQLException {
        return disciplineDAO.selectById(id);
    }

    public Discipline getByName(String name) throws SQLException {
        return disciplineDAO.selectByName(name);
    }

    public void insert(String code, String name, int weekDay, Phase phase) throws SQLException {
        Discipline discipline = new Discipline(code, name, weekDay, phase);
        disciplineDAO.insert(discipline);
    }

    public void update(int id, String code, String name, int weekDay, Phase phase) throws SQLException {
        Discipline discipline = new Discipline(id, code, name, weekDay, phase);
        disciplineDAO.update(discipline);
    }

    public void delete(int id) throws SQLException {
        disciplineDAO.delete(id);
    }

}
